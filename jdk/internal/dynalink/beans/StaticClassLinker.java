package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.GuardedInvocationComponent;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Lookup;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/StaticClassLinker.class */
class StaticClassLinker implements TypeBasedGuardingDynamicLinker {
    static final MethodHandle GET_CLASS;
    static final MethodHandle IS_CLASS;
    private static final ClassValue linkers = new ClassValue() { // from class: jdk.internal.dynalink.beans.StaticClassLinker.1
        @Override // java.lang.ClassValue
        protected Object computeValue(Class cls) {
            return computeValue(cls);
        }

        @Override // java.lang.ClassValue
        protected SingleClassStaticsLinker computeValue(Class cls) {
            return new SingleClassStaticsLinker(cls);
        }
    };
    static final MethodHandle ARRAY_CTOR = Lookup.PUBLIC.findStatic(Array.class, "newInstance", MethodType.methodType(Object.class, Class.class, Integer.TYPE));

    StaticClassLinker() {
    }

    static {
        Lookup lookup = new Lookup(MethodHandles.lookup());
        GET_CLASS = lookup.findVirtual(StaticClass.class, "getRepresentedClass", MethodType.methodType(Class.class));
        IS_CLASS = lookup.findOwnStatic("isClass", Boolean.TYPE, new Class[]{Class.class, Object.class});
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/StaticClassLinker$SingleClassStaticsLinker.class */
    private static class SingleClassStaticsLinker extends AbstractJavaLinker {
        private final DynamicMethod constructor;

        SingleClassStaticsLinker(Class cls) {
            super(cls, StaticClassLinker.IS_CLASS.bindTo(cls));
            setPropertyGetter("class", StaticClassLinker.GET_CLASS, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
            this.constructor = createConstructorMethod(cls);
        }

        private static DynamicMethod createConstructorMethod(Class cls) {
            if (cls.isArray()) {
                MethodHandle methodHandleBindTo = StaticClassLinker.ARRAY_CTOR.bindTo(cls.getComponentType());
                return new SimpleDynamicMethod(StaticClassIntrospector.editConstructorMethodHandle(methodHandleBindTo.asType(methodHandleBindTo.type().changeReturnType((Class<?>) cls))), cls, Constants.CTOR);
            }
            if (CheckRestrictedPackage.isRestrictedClass(cls)) {
                return null;
            }
            return createDynamicMethod(Arrays.asList(cls.getConstructors()), cls, Constants.CTOR);
        }

        @Override // jdk.internal.dynalink.beans.AbstractJavaLinker
        FacetIntrospector createFacetIntrospector() {
            return new StaticClassIntrospector(this.clazz);
        }

        @Override // jdk.internal.dynalink.beans.AbstractJavaLinker, jdk.internal.dynalink.linker.GuardingDynamicLinker
        public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
            MethodHandle invocation;
            GuardedInvocation guardedInvocation = super.getGuardedInvocation(linkRequest, linkerServices);
            if (guardedInvocation != null) {
                return guardedInvocation;
            }
            CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
            if ("new" == callSiteDescriptor.getNameToken(1) && this.constructor != null && (invocation = this.constructor.getInvocation(callSiteDescriptor, linkerServices)) != null) {
                return new GuardedInvocation(invocation, getClassGuard(callSiteDescriptor.getMethodType()));
            }
            return null;
        }

        SingleDynamicMethod getConstructorMethod(String str) {
            if (this.constructor != null) {
                return this.constructor.getMethodForExactParamTypes(str);
            }
            return null;
        }
    }

    static Object getConstructorMethod(Class cls, String str) {
        return ((SingleClassStaticsLinker) linkers.get(cls)).getConstructorMethod(str);
    }

    static Collection getReadableStaticPropertyNames(Class cls) {
        return ((SingleClassStaticsLinker) linkers.get(cls)).getReadablePropertyNames();
    }

    static Collection getWritableStaticPropertyNames(Class cls) {
        return ((SingleClassStaticsLinker) linkers.get(cls)).getWritablePropertyNames();
    }

    static Collection getStaticMethodNames(Class cls) {
        return ((SingleClassStaticsLinker) linkers.get(cls)).getMethodNames();
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        Object receiver = linkRequest.getReceiver();
        if (receiver instanceof StaticClass) {
            return ((SingleClassStaticsLinker) linkers.get(((StaticClass) receiver).getRepresentedClass())).getGuardedInvocation(linkRequest, linkerServices);
        }
        return null;
    }

    private static boolean isClass(Class cls, Object obj) {
        return (obj instanceof StaticClass) && ((StaticClass) obj).getRepresentedClass() == cls;
    }
}

package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/Guards.class */
public class Guards {
    private static final Logger LOG;
    private static final MethodHandle IS_INSTANCE;
    private static final MethodHandle IS_OF_CLASS;
    private static final MethodHandle IS_ARRAY;
    private static final MethodHandle IS_IDENTICAL;
    private static final MethodHandle IS_NULL;
    private static final MethodHandle IS_NOT_NULL;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Guards.class.desiredAssertionStatus();
        LOG = Logger.getLogger(Guards.class.getName(), "jdk.internal.dynalink.support.messages");
        IS_INSTANCE = Lookup.PUBLIC.findVirtual(Class.class, "isInstance", MethodType.methodType((Class<?>) Boolean.TYPE, (Class<?>) Object.class));
        Lookup lookup = new Lookup(MethodHandles.lookup());
        IS_OF_CLASS = lookup.findOwnStatic("isOfClass", Boolean.TYPE, new Class[]{Class.class, Object.class});
        IS_ARRAY = lookup.findOwnStatic("isArray", Boolean.TYPE, new Class[]{Object.class});
        IS_IDENTICAL = lookup.findOwnStatic("isIdentical", Boolean.TYPE, new Class[]{Object.class, Object.class});
        IS_NULL = lookup.findOwnStatic("isNull", Boolean.TYPE, new Class[]{Object.class});
        IS_NOT_NULL = lookup.findOwnStatic("isNotNull", Boolean.TYPE, new Class[]{Object.class});
    }

    private Guards() {
    }

    public static MethodHandle isOfClass(Class cls, MethodType methodType) {
        Class<?> clsParameterType = methodType.parameterType(0);
        if (cls == clsParameterType) {
            LOG.log(Level.WARNING, "isOfClassGuardAlwaysTrue", new Object[]{cls.getName(), 0, methodType, DynamicLinker.getLinkedCallSiteLocation()});
            return constantTrue(methodType);
        }
        if (!clsParameterType.isAssignableFrom(cls)) {
            LOG.log(Level.WARNING, "isOfClassGuardAlwaysFalse", new Object[]{cls.getName(), 0, methodType, DynamicLinker.getLinkedCallSiteLocation()});
            return constantFalse(methodType);
        }
        return getClassBoundArgumentTest(IS_OF_CLASS, cls, 0, methodType);
    }

    public static MethodHandle isInstance(Class cls, MethodType methodType) {
        return isInstance(cls, 0, methodType);
    }

    public static MethodHandle isInstance(Class cls, int i, MethodType methodType) {
        Class<?> clsParameterType = methodType.parameterType(i);
        if (cls.isAssignableFrom(clsParameterType)) {
            LOG.log(Level.WARNING, "isInstanceGuardAlwaysTrue", new Object[]{cls.getName(), Integer.valueOf(i), methodType, DynamicLinker.getLinkedCallSiteLocation()});
            return constantTrue(methodType);
        }
        if (!clsParameterType.isAssignableFrom(cls)) {
            LOG.log(Level.WARNING, "isInstanceGuardAlwaysFalse", new Object[]{cls.getName(), Integer.valueOf(i), methodType, DynamicLinker.getLinkedCallSiteLocation()});
            return constantFalse(methodType);
        }
        return getClassBoundArgumentTest(IS_INSTANCE, cls, i, methodType);
    }

    public static MethodHandle isArray(int i, MethodType methodType) {
        Class<?> clsParameterType = methodType.parameterType(i);
        if (clsParameterType.isArray()) {
            LOG.log(Level.WARNING, "isArrayGuardAlwaysTrue", new Object[]{Integer.valueOf(i), methodType, DynamicLinker.getLinkedCallSiteLocation()});
            return constantTrue(methodType);
        }
        if (!clsParameterType.isAssignableFrom(Object[].class)) {
            LOG.log(Level.WARNING, "isArrayGuardAlwaysFalse", new Object[]{Integer.valueOf(i), methodType, DynamicLinker.getLinkedCallSiteLocation()});
            return constantFalse(methodType);
        }
        return asType(IS_ARRAY, i, methodType);
    }

    public static boolean canReferenceDirectly(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader2 == null) {
            return true;
        }
        if (classLoader == null) {
            return false;
        }
        ClassLoader parent = classLoader;
        while (parent != classLoader2) {
            parent = parent.getParent();
            if (parent == null) {
                return false;
            }
        }
        return true;
    }

    private static MethodHandle getClassBoundArgumentTest(MethodHandle methodHandle, Class cls, int i, MethodType methodType) {
        return asType(methodHandle.bindTo(cls), i, methodType);
    }

    public static MethodHandle asType(MethodHandle methodHandle, MethodType methodType) {
        return methodHandle.asType(getTestType(methodHandle, methodType));
    }

    public static MethodHandle asType(LinkerServices linkerServices, MethodHandle methodHandle, MethodType methodType) {
        return linkerServices.asType(methodHandle, getTestType(methodHandle, methodType));
    }

    private static MethodType getTestType(MethodHandle methodHandle, MethodType methodType) {
        return methodType.dropParameterTypes(methodHandle.type().parameterCount(), methodType.parameterCount()).changeReturnType(Boolean.TYPE);
    }

    private static MethodHandle asType(MethodHandle methodHandle, int i, MethodType methodType) {
        if (!$assertionsDisabled && methodHandle == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && methodType == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && methodType.parameterCount() <= 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && (i < 0 || i >= methodType.parameterCount())) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && methodHandle.type().parameterCount() != 1) {
            throw new AssertionError();
        }
        if ($assertionsDisabled || methodHandle.type().returnType() == Boolean.TYPE) {
            return MethodHandles.permuteArguments(methodHandle.asType(methodHandle.type().changeParameterType(0, methodType.parameterType(i))), methodType.changeReturnType(Boolean.TYPE), i);
        }
        throw new AssertionError();
    }

    public static MethodHandle getClassGuard(Class cls) {
        return IS_OF_CLASS.bindTo(cls);
    }

    public static MethodHandle getInstanceOfGuard(Class cls) {
        return IS_INSTANCE.bindTo(cls);
    }

    public static MethodHandle getIdentityGuard(Object obj) {
        return IS_IDENTICAL.bindTo(obj);
    }

    public static MethodHandle isNull() {
        return IS_NULL;
    }

    public static MethodHandle isNotNull() {
        return IS_NOT_NULL;
    }

    private static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    private static boolean isOfClass(Class cls, Object obj) {
        return obj != null && obj.getClass() == cls;
    }

    private static MethodHandle constantTrue(MethodType methodType) {
        return constantBoolean(Boolean.TRUE, methodType);
    }

    private static MethodHandle constantFalse(MethodType methodType) {
        return constantBoolean(Boolean.FALSE, methodType);
    }

    private static MethodHandle constantBoolean(Boolean bool, MethodType methodType) {
        return MethodHandles.permuteArguments(MethodHandles.constant(Boolean.TYPE, bool), methodType.changeReturnType(Boolean.TYPE), new int[0]);
    }
}

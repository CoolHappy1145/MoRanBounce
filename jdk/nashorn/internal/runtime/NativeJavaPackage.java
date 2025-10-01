package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.annotations.Function;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/NativeJavaPackage.class */
public final class NativeJavaPackage extends ScriptObject {

    /* renamed from: MH */
    private static final MethodHandleFunctionality f50MH = MethodHandleFactory.getFunctionality();
    private static final MethodHandle CLASS_NOT_FOUND = findOwnMH("classNotFound", Void.TYPE, new Class[]{NativeJavaPackage.class});
    private static final MethodHandle TYPE_GUARD = Guards.getClassGuard(NativeJavaPackage.class);
    private final String name;

    public NativeJavaPackage(String str, ScriptObject scriptObject) {
        super(scriptObject, null);
        Context.checkPackageAccess(str);
        this.name = str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof NativeJavaPackage) {
            return this.name.equals(((NativeJavaPackage) obj).name);
        }
        return false;
    }

    public int hashCode() {
        if (this.name == null) {
            return 0;
        }
        return this.name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return toString();
    }

    public String toString() {
        return "[JavaPackage " + this.name + "]";
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getDefaultValue(Class cls) {
        if (cls == String.class) {
            return toString();
        }
        return super.getDefaultValue(cls);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findNewMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        return createClassNotFoundInvocation(callSiteDescriptor);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findCallMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        return createClassNotFoundInvocation(callSiteDescriptor);
    }

    private static GuardedInvocation createClassNotFoundInvocation(CallSiteDescriptor callSiteDescriptor) {
        MethodType methodType = callSiteDescriptor.getMethodType();
        return new GuardedInvocation(f50MH.dropArguments(CLASS_NOT_FOUND, 1, methodType.parameterList().subList(1, methodType.parameterCount())), methodType.parameterType(0) == NativeJavaPackage.class ? null : TYPE_GUARD);
    }

    private static void classNotFound(NativeJavaPackage nativeJavaPackage) throws ClassNotFoundException {
        throw new ClassNotFoundException(nativeJavaPackage.name);
    }

    @Function(attributes = 2)
    public static Object __noSuchProperty__(Object obj, Object obj2) {
        throw new AssertionError("__noSuchProperty__ placeholder called");
    }

    @Function(attributes = 2)
    public static Object __noSuchMethod__(Object obj, Object[] objArr) {
        throw new AssertionError("__noSuchMethod__ placeholder called");
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchProperty(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        createProperty(callSiteDescriptor.getNameToken(2));
        return super.lookup(callSiteDescriptor, linkRequest);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected Object invokeNoSuchProperty(String str, boolean z, int i) {
        Object objCreateProperty = createProperty(str);
        if (UnwarrantedOptimismException.isValid(i)) {
            throw new UnwarrantedOptimismException(objCreateProperty, i);
        }
        return objCreateProperty;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        return noSuchProperty(callSiteDescriptor, linkRequest);
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return f50MH.findStatic(MethodHandles.lookup(), NativeJavaPackage.class, str, f50MH.type(cls, clsArr));
    }

    private Object createProperty(String str) {
        Object objForClass;
        String str2 = this.name.isEmpty() ? str : this.name + "." + str;
        Context contextTrusted = Context.getContextTrusted();
        Class<?> clsFindClass = null;
        try {
            clsFindClass = contextTrusted.findClass(str2);
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
        }
        int iIndexOf = str.indexOf(40);
        int iLastIndexOf = str.lastIndexOf(41);
        if (iIndexOf != -1 || iLastIndexOf != -1) {
            int length = str.length() - 1;
            if (iIndexOf == -1 || iLastIndexOf != length) {
                throw ECMAErrors.typeError("improper.constructor.signature", new String[]{str});
            }
            String str3 = this.name + "." + str.substring(0, iIndexOf);
            try {
                Object constructorMethod = BeansLinker.getConstructorMethod(contextTrusted.findClass(str3), str.substring(iIndexOf + 1, length));
                if (constructorMethod != null) {
                    set(str, constructorMethod, 0);
                    return constructorMethod;
                }
                throw ECMAErrors.typeError("no.such.java.constructor", new String[]{str});
            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                throw ECMAErrors.typeError(e, "no.such.java.class", new String[]{str3});
            }
        }
        if (clsFindClass == null) {
            objForClass = new NativeJavaPackage(str2, getProto());
        } else {
            objForClass = StaticClass.forClass(clsFindClass);
        }
        set(str, objForClass, 0);
        return objForClass;
    }
}

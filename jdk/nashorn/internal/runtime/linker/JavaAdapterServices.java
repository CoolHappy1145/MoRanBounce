package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.InstructionAdapter;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import kotlin.text.Typography;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterServices.class */
public final class JavaAdapterServices {
    private static final ThreadLocal classOverrides;
    private static final MethodHandle NO_PERMISSIONS_INVOKER;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaAdapterServices.class.desiredAssertionStatus();
        classOverrides = new ThreadLocal();
        NO_PERMISSIONS_INVOKER = createNoPermissionsInvoker();
    }

    private JavaAdapterServices() {
    }

    public static MethodHandle getHandle(ScriptFunction scriptFunction, MethodType methodType) {
        return bindAndAdaptHandle(scriptFunction, scriptFunction.isStrict() ? ScriptRuntime.UNDEFINED : Context.getGlobal(), methodType);
    }

    public static MethodHandle getHandle(Object obj, String str, MethodType methodType) {
        if (!(obj instanceof ScriptObject)) {
            throw ECMAErrors.typeError("not.an.object", new String[]{ScriptRuntime.safeToString(obj)});
        }
        ScriptObject scriptObject = (ScriptObject) obj;
        if ("toString".equals(str) && !scriptObject.hasOwnProperty("toString")) {
            return null;
        }
        Object obj2 = scriptObject.get(str);
        if (obj2 instanceof ScriptFunction) {
            return bindAndAdaptHandle((ScriptFunction) obj2, scriptObject, methodType);
        }
        if (obj2 == null || (obj2 instanceof Undefined)) {
            return null;
        }
        throw ECMAErrors.typeError("not.a.function", new String[]{str});
    }

    public static Object getClassOverrides() {
        Object obj = classOverrides.get();
        if ($assertionsDisabled || obj != null) {
            return obj;
        }
        throw new AssertionError();
    }

    public static void invokeNoPermissions(MethodHandle methodHandle, Object obj) {
        (void) NO_PERMISSIONS_INVOKER.invokeExact(methodHandle, obj);
    }

    public static void setGlobal(Object obj) {
        Context.setGlobal((ScriptObject) obj);
    }

    public static Object getGlobal() {
        return Context.getGlobal();
    }

    static void setClassOverrides(ScriptObject scriptObject) {
        classOverrides.set(scriptObject);
    }

    private static MethodHandle bindAndAdaptHandle(ScriptFunction scriptFunction, Object obj, MethodType methodType) {
        return Bootstrap.getLinkerServices().asType(ScriptObject.pairArguments(scriptFunction.getBoundInvokeHandle(obj), methodType, false), methodType);
    }

    private static MethodHandle createNoPermissionsInvoker() {
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(51, 49, "NoPermissionsInvoker", (String) null, Constants.OBJECT, (String[]) null);
        Type type = Type.getType(Object.class);
        Type type2 = Type.getType(MethodHandle.class);
        InstructionAdapter instructionAdapter = new InstructionAdapter(classWriter.visitMethod(9, "invoke", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{type2, type}), (String) null, (String[]) null));
        instructionAdapter.visitCode();
        instructionAdapter.visitVarInsn(25, 0);
        instructionAdapter.visitVarInsn(25, 1);
        instructionAdapter.invokevirtual(type2.getInternalName(), "invokeExact", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{type}), false);
        instructionAdapter.visitInsn(Typography.plusMinus);
        instructionAdapter.visitMaxs(0, 0);
        instructionAdapter.visitEnd();
        classWriter.visitEnd();
        try {
            return MethodHandles.lookup().findStatic(Class.forName("NoPermissionsInvoker", true, (ClassLoader) AccessController.doPrivileged(new C02561(classWriter.toByteArray()))), "invoke", MethodType.methodType(Void.TYPE, MethodHandle.class, Object.class));
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e.getMessage(), e);
        }
    }

    /* renamed from: jdk.nashorn.internal.runtime.linker.JavaAdapterServices$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterServices$1.class */
    static class C02561 implements PrivilegedAction {
        final byte[] val$bytes;

        C02561(byte[] bArr) {
            this.val$bytes = bArr;
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            return run();
        }

        @Override // java.security.PrivilegedAction
        public ClassLoader run() {
            return new SecureClassLoader(this, null) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterServices.1.1
                final C02561 this$0;

                {
                    this.this$0 = this;
                }

                @Override // java.lang.ClassLoader
                protected Class findClass(String str) throws ClassNotFoundException {
                    if (str.equals("NoPermissionsInvoker")) {
                        return defineClass(str, this.this$0.val$bytes, 0, this.this$0.val$bytes.length, new ProtectionDomain(new CodeSource((URL) null, (CodeSigner[]) null), new Permissions()));
                    }
                    throw new ClassNotFoundException(str);
                }
            };
        }
    }

    public static MethodHandle getObjectConverter(Class cls) {
        return Bootstrap.getLinkerServices().getTypeConverter(Object.class, cls);
    }

    public static Object exportReturnValue(Object obj) {
        return NashornBeansLinker.exportArgument(obj, true);
    }

    public static char toCharPrimitive(Object obj) {
        return JavaArgumentConverters.toCharPrimitive(obj);
    }

    public static String toString(Object obj) {
        return JavaArgumentConverters.toString(obj);
    }
}

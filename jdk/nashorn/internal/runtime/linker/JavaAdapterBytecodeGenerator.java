package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.InstructionAdapter;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.linker.AdaptationResult;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import sun.reflect.CallerSensitive;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterBytecodeGenerator.class */
final class JavaAdapterBytecodeGenerator {
    private static final Type SCRIPTUTILS_TYPE;
    private static final Type OBJECT_TYPE;
    private static final Type CLASS_TYPE;
    static final String OBJECT_TYPE_NAME;
    static final String SCRIPTUTILS_TYPE_NAME;
    static final String INIT = "<init>";
    static final String GLOBAL_FIELD_NAME = "global";
    static final String GLOBAL_TYPE_DESCRIPTOR;
    static final String SET_GLOBAL_METHOD_DESCRIPTOR;
    static final String VOID_NOARG_METHOD_DESCRIPTOR;
    private static final Type SCRIPT_OBJECT_TYPE;
    private static final Type SCRIPT_FUNCTION_TYPE;
    private static final Type STRING_TYPE;
    private static final Type METHOD_TYPE_TYPE;
    private static final Type METHOD_HANDLE_TYPE;
    private static final String GET_HANDLE_OBJECT_DESCRIPTOR;
    private static final String GET_HANDLE_FUNCTION_DESCRIPTOR;
    private static final String GET_CLASS_INITIALIZER_DESCRIPTOR;
    private static final Type RUNTIME_EXCEPTION_TYPE;
    private static final Type THROWABLE_TYPE;
    private static final Type UNSUPPORTED_OPERATION_TYPE;
    private static final String SERVICES_CLASS_TYPE_NAME;
    private static final String RUNTIME_EXCEPTION_TYPE_NAME;
    private static final String ERROR_TYPE_NAME;
    private static final String THROWABLE_TYPE_NAME;
    private static final String UNSUPPORTED_OPERATION_TYPE_NAME;
    private static final String METHOD_HANDLE_TYPE_DESCRIPTOR;
    private static final String GET_GLOBAL_METHOD_DESCRIPTOR;
    private static final String GET_CLASS_METHOD_DESCRIPTOR;
    private static final String EXPORT_RETURN_VALUE_METHOD_DESCRIPTOR;
    private static final String UNWRAP_METHOD_DESCRIPTOR;
    private static final String GET_CONVERTER_METHOD_DESCRIPTOR;
    private static final String TO_CHAR_PRIMITIVE_METHOD_DESCRIPTOR;
    private static final String TO_STRING_METHOD_DESCRIPTOR;
    private static final String ADAPTER_PACKAGE_PREFIX = "jdk/nashorn/javaadapters/";
    private static final String ADAPTER_CLASS_NAME_SUFFIX = "$$NashornJavaAdapter";
    private static final String JAVA_PACKAGE_PREFIX = "java/";
    private static final int MAX_GENERATED_TYPE_NAME_LENGTH = 255;
    private static final String CLASS_INIT = "<clinit>";
    static final String SUPER_PREFIX = "super$";
    private static final Collection EXCLUDED;
    private final Class superClass;
    private final List interfaces;
    private final ClassLoader commonLoader;
    private final boolean classOverride;
    private final String superClassName;
    private final String generatedClassName;
    private final String samName;

    /* renamed from: cw */
    private final ClassWriter f60cw;
    private static final AccessControlContext GET_DECLARED_MEMBERS_ACC_CTXT;
    static final boolean $assertionsDisabled;
    private final Set usedFieldNames = new HashSet();
    private final Set abstractMethodNames = new HashSet();
    private final Set finalMethods = new HashSet(EXCLUDED);
    private final Set methodInfos = new HashSet();
    private boolean autoConvertibleFromFunction = false;
    private boolean hasExplicitFinalizer = false;
    private final Map converterFields = new LinkedHashMap();
    private final Set samReturnTypes = new HashSet();

    static {
        $assertionsDisabled = !JavaAdapterBytecodeGenerator.class.desiredAssertionStatus();
        SCRIPTUTILS_TYPE = Type.getType(ScriptUtils.class);
        OBJECT_TYPE = Type.getType(Object.class);
        CLASS_TYPE = Type.getType(Class.class);
        OBJECT_TYPE_NAME = OBJECT_TYPE.getInternalName();
        SCRIPTUTILS_TYPE_NAME = SCRIPTUTILS_TYPE.getInternalName();
        GLOBAL_TYPE_DESCRIPTOR = OBJECT_TYPE.getDescriptor();
        SET_GLOBAL_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{OBJECT_TYPE});
        VOID_NOARG_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]);
        SCRIPT_OBJECT_TYPE = Type.getType(ScriptObject.class);
        SCRIPT_FUNCTION_TYPE = Type.getType(ScriptFunction.class);
        STRING_TYPE = Type.getType(String.class);
        METHOD_TYPE_TYPE = Type.getType(MethodType.class);
        METHOD_HANDLE_TYPE = Type.getType(MethodHandle.class);
        GET_HANDLE_OBJECT_DESCRIPTOR = Type.getMethodDescriptor(METHOD_HANDLE_TYPE, new Type[]{OBJECT_TYPE, STRING_TYPE, METHOD_TYPE_TYPE});
        GET_HANDLE_FUNCTION_DESCRIPTOR = Type.getMethodDescriptor(METHOD_HANDLE_TYPE, new Type[]{SCRIPT_FUNCTION_TYPE, METHOD_TYPE_TYPE});
        GET_CLASS_INITIALIZER_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE, new Type[0]);
        RUNTIME_EXCEPTION_TYPE = Type.getType(RuntimeException.class);
        THROWABLE_TYPE = Type.getType(Throwable.class);
        UNSUPPORTED_OPERATION_TYPE = Type.getType(UnsupportedOperationException.class);
        SERVICES_CLASS_TYPE_NAME = Type.getInternalName(JavaAdapterServices.class);
        RUNTIME_EXCEPTION_TYPE_NAME = RUNTIME_EXCEPTION_TYPE.getInternalName();
        ERROR_TYPE_NAME = Type.getInternalName(Error.class);
        THROWABLE_TYPE_NAME = THROWABLE_TYPE.getInternalName();
        UNSUPPORTED_OPERATION_TYPE_NAME = UNSUPPORTED_OPERATION_TYPE.getInternalName();
        METHOD_HANDLE_TYPE_DESCRIPTOR = METHOD_HANDLE_TYPE.getDescriptor();
        GET_GLOBAL_METHOD_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE, new Type[0]);
        GET_CLASS_METHOD_DESCRIPTOR = Type.getMethodDescriptor(CLASS_TYPE, new Type[0]);
        EXPORT_RETURN_VALUE_METHOD_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE, new Type[]{OBJECT_TYPE});
        UNWRAP_METHOD_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE, new Type[]{OBJECT_TYPE});
        GET_CONVERTER_METHOD_DESCRIPTOR = Type.getMethodDescriptor(METHOD_HANDLE_TYPE, new Type[]{CLASS_TYPE});
        TO_CHAR_PRIMITIVE_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.CHAR_TYPE, new Type[]{OBJECT_TYPE});
        TO_STRING_METHOD_DESCRIPTOR = Type.getMethodDescriptor(STRING_TYPE, new Type[]{OBJECT_TYPE});
        EXCLUDED = getExcludedMethods();
        GET_DECLARED_MEMBERS_ACC_CTXT = ClassAndLoader.createPermAccCtxt(new String[]{"accessDeclaredMembers"});
    }

    JavaAdapterBytecodeGenerator(Class cls, List list, ClassLoader classLoader, boolean z) throws SecurityException, AdaptationException {
        if (!$assertionsDisabled && (cls == null || cls.isInterface())) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && list == null) {
            throw new AssertionError();
        }
        this.superClass = cls;
        this.interfaces = list;
        this.classOverride = z;
        this.commonLoader = classLoader;
        this.f60cw = new ClassWriter(this, 3) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterBytecodeGenerator.1
            final JavaAdapterBytecodeGenerator this$0;

            {
                this.this$0 = this;
            }

            protected String getCommonSuperClass(String str, String str2) {
                return this.this$0.getCommonSuperClass(str, str2);
            }
        };
        this.superClassName = Type.getInternalName(cls);
        this.generatedClassName = getGeneratedClassName(cls, list);
        this.f60cw.visit(51, 33, this.generatedClassName, (String) null, this.superClassName, getInternalTypeNames(list));
        generateGlobalFields();
        gatherMethods(cls);
        gatherMethods(list);
        this.samName = this.abstractMethodNames.size() == 1 ? (String) this.abstractMethodNames.iterator().next() : null;
        generateHandleFields();
        generateConverterFields();
        if (z) {
            generateClassInit();
        }
        generateConstructors();
        generateMethods();
        generateSuperMethods();
        if (this.hasExplicitFinalizer) {
            generateFinalizerMethods();
        }
        this.f60cw.visitEnd();
    }

    private void generateGlobalFields() {
        this.f60cw.visitField(18 | (this.classOverride ? 8 : 0), GLOBAL_FIELD_NAME, GLOBAL_TYPE_DESCRIPTOR, (String) null, (Object) null).visitEnd();
        this.usedFieldNames.add(GLOBAL_FIELD_NAME);
    }

    JavaAdapterClassLoader createAdapterClassLoader() {
        return new JavaAdapterClassLoader(this.generatedClassName, this.f60cw.toByteArray());
    }

    boolean isAutoConvertibleFromFunction() {
        return this.autoConvertibleFromFunction;
    }

    private static String getGeneratedClassName(Class cls, List list) {
        Class cls2 = cls == Object.class ? list.isEmpty() ? Object.class : (Class) list.get(0) : cls;
        Package r0 = cls2.getPackage();
        String internalName = Type.getInternalName(cls2);
        StringBuilder sb = new StringBuilder();
        if (internalName.startsWith(JAVA_PACKAGE_PREFIX) || r0 == null || r0.isSealed()) {
            sb.append(ADAPTER_PACKAGE_PREFIX).append(internalName);
        } else {
            sb.append(internalName).append(ADAPTER_CLASS_NAME_SUFFIX);
        }
        Iterator it = list.iterator();
        if (cls == Object.class && it.hasNext()) {
            it.next();
        }
        while (it.hasNext()) {
            sb.append("$$").append(((Class) it.next()).getSimpleName());
        }
        return sb.toString().substring(0, Math.min(255, sb.length()));
    }

    private static String[] getInternalTypeNames(List list) {
        int size = list.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = Type.getInternalName((Class) list.get(i));
        }
        return strArr;
    }

    private void generateHandleFields() {
        int i = 18 | (this.classOverride ? 8 : 0);
        Iterator it = this.methodInfos.iterator();
        while (it.hasNext()) {
            this.f60cw.visitField(i, ((MethodInfo) it.next()).methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR, (String) null, (Object) null).visitEnd();
        }
    }

    private void generateConverterFields() {
        int i = 18 | (this.classOverride ? 8 : 0);
        for (MethodInfo methodInfo : this.methodInfos) {
            Class<?> clsReturnType = methodInfo.type.returnType();
            if (!clsReturnType.isPrimitive() && clsReturnType != Object.class && clsReturnType != String.class && !this.converterFields.containsKey(clsReturnType)) {
                String strNextName = nextName("convert");
                this.converterFields.put(clsReturnType, strNextName);
                if (methodInfo.getName().equals(this.samName)) {
                    this.samReturnTypes.add(clsReturnType);
                }
                this.f60cw.visitField(i, strNextName, METHOD_HANDLE_TYPE_DESCRIPTOR, (String) null, (Object) null).visitEnd();
            }
        }
    }

    private void generateClassInit() {
        Label label;
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(8, "<clinit>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), (String) null, (String[]) null));
        instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "getClassOverrides", GET_CLASS_INITIALIZER_DESCRIPTOR, false);
        if (this.samName != null) {
            Label label2 = new Label();
            instructionAdapter.dup();
            instructionAdapter.instanceOf(SCRIPT_FUNCTION_TYPE);
            instructionAdapter.ifeq(label2);
            instructionAdapter.checkcast(SCRIPT_FUNCTION_TYPE);
            for (MethodInfo methodInfo : this.methodInfos) {
                if (methodInfo.getName().equals(this.samName)) {
                    instructionAdapter.dup();
                    loadMethodTypeAndGetHandle(instructionAdapter, methodInfo, GET_HANDLE_FUNCTION_DESCRIPTOR);
                } else {
                    instructionAdapter.visitInsn(1);
                }
                instructionAdapter.putstatic(this.generatedClassName, methodInfo.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
            }
            label = new Label();
            instructionAdapter.goTo(label);
            instructionAdapter.visitLabel(label2);
        } else {
            label = null;
        }
        for (MethodInfo methodInfo2 : this.methodInfos) {
            instructionAdapter.dup();
            instructionAdapter.aconst(methodInfo2.getName());
            loadMethodTypeAndGetHandle(instructionAdapter, methodInfo2, GET_HANDLE_OBJECT_DESCRIPTOR);
            instructionAdapter.putstatic(this.generatedClassName, methodInfo2.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
        }
        if (label != null) {
            instructionAdapter.visitLabel(label);
        }
        invokeGetGlobalWithNullCheck(instructionAdapter);
        instructionAdapter.putstatic(this.generatedClassName, GLOBAL_FIELD_NAME, GLOBAL_TYPE_DESCRIPTOR);
        generateConverterInit(instructionAdapter, false);
        endInitMethod(instructionAdapter);
    }

    private void generateConverterInit(InstructionAdapter instructionAdapter, boolean z) {
        if (!$assertionsDisabled && z && this.classOverride) {
            throw new AssertionError();
        }
        for (Map.Entry entry : this.converterFields.entrySet()) {
            Class cls = (Class) entry.getKey();
            if (!this.classOverride) {
                instructionAdapter.visitVarInsn(25, 0);
            }
            if (z && !this.samReturnTypes.contains(cls)) {
                instructionAdapter.visitInsn(1);
            } else {
                instructionAdapter.aconst(Type.getType((Class) entry.getKey()));
                instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "getObjectConverter", GET_CONVERTER_METHOD_DESCRIPTOR, false);
            }
            if (this.classOverride) {
                instructionAdapter.putstatic(this.generatedClassName, (String) entry.getValue(), METHOD_HANDLE_TYPE_DESCRIPTOR);
            } else {
                instructionAdapter.putfield(this.generatedClassName, (String) entry.getValue(), METHOD_HANDLE_TYPE_DESCRIPTOR);
            }
        }
    }

    private static void loadMethodTypeAndGetHandle(InstructionAdapter instructionAdapter, MethodInfo methodInfo, String str) {
        instructionAdapter.aconst(Type.getMethodType(methodInfo.type.generic().toMethodDescriptorString()));
        instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "getHandle", str, false);
    }

    private static void invokeGetGlobalWithNullCheck(InstructionAdapter instructionAdapter) {
        invokeGetGlobal(instructionAdapter);
        instructionAdapter.dup();
        instructionAdapter.invokevirtual(OBJECT_TYPE_NAME, "getClass", GET_CLASS_METHOD_DESCRIPTOR, false);
        instructionAdapter.pop();
    }

    private void generateConstructors() throws SecurityException, AdaptationException {
        boolean z = false;
        for (Constructor<?> constructor : this.superClass.getDeclaredConstructors()) {
            if ((constructor.getModifiers() & 5) != 0 && !isCallerSensitive(constructor)) {
                generateConstructors(constructor);
                z = true;
            }
        }
        if (!z) {
            throw new AdaptationException(AdaptationResult.Outcome.ERROR_NO_ACCESSIBLE_CONSTRUCTOR, this.superClass.getCanonicalName());
        }
    }

    private void generateConstructors(Constructor constructor) {
        if (this.classOverride) {
            generateDelegatingConstructor(constructor);
            return;
        }
        generateOverridingConstructor(constructor, false);
        if (this.samName != null) {
            if (!this.autoConvertibleFromFunction && constructor.getParameterTypes().length == 0) {
                this.autoConvertibleFromFunction = true;
            }
            generateOverridingConstructor(constructor, true);
        }
    }

    private void generateDelegatingConstructor(Constructor constructor) {
        Type type = Type.getType(constructor);
        Type[] argumentTypes = type.getArgumentTypes();
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(1 | (constructor.isVarArgs() ? 128 : 0), "<init>", Type.getMethodDescriptor(type.getReturnType(), argumentTypes), (String) null, (String[]) null));
        instructionAdapter.visitCode();
        instructionAdapter.visitVarInsn(25, 0);
        int size = 1;
        for (Type type2 : argumentTypes) {
            instructionAdapter.load(size, type2);
            size += type2.getSize();
        }
        instructionAdapter.invokespecial(this.superClassName, "<init>", type.getDescriptor(), false);
        endInitMethod(instructionAdapter);
    }

    private void generateOverridingConstructor(Constructor constructor, boolean z) {
        Type type = Type.getType(constructor);
        Type[] argumentTypes = type.getArgumentTypes();
        int length = argumentTypes.length;
        Type[] typeArr = new Type[length + 1];
        typeArr[length] = z ? SCRIPT_FUNCTION_TYPE : SCRIPT_OBJECT_TYPE;
        System.arraycopy(argumentTypes, 0, typeArr, 0, length);
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(1, "<init>", Type.getMethodDescriptor(type.getReturnType(), typeArr), (String) null, (String[]) null));
        instructionAdapter.visitCode();
        instructionAdapter.visitVarInsn(25, 0);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        int size = 1;
        for (int i = 0; i < length; i++) {
            Type type2 = Type.getType(parameterTypes[i]);
            instructionAdapter.load(size, type2);
            size += type2.getSize();
        }
        instructionAdapter.invokespecial(this.superClassName, "<init>", type.getDescriptor(), false);
        String str = z ? GET_HANDLE_FUNCTION_DESCRIPTOR : GET_HANDLE_OBJECT_DESCRIPTOR;
        for (MethodInfo methodInfo : this.methodInfos) {
            instructionAdapter.visitVarInsn(25, 0);
            if (z && !methodInfo.getName().equals(this.samName)) {
                instructionAdapter.visitInsn(1);
            } else {
                instructionAdapter.visitVarInsn(25, size);
                if (!z) {
                    instructionAdapter.aconst(methodInfo.getName());
                }
                loadMethodTypeAndGetHandle(instructionAdapter, methodInfo, str);
            }
            instructionAdapter.putfield(this.generatedClassName, methodInfo.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
        }
        instructionAdapter.visitVarInsn(25, 0);
        invokeGetGlobalWithNullCheck(instructionAdapter);
        instructionAdapter.putfield(this.generatedClassName, GLOBAL_FIELD_NAME, GLOBAL_TYPE_DESCRIPTOR);
        generateConverterInit(instructionAdapter, z);
        endInitMethod(instructionAdapter);
        if (!z) {
            typeArr[length] = OBJECT_TYPE;
            generateOverridingConstructorWithObjectParam(new InstructionAdapter(this.f60cw.visitMethod(1, "<init>", Type.getMethodDescriptor(type.getReturnType(), typeArr), (String) null, (String[]) null)), constructor, type.getDescriptor());
        }
    }

    private void generateOverridingConstructorWithObjectParam(InstructionAdapter instructionAdapter, Constructor constructor, String str) {
        instructionAdapter.visitCode();
        instructionAdapter.visitVarInsn(25, 0);
        int size = 1;
        for (Class<?> cls : constructor.getParameterTypes()) {
            Type type = Type.getType(cls);
            instructionAdapter.load(size, type);
            size += type.getSize();
        }
        instructionAdapter.invokespecial(this.superClassName, "<init>", str, false);
        instructionAdapter.visitVarInsn(25, size);
        instructionAdapter.visitInsn(1);
        instructionAdapter.visitInsn(1);
        instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "getHandle", GET_HANDLE_OBJECT_DESCRIPTOR, false);
        endInitMethod(instructionAdapter);
    }

    private static void endInitMethod(InstructionAdapter instructionAdapter) {
        instructionAdapter.visitInsn(Typography.plusMinus);
        endMethod(instructionAdapter);
    }

    private static void endMethod(InstructionAdapter instructionAdapter) {
        instructionAdapter.visitMaxs(0, 0);
        instructionAdapter.visitEnd();
    }

    private static void invokeGetGlobal(InstructionAdapter instructionAdapter) {
        instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "getGlobal", GET_GLOBAL_METHOD_DESCRIPTOR, false);
    }

    private static void invokeSetGlobal(InstructionAdapter instructionAdapter) {
        instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "setGlobal", SET_GLOBAL_METHOD_DESCRIPTOR, false);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterBytecodeGenerator$MethodInfo.class */
    private static class MethodInfo {
        private final Method method;
        private final MethodType type;
        private String methodHandleFieldName;

        MethodInfo(Method method, C02491 c02491) {
            this(method);
        }

        MethodInfo(Class cls, String str, Class[] clsArr, C02491 c02491) {
            this(cls, str, clsArr);
        }

        private MethodInfo(Class cls, String str, Class[] clsArr) {
            this(cls.getDeclaredMethod(str, clsArr));
        }

        private MethodInfo(Method method) {
            this.method = method;
            this.type = Lookup.f31MH.type(method.getReturnType(), method.getParameterTypes());
        }

        public boolean equals(Object obj) {
            return (obj instanceof MethodInfo) && equals((MethodInfo) obj);
        }

        private boolean equals(MethodInfo methodInfo) {
            return getName().equals(methodInfo.getName()) && this.type.equals(methodInfo.type);
        }

        String getName() {
            return this.method.getName();
        }

        public int hashCode() {
            return getName().hashCode() ^ this.type.hashCode();
        }

        void setIsCanonical(JavaAdapterBytecodeGenerator javaAdapterBytecodeGenerator) {
            this.methodHandleFieldName = javaAdapterBytecodeGenerator.nextName(getName());
        }
    }

    private String nextName(String str) {
        int i = 0;
        String strConcat = str;
        while (true) {
            String str2 = strConcat;
            if (!this.usedFieldNames.add(str2)) {
                int i2 = i;
                i++;
                String strValueOf = String.valueOf(i2);
                int length = 255 - strValueOf.length();
                strConcat = (str.length() <= length ? str : str.substring(0, length)).concat(strValueOf);
            } else {
                return str2;
            }
        }
    }

    private void generateMethods() {
        Iterator it = this.methodInfos.iterator();
        while (it.hasNext()) {
            generateMethod((MethodInfo) it.next());
        }
    }

    private void generateMethod(MethodInfo methodInfo) {
        boolean z;
        Label label;
        Method method = methodInfo.method;
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        String[] exceptionNames = getExceptionNames(exceptionTypes);
        MethodType methodType = methodInfo.type;
        String methodDescriptorString = methodType.toMethodDescriptorString();
        String name = methodInfo.getName();
        Type[] argumentTypes = Type.getMethodType(methodDescriptorString).getArgumentTypes();
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(getAccessModifiers(method), name, methodDescriptorString, (String) null, exceptionNames));
        instructionAdapter.visitCode();
        Label label2 = new Label();
        Class<?> clsReturnType = methodType.returnType();
        Type type = Type.getType(clsReturnType);
        if (this.classOverride) {
            instructionAdapter.getstatic(this.generatedClassName, methodInfo.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
        } else {
            instructionAdapter.visitVarInsn(25, 0);
            instructionAdapter.getfield(this.generatedClassName, methodInfo.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
        }
        instructionAdapter.visitInsn(89);
        instructionAdapter.visitJumpInsn(199, label2);
        if (Modifier.isAbstract(method.getModifiers())) {
            instructionAdapter.anew(UNSUPPORTED_OPERATION_TYPE);
            instructionAdapter.dup();
            instructionAdapter.invokespecial(UNSUPPORTED_OPERATION_TYPE_NAME, "<init>", VOID_NOARG_METHOD_DESCRIPTOR, false);
            instructionAdapter.athrow();
        } else {
            instructionAdapter.visitInsn(87);
            emitSuperCall(instructionAdapter, method.getDeclaringClass(), name, methodDescriptorString);
        }
        instructionAdapter.visitLabel(label2);
        if (this.classOverride) {
            instructionAdapter.getstatic(this.generatedClassName, GLOBAL_FIELD_NAME, GLOBAL_TYPE_DESCRIPTOR);
        } else {
            instructionAdapter.visitVarInsn(25, 0);
            instructionAdapter.getfield(this.generatedClassName, GLOBAL_FIELD_NAME, GLOBAL_TYPE_DESCRIPTOR);
        }
        Label label3 = new Label();
        instructionAdapter.visitLabel(label3);
        int size = 1;
        for (Type type2 : argumentTypes) {
            size += type2.getSize();
        }
        int i = size;
        int i2 = size + 1;
        int i3 = i2 + 1;
        instructionAdapter.dup();
        invokeGetGlobal(instructionAdapter);
        instructionAdapter.dup();
        instructionAdapter.visitVarInsn(58, i);
        Label label4 = new Label();
        instructionAdapter.ifacmpne(label4);
        instructionAdapter.pop();
        instructionAdapter.iconst(0);
        Label label5 = new Label();
        instructionAdapter.goTo(label5);
        instructionAdapter.visitLabel(label4);
        invokeSetGlobal(instructionAdapter);
        instructionAdapter.iconst(1);
        instructionAdapter.visitLabel(label5);
        instructionAdapter.visitVarInsn(54, i2);
        int size2 = 1;
        for (Type type3 : argumentTypes) {
            instructionAdapter.load(size2, type3);
            boxStackTop(instructionAdapter, type3);
            size2 += type3.getSize();
        }
        Label label6 = new Label();
        instructionAdapter.visitLabel(label6);
        emitInvokeExact(instructionAdapter, methodType.generic());
        convertReturnValue(instructionAdapter, clsReturnType, type);
        Label label7 = new Label();
        instructionAdapter.visitLabel(label7);
        emitFinally(instructionAdapter, i, i2);
        instructionAdapter.areturn(type);
        int length = exceptionTypes.length;
        int i4 = 0;
        while (true) {
            if (i4 < length) {
                if (exceptionTypes[i4] != Throwable.class) {
                    i4++;
                } else {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        boolean z2 = z;
        if (!z2) {
            label = new Label();
            instructionAdapter.visitLabel(label);
            instructionAdapter.anew(RUNTIME_EXCEPTION_TYPE);
            instructionAdapter.dupX1();
            instructionAdapter.swap();
            instructionAdapter.invokespecial(RUNTIME_EXCEPTION_TYPE_NAME, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{THROWABLE_TYPE}), false);
        } else {
            label = null;
        }
        Label label8 = new Label();
        instructionAdapter.visitLabel(label8);
        emitFinally(instructionAdapter, i, i2);
        instructionAdapter.athrow();
        Label label9 = new Label();
        instructionAdapter.visitLabel(label9);
        instructionAdapter.visitLocalVariable("currentGlobal", GLOBAL_TYPE_DESCRIPTOR, (String) null, label3, label9, i);
        instructionAdapter.visitLocalVariable("globalsDiffer", Type.BOOLEAN_TYPE.getDescriptor(), (String) null, label3, label9, i2);
        if (z2) {
            instructionAdapter.visitTryCatchBlock(label6, label7, label8, THROWABLE_TYPE_NAME);
            if (!$assertionsDisabled && label != null) {
                throw new AssertionError();
            }
        } else {
            instructionAdapter.visitTryCatchBlock(label6, label7, label8, RUNTIME_EXCEPTION_TYPE_NAME);
            instructionAdapter.visitTryCatchBlock(label6, label7, label8, ERROR_TYPE_NAME);
            for (String str : exceptionNames) {
                instructionAdapter.visitTryCatchBlock(label6, label7, label8, str);
            }
            instructionAdapter.visitTryCatchBlock(label6, label7, label, THROWABLE_TYPE_NAME);
        }
        endMethod(instructionAdapter);
    }

    private void convertReturnValue(InstructionAdapter instructionAdapter, Class cls, Type type) {
        switch (type.getSort()) {
            case 0:
                instructionAdapter.pop();
                break;
            case 1:
                JSType.TO_BOOLEAN.invoke((MethodVisitor) instructionAdapter);
                break;
            case 2:
                instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "toCharPrimitive", TO_CHAR_PRIMITIVE_METHOD_DESCRIPTOR, false);
                break;
            case 3:
                JSType.TO_INT32.invoke((MethodVisitor) instructionAdapter);
                instructionAdapter.visitInsn(145);
                break;
            case 4:
                JSType.TO_INT32.invoke((MethodVisitor) instructionAdapter);
                instructionAdapter.visitInsn(147);
                break;
            case 5:
                JSType.TO_INT32.invoke((MethodVisitor) instructionAdapter);
                break;
            case 6:
                JSType.TO_NUMBER.invoke((MethodVisitor) instructionAdapter);
                instructionAdapter.visitInsn(SyslogAppender.LOG_LOCAL2);
                break;
            case 7:
                JSType.TO_LONG.invoke((MethodVisitor) instructionAdapter);
                break;
            case 8:
                JSType.TO_NUMBER.invoke((MethodVisitor) instructionAdapter);
                break;
            default:
                if (type.equals(OBJECT_TYPE)) {
                    instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "exportReturnValue", EXPORT_RETURN_VALUE_METHOD_DESCRIPTOR, false);
                    break;
                } else if (type.equals(STRING_TYPE)) {
                    instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "toString", TO_STRING_METHOD_DESCRIPTOR, false);
                    break;
                } else {
                    if (this.classOverride) {
                        instructionAdapter.getstatic(this.generatedClassName, (String) this.converterFields.get(cls), METHOD_HANDLE_TYPE_DESCRIPTOR);
                    } else {
                        instructionAdapter.visitVarInsn(25, 0);
                        instructionAdapter.getfield(this.generatedClassName, (String) this.converterFields.get(cls), METHOD_HANDLE_TYPE_DESCRIPTOR);
                    }
                    instructionAdapter.swap();
                    emitInvokeExact(instructionAdapter, MethodType.methodType((Class<?>) cls, (Class<?>) Object.class));
                    break;
                }
        }
    }

    private static void emitInvokeExact(InstructionAdapter instructionAdapter, MethodType methodType) {
        instructionAdapter.invokevirtual(METHOD_HANDLE_TYPE.getInternalName(), "invokeExact", methodType.toMethodDescriptorString(), false);
    }

    private static void boxStackTop(InstructionAdapter instructionAdapter, Type type) {
        switch (type.getSort()) {
            case 1:
                invokeValueOf(instructionAdapter, "Boolean", 'Z');
                return;
            case 2:
                invokeValueOf(instructionAdapter, "Character", 'C');
                return;
            case 3:
            case 4:
            case 5:
                invokeValueOf(instructionAdapter, "Integer", 'I');
                return;
            case 6:
                instructionAdapter.visitInsn(141);
                invokeValueOf(instructionAdapter, "Double", 'D');
                return;
            case 7:
                invokeValueOf(instructionAdapter, "Long", 'J');
                return;
            case 8:
                invokeValueOf(instructionAdapter, "Double", 'D');
                return;
            case 9:
            case 11:
                return;
            case 10:
                if (type.equals(OBJECT_TYPE)) {
                    instructionAdapter.invokestatic(SCRIPTUTILS_TYPE_NAME, "unwrap", UNWRAP_METHOD_DESCRIPTOR, false);
                    return;
                }
                return;
            default:
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                return;
        }
    }

    private static void invokeValueOf(InstructionAdapter instructionAdapter, String str, char c) {
        instructionAdapter.invokestatic("java/lang/" + str, "valueOf", "(" + c + ")Ljava/lang/" + str + ";", false);
    }

    private static void emitFinally(InstructionAdapter instructionAdapter, int i, int i2) {
        instructionAdapter.visitVarInsn(21, i2);
        Label label = new Label();
        instructionAdapter.ifeq(label);
        instructionAdapter.visitVarInsn(25, i);
        invokeSetGlobal(instructionAdapter);
        instructionAdapter.visitLabel(label);
    }

    private void generateSuperMethods() {
        for (MethodInfo methodInfo : this.methodInfos) {
            if (!Modifier.isAbstract(methodInfo.method.getModifiers())) {
                generateSuperMethod(methodInfo);
            }
        }
    }

    private void generateSuperMethod(MethodInfo methodInfo) {
        Method method = methodInfo.method;
        String methodDescriptorString = methodInfo.type.toMethodDescriptorString();
        String name = methodInfo.getName();
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(getAccessModifiers(method), "super$" + name, methodDescriptorString, (String) null, getExceptionNames(method.getExceptionTypes())));
        instructionAdapter.visitCode();
        emitSuperCall(instructionAdapter, method.getDeclaringClass(), name, methodDescriptorString);
        endMethod(instructionAdapter);
    }

    private Class findInvokespecialOwnerFor(Class cls) {
        if (!$assertionsDisabled && !Modifier.isInterface(cls.getModifiers())) {
            throw new AssertionError(cls + " is not an interface");
        }
        if (cls.isAssignableFrom(this.superClass)) {
            return this.superClass;
        }
        for (Class<?> cls2 : this.interfaces) {
            if (cls.isAssignableFrom(cls2)) {
                return cls2;
            }
        }
        throw new AssertionError("can't find the class/interface that extends " + cls);
    }

    private void emitSuperCall(InstructionAdapter instructionAdapter, Class cls, String str, String str2) {
        instructionAdapter.visitVarInsn(25, 0);
        int size = 1;
        Type methodType = Type.getMethodType(str2);
        for (Type type : methodType.getArgumentTypes()) {
            instructionAdapter.load(size, type);
            size += type.getSize();
        }
        if (Modifier.isInterface(cls.getModifiers())) {
            instructionAdapter.invokespecial(Type.getInternalName(findInvokespecialOwnerFor(cls)), str, str2, false);
        } else {
            instructionAdapter.invokespecial(this.superClassName, str, str2, false);
        }
        instructionAdapter.areturn(methodType.getReturnType());
    }

    private void generateFinalizerMethods() {
        String strNextName = nextName("access$");
        generateFinalizerDelegate(strNextName);
        generateFinalizerOverride(strNextName);
    }

    private void generateFinalizerDelegate(String str) {
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(10, str, Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{OBJECT_TYPE}), (String) null, (String[]) null));
        instructionAdapter.visitVarInsn(25, 0);
        instructionAdapter.checkcast(Type.getType(this.generatedClassName));
        instructionAdapter.invokespecial(this.superClassName, "finalize", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
        instructionAdapter.visitInsn(Typography.plusMinus);
        endMethod(instructionAdapter);
    }

    private void generateFinalizerOverride(String str) {
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.f60cw.visitMethod(1, "finalize", VOID_NOARG_METHOD_DESCRIPTOR, (String) null, (String[]) null));
        instructionAdapter.aconst(new Handle(6, this.generatedClassName, str, Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{OBJECT_TYPE})));
        instructionAdapter.visitVarInsn(25, 0);
        instructionAdapter.invokestatic(SERVICES_CLASS_TYPE_NAME, "invokeNoPermissions", Type.getMethodDescriptor(METHOD_HANDLE_TYPE, new Type[]{OBJECT_TYPE}), false);
        instructionAdapter.visitInsn(Typography.plusMinus);
        endMethod(instructionAdapter);
    }

    private static String[] getExceptionNames(Class[] clsArr) {
        String[] strArr = new String[clsArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            strArr[i] = Type.getInternalName(clsArr[i]);
        }
        return strArr;
    }

    private static int getAccessModifiers(Method method) {
        return 1 | (method.isVarArgs() ? 128 : 0);
    }

    private void gatherMethods(Class cls) throws AdaptationException {
        if (Modifier.isPublic(cls.getModifiers())) {
            for (Method method : cls.isInterface() ? cls.getMethods() : cls.getDeclaredMethods()) {
                String name = method.getName();
                if (!name.startsWith("super$")) {
                    int modifiers = method.getModifiers();
                    if (!Modifier.isStatic(modifiers) && (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers))) {
                        if (name.equals("finalize") && method.getParameterCount() == 0) {
                            if (cls != Object.class) {
                                this.hasExplicitFinalizer = true;
                                if (Modifier.isFinal(modifiers)) {
                                    throw new AdaptationException(AdaptationResult.Outcome.ERROR_FINAL_FINALIZER, cls.getCanonicalName());
                                }
                            } else {
                                continue;
                            }
                        } else {
                            MethodInfo methodInfo = new MethodInfo(method, null);
                            if (Modifier.isFinal(modifiers) || isCallerSensitive(method)) {
                                this.finalMethods.add(methodInfo);
                            } else if (!this.finalMethods.contains(methodInfo) && this.methodInfos.add(methodInfo)) {
                                if (Modifier.isAbstract(modifiers)) {
                                    this.abstractMethodNames.add(methodInfo.getName());
                                }
                                methodInfo.setIsCanonical(this);
                            }
                        }
                    }
                }
            }
        }
        if (!cls.isInterface()) {
            Class superclass = cls.getSuperclass();
            if (superclass != null) {
                gatherMethods(superclass);
            }
            for (Class<?> cls2 : cls.getInterfaces()) {
                gatherMethods(cls2);
            }
        }
    }

    private void gatherMethods(List list) throws AdaptationException {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            gatherMethods((Class) it.next());
        }
    }

    private static Collection getExcludedMethods() {
        return (Collection) AccessController.doPrivileged(new PrivilegedAction() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterBytecodeGenerator.2
            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public Collection run() {
                try {
                    return Arrays.asList(new MethodInfo(Object.class, "finalize", new Class[0], null), new MethodInfo(Object.class, "clone", new Class[0], null));
                } catch (NoSuchMethodException e) {
                    throw new AssertionError(e);
                }
            }
        }, GET_DECLARED_MEMBERS_ACC_CTXT);
    }

    private String getCommonSuperClass(String str, String str2) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName(str.replace('/', '.'), false, this.commonLoader);
            Class<?> cls2 = Class.forName(str2.replace('/', '.'), false, this.commonLoader);
            if (cls.isAssignableFrom(cls2)) {
                return str;
            }
            if (cls2.isAssignableFrom(cls)) {
                return str2;
            }
            if (cls.isInterface() || cls2.isInterface()) {
                return OBJECT_TYPE_NAME;
            }
            return assignableSuperClass(cls, cls2).getName().replace('.', '/');
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class assignableSuperClass(Class cls, Class cls2) {
        Class superclass = cls.getSuperclass();
        return superclass.isAssignableFrom(cls2) ? superclass : assignableSuperClass(superclass, cls2);
    }

    private static boolean isCallerSensitive(AccessibleObject accessibleObject) {
        return accessibleObject.isAnnotationPresent(CallerSensitive.class);
    }
}

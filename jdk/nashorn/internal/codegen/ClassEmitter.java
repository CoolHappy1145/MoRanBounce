package jdk.nashorn.internal.codegen;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.debug.NashornClassReader;
import jdk.nashorn.internal.p001ir.debug.NashornTextifier;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.scripts.C0278JS;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ClassEmitter.class */
public class ClassEmitter {
    private static final EnumSet DEFAULT_METHOD_FLAGS;
    private boolean classStarted;
    private boolean classEnded;
    private final HashSet methodsStarted;

    /* renamed from: cw */
    protected final ClassWriter f7cw;
    protected final Context context;
    private String unitClassName;
    private Set constantMethodNeeded;
    private int methodCount;
    private int initCount;
    private int clinitCount;
    private int fieldCount;
    private final Set methodNames;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ClassEmitter.class.desiredAssertionStatus();
        DEFAULT_METHOD_FLAGS = EnumSet.of(Flag.PUBLIC);
    }

    private ClassEmitter(Context context, ClassWriter classWriter) {
        this.context = context;
        this.f7cw = classWriter;
        this.methodsStarted = new HashSet();
        this.methodNames = new HashSet();
    }

    public Set getMethodNames() {
        return Collections.unmodifiableSet(this.methodNames);
    }

    ClassEmitter(Context context, String str, String str2, String[] strArr) {
        this(context, new ClassWriter(3));
        this.f7cw.visit(51, 33, str, (String) null, str2, strArr);
    }

    ClassEmitter(Context context, String str, String str2, boolean z) {
        this(context, new ClassWriter(3) { // from class: jdk.nashorn.internal.codegen.ClassEmitter.1
            private static final String OBJECT_CLASS = "java/lang/Object";

            protected String getCommonSuperClass(String str3, String str4) {
                try {
                    return super.getCommonSuperClass(str3, str4);
                } catch (RuntimeException unused) {
                    if (ClassEmitter.isScriptObject(Compiler.SCRIPTS_PACKAGE, str3) && ClassEmitter.isScriptObject(Compiler.SCRIPTS_PACKAGE, str4)) {
                        return CompilerConstants.className(ScriptObject.class);
                    }
                    return "java/lang/Object";
                }
            }
        });
        this.unitClassName = str2;
        this.constantMethodNeeded = new HashSet();
        this.f7cw.visit(51, 33, str2, (String) null, pathName(C0278JS.class.getName()), (String[]) null);
        this.f7cw.visitSource(str, (String) null);
        defineCommonStatics(z);
    }

    Context getContext() {
        return this.context;
    }

    String getUnitClassName() {
        return this.unitClassName;
    }

    public int getMethodCount() {
        return this.methodCount;
    }

    public int getClinitCount() {
        return this.clinitCount;
    }

    public int getInitCount() {
        return this.initCount;
    }

    public int getFieldCount() {
        return this.fieldCount;
    }

    private static String pathName(String str) {
        return str.replace('.', '/');
    }

    private void defineCommonStatics(boolean z) {
        field(EnumSet.of(Flag.PRIVATE, Flag.STATIC), CompilerConstants.SOURCE.symbolName(), Source.class);
        field(EnumSet.of(Flag.PRIVATE, Flag.STATIC), CompilerConstants.CONSTANTS.symbolName(), Object[].class);
        field(EnumSet.of(Flag.PUBLIC, Flag.STATIC, Flag.FINAL), CompilerConstants.STRICT_MODE.symbolName(), Boolean.TYPE, Boolean.valueOf(z));
    }

    private void defineCommonUtilities() {
        if (!$assertionsDisabled && this.unitClassName == null) {
            throw new AssertionError();
        }
        if (this.constantMethodNeeded.contains(String.class)) {
            MethodEmitter methodEmitterMethod = method(EnumSet.of(Flag.PRIVATE, Flag.STATIC), CompilerConstants.GET_STRING.symbolName(), String.class, new Class[]{Integer.TYPE});
            methodEmitterMethod.begin();
            methodEmitterMethod.getStatic(this.unitClassName, CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor()).load(Type.INT, 0).arrayload().checkcast(String.class)._return();
            methodEmitterMethod.end();
        }
        if (this.constantMethodNeeded.contains(PropertyMap.class)) {
            MethodEmitter methodEmitterMethod2 = method(EnumSet.of(Flag.PUBLIC, Flag.STATIC), CompilerConstants.GET_MAP.symbolName(), PropertyMap.class, new Class[]{Integer.TYPE});
            methodEmitterMethod2.begin();
            methodEmitterMethod2.loadConstants().load(Type.INT, 0).arrayload().checkcast(PropertyMap.class)._return();
            methodEmitterMethod2.end();
            MethodEmitter methodEmitterMethod3 = method(EnumSet.of(Flag.PUBLIC, Flag.STATIC), CompilerConstants.SET_MAP.symbolName(), Void.TYPE, new Class[]{Integer.TYPE, PropertyMap.class});
            methodEmitterMethod3.begin();
            methodEmitterMethod3.loadConstants().load(Type.INT, 0).load(Type.OBJECT, 1).arraystore();
            methodEmitterMethod3.returnVoid();
            methodEmitterMethod3.end();
        }
        for (Class cls : this.constantMethodNeeded) {
            if (cls.isArray()) {
                defineGetArrayMethod(cls);
            }
        }
    }

    private void defineGetArrayMethod(Class cls) {
        if (!$assertionsDisabled && this.unitClassName == null) {
            throw new AssertionError();
        }
        MethodEmitter methodEmitterMethod = method(EnumSet.of(Flag.PRIVATE, Flag.STATIC), getArrayMethodName(cls), cls, new Class[]{Integer.TYPE});
        methodEmitterMethod.begin();
        methodEmitterMethod.getStatic(this.unitClassName, CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor()).load(Type.INT, 0).arrayload().checkcast((Class<?>) cls).invoke(CompilerConstants.virtualCallNoLookup(cls, "clone", Object.class, new Class[0])).checkcast((Class<?>) cls)._return();
        methodEmitterMethod.end();
    }

    static String getArrayMethodName(Class cls) {
        if ($assertionsDisabled || cls.isArray()) {
            return CompilerConstants.GET_ARRAY_PREFIX.symbolName() + cls.getComponentType().getSimpleName() + CompilerConstants.GET_ARRAY_SUFFIX.symbolName();
        }
        throw new AssertionError();
    }

    void needGetConstantMethod(Class cls) {
        this.constantMethodNeeded.add(cls);
    }

    private static boolean isScriptObject(String str, String str2) {
        if (str2.startsWith(str) || str2.equals(CompilerConstants.className(ScriptObject.class)) || str2.startsWith(Compiler.OBJECTS_PACKAGE)) {
            return true;
        }
        return false;
    }

    public void begin() {
        this.classStarted = true;
    }

    public void end() {
        if (!$assertionsDisabled && !this.classStarted) {
            throw new AssertionError("class not started for " + this.unitClassName);
        }
        if (this.unitClassName != null) {
            MethodEmitter methodEmitterInit = init(EnumSet.of(Flag.PRIVATE), new Class[0]);
            methodEmitterInit.begin();
            methodEmitterInit.load(Type.OBJECT, 0);
            methodEmitterInit.newInstance(C0278JS.class);
            methodEmitterInit.returnVoid();
            methodEmitterInit.end();
            defineCommonUtilities();
        }
        this.f7cw.visitEnd();
        this.classStarted = false;
        this.classEnded = true;
        if (!$assertionsDisabled && !this.methodsStarted.isEmpty()) {
            throw new AssertionError("methodsStarted not empty " + this.methodsStarted);
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.PrintWriter, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.PrintWriter, java.lang.Throwable] */
    static String disassemble(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        try {
            NashornClassReader nashornClassReader = new NashornClassReader(bArr);
            nashornClassReader.accept(new TraceClassVisitor((ClassVisitor) null, new NashornTextifier(((Context) AccessController.doPrivileged(new PrivilegedAction() { // from class: jdk.nashorn.internal.codegen.ClassEmitter.2
                @Override // java.security.PrivilegedAction
                public Object run() {
                    return run();
                }

                @Override // java.security.PrivilegedAction
                public Context run() {
                    return Context.getContext();
                }
            })).getEnv(), nashornClassReader), printWriter), 0);
            if (printWriter != null) {
                close();
            }
            return new String(byteArrayOutputStream.toByteArray());
        } catch (Throwable th) {
            throw th;
        }
    }

    void beginMethod(MethodEmitter methodEmitter) {
        if (!$assertionsDisabled && this.methodsStarted.contains(methodEmitter)) {
            throw new AssertionError();
        }
        this.methodsStarted.add(methodEmitter);
    }

    void endMethod(MethodEmitter methodEmitter) {
        if (!$assertionsDisabled && !this.methodsStarted.contains(methodEmitter)) {
            throw new AssertionError();
        }
        this.methodsStarted.remove(methodEmitter);
    }

    MethodEmitter method(String str, Class cls, Class[] clsArr) {
        return method(DEFAULT_METHOD_FLAGS, str, cls, clsArr);
    }

    MethodEmitter method(EnumSet enumSet, String str, Class cls, Class[] clsArr) {
        this.methodCount++;
        this.methodNames.add(str);
        return new MethodEmitter(this, methodVisitor(enumSet, str, cls, clsArr));
    }

    MethodEmitter method(String str, String str2) {
        return method(DEFAULT_METHOD_FLAGS, str, str2);
    }

    MethodEmitter method(EnumSet enumSet, String str, String str2) {
        this.methodCount++;
        this.methodNames.add(str);
        return new MethodEmitter(this, this.f7cw.visitMethod(Flag.getValue(enumSet), str, str2, (String) null, (String[]) null));
    }

    MethodEmitter method(FunctionNode functionNode) {
        this.methodCount++;
        this.methodNames.add(functionNode.getName());
        return new MethodEmitter(this, this.f7cw.visitMethod(9 | (functionNode.isVarArg() ? 128 : 0), functionNode.getName(), new FunctionSignature(functionNode).toString(), (String) null, (String[]) null), functionNode);
    }

    MethodEmitter restOfMethod(FunctionNode functionNode) {
        this.methodCount++;
        this.methodNames.add(functionNode.getName());
        return new MethodEmitter(this, this.f7cw.visitMethod(9, functionNode.getName(), Type.getMethodDescriptor(functionNode.getReturnType().getTypeClass(), (Class<?>[]) new Class[]{RewriteException.class}), (String) null, (String[]) null), functionNode);
    }

    MethodEmitter clinit() {
        this.clinitCount++;
        return method(EnumSet.of(Flag.STATIC), CompilerConstants.CLINIT.symbolName(), Void.TYPE, new Class[0]);
    }

    MethodEmitter init() {
        this.initCount++;
        return method(CompilerConstants.INIT.symbolName(), Void.TYPE, new Class[0]);
    }

    MethodEmitter init(Class[] clsArr) {
        this.initCount++;
        return method(CompilerConstants.INIT.symbolName(), Void.TYPE, clsArr);
    }

    MethodEmitter init(EnumSet enumSet, Class[] clsArr) {
        this.initCount++;
        return method(enumSet, CompilerConstants.INIT.symbolName(), Void.TYPE, clsArr);
    }

    final void field(EnumSet enumSet, String str, Class cls, Object obj) {
        this.fieldCount++;
        this.f7cw.visitField(Flag.getValue(enumSet), str, CompilerConstants.typeDescriptor(cls), (String) null, obj).visitEnd();
    }

    final void field(EnumSet enumSet, String str, Class cls) {
        field(enumSet, str, cls, null);
    }

    final void field(String str, Class cls) {
        field(EnumSet.of(Flag.PUBLIC), str, cls, null);
    }

    byte[] toByteArray() {
        if (!$assertionsDisabled && !this.classEnded) {
            throw new AssertionError();
        }
        if (!this.classEnded) {
            return null;
        }
        return this.f7cw.toByteArray();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ClassEmitter$Flag.class */
    enum Flag {
        HANDLE_STATIC(6),
        HANDLE_NEWSPECIAL(8),
        HANDLE_SPECIAL(7),
        HANDLE_VIRTUAL(5),
        HANDLE_INTERFACE(9),
        FINAL(16),
        STATIC(8),
        PUBLIC(1),
        PRIVATE(2);

        private int value;

        Flag(int i) {
            this.value = i;
        }

        int getValue() {
            return this.value;
        }

        static int getValue(EnumSet enumSet) {
            int value = 0;
            Iterator it = enumSet.iterator();
            while (it.hasNext()) {
                value |= ((Flag) it.next()).getValue();
            }
            return value;
        }
    }

    private MethodVisitor methodVisitor(EnumSet enumSet, String str, Class cls, Class[] clsArr) {
        return this.f7cw.visitMethod(Flag.getValue(enumSet), str, CompilerConstants.methodDescriptor(cls, clsArr), (String) null, (String[]) null);
    }
}

package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilerConstants.class */
public enum CompilerConstants {
    __FILE__,
    __DIR__,
    __LINE__,
    INIT(Constants.CTOR),
    CLINIT(Constants.CLINIT),
    EVAL("eval"),
    SOURCE("source", Source.class),
    CONSTANTS("constants", Object[].class),
    STRICT_MODE("strictMode", Boolean.TYPE),
    DEFAULT_SCRIPT_NAME("Script"),
    ANON_FUNCTION_PREFIX("L:"),
    NESTED_FUNCTION_SEPARATOR("#"),
    ID_FUNCTION_SEPARATOR("-"),
    PROGRAM(":program"),
    CREATE_PROGRAM_FUNCTION(":createProgramFunction"),
    THIS("this", Object.class),
    THIS_DEBUGGER(":this"),
    SCOPE(":scope", ScriptObject.class, 2),
    RETURN(":return"),
    CALLEE(":callee", ScriptFunction.class),
    VARARGS(":varargs", Object[].class),
    ARGUMENTS_VAR("arguments", Object.class),
    ARGUMENTS(":arguments", ScriptObject.class),
    EXPLODED_ARGUMENT_PREFIX(":xarg"),
    ITERATOR_PREFIX(":i", Iterator.class),
    SWITCH_TAG_PREFIX(":s"),
    EXCEPTION_PREFIX(":e", Throwable.class),
    QUICK_PREFIX(":q"),
    TEMP_PREFIX(":t"),
    LITERAL_PREFIX(":l"),
    REGEX_PREFIX(":r"),
    JAVA_THIS((String) null, 0),
    INIT_MAP((String) null, 1),
    INIT_SCOPE((String) null, 2),
    INIT_ARGUMENTS((String) null, 3),
    JS_OBJECT_DUAL_FIELD_PREFIX("JD"),
    JS_OBJECT_SINGLE_FIELD_PREFIX("JO"),
    ALLOCATE("allocate"),
    SPLIT_PREFIX(":split"),
    SPLIT_ARRAY_ARG(":split_array", 3),
    GET_STRING(":getString"),
    GET_MAP(":getMap"),
    SET_MAP(":setMap"),
    GET_ARRAY_PREFIX(":get"),
    GET_ARRAY_SUFFIX("$array");

    private static Set symbolNames;
    private static final String INTERNAL_METHOD_PREFIX = ":";
    private final String symbolName;
    private final Class type;
    private final int slot;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !CompilerConstants.class.desiredAssertionStatus();
        for (CompilerConstants compilerConstants : values()) {
            String strSymbolName = compilerConstants.symbolName();
            if (strSymbolName != null) {
                strSymbolName.intern();
            }
        }
    }

    CompilerConstants() {
        this.symbolName = name();
        this.type = null;
        this.slot = -1;
    }

    CompilerConstants(String str) {
        this(str, -1);
    }

    CompilerConstants(String str, int i) {
        this(str, null, i);
    }

    CompilerConstants(String str, Class cls) {
        this(str, cls, -1);
    }

    CompilerConstants(String str, Class cls, int i) {
        this.symbolName = str;
        this.type = cls;
        this.slot = i;
    }

    public static boolean isCompilerConstant(String str) {
        ensureSymbolNames();
        return symbolNames.contains(str);
    }

    private static void ensureSymbolNames() {
        if (symbolNames == null) {
            symbolNames = new HashSet();
            for (CompilerConstants compilerConstants : values()) {
                symbolNames.add(compilerConstants.symbolName);
            }
        }
    }

    public final String symbolName() {
        return this.symbolName;
    }

    public final Class type() {
        return this.type;
    }

    public final int slot() {
        return this.slot;
    }

    public final String descriptor() {
        if ($assertionsDisabled || this.type != null) {
            return typeDescriptor(this.type);
        }
        throw new AssertionError(" asking for descriptor of typeless constant");
    }

    public static String className(Class cls) {
        return Type.getInternalName(cls);
    }

    public static String methodDescriptor(Class cls, Class[] clsArr) {
        return Type.getMethodDescriptor((Class<?>) cls, (Class<?>[]) clsArr);
    }

    public static String typeDescriptor(Class cls) {
        return Type.typeFor((Class<?>) cls).getDescriptor();
    }

    public static Call constructorNoLookup(Class cls) {
        return specialCallNoLookup(cls, INIT.symbolName(), Void.TYPE, new Class[0]);
    }

    public static Call constructorNoLookup(String str, Class[] clsArr) {
        return specialCallNoLookup(str, INIT.symbolName(), methodDescriptor(Void.TYPE, clsArr));
    }

    public static Call constructorNoLookup(Class cls, Class[] clsArr) {
        return specialCallNoLookup(cls, INIT.symbolName(), Void.TYPE, clsArr);
    }

    public static Call specialCallNoLookup(String str, String str2, String str3) {
        return new Call(null, str, str2, str3, str3) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.1
            final String val$desc;

            {
                this.val$desc = str3;
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokespecial(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(Typography.middleDot, this.className, this.name, this.val$desc, false);
            }
        };
    }

    public static Call specialCallNoLookup(Class cls, String str, Class cls2, Class[] clsArr) {
        return specialCallNoLookup(className(cls), str, methodDescriptor(cls2, clsArr));
    }

    public static Call staticCallNoLookup(String str, String str2, String str3) {
        return new Call(null, str, str2, str3, str3) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.2
            final String val$desc;

            {
                this.val$desc = str3;
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokestatic(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(SyslogAppender.LOG_LOCAL7, this.className, this.name, this.val$desc, false);
            }
        };
    }

    public static Call staticCallNoLookup(Class cls, String str, Class cls2, Class[] clsArr) {
        return staticCallNoLookup(className(cls), str, methodDescriptor(cls2, clsArr));
    }

    public static Call virtualCallNoLookup(Class cls, String str, Class cls2, Class[] clsArr) {
        return new Call(null, className(cls), str, methodDescriptor(cls2, clsArr)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.3
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokevirtual(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(Typography.paragraph, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static Call interfaceCallNoLookup(Class cls, String str, Class cls2, Class[] clsArr) {
        return new Call(null, className(cls), str, methodDescriptor(cls2, clsArr)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.4
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokeinterface(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(185, this.className, this.name, this.descriptor, true);
            }
        };
    }

    public static FieldAccess virtualField(String str, String str2, String str3) {
        return new FieldAccess(str, str2, str3) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.5
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public MethodEmitter get(MethodEmitter methodEmitter) {
                return methodEmitter.getField(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public void put(MethodEmitter methodEmitter) {
                methodEmitter.putField(this.className, this.name, this.descriptor);
            }
        };
    }

    public static FieldAccess virtualField(Class cls, String str, Class cls2) {
        return virtualField(className(cls), str, typeDescriptor(cls2));
    }

    public static FieldAccess staticField(String str, String str2, String str3) {
        return new FieldAccess(str, str2, str3) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.6
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public MethodEmitter get(MethodEmitter methodEmitter) {
                return methodEmitter.getStatic(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public void put(MethodEmitter methodEmitter) {
                methodEmitter.putStatic(this.className, this.name, this.descriptor);
            }
        };
    }

    public static FieldAccess staticField(Class cls, String str, Class cls2) {
        return staticField(className(cls), str, typeDescriptor(cls2));
    }

    public static Call staticCall(MethodHandles.Lookup lookup, Class cls, String str, Class cls2, Class[] clsArr) {
        return new Call(Lookup.f31MH.findStatic(lookup, cls, str, Lookup.f31MH.type(cls2, clsArr)), className(cls), str, methodDescriptor(cls2, clsArr)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.7
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokestatic(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(SyslogAppender.LOG_LOCAL7, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static Call virtualCall(MethodHandles.Lookup lookup, Class cls, String str, Class cls2, Class[] clsArr) {
        return new Call(Lookup.f31MH.findVirtual(lookup, cls, str, Lookup.f31MH.type(cls2, clsArr)), className(cls), str, methodDescriptor(cls2, clsArr)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.8
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokevirtual(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(Typography.paragraph, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static Call specialCall(MethodHandles.Lookup lookup, Class cls, String str, Class cls2, Class[] clsArr) {
        return new Call(Lookup.f31MH.findSpecial(lookup, cls, str, Lookup.f31MH.type(cls2, clsArr), cls), className(cls), str, methodDescriptor(cls2, clsArr)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.9
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            MethodEmitter invoke(MethodEmitter methodEmitter) {
                return methodEmitter.invokespecial(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor methodVisitor) {
                methodVisitor.visitMethodInsn(Typography.middleDot, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static boolean isInternalMethodName(String str) {
        return str.startsWith(":") && !str.equals(PROGRAM.symbolName);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilerConstants$Access.class */
    private static abstract class Access {
        protected final MethodHandle methodHandle;
        protected final String className;
        protected final String name;
        protected final String descriptor;

        protected Access(MethodHandle methodHandle, String str, String str2, String str3) {
            this.methodHandle = methodHandle;
            this.className = str;
            this.name = str2;
            this.descriptor = str3;
        }

        public MethodHandle methodHandle() {
            return this.methodHandle;
        }

        public String className() {
            return this.className;
        }

        public String name() {
            return this.name;
        }

        public String descriptor() {
            return this.descriptor;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilerConstants$FieldAccess.class */
    public static abstract class FieldAccess extends Access {
        protected abstract MethodEmitter get(MethodEmitter methodEmitter);

        protected abstract void put(MethodEmitter methodEmitter);

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public String descriptor() {
            return super.descriptor();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public String name() {
            return super.name();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public String className() {
            return super.className();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public MethodHandle methodHandle() {
            return super.methodHandle();
        }

        protected FieldAccess(String str, String str2, String str3) {
            super(null, str, str2, str3);
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilerConstants$Call.class */
    public static abstract class Call extends Access {
        abstract MethodEmitter invoke(MethodEmitter methodEmitter);

        public abstract void invoke(MethodVisitor methodVisitor);

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public String descriptor() {
            return super.descriptor();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public String name() {
            return super.name();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public String className() {
            return super.className();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public MethodHandle methodHandle() {
            return super.methodHandle();
        }

        protected Call(String str, String str2, String str3) {
            super(null, str, str2, str3);
        }

        protected Call(MethodHandle methodHandle, String str, String str2, String str3) {
            super(methodHandle, str, str2, str3);
        }
    }
}

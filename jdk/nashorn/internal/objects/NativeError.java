package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeError.class */
public final class NativeError extends ScriptObject {
    static final MethodHandle GET_COLUMNNUMBER = findOwnMH("getColumnNumber", Object.class, new Class[]{Object.class});
    static final MethodHandle SET_COLUMNNUMBER = findOwnMH("setColumnNumber", Object.class, new Class[]{Object.class, Object.class});
    static final MethodHandle GET_LINENUMBER = findOwnMH("getLineNumber", Object.class, new Class[]{Object.class});
    static final MethodHandle SET_LINENUMBER = findOwnMH("setLineNumber", Object.class, new Class[]{Object.class, Object.class});
    static final MethodHandle GET_FILENAME = findOwnMH("getFileName", Object.class, new Class[]{Object.class});
    static final MethodHandle SET_FILENAME = findOwnMH("setFileName", Object.class, new Class[]{Object.class, Object.class});
    static final MethodHandle GET_STACK = findOwnMH("getStack", Object.class, new Class[]{Object.class});
    static final MethodHandle SET_STACK = findOwnMH("setStack", Object.class, new Class[]{Object.class, Object.class});
    static final String MESSAGE = "message";
    static final String NAME = "name";
    static final String STACK = "__stack__";
    static final String LINENUMBER = "__lineNumber__";
    static final String COLUMNNUMBER = "__columnNumber__";
    static final String FILENAME = "__fileName__";
    public Object instMessage;
    public Object nashornException;
    private static PropertyMap $nasgenmap$;

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeError$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object captureStackTrace;
        private Object dumpStack;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$captureStackTrace() {
            return this.captureStackTrace;
        }

        public void S$captureStackTrace(Object obj) {
            this.captureStackTrace = obj;
        }

        public Object G$dumpStack() {
            return this.dumpStack;
        }

        public void S$dumpStack(Object obj) {
            this.dumpStack = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x000E: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0018: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Constructor() {
            /*
                r6 = this;
                r0 = r6
                java.lang.String r1 = "Error"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r8 = r1
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                java.lang.String r-2 = "captureStackTrace"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-4[r-3] = r-2
                r-6.captureStackTrace = r-5
                r-6 = r6
                java.lang.String r-5 = "dumpStack"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-7[r-6] = r-5
                r-9.dumpStack = r-8
                r-9 = r6
                jdk.nashorn.internal.objects.NativeError$Prototype r-8 = new jdk.nashorn.internal.objects.NativeError$Prototype
                r-7 = r-8
                r-7.<init>()
                r-7 = r-8
                r-6 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-7, r-6)
                r-9.setPrototype(r-8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeError.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeError$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object name;
        private Object message;
        private Object printStackTrace;
        private Object getStackTrace;
        private Object toString;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$name() {
            return this.name;
        }

        public void S$name(Object obj) {
            this.name = obj;
        }

        public Object G$message() {
            return this.message;
        }

        public void S$message(Object obj) {
            this.message = obj;
        }

        public Object G$printStackTrace() {
            return this.printStackTrace;
        }

        public void S$printStackTrace(Object obj) {
            this.printStackTrace = obj;
        }

        public Object G$getStackTrace() {
            return this.getStackTrace;
        }

        public void S$getStackTrace(Object obj) {
            this.getStackTrace = obj;
        }

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x001E: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Prototype() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeError.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "printStackTrace"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 % r1
                r-1.printStackTrace = r0
                r-1 = r4
                java.lang.String r0 = "getStackTrace"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 % r0
                r-2.getStackTrace = r-1
                r-2 = r4
                java.lang.String r-1 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 % r-1
                r-3.toString = r-2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeError.Prototype.<init>():void");
        }
    }

    /*  JADX ERROR: Failed to decode insn: 0x000C: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    /*  JADX ERROR: Failed to decode insn: 0x001E: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    public static void $clinit$() {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = 2
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "message"
            r3 = 2
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r4 = r7
            jdk.nashorn.internal.runtime.AccessorProperty r1 = jdk.nashorn.internal.runtime.AccessorProperty.create(r1, r2, r3, r4)
            boolean r0 = r0.add(r1)
            r0 = r-1
            java.lang.String r1 = "nashornException"
            r2 = 2
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r1 = r1[r2]
            jdk.nashorn.internal.runtime.AccessorProperty r-2 = jdk.nashorn.internal.runtime.AccessorProperty.create(r-2, r-1, r0, r1)
            boolean r-3 = r-3.add(r-2)
            jdk.nashorn.internal.runtime.PropertyMap r-4 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r-4)
            jdk.nashorn.internal.objects.NativeError.$nasgenmap$ = r-4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeError.$clinit$():void");
    }

    public Object G$instMessage() {
        return this.instMessage;
    }

    public void S$instMessage(Object obj) {
        this.instMessage = obj;
    }

    public Object G$nashornException() {
        return this.nashornException;
    }

    public void S$nashornException(Object obj) {
        this.nashornException = obj;
    }

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.NativeError.$clinit$():void */
    static {
        $clinit$();
    }

    private NativeError(Object obj, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        if (obj != ScriptRuntime.UNDEFINED) {
            this.instMessage = JSType.toString(obj);
        } else {
            delete(MESSAGE, false);
        }
        initException(this);
    }

    NativeError(Object obj, Global global) {
        this(obj, global.getErrorPrototype(), $nasgenmap$);
    }

    private NativeError(Object obj) {
        this(obj, Global.instance());
    }

    public static NativeError constructor(boolean z, Object obj, Object obj2) {
        return new NativeError(obj2);
    }

    static void initException(ScriptObject scriptObject) {
        new ECMAException(scriptObject, null);
    }

    public static Object captureStackTrace(Object obj, Object obj2) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj2);
        initException(scriptObjectCheckObject);
        scriptObjectCheckObject.delete((Object) STACK, false);
        if (!scriptObjectCheckObject.has("stack")) {
            scriptObjectCheckObject.addOwnProperty("stack", 2, ScriptFunction.createBuiltin("getStack", GET_STACK), ScriptFunction.createBuiltin("setStack", SET_STACK));
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object dumpStack(Object obj) {
        Thread.dumpStack();
        return ScriptRuntime.UNDEFINED;
    }

    public static Object printStackTrace(Object obj) {
        return ECMAException.printStackTrace(Global.checkObject(obj));
    }

    public static Object getStackTrace(Object obj) {
        Object[] scriptFrames;
        Object exception = ECMAException.getException(Global.checkObject(obj));
        if (exception instanceof Throwable) {
            scriptFrames = NashornException.getScriptFrames((Throwable) exception);
        } else {
            scriptFrames = ScriptRuntime.EMPTY_ARRAY;
        }
        return new NativeArray(scriptFrames);
    }

    public static Object getLineNumber(Object obj) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        return scriptObjectCheckObject.has(LINENUMBER) ? scriptObjectCheckObject.get(LINENUMBER) : ECMAException.getLineNumber(scriptObjectCheckObject);
    }

    public static Object setLineNumber(Object obj, Object obj2) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        if (scriptObjectCheckObject.hasOwnProperty(LINENUMBER)) {
            scriptObjectCheckObject.put(LINENUMBER, obj2, false);
        } else {
            scriptObjectCheckObject.addOwnProperty(LINENUMBER, 2, obj2);
        }
        return obj2;
    }

    public static Object getColumnNumber(Object obj) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        return scriptObjectCheckObject.has(COLUMNNUMBER) ? scriptObjectCheckObject.get(COLUMNNUMBER) : ECMAException.getColumnNumber((ScriptObject) obj);
    }

    public static Object setColumnNumber(Object obj, Object obj2) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        if (scriptObjectCheckObject.hasOwnProperty(COLUMNNUMBER)) {
            scriptObjectCheckObject.put(COLUMNNUMBER, obj2, false);
        } else {
            scriptObjectCheckObject.addOwnProperty(COLUMNNUMBER, 2, obj2);
        }
        return obj2;
    }

    public static Object getFileName(Object obj) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        return scriptObjectCheckObject.has(FILENAME) ? scriptObjectCheckObject.get(FILENAME) : ECMAException.getFileName((ScriptObject) obj);
    }

    public static Object setFileName(Object obj, Object obj2) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        if (scriptObjectCheckObject.hasOwnProperty(FILENAME)) {
            scriptObjectCheckObject.put(FILENAME, obj2, false);
        } else {
            scriptObjectCheckObject.addOwnProperty(FILENAME, 2, obj2);
        }
        return obj2;
    }

    public static Object getStack(Object obj) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        if (scriptObjectCheckObject.has(STACK)) {
            return scriptObjectCheckObject.get(STACK);
        }
        Object exception = ECMAException.getException(scriptObjectCheckObject);
        if (exception instanceof Throwable) {
            String scriptStackString = getScriptStackString(scriptObjectCheckObject, (Throwable) exception);
            if (scriptObjectCheckObject.hasOwnProperty(STACK)) {
                scriptObjectCheckObject.put(STACK, scriptStackString, false);
            } else {
                scriptObjectCheckObject.addOwnProperty(STACK, 2, scriptStackString);
            }
            return scriptStackString;
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object setStack(Object obj, Object obj2) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        if (scriptObjectCheckObject.hasOwnProperty(STACK)) {
            scriptObjectCheckObject.put(STACK, obj2, false);
        } else {
            scriptObjectCheckObject.addOwnProperty(STACK, 2, obj2);
        }
        return obj2;
    }

    public static Object toString(Object obj) {
        String string;
        String string2;
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj);
        Object obj2 = scriptObjectCheckObject.get(NAME);
        if (obj2 == ScriptRuntime.UNDEFINED) {
            string = "Error";
        } else {
            string = JSType.toString(obj2);
        }
        Object obj3 = scriptObjectCheckObject.get(MESSAGE);
        if (obj3 == ScriptRuntime.UNDEFINED) {
            string2 = "";
        } else {
            string2 = JSType.toString(obj3);
        }
        if (string.isEmpty()) {
            return string2;
        }
        if (string2.isEmpty()) {
            return string;
        }
        return ((Object) string) + ": " + ((Object) string2);
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeError.class, str, Lookup.f31MH.type(cls, clsArr));
    }

    private static String getScriptStackString(ScriptObject scriptObject, Throwable th) {
        return JSType.toString(scriptObject) + "\n" + NashornException.getScriptStackString(th);
    }
}

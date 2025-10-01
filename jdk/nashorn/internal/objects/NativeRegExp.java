package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeRegExp.class */
public final class NativeRegExp extends ScriptObject {
    public Object lastIndex;
    private RegExp regexp;
    private final Global globalObject;
    private static PropertyMap $nasgenmap$;
    private static final Object REPLACE_VALUE;
    static final boolean $assertionsDisabled;

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeRegExp$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

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
        /*  JADX ERROR: Failed to decode insn: 0x0011: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x001D: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x0029: CONST
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
                r12 = this;
                r0 = r12
                java.lang.String r1 = "RegExp"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                long r0 = r0 & r1
                r1 = 3
                jdk.nashorn.internal.runtime.Specialization[] r1 = new jdk.nashorn.internal.runtime.Specialization[r1]
                r2 = r1
                r3 = 0
                jdk.nashorn.internal.runtime.Specialization r4 = new jdk.nashorn.internal.runtime.Specialization
                r5 = r4
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r3.<init>(r4, r5)
                r0[r1] = r2
                r0 = r-1
                r1 = 1
                jdk.nashorn.internal.runtime.Specialization r2 = new jdk.nashorn.internal.runtime.Specialization
                r3 = r2
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r1.<init>(r2, r3)
                r-2[r-1] = r0
                r-2 = r-3
                r-1 = 2
                jdk.nashorn.internal.runtime.Specialization r0 = new jdk.nashorn.internal.runtime.Specialization
                r1 = r0
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-1.<init>(r0, r1)
                r-4[r-3] = r-2
                r-9.<init>(r-8, r-7, r-6, r-5)
                r-9 = r12
                jdk.nashorn.internal.objects.NativeRegExp$Prototype r-8 = new jdk.nashorn.internal.objects.NativeRegExp$Prototype
                r-7 = r-8
                r-7.<init>()
                r-7 = r-8
                r-6 = r12
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-7, r-6)
                r-9.setPrototype(r-8)
                r-9 = r12
                r-8 = 2
                r-9.setArity(r-8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRegExp.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeRegExp$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object compile;
        private Object exec;
        private Object test;
        private Object toString;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$compile() {
            return this.compile;
        }

        public void S$compile(Object obj) {
            this.compile = obj;
        }

        public Object G$exec() {
            return this.exec;
        }

        public void S$exec(Object obj) {
            this.exec = obj;
        }

        public Object G$test() {
            return this.test;
        }

        public void S$test(Object obj) {
            this.test = obj;
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
        /*  JADX ERROR: Failed to decode insn: 0x0028: CONST
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
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeRegExp.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "compile"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 - r1
                r-1.compile = r0
                r-1 = r4
                java.lang.String r0 = "exec"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 - r0
                r-2.exec = r-1
                r-2 = r4
                java.lang.String r-1 = "test"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 - r-1
                r-3.test = r-2
                r-3 = r4
                java.lang.String r-2 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = r-3 - r-2
                r-4.toString = r-3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRegExp.Prototype.<init>():void");
        }
    }

    /*  JADX ERROR: Failed to decode insn: 0x000E: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x0021: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x0032: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x0043: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x0054: CONST
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
            r2 = 5
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "lastIndex"
            r3 = 6
            // decode failed: Unsupported constant type: METHOD_HANDLE
            ret r191
            r4 = 0
            return r4
            boolean r2 = r2.add(r3)
            r2 = r1
            java.lang.String r3 = "source"
            r4 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r5 = 0
            return r5
            boolean r3 = r3.add(r4)
            r3 = r2
            java.lang.String r4 = "global"
            r5 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r6 = 0
            return r6
            boolean r4 = r4.add(r5)
            r4 = r3
            java.lang.String r5 = "ignoreCase"
            r6 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r7 = 0
            return r7
            boolean r5 = r5.add(r6)
            r5 = r4
            java.lang.String r6 = "multiline"
            r7 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r8 = 0
            return r8
            boolean r6 = r6.add(r7)
            jdk.nashorn.internal.runtime.PropertyMap r5 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r5)
            jdk.nashorn.internal.objects.NativeRegExp.$nasgenmap$ = r5
            return
            r5 = 0
            r6 = 0
            if (r5 < r6) goto Lbb
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRegExp.$clinit$():void");
    }

    public Object G$lastIndex() {
        return this.lastIndex;
    }

    public void S$lastIndex(Object obj) {
        this.lastIndex = obj;
    }

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.NativeRegExp.$clinit$():void */
    static {
        $assertionsDisabled = !NativeRegExp.class.desiredAssertionStatus();
        REPLACE_VALUE = new Object();
        $clinit$();
    }

    private NativeRegExp(Global global) {
        super(global.getRegExpPrototype(), $nasgenmap$);
        this.globalObject = global;
    }

    NativeRegExp(String str, String str2, Global global, ScriptObject scriptObject) {
        super(scriptObject, $nasgenmap$);
        try {
            this.regexp = RegExpFactory.create(str, str2);
            this.globalObject = global;
            setLastIndex(0);
        } catch (ParserException e) {
            e.throwAsEcmaException();
            throw new AssertionError();
        }
    }

    NativeRegExp(String str, String str2, Global global) {
        this(str, str2, global, global.getRegExpPrototype());
    }

    NativeRegExp(String str, String str2) {
        this(str, str2, Global.instance());
    }

    NativeRegExp(String str, Global global) {
        this(str, "", global);
    }

    NativeRegExp(String str) {
        this(str, Global.instance());
    }

    NativeRegExp(NativeRegExp nativeRegExp) {
        this(Global.instance());
        this.lastIndex = nativeRegExp.getLastIndexObject();
        this.regexp = nativeRegExp.getRegExp();
    }

    public static NativeRegExp constructor(boolean z, Object obj, Object[] objArr) {
        if (objArr.length > 1) {
            return newRegExp(objArr[0], objArr[1]);
        }
        if (objArr.length > 0) {
            return newRegExp(objArr[0], ScriptRuntime.UNDEFINED);
        }
        return newRegExp(ScriptRuntime.UNDEFINED, ScriptRuntime.UNDEFINED);
    }

    public static NativeRegExp constructor(boolean z, Object obj) {
        return new NativeRegExp("", "");
    }

    public static NativeRegExp constructor(boolean z, Object obj, Object obj2) {
        return newRegExp(obj2, ScriptRuntime.UNDEFINED);
    }

    public static NativeRegExp constructor(boolean z, Object obj, Object obj2, Object obj3) {
        return newRegExp(obj2, obj3);
    }

    public static NativeRegExp newRegExp(Object obj, Object obj2) {
        String string = "";
        String string2 = "";
        if (obj != ScriptRuntime.UNDEFINED) {
            if (obj instanceof NativeRegExp) {
                if (obj2 != ScriptRuntime.UNDEFINED) {
                    throw ECMAErrors.typeError("regex.cant.supply.flags", new String[0]);
                }
                return (NativeRegExp) obj;
            }
            string = JSType.toString(obj);
        }
        if (obj2 != ScriptRuntime.UNDEFINED) {
            string2 = JSType.toString(obj2);
        }
        return new NativeRegExp(string, string2);
    }

    static NativeRegExp flatRegExp(String str) {
        StringBuilder sb = null;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            switch (cCharAt) {
                case '$':
                case '(':
                case OPCode.BACKREF1 /* 41 */:
                case OPCode.BACKREF2 /* 42 */:
                case OPCode.BACKREFN /* 43 */:
                case OPCode.BACKREF_MULTI_IC /* 46 */:
                case OPCode.REPEAT_INC_NG /* 63 */:
                case '[':
                case '\\':
                case '^':
                case '{':
                case '|':
                    if (sb == null) {
                        sb = new StringBuilder(length * 2);
                        sb.append((CharSequence) str, 0, i);
                    }
                    sb.append('\\');
                    sb.append(cCharAt);
                    break;
                default:
                    if (sb != null) {
                        sb.append(cCharAt);
                        break;
                    } else {
                        break;
                    }
            }
        }
        return new NativeRegExp(sb == null ? str : sb.toString(), "");
    }

    private String getFlagString() {
        StringBuilder sb = new StringBuilder(3);
        if (this.regexp.isGlobal()) {
            sb.append('g');
        }
        if (this.regexp.isIgnoreCase()) {
            sb.append('i');
        }
        if (this.regexp.isMultiline()) {
            sb.append('m');
        }
        return sb.toString();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[RegExp " + toString() + "]";
    }

    public String toString() {
        return "/" + this.regexp.getSource() + "/" + getFlagString();
    }

    public static ScriptObject compile(Object obj, Object obj2, Object obj3) {
        NativeRegExp nativeRegExpCheckRegExp = checkRegExp(obj);
        nativeRegExpCheckRegExp.setRegExp(newRegExp(obj2, obj3).getRegExp());
        return nativeRegExpCheckRegExp;
    }

    public static ScriptObject exec(Object obj, Object obj2) {
        return checkRegExp(obj).exec(JSType.toString(obj2));
    }

    public static boolean test(Object obj, Object obj2) {
        return checkRegExp(obj).test(JSType.toString(obj2));
    }

    public static String toString(Object obj) {
        return checkRegExp(obj).toString();
    }

    public static Object source(Object obj) {
        return checkRegExp(obj).getRegExp().getSource();
    }

    public static Object global(Object obj) {
        return Boolean.valueOf(checkRegExp(obj).getRegExp().isGlobal());
    }

    public static Object ignoreCase(Object obj) {
        return Boolean.valueOf(checkRegExp(obj).getRegExp().isIgnoreCase());
    }

    public static Object multiline(Object obj) {
        return Boolean.valueOf(checkRegExp(obj).getRegExp().isMultiline());
    }

    public static Object getLastInput(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getInput();
    }

    public static Object getLastMultiline(Object obj) {
        return false;
    }

    public static Object getLastMatch(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(0);
    }

    public static Object getLastParen(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getLastParen();
    }

    public static Object getLeftContext(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getInput().substring(0, lastRegExpResult.getIndex());
    }

    public static Object getRightContext(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getInput().substring(lastRegExpResult.getIndex() + lastRegExpResult.length());
    }

    public static Object getGroup1(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(1);
    }

    public static Object getGroup2(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(2);
    }

    public static Object getGroup3(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(3);
    }

    public static Object getGroup4(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(4);
    }

    public static Object getGroup5(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(5);
    }

    public static Object getGroup6(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(6);
    }

    public static Object getGroup7(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(7);
    }

    public static Object getGroup8(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(8);
    }

    public static Object getGroup9(Object obj) {
        RegExpResult lastRegExpResult = Global.instance().getLastRegExpResult();
        return lastRegExpResult == null ? "" : lastRegExpResult.getGroup(9);
    }

    private RegExpResult execInner(String str) {
        boolean zIsGlobal = this.regexp.isGlobal();
        int lastIndex = getLastIndex();
        if (!zIsGlobal) {
            lastIndex = 0;
        }
        if (lastIndex < 0 || lastIndex > str.length()) {
            if (zIsGlobal) {
                setLastIndex(0);
                return null;
            }
            return null;
        }
        RegExpMatcher regExpMatcherMatch = this.regexp.match(str);
        if (regExpMatcherMatch == null || !regExpMatcherMatch.search(lastIndex)) {
            if (zIsGlobal) {
                setLastIndex(0);
                return null;
            }
            return null;
        }
        if (zIsGlobal) {
            setLastIndex(regExpMatcherMatch.end());
        }
        RegExpResult regExpResult = new RegExpResult(str, regExpMatcherMatch.start(), groups(regExpMatcherMatch));
        this.globalObject.setLastRegExpResult(regExpResult);
        return regExpResult;
    }

    private RegExpResult execSplit(String str, int i) {
        RegExpMatcher regExpMatcherMatch;
        if (i < 0 || i > str.length() || (regExpMatcherMatch = this.regexp.match(str)) == null || !regExpMatcherMatch.search(i)) {
            return null;
        }
        RegExpResult regExpResult = new RegExpResult(str, regExpMatcherMatch.start(), groups(regExpMatcherMatch));
        this.globalObject.setLastRegExpResult(regExpResult);
        return regExpResult;
    }

    private Object[] groups(RegExpMatcher regExpMatcher) {
        int iGroupCount = regExpMatcher.groupCount();
        Object[] objArr = new Object[iGroupCount + 1];
        BitVector groupsInNegativeLookahead = this.regexp.getGroupsInNegativeLookahead();
        int iStart = regExpMatcher.start();
        for (int i = 0; i <= iGroupCount; i++) {
            int iStart2 = regExpMatcher.start(i);
            if (iStart > iStart2 || (groupsInNegativeLookahead != null && groupsInNegativeLookahead.isSet(i))) {
                objArr[i] = ScriptRuntime.UNDEFINED;
            } else {
                String strGroup = regExpMatcher.group(i);
                objArr[i] = strGroup == null ? ScriptRuntime.UNDEFINED : strGroup;
                iStart = iStart2;
            }
        }
        return objArr;
    }

    public NativeRegExpExecResult exec(String str) {
        RegExpResult regExpResultExecInner = execInner(str);
        if (regExpResultExecInner == null) {
            return null;
        }
        return new NativeRegExpExecResult(regExpResultExecInner, this.globalObject);
    }

    public boolean test(String str) {
        return execInner(str) != null;
    }

    String replace(String str, String str2, Object obj) {
        RegExpMatcher regExpMatcherMatch = this.regexp.match(str);
        if (regExpMatcherMatch == null) {
            return str;
        }
        if (!this.regexp.isGlobal()) {
            if (!regExpMatcherMatch.search(0)) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            sb.append((CharSequence) str, 0, regExpMatcherMatch.start());
            if (obj != null) {
                sb.append(callReplaceValue(getReplaceValueInvoker(), obj, Bootstrap.isStrictCallable(obj) ? ScriptRuntime.UNDEFINED : Global.instance(), regExpMatcherMatch, str));
            } else {
                appendReplacement(regExpMatcherMatch, str, str2, sb);
            }
            sb.append((CharSequence) str, regExpMatcherMatch.end(), str.length());
            return sb.toString();
        }
        setLastIndex(0);
        if (!regExpMatcherMatch.search(0)) {
            return str;
        }
        int iEnd = 0;
        int i = 0;
        StringBuilder sb2 = new StringBuilder();
        MethodHandle replaceValueInvoker = obj == null ? null : getReplaceValueInvoker();
        Object objInstance = (obj == null || Bootstrap.isStrictCallable(obj)) ? ScriptRuntime.UNDEFINED : Global.instance();
        do {
            sb2.append((CharSequence) str, iEnd, regExpMatcherMatch.start());
            if (obj != null) {
                sb2.append(callReplaceValue(replaceValueInvoker, obj, objInstance, regExpMatcherMatch, str));
            } else {
                appendReplacement(regExpMatcherMatch, str, str2, sb2);
            }
            iEnd = regExpMatcherMatch.end();
            if (iEnd != str.length() || regExpMatcherMatch.start() != regExpMatcherMatch.end()) {
                if (iEnd == i) {
                    setLastIndex(iEnd + 1);
                    i = iEnd + 1;
                } else {
                    i = iEnd;
                }
                if (i > str.length()) {
                    break;
                }
            } else {
                break;
            }
        } while (regExpMatcherMatch.search(i));
        sb2.append((CharSequence) str, iEnd, str.length());
        return sb2.toString();
    }

    private void appendReplacement(RegExpMatcher regExpMatcher, String str, String str2, StringBuilder sb) {
        int iCharAt;
        int i;
        int i2 = 0;
        Object[] objArrGroups = null;
        while (i2 < str2.length()) {
            char cCharAt = str2.charAt(i2);
            if (cCharAt == '$') {
                i2++;
                if (i2 == str2.length()) {
                    sb.append('$');
                    return;
                }
                char cCharAt2 = str2.charAt(i2);
                int i3 = cCharAt2 - '0';
                if (i3 >= 0 && i3 <= 9 && i3 <= regExpMatcher.groupCount()) {
                    int i4 = i3;
                    i2++;
                    if (i2 < str2.length() && i3 < regExpMatcher.groupCount() && (iCharAt = str2.charAt(i2) - '0') >= 0 && iCharAt <= 9 && (i = (i3 * 10) + iCharAt) <= regExpMatcher.groupCount() && i > 0) {
                        i4 = i;
                        i2++;
                    }
                    if (i4 > 0) {
                        if (objArrGroups == null) {
                            objArrGroups = groups(regExpMatcher);
                        }
                        if (objArrGroups[i4] != ScriptRuntime.UNDEFINED) {
                            sb.append((String) objArrGroups[i4]);
                        }
                    } else {
                        if (!$assertionsDisabled && i4 != 0) {
                            throw new AssertionError();
                        }
                        sb.append("$0");
                    }
                } else if (cCharAt2 == '$') {
                    sb.append('$');
                    i2++;
                } else if (cCharAt2 == '&') {
                    sb.append(regExpMatcher.group());
                    i2++;
                } else if (cCharAt2 == '`') {
                    sb.append((CharSequence) str, 0, regExpMatcher.start());
                    i2++;
                } else if (cCharAt2 == '\'') {
                    sb.append((CharSequence) str, regExpMatcher.end(), str.length());
                    i2++;
                } else {
                    sb.append('$');
                }
            } else {
                sb.append(cCharAt);
                i2++;
            }
        }
    }

    private static final MethodHandle getReplaceValueInvoker() {
        return Global.instance().getDynamicInvoker(REPLACE_VALUE, new Callable() { // from class: jdk.nashorn.internal.objects.NativeRegExp.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", String.class, new Class[]{Object.class, Object.class, Object[].class});
            }
        });
    }

    private String callReplaceValue(MethodHandle methodHandle, Object obj, Object obj2, RegExpMatcher regExpMatcher, String str) {
        Object[] objArrGroups = groups(regExpMatcher);
        Object[] objArrCopyOf = Arrays.copyOf(objArrGroups, objArrGroups.length + 2);
        objArrCopyOf[objArrGroups.length] = Integer.valueOf(regExpMatcher.start());
        objArrCopyOf[objArrGroups.length + 1] = str;
        return (String) methodHandle.invokeExact(obj, obj2, objArrCopyOf);
    }

    NativeArray split(String str, long j) {
        if (j == 0) {
            return new NativeArray();
        }
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int length2 = -1;
        int index = 0;
        int i = 0;
        while (true) {
            RegExpResult regExpResultExecSplit = execSplit(str, index);
            if (regExpResultExecSplit == null) {
                break;
            }
            index = regExpResultExecSplit.getIndex() + regExpResultExecSplit.length();
            if (index > i) {
                arrayList.add(str.substring(i, regExpResultExecSplit.getIndex()));
                Object[] groups = regExpResultExecSplit.getGroups();
                if (groups.length > 1 && regExpResultExecSplit.getIndex() < length) {
                    for (int i2 = 1; i2 < groups.length && arrayList.size() < j; i2++) {
                        arrayList.add(groups[i2]);
                    }
                }
                length2 = regExpResultExecSplit.length();
                if (arrayList.size() >= j) {
                    break;
                }
            }
            if (index == i) {
                index++;
            } else {
                i = index;
            }
        }
        if (arrayList.size() < j) {
            if (i == str.length()) {
                if (length2 > 0 || execSplit("", 0) == null) {
                    arrayList.add("");
                }
            } else {
                arrayList.add(str.substring(i, length));
            }
        }
        return new NativeArray(arrayList.toArray());
    }

    int search(String str) {
        RegExpResult regExpResultExecInner = execInner(str);
        if (regExpResultExecInner == null) {
            return -1;
        }
        return regExpResultExecInner.getIndex();
    }

    public int getLastIndex() {
        return JSType.toInteger(this.lastIndex);
    }

    public Object getLastIndexObject() {
        return this.lastIndex;
    }

    public void setLastIndex(int i) {
        this.lastIndex = JSType.toObject(i);
    }

    private static NativeRegExp checkRegExp(Object obj) {
        if (obj instanceof NativeRegExp) {
            return (NativeRegExp) obj;
        }
        if (obj != null && obj == Global.instance().getRegExpPrototype()) {
            return Global.instance().getDefaultRegExp();
        }
        throw ECMAErrors.typeError("not.a.regexp", new String[]{ScriptRuntime.safeToString(obj)});
    }

    boolean getGlobal() {
        return this.regexp.isGlobal();
    }

    private RegExp getRegExp() {
        return this.regexp;
    }

    private void setRegExp(RegExp regExp) {
        this.regexp = regExp;
    }
}

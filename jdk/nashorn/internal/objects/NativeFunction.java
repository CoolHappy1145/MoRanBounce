package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFunction.class */
public final class NativeFunction {
    public static final MethodHandle TO_APPLY_ARGS = Lookup.findOwnStatic(MethodHandles.lookup(), "toApplyArgs", Object[].class, new Class[]{Object.class});
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFunction$Constructor.class */
    final class Constructor extends ScriptFunction {
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
        Constructor() {
            /*
                r5 = this;
                r0 = r5
                java.lang.String r1 = "Function"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeFunction$Prototype r-1 = new jdk.nashorn.internal.objects.NativeFunction$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                r-2 = r5
                r-1 = 1
                r-2.setArity(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeFunction.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFunction$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object apply;
        private Object call;
        private Object bind;
        private Object toSource;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$apply() {
            return this.apply;
        }

        public void S$apply(Object obj) {
            this.apply = obj;
        }

        public Object G$call() {
            return this.call;
        }

        public void S$call(Object obj) {
            this.call = obj;
        }

        public Object G$bind() {
            return this.bind;
        }

        public void S$bind(Object obj) {
            this.bind = obj;
        }

        public Object G$toSource() {
            return this.toSource;
        }

        public void S$toSource(Object obj) {
            this.toSource = obj;
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
        /*  JADX ERROR: Failed to decode insn: 0x002D: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x003C: CONST
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
                r5 = this;
                r0 = r5
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeFunction.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r5
                java.lang.String r1 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 % r1
                r-1.toString = r0
                r-1 = r5
                java.lang.String r0 = "apply"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 % r0
                r-2.apply = r-1
                r-2 = r5
                java.lang.String r-1 = "call"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 % r-1
                r-1 = r-2
                r0 = 1
                r-1.setArity(r0)
                r-3.call = r-2
                r-3 = r5
                java.lang.String r-2 = "bind"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = r-3 % r-2
                r-2 = r-3
                r-1 = 1
                r-2.setArity(r-1)
                r-4.bind = r-3
                r-4 = r5
                java.lang.String r-3 = "toSource"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-4 = r-4 % r-3
                r-5.toSource = r-4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void");
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $clinit$();
    }

    private NativeFunction() {
        throw new UnsupportedOperationException();
    }

    public static String toString(Object obj) {
        if (!(obj instanceof ScriptFunction)) {
            throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(obj)});
        }
        return ((ScriptFunction) obj).toSource();
    }

    public static Object apply(Object obj, Object obj2, Object obj3) {
        checkCallable(obj);
        Object[] applyArgs = toApplyArgs(obj3);
        if (obj instanceof ScriptFunction) {
            return ScriptRuntime.apply((ScriptFunction) obj, obj2, applyArgs);
        }
        if (obj instanceof JSObject) {
            return ((JSObject) obj).call(obj2, applyArgs);
        }
        throw new AssertionError("Should not reach here");
    }

    public static Object[] toApplyArgs(Object obj) {
        if (obj instanceof NativeArguments) {
            return ((NativeArguments) obj).getArray().asObjectArray();
        }
        if (obj instanceof ScriptObject) {
            ScriptObject scriptObject = (ScriptObject) obj;
            Object[] objArr = new Object[lengthToInt(scriptObject.getLength())];
            for (int i = 0; i < objArr.length; i++) {
                objArr[i] = scriptObject.get(i);
            }
            return objArr;
        }
        if (obj instanceof Object[]) {
            return (Object[]) obj;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            return list.toArray(new Object[list.size()]);
        }
        if (obj == null || obj == ScriptRuntime.UNDEFINED) {
            return ScriptRuntime.EMPTY_ARRAY;
        }
        if (obj instanceof JSObject) {
            JSObject jSObject = (JSObject) obj;
            Object[] objArr2 = new Object[lengthToInt(jSObject.hasMember("length") ? jSObject.getMember("length") : 0)];
            for (int i2 = 0; i2 < objArr2.length; i2++) {
                objArr2[i2] = jSObject.hasSlot(i2) ? jSObject.getSlot(i2) : ScriptRuntime.UNDEFINED;
            }
            return objArr2;
        }
        throw ECMAErrors.typeError("function.apply.expects.array", new String[0]);
    }

    private static int lengthToInt(Object obj) {
        long uint32 = JSType.toUint32(obj);
        if (uint32 > 2147483647L) {
            throw ECMAErrors.rangeError("range.error.inappropriate.array.length", new String[]{JSType.toString(obj)});
        }
        return (int) uint32;
    }

    private static void checkCallable(Object obj) {
        if (obj instanceof ScriptFunction) {
            return;
        }
        if (!(obj instanceof JSObject) || !((JSObject) obj).isFunction()) {
            throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(obj)});
        }
    }

    public static Object call(Object obj, Object[] objArr) {
        Object[] objArr2;
        checkCallable(obj);
        Object obj2 = objArr.length == 0 ? ScriptRuntime.UNDEFINED : objArr[0];
        if (objArr.length > 1) {
            objArr2 = new Object[objArr.length - 1];
            System.arraycopy(objArr, 1, objArr2, 0, objArr2.length);
        } else {
            objArr2 = ScriptRuntime.EMPTY_ARRAY;
        }
        if (obj instanceof ScriptFunction) {
            return ScriptRuntime.apply((ScriptFunction) obj, obj2, objArr2);
        }
        if (obj instanceof JSObject) {
            return ((JSObject) obj).call(obj2, objArr2);
        }
        throw new AssertionError("should not reach here");
    }

    public static Object bind(Object obj, Object[] objArr) {
        Object[] objArr2;
        Object obj2 = objArr.length == 0 ? ScriptRuntime.UNDEFINED : objArr[0];
        if (objArr.length > 1) {
            objArr2 = new Object[objArr.length - 1];
            System.arraycopy(objArr, 1, objArr2, 0, objArr2.length);
        } else {
            objArr2 = ScriptRuntime.EMPTY_ARRAY;
        }
        return Bootstrap.bindCallable(obj, obj2, objArr2);
    }

    public static String toSource(Object obj) {
        if (!(obj instanceof ScriptFunction)) {
            throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(obj)});
        }
        return ((ScriptFunction) obj).toSource();
    }

    public static ScriptFunction function(boolean z, Object obj, Object[] objArr) {
        String string;
        StringBuilder sb = new StringBuilder();
        sb.append("(function (");
        if (objArr.length > 0) {
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < objArr.length - 1; i++) {
                sb2.append(JSType.toString(objArr[i]));
                if (i < objArr.length - 2) {
                    sb2.append(",");
                }
            }
            string = JSType.toString(objArr[objArr.length - 1]);
            String string2 = sb2.toString();
            if (!string2.isEmpty()) {
                checkFunctionParameters(string2);
                sb.append(string2);
            }
        } else {
            string = null;
        }
        sb.append(") {\n");
        if (objArr.length > 0) {
            checkFunctionBody(string);
            sb.append(string);
            sb.append('\n');
        }
        sb.append("})");
        ScriptObject scriptObjectInstance = Global.instance();
        return (ScriptFunction) scriptObjectInstance.getContext().eval(scriptObjectInstance, sb.toString(), scriptObjectInstance, "<function>");
    }

    private static void checkFunctionParameters(String str) {
        try {
            getParser(str).parseFormalParameterList();
        } catch (ParserException e) {
            e.throwAsEcmaException();
        }
    }

    private static void checkFunctionBody(String str) {
        try {
            getParser(str).parseFunctionBody();
        } catch (ParserException e) {
            e.throwAsEcmaException();
        }
    }

    private static Parser getParser(String str) {
        ScriptEnvironment env = Global.getEnv();
        return new Parser(env, Source.sourceFor("<function>", str), new Context.ThrowErrorManager(), env._strict, null);
    }
}

package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSONFunctions;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJSON.class */
public final class NativeJSON extends ScriptObject {
    private static final Object TO_JSON;
    private static final Object JSOBJECT_INVOKER;
    private static final Object REPLACER_INVOKER;
    private static PropertyMap $nasgenmap$;
    static final boolean $assertionsDisabled;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJSON$Constructor.class */
    final class Constructor extends ScriptObject {
        private Object parse;
        private Object stringify;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$parse() {
            return this.parse;
        }

        public void S$parse(Object obj) {
            this.parse = obj;
        }

        public Object G$stringify() {
            return this.stringify;
        }

        public void S$stringify(Object obj) {
            this.stringify = obj;
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Constructor() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeJSON.Constructor.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "parse"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r1
                r-1.parse = r0
                r-1 = r4
                java.lang.String r0 = "stringify"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r0
                r-2.stringify = r-1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJSON.Constructor.<init>():void");
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $assertionsDisabled = !NativeJSON.class.desiredAssertionStatus();
        TO_JSON = new Object();
        JSOBJECT_INVOKER = new Object();
        REPLACER_INVOKER = new Object();
        $clinit$();
    }

    private static InvokeByName getTO_JSON() {
        return Global.instance().getInvokeByName(TO_JSON, new Callable() { // from class: jdk.nashorn.internal.objects.NativeJSON.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("toJSON", ScriptObject.class, Object.class, new Class[]{Object.class});
            }
        });
    }

    private static MethodHandle getJSOBJECT_INVOKER() {
        return Global.instance().getDynamicInvoker(JSOBJECT_INVOKER, new Callable() { // from class: jdk.nashorn.internal.objects.NativeJSON.2
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Object.class, new Class[]{Object.class, Object.class});
            }
        });
    }

    private static MethodHandle getREPLACER_INVOKER() {
        return Global.instance().getDynamicInvoker(REPLACER_INVOKER, new Callable() { // from class: jdk.nashorn.internal.objects.NativeJSON.3
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Object.class, new Class[]{Object.class, Object.class, Object.class, Object.class});
            }
        });
    }

    private NativeJSON() {
        throw new UnsupportedOperationException();
    }

    public static Object parse(Object obj, Object obj2, Object obj3) {
        return JSONFunctions.parse(obj2, obj3);
    }

    public static Object stringify(Object obj, Object obj2, Object obj3, Object obj4) {
        String strSubstring;
        StringifyState stringifyState = new StringifyState(null);
        if (Bootstrap.isCallable(obj3)) {
            stringifyState.replacerFunction = obj3;
        } else if (isArray(obj3) || isJSObjectArray(obj3) || (obj3 instanceof Iterable) || (obj3 != null && obj3.getClass().isArray())) {
            stringifyState.propertyList = new ArrayList();
            ArrayLikeIterator arrayLikeIterator = ArrayLikeIterator.arrayLikeIterator(obj3);
            while (arrayLikeIterator.hasNext()) {
                String string = null;
                Object next = arrayLikeIterator.next();
                if (next instanceof String) {
                    string = (String) next;
                } else if (next instanceof ConsString) {
                    string = next.toString();
                } else if ((next instanceof Number) || (next instanceof NativeNumber) || (next instanceof NativeString)) {
                    string = JSType.toString(next);
                }
                if (string != null) {
                    stringifyState.propertyList.add(string);
                }
            }
        }
        Object string2 = obj4;
        if (string2 instanceof NativeNumber) {
            string2 = Double.valueOf(JSType.toNumber(JSType.toPrimitive(string2, Number.class)));
        } else if (string2 instanceof NativeString) {
            string2 = JSType.toString(JSType.toPrimitive(string2, String.class));
        }
        if (string2 instanceof Number) {
            int iMin = Math.min(10, JSType.toInteger(string2));
            if (iMin < 1) {
                strSubstring = "";
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < iMin; i++) {
                    sb.append(' ');
                }
                strSubstring = sb.toString();
            }
        } else {
            Object obj5 = string2;
            if ((obj5 instanceof String) || (obj5 instanceof ConsString)) {
                String string3 = string2.toString();
                strSubstring = string3.substring(0, Math.min(10, string3.length()));
            } else {
                strSubstring = "";
            }
        }
        stringifyState.gap = strSubstring;
        ScriptObject scriptObjectNewEmptyInstance = Global.newEmptyInstance();
        scriptObjectNewEmptyInstance.set("", obj2, 0);
        return str("", scriptObjectNewEmptyInstance, stringifyState);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJSON$StringifyState.class */
    private static class StringifyState {
        final Map stack;
        StringBuilder indent;
        String gap;
        List propertyList;
        Object replacerFunction;

        private StringifyState() {
            this.stack = new IdentityHashMap();
            this.indent = new StringBuilder();
            this.gap = "";
            this.propertyList = null;
            this.replacerFunction = null;
        }

        StringifyState(CallableC01881 callableC01881) {
            this();
        }
    }

    private static Object str(Object obj, Object obj2, StringifyState stringifyState) {
        if (!$assertionsDisabled && !(obj2 instanceof ScriptObject) && !(obj2 instanceof JSObject)) {
            throw new AssertionError();
        }
        Object property = getProperty(obj2, obj);
        try {
            if (property instanceof ScriptObject) {
                InvokeByName to_json = getTO_JSON();
                ScriptObject scriptObject = (ScriptObject) property;
                Object objInvokeExact = (Object) to_json.getGetter().invokeExact(scriptObject);
                if (Bootstrap.isCallable(objInvokeExact)) {
                    property = (Object) to_json.getInvoker().invokeExact(objInvokeExact, scriptObject, obj);
                }
            } else if (property instanceof JSObject) {
                Object member = ((JSObject) property).getMember("toJSON");
                if (Bootstrap.isCallable(member)) {
                    property = (Object) getJSOBJECT_INVOKER().invokeExact(member, property);
                }
            }
            if (stringifyState.replacerFunction != null) {
                property = (Object) getREPLACER_INVOKER().invokeExact(stringifyState.replacerFunction, obj2, obj, property);
            }
            if (property instanceof ScriptObject) {
                if (property instanceof NativeNumber) {
                    property = Double.valueOf(JSType.toNumber(property));
                } else if (property instanceof NativeString) {
                    property = JSType.toString(property);
                } else if (property instanceof NativeBoolean) {
                    property = Boolean.valueOf(((NativeBoolean) property).booleanValue());
                }
            }
            if (property == null) {
                return Configurator.NULL;
            }
            if (Boolean.TRUE.equals(property)) {
                return "true";
            }
            if (Boolean.FALSE.equals(property)) {
                return "false";
            }
            if (property instanceof String) {
                return JSONFunctions.quote((String) property);
            }
            if (property instanceof ConsString) {
                return JSONFunctions.quote(property.toString());
            }
            if (property instanceof Number) {
                return JSType.isFinite(((Number) property).doubleValue()) ? JSType.toString(property) : Configurator.NULL;
            }
            if (JSType.m12of(property) == JSType.OBJECT) {
                if (isArray(property) || isJSObjectArray(property)) {
                    return m8JA(property, stringifyState);
                }
                if ((property instanceof ScriptObject) || (property instanceof JSObject)) {
                    return m7JO(property, stringifyState);
                }
            }
            return ScriptRuntime.UNDEFINED;
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    /* renamed from: JO */
    private static String m7JO(Object obj, StringifyState stringifyState) {
        if (!$assertionsDisabled && !(obj instanceof ScriptObject) && !(obj instanceof JSObject)) {
            throw new AssertionError();
        }
        if (stringifyState.stack.containsKey(obj)) {
            throw ECMAErrors.typeError("JSON.stringify.cyclic", new String[0]);
        }
        stringifyState.stack.put(obj, obj);
        StringBuilder sb = new StringBuilder(stringifyState.indent.toString());
        stringifyState.indent.append(stringifyState.gap);
        StringBuilder sb2 = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : stringifyState.propertyList == null ? Arrays.asList(getOwnKeys(obj)) : stringifyState.propertyList) {
            Object objStr = str(obj2, obj, stringifyState);
            if (objStr != ScriptRuntime.UNDEFINED) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(JSONFunctions.quote(obj2.toString())).append(':');
                if (!stringifyState.gap.isEmpty()) {
                    sb3.append(' ');
                }
                sb3.append(objStr);
                arrayList.add(sb3);
            }
        }
        if (arrayList.isEmpty()) {
            sb2.append("{}");
        } else if (stringifyState.gap.isEmpty()) {
            int size = arrayList.size();
            int i = 0;
            sb2.append('{');
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                sb2.append(it.next());
                if (i < size - 1) {
                    sb2.append(',');
                }
                i++;
            }
            sb2.append('}');
        } else {
            int size2 = arrayList.size();
            int i2 = 0;
            sb2.append("{\n");
            sb2.append((CharSequence) stringifyState.indent);
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                sb2.append(it2.next());
                if (i2 < size2 - 1) {
                    sb2.append(",\n");
                    sb2.append((CharSequence) stringifyState.indent);
                }
                i2++;
            }
            sb2.append('\n');
            sb2.append((CharSequence) sb);
            sb2.append('}');
        }
        stringifyState.stack.remove(obj);
        stringifyState.indent = sb;
        return sb2.toString();
    }

    /* renamed from: JA */
    private static Object m8JA(Object obj, StringifyState stringifyState) {
        if (!$assertionsDisabled && !(obj instanceof ScriptObject) && !(obj instanceof JSObject)) {
            throw new AssertionError();
        }
        if (stringifyState.stack.containsKey(obj)) {
            throw ECMAErrors.typeError("JSON.stringify.cyclic", new String[0]);
        }
        stringifyState.stack.put(obj, obj);
        StringBuilder sb = new StringBuilder(stringifyState.indent.toString());
        stringifyState.indent.append(stringifyState.gap);
        ArrayList arrayList = new ArrayList();
        int integer = JSType.toInteger(getLength(obj));
        for (int i = 0; i < integer; i++) {
            Object objStr = str(Integer.valueOf(i), obj, stringifyState);
            if (objStr == ScriptRuntime.UNDEFINED) {
                objStr = Configurator.NULL;
            }
            arrayList.add(objStr);
        }
        StringBuilder sb2 = new StringBuilder();
        if (arrayList.isEmpty()) {
            sb2.append("[]");
        } else if (stringifyState.gap.isEmpty()) {
            int size = arrayList.size();
            int i2 = 0;
            sb2.append('[');
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                sb2.append(it.next());
                if (i2 < size - 1) {
                    sb2.append(',');
                }
                i2++;
            }
            sb2.append(']');
        } else {
            int size2 = arrayList.size();
            int i3 = 0;
            sb2.append("[\n");
            sb2.append((CharSequence) stringifyState.indent);
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                sb2.append(it2.next());
                if (i3 < size2 - 1) {
                    sb2.append(",\n");
                    sb2.append((CharSequence) stringifyState.indent);
                }
                i3++;
            }
            sb2.append('\n');
            sb2.append((CharSequence) sb);
            sb2.append(']');
        }
        stringifyState.stack.remove(obj);
        stringifyState.indent = sb;
        return sb2.toString();
    }

    private static String[] getOwnKeys(Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getOwnKeys(false);
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).getOwnKeys(false);
        }
        if (obj instanceof JSObject) {
            return (String[]) ((JSObject) obj).keySet().toArray(new String[0]);
        }
        throw new AssertionError("should not reach here");
    }

    private static Object getLength(Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getLength();
        }
        if (obj instanceof JSObject) {
            return ((JSObject) obj).getMember("length");
        }
        throw new AssertionError("should not reach here");
    }

    private static boolean isJSObjectArray(Object obj) {
        return (obj instanceof JSObject) && ((JSObject) obj).isArray();
    }

    private static Object getProperty(Object obj, Object obj2) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).get(obj2);
        }
        if (obj instanceof JSObject) {
            JSObject jSObject = (JSObject) obj;
            if (obj2 instanceof Integer) {
                return jSObject.getSlot(((Integer) obj2).intValue());
            }
            return jSObject.getMember(Objects.toString(obj2));
        }
        return new AssertionError("should not reach here");
    }
}

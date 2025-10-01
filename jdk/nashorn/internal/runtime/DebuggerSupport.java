package jdk.nashorn.internal.runtime;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.scripts.C0278JS;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/DebuggerSupport.class */
final class DebuggerSupport {
    static boolean FORCELOAD;
    static final boolean $assertionsDisabled;

    DebuggerSupport() {
    }

    static {
        $assertionsDisabled = !DebuggerSupport.class.desiredAssertionStatus();
        FORCELOAD = true;
        new DebuggerValueDesc(null, false, null, null);
        new SourceInfo(null, 0, null, null);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/DebuggerSupport$DebuggerValueDesc.class */
    static class DebuggerValueDesc {
        final String key;
        final boolean expandable;
        final Object valueAsObject;
        final String valueAsString;

        DebuggerValueDesc(String str, boolean z, Object obj, String str2) {
            this.key = str;
            this.expandable = z;
            this.valueAsObject = obj;
            this.valueAsString = str2;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/DebuggerSupport$SourceInfo.class */
    static class SourceInfo {
        final String name;
        final URL url;
        final int hash;
        final char[] content;

        SourceInfo(String str, int i, URL url, char[] cArr) {
            this.name = str;
            this.hash = i;
            this.url = url;
            this.content = cArr;
        }
    }

    static SourceInfo getSourceInfo(Class cls) throws NoSuchFieldException {
        if (C0278JS.class.isAssignableFrom(cls)) {
            try {
                Field declaredField = cls.getDeclaredField(CompilerConstants.SOURCE.symbolName());
                declaredField.setAccessible(true);
                return ((Source) declaredField.get(null)).getSourceInfo();
            } catch (IllegalAccessException | NoSuchFieldException unused) {
                return null;
            }
        }
        return null;
    }

    static Object getGlobal() {
        return Context.getGlobal();
    }

    static Object eval(ScriptObject scriptObject, Object obj, String str, boolean z) {
        Global global = Context.getGlobal();
        try {
            return global.getContext().eval(scriptObject != null ? scriptObject : global, str, obj != null ? obj : global, ScriptRuntime.UNDEFINED);
        } catch (Throwable th) {
            if (z) {
                return th;
            }
            return null;
        }
    }

    static DebuggerValueDesc[] valueInfos(Object obj, boolean z) {
        if ($assertionsDisabled || (obj instanceof ScriptObject)) {
            return getDebuggerValueDescs((ScriptObject) obj, z, new HashSet());
        }
        throw new AssertionError();
    }

    static DebuggerValueDesc valueInfo(String str, Object obj, boolean z) {
        return valueInfo(str, obj, z, new HashSet());
    }

    private static DebuggerValueDesc valueInfo(String str, Object obj, boolean z, Set set) {
        if ((obj instanceof ScriptObject) && !(obj instanceof ScriptFunction)) {
            ScriptObject scriptObject = (ScriptObject) obj;
            return new DebuggerValueDesc(str, !scriptObject.isEmpty(), obj, objectAsString(scriptObject, z, set));
        }
        return new DebuggerValueDesc(str, false, obj, valueAsString(obj));
    }

    private static DebuggerValueDesc[] getDebuggerValueDescs(ScriptObject scriptObject, boolean z, Set set) {
        if (set.contains(scriptObject)) {
            return null;
        }
        set.add(scriptObject);
        String[] ownKeys = scriptObject.getOwnKeys(z);
        DebuggerValueDesc[] debuggerValueDescArr = new DebuggerValueDesc[ownKeys.length];
        for (int i = 0; i < ownKeys.length; i++) {
            String str = ownKeys[i];
            debuggerValueDescArr[i] = valueInfo(str, scriptObject.get(str), z, set);
        }
        set.remove(scriptObject);
        return debuggerValueDescArr;
    }

    private static String objectAsString(ScriptObject scriptObject, boolean z, Set set) {
        StringBuilder sb = new StringBuilder();
        if (ScriptObject.isArray(scriptObject)) {
            sb.append('[');
            long j = (long) scriptObject.getDouble("length", -1);
            long j2 = 0;
            while (true) {
                long j3 = j2;
                if (j3 >= j) {
                    break;
                }
                if (scriptObject.has(j3)) {
                    Object obj = scriptObject.get(j3);
                    if (!(obj == ScriptRuntime.UNDEFINED)) {
                        if (j3 != 0) {
                            sb.append(", ");
                        }
                        if ((obj instanceof ScriptObject) && !(obj instanceof ScriptFunction)) {
                            String strObjectAsString = objectAsString((ScriptObject) obj, z, set);
                            sb.append(strObjectAsString != null ? strObjectAsString : "{...}");
                        } else {
                            sb.append(valueAsString(obj));
                        }
                    } else if (j3 != 0) {
                        sb.append(",");
                    }
                } else if (j3 != 0) {
                    sb.append(',');
                }
                j2 = j3 + 1;
            }
            sb.append(']');
        } else {
            sb.append('{');
            DebuggerValueDesc[] debuggerValueDescs = getDebuggerValueDescs(scriptObject, z, set);
            if (debuggerValueDescs != null) {
                for (int i = 0; i < debuggerValueDescs.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    String str = debuggerValueDescs[i].valueAsString;
                    sb.append(debuggerValueDescs[i].key);
                    sb.append(": ");
                    sb.append(str);
                }
            }
            sb.append('}');
        }
        return sb.toString();
    }

    static String valueAsString(Object obj) {
        switch (C02231.$SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.m12of(obj).ordinal()]) {
            case 1:
                return obj.toString();
            case 2:
                return escape(obj.toString());
            case 3:
                return JSType.toString(((Number) obj).doubleValue());
            case 4:
                return Configurator.NULL;
            case 5:
                return "undefined";
            case 6:
                return ScriptRuntime.safeToString(obj);
            case 7:
                if (obj instanceof ScriptFunction) {
                    return ((ScriptFunction) obj).toSource();
                }
                return obj.toString();
            default:
                return obj.toString();
        }
    }

    /* renamed from: jdk.nashorn.internal.runtime.DebuggerSupport$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/DebuggerSupport$1.class */
    static /* synthetic */ class C02231 {
        static final int[] $SwitchMap$jdk$nashorn$internal$runtime$JSType = new int[JSType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.NULL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.UNDEFINED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.FUNCTION.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private static String escape(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for (char c : str.toCharArray()) {
            switch (c) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case CharacterType.ALNUM /* 13 */:
                    sb.append("\\r");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                case OPCode.SEMI_END_BUF /* 39 */:
                    sb.append("\\'");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    if (c < ' ' || c >= '\u00ff') {
                        sb.append("\\u");
                        String hexString = Integer.toHexString(c);
                        for (int length = hexString.length(); length < 4; length++) {
                            sb.append('0');
                        }
                        sb.append(hexString);
                        break;
                    } else {
                        sb.append(c);
                        break;
                    }
                    break;
            }
        }
        sb.append("\"");
        return sb.toString();
    }
}

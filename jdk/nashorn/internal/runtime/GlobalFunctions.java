package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/GlobalFunctions.class */
public final class GlobalFunctions {
    public static final MethodHandle PARSEINT = findOwnMH("parseInt", Double.TYPE, new Class[]{Object.class, Object.class, Object.class});
    public static final MethodHandle PARSEINT_OI = findOwnMH("parseInt", Double.TYPE, new Class[]{Object.class, Object.class, Integer.TYPE});
    public static final MethodHandle PARSEINT_Z = Lookup.f31MH.dropArguments(Lookup.f31MH.dropArguments(Lookup.f31MH.constant(Double.TYPE, Double.valueOf(Double.NaN)), 0, new Class[]{Boolean.TYPE}), 0, new Class[]{Object.class});
    public static final MethodHandle PARSEINT_I = Lookup.f31MH.dropArguments(Lookup.f31MH.identity(Integer.TYPE), 0, new Class[]{Object.class});
    public static final MethodHandle PARSEINT_O = findOwnMH("parseInt", Double.TYPE, new Class[]{Object.class, Object.class});
    public static final MethodHandle PARSEFLOAT = findOwnMH("parseFloat", Double.TYPE, new Class[]{Object.class, Object.class});
    public static final MethodHandle IS_NAN_I = Lookup.f31MH.dropArguments(Lookup.f31MH.constant(Boolean.TYPE, false), 0, new Class[]{Object.class});
    public static final MethodHandle IS_NAN_J = Lookup.f31MH.dropArguments(Lookup.f31MH.constant(Boolean.TYPE, false), 0, new Class[]{Object.class});
    public static final MethodHandle IS_NAN_D = Lookup.f31MH.dropArguments(Lookup.f31MH.findStatic(MethodHandles.lookup(), Double.class, "isNaN", Lookup.f31MH.type(Boolean.TYPE, new Class[]{Double.TYPE})), 0, new Class[]{Object.class});
    public static final MethodHandle IS_NAN = findOwnMH("isNaN", Boolean.TYPE, new Class[]{Object.class, Object.class});
    public static final MethodHandle IS_FINITE = findOwnMH("isFinite", Boolean.TYPE, new Class[]{Object.class, Object.class});
    public static final MethodHandle ENCODE_URI = findOwnMH("encodeURI", Object.class, new Class[]{Object.class, Object.class});
    public static final MethodHandle ENCODE_URICOMPONENT = findOwnMH("encodeURIComponent", Object.class, new Class[]{Object.class, Object.class});
    public static final MethodHandle DECODE_URI = findOwnMH("decodeURI", Object.class, new Class[]{Object.class, Object.class});
    public static final MethodHandle DECODE_URICOMPONENT = findOwnMH("decodeURIComponent", Object.class, new Class[]{Object.class, Object.class});
    public static final MethodHandle ESCAPE = findOwnMH("escape", String.class, new Class[]{Object.class, Object.class});
    public static final MethodHandle UNESCAPE = findOwnMH("unescape", String.class, new Class[]{Object.class, Object.class});
    public static final MethodHandle ANONYMOUS = findOwnMH("anonymous", Object.class, new Class[]{Object.class});
    private static final String UNESCAPED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_+-./";

    private GlobalFunctions() {
    }

    public static double parseInt(Object obj, Object obj2, Object obj3) {
        return parseIntInternal(JSType.trimLeft(JSType.toString(obj2)), JSType.toInt32(obj3));
    }

    public static double parseInt(Object obj, Object obj2, int i) {
        return parseIntInternal(JSType.trimLeft(JSType.toString(obj2)), i);
    }

    public static double parseInt(Object obj, Object obj2) {
        return parseIntInternal(JSType.trimLeft(JSType.toString(obj2)), 0);
    }

    private static double parseIntInternal(String str, int i) {
        int length = str.length();
        int i2 = i;
        if (length == 0) {
            return Double.NaN;
        }
        boolean z = false;
        int i3 = 0;
        char cCharAt = str.charAt(0);
        if (cCharAt < '0') {
            if (cCharAt == '-') {
                z = true;
            } else if (cCharAt != '+') {
                return Double.NaN;
            }
            i3 = 0 + 1;
        }
        boolean z2 = true;
        if (i2 != 0) {
            if (i2 < 2 || i2 > 36) {
                return Double.NaN;
            }
            if (i2 != 16) {
                z2 = false;
            }
        } else {
            i2 = 10;
        }
        if (z2 && i3 + 1 < length) {
            char cCharAt2 = str.charAt(i3);
            char cCharAt3 = str.charAt(i3 + 1);
            if (cCharAt2 == '0' && (cCharAt3 == 'x' || cCharAt3 == 'X')) {
                i2 = 16;
                i3 += 2;
            }
        }
        double d = 0.0d;
        boolean z3 = false;
        while (i3 < length) {
            int i4 = i3;
            i3++;
            char cCharAt4 = str.charAt(i4);
            int i5 = i2;
            int i6 = -1;
            if (cCharAt4 >= '0' && cCharAt4 <= '9') {
                i6 = cCharAt4 - '0';
            } else if (i5 > 10) {
                if (cCharAt4 >= 'a' && cCharAt4 <= 'z') {
                    i6 = (cCharAt4 - 'a') + 10;
                } else if (cCharAt4 >= 'A' && cCharAt4 <= 'Z') {
                    i6 = (cCharAt4 - 'A') + 10;
                }
            }
            int i7 = i6 < i5 ? i6 : -1;
            if (i7 < 0) {
                break;
            }
            z3 = true;
            d = (d * i2) + i7;
        }
        if (z3) {
            return z ? -d : d;
        }
        return Double.NaN;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x01fb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double parseFloat(Object obj, Object obj2) {
        String strTrimLeft = JSType.trimLeft(JSType.toString(obj2));
        int length = strTrimLeft.length();
        if (length == 0) {
            return Double.NaN;
        }
        int i = 0;
        boolean z = false;
        char cCharAt = strTrimLeft.charAt(0);
        if (cCharAt == '-') {
            i = 0 + 1;
            z = true;
        } else if (cCharAt == '+') {
            i = 0 + 1;
        } else if (cCharAt == 'N' && strTrimLeft.startsWith("NaN")) {
            return Double.NaN;
        }
        if (i == length) {
            return Double.NaN;
        }
        if (strTrimLeft.charAt(i) == 'I' && strTrimLeft.substring(i).startsWith("Infinity")) {
            return z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        boolean z2 = false;
        boolean z3 = false;
        int i2 = -1;
        int i3 = i;
        while (i3 < length) {
            switch (strTrimLeft.charAt(i3)) {
                case OPCode.BACKREFN /* 43 */:
                case OPCode.BACKREF_MULTI /* 45 */:
                    if (i2 != i3 - 1) {
                        break;
                    } else {
                        i3++;
                    }
                case OPCode.BACKREF_MULTI_IC /* 46 */:
                    if (i2 != -1 || z2) {
                        break;
                    } else {
                        z2 = true;
                        i3++;
                    }
                    break;
                case '0':
                case OPCode.MEMORY_START_PUSH /* 49 */:
                case OPCode.MEMORY_END_PUSH /* 50 */:
                case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                case OPCode.MEMORY_END /* 52 */:
                case OPCode.MEMORY_END_REC /* 53 */:
                case OPCode.FAIL /* 54 */:
                case OPCode.JUMP /* 55 */:
                case '8':
                case OPCode.POP /* 57 */:
                    if (i2 != -1) {
                        z3 = true;
                    } else {
                        continue;
                    }
                    i3++;
                case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                case Shell.COMPILATION_ERROR /* 101 */:
                    if (i2 != -1) {
                        break;
                    } else {
                        i2 = i3;
                        i3++;
                    }
            }
            if (i2 != -1 && !z3) {
                i3 = i2;
            }
            if (i != i3) {
                return Double.NaN;
            }
            try {
                double dDoubleValue = Double.valueOf(strTrimLeft.substring(i, i3)).doubleValue();
                return z ? -dDoubleValue : dDoubleValue;
            } catch (NumberFormatException unused) {
                return Double.NaN;
            }
        }
        if (i2 != -1) {
            i3 = i2;
        }
        if (i != i3) {
        }
    }

    public static boolean isNaN(Object obj, Object obj2) {
        return Double.isNaN(JSType.toNumber(obj2));
    }

    public static boolean isFinite(Object obj, Object obj2) {
        double number = JSType.toNumber(obj2);
        return (Double.isInfinite(number) || Double.isNaN(number)) ? false : true;
    }

    public static Object encodeURI(Object obj, Object obj2) {
        return URIUtils.encodeURI(obj, JSType.toString(obj2));
    }

    public static Object encodeURIComponent(Object obj, Object obj2) {
        return URIUtils.encodeURIComponent(obj, JSType.toString(obj2));
    }

    public static Object decodeURI(Object obj, Object obj2) {
        return URIUtils.decodeURI(obj, JSType.toString(obj2));
    }

    public static Object decodeURIComponent(Object obj, Object obj2) {
        return URIUtils.decodeURIComponent(obj, JSType.toString(obj2));
    }

    public static String escape(Object obj, Object obj2) {
        String string = JSType.toString(obj2);
        int length = string.length();
        if (length == 0) {
            return string;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char cCharAt = string.charAt(i);
            if (UNESCAPED.indexOf(cCharAt) != -1) {
                sb.append(cCharAt);
            } else if (cCharAt < '\u0100') {
                sb.append('%');
                if (cCharAt < 16) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(cCharAt).toUpperCase(Locale.ENGLISH));
            } else {
                sb.append("%u");
                if (cCharAt < '\u1000') {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(cCharAt).toUpperCase(Locale.ENGLISH));
            }
        }
        return sb.toString();
    }

    public static String unescape(Object obj, Object obj2) {
        String string = JSType.toString(obj2);
        int length = string.length();
        if (length == 0) {
            return string;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < length) {
            char cCharAt = string.charAt(i);
            if (cCharAt != '%') {
                sb.append(cCharAt);
            } else if (i < length - 5 && string.charAt(i + 1) == 'u') {
                try {
                    cCharAt = (char) Integer.parseInt(string.substring(i + 2, i + 6), 16);
                    sb.append(cCharAt);
                    i += 5;
                } catch (NumberFormatException unused) {
                }
            } else if (i < length - 2) {
                try {
                    cCharAt = (char) Integer.parseInt(string.substring(i + 1, i + 3), 16);
                    sb.append(cCharAt);
                    i += 2;
                } catch (NumberFormatException unused2) {
                }
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        return sb.toString();
    }

    public static Object anonymous(Object obj) {
        return ScriptRuntime.UNDEFINED;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), GlobalFunctions.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}

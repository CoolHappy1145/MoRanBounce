package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:org/slf4j/helpers/MessageFormatter.class */
public final class MessageFormatter {
    static final char DELIM_START = '{';
    static final char DELIM_STOP = '}';
    static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    public static final FormattingTuple format(String str, Object obj) {
        return arrayFormat(str, new Object[]{obj});
    }

    public static final FormattingTuple format(String str, Object obj, Object obj2) {
        return arrayFormat(str, new Object[]{obj, obj2});
    }

    public static final FormattingTuple arrayFormat(String str, Object[] objArr) {
        Throwable th;
        if (objArr == null || objArr.length == 0) {
            th = null;
        } else {
            Object obj = objArr[objArr.length - 1];
            if (obj instanceof Throwable) {
                th = (Throwable) obj;
            } else {
                th = null;
            }
        }
        Throwable th2 = th;
        Object[] objArrTrimmedCopy = objArr;
        if (th2 != null) {
            objArrTrimmedCopy = trimmedCopy(objArr);
        }
        return arrayFormat(str, objArrTrimmedCopy, th2);
    }

    private static Object[] trimmedCopy(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        return objArr2;
    }

    public static final FormattingTuple arrayFormat(String str, Object[] objArr, Throwable th) {
        int i;
        int i2;
        if (str == null) {
            return new FormattingTuple(null, objArr, th);
        }
        if (objArr == null) {
            return new FormattingTuple(str);
        }
        int i3 = 0;
        StringBuilder sb = new StringBuilder(str.length() + 50);
        int i4 = 0;
        while (i4 < objArr.length) {
            int iIndexOf = str.indexOf(DELIM_STR, i3);
            if (iIndexOf == -1) {
                if (i3 == 0) {
                    return new FormattingTuple(str, objArr, th);
                }
                sb.append((CharSequence) str, i3, str.length());
                return new FormattingTuple(sb.toString(), objArr, th);
            }
            if (isEscapedDelimeter(str, iIndexOf)) {
                if (!isDoubleEscaped(str, iIndexOf)) {
                    i4--;
                    sb.append((CharSequence) str, i3, iIndexOf - 1);
                    sb.append('{');
                    i = iIndexOf;
                    i2 = 1;
                } else {
                    sb.append((CharSequence) str, i3, iIndexOf - 1);
                    deeplyAppendParameter(sb, objArr[i4], new HashMap());
                    i = iIndexOf;
                    i2 = 2;
                }
            } else {
                sb.append((CharSequence) str, i3, iIndexOf);
                deeplyAppendParameter(sb, objArr[i4], new HashMap());
                i = iIndexOf;
                i2 = 2;
            }
            i3 = i + i2;
            i4++;
        }
        sb.append((CharSequence) str, i3, str.length());
        return new FormattingTuple(sb.toString(), objArr, th);
    }

    static final boolean isEscapedDelimeter(String str, int i) {
        if (i != 0 && str.charAt(i - 1) == ESCAPE_CHAR) {
            return true;
        }
        return false;
    }

    static final boolean isDoubleEscaped(String str, int i) {
        if (i >= 2 && str.charAt(i - 2) == ESCAPE_CHAR) {
            return true;
        }
        return false;
    }

    private static void deeplyAppendParameter(StringBuilder sb, Object obj, Map map) {
        if (obj == null) {
            sb.append(Configurator.NULL);
            return;
        }
        if (!obj.getClass().isArray()) {
            safeObjectAppend(sb, obj);
            return;
        }
        if (obj instanceof boolean[]) {
            booleanArrayAppend(sb, (boolean[]) obj);
            return;
        }
        if (obj instanceof byte[]) {
            byteArrayAppend(sb, (byte[]) obj);
            return;
        }
        if (obj instanceof char[]) {
            charArrayAppend(sb, (char[]) obj);
            return;
        }
        if (obj instanceof short[]) {
            shortArrayAppend(sb, (short[]) obj);
            return;
        }
        if (obj instanceof int[]) {
            intArrayAppend(sb, (int[]) obj);
            return;
        }
        if (obj instanceof long[]) {
            longArrayAppend(sb, (long[]) obj);
            return;
        }
        if (obj instanceof float[]) {
            floatArrayAppend(sb, (float[]) obj);
        } else if (obj instanceof double[]) {
            doubleArrayAppend(sb, (double[]) obj);
        } else {
            objectArrayAppend(sb, (Object[]) obj, map);
        }
    }

    private static void safeObjectAppend(StringBuilder sb, Object obj) {
        try {
            sb.append(obj.toString());
        } catch (Throwable th) {
            Util.report("SLF4J: Failed toString() invocation on an object of type [" + obj.getClass().getName() + "]", th);
            sb.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuilder sb, Object[] objArr, Map map) {
        sb.append('[');
        if (!map.containsKey(objArr)) {
            map.put(objArr, null);
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                deeplyAppendParameter(sb, objArr[i], map);
                if (i != length - 1) {
                    sb.append(", ");
                }
            }
            map.remove(objArr);
        } else {
            sb.append("...");
        }
        sb.append(']');
    }

    private static void booleanArrayAppend(StringBuilder sb, boolean[] zArr) {
        sb.append('[');
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(zArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void byteArrayAppend(StringBuilder sb, byte[] bArr) {
        sb.append('[');
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append((int) bArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void charArrayAppend(StringBuilder sb, char[] cArr) {
        sb.append('[');
        int length = cArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(cArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void shortArrayAppend(StringBuilder sb, short[] sArr) {
        sb.append('[');
        int length = sArr.length;
        for (int i = 0; i < length; i++) {
            sb.append((int) sArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void intArrayAppend(StringBuilder sb, int[] iArr) {
        sb.append('[');
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(iArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void longArrayAppend(StringBuilder sb, long[] jArr) {
        sb.append('[');
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(jArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void floatArrayAppend(StringBuilder sb, float[] fArr) {
        sb.append('[');
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(fArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void doubleArrayAppend(StringBuilder sb, double[] dArr) {
        sb.append('[');
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(dArr[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
}

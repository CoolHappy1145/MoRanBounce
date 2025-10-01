package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ArrayIndex.class */
public final class ArrayIndex {
    private static final int INVALID_ARRAY_INDEX = -1;
    private static final long MAX_ARRAY_INDEX = 4294967294L;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ArrayIndex.class.desiredAssertionStatus();
    }

    private ArrayIndex() {
    }

    private static long fromString(String str) {
        long j = 0;
        int length = str.length();
        if (length == 0) {
            return -1L;
        }
        if (length > 1 && str.charAt(0) == '0') {
            return -1L;
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return -1L;
            }
            j = ((j * 10) + cCharAt) - 48;
            if (j > MAX_ARRAY_INDEX) {
                return -1L;
            }
        }
        return j;
    }

    public static int getArrayIndex(Object obj) {
        if (obj instanceof Integer) {
            int iIntValue = ((Integer) obj).intValue();
            if (iIntValue >= 0) {
                return iIntValue;
            }
            return -1;
        }
        if (obj instanceof Double) {
            return getArrayIndex(((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return (int) fromString((String) obj);
        }
        if (obj instanceof Long) {
            long jLongValue = ((Long) obj).longValue();
            if (jLongValue >= 0 && jLongValue <= MAX_ARRAY_INDEX) {
                return (int) jLongValue;
            }
            return -1;
        }
        if (obj instanceof ConsString) {
            return (int) fromString(obj.toString());
        }
        if ($assertionsDisabled || !(obj instanceof ScriptObject)) {
            return -1;
        }
        throw new AssertionError();
    }

    public static int getArrayIndex(double d) {
        if (((double) ((int) d)) == d) {
            int i = (int) d;
            if (i >= 0) {
                return i;
            }
            return -1;
        }
        if (((double) ((long) d)) == d) {
            long j = (long) d;
            if (j >= 0 && j <= MAX_ARRAY_INDEX) {
                return (int) j;
            }
            return -1;
        }
        return -1;
    }

    public static int getArrayIndex(String str) {
        return (int) fromString(str);
    }

    public static long toLongIndex(int i) {
        return i & JSType.MAX_UINT;
    }

    public static String toKey(int i) {
        return Long.toString(i & JSType.MAX_UINT);
    }
}

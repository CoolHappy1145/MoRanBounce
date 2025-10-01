package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/MinMaxLen.class */
final class MinMaxLen {
    int min;
    int max;
    private static final short[] distValues = {1000, 500, 333, 250, 200, 167, 143, 125, 111, 100, 91, 83, 77, 71, 67, 63, 59, 56, 53, 50, 48, 45, 43, 42, 40, 38, 37, 36, 34, 33, 32, 31, 30, 29, 29, 28, 27, 26, 26, 25, 24, 24, 23, 23, 22, 22, 21, 21, 20, 20, 20, 19, 19, 19, 18, 18, 18, 17, 17, 17, 16, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 10, 10, 10, 10};
    static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

    MinMaxLen() {
    }

    MinMaxLen(int i, int i2) {
        this.min = i;
        this.max = i2;
    }

    int distanceValue() {
        if (this.max == Integer.MAX_VALUE) {
            return 0;
        }
        int i = this.max - this.min;
        if (i < distValues.length) {
            return distValues[i];
        }
        return 1;
    }

    int compareDistanceValue(MinMaxLen minMaxLen, int i, int i2) {
        int iDistanceValue;
        int iDistanceValue2;
        if (i2 <= 0) {
            return -1;
        }
        if (i <= 0 || (iDistanceValue2 = i2 * minMaxLen.distanceValue()) > (iDistanceValue = i * distanceValue())) {
            return 1;
        }
        if (iDistanceValue2 < iDistanceValue) {
            return -1;
        }
        if (minMaxLen.min < this.min) {
            return 1;
        }
        if (minMaxLen.min > this.min) {
            return -1;
        }
        return 0;
    }

    boolean equal(MinMaxLen minMaxLen) {
        return this.min == minMaxLen.min && this.max == minMaxLen.max;
    }

    void set(int i, int i2) {
        this.min = i;
        this.max = i2;
    }

    void clear() {
        this.max = 0;
        this.min = 0;
    }

    void copy(MinMaxLen minMaxLen) {
        this.min = minMaxLen.min;
        this.max = minMaxLen.max;
    }

    void add(MinMaxLen minMaxLen) {
        int i;
        int i2;
        int i3 = this.min;
        int i4 = minMaxLen.min;
        if (i3 != Integer.MAX_VALUE && i4 != Integer.MAX_VALUE && i3 <= Integer.MAX_VALUE - i4) {
            i = i3 + i4;
        } else {
            i = Integer.MAX_VALUE;
        }
        this.min = i;
        int i5 = this.max;
        int i6 = minMaxLen.max;
        if (i5 != Integer.MAX_VALUE && i6 != Integer.MAX_VALUE && i5 <= Integer.MAX_VALUE - i6) {
            i2 = i5 + i6;
        } else {
            i2 = Integer.MAX_VALUE;
        }
        this.max = i2;
    }

    void addLength(int i) {
        int i2;
        int i3;
        int i4 = this.min;
        if (i4 != Integer.MAX_VALUE && i != Integer.MAX_VALUE && i4 <= Integer.MAX_VALUE - i) {
            i2 = i4 + i;
        } else {
            i2 = Integer.MAX_VALUE;
        }
        this.min = i2;
        int i5 = this.max;
        if (i5 != Integer.MAX_VALUE && i != Integer.MAX_VALUE && i5 <= Integer.MAX_VALUE - i) {
            i3 = i5 + i;
        } else {
            i3 = Integer.MAX_VALUE;
        }
        this.max = i3;
    }

    void altMerge(MinMaxLen minMaxLen) {
        if (this.min > minMaxLen.min) {
            this.min = minMaxLen.min;
        }
        if (this.max < minMaxLen.max) {
            this.max = minMaxLen.max;
        }
    }

    static String distanceRangeToString(int i, int i2) {
        String str;
        String str2;
        if (i == Integer.MAX_VALUE) {
            str = "inf";
        } else {
            str = "(" + i + ")";
        }
        String str3 = str + "-";
        if (i2 == Integer.MAX_VALUE) {
            str2 = str3 + "inf";
        } else {
            str2 = str3 + "(" + i2 + ")";
        }
        return str2;
    }
}

package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/SearchAlgorithm.class */
public abstract class SearchAlgorithm {
    public static final SearchAlgorithm NONE = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.1
    };
    public static final SearchAlgorithm SLOW = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.2
        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] cArr, int i, int i2, int i3) {
            char[] cArr2 = regex.exact;
            int i4 = regex.exactP;
            int i5 = regex.exactEnd;
            int i6 = i2 - ((i5 - i4) - 1);
            if (i6 > i3) {
                i6 = i3;
            }
            for (int i7 = i; i7 < i6; i7++) {
                if (cArr[i7] == cArr2[i4]) {
                    int i8 = i7 + 1;
                    int i9 = i4 + 1;
                    while (i9 < i5) {
                        int i10 = i8;
                        i8++;
                        if (cArr2[i9] != cArr[i10]) {
                            break;
                        }
                        i9++;
                    }
                    if (i9 == i5) {
                        return i7;
                    }
                }
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] cArr, int i, int i2, int i3, int i4, int i5, int i6) {
            char[] cArr2 = regex.exact;
            int i7 = regex.exactP;
            int i8 = regex.exactEnd;
            int i9 = i3 - (i8 - i7);
            if (i9 > i4) {
                i9 = i4;
            }
            while (i9 >= i) {
                if (cArr[i9] == cArr2[i7]) {
                    int i10 = i9 + 1;
                    int i11 = i7 + 1;
                    while (i11 < i8) {
                        int i12 = i10;
                        i10++;
                        if (cArr2[i11] != cArr[i12]) {
                            break;
                        }
                        i11++;
                    }
                    if (i11 == i8) {
                        return i9;
                    }
                }
                i9--;
            }
            return -1;
        }
    };

    /* renamed from: BM */
    public static final SearchAlgorithm f72BM = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.3
        private static final int BM_BACKWARD_SEARCH_LENGTH_THRESHOLD = 100;

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] cArr, int i, int i2, int i3) {
            char[] cArr2 = regex.exact;
            int i4 = regex.exactP;
            int i5 = regex.exactEnd;
            int i6 = (i3 + (i5 - i4)) - 1;
            if (i6 > i2) {
                i6 = i2;
            }
            int i7 = i5 - 1;
            int i8 = (i + (i5 - i4)) - 1;
            if (regex.intMap == null) {
                while (i8 < i6) {
                    int i9 = i8;
                    for (int i10 = i7; cArr[i9] == cArr2[i10]; i10--) {
                        if (i10 == i4) {
                            return i9;
                        }
                        i9--;
                    }
                    i8 += regex.map[cArr[i8] & '\u00ff'];
                }
                return -1;
            }
            while (i8 < i6) {
                int i11 = i8;
                for (int i12 = i7; cArr[i11] == cArr2[i12]; i12--) {
                    if (i12 == i4) {
                        return i11;
                    }
                    i11--;
                }
                i8 += regex.intMap[cArr[i8] & '\u00ff'];
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] cArr, int i, int i2, int i3, int i4, int i5, int i6) {
            char[] cArr2 = regex.exact;
            int i7 = regex.exactP;
            int i8 = regex.exactEnd;
            if (regex.intMapBackward == null) {
                if (i5 - i6 < 100) {
                    return SLOW.searchBackward(regex, cArr, i, i2, i3, i4, i5, i6);
                }
                setBmBackwardSkip(regex, cArr2, i7, i8);
            }
            int i9 = i3 - (i8 - i7);
            if (i4 < i9) {
                i9 = i4;
            }
            while (i9 >= i) {
                int i10 = i9;
                int i11 = i7;
                while (i11 < i8 && cArr[i10] == cArr2[i11]) {
                    i10++;
                    i11++;
                }
                if (i11 == i8) {
                    return i9;
                }
                i9 -= regex.intMapBackward[cArr[i9] & '\u00ff'];
            }
            return -1;
        }

        private void setBmBackwardSkip(Regex regex, char[] cArr, int i, int i2) {
            int[] iArr;
            if (regex.intMapBackward == null) {
                iArr = new int[256];
                regex.intMapBackward = iArr;
            } else {
                iArr = regex.intMapBackward;
            }
            int i3 = i2 - i;
            for (int i4 = 0; i4 < 256; i4++) {
                iArr[i4] = i3;
            }
            for (int i5 = i3 - 1; i5 > 0; i5--) {
                iArr[cArr[i5] & '\u00ff'] = i5;
            }
        }
    };
    public static final SearchAlgorithm MAP = new SearchAlgorithm() { // from class: jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm.4
        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] cArr, int i, int i2, int i3) {
            byte[] bArr = regex.map;
            for (int i4 = i; i4 < i3; i4++) {
                if (cArr[i4] > '\u00ff' || bArr[cArr[i4]] != 0) {
                    return i4;
                }
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] cArr, int i, int i2, int i3, int i4, int i5, int i6) {
            byte[] bArr = regex.map;
            int i7 = i4;
            if (i7 >= i3) {
                i7 = i3 - 1;
            }
            while (i7 >= i) {
                if (cArr[i7] > '\u00ff' || bArr[cArr[i7]] != 0) {
                    return i7;
                }
                i7--;
            }
            return -1;
        }
    };

    public abstract String getName();

    public abstract int search(Regex regex, char[] cArr, int i, int i2, int i3);

    public abstract int searchBackward(Regex regex, char[] cArr, int i, int i2, int i3, int i4, int i5, int i6);

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/SearchAlgorithm$SLOW_IC.class */
    public static final class SLOW_IC extends SearchAlgorithm {
        public SLOW_IC(Regex regex) {
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int search(Regex regex, char[] cArr, int i, int i2, int i3) {
            char[] cArr2 = regex.exact;
            int i4 = regex.exactP;
            int i5 = regex.exactEnd;
            int i6 = i2 - ((i5 - i4) - 1);
            if (i6 > i3) {
                i6 = i3;
            }
            for (int i7 = i; i7 < i6; i7++) {
                if (lowerCaseMatch(cArr2, i4, i5, cArr, i7, i2)) {
                    return i7;
                }
            }
            return -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm
        public final int searchBackward(Regex regex, char[] cArr, int i, int i2, int i3, int i4, int i5, int i6) {
            char[] cArr2 = regex.exact;
            int i7 = regex.exactP;
            int i8 = regex.exactEnd;
            int i9 = i3 - (i8 - i7);
            if (i9 > i4) {
                i9 = i4;
            }
            while (i9 >= i) {
                if (!lowerCaseMatch(cArr2, i7, i8, cArr, i9, i3)) {
                    int i10 = i9;
                    i9 = i10 <= i2 ? -1 : i10 - 1;
                } else {
                    return i9;
                }
            }
            return -1;
        }

        private static boolean lowerCaseMatch(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4) {
            int i5 = i;
            int i6 = i3;
            while (i5 < i2) {
                int i7 = i5;
                i5++;
                int i8 = i6;
                i6++;
                if (cArr[i7] != EncodingHelper.toLowerCase(cArr2[i8])) {
                    return false;
                }
            }
            return true;
        }
    }
}

package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.codegen.SharedScopeCall;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/OptExactInfo.class */
final class OptExactInfo {
    static final int OPT_EXACT_MAXLEN = 24;
    boolean reachEnd;
    boolean ignoreCase;
    int length;
    private static final int COMP_EM_BASE = 20;
    final MinMaxLen mmd = new MinMaxLen();
    final OptAnchorInfo anchor = new OptAnchorInfo();
    final char[] chars = new char[24];

    OptExactInfo() {
    }

    boolean isFull() {
        return this.length >= 24;
    }

    void clear() {
        this.mmd.clear();
        this.anchor.clear();
        this.reachEnd = false;
        this.ignoreCase = false;
        this.length = 0;
    }

    void copy(OptExactInfo optExactInfo) {
        this.mmd.copy(optExactInfo.mmd);
        this.anchor.copy(optExactInfo.anchor);
        this.reachEnd = optExactInfo.reachEnd;
        this.ignoreCase = optExactInfo.ignoreCase;
        this.length = optExactInfo.length;
        System.arraycopy(optExactInfo.chars, 0, this.chars, 0, 24);
    }

    void concat(OptExactInfo optExactInfo) {
        if (!this.ignoreCase && optExactInfo.ignoreCase) {
            if (this.length >= optExactInfo.length) {
                return;
            } else {
                this.ignoreCase = true;
            }
        }
        int i = 0;
        int i2 = 0 + optExactInfo.length;
        int i3 = this.length;
        while (i < i2 && i3 + 1 <= 24) {
            int i4 = i3;
            i3++;
            int i5 = i;
            i++;
            this.chars[i4] = optExactInfo.chars[i5];
        }
        this.length = i3;
        this.reachEnd = i == i2 ? optExactInfo.reachEnd : false;
        OptAnchorInfo optAnchorInfo = new OptAnchorInfo();
        optAnchorInfo.concat(this.anchor, optExactInfo.anchor, 1, 1);
        if (!optExactInfo.reachEnd) {
            optAnchorInfo.rightAnchor = 0;
        }
        this.anchor.copy(optAnchorInfo);
    }

    void concatStr(char[] cArr, int i, int i2, boolean z) {
        int i3 = i;
        int i4 = this.length;
        while (i3 < i2 && i4 < 24 && i4 + 1 <= 24) {
            int i5 = i4;
            i4++;
            int i6 = i3;
            i3++;
            this.chars[i5] = cArr[i6];
        }
        this.length = i4;
    }

    void altMerge(OptExactInfo optExactInfo, OptEnvironment optEnvironment) {
        if (optExactInfo.length == 0 || this.length == 0) {
            clear();
            return;
        }
        if (!this.mmd.equal(optExactInfo.mmd)) {
            clear();
            return;
        }
        int i = 0;
        while (i < this.length && i < optExactInfo.length && this.chars[i] == optExactInfo.chars[i]) {
            i++;
        }
        if (!optExactInfo.reachEnd || i < optExactInfo.length || i < this.length) {
            this.reachEnd = false;
        }
        this.length = i;
        this.ignoreCase |= optExactInfo.ignoreCase;
        this.anchor.altMerge(optExactInfo.anchor);
        if (!this.reachEnd) {
            this.anchor.rightAnchor = 0;
        }
    }

    void select(OptExactInfo optExactInfo) {
        int iPositionValue = this.length;
        int iPositionValue2 = optExactInfo.length;
        if (iPositionValue2 == 0) {
            return;
        }
        if (iPositionValue == 0) {
            copy(optExactInfo);
            return;
        }
        if (iPositionValue <= 2 && iPositionValue2 <= 2) {
            iPositionValue2 = OptMapInfo.positionValue(this.chars[0] & '\u00ff');
            iPositionValue = OptMapInfo.positionValue(optExactInfo.chars[0] & '\u00ff');
            if (this.length > 1) {
                iPositionValue += 5;
            }
            if (optExactInfo.length > 1) {
                iPositionValue2 += 5;
            }
        }
        if (!this.ignoreCase) {
            iPositionValue *= 2;
        }
        if (!optExactInfo.ignoreCase) {
            iPositionValue2 *= 2;
        }
        if (this.mmd.compareDistanceValue(optExactInfo.mmd, iPositionValue, iPositionValue2) > 0) {
            copy(optExactInfo);
        }
    }

    int compare(OptMapInfo optMapInfo) {
        if (optMapInfo.value <= 0) {
            return -1;
        }
        return this.mmd.compareDistanceValue(optMapInfo.mmd, 20 * this.length * (this.ignoreCase ? 1 : 2), SharedScopeCall.FAST_SCOPE_GET_THRESHOLD / optMapInfo.value);
    }
}

package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/OptMapInfo.class */
final class OptMapInfo {
    int value;

    /* renamed from: z */
    private static final int f67z = 32768;
    static final short[] ByteValTable = {5, 1, 1, 1, 1, 1, 1, 1, 1, 10, 10, 1, 1, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 4, 7, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 6, 5, 5, 5, 5, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 1};
    final MinMaxLen mmd = new MinMaxLen();
    final OptAnchorInfo anchor = new OptAnchorInfo();
    final byte[] map = new byte[256];

    OptMapInfo() {
    }

    void clear() {
        this.mmd.clear();
        this.anchor.clear();
        this.value = 0;
        for (int i = 0; i < this.map.length; i++) {
            this.map[i] = 0;
        }
    }

    void copy(OptMapInfo optMapInfo) {
        this.mmd.copy(optMapInfo.mmd);
        this.anchor.copy(optMapInfo.anchor);
        this.value = optMapInfo.value;
        System.arraycopy(optMapInfo.map, 0, this.map, 0, optMapInfo.map.length);
    }

    void addChar(int i) {
        int i2 = i & 255;
        if (this.map[i2] == 0) {
            this.map[i2] = 1;
            this.value += positionValue(i2);
        }
    }

    void addCharAmb(char[] cArr, int i, int i2, int i3) {
        addChar(cArr[i]);
        for (char c : EncodingHelper.caseFoldCodesByString(i3 & (-1073741825), cArr[i])) {
            addChar(c);
        }
    }

    void select(OptMapInfo optMapInfo) {
        if (optMapInfo.value == 0) {
            return;
        }
        if (this.value == 0) {
            copy(optMapInfo);
            return;
        }
        if (this.mmd.compareDistanceValue(optMapInfo.mmd, 32768 / this.value, 32768 / optMapInfo.value) > 0) {
            copy(optMapInfo);
        }
    }

    void altMerge(OptMapInfo optMapInfo) {
        if (this.value == 0) {
            return;
        }
        if (optMapInfo.value == 0 || this.mmd.max < optMapInfo.mmd.max) {
            clear();
            return;
        }
        this.mmd.altMerge(optMapInfo.mmd);
        int iPositionValue = 0;
        for (int i = 0; i < 256; i++) {
            if (optMapInfo.map[i] != 0) {
                this.map[i] = 1;
            }
            if (this.map[i] != 0) {
                iPositionValue += positionValue(i);
            }
        }
        this.value = iPositionValue;
        this.anchor.altMerge(optMapInfo.anchor);
    }

    static int positionValue(int i) {
        if (i < ByteValTable.length) {
            return ByteValTable[i];
        }
        return 4;
    }
}

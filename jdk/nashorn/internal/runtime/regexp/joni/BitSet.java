package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/BitSet.class */
public final class BitSet {
    static final int BITS_PER_BYTE = 8;
    public static final int SINGLE_BYTE_SIZE = 256;
    private static final int BITS_IN_ROOM = 32;
    static final int BITSET_SIZE = 8;
    static final int ROOM_SHIFT;
    final int[] bits = new int[8];
    private static final int BITS_TO_STRING_WRAP = 4;

    static {
        int i = 0;
        int i2 = 32;
        while (true) {
            int i3 = i2 >>> 1;
            i2 = i3;
            if (i3 != 0) {
                i++;
            } else {
                ROOM_SHIFT = i;
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BitSet");
        for (int i = 0; i < 256; i++) {
            if (i % 64 == 0) {
                sb.append("\n  ");
            }
            sb.append(m20at(i) ? "1" : "0");
        }
        return sb.toString();
    }

    /* renamed from: at */
    public boolean m20at(int i) {
        return (this.bits[i >>> ROOM_SHIFT] & (1 << (i % 256))) != 0;
    }

    public void set(int i) {
        int[] iArr = this.bits;
        int i2 = i >>> ROOM_SHIFT;
        iArr[i2] = iArr[i2] | (1 << (i % 256));
    }

    public void clear(int i) {
        int[] iArr = this.bits;
        int i2 = i >>> ROOM_SHIFT;
        iArr[i2] = iArr[i2] & ((1 << (i % 256)) ^ (-1));
    }

    public void clear() {
        for (int i = 0; i < 8; i++) {
            this.bits[i] = 0;
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < 8; i++) {
            if (this.bits[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public void setRange(int i, int i2) {
        for (int i3 = i; i3 <= i2 && i3 < 256; i3++) {
            set(i3);
        }
    }

    public void invert() {
        for (int i = 0; i < 8; i++) {
            this.bits[i] = this.bits[i] ^ (-1);
        }
    }

    public void invertTo(BitSet bitSet) {
        for (int i = 0; i < 8; i++) {
            bitSet.bits[i] = this.bits[i] ^ (-1);
        }
    }

    public void and(BitSet bitSet) {
        for (int i = 0; i < 8; i++) {
            int[] iArr = this.bits;
            int i2 = i;
            iArr[i2] = iArr[i2] & bitSet.bits[i];
        }
    }

    /* renamed from: or */
    public void m21or(BitSet bitSet) {
        for (int i = 0; i < 8; i++) {
            int[] iArr = this.bits;
            int i2 = i;
            iArr[i2] = iArr[i2] | bitSet.bits[i];
        }
    }

    public void copy(BitSet bitSet) {
        for (int i = 0; i < 8; i++) {
            this.bits[i] = bitSet.bits[i];
        }
    }

    public int numOn() {
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            if (m20at(i2)) {
                i++;
            }
        }
        return i;
    }
}

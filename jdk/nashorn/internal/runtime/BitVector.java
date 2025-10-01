package jdk.nashorn.internal.runtime;

import java.util.Arrays;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/BitVector.class */
public final class BitVector implements Cloneable {
    private static final int BITSPERSLOT = 64;
    private static final int SLOTSQUANTA = 4;
    private static final int BITSHIFT = 6;
    private static final int BITMASK = 63;
    private long[] bits;

    public BitVector() {
        this.bits = new long[4];
    }

    public BitVector(long j) {
        this.bits = new long[(int) growthNeeded(j)];
    }

    public BitVector(long[] jArr) {
        this.bits = (long[]) jArr.clone();
    }

    public void copy(BitVector bitVector) {
        this.bits = (long[]) bitVector.bits.clone();
    }

    private static long growthNeeded(long j) {
        return (((((j + 63) >> 6) + 4) - 1) / 4) * 4;
    }

    private long slot(int i) {
        if (0 > i || i >= this.bits.length) {
            return 0L;
        }
        return this.bits[i];
    }

    public void resize(long j) {
        int iGrowthNeeded = (int) growthNeeded(j);
        if (this.bits.length != iGrowthNeeded) {
            this.bits = Arrays.copyOf(this.bits, iGrowthNeeded);
        }
        int i = (int) (j & 63);
        int i2 = (int) (j >> 6);
        if (i != 0) {
            long[] jArr = this.bits;
            jArr[i2] = jArr[i2] & ((1 << i) - 1);
            i2++;
        }
        while (i2 < this.bits.length) {
            this.bits[i2] = 0;
            i2++;
        }
    }

    public void set(long j) {
        long[] jArr = this.bits;
        int i = (int) (j >> 6);
        jArr[i] = jArr[i] | (1 << ((int) (j & 63)));
    }

    public void clear(long j) {
        long[] jArr = this.bits;
        int i = (int) (j >> 6);
        jArr[i] = jArr[i] & ((1 << ((int) (j & 63))) ^ (-1));
    }

    public void toggle(long j) {
        long[] jArr = this.bits;
        int i = (int) (j >> 6);
        jArr[i] = jArr[i] ^ (1 << ((int) (j & 63)));
    }

    public void setTo(long j) {
        if (0 < j) {
            int i = (int) (j >> 6);
            long j2 = (1 << ((int) (j & 63))) - 1;
            Arrays.fill(this.bits, 0, i, -1L);
            if (j2 != 0) {
                long[] jArr = this.bits;
                jArr[i] = jArr[i] | j2;
            }
        }
    }

    public void clearAll() {
        Arrays.fill(this.bits, 0L);
    }

    public boolean isSet(long j) {
        return (this.bits[(int) (j >> 6)] & (1 << ((int) (j & 63)))) != 0;
    }

    public boolean isClear(long j) {
        return (this.bits[(int) (j >> 6)] & (1 << ((int) (j & 63)))) == 0;
    }

    public void shiftLeft(long j, long j2) {
        if (j != 0) {
            int i = (int) (j & 63);
            int i2 = 64 - i;
            int i3 = (int) (j >> 6);
            int length = this.bits.length - i3;
            if (i == 0) {
                int i4 = 0;
                int i5 = i3;
                while (i4 < length) {
                    this.bits[i4] = slot(i5);
                    i4++;
                    i5++;
                }
            } else {
                int i6 = i3;
                for (int i7 = 0; i7 < length; i7++) {
                    long jSlot = slot(i6) >>> i;
                    i6++;
                    this.bits[i7] = jSlot | (slot(i6) << i2);
                }
            }
        }
        resize(j2);
    }

    public void shiftRight(long j, long j2) {
        resize(j2);
        if (j != 0) {
            int i = (int) (j & 63);
            int i2 = 64 - i;
            int i3 = (int) (j >> 6);
            if (i2 == 0) {
                int length = this.bits.length;
                int i4 = length - i3;
                while (length >= i3) {
                    length--;
                    i4--;
                    this.bits[length] = slot(i4);
                }
            } else {
                int length2 = this.bits.length;
                int i5 = length2 - i3;
                while (length2 > 0) {
                    length2--;
                    i5--;
                    this.bits[length2] = (slot(i5 - 1) >>> i2) | (slot(i5) << i);
                }
            }
        }
        resize(j2);
    }

    public void setRange(long j, long j2) {
        if (j < j2) {
            int i = (int) (j >> 6);
            int i2 = (int) ((j2 - 1) >> 6);
            long j3 = (-1) << ((int) j);
            long j4 = (-1) >>> ((int) (-j2));
            if (i == i2) {
                long[] jArr = this.bits;
                jArr[i] = jArr[i] | (j3 & j4);
                return;
            }
            long[] jArr2 = this.bits;
            jArr2[i] = jArr2[i] | j3;
            Arrays.fill(this.bits, i + 1, i2, -1L);
            long[] jArr3 = this.bits;
            jArr3[i2] = jArr3[i2] | j4;
        }
    }
}

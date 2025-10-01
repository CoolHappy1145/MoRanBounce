package kotlin.random;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\r\b\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005B7\b\ufffd\ufffd\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0010"}, m27d2 = {"Lkotlin/random/XorWowRandom;", "Lkotlin/random/Random;", "seed1", "", "seed2", "(II)V", "x", "y", "z", "w", "v", "addend", "(IIIIII)V", "nextBits", "bitCount", "nextInt", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/random/XorWowRandom.class */
public final class XorWowRandom extends Random {

    /* renamed from: x */
    private int f96x;

    /* renamed from: y */
    private int f97y;

    /* renamed from: z */
    private int f98z;

    /* renamed from: w */
    private int f99w;

    /* renamed from: v */
    private int f100v;
    private int addend;

    public XorWowRandom(int i, int i2, int i3, int i4, int i5, int i6) {
        this.f96x = i;
        this.f97y = i2;
        this.f98z = i3;
        this.f99w = i4;
        this.f100v = i5;
        this.addend = i6;
        if (!(((((this.f96x | this.f97y) | this.f98z) | this.f99w) | this.f100v) != 0)) {
            throw new IllegalArgumentException("Initial state must have at least one non-zero element.".toString());
        }
        for (int i7 = 0; i7 < 64; i7++) {
            nextInt();
        }
    }

    public XorWowRandom(int i, int i2) {
        this(i, i2, 0, 0, i ^ (-1), (i << 10) ^ (i2 >>> 4));
    }

    @Override // kotlin.random.Random
    public int nextInt() {
        int i = this.f96x;
        int i2 = i ^ (i >>> 2);
        this.f96x = this.f97y;
        this.f97y = this.f98z;
        this.f98z = this.f99w;
        int i3 = this.f100v;
        this.f99w = i3;
        int i4 = ((i2 ^ (i2 << 1)) ^ i3) ^ (i3 << 4);
        this.f100v = i4;
        this.addend += 362437;
        return i4 + this.addend;
    }

    @Override // kotlin.random.Random
    public int nextBits(int i) {
        return (nextInt() >>> (32 - i)) & ((-i) >> 31);
    }
}

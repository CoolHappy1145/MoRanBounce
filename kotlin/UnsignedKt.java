package kotlin;

import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd0\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\ufffd\ufffd\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, m27d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"})
@JvmName(name = "UnsignedKt")
/* loaded from: L-out.jar:kotlin/UnsignedKt.class */
public final class UnsignedKt {
    @PublishedApi
    public static final int uintCompare(int i, int i2) {
        int i3 = i ^ Integer.MIN_VALUE;
        int i4 = i2 ^ Integer.MIN_VALUE;
        if (i3 < i4) {
            return -1;
        }
        return i3 == i4 ? 0 : 1;
    }

    @PublishedApi
    /* renamed from: uintDivide-J1ME1BU, reason: not valid java name */
    public static final int m765uintDivideJ1ME1BU(int i, int i2) {
        return (int) ((i & JSType.MAX_UINT) / (i2 & JSType.MAX_UINT));
    }

    @PublishedApi
    /* renamed from: uintRemainder-J1ME1BU, reason: not valid java name */
    public static final int m766uintRemainderJ1ME1BU(int i, int i2) {
        return (int) ((i & JSType.MAX_UINT) % (i2 & JSType.MAX_UINT));
    }

    @PublishedApi
    /* renamed from: ulongDivide-eb3DHEI, reason: not valid java name */
    public static final long m767ulongDivideeb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return (j ^ Long.MIN_VALUE) < (j2 ^ Long.MIN_VALUE) ? 0L : 1L;
        }
        if (j >= 0) {
            return j / j2;
        }
        long j3 = ((j >>> 1) / j2) << 1;
        return j3 + (((j - (j3 * j2)) ^ Long.MIN_VALUE) >= (j2 ^ Long.MIN_VALUE) ? 1 : 0);
    }

    @PublishedApi
    /* renamed from: ulongRemainder-eb3DHEI, reason: not valid java name */
    public static final long m768ulongRemaindereb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return (j ^ Long.MIN_VALUE) < (j2 ^ Long.MIN_VALUE) ? j : j - j2;
        }
        if (j >= 0) {
            return j % j2;
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        return j3 - ((j3 ^ Long.MIN_VALUE) >= (j2 ^ Long.MIN_VALUE) ? j2 : 0L);
    }

    @PublishedApi
    public static final int doubleToUInt(double d) {
        if (Double.isNaN(d) || d <= (0 & Integer.MAX_VALUE) + (((0 >>> 31) << 30) * 2.0d)) {
            return 0;
        }
        if (d >= ((-1) & Integer.MAX_VALUE) + ((((-1) >>> 31) << 30) * 2.0d)) {
            return -1;
        }
        return d <= 2.147483647E9d ? (int) d : ((int) (d - 2.147483647E9d)) + Integer.MAX_VALUE;
    }

    @PublishedApi
    public static final long doubleToULong(double d) {
        if (Double.isNaN(d) || d <= ((0 >>> 11) * 2048.0d) + (0 & 2047)) {
            return 0L;
        }
        if (d >= (((-1) >>> 11) * 2048.0d) + ((-1) & 2047)) {
            return -1L;
        }
        return d < 9.223372036854776E18d ? (long) d : ((long) (d - 9.223372036854776E18d)) - Long.MIN_VALUE;
    }

    @NotNull
    public static final String ulongToString(long j) {
        return ulongToString(j, 10);
    }

    @NotNull
    public static final String ulongToString(long j, int i) {
        if (j >= 0) {
            String string = Long.toString(j, CharsKt.checkRadix(i));
            Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Long.toString(this, checkRadix(radix))");
            return string;
        }
        long j2 = ((j >>> 1) / i) << 1;
        long j3 = j - (j2 * i);
        if (j3 >= i) {
            j3 -= i;
            j2++;
        }
        StringBuilder sb = new StringBuilder();
        String string2 = Long.toString(j2, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Long.toString(this, checkRadix(radix))");
        StringBuilder sbAppend = sb.append(string2);
        String string3 = Long.toString(j3, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(string3, "java.lang.Long.toString(this, checkRadix(radix))");
        return sbAppend.append(string3).toString();
    }
}

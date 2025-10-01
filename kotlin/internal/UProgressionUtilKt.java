package kotlin.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UnsignedKt;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd \n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, m27d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", AsmConstants.END, "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/internal/UProgressionUtilKt.class */
public final class UProgressionUtilKt {
    /* renamed from: differenceModulo-WZ9TVnA, reason: not valid java name */
    private static final int m1347differenceModuloWZ9TVnA(int i, int i2, int i3) {
        int iM766uintRemainderJ1ME1BU = UnsignedKt.m766uintRemainderJ1ME1BU(i, i3);
        int iM766uintRemainderJ1ME1BU2 = UnsignedKt.m766uintRemainderJ1ME1BU(i2, i3);
        return UnsignedKt.uintCompare(iM766uintRemainderJ1ME1BU, iM766uintRemainderJ1ME1BU2) >= 0 ? iM766uintRemainderJ1ME1BU - iM766uintRemainderJ1ME1BU2 : (iM766uintRemainderJ1ME1BU - iM766uintRemainderJ1ME1BU2) + i3;
    }

    /* renamed from: differenceModulo-sambcqE, reason: not valid java name */
    private static final long m1348differenceModulosambcqE(long j, long j2, long j3) {
        long jM768ulongRemaindereb3DHEI = UnsignedKt.m768ulongRemaindereb3DHEI(j, j3);
        long jM768ulongRemaindereb3DHEI2 = UnsignedKt.m768ulongRemaindereb3DHEI(j2, j3);
        return (jM768ulongRemaindereb3DHEI ^ Long.MIN_VALUE) >= (jM768ulongRemaindereb3DHEI2 ^ Long.MIN_VALUE) ? jM768ulongRemaindereb3DHEI - jM768ulongRemaindereb3DHEI2 : (jM768ulongRemaindereb3DHEI - jM768ulongRemaindereb3DHEI2) + j3;
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-Nkh28Cs, reason: not valid java name */
    public static final int m1349getProgressionLastElementNkh28Cs(int i, int i2, int i3) {
        if (i3 > 0) {
            return UnsignedKt.uintCompare(i, i2) >= 0 ? i2 : i2 - m1347differenceModuloWZ9TVnA(i2, i, i3);
        }
        if (i3 < 0) {
            return UnsignedKt.uintCompare(i, i2) <= 0 ? i2 : i2 + m1347differenceModuloWZ9TVnA(i, i2, -i3);
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-7ftBX0g, reason: not valid java name */
    public static final long m1350getProgressionLastElement7ftBX0g(long j, long j2, long j3) {
        if (j3 > 0) {
            return (j ^ Long.MIN_VALUE) >= (j2 ^ Long.MIN_VALUE) ? j2 : j2 - m1348differenceModulosambcqE(j2, j, j3);
        }
        if (j3 < 0) {
            return (j ^ Long.MIN_VALUE) <= (j2 ^ Long.MIN_VALUE) ? j2 : j2 + m1348differenceModulosambcqE(j, j2, -j3);
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}

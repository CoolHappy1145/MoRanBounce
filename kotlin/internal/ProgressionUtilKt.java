package kotlin.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\u001a \u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u001a \u0010\ufffd\ufffd\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a \u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0001\u001a\u0018\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0002\u00a8\u0006\u000b"}, m27d2 = {"differenceModulo", "", "a", "b", "c", "", "getProgressionLastElement", "start", AsmConstants.END, "step", "mod", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/internal/ProgressionUtilKt.class */
public final class ProgressionUtilKt {
    private static final int differenceModulo(int i, int i2, int i3) {
        int i4 = i % i3;
        int i5 = i2 % i3;
        int i6 = ((i4 >= 0 ? i4 : i4 + i3) - (i5 >= 0 ? i5 : i5 + i3)) % i3;
        return i6 >= 0 ? i6 : i6 + i3;
    }

    private static final long differenceModulo(long j, long j2, long j3) {
        long j4 = j % j3;
        long j5 = j2 % j3;
        long j6 = ((j4 >= 0 ? j4 : j4 + j3) - (j5 >= 0 ? j5 : j5 + j3)) % j3;
        return j6 >= 0 ? j6 : j6 + j3;
    }

    @PublishedApi
    public static final int getProgressionLastElement(int i, int i2, int i3) {
        if (i3 > 0) {
            return i >= i2 ? i2 : i2 - differenceModulo(i2, i, i3);
        }
        if (i3 < 0) {
            return i <= i2 ? i2 : i2 + differenceModulo(i, i2, -i3);
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    @PublishedApi
    public static final long getProgressionLastElement(long j, long j2, long j3) {
        if (j3 > 0) {
            return j >= j2 ? j2 : j2 - differenceModulo(j2, j, j3);
        }
        if (j3 < 0) {
            return j <= j2 ? j2 : j2 + differenceModulo(j, j2, -j3);
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}

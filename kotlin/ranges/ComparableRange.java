package kotlin.ranges;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0012\u0018\ufffd\ufffd*\u000e\b\ufffd\ufffd\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0016\u0010\u0005\u001a\u00028\ufffd\ufffdX\u0096\u0004\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00028\ufffd\ufffdX\u0096\u0004\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0013"}, m27d2 = {"Lkotlin/ranges/ComparableRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "start", "endInclusive", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)V", "getEndInclusive", "()Ljava/lang/Comparable;", "Ljava/lang/Comparable;", "getStart", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ranges/ComparableRange.class */
class ComparableRange implements ClosedRange {

    @NotNull
    private final Comparable start;

    @NotNull
    private final Comparable endInclusive;

    public ComparableRange(@NotNull Comparable start, @NotNull Comparable endInclusive) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(endInclusive, "endInclusive");
        this.start = start;
        this.endInclusive = endInclusive;
    }

    @Override // kotlin.ranges.ClosedRange
    public boolean contains(@NotNull Comparable value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return ClosedRange.DefaultImpls.contains(this, value);
    }

    @Override // kotlin.ranges.ClosedRange
    public boolean isEmpty() {
        return ClosedRange.DefaultImpls.isEmpty(this);
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public Comparable getStart() {
        return this.start;
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public Comparable getEndInclusive() {
        return this.endInclusive;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ComparableRange) && ((isEmpty() && ((ComparableRange) obj).isEmpty()) || (Intrinsics.areEqual(getStart(), ((ComparableRange) obj).getStart()) && Intrinsics.areEqual(getEndInclusive(), ((ComparableRange) obj).getEndInclusive())));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * getStart().hashCode()) + getEndInclusive().hashCode();
    }

    @NotNull
    public String toString() {
        return getStart() + ".." + getEndInclusive();
    }
}

package kotlin.ranges;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0096\u0002J\u0013\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0019"}, m27d2 = {"Lkotlin/ranges/ClosedDoubleRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "start", "endInclusive", "(DD)V", "_endInclusive", "_start", "getEndInclusive", "()Ljava/lang/Double;", "getStart", "contains", "", PropertyDescriptor.VALUE, "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ranges/ClosedDoubleRange.class */
final class ClosedDoubleRange implements ClosedFloatingPointRange {
    private final double _start;
    private final double _endInclusive;

    @Override // kotlin.ranges.ClosedRange
    public Comparable getStart() {
        return getStart();
    }

    @Override // kotlin.ranges.ClosedRange
    public Comparable getEndInclusive() {
        return getEndInclusive();
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Failed to calculate best type for var: r10v0 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r12v0 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Not initialized variable reg: 10, insn: 0x0012: MOVE (r1 I:??[long, double]) = (r10 I:??[long, double]) (LINE:63), block:B:2:0x0000 */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x0014: MOVE (r2 I:??[long, double]) = (r12 I:??[long, double]), block:B:2:0x0000 */
    @Override // kotlin.ranges.ClosedFloatingPointRange
    public boolean lessThanOrEquals(java.lang.Comparable r7, java.lang.Comparable r8) {
        /*
            r6 = this;
            r0 = r6
            r1 = r7
            java.lang.Number r1 = (java.lang.Number) r1
            double r1 = r1.doubleValue()
            r2 = r8
            java.lang.Number r2 = (java.lang.Number) r2
            double r2 = r2.doubleValue()
            r11 = r2
            r9 = r1
            r1 = r10
            r2 = r12
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L1e
            r1 = 1
            goto L1f
        L1e:
            r1 = 0
        L1f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.ranges.ClosedDoubleRange.lessThanOrEquals(java.lang.Comparable, java.lang.Comparable):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.ranges.ClosedFloatingPointRange, kotlin.ranges.ClosedRange
    public boolean contains(Comparable comparable) {
        return contains(((Number) comparable).doubleValue());
    }

    public ClosedDoubleRange(double d, double d2) {
        this._start = d;
        this._endInclusive = d2;
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public Double getStart() {
        return Double.valueOf(this._start);
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public Double getEndInclusive() {
        return Double.valueOf(this._endInclusive);
    }

    public boolean contains(double d) {
        return d >= this._start && d <= this._endInclusive;
    }

    @Override // kotlin.ranges.ClosedFloatingPointRange, kotlin.ranges.ClosedRange
    public boolean isEmpty() {
        return this._start > this._endInclusive;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ClosedDoubleRange) && ((isEmpty() && ((ClosedDoubleRange) obj).isEmpty()) || (this._start == ((ClosedDoubleRange) obj)._start && this._endInclusive == ((ClosedDoubleRange) obj)._endInclusive));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * Double.valueOf(this._start).hashCode()) + Double.valueOf(this._endInclusive).hashCode();
    }

    @NotNull
    public String toString() {
        return this._start + ".." + this._endInclusive;
    }
}

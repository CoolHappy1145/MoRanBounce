package kotlin.ranges;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0096\u0002J\u0013\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0019"}, m27d2 = {"Lkotlin/ranges/ClosedFloatRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "start", "endInclusive", "(FF)V", "_endInclusive", "_start", "getEndInclusive", "()Ljava/lang/Float;", "getStart", "contains", "", PropertyDescriptor.VALUE, "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ranges/ClosedFloatRange.class */
final class ClosedFloatRange implements ClosedFloatingPointRange {
    private final float _start;
    private final float _endInclusive;

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
    /* JADX WARN: Failed to calculate best type for var: r9v0 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0014: MOVE (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r9 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:2:0x0000 */
    @Override // kotlin.ranges.ClosedFloatingPointRange
    public boolean lessThanOrEquals(java.lang.Comparable r5, java.lang.Comparable r6) {
        /*
            r4 = this;
            r0 = r4
            r1 = r5
            java.lang.Number r1 = (java.lang.Number) r1
            float r1 = r1.floatValue()
            r2 = r6
            java.lang.Number r2 = (java.lang.Number) r2
            float r2 = r2.floatValue()
            r8 = r2
            r7 = r1
            r1 = r8
            r2 = r9
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L1e
            r1 = 1
            goto L1f
        L1e:
            r1 = 0
        L1f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.ranges.ClosedFloatRange.lessThanOrEquals(java.lang.Comparable, java.lang.Comparable):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.ranges.ClosedFloatingPointRange, kotlin.ranges.ClosedRange
    public boolean contains(Comparable comparable) {
        return contains(((Number) comparable).floatValue());
    }

    public ClosedFloatRange(float f, float f2) {
        this._start = f;
        this._endInclusive = f2;
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public Float getStart() {
        return Float.valueOf(this._start);
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public Float getEndInclusive() {
        return Float.valueOf(this._endInclusive);
    }

    public boolean contains(float f) {
        return f >= this._start && f <= this._endInclusive;
    }

    @Override // kotlin.ranges.ClosedFloatingPointRange, kotlin.ranges.ClosedRange
    public boolean isEmpty() {
        return this._start > this._endInclusive;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ClosedFloatRange) && ((isEmpty() && ((ClosedFloatRange) obj).isEmpty()) || (this._start == ((ClosedFloatRange) obj)._start && this._endInclusive == ((ClosedFloatRange) obj)._endInclusive));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * Float.valueOf(this._start).hashCode()) + Float.valueOf(this._endInclusive).hashCode();
    }

    @NotNull
    public String toString() {
        return this._start + ".." + this._endInclusive;
    }
}

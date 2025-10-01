package kotlin.time;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0018\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\u0004H\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0004H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, m27d2 = {"Lkotlin/time/AdjustedClockMark;", "Lkotlin/time/ClockMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/ClockMark;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment", "()D", "D", "getMark", "()Lkotlin/time/ClockMark;", "elapsedNow", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/ClockMark;", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/AdjustedClockMark.class */
final class AdjustedClockMark extends ClockMark {

    @NotNull
    private final ClockMark mark;
    private final double adjustment;

    @NotNull
    public final ClockMark getMark() {
        return this.mark;
    }

    public final double getAdjustment() {
        return this.adjustment;
    }

    private AdjustedClockMark(ClockMark clockMark, double d) {
        this.mark = clockMark;
        this.adjustment = d;
    }

    public AdjustedClockMark(ClockMark clockMark, double d, DefaultConstructorMarker defaultConstructorMarker) {
        this(clockMark, d);
    }

    @Override // kotlin.time.ClockMark
    public double elapsedNow() {
        return Duration.m1485minusLRDsOJo(this.mark.elapsedNow(), this.adjustment);
    }

    @Override // kotlin.time.ClockMark
    @NotNull
    /* renamed from: plus-LRDsOJo */
    public ClockMark mo1480plusLRDsOJo(double d) {
        return new AdjustedClockMark(this.mark, Duration.m1484plusLRDsOJo(this.adjustment, d), null);
    }
}

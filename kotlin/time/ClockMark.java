package kotlin.time;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b'\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u0004H&\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u0006\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\u0007J\u001b\u0010\t\u001a\u00020\ufffd\ufffd2\u0006\u0010\n\u001a\u00020\u0004H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\r\u001a\u00020\ufffd\ufffd2\u0006\u0010\n\u001a\u00020\u0004H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000e\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/time/ClockMark;", "", "()V", "elapsedNow", "Lkotlin/time/Duration;", "()D", "hasNotPassedNow", "", "hasPassedNow", "minus", "duration", "minus-LRDsOJo", "(D)Lkotlin/time/ClockMark;", "plus", "plus-LRDsOJo", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/ClockMark.class */
public abstract class ClockMark {
    public abstract double elapsedNow();

    @NotNull
    /* renamed from: plus-LRDsOJo */
    public ClockMark mo1480plusLRDsOJo(double d) {
        return new AdjustedClockMark(this, d, null);
    }

    @NotNull
    /* renamed from: minus-LRDsOJo, reason: not valid java name */
    public ClockMark m1481minusLRDsOJo(double d) {
        return mo1480plusLRDsOJo(Duration.m1483unaryMinusimpl(d));
    }

    public final boolean hasPassedNow() {
        return !((elapsedNow() > 0.0d ? 1 : (elapsedNow() == 0.0d ? 0 : -1)) < 0);
    }

    public final boolean hasNotPassedNow() {
        return elapsedNow() < 0.0d;
    }
}

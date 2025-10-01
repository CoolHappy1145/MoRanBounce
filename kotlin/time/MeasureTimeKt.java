package kotlin.time;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\"\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a,\u0010\ufffd\ufffd\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0005\u001a0\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a0\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\n\u001a4\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\b*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000b"}, m27d2 = {"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)D", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/Clock;", "(Lkotlin/time/Clock;Lkotlin/jvm/functions/Function0;)D", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/time/MeasureTimeKt.class */
public final class MeasureTimeKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final double measureTime(@NotNull Function0 block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        ClockMark clockMarkMarkNow = MonoClock.INSTANCE.markNow();
        block.invoke();
        return clockMarkMarkNow.elapsedNow();
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final double measureTime(@NotNull Clock measureTime, @NotNull Function0 block) {
        Intrinsics.checkParameterIsNotNull(measureTime, "$this$measureTime");
        Intrinsics.checkParameterIsNotNull(block, "block");
        ClockMark clockMarkMarkNow = measureTime.markNow();
        block.invoke();
        return clockMarkMarkNow.elapsedNow();
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    @NotNull
    public static final TimedValue measureTimedValue(@NotNull Function0 block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        return new TimedValue(block.invoke(), MonoClock.INSTANCE.markNow().elapsedNow(), null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    @NotNull
    public static final TimedValue measureTimedValue(@NotNull Clock measureTimedValue, @NotNull Function0 block) {
        Intrinsics.checkParameterIsNotNull(measureTimedValue, "$this$measureTimedValue");
        Intrinsics.checkParameterIsNotNull(block, "block");
        return new TimedValue(block.invoke(), measureTimedValue.markNow().elapsedNow(), null);
    }
}

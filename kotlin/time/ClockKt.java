package kotlin.time;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0016\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\n\u001a\u001d\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, m27d2 = {"compareTo", "", "Lkotlin/time/ClockMark;", "other", "minus", "Lkotlin/time/Duration;", "(Lkotlin/time/ClockMark;Lkotlin/time/ClockMark;)D", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/time/ClockKt.class */
public final class ClockKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    @Deprecated(message = "Subtracting one ClockMark from another is not a well defined operation because these clock marks could have been obtained from the different clocks.", level = DeprecationLevel.ERROR)
    @ExperimentalTime
    private static final double minus(@NotNull ClockMark minus, ClockMark clockMark) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        throw new Error("Operation is disallowed.");
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    @Deprecated(message = "Comparing one ClockMark to another is not a well defined operation because these clock marks could have been obtained from the different clocks.", level = DeprecationLevel.ERROR)
    @ExperimentalTime
    private static final int compareTo(@NotNull ClockMark compareTo, ClockMark clockMark) {
        Intrinsics.checkParameterIsNotNull(compareTo, "$this$compareTo");
        throw new Error("Operation is disallowed.");
    }
}

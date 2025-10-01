package kotlin.time;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0010\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\bg\u0018\ufffd\ufffd2\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, m27d2 = {"Lkotlin/time/Clock;", "", "markNow", "Lkotlin/time/ClockMark;", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/Clock.class */
public interface Clock {
    @NotNull
    ClockMark markNow();
}

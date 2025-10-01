package kotlin.time;

import java.util.concurrent.TimeUnit;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationUnitKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0010\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u0010\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0001\u00a8\u0006\u0004"}, m27d2 = {"shortName", "", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "kotlin-stdlib"}, m28xs = "kotlin/time/DurationUnitKt")
/* loaded from: L-out.jar:kotlin/time/DurationUnitKt__DurationUnitKt.class */
class DurationUnitKt__DurationUnitKt extends DurationUnitKt__DurationUnitJvmKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    @NotNull
    public static final String shortName(@NotNull TimeUnit shortName) {
        Intrinsics.checkParameterIsNotNull(shortName, "$this$shortName");
        switch (DurationUnitKt.WhenMappings.$EnumSwitchMapping$0[shortName.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return "us";
            case 3:
                return "ms";
            case 4:
                return "s";
            case 5:
                return "m";
            case 6:
                return "h";
            case 7:
                return "d";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}

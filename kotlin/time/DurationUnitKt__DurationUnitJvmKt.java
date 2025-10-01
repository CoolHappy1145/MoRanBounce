package kotlin.time;

import java.util.concurrent.TimeUnit;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd$\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a(\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\n\u0010\u0006\u001a\u00060\u0004j\u0002`\u0005H\u0001*\u001e\b\u0007\u0010\u0007\"\u00020\u00042\u00020\u0004B\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\nB\u0002\b\u000b\u00a8\u0006\f"}, m27d2 = {"convertDurationUnit", "", PropertyDescriptor.VALUE, "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "DurationUnit", "Lkotlin/SinceKotlin;", "version", "1.3", "Lkotlin/time/ExperimentalTime;", "kotlin-stdlib"}, m28xs = "kotlin/time/DurationUnitKt")
/* loaded from: L-out.jar:kotlin/time/DurationUnitKt__DurationUnitJvmKt.class */
class DurationUnitKt__DurationUnitJvmKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final double convertDurationUnit(double d, @NotNull TimeUnit sourceUnit, @NotNull TimeUnit targetUnit) {
        Intrinsics.checkParameterIsNotNull(sourceUnit, "sourceUnit");
        Intrinsics.checkParameterIsNotNull(targetUnit, "targetUnit");
        long jConvert = targetUnit.convert(1L, sourceUnit);
        if (jConvert > 0) {
            return d * jConvert;
        }
        return d / sourceUnit.convert(1L, targetUnit);
    }
}

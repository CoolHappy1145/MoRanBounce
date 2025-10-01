package kotlin.ranges;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u001a\u001b\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\u0002\u00a8\u0006\u0004"}, m27d2 = {"rangeTo", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "that", "kotlin-stdlib"}, m28xs = "kotlin/ranges/RangesKt")
/* loaded from: L-out.jar:kotlin/ranges/RangesKt__RangesJVMKt.class */
class RangesKt__RangesJVMKt {
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final ClosedFloatingPointRange rangeTo(float f, float f2) {
        return new ClosedFloatRange(f, f2);
    }
}

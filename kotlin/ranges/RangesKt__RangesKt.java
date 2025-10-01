package kotlin.ranges;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\ufffd\ufffd\u001a\u0018\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\ufffd\ufffd\u001a@\u0010\u0006\u001a\u00020\u0003\"\b\b\ufffd\ufffd\u0010\u0007*\u00020\b\"\u0018\b\u0001\u0010\t*\b\u0012\u0004\u0012\u0002H\u00070\n*\b\u0012\u0004\u0012\u0002H\u00070\u000b*\u0002H\t2\b\u0010\f\u001a\u0004\u0018\u0001H\u0007H\u0087\n\u00a2\u0006\u0002\u0010\r\u001a0\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00070\u000b\"\u000e\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u000f*\u0002H\u00072\u0006\u0010\u0010\u001a\u0002H\u0007H\u0086\u0002\u00a2\u0006\u0002\u0010\u0011\u001a\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0013H\u0087\u0002\u00a8\u0006\u0014"}, m27d2 = {"checkStepIsPositive", "", "isPositive", "", "step", "", "contains", "T", "", "R", "", "Lkotlin/ranges/ClosedRange;", "element", "(Ljava/lang/Iterable;Ljava/lang/Object;)Z", "rangeTo", "", "that", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Lkotlin/ranges/ClosedRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "kotlin-stdlib"}, m28xs = "kotlin/ranges/RangesKt")
/* loaded from: L-out.jar:kotlin/ranges/RangesKt__RangesKt.class */
class RangesKt__RangesKt extends RangesKt__RangesJVMKt {
    @NotNull
    public static final ClosedRange rangeTo(@NotNull Comparable rangeTo, @NotNull Comparable that) {
        Intrinsics.checkParameterIsNotNull(rangeTo, "$this$rangeTo");
        Intrinsics.checkParameterIsNotNull(that, "that");
        return new ComparableRange(rangeTo, that);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final ClosedFloatingPointRange rangeTo(double d, double d2) {
        return new ClosedDoubleRange(d, d2);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean contains(@NotNull Iterable contains, Object obj) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return obj != null && ((ClosedRange) contains).contains((Comparable) obj);
    }

    public static final void checkStepIsPositive(boolean z, @NotNull Number step) {
        Intrinsics.checkParameterIsNotNull(step, "step");
        if (!z) {
            throw new IllegalArgumentException("Step must be positive, was: " + step + '.');
        }
    }
}

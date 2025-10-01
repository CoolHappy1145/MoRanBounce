package kotlin.ranges;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\b\bg\u0018\ufffd\ufffd*\u000e\b\ufffd\ufffd\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0005H\u0016J\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\ufffd\ufffd2\u0006\u0010\u000b\u001a\u00028\ufffd\ufffdH&\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, m27d2 = {"Lkotlin/ranges/ClosedFloatingPointRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "contains", "", PropertyDescriptor.VALUE, "(Ljava/lang/Comparable;)Z", "isEmpty", "lessThanOrEquals", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Z", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ranges/ClosedFloatingPointRange.class */
public interface ClosedFloatingPointRange extends ClosedRange {
    @Override // kotlin.ranges.ClosedRange
    boolean contains(@NotNull Comparable comparable);

    @Override // kotlin.ranges.ClosedRange
    boolean isEmpty();

    boolean lessThanOrEquals(@NotNull Comparable comparable, @NotNull Comparable comparable2);

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/ranges/ClosedFloatingPointRange$DefaultImpls.class */
    public static final class DefaultImpls {
        public static boolean contains(ClosedFloatingPointRange closedFloatingPointRange, @NotNull Comparable value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return closedFloatingPointRange.lessThanOrEquals(closedFloatingPointRange.getStart(), value) && closedFloatingPointRange.lessThanOrEquals(value, closedFloatingPointRange.getEndInclusive());
        }

        public static boolean isEmpty(ClosedFloatingPointRange closedFloatingPointRange) {
            return !closedFloatingPointRange.lessThanOrEquals(closedFloatingPointRange.getStart(), closedFloatingPointRange.getEndInclusive());
        }
    }
}

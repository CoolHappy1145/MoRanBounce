package kotlin.ranges;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000f\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\ufffd\ufffd*\u000e\b\ufffd\ufffd\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\nH\u0016R\u0012\u0010\u0004\u001a\u00028\ufffd\ufffdX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00028\ufffd\ufffdX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\u000e"}, m27d2 = {"Lkotlin/ranges/ClosedRange;", "T", "", "", "endInclusive", "getEndInclusive", "()Ljava/lang/Comparable;", "start", "getStart", "contains", "", PropertyDescriptor.VALUE, "(Ljava/lang/Comparable;)Z", "isEmpty", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ranges/ClosedRange.class */
public interface ClosedRange {
    @NotNull
    Comparable getStart();

    @NotNull
    Comparable getEndInclusive();

    boolean contains(@NotNull Comparable comparable);

    boolean isEmpty();

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/ranges/ClosedRange$DefaultImpls.class */
    public static final class DefaultImpls {
        public static boolean contains(ClosedRange closedRange, @NotNull Comparable value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return value.compareTo(closedRange.getStart()) >= 0 && value.compareTo(closedRange.getEndInclusive()) <= 0;
        }

        public static boolean isEmpty(ClosedRange closedRange) {
            return closedRange.getStart().compareTo(closedRange.getEndInclusive()) > 0;
        }
    }
}

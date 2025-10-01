package kotlin.comparisons;

import java.util.Comparator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0002\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u0012\u0012\u0004\u0012\u0002H\u00010\u0002j\b\u0012\u0004\u0012\u0002H\u0001`\u0003B\u001d\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002j\b\u0012\u0004\u0012\u00028\ufffd\ufffd`\u0003\u00a2\u0006\u0002\u0010\u0005J\u001d\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\ufffd\ufffd2\u0006\u0010\u000b\u001a\u00028\ufffd\ufffdH\u0016\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002j\b\u0012\u0004\u0012\u00028\ufffd\ufffd`\u0003R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002j\b\u0012\u0004\u0012\u00028\ufffd\ufffd`\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000e"}, m27d2 = {"Lkotlin/comparisons/ReversedComparator;", "T", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "comparator", "(Ljava/util/Comparator;)V", "getComparator", "()Ljava/util/Comparator;", "compare", "", "a", "b", "(Ljava/lang/Object;Ljava/lang/Object;)I", "reversed", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/comparisons/ReversedComparator.class */
final class ReversedComparator implements Comparator {

    @NotNull
    private final Comparator comparator;

    @NotNull
    public final Comparator getComparator() {
        return this.comparator;
    }

    public ReversedComparator(@NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        this.comparator = comparator;
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return this.comparator.compare(obj2, obj);
    }

    @Override // java.util.Comparator
    @NotNull
    public final Comparator reversed() {
        return this.comparator;
    }
}

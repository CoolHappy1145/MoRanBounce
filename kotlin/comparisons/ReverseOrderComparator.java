package kotlin.comparisons;

import java.util.Comparator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c2\u0002\u0018\ufffd\ufffd2\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002`\u0004B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0005J$\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J\"\u0010\n\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002`\u0004\u00a8\u0006\u000b"}, m27d2 = {"Lkotlin/comparisons/ReverseOrderComparator;", "Ljava/util/Comparator;", "", "", "Lkotlin/Comparator;", "()V", "compare", "", "a", "b", "reversed", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/comparisons/ReverseOrderComparator.class */
final class ReverseOrderComparator implements Comparator {
    public static final ReverseOrderComparator INSTANCE = new ReverseOrderComparator();

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return compare((Comparable) obj, (Comparable) obj2);
    }

    private ReverseOrderComparator() {
    }

    public int compare(@NotNull Comparable a, @NotNull Comparable b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        return b.compareTo(a);
    }

    @Override // java.util.Comparator
    @NotNull
    public final Comparator reversed() {
        return NaturalOrderComparator.INSTANCE;
    }
}

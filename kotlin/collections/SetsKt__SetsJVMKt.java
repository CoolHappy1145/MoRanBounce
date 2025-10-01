package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd$\n\ufffd\ufffd\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002\u00a2\u0006\u0002\u0010\u0004\u001a+\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\ufffd\ufffd\u0010\u00022\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\b\"\u0002H\u0002\u00a2\u0006\u0002\u0010\t\u001aG\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\ufffd\ufffd\u0010\u00022\u001a\u0010\n\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u000bj\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\f2\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\b\"\u0002H\u0002\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, m27d2 = {"setOf", "", "T", "element", "(Ljava/lang/Object;)Ljava/util/Set;", "sortedSetOf", "Ljava/util/TreeSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/TreeSet;", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Comparator;[Ljava/lang/Object;)Ljava/util/TreeSet;", "kotlin-stdlib"}, m28xs = "kotlin/collections/SetsKt")
/* loaded from: L-out.jar:kotlin/collections/SetsKt__SetsJVMKt.class */
class SetsKt__SetsJVMKt {
    @NotNull
    public static final Set setOf(Object obj) {
        Set setSingleton = Collections.singleton(obj);
        Intrinsics.checkExpressionValueIsNotNull(setSingleton, "java.util.Collections.singleton(element)");
        return setSingleton;
    }

    @NotNull
    public static final TreeSet sortedSetOf(@NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return (TreeSet) ArraysKt.toCollection(elements, new TreeSet());
    }

    @NotNull
    public static final TreeSet sortedSetOf(@NotNull Comparator comparator, @NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return (TreeSet) ArraysKt.toCollection(elements, new TreeSet(comparator));
    }
}

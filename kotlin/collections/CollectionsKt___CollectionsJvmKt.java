package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd>\n\ufffd\ufffd\n\u0002\u0010 \n\ufffd\ufffd\n\u0002\u0010\u001c\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a(\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001aA\u0010\u0006\u001a\u0002H\u0007\"\u0010\b\ufffd\ufffd\u0010\u0007*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\t\u001a\u0002H\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\n\u001a\u0016\u0010\u000b\u001a\u00020\f\"\u0004\b\ufffd\ufffd\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e\u001a&\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\r0\u0010\"\u000e\b\ufffd\ufffd\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u0011*\b\u0012\u0004\u0012\u0002H\r0\u0003\u001a8\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\r0\u0010\"\u0004\b\ufffd\ufffd\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u00032\u001a\u0010\u0012\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r0\u0013j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r`\u0014\u00a8\u0006\u0015"}, m27d2 = {"filterIsInstance", "", "R", "", "klass", Constants.CLASS_DESC, "filterIsInstanceTo", "C", "", "destination", "(Ljava/lang/Iterable;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "reverse", "", "T", "", "toSortedSet", "Ljava/util/SortedSet;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt___CollectionsJvmKt.class */
class CollectionsKt___CollectionsJvmKt extends CollectionsKt__ReversedViewsKt {
    @NotNull
    public static final List filterIsInstance(@NotNull Iterable filterIsInstance, @NotNull Class klass) {
        Intrinsics.checkParameterIsNotNull(filterIsInstance, "$this$filterIsInstance");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        return (List) CollectionsKt.filterIsInstanceTo(filterIsInstance, new ArrayList(), klass);
    }

    @NotNull
    public static final Collection filterIsInstanceTo(@NotNull Iterable filterIsInstanceTo, @NotNull Collection destination, @NotNull Class klass) {
        Intrinsics.checkParameterIsNotNull(filterIsInstanceTo, "$this$filterIsInstanceTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        for (Object obj : filterIsInstanceTo) {
            if (klass.isInstance(obj)) {
                destination.add(obj);
            }
        }
        return destination;
    }

    public static final void reverse(@NotNull List reverse) {
        Intrinsics.checkParameterIsNotNull(reverse, "$this$reverse");
        Collections.reverse(reverse);
    }

    @NotNull
    public static final SortedSet toSortedSet(@NotNull Iterable toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) CollectionsKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet toSortedSet(@NotNull Iterable toSortedSet, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return (SortedSet) CollectionsKt.toCollection(toSortedSet, new TreeSet(comparator));
    }
}

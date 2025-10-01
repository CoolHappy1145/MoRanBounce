package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd<\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0010\u001c\n\ufffd\ufffd\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0087\b\u00a2\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0006\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u001a!\u0010\u0006\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0087\b\u001a\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0007\u001a&\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a \u0010\f\u001a\u00020\u0001\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a3\u0010\f\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000fH\u0087\b\u001a5\u0010\f\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\u0013H\u0087\b\u001a2\u0010\u0014\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\u0013\u00a8\u0006\u0015"}, m27d2 = {"fill", "", "T", "", PropertyDescriptor.VALUE, "(Ljava/util/List;Ljava/lang/Object;)V", "shuffle", "random", "Ljava/util/Random;", "shuffled", "", "", "sort", "", "comparison", "Lkotlin/Function2;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "sortWith", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__MutableCollectionsJVMKt.class */
class CollectionsKt__MutableCollectionsJVMKt extends CollectionsKt__IteratorsKt {
    @Deprecated(message = "Use sortWith(comparator) instead.", replaceWith = @ReplaceWith(imports = {}, expression = "this.sortWith(comparator)"), level = DeprecationLevel.ERROR)
    @InlineOnly
    private static final void sort(@NotNull List list, Comparator comparator) {
        throw new NotImplementedError(null, 1, null);
    }

    @Deprecated(message = "Use sortWith(Comparator(comparison)) instead.", replaceWith = @ReplaceWith(imports = {}, expression = "this.sortWith(Comparator(comparison))"), level = DeprecationLevel.ERROR)
    @InlineOnly
    private static final void sort(@NotNull List list, Function2 function2) {
        throw new NotImplementedError(null, 1, null);
    }

    public static final void sort(@NotNull List sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.size() > 1) {
            Collections.sort(sort);
        }
    }

    public static final void sortWith(@NotNull List sortWith, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(sortWith, "$this$sortWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if (sortWith.size() > 1) {
            Collections.sort(sortWith, comparator);
        }
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final void fill(@NotNull List list, Object obj) {
        Collections.fill(list, obj);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final void shuffle(@NotNull List list) {
        Collections.shuffle(list);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final void shuffle(@NotNull List list, Random random) {
        Collections.shuffle(list, random);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List shuffled(@NotNull Iterable shuffled) {
        Intrinsics.checkParameterIsNotNull(shuffled, "$this$shuffled");
        List mutableList = CollectionsKt.toMutableList(shuffled);
        Collections.shuffle(mutableList);
        return mutableList;
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List shuffled(@NotNull Iterable shuffled, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(shuffled, "$this$shuffled");
        Intrinsics.checkParameterIsNotNull(random, "random");
        List mutableList = CollectionsKt.toMutableList(shuffled);
        Collections.shuffle(mutableList, random);
        return mutableList;
    }
}

package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdx\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a@\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\ufffd\ufffd\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a@\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\ufffd\ufffd\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\ufffd\ufffd\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\ufffd\ufffd\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u0019\u001a\u0012\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\ufffd\ufffd\u0010\u0007\u001a\u0015\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\ufffd\ufffd\u0010\u0007H\u0087\b\u001a+\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\ufffd\ufffd\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u001c\u001a%\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\ufffd\ufffd\u0010\u0007*\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u0007\u00a2\u0006\u0002\u0010 \u001a3\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\ufffd\ufffd\u0010\u0007*\u00020\u001e2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007\u00a2\u0006\u0002\u0010\u001c\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\ufffd\ufffd\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\ufffd\ufffd\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u001c\u001a%\u0010\"\u001a\u00020#2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b&\u001a\b\u0010'\u001a\u00020#H\u0001\u001a\b\u0010(\u001a\u00020#H\u0001\u001a%\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\ufffd\ufffd\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\ufffd\ufffd\u00a2\u0006\u0002\u0010*\u001aS\u0010+\u001a\u00020\u0006\"\u0004\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010\u001f\u001a\u0002H\u00072\u001a\u0010,\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00070-j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0007`.2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006\u00a2\u0006\u0002\u0010/\u001a>\u0010+\u001a\u00020\u0006\"\u0004\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u0010+\u001a\u00020\u0006\"\u000e\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u000701*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006\u00a2\u0006\u0002\u00102\u001ad\u00103\u001a\u00020\u0006\"\u0004\b\ufffd\ufffd\u0010\u0007\"\u000e\b\u0001\u00104*\b\u0012\u0004\u0012\u0002H401*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u00105\u001a\u0004\u0018\u0001H42\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0016\b\u0004\u00106\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H40\u000eH\u0086\b\u00a2\u0006\u0002\u00107\u001a,\u00108\u001a\u000209\"\t\b\ufffd\ufffd\u0010\u0007\u00a2\u0006\u0002\b:*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a8\u0010;\u001a\u0002H<\"\u0010\b\ufffd\ufffd\u0010=*\u0006\u0012\u0002\b\u00030\u0002*\u0002H<\"\u0004\b\u0001\u0010<*\u0002H=2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H<0?H\u0087\b\u00a2\u0006\u0002\u0010@\u001a\u0019\u0010A\u001a\u000209\"\u0004\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a,\u0010B\u001a\u000209\"\u0004\b\ufffd\ufffd\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\ufffd\ufffd\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\ufffd\ufffd\u001a\u001e\u0010C\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\ufffd\ufffd\u001a!\u0010D\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\ufffd\ufffd\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010D\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\ufffd\ufffd\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\"\u0019\u0010\ufffd\ufffd\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\ufffd\ufffd\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006E"}, m27d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__CollectionsKt.class */
class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
    @NotNull
    public static final Collection asCollection(@NotNull Object[] asCollection) {
        Intrinsics.checkParameterIsNotNull(asCollection, "$this$asCollection");
        return new ArrayAsCollection(asCollection, false);
    }

    @NotNull
    public static final List emptyList() {
        return EmptyList.INSTANCE;
    }

    @NotNull
    public static final List listOf(@NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length > 0 ? ArraysKt.asList(elements) : CollectionsKt.emptyList();
    }

    @InlineOnly
    private static final List listOf() {
        return CollectionsKt.emptyList();
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final List mutableListOf() {
        return new ArrayList();
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final ArrayList arrayListOf() {
        return new ArrayList();
    }

    @NotNull
    public static final List mutableListOf(@NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(elements, true));
    }

    @NotNull
    public static final ArrayList arrayListOf(@NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(elements, true));
    }

    @NotNull
    public static final List listOfNotNull(@Nullable Object obj) {
        return obj != null ? CollectionsKt.listOf(obj) : CollectionsKt.emptyList();
    }

    @NotNull
    public static final List listOfNotNull(@NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return ArraysKt.filterNotNull(elements);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final List List(int i, Function1 function1) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(function1.invoke(Integer.valueOf(i2)));
        }
        return arrayList;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final List MutableList(int i, Function1 function1) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(function1.invoke(Integer.valueOf(i2)));
        }
        return arrayList;
    }

    @NotNull
    public static final IntRange getIndices(@NotNull Collection indices) {
        Intrinsics.checkParameterIsNotNull(indices, "$this$indices");
        return new IntRange(0, indices.size() - 1);
    }

    public static final int getLastIndex(@NotNull List lastIndex) {
        Intrinsics.checkParameterIsNotNull(lastIndex, "$this$lastIndex");
        return lastIndex.size() - 1;
    }

    @InlineOnly
    private static final boolean isNotEmpty(@NotNull Collection collection) {
        return !collection.isEmpty();
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean isNullOrEmpty(@Nullable Collection collection) {
        return collection == null || collection.isEmpty();
    }

    @InlineOnly
    private static final Collection orEmpty(@Nullable Collection collection) {
        return collection != null ? collection : CollectionsKt.emptyList();
    }

    @InlineOnly
    private static final List orEmpty(@Nullable List list) {
        return list != null ? list : CollectionsKt.emptyList();
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object ifEmpty(Collection collection, Function0 function0) {
        return collection.isEmpty() ? function0.invoke() : collection;
    }

    @InlineOnly
    private static final boolean containsAll(@NotNull Collection collection, Collection collection2) {
        return collection.containsAll(collection2);
    }

    @NotNull
    public static final List optimizeReadOnlyList(@NotNull List optimizeReadOnlyList) {
        Intrinsics.checkParameterIsNotNull(optimizeReadOnlyList, "$this$optimizeReadOnlyList");
        switch (optimizeReadOnlyList.size()) {
            case 0:
                return CollectionsKt.emptyList();
            case 1:
                return CollectionsKt.listOf(optimizeReadOnlyList.get(0));
            default:
                return optimizeReadOnlyList;
        }
    }

    public static int binarySearch$default(List list, Comparable comparable, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = list.size();
        }
        return CollectionsKt.binarySearch(list, comparable, i, i2);
    }

    public static final int binarySearch(@NotNull List binarySearch, @Nullable Comparable comparable, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        rangeCheck$CollectionsKt__CollectionsKt(binarySearch.size(), i, i2);
        int i3 = i;
        int i4 = i2 - 1;
        while (i3 <= i4) {
            int i5 = (i3 + i4) >>> 1;
            int iCompareValues = ComparisonsKt.compareValues((Comparable) binarySearch.get(i5), comparable);
            if (iCompareValues < 0) {
                i3 = i5 + 1;
            } else if (iCompareValues > 0) {
                i4 = i5 - 1;
            } else {
                return i5;
            }
        }
        return -(i3 + 1);
    }

    public static int binarySearch$default(List list, Object obj, Comparator comparator, int i, int i2, int i3, Object obj2) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = list.size();
        }
        return CollectionsKt.binarySearch(list, obj, comparator, i, i2);
    }

    public static final int binarySearch(@NotNull List binarySearch, Object obj, @NotNull Comparator comparator, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        rangeCheck$CollectionsKt__CollectionsKt(binarySearch.size(), i, i2);
        int i3 = i;
        int i4 = i2 - 1;
        while (i3 <= i4) {
            int i5 = (i3 + i4) >>> 1;
            int iCompare = comparator.compare(binarySearch.get(i5), obj);
            if (iCompare < 0) {
                i3 = i5 + 1;
            } else if (iCompare > 0) {
                i4 = i5 - 1;
            } else {
                return i5;
            }
        }
        return -(i3 + 1);
    }

    public static int binarySearchBy$default(List binarySearchBy, Comparable comparable, int i, int i2, Function1 selector, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = binarySearchBy.size();
        }
        Intrinsics.checkParameterIsNotNull(binarySearchBy, "$this$binarySearchBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        return CollectionsKt.binarySearch(binarySearchBy, i, i2, new C03001(selector, comparable));
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0005\u001a\u0002H\u0002H\n\u00a2\u0006\u0004\b\u0006\u0010\u0007"}, m27d2 = {"<anonymous>", "", "T", "K", "", "it", "invoke", "(Ljava/lang/Object;)I"})
    /* renamed from: kotlin.collections.CollectionsKt__CollectionsKt$binarySearchBy$1 */
    /* loaded from: L-out.jar:kotlin/collections/CollectionsKt__CollectionsKt$binarySearchBy$1.class */
    public static final class C03001 extends Lambda implements Function1 {
        final Function1 $selector;
        final Comparable $key;

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Integer.valueOf(m776invoke(obj));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03001(Function1 function1, Comparable comparable) {
            super(1);
            this.$selector = function1;
            this.$key = comparable;
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final int m776invoke(Object obj) {
            return ComparisonsKt.compareValues((Comparable) this.$selector.invoke(obj), this.$key);
        }
    }

    public static final int binarySearchBy(@NotNull List binarySearchBy, @Nullable Comparable comparable, int i, int i2, @NotNull Function1 selector) {
        Intrinsics.checkParameterIsNotNull(binarySearchBy, "$this$binarySearchBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        return CollectionsKt.binarySearch(binarySearchBy, i, i2, new C03001(selector, comparable));
    }

    public static int binarySearch$default(List list, int i, int i2, Function1 function1, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = list.size();
        }
        return CollectionsKt.binarySearch(list, i, i2, function1);
    }

    public static final int binarySearch(@NotNull List binarySearch, int i, int i2, @NotNull Function1 comparison) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        Intrinsics.checkParameterIsNotNull(comparison, "comparison");
        rangeCheck$CollectionsKt__CollectionsKt(binarySearch.size(), i, i2);
        int i3 = i;
        int i4 = i2 - 1;
        while (i3 <= i4) {
            int i5 = (i3 + i4) >>> 1;
            int iIntValue = ((Number) comparison.invoke(binarySearch.get(i5))).intValue();
            if (iIntValue < 0) {
                i3 = i5 + 1;
            } else if (iIntValue > 0) {
                i4 = i5 - 1;
            } else {
                return i5;
            }
        }
        return -(i3 + 1);
    }

    private static final void rangeCheck$CollectionsKt__CollectionsKt(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex (" + i2 + ") is greater than toIndex (" + i3 + ").");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + i2 + ") is less than zero.");
        }
        if (i3 > i) {
            throw new IndexOutOfBoundsException("toIndex (" + i3 + ") is greater than size (" + i + ").");
        }
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final void throwIndexOverflow() {
        throw new ArithmeticException("Index overflow has happened.");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final void throwCountOverflow() {
        throw new ArithmeticException("Count overflow has happened.");
    }
}

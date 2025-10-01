package kotlin.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd^\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u001f\n\ufffd\ufffd\n\u0002\u0010\u0011\n\ufffd\ufffd\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001d\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\ufffd\ufffd\u001a-\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a&\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002\u00a2\u0006\u0002\b\u000e\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002\u00a2\u0006\u0002\b\u000e\u001a(\u0010\u0010\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n\u00a2\u0006\u0002\u0010\u0013\u001a.\u0010\u0010\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n\u00a2\u0006\u0002\u0010\u0014\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a(\u0010\u0015\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n\u00a2\u0006\u0002\u0010\u0013\u001a.\u0010\u0015\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n\u00a2\u0006\u0002\u0010\u0014\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a-\u0010\u0016\u001a\u00020\u0001\"\t\b\ufffd\ufffd\u0010\u0002\u00a2\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\b\u00a2\u0006\u0002\u0010\u0018\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0087\b\u00a2\u0006\u0002\u0010\u001b\u001a-\u0010\u001c\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001c\u001a\u00020\u0001\"\t\b\ufffd\ufffd\u0010\u0002\u00a2\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a-\u0010\u001e\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a&\u0010\u001e\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001e\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001e\u001a\u00020\u0001\"\t\b\ufffd\ufffd\u0010\u0002\u00a2\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001e\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001e\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u0015\u0010\u001f\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0003H\u0002\u00a2\u0006\u0002\b \u001a \u0010!\u001a\u00020\u0011\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\"\u001a\u00020#H\u0007\u001a&\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00020%\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0007\u00a8\u0006&"}, m27d2 = {"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "shuffle", "random", "Lkotlin/random/Random;", "shuffled", "", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__MutableCollectionsKt.class */
class CollectionsKt__MutableCollectionsKt extends CollectionsKt__MutableCollectionsJVMKt {
    @InlineOnly
    private static final boolean remove(@NotNull Collection collection, Object obj) {
        if (collection == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        }
        return TypeIntrinsics.asMutableCollection(collection).remove(obj);
    }

    @InlineOnly
    private static final boolean removeAll(@NotNull Collection collection, Collection collection2) {
        if (collection == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        }
        return TypeIntrinsics.asMutableCollection(collection).removeAll(collection2);
    }

    @InlineOnly
    private static final boolean retainAll(@NotNull Collection collection, Collection collection2) {
        if (collection == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        }
        return TypeIntrinsics.asMutableCollection(collection).retainAll(collection2);
    }

    @Deprecated(message = "Use removeAt(index) instead.", replaceWith = @ReplaceWith(imports = {}, expression = "removeAt(index)"), level = DeprecationLevel.ERROR)
    @InlineOnly
    private static final Object remove(@NotNull List list, int i) {
        return list.remove(i);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Collection plusAssign, Object obj) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        plusAssign.add(obj);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Collection plusAssign, Iterable iterable) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        CollectionsKt.addAll(plusAssign, iterable);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Collection plusAssign, Object[] objArr) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        CollectionsKt.addAll(plusAssign, objArr);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Collection plusAssign, Sequence sequence) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        CollectionsKt.addAll(plusAssign, sequence);
    }

    @InlineOnly
    private static final void minusAssign(@NotNull Collection minusAssign, Object obj) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        minusAssign.remove(obj);
    }

    @InlineOnly
    private static final void minusAssign(@NotNull Collection minusAssign, Iterable iterable) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        CollectionsKt.removeAll(minusAssign, iterable);
    }

    @InlineOnly
    private static final void minusAssign(@NotNull Collection minusAssign, Object[] objArr) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        CollectionsKt.removeAll(minusAssign, objArr);
    }

    @InlineOnly
    private static final void minusAssign(@NotNull Collection minusAssign, Sequence sequence) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        CollectionsKt.removeAll(minusAssign, sequence);
    }

    public static final boolean addAll(@NotNull Collection addAll, @NotNull Iterable elements) {
        Intrinsics.checkParameterIsNotNull(addAll, "$this$addAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        if (elements instanceof Collection) {
            return addAll.addAll((Collection) elements);
        }
        boolean z = false;
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            if (addAll.add(it.next())) {
                z = true;
            }
        }
        return z;
    }

    public static final boolean addAll(@NotNull Collection addAll, @NotNull Sequence elements) {
        Intrinsics.checkParameterIsNotNull(addAll, "$this$addAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        boolean z = false;
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            if (addAll.add(it.next())) {
                z = true;
            }
        }
        return z;
    }

    public static final boolean addAll(@NotNull Collection addAll, @NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(addAll, "$this$addAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return addAll.addAll(ArraysKt.asList(elements));
    }

    public static final boolean removeAll(@NotNull Iterable removeAll, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(removeAll, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt(removeAll, predicate, true);
    }

    public static final boolean retainAll(@NotNull Iterable retainAll, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(retainAll, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt(retainAll, predicate, false);
    }

    private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(@NotNull Iterable iterable, Function1 function1, boolean z) {
        boolean z2 = false;
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            if (((Boolean) function1.invoke(it.next())).booleanValue() == z) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    public static final boolean removeAll(@NotNull List removeAll, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(removeAll, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt(removeAll, predicate, true);
    }

    public static final boolean retainAll(@NotNull List retainAll, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(retainAll, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt(retainAll, predicate, false);
    }

    private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(@NotNull List list, Function1 function1, boolean z) {
        if (!(list instanceof RandomAccess)) {
            if (list == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableIterable<T>");
            }
            return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(list), function1, z);
        }
        int i = 0;
        int i2 = 0;
        int lastIndex = CollectionsKt.getLastIndex(list);
        if (0 <= lastIndex) {
            while (true) {
                Object obj = list.get(i2);
                if (((Boolean) function1.invoke(obj)).booleanValue() != z) {
                    if (i != i2) {
                        list.set(i, obj);
                    }
                    i++;
                }
                if (i2 == lastIndex) {
                    break;
                }
                i2++;
            }
        }
        if (i < list.size()) {
            int lastIndex2 = CollectionsKt.getLastIndex(list);
            int i3 = i;
            if (lastIndex2 < i3) {
                return true;
            }
            while (true) {
                list.remove(lastIndex2);
                if (lastIndex2 == i3) {
                    return true;
                }
                lastIndex2--;
            }
        } else {
            return false;
        }
    }

    public static final boolean removeAll(@NotNull Collection removeAll, @NotNull Iterable elements) {
        Intrinsics.checkParameterIsNotNull(removeAll, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return TypeIntrinsics.asMutableCollection(removeAll).removeAll(CollectionsKt.convertToSetForSetOperationWith(elements, removeAll));
    }

    public static final boolean removeAll(@NotNull Collection removeAll, @NotNull Sequence elements) {
        Intrinsics.checkParameterIsNotNull(removeAll, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        HashSet hashSet = SequencesKt.toHashSet(elements);
        return (!hashSet.isEmpty()) && removeAll.removeAll(hashSet);
    }

    public static final boolean removeAll(@NotNull Collection removeAll, @NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(removeAll, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return (!(elements.length == 0)) && removeAll.removeAll(ArraysKt.toHashSet(elements));
    }

    public static final boolean retainAll(@NotNull Collection retainAll, @NotNull Iterable elements) {
        Intrinsics.checkParameterIsNotNull(retainAll, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return TypeIntrinsics.asMutableCollection(retainAll).retainAll(CollectionsKt.convertToSetForSetOperationWith(elements, retainAll));
    }

    public static final boolean retainAll(@NotNull Collection retainAll, @NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(retainAll, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        if (!(elements.length == 0)) {
            return retainAll.retainAll(ArraysKt.toHashSet(elements));
        }
        return retainNothing$CollectionsKt__MutableCollectionsKt(retainAll);
    }

    public static final boolean retainAll(@NotNull Collection retainAll, @NotNull Sequence elements) {
        Intrinsics.checkParameterIsNotNull(retainAll, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        HashSet hashSet = SequencesKt.toHashSet(elements);
        if (!hashSet.isEmpty()) {
            return retainAll.retainAll(hashSet);
        }
        return retainNothing$CollectionsKt__MutableCollectionsKt(retainAll);
    }

    private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(@NotNull Collection collection) {
        boolean z = !collection.isEmpty();
        collection.clear();
        return z;
    }

    @SinceKotlin(version = "1.3")
    public static final void shuffle(@NotNull List shuffle, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(shuffle, "$this$shuffle");
        Intrinsics.checkParameterIsNotNull(random, "random");
        for (int lastIndex = CollectionsKt.getLastIndex(shuffle); lastIndex >= 1; lastIndex--) {
            int iNextInt = random.nextInt(lastIndex + 1);
            Object obj = shuffle.get(lastIndex);
            shuffle.set(lastIndex, shuffle.get(iNextInt));
            shuffle.set(iNextInt, obj);
        }
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final List shuffled(@NotNull Iterable shuffled, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(shuffled, "$this$shuffled");
        Intrinsics.checkParameterIsNotNull(random, "random");
        List mutableList = CollectionsKt.toMutableList(shuffled);
        CollectionsKt.shuffle(mutableList, random);
        return mutableList;
    }
}

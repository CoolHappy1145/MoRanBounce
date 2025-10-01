package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.TuplesKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a+\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a \u0010\u0006\u001a\u00020\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\b\u001a\u00020\u0007H\u0001\u001a\u001f\u0010\t\u001a\u0004\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0001\u00a2\u0006\u0002\u0010\n\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\ufffd\ufffd\u001a,\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\ufffd\ufffd\u001a\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0010\"\u0004\b\ufffd\ufffd\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a\u001d\u0010\u0011\u001a\u00020\u0012\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\fH\u0002\u00a2\u0006\u0002\b\u0013\u001a@\u0010\u0014\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00160\u00100\u0015\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00160\u00150\u0001\u00a8\u0006\u0017"}, m27d2 = {"Iterable", "", "T", "iterator", "Lkotlin/Function0;", "", "collectionSizeOrDefault", "", "default", "collectionSizeOrNull", "(Ljava/lang/Iterable;)Ljava/lang/Integer;", "convertToSetForSetOperation", "", "convertToSetForSetOperationWith", "source", "flatten", "", "safeToConvertToSet", "", "safeToConvertToSet$CollectionsKt__IterablesKt", "unzip", "Lkotlin/Pair;", "R", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__IterablesKt.class */
class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0011\n\ufffd\ufffd\n\u0002\u0010\u001c\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0003H\u0096\u0002\u00a8\u0006\u0004"}, m27d2 = {"kotlin/collections/CollectionsKt__IterablesKt$Iterable$1", "", "iterator", "", "kotlin-stdlib"})
    /* renamed from: kotlin.collections.CollectionsKt__IterablesKt$Iterable$1 */
    /* loaded from: L-out.jar:kotlin/collections/CollectionsKt__IterablesKt$Iterable$1.class */
    public static final class C03011 implements Iterable, KMappedMarker {
        final Function0 $iterator;

        public C03011(Function0 function0) {
            this.$iterator = function0;
        }

        @Override // java.lang.Iterable
        @NotNull
        public Iterator iterator() {
            return (Iterator) this.$iterator.invoke();
        }
    }

    @InlineOnly
    private static final Iterable Iterable(Function0 function0) {
        return new C03011(function0);
    }

    @PublishedApi
    @Nullable
    public static final Integer collectionSizeOrNull(@NotNull Iterable collectionSizeOrNull) {
        Intrinsics.checkParameterIsNotNull(collectionSizeOrNull, "$this$collectionSizeOrNull");
        if (collectionSizeOrNull instanceof Collection) {
            return Integer.valueOf(((Collection) collectionSizeOrNull).size());
        }
        return null;
    }

    @PublishedApi
    public static final int collectionSizeOrDefault(@NotNull Iterable collectionSizeOrDefault, int i) {
        Intrinsics.checkParameterIsNotNull(collectionSizeOrDefault, "$this$collectionSizeOrDefault");
        return collectionSizeOrDefault instanceof Collection ? ((Collection) collectionSizeOrDefault).size() : i;
    }

    private static final boolean safeToConvertToSet$CollectionsKt__IterablesKt(@NotNull Collection collection) {
        return collection.size() > 2 && (collection instanceof ArrayList);
    }

    @NotNull
    public static final Collection convertToSetForSetOperationWith(@NotNull Iterable convertToSetForSetOperationWith, @NotNull Iterable source) {
        Intrinsics.checkParameterIsNotNull(convertToSetForSetOperationWith, "$this$convertToSetForSetOperationWith");
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (convertToSetForSetOperationWith instanceof Set) {
            return (Collection) convertToSetForSetOperationWith;
        }
        if (convertToSetForSetOperationWith instanceof Collection) {
            if ((!(source instanceof Collection) || ((Collection) source).size() >= 2) && safeToConvertToSet$CollectionsKt__IterablesKt((Collection) convertToSetForSetOperationWith)) {
                return CollectionsKt.toHashSet(convertToSetForSetOperationWith);
            }
            return (Collection) convertToSetForSetOperationWith;
        }
        return CollectionsKt.toHashSet(convertToSetForSetOperationWith);
    }

    @NotNull
    public static final Collection convertToSetForSetOperation(@NotNull Iterable convertToSetForSetOperation) {
        Intrinsics.checkParameterIsNotNull(convertToSetForSetOperation, "$this$convertToSetForSetOperation");
        if (convertToSetForSetOperation instanceof Set) {
            return (Collection) convertToSetForSetOperation;
        }
        if (convertToSetForSetOperation instanceof Collection) {
            return safeToConvertToSet$CollectionsKt__IterablesKt((Collection) convertToSetForSetOperation) ? CollectionsKt.toHashSet(convertToSetForSetOperation) : (Collection) convertToSetForSetOperation;
        }
        return CollectionsKt.toHashSet(convertToSetForSetOperation);
    }

    @NotNull
    public static final List flatten(@NotNull Iterable flatten) {
        Intrinsics.checkParameterIsNotNull(flatten, "$this$flatten");
        ArrayList arrayList = new ArrayList();
        Iterator it = flatten.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, (Iterable) it.next());
        }
        return arrayList;
    }

    @NotNull
    public static final Pair unzip(@NotNull Iterable unzip) {
        Intrinsics.checkParameterIsNotNull(unzip, "$this$unzip");
        int iCollectionSizeOrDefault = CollectionsKt.collectionSizeOrDefault(unzip, 10);
        ArrayList arrayList = new ArrayList(iCollectionSizeOrDefault);
        ArrayList arrayList2 = new ArrayList(iCollectionSizeOrDefault);
        Iterator it = unzip.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.m32to(arrayList, arrayList2);
    }
}

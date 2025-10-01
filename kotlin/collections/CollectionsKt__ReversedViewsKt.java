package kotlin.collections;

import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0018\n\ufffd\ufffd\n\u0002\u0010 \n\ufffd\ufffd\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u001c\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a#\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0004\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\b\u001a\u001d\u0010\t\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\n\u00a8\u0006\u000b"}, m27d2 = {"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__ReversedViewsKt.class */
class CollectionsKt__ReversedViewsKt extends CollectionsKt__MutableCollectionsKt {
    private static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(@NotNull List list, int i) {
        int lastIndex = CollectionsKt.getLastIndex(list);
        if (0 <= i && lastIndex >= i) {
            return CollectionsKt.getLastIndex(list) - i;
        }
        throw new IndexOutOfBoundsException("Element index " + i + " must be in range [" + new IntRange(0, CollectionsKt.getLastIndex(list)) + "].");
    }

    private static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(@NotNull List list, int i) {
        int size = list.size();
        if (0 <= i && size >= i) {
            return list.size() - i;
        }
        throw new IndexOutOfBoundsException("Position index " + i + " must be in range [" + new IntRange(0, list.size()) + "].");
    }

    @NotNull
    public static final List asReversed(@NotNull List asReversed) {
        Intrinsics.checkParameterIsNotNull(asReversed, "$this$asReversed");
        return new ReversedListReadOnly(asReversed);
    }

    @JvmName(name = "asReversedMutable")
    @NotNull
    public static final List asReversedMutable(@NotNull List asReversed) {
        Intrinsics.checkParameterIsNotNull(asReversed, "$this$asReversed");
        return new ReversedList(asReversed);
    }
}

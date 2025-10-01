package kotlin.collections;

import java.util.Enumeration;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u001f\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\u0002\u00a8\u0006\u0004"}, m27d2 = {"iterator", "", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__IteratorsJVMKt.class */
class CollectionsKt__IteratorsJVMKt extends CollectionsKt__IterablesKt {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0013\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\t\u0010\u0002\u001a\u00020\u0003H\u0096\u0002J\u000e\u0010\u0004\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, m27d2 = {"kotlin/collections/CollectionsKt__IteratorsJVMKt$iterator$1", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    /* renamed from: kotlin.collections.CollectionsKt__IteratorsJVMKt$iterator$1 */
    /* loaded from: L-out.jar:kotlin/collections/CollectionsKt__IteratorsJVMKt$iterator$1.class */
    public static final class C03021 implements Iterator, KMappedMarker {
        final Enumeration $this_iterator;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        C03021(Enumeration enumeration) {
            this.$this_iterator = enumeration;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.$this_iterator.hasMoreElements();
        }

        @Override // java.util.Iterator
        public Object next() {
            return this.$this_iterator.nextElement();
        }
    }

    @NotNull
    public static final Iterator iterator(@NotNull Enumeration iterator) {
        Intrinsics.checkParameterIsNotNull(iterator, "$this$iterator");
        return new C03021(iterator);
    }
}

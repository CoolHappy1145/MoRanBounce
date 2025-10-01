package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002\u00a2\u0006\u0002\u0010\u0005J\u0015\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u00030\u0007H\u0096\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\b"}, m27d2 = {"Lkotlin/sequences/IndexingSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/collections/IndexedValue;", "sequence", "(Lkotlin/sequences/Sequence;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/IndexingSequence.class */
public final class IndexingSequence implements Sequence {
    private final Sequence sequence;

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001f\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u00020\u0001J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002H\u0096\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, m27d2 = {"kotlin/sequences/IndexingSequence$iterator$1", "", "Lkotlin/collections/IndexedValue;", "index", "", "getIndex", "()I", "setIndex", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "kotlin-stdlib"})
    /* renamed from: kotlin.sequences.IndexingSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/sequences/IndexingSequence$iterator$1.class */
    public static final class C03521 implements Iterator, KMappedMarker {

        @NotNull
        private final Iterator iterator;
        private int index;
        final IndexingSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Iterator
        public Object next() {
            return next();
        }

        C03521(IndexingSequence indexingSequence) {
            this.this$0 = indexingSequence;
            this.iterator = indexingSequence.sequence.iterator();
        }

        @NotNull
        public final Iterator getIterator() {
            return this.iterator;
        }

        public final int getIndex() {
            return this.index;
        }

        public final void setIndex(int i) {
            this.index = i;
        }

        @Override // java.util.Iterator
        @NotNull
        public IndexedValue next() {
            int i = this.index;
            this.index = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            return new IndexedValue(i, this.iterator.next());
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }
    }

    public IndexingSequence(@NotNull Sequence sequence) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        this.sequence = sequence;
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03521(this);
    }
}

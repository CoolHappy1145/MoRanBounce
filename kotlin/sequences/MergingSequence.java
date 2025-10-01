package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u0004B;\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00020\u000bH\u0096\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\f"}, m27d2 = {"Lkotlin/sequences/MergingSequence;", "T1", "T2", "V", "Lkotlin/sequences/Sequence;", "sequence1", "sequence2", "transform", "Lkotlin/Function2;", "(Lkotlin/sequences/Sequence;Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/MergingSequence.class */
public final class MergingSequence implements Sequence {
    private final Sequence sequence1;
    private final Sequence sequence2;
    private final Function2 transform;

    public MergingSequence(@NotNull Sequence sequence1, @NotNull Sequence sequence2, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(sequence1, "sequence1");
        Intrinsics.checkParameterIsNotNull(sequence2, "sequence2");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.transform = transform;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0015\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u000e\u0010\t\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0004\u00a8\u0006\u000b"}, m27d2 = {"kotlin/sequences/MergingSequence$iterator$1", "", "iterator1", "getIterator1", "()Ljava/util/Iterator;", "iterator2", "getIterator2", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    /* renamed from: kotlin.sequences.MergingSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/sequences/MergingSequence$iterator$1.class */
    public static final class C03531 implements Iterator, KMappedMarker {

        @NotNull
        private final Iterator iterator1;

        @NotNull
        private final Iterator iterator2;
        final MergingSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        C03531(MergingSequence mergingSequence) {
            this.this$0 = mergingSequence;
            this.iterator1 = mergingSequence.sequence1.iterator();
            this.iterator2 = mergingSequence.sequence2.iterator();
        }

        @NotNull
        public final Iterator getIterator1() {
            return this.iterator1;
        }

        @NotNull
        public final Iterator getIterator2() {
            return this.iterator2;
        }

        @Override // java.util.Iterator
        public Object next() {
            return this.this$0.transform.invoke(this.iterator1.next(), this.iterator2.next());
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator1.hasNext() && this.iterator2.hasNext();
        }
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03531(this);
    }
}

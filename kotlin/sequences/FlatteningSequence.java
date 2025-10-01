package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\u0002\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u0004BA\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\tH\u0096\u0002R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lkotlin/sequences/FlatteningSequence;", "T", "R", "E", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function1;", "iterator", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/FlatteningSequence.class */
public final class FlatteningSequence implements Sequence {
    private final Sequence sequence;
    private final Function1 transformer;
    private final Function1 iterator;

    public FlatteningSequence(@NotNull Sequence sequence, @NotNull Function1 transformer, @NotNull Function1 iterator) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(transformer, "transformer");
        Intrinsics.checkParameterIsNotNull(iterator, "iterator");
        this.sequence = sequence;
        this.transformer = transformer;
        this.iterator = iterator;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0015\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\b\u0010\t\u001a\u00020\nH\u0002J\t\u0010\u000b\u001a\u00020\nH\u0096\u0002J\u000e\u0010\f\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\rR\"\u0010\u0002\u001a\n\u0012\u0004\u0012\u00028\ufffd\ufffd\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0004\u00a8\u0006\u000e"}, m27d2 = {"kotlin/sequences/FlatteningSequence$iterator$1", "", "itemIterator", "getItemIterator", "()Ljava/util/Iterator;", "setItemIterator", "(Ljava/util/Iterator;)V", "iterator", "getIterator", "ensureItemIterator", "", "hasNext", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    /* renamed from: kotlin.sequences.FlatteningSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/sequences/FlatteningSequence$iterator$1.class */
    public static final class C03501 implements Iterator, KMappedMarker {

        @NotNull
        private final Iterator iterator;

        @Nullable
        private Iterator itemIterator;
        final FlatteningSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        C03501(FlatteningSequence flatteningSequence) {
            this.this$0 = flatteningSequence;
            this.iterator = flatteningSequence.sequence.iterator();
        }

        @NotNull
        public final Iterator getIterator() {
            return this.iterator;
        }

        @Nullable
        public final Iterator getItemIterator() {
            return this.itemIterator;
        }

        public final void setItemIterator(@Nullable Iterator it) {
            this.itemIterator = it;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!ensureItemIterator()) {
                throw new NoSuchElementException();
            }
            Iterator it = this.itemIterator;
            if (it == null) {
                Intrinsics.throwNpe();
            }
            return it.next();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return ensureItemIterator();
        }

        private final boolean ensureItemIterator() {
            Iterator it = this.itemIterator;
            if (it != null && !it.hasNext()) {
                this.itemIterator = (Iterator) null;
            }
            while (this.itemIterator == null) {
                if (!this.iterator.hasNext()) {
                    return false;
                }
                Iterator it2 = (Iterator) this.this$0.iterator.invoke(this.this$0.transformer.invoke(this.iterator.next()));
                if (it2.hasNext()) {
                    this.itemIterator = it2;
                    return true;
                }
            }
            return true;
        }
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03501(this);
    }
}

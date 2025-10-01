package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\tH\u0096\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\n"}, m27d2 = {"Lkotlin/sequences/DropWhileSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "predicate", "Lkotlin/Function1;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/DropWhileSequence.class */
public final class DropWhileSequence implements Sequence {
    private final Sequence sequence;
    private final Function1 predicate;

    public DropWhileSequence(@NotNull Sequence sequence, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        this.sequence = sequence;
        this.predicate = predicate;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd!\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u001e\u0010\u000b\u001a\u0004\u0018\u00018\ufffd\ufffdX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0016"}, m27d2 = {"kotlin/sequences/DropWhileSequence$iterator$1", "", "dropState", "", "getDropState", "()I", "setDropState", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", Constants.OBJECT_DESC, "drop", "", "hasNext", "", "next", "kotlin-stdlib"})
    /* renamed from: kotlin.sequences.DropWhileSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/sequences/DropWhileSequence$iterator$1.class */
    public static final class C03481 implements Iterator, KMappedMarker {

        @NotNull
        private final Iterator iterator;
        private int dropState = -1;

        @Nullable
        private Object nextItem;
        final DropWhileSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        C03481(DropWhileSequence dropWhileSequence) {
            this.this$0 = dropWhileSequence;
            this.iterator = dropWhileSequence.sequence.iterator();
        }

        @NotNull
        public final Iterator getIterator() {
            return this.iterator;
        }

        public final int getDropState() {
            return this.dropState;
        }

        public final void setDropState(int i) {
            this.dropState = i;
        }

        @Nullable
        public final Object getNextItem() {
            return this.nextItem;
        }

        public final void setNextItem(@Nullable Object obj) {
            this.nextItem = obj;
        }

        private final void drop() {
            while (this.iterator.hasNext()) {
                Object next = this.iterator.next();
                if (!((Boolean) this.this$0.predicate.invoke(next)).booleanValue()) {
                    this.nextItem = next;
                    this.dropState = 1;
                    return;
                }
            }
            this.dropState = 0;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.dropState == -1) {
                drop();
            }
            if (this.dropState == 1) {
                Object obj = this.nextItem;
                this.nextItem = null;
                this.dropState = 0;
                return obj;
            }
            return this.iterator.next();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.dropState == -1) {
                drop();
            }
            return this.dropState == 1 || this.iterator.hasNext();
        }
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03481(this);
    }
}

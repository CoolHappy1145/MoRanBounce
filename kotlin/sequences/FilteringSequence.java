package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\nH\u0096\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lkotlin/sequences/FilteringSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "sendWhen", "", "predicate", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;ZLkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/FilteringSequence.class */
public final class FilteringSequence implements Sequence {
    private final Sequence sequence;
    private final boolean sendWhen;
    private final Function1 predicate;

    public FilteringSequence(@NotNull Sequence sequence, boolean z, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        this.sequence = sequence;
        this.sendWhen = z;
        this.predicate = predicate;
    }

    public FilteringSequence(Sequence sequence, boolean z, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sequence, (i & 2) != 0 ? true : z, function1);
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd#\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0004R\u001e\u0010\u0005\u001a\u0004\u0018\u00018\ufffd\ufffdX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0016"}, m27d2 = {"kotlin/sequences/FilteringSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", Constants.OBJECT_DESC, "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"})
    /* renamed from: kotlin.sequences.FilteringSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/sequences/FilteringSequence$iterator$1.class */
    public static final class C03491 implements Iterator, KMappedMarker {

        @NotNull
        private final Iterator iterator;
        private int nextState = -1;

        @Nullable
        private Object nextItem;
        final FilteringSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        C03491(FilteringSequence filteringSequence) {
            this.this$0 = filteringSequence;
            this.iterator = filteringSequence.sequence.iterator();
        }

        @NotNull
        public final Iterator getIterator() {
            return this.iterator;
        }

        public final int getNextState() {
            return this.nextState;
        }

        public final void setNextState(int i) {
            this.nextState = i;
        }

        @Nullable
        public final Object getNextItem() {
            return this.nextItem;
        }

        public final void setNextItem(@Nullable Object obj) {
            this.nextItem = obj;
        }

        private final void calcNext() {
            while (this.iterator.hasNext()) {
                Object next = this.iterator.next();
                if (((Boolean) this.this$0.predicate.invoke(next)).booleanValue() == this.this$0.sendWhen) {
                    this.nextItem = next;
                    this.nextState = 1;
                    return;
                }
            }
            this.nextState = 0;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.nextState == -1) {
                calcNext();
            }
            if (this.nextState == 0) {
                throw new NoSuchElementException();
            }
            Object obj = this.nextItem;
            this.nextItem = null;
            this.nextState = -1;
            return obj;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextState == -1) {
                calcNext();
            }
            return this.nextState == 1;
        }
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03491(this);
    }
}

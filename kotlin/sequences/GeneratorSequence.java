package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\u0002\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B+\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\ufffd\ufffd0\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0006\u0012\u0004\u0018\u00018\ufffd\ufffd0\u0007\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\nH\u0096\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\ufffd\ufffd0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0006\u0012\u0004\u0018\u00018\ufffd\ufffd0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lkotlin/sequences/GeneratorSequence;", "T", "", "Lkotlin/sequences/Sequence;", "getInitialValue", "Lkotlin/Function0;", "getNextValue", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/GeneratorSequence.class */
final class GeneratorSequence implements Sequence {
    private final Function0 getInitialValue;
    private final Function1 getNextValue;

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd#\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\t\u0010\u0010\u001a\u00020\u0011H\u0096\u0002J\u000e\u0010\u0012\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\u0004R\u001e\u0010\u0002\u001a\u0004\u0018\u00018\ufffd\ufffdX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0007\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u0013"}, m27d2 = {"kotlin/sequences/GeneratorSequence$iterator$1", "", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", Constants.OBJECT_DESC, "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"})
    /* renamed from: kotlin.sequences.GeneratorSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/sequences/GeneratorSequence$iterator$1.class */
    public static final class C03511 implements Iterator, KMappedMarker {

        @Nullable
        private Object nextItem;
        private int nextState = -2;
        final GeneratorSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        C03511(GeneratorSequence generatorSequence) {
            this.this$0 = generatorSequence;
        }

        @Nullable
        public final Object getNextItem() {
            return this.nextItem;
        }

        public final void setNextItem(@Nullable Object obj) {
            this.nextItem = obj;
        }

        public final int getNextState() {
            return this.nextState;
        }

        public final void setNextState(int i) {
            this.nextState = i;
        }

        private final void calcNext() {
            Object objInvoke;
            if (this.nextState == -2) {
                objInvoke = this.this$0.getInitialValue.invoke();
            } else {
                Function1 function1 = this.this$0.getNextValue;
                Object obj = this.nextItem;
                if (obj == null) {
                    Intrinsics.throwNpe();
                }
                objInvoke = function1.invoke(obj);
            }
            this.nextItem = objInvoke;
            this.nextState = this.nextItem == null ? 0 : 1;
        }

        @Override // java.util.Iterator
        @NotNull
        public Object next() {
            if (this.nextState < 0) {
                calcNext();
            }
            if (this.nextState == 0) {
                throw new NoSuchElementException();
            }
            Object obj = this.nextItem;
            if (obj == null) {
                throw new TypeCastException("null cannot be cast to non-null type T");
            }
            this.nextState = -1;
            return obj;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextState < 0) {
                calcNext();
            }
            return this.nextState == 1;
        }
    }

    public GeneratorSequence(@NotNull Function0 getInitialValue, @NotNull Function1 getNextValue) {
        Intrinsics.checkParameterIsNotNull(getInitialValue, "getInitialValue");
        Intrinsics.checkParameterIsNotNull(getNextValue, "getNextValue");
        this.getInitialValue = getInitialValue;
        this.getNextValue = getNextValue;
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03511(this);
    }
}

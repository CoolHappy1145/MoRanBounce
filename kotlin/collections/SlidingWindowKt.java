package kotlin.collections;

import java.util.ArrayList;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd*\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u0018\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\ufffd\ufffd\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\ufffd\ufffd\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\ufffd\ufffd\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\ufffd\ufffd\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/SlidingWindowKt.class */
public final class SlidingWindowKt {
    public static final void checkWindowSizeStep(int i, int i2) {
        String str;
        if (!(i > 0 && i2 > 0)) {
            if (i != i2) {
                str = "Both size " + i + " and step " + i2 + " must be greater than zero.";
            } else {
                str = "size " + i + " must be greater than zero.";
            }
            throw new IllegalArgumentException(str.toString());
        }
    }

    @NotNull
    public static final Sequence windowedSequence(@NotNull final Sequence windowedSequence, final int i, final int i2, final boolean z, final boolean z2) {
        Intrinsics.checkParameterIsNotNull(windowedSequence, "$this$windowedSequence");
        checkWindowSizeStep(i, i2);
        return new Sequence(windowedSequence, i, i2, z, z2) { // from class: kotlin.collections.SlidingWindowKt$windowedSequence$$inlined$Sequence$1
            final Sequence $this_windowedSequence$inlined;
            final int $size$inlined;
            final int $step$inlined;
            final boolean $partialWindows$inlined;
            final boolean $reuseBuffer$inlined;

            {
                this.$this_windowedSequence$inlined = windowedSequence;
                this.$size$inlined = i;
                this.$step$inlined = i2;
                this.$partialWindows$inlined = z;
                this.$reuseBuffer$inlined = z2;
            }

            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator iterator() {
                return SlidingWindowKt.windowedIterator(this.$this_windowedSequence$inlined.iterator(), this.$size$inlined, this.$step$inlined, this.$partialWindows$inlined, this.$reuseBuffer$inlined);
            }
        };
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@\u00a2\u0006\u0004\b\u0005\u0010\u0006"}, m27d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"})
    @DebugMetadata(m34f = "SlidingWindow.kt", m35l = {34, 40, OPCode.MEMORY_START_PUSH, OPCode.JUMP, OPCode.PUSH_OR_JUMP_EXACT1}, m38i = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4}, m37s = {"L$0", "I$0", "I$1", "L$1", "I$2", "L$2", "L$0", "I$0", "I$1", "L$1", "I$2", "L$0", "I$0", "I$1", "L$1", "L$2", "L$0", "I$0", "I$1", "L$1", "L$0", "I$0", "I$1", "L$1"}, m36n = {"$this$iterator", "bufferInitialCapacity", "gap", "buffer", "skip", "e", "$this$iterator", "bufferInitialCapacity", "gap", "buffer", "skip", "$this$iterator", "bufferInitialCapacity", "gap", "buffer", "e", "$this$iterator", "bufferInitialCapacity", "gap", "buffer", "$this$iterator", "bufferInitialCapacity", "gap", "buffer"}, m39m = "invokeSuspend", m40c = "kotlin.collections.SlidingWindowKt$windowedIterator$1")
    /* renamed from: kotlin.collections.SlidingWindowKt$windowedIterator$1 */
    /* loaded from: L-out.jar:kotlin/collections/SlidingWindowKt$windowedIterator$1.class */
    static final class C03071 extends RestrictedSuspendLambda implements Function2 {

        /* renamed from: p$ */
        private SequenceScope f93p$;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int I$0;
        int I$1;
        int I$2;
        int label;
        final int $size;
        final int $step;
        final Iterator $iterator;
        final boolean $reuseBuffer;
        final boolean $partialWindows;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03071(int i, int i2, Iterator it, boolean z, boolean z2, Continuation continuation) {
            super(2, continuation);
            this.$size = i;
            this.$step = i2;
            this.$iterator = it;
            this.$reuseBuffer = z;
            this.$partialWindows = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation create(@Nullable Object obj, @NotNull Continuation completion) {
            Intrinsics.checkParameterIsNotNull(completion, "completion");
            C03071 c03071 = new C03071(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, completion);
            c03071.f93p$ = (SequenceScope) obj;
            return c03071;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C03071) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:111:? A[PHI: r8 r9 r10 r11
  PHI (r8v3 kotlin.sequences.SequenceScope) = (r8v0 kotlin.sequences.SequenceScope), (r8v4 kotlin.sequences.SequenceScope) binds: [B:90:0x0294, B:60:0x0291] A[DONT_GENERATE, DONT_INLINE]
  PHI (r9v3 int) = (r9v0 int), (r9v4 int) binds: [B:90:0x0294, B:60:0x0291] A[DONT_GENERATE, DONT_INLINE]
  PHI (r10v3 int) = (r10v0 int), (r10v4 int) binds: [B:90:0x0294, B:60:0x0291] A[DONT_GENERATE, DONT_INLINE]
  PHI (r11v3 kotlin.collections.RingBuffer) = (r11v0 kotlin.collections.RingBuffer), (r11v5 kotlin.collections.RingBuffer) binds: [B:90:0x0294, B:60:0x0291] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0115  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x011d  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x013e  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0142  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0187  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x01cc  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x02a0  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0322  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x0326  */
        /* JADX WARN: Removed duplicated region for block: B:78:0x032a  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0071  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(@NotNull Object obj) {
            RingBuffer ringBufferExpanded;
            int i;
            int iCoerceAtMost;
            SequenceScope sequenceScope;
            Iterator it;
            Iterator it2;
            ArrayList arrayList;
            int i2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    sequenceScope = this.f93p$;
                    iCoerceAtMost = RangesKt.coerceAtMost(this.$size, 1024);
                    i = this.$step - this.$size;
                    if (i >= 0) {
                        arrayList = new ArrayList(iCoerceAtMost);
                        i2 = 0;
                        it2 = this.$iterator;
                        while (it2.hasNext()) {
                            Object next = it2.next();
                            if (i2 > 0) {
                                i2--;
                            } else {
                                arrayList.add(next);
                                if (arrayList.size() == this.$size) {
                                    this.L$0 = sequenceScope;
                                    this.I$0 = iCoerceAtMost;
                                    this.I$1 = i;
                                    this.L$1 = arrayList;
                                    this.I$2 = i2;
                                    this.L$2 = next;
                                    this.L$3 = it2;
                                    this.label = 1;
                                    if (sequenceScope.yield(arrayList, this) == coroutine_suspended) {
                                        return coroutine_suspended;
                                    }
                                    if (this.$reuseBuffer) {
                                        arrayList = new ArrayList(this.$size);
                                        i2 = i;
                                    } else {
                                        arrayList.clear();
                                        i2 = i;
                                    }
                                    while (it2.hasNext()) {
                                    }
                                }
                            }
                        }
                        if ((arrayList.isEmpty()) && (this.$partialWindows || arrayList.size() == this.$size)) {
                            this.L$0 = sequenceScope;
                            this.I$0 = iCoerceAtMost;
                            this.I$1 = i;
                            this.L$1 = arrayList;
                            this.I$2 = i2;
                            this.label = 2;
                            if (sequenceScope.yield(arrayList, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    ringBufferExpanded = new RingBuffer(iCoerceAtMost);
                    it = this.$iterator;
                    while (it.hasNext()) {
                        Object next2 = it.next();
                        ringBufferExpanded.add(next2);
                        if (ringBufferExpanded.isFull()) {
                            if (ringBufferExpanded.size() < this.$size) {
                                ringBufferExpanded = ringBufferExpanded.expanded(this.$size);
                            } else {
                                SequenceScope sequenceScope2 = sequenceScope;
                                ArrayList arrayList2 = this.$reuseBuffer ? ringBufferExpanded : new ArrayList(ringBufferExpanded);
                                this.L$0 = sequenceScope;
                                this.I$0 = iCoerceAtMost;
                                this.I$1 = i;
                                this.L$1 = ringBufferExpanded;
                                this.L$2 = next2;
                                this.L$3 = it;
                                this.label = 3;
                                if (sequenceScope2.yield(arrayList2, this) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                                ringBufferExpanded.removeFirst(this.$step);
                            }
                        }
                    }
                    if (this.$partialWindows) {
                        while (ringBufferExpanded.size() > this.$step) {
                            SequenceScope sequenceScope3 = sequenceScope;
                            ArrayList arrayList3 = this.$reuseBuffer ? ringBufferExpanded : new ArrayList(ringBufferExpanded);
                            this.L$0 = sequenceScope;
                            this.I$0 = iCoerceAtMost;
                            this.I$1 = i;
                            this.L$1 = ringBufferExpanded;
                            this.label = 4;
                            if (sequenceScope3.yield(arrayList3, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            ringBufferExpanded.removeFirst(this.$step);
                        }
                        if (ringBufferExpanded.isEmpty()) {
                            this.L$0 = sequenceScope;
                            this.I$0 = iCoerceAtMost;
                            this.I$1 = i;
                            this.L$1 = ringBufferExpanded;
                            this.label = 5;
                            if (sequenceScope.yield(ringBufferExpanded, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    }
                    return Unit.INSTANCE;
                case 1:
                    it2 = (Iterator) this.L$3;
                    Object obj2 = this.L$2;
                    int i3 = this.I$2;
                    arrayList = (ArrayList) this.L$1;
                    i = this.I$1;
                    iCoerceAtMost = this.I$0;
                    sequenceScope = (SequenceScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    if (this.$reuseBuffer) {
                    }
                    while (it2.hasNext()) {
                    }
                    if (arrayList.isEmpty()) {
                        this.L$0 = sequenceScope;
                        this.I$0 = iCoerceAtMost;
                        this.I$1 = i;
                        this.L$1 = arrayList;
                        this.I$2 = i2;
                        this.label = 2;
                        if (sequenceScope.yield(arrayList, this) == coroutine_suspended) {
                        }
                        break;
                    }
                    return Unit.INSTANCE;
                case 2:
                    int i4 = this.I$2;
                    int i5 = this.I$1;
                    int i6 = this.I$0;
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                case 3:
                    it = (Iterator) this.L$3;
                    Object obj3 = this.L$2;
                    ringBufferExpanded = (RingBuffer) this.L$1;
                    i = this.I$1;
                    iCoerceAtMost = this.I$0;
                    sequenceScope = (SequenceScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    ringBufferExpanded.removeFirst(this.$step);
                    while (it.hasNext()) {
                    }
                    if (this.$partialWindows) {
                    }
                    return Unit.INSTANCE;
                case 4:
                    ringBufferExpanded = (RingBuffer) this.L$1;
                    i = this.I$1;
                    iCoerceAtMost = this.I$0;
                    sequenceScope = (SequenceScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    ringBufferExpanded.removeFirst(this.$step);
                    while (ringBufferExpanded.size() > this.$step) {
                    }
                    if (ringBufferExpanded.isEmpty()) {
                    }
                    return Unit.INSTANCE;
                case 5:
                    int i7 = this.I$1;
                    int i8 = this.I$0;
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @NotNull
    public static final Iterator windowedIterator(@NotNull Iterator iterator, int i, int i2, boolean z, boolean z2) {
        Intrinsics.checkParameterIsNotNull(iterator, "iterator");
        return !iterator.hasNext() ? EmptyIterator.INSTANCE : SequencesKt.iterator(new C03071(i, i2, iterator, z2, z, null));
    }
}

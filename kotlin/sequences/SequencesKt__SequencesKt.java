package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd@\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\ufffd\ufffd\u001a+\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\b2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000b\u001a=\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000bH\u0007\u00a2\u0006\u0002\u0010\r\u001a+\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010\"\u0002H\u0002\u00a2\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00050\u000bH\u0002\u00a2\u0006\u0002\b\u0016\u001a)\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00170\u0001H\u0007\u00a2\u0006\u0002\b\u0018\u001a\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a@\u0010\u001c\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001e0\u001d\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00150\u001d0\u0001\u00a8\u0006\u001f"}, m27d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "generateSequence", "", "nextFunction", "seedFunction", "Lkotlin/Function1;", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "R", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, m28xs = "kotlin/sequences/SequencesKt")
/* loaded from: L-out.jar:kotlin/sequences/SequencesKt__SequencesKt.class */
class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    @InlineOnly
    private static final Sequence Sequence(Function0 function0) {
        return new Sequence(function0) { // from class: kotlin.sequences.SequencesKt__SequencesKt.Sequence.1
            final Function0 $iterator;

            {
                this.$iterator = function0;
            }

            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator iterator() {
                return (Iterator) this.$iterator.invoke();
            }
        };
    }

    @NotNull
    public static final Sequence asSequence(@NotNull final Iterator asSequence) {
        Intrinsics.checkParameterIsNotNull(asSequence, "$this$asSequence");
        return SequencesKt.constrainOnce(new Sequence(asSequence) { // from class: kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1
            final Iterator $this_asSequence$inlined;

            {
                this.$this_asSequence$inlined = asSequence;
            }

            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator iterator() {
                return this.$this_asSequence$inlined;
            }
        });
    }

    @NotNull
    public static final Sequence sequenceOf(@NotNull Object[] elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? SequencesKt.emptySequence() : ArraysKt.asSequence(elements);
    }

    @NotNull
    public static final Sequence emptySequence() {
        return EmptySequence.INSTANCE;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Sequence orEmpty(@Nullable Sequence sequence) {
        return sequence != null ? sequence : SequencesKt.emptySequence();
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0010\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@\u00a2\u0006\u0004\b\u0004\u0010\u0005"}, m27d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"})
    @DebugMetadata(m34f = "Sequences.kt", m35l = {OPCode.NULL_CHECK_END, OPCode.NULL_CHECK_END_MEMST_PUSH}, m38i = {0, 0, 1, 1}, m37s = {"L$0", "L$1", "L$0", "L$1"}, m36n = {"$this$sequence", "iterator", "$this$sequence", "iterator"}, m39m = "invokeSuspend", m40c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1")
    /* renamed from: kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1 */
    /* loaded from: L-out.jar:kotlin/sequences/SequencesKt__SequencesKt$ifEmpty$1.class */
    static final class C03601 extends RestrictedSuspendLambda implements Function2 {

        /* renamed from: p$ */
        private SequenceScope f102p$;
        Object L$0;
        Object L$1;
        int label;
        final Sequence $this_ifEmpty;
        final Function0 $defaultValue;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03601(Sequence sequence, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$this_ifEmpty = sequence;
            this.$defaultValue = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation create(@Nullable Object obj, @NotNull Continuation completion) {
            Intrinsics.checkParameterIsNotNull(completion, "completion");
            C03601 c03601 = new C03601(this.$this_ifEmpty, this.$defaultValue, completion);
            c03601.f102p$ = (SequenceScope) obj;
            return c03601;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C03601) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    SequenceScope sequenceScope = this.f102p$;
                    Iterator it = this.$this_ifEmpty.iterator();
                    if (it.hasNext()) {
                        this.L$0 = sequenceScope;
                        this.L$1 = it;
                        this.label = 1;
                        if (sequenceScope.yieldAll(it, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        Sequence sequence = (Sequence) this.$defaultValue.invoke();
                        this.L$0 = sequenceScope;
                        this.L$1 = it;
                        this.label = 2;
                        if (sequenceScope.yieldAll(sequence, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure(obj);
                    break;
                case 2:
                    ResultKt.throwOnFailure(obj);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Sequence ifEmpty(@NotNull Sequence ifEmpty, @NotNull Function0 defaultValue) {
        Intrinsics.checkParameterIsNotNull(ifEmpty, "$this$ifEmpty");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        return SequencesKt.sequence(new C03601(ifEmpty, defaultValue, null));
    }

    @NotNull
    public static final Sequence flatten(@NotNull Sequence flatten) {
        Intrinsics.checkParameterIsNotNull(flatten, "$this$flatten");
        return flatten$SequencesKt__SequencesKt(flatten, new Function1() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((Sequence) obj);
            }

            @NotNull
            public final Iterator invoke(@NotNull Sequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.iterator();
            }
        });
    }

    @JvmName(name = "flattenSequenceOfIterable")
    @NotNull
    public static final Sequence flattenSequenceOfIterable(@NotNull Sequence flatten) {
        Intrinsics.checkParameterIsNotNull(flatten, "$this$flatten");
        return flatten$SequencesKt__SequencesKt(flatten, new Function1() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.2
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((Iterable) obj);
            }

            @NotNull
            public final Iterator invoke(@NotNull Iterable it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.iterator();
            }
        });
    }

    private static final Sequence flatten$SequencesKt__SequencesKt(@NotNull Sequence sequence, Function1 function1) {
        if (sequence instanceof TransformingSequence) {
            return ((TransformingSequence) sequence).flatten$kotlin_stdlib(function1);
        }
        return new FlatteningSequence(sequence, new Function1() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.3
        }, function1);
    }

    @NotNull
    public static final Pair unzip(@NotNull Sequence unzip) {
        Intrinsics.checkParameterIsNotNull(unzip, "$this$unzip");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = unzip.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.m32to(arrayList, arrayList2);
    }

    @NotNull
    public static final Sequence constrainOnce(@NotNull Sequence constrainOnce) {
        Intrinsics.checkParameterIsNotNull(constrainOnce, "$this$constrainOnce");
        return constrainOnce instanceof ConstrainedOnceSequence ? constrainOnce : new ConstrainedOnceSequence(constrainOnce);
    }

    @NotNull
    public static final Sequence generateSequence(@NotNull Function0 nextFunction) {
        Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
        return SequencesKt.constrainOnce(new GeneratorSequence(nextFunction, new Function1(nextFunction) { // from class: kotlin.sequences.SequencesKt__SequencesKt.generateSequence.1
            final Function0 $nextFunction;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$nextFunction = nextFunction;
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final Object invoke(@NotNull Object it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return this.$nextFunction.invoke();
            }
        }));
    }

    @LowPriorityInOverloadResolution
    @NotNull
    public static final Sequence generateSequence(@Nullable Object obj, @NotNull Function1 nextFunction) {
        Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
        if (obj == null) {
            return EmptySequence.INSTANCE;
        }
        return new GeneratorSequence(new Function0(obj) { // from class: kotlin.sequences.SequencesKt__SequencesKt.generateSequence.2
            final Object $seed;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.$seed = obj;
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final Object invoke() {
                return this.$seed;
            }
        }, nextFunction);
    }

    @NotNull
    public static final Sequence generateSequence(@NotNull Function0 seedFunction, @NotNull Function1 nextFunction) {
        Intrinsics.checkParameterIsNotNull(seedFunction, "seedFunction");
        Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
        return new GeneratorSequence(seedFunction, nextFunction);
    }
}

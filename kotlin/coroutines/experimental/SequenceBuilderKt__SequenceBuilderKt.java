package kotlin.coroutines.experimental;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.BuilderInference;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aM\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\ufffd\ufffd\u0010\n2/\b\u0001\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f\u00a2\u0006\u0002\b\u0011H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0012\u001aM\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\n0\u0014\"\u0004\b\ufffd\ufffd\u0010\n2/\b\u0001\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f\u00a2\u0006\u0002\b\u0011H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0015\"\u0012\u0010\ufffd\ufffd\u001a\u00060\u0001j\u0002`\u0002X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\"\u0012\u0010\u0003\u001a\u00060\u0001j\u0002`\u0002X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\"\u0012\u0010\u0004\u001a\u00060\u0001j\u0002`\u0002X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\"\u0012\u0010\u0005\u001a\u00060\u0001j\u0002`\u0002X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\"\u0012\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\"\u0012\u0010\u0007\u001a\u00060\u0001j\u0002`\u0002X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd*\f\b\u0002\u0010\u0016\"\u00020\u00012\u00020\u0001\u0082\u0002\u0004\n\u0002\b\t\u00a8\u0006\u0017"}, m27d2 = {"State_Done", "", "Lkotlin/coroutines/experimental/State;", "State_Failed", "State_ManyNotReady", "State_ManyReady", "State_NotReady", "State_Ready", "buildIterator", "", "T", "builderAction", "Lkotlin/Function2;", "Lkotlin/coroutines/experimental/SequenceBuilder;", "Lkotlin/coroutines/experimental/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "buildSequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/jvm/functions/Function2;)Lkotlin/sequences/Sequence;", "State", "kotlin-stdlib-coroutines"}, m28xs = "kotlin/coroutines/experimental/SequenceBuilderKt")
/* loaded from: L-out.jar:kotlin/coroutines/experimental/SequenceBuilderKt__SequenceBuilderKt.class */
class SequenceBuilderKt__SequenceBuilderKt {
    private static final int State_NotReady = 0;
    private static final int State_ManyNotReady = 1;
    private static final int State_ManyReady = 2;
    private static final int State_Ready = 3;
    private static final int State_Done = 4;
    private static final int State_Failed = 5;

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Sequence buildSequence(@BuilderInference @NotNull final Function2 builderAction) {
        Intrinsics.checkParameterIsNotNull(builderAction, "builderAction");
        return new Sequence(builderAction) { // from class: kotlin.coroutines.experimental.SequenceBuilderKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1
            final Function2 $builderAction$inlined;

            {
                this.$builderAction$inlined = builderAction;
            }

            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator iterator() {
                return SequenceBuilderKt.buildIterator(this.$builderAction$inlined);
            }
        };
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Iterator buildIterator(@BuilderInference @NotNull Function2 builderAction) {
        Intrinsics.checkParameterIsNotNull(builderAction, "builderAction");
        SequenceBuilderIterator sequenceBuilderIterator = new SequenceBuilderIterator();
        sequenceBuilderIterator.setNextStep(IntrinsicsKt.createCoroutineUnchecked(builderAction, sequenceBuilderIterator, sequenceBuilderIterator));
        return sequenceBuilderIterator;
    }
}

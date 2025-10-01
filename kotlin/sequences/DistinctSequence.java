package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\tH\u0096\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\n"}, m27d2 = {"Lkotlin/sequences/DistinctSequence;", "T", "K", "Lkotlin/sequences/Sequence;", "source", "keySelector", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/DistinctSequence.class */
public final class DistinctSequence implements Sequence {
    private final Sequence source;
    private final Function1 keySelector;

    public DistinctSequence(@NotNull Sequence source, @NotNull Function1 keySelector) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        this.source = source;
        this.keySelector = keySelector;
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new DistinctIterator(this.source.iterator(), this.keySelector);
    }
}

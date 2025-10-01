package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.collections.EmptyIterator;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\u0002\b\u00c2\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\tH\u0096\u0002J\u0010\u0010\n\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\u000b"}, m27d2 = {"Lkotlin/sequences/EmptySequence;", "Lkotlin/sequences/Sequence;", "", "Lkotlin/sequences/DropTakeSequence;", "()V", "drop", "n", "", "iterator", "", "take", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/EmptySequence.class */
final class EmptySequence implements Sequence, DropTakeSequence {
    public static final EmptySequence INSTANCE = new EmptySequence();

    @Override // kotlin.sequences.DropTakeSequence
    public Sequence drop(int i) {
        return drop(i);
    }

    @Override // kotlin.sequences.DropTakeSequence
    public Sequence take(int i) {
        return take(i);
    }

    private EmptySequence() {
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return EmptyIterator.INSTANCE;
    }

    @Override // kotlin.sequences.DropTakeSequence
    @NotNull
    public EmptySequence drop(int i) {
        return INSTANCE;
    }

    @Override // kotlin.sequences.DropTakeSequence
    @NotNull
    public EmptySequence take(int i) {
        return INSTANCE;
    }
}

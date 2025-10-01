package kotlin.sequences;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002\u00a2\u0006\u0002\u0010\u0004J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\tH\u0096\u0002R(\u0010\u0005\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00028\ufffd\ufffd \u0007*\n\u0012\u0004\u0012\u00028\ufffd\ufffd\u0018\u00010\u00020\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\n"}, m27d2 = {"Lkotlin/sequences/ConstrainedOnceSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "(Lkotlin/sequences/Sequence;)V", "sequenceRef", "Ljava/util/concurrent/atomic/AtomicReference;", "kotlin.jvm.PlatformType", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/ConstrainedOnceSequence.class */
public final class ConstrainedOnceSequence implements Sequence {
    private final AtomicReference sequenceRef;

    public ConstrainedOnceSequence(@NotNull Sequence sequence) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        this.sequenceRef = new AtomicReference(sequence);
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        Sequence sequence = (Sequence) this.sequenceRef.getAndSet(null);
        if (sequence != null) {
            return sequence.iterator();
        }
        throw new IllegalStateException("This sequence can be consumed only once.");
    }
}

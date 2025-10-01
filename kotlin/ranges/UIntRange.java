package kotlin.ranges;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, m27d2 = {"Lkotlin/ranges/UIntRange;", "Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/UInt;", "start", "endInclusive", "(IILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/UInt;", "getStart", "contains", "", PropertyDescriptor.VALUE, "contains-WZ4Q5Ns", "(I)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/ranges/UIntRange.class */
public final class UIntRange extends UIntProgression implements ClosedRange {
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final UIntRange EMPTY = new UIntRange(-1, 0, null);

    @Override // kotlin.ranges.ClosedRange
    public Comparable getStart() {
        return getStart();
    }

    @Override // kotlin.ranges.ClosedRange
    public Comparable getEndInclusive() {
        return getEndInclusive();
    }

    @Override // kotlin.ranges.ClosedRange
    public boolean contains(Comparable comparable) {
        return m1397containsWZ4Q5Ns(((UInt) comparable).m607unboximpl());
    }

    private UIntRange(int i, int i2) {
        super(i, i2, 1, null);
    }

    public UIntRange(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2);
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public UInt getStart() {
        return UInt.m605boximpl(getFirst());
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public UInt getEndInclusive() {
        return UInt.m605boximpl(getLast());
    }

    /* renamed from: contains-WZ4Q5Ns, reason: not valid java name */
    public boolean m1397containsWZ4Q5Ns(int i) {
        return UnsignedKt.uintCompare(getFirst(), i) <= 0 && UnsignedKt.uintCompare(i, getLast()) <= 0;
    }

    @Override // kotlin.ranges.UIntProgression, kotlin.ranges.ClosedRange
    public boolean isEmpty() {
        return UnsignedKt.uintCompare(getFirst(), getLast()) > 0;
    }

    @Override // kotlin.ranges.UIntProgression
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof UIntRange) && ((isEmpty() && ((UIntRange) obj).isEmpty()) || (getFirst() == ((UIntRange) obj).getFirst() && getLast() == ((UIntRange) obj).getLast()));
    }

    @Override // kotlin.ranges.UIntProgression
    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * getFirst()) + getLast();
    }

    @Override // kotlin.ranges.UIntProgression
    @NotNull
    public String toString() {
        return UInt.m604toStringimpl(getFirst()) + ".." + UInt.m604toStringimpl(getLast());
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/ranges/UIntRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/UIntRange;", "getEMPTY", "()Lkotlin/ranges/UIntRange;", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/ranges/UIntRange$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final UIntRange getEMPTY() {
            return UIntRange.EMPTY;
        }
    }
}

package kotlin.ranges;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, m27d2 = {"Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/ULong;", "getStart", "contains", "", PropertyDescriptor.VALUE, "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/ranges/ULongRange.class */
public final class ULongRange extends ULongProgression implements ClosedRange {
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final ULongRange EMPTY = new ULongRange(-1, 0, null);

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
        return m1401containsVKZWuLQ(((ULong) comparable).m665unboximpl());
    }

    private ULongRange(long j, long j2) {
        super(j, j2, 1L, null);
    }

    public ULongRange(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public ULong getStart() {
        return ULong.m663boximpl(getFirst());
    }

    @Override // kotlin.ranges.ClosedRange
    @NotNull
    public ULong getEndInclusive() {
        return ULong.m663boximpl(getLast());
    }

    /* renamed from: contains-VKZWuLQ, reason: not valid java name */
    public boolean m1401containsVKZWuLQ(long j) {
        return (getFirst() ^ Long.MIN_VALUE) <= (j ^ Long.MIN_VALUE) && (j ^ Long.MIN_VALUE) <= (getLast() ^ Long.MIN_VALUE);
    }

    @Override // kotlin.ranges.ULongProgression, kotlin.ranges.ClosedRange
    public boolean isEmpty() {
        return (getFirst() ^ Long.MIN_VALUE) > (getLast() ^ Long.MIN_VALUE);
    }

    @Override // kotlin.ranges.ULongProgression
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ULongRange) && ((isEmpty() && ((ULongRange) obj).isEmpty()) || (getFirst() == ((ULongRange) obj).getFirst() && getLast() == ((ULongRange) obj).getLast()));
    }

    @Override // kotlin.ranges.ULongProgression
    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * ((int) (getFirst() ^ (getFirst() >>> 32)))) + ((int) (getLast() ^ (getLast() >>> 32)));
    }

    @Override // kotlin.ranges.ULongProgression
    @NotNull
    public String toString() {
        return ULong.m662toStringimpl(getFirst()) + ".." + ULong.m662toStringimpl(getLast());
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/ranges/ULongRange$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final ULongRange getEMPTY() {
            return ULongRange.EMPTY;
        }
    }
}

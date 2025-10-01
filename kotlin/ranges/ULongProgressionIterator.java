package kotlin.ranges;

import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.ULongIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u000bH\u0096\u0002J\u0010\u0010\r\u001a\u00020\u0003H\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000eR\u0013\u0010\b\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0013\u0010\f\u001a\u00020\u0003X\u0082\u000e\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/ranges/ULongProgressionIterator;", "Lkotlin/collections/ULongIterator;", "first", "Lkotlin/ULong;", "last", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "finalElement", "J", "hasNext", "", "next", "nextULong", "()J", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/ranges/ULongProgressionIterator.class */
final class ULongProgressionIterator extends ULongIterator {
    private final long finalElement;
    private boolean hasNext;
    private final long step;
    private long next;

    private ULongProgressionIterator(long j, long j2, long j3) {
        ULongProgressionIterator uLongProgressionIterator;
        boolean z;
        this.finalElement = j2;
        if (j3 > 0) {
            uLongProgressionIterator = this;
            z = (j ^ Long.MIN_VALUE) <= (j2 ^ Long.MIN_VALUE);
        } else {
            uLongProgressionIterator = this;
            z = (j ^ Long.MIN_VALUE) >= (j2 ^ Long.MIN_VALUE);
        }
        uLongProgressionIterator.hasNext = z;
        this.step = j3;
        this.next = this.hasNext ? j : this.finalElement;
    }

    public ULongProgressionIterator(long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override // kotlin.collections.ULongIterator
    public long nextULong() {
        long j = this.next;
        if (j == this.finalElement) {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            this.hasNext = false;
        } else {
            this.next += this.step;
        }
        return j;
    }
}

package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\ufffd\ufffd\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\b\b\u0002\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007B\u001d\u0012\u000e\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\fJ\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\ufffd\ufffd2\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00028\ufffd\ufffd2\u0006\u0010\u001a\u001a\u00020\u0006H\u0096\u0002\u00a2\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u001fH\u0096\u0002J\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0006J\u0015\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014\u00a2\u0006\u0002\u0010#J'\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0014\u00a2\u0006\u0002\u0010%J\u0015\u0010&\u001a\u00020\u0006*\u00020\u00062\u0006\u0010!\u001a\u00020\u0006H\u0082\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u0096\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006'"}, m27d2 = {"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "filledSize", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "expanded", "maxCapacity", PropertyDescriptor.GET, "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "forward", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/RingBuffer.class */
final class RingBuffer extends AbstractList implements RandomAccess {
    private final int capacity;
    private int startIndex;
    private int size;
    private final Object[] buffer;

    public RingBuffer(@NotNull Object[] buffer, int i) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        this.buffer = buffer;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("ring buffer filled size should not be negative but it is " + i).toString());
        }
        if (!(i <= this.buffer.length)) {
            throw new IllegalArgumentException(("ring buffer filled size: " + i + " cannot be larger than the buffer size: " + this.buffer.length).toString());
        }
        this.capacity = this.buffer.length;
        this.size = i;
    }

    public RingBuffer(int i) {
        this(new Object[i], 0);
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public Object get(int i) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        return this.buffer[(this.startIndex + i) % this.capacity];
    }

    public final boolean isFull() {
        return size() == this.capacity;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
    @NotNull
    public Iterator iterator() {
        return new AbstractIterator(this) { // from class: kotlin.collections.RingBuffer.iterator.1
            private int count;
            private int index;
            final RingBuffer this$0;

            {
                this.this$0 = this;
                this.count = this.size();
                this.index = this.startIndex;
            }

            @Override // kotlin.collections.AbstractIterator
            protected void computeNext() {
                if (this.count == 0) {
                    done();
                    return;
                }
                setNext(this.this$0.buffer[this.index]);
                this.index = (this.index + 1) % this.this$0.capacity;
                this.count--;
            }
        };
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    @NotNull
    public Object[] toArray(@NotNull Object[] array) {
        Object[] objArrCopyOf;
        Intrinsics.checkParameterIsNotNull(array, "array");
        if (array.length < size()) {
            objArrCopyOf = Arrays.copyOf(array, size());
            Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        } else {
            objArrCopyOf = array;
        }
        Object[] objArr = objArrCopyOf;
        int size = size();
        int i = 0;
        for (int i2 = this.startIndex; i < size && i2 < this.capacity; i2++) {
            objArr[i] = this.buffer[i2];
            i++;
        }
        int i3 = 0;
        while (i < size) {
            objArr[i] = this.buffer[i3];
            i++;
            i3++;
        }
        if (objArr.length > size()) {
            objArr[size()] = null;
        }
        if (objArr == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return objArr;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    @NotNull
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    @NotNull
    public final RingBuffer expanded(int i) {
        Object[] array;
        int iCoerceAtMost = RangesKt.coerceAtMost(this.capacity + (this.capacity >> 1) + 1, i);
        if (this.startIndex == 0) {
            array = Arrays.copyOf(this.buffer, iCoerceAtMost);
            Intrinsics.checkExpressionValueIsNotNull(array, "java.util.Arrays.copyOf(this, newSize)");
        } else {
            array = toArray(new Object[iCoerceAtMost]);
        }
        return new RingBuffer(array, size());
    }

    @Override // java.util.Collection, java.util.List
    public final void add(Object obj) {
        if (!isFull()) {
            this.buffer[(this.startIndex + size()) % this.capacity] = obj;
            this.size = size() + 1;
            return;
        }
        throw new IllegalStateException("ring buffer is full");
    }

    public final void removeFirst(int i) {
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("n shouldn't be negative but it is " + i).toString());
        }
        if (!(i <= size())) {
            throw new IllegalArgumentException(("n shouldn't be greater than the buffer size: n = " + i + ", size = " + size()).toString());
        }
        if (i > 0) {
            int i2 = this.startIndex;
            int i3 = (i2 + i) % this.capacity;
            if (i2 <= i3) {
                ArraysKt.fill(this.buffer, (Object) null, i2, i3);
            } else {
                ArraysKt.fill(this.buffer, (Object) null, i2, this.capacity);
                ArraysKt.fill(this.buffer, (Object) null, 0, i3);
            }
            this.startIndex = i3;
            this.size = size() - i;
        }
    }

    private final int forward(int i, int i2) {
        return (i + i2) % this.capacity;
    }
}

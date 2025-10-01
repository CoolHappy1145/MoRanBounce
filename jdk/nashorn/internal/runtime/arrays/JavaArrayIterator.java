package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/JavaArrayIterator.class */
class JavaArrayIterator extends ArrayLikeIterator {
    protected final Object array;
    protected final long length;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaArrayIterator.class.desiredAssertionStatus();
    }

    protected JavaArrayIterator(Object obj, boolean z) {
        super(z);
        if (!$assertionsDisabled && !obj.getClass().isArray()) {
            throw new AssertionError("expecting Java array object");
        }
        this.array = obj;
        this.length = Array.getLength(obj);
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // java.util.Iterator
    public Object next() {
        return Array.get(this.array, (int) bumpIndex());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long getLength() {
        return this.length;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return indexInArray();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ScriptArrayIterator.class */
class ScriptArrayIterator extends ArrayLikeIterator {
    protected final ScriptObject array;
    protected final long length;

    protected ScriptArrayIterator(ScriptObject scriptObject, boolean z) {
        super(z);
        this.array = scriptObject;
        this.length = scriptObject.getArray().length();
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // java.util.Iterator
    public Object next() {
        return this.array.get(bumpIndex());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long getLength() {
        return this.length;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.includeUndefined) {
            while (indexInArray() && !this.array.has(this.index)) {
                bumpIndex();
            }
        }
        return indexInArray();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator, java.util.Iterator
    public void remove() {
        this.array.delete(this.index, false);
    }
}

package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ScriptObjectIterator.class */
class ScriptObjectIterator extends ArrayLikeIterator {
    protected final ScriptObject obj;
    private final long length;

    ScriptObjectIterator(ScriptObject scriptObject, boolean z) {
        super(z);
        this.obj = scriptObject;
        this.length = JSType.toUint32(scriptObject.getLength());
        this.index = 0L;
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long getLength() {
        return this.length;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.length == 0) {
            return false;
        }
        while (indexInArray() && !this.obj.has(this.index) && !this.includeUndefined) {
            bumpIndex();
        }
        return indexInArray();
    }

    @Override // java.util.Iterator
    public Object next() {
        if (indexInArray()) {
            return this.obj.get(bumpIndex());
        }
        throw new NoSuchElementException();
    }
}

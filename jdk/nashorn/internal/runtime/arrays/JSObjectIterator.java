package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/JSObjectIterator.class */
class JSObjectIterator extends ArrayLikeIterator {
    protected final JSObject obj;
    private final long length;

    JSObjectIterator(JSObject jSObject, boolean z) {
        super(z);
        this.obj = jSObject;
        this.length = JSType.toUint32(jSObject.hasMember("length") ? jSObject.getMember("length") : 0);
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
        while (indexInArray() && !this.obj.hasSlot((int) this.index) && !this.includeUndefined) {
            bumpIndex();
        }
        return indexInArray();
    }

    @Override // java.util.Iterator
    public Object next() {
        if (indexInArray()) {
            return this.obj.getSlot((int) bumpIndex());
        }
        throw new NoSuchElementException();
    }
}

package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/EmptyArrayLikeIterator.class */
final class EmptyArrayLikeIterator extends ArrayLikeIterator {
    EmptyArrayLikeIterator() {
        super(false);
    }

    @Override // java.util.Iterator
    public Object next() {
        throw new NoSuchElementException();
    }
}

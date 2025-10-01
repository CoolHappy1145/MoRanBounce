package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0013\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\t\u0010\u0002\u001a\u00020\u0003H\u0096\u0002J\u000e\u0010\u0004\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, m27d2 = {"kotlin/collections/AbstractMap$values$1$iterator$1", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/AbstractMap$values$1$iterator$1.class */
public final class AbstractMap$values$1$iterator$1 implements Iterator, KMappedMarker {
    final Iterator $entryIterator;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    AbstractMap$values$1$iterator$1(Iterator it) {
        this.$entryIterator = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.$entryIterator.hasNext();
    }

    @Override // java.util.Iterator
    public Object next() {
        return ((Map.Entry) this.$entryIterator.next()).getValue();
    }
}

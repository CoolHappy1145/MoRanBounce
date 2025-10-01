package kotlin.collections;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0010\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\b\u0005\b'\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\f\u0010\u0004\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\ufffd\ufffdJ\u0010\u0010\u0005\u001a\u00020\u0002H&\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/collections/UByteIterator;", "", "Lkotlin/UByte;", "()V", "next", "nextUByte", "()B", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/collections/UByteIterator.class */
public abstract class UByteIterator implements Iterator, KMappedMarker {
    public abstract byte nextUByte();

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Iterator
    public Object next() {
        return next();
    }

    @Override // java.util.Iterator
    @NotNull
    public final UByte next() {
        return UByte.m547boximpl(nextUByte());
    }
}

package kotlin.collections;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0011\n\ufffd\ufffd\n\u0002\u0010\u001c\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0003H\u0096\u0002\u00a8\u0006\u0004\u00b8\u0006\ufffd\ufffd"}, m27d2 = {"kotlin/collections/CollectionsKt__IterablesKt$Iterable$1", "", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/ArraysKt___ArraysKt$asIterable$$inlined$Iterable$6.class */
public final class ArraysKt___ArraysKt$asIterable$$inlined$Iterable$6 implements Iterable, KMappedMarker {
    final float[] $this_asIterable$inlined;

    public ArraysKt___ArraysKt$asIterable$$inlined$Iterable$6(float[] fArr) {
        this.$this_asIterable$inlined = fArr;
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator iterator() {
        return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
    }
}

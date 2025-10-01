package kotlin.collections;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0003\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0015\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u00030\u0006H\u0096\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lkotlin/collections/IndexingIterable;", "T", "", "Lkotlin/collections/IndexedValue;", "iteratorFactory", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)V", "iterator", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/IndexingIterable.class */
public final class IndexingIterable implements Iterable, KMappedMarker {
    private final Function0 iteratorFactory;

    public IndexingIterable(@NotNull Function0 iteratorFactory) {
        Intrinsics.checkParameterIsNotNull(iteratorFactory, "iteratorFactory");
        this.iteratorFactory = iteratorFactory;
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator iterator() {
        return new IndexingIterator((Iterator) this.iteratorFactory.invoke());
    }
}

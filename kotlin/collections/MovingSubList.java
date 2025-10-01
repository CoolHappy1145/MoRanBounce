package kotlin.collections;

import java.util.List;
import java.util.RandomAccess;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0006\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u000e\u001a\u00028\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\tH\u0096\u0002\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0014"}, m27d2 = {"Lkotlin/collections/MovingSubList;", "E", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "list", "", "(Ljava/util/List;)V", "_size", "", "fromIndex", "size", "getSize", "()I", PropertyDescriptor.GET, "index", "(I)Ljava/lang/Object;", "move", "", "toIndex", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/MovingSubList.class */
public final class MovingSubList extends AbstractList implements RandomAccess {
    private int fromIndex;
    private int _size;
    private final List list;

    public MovingSubList(@NotNull List list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        this.list = list;
    }

    public final void move(int i, int i2) {
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, this.list.size());
        this.fromIndex = i;
        this._size = i2 - i;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public Object get(int i) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, this._size);
        return this.list.get(this.fromIndex + i);
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this._size;
    }
}

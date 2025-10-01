package kotlin.collections;

import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.markers.KMutableList;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0007\b'\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\ufffd\ufffdH&\u00a2\u0006\u0002\u0010\nJ\u0015\u0010\u000b\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0007\u001a\u00020\bH&\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\ufffd\ufffdH\u00a6\u0002\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/collections/AbstractMutableList;", "E", "", "Ljava/util/AbstractList;", "()V", "add", "", "index", "", "element", "(ILjava/lang/Object;)V", "removeAt", "(I)Ljava/lang/Object;", PropertyDescriptor.SET, "(ILjava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/AbstractMutableList.class */
public abstract class AbstractMutableList extends java.util.AbstractList implements List, KMutableList {
    @Override // java.util.AbstractList, java.util.List
    public abstract Object set(int i, Object obj);

    public abstract Object removeAt(int i);

    @Override // java.util.AbstractList, java.util.List
    public abstract void add(int i, Object obj);

    public abstract int getSize();

    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        return removeAt(i);
    }

    protected AbstractMutableList() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return getSize();
    }
}

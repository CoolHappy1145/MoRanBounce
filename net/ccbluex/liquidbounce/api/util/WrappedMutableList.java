package net.ccbluex.liquidbounce.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd@\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0010+\n\u0002\b\b\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u000e\b\u0002\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00052\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\"B5\u0012\u0006\u0010\u0006\u001a\u00028\u0002\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\ufffd\ufffd0\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\b\u00a2\u0006\u0002\u0010\nJ\u001d\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014H\u0016J\u0016\u0010\u0015\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0002\u00a2\u0006\u0002\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u0018J\u0015\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u001bH\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u001b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0015\u0010\u001c\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010\u001d\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0001H\u0096\u0002\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00042\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000eH\u0016\u00a8\u0006#"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/WrappedMutableList;", "O", "T", "C", "", "Lnet/ccbluex/liquidbounce/api/util/WrappedMutableCollection;", "wrapped", "unwrapper", "Lkotlin/Function1;", "wrapper", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "add", "", "index", "", "element", "(ILjava/lang/Object;)V", "addAll", "", "elements", "", PropertyDescriptor.GET, "(I)Ljava/lang/Object;", "indexOf", "(Ljava/lang/Object;)I", "lastIndexOf", "listIterator", "", "removeAt", PropertyDescriptor.SET, "(ILjava/lang/Object;)Ljava/lang/Object;", "subList", "fromIndex", "toIndex", "WrappedMutableCollectionIterator", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/WrappedMutableList.class */
public final class WrappedMutableList extends WrappedMutableCollection implements List, KMutableList {
    @Override // java.util.List
    public final Object remove(int i) {
        return removeAt(i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrappedMutableList(@NotNull List wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
        super(wrapped, unwrapper, wrapper);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
        Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
    }

    @Override // java.util.List
    public Object get(int i) {
        return getWrapper().invoke(((List) getWrapped()).get(i));
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return ((List) getWrapped()).indexOf(getUnwrapper().invoke(obj));
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return ((List) getWrapped()).lastIndexOf(getUnwrapper().invoke(obj));
    }

    @Override // java.util.List
    public void add(int i, Object obj) {
        ((List) getWrapped()).add(i, getUnwrapper().invoke(obj));
    }

    @Override // java.util.List
    public boolean addAll(int i, @NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        List list = (List) getWrapped();
        Collection collection = elements;
        Function1 unwrapper = getUnwrapper();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(unwrapper.invoke(it.next()));
        }
        return list.addAll(i, arrayList);
    }

    @Override // java.util.List
    @NotNull
    public ListIterator listIterator() {
        return new WrappedMutableCollectionIterator(((List) getWrapped()).listIterator(), getWrapper(), getUnwrapper());
    }

    @Override // java.util.List
    @NotNull
    public ListIterator listIterator(int i) {
        return new WrappedMutableCollectionIterator(((List) getWrapped()).listIterator(i), getWrapper(), getUnwrapper());
    }

    public Object removeAt(int i) {
        return getWrapper().invoke(((List) getWrapped()).remove(i));
    }

    @Override // java.util.List
    public Object set(int i, Object obj) {
        return getWrapper().invoke(((List) getWrapped()).set(i, getUnwrapper().invoke(obj)));
    }

    @Override // java.util.List
    @NotNull
    public List subList(int i, int i2) {
        return new WrappedMutableList(((List) getWrapped()).subList(i, i2), getUnwrapper(), getWrapper());
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010+\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\u0018\ufffd\ufffd*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B;\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00028\u00030\u0006\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0004H\u0016\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u0012\u001a\u00020\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u000e\u0010\u0015\u001a\u00028\u0004H\u0096\u0002\u00a2\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\r\u0010\u0019\u001a\u00028\u0004H\u0016\u00a2\u0006\u0002\u0010\u0016J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\u0015\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0004H\u0016\u00a2\u0006\u0002\u0010\u0011R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00028\u00030\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\n\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/WrappedMutableList$WrappedMutableCollectionIterator;", "O", "T", "", "wrapped", "wrapper", "Lkotlin/Function1;", "unwrapper", "(Ljava/util/ListIterator;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getUnwrapper", "()Lkotlin/jvm/functions/Function1;", "getWrapped", "()Ljava/util/ListIterator;", "getWrapper", "add", "", "element", "(Ljava/lang/Object;)V", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "", "previous", "previousIndex", "remove", PropertyDescriptor.SET, LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/WrappedMutableList$WrappedMutableCollectionIterator.class */
    public static final class WrappedMutableCollectionIterator implements ListIterator, KMutableListIterator {

        @NotNull
        private final ListIterator wrapped;

        @NotNull
        private final Function1 wrapper;

        @NotNull
        private final Function1 unwrapper;

        @NotNull
        public final ListIterator getWrapped() {
            return this.wrapped;
        }

        @NotNull
        public final Function1 getWrapper() {
            return this.wrapper;
        }

        @NotNull
        public final Function1 getUnwrapper() {
            return this.unwrapper;
        }

        public WrappedMutableCollectionIterator(@NotNull ListIterator wrapped, @NotNull Function1 wrapper, @NotNull Function1 unwrapper) {
            Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
            Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
            Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
            this.wrapped = wrapped;
            this.wrapper = wrapper;
            this.unwrapper = unwrapper;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.wrapped.hasNext();
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.wrapped.hasPrevious();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public Object next() {
            return this.wrapper.invoke(this.wrapped.next());
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.wrapped.nextIndex();
        }

        @Override // java.util.ListIterator
        public Object previous() {
            return this.wrapper.invoke(this.wrapped.previous());
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.wrapped.previousIndex();
        }

        @Override // java.util.ListIterator
        public void add(Object obj) {
            this.wrapped.add(this.unwrapper.invoke(obj));
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            this.wrapped.remove();
        }

        @Override // java.util.ListIterator
        public void set(Object obj) {
            this.wrapped.set(this.unwrapper.invoke(obj));
        }
    }
}

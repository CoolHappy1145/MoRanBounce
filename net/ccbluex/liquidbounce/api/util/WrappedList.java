package net.ccbluex.liquidbounce.api.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010*\n\u0002\b\u0005\b\u0016\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u000e\b\u0002\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00052\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\u0018B5\u0012\u0006\u0010\u0006\u001a\u00028\u0002\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\ufffd\ufffd0\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\b\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00028\u00012\u0006\u0010\f\u001a\u00020\rH\u0096\u0002\u00a2\u0006\u0002\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u0011J\u0015\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014H\u0016J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u00142\u0006\u0010\f\u001a\u00020\rH\u0016J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00010\u00042\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\rH\u0016\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/WrappedList;", "O", "T", "C", "", "Lnet/ccbluex/liquidbounce/api/util/WrappedCollection;", "wrapped", "unwrapper", "Lkotlin/Function1;", "wrapper", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", PropertyDescriptor.GET, "index", "", "(I)Ljava/lang/Object;", "indexOf", "element", "(Ljava/lang/Object;)I", "lastIndexOf", "listIterator", "", "subList", "fromIndex", "toIndex", "WrappedCollectionIterator", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/WrappedList.class */
public class WrappedList extends WrappedCollection implements List, KMappedMarker {
    @Override // java.util.List
    public void add(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public Object remove(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public void replaceAll(UnaryOperator unaryOperator) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public Object set(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public void sort(Comparator comparator) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrappedList(@NotNull List wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
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
        return ((List) getWrapped()).indexOf(getUnwrapper().invoke(obj));
    }

    @Override // java.util.List
    @NotNull
    public ListIterator listIterator() {
        return new WrappedCollectionIterator(((List) getWrapped()).listIterator(), getWrapper());
    }

    @Override // java.util.List
    @NotNull
    public ListIterator listIterator(int i) {
        return new WrappedCollectionIterator(((List) getWrapped()).listIterator(i), getWrapper());
    }

    @Override // java.util.List
    @NotNull
    public List subList(int i, int i2) {
        return new WrappedList(((List) getWrapped()).subList(i, i2), getUnwrapper(), getWrapper());
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\u0018\ufffd\ufffd*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\rH\u0016J\u000e\u0010\u000f\u001a\u00028\u0004H\u0096\u0002\u00a2\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\r\u0010\u0013\u001a\u00028\u0004H\u0016\u00a2\u0006\u0002\u0010\u0010J\b\u0010\u0014\u001a\u00020\u0012H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00030\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/WrappedList$WrappedCollectionIterator;", "O", "T", "", "wrapped", "wrapper", "Lkotlin/Function1;", "(Ljava/util/ListIterator;Lkotlin/jvm/functions/Function1;)V", "getWrapped", "()Ljava/util/ListIterator;", "getWrapper", "()Lkotlin/jvm/functions/Function1;", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "", "previous", "previousIndex", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/WrappedList$WrappedCollectionIterator.class */
    public static final class WrappedCollectionIterator implements ListIterator, KMappedMarker {

        @NotNull
        private final ListIterator wrapped;

        @NotNull
        private final Function1 wrapper;

        @Override // java.util.ListIterator
        public void add(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.ListIterator
        public void set(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @NotNull
        public final ListIterator getWrapped() {
            return this.wrapped;
        }

        @NotNull
        public final Function1 getWrapper() {
            return this.wrapper;
        }

        public WrappedCollectionIterator(@NotNull ListIterator wrapped, @NotNull Function1 wrapper) {
            Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
            Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
            this.wrapped = wrapped;
            this.wrapper = wrapper;
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
    }
}

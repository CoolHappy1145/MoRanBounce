package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.collections.ArraysKt;
import kotlin.collections.UIntIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\ufffd\ufffd\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004H\u00d6\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002\u00a2\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\ufffd\ufffdX\u0081\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\f\u0010\r\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, m27d2 = {"Lkotlin/UIntArray;", "", "Lkotlin/UInt;", "size", "", "constructor-impl", "(I)[I", "storage", "", "([I)[I", "getSize-impl", "([I)I", "storage$annotations", "()V", "contains", "", "element", "contains-WZ4Q5Ns", "([II)Z", "containsAll", "elements", "containsAll-impl", "([ILjava/util/Collection;)Z", "equals", "other", "", PropertyDescriptor.GET, "index", "get-impl", "([II)I", "hashCode", "isEmpty", "isEmpty-impl", "([I)Z", "iterator", "Lkotlin/collections/UIntIterator;", "iterator-impl", "([I)Lkotlin/collections/UIntIterator;", PropertyDescriptor.SET, "", PropertyDescriptor.VALUE, "set-VXSXFK8", "([III)V", "toString", "", "Iterator", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/UIntArray.class */
public final class UIntArray implements Collection, KMappedMarker {

    @NotNull
    private final int[] storage;

    public int getSize() {
        return this.storage.length;
    }

    @Override // java.util.Collection, java.lang.Iterable
    @NotNull
    public UIntIterator iterator() {
        return m611iteratorimpl(this.storage);
    }

    /* renamed from: contains-WZ4Q5Ns, reason: not valid java name */
    public boolean m609containsWZ4Q5Ns(int i) {
        return m612containsWZ4Q5Ns(this.storage, i);
    }

    @Override // java.util.Collection
    public boolean containsAll(@NotNull Collection collection) {
        return m613containsAllimpl(this.storage, collection);
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.storage.length == 0;
    }

    @NotNull
    /* renamed from: box-impl, reason: not valid java name */
    public static final UIntArray m616boximpl(@NotNull int[] v) {
        Intrinsics.checkParameterIsNotNull(v, "v");
        return new UIntArray(v);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m617toStringimpl(int[] iArr) {
        return "UIntArray(storage=" + Arrays.toString(iArr) + ")";
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m618hashCodeimpl(int[] iArr) {
        if (iArr != null) {
            return Arrays.hashCode(iArr);
        }
        return 0;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m619equalsimpl(int[] iArr, @Nullable Object obj) {
        return (obj instanceof UIntArray) && Intrinsics.areEqual(iArr, ((UIntArray) obj).m621unboximpl());
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m620equalsimpl0(@NotNull int[] p1, @NotNull int[] p2) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        Intrinsics.checkParameterIsNotNull(p2, "p2");
        throw null;
    }

    @NotNull
    /* renamed from: unbox-impl, reason: not valid java name */
    public final int[] m621unboximpl() {
        return this.storage;
    }

    public String toString() {
        return m617toStringimpl(this.storage);
    }

    @Override // java.util.Collection
    public int hashCode() {
        return m618hashCodeimpl(this.storage);
    }

    @Override // java.util.Collection
    public boolean equals(Object obj) {
        return m619equalsimpl(this.storage, obj);
    }

    /* renamed from: add-WZ4Q5Ns, reason: not valid java name */
    public boolean m622addWZ4Q5Ns(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }

    @Override // java.util.Collection
    public final int size() {
        return getSize();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public java.util.Iterator iterator() {
        return iterator();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        if (obj instanceof UInt) {
            return m609containsWZ4Q5Ns(((UInt) obj).m607unboximpl());
        }
        return false;
    }

    @PublishedApi
    private UIntArray(@NotNull int[] storage) {
        Intrinsics.checkParameterIsNotNull(storage, "storage");
        this.storage = storage;
    }

    @PublishedApi
    @NotNull
    /* renamed from: constructor-impl, reason: not valid java name */
    public static int[] m614constructorimpl(@NotNull int[] storage) {
        Intrinsics.checkParameterIsNotNull(storage, "storage");
        return storage;
    }

    @NotNull
    /* renamed from: constructor-impl, reason: not valid java name */
    public static int[] m615constructorimpl(int i) {
        return m614constructorimpl(new int[i]);
    }

    /* renamed from: get-impl, reason: not valid java name */
    public static final int m610getimpl(int[] iArr, int i) {
        return iArr[i];
    }

    @NotNull
    /* renamed from: iterator-impl, reason: not valid java name */
    public static UIntIterator m611iteratorimpl(int[] iArr) {
        return new Iterator(iArr);
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, m27d2 = {"Lkotlin/UIntArray$Iterator;", "Lkotlin/collections/UIntIterator;", "array", "", "([I)V", "index", "", "hasNext", "", "nextUInt", "Lkotlin/UInt;", "()I", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/UIntArray$Iterator.class */
    private static final class Iterator extends UIntIterator {
        private int index;
        private final int[] array;

        public Iterator(@NotNull int[] array) {
            Intrinsics.checkParameterIsNotNull(array, "array");
            this.array = array;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override // kotlin.collections.UIntIterator
        public int nextUInt() {
            if (this.index >= this.array.length) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }
            int[] iArr = this.array;
            int i = this.index;
            this.index = i + 1;
            return iArr[i];
        }
    }

    /* renamed from: contains-WZ4Q5Ns, reason: not valid java name */
    public static boolean m612containsWZ4Q5Ns(int[] iArr, int i) {
        return ArraysKt.contains(iArr, i);
    }

    /* renamed from: containsAll-impl, reason: not valid java name */
    public static boolean m613containsAllimpl(int[] iArr, @NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        for (Object obj : collection) {
            if (!((obj instanceof UInt) && ArraysKt.contains(iArr, ((UInt) obj).m607unboximpl()))) {
                return false;
            }
        }
        return true;
    }
}

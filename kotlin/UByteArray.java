package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.collections.ArraysKt;
import kotlin.collections.UByteIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\ufffd\ufffd\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004H\u00d6\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002\u00a2\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\ufffd\ufffdX\u0081\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\f\u0010\r\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, m27d2 = {"Lkotlin/UByteArray;", "", "Lkotlin/UByte;", "size", "", "constructor-impl", "(I)[B", "storage", "", "([B)[B", "getSize-impl", "([B)I", "storage$annotations", "()V", "contains", "", "element", "contains-7apg3OU", "([BB)Z", "containsAll", "elements", "containsAll-impl", "([BLjava/util/Collection;)Z", "equals", "other", "", PropertyDescriptor.GET, "index", "get-impl", "([BI)B", "hashCode", "isEmpty", "isEmpty-impl", "([B)Z", "iterator", "Lkotlin/collections/UByteIterator;", "iterator-impl", "([B)Lkotlin/collections/UByteIterator;", PropertyDescriptor.SET, "", PropertyDescriptor.VALUE, "set-VurrAj0", "([BIB)V", "toString", "", "Iterator", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/UByteArray.class */
public final class UByteArray implements Collection, KMappedMarker {

    @NotNull
    private final byte[] storage;

    public int getSize() {
        return this.storage.length;
    }

    @Override // java.util.Collection, java.lang.Iterable
    @NotNull
    public UByteIterator iterator() {
        return m553iteratorimpl(this.storage);
    }

    /* renamed from: contains-7apg3OU, reason: not valid java name */
    public boolean m551contains7apg3OU(byte b) {
        return m554contains7apg3OU(this.storage, b);
    }

    @Override // java.util.Collection
    public boolean containsAll(@NotNull Collection collection) {
        return m555containsAllimpl(this.storage, collection);
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.storage.length == 0;
    }

    @NotNull
    /* renamed from: box-impl, reason: not valid java name */
    public static final UByteArray m558boximpl(@NotNull byte[] v) {
        Intrinsics.checkParameterIsNotNull(v, "v");
        return new UByteArray(v);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m559toStringimpl(byte[] bArr) {
        return "UByteArray(storage=" + Arrays.toString(bArr) + ")";
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m560hashCodeimpl(byte[] bArr) {
        if (bArr != null) {
            return Arrays.hashCode(bArr);
        }
        return 0;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m561equalsimpl(byte[] bArr, @Nullable Object obj) {
        return (obj instanceof UByteArray) && Intrinsics.areEqual(bArr, ((UByteArray) obj).m563unboximpl());
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m562equalsimpl0(@NotNull byte[] p1, @NotNull byte[] p2) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        Intrinsics.checkParameterIsNotNull(p2, "p2");
        throw null;
    }

    @NotNull
    /* renamed from: unbox-impl, reason: not valid java name */
    public final byte[] m563unboximpl() {
        return this.storage;
    }

    public String toString() {
        return m559toStringimpl(this.storage);
    }

    @Override // java.util.Collection
    public int hashCode() {
        return m560hashCodeimpl(this.storage);
    }

    @Override // java.util.Collection
    public boolean equals(Object obj) {
        return m561equalsimpl(this.storage, obj);
    }

    /* renamed from: add-7apg3OU, reason: not valid java name */
    public boolean m564add7apg3OU(byte b) {
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
        if (obj instanceof UByte) {
            return m551contains7apg3OU(((UByte) obj).m549unboximpl());
        }
        return false;
    }

    @PublishedApi
    private UByteArray(@NotNull byte[] storage) {
        Intrinsics.checkParameterIsNotNull(storage, "storage");
        this.storage = storage;
    }

    @PublishedApi
    @NotNull
    /* renamed from: constructor-impl, reason: not valid java name */
    public static byte[] m556constructorimpl(@NotNull byte[] storage) {
        Intrinsics.checkParameterIsNotNull(storage, "storage");
        return storage;
    }

    @NotNull
    /* renamed from: constructor-impl, reason: not valid java name */
    public static byte[] m557constructorimpl(int i) {
        return m556constructorimpl(new byte[i]);
    }

    /* renamed from: get-impl, reason: not valid java name */
    public static final byte m552getimpl(byte[] bArr, int i) {
        return bArr[i];
    }

    @NotNull
    /* renamed from: iterator-impl, reason: not valid java name */
    public static UByteIterator m553iteratorimpl(byte[] bArr) {
        return new Iterator(bArr);
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, m27d2 = {"Lkotlin/UByteArray$Iterator;", "Lkotlin/collections/UByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextUByte", "Lkotlin/UByte;", "()B", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/UByteArray$Iterator.class */
    private static final class Iterator extends UByteIterator {
        private int index;
        private final byte[] array;

        public Iterator(@NotNull byte[] array) {
            Intrinsics.checkParameterIsNotNull(array, "array");
            this.array = array;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override // kotlin.collections.UByteIterator
        public byte nextUByte() {
            if (this.index >= this.array.length) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }
            byte[] bArr = this.array;
            int i = this.index;
            this.index = i + 1;
            return bArr[i];
        }
    }

    /* renamed from: contains-7apg3OU, reason: not valid java name */
    public static boolean m554contains7apg3OU(byte[] bArr, byte b) {
        return ArraysKt.contains(bArr, b);
    }

    /* renamed from: containsAll-impl, reason: not valid java name */
    public static boolean m555containsAllimpl(byte[] bArr, @NotNull Collection elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        for (Object obj : collection) {
            if (!((obj instanceof UByte) && ArraysKt.contains(bArr, ((UByte) obj).m549unboximpl()))) {
                return false;
            }
        }
        return true;
    }
}

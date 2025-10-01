package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/DeletedRangeArrayFilter.class */
final class DeletedRangeArrayFilter extends ArrayFilter {

    /* renamed from: lo */
    private long f54lo;

    /* renamed from: hi */
    private long f55hi;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DeletedRangeArrayFilter.class.desiredAssertionStatus();
    }

    DeletedRangeArrayFilter(ArrayData arrayData, long j, long j2) {
        super(maybeSparse(arrayData, j2));
        this.f54lo = j;
        this.f55hi = j2;
    }

    private static ArrayData maybeSparse(ArrayData arrayData, long j) {
        if (j < 131072 || (arrayData instanceof SparseArrayData)) {
            return arrayData;
        }
        return new SparseArrayData(arrayData, arrayData.length());
    }

    private boolean isEmpty() {
        return this.f54lo > this.f55hi;
    }

    private boolean isDeleted(int i) {
        long longIndex = ArrayIndex.toLongIndex(i);
        return this.f54lo <= longIndex && longIndex <= this.f55hi;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new DeletedRangeArrayFilter(this.underlying.copy(), this.f54lo, this.f55hi);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        Object[] objArrAsObjectArray = super.asObjectArray();
        if (this.f54lo < 2147483647L) {
            int iMin = (int) Math.min(this.f55hi + 1, 2147483647L);
            for (int i = (int) this.f54lo; i < iMin; i++) {
                objArrAsObjectArray[i] = ScriptRuntime.UNDEFINED;
            }
        }
        return objArrAsObjectArray;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class cls) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object objAsArrayOfType = super.asArrayOfType(cls);
        Object objConvertUndefinedValue = convertUndefinedValue(cls);
        if (this.f54lo < 2147483647L) {
            int iMin = (int) Math.min(this.f55hi + 1, 2147483647L);
            for (int i = (int) this.f54lo; i < iMin; i++) {
                Array.set(objAsArrayOfType, i, objConvertUndefinedValue);
            }
        }
        return objAsArrayOfType;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        if (j >= 131072 && j >= length()) {
            return new SparseArrayData(this, j + 1);
        }
        return super.ensure(j);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        super.shiftLeft(i);
        this.f54lo = Math.max(0L, this.f54lo - i);
        this.f55hi = Math.max(-1L, this.f55hi - i);
        return isEmpty() ? getUnderlying() : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        super.shiftRight(i);
        long length = length();
        this.f54lo = Math.min(length, this.f54lo + i);
        this.f55hi = Math.min(length - 1, this.f55hi + i);
        return isEmpty() ? getUnderlying() : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        super.shrink(j);
        this.f54lo = Math.min(j, this.f54lo);
        this.f55hi = Math.min(j - 1, this.f55hi);
        return isEmpty() ? getUnderlying() : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        long longIndex = ArrayIndex.toLongIndex(i);
        if (longIndex < this.f54lo || longIndex > this.f55hi) {
            return super.set(i, obj, z);
        }
        if (longIndex > this.f54lo && longIndex < this.f55hi) {
            return getDeletedArrayFilter().set(i, obj, z);
        }
        if (longIndex == this.f54lo) {
            this.f54lo++;
        } else {
            if (!$assertionsDisabled && longIndex != this.f55hi) {
                throw new AssertionError();
            }
            this.f55hi--;
        }
        return isEmpty() ? getUnderlying().set(i, obj, z) : super.set(i, obj, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        long longIndex = ArrayIndex.toLongIndex(i);
        if (longIndex < this.f54lo || longIndex > this.f55hi) {
            return super.set(i, i2, z);
        }
        if (longIndex > this.f54lo && longIndex < this.f55hi) {
            return getDeletedArrayFilter().set(i, i2, z);
        }
        if (longIndex == this.f54lo) {
            this.f54lo++;
        } else {
            if (!$assertionsDisabled && longIndex != this.f55hi) {
                throw new AssertionError();
            }
            this.f55hi--;
        }
        return isEmpty() ? getUnderlying().set(i, i2, z) : super.set(i, i2, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        long longIndex = ArrayIndex.toLongIndex(i);
        if (longIndex < this.f54lo || longIndex > this.f55hi) {
            return super.set(i, d, z);
        }
        if (longIndex > this.f54lo && longIndex < this.f55hi) {
            return getDeletedArrayFilter().set(i, d, z);
        }
        if (longIndex == this.f54lo) {
            this.f54lo++;
        } else {
            if (!$assertionsDisabled && longIndex != this.f55hi) {
                throw new AssertionError();
            }
            this.f55hi--;
        }
        return isEmpty() ? getUnderlying().set(i, d, z) : super.set(i, d, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return super.has(i) && !isDeleted(i);
    }

    private ArrayData getDeletedArrayFilter() {
        DeletedArrayFilter deletedArrayFilter = new DeletedArrayFilter(getUnderlying());
        deletedArrayFilter.delete(this.f54lo, this.f55hi);
        return deletedArrayFilter;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        long longIndex = ArrayIndex.toLongIndex(i);
        ArrayData arrayData = this.underlying;
        if (longIndex + 1 == this.f54lo) {
            this.f54lo = longIndex;
        } else if (longIndex - 1 == this.f55hi) {
            this.f55hi = longIndex;
        } else if (longIndex < this.f54lo || this.f55hi < longIndex) {
            return getDeletedArrayFilter().delete(i);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        if (j > this.f55hi + 1 || j2 < this.f54lo - 1) {
            return getDeletedArrayFilter().delete(j, j2);
        }
        this.f54lo = Math.min(j, this.f54lo);
        this.f55hi = Math.max(j2, this.f55hi);
        ArrayData arrayData = this.underlying;
        long j3 = this.f54lo;
        long j4 = this.f55hi;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        int length = ((int) length()) - 1;
        if (super.has(length)) {
            boolean zIsDeleted = isDeleted(length);
            Object objPop = super.pop();
            this.f54lo = Math.min(length + 1, this.f54lo);
            this.f55hi = Math.min(length, this.f55hi);
            return zIsDeleted ? ScriptRuntime.UNDEFINED : objPop;
        }
        return super.pop();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return new DeletedRangeArrayFilter(this.underlying.slice(j, j2), Math.max(0L, this.f54lo - j), Math.max(0L, this.f55hi - j));
    }
}

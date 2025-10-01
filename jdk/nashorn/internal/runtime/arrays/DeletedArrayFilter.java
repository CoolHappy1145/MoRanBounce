package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/DeletedArrayFilter.class */
final class DeletedArrayFilter extends ArrayFilter {
    private final BitVector deleted;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DeletedArrayFilter.class.desiredAssertionStatus();
    }

    DeletedArrayFilter(ArrayData arrayData) {
        super(arrayData);
        this.deleted = new BitVector(arrayData.length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        DeletedArrayFilter deletedArrayFilter = new DeletedArrayFilter(this.underlying.copy());
        deletedArrayFilter.getDeleted().copy(this.deleted);
        return deletedArrayFilter;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        Object[] objArrAsObjectArray = super.asObjectArray();
        for (int i = 0; i < objArrAsObjectArray.length; i++) {
            if (this.deleted.isSet(i)) {
                objArrAsObjectArray[i] = ScriptRuntime.UNDEFINED;
            }
        }
        return objArrAsObjectArray;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class cls) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object objAsArrayOfType = super.asArrayOfType(cls);
        Object objConvertUndefinedValue = convertUndefinedValue(cls);
        int length = Array.getLength(objAsArrayOfType);
        for (int i = 0; i < length; i++) {
            if (this.deleted.isSet(i)) {
                Array.set(objAsArrayOfType, i, objConvertUndefinedValue);
            }
        }
        return objAsArrayOfType;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        super.shiftLeft(i);
        this.deleted.shiftLeft(i, length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        super.shiftRight(i);
        this.deleted.shiftRight(i, length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        if (j >= 131072 && j >= length()) {
            return new SparseArrayData(this, j + 1);
        }
        super.ensure(j);
        this.deleted.resize(length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        super.shrink(j);
        this.deleted.resize(length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        this.deleted.clear(ArrayIndex.toLongIndex(i));
        return super.set(i, obj, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.deleted.clear(ArrayIndex.toLongIndex(i));
        return super.set(i, i2, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        this.deleted.clear(ArrayIndex.toLongIndex(i));
        return super.set(i, d, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return super.has(i) && this.deleted.isClear(ArrayIndex.toLongIndex(i));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        long longIndex = ArrayIndex.toLongIndex(i);
        if (!$assertionsDisabled && (longIndex < 0 || longIndex >= length())) {
            throw new AssertionError();
        }
        this.deleted.set(longIndex);
        ArrayData arrayData = this.underlying;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        if (!$assertionsDisabled && (j < 0 || j > j2 || j2 >= length())) {
            throw new AssertionError();
        }
        this.deleted.setRange(j, j2 + 1);
        ArrayData arrayData = this.underlying;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        long length = length() - 1;
        if (super.has((int) length)) {
            return this.deleted.isSet(length) ? ScriptRuntime.UNDEFINED : super.pop();
        }
        return super.pop();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        DeletedArrayFilter deletedArrayFilter = new DeletedArrayFilter(this.underlying.slice(j, j2));
        deletedArrayFilter.getDeleted().copy(this.deleted);
        deletedArrayFilter.getDeleted().shiftLeft(j, deletedArrayFilter.length());
        return deletedArrayFilter;
    }

    private BitVector getDeleted() {
        return this.deleted;
    }
}

package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/UndefinedArrayFilter.class */
final class UndefinedArrayFilter extends ArrayFilter {
    private final BitVector undefined;

    UndefinedArrayFilter(ArrayData arrayData) {
        super(arrayData);
        this.undefined = new BitVector(arrayData.length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        UndefinedArrayFilter undefinedArrayFilter = new UndefinedArrayFilter(this.underlying.copy());
        undefinedArrayFilter.getUndefined().copy(this.undefined);
        return undefinedArrayFilter;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        Object[] objArrAsObjectArray = super.asObjectArray();
        for (int i = 0; i < objArrAsObjectArray.length; i++) {
            if (this.undefined.isSet(i)) {
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
            if (this.undefined.isSet(i)) {
                Array.set(objAsArrayOfType, i, objConvertUndefinedValue);
            }
        }
        return objAsArrayOfType;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        super.shiftLeft(i);
        this.undefined.shiftLeft(i, length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        super.shiftRight(i);
        this.undefined.shiftRight(i, length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        if (j >= 131072 && j >= length()) {
            return new SparseArrayData(this, j + 1);
        }
        super.ensure(j);
        this.undefined.resize(length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        super.shrink(j);
        this.undefined.resize(length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        this.undefined.clear(i);
        if (obj == ScriptRuntime.UNDEFINED) {
            this.undefined.set(i);
            return this;
        }
        return super.set(i, obj, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.undefined.clear(i);
        return super.set(i, i2, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        this.undefined.clear(i);
        return super.set(i, d, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        if (this.undefined.isSet(i)) {
            return 0;
        }
        return super.getInt(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int i, int i2) {
        if (this.undefined.isSet(i)) {
            throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, i2);
        }
        return super.getIntOptimistic(i, i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        if (this.undefined.isSet(i)) {
            return Double.NaN;
        }
        return super.getDouble(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int i, int i2) {
        if (this.undefined.isSet(i)) {
            throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, i2);
        }
        return super.getDoubleOptimistic(i, i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        if (this.undefined.isSet(i)) {
            return ScriptRuntime.UNDEFINED;
        }
        return super.getObject(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        this.undefined.clear(i);
        return super.delete(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        long length = length() - 1;
        if (super.has((int) length)) {
            return this.undefined.isSet(length) ? ScriptRuntime.UNDEFINED : super.pop();
        }
        return super.pop();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        UndefinedArrayFilter undefinedArrayFilter = new UndefinedArrayFilter(this.underlying.slice(j, j2));
        undefinedArrayFilter.getUndefined().copy(this.undefined);
        undefinedArrayFilter.getUndefined().shiftLeft(j, undefinedArrayFilter.length());
        return undefinedArrayFilter;
    }

    private BitVector getUndefined() {
        return this.undefined;
    }
}

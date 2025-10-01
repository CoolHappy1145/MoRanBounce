package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/IntArrayData.class */
final class IntArrayData extends ContinuousArrayData implements IntElements {
    private int[] array;
    private static final MethodHandle HAS_GET_ELEM;
    private static final MethodHandle SET_ELEM;
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ContinuousArrayData copy() {
        return copy();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return copy();
    }

    static {
        $assertionsDisabled = !IntArrayData.class.desiredAssertionStatus();
        HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), IntArrayData.class, "getElem", Integer.TYPE, new Class[]{Integer.TYPE}).methodHandle();
        SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), IntArrayData.class, "setElem", Void.TYPE, new Class[]{Integer.TYPE, Integer.TYPE}).methodHandle();
    }

    IntArrayData() {
        this(new int[32], 0);
    }

    IntArrayData(int i) {
        super(i);
        this.array = new int[ArrayData.nextSize(i)];
    }

    IntArrayData(int[] iArr, int i) {
        super(i);
        if (!$assertionsDisabled && iArr != null && iArr.length < i) {
            throw new AssertionError();
        }
        this.array = iArr;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class getElementType() {
        return Integer.TYPE;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return toObjectArray(true);
    }

    private int getElem(int i) {
        if (has(i)) {
            return this.array[i];
        }
        throw new ClassCastException();
    }

    private void setElem(int i, int i2) {
        if (hasRoomFor(i)) {
            this.array[i] = i2;
            return;
        }
        throw new ClassCastException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class cls, int i) {
        return getContinuousElementGetter(HAS_GET_ELEM, cls, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class cls) {
        if (cls == Integer.TYPE) {
            return getContinuousElementSetter(SET_ELEM, cls);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public IntArrayData copy() {
        return new IntArrayData((int[]) this.array.clone(), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class cls) {
        if (cls == Integer.TYPE) {
            int length = (int) length();
            return this.array.length == length ? (int[]) this.array.clone() : Arrays.copyOf(this.array, length);
        }
        return super.asArrayOfType(cls);
    }

    private Object[] toObjectArray(boolean z) {
        if (!$assertionsDisabled && length() > this.array.length) {
            throw new AssertionError("length exceeds internal array size");
        }
        int length = (int) length();
        Object[] objArr = new Object[z ? length : this.array.length];
        for (int i = 0; i < length; i++) {
            objArr[i] = Integer.valueOf(this.array[i]);
        }
        return objArr;
    }

    private double[] toDoubleArray() {
        if (!$assertionsDisabled && length() > this.array.length) {
            throw new AssertionError("length exceeds internal array size");
        }
        int length = (int) length();
        double[] dArr = new double[this.array.length];
        for (int i = 0; i < length; i++) {
            dArr[i] = this.array[i];
        }
        return dArr;
    }

    private NumberArrayData convertToDouble() {
        return new NumberArrayData(toDoubleArray(), (int) length());
    }

    private ObjectArrayData convertToObject() {
        return new ObjectArrayData(toObjectArray(false), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        if (cls == Integer.class || cls == Byte.class || cls == Short.class) {
            return this;
        }
        if (cls == Double.class || cls == Float.class) {
            return convertToDouble();
        }
        return convertToObject();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        if (i >= length()) {
            shrink(0L);
        } else {
            System.arraycopy(this.array, i, this.array, 0, this.array.length - i);
        }
        setLength(Math.max(0L, length() - i));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        ArrayData arrayDataEnsure = ensure((i + length()) - 1);
        if (arrayDataEnsure != this) {
            arrayDataEnsure.shiftRight(i);
            return arrayDataEnsure;
        }
        System.arraycopy(this.array, 0, this.array, i, this.array.length - i);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        if (j >= 131072) {
            return new SparseArrayData(this, j + 1);
        }
        if (j >= this.array.length) {
            this.array = Arrays.copyOf(this.array, ArrayData.nextSize((int) j));
        }
        if (j >= length()) {
            setLength(j + 1);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        Arrays.fill(this.array, (int) j, this.array.length, 0);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if (JSType.isRepresentableAsInt(obj)) {
            return set(i, JSType.toInt32(obj), z);
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return new UndefinedArrayFilter(this).set(i, obj, z);
        }
        return convert(obj == null ? Object.class : obj.getClass()).set(i, obj, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.array[i] = i2;
        setLength(Math.max(i + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        if (((double) ((int) d)) == d) {
            this.array[i] = (int) d;
            setLength(Math.max(i + 1, length()));
            return this;
        }
        return convert(Double.class).set(i, d, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        return this.array[i];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int i, int i2) {
        return this.array[i];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        return this.array[i];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int i, int i2) {
        return this.array[i];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        return Integer.valueOf(this.array[i]);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return 0 <= i && ((long) i) < length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        return new DeletedRangeArrayFilter(this, i, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        return new DeletedRangeArrayFilter(this, j, j2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        int length = (int) length();
        if (length == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        int i = length - 1;
        int i2 = this.array[i];
        this.array[i] = 0;
        setLength(i);
        return Integer.valueOf(i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return new IntArrayData(Arrays.copyOfRange(this.array, (int) j, (int) j2), (int) (j2 - (j < 0 ? j + length() : j)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData fastSplice(int i, int i2, int i3) {
        int[] iArr;
        long length = length();
        long j = (length - i2) + i3;
        if (j > 131072 && j > this.array.length) {
            throw new UnsupportedOperationException();
        }
        ArrayData intArrayData = i2 == 0 ? EMPTY_ARRAY : new IntArrayData(Arrays.copyOfRange(this.array, i, i + i2), i2);
        if (j != length) {
            if (j > this.array.length) {
                iArr = new int[ArrayData.nextSize((int) j)];
                System.arraycopy(this.array, 0, iArr, 0, i);
            } else {
                iArr = this.array;
            }
            System.arraycopy(this.array, i + i2, iArr, i + i3, (int) ((length - i) - i2));
            this.array = iArr;
            setLength(j);
        }
        return intArrayData;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(int i) {
        int length = (int) length();
        if (length == this.array.length) {
            this.array = Arrays.copyOf(this.array, nextSize(length));
        }
        this.array[length] = i;
        return increaseLength();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public int fastPopInt() {
        if (length() == 0) {
            throw new ClassCastException();
        }
        int iDecreaseLength = (int) decreaseLength();
        int i = this.array[iDecreaseLength];
        this.array[iDecreaseLength] = 0;
        return i;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPopDouble() {
        return fastPopInt();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public Object fastPopObject() {
        return Integer.valueOf(fastPopInt());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public ContinuousArrayData fastConcat(ContinuousArrayData continuousArrayData) {
        int length = (int) continuousArrayData.length();
        int length2 = (int) length();
        if (!$assertionsDisabled && (length <= 0 || length2 <= 0)) {
            throw new AssertionError();
        }
        int[] iArr = ((IntArrayData) continuousArrayData).array;
        int i = length + length2;
        int[] iArr2 = new int[((i + 32) - 1) & (-32)];
        System.arraycopy(this.array, 0, iArr2, 0, length2);
        System.arraycopy(iArr, 0, iArr2, length2, length);
        return new IntArrayData(iArr2, i);
    }

    public String toString() {
        if ($assertionsDisabled || length() <= this.array.length) {
            return getClass().getSimpleName() + ':' + Arrays.toString(Arrays.copyOf(this.array, (int) length()));
        }
        throw new AssertionError(length() + " > " + this.array.length);
    }
}

package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/NumberArrayData.class */
final class NumberArrayData extends ContinuousArrayData implements NumericElements {
    private double[] array;
    private static final MethodHandle HAS_GET_ELEM;
    private static final MethodHandle SET_ELEM;
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ContinuousArrayData copy() {
        return copy();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        return convert(cls);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return copy();
    }

    static {
        $assertionsDisabled = !NumberArrayData.class.desiredAssertionStatus();
        HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NumberArrayData.class, "getElem", Double.TYPE, new Class[]{Integer.TYPE}).methodHandle();
        SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NumberArrayData.class, "setElem", Void.TYPE, new Class[]{Integer.TYPE, Double.TYPE}).methodHandle();
    }

    NumberArrayData(double[] dArr, int i) {
        super(i);
        if (!$assertionsDisabled && dArr.length < i) {
            throw new AssertionError();
        }
        this.array = dArr;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class getElementType() {
        return Double.TYPE;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public NumberArrayData copy() {
        return new NumberArrayData((double[]) this.array.clone(), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return toObjectArray(true);
    }

    private Object[] toObjectArray(boolean z) {
        if (!$assertionsDisabled && length() > this.array.length) {
            throw new AssertionError("length exceeds internal array size");
        }
        int length = (int) length();
        Object[] objArr = new Object[z ? length : this.array.length];
        for (int i = 0; i < length; i++) {
            objArr[i] = Double.valueOf(this.array[i]);
        }
        return objArr;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class cls) {
        if (cls == Double.TYPE) {
            int length = (int) length();
            return this.array.length == length ? (double[]) this.array.clone() : Arrays.copyOf(this.array, length);
        }
        return super.asArrayOfType(cls);
    }

    private static boolean canWiden(Class cls) {
        return (!TypeUtilities.isWrapperType(cls) || cls == Boolean.class || cls == Character.class) ? false : true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ContinuousArrayData convert(Class cls) {
        if (!canWiden(cls)) {
            return new ObjectArrayData(toObjectArray(false), (int) length());
        }
        return this;
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
        Arrays.fill(this.array, (int) j, this.array.length, 0.0d);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if ((obj instanceof Double) || (obj != null && canWiden(obj.getClass()))) {
            return set(i, ((Number) obj).doubleValue(), z);
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return new UndefinedArrayFilter(this).set(i, obj, z);
        }
        return convert((Class) (obj == null ? Object.class : obj.getClass())).set(i, obj, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.array[i] = i2;
        setLength(Math.max(i + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        this.array[i] = d;
        setLength(Math.max(i + 1, length()));
        return this;
    }

    private double getElem(int i) {
        if (has(i)) {
            return this.array[i];
        }
        throw new ClassCastException();
    }

    private void setElem(int i, double d) {
        if (hasRoomFor(i)) {
            this.array[i] = d;
            return;
        }
        throw new ClassCastException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class cls, int i) {
        if (cls == Integer.TYPE) {
            return null;
        }
        return getContinuousElementGetter(HAS_GET_ELEM, cls, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class cls) {
        if (cls.isPrimitive()) {
            return getContinuousElementSetter(Lookup.f31MH.asType(SET_ELEM, SET_ELEM.type().changeParameterType(2, (Class<?>) cls)), cls);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        return JSType.toInt32(this.array[i]);
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
        return Double.valueOf(this.array[i]);
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
        double d = this.array[i];
        this.array[i] = 0.0d;
        setLength(i);
        return Double.valueOf(d);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return new NumberArrayData(Arrays.copyOfRange(this.array, (int) j, (int) j2), (int) (j2 - (j < 0 ? j + length() : j)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData fastSplice(int i, int i2, int i3) {
        double[] dArr;
        long length = length();
        long j = (length - i2) + i3;
        if (j > 131072 && j > this.array.length) {
            throw new UnsupportedOperationException();
        }
        ArrayData numberArrayData = i2 == 0 ? EMPTY_ARRAY : new NumberArrayData(Arrays.copyOfRange(this.array, i, i + i2), i2);
        if (j != length) {
            if (j > this.array.length) {
                dArr = new double[ArrayData.nextSize((int) j)];
                System.arraycopy(this.array, 0, dArr, 0, i);
            } else {
                dArr = this.array;
            }
            System.arraycopy(this.array, i + i2, dArr, i + i3, (int) ((length - i) - i2));
            this.array = dArr;
            setLength(j);
        }
        return numberArrayData;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(int i) {
        return fastPush(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(long j) {
        return fastPush(j);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(double d) {
        int length = (int) length();
        if (length == this.array.length) {
            this.array = Arrays.copyOf(this.array, nextSize(length));
        }
        this.array[length] = d;
        return increaseLength();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPopDouble() {
        if (length() == 0) {
            throw new ClassCastException();
        }
        int iDecreaseLength = (int) decreaseLength();
        double d = this.array[iDecreaseLength];
        this.array[iDecreaseLength] = 0.0d;
        return d;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public Object fastPopObject() {
        return Double.valueOf(fastPopDouble());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public ContinuousArrayData fastConcat(ContinuousArrayData continuousArrayData) {
        int length = (int) continuousArrayData.length();
        int length2 = (int) length();
        if (!$assertionsDisabled && (length <= 0 || length2 <= 0)) {
            throw new AssertionError();
        }
        double[] dArr = ((NumberArrayData) continuousArrayData).array;
        int i = length + length2;
        double[] dArr2 = new double[((i + 32) - 1) & (-32)];
        System.arraycopy(this.array, 0, dArr2, 0, length2);
        System.arraycopy(dArr, 0, dArr2, length2, length);
        return new NumberArrayData(dArr2, i);
    }

    public String toString() {
        if ($assertionsDisabled || length() <= this.array.length) {
            return getClass().getSimpleName() + ':' + Arrays.toString(Arrays.copyOf(this.array, (int) length()));
        }
        throw new AssertionError(length() + " > " + this.array.length);
    }
}

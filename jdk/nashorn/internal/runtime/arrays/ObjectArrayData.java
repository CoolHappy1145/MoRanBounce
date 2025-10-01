package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ObjectArrayData.class */
final class ObjectArrayData extends ContinuousArrayData implements AnyElements {
    private Object[] array;
    private static final MethodHandle HAS_GET_ELEM;
    private static final MethodHandle SET_ELEM;
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ContinuousArrayData copy() {
        return copy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        return cls;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return copy();
    }

    static {
        $assertionsDisabled = !ObjectArrayData.class.desiredAssertionStatus();
        HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), ObjectArrayData.class, "getElem", Object.class, new Class[]{Integer.TYPE}).methodHandle();
        SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), ObjectArrayData.class, "setElem", Void.TYPE, new Class[]{Integer.TYPE, Object.class}).methodHandle();
    }

    ObjectArrayData(Object[] objArr, int i) {
        super(i);
        if (!$assertionsDisabled && objArr.length < i) {
            throw new AssertionError();
        }
        this.array = objArr;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class getBoxedElementType() {
        return Object.class;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ObjectArrayData copy() {
        return new ObjectArrayData((Object[]) this.array.clone(), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return ((long) this.array.length) == length() ? (Object[]) this.array.clone() : asObjectArrayCopy();
    }

    private Object[] asObjectArrayCopy() {
        long length = length();
        if (!$assertionsDisabled && length > 2147483647L) {
            throw new AssertionError();
        }
        Object[] objArr = new Object[(int) length];
        System.arraycopy(this.array, 0, objArr, 0, (int) length);
        return objArr;
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
        Arrays.fill(this.array, (int) j, this.array.length, ScriptRuntime.UNDEFINED);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        this.array[i] = obj;
        setLength(Math.max(i + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.array[i] = Integer.valueOf(i2);
        setLength(Math.max(i + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        this.array[i] = Double.valueOf(d);
        setLength(Math.max(i + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(int i) {
        this.array[i] = ScriptRuntime.EMPTY;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(long j, long j2) {
        Arrays.fill(this.array, (int) Math.max(j, 0L), (int) Math.min(j2 + 1, 2147483647L), ScriptRuntime.EMPTY);
        return this;
    }

    private Object getElem(int i) {
        if (has(i)) {
            return this.array[i];
        }
        throw new ClassCastException();
    }

    private void setElem(int i, Object obj) {
        if (hasRoomFor(i)) {
            this.array[i] = obj;
            return;
        }
        throw new ClassCastException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class cls, int i) {
        if (cls.isPrimitive()) {
            return null;
        }
        return getContinuousElementGetter(HAS_GET_ELEM, cls, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class cls) {
        return getContinuousElementSetter(SET_ELEM, Object.class);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        return JSType.toInt32(this.array[i]);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        return JSType.toNumber(this.array[i]);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        return this.array[i];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return 0 <= i && ((long) i) < length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        setEmpty(i);
        return new DeletedRangeArrayFilter(this, i, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        setEmpty(j, j2);
        return new DeletedRangeArrayFilter(this, j, j2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(int i) {
        return fastPush(Integer.valueOf(i));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(long j) {
        return fastPush(Long.valueOf(j));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(double d) {
        return fastPush(Double.valueOf(d));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(Object obj) {
        int length = (int) length();
        if (length == this.array.length) {
            this.array = Arrays.copyOf(this.array, nextSize(length));
        }
        this.array[length] = obj;
        return increaseLength();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public Object fastPopObject() {
        if (length() == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        int iDecreaseLength = (int) decreaseLength();
        Object obj = this.array[iDecreaseLength];
        this.array[iDecreaseLength] = ScriptRuntime.EMPTY;
        return obj;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        if (length() == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        int length = ((int) length()) - 1;
        Object obj = this.array[length];
        setEmpty(length);
        setLength(length);
        return obj;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return new ObjectArrayData(Arrays.copyOfRange(this.array, (int) j, (int) j2), (int) (j2 - (j < 0 ? j + length() : j)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData push(boolean z, Object obj) {
        long length = length();
        ArrayData arrayDataEnsure = ensure(length);
        if (arrayDataEnsure == this) {
            this.array[(int) length] = obj;
            return this;
        }
        return arrayDataEnsure.set((int) length, obj, z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData fastSplice(int i, int i2, int i3) {
        Object[] objArr;
        long length = length();
        long j = (length - i2) + i3;
        if (j > 131072 && j > this.array.length) {
            throw new UnsupportedOperationException();
        }
        ArrayData objectArrayData = i2 == 0 ? EMPTY_ARRAY : new ObjectArrayData(Arrays.copyOfRange(this.array, i, i + i2), i2);
        if (j != length) {
            if (j > this.array.length) {
                objArr = new Object[ArrayData.nextSize((int) j)];
                System.arraycopy(this.array, 0, objArr, 0, i);
            } else {
                objArr = this.array;
            }
            System.arraycopy(this.array, i + i2, objArr, i + i3, (int) ((length - i) - i2));
            this.array = objArr;
            setLength(j);
        }
        return objectArrayData;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public ContinuousArrayData fastConcat(ContinuousArrayData continuousArrayData) {
        int length = (int) continuousArrayData.length();
        int length2 = (int) length();
        if (!$assertionsDisabled && (length <= 0 || length2 <= 0)) {
            throw new AssertionError();
        }
        Object[] objArr = ((ObjectArrayData) continuousArrayData).array;
        int i = length + length2;
        Object[] objArr2 = new Object[((i + 32) - 1) & (-32)];
        System.arraycopy(this.array, 0, objArr2, 0, length2);
        System.arraycopy(objArr, 0, objArr2, length2, length);
        return new ObjectArrayData(objArr2, i);
    }

    public String toString() {
        if ($assertionsDisabled || length() <= this.array.length) {
            return getClass().getSimpleName() + ':' + Arrays.toString(Arrays.copyOf(this.array, (int) length()));
        }
        throw new AssertionError(length() + " > " + this.array.length);
    }
}

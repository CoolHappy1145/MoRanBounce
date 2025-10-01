package jdk.nashorn.internal.runtime.arrays;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/SparseArrayData.class */
class SparseArrayData extends ArrayData {
    static final int MAX_DENSE_LENGTH = 131072;
    private ArrayData underlying;
    private final long maxDenseLength;
    private TreeMap sparseMap;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SparseArrayData.class.desiredAssertionStatus();
    }

    SparseArrayData(ArrayData arrayData, long j) {
        this(arrayData, j, new TreeMap());
    }

    private SparseArrayData(ArrayData arrayData, long j, TreeMap treeMap) {
        super(j);
        if (!$assertionsDisabled && arrayData.length() > j) {
            throw new AssertionError();
        }
        this.underlying = arrayData;
        this.maxDenseLength = arrayData.length();
        this.sparseMap = treeMap;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new SparseArrayData(this.underlying.copy(), length(), new TreeMap((SortedMap) this.sparseMap));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        int iMin = (int) Math.min(length(), 2147483647L);
        int iMin2 = (int) Math.min(iMin, this.underlying.length());
        Object[] objArr = new Object[iMin];
        for (int i = 0; i < iMin2; i++) {
            objArr[i] = this.underlying.getObject(i);
        }
        Arrays.fill(objArr, iMin2, iMin, ScriptRuntime.UNDEFINED);
        for (Map.Entry entry : this.sparseMap.entrySet()) {
            long jLongValue = ((Long) entry.getKey()).longValue();
            if (jLongValue >= 2147483647L) {
                break;
            }
            objArr[(int) jLongValue] = entry.getValue();
        }
        return objArr;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        this.underlying = this.underlying.shiftLeft(i);
        TreeMap treeMap = new TreeMap();
        for (Map.Entry entry : this.sparseMap.entrySet()) {
            long jLongValue = ((Long) entry.getKey()).longValue() - i;
            if (jLongValue >= 0) {
                if (jLongValue < this.maxDenseLength) {
                    this.underlying = this.underlying.ensure(jLongValue).set((int) jLongValue, entry.getValue(), false).safeDelete(this.underlying.length(), jLongValue - 1, false);
                } else {
                    treeMap.put(Long.valueOf(jLongValue), entry.getValue());
                }
            }
        }
        this.sparseMap = treeMap;
        setLength(Math.max(length() - i, 0L));
        return this.sparseMap.isEmpty() ? this.underlying : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        TreeMap treeMap = new TreeMap();
        long length = this.underlying.length();
        if (length + i > this.maxDenseLength) {
            long jMax = Math.max(0L, this.maxDenseLength - i);
            long j = jMax;
            while (true) {
                long j2 = j;
                if (j2 >= length) {
                    break;
                }
                if (this.underlying.has((int) j2)) {
                    treeMap.put(Long.valueOf(j2 + i), this.underlying.getObject((int) j2));
                }
                j = j2 + 1;
            }
            this.underlying = this.underlying.shrink((int) jMax);
            this.underlying.setLength(jMax);
        }
        this.underlying = this.underlying.shiftRight(i);
        for (Map.Entry entry : this.sparseMap.entrySet()) {
            treeMap.put(Long.valueOf(((Long) entry.getKey()).longValue() + i), entry.getValue());
        }
        this.sparseMap = treeMap;
        setLength(length() + i);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        if (j >= length()) {
            setLength(j + 1);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        if (j < this.underlying.length()) {
            this.underlying = this.underlying.shrink(j);
            this.underlying.setLength(j);
            this.sparseMap.clear();
            setLength(j);
        }
        this.sparseMap.subMap(Long.valueOf(j), Long.valueOf(LongCompanionObject.MAX_VALUE)).clear();
        setLength(j);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if (i >= 0 && i < this.maxDenseLength) {
            this.underlying = this.underlying.ensure(i).set(i, obj, z).safeDelete(this.underlying.length(), i - 1, z);
            setLength(Math.max(this.underlying.length(), length()));
        } else {
            Long lIndexToKey = indexToKey(i);
            this.sparseMap.put(lIndexToKey, obj);
            setLength(Math.max(lIndexToKey.longValue() + 1, length()));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        if (i >= 0 && i < this.maxDenseLength) {
            this.underlying = this.underlying.ensure(i).set(i, i2, z).safeDelete(this.underlying.length(), i - 1, z);
            setLength(Math.max(this.underlying.length(), length()));
        } else {
            Long lIndexToKey = indexToKey(i);
            this.sparseMap.put(lIndexToKey, Integer.valueOf(i2));
            setLength(Math.max(lIndexToKey.longValue() + 1, length()));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        if (i >= 0 && i < this.maxDenseLength) {
            this.underlying = this.underlying.ensure(i).set(i, d, z).safeDelete(this.underlying.length(), i - 1, z);
            setLength(Math.max(this.underlying.length(), length()));
        } else {
            Long lIndexToKey = indexToKey(i);
            this.sparseMap.put(lIndexToKey, Double.valueOf(d));
            setLength(Math.max(lIndexToKey.longValue() + 1, length()));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(int i) {
        ArrayData arrayData = this.underlying;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(long j, long j2) {
        ArrayData arrayData = this.underlying;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Type getOptimisticType() {
        return this.underlying.getOptimisticType();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        if (i >= 0 && i < this.maxDenseLength) {
            return this.underlying.getInt(i);
        }
        return JSType.toInt32(this.sparseMap.get(indexToKey(i)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int i, int i2) {
        if (i >= 0 && i < this.maxDenseLength) {
            return this.underlying.getIntOptimistic(i, i2);
        }
        return JSType.toInt32Optimistic(this.sparseMap.get(indexToKey(i)), i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        if (i >= 0 && i < this.maxDenseLength) {
            return this.underlying.getDouble(i);
        }
        return JSType.toNumber(this.sparseMap.get(indexToKey(i)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int i, int i2) {
        if (i >= 0 && i < this.maxDenseLength) {
            return this.underlying.getDouble(i);
        }
        return JSType.toNumberOptimistic(this.sparseMap.get(indexToKey(i)), i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        if (i >= 0 && i < this.maxDenseLength) {
            return this.underlying.getObject(i);
        }
        Long lIndexToKey = indexToKey(i);
        if (this.sparseMap.containsKey(lIndexToKey)) {
            return this.sparseMap.get(lIndexToKey);
        }
        return ScriptRuntime.UNDEFINED;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        if (i < 0 || i >= this.maxDenseLength) {
            return this.sparseMap.containsKey(indexToKey(i));
        }
        return ((long) i) < this.underlying.length() && this.underlying.has(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        if (i < 0 || i >= this.maxDenseLength) {
            this.sparseMap.remove(indexToKey(i));
        } else if (i < this.underlying.length()) {
            this.underlying = this.underlying.delete(i);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        if (j < this.maxDenseLength && j < this.underlying.length()) {
            this.underlying = this.underlying.delete(j, Math.min(j2, this.underlying.length() - 1));
        }
        if (j2 >= this.maxDenseLength) {
            this.sparseMap.subMap(Long.valueOf(j), true, Long.valueOf(j2), true).clear();
        }
        return this;
    }

    private static Long indexToKey(int i) {
        return Long.valueOf(ArrayIndex.toLongIndex(i));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        this.underlying = this.underlying.convert(cls);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        long length = length();
        long length2 = this.underlying.length();
        if (length == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        if (length == length2) {
            Object objPop = this.underlying.pop();
            setLength(this.underlying.length());
            return objPop;
        }
        setLength(length - 1);
        Long lValueOf = Long.valueOf(length - 1);
        return this.sparseMap.containsKey(lValueOf) ? this.sparseMap.remove(lValueOf) : ScriptRuntime.UNDEFINED;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        if (!$assertionsDisabled && j2 > length()) {
            throw new AssertionError();
        }
        long length = j < 0 ? j + length() : j;
        long j3 = j2 - length;
        long length2 = this.underlying.length();
        if (length >= 0 && j2 <= this.maxDenseLength) {
            if (j3 <= length2) {
                return this.underlying.slice(j, j2);
            }
            return this.underlying.slice(j, j2).ensure(j3 - 1).delete(length2, j3);
        }
        ArrayData arrayDataEnsure = EMPTY_ARRAY.ensure(j3 - 1);
        long jNextIndex = length;
        while (true) {
            long j4 = jNextIndex;
            if (j4 >= j2) {
                break;
            }
            if (has((int) j4)) {
                arrayDataEnsure = arrayDataEnsure.set((int) (j4 - length), getObject((int) j4), false);
            }
            jNextIndex = nextIndex(j4);
        }
        if ($assertionsDisabled || arrayDataEnsure.length() == j3) {
            return arrayDataEnsure;
        }
        throw new AssertionError();
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Failed to calculate best type for var: r12v0 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r1v8 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x0015: MOVE (r1 I:??[long, double]) = (r12 I:??[long, double]) (LINE:825), block:B:4:0x000e */
    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public long nextIndex(long r8) {
        /*
            r7 = this;
            r0 = r8
            r1 = r7
            jdk.nashorn.internal.runtime.arrays.ArrayData r1 = r1.underlying
            long r1 = r1.length()
            r2 = 1
            long r1 = r1 - r2
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L1a
            r0 = r7
            jdk.nashorn.internal.runtime.arrays.ArrayData r0 = r0.underlying
            r1 = r8
            r11 = r1
            r1 = r12
            r2 = 1
            long r1 = r1 + r2
            return r1
        L1a:
            r0 = r7
            java.util.TreeMap r0 = r0.sparseMap
            r1 = r8
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            java.lang.Object r0 = r0.higherKey(r1)
            java.lang.Long r0 = (java.lang.Long) r0
            r10 = r0
            r0 = r10
            if (r0 == 0) goto L32
            r0 = r10
            long r0 = r0.longValue()
            return r0
        L32:
            r0 = r7
            long r0 = r0.length()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.arrays.SparseArrayData.nextIndex(long):long");
    }
}

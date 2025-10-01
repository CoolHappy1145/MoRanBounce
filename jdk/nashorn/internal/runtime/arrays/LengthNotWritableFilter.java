package jdk.nashorn.internal.runtime.arrays;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/LengthNotWritableFilter.class */
final class LengthNotWritableFilter extends ArrayFilter {
    private final SortedMap extraElements;

    LengthNotWritableFilter(ArrayData arrayData) {
        this(arrayData, new TreeMap());
    }

    private LengthNotWritableFilter(ArrayData arrayData, SortedMap sortedMap) {
        super(arrayData);
        this.extraElements = sortedMap;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new LengthNotWritableFilter(this.underlying.copy(), new TreeMap(this.extraElements));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return super.has(i) || this.extraElements.containsKey(Long.valueOf((long) i));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return new LengthNotWritableFilter(this.underlying.slice(j, j2), this.extraElements.subMap(Long.valueOf(j), Long.valueOf(j2)));
    }

    private boolean checkAdd(long j, Object obj) {
        if (j >= length()) {
            this.extraElements.put(Long.valueOf(j), obj);
            return true;
        }
        return false;
    }

    private Object get(long j) {
        Object obj = this.extraElements.get(Long.valueOf(j));
        if (obj == null) {
            return ScriptRuntime.UNDEFINED;
        }
        return obj;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        if (i >= length()) {
            return JSType.toInt32(get(i));
        }
        return this.underlying.getInt(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int i, int i2) {
        if (i >= length()) {
            return JSType.toInt32Optimistic(get(i), i2);
        }
        return this.underlying.getIntOptimistic(i, i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        if (i >= length()) {
            return JSType.toNumber(get(i));
        }
        return this.underlying.getDouble(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int i, int i2) {
        if (i >= length()) {
            return JSType.toNumberOptimistic(get(i), i2);
        }
        return this.underlying.getDoubleOptimistic(i, i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        if (i >= length()) {
            return get(i);
        }
        return this.underlying.getObject(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if (checkAdd(i, obj)) {
            return this;
        }
        this.underlying = this.underlying.set(i, obj, z);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        if (checkAdd(i, Integer.valueOf(i2))) {
            return this;
        }
        this.underlying = this.underlying.set(i, i2, z);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        if (checkAdd(i, Double.valueOf(d))) {
            return this;
        }
        this.underlying = this.underlying.set(i, d, z);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        this.extraElements.remove(Integer.valueOf(i));
        this.underlying = this.underlying.delete(i);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        Iterator it = this.extraElements.keySet().iterator();
        while (it.hasNext()) {
            long jLongValue = ((Long) it.next()).longValue();
            if (jLongValue >= j && jLongValue <= j2) {
                it.remove();
            }
            if (jLongValue > j2) {
                break;
            }
        }
        this.underlying = this.underlying.delete(j, j2);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Iterator indexIterator() {
        List<Long> listComputeIteratorKeys = computeIteratorKeys();
        listComputeIteratorKeys.addAll(this.extraElements.keySet());
        return listComputeIteratorKeys.iterator();
    }
}

package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ArrayFilter.class */
abstract class ArrayFilter extends ArrayData {
    protected ArrayData underlying;

    ArrayFilter(ArrayData arrayData) {
        super(arrayData.length());
        this.underlying = arrayData;
    }

    protected ArrayData getUnderlying() {
        return this.underlying;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public void setLength(long j) {
        super.setLength(j);
        this.underlying.setLength(j);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return this.underlying.asObjectArray();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class cls) {
        return this.underlying.asArrayOfType(cls);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        this.underlying.shiftLeft(i);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        this.underlying = this.underlying.shiftRight(i);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        this.underlying = this.underlying.ensure(j);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        this.underlying = this.underlying.shrink(j);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        this.underlying = this.underlying.set(i, obj, z);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.underlying = this.underlying.set(i, i2, z);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        this.underlying = this.underlying.set(i, d, z);
        setLength(this.underlying.length());
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
        return this.underlying.getInt(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int i, int i2) {
        return this.underlying.getIntOptimistic(i, i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        return this.underlying.getDouble(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int i, int i2) {
        return this.underlying.getDoubleOptimistic(i, i2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        return this.underlying.getObject(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return this.underlying.has(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        this.underlying = this.underlying.delete(i);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        this.underlying = this.underlying.delete(j, j2);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        this.underlying = this.underlying.convert(cls);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        Object objPop = this.underlying.pop();
        setLength(this.underlying.length());
        return objPop;
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Failed to calculate best type for var: r10v0 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r1v2 ??
    java.lang.NullPointerException
     */
    /* JADX WARN: Not initialized variable reg: 10, insn: 0x0006: MOVE (r1 I:??[long, double]) = (r10 I:??[long, double]) (LINE:825), block:B:2:0x0000 */
    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public long nextIndex(long r7) {
        /*
            r6 = this;
            r0 = r6
            jdk.nashorn.internal.runtime.arrays.ArrayData r0 = r0.underlying
            r1 = r7
            r9 = r1
            r1 = r10
            r2 = 1
            long r1 = r1 + r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.arrays.ArrayFilter.nextIndex(long):long");
    }

    static Object convertUndefinedValue(Class cls) {
        return invoke(Bootstrap.getLinkerServices().getTypeConverter(Undefined.class, cls), ScriptRuntime.UNDEFINED);
    }
}

package jdk.nashorn.internal.runtime.arrays;

import java.nio.ByteBuffer;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ByteBufferArrayData.class */
final class ByteBufferArrayData extends ArrayData {
    private final ByteBuffer buf;

    ByteBufferArrayData(int i) {
        super(i);
        this.buf = ByteBuffer.allocateDirect(i);
    }

    ByteBufferArrayData(ByteBuffer byteBuffer) {
        super(byteBuffer.capacity());
        this.buf = byteBuffer;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public PropertyDescriptor getDescriptor(Global global, int i) {
        return global.newDataDescriptor(getObject(i), false, true, true);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        throw unsupported("copy");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        throw unsupported("asObjectArray");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public void setLength(long j) {
        throw new UnsupportedOperationException("setLength");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        throw unsupported("shiftLeft");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        throw unsupported("shiftRight");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long j) {
        if (j < this.buf.capacity()) {
            return this;
        }
        throw unsupported("ensure");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        throw unsupported("shrink");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if (obj instanceof Number) {
            this.buf.put(i, ((Number) obj).byteValue());
            return this;
        }
        throw ECMAErrors.typeError("not.a.number", new String[]{ScriptRuntime.safeToString(obj)});
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        this.buf.put(i, (byte) i2);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        this.buf.put(i, (byte) d);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int i) {
        return 255 & this.buf.get(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int i) {
        return 255 & this.buf.get(i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int i) {
        return Integer.valueOf(255 & this.buf.get(i));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int i) {
        return i > -1 && i < this.buf.capacity();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int i) {
        throw unsupported("delete");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long j, long j2) {
        throw unsupported("delete");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData push(boolean z, Object[] objArr) {
        throw unsupported("push");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        throw unsupported("pop");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        throw unsupported("slice");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        throw unsupported("convert");
    }

    private static UnsupportedOperationException unsupported(String str) {
        return new UnsupportedOperationException(str);
    }
}

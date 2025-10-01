package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/FrozenArrayFilter.class */
final class FrozenArrayFilter extends SealedArrayFilter {
    FrozenArrayFilter(ArrayData arrayData) {
        super(arrayData);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.SealedArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public PropertyDescriptor getDescriptor(Global global, int i) {
        return global.newDataDescriptor(getObject(i), false, true, false);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        if (z) {
            throw ECMAErrors.typeError("cant.set.property", new String[]{Integer.toString(i), "frozen array"});
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        if (z) {
            throw ECMAErrors.typeError("cant.set.property", new String[]{Integer.toString(i), "frozen array"});
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if (z) {
            throw ECMAErrors.typeError("cant.set.property", new String[]{Integer.toString(i), "frozen array"});
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        int length = (int) this.underlying.length();
        return length == 0 ? ScriptRuntime.UNDEFINED : this.underlying.getObject(length - 1);
    }
}

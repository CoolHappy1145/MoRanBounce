package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/SealedArrayFilter.class */
class SealedArrayFilter extends ArrayFilter {
    SealedArrayFilter(ArrayData arrayData) {
        super(arrayData);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new SealedArrayFilter(this.underlying.copy());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return getUnderlying().slice(j, j2);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(int i, boolean z) {
        return canDelete(ArrayIndex.toLongIndex(i), z);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(long j, boolean z) {
        if (z) {
            throw ECMAErrors.typeError("cant.delete.property", new String[]{Long.toString(j), "sealed array"});
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public PropertyDescriptor getDescriptor(Global global, int i) {
        return global.newDataDescriptor(getObject(i), false, true, true);
    }
}

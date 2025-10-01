package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/NonExtensibleArrayFilter.class */
final class NonExtensibleArrayFilter extends ArrayFilter {
    NonExtensibleArrayFilter(ArrayData arrayData) {
        super(arrayData);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new NonExtensibleArrayFilter(this.underlying.copy());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        return new NonExtensibleArrayFilter(this.underlying.slice(j, j2));
    }

    private ArrayData extensionCheck(boolean z, int i) {
        if (z) {
            throw ECMAErrors.typeError(Global.instance(), "object.non.extensible", new String[]{String.valueOf(i), ScriptRuntime.safeToString(this)});
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, Object obj, boolean z) {
        if (has(i)) {
            return this.underlying.set(i, obj, z);
        }
        return extensionCheck(z, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, int i2, boolean z) {
        if (has(i)) {
            return this.underlying.set(i, i2, z);
        }
        return extensionCheck(z, i);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int i, double d, boolean z) {
        if (has(i)) {
            return this.underlying.set(i, d, z);
        }
        return extensionCheck(z, i);
    }
}

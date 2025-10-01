package jdk.nashorn.internal.runtime.linker;

import java.util.Arrays;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/BoundCallable.class */
public final class BoundCallable {
    private final Object callable;
    private final Object boundThis;
    private final Object[] boundArgs;

    BoundCallable(Object obj, Object obj2, Object[] objArr) {
        this.callable = obj;
        this.boundThis = obj2;
        this.boundArgs = objArr == null || objArr.length == 0 ? ScriptRuntime.EMPTY_ARRAY : (Object[]) objArr.clone();
    }

    private BoundCallable(BoundCallable boundCallable, Object[] objArr) {
        this.callable = boundCallable.callable;
        this.boundThis = boundCallable.boundThis;
        this.boundArgs = boundCallable.concatenateBoundArgs(objArr);
    }

    Object getCallable() {
        return this.callable;
    }

    Object getBoundThis() {
        return this.boundThis;
    }

    Object[] getBoundArgs() {
        return this.boundArgs;
    }

    BoundCallable bind(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return this;
        }
        return new BoundCallable(this, objArr);
    }

    private Object[] concatenateBoundArgs(Object[] objArr) {
        if (this.boundArgs.length == 0) {
            return (Object[]) objArr.clone();
        }
        int length = this.boundArgs.length;
        int length2 = objArr.length;
        Object[] objArr2 = new Object[length + length2];
        System.arraycopy(this.boundArgs, 0, objArr2, 0, length);
        System.arraycopy(objArr, 0, objArr2, length, length2);
        return objArr2;
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder(this.callable.toString()).append(" on ").append(this.boundThis);
        if (this.boundArgs.length != 0) {
            sbAppend.append(" with ").append(Arrays.toString(this.boundArgs));
        }
        return sbAppend.toString();
    }
}

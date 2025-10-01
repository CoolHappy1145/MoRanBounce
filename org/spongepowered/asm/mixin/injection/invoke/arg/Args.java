package org.spongepowered.asm.mixin.injection.invoke.arg;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/arg/Args.class */
public abstract class Args {
    protected final Object[] values;

    public abstract void set(int i, Object obj);

    public abstract void setAll(Object[] objArr);

    protected Args(Object[] objArr) {
        this.values = objArr;
    }

    public int size() {
        return this.values.length;
    }

    public Object get(int i) {
        return this.values[i];
    }
}

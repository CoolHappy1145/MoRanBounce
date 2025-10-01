package jdk.nashorn.internal.runtime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/DefaultPropertyAccess.class */
public abstract class DefaultPropertyAccess implements PropertyAccess {
    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract Object get(Object obj);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract void set(Object obj, Object obj2, int i);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract boolean has(Object obj);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract boolean hasOwnProperty(Object obj);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object obj, int i) {
        return JSType.toInt32(get(obj));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double d, int i) {
        return getInt(JSType.toObject(d), i);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int i, int i2) {
        return getInt(JSType.toObject(i), i2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object obj, int i) {
        return JSType.toNumber(get(obj));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double d, int i) {
        return getDouble(JSType.toObject(d), i);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int i, int i2) {
        return getDouble(JSType.toObject(i), i2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double d) {
        return get(JSType.toObject(d));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int i) {
        return get(JSType.toObject(i));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double d, int i, int i2) {
        set(JSType.toObject(d), JSType.toObject(i), i2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double d, double d2, int i) {
        set(JSType.toObject(d), JSType.toObject(d2), i);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double d, Object obj, int i) {
        set(JSType.toObject(d), obj, i);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int i, int i2, int i3) {
        set(JSType.toObject(i), JSType.toObject(i2), i3);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int i, double d, int i2) {
        set(JSType.toObject(i), JSType.toObject(d), i2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int i, Object obj, int i2) {
        set(JSType.toObject(i), obj, i2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object obj, int i, int i2) {
        set(obj, JSType.toObject(i), i2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object obj, double d, int i) {
        set(obj, JSType.toObject(d), i);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int i) {
        return has(JSType.toObject(i));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double d) {
        return has(JSType.toObject(d));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(int i) {
        return hasOwnProperty(JSType.toObject(i));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(double d) {
        return hasOwnProperty(JSType.toObject(d));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int i, boolean z) {
        return delete(JSType.toObject(i), z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double d, boolean z) {
        return delete(JSType.toObject(d), z);
    }
}

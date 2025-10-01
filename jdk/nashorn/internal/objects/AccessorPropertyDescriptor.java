package jdk.nashorn.internal.objects;

import java.util.Objects;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/AccessorPropertyDescriptor.class */
public final class AccessorPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
    public Object configurable;
    public Object enumerable;
    public Object get;
    public Object set;
    private static PropertyMap $nasgenmap$;

    static {
        $clinit$();
    }

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.AccessorPropertyDescriptor.$clinit$():void, file: L-out.jar:jdk/nashorn/internal/objects/AccessorPropertyDescriptor.class
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:168)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        Caused by: java.lang.ArrayIndexOutOfBoundsException
        */
    public static void $clinit$() {
        /*
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.AccessorPropertyDescriptor.$clinit$():void, file: L-out.jar:jdk/nashorn/internal/objects/AccessorPropertyDescriptor.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.AccessorPropertyDescriptor.$clinit$():void");
    }

    public Object G$configurable() {
        return this.configurable;
    }

    public void S$configurable(Object obj) {
        this.configurable = obj;
    }

    public Object G$enumerable() {
        return this.enumerable;
    }

    public void S$enumerable(Object obj) {
        this.enumerable = obj;
    }

    public Object G$get() {
        return this.get;
    }

    public void S$get(Object obj) {
        this.get = obj;
    }

    public Object G$set() {
        return this.set;
    }

    public void S$set(Object obj) {
        this.set = obj;
    }

    AccessorPropertyDescriptor(boolean z, boolean z2, Object obj, Object obj2, Global global) {
        super(global.getObjectPrototype(), $nasgenmap$);
        this.configurable = Boolean.valueOf(z);
        this.enumerable = Boolean.valueOf(z2);
        this.get = obj;
        this.set = obj2;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isConfigurable() {
        return JSType.toBoolean(this.configurable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isEnumerable() {
        return JSType.toBoolean(this.enumerable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public Object getValue() {
        throw new UnsupportedOperationException(PropertyDescriptor.VALUE);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getGetter() {
        if (this.get instanceof ScriptFunction) {
            return (ScriptFunction) this.get;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getSetter() {
        if (this.set instanceof ScriptFunction) {
            return (ScriptFunction) this.set;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setConfigurable(boolean z) {
        this.configurable = Boolean.valueOf(z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setEnumerable(boolean z) {
        this.enumerable = Boolean.valueOf(z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setWritable(boolean z) {
        throw new UnsupportedOperationException("set writable");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setValue(Object obj) {
        throw new UnsupportedOperationException("set value");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setGetter(Object obj) {
        this.get = obj;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setSetter(Object obj) {
        this.set = obj;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public PropertyDescriptor fillFrom(ScriptObject scriptObject) {
        if (scriptObject.has(PropertyDescriptor.CONFIGURABLE)) {
            this.configurable = Boolean.valueOf(JSType.toBoolean(scriptObject.get(PropertyDescriptor.CONFIGURABLE)));
        } else {
            delete(PropertyDescriptor.CONFIGURABLE, false);
        }
        if (scriptObject.has(PropertyDescriptor.ENUMERABLE)) {
            this.enumerable = Boolean.valueOf(JSType.toBoolean(scriptObject.get(PropertyDescriptor.ENUMERABLE)));
        } else {
            delete(PropertyDescriptor.ENUMERABLE, false);
        }
        if (scriptObject.has(PropertyDescriptor.GET)) {
            Object obj = scriptObject.get(PropertyDescriptor.GET);
            if (obj == ScriptRuntime.UNDEFINED || (obj instanceof ScriptFunction)) {
                this.get = obj;
            } else {
                throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(obj)});
            }
        } else {
            delete(PropertyDescriptor.GET, false);
        }
        if (scriptObject.has(PropertyDescriptor.SET)) {
            Object obj2 = scriptObject.get(PropertyDescriptor.SET);
            if (obj2 == ScriptRuntime.UNDEFINED || (obj2 instanceof ScriptFunction)) {
                this.set = obj2;
            } else {
                throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(obj2)});
            }
        } else {
            delete(PropertyDescriptor.SET, false);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean hasAndEquals(PropertyDescriptor propertyDescriptor) {
        if (!(propertyDescriptor instanceof AccessorPropertyDescriptor)) {
            return false;
        }
        AccessorPropertyDescriptor accessorPropertyDescriptor = (AccessorPropertyDescriptor) propertyDescriptor;
        return (!has(PropertyDescriptor.CONFIGURABLE) || ScriptRuntime.sameValue(this.configurable, accessorPropertyDescriptor.configurable)) && (!has(PropertyDescriptor.ENUMERABLE) || ScriptRuntime.sameValue(this.enumerable, accessorPropertyDescriptor.enumerable)) && ((!has(PropertyDescriptor.GET) || ScriptRuntime.sameValue(this.get, accessorPropertyDescriptor.get)) && (!has(PropertyDescriptor.SET) || ScriptRuntime.sameValue(this.set, accessorPropertyDescriptor.set)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessorPropertyDescriptor)) {
            return false;
        }
        AccessorPropertyDescriptor accessorPropertyDescriptor = (AccessorPropertyDescriptor) obj;
        return ScriptRuntime.sameValue(this.configurable, accessorPropertyDescriptor.configurable) && ScriptRuntime.sameValue(this.enumerable, accessorPropertyDescriptor.enumerable) && ScriptRuntime.sameValue(this.get, accessorPropertyDescriptor.get) && ScriptRuntime.sameValue(this.set, accessorPropertyDescriptor.set);
    }

    public String toString() {
        return '[' + getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + " getter=" + this.get + " setter=" + this.set + "}]";
    }

    public int hashCode() {
        return (41 * ((41 * ((41 * (287 + Objects.hashCode(this.configurable))) + Objects.hashCode(this.enumerable))) + Objects.hashCode(this.get))) + Objects.hashCode(this.set);
    }
}

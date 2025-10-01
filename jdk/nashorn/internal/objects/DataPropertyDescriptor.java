package jdk.nashorn.internal.objects;

import java.util.Objects;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import kotlin.text.Typography;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/DataPropertyDescriptor.class */
public final class DataPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
    public Object configurable;
    public Object enumerable;
    public Object writable;
    public Object value;
    private static PropertyMap $nasgenmap$;

    static {
        $clinit$();
    }

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.DataPropertyDescriptor.$clinit$():void, file: L-out.jar:jdk/nashorn/internal/objects/DataPropertyDescriptor.class
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
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.DataPropertyDescriptor.$clinit$():void, file: L-out.jar:jdk/nashorn/internal/objects/DataPropertyDescriptor.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.DataPropertyDescriptor.$clinit$():void");
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

    public Object G$writable() {
        return this.writable;
    }

    public void S$writable(Object obj) {
        this.writable = obj;
    }

    public Object G$value() {
        return this.value;
    }

    public void S$value(Object obj) {
        this.value = obj;
    }

    DataPropertyDescriptor(boolean z, boolean z2, boolean z3, Object obj, Global global) {
        super(global.getObjectPrototype(), $nasgenmap$);
        this.configurable = Boolean.valueOf(z);
        this.enumerable = Boolean.valueOf(z2);
        this.writable = Boolean.valueOf(z3);
        this.value = obj;
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
    public boolean isWritable() {
        return JSType.toBoolean(this.writable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public Object getValue() {
        return this.value;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getGetter() {
        throw new UnsupportedOperationException("getter");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getSetter() {
        throw new UnsupportedOperationException("setter");
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
        this.writable = Boolean.valueOf(z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setValue(Object obj) {
        this.value = obj;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setGetter(Object obj) {
        throw new UnsupportedOperationException("set getter");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setSetter(Object obj) {
        throw new UnsupportedOperationException("set setter");
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
        if (scriptObject.has(PropertyDescriptor.WRITABLE)) {
            this.writable = Boolean.valueOf(JSType.toBoolean(scriptObject.get(PropertyDescriptor.WRITABLE)));
        } else {
            delete(PropertyDescriptor.WRITABLE, false);
        }
        if (scriptObject.has(PropertyDescriptor.VALUE)) {
            this.value = scriptObject.get(PropertyDescriptor.VALUE);
        } else {
            delete(PropertyDescriptor.VALUE, false);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean hasAndEquals(PropertyDescriptor propertyDescriptor) {
        if (!(propertyDescriptor instanceof DataPropertyDescriptor)) {
            return false;
        }
        DataPropertyDescriptor dataPropertyDescriptor = (DataPropertyDescriptor) propertyDescriptor;
        return (!has(PropertyDescriptor.CONFIGURABLE) || ScriptRuntime.sameValue(this.configurable, dataPropertyDescriptor.configurable)) && (!has(PropertyDescriptor.ENUMERABLE) || ScriptRuntime.sameValue(this.enumerable, dataPropertyDescriptor.enumerable)) && ((!has(PropertyDescriptor.WRITABLE) || ScriptRuntime.sameValue(this.writable, dataPropertyDescriptor.writable)) && (!has(PropertyDescriptor.VALUE) || ScriptRuntime.sameValue(this.value, dataPropertyDescriptor.value)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataPropertyDescriptor)) {
            return false;
        }
        DataPropertyDescriptor dataPropertyDescriptor = (DataPropertyDescriptor) obj;
        return ScriptRuntime.sameValue(this.configurable, dataPropertyDescriptor.configurable) && ScriptRuntime.sameValue(this.enumerable, dataPropertyDescriptor.enumerable) && ScriptRuntime.sameValue(this.writable, dataPropertyDescriptor.writable) && ScriptRuntime.sameValue(this.value, dataPropertyDescriptor.value);
    }

    public String toString() {
        return '[' + getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + " writable=" + this.writable + " value=" + this.value + "}]";
    }

    public int hashCode() {
        return (43 * ((43 * ((43 * (Typography.times + Objects.hashCode(this.configurable))) + Objects.hashCode(this.enumerable))) + Objects.hashCode(this.writable))) + Objects.hashCode(this.value);
    }
}

package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ObfuscationData.class */
public class ObfuscationData implements Iterable {
    private final Map data;
    private final Object defaultValue;

    public ObfuscationData() {
        this(null);
    }

    public ObfuscationData(Object obj) {
        this.data = new HashMap();
        this.defaultValue = obj;
    }

    @Deprecated
    public void add(ObfuscationType obfuscationType, Object obj) {
        put(obfuscationType, obj);
    }

    public void put(ObfuscationType obfuscationType, Object obj) {
        this.data.put(obfuscationType, obj);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public Object get(ObfuscationType obfuscationType) {
        Object obj = this.data.get(obfuscationType);
        return obj != null ? obj : this.defaultValue;
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return this.data.keySet().iterator();
    }

    public String toString() {
        return String.format("ObfuscationData[%s,DEFAULT=%s]", listValues(), this.defaultValue);
    }

    public String values() {
        return "[" + listValues() + "]";
    }

    private String listValues() {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (ObfuscationType obfuscationType : this.data.keySet()) {
            if (z) {
                sb.append(',');
            }
            sb.append(obfuscationType.getKey()).append('=').append(this.data.get(obfuscationType));
            z = true;
        }
        return sb.toString();
    }
}

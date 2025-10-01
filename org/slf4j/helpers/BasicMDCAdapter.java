package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

/* loaded from: L-out.jar:org/slf4j/helpers/BasicMDCAdapter.class */
public class BasicMDCAdapter implements MDCAdapter {
    private InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal(this) { // from class: org.slf4j.helpers.BasicMDCAdapter.1
        final BasicMDCAdapter this$0;

        {
            this.this$0 = this;
        }

        @Override // java.lang.InheritableThreadLocal
        protected Object childValue(Object obj) {
            return childValue((Map) obj);
        }

        protected Map childValue(Map map) {
            if (map == null) {
                return null;
            }
            return new HashMap(map);
        }
    };

    @Override // org.slf4j.spi.MDCAdapter
    public void put(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Map map = (Map) this.inheritableThreadLocal.get();
        if (map == null) {
            map = new HashMap();
            this.inheritableThreadLocal.set(map);
        }
        map.put(str, str2);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public String get(String str) {
        Map map = (Map) this.inheritableThreadLocal.get();
        if (map != null && str != null) {
            return (String) map.get(str);
        }
        return null;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void remove(String str) {
        Map map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            map.remove(str);
        }
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void clear() {
        Map map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            map.clear();
            this.inheritableThreadLocal.remove();
        }
    }

    public Set getKeys() {
        Map map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            return map.keySet();
        }
        return null;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public Map getCopyOfContextMap() {
        Map map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            return new HashMap(map);
        }
        return null;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void setContextMap(Map map) {
        this.inheritableThreadLocal.set(new HashMap(map));
    }
}

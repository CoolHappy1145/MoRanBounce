package jdk.nashorn.internal.runtime;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.LongAdder;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/PropertyListeners.class */
public class PropertyListeners {
    private Map listeners;
    private static LongAdder listenersAdded;
    private static LongAdder listenersRemoved;

    static {
        if (Context.DEBUG) {
            listenersAdded = new LongAdder();
            listenersRemoved = new LongAdder();
        }
    }

    PropertyListeners(PropertyListeners propertyListeners) {
        if (propertyListeners != null && propertyListeners.listeners != null) {
            this.listeners = new WeakHashMap();
            synchronized (propertyListeners) {
                for (Map.Entry entry : propertyListeners.listeners.entrySet()) {
                    this.listeners.put(entry.getKey(), new WeakPropertyMapSet((WeakPropertyMapSet) entry.getValue()));
                }
            }
        }
    }

    public static long getListenersAdded() {
        return listenersAdded.longValue();
    }

    public static long getListenersRemoved() {
        return listenersRemoved.longValue();
    }

    public static int getListenerCount(ScriptObject scriptObject) {
        return scriptObject.getMap().getListenerCount();
    }

    public int getListenerCount() {
        if (this.listeners == null) {
            return 0;
        }
        return this.listeners.size();
    }

    public static PropertyListeners addListener(PropertyListeners propertyListeners, String str, PropertyMap propertyMap) {
        if (propertyListeners == null || !propertyListeners.containsListener(str, propertyMap)) {
            PropertyListeners propertyListeners2 = new PropertyListeners(propertyListeners);
            propertyListeners2.addListener(str, propertyMap);
            return propertyListeners2;
        }
        return propertyListeners;
    }

    boolean containsListener(String str, PropertyMap propertyMap) {
        WeakPropertyMapSet weakPropertyMapSet;
        return (this.listeners == null || (weakPropertyMapSet = (WeakPropertyMapSet) this.listeners.get(str)) == null || !weakPropertyMapSet.contains(propertyMap)) ? false : true;
    }

    final void addListener(String str, PropertyMap propertyMap) {
        if (Context.DEBUG) {
            listenersAdded.increment();
        }
        if (this.listeners == null) {
            this.listeners = new WeakHashMap();
        }
        WeakPropertyMapSet weakPropertyMapSet = (WeakPropertyMapSet) this.listeners.get(str);
        if (weakPropertyMapSet == null) {
            weakPropertyMapSet = new WeakPropertyMapSet();
            this.listeners.put(str, weakPropertyMapSet);
        }
        if (!weakPropertyMapSet.contains(propertyMap)) {
            weakPropertyMapSet.add(propertyMap);
        }
    }

    public void propertyAdded(Property property) {
        WeakPropertyMapSet weakPropertyMapSet;
        if (this.listeners != null && (weakPropertyMapSet = (WeakPropertyMapSet) this.listeners.get(property.getKey())) != null) {
            Iterator it = weakPropertyMapSet.elements().iterator();
            while (it.hasNext()) {
                ((PropertyMap) it.next()).propertyAdded(property, false);
            }
            this.listeners.remove(property.getKey());
            if (Context.DEBUG) {
                listenersRemoved.increment();
            }
        }
    }

    public void propertyDeleted(Property property) {
        WeakPropertyMapSet weakPropertyMapSet;
        if (this.listeners != null && (weakPropertyMapSet = (WeakPropertyMapSet) this.listeners.get(property.getKey())) != null) {
            Iterator it = weakPropertyMapSet.elements().iterator();
            while (it.hasNext()) {
                ((PropertyMap) it.next()).propertyDeleted(property, false);
            }
            this.listeners.remove(property.getKey());
            if (Context.DEBUG) {
                listenersRemoved.increment();
            }
        }
    }

    public void propertyModified(Property property, Property property2) {
        WeakPropertyMapSet weakPropertyMapSet;
        if (this.listeners != null && (weakPropertyMapSet = (WeakPropertyMapSet) this.listeners.get(property.getKey())) != null) {
            Iterator it = weakPropertyMapSet.elements().iterator();
            while (it.hasNext()) {
                ((PropertyMap) it.next()).propertyModified(property, property2, false);
            }
            this.listeners.remove(property.getKey());
            if (Context.DEBUG) {
                listenersRemoved.increment();
            }
        }
    }

    public void protoChanged() {
        if (this.listeners != null) {
            Iterator it = this.listeners.values().iterator();
            while (it.hasNext()) {
                Iterator it2 = ((WeakPropertyMapSet) it.next()).elements().iterator();
                while (it2.hasNext()) {
                    ((PropertyMap) it2.next()).protoChanged(false);
                }
            }
            this.listeners.clear();
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/PropertyListeners$WeakPropertyMapSet.class */
    private static class WeakPropertyMapSet {
        private final WeakHashMap map;

        WeakPropertyMapSet() {
            this.map = new WeakHashMap();
        }

        WeakPropertyMapSet(WeakPropertyMapSet weakPropertyMapSet) {
            this.map = new WeakHashMap(weakPropertyMapSet.map);
        }

        void add(PropertyMap propertyMap) {
            this.map.put(propertyMap, Boolean.TRUE);
        }

        boolean contains(PropertyMap propertyMap) {
            return this.map.containsKey(propertyMap);
        }

        Set elements() {
            return this.map.keySet();
        }
    }
}

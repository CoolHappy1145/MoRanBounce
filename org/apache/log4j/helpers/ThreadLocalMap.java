package org.apache.log4j.helpers;

import java.util.Hashtable;

/* loaded from: L-out.jar:org/apache/log4j/helpers/ThreadLocalMap.class */
public final class ThreadLocalMap extends InheritableThreadLocal {
    @Override // java.lang.InheritableThreadLocal
    public final Object childValue(Object obj) {
        Hashtable hashtable = (Hashtable) obj;
        if (hashtable != null) {
            return hashtable.clone();
        }
        return null;
    }
}

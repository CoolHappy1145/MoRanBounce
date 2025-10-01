package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: L-out.jar:org/apache/log4j/SortedKeyEnumeration.class */
class SortedKeyEnumeration implements Enumeration {

    /* renamed from: e */
    private Enumeration f188e;

    public SortedKeyEnumeration(Hashtable hashtable) {
        Enumeration enumerationKeys = hashtable.keys();
        Vector vector = new Vector(hashtable.size());
        int i = 0;
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            int i2 = 0;
            while (i2 < i && str.compareTo((String) vector.get(i2)) > 0) {
                i2++;
            }
            vector.add(i2, str);
            i++;
        }
        this.f188e = vector.elements();
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.f188e.hasMoreElements();
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        return this.f188e.nextElement();
    }
}

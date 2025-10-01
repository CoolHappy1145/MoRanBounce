package jdk.nashorn.internal.runtime;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/PropertyHashMap.class */
public final class PropertyHashMap implements Map {
    private static final int INITIAL_BINS = 32;
    private static final int LIST_THRESHOLD = 8;
    public static final PropertyHashMap EMPTY_HASHMAP;
    private final int size;
    private final int threshold;
    private final Element list;
    private final Element[] bins;
    private Property[] properties;
    static final boolean $assertionsDisabled;

    @Override // java.util.Map
    public Object remove(Object obj) {
        return remove(obj);
    }

    @Override // java.util.Map
    public Object put(Object obj, Object obj2) {
        return put((String) obj, (Property) obj2);
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        return get(obj);
    }

    static {
        $assertionsDisabled = !PropertyHashMap.class.desiredAssertionStatus();
        EMPTY_HASHMAP = new PropertyHashMap();
    }

    private PropertyHashMap() {
        this.size = 0;
        this.threshold = 0;
        this.bins = null;
        this.list = null;
    }

    private PropertyHashMap(PropertyHashMap propertyHashMap) {
        this.size = propertyHashMap.size;
        this.threshold = propertyHashMap.threshold;
        this.bins = propertyHashMap.bins;
        this.list = propertyHashMap.list;
    }

    private PropertyHashMap(int i, Element[] elementArr, Element element) {
        int i2;
        this.size = i;
        if (elementArr != null) {
            int length = elementArr.length;
            i2 = (length >>> 1) + (length >>> 2);
        } else {
            i2 = 0;
        }
        this.threshold = i2;
        this.bins = elementArr;
        this.list = element;
    }

    public PropertyHashMap immutableReplace(Property property, Property property2) {
        if (!$assertionsDisabled && !property.getKey().equals(property2.getKey())) {
            throw new AssertionError("replacing properties with different keys: '" + property.getKey() + "' != '" + property2.getKey() + "'");
        }
        if ($assertionsDisabled || findElement(property.getKey()) != null) {
            return cloneMap().replaceNoClone(property.getKey(), property2);
        }
        throw new AssertionError("replacing property that doesn't exist in map: '" + property.getKey() + "'");
    }

    public PropertyHashMap immutableAdd(Property property) {
        return cloneMap(this.size + 1).addNoClone(property);
    }

    public PropertyHashMap immutableAdd(Property[] propertyArr) {
        PropertyHashMap propertyHashMapCloneMap = cloneMap(this.size + propertyArr.length);
        for (Property property : propertyArr) {
            propertyHashMapCloneMap = propertyHashMapCloneMap.addNoClone(property);
        }
        return propertyHashMapCloneMap;
    }

    public PropertyHashMap immutableAdd(Collection collection) {
        if (collection != null) {
            PropertyHashMap propertyHashMapCloneMap = cloneMap(this.size + collection.size());
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                propertyHashMapCloneMap = propertyHashMapCloneMap.addNoClone((Property) it.next());
            }
            return propertyHashMapCloneMap;
        }
        return this;
    }

    public PropertyHashMap immutableRemove(Property property) {
        return immutableRemove(property.getKey());
    }

    public PropertyHashMap immutableRemove(String str) {
        if (this.bins != null) {
            int iBinIndex = binIndex(this.bins, str);
            Element element = this.bins[iBinIndex];
            if (findElement(element, str) != null) {
                int i = this.size - 1;
                Element[] elementArr = null;
                if (i >= 8) {
                    elementArr = (Element[]) this.bins.clone();
                    elementArr[iBinIndex] = removeFromList(element, str);
                }
                return new PropertyHashMap(i, elementArr, removeFromList(this.list, str));
            }
        } else if (findElement(this.list, str) != null) {
            int i2 = this.size - 1;
            return i2 != 0 ? new PropertyHashMap(i2, null, removeFromList(this.list, str)) : EMPTY_HASHMAP;
        }
        return this;
    }

    public Property find(String str) {
        Element elementFindElement = findElement(str);
        if (elementFindElement != null) {
            return elementFindElement.getProperty();
        }
        return null;
    }

    Property[] getProperties() {
        if (this.properties == null) {
            Property[] propertyArr = new Property[this.size];
            int i = this.size;
            Element link = this.list;
            while (true) {
                Element element = link;
                if (element == null) {
                    break;
                }
                i--;
                propertyArr[i] = element.getProperty();
                link = element.getLink();
            }
            this.properties = propertyArr;
        }
        return this.properties;
    }

    private static int binIndex(Element[] elementArr, String str) {
        return str.hashCode() & (elementArr.length - 1);
    }

    private static int binsNeeded(int i) {
        return 1 << (32 - Integer.numberOfLeadingZeros((i + (i >>> 1)) | 31));
    }

    private static Element[] rehash(Element element, int i) {
        Element[] elementArr = new Element[i];
        Element link = element;
        while (true) {
            Element element2 = link;
            if (element2 != null) {
                Property property = element2.getProperty();
                int iBinIndex = binIndex(elementArr, property.getKey());
                elementArr[iBinIndex] = new Element(elementArr[iBinIndex], property);
                link = element2.getLink();
            } else {
                return elementArr;
            }
        }
    }

    private Element findElement(String str) {
        if (this.bins != null) {
            return findElement(this.bins[binIndex(this.bins, str)], str);
        }
        return findElement(this.list, str);
    }

    private static Element findElement(Element element, String str) {
        int iHashCode = str.hashCode();
        Element link = element;
        while (true) {
            Element element2 = link;
            if (element2 != null) {
                if (!element2.match(str, iHashCode)) {
                    link = element2.getLink();
                } else {
                    return element2;
                }
            } else {
                return null;
            }
        }
    }

    private PropertyHashMap cloneMap() {
        return new PropertyHashMap(this.size, this.bins == null ? null : (Element[]) this.bins.clone(), this.list);
    }

    private PropertyHashMap cloneMap(int i) {
        Element[] elementArrRehash;
        if (this.bins == null && i <= 8) {
            elementArrRehash = null;
        } else if (i > this.threshold) {
            elementArrRehash = rehash(this.list, binsNeeded(i));
        } else {
            elementArrRehash = (Element[]) this.bins.clone();
        }
        return new PropertyHashMap(i, elementArrRehash, this.list);
    }

    private PropertyHashMap addNoClone(Property property) {
        int i = this.size;
        String key = property.getKey();
        Element elementRemoveFromList = this.list;
        if (this.bins != null) {
            int iBinIndex = binIndex(this.bins, key);
            Element elementRemoveFromList2 = this.bins[iBinIndex];
            if (findElement(elementRemoveFromList2, key) != null) {
                i--;
                elementRemoveFromList2 = removeFromList(elementRemoveFromList2, key);
                elementRemoveFromList = removeFromList(this.list, key);
            }
            this.bins[iBinIndex] = new Element(elementRemoveFromList2, property);
        } else if (findElement(this.list, key) != null) {
            i--;
            elementRemoveFromList = removeFromList(this.list, key);
        }
        return new PropertyHashMap(i, this.bins, new Element(elementRemoveFromList, property));
    }

    private PropertyHashMap replaceNoClone(String str, Property property) {
        if (this.bins != null) {
            int iBinIndex = binIndex(this.bins, str);
            this.bins[iBinIndex] = replaceInList(this.bins[iBinIndex], str, property);
        }
        return new PropertyHashMap(this.size, this.bins, replaceInList(this.list, str, property));
    }

    private static Element removeFromList(Element element, String str) {
        if (element == null) {
            return null;
        }
        int iHashCode = str.hashCode();
        if (element.match(str, iHashCode)) {
            return element.getLink();
        }
        Element element2 = new Element(null, element.getProperty());
        Element element3 = element2;
        Element link = element.getLink();
        while (true) {
            Element element4 = link;
            if (element4 != null) {
                if (element4.match(str, iHashCode)) {
                    element3.setLink(element4.getLink());
                    return element2;
                }
                Element element5 = new Element(null, element4.getProperty());
                element3.setLink(element5);
                element3 = element5;
                link = element4.getLink();
            } else {
                return element;
            }
        }
    }

    private static Element replaceInList(Element element, String str, Property property) {
        if (!$assertionsDisabled && element == null) {
            throw new AssertionError();
        }
        int iHashCode = str.hashCode();
        if (element.match(str, iHashCode)) {
            return new Element(element.getLink(), property);
        }
        Element element2 = new Element(null, element.getProperty());
        Element element3 = element2;
        Element link = element.getLink();
        while (true) {
            Element element4 = link;
            if (element4 != null) {
                if (element4.match(str, iHashCode)) {
                    element3.setLink(new Element(element4.getLink(), property));
                    return element2;
                }
                Element element5 = new Element(null, element4.getProperty());
                element3.setLink(element5);
                element3 = element5;
                link = element4.getLink();
            } else {
                return element;
            }
        }
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        if (obj instanceof String) {
            return findElement((String) obj) != null;
        }
        if ($assertionsDisabled || (obj instanceof String)) {
            return false;
        }
        throw new AssertionError();
    }

    public boolean containsKey(String str) {
        return findElement(str) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        Element elementFindElement;
        return (obj instanceof Property) && (elementFindElement = findElement(((Property) obj).getKey())) != null && elementFindElement.getProperty().equals(obj);
    }

    @Override // java.util.Map
    public Property get(Object obj) {
        if (obj instanceof String) {
            Element elementFindElement = findElement((String) obj);
            if (elementFindElement != null) {
                return elementFindElement.getProperty();
            }
            return null;
        }
        if ($assertionsDisabled || (obj instanceof String)) {
            return null;
        }
        throw new AssertionError();
    }

    public Property get(String str) {
        Element elementFindElement = findElement(str);
        if (elementFindElement != null) {
            return elementFindElement.getProperty();
        }
        return null;
    }

    public Property put(String str, Property property) {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public Property remove(Object obj) {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Immutable map.");
    }

    @Override // java.util.Map
    public Set keySet() {
        HashSet hashSet = new HashSet();
        Element link = this.list;
        while (true) {
            Element element = link;
            if (element != null) {
                hashSet.add(element.getKey());
                link = element.getLink();
            } else {
                return Collections.unmodifiableSet(hashSet);
            }
        }
    }

    @Override // java.util.Map
    public Collection values() {
        return Collections.unmodifiableList(Arrays.asList(getProperties()));
    }

    @Override // java.util.Map
    public Set entrySet() {
        HashSet hashSet = new HashSet();
        Element link = this.list;
        while (true) {
            Element element = link;
            if (element != null) {
                hashSet.add(element);
                link = element.getLink();
            } else {
                return Collections.unmodifiableSet(hashSet);
            }
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/PropertyHashMap$Element.class */
    static final class Element implements Map.Entry {
        private Element link;
        private final Property property;
        private final String key;
        private final int hashCode;
        static final boolean $assertionsDisabled;

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            return setValue((Property) obj);
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return getValue();
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return getKey();
        }

        static {
            $assertionsDisabled = !PropertyHashMap.class.desiredAssertionStatus();
        }

        Element(Element element, Property property) {
            this.link = element;
            this.property = property;
            this.key = property.getKey();
            this.hashCode = this.key.hashCode();
        }

        boolean match(String str, int i) {
            return this.hashCode == i && this.key.equals(str);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if ($assertionsDisabled || !(this.property == null || obj == null)) {
                return (obj instanceof Element) && this.property.equals(((Element) obj).property);
            }
            throw new AssertionError();
        }

        @Override // java.util.Map.Entry
        public String getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Property getValue() {
            return this.property;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.hashCode;
        }

        public Property setValue(Property property) {
            throw new UnsupportedOperationException("Immutable map.");
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('[');
            Element element = this;
            do {
                stringBuffer.append(element.getValue());
                element = element.link;
                if (element != null) {
                    stringBuffer.append(ActivityStack.GLUE_STRING);
                }
            } while (element != null);
            stringBuffer.append(']');
            return stringBuffer.toString();
        }

        Element getLink() {
            return this.link;
        }

        void setLink(Element element) {
            this.link = element;
        }

        Property getProperty() {
            return this.property;
        }
    }
}

package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import jdk.nashorn.internal.runtime.PropertyMap;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ConstantData.class */
final class ConstantData {
    final List constants = new ArrayList();
    final Map stringMap = new HashMap();
    final Map objectMap = new HashMap();
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ConstantData.class.desiredAssertionStatus();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ConstantData$ArrayWrapper.class */
    private static class ArrayWrapper {
        private final Object array;
        private final int hashCode = calcHashCode();

        public ArrayWrapper(Object obj) {
            this.array = obj;
        }

        private int calcHashCode() {
            Class<?> cls = this.array.getClass();
            if (!cls.getComponentType().isPrimitive()) {
                return Arrays.hashCode((Object[]) this.array);
            }
            if (cls == double[].class) {
                return Arrays.hashCode((double[]) this.array);
            }
            if (cls == long[].class) {
                return Arrays.hashCode((long[]) this.array);
            }
            if (cls == int[].class) {
                return Arrays.hashCode((int[]) this.array);
            }
            throw new AssertionError("ConstantData doesn't support " + cls);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ArrayWrapper)) {
                return false;
            }
            Object obj2 = ((ArrayWrapper) obj).array;
            if (this.array == obj2) {
                return true;
            }
            Class<?> cls = this.array.getClass();
            if (cls == obj2.getClass()) {
                if (!cls.getComponentType().isPrimitive()) {
                    return Arrays.equals((Object[]) this.array, (Object[]) obj2);
                }
                if (cls == double[].class) {
                    return Arrays.equals((double[]) this.array, (double[]) obj2);
                }
                if (cls == long[].class) {
                    return Arrays.equals((long[]) this.array, (long[]) obj2);
                }
                if (cls == int[].class) {
                    return Arrays.equals((int[]) this.array, (int[]) obj2);
                }
                return false;
            }
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ConstantData$PropertyMapWrapper.class */
    private static class PropertyMapWrapper {
        private final PropertyMap propertyMap;
        private final int hashCode;

        public PropertyMapWrapper(PropertyMap propertyMap) {
            this.hashCode = Arrays.hashCode(propertyMap.getProperties()) + (31 * Objects.hashCode(propertyMap.getClassName()));
            this.propertyMap = propertyMap;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PropertyMapWrapper)) {
                return false;
            }
            PropertyMap propertyMap = ((PropertyMapWrapper) obj).propertyMap;
            return this.propertyMap == propertyMap || (Arrays.equals(this.propertyMap.getProperties(), propertyMap.getProperties()) && Objects.equals(this.propertyMap.getClassName(), propertyMap.getClassName()));
        }
    }

    ConstantData() {
    }

    public int add(String str) {
        Integer num = (Integer) this.stringMap.get(str);
        if (num != null) {
            return num.intValue();
        }
        this.constants.add(str);
        int size = this.constants.size() - 1;
        this.stringMap.put(str, Integer.valueOf(size));
        return size;
    }

    public int add(Object obj) {
        Object propertyMapWrapper;
        if (!$assertionsDisabled && obj == null) {
            throw new AssertionError();
        }
        if (obj.getClass().isArray()) {
            propertyMapWrapper = new ArrayWrapper(obj);
        } else if (obj instanceof PropertyMap) {
            propertyMapWrapper = new PropertyMapWrapper((PropertyMap) obj);
        } else {
            propertyMapWrapper = obj;
        }
        Integer num = (Integer) this.objectMap.get(propertyMapWrapper);
        if (num != null) {
            return num.intValue();
        }
        this.constants.add(obj);
        int size = this.constants.size() - 1;
        this.objectMap.put(propertyMapWrapper, Integer.valueOf(size));
        return size;
    }

    Object[] toArray() {
        return this.constants.toArray();
    }
}

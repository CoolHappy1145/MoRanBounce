package jdk.internal.dynalink.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/TypeUtilities.class */
public class TypeUtilities {
    static final Class OBJECT_CLASS;
    private static final Map WRAPPER_TYPES;
    private static final Map PRIMITIVE_TYPES;
    private static final Map PRIMITIVE_TYPES_BY_NAME;
    private static final Map WRAPPER_TO_PRIMITIVE_TYPES;
    private static final Set PRIMITIVE_WRAPPER_TYPES;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !TypeUtilities.class.desiredAssertionStatus();
        OBJECT_CLASS = Object.class;
        WRAPPER_TYPES = createWrapperTypes();
        PRIMITIVE_TYPES = invertMap(WRAPPER_TYPES);
        PRIMITIVE_TYPES_BY_NAME = createClassNameMapping(WRAPPER_TYPES.keySet());
        WRAPPER_TO_PRIMITIVE_TYPES = createWrapperToPrimitiveTypes();
        PRIMITIVE_WRAPPER_TYPES = createPrimitiveWrapperTypes();
    }

    private TypeUtilities() {
    }

    public static Class getCommonLosslessConversionType(Class cls, Class cls2) {
        if (cls == cls2) {
            return cls;
        }
        if (cls == Void.TYPE || cls2 == Void.TYPE) {
            return Object.class;
        }
        if (isConvertibleWithoutLoss(cls2, cls)) {
            return cls;
        }
        if (isConvertibleWithoutLoss(cls, cls2)) {
            return cls2;
        }
        if (cls.isPrimitive() && cls2.isPrimitive()) {
            if ((cls == Byte.TYPE && cls2 == Character.TYPE) || (cls == Character.TYPE && cls2 == Byte.TYPE)) {
                return Integer.TYPE;
            }
            if ((cls == Short.TYPE && cls2 == Character.TYPE) || (cls == Character.TYPE && cls2 == Short.TYPE)) {
                return Integer.TYPE;
            }
            if ((cls == Integer.TYPE && cls2 == Float.TYPE) || (cls == Float.TYPE && cls2 == Integer.TYPE)) {
                return Double.TYPE;
            }
        }
        return getMostSpecificCommonTypeUnequalNonprimitives(cls, cls2);
    }

    private static Class getMostSpecificCommonTypeUnequalNonprimitives(Class cls, Class cls2) {
        Class wrapperType = cls.isPrimitive() ? getWrapperType(cls) : cls;
        Class wrapperType2 = cls2.isPrimitive() ? getWrapperType(cls2) : cls2;
        Set<Class> assignables = getAssignables(wrapperType, wrapperType2);
        assignables.retainAll(getAssignables(wrapperType2, wrapperType));
        if (assignables.isEmpty()) {
            return Object.class;
        }
        ArrayList arrayList = new ArrayList();
        for (Class cls3 : assignables) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    Class cls4 = (Class) it.next();
                    if (isSubtype(cls4, cls3)) {
                        break;
                    }
                    if (isSubtype(cls3, cls4)) {
                        it.remove();
                    }
                } else {
                    arrayList.add(cls3);
                    break;
                }
            }
        }
        if (arrayList.size() > 1) {
            return Object.class;
        }
        return (Class) arrayList.get(0);
    }

    private static Set getAssignables(Class cls, Class cls2) {
        HashSet hashSet = new HashSet();
        collectAssignables(cls, cls2, hashSet);
        return hashSet;
    }

    private static void collectAssignables(Class cls, Class cls2, Set set) {
        if (cls.isAssignableFrom(cls2)) {
            set.add(cls);
        }
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            collectAssignables(superclass, cls2, set);
        }
        for (Class<?> cls3 : cls.getInterfaces()) {
            collectAssignables(cls3, cls2, set);
        }
    }

    private static Map createWrapperTypes() {
        IdentityHashMap identityHashMap = new IdentityHashMap(8);
        identityHashMap.put(Boolean.TYPE, Boolean.class);
        identityHashMap.put(Byte.TYPE, Byte.class);
        identityHashMap.put(Character.TYPE, Character.class);
        identityHashMap.put(Short.TYPE, Short.class);
        identityHashMap.put(Integer.TYPE, Integer.class);
        identityHashMap.put(Long.TYPE, Long.class);
        identityHashMap.put(Float.TYPE, Float.class);
        identityHashMap.put(Double.TYPE, Double.class);
        return Collections.unmodifiableMap(identityHashMap);
    }

    private static Map createClassNameMapping(Collection collection) {
        HashMap map = new HashMap();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Class cls = (Class) it.next();
            map.put(cls.getName(), cls);
        }
        return map;
    }

    private static Map invertMap(Map map) {
        IdentityHashMap identityHashMap = new IdentityHashMap(map.size());
        for (Map.Entry entry : map.entrySet()) {
            identityHashMap.put(entry.getValue(), entry.getKey());
        }
        return Collections.unmodifiableMap(identityHashMap);
    }

    public static boolean isMethodInvocationConvertible(Class cls, Class cls2) {
        Class cls3;
        if (cls2.isAssignableFrom(cls)) {
            return true;
        }
        if (!cls.isPrimitive()) {
            return cls2.isPrimitive() && (cls3 = (Class) PRIMITIVE_TYPES.get(cls)) != null && (cls3 == cls2 || isProperPrimitiveSubtype(cls3, cls2));
        }
        if (cls2.isPrimitive()) {
            return isProperPrimitiveSubtype(cls, cls2);
        }
        if ($assertionsDisabled || WRAPPER_TYPES.get(cls) != null) {
            return cls2.isAssignableFrom((Class) WRAPPER_TYPES.get(cls));
        }
        throw new AssertionError(cls.getName());
    }

    public static boolean isConvertibleWithoutLoss(Class cls, Class cls2) {
        if (cls2.isAssignableFrom(cls) || cls2 == Void.TYPE) {
            return true;
        }
        if (cls.isPrimitive()) {
            if (cls == Void.TYPE) {
                return cls2 == Object.class;
            }
            if (cls2.isPrimitive()) {
                return isProperPrimitiveLosslessSubtype(cls, cls2);
            }
            if ($assertionsDisabled || WRAPPER_TYPES.get(cls) != null) {
                return cls2.isAssignableFrom((Class) WRAPPER_TYPES.get(cls));
            }
            throw new AssertionError(cls.getName());
        }
        return false;
    }

    public static boolean isPotentiallyConvertible(Class cls, Class cls2) {
        if (areAssignable(cls, cls2)) {
            return true;
        }
        if (cls.isPrimitive()) {
            return cls2.isPrimitive() || isAssignableFromBoxedPrimitive(cls2);
        }
        if (cls2.isPrimitive()) {
            return isAssignableFromBoxedPrimitive(cls);
        }
        return false;
    }

    public static boolean areAssignable(Class cls, Class cls2) {
        return cls.isAssignableFrom(cls2) || cls2.isAssignableFrom(cls);
    }

    public static boolean isSubtype(Class cls, Class cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return true;
        }
        if (cls2.isPrimitive() && cls.isPrimitive()) {
            return isProperPrimitiveSubtype(cls, cls2);
        }
        return false;
    }

    private static boolean isProperPrimitiveSubtype(Class cls, Class cls2) {
        if (cls2 == Boolean.TYPE || cls == Boolean.TYPE) {
            return false;
        }
        return cls == Byte.TYPE ? cls2 != Character.TYPE : cls == Character.TYPE ? (cls2 == Short.TYPE || cls2 == Byte.TYPE) ? false : true : cls == Short.TYPE ? (cls2 == Character.TYPE || cls2 == Byte.TYPE) ? false : true : cls == Integer.TYPE ? cls2 == Long.TYPE || cls2 == Float.TYPE || cls2 == Double.TYPE : cls == Long.TYPE ? cls2 == Float.TYPE || cls2 == Double.TYPE : cls == Float.TYPE && cls2 == Double.TYPE;
    }

    private static boolean isProperPrimitiveLosslessSubtype(Class cls, Class cls2) {
        if (cls2 == Boolean.TYPE || cls == Boolean.TYPE || cls2 == Character.TYPE || cls == Character.TYPE) {
            return false;
        }
        if (cls == Byte.TYPE) {
            return true;
        }
        return cls == Short.TYPE ? cls2 != Byte.TYPE : cls == Integer.TYPE ? cls2 == Long.TYPE || cls2 == Double.TYPE : cls == Float.TYPE && cls2 == Double.TYPE;
    }

    private static Map createWrapperToPrimitiveTypes() {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        identityHashMap.put(Void.class, Void.TYPE);
        identityHashMap.put(Boolean.class, Boolean.TYPE);
        identityHashMap.put(Byte.class, Byte.TYPE);
        identityHashMap.put(Character.class, Character.TYPE);
        identityHashMap.put(Short.class, Short.TYPE);
        identityHashMap.put(Integer.class, Integer.TYPE);
        identityHashMap.put(Long.class, Long.TYPE);
        identityHashMap.put(Float.class, Float.TYPE);
        identityHashMap.put(Double.class, Double.TYPE);
        return identityHashMap;
    }

    private static Set createPrimitiveWrapperTypes() {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        addClassHierarchy(identityHashMap, Boolean.class);
        addClassHierarchy(identityHashMap, Byte.class);
        addClassHierarchy(identityHashMap, Character.class);
        addClassHierarchy(identityHashMap, Short.class);
        addClassHierarchy(identityHashMap, Integer.class);
        addClassHierarchy(identityHashMap, Long.class);
        addClassHierarchy(identityHashMap, Float.class);
        addClassHierarchy(identityHashMap, Double.class);
        return identityHashMap.keySet();
    }

    private static void addClassHierarchy(Map map, Class cls) {
        if (cls == null) {
            return;
        }
        map.put(cls, cls);
        addClassHierarchy(map, cls.getSuperclass());
        for (Class<?> cls2 : cls.getInterfaces()) {
            addClassHierarchy(map, cls2);
        }
    }

    private static boolean isAssignableFromBoxedPrimitive(Class cls) {
        return PRIMITIVE_WRAPPER_TYPES.contains(cls);
    }

    public static Class getPrimitiveTypeByName(String str) {
        return (Class) PRIMITIVE_TYPES_BY_NAME.get(str);
    }

    public static Class getPrimitiveType(Class cls) {
        return (Class) WRAPPER_TO_PRIMITIVE_TYPES.get(cls);
    }

    public static Class getWrapperType(Class cls) {
        return (Class) WRAPPER_TYPES.get(cls);
    }

    public static boolean isWrapperType(Class cls) {
        return PRIMITIVE_TYPES.containsKey(cls);
    }
}

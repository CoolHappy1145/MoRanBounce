package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/ClassString.class */
final class ClassString {
    static final Class NULL_CLASS = new Object() { // from class: jdk.internal.dynalink.beans.ClassString.1
    }.getClass();
    private final Class[] classes;
    private int hashCode;

    ClassString(Class[] clsArr) {
        this.classes = clsArr;
    }

    ClassString(MethodType methodType) {
        this(methodType.parameterArray());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ClassString)) {
            return false;
        }
        Class[] clsArr = ((ClassString) obj).classes;
        if (clsArr.length != this.classes.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (clsArr[i] != this.classes[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int iHashCode = 0;
            for (int i = 0; i < this.classes.length; i++) {
                iHashCode ^= this.classes[i].hashCode();
            }
            this.hashCode = iHashCode;
        }
        return this.hashCode;
    }

    boolean isVisibleFrom(ClassLoader classLoader) {
        for (int i = 0; i < this.classes.length; i++) {
            if (!Guards.canReferenceDirectly(classLoader, this.classes[i].getClassLoader())) {
                return false;
            }
        }
        return true;
    }

    List getMaximallySpecifics(List list, LinkerServices linkerServices, boolean z) {
        return MaximallySpecific.getMaximallySpecificMethodHandles(getApplicables(list, linkerServices, z), z, this.classes, linkerServices);
    }

    LinkedList getApplicables(List list, LinkerServices linkerServices, boolean z) {
        LinkedList linkedList = new LinkedList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MethodHandle methodHandle = (MethodHandle) it.next();
            if (isApplicable(methodHandle, linkerServices, z)) {
                linkedList.add(methodHandle);
            }
        }
        return linkedList;
    }

    private boolean isApplicable(MethodHandle methodHandle, LinkerServices linkerServices, boolean z) {
        Class<?>[] clsArrParameterArray = methodHandle.type().parameterArray();
        int length = this.classes.length;
        int length2 = clsArrParameterArray.length - (z ? 1 : 0);
        if (z) {
            if (length < length2) {
                return false;
            }
        } else if (length != length2) {
            return false;
        }
        for (int i = 1; i < length2; i++) {
            if (!canConvert(linkerServices, this.classes[i], clsArrParameterArray[i])) {
                return false;
            }
        }
        if (z) {
            Class<?> componentType = clsArrParameterArray[length2].getComponentType();
            for (int i2 = length2; i2 < length; i2++) {
                if (!canConvert(linkerServices, this.classes[i2], componentType)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private static boolean canConvert(LinkerServices linkerServices, Class cls, Class cls2) {
        return cls == NULL_CLASS ? !cls2.isPrimitive() : linkerServices == null ? TypeUtilities.isMethodInvocationConvertible(cls, cls2) : linkerServices.canConvert(cls, cls2);
    }
}

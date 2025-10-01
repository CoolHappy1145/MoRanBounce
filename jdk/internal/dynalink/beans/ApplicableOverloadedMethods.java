package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/ApplicableOverloadedMethods.class */
class ApplicableOverloadedMethods {
    private final List methods = new LinkedList();
    private final boolean varArgs;
    static final ApplicabilityTest APPLICABLE_BY_SUBTYPING = new ApplicabilityTest() { // from class: jdk.internal.dynalink.beans.ApplicableOverloadedMethods.1
        @Override // jdk.internal.dynalink.beans.ApplicableOverloadedMethods.ApplicabilityTest
        boolean isApplicable(MethodType methodType, SingleDynamicMethod singleDynamicMethod) {
            MethodType methodType2 = singleDynamicMethod.getMethodType();
            int iParameterCount = methodType2.parameterCount();
            if (iParameterCount != methodType.parameterCount()) {
                return false;
            }
            for (int i = 1; i < iParameterCount; i++) {
                if (!TypeUtilities.isSubtype(methodType.parameterType(i), methodType2.parameterType(i))) {
                    return false;
                }
            }
            return true;
        }
    };
    static final ApplicabilityTest APPLICABLE_BY_METHOD_INVOCATION_CONVERSION = new ApplicabilityTest() { // from class: jdk.internal.dynalink.beans.ApplicableOverloadedMethods.2
        @Override // jdk.internal.dynalink.beans.ApplicableOverloadedMethods.ApplicabilityTest
        boolean isApplicable(MethodType methodType, SingleDynamicMethod singleDynamicMethod) {
            MethodType methodType2 = singleDynamicMethod.getMethodType();
            int iParameterCount = methodType2.parameterCount();
            if (iParameterCount != methodType.parameterCount()) {
                return false;
            }
            for (int i = 1; i < iParameterCount; i++) {
                if (!TypeUtilities.isMethodInvocationConvertible(methodType.parameterType(i), methodType2.parameterType(i))) {
                    return false;
                }
            }
            return true;
        }
    };
    static final ApplicabilityTest APPLICABLE_BY_VARIABLE_ARITY = new ApplicabilityTest() { // from class: jdk.internal.dynalink.beans.ApplicableOverloadedMethods.3
        @Override // jdk.internal.dynalink.beans.ApplicableOverloadedMethods.ApplicabilityTest
        boolean isApplicable(MethodType methodType, SingleDynamicMethod singleDynamicMethod) {
            if (!singleDynamicMethod.isVarArgs()) {
                return false;
            }
            MethodType methodType2 = singleDynamicMethod.getMethodType();
            int iParameterCount = methodType2.parameterCount() - 1;
            int iParameterCount2 = methodType.parameterCount();
            if (iParameterCount > iParameterCount2) {
                return false;
            }
            for (int i = 1; i < iParameterCount; i++) {
                if (!TypeUtilities.isMethodInvocationConvertible(methodType.parameterType(i), methodType2.parameterType(i))) {
                    return false;
                }
            }
            Class<?> componentType = methodType2.parameterType(iParameterCount).getComponentType();
            for (int i2 = iParameterCount; i2 < iParameterCount2; i2++) {
                if (!TypeUtilities.isMethodInvocationConvertible(methodType.parameterType(i2), componentType)) {
                    return false;
                }
            }
            return true;
        }
    };

    ApplicableOverloadedMethods(List list, MethodType methodType, ApplicabilityTest applicabilityTest) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SingleDynamicMethod singleDynamicMethod = (SingleDynamicMethod) it.next();
            if (applicabilityTest.isApplicable(methodType, singleDynamicMethod)) {
                this.methods.add(singleDynamicMethod);
            }
        }
        this.varArgs = applicabilityTest == APPLICABLE_BY_VARIABLE_ARITY;
    }

    List getMethods() {
        return this.methods;
    }

    List findMaximallySpecificMethods() {
        return MaximallySpecific.getMaximallySpecificMethods(this.methods, this.varArgs);
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/ApplicableOverloadedMethods$ApplicabilityTest.class */
    static abstract class ApplicabilityTest {
        abstract boolean isApplicable(MethodType methodType, SingleDynamicMethod singleDynamicMethod);

        ApplicabilityTest() {
        }
    }
}

package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/DynamicMethod.class */
abstract class DynamicMethod {
    private final String name;

    abstract MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices);

    abstract SingleDynamicMethod getMethodForExactParamTypes(String str);

    abstract boolean contains(SingleDynamicMethod singleDynamicMethod);

    DynamicMethod(String str) {
        this.name = str;
    }

    String getName() {
        return this.name;
    }

    static String getClassAndMethodName(Class cls, String str) {
        String canonicalName = cls.getCanonicalName();
        return (canonicalName == null ? cls.getName() : canonicalName) + "." + str;
    }

    public String toString() {
        return "[" + getClass().getName() + " " + getName() + "]";
    }
}

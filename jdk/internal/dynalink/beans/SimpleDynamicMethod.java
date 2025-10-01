package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/SimpleDynamicMethod.class */
class SimpleDynamicMethod extends SingleDynamicMethod {
    private final MethodHandle target;
    private final boolean constructor;

    SimpleDynamicMethod(MethodHandle methodHandle, Class cls, String str) {
        this(methodHandle, cls, str, false);
    }

    SimpleDynamicMethod(MethodHandle methodHandle, Class cls, String str, boolean z) {
        super(getName(methodHandle, cls, str, z));
        this.target = methodHandle;
        this.constructor = z;
    }

    private static String getName(MethodHandle methodHandle, Class cls, String str, boolean z) {
        return getMethodNameWithSignature(methodHandle.type(), z ? str : getClassAndMethodName(cls, str), !z);
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    boolean isVarArgs() {
        return this.target.isVarargsCollector();
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    MethodType getMethodType() {
        return this.target.type();
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    MethodHandle getTarget(MethodHandles.Lookup lookup) {
        return this.target;
    }

    boolean isConstructor() {
        return this.constructor;
    }
}

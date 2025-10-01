package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/UnnamedDynCallSiteDescriptor.class */
class UnnamedDynCallSiteDescriptor extends AbstractCallSiteDescriptor {
    private final MethodType methodType;

    /* renamed from: op */
    private final String f5op;

    UnnamedDynCallSiteDescriptor(String str, MethodType methodType) {
        this.f5op = str;
        this.methodType = methodType;
    }

    String getOp() {
        return this.f5op;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getNameToken(int i) {
        switch (i) {
            case 0:
                return "dyn";
            case 1:
                return this.f5op;
            default:
                throw new IndexOutOfBoundsException(String.valueOf(i));
        }
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodType getMethodType() {
        return this.methodType;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType methodType) {
        return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new UnnamedDynCallSiteDescriptor(this.f5op, methodType));
    }
}

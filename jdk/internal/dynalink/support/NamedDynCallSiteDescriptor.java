package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/NamedDynCallSiteDescriptor.class */
class NamedDynCallSiteDescriptor extends UnnamedDynCallSiteDescriptor {
    private final String name;

    NamedDynCallSiteDescriptor(String str, String str2, MethodType methodType) {
        super(str, methodType);
        this.name = str2;
    }

    @Override // jdk.internal.dynalink.support.UnnamedDynCallSiteDescriptor, jdk.internal.dynalink.CallSiteDescriptor
    public String getNameToken(int i) {
        switch (i) {
            case 0:
                return "dyn";
            case 1:
                return getOp();
            case 2:
                return this.name;
            default:
                throw new IndexOutOfBoundsException(String.valueOf(i));
        }
    }

    @Override // jdk.internal.dynalink.support.UnnamedDynCallSiteDescriptor, jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType methodType) {
        return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new NamedDynCallSiteDescriptor(getOp(), this.name, methodType));
    }
}

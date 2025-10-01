package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/LookupCallSiteDescriptor.class */
class LookupCallSiteDescriptor extends DefaultCallSiteDescriptor {
    private final MethodHandles.Lookup lookup;

    LookupCallSiteDescriptor(String[] strArr, MethodType methodType, MethodHandles.Lookup lookup) {
        super(strArr, methodType);
        this.lookup = lookup;
    }

    @Override // jdk.internal.dynalink.support.AbstractCallSiteDescriptor, jdk.internal.dynalink.CallSiteDescriptor
    public MethodHandles.Lookup getLookup() {
        return this.lookup;
    }

    @Override // jdk.internal.dynalink.support.DefaultCallSiteDescriptor, jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType methodType) {
        return new LookupCallSiteDescriptor(getTokenizedName(), methodType, this.lookup);
    }
}

package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/DefaultCallSiteDescriptor.class */
class DefaultCallSiteDescriptor extends AbstractCallSiteDescriptor {
    private final String[] tokenizedName;
    private final MethodType methodType;

    DefaultCallSiteDescriptor(String[] strArr, MethodType methodType) {
        this.tokenizedName = strArr;
        this.methodType = methodType;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public int getNameTokenCount() {
        return this.tokenizedName.length;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getNameToken(int i) {
        try {
            return this.tokenizedName[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    String[] getTokenizedName() {
        return this.tokenizedName;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodType getMethodType() {
        return this.methodType;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType methodType) {
        return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new DefaultCallSiteDescriptor(this.tokenizedName, methodType));
    }
}

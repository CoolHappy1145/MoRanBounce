package jdk.internal.dynalink.support;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/RuntimeContextLinkRequestImpl.class */
public class RuntimeContextLinkRequestImpl extends LinkRequestImpl {
    private final int runtimeContextArgCount;
    private LinkRequestImpl contextStrippedRequest;

    public RuntimeContextLinkRequestImpl(CallSiteDescriptor callSiteDescriptor, Object obj, int i, boolean z, Object[] objArr, int i2) {
        super(callSiteDescriptor, obj, i, z, objArr);
        if (i2 < 1) {
            throw new IllegalArgumentException("runtimeContextArgCount < 1");
        }
        this.runtimeContextArgCount = i2;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public LinkRequest withoutRuntimeContext() {
        if (this.contextStrippedRequest == null) {
            this.contextStrippedRequest = new LinkRequestImpl(CallSiteDescriptorFactory.dropParameterTypes(getCallSiteDescriptor(), 1, this.runtimeContextArgCount + 1), getCallSiteToken(), getLinkCount(), isCallSiteUnstable(), getTruncatedArguments());
        }
        return this.contextStrippedRequest;
    }

    @Override // jdk.internal.dynalink.support.LinkRequestImpl, jdk.internal.dynalink.linker.LinkRequest
    public LinkRequest replaceArguments(CallSiteDescriptor callSiteDescriptor, Object[] objArr) {
        return new RuntimeContextLinkRequestImpl(callSiteDescriptor, getCallSiteToken(), getLinkCount(), isCallSiteUnstable(), objArr, this.runtimeContextArgCount);
    }

    private Object[] getTruncatedArguments() {
        Object[] arguments = getArguments();
        Object[] objArr = new Object[arguments.length - this.runtimeContextArgCount];
        objArr[0] = arguments[0];
        System.arraycopy(arguments, this.runtimeContextArgCount + 1, objArr, 1, objArr.length - 1);
        return objArr;
    }
}

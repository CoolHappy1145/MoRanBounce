package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MutableCallSite;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.RelinkableCallSite;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/AbstractRelinkableCallSite.class */
public abstract class AbstractRelinkableCallSite extends MutableCallSite implements RelinkableCallSite {
    private final CallSiteDescriptor descriptor;

    protected AbstractRelinkableCallSite(CallSiteDescriptor callSiteDescriptor) {
        super(callSiteDescriptor.getMethodType());
        this.descriptor = callSiteDescriptor;
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public CallSiteDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void initialize(MethodHandle methodHandle) {
        setTarget(methodHandle);
    }
}

package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.AbstractRelinkableCallSite;

/* loaded from: L-out.jar:jdk/internal/dynalink/MonomorphicCallSite.class */
public class MonomorphicCallSite extends AbstractRelinkableCallSite {
    public MonomorphicCallSite(CallSiteDescriptor callSiteDescriptor) {
        super(callSiteDescriptor);
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void relink(GuardedInvocation guardedInvocation, MethodHandle methodHandle) {
        setTarget(guardedInvocation.compose(methodHandle));
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle methodHandle) {
        relink(guardedInvocation, methodHandle);
    }
}

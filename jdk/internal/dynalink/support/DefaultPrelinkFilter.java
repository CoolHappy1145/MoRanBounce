package jdk.internal.dynalink.support;

import jdk.internal.dynalink.GuardedInvocationFilter;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/DefaultPrelinkFilter.class */
public class DefaultPrelinkFilter implements GuardedInvocationFilter {
    @Override // jdk.internal.dynalink.GuardedInvocationFilter
    public GuardedInvocation filter(GuardedInvocation guardedInvocation, LinkRequest linkRequest, LinkerServices linkerServices) {
        return guardedInvocation.asType(linkerServices, linkRequest.getCallSiteDescriptor().getMethodType());
    }
}

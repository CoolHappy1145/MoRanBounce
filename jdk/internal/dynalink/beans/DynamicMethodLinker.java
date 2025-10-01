package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/DynamicMethodLinker.class */
class DynamicMethodLinker implements TypeBasedGuardingDynamicLinker {
    DynamicMethodLinker() {
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return DynamicMethod.class.isAssignableFrom(cls);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        MethodHandle invocation;
        MethodHandle methodHandleInsertArguments;
        Object receiver = linkRequest.getReceiver();
        if (!(receiver instanceof DynamicMethod)) {
            return null;
        }
        CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if (callSiteDescriptor.getNameTokenCount() != 2 && callSiteDescriptor.getNameToken(0) != "dyn") {
            return null;
        }
        String nameToken = callSiteDescriptor.getNameToken(1);
        DynamicMethod dynamicMethod = (DynamicMethod) receiver;
        if (nameToken == "call" && 0 == 0) {
            methodHandleInsertArguments = dynamicMethod.getInvocation(CallSiteDescriptorFactory.dropParameterTypes(callSiteDescriptor, 0, 1), linkerServices);
        } else {
            if (nameToken != "new" || 0 == 0 || (invocation = dynamicMethod.getInvocation(callSiteDescriptor, linkerServices)) == null) {
                return null;
            }
            methodHandleInsertArguments = MethodHandles.insertArguments(invocation, 0, null);
        }
        if (methodHandleInsertArguments != null) {
            return new GuardedInvocation(MethodHandles.dropArguments(methodHandleInsertArguments, 0, (Class<?>[]) new Class[]{callSiteDescriptor.getMethodType().parameterType(0)}), Guards.getIdentityGuard(receiver));
        }
        return null;
    }
}

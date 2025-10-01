package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/BoundCallableLinker.class */
final class BoundCallableLinker implements TypeBasedGuardingDynamicLinker {
    BoundCallableLinker() {
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        boolean z;
        int i;
        MethodHandle methodHandleDropArguments;
        Object receiver = linkRequest.getReceiver();
        if (!(receiver instanceof BoundCallable)) {
            return null;
        }
        CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if (callSiteDescriptor.getNameTokenCount() < 2 || !"dyn".equals(callSiteDescriptor.getNameToken(0))) {
            return null;
        }
        String nameToken = callSiteDescriptor.getNameToken(1);
        if ("new".equals(nameToken)) {
            z = false;
        } else if ("call".equals(nameToken)) {
            z = true;
        } else {
            return null;
        }
        BoundCallable boundCallable = (BoundCallable) receiver;
        Object callable = boundCallable.getCallable();
        Object boundThis = boundCallable.getBoundThis();
        Object[] arguments = linkRequest.getArguments();
        Object[] boundArgs = boundCallable.getBoundArgs();
        int length = arguments.length;
        int length2 = boundArgs.length;
        Object[] objArr = new Object[length + length2];
        objArr[0] = callable;
        if (z) {
            objArr[1] = boundThis;
            i = 2;
        } else {
            i = 1;
        }
        System.arraycopy(boundArgs, 0, objArr, i, length2);
        System.arraycopy(arguments, i, objArr, i + length2, length - i);
        MethodType methodType = callSiteDescriptor.getMethodType();
        MethodType methodTypeChangeParameterType = callSiteDescriptor.getMethodType().changeParameterType(0, callable.getClass());
        if (z) {
            methodTypeChangeParameterType = methodTypeChangeParameterType.changeParameterType(1, boundThis == null ? Object.class : boundThis.getClass());
        }
        int length3 = boundArgs.length;
        while (true) {
            int i2 = length3;
            length3--;
            if (i2 <= 0) {
                break;
            }
            MethodType methodType2 = methodTypeChangeParameterType;
            int i3 = i;
            Class<?>[] clsArr = new Class[1];
            clsArr[0] = boundArgs[length3] == null ? Object.class : boundArgs[length3].getClass();
            methodTypeChangeParameterType = methodType2.insertParameterTypes(i3, clsArr);
        }
        GuardedInvocation guardedInvocation = linkerServices.getGuardedInvocation(linkRequest.replaceArguments(callSiteDescriptor.changeMethodType(methodTypeChangeParameterType), objArr));
        if (guardedInvocation == null) {
            return null;
        }
        MethodHandle methodHandleInsertArguments = MethodHandles.insertArguments(guardedInvocation.getInvocation(), 0, Arrays.copyOf(objArr, i + boundArgs.length));
        Class<?> clsParameterType = methodType.parameterType(0);
        if (z) {
            methodHandleDropArguments = MethodHandles.dropArguments(methodHandleInsertArguments, 0, (Class<?>[]) new Class[]{clsParameterType, methodType.parameterType(1)});
        } else {
            methodHandleDropArguments = MethodHandles.dropArguments(methodHandleInsertArguments, 0, (Class<?>[]) new Class[]{clsParameterType});
        }
        MethodHandle identityGuard = Guards.getIdentityGuard(boundCallable);
        return guardedInvocation.replaceMethods(methodHandleDropArguments, identityGuard.asType(identityGuard.type().changeParameterType(0, clsParameterType)));
    }
}

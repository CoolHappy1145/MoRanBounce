package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaSuperAdapterLinker.class */
final class JavaSuperAdapterLinker implements TypeBasedGuardingDynamicLinker {
    private static final String GET_METHOD = "getMethod";
    private static final String DYN_GET_METHOD = "dyn:getMethod";
    private static final String DYN_GET_METHOD_FIXED = "dyn:getMethod:super$";
    private static final MethodHandle ADD_PREFIX_TO_METHOD_NAME;
    private static final MethodHandle BIND_DYNAMIC_METHOD;
    private static final MethodHandle GET_ADAPTER;
    private static final MethodHandle IS_ADAPTER_OF_CLASS;

    JavaSuperAdapterLinker() {
    }

    static {
        Lookup lookup = new Lookup(MethodHandles.lookup());
        ADD_PREFIX_TO_METHOD_NAME = lookup.findOwnStatic("addPrefixToMethodName", Object.class, new Class[]{Object.class});
        BIND_DYNAMIC_METHOD = lookup.findOwnStatic("bindDynamicMethod", Object.class, new Class[]{Object.class, Object.class});
        GET_ADAPTER = lookup.findVirtual(JavaSuperAdapter.class, "getAdapter", MethodType.methodType(Object.class));
        IS_ADAPTER_OF_CLASS = lookup.findOwnStatic("isAdapterOfClass", Boolean.TYPE, new Class[]{Class.class, Object.class});
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        MethodHandle methodHandleFilterArguments;
        Object receiver = linkRequest.getReceiver();
        if (!(receiver instanceof JavaSuperAdapter)) {
            return null;
        }
        CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if (!CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).contains(GET_METHOD)) {
            return null;
        }
        Object adapter = ((JavaSuperAdapter) receiver).getAdapter();
        Object[] arguments = linkRequest.getArguments();
        arguments[0] = adapter;
        MethodType methodType = callSiteDescriptor.getMethodType();
        Class<?> cls = adapter.getClass();
        boolean z = callSiteDescriptor.getNameTokenCount() > 2;
        GuardedInvocation guardedInvocation = NashornBeansLinker.getGuardedInvocation(BeansLinker.getLinkerForClass(cls), linkRequest.replaceArguments(NashornCallSiteDescriptor.get(callSiteDescriptor.getLookup(), z ? DYN_GET_METHOD_FIXED + callSiteDescriptor.getNameToken(2) : DYN_GET_METHOD, methodType.changeParameterType(0, cls), 0), arguments), linkerServices);
        MethodHandle methodHandleBindTo = IS_ADAPTER_OF_CLASS.bindTo(cls);
        if (guardedInvocation == null) {
            return new GuardedInvocation(MethodHandles.dropArguments(jdk.nashorn.internal.lookup.Lookup.EMPTY_GETTER, 1, methodType.parameterList().subList(1, methodType.parameterCount())), methodHandleBindTo).asType(callSiteDescriptor);
        }
        MethodHandle invocation = guardedInvocation.getInvocation();
        MethodType methodTypeType = invocation.type();
        MethodHandle methodHandleFoldArguments = MethodHandles.foldArguments(MethodHandles.dropArguments(BIND_DYNAMIC_METHOD.asType(MethodType.methodType(Object.class, methodTypeType.returnType(), methodTypeType.parameterType(0))), 2, methodTypeType.parameterList().subList(1, methodTypeType.parameterCount())), invocation);
        MethodHandle methodHandleAsFilterType = asFilterType(GET_ADAPTER, 0, methodTypeType, methodType);
        if (z) {
            methodHandleFilterArguments = MethodHandles.filterArguments(methodHandleFoldArguments, 0, methodHandleAsFilterType);
        } else {
            methodHandleFilterArguments = MethodHandles.filterArguments(methodHandleFoldArguments, 0, methodHandleAsFilterType, asFilterType(ADD_PREFIX_TO_METHOD_NAME, 1, methodTypeType, methodType));
        }
        return guardedInvocation.replaceMethods(methodHandleFilterArguments, methodHandleBindTo).asType(callSiteDescriptor);
    }

    private static MethodHandle asFilterType(MethodHandle methodHandle, int i, MethodType methodType, MethodType methodType2) {
        return methodHandle.asType(MethodType.methodType(methodType.parameterType(i), methodType2.parameterType(i)));
    }

    private static Object addPrefixToMethodName(Object obj) {
        return Constants.IMAGINARY_SUPER.concat(String.valueOf(obj));
    }

    private static Object bindDynamicMethod(Object obj, Object obj2) {
        return obj == null ? ScriptRuntime.UNDEFINED : Bootstrap.bindCallable(obj, obj2, null);
    }

    private static boolean isAdapterOfClass(Class cls, Object obj) {
        return (obj instanceof JavaSuperAdapter) && cls == ((JavaSuperAdapter) obj).getAdapter().getClass();
    }
}

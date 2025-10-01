package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.DynamicMethod;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornBottomLinker.class */
final class NashornBottomLinker implements GuardingDynamicLinker, GuardingTypeConverterFactory {
    private static final MethodHandle EMPTY_PROP_GETTER;
    private static final MethodHandle EMPTY_ELEM_GETTER;
    private static final MethodHandle EMPTY_PROP_SETTER;
    private static final MethodHandle EMPTY_ELEM_SETTER;
    private static final Map CONVERTERS;
    static final boolean $assertionsDisabled;

    NashornBottomLinker() {
    }

    static {
        $assertionsDisabled = !NashornBottomLinker.class.desiredAssertionStatus();
        EMPTY_PROP_GETTER = Lookup.f31MH.dropArguments(Lookup.f31MH.constant(Object.class, ScriptRuntime.UNDEFINED), 0, new Class[]{Object.class});
        EMPTY_ELEM_GETTER = Lookup.f31MH.dropArguments(EMPTY_PROP_GETTER, 0, new Class[]{Object.class});
        EMPTY_PROP_SETTER = Lookup.f31MH.asType(EMPTY_ELEM_GETTER, EMPTY_ELEM_GETTER.type().changeReturnType(Void.TYPE));
        EMPTY_ELEM_SETTER = Lookup.f31MH.dropArguments(EMPTY_PROP_SETTER, 0, new Class[]{Object.class});
        CONVERTERS = new HashMap();
        CONVERTERS.put(Boolean.TYPE, JSType.TO_BOOLEAN.methodHandle());
        CONVERTERS.put(Double.TYPE, JSType.TO_NUMBER.methodHandle());
        CONVERTERS.put(Integer.TYPE, JSType.TO_INTEGER.methodHandle());
        CONVERTERS.put(Long.TYPE, JSType.TO_LONG.methodHandle());
        CONVERTERS.put(String.class, JSType.TO_STRING.methodHandle());
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        Object receiver = linkRequest.getReceiver();
        if (receiver == null) {
            return linkNull(linkRequest);
        }
        if ($assertionsDisabled || isExpectedObject(receiver)) {
            return linkBean(linkRequest, linkerServices);
        }
        throw new AssertionError("Couldn't link " + linkRequest.getCallSiteDescriptor() + " for " + receiver.getClass().getName());
    }

    private static GuardedInvocation linkBean(LinkRequest linkRequest, LinkerServices linkerServices) {
        NashornCallSiteDescriptor nashornCallSiteDescriptor;
        Object receiver;
        nashornCallSiteDescriptor = (NashornCallSiteDescriptor) linkRequest.getCallSiteDescriptor();
        receiver = linkRequest.getReceiver();
        switch (nashornCallSiteDescriptor.getFirstOperator()) {
            case "new":
                if (BeansLinker.isDynamicConstructor(receiver)) {
                    throw ECMAErrors.typeError("no.constructor.matches.args", new String[]{ScriptRuntime.safeToString(receiver)});
                }
                if (receiver instanceof DynamicMethod) {
                    throw ECMAErrors.typeError("method.not.constructor", new String[]{ScriptRuntime.safeToString(receiver)});
                }
                throw ECMAErrors.typeError("not.a.function", new String[]{nashornCallSiteDescriptor.getFunctionErrorMessage(receiver)});
            case "call":
                if (BeansLinker.isDynamicConstructor(receiver)) {
                    throw ECMAErrors.typeError("constructor.requires.new", new String[]{ScriptRuntime.safeToString(receiver)});
                }
                if (receiver instanceof DynamicMethod) {
                    throw ECMAErrors.typeError("no.method.matches.args", new String[]{ScriptRuntime.safeToString(receiver)});
                }
                throw ECMAErrors.typeError("not.a.function", new String[]{nashornCallSiteDescriptor.getFunctionErrorMessage(receiver)});
            case "callMethod":
                throw ECMAErrors.typeError("no.such.function", new String[]{getArgument(linkRequest), ScriptRuntime.safeToString(receiver)});
            case "getMethod":
                return getInvocation(Lookup.f31MH.dropArguments((MethodHandle) JSType.GET_UNDEFINED.get(2), 0, new Class[]{Object.class}), receiver, linkerServices, nashornCallSiteDescriptor);
            case "getProp":
            case "getElem":
                if (NashornCallSiteDescriptor.isOptimistic(nashornCallSiteDescriptor)) {
                    throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, NashornCallSiteDescriptor.getProgramPoint(nashornCallSiteDescriptor), Type.OBJECT);
                }
                if (nashornCallSiteDescriptor.getOperand() != null) {
                    return getInvocation(EMPTY_PROP_GETTER, receiver, linkerServices, nashornCallSiteDescriptor);
                }
                return getInvocation(EMPTY_ELEM_GETTER, receiver, linkerServices, nashornCallSiteDescriptor);
            case "setProp":
            case "setElem":
                if (NashornCallSiteDescriptor.isStrict(nashornCallSiteDescriptor)) {
                    throw ECMAErrors.typeError("cant.set.property", new String[]{getArgument(linkRequest), ScriptRuntime.safeToString(receiver)});
                }
                if (nashornCallSiteDescriptor.getOperand() != null) {
                    return getInvocation(EMPTY_PROP_SETTER, receiver, linkerServices, nashornCallSiteDescriptor);
                }
                return getInvocation(EMPTY_ELEM_SETTER, receiver, linkerServices, nashornCallSiteDescriptor);
            default:
                throw new AssertionError("unknown call type " + nashornCallSiteDescriptor);
        }
    }

    @Override // jdk.internal.dynalink.linker.GuardingTypeConverterFactory
    public GuardedTypeConversion convertToType(Class cls, Class cls2) {
        GuardedInvocation guardedInvocationConvertToTypeNoCast = convertToTypeNoCast(cls, cls2);
        if (guardedInvocationConvertToTypeNoCast == null) {
            return null;
        }
        return new GuardedTypeConversion(guardedInvocationConvertToTypeNoCast.asType(Lookup.f31MH.type(cls2, new Class[]{cls})), true);
    }

    private static GuardedInvocation convertToTypeNoCast(Class cls, Class cls2) {
        MethodHandle methodHandle = (MethodHandle) CONVERTERS.get(cls2);
        if (methodHandle != null) {
            return new GuardedInvocation(methodHandle);
        }
        return null;
    }

    private static GuardedInvocation getInvocation(MethodHandle methodHandle, Object obj, LinkerServices linkerServices, CallSiteDescriptor callSiteDescriptor) {
        return Bootstrap.asTypeSafeReturn(new GuardedInvocation(methodHandle, Guards.getClassGuard(obj.getClass())), linkerServices, callSiteDescriptor);
    }

    private static boolean isExpectedObject(Object obj) {
        return !NashornLinker.canLinkTypeStatic(obj.getClass());
    }

    private static GuardedInvocation linkNull(LinkRequest linkRequest) {
        NashornCallSiteDescriptor nashornCallSiteDescriptor;
        nashornCallSiteDescriptor = (NashornCallSiteDescriptor) linkRequest.getCallSiteDescriptor();
        switch (nashornCallSiteDescriptor.getFirstOperator()) {
            case "new":
            case "call":
                throw ECMAErrors.typeError("not.a.function", new String[]{Configurator.NULL});
            case "callMethod":
            case "getMethod":
                throw ECMAErrors.typeError("no.such.function", new String[]{getArgument(linkRequest), Configurator.NULL});
            case "getProp":
            case "getElem":
                throw ECMAErrors.typeError("cant.get.property", new String[]{getArgument(linkRequest), Configurator.NULL});
            case "setProp":
            case "setElem":
                throw ECMAErrors.typeError("cant.set.property", new String[]{getArgument(linkRequest), Configurator.NULL});
            default:
                throw new AssertionError("unknown call type " + nashornCallSiteDescriptor);
        }
    }

    private static String getArgument(LinkRequest linkRequest) {
        CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if (callSiteDescriptor.getNameTokenCount() > 2) {
            return callSiteDescriptor.getNameToken(2);
        }
        return ScriptRuntime.safeToString(linkRequest.getArguments()[1]);
    }
}

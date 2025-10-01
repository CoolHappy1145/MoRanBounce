package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.GlobalConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UserAccessorProperty;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/PrimitiveLookup.class */
public final class PrimitiveLookup {
    private static final MethodHandle PRIMITIVE_SETTER;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !PrimitiveLookup.class.desiredAssertionStatus();
        PRIMITIVE_SETTER = findOwnMH("primitiveSetter", Lookup.f31MH.type(Void.TYPE, new Class[]{ScriptObject.class, Object.class, Object.class, Boolean.TYPE, Object.class}));
    }

    private PrimitiveLookup() {
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest linkRequest, Class cls, ScriptObject scriptObject, MethodHandle methodHandle, MethodHandle methodHandle2) {
        return lookupPrimitive(linkRequest, Guards.getInstanceOfGuard(cls), scriptObject, methodHandle, methodHandle2);
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest linkRequest, MethodHandle methodHandle, ScriptObject scriptObject, MethodHandle methodHandle2, MethodHandle methodHandle3) {
        CallSiteDescriptor callSiteDescriptor;
        String nameToken;
        FindProperty findProperty;
        GuardedInvocation guardedInvocationLookup;
        callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if (callSiteDescriptor.getNameTokenCount() > 2) {
            nameToken = callSiteDescriptor.getNameToken(2);
            findProperty = scriptObject.findProperty(nameToken, true);
        } else {
            nameToken = null;
            findProperty = null;
        }
        switch ((String) CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).get(0)) {
            case "getProp":
            case "getElem":
            case "getMethod":
                if (nameToken != null) {
                    if (findProperty == null) {
                        return null;
                    }
                    SwitchPoint builtinSwitchPoint = findProperty.getProperty().getBuiltinSwitchPoint();
                    if ((builtinSwitchPoint instanceof Context.BuiltinSwitchPoint) && !builtinSwitchPoint.hasBeenInvalidated()) {
                        return new GuardedInvocation(GlobalConstants.staticConstantGetter(findProperty.getObjectValue()), methodHandle, builtinSwitchPoint, (Class) null);
                    }
                    if (findProperty.isInherited() && !(findProperty.getProperty() instanceof UserAccessorProperty) && (guardedInvocationLookup = scriptObject.getProto().lookup(callSiteDescriptor, linkRequest)) != null) {
                        MethodHandle invocation = guardedInvocationLookup.getInvocation();
                        return new GuardedInvocation(Lookup.f31MH.filterArguments(Lookup.f31MH.asType(invocation, invocation.type().changeParameterType(0, Object.class)), 0, new MethodHandle[]{methodHandle3}), NashornGuards.combineGuards(methodHandle, Lookup.f31MH.filterArguments(guardedInvocationLookup.getGuard(), 0, new MethodHandle[]{methodHandle3})));
                    }
                }
                break;
            case "setProp":
            case "setElem":
                return getPrimitiveSetter(nameToken, methodHandle, methodHandle2, NashornCallSiteDescriptor.isStrict(callSiteDescriptor));
        }
        GuardedInvocation guardedInvocationLookup2 = scriptObject.lookup(callSiteDescriptor, linkRequest);
        if (guardedInvocationLookup2 != null) {
            MethodHandle invocation2 = guardedInvocationLookup2.getInvocation();
            Class<?> clsParameterType = invocation2.type().parameterType(0);
            if (clsParameterType != Object.class) {
                MethodType methodTypeType = methodHandle2.type();
                if (!$assertionsDisabled && !clsParameterType.isAssignableFrom(methodTypeType.returnType())) {
                    throw new AssertionError();
                }
                invocation2 = Lookup.f31MH.filterArguments(invocation2, 0, new MethodHandle[]{Lookup.f31MH.asType(methodHandle2, methodTypeType.changeReturnType(clsParameterType))});
            }
            return new GuardedInvocation(invocation2, methodHandle, guardedInvocationLookup2.getSwitchPoints(), (Class) null);
        }
        return null;
    }

    private static GuardedInvocation getPrimitiveSetter(String str, MethodHandle methodHandle, MethodHandle methodHandle2, boolean z) {
        MethodHandle methodHandleDropArguments;
        MethodHandle methodHandleInsertArguments;
        MethodHandle methodHandleAsType = Lookup.f31MH.asType(methodHandle2, methodHandle2.type().changeReturnType(ScriptObject.class));
        if (str == null) {
            methodHandleDropArguments = Lookup.f31MH.dropArguments(methodHandleAsType, 1, new Class[]{Object.class, Object.class});
            methodHandleInsertArguments = Lookup.f31MH.insertArguments(PRIMITIVE_SETTER, 3, new Object[]{Boolean.valueOf(z)});
        } else {
            methodHandleDropArguments = Lookup.f31MH.dropArguments(methodHandleAsType, 1, new Class[]{Object.class});
            methodHandleInsertArguments = Lookup.f31MH.insertArguments(PRIMITIVE_SETTER, 2, new Object[]{str, Boolean.valueOf(z)});
        }
        return new GuardedInvocation(Lookup.f31MH.foldArguments(methodHandleInsertArguments, methodHandleDropArguments), methodHandle);
    }

    private static void primitiveSetter(ScriptObject scriptObject, Object obj, Object obj2, boolean z, Object obj3) {
        String string = JSType.toString(obj2);
        FindProperty findProperty = scriptObject.findProperty(string, true);
        if (findProperty == null || !(findProperty.getProperty() instanceof UserAccessorProperty) || !findProperty.getProperty().isWritable()) {
            if (z) {
                throw ECMAErrors.typeError("property.not.writable", new String[]{string, ScriptRuntime.safeToString(obj)});
            }
        } else {
            findProperty.setValue(obj3, z);
        }
    }

    private static MethodHandle findOwnMH(String str, MethodType methodType) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), PrimitiveLookup.class, str, methodType);
    }
}

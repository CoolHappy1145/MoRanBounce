package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Undefined.class */
public final class Undefined extends DefaultPropertyAccess {
    private static final Undefined UNDEFINED = new Undefined();
    private static final Undefined EMPTY = new Undefined();
    private static final MethodHandle UNDEFINED_GUARD = Guards.getIdentityGuard(UNDEFINED);
    private static final MethodHandle GET_METHOD = findOwnMH(PropertyDescriptor.GET, Object.class, new Class[]{Object.class});
    private static final MethodHandle SET_METHOD = Lookup.f31MH.insertArguments(findOwnMH(PropertyDescriptor.SET, Void.TYPE, new Class[]{Object.class, Object.class, Integer.TYPE}), 3, new Object[]{2});

    private Undefined() {
    }

    public static Undefined getUndefined() {
        return UNDEFINED;
    }

    public static Undefined getEmpty() {
        return EMPTY;
    }

    public static GuardedInvocation lookup(CallSiteDescriptor callSiteDescriptor) {
        switch ((String) CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).get(0)) {
            case "new":
            case "call":
                String functionDescription = NashornCallSiteDescriptor.getFunctionDescription(callSiteDescriptor);
                throw ECMAErrors.typeError(functionDescription != null ? "not.a.function" : "cant.call.undefined", new String[]{functionDescription});
            case "callMethod":
                throw lookupTypeError("cant.read.property.of.undefined", callSiteDescriptor);
            case "getProp":
            case "getElem":
            case "getMethod":
                if (callSiteDescriptor.getNameTokenCount() < 3) {
                    return findGetIndexMethod(callSiteDescriptor);
                }
                return findGetMethod(callSiteDescriptor);
            case "setProp":
            case "setElem":
                if (callSiteDescriptor.getNameTokenCount() < 3) {
                    return findSetIndexMethod(callSiteDescriptor);
                }
                return findSetMethod(callSiteDescriptor);
            default:
                return null;
        }
    }

    private static ECMAException lookupTypeError(String str, CallSiteDescriptor callSiteDescriptor) {
        String nameToken = callSiteDescriptor.getNameToken(2);
        String[] strArr = new String[1];
        strArr[0] = (nameToken == null || nameToken.isEmpty()) ? null : nameToken;
        return ECMAErrors.typeError(str, strArr);
    }

    private static GuardedInvocation findGetMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(Lookup.f31MH.insertArguments(GET_METHOD, 1, new Object[]{callSiteDescriptor.getNameToken(2)}), UNDEFINED_GUARD).asType(callSiteDescriptor);
    }

    private static GuardedInvocation findGetIndexMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(GET_METHOD, UNDEFINED_GUARD).asType(callSiteDescriptor);
    }

    private static GuardedInvocation findSetMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(Lookup.f31MH.insertArguments(SET_METHOD, 1, new Object[]{callSiteDescriptor.getNameToken(2)}), UNDEFINED_GUARD).asType(callSiteDescriptor);
    }

    private static GuardedInvocation findSetIndexMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(SET_METHOD, UNDEFINED_GUARD).asType(callSiteDescriptor);
    }

    @Override // jdk.nashorn.internal.runtime.DefaultPropertyAccess, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(Object obj) {
        throw ECMAErrors.typeError("cant.read.property.of.undefined", new String[]{ScriptRuntime.safeToString(obj)});
    }

    @Override // jdk.nashorn.internal.runtime.DefaultPropertyAccess, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object obj, Object obj2, int i) {
        throw ECMAErrors.typeError("cant.set.property.of.undefined", new String[]{ScriptRuntime.safeToString(obj)});
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object obj, boolean z) {
        throw ECMAErrors.typeError("cant.delete.property.of.undefined", new String[]{ScriptRuntime.safeToString(obj)});
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findVirtual(MethodHandles.lookup(), Undefined.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}

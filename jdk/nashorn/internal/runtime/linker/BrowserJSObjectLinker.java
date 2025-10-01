package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/BrowserJSObjectLinker.class */
final class BrowserJSObjectLinker implements TypeBasedGuardingDynamicLinker {
    private static final ClassLoader myLoader;
    private static final String JSOBJECT_CLASS = "netscape.javascript.JSObject";
    private static Class jsObjectClass;
    private final NashornBeansLinker nashornBeansLinker;

    /* renamed from: MH */
    private static final MethodHandleFunctionality f58MH;
    private static final MethodHandle IS_JSOBJECT_GUARD;
    private static final MethodHandle JSOBJECTLINKER_GET;
    private static final MethodHandle JSOBJECTLINKER_PUT;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !BrowserJSObjectLinker.class.desiredAssertionStatus();
        myLoader = BrowserJSObjectLinker.class.getClassLoader();
        f58MH = MethodHandleFactory.getFunctionality();
        IS_JSOBJECT_GUARD = findOwnMH_S("isJSObject", Boolean.TYPE, new Class[]{Object.class});
        JSOBJECTLINKER_GET = findOwnMH_S(PropertyDescriptor.GET, Object.class, new Class[]{MethodHandle.class, Object.class, Object.class});
        JSOBJECTLINKER_PUT = findOwnMH_S("put", Void.TYPE, new Class[]{Object.class, Object.class, Object.class});
    }

    BrowserJSObjectLinker(NashornBeansLinker nashornBeansLinker) {
        this.nashornBeansLinker = nashornBeansLinker;
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return canLinkTypeStatic(cls);
    }

    static boolean canLinkTypeStatic(Class cls) {
        if (jsObjectClass != null && jsObjectClass.isAssignableFrom(cls)) {
            return true;
        }
        Class superclass = cls;
        while (true) {
            Class cls2 = superclass;
            if (cls2 != null) {
                if (cls2.getClassLoader() == myLoader && cls2.getName().equals(JSOBJECT_CLASS)) {
                    jsObjectClass = cls2;
                    return true;
                }
                superclass = cls2.getSuperclass();
            } else {
                return false;
            }
        }
    }

    private static void checkJSObjectClass() {
        if (!$assertionsDisabled && jsObjectClass == null) {
            throw new AssertionError("netscape.javascript.JSObject not found!");
        }
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        LinkRequest linkRequestWithoutRuntimeContext = linkRequest.withoutRuntimeContext();
        Object receiver = linkRequestWithoutRuntimeContext.getReceiver();
        CallSiteDescriptor callSiteDescriptor = linkRequestWithoutRuntimeContext.getCallSiteDescriptor();
        checkJSObjectClass();
        if (callSiteDescriptor.getNameTokenCount() < 2 || !"dyn".equals(callSiteDescriptor.getNameToken(0))) {
            return null;
        }
        if (jsObjectClass.isInstance(receiver)) {
            GuardedInvocation guardedInvocationLookup = lookup(callSiteDescriptor, linkRequest, linkerServices);
            return Bootstrap.asTypeSafeReturn(guardedInvocationLookup.replaceMethods(linkerServices.filterInternalObjects(guardedInvocationLookup.getInvocation()), guardedInvocationLookup.getGuard()), linkerServices, callSiteDescriptor);
        }
        throw new AssertionError();
    }

    private GuardedInvocation lookup(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest, LinkerServices linkerServices) {
        int nameTokenCount;
        GuardedInvocation guardedInvocation;
        String str = (String) CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).get(0);
        nameTokenCount = callSiteDescriptor.getNameTokenCount();
        try {
            guardedInvocation = this.nashornBeansLinker.getGuardedInvocation(linkRequest, linkerServices);
        } catch (Throwable unused) {
            guardedInvocation = null;
        }
        switch (str) {
            case "getProp":
            case "getElem":
            case "getMethod":
                return nameTokenCount > 2 ? findGetMethod(callSiteDescriptor, guardedInvocation) : findGetIndexMethod(guardedInvocation);
            case "setProp":
            case "setElem":
                return nameTokenCount > 2 ? findSetMethod(callSiteDescriptor, guardedInvocation) : findSetIndexMethod();
            case "call":
                return findCallMethod(callSiteDescriptor);
            default:
                return null;
        }
    }

    private static GuardedInvocation findGetMethod(CallSiteDescriptor callSiteDescriptor, GuardedInvocation guardedInvocation) {
        if (guardedInvocation != null) {
            return guardedInvocation;
        }
        return new GuardedInvocation(f58MH.insertArguments(JSObjectHandles.JSOBJECT_GETMEMBER, 1, new Object[]{callSiteDescriptor.getNameToken(2)}), IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findGetIndexMethod(GuardedInvocation guardedInvocation) {
        return guardedInvocation.replaceMethods(f58MH.insertArguments(JSOBJECTLINKER_GET, 0, new Object[]{guardedInvocation.getInvocation()}), guardedInvocation.getGuard());
    }

    private static GuardedInvocation findSetMethod(CallSiteDescriptor callSiteDescriptor, GuardedInvocation guardedInvocation) {
        if (guardedInvocation != null) {
            return guardedInvocation;
        }
        return new GuardedInvocation(f58MH.insertArguments(JSObjectHandles.JSOBJECT_SETMEMBER, 1, new Object[]{callSiteDescriptor.getNameToken(2)}), IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findSetIndexMethod() {
        return new GuardedInvocation(JSOBJECTLINKER_PUT, IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findCallMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(f58MH.asCollector(f58MH.insertArguments(JSObjectHandles.JSOBJECT_CALL, 1, new Object[]{"call"}), Object[].class, callSiteDescriptor.getMethodType().parameterCount() - 1), IS_JSOBJECT_GUARD);
    }

    private static boolean isJSObject(Object obj) {
        return jsObjectClass.isInstance(obj);
    }

    private static Object get(MethodHandle methodHandle, Object obj, Object obj2) {
        if (obj2 instanceof Integer) {
            return (Object) JSObjectHandles.JSOBJECT_GETSLOT.invokeExact(obj, ((Integer) obj2).intValue());
        }
        if (obj2 instanceof Number) {
            int index = getIndex((Number) obj2);
            if (index > -1) {
                return (Object) JSObjectHandles.JSOBJECT_GETSLOT.invokeExact(obj, index);
            }
            return null;
        }
        if ((obj2 instanceof String) || (obj2 instanceof ConsString)) {
            String string = obj2.toString();
            if (string.indexOf(40) != -1) {
                return (Object) methodHandle.invokeExact(obj, string);
            }
            return (Object) JSObjectHandles.JSOBJECT_GETMEMBER.invokeExact(obj, string);
        }
        return null;
    }

    private static void put(Object obj, Object obj2, Object obj3) {
        if (obj2 instanceof Integer) {
            (void) JSObjectHandles.JSOBJECT_SETSLOT.invokeExact(obj, ((Integer) obj2).intValue(), obj3);
        } else {
            if (obj2 instanceof Number) {
                (void) JSObjectHandles.JSOBJECT_SETSLOT.invokeExact(obj, getIndex((Number) obj2), obj3);
                return;
            }
            if ((obj2 instanceof String) || (obj2 instanceof ConsString)) {
                (void) JSObjectHandles.JSOBJECT_SETMEMBER.invokeExact(obj, obj2.toString(), obj3);
            }
        }
    }

    private static int getIndex(Number number) {
        double dDoubleValue = number.doubleValue();
        if (((double) ((int) dDoubleValue)) == dDoubleValue) {
            return (int) dDoubleValue;
        }
        return -1;
    }

    private static MethodHandle findOwnMH_S(String str, Class cls, Class[] clsArr) {
        return f58MH.findStatic(MethodHandles.lookup(), BrowserJSObjectLinker.class, str, f58MH.type(cls, clsArr));
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/BrowserJSObjectLinker$JSObjectHandles.class */
    static class JSObjectHandles {
        static final MethodHandle JSOBJECT_GETMEMBER = findJSObjectMH_V("getMember", Object.class, new Class[]{String.class}).asType(BrowserJSObjectLinker.f58MH.type(Object.class, new Class[]{Object.class, String.class}));
        static final MethodHandle JSOBJECT_GETSLOT = findJSObjectMH_V("getSlot", Object.class, new Class[]{Integer.TYPE}).asType(BrowserJSObjectLinker.f58MH.type(Object.class, new Class[]{Object.class, Integer.TYPE}));
        static final MethodHandle JSOBJECT_SETMEMBER = findJSObjectMH_V("setMember", Void.TYPE, new Class[]{String.class, Object.class}).asType(BrowserJSObjectLinker.f58MH.type(Void.TYPE, new Class[]{Object.class, String.class, Object.class}));
        static final MethodHandle JSOBJECT_SETSLOT = findJSObjectMH_V("setSlot", Void.TYPE, new Class[]{Integer.TYPE, Object.class}).asType(BrowserJSObjectLinker.f58MH.type(Void.TYPE, new Class[]{Object.class, Integer.TYPE, Object.class}));
        static final MethodHandle JSOBJECT_CALL = findJSObjectMH_V("call", Object.class, new Class[]{String.class, Object[].class}).asType(BrowserJSObjectLinker.f58MH.type(Object.class, new Class[]{Object.class, String.class, Object[].class}));

        JSObjectHandles() {
        }

        private static MethodHandle findJSObjectMH_V(String str, Class cls, Class[] clsArr) {
            BrowserJSObjectLinker.checkJSObjectClass();
            return BrowserJSObjectLinker.f58MH.findVirtual(MethodHandles.publicLookup(), BrowserJSObjectLinker.jsObjectClass, str, BrowserJSObjectLinker.f58MH.type(cls, clsArr));
        }
    }
}

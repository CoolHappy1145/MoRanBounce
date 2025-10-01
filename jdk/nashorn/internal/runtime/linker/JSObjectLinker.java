package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import javax.script.Bindings;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JSObjectLinker.class */
final class JSObjectLinker implements TypeBasedGuardingDynamicLinker {
    private final NashornBeansLinker nashornBeansLinker;

    /* renamed from: MH */
    private static final MethodHandleFunctionality f59MH;
    private static final MethodHandle IS_JSOBJECT_GUARD;
    private static final MethodHandle JSOBJECTLINKER_GET;
    private static final MethodHandle JSOBJECTLINKER_PUT;
    private static final MethodHandle JSOBJECT_GETMEMBER;
    private static final MethodHandle JSOBJECT_SETMEMBER;
    private static final MethodHandle JSOBJECT_CALL;
    private static final MethodHandle JSOBJECT_SCOPE_CALL;
    private static final MethodHandle JSOBJECT_CALL_TO_APPLY;
    private static final MethodHandle JSOBJECT_NEW;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JSObjectLinker.class.desiredAssertionStatus();
        f59MH = MethodHandleFactory.getFunctionality();
        IS_JSOBJECT_GUARD = findOwnMH_S("isJSObject", Boolean.TYPE, new Class[]{Object.class});
        JSOBJECTLINKER_GET = findOwnMH_S(PropertyDescriptor.GET, Object.class, new Class[]{MethodHandle.class, Object.class, Object.class});
        JSOBJECTLINKER_PUT = findOwnMH_S("put", Void.TYPE, new Class[]{Object.class, Object.class, Object.class});
        JSOBJECT_GETMEMBER = findJSObjectMH_V("getMember", Object.class, new Class[]{String.class});
        JSOBJECT_SETMEMBER = findJSObjectMH_V("setMember", Void.TYPE, new Class[]{String.class, Object.class});
        JSOBJECT_CALL = findJSObjectMH_V("call", Object.class, new Class[]{Object.class, Object[].class});
        JSOBJECT_SCOPE_CALL = findOwnMH_S("jsObjectScopeCall", Object.class, new Class[]{JSObject.class, Object.class, Object[].class});
        JSOBJECT_CALL_TO_APPLY = findOwnMH_S("callToApply", Object.class, new Class[]{MethodHandle.class, JSObject.class, Object.class, Object[].class});
        JSOBJECT_NEW = findJSObjectMH_V("newObject", Object.class, new Class[]{Object[].class});
    }

    JSObjectLinker(NashornBeansLinker nashornBeansLinker) {
        this.nashornBeansLinker = nashornBeansLinker;
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return canLinkTypeStatic(cls);
    }

    static boolean canLinkTypeStatic(Class cls) {
        return Map.class.isAssignableFrom(cls) || Bindings.class.isAssignableFrom(cls) || JSObject.class.isAssignableFrom(cls);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        GuardedInvocation guardedInvocation;
        LinkRequest linkRequestWithoutRuntimeContext = linkRequest.withoutRuntimeContext();
        Object receiver = linkRequestWithoutRuntimeContext.getReceiver();
        CallSiteDescriptor callSiteDescriptor = linkRequestWithoutRuntimeContext.getCallSiteDescriptor();
        if (callSiteDescriptor.getNameTokenCount() < 2 || !"dyn".equals(callSiteDescriptor.getNameToken(0))) {
            return null;
        }
        if (receiver instanceof JSObject) {
            GuardedInvocation guardedInvocationLookup = lookup(callSiteDescriptor, linkRequest, linkerServices);
            guardedInvocation = guardedInvocationLookup.replaceMethods(linkerServices.filterInternalObjects(guardedInvocationLookup.getInvocation()), guardedInvocationLookup.getGuard());
        } else if ((receiver instanceof Map) || (receiver instanceof Bindings)) {
            GuardedInvocation guardedInvocation2 = this.nashornBeansLinker.getGuardedInvocation(linkRequest, linkerServices);
            guardedInvocation = new GuardedInvocation(guardedInvocation2.getInvocation(), NashornGuards.combineGuards(guardedInvocation2.getGuard(), NashornGuards.getNotJSObjectGuard()));
        } else {
            throw new AssertionError();
        }
        return Bootstrap.asTypeSafeReturn(guardedInvocation, linkerServices, callSiteDescriptor);
    }

    private GuardedInvocation lookup(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest, LinkerServices linkerServices) {
        int nameTokenCount;
        String str = (String) CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).get(0);
        nameTokenCount = callSiteDescriptor.getNameTokenCount();
        switch (str) {
            case "getProp":
            case "getElem":
            case "getMethod":
                if (nameTokenCount > 2) {
                    return findGetMethod(callSiteDescriptor);
                }
                return findGetIndexMethod(this.nashornBeansLinker.getGuardedInvocation(linkRequest, linkerServices));
            case "setProp":
            case "setElem":
                return nameTokenCount > 2 ? findSetMethod(callSiteDescriptor) : findSetIndexMethod();
            case "call":
                return findCallMethod(callSiteDescriptor);
            case "new":
                return findNewMethod(callSiteDescriptor);
            default:
                return null;
        }
    }

    private static GuardedInvocation findGetMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(f59MH.insertArguments(JSOBJECT_GETMEMBER, 1, new Object[]{callSiteDescriptor.getNameToken(2)}), IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findGetIndexMethod(GuardedInvocation guardedInvocation) {
        return guardedInvocation.replaceMethods(f59MH.insertArguments(JSOBJECTLINKER_GET, 0, new Object[]{guardedInvocation.getInvocation()}), guardedInvocation.getGuard());
    }

    private static GuardedInvocation findSetMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(f59MH.insertArguments(JSOBJECT_SETMEMBER, 1, new Object[]{callSiteDescriptor.getNameToken(2)}), IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findSetIndexMethod() {
        return new GuardedInvocation(JSOBJECTLINKER_PUT, IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findCallMethod(CallSiteDescriptor callSiteDescriptor) {
        MethodHandle methodHandleInsertArguments = NashornCallSiteDescriptor.isScope(callSiteDescriptor) ? JSOBJECT_SCOPE_CALL : JSOBJECT_CALL;
        if (NashornCallSiteDescriptor.isApplyToCall(callSiteDescriptor)) {
            methodHandleInsertArguments = f59MH.insertArguments(JSOBJECT_CALL_TO_APPLY, 0, new Object[]{methodHandleInsertArguments});
        }
        MethodType methodType = callSiteDescriptor.getMethodType();
        return new GuardedInvocation(methodType.parameterType(methodType.parameterCount() - 1) == Object[].class ? methodHandleInsertArguments : f59MH.asCollector(methodHandleInsertArguments, Object[].class, methodType.parameterCount() - 2), IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findNewMethod(CallSiteDescriptor callSiteDescriptor) {
        return new GuardedInvocation(f59MH.asCollector(JSOBJECT_NEW, Object[].class, callSiteDescriptor.getMethodType().parameterCount() - 1), IS_JSOBJECT_GUARD);
    }

    private static Object get(MethodHandle methodHandle, Object obj, Object obj2) {
        if (obj2 instanceof Integer) {
            return ((JSObject) obj).getSlot(((Integer) obj2).intValue());
        }
        if (obj2 instanceof Number) {
            int index = getIndex((Number) obj2);
            if (index > -1) {
                return ((JSObject) obj).getSlot(index);
            }
            return ((JSObject) obj).getMember(JSType.toString(obj2));
        }
        if ((obj2 instanceof String) || (obj2 instanceof ConsString)) {
            String string = obj2.toString();
            if (string.indexOf(40) != -1) {
                return (Object) methodHandle.invokeExact(obj, string);
            }
            return ((JSObject) obj).getMember(string);
        }
        return null;
    }

    private static void put(Object obj, Object obj2, Object obj3) {
        if (obj2 instanceof Integer) {
            ((JSObject) obj).setSlot(((Integer) obj2).intValue(), obj3);
            return;
        }
        if (obj2 instanceof Number) {
            int index = getIndex((Number) obj2);
            if (index > -1) {
                ((JSObject) obj).setSlot(index, obj3);
                return;
            } else {
                ((JSObject) obj).setMember(JSType.toString(obj2), obj3);
                return;
            }
        }
        if ((obj2 instanceof String) || (obj2 instanceof ConsString)) {
            ((JSObject) obj).setMember(obj2.toString(), obj3);
        }
    }

    private static int getIndex(Number number) {
        double dDoubleValue = number.doubleValue();
        if (((double) ((int) dDoubleValue)) == dDoubleValue) {
            return (int) dDoubleValue;
        }
        return -1;
    }

    private static Object callToApply(MethodHandle methodHandle, JSObject jSObject, Object obj, Object[] objArr) {
        if (!$assertionsDisabled && objArr.length < 2) {
            throw new AssertionError();
        }
        Object obj2 = objArr[0];
        Object[] objArr2 = new Object[objArr.length - 1];
        System.arraycopy(objArr, 1, objArr2, 0, objArr2.length);
        try {
            return (Object) methodHandle.invokeExact(jSObject, obj, new Object[]{obj2, objArr2});
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private static Object jsObjectScopeCall(JSObject jSObject, Object obj, Object[] objArr) {
        Object objWrap;
        if (obj == ScriptRuntime.UNDEFINED && !jSObject.isStrictFunction()) {
            Global global = Context.getGlobal();
            objWrap = ScriptObjectMirror.wrap(global, global);
        } else {
            objWrap = obj;
        }
        return jSObject.call(objWrap, objArr);
    }

    private static MethodHandle findJSObjectMH_V(String str, Class cls, Class[] clsArr) {
        return f59MH.findVirtual(MethodHandles.lookup(), JSObject.class, str, f59MH.type(cls, clsArr));
    }

    private static MethodHandle findOwnMH_S(String str, Class cls, Class[] clsArr) {
        return f59MH.findStatic(MethodHandles.lookup(), JSObjectLinker.class, str, f59MH.type(cls, clsArr));
    }
}

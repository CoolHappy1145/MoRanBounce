package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.DynamicLinkerFactory;
import jdk.internal.dynalink.GuardedInvocationFilter;
import jdk.internal.dynalink.beans.DynamicMethod;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/Bootstrap.class */
public final class Bootstrap {
    public static final CompilerConstants.Call BOOTSTRAP;

    /* renamed from: MH */
    private static final MethodHandleFunctionality f57MH;
    private static final MethodHandle VOID_TO_OBJECT;
    private static final int NASHORN_DEFAULT_UNSTABLE_RELINK_THRESHOLD = 16;
    private static final DynamicLinker dynamicLinker;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Bootstrap.class.desiredAssertionStatus();
        BOOTSTRAP = CompilerConstants.staticCallNoLookup(Bootstrap.class, "bootstrap", CallSite.class, new Class[]{MethodHandles.Lookup.class, String.class, MethodType.class, Integer.TYPE});
        f57MH = MethodHandleFactory.getFunctionality();
        VOID_TO_OBJECT = f57MH.constant(Object.class, ScriptRuntime.UNDEFINED);
        DynamicLinkerFactory dynamicLinkerFactory = new DynamicLinkerFactory();
        NashornBeansLinker nashornBeansLinker = new NashornBeansLinker();
        dynamicLinkerFactory.setPrioritizedLinkers(new GuardingDynamicLinker[]{new NashornLinker(), new NashornPrimitiveLinker(), new NashornStaticClassLinker(), new BoundCallableLinker(), new JavaSuperAdapterLinker(), new JSObjectLinker(nashornBeansLinker), new BrowserJSObjectLinker(nashornBeansLinker), new ReflectionCheckLinker()});
        dynamicLinkerFactory.setFallbackLinkers(new GuardingDynamicLinker[]{nashornBeansLinker, new NashornBottomLinker()});
        dynamicLinkerFactory.setSyncOnRelink(true);
        dynamicLinkerFactory.setPrelinkFilter(new GuardedInvocationFilter() { // from class: jdk.nashorn.internal.runtime.linker.Bootstrap.1
            @Override // jdk.internal.dynalink.GuardedInvocationFilter
            public GuardedInvocation filter(GuardedInvocation guardedInvocation, LinkRequest linkRequest, LinkerServices linkerServices) {
                CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
                return OptimisticReturnFilters.filterOptimisticReturnValue(guardedInvocation, callSiteDescriptor).asType(linkerServices, callSiteDescriptor.getMethodType());
            }
        });
        dynamicLinkerFactory.setAutoConversionStrategy(new MethodTypeConversionStrategy() { // from class: jdk.nashorn.internal.runtime.linker.Bootstrap.2
            @Override // jdk.internal.dynalink.linker.MethodTypeConversionStrategy
            public MethodHandle asType(MethodHandle methodHandle, MethodType methodType) {
                return Bootstrap.unboxReturnType(methodHandle, methodType);
            }
        });
        dynamicLinkerFactory.setInternalObjectsFilter(NashornBeansLinker.createHiddenObjectFilter());
        int intProperty = Options.getIntProperty("nashorn.unstable.relink.threshold", 16);
        if (intProperty > -1) {
            dynamicLinkerFactory.setUnstableRelinkThreshold(intProperty);
        }
        dynamicLinkerFactory.setClassLoader(Bootstrap.class.getClassLoader());
        dynamicLinker = dynamicLinkerFactory.createLinker();
    }

    private Bootstrap() {
    }

    public static boolean isCallable(Object obj) {
        if (obj == ScriptRuntime.UNDEFINED || obj == null) {
            return false;
        }
        return (obj instanceof ScriptFunction) || isJSObjectFunction(obj) || (obj instanceof DynamicMethod) || (obj instanceof BoundCallable) || isFunctionalInterfaceObject(obj) || (obj instanceof StaticClass);
    }

    public static boolean isStrictCallable(Object obj) {
        if (obj instanceof ScriptFunction) {
            return ((ScriptFunction) obj).isStrict();
        }
        if (isJSObjectFunction(obj)) {
            return ((JSObject) obj).isStrictFunction();
        }
        if (obj instanceof BoundCallable) {
            return isStrictCallable(((BoundCallable) obj).getCallable());
        }
        if ((obj instanceof DynamicMethod) || (obj instanceof StaticClass) || isFunctionalInterfaceObject(obj)) {
            return false;
        }
        throw notFunction(obj);
    }

    private static ECMAException notFunction(Object obj) {
        return ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(obj)});
    }

    private static boolean isJSObjectFunction(Object obj) {
        return (obj instanceof JSObject) && ((JSObject) obj).isFunction();
    }

    public static boolean isDynamicMethod(Object obj) {
        return (obj instanceof BoundCallable ? ((BoundCallable) obj).getCallable() : obj) instanceof DynamicMethod;
    }

    public static boolean isFunctionalInterfaceObject(Object obj) {
        return (JSType.isPrimitive(obj) || NashornBeansLinker.getFunctionalInterfaceMethodName(obj.getClass()) == null) ? false : true;
    }

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String str, MethodType methodType, int i) {
        return (CallSite) dynamicLinker.link(LinkerCallSite.newLinkerCallSite(lookup, str, methodType, i));
    }

    public static CallSite mathBootstrap(MethodHandles.Lookup lookup, String str, MethodType methodType, int i) {
        MethodHandle methodHandle;
        switch (str) {
            case "iadd":
                methodHandle = JSType.ADD_EXACT.methodHandle();
                break;
            case "isub":
                methodHandle = JSType.SUB_EXACT.methodHandle();
                break;
            case "imul":
                methodHandle = JSType.MUL_EXACT.methodHandle();
                break;
            case "idiv":
                methodHandle = JSType.DIV_EXACT.methodHandle();
                break;
            case "irem":
                methodHandle = JSType.REM_EXACT.methodHandle();
                break;
            case "ineg":
                methodHandle = JSType.NEGATE_EXACT.methodHandle();
                break;
            default:
                throw new AssertionError("unsupported math intrinsic");
        }
        return new ConstantCallSite(f57MH.insertArguments(methodHandle, methodHandle.type().parameterCount() - 1, new Object[]{Integer.valueOf(i)}));
    }

    public static MethodHandle createDynamicInvoker(String str, Class cls, Class[] clsArr) {
        return createDynamicInvoker(str, MethodType.methodType((Class<?>) cls, (Class<?>[]) clsArr));
    }

    public static MethodHandle createDynamicInvoker(String str, int i, Class cls, Class[] clsArr) {
        return bootstrap(MethodHandles.publicLookup(), str, MethodType.methodType((Class<?>) cls, (Class<?>[]) clsArr), i).dynamicInvoker();
    }

    public static MethodHandle createDynamicInvoker(String str, MethodType methodType) {
        return bootstrap(MethodHandles.publicLookup(), str, methodType, 0).dynamicInvoker();
    }

    public static Object bindCallable(Object obj, Object obj2, Object[] objArr) {
        if (obj instanceof ScriptFunction) {
            return ((ScriptFunction) obj).createBound(obj2, objArr);
        }
        if (obj instanceof BoundCallable) {
            return ((BoundCallable) obj).bind(objArr);
        }
        if (isCallable(obj)) {
            return new BoundCallable(obj, obj2, objArr);
        }
        throw notFunction(obj);
    }

    public static Object createSuperAdapter(Object obj) {
        return new JavaSuperAdapter(obj);
    }

    public static void checkReflectionAccess(Class cls, boolean z) {
        ReflectionCheckLinker.checkReflectionAccess(cls, z);
    }

    public static LinkerServices getLinkerServices() {
        return dynamicLinker.getLinkerServices();
    }

    static GuardedInvocation asTypeSafeReturn(GuardedInvocation guardedInvocation, LinkerServices linkerServices, CallSiteDescriptor callSiteDescriptor) {
        if (guardedInvocation == null) {
            return null;
        }
        return guardedInvocation.asTypeSafeReturn(linkerServices, callSiteDescriptor.getMethodType());
    }

    private static MethodHandle unboxReturnType(MethodHandle methodHandle, MethodType methodType) {
        MethodType methodTypeType = methodHandle.type();
        Class<?> clsReturnType = methodTypeType.returnType();
        Class<?> clsReturnType2 = methodType.returnType();
        if (TypeUtilities.isWrapperType(clsReturnType)) {
            if (clsReturnType2.isPrimitive()) {
                if ($assertionsDisabled || TypeUtilities.isMethodInvocationConvertible(clsReturnType, clsReturnType2)) {
                    return MethodHandles.explicitCastArguments(methodHandle, methodTypeType.changeReturnType(clsReturnType2));
                }
                throw new AssertionError();
            }
        } else if (clsReturnType == Void.TYPE && clsReturnType2 == Object.class) {
            return MethodHandles.filterReturnValue(methodHandle, VOID_TO_OBJECT);
        }
        return methodHandle;
    }
}

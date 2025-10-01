package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import jdk.internal.dynalink.support.DefaultInternalObjectFilter;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornBeansLinker.class */
public class NashornBeansLinker implements GuardingDynamicLinker {
    private static final boolean MIRROR_ALWAYS = Options.getBooleanProperty("nashorn.mirror.always", true);
    private static final MethodHandle EXPORT_ARGUMENT;
    private static final MethodHandle IMPORT_RESULT;
    private static final MethodHandle FILTER_CONSSTRING;
    private static final ClassValue FUNCTIONAL_IFACE_METHOD_NAME;
    private final BeansLinker beansLinker = new BeansLinker();

    static {
        Lookup lookup = new Lookup(MethodHandles.lookup());
        EXPORT_ARGUMENT = lookup.findOwnStatic("exportArgument", Object.class, new Class[]{Object.class});
        IMPORT_RESULT = lookup.findOwnStatic("importResult", Object.class, new Class[]{Object.class});
        FILTER_CONSSTRING = lookup.findOwnStatic("consStringFilter", Object.class, new Class[]{Object.class});
        FUNCTIONAL_IFACE_METHOD_NAME = new ClassValue() { // from class: jdk.nashorn.internal.runtime.linker.NashornBeansLinker.1
            @Override // java.lang.ClassValue
            protected Object computeValue(Class cls) {
                return computeValue(cls);
            }

            @Override // java.lang.ClassValue
            protected String computeValue(Class cls) {
                return NashornBeansLinker.findFunctionalInterfaceMethodName(cls);
            }
        };
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        String functionalInterfaceMethodName;
        Object receiver = linkRequest.getReceiver();
        CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if (receiver instanceof ConsString) {
            Object[] arguments = linkRequest.getArguments();
            arguments[0] = "";
            GuardedInvocation guardedInvocation = getGuardedInvocation(this.beansLinker, linkRequest.replaceArguments(callSiteDescriptor, arguments), linkerServices);
            if (guardedInvocation == null) {
                return null;
            }
            return guardedInvocation.filterArguments(0, new MethodHandle[]{FILTER_CONSSTRING});
        }
        if (receiver != null && "call".equals(callSiteDescriptor.getNameToken(1)) && (functionalInterfaceMethodName = getFunctionalInterfaceMethodName(receiver.getClass())) != null) {
            MethodType methodType = callSiteDescriptor.getMethodType();
            GuardedInvocation guardedInvocation2 = getGuardedInvocation(this.beansLinker, linkRequest.replaceArguments(NashornCallSiteDescriptor.get(callSiteDescriptor.getLookup(), "dyn:callMethod:" + functionalInterfaceMethodName, callSiteDescriptor.getMethodType().dropParameterTypes(1, 2), NashornCallSiteDescriptor.getFlags(callSiteDescriptor)), linkRequest.getArguments()), new NashornBeansLinkerServices(linkerServices));
            return guardedInvocation2.replaceMethods(jdk.nashorn.internal.lookup.Lookup.f31MH.dropArguments(linkerServices.filterInternalObjects(guardedInvocation2.getInvocation()), 1, new Class[]{methodType.parameterType(1)}), guardedInvocation2.getGuard());
        }
        return getGuardedInvocation(this.beansLinker, linkRequest, linkerServices);
    }

    public static GuardedInvocation getGuardedInvocation(GuardingDynamicLinker guardingDynamicLinker, LinkRequest linkRequest, LinkerServices linkerServices) {
        return guardingDynamicLinker.getGuardedInvocation(linkRequest, new NashornBeansLinkerServices(linkerServices));
    }

    private static Object exportArgument(Object obj) {
        return exportArgument(obj, MIRROR_ALWAYS);
    }

    static Object exportArgument(Object obj, boolean z) {
        if (obj instanceof ConsString) {
            return obj.toString();
        }
        if (z && (obj instanceof ScriptObject)) {
            return ScriptUtils.wrap((ScriptObject) obj);
        }
        return obj;
    }

    private static Object importResult(Object obj) {
        return ScriptUtils.unwrap(obj);
    }

    private static Object consStringFilter(Object obj) {
        return obj instanceof ConsString ? obj.toString() : obj;
    }

    private static String findFunctionalInterfaceMethodName(Class cls) throws SecurityException {
        if (cls == null) {
            return null;
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            if (Context.isAccessibleClass(cls2) && cls2.isAnnotationPresent(FunctionalInterface.class)) {
                for (Method method : cls2.getMethods()) {
                    if (Modifier.isAbstract(method.getModifiers()) && !isOverridableObjectMethod(method)) {
                        return method.getName();
                    }
                }
            }
        }
        return findFunctionalInterfaceMethodName(cls.getSuperclass());
    }

    private static boolean isOverridableObjectMethod(Method method) {
        switch (method.getName()) {
            case "equals":
                if (method.getReturnType() == Boolean.TYPE) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1 || parameterTypes[0] != Object.class) {
                    }
                }
                break;
            case "hashCode":
                if (method.getReturnType() != Integer.TYPE || method.getParameterCount() != 0) {
                }
                break;
            case "toString":
                if (method.getReturnType() != String.class || method.getParameterCount() != 0) {
                }
                break;
        }
        return false;
    }

    static String getFunctionalInterfaceMethodName(Class cls) {
        return (String) FUNCTIONAL_IFACE_METHOD_NAME.get(cls);
    }

    static MethodHandleTransformer createHiddenObjectFilter() {
        return new DefaultInternalObjectFilter(EXPORT_ARGUMENT, MIRROR_ALWAYS ? IMPORT_RESULT : null);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornBeansLinker$NashornBeansLinkerServices.class */
    private static class NashornBeansLinkerServices implements LinkerServices {
        private final LinkerServices linkerServices;

        NashornBeansLinkerServices(LinkerServices linkerServices) {
            this.linkerServices = linkerServices;
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle asType(MethodHandle methodHandle, MethodType methodType) {
            return this.linkerServices.asType(methodHandle, methodType);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle asTypeLosslessReturn(MethodHandle methodHandle, MethodType methodType) {
            return LinkerServices.Implementation.asTypeLosslessReturn(this, methodHandle, methodType);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle getTypeConverter(Class cls, Class cls2) {
            return this.linkerServices.getTypeConverter(cls, cls2);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public boolean canConvert(Class cls, Class cls2) {
            return this.linkerServices.canConvert(cls, cls2);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) {
            return this.linkerServices.getGuardedInvocation(linkRequest);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public ConversionComparator.Comparison compareConversion(Class cls, Class cls2, Class cls3) {
            if (cls == ConsString.class) {
                if (String.class == cls2 || CharSequence.class == cls2) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
                if (String.class == cls3 || CharSequence.class == cls3) {
                    return ConversionComparator.Comparison.TYPE_2_BETTER;
                }
            }
            return this.linkerServices.compareConversion(cls, cls2, cls3);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle filterInternalObjects(MethodHandle methodHandle) {
            return this.linkerServices.filterInternalObjects(methodHandle);
        }
    }
}

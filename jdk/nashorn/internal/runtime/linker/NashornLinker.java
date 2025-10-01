package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.script.Bindings;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ListAdapter;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Undefined;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornLinker.class */
final class NashornLinker implements TypeBasedGuardingDynamicLinker, GuardingTypeConverterFactory, ConversionComparator {
    private static final ClassValue ARRAY_CONVERTERS;
    private static final MethodHandle IS_SCRIPT_OBJECT;
    private static final MethodHandle IS_SCRIPT_FUNCTION;
    private static final MethodHandle IS_NATIVE_ARRAY;
    private static final MethodHandle IS_NASHORN_OR_UNDEFINED_TYPE;
    private static final MethodHandle CREATE_MIRROR;
    private static final MethodHandle TO_COLLECTION;
    private static final MethodHandle TO_DEQUE;
    private static final MethodHandle TO_LIST;
    private static final MethodHandle TO_QUEUE;
    static final boolean $assertionsDisabled;

    NashornLinker() {
    }

    static {
        $assertionsDisabled = !NashornLinker.class.desiredAssertionStatus();
        ARRAY_CONVERTERS = new ClassValue() { // from class: jdk.nashorn.internal.runtime.linker.NashornLinker.1
            @Override // java.lang.ClassValue
            protected Object computeValue(Class cls) {
                return computeValue(cls);
            }

            @Override // java.lang.ClassValue
            protected MethodHandle computeValue(Class cls) {
                return NashornLinker.createArrayConverter(cls);
            }
        };
        IS_SCRIPT_OBJECT = Guards.isInstance(ScriptObject.class, Lookup.f31MH.type(Boolean.TYPE, new Class[]{Object.class}));
        IS_SCRIPT_FUNCTION = Guards.isInstance(ScriptFunction.class, Lookup.f31MH.type(Boolean.TYPE, new Class[]{Object.class}));
        IS_NATIVE_ARRAY = Guards.isOfClass(NativeArray.class, Lookup.f31MH.type(Boolean.TYPE, new Class[]{Object.class}));
        IS_NASHORN_OR_UNDEFINED_TYPE = findOwnMH("isNashornTypeOrUndefined", Boolean.TYPE, new Class[]{Object.class});
        CREATE_MIRROR = findOwnMH("createMirror", Object.class, new Class[]{Object.class});
        MethodHandle methodHandleFindStatic = new jdk.internal.dynalink.support.Lookup(MethodHandles.lookup()).findStatic(ListAdapter.class, "create", MethodType.methodType((Class<?>) ListAdapter.class, (Class<?>) Object.class));
        TO_COLLECTION = asReturning(methodHandleFindStatic, Collection.class);
        TO_DEQUE = asReturning(methodHandleFindStatic, Deque.class);
        TO_LIST = asReturning(methodHandleFindStatic, List.class);
        TO_QUEUE = asReturning(methodHandleFindStatic, Queue.class);
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return canLinkTypeStatic(cls);
    }

    static boolean canLinkTypeStatic(Class cls) {
        return ScriptObject.class.isAssignableFrom(cls) || Undefined.class == cls;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        LinkRequest linkRequestWithoutRuntimeContext = linkRequest.withoutRuntimeContext();
        Object receiver = linkRequestWithoutRuntimeContext.getReceiver();
        CallSiteDescriptor callSiteDescriptor = linkRequestWithoutRuntimeContext.getCallSiteDescriptor();
        if (callSiteDescriptor.getNameTokenCount() < 2 || !"dyn".equals(callSiteDescriptor.getNameToken(0))) {
            return null;
        }
        return Bootstrap.asTypeSafeReturn(getGuardedInvocation(receiver, linkRequest, callSiteDescriptor), linkerServices, callSiteDescriptor);
    }

    private static GuardedInvocation getGuardedInvocation(Object obj, LinkRequest linkRequest, CallSiteDescriptor callSiteDescriptor) {
        GuardedInvocation guardedInvocationLookup;
        if (obj instanceof ScriptObject) {
            guardedInvocationLookup = ((ScriptObject) obj).lookup(callSiteDescriptor, linkRequest);
        } else if (obj instanceof Undefined) {
            guardedInvocationLookup = Undefined.lookup(callSiteDescriptor);
        } else {
            throw new AssertionError(obj.getClass().getName());
        }
        return guardedInvocationLookup;
    }

    @Override // jdk.internal.dynalink.linker.GuardingTypeConverterFactory
    public GuardedTypeConversion convertToType(Class cls, Class cls2) {
        GuardedInvocation guardedInvocationConvertToTypeNoCast = convertToTypeNoCast(cls, cls2);
        if (guardedInvocationConvertToTypeNoCast != null) {
            return new GuardedTypeConversion(guardedInvocationConvertToTypeNoCast.asType(Lookup.f31MH.type(cls2, new Class[]{cls})), true);
        }
        GuardedInvocation samTypeConverter = getSamTypeConverter(cls, cls2);
        if (samTypeConverter != null) {
            return new GuardedTypeConversion(samTypeConverter.asType(Lookup.f31MH.type(cls2, new Class[]{cls})), false);
        }
        return null;
    }

    private static GuardedInvocation convertToTypeNoCast(Class cls, Class cls2) {
        MethodHandle converter = JavaArgumentConverters.getConverter(cls2);
        if (converter != null) {
            return new GuardedInvocation(converter, canLinkTypeStatic(cls) ? null : IS_NASHORN_OR_UNDEFINED_TYPE);
        }
        GuardedInvocation arrayConverter = getArrayConverter(cls, cls2);
        if (arrayConverter != null) {
            return arrayConverter;
        }
        return getMirrorConverter(cls, cls2);
    }

    private static GuardedInvocation getSamTypeConverter(Class cls, Class cls2) {
        boolean zIsAssignableFrom = cls.isAssignableFrom(ScriptFunction.class);
        if ((zIsAssignableFrom || ScriptFunction.class.isAssignableFrom(cls)) && isAutoConvertibleFromFunction(cls2)) {
            MethodHandle constructor = JavaAdapterFactory.getConstructor(ScriptFunction.class, cls2, getCurrentLookup());
            if ($assertionsDisabled || constructor != null) {
                return new GuardedInvocation(constructor, zIsAssignableFrom ? IS_SCRIPT_FUNCTION : null);
            }
            throw new AssertionError();
        }
        return null;
    }

    private static MethodHandles.Lookup getCurrentLookup() {
        LinkRequest linkRequest = (LinkRequest) AccessController.doPrivileged(new PrivilegedAction() { // from class: jdk.nashorn.internal.runtime.linker.NashornLinker.2
            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public LinkRequest run() {
                return LinkerServicesImpl.getCurrentLinkRequest();
            }
        });
        return linkRequest == null ? MethodHandles.publicLookup() : linkRequest.getCallSiteDescriptor().getLookup();
    }

    private static GuardedInvocation getArrayConverter(Class cls, Class cls2) {
        boolean z = cls == NativeArray.class;
        boolean z2 = !z && cls.isAssignableFrom(NativeArray.class);
        if (z || z2) {
            MethodHandle methodHandle = z2 ? IS_NATIVE_ARRAY : null;
            if (cls2.isArray()) {
                return new GuardedInvocation((MethodHandle) ARRAY_CONVERTERS.get(cls2), methodHandle);
            }
            if (cls2 == List.class) {
                return new GuardedInvocation(TO_LIST, methodHandle);
            }
            if (cls2 == Deque.class) {
                return new GuardedInvocation(TO_DEQUE, methodHandle);
            }
            if (cls2 == Queue.class) {
                return new GuardedInvocation(TO_QUEUE, methodHandle);
            }
            if (cls2 == Collection.class) {
                return new GuardedInvocation(TO_COLLECTION, methodHandle);
            }
            return null;
        }
        return null;
    }

    private static MethodHandle createArrayConverter(Class cls) {
        if (!$assertionsDisabled && !cls.isArray()) {
            throw new AssertionError();
        }
        MethodHandle methodHandleInsertArguments = Lookup.f31MH.insertArguments(JSType.TO_JAVA_ARRAY.methodHandle(), 1, new Object[]{cls.getComponentType()});
        return Lookup.f31MH.asType(methodHandleInsertArguments, methodHandleInsertArguments.type().changeReturnType((Class<?>) cls));
    }

    private static GuardedInvocation getMirrorConverter(Class cls, Class cls2) {
        if (cls2 == Map.class || cls2 == Bindings.class || cls2 == JSObject.class || cls2 == ScriptObjectMirror.class) {
            if (ScriptObject.class.isAssignableFrom(cls)) {
                return new GuardedInvocation(CREATE_MIRROR);
            }
            if (cls.isAssignableFrom(ScriptObject.class) || cls.isInterface()) {
                return new GuardedInvocation(CREATE_MIRROR, IS_SCRIPT_OBJECT);
            }
            return null;
        }
        return null;
    }

    private static boolean isAutoConvertibleFromFunction(Class cls) {
        return isAbstractClass(cls) && !ScriptObject.class.isAssignableFrom(cls) && JavaAdapterFactory.isAutoConvertibleFromFunction(cls);
    }

    static boolean isAbstractClass(Class cls) {
        return Modifier.isAbstract(cls.getModifiers()) && !cls.isArray();
    }

    @Override // jdk.internal.dynalink.linker.ConversionComparator
    public ConversionComparator.Comparison compareConversion(Class cls, Class cls2, Class cls3) {
        if (cls == NativeArray.class) {
            if (cls2 == List.class || cls2 == Collection.class || cls2 == Queue.class || cls2 == Deque.class) {
                if (!(cls3 == List.class || cls3 == Collection.class || cls3 == Queue.class || cls3 == Deque.class)) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else {
                if (cls3 == List.class || cls3 == Collection.class || cls3 == Queue.class || cls3 == Deque.class) {
                    return ConversionComparator.Comparison.TYPE_2_BETTER;
                }
            }
            if (cls2.isArray()) {
                if (!cls3.isArray()) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (cls3.isArray()) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        if (ScriptObject.class.isAssignableFrom(cls)) {
            if (cls2.isInterface()) {
                if (!cls3.isInterface()) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (cls3.isInterface()) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static MethodHandle asReturning(MethodHandle methodHandle, Class cls) {
        return methodHandle.asType(methodHandle.type().changeReturnType((Class<?>) cls));
    }

    private static Object createMirror(Object obj) {
        return obj instanceof ScriptObject ? ScriptUtils.wrap((ScriptObject) obj) : obj;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NashornLinker.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}

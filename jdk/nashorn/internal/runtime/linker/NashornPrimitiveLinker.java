package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornPrimitiveLinker.class */
final class NashornPrimitiveLinker implements TypeBasedGuardingDynamicLinker, GuardingTypeConverterFactory, ConversionComparator {
    private static final GuardedTypeConversion VOID_TO_OBJECT = new GuardedTypeConversion(new GuardedInvocation(MethodHandles.constant(Object.class, ScriptRuntime.UNDEFINED)), true);
    private static final MethodHandle GUARD_PRIMITIVE = findOwnMH("isJavaScriptPrimitive", Boolean.TYPE, new Class[]{Object.class});

    NashornPrimitiveLinker() {
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return cls == String.class || cls == Boolean.class || cls == ConsString.class || cls == Integer.class || cls == Double.class || cls == Float.class || cls == Short.class || cls == Byte.class;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        LinkRequest linkRequestWithoutRuntimeContext = linkRequest.withoutRuntimeContext();
        Object receiver = linkRequestWithoutRuntimeContext.getReceiver();
        return Bootstrap.asTypeSafeReturn(Global.primitiveLookup(linkRequestWithoutRuntimeContext, receiver), linkerServices, (NashornCallSiteDescriptor) linkRequestWithoutRuntimeContext.getCallSiteDescriptor());
    }

    @Override // jdk.internal.dynalink.linker.GuardingTypeConverterFactory
    public GuardedTypeConversion convertToType(Class cls, Class cls2) {
        MethodHandle converter = JavaArgumentConverters.getConverter(cls2);
        if (converter == null) {
            if (cls2 == Object.class && cls == Void.TYPE) {
                return VOID_TO_OBJECT;
            }
            return null;
        }
        return new GuardedTypeConversion(new GuardedInvocation(converter, cls == String.class || cls == Boolean.class || cls == ConsString.class || cls == Integer.class || cls == Double.class || cls == Float.class || cls == Short.class || cls == Byte.class ? null : GUARD_PRIMITIVE).asType(converter.type().changeParameterType(0, (Class<?>) cls)), true);
    }

    @Override // jdk.internal.dynalink.linker.ConversionComparator
    public ConversionComparator.Comparison compareConversion(Class cls, Class cls2, Class cls3) {
        Class wrapperTypeOrSelf = getWrapperTypeOrSelf(cls2);
        if (cls == wrapperTypeOrSelf) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
        }
        Class wrapperTypeOrSelf2 = getWrapperTypeOrSelf(cls3);
        if (cls == wrapperTypeOrSelf2) {
            return ConversionComparator.Comparison.TYPE_2_BETTER;
        }
        if (Number.class.isAssignableFrom(cls)) {
            if (Number.class.isAssignableFrom(wrapperTypeOrSelf)) {
                if (!Number.class.isAssignableFrom(wrapperTypeOrSelf2)) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (Number.class.isAssignableFrom(wrapperTypeOrSelf2)) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
            if (Character.class == wrapperTypeOrSelf) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (Character.class == wrapperTypeOrSelf2) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        if (cls == String.class || cls == Boolean.class || Number.class.isAssignableFrom(cls)) {
            Class primitiveTypeOrSelf = getPrimitiveTypeOrSelf(cls2);
            Class primitiveTypeOrSelf2 = getPrimitiveTypeOrSelf(cls3);
            if (TypeUtilities.isMethodInvocationConvertible(primitiveTypeOrSelf, primitiveTypeOrSelf2)) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
            if (TypeUtilities.isMethodInvocationConvertible(primitiveTypeOrSelf2, primitiveTypeOrSelf)) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (cls2 == String.class) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (cls3 == String.class) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static Class getPrimitiveTypeOrSelf(Class cls) {
        Class primitiveType = TypeUtilities.getPrimitiveType(cls);
        return primitiveType == null ? cls : primitiveType;
    }

    private static Class getWrapperTypeOrSelf(Class cls) {
        Class wrapperType = TypeUtilities.getWrapperType(cls);
        return wrapperType == null ? cls : wrapperType;
    }

    private static boolean isJavaScriptPrimitive(Object obj) {
        return ((obj instanceof String) || (obj instanceof ConsString)) || (obj instanceof Boolean) || JSType.isNumber(obj) || obj == null;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NashornPrimitiveLinker.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}

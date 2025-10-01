package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.GuardedInvocationComponent;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/BeanLinker.class */
class BeanLinker extends AbstractJavaLinker implements TypeBasedGuardingDynamicLinker {
    private static MethodHandle GET_LIST_ELEMENT;
    private static MethodHandle GET_MAP_ELEMENT;
    private static MethodHandle LIST_GUARD;
    private static MethodHandle MAP_GUARD;
    private static MethodHandle RANGE_CHECK_ARRAY;
    private static MethodHandle RANGE_CHECK_LIST;
    private static MethodHandle CONTAINS_MAP;
    private static MethodHandle SET_LIST_ELEMENT;
    private static MethodHandle PUT_MAP_ELEMENT;
    private static MethodHandle GET_ARRAY_LENGTH;
    private static MethodHandle GET_COLLECTION_LENGTH;
    private static MethodHandle GET_MAP_LENGTH;
    private static MethodHandle COLLECTION_GUARD;
    static final boolean $assertionsDisabled;

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/BeanLinker$CollectionType.class */
    private enum CollectionType {
        ARRAY,
        LIST,
        MAP
    }

    static {
        $assertionsDisabled = !BeanLinker.class.desiredAssertionStatus();
        GET_LIST_ELEMENT = Lookup.PUBLIC.findVirtual(List.class, PropertyDescriptor.GET, MethodType.methodType((Class<?>) Object.class, (Class<?>) Integer.TYPE));
        GET_MAP_ELEMENT = Lookup.PUBLIC.findVirtual(Map.class, PropertyDescriptor.GET, MethodType.methodType((Class<?>) Object.class, (Class<?>) Object.class));
        LIST_GUARD = Guards.getInstanceOfGuard(List.class);
        MAP_GUARD = Guards.getInstanceOfGuard(Map.class);
        RANGE_CHECK_ARRAY = findRangeCheck(Object.class);
        RANGE_CHECK_LIST = findRangeCheck(List.class);
        CONTAINS_MAP = Lookup.PUBLIC.findVirtual(Map.class, "containsKey", MethodType.methodType((Class<?>) Boolean.TYPE, (Class<?>) Object.class));
        SET_LIST_ELEMENT = Lookup.PUBLIC.findVirtual(List.class, PropertyDescriptor.SET, MethodType.methodType(Object.class, Integer.TYPE, Object.class));
        PUT_MAP_ELEMENT = Lookup.PUBLIC.findVirtual(Map.class, "put", MethodType.methodType(Object.class, Object.class, Object.class));
        GET_ARRAY_LENGTH = Lookup.PUBLIC.findStatic(Array.class, "getLength", MethodType.methodType((Class<?>) Integer.TYPE, (Class<?>) Object.class));
        GET_COLLECTION_LENGTH = Lookup.PUBLIC.findVirtual(Collection.class, "size", MethodType.methodType(Integer.TYPE));
        GET_MAP_LENGTH = Lookup.PUBLIC.findVirtual(Map.class, "size", MethodType.methodType(Integer.TYPE));
        COLLECTION_GUARD = Guards.getInstanceOfGuard(Collection.class);
    }

    BeanLinker(Class cls) {
        super(cls, Guards.getClassGuard(cls), Guards.getInstanceOfGuard(cls));
        if (cls.isArray()) {
            setPropertyGetter("length", GET_ARRAY_LENGTH, GuardedInvocationComponent.ValidationType.IS_ARRAY);
        } else if (List.class.isAssignableFrom(cls)) {
            setPropertyGetter("length", GET_COLLECTION_LENGTH, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
        }
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return cls == this.clazz;
    }

    @Override // jdk.internal.dynalink.beans.AbstractJavaLinker
    FacetIntrospector createFacetIntrospector() {
        return new BeanIntrospector(this.clazz);
    }

    @Override // jdk.internal.dynalink.beans.AbstractJavaLinker
    protected GuardedInvocationComponent getGuardedInvocationComponent(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) {
        GuardedInvocationComponent guardedInvocationComponent = super.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
        if (guardedInvocationComponent != null) {
            return guardedInvocationComponent;
        }
        if (list.isEmpty()) {
            return null;
        }
        String str = (String) list.get(0);
        if ("getElem".equals(str)) {
            return getElementGetter(callSiteDescriptor, linkerServices, pop(list));
        }
        if ("setElem".equals(str)) {
            return getElementSetter(callSiteDescriptor, linkerServices, pop(list));
        }
        if ("getLength".equals(str)) {
            return getLengthGetter(callSiteDescriptor);
        }
        return null;
    }

    private GuardedInvocationComponent getElementGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) throws Exception {
        GuardedInvocationComponent guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent;
        CollectionType collectionType;
        Object objConvertKeyToInteger;
        MethodHandle methodHandleConvertArgToInt;
        MethodType methodType = callSiteDescriptor.getMethodType();
        Class<?> clsParameterType = methodType.parameterType(0);
        GuardedInvocationComponent guardedInvocationComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
        if (clsParameterType.isArray()) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(MethodHandles.arrayElementGetter(clsParameterType), linkerServices);
            collectionType = CollectionType.ARRAY;
        } else if (List.class.isAssignableFrom(clsParameterType)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(GET_LIST_ELEMENT, linkerServices);
            collectionType = CollectionType.LIST;
        } else if (Map.class.isAssignableFrom(clsParameterType)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(GET_MAP_ELEMENT, linkerServices);
            collectionType = CollectionType.MAP;
        } else if (this.clazz.isArray()) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = getClassGuardedInvocationComponent(linkerServices.filterInternalObjects(MethodHandles.arrayElementGetter(this.clazz)), methodType);
            collectionType = CollectionType.ARRAY;
        } else if (List.class.isAssignableFrom(this.clazz)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(GET_LIST_ELEMENT, Guards.asType(LIST_GUARD, methodType), List.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
            collectionType = CollectionType.LIST;
        } else if (Map.class.isAssignableFrom(this.clazz)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(GET_MAP_ELEMENT, Guards.asType(MAP_GUARD, methodType), Map.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
            collectionType = CollectionType.MAP;
        } else {
            return guardedInvocationComponent;
        }
        String fixedKey = getFixedKey(callSiteDescriptor);
        if (collectionType != CollectionType.MAP && fixedKey != null) {
            objConvertKeyToInteger = convertKeyToInteger(fixedKey, linkerServices);
            if (objConvertKeyToInteger == null) {
                return guardedInvocationComponent;
            }
        } else {
            objConvertKeyToInteger = fixedKey;
        }
        GuardedInvocation guardedInvocation = guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.getGuardedInvocation();
        Binder binder = new Binder(linkerServices, methodType, objConvertKeyToInteger);
        MethodHandle invocation = guardedInvocation.getInvocation();
        if (guardedInvocationComponent == null) {
            return guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.replaceInvocation(binder.bind(invocation));
        }
        switch (C00071.$SwitchMap$jdk$internal$dynalink$beans$BeanLinker$CollectionType[collectionType.ordinal()]) {
            case 1:
                methodHandleConvertArgToInt = convertArgToInt(RANGE_CHECK_LIST, linkerServices, callSiteDescriptor);
                break;
            case 2:
                methodHandleConvertArgToInt = linkerServices.filterInternalObjects(CONTAINS_MAP);
                break;
            case 3:
                methodHandleConvertArgToInt = convertArgToInt(RANGE_CHECK_ARRAY, linkerServices, callSiteDescriptor);
                break;
            default:
                throw new AssertionError();
        }
        return guardedInvocationComponent.compose(matchReturnTypes(binder.bind(invocation), guardedInvocationComponent.getGuardedInvocation().getInvocation()).guardWithTest(binder.bindTest(methodHandleConvertArgToInt)), guardedInvocation.getGuard(), guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.getValidatorClass(), guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.getValidationType());
    }

    /* renamed from: jdk.internal.dynalink.beans.BeanLinker$1 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/BeanLinker$1.class */
    static /* synthetic */ class C00071 {
        static final int[] $SwitchMap$jdk$internal$dynalink$beans$BeanLinker$CollectionType = new int[CollectionType.values().length];

        static {
            try {
                $SwitchMap$jdk$internal$dynalink$beans$BeanLinker$CollectionType[CollectionType.LIST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$internal$dynalink$beans$BeanLinker$CollectionType[CollectionType.MAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$internal$dynalink$beans$BeanLinker$CollectionType[CollectionType.ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static GuardedInvocationComponent createInternalFilteredGuardedInvocationComponent(MethodHandle methodHandle, LinkerServices linkerServices) {
        return new GuardedInvocationComponent(linkerServices.filterInternalObjects(methodHandle));
    }

    private static GuardedInvocationComponent createInternalFilteredGuardedInvocationComponent(MethodHandle methodHandle, MethodHandle methodHandle2, Class cls, GuardedInvocationComponent.ValidationType validationType, LinkerServices linkerServices) {
        return new GuardedInvocationComponent(linkerServices.filterInternalObjects(methodHandle), methodHandle2, cls, validationType);
    }

    private static String getFixedKey(CallSiteDescriptor callSiteDescriptor) {
        if (callSiteDescriptor.getNameTokenCount() == 2) {
            return null;
        }
        return callSiteDescriptor.getNameToken(2);
    }

    private static Object convertKeyToInteger(String str, LinkerServices linkerServices) throws Exception {
        try {
            if (linkerServices.canConvert(String.class, Number.class)) {
                try {
                    try {
                        Object objInvoke = (Object) linkerServices.getTypeConverter(String.class, Number.class).invoke(str);
                        if (!(objInvoke instanceof Number)) {
                            return null;
                        }
                        Number number = (Number) objInvoke;
                        if (number instanceof Integer) {
                            return number;
                        }
                        int iIntValue = number.intValue();
                        double dDoubleValue = number.doubleValue();
                        if (iIntValue != dDoubleValue && !Double.isInfinite(dDoubleValue)) {
                            return null;
                        }
                        return Integer.valueOf(iIntValue);
                    } catch (Throwable th) {
                        throw new RuntimeException(th);
                    }
                } catch (Error | Exception e) {
                    throw e;
                }
            }
            return Integer.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static MethodHandle convertArgToInt(MethodHandle methodHandle, LinkerServices linkerServices, CallSiteDescriptor callSiteDescriptor) {
        Class<?> clsParameterType = callSiteDescriptor.getMethodType().parameterType(1);
        if (TypeUtilities.isMethodInvocationConvertible(clsParameterType, Number.class)) {
            return methodHandle;
        }
        if (linkerServices.canConvert(clsParameterType, Number.class)) {
            MethodHandle typeConverter = linkerServices.getTypeConverter(clsParameterType, Number.class);
            return MethodHandles.filterArguments(methodHandle, 1, typeConverter.asType(typeConverter.type().changeReturnType(methodHandle.type().parameterType(1))));
        }
        return methodHandle;
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/BeanLinker$Binder.class */
    private static class Binder {
        private final LinkerServices linkerServices;
        private final MethodType methodType;
        private final Object fixedKey;

        Binder(LinkerServices linkerServices, MethodType methodType, Object obj) {
            this.linkerServices = linkerServices;
            this.methodType = obj == null ? methodType : methodType.insertParameterTypes(1, obj.getClass());
            this.fixedKey = obj;
        }

        MethodHandle bind(MethodHandle methodHandle) {
            return bindToFixedKey(this.linkerServices.asTypeLosslessReturn(methodHandle, this.methodType));
        }

        MethodHandle bindTest(MethodHandle methodHandle) {
            return bindToFixedKey(Guards.asType(methodHandle, this.methodType));
        }

        private MethodHandle bindToFixedKey(MethodHandle methodHandle) {
            return this.fixedKey == null ? methodHandle : MethodHandles.insertArguments(methodHandle, 1, this.fixedKey);
        }
    }

    private static MethodHandle findRangeCheck(Class cls) {
        return Lookup.findOwnStatic(MethodHandles.lookup(), "rangeCheck", Boolean.TYPE, new Class[]{cls, Object.class});
    }

    private static final boolean rangeCheck(Object obj, Object obj2) {
        if (!(obj2 instanceof Number)) {
            return false;
        }
        Number number = (Number) obj2;
        int iIntValue = number.intValue();
        double dDoubleValue = number.doubleValue();
        if (iIntValue != dDoubleValue && !Double.isInfinite(dDoubleValue)) {
            return false;
        }
        if (0 <= iIntValue && iIntValue < Array.getLength(obj)) {
            return true;
        }
        throw new ArrayIndexOutOfBoundsException("Array index out of range: " + number);
    }

    private static final boolean rangeCheck(List list, Object obj) {
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        int iIntValue = number.intValue();
        double dDoubleValue = number.doubleValue();
        if (iIntValue != dDoubleValue && !Double.isInfinite(dDoubleValue)) {
            return false;
        }
        if (0 <= iIntValue && iIntValue < list.size()) {
            return true;
        }
        throw new IndexOutOfBoundsException("Index: " + number + ", Size: " + list.size());
    }

    private GuardedInvocationComponent getElementSetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) throws Exception {
        GuardedInvocationComponent guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent;
        CollectionType collectionType;
        Object objConvertKeyToInteger;
        MethodType methodType = callSiteDescriptor.getMethodType();
        Class<?> clsParameterType = methodType.parameterType(0);
        if (clsParameterType.isArray()) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(MethodHandles.arrayElementSetter(clsParameterType), linkerServices);
            collectionType = CollectionType.ARRAY;
        } else if (List.class.isAssignableFrom(clsParameterType)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(SET_LIST_ELEMENT, linkerServices);
            collectionType = CollectionType.LIST;
        } else if (Map.class.isAssignableFrom(clsParameterType)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(PUT_MAP_ELEMENT, linkerServices);
            collectionType = CollectionType.MAP;
        } else if (this.clazz.isArray()) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = getClassGuardedInvocationComponent(linkerServices.filterInternalObjects(MethodHandles.arrayElementSetter(this.clazz)), methodType);
            collectionType = CollectionType.ARRAY;
        } else if (List.class.isAssignableFrom(this.clazz)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(SET_LIST_ELEMENT, Guards.asType(LIST_GUARD, methodType), List.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
            collectionType = CollectionType.LIST;
        } else if (Map.class.isAssignableFrom(this.clazz)) {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = createInternalFilteredGuardedInvocationComponent(PUT_MAP_ELEMENT, Guards.asType(MAP_GUARD, methodType), Map.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
            collectionType = CollectionType.MAP;
        } else {
            guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent = null;
            collectionType = null;
        }
        GuardedInvocationComponent guardedInvocationComponent = collectionType == CollectionType.MAP ? null : getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
        if (guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent == null) {
            return guardedInvocationComponent;
        }
        String fixedKey = getFixedKey(callSiteDescriptor);
        if (collectionType != CollectionType.MAP && fixedKey != null) {
            objConvertKeyToInteger = convertKeyToInteger(fixedKey, linkerServices);
            if (objConvertKeyToInteger == null) {
                return guardedInvocationComponent;
            }
        } else {
            objConvertKeyToInteger = fixedKey;
        }
        GuardedInvocation guardedInvocation = guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.getGuardedInvocation();
        Binder binder = new Binder(linkerServices, methodType, objConvertKeyToInteger);
        MethodHandle invocation = guardedInvocation.getInvocation();
        if (guardedInvocationComponent == null) {
            return guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.replaceInvocation(binder.bind(invocation));
        }
        if ($assertionsDisabled || collectionType == CollectionType.LIST || collectionType == CollectionType.ARRAY) {
            return guardedInvocationComponent.compose(matchReturnTypes(binder.bind(invocation), guardedInvocationComponent.getGuardedInvocation().getInvocation()).guardWithTest(binder.bindTest(convertArgToInt(collectionType == CollectionType.LIST ? RANGE_CHECK_LIST : RANGE_CHECK_ARRAY, linkerServices, callSiteDescriptor))), guardedInvocation.getGuard(), guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.getValidatorClass(), guardedInvocationComponentCreateInternalFilteredGuardedInvocationComponent.getValidationType());
        }
        throw new AssertionError();
    }

    private GuardedInvocationComponent getLengthGetter(CallSiteDescriptor callSiteDescriptor) {
        assertParameterCount(callSiteDescriptor, 1);
        MethodType methodType = callSiteDescriptor.getMethodType();
        Class<?> clsParameterType = methodType.parameterType(0);
        if (clsParameterType.isArray()) {
            return new GuardedInvocationComponent(GET_ARRAY_LENGTH.asType(methodType));
        }
        if (Collection.class.isAssignableFrom(clsParameterType)) {
            return new GuardedInvocationComponent(GET_COLLECTION_LENGTH.asType(methodType));
        }
        if (Map.class.isAssignableFrom(clsParameterType)) {
            return new GuardedInvocationComponent(GET_MAP_LENGTH.asType(methodType));
        }
        if (this.clazz.isArray()) {
            return new GuardedInvocationComponent(GET_ARRAY_LENGTH.asType(methodType), Guards.isArray(0, methodType), GuardedInvocationComponent.ValidationType.IS_ARRAY);
        }
        if (Collection.class.isAssignableFrom(this.clazz)) {
            return new GuardedInvocationComponent(GET_COLLECTION_LENGTH.asType(methodType), Guards.asType(COLLECTION_GUARD, methodType), Collection.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
        }
        if (Map.class.isAssignableFrom(this.clazz)) {
            return new GuardedInvocationComponent(GET_MAP_LENGTH.asType(methodType), Guards.asType(MAP_GUARD, methodType), Map.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
        }
        return null;
    }

    private static void assertParameterCount(CallSiteDescriptor callSiteDescriptor, int i) {
        if (callSiteDescriptor.getMethodType().parameterCount() != i) {
            throw new BootstrapMethodError(callSiteDescriptor.getName() + " must have exactly " + i + " parameters.");
        }
    }
}

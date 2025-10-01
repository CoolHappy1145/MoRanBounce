package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.GuardedInvocationComponent;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/AbstractJavaLinker.class */
abstract class AbstractJavaLinker implements GuardingDynamicLinker {
    final Class clazz;
    private final MethodHandle classGuard;
    private final MethodHandle assignableGuard;
    private final Map propertyGetters;
    private final Map propertySetters;
    private final Map methods;
    private static final MethodHandle IS_METHOD_HANDLE_NOT_NULL;
    private static final MethodHandle CONSTANT_NULL_DROP_METHOD_HANDLE;
    private static final Lookup privateLookup;
    private static final MethodHandle IS_ANNOTATED_METHOD_NOT_NULL;
    private static final MethodHandle CONSTANT_NULL_DROP_ANNOTATED_METHOD;
    private static final MethodHandle GET_ANNOTATED_METHOD;
    private static final MethodHandle GETTER_INVOKER;
    private static final MethodHandle IS_DYNAMIC_METHOD;
    private static final MethodHandle OBJECT_IDENTITY;
    private static MethodHandle GET_PROPERTY_GETTER_HANDLE;
    private final MethodHandle getPropertyGetterHandle;
    private static final MethodHandle GET_PROPERTY_SETTER_HANDLE;
    private final MethodHandle getPropertySetterHandle;
    private static MethodHandle GET_DYNAMIC_METHOD;
    private final MethodHandle getDynamicMethod;
    static final boolean $assertionsDisabled;

    abstract FacetIntrospector createFacetIntrospector();

    static {
        $assertionsDisabled = !AbstractJavaLinker.class.desiredAssertionStatus();
        IS_METHOD_HANDLE_NOT_NULL = Guards.isNotNull().asType(MethodType.methodType((Class<?>) Boolean.TYPE, (Class<?>) MethodHandle.class));
        CONSTANT_NULL_DROP_METHOD_HANDLE = MethodHandles.dropArguments(MethodHandles.constant(Object.class, null), 0, (Class<?>[]) new Class[]{MethodHandle.class});
        privateLookup = new Lookup(MethodHandles.lookup());
        IS_ANNOTATED_METHOD_NOT_NULL = Guards.isNotNull().asType(MethodType.methodType((Class<?>) Boolean.TYPE, (Class<?>) AnnotatedDynamicMethod.class));
        CONSTANT_NULL_DROP_ANNOTATED_METHOD = MethodHandles.dropArguments(MethodHandles.constant(Object.class, null), 0, (Class<?>[]) new Class[]{AnnotatedDynamicMethod.class});
        GET_ANNOTATED_METHOD = privateLookup.findVirtual(AnnotatedDynamicMethod.class, "getTarget", MethodType.methodType(MethodHandle.class, MethodHandles.Lookup.class, LinkerServices.class));
        GETTER_INVOKER = MethodHandles.invoker(MethodType.methodType((Class<?>) Object.class, (Class<?>) Object.class));
        IS_DYNAMIC_METHOD = Guards.isInstance(DynamicMethod.class, MethodType.methodType((Class<?>) Boolean.TYPE, (Class<?>) Object.class));
        OBJECT_IDENTITY = MethodHandles.identity(Object.class);
        GET_PROPERTY_GETTER_HANDLE = MethodHandles.dropArguments(privateLookup.findOwnSpecial("getPropertyGetterHandle", Object.class, new Class[]{Object.class}), 1, (Class<?>[]) new Class[]{Object.class});
        GET_PROPERTY_SETTER_HANDLE = MethodHandles.dropArguments(MethodHandles.dropArguments(privateLookup.findOwnSpecial("getPropertySetterHandle", MethodHandle.class, new Class[]{CallSiteDescriptor.class, LinkerServices.class, Object.class}), 3, (Class<?>[]) new Class[]{Object.class}), 5, (Class<?>[]) new Class[]{Object.class});
        GET_DYNAMIC_METHOD = MethodHandles.dropArguments(privateLookup.findOwnSpecial("getDynamicMethod", Object.class, new Class[]{Object.class}), 1, (Class<?>[]) new Class[]{Object.class});
    }

    AbstractJavaLinker(Class cls, MethodHandle methodHandle) {
        this(cls, methodHandle, methodHandle);
    }

    AbstractJavaLinker(Class cls, MethodHandle methodHandle, MethodHandle methodHandle2) {
        this.propertyGetters = new HashMap();
        this.propertySetters = new HashMap();
        this.methods = new HashMap();
        this.getPropertyGetterHandle = GET_PROPERTY_GETTER_HANDLE.bindTo(this);
        this.getPropertySetterHandle = GET_PROPERTY_SETTER_HANDLE.bindTo(this);
        this.getDynamicMethod = GET_DYNAMIC_METHOD.bindTo(this);
        this.clazz = cls;
        this.classGuard = methodHandle;
        this.assignableGuard = methodHandle2;
        FacetIntrospector facetIntrospectorCreateFacetIntrospector = createFacetIntrospector();
        for (Method method : facetIntrospectorCreateFacetIntrospector.getMethods()) {
            String name = method.getName();
            addMember(name, method, this.methods);
            if (name.startsWith(PropertyDescriptor.GET) && name.length() > 3 && method.getParameterTypes().length == 0) {
                setPropertyGetter(method, 3);
            } else if (name.startsWith("is") && name.length() > 2 && method.getParameterTypes().length == 0 && method.getReturnType() == Boolean.TYPE) {
                setPropertyGetter(method, 2);
            } else if (name.startsWith(PropertyDescriptor.SET) && name.length() > 3 && method.getParameterTypes().length == 1) {
                addMember(decapitalize(name.substring(3)), method, this.propertySetters);
            }
        }
        for (Field field : facetIntrospectorCreateFacetIntrospector.getFields()) {
            String name2 = field.getName();
            if (!this.propertyGetters.containsKey(name2)) {
                setPropertyGetter(name2, facetIntrospectorCreateFacetIntrospector.unreflectGetter(field), GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            }
            if (!Modifier.isFinal(field.getModifiers()) && !this.propertySetters.containsKey(name2)) {
                addMember(name2, new SimpleDynamicMethod(facetIntrospectorCreateFacetIntrospector.unreflectSetter(field), cls, name2), this.propertySetters);
            }
        }
        for (Map.Entry entry : facetIntrospectorCreateFacetIntrospector.getInnerClassGetters().entrySet()) {
            String str = (String) entry.getKey();
            if (!this.propertyGetters.containsKey(str)) {
                setPropertyGetter(str, (MethodHandle) entry.getValue(), GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            }
        }
    }

    private static String decapitalize(String str) {
        if (!$assertionsDisabled && str == null) {
            throw new AssertionError();
        }
        if (str.isEmpty()) {
            return str;
        }
        char cCharAt = str.charAt(0);
        if (Character.isLowerCase(cCharAt)) {
            return str;
        }
        if (str.length() > 1 && Character.isUpperCase(str.charAt(1))) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(cCharAt);
        return new String(charArray);
    }

    Collection getReadablePropertyNames() {
        return getUnmodifiableKeys(this.propertyGetters);
    }

    Collection getWritablePropertyNames() {
        return getUnmodifiableKeys(this.propertySetters);
    }

    Collection getMethodNames() {
        return getUnmodifiableKeys(this.methods);
    }

    private static Collection getUnmodifiableKeys(Map map) {
        return Collections.unmodifiableCollection(map.keySet());
    }

    private void setPropertyGetter(String str, SingleDynamicMethod singleDynamicMethod, GuardedInvocationComponent.ValidationType validationType) {
        this.propertyGetters.put(str, new AnnotatedDynamicMethod(singleDynamicMethod, validationType));
    }

    private void setPropertyGetter(Method method, int i) {
        setPropertyGetter(decapitalize(method.getName().substring(i)), createDynamicMethod(getMostGenericGetter(method)), GuardedInvocationComponent.ValidationType.INSTANCE_OF);
    }

    void setPropertyGetter(String str, MethodHandle methodHandle, GuardedInvocationComponent.ValidationType validationType) {
        setPropertyGetter(str, new SimpleDynamicMethod(methodHandle, this.clazz, str), validationType);
    }

    private void addMember(String str, AccessibleObject accessibleObject, Map map) {
        addMember(str, createDynamicMethod(accessibleObject), map);
    }

    private void addMember(String str, SingleDynamicMethod singleDynamicMethod, Map map) {
        DynamicMethod dynamicMethod = (DynamicMethod) map.get(str);
        DynamicMethod dynamicMethodMergeMethods = mergeMethods(singleDynamicMethod, dynamicMethod, this.clazz, str);
        if (dynamicMethodMergeMethods != dynamicMethod) {
            map.put(str, dynamicMethodMergeMethods);
        }
    }

    static DynamicMethod createDynamicMethod(Iterable iterable, Class cls, String str) {
        DynamicMethod dynamicMethodMergeMethods = null;
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            dynamicMethodMergeMethods = mergeMethods(createDynamicMethod((AccessibleObject) it.next()), dynamicMethodMergeMethods, cls, str);
        }
        return dynamicMethodMergeMethods;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static SingleDynamicMethod createDynamicMethod(AccessibleObject accessibleObject) {
        if (CallerSensitiveDetector.isCallerSensitive(accessibleObject)) {
            return new CallerSensitiveDynamicMethod(accessibleObject);
        }
        try {
            Member member = (Member) accessibleObject;
            return new SimpleDynamicMethod(unreflectSafely(accessibleObject), member.getDeclaringClass(), member.getName(), accessibleObject instanceof Constructor);
        } catch (IllegalAccessError unused) {
            return new CallerSensitiveDynamicMethod(accessibleObject);
        }
    }

    private static MethodHandle unreflectSafely(AccessibleObject accessibleObject) {
        if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            MethodHandle methodHandleUnreflect = Lookup.PUBLIC.unreflect(method);
            if (Modifier.isStatic(method.getModifiers())) {
                return StaticClassIntrospector.editStaticMethodHandle(methodHandleUnreflect);
            }
            return methodHandleUnreflect;
        }
        return StaticClassIntrospector.editConstructorMethodHandle(Lookup.PUBLIC.unreflectConstructor((Constructor) accessibleObject));
    }

    private static DynamicMethod mergeMethods(SingleDynamicMethod singleDynamicMethod, DynamicMethod dynamicMethod, Class cls, String str) {
        if (dynamicMethod == null) {
            return singleDynamicMethod;
        }
        if (dynamicMethod.contains(singleDynamicMethod)) {
            return dynamicMethod;
        }
        if (dynamicMethod instanceof SingleDynamicMethod) {
            OverloadedDynamicMethod overloadedDynamicMethod = new OverloadedDynamicMethod(cls, str);
            overloadedDynamicMethod.addMethod((SingleDynamicMethod) dynamicMethod);
            overloadedDynamicMethod.addMethod(singleDynamicMethod);
            return overloadedDynamicMethod;
        }
        if (dynamicMethod instanceof OverloadedDynamicMethod) {
            ((OverloadedDynamicMethod) dynamicMethod).addMethod(singleDynamicMethod);
            return dynamicMethod;
        }
        throw new AssertionError();
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        CallSiteDescriptor callSiteDescriptor = linkRequest.withoutRuntimeContext().getCallSiteDescriptor();
        if ("callMethod" == callSiteDescriptor.getNameToken(1)) {
            return getCallPropWithThis(callSiteDescriptor, linkerServices);
        }
        List listPop = CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor);
        while (true) {
            List list = listPop;
            if (!list.isEmpty()) {
                GuardedInvocationComponent guardedInvocationComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
                if (guardedInvocationComponent != null) {
                    return guardedInvocationComponent.getGuardedInvocation();
                }
                listPop = pop(list);
            } else {
                return null;
            }
        }
    }

    protected GuardedInvocationComponent getGuardedInvocationComponent(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) {
        if (list.isEmpty()) {
            return null;
        }
        String str = (String) list.get(0);
        if ("getProp".equals(str)) {
            return getPropertyGetter(callSiteDescriptor, linkerServices, pop(list));
        }
        if ("setProp".equals(str)) {
            return getPropertySetter(callSiteDescriptor, linkerServices, pop(list));
        }
        if ("getMethod".equals(str)) {
            return getMethodGetter(callSiteDescriptor, linkerServices, pop(list));
        }
        return null;
    }

    static final List pop(List list) {
        return list.subList(1, list.size());
    }

    MethodHandle getClassGuard(CallSiteDescriptor callSiteDescriptor) {
        return getClassGuard(callSiteDescriptor.getMethodType());
    }

    MethodHandle getClassGuard(MethodType methodType) {
        return Guards.asType(this.classGuard, methodType);
    }

    GuardedInvocationComponent getClassGuardedInvocationComponent(MethodHandle methodHandle, MethodType methodType) {
        return new GuardedInvocationComponent(methodHandle, getClassGuard(methodType), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
    }

    private MethodHandle getAssignableGuard(MethodType methodType) {
        return Guards.asType(this.assignableGuard, methodType);
    }

    private GuardedInvocation getCallPropWithThis(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 3:
                return createGuardedDynamicMethodInvocation(callSiteDescriptor, linkerServices, callSiteDescriptor.getNameToken(2), this.methods);
            default:
                return null;
        }
    }

    private GuardedInvocation createGuardedDynamicMethodInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, String str, Map map) {
        MethodHandle dynamicMethodInvocation = getDynamicMethodInvocation(callSiteDescriptor, linkerServices, str, map);
        if (dynamicMethodInvocation == null) {
            return null;
        }
        return new GuardedInvocation(dynamicMethodInvocation, getClassGuard(callSiteDescriptor.getMethodType()));
    }

    private MethodHandle getDynamicMethodInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, String str, Map map) {
        DynamicMethod dynamicMethod = getDynamicMethod(str, map);
        if (dynamicMethod != null) {
            return dynamicMethod.getInvocation(callSiteDescriptor, linkerServices);
        }
        return null;
    }

    private DynamicMethod getDynamicMethod(String str, Map map) {
        DynamicMethod dynamicMethod = (DynamicMethod) map.get(str);
        return dynamicMethod != null ? dynamicMethod : getExplicitSignatureDynamicMethod(str, map);
    }

    private SingleDynamicMethod getExplicitSignatureDynamicMethod(String str, Map map) {
        int iIndexOf;
        int length = str.length() - 1;
        if (str.charAt(length) != ')' || (iIndexOf = str.indexOf(40)) == -1) {
            return null;
        }
        String strSubstring = str.substring(0, iIndexOf);
        String strSubstring2 = str.substring(iIndexOf + 1, length);
        DynamicMethod dynamicMethod = (DynamicMethod) map.get(strSubstring);
        if (dynamicMethod == null) {
            if (strSubstring.isEmpty()) {
                return null;
            }
            return null;
        }
        return dynamicMethod.getMethodForExactParamTypes(strSubstring2);
    }

    private GuardedInvocationComponent getPropertySetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) {
        MethodHandle methodHandleDropArguments;
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 2:
                assertParameterCount(callSiteDescriptor, 3);
                MethodType methodType = callSiteDescriptor.getMethodType();
                MethodType methodTypeChangeReturnType = methodType.returnType() == Void.TYPE ? methodType : methodType.changeReturnType(Object.class);
                MethodType methodTypeDropParameterTypes = methodTypeChangeReturnType.dropParameterTypes(1, 2);
                MethodHandle methodHandleAsType = linkerServices.asType(MethodHandles.insertArguments(this.getPropertySetterHandle, 0, callSiteDescriptor.changeMethodType(methodTypeDropParameterTypes), linkerServices), methodTypeChangeReturnType.changeReturnType(MethodHandle.class));
                MethodHandle methodHandleDropArguments2 = MethodHandles.dropArguments(MethodHandles.exactInvoker(methodTypeDropParameterTypes), 2, (Class<?>[]) new Class[]{methodTypeChangeReturnType.parameterType(1)});
                GuardedInvocationComponent guardedInvocationComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
                if (guardedInvocationComponent == null) {
                    methodHandleDropArguments = MethodHandles.dropArguments(CONSTANT_NULL_DROP_METHOD_HANDLE, 1, methodTypeChangeReturnType.parameterList()).asType(methodTypeChangeReturnType.insertParameterTypes(0, MethodHandle.class));
                } else {
                    methodHandleDropArguments = MethodHandles.dropArguments(guardedInvocationComponent.getGuardedInvocation().getInvocation(), 0, (Class<?>[]) new Class[]{MethodHandle.class});
                }
                MethodHandle methodHandleFoldArguments = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_METHOD_HANDLE_NOT_NULL, methodHandleDropArguments2, methodHandleDropArguments), methodHandleAsType);
                if (guardedInvocationComponent == null) {
                    return getClassGuardedInvocationComponent(methodHandleFoldArguments, methodTypeChangeReturnType);
                }
                return guardedInvocationComponent.compose(methodHandleFoldArguments, getClassGuard(methodTypeChangeReturnType), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            case 3:
                assertParameterCount(callSiteDescriptor, 2);
                GuardedInvocation guardedInvocationCreateGuardedDynamicMethodInvocation = createGuardedDynamicMethodInvocation(callSiteDescriptor, linkerServices, callSiteDescriptor.getNameToken(2), this.propertySetters);
                if (guardedInvocationCreateGuardedDynamicMethodInvocation != null) {
                    return new GuardedInvocationComponent(guardedInvocationCreateGuardedDynamicMethodInvocation, this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
                }
                return getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
            default:
                return null;
        }
    }

    private GuardedInvocationComponent getPropertyGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) {
        MethodHandle methodHandleDropArguments;
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 2:
                MethodType methodTypeChangeReturnType = callSiteDescriptor.getMethodType().changeReturnType(Object.class);
                assertParameterCount(callSiteDescriptor, 2);
                MethodHandle methodHandleAsType = linkerServices.asType(this.getPropertyGetterHandle, methodTypeChangeReturnType.changeReturnType(AnnotatedDynamicMethod.class));
                MethodHandle methodHandleDropArguments2 = MethodHandles.dropArguments(linkerServices.asType(MethodHandles.filterArguments(GETTER_INVOKER, 0, MethodHandles.insertArguments(GET_ANNOTATED_METHOD, 1, callSiteDescriptor.getLookup(), linkerServices)), MethodType.methodType(methodTypeChangeReturnType.returnType(), AnnotatedDynamicMethod.class, methodTypeChangeReturnType.parameterType(0))), 2, (Class<?>[]) new Class[]{methodTypeChangeReturnType.parameterType(1)});
                GuardedInvocationComponent guardedInvocationComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
                if (guardedInvocationComponent == null) {
                    methodHandleDropArguments = MethodHandles.dropArguments(CONSTANT_NULL_DROP_ANNOTATED_METHOD, 1, methodTypeChangeReturnType.parameterList()).asType(methodTypeChangeReturnType.insertParameterTypes(0, AnnotatedDynamicMethod.class));
                } else {
                    MethodHandle invocation = guardedInvocationComponent.getGuardedInvocation().getInvocation();
                    methodHandleDropArguments = MethodHandles.dropArguments(invocation.asType(invocation.type().changeReturnType(Object.class)), 0, (Class<?>[]) new Class[]{AnnotatedDynamicMethod.class});
                }
                MethodHandle methodHandleFoldArguments = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_ANNOTATED_METHOD_NOT_NULL, methodHandleDropArguments2, methodHandleDropArguments), methodHandleAsType);
                if (guardedInvocationComponent == null) {
                    return getClassGuardedInvocationComponent(methodHandleFoldArguments, methodTypeChangeReturnType);
                }
                return guardedInvocationComponent.compose(methodHandleFoldArguments, getClassGuard(methodTypeChangeReturnType), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            case 3:
                assertParameterCount(callSiteDescriptor, 1);
                AnnotatedDynamicMethod annotatedDynamicMethod = (AnnotatedDynamicMethod) this.propertyGetters.get(callSiteDescriptor.getNameToken(2));
                if (annotatedDynamicMethod == null) {
                    return getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
                }
                MethodHandle invocation2 = annotatedDynamicMethod.getInvocation(callSiteDescriptor, linkerServices);
                GuardedInvocationComponent.ValidationType validationType = annotatedDynamicMethod.validationType;
                return new GuardedInvocationComponent(invocation2, getGuard(validationType, callSiteDescriptor.getMethodType()), this.clazz, validationType);
            default:
                return null;
        }
    }

    /* renamed from: jdk.internal.dynalink.beans.AbstractJavaLinker$1 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/AbstractJavaLinker$1.class */
    static /* synthetic */ class C00031 {

        /* renamed from: $SwitchMap$jdk$internal$dynalink$beans$GuardedInvocationComponent$ValidationType */
        static final int[] f2x6625531f = new int[GuardedInvocationComponent.ValidationType.values().length];

        static {
            try {
                f2x6625531f[GuardedInvocationComponent.ValidationType.EXACT_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2x6625531f[GuardedInvocationComponent.ValidationType.INSTANCE_OF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2x6625531f[GuardedInvocationComponent.ValidationType.IS_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2x6625531f[GuardedInvocationComponent.ValidationType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private MethodHandle getGuard(GuardedInvocationComponent.ValidationType validationType, MethodType methodType) {
        switch (C00031.f2x6625531f[validationType.ordinal()]) {
            case 1:
                return getClassGuard(methodType);
            case 2:
                return getAssignableGuard(methodType);
            case 3:
                return Guards.isArray(0, methodType);
            case 4:
                return null;
            default:
                throw new AssertionError();
        }
    }

    private GuardedInvocationComponent getMethodGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List list) {
        MethodType methodTypeChangeReturnType = callSiteDescriptor.getMethodType().changeReturnType(Object.class);
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 2:
                assertParameterCount(callSiteDescriptor, 2);
                GuardedInvocationComponent guardedInvocationComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
                if (guardedInvocationComponent == null || !TypeUtilities.areAssignable(DynamicMethod.class, guardedInvocationComponent.getGuardedInvocation().getInvocation().type().returnType())) {
                    return getClassGuardedInvocationComponent(linkerServices.asType(this.getDynamicMethod, methodTypeChangeReturnType), methodTypeChangeReturnType);
                }
                MethodHandle methodHandleAsType = linkerServices.asType(this.getDynamicMethod, methodTypeChangeReturnType);
                MethodHandle methodHandleAsType2 = linkerServices.asType(MethodHandles.dropArguments(OBJECT_IDENTITY, 1, methodTypeChangeReturnType.parameterList()), methodTypeChangeReturnType.insertParameterTypes(0, Object.class));
                MethodHandle invocation = guardedInvocationComponent.getGuardedInvocation().getInvocation();
                if ($assertionsDisabled || invocation.type().changeReturnType(methodTypeChangeReturnType.returnType()).equals(methodTypeChangeReturnType)) {
                    return guardedInvocationComponent.compose(MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_DYNAMIC_METHOD, methodHandleAsType2, MethodHandles.dropArguments(invocation, 0, (Class<?>[]) new Class[]{Object.class})), methodHandleAsType), getClassGuard(methodTypeChangeReturnType), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
                }
                throw new AssertionError();
            case 3:
                assertParameterCount(callSiteDescriptor, 1);
                DynamicMethod dynamicMethod = getDynamicMethod(callSiteDescriptor.getNameToken(2));
                if (dynamicMethod == null) {
                    return getGuardedInvocationComponent(callSiteDescriptor, linkerServices, list);
                }
                return getClassGuardedInvocationComponent(linkerServices.asType(MethodHandles.dropArguments(MethodHandles.constant(Object.class, dynamicMethod), 0, (Class<?>[]) new Class[]{methodTypeChangeReturnType.parameterType(0)}), methodTypeChangeReturnType), methodTypeChangeReturnType);
            default:
                return null;
        }
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/AbstractJavaLinker$MethodPair.class */
    static class MethodPair {
        final MethodHandle method1;
        final MethodHandle method2;

        MethodPair(MethodHandle methodHandle, MethodHandle methodHandle2) {
            this.method1 = methodHandle;
            this.method2 = methodHandle2;
        }

        MethodHandle guardWithTest(MethodHandle methodHandle) {
            return MethodHandles.guardWithTest(methodHandle, this.method1, this.method2);
        }
    }

    static MethodPair matchReturnTypes(MethodHandle methodHandle, MethodHandle methodHandle2) {
        MethodType methodTypeType = methodHandle.type();
        MethodType methodTypeType2 = methodHandle2.type();
        Class<?> commonLosslessConversionType = TypeUtilities.getCommonLosslessConversionType(methodTypeType.returnType(), methodTypeType2.returnType());
        return new MethodPair(methodHandle.asType(methodTypeType.changeReturnType(commonLosslessConversionType)), methodHandle2.asType(methodTypeType2.changeReturnType(commonLosslessConversionType)));
    }

    private static void assertParameterCount(CallSiteDescriptor callSiteDescriptor, int i) {
        if (callSiteDescriptor.getMethodType().parameterCount() != i) {
            throw new BootstrapMethodError(callSiteDescriptor.getName() + " must have exactly " + i + " parameters.");
        }
    }

    private Object getPropertyGetterHandle(Object obj) {
        return this.propertyGetters.get(String.valueOf(obj));
    }

    private MethodHandle getPropertySetterHandle(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, Object obj) {
        return getDynamicMethodInvocation(callSiteDescriptor, linkerServices, String.valueOf(obj), this.propertySetters);
    }

    private Object getDynamicMethod(Object obj) {
        return getDynamicMethod(String.valueOf(obj), this.methods);
    }

    DynamicMethod getDynamicMethod(String str) {
        return getDynamicMethod(str, this.methods);
    }

    private static Method getMostGenericGetter(Method method) {
        return getMostGenericGetter(method.getName(), method.getReturnType(), method.getDeclaringClass());
    }

    private static Method getMostGenericGetter(String str, Class cls, Class cls2) {
        if (cls2 == null) {
            return null;
        }
        for (Class<?> cls3 : cls2.getInterfaces()) {
            Method mostGenericGetter = getMostGenericGetter(str, cls, cls3);
            if (mostGenericGetter != null) {
                return mostGenericGetter;
            }
        }
        Method mostGenericGetter2 = getMostGenericGetter(str, cls, cls2.getSuperclass());
        if (mostGenericGetter2 != null) {
            return mostGenericGetter2;
        }
        if (!CheckRestrictedPackage.isRestrictedClass(cls2)) {
            try {
                return cls2.getMethod(str, new Class[0]);
            } catch (NoSuchMethodException unused) {
                return null;
            }
        }
        return null;
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/AbstractJavaLinker$AnnotatedDynamicMethod.class */
    private static final class AnnotatedDynamicMethod {
        private final SingleDynamicMethod method;
        final GuardedInvocationComponent.ValidationType validationType;
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !AbstractJavaLinker.class.desiredAssertionStatus();
        }

        AnnotatedDynamicMethod(SingleDynamicMethod singleDynamicMethod, GuardedInvocationComponent.ValidationType validationType) {
            this.method = singleDynamicMethod;
            this.validationType = validationType;
        }

        MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
            return this.method.getInvocation(callSiteDescriptor, linkerServices);
        }

        MethodHandle getTarget(MethodHandles.Lookup lookup, LinkerServices linkerServices) {
            MethodHandle methodHandleFilterInternalObjects = linkerServices.filterInternalObjects(this.method.getTarget(lookup));
            if ($assertionsDisabled || methodHandleFilterInternalObjects != null) {
                return methodHandleFilterInternalObjects;
            }
            throw new AssertionError();
        }
    }
}

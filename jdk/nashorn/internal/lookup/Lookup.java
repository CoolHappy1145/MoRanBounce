package jdk.nashorn.internal.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:jdk/nashorn/internal/lookup/Lookup.class */
public final class Lookup {

    /* renamed from: MH */
    public static final MethodHandleFunctionality f31MH;
    public static final MethodHandle EMPTY_GETTER;
    public static final MethodHandle EMPTY_SETTER;
    public static final MethodHandle TYPE_ERROR_THROWER_GETTER;
    public static final MethodHandle TYPE_ERROR_THROWER_SETTER;
    public static final MethodType GET_OBJECT_TYPE;
    public static final MethodType SET_OBJECT_TYPE;
    public static final MethodType GET_PRIMITIVE_TYPE;
    public static final MethodType SET_PRIMITIVE_TYPE;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Lookup.class.desiredAssertionStatus();
        f31MH = MethodHandleFactory.getFunctionality();
        EMPTY_GETTER = findOwnMH("emptyGetter", Object.class, new Class[]{Object.class});
        EMPTY_SETTER = findOwnMH("emptySetter", Void.TYPE, new Class[]{Object.class, Object.class});
        TYPE_ERROR_THROWER_GETTER = findOwnMH("typeErrorThrowerGetter", Object.class, new Class[]{Object.class});
        TYPE_ERROR_THROWER_SETTER = findOwnMH("typeErrorThrowerSetter", Void.TYPE, new Class[]{Object.class, Object.class});
        GET_OBJECT_TYPE = f31MH.type(Object.class, new Class[]{Object.class});
        SET_OBJECT_TYPE = f31MH.type(Void.TYPE, new Class[]{Object.class, Object.class});
        GET_PRIMITIVE_TYPE = f31MH.type(Long.TYPE, new Class[]{Object.class});
        SET_PRIMITIVE_TYPE = f31MH.type(Void.TYPE, new Class[]{Object.class, Long.TYPE});
    }

    private Lookup() {
    }

    public static Object emptyGetter(Object obj) {
        return ScriptRuntime.UNDEFINED;
    }

    public static MethodHandle emptyGetter(Class cls) {
        return filterReturnType(EMPTY_GETTER, cls);
    }

    public static Object typeErrorThrowerGetter(Object obj) {
        throw ECMAErrors.typeError("strict.getter.setter.poison", new String[]{ScriptRuntime.safeToString(obj)});
    }

    public static void typeErrorThrowerSetter(Object obj, Object obj2) {
        throw ECMAErrors.typeError("strict.getter.setter.poison", new String[]{ScriptRuntime.safeToString(obj)});
    }

    public static MethodHandle filterArgumentType(MethodHandle methodHandle, int i, Class cls) {
        Class<?> clsParameterType = methodHandle.type().parameterType(i);
        if (cls != Integer.TYPE) {
            if (cls == Long.TYPE) {
                if (clsParameterType == Integer.TYPE) {
                    return f31MH.filterArguments(methodHandle, i, new MethodHandle[]{JSType.TO_INT32_L.methodHandle()});
                }
            } else if (cls == Double.TYPE) {
                if (clsParameterType == Integer.TYPE) {
                    return f31MH.filterArguments(methodHandle, i, new MethodHandle[]{JSType.TO_INT32_D.methodHandle()});
                }
                if (clsParameterType == Long.TYPE) {
                    return f31MH.filterArguments(methodHandle, i, new MethodHandle[]{JSType.TO_UINT32_D.methodHandle()});
                }
            } else if (!cls.isPrimitive()) {
                if (clsParameterType == Integer.TYPE) {
                    return f31MH.filterArguments(methodHandle, i, new MethodHandle[]{JSType.TO_INT32.methodHandle()});
                }
                if (clsParameterType == Long.TYPE) {
                    return f31MH.filterArguments(methodHandle, i, new MethodHandle[]{JSType.TO_UINT32.methodHandle()});
                }
                if (clsParameterType == Double.TYPE) {
                    return f31MH.filterArguments(methodHandle, i, new MethodHandle[]{JSType.TO_NUMBER.methodHandle()});
                }
                if (!clsParameterType.isPrimitive()) {
                    return methodHandle;
                }
                if (!$assertionsDisabled) {
                    throw new AssertionError("unsupported Lookup.filterReturnType type " + cls + ActivityStack.GLUE_STRING + clsParameterType);
                }
            }
        }
        return f31MH.explicitCastArguments(methodHandle, methodHandle.type().changeParameterType(i, (Class<?>) cls));
    }

    public static MethodHandle filterReturnType(MethodHandle methodHandle, Class cls) {
        Class<?> clsReturnType = methodHandle.type().returnType();
        if (clsReturnType != Integer.TYPE) {
            if (clsReturnType == Long.TYPE) {
                if (cls == Integer.TYPE) {
                    return f31MH.filterReturnValue(methodHandle, JSType.TO_INT32_L.methodHandle());
                }
            } else if (clsReturnType == Double.TYPE) {
                if (cls == Integer.TYPE) {
                    return f31MH.filterReturnValue(methodHandle, JSType.TO_INT32_D.methodHandle());
                }
                if (cls == Long.TYPE) {
                    return f31MH.filterReturnValue(methodHandle, JSType.TO_UINT32_D.methodHandle());
                }
            } else if (!clsReturnType.isPrimitive()) {
                if (cls == Integer.TYPE) {
                    return f31MH.filterReturnValue(methodHandle, JSType.TO_INT32.methodHandle());
                }
                if (cls == Long.TYPE) {
                    return f31MH.filterReturnValue(methodHandle, JSType.TO_UINT32.methodHandle());
                }
                if (cls == Double.TYPE) {
                    return f31MH.filterReturnValue(methodHandle, JSType.TO_NUMBER.methodHandle());
                }
                if (!cls.isPrimitive()) {
                    return methodHandle;
                }
                if (!$assertionsDisabled) {
                    throw new AssertionError("unsupported Lookup.filterReturnType type " + clsReturnType + ActivityStack.GLUE_STRING + cls);
                }
            }
        }
        return f31MH.explicitCastArguments(methodHandle, methodHandle.type().changeReturnType((Class<?>) cls));
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return f31MH.findStatic(MethodHandles.lookup(), Lookup.class, str, f31MH.type(cls, clsArr));
    }
}

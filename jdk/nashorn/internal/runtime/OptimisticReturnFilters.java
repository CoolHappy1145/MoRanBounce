package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/OptimisticReturnFilters.class */
public final class OptimisticReturnFilters {
    private static final MethodHandle[] ENSURE_INT;
    private static final MethodHandle[] ENSURE_NUMBER;
    private static final int VOID_TYPE_INDEX;
    private static final int BOOLEAN_TYPE_INDEX;
    private static final int CHAR_TYPE_INDEX;
    private static final int LONG_TYPE_INDEX;
    private static final int FLOAT_TYPE_INDEX;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !OptimisticReturnFilters.class.desiredAssertionStatus();
        MethodHandle methodHandleFindOwnMH = findOwnMH("ensureInt", Integer.TYPE, new Class[]{Double.TYPE, Integer.TYPE});
        ENSURE_INT = new MethodHandle[]{null, methodHandleFindOwnMH, findOwnMH("ensureInt", Integer.TYPE, new Class[]{Object.class, Integer.TYPE}), findOwnMH("ensureInt", Integer.TYPE, new Class[]{Integer.TYPE}), findOwnMH("ensureInt", Integer.TYPE, new Class[]{Boolean.TYPE, Integer.TYPE}), findOwnMH("ensureInt", Integer.TYPE, new Class[]{Character.TYPE, Integer.TYPE}), findOwnMH("ensureInt", Integer.TYPE, new Class[]{Long.TYPE, Integer.TYPE}), methodHandleFindOwnMH.asType(methodHandleFindOwnMH.type().changeParameterType(0, Float.TYPE))};
        VOID_TYPE_INDEX = ENSURE_INT.length - 5;
        BOOLEAN_TYPE_INDEX = ENSURE_INT.length - 4;
        CHAR_TYPE_INDEX = ENSURE_INT.length - 3;
        LONG_TYPE_INDEX = ENSURE_INT.length - 2;
        FLOAT_TYPE_INDEX = ENSURE_INT.length - 1;
        ENSURE_NUMBER = new MethodHandle[]{null, null, findOwnMH("ensureNumber", Double.TYPE, new Class[]{Object.class, Integer.TYPE}), ENSURE_INT[VOID_TYPE_INDEX].asType(ENSURE_INT[VOID_TYPE_INDEX].type().changeReturnType(Double.TYPE)), ENSURE_INT[BOOLEAN_TYPE_INDEX].asType(ENSURE_INT[BOOLEAN_TYPE_INDEX].type().changeReturnType(Double.TYPE)), ENSURE_INT[CHAR_TYPE_INDEX].asType(ENSURE_INT[CHAR_TYPE_INDEX].type().changeReturnType(Double.TYPE)), findOwnMH("ensureNumber", Double.TYPE, new Class[]{Long.TYPE, Integer.TYPE}), null};
    }

    public static MethodHandle filterOptimisticReturnValue(MethodHandle methodHandle, Class cls, int i) {
        if (!UnwarrantedOptimismException.isValid(i)) {
            return methodHandle;
        }
        Class<?> clsReturnType = methodHandle.type().returnType();
        if (TypeUtilities.isConvertibleWithoutLoss(clsReturnType, cls)) {
            return methodHandle;
        }
        MethodHandle optimisticTypeGuard = getOptimisticTypeGuard(cls, clsReturnType);
        return optimisticTypeGuard == null ? methodHandle : Lookup.f31MH.filterReturnValue(methodHandle, Lookup.f31MH.insertArguments(optimisticTypeGuard, optimisticTypeGuard.type().parameterCount() - 1, new Object[]{Integer.valueOf(i)}));
    }

    public static GuardedInvocation filterOptimisticReturnValue(GuardedInvocation guardedInvocation, CallSiteDescriptor callSiteDescriptor) {
        if (!NashornCallSiteDescriptor.isOptimistic(callSiteDescriptor)) {
            return guardedInvocation;
        }
        return guardedInvocation.replaceMethods(filterOptimisticReturnValue(guardedInvocation.getInvocation(), callSiteDescriptor.getMethodType().returnType(), NashornCallSiteDescriptor.getProgramPoint(callSiteDescriptor)), guardedInvocation.getGuard());
    }

    private static MethodHandle getOptimisticTypeGuard(Class cls, Class cls2) {
        MethodHandle methodHandle;
        int provableTypeIndex = getProvableTypeIndex(cls2);
        if (cls == Integer.TYPE) {
            methodHandle = ENSURE_INT[provableTypeIndex];
        } else if (cls == Double.TYPE) {
            methodHandle = ENSURE_NUMBER[provableTypeIndex];
        } else {
            methodHandle = null;
            if (!$assertionsDisabled && cls.isPrimitive()) {
                throw new AssertionError(cls + ", " + cls2);
            }
        }
        if (methodHandle != null && !cls2.isPrimitive()) {
            return methodHandle.asType(methodHandle.type().changeParameterType(0, (Class<?>) cls2));
        }
        return methodHandle;
    }

    private static int getProvableTypeIndex(Class cls) {
        int accessorTypeIndex = JSType.getAccessorTypeIndex(cls);
        if (accessorTypeIndex != -1) {
            return accessorTypeIndex;
        }
        if (cls == Boolean.TYPE) {
            return BOOLEAN_TYPE_INDEX;
        }
        if (cls == Void.TYPE) {
            return VOID_TYPE_INDEX;
        }
        if (cls == Byte.TYPE || cls == Short.TYPE) {
            return 0;
        }
        if (cls == Character.TYPE) {
            return CHAR_TYPE_INDEX;
        }
        if (cls == Long.TYPE) {
            return LONG_TYPE_INDEX;
        }
        if (cls == Float.TYPE) {
            return FLOAT_TYPE_INDEX;
        }
        throw new AssertionError(cls.getName());
    }

    private static int ensureInt(long j, int i) {
        if (((long) ((int) j)) == j) {
            return (int) j;
        }
        throw UnwarrantedOptimismException.createNarrowest(Long.valueOf(j), i);
    }

    private static int ensureInt(double d, int i) {
        if (JSType.isStrictlyRepresentableAsInt(d)) {
            return (int) d;
        }
        throw new UnwarrantedOptimismException(Double.valueOf(d), i, Type.NUMBER);
    }

    public static int ensureInt(Object obj, int i) {
        if (isPrimitiveNumberWrapper(obj)) {
            double dDoubleValue = ((Number) obj).doubleValue();
            if (JSType.isStrictlyRepresentableAsInt(dDoubleValue)) {
                return (int) dDoubleValue;
            }
        }
        throw UnwarrantedOptimismException.createNarrowest(obj, i);
    }

    private static boolean isPrimitiveNumberWrapper(Object obj) {
        if (obj == null) {
            return false;
        }
        Class<?> cls = obj.getClass();
        return cls == Integer.class || cls == Double.class || cls == Long.class || cls == Float.class || cls == Short.class || cls == Byte.class;
    }

    private static int ensureInt(boolean z, int i) {
        throw new UnwarrantedOptimismException(Boolean.valueOf(z), i, Type.OBJECT);
    }

    private static int ensureInt(char c, int i) {
        throw new UnwarrantedOptimismException(Character.valueOf(c), i, Type.OBJECT);
    }

    private static int ensureInt(int i) {
        throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, i, Type.OBJECT);
    }

    private static double ensureNumber(long j, int i) {
        if (9007199254740992L >= j && j >= -9007199254740992L) {
            return j;
        }
        throw new UnwarrantedOptimismException(Long.valueOf(j), i, Type.OBJECT);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
    
        if ((9007199254740992L >= r0 && r0 >= -9007199254740992L) != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double ensureNumber(Object obj, int i) {
        if (isPrimitiveNumberWrapper(obj)) {
            if (obj.getClass() == Long.class) {
                long jLongValue = ((Long) obj).longValue();
            }
            return ((Number) obj).doubleValue();
        }
        throw new UnwarrantedOptimismException(obj, i, Type.OBJECT);
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), OptimisticReturnFilters.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}

package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.MethodHandleTransformer;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/DefaultInternalObjectFilter.class */
public class DefaultInternalObjectFilter implements MethodHandleTransformer {
    private static final MethodHandle FILTER_VARARGS;
    private final MethodHandle parameterFilter;
    private final MethodHandle returnFilter;
    private final MethodHandle varArgFilter;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DefaultInternalObjectFilter.class.desiredAssertionStatus();
        FILTER_VARARGS = new Lookup(MethodHandles.lookup()).findStatic(DefaultInternalObjectFilter.class, "filterVarArgs", MethodType.methodType(Object[].class, MethodHandle.class, Object[].class));
    }

    public DefaultInternalObjectFilter(MethodHandle methodHandle, MethodHandle methodHandle2) {
        this.parameterFilter = checkHandle(methodHandle, "parameterFilter");
        this.returnFilter = checkHandle(methodHandle2, "returnFilter");
        this.varArgFilter = methodHandle == null ? null : FILTER_VARARGS.bindTo(methodHandle);
    }

    @Override // jdk.internal.dynalink.linker.MethodHandleTransformer
    public MethodHandle transform(MethodHandle methodHandle) {
        MethodHandle methodHandleFilterArguments;
        if (!$assertionsDisabled && methodHandle == null) {
            throw new AssertionError();
        }
        MethodHandle[] methodHandleArr = null;
        MethodType methodTypeType = methodHandle.type();
        boolean zIsVarargsCollector = methodHandle.isVarargsCollector();
        int iParameterCount = methodTypeType.parameterCount();
        if (this.parameterFilter != null) {
            int i = -1;
            int i2 = 1;
            while (i2 < iParameterCount) {
                Class<?> clsParameterType = methodTypeType.parameterType(i2);
                boolean z = zIsVarargsCollector && i2 == iParameterCount - 1 && clsParameterType == Object[].class;
                if (z || clsParameterType == Object.class) {
                    if (methodHandleArr == null) {
                        i = i2;
                        methodHandleArr = new MethodHandle[iParameterCount - i];
                    }
                    methodHandleArr[i2 - i] = z ? this.varArgFilter : this.parameterFilter;
                }
                i2++;
            }
            methodHandleFilterArguments = methodHandleArr != null ? MethodHandles.filterArguments(methodHandle, i, methodHandleArr) : methodHandle;
        } else {
            methodHandleFilterArguments = methodHandle;
        }
        MethodHandle methodHandleFilterReturnValue = (this.returnFilter == null || methodTypeType.returnType() != Object.class) ? methodHandleFilterArguments : MethodHandles.filterReturnValue(methodHandleFilterArguments, this.returnFilter);
        return (!zIsVarargsCollector || methodHandleFilterReturnValue.isVarargsCollector()) ? methodHandleFilterReturnValue : methodHandleFilterReturnValue.asVarargsCollector(methodTypeType.parameterType(iParameterCount - 1));
    }

    private static MethodHandle checkHandle(MethodHandle methodHandle, String str) {
        if (methodHandle != null) {
            MethodType methodType = MethodType.methodType((Class<?>) Object.class, (Class<?>) Object.class);
            if (!methodHandle.type().equals(methodType)) {
                throw new IllegalArgumentException("Method type for " + str + " must be " + methodType);
            }
        }
        return methodHandle;
    }

    private static Object[] filterVarArgs(MethodHandle methodHandle, Object[] objArr) {
        Object[] objArr2 = null;
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            Object objInvokeExact = (Object) methodHandle.invokeExact(obj);
            if (obj != objInvokeExact) {
                if (objArr2 == null) {
                    objArr2 = (Object[]) objArr.clone();
                }
                objArr2[i] = objInvokeExact;
            }
        }
        return objArr2 == null ? objArr : objArr2;
    }
}

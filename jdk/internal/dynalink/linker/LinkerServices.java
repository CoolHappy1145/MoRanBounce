package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: L-out.jar:jdk/internal/dynalink/linker/LinkerServices.class */
public interface LinkerServices {
    MethodHandle asType(MethodHandle methodHandle, MethodType methodType);

    MethodHandle asTypeLosslessReturn(MethodHandle methodHandle, MethodType methodType);

    MethodHandle getTypeConverter(Class cls, Class cls2);

    boolean canConvert(Class cls, Class cls2);

    GuardedInvocation getGuardedInvocation(LinkRequest linkRequest);

    ConversionComparator.Comparison compareConversion(Class cls, Class cls2, Class cls3);

    MethodHandle filterInternalObjects(MethodHandle methodHandle);

    /* loaded from: L-out.jar:jdk/internal/dynalink/linker/LinkerServices$Implementation.class */
    public static class Implementation {
        public static MethodHandle asTypeLosslessReturn(LinkerServices linkerServices, MethodHandle methodHandle, MethodType methodType) {
            Class<?> clsReturnType = methodHandle.type().returnType();
            return linkerServices.asType(methodHandle, TypeUtilities.isConvertibleWithoutLoss(clsReturnType, methodType.returnType()) ? methodType : methodType.changeReturnType(clsReturnType));
        }
    }
}

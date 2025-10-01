package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/SingleDynamicMethod.class */
abstract class SingleDynamicMethod extends DynamicMethod {
    private static final MethodHandle CAN_CONVERT_TO;
    static final boolean $assertionsDisabled;

    abstract boolean isVarArgs();

    abstract MethodType getMethodType();

    abstract MethodHandle getTarget(MethodHandles.Lookup lookup);

    static {
        $assertionsDisabled = !SingleDynamicMethod.class.desiredAssertionStatus();
        CAN_CONVERT_TO = Lookup.findOwnStatic(MethodHandles.lookup(), "canConvertTo", Boolean.TYPE, new Class[]{LinkerServices.class, Class.class, Object.class});
    }

    SingleDynamicMethod(String str) {
        super(str);
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
        return getInvocation(getTarget(callSiteDescriptor.getLookup()), callSiteDescriptor.getMethodType(), linkerServices);
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    SingleDynamicMethod getMethodForExactParamTypes(String str) {
        if (typeMatchesDescription(str, getMethodType())) {
            return this;
        }
        return null;
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    boolean contains(SingleDynamicMethod singleDynamicMethod) {
        return getMethodType().parameterList().equals(singleDynamicMethod.getMethodType().parameterList());
    }

    static String getMethodNameWithSignature(MethodType methodType, String str, boolean z) {
        String string = methodType.toString();
        int iLastIndexOf = string.lastIndexOf(41) + 1;
        int iIndexOf = string.indexOf(44) + 1;
        if (iIndexOf == 0) {
            iIndexOf = iLastIndexOf - 1;
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append((CharSequence) string, iLastIndexOf, string.length()).append(' ');
        }
        return sb.append(str).append('(').append((CharSequence) string, iIndexOf, iLastIndexOf).toString();
    }

    static MethodHandle getInvocation(MethodHandle methodHandle, MethodType methodType, LinkerServices linkerServices) {
        MethodHandle methodHandleInsertArguments;
        MethodHandle methodHandleFilterInternalObjects = linkerServices.filterInternalObjects(methodHandle);
        MethodType methodTypeType = methodHandleFilterInternalObjects.type();
        int iParameterCount = methodTypeType.parameterCount();
        boolean zIsVarargsCollector = methodHandle.isVarargsCollector();
        MethodHandle methodHandleAsFixedArity = zIsVarargsCollector ? methodHandleFilterInternalObjects.asFixedArity() : methodHandleFilterInternalObjects;
        int i = zIsVarargsCollector ? iParameterCount - 1 : iParameterCount;
        int iParameterCount2 = methodType.parameterCount();
        if (iParameterCount2 < i) {
            return null;
        }
        if (iParameterCount2 == i) {
            if (zIsVarargsCollector) {
                methodHandleInsertArguments = MethodHandles.insertArguments(methodHandleAsFixedArity, i, Array.newInstance(methodTypeType.parameterType(i).getComponentType(), 0));
            } else {
                methodHandleInsertArguments = methodHandleAsFixedArity;
            }
            return createConvertingInvocation(methodHandleInsertArguments, linkerServices, methodType);
        }
        if (!zIsVarargsCollector) {
            return null;
        }
        Class<?> clsParameterType = methodTypeType.parameterType(i);
        if (iParameterCount2 == iParameterCount) {
            Class<?> clsParameterType2 = methodType.parameterType(i);
            if (clsParameterType.isAssignableFrom(clsParameterType2)) {
                return createConvertingInvocation(methodHandleFilterInternalObjects, linkerServices, methodType).asVarargsCollector(clsParameterType2);
            }
            MethodHandle methodHandleCreateConvertingInvocation = createConvertingInvocation(collectArguments(methodHandleAsFixedArity, iParameterCount2), linkerServices, methodType);
            boolean zIsAssignableFrom = clsParameterType2.isAssignableFrom(clsParameterType);
            boolean zCanConvert = linkerServices.canConvert(clsParameterType2, clsParameterType);
            if (!zIsAssignableFrom && !zCanConvert) {
                return methodHandleCreateConvertingInvocation;
            }
            MethodHandle methodHandleGuardWithTest = MethodHandles.guardWithTest(MethodHandles.dropArguments(MethodHandles.insertArguments(CAN_CONVERT_TO, 0, linkerServices, clsParameterType), 0, MethodType.genericMethodType(i).parameterList()).asType(methodType.changeReturnType(Boolean.TYPE)), createConvertingInvocation(MethodHandles.filterArguments(methodHandleAsFixedArity, i, linkerServices.getTypeConverter(clsParameterType2, clsParameterType)), linkerServices, methodType), methodHandleCreateConvertingInvocation);
            if (zIsAssignableFrom) {
                return MethodHandles.guardWithTest(Guards.isInstance(clsParameterType, i, methodType), createConvertingInvocation(methodHandleAsFixedArity, linkerServices, methodType), zCanConvert ? methodHandleGuardWithTest : methodHandleCreateConvertingInvocation);
            }
            if ($assertionsDisabled || zCanConvert) {
                return methodHandleGuardWithTest;
            }
            throw new AssertionError();
        }
        return createConvertingInvocation(collectArguments(methodHandleAsFixedArity, iParameterCount2), linkerServices, methodType);
    }

    private static boolean canConvertTo(LinkerServices linkerServices, Class cls, Object obj) {
        if (obj == null) {
            return false;
        }
        return linkerServices.canConvert(obj.getClass(), cls);
    }

    static MethodHandle collectArguments(MethodHandle methodHandle, int i) {
        MethodType methodTypeType = methodHandle.type();
        int iParameterCount = methodTypeType.parameterCount() - 1;
        return methodHandle.asCollector(methodTypeType.parameterType(iParameterCount), i - iParameterCount);
    }

    private static MethodHandle createConvertingInvocation(MethodHandle methodHandle, LinkerServices linkerServices, MethodType methodType) {
        return linkerServices.asTypeLosslessReturn(methodHandle, methodType);
    }

    private static boolean typeMatchesDescription(String str, MethodType methodType) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ", ");
        for (int i = 1; i < methodType.parameterCount(); i++) {
            if (!stringTokenizer.hasMoreTokens() || !typeNameMatches(stringTokenizer.nextToken(), methodType.parameterType(i))) {
                return false;
            }
        }
        return !stringTokenizer.hasMoreTokens();
    }

    private static boolean typeNameMatches(String str, Class cls) {
        return str.equals(str.indexOf(46) == -1 ? cls.getSimpleName() : cls.getCanonicalName());
    }
}

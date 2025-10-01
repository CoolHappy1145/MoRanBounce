package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/MaximallySpecific.class */
class MaximallySpecific {
    private static final MethodTypeGetter METHOD_HANDLE_TYPE_GETTER;
    private static final MethodTypeGetter DYNAMIC_METHOD_TYPE_GETTER;
    static final boolean $assertionsDisabled;

    MaximallySpecific() {
    }

    static {
        $assertionsDisabled = !MaximallySpecific.class.desiredAssertionStatus();
        METHOD_HANDLE_TYPE_GETTER = new MethodTypeGetter() { // from class: jdk.internal.dynalink.beans.MaximallySpecific.1
            @Override // jdk.internal.dynalink.beans.MaximallySpecific.MethodTypeGetter
            MethodType getMethodType(Object obj) {
                return getMethodType((MethodHandle) obj);
            }

            MethodType getMethodType(MethodHandle methodHandle) {
                return methodHandle.type();
            }
        };
        DYNAMIC_METHOD_TYPE_GETTER = new MethodTypeGetter() { // from class: jdk.internal.dynalink.beans.MaximallySpecific.2
            @Override // jdk.internal.dynalink.beans.MaximallySpecific.MethodTypeGetter
            MethodType getMethodType(Object obj) {
                return getMethodType((SingleDynamicMethod) obj);
            }

            MethodType getMethodType(SingleDynamicMethod singleDynamicMethod) {
                return singleDynamicMethod.getMethodType();
            }
        };
    }

    static List getMaximallySpecificMethods(List list, boolean z) {
        return getMaximallySpecificSingleDynamicMethods(list, z, null, null);
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/MaximallySpecific$MethodTypeGetter.class */
    private static abstract class MethodTypeGetter {
        abstract MethodType getMethodType(Object obj);

        private MethodTypeGetter() {
        }

        MethodTypeGetter(C00131 c00131) {
            this();
        }
    }

    static List getMaximallySpecificMethodHandles(List list, boolean z, Class[] clsArr, LinkerServices linkerServices) {
        return getMaximallySpecificMethods(list, z, clsArr, linkerServices, METHOD_HANDLE_TYPE_GETTER);
    }

    static List getMaximallySpecificSingleDynamicMethods(List list, boolean z, Class[] clsArr, LinkerServices linkerServices) {
        return getMaximallySpecificMethods(list, z, clsArr, linkerServices, DYNAMIC_METHOD_TYPE_GETTER);
    }

    private static List getMaximallySpecificMethods(List list, boolean z, Class[] clsArr, LinkerServices linkerServices, MethodTypeGetter methodTypeGetter) {
        if (list.size() < 2) {
            return list;
        }
        LinkedList linkedList = new LinkedList();
        for (Object obj : list) {
            MethodType methodType = methodTypeGetter.getMethodType(obj);
            boolean z2 = false;
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                switch (C00153.f4xc3cae252[isMoreSpecific(methodType, methodTypeGetter.getMethodType(it.next()), z, clsArr, linkerServices).ordinal()]) {
                    case 1:
                        it.remove();
                        break;
                    case 2:
                        z2 = true;
                        break;
                    case 3:
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            if (!z2) {
                linkedList.addLast(obj);
            }
        }
        return linkedList;
    }

    /* renamed from: jdk.internal.dynalink.beans.MaximallySpecific$3 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/MaximallySpecific$3.class */
    static /* synthetic */ class C00153 {

        /* renamed from: $SwitchMap$jdk$internal$dynalink$linker$ConversionComparator$Comparison */
        static final int[] f4xc3cae252 = new int[ConversionComparator.Comparison.values().length];

        static {
            try {
                f4xc3cae252[ConversionComparator.Comparison.TYPE_1_BETTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4xc3cae252[ConversionComparator.Comparison.TYPE_2_BETTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4xc3cae252[ConversionComparator.Comparison.INDETERMINATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static ConversionComparator.Comparison isMoreSpecific(MethodType methodType, MethodType methodType2, boolean z, Class[] clsArr, LinkerServices linkerServices) {
        int iParameterCount = methodType.parameterCount();
        int iParameterCount2 = methodType2.parameterCount();
        if (!$assertionsDisabled && !z && (iParameterCount != iParameterCount2 || (clsArr != null && clsArr.length != iParameterCount))) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled) {
            if ((clsArr == null) != (linkerServices == null)) {
                throw new AssertionError();
            }
        }
        int iMax = Math.max(Math.max(iParameterCount, iParameterCount2), clsArr == null ? 0 : clsArr.length);
        boolean z2 = false;
        boolean z3 = false;
        for (int i = 1; i < iMax; i++) {
            Class parameterClass = getParameterClass(methodType, iParameterCount, i, z);
            Class parameterClass2 = getParameterClass(methodType2, iParameterCount2, i, z);
            if (parameterClass != parameterClass2) {
                ConversionComparator.Comparison comparisonCompare = compare(parameterClass, parameterClass2, clsArr, i, linkerServices);
                if (comparisonCompare == ConversionComparator.Comparison.TYPE_1_BETTER && !z2) {
                    z2 = true;
                    if (z3) {
                        return ConversionComparator.Comparison.INDETERMINATE;
                    }
                }
                if (comparisonCompare == ConversionComparator.Comparison.TYPE_2_BETTER && !z3) {
                    z3 = true;
                    if (z2) {
                        return ConversionComparator.Comparison.INDETERMINATE;
                    }
                }
            }
        }
        if (z2) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
        }
        if (z3) {
            return ConversionComparator.Comparison.TYPE_2_BETTER;
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static ConversionComparator.Comparison compare(Class cls, Class cls2, Class[] clsArr, int i, LinkerServices linkerServices) {
        ConversionComparator.Comparison comparisonCompareConversion;
        if (linkerServices != null && (comparisonCompareConversion = linkerServices.compareConversion(clsArr[i], cls, cls2)) != ConversionComparator.Comparison.INDETERMINATE) {
            return comparisonCompareConversion;
        }
        if (TypeUtilities.isSubtype(cls, cls2)) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
        }
        if (TypeUtilities.isSubtype(cls2, cls)) {
            return ConversionComparator.Comparison.TYPE_2_BETTER;
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static Class getParameterClass(MethodType methodType, int i, int i2, boolean z) {
        return (!z || i2 < i - 1) ? methodType.parameterType(i2) : methodType.parameterType(i - 1).getComponentType();
    }
}

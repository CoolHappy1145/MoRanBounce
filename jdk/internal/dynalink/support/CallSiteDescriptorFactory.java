package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.WeakHashMap;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/CallSiteDescriptorFactory.class */
public class CallSiteDescriptorFactory {
    private static final WeakHashMap publicDescs = new WeakHashMap();

    private CallSiteDescriptorFactory() {
    }

    public static CallSiteDescriptor create(MethodHandles.Lookup lookup, String str, MethodType methodType) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(methodType);
        Objects.requireNonNull(lookup);
        String[] strArr = tokenizeName(str);
        if (isPublicLookup(lookup)) {
            return getCanonicalPublicDescriptor(createPublicCallSiteDescriptor(strArr, methodType));
        }
        return new LookupCallSiteDescriptor(strArr, methodType, lookup);
    }

    static CallSiteDescriptor getCanonicalPublicDescriptor(CallSiteDescriptor callSiteDescriptor) {
        CallSiteDescriptor callSiteDescriptor2;
        synchronized (publicDescs) {
            Reference reference = (Reference) publicDescs.get(callSiteDescriptor);
            if (reference != null && (callSiteDescriptor2 = (CallSiteDescriptor) reference.get()) != null) {
                return callSiteDescriptor2;
            }
            publicDescs.put(callSiteDescriptor, createReference(callSiteDescriptor));
            return callSiteDescriptor;
        }
    }

    protected static Reference createReference(CallSiteDescriptor callSiteDescriptor) {
        return new WeakReference(callSiteDescriptor);
    }

    private static CallSiteDescriptor createPublicCallSiteDescriptor(String[] strArr, MethodType methodType) {
        int length = strArr.length;
        if (length > 0 && strArr[0] == "dyn") {
            if (length == 2) {
                return new UnnamedDynCallSiteDescriptor(strArr[1], methodType);
            }
            if (length == 3) {
                return new NamedDynCallSiteDescriptor(strArr[1], strArr[2], methodType);
            }
        }
        return new DefaultCallSiteDescriptor(strArr, methodType);
    }

    private static boolean isPublicLookup(MethodHandles.Lookup lookup) {
        return lookup == MethodHandles.publicLookup();
    }

    public static String[] tokenizeName(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, CallSiteDescriptor.TOKEN_DELIMITER);
        String[] strArr = new String[stringTokenizer.countTokens()];
        for (int i = 0; i < strArr.length; i++) {
            String strNextToken = stringTokenizer.nextToken();
            if (i > 1) {
                strNextToken = NameCodec.decode(strNextToken);
            }
            strArr[i] = strNextToken.intern();
        }
        return strArr;
    }

    public static List tokenizeOperators(CallSiteDescriptor callSiteDescriptor) {
        String nameToken = callSiteDescriptor.getNameToken(1);
        StringTokenizer stringTokenizer = new StringTokenizer(nameToken, CallSiteDescriptor.OPERATOR_DELIMITER);
        int iCountTokens = stringTokenizer.countTokens();
        if (iCountTokens == 1) {
            return Collections.singletonList(nameToken);
        }
        String[] strArr = new String[iCountTokens];
        for (int i = 0; i < iCountTokens; i++) {
            strArr[i] = stringTokenizer.nextToken();
        }
        return Arrays.asList(strArr);
    }

    public static CallSiteDescriptor dropParameterTypes(CallSiteDescriptor callSiteDescriptor, int i, int i2) {
        return callSiteDescriptor.changeMethodType(callSiteDescriptor.getMethodType().dropParameterTypes(i, i2));
    }

    public static CallSiteDescriptor changeParameterType(CallSiteDescriptor callSiteDescriptor, int i, Class cls) {
        return callSiteDescriptor.changeMethodType(callSiteDescriptor.getMethodType().changeParameterType(i, (Class<?>) cls));
    }

    public static CallSiteDescriptor changeReturnType(CallSiteDescriptor callSiteDescriptor, Class cls) {
        return callSiteDescriptor.changeMethodType(callSiteDescriptor.getMethodType().changeReturnType((Class<?>) cls));
    }

    public static CallSiteDescriptor insertParameterTypes(CallSiteDescriptor callSiteDescriptor, int i, Class[] clsArr) {
        return callSiteDescriptor.changeMethodType(callSiteDescriptor.getMethodType().insertParameterTypes(i, (Class<?>[]) clsArr));
    }

    public static CallSiteDescriptor insertParameterTypes(CallSiteDescriptor callSiteDescriptor, int i, List list) {
        return callSiteDescriptor.changeMethodType(callSiteDescriptor.getMethodType().insertParameterTypes(i, (List<Class<?>>) list));
    }
}

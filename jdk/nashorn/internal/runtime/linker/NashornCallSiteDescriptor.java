package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.support.AbstractCallSiteDescriptor;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornCallSiteDescriptor.class */
public final class NashornCallSiteDescriptor extends AbstractCallSiteDescriptor {
    public static final int CALLSITE_SCOPE = 1;
    public static final int CALLSITE_STRICT = 2;
    public static final int CALLSITE_FAST_SCOPE = 4;
    public static final int CALLSITE_OPTIMISTIC = 8;
    public static final int CALLSITE_APPLY_TO_CALL = 16;
    public static final int CALLSITE_DECLARE = 32;
    public static final int CALLSITE_PROFILE = 64;
    public static final int CALLSITE_TRACE = 128;
    public static final int CALLSITE_TRACE_MISSES = 256;
    public static final int CALLSITE_TRACE_ENTEREXIT = 512;
    public static final int CALLSITE_TRACE_VALUES = 1024;
    public static final int CALLSITE_PROGRAM_POINT_SHIFT = 11;
    public static final int MAX_PROGRAM_POINT_VALUE = 2097151;
    public static final int FLAGS_MASK = 2047;
    private static final ClassValue canonicals;
    private final MethodHandles.Lookup lookup;
    private final String operator;
    private final String operand;
    private final MethodType methodType;
    private final int flags;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornCallSiteDescriptor.class.desiredAssertionStatus();
        canonicals = new ClassValue() { // from class: jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor.1
            @Override // java.lang.ClassValue
            protected Object computeValue(Class cls) {
                return computeValue(cls);
            }

            @Override // java.lang.ClassValue
            protected ConcurrentMap computeValue(Class cls) {
                return new ConcurrentHashMap();
            }
        };
    }

    public static String toString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            if ((i & 4) != 0) {
                sb.append("fastscope ");
            } else {
                if (!$assertionsDisabled && (i & 4) != 0) {
                    throw new AssertionError("can't be fastscope without scope");
                }
                sb.append("scope ");
            }
            if ((i & 32) != 0) {
                sb.append("declare ");
            }
        }
        if ((i & 16) != 0) {
            sb.append("apply2call ");
        }
        if ((i & 2) != 0) {
            sb.append("strict ");
        }
        return sb.length() == 0 ? "" : " " + sb.toString().trim();
    }

    public static NashornCallSiteDescriptor get(MethodHandles.Lookup lookup, String str, MethodType methodType, int i) {
        String[] strArr = CallSiteDescriptorFactory.tokenizeName(str);
        if (!$assertionsDisabled && strArr.length < 2) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !"dyn".equals(strArr[0])) {
            throw new AssertionError();
        }
        if ($assertionsDisabled || strArr[1] != null) {
            return get(lookup, strArr[1], strArr.length == 3 ? strArr[2].intern() : null, methodType, i);
        }
        throw new AssertionError();
    }

    private static NashornCallSiteDescriptor get(MethodHandles.Lookup lookup, String str, String str2, MethodType methodType, int i) {
        NashornCallSiteDescriptor nashornCallSiteDescriptor = new NashornCallSiteDescriptor(lookup, str, str2, methodType, i);
        NashornCallSiteDescriptor nashornCallSiteDescriptor2 = (NashornCallSiteDescriptor) ((ConcurrentMap) canonicals.get(lookup.lookupClass())).putIfAbsent(nashornCallSiteDescriptor, nashornCallSiteDescriptor);
        return nashornCallSiteDescriptor2 != null ? nashornCallSiteDescriptor2 : nashornCallSiteDescriptor;
    }

    private NashornCallSiteDescriptor(MethodHandles.Lookup lookup, String str, String str2, MethodType methodType, int i) {
        this.lookup = lookup;
        this.operator = str;
        this.operand = str2;
        this.methodType = methodType;
        this.flags = i;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public int getNameTokenCount() {
        return this.operand == null ? 2 : 3;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getNameToken(int i) {
        switch (i) {
            case 0:
                return "dyn";
            case 1:
                return this.operator;
            case 2:
                if (this.operand != null) {
                    return this.operand;
                }
                break;
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    @Override // jdk.internal.dynalink.support.AbstractCallSiteDescriptor, jdk.internal.dynalink.CallSiteDescriptor
    public MethodHandles.Lookup getLookup() {
        return this.lookup;
    }

    @Override // jdk.internal.dynalink.support.AbstractCallSiteDescriptor
    public boolean equals(CallSiteDescriptor callSiteDescriptor) {
        return super.equals(callSiteDescriptor) && this.flags == getFlags(callSiteDescriptor);
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodType getMethodType() {
        return this.methodType;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getFirstOperator() {
        int iIndexOf = this.operator.indexOf(CallSiteDescriptor.OPERATOR_DELIMITER);
        return iIndexOf == -1 ? this.operator : this.operator.substring(0, iIndexOf);
    }

    public String getOperand() {
        return this.operand;
    }

    public String getFunctionDescription() {
        if (!$assertionsDisabled && !getFirstOperator().equals("call") && !getFirstOperator().equals("new")) {
            throw new AssertionError();
        }
        if (getNameTokenCount() > 2) {
            return getNameToken(2);
        }
        return null;
    }

    public static String getFunctionDescription(CallSiteDescriptor callSiteDescriptor) {
        if (callSiteDescriptor instanceof NashornCallSiteDescriptor) {
            return ((NashornCallSiteDescriptor) callSiteDescriptor).getFunctionDescription();
        }
        return null;
    }

    public String getFunctionErrorMessage(Object obj) {
        String functionDescription = getFunctionDescription();
        return functionDescription != null ? functionDescription : ScriptRuntime.safeToString(obj);
    }

    public static String getFunctionErrorMessage(CallSiteDescriptor callSiteDescriptor, Object obj) {
        if (callSiteDescriptor instanceof NashornCallSiteDescriptor) {
            return ((NashornCallSiteDescriptor) callSiteDescriptor).getFunctionErrorMessage(obj);
        }
        return ScriptRuntime.safeToString(obj);
    }

    public static int getFlags(CallSiteDescriptor callSiteDescriptor) {
        if (callSiteDescriptor instanceof NashornCallSiteDescriptor) {
            return ((NashornCallSiteDescriptor) callSiteDescriptor).flags;
        }
        return 0;
    }

    private boolean isFlag(int i) {
        return (this.flags & i) != 0;
    }

    private static boolean isFlag(CallSiteDescriptor callSiteDescriptor, int i) {
        return (getFlags(callSiteDescriptor) & i) != 0;
    }

    public static boolean isScope(CallSiteDescriptor callSiteDescriptor) {
        return isFlag(callSiteDescriptor, 1);
    }

    public static boolean isFastScope(CallSiteDescriptor callSiteDescriptor) {
        return isFlag(callSiteDescriptor, 4);
    }

    public static boolean isStrict(CallSiteDescriptor callSiteDescriptor) {
        return isFlag(callSiteDescriptor, 2);
    }

    public static boolean isApplyToCall(CallSiteDescriptor callSiteDescriptor) {
        return isFlag(callSiteDescriptor, 16);
    }

    public static boolean isOptimistic(CallSiteDescriptor callSiteDescriptor) {
        return isFlag(callSiteDescriptor, 8);
    }

    public static boolean isDeclaration(CallSiteDescriptor callSiteDescriptor) {
        return isFlag(callSiteDescriptor, 32);
    }

    public static int getProgramPoint(CallSiteDescriptor callSiteDescriptor) {
        if ($assertionsDisabled || isOptimistic(callSiteDescriptor)) {
            return getFlags(callSiteDescriptor) >> 11;
        }
        throw new AssertionError("program point requested from non-optimistic descriptor " + callSiteDescriptor);
    }

    boolean isProfile() {
        return isFlag(64);
    }

    boolean isTrace() {
        return isFlag(128);
    }

    boolean isTraceMisses() {
        return isFlag(256);
    }

    boolean isTraceEnterExit() {
        return isFlag(512);
    }

    boolean isTraceObjects() {
        return isFlag(1024);
    }

    boolean isOptimistic() {
        return isFlag(8);
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType methodType) {
        return get(getLookup(), this.operator, this.operand, methodType, this.flags);
    }
}

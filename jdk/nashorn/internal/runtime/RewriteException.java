package jdk.nashorn.internal.runtime;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.Global;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/RewriteException.class */
public final class RewriteException extends Exception {

    /* renamed from: MH */
    private static final MethodHandleFunctionality f51MH;
    private ScriptObject runtimeScope;
    private Object[] byteCodeSlots;
    private final int[] previousContinuationEntryPoints;
    public static final CompilerConstants.Call GET_BYTECODE_SLOTS;
    public static final CompilerConstants.Call GET_PROGRAM_POINT;
    public static final CompilerConstants.Call GET_RETURN_VALUE;
    public static final CompilerConstants.Call BOOTSTRAP;
    private static final CompilerConstants.Call POPULATE_ARRAY;
    public static final CompilerConstants.Call TO_LONG_ARRAY;
    public static final CompilerConstants.Call TO_DOUBLE_ARRAY;
    public static final CompilerConstants.Call TO_OBJECT_ARRAY;
    public static final CompilerConstants.Call INSTANCE_OR_NULL;
    public static final CompilerConstants.Call ASSERT_ARRAY_LENGTH;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RewriteException.class.desiredAssertionStatus();
        f51MH = MethodHandleFactory.getFunctionality();
        GET_BYTECODE_SLOTS = CompilerConstants.virtualCallNoLookup(RewriteException.class, "getByteCodeSlots", Object[].class, new Class[0]);
        GET_PROGRAM_POINT = CompilerConstants.virtualCallNoLookup(RewriteException.class, "getProgramPoint", Integer.TYPE, new Class[0]);
        GET_RETURN_VALUE = CompilerConstants.virtualCallNoLookup(RewriteException.class, "getReturnValueDestructive", Object.class, new Class[0]);
        BOOTSTRAP = CompilerConstants.staticCallNoLookup(RewriteException.class, "populateArrayBootstrap", CallSite.class, new Class[]{MethodHandles.Lookup.class, String.class, MethodType.class, Integer.TYPE});
        POPULATE_ARRAY = CompilerConstants.staticCall(MethodHandles.lookup(), RewriteException.class, "populateArray", Object[].class, new Class[]{Object[].class, Integer.TYPE, Object[].class});
        TO_LONG_ARRAY = CompilerConstants.staticCallNoLookup(RewriteException.class, "toLongArray", long[].class, new Class[]{Object.class, RewriteException.class});
        TO_DOUBLE_ARRAY = CompilerConstants.staticCallNoLookup(RewriteException.class, "toDoubleArray", double[].class, new Class[]{Object.class, RewriteException.class});
        TO_OBJECT_ARRAY = CompilerConstants.staticCallNoLookup(RewriteException.class, "toObjectArray", Object[].class, new Class[]{Object.class, RewriteException.class});
        INSTANCE_OR_NULL = CompilerConstants.staticCallNoLookup(RewriteException.class, "instanceOrNull", Object.class, new Class[]{Object.class, Class.class});
        ASSERT_ARRAY_LENGTH = CompilerConstants.staticCallNoLookup(RewriteException.class, "assertArrayLength", Void.TYPE, new Class[]{Object[].class, Integer.TYPE});
    }

    private RewriteException(UnwarrantedOptimismException unwarrantedOptimismException, Object[] objArr, String[] strArr, int[] iArr) {
        super("", unwarrantedOptimismException, false, Context.DEBUG);
        this.byteCodeSlots = objArr;
        this.runtimeScope = mergeSlotsWithScope(objArr, strArr);
        this.previousContinuationEntryPoints = iArr;
    }

    public static RewriteException create(UnwarrantedOptimismException unwarrantedOptimismException, Object[] objArr, String[] strArr) {
        return create(unwarrantedOptimismException, objArr, strArr, null);
    }

    public static RewriteException create(UnwarrantedOptimismException unwarrantedOptimismException, Object[] objArr, String[] strArr, int[] iArr) {
        return new RewriteException(unwarrantedOptimismException, objArr, strArr, iArr);
    }

    public static CallSite populateArrayBootstrap(MethodHandles.Lookup lookup, String str, MethodType methodType, int i) {
        return new ConstantCallSite(f51MH.asType(f51MH.asCollector(f51MH.insertArguments(POPULATE_ARRAY.methodHandle(), 1, new Object[]{Integer.valueOf(i)}), Object[].class, methodType.parameterCount() - 1), methodType));
    }

    private static ScriptObject mergeSlotsWithScope(Object[] objArr, String[] strArr) {
        ScriptObject scriptObjectNewEmptyInstance = Global.newEmptyInstance();
        int iMin = Math.min(objArr.length, strArr.length);
        ScriptObject scriptObject = null;
        String strSymbolName = CompilerConstants.SCOPE.symbolName();
        for (int i = 0; i < iMin; i++) {
            String str = strArr[i];
            Object obj = objArr[i];
            if (strSymbolName.equals(str)) {
                if (!$assertionsDisabled && scriptObject != null) {
                    throw new AssertionError();
                }
                scriptObject = (ScriptObject) obj;
            } else if (str != null) {
                scriptObjectNewEmptyInstance.set(str, obj, 2);
            }
        }
        scriptObjectNewEmptyInstance.setProto(scriptObject);
        return scriptObjectNewEmptyInstance;
    }

    public static Object[] populateArray(Object[] objArr, int i, Object[] objArr2) {
        System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
        return objArr;
    }

    public static long[] toLongArray(Object obj, RewriteException rewriteException) {
        if (obj instanceof long[]) {
            return (long[]) obj;
        }
        if (!$assertionsDisabled && !(obj instanceof int[])) {
            throw new AssertionError();
        }
        int[] iArr = (int[]) obj;
        long[] jArr = new long[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            jArr[i] = iArr[i];
        }
        return (long[]) rewriteException.replaceByteCodeValue(iArr, jArr);
    }

    public static double[] toDoubleArray(Object obj, RewriteException rewriteException) {
        if (obj instanceof double[]) {
            return (double[]) obj;
        }
        if (!$assertionsDisabled && !(obj instanceof int[]) && !(obj instanceof long[])) {
            throw new AssertionError();
        }
        int length = Array.getLength(obj);
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = Array.getDouble(obj, i);
        }
        return (double[]) rewriteException.replaceByteCodeValue(obj, dArr);
    }

    public static Object[] toObjectArray(Object obj, RewriteException rewriteException) {
        if (obj instanceof Object[]) {
            return (Object[]) obj;
        }
        if (!$assertionsDisabled && !(obj instanceof int[]) && !(obj instanceof long[]) && !(obj instanceof double[])) {
            throw new AssertionError(obj + " is " + obj.getClass().getName());
        }
        int length = Array.getLength(obj);
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            objArr[i] = Array.get(obj, i);
        }
        return (Object[]) rewriteException.replaceByteCodeValue(obj, objArr);
    }

    public static Object instanceOrNull(Object obj, Class cls) {
        if (cls.isInstance(obj)) {
            return obj;
        }
        return null;
    }

    public static void assertArrayLength(Object[] objArr, int i) {
        int length = objArr.length;
        do {
            int i2 = length;
            length--;
            if (i2 <= i) {
                return;
            }
        } while (objArr[length] == ScriptRuntime.UNDEFINED);
        throw new AssertionError(String.format("Expected array length %d, but it is %d", Integer.valueOf(i), Integer.valueOf(length + 1)));
    }

    private Object replaceByteCodeValue(Object obj, Object obj2) {
        for (int i = 0; i < this.byteCodeSlots.length; i++) {
            if (this.byteCodeSlots[i] == obj) {
                this.byteCodeSlots[i] = obj2;
            }
        }
        return obj2;
    }

    private UnwarrantedOptimismException getUOE() {
        return (UnwarrantedOptimismException) getCause();
    }

    public Object getReturnValueDestructive() {
        if (!$assertionsDisabled && this.byteCodeSlots == null) {
            throw new AssertionError();
        }
        this.byteCodeSlots = null;
        this.runtimeScope = null;
        return getUOE().getReturnValueDestructive();
    }

    Object getReturnValueNonDestructive() {
        return getUOE().getReturnValueNonDestructive();
    }

    public Type getReturnType() {
        return getUOE().getReturnType();
    }

    public int getProgramPoint() {
        return getUOE().getProgramPoint();
    }

    public Object[] getByteCodeSlots() {
        if (this.byteCodeSlots == null) {
            return null;
        }
        return (Object[]) this.byteCodeSlots.clone();
    }

    public int[] getPreviousContinuationEntryPoints() {
        if (this.previousContinuationEntryPoints == null) {
            return null;
        }
        return (int[]) this.previousContinuationEntryPoints.clone();
    }

    public ScriptObject getRuntimeScope() {
        return this.runtimeScope;
    }

    private static String stringify(Object obj) {
        if (obj == null) {
            return Configurator.NULL;
        }
        String string = obj.toString();
        if (obj instanceof String) {
            string = '\'' + string + '\'';
        } else if (obj instanceof Double) {
            string = string + 'd';
        } else if (obj instanceof Long) {
            string = string + 'l';
        }
        return string;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return getMessage(false);
    }

    public String getMessageShort() {
        return getMessage(true);
    }

    private String getMessage(boolean z) {
        Object[] objArr;
        StringBuilder sb = new StringBuilder();
        sb.append("[pp=").append(getProgramPoint()).append(", ");
        if (!z && (objArr = this.byteCodeSlots) != null) {
            sb.append("slots=").append(Arrays.asList(objArr)).append(", ");
        }
        sb.append("type=").append(getReturnType()).append(", ");
        sb.append("value=").append(stringify(getReturnValueNonDestructive())).append(")]");
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException(getClass().getName());
    }

    private void readObject(ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException(getClass().getName());
    }
}

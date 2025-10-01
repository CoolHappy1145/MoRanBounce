package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import kotlin.jvm.internal.CharCompanionObject;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/JSType.class */
public enum JSType {
    UNDEFINED("undefined"),
    NULL("object"),
    BOOLEAN("boolean"),
    NUMBER("number"),
    STRING("string"),
    OBJECT("object"),
    FUNCTION("function");

    private final String typeName;
    public static final long MAX_UINT = 4294967295L;
    private static final MethodHandles.Lookup JSTYPE_LOOKUP;
    public static final CompilerConstants.Call TO_BOOLEAN;
    public static final CompilerConstants.Call TO_BOOLEAN_D;
    public static final CompilerConstants.Call TO_INTEGER;
    public static final CompilerConstants.Call TO_LONG;
    public static final CompilerConstants.Call TO_LONG_D;
    public static final CompilerConstants.Call TO_NUMBER;
    public static final CompilerConstants.Call TO_NUMBER_OPTIMISTIC;
    public static final CompilerConstants.Call TO_STRING;
    public static final CompilerConstants.Call TO_INT32;
    public static final CompilerConstants.Call TO_INT32_L;
    public static final CompilerConstants.Call TO_INT32_OPTIMISTIC;
    public static final CompilerConstants.Call TO_INT32_D;
    public static final CompilerConstants.Call TO_UINT32_OPTIMISTIC;
    public static final CompilerConstants.Call TO_UINT32_DOUBLE;
    public static final CompilerConstants.Call TO_UINT32;
    public static final CompilerConstants.Call TO_UINT32_D;
    public static final CompilerConstants.Call TO_STRING_D;
    public static final CompilerConstants.Call TO_PRIMITIVE_TO_STRING;
    public static final CompilerConstants.Call TO_PRIMITIVE_TO_CHARSEQUENCE;
    public static final CompilerConstants.Call THROW_UNWARRANTED;
    public static final CompilerConstants.Call ADD_EXACT;
    public static final CompilerConstants.Call SUB_EXACT;
    public static final CompilerConstants.Call MUL_EXACT;
    public static final CompilerConstants.Call DIV_EXACT;
    public static final CompilerConstants.Call DIV_ZERO;
    public static final CompilerConstants.Call REM_ZERO;
    public static final CompilerConstants.Call REM_EXACT;
    public static final CompilerConstants.Call DECREMENT_EXACT;
    public static final CompilerConstants.Call INCREMENT_EXACT;
    public static final CompilerConstants.Call NEGATE_EXACT;
    public static final CompilerConstants.Call TO_JAVA_ARRAY;
    public static final CompilerConstants.Call VOID_RETURN;
    public static final CompilerConstants.Call IS_STRING;
    public static final CompilerConstants.Call IS_NUMBER;
    private static final List ACCESSOR_TYPES;
    public static final int TYPE_UNDEFINED_INDEX = -1;
    public static final int TYPE_INT_INDEX = 0;
    public static final int TYPE_DOUBLE_INDEX = 1;
    public static final int TYPE_OBJECT_INDEX = 2;
    public static final List CONVERT_OBJECT;
    public static final List CONVERT_OBJECT_OPTIMISTIC;
    public static final int UNDEFINED_INT = 0;
    public static final long UNDEFINED_LONG = 0;
    public static final double UNDEFINED_DOUBLE = Double.NaN;
    private static final long MAX_PRECISE_DOUBLE = 9007199254740992L;
    private static final long MIN_PRECISE_DOUBLE = -9007199254740992L;
    public static final List GET_UNDEFINED;
    private static final double INT32_LIMIT = 4.294967296E9d;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JSType.class.desiredAssertionStatus();
        JSTYPE_LOOKUP = MethodHandles.lookup();
        TO_BOOLEAN = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toBoolean", Boolean.TYPE, new Class[]{Object.class});
        TO_BOOLEAN_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toBoolean", Boolean.TYPE, new Class[]{Double.TYPE});
        TO_INTEGER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInteger", Integer.TYPE, new Class[]{Object.class});
        TO_LONG = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toLong", Long.TYPE, new Class[]{Object.class});
        TO_LONG_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toLong", Long.TYPE, new Class[]{Double.TYPE});
        TO_NUMBER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toNumber", Double.TYPE, new Class[]{Object.class});
        TO_NUMBER_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toNumberOptimistic", Double.TYPE, new Class[]{Object.class, Integer.TYPE});
        TO_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toString", String.class, new Class[]{Object.class});
        TO_INT32 = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, new Class[]{Object.class});
        TO_INT32_L = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, new Class[]{Long.TYPE});
        TO_INT32_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32Optimistic", Integer.TYPE, new Class[]{Object.class, Integer.TYPE});
        TO_INT32_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, new Class[]{Double.TYPE});
        TO_UINT32_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32Optimistic", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE});
        TO_UINT32_DOUBLE = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32Double", Double.TYPE, new Class[]{Integer.TYPE});
        TO_UINT32 = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32", Long.TYPE, new Class[]{Object.class});
        TO_UINT32_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32", Long.TYPE, new Class[]{Double.TYPE});
        TO_STRING_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toString", String.class, new Class[]{Double.TYPE});
        TO_PRIMITIVE_TO_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toPrimitiveToString", String.class, new Class[]{Object.class});
        TO_PRIMITIVE_TO_CHARSEQUENCE = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toPrimitiveToCharSequence", CharSequence.class, new Class[]{Object.class});
        THROW_UNWARRANTED = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "throwUnwarrantedOptimismException", Object.class, new Class[]{Object.class, Integer.TYPE});
        ADD_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "addExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
        SUB_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "subExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
        MUL_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "mulExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
        DIV_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "divExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
        DIV_ZERO = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "divZero", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE});
        REM_ZERO = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "remZero", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE});
        REM_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "remExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
        DECREMENT_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "decrementExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE});
        INCREMENT_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "incrementExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE});
        NEGATE_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "negateExact", Integer.TYPE, new Class[]{Integer.TYPE, Integer.TYPE});
        TO_JAVA_ARRAY = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toJavaArray", Object.class, new Class[]{Object.class, Class.class});
        VOID_RETURN = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "voidReturn", Void.TYPE, new Class[0]);
        IS_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "isString", Boolean.TYPE, new Class[]{Object.class});
        IS_NUMBER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "isNumber", Boolean.TYPE, new Class[]{Object.class});
        ACCESSOR_TYPES = Collections.unmodifiableList(Arrays.asList(Type.INT, Type.NUMBER, Type.OBJECT));
        CONVERT_OBJECT = toUnmodifiableList(new MethodHandle[]{TO_INT32.methodHandle(), TO_NUMBER.methodHandle(), null});
        CONVERT_OBJECT_OPTIMISTIC = toUnmodifiableList(new MethodHandle[]{TO_INT32_OPTIMISTIC.methodHandle(), TO_NUMBER_OPTIMISTIC.methodHandle(), null});
        GET_UNDEFINED = toUnmodifiableList(new MethodHandle[]{Lookup.f31MH.constant(Integer.TYPE, 0), Lookup.f31MH.constant(Double.TYPE, Double.valueOf(Double.NaN)), Lookup.f31MH.constant(Object.class, Undefined.getUndefined())});
    }

    JSType(String str) {
        this.typeName = str;
    }

    public final String typeName() {
        return this.typeName;
    }

    /* renamed from: of */
    public static JSType m12of(Object obj) {
        if (obj == null) {
            return NULL;
        }
        if (obj instanceof ScriptObject) {
            return obj instanceof ScriptFunction ? FUNCTION : OBJECT;
        }
        if (obj instanceof Boolean) {
            return BOOLEAN;
        }
        if ((obj instanceof String) || (obj instanceof ConsString)) {
            return STRING;
        }
        if (isNumber(obj)) {
            return NUMBER;
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return UNDEFINED;
        }
        return Bootstrap.isCallable(obj) ? FUNCTION : OBJECT;
    }

    public static JSType ofNoFunction(Object obj) {
        if (obj == null) {
            return NULL;
        }
        if (obj instanceof ScriptObject) {
            return OBJECT;
        }
        if (obj instanceof Boolean) {
            return BOOLEAN;
        }
        if ((obj instanceof String) || (obj instanceof ConsString)) {
            return STRING;
        }
        if (isNumber(obj)) {
            return NUMBER;
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return UNDEFINED;
        }
        return OBJECT;
    }

    public static boolean isStrictlyRepresentableAsInt(double d) {
        return ((((double) ((int) d)) > d ? 1 : (((double) ((int) d)) == d ? 0 : -1)) == 0) && isNotNegativeZero(d);
    }

    public static boolean isRepresentableAsInt(Object obj) {
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            return ((double) ((int) dDoubleValue)) == dDoubleValue;
        }
        return false;
    }

    private static boolean isNotNegativeZero(double d) {
        return Double.doubleToRawLongBits(d) != Long.MIN_VALUE;
    }

    public static boolean isPrimitive(Object obj) {
        if (obj != null && obj != ScriptRuntime.UNDEFINED) {
            if (!((obj instanceof String) || (obj instanceof ConsString)) && !isNumber(obj) && !(obj instanceof Boolean)) {
                return false;
            }
        }
        return true;
    }

    public static Object toPrimitive(Object obj) {
        return toPrimitive(obj, (Class) null);
    }

    public static Object toPrimitive(Object obj, Class cls) {
        if (obj instanceof ScriptObject) {
            return toPrimitive((ScriptObject) obj, cls);
        }
        if (isPrimitive(obj)) {
            return obj;
        }
        if (cls == Number.class && (obj instanceof Number)) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        if (obj instanceof JSObject) {
            return toPrimitive((JSObject) obj, cls);
        }
        if (obj instanceof StaticClass) {
            String name = ((StaticClass) obj).getRepresentedClass().getName();
            return new StringBuilder(12 + name.length()).append("[JavaClass ").append(name).append(']').toString();
        }
        return obj.toString();
    }

    private static Object toPrimitive(ScriptObject scriptObject, Class cls) {
        return requirePrimitive(scriptObject.getDefaultValue(cls));
    }

    private static Object requirePrimitive(Object obj) {
        if (!isPrimitive(obj)) {
            throw ECMAErrors.typeError("bad.default.value", new String[]{obj.toString()});
        }
        return obj;
    }

    public static Object toPrimitive(JSObject jSObject, Class cls) {
        try {
            return requirePrimitive(AbstractJSObject.getDefaultValue(jSObject, cls));
        } catch (UnsupportedOperationException e) {
            throw new ECMAException(Context.getGlobal().newTypeError(e.getMessage()), e);
        }
    }

    public static String toPrimitiveToString(Object obj) {
        return toString(toPrimitive(obj));
    }

    public static CharSequence toPrimitiveToCharSequence(Object obj) {
        return toCharSequence(toPrimitive(obj));
    }

    public static boolean toBoolean(double d) {
        return (d == 0.0d || Double.isNaN(d)) ? false : true;
    }

    public static boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (nullOrUndefined(obj)) {
            return false;
        }
        if (!(obj instanceof Number)) {
            return !((obj instanceof String) || (obj instanceof ConsString)) || ((CharSequence) obj).length() > 0;
        }
        double dDoubleValue = ((Number) obj).doubleValue();
        return (dDoubleValue == 0.0d || Double.isNaN(dDoubleValue)) ? false : true;
    }

    public static String toString(Object obj) {
        return toStringImpl(obj, false);
    }

    public static CharSequence toCharSequence(Object obj) {
        if (obj instanceof ConsString) {
            return (CharSequence) obj;
        }
        return toString(obj);
    }

    public static boolean isNumber(Object obj) {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            return cls == Integer.class || cls == Double.class || cls == Float.class || cls == Short.class || cls == Byte.class;
        }
        return false;
    }

    public static String toString(int i) {
        return Integer.toString(i);
    }

    public static String toString(double d) {
        if (((double) ((int) d)) == d) {
            return Integer.toString((int) d);
        }
        if (d == Double.POSITIVE_INFINITY) {
            return "Infinity";
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return "-Infinity";
        }
        if (Double.isNaN(d)) {
            return "NaN";
        }
        return NumberToString.stringFor(d);
    }

    public static String toString(double d, int i) {
        if (!$assertionsDisabled && (i < 2 || i > 36)) {
            throw new AssertionError("invalid radix");
        }
        if (((double) ((int) d)) == d) {
            return Integer.toString((int) d, i);
        }
        if (d == Double.POSITIVE_INFINITY) {
            return "Infinity";
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return "-Infinity";
        }
        if (Double.isNaN(d)) {
            return "NaN";
        }
        if (d == 0.0d) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = d < 0.0d;
        double d2 = z ? -d : d;
        double dFloor = Math.floor(d2);
        double d3 = d2 - dFloor;
        do {
            double d4 = dFloor % i;
            sb.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((int) d4));
            dFloor = (dFloor - d4) / i;
        } while (dFloor >= 1.0d);
        if (z) {
            sb.append('-');
        }
        sb.reverse();
        if (d3 > 0.0d) {
            int length = sb.length();
            sb.append('.');
            do {
                double d5 = d3 * i;
                double dFloor2 = Math.floor(d5);
                sb.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((int) dFloor2));
                d3 = d5 - dFloor2;
                if (d3 <= 0.0d) {
                    break;
                }
            } while (sb.length() - length < 1100);
        }
        return sb.toString();
    }

    public static double toNumber(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue();
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return toNumberGeneric(obj);
    }

    public static double toNumberForEq(Object obj) {
        if (obj == null) {
            return Double.NaN;
        }
        return toNumber(obj);
    }

    public static double toNumberForStrictEq(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue();
        }
        if (isNumber(obj)) {
            return ((Number) obj).doubleValue();
        }
        return Double.NaN;
    }

    public static Number toNarrowestNumber(long j) {
        return Double.valueOf((((long) ((int) j)) > j ? 1 : (((long) ((int) j)) == j ? 0 : -1)) == 0 ? Integer.valueOf((int) j).intValue() : Double.valueOf(j).doubleValue());
    }

    public static double toNumber(Boolean bool) {
        return bool.booleanValue() ? 1.0d : 0.0d;
    }

    public static double toNumber(ScriptObject scriptObject) {
        return toNumber(toPrimitive(scriptObject, Number.class));
    }

    public static double toNumberOptimistic(Object obj, int i) {
        Class<?> cls;
        if (obj != null && ((cls = obj.getClass()) == Double.class || cls == Integer.class || cls == Long.class)) {
            return ((Number) obj).doubleValue();
        }
        throw new UnwarrantedOptimismException(obj, i);
    }

    public static double toNumberMaybeOptimistic(Object obj, int i) {
        return UnwarrantedOptimismException.isValid(i) ? toNumberOptimistic(obj, i) : toNumber(obj);
    }

    public static int digit(char c, int i) {
        return digit(c, i, false);
    }

    public static int digit(char c, int i, boolean z) {
        char c2 = (char) ((97 + (i - 1)) - 10);
        char lowerCase = Character.toLowerCase(c);
        if (lowerCase >= 'a' && lowerCase <= c2) {
            return Character.digit(c, i);
        }
        if (Character.isDigit(c)) {
            if (!z || (c >= '0' && c <= '9')) {
                return Character.digit(c, i);
            }
            return -1;
        }
        return -1;
    }

    public static double toNumber(String str) throws NumberFormatException {
        boolean z;
        double radix;
        int length = str.length();
        if (length == 0) {
            return 0.0d;
        }
        int i = 0;
        char cCharAt = str.charAt(0);
        while (true) {
            char cCharAt2 = cCharAt;
            if (Lexer.isJSWhitespace(cCharAt2)) {
                i++;
                if (i == length) {
                    return 0.0d;
                }
                cCharAt = str.charAt(i);
            } else {
                while (Lexer.isJSWhitespace(str.charAt(length - 1))) {
                    length--;
                }
                if (cCharAt2 == '-') {
                    i++;
                    if (i == length) {
                        return Double.NaN;
                    }
                    cCharAt2 = str.charAt(i);
                    z = true;
                } else {
                    if (cCharAt2 == '+') {
                        i++;
                        if (i == length) {
                            return Double.NaN;
                        }
                        cCharAt2 = str.charAt(i);
                    }
                    z = false;
                }
                if (i + 1 < length && cCharAt2 == '0' && Character.toLowerCase(str.charAt(i + 1)) == 'x') {
                    radix = parseRadix(str.toCharArray(), i + 2, length, 16);
                } else {
                    if (cCharAt2 == 'I' && length - i == 8 && str.regionMatches(i, "Infinity", 0, 8)) {
                        return z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                    }
                    for (int i2 = i; i2 < length; i2++) {
                        char cCharAt3 = str.charAt(i2);
                        if ((cCharAt3 < '0' || cCharAt3 > '9') && cCharAt3 != '.' && cCharAt3 != 'e' && cCharAt3 != 'E' && cCharAt3 != '+' && cCharAt3 != '-') {
                            return Double.NaN;
                        }
                    }
                    try {
                        radix = Double.parseDouble(str.substring(i, length));
                    } catch (NumberFormatException unused) {
                        return Double.NaN;
                    }
                }
                return z ? -radix : radix;
            }
        }
    }

    public static int toInteger(Object obj) {
        return (int) toNumber(obj);
    }

    public static long toLong(Object obj) {
        return obj instanceof Long ? ((Long) obj).longValue() : (long) toNumber(obj);
    }

    public static int toInt32(Object obj) {
        return toInt32(toNumber(obj));
    }

    public static int toInt32Optimistic(Object obj, int i) {
        if (obj != null && obj.getClass() == Integer.class) {
            return ((Integer) obj).intValue();
        }
        throw new UnwarrantedOptimismException(obj, i);
    }

    public static int toInt32MaybeOptimistic(Object obj, int i) {
        return UnwarrantedOptimismException.isValid(i) ? toInt32Optimistic(obj, i) : toInt32(obj);
    }

    public static int toInt32(double d) {
        return (int) doubleToInt32(d);
    }

    public static long toUint32(Object obj) {
        return toUint32(toNumber(obj));
    }

    public static long toUint32(double d) {
        return doubleToInt32(d) & MAX_UINT;
    }

    public static int toUint32Optimistic(int i, int i2) {
        if (i >= 0) {
            return i;
        }
        throw new UnwarrantedOptimismException(Double.valueOf(toUint32Double(i)), i2, Type.NUMBER);
    }

    public static double toUint32Double(int i) {
        return i & MAX_UINT;
    }

    public static int toUint16(Object obj) {
        return toUint16(toNumber(obj));
    }

    public static int toUint16(double d) {
        return ((int) doubleToInt32(d)) & CharCompanionObject.MAX_VALUE;
    }

    private static long doubleToInt32(double d) {
        int exponent = Math.getExponent(d);
        if (exponent < 31) {
            return (long) d;
        }
        if (exponent >= 84) {
            return 0L;
        }
        return (long) ((d >= 0.0d ? Math.floor(d) : Math.ceil(d)) % INT32_LIMIT);
    }

    public static boolean isFinite(double d) {
        return (Double.isInfinite(d) || Double.isNaN(d)) ? false : true;
    }

    public static Double toDouble(double d) {
        return Double.valueOf(d);
    }

    public static Double toDouble(long j) {
        return Double.valueOf(j);
    }

    public static Double toDouble(int i) {
        return Double.valueOf(i);
    }

    public static Object toObject(boolean z) {
        return Boolean.valueOf(z);
    }

    public static Object toObject(int i) {
        return Integer.valueOf(i);
    }

    public static Object toObject(long j) {
        return Long.valueOf(j);
    }

    public static Object toObject(double d) {
        return Double.valueOf(d);
    }

    public static Object toScriptObject(Object obj) {
        return toScriptObject(Context.getGlobal(), obj);
    }

    public static Object toScriptObject(Global global, Object obj) {
        if (nullOrUndefined(obj)) {
            throw ECMAErrors.typeError(global, "not.an.object", new String[]{ScriptRuntime.safeToString(obj)});
        }
        if (obj instanceof ScriptObject) {
            return obj;
        }
        return global.wrapAsObject(obj);
    }

    public static Object toJavaArray(Object obj, Class cls) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getArray().asArrayOfType(cls);
        }
        if (obj instanceof JSObject) {
            ArrayLikeIterator arrayLikeIterator = ArrayLikeIterator.arrayLikeIterator(obj);
            Object[] objArr = new Object[(int) arrayLikeIterator.getLength()];
            int i = 0;
            while (arrayLikeIterator.hasNext()) {
                int i2 = i;
                i++;
                objArr[i2] = arrayLikeIterator.next();
            }
            return convertArray(objArr, cls);
        }
        if (obj == null) {
            return null;
        }
        throw new IllegalArgumentException("not a script object");
    }

    public static Object convertArray(Object[] objArr, Class cls) throws NegativeArraySizeException {
        if (cls == Object.class) {
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj instanceof ConsString) {
                    objArr[i] = obj.toString();
                }
            }
        }
        Object objNewInstance = Array.newInstance((Class<?>) cls, objArr.length);
        MethodHandle typeConverter = Bootstrap.getLinkerServices().getTypeConverter(Object.class, cls);
        for (int i2 = 0; i2 < objArr.length; i2++) {
            try {
                Array.set(objNewInstance, i2, invoke(typeConverter, objArr[i2]));
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
        return objNewInstance;
    }

    public static boolean nullOrUndefined(Object obj) {
        return obj == null || obj == ScriptRuntime.UNDEFINED;
    }

    static String toStringImpl(Object obj, boolean z) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ConsString) {
            return obj.toString();
        }
        if (isNumber(obj)) {
            return toString(((Number) obj).doubleValue());
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return "undefined";
        }
        if (obj == null) {
            return Configurator.NULL;
        }
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        if (z && (obj instanceof ScriptObject)) {
            ScriptObject scriptObject = (ScriptObject) obj;
            if (Context.getGlobal().isError(scriptObject)) {
                return ECMAException.safeToString(scriptObject);
            }
            return scriptObject.safeToString();
        }
        return toString(toPrimitive(obj, String.class));
    }

    static String trimLeft(String str) {
        int i = 0;
        while (i < str.length() && Lexer.isJSWhitespace(str.charAt(i))) {
            i++;
        }
        return str.substring(i);
    }

    private static Object throwUnwarrantedOptimismException(Object obj, int i) {
        throw new UnwarrantedOptimismException(obj, i);
    }

    public static int addExact(int i, int i2, int i3) {
        try {
            return Math.addExact(i, i2);
        } catch (ArithmeticException unused) {
            throw new UnwarrantedOptimismException(Double.valueOf(i + i2), i3);
        }
    }

    public static int subExact(int i, int i2, int i3) {
        try {
            return Math.subtractExact(i, i2);
        } catch (ArithmeticException unused) {
            throw new UnwarrantedOptimismException(Double.valueOf(i - i2), i3);
        }
    }

    public static int mulExact(int i, int i2, int i3) {
        try {
            return Math.multiplyExact(i, i2);
        } catch (ArithmeticException unused) {
            throw new UnwarrantedOptimismException(Double.valueOf(i * i2), i3);
        }
    }

    public static int divExact(int i, int i2, int i3) {
        try {
            int i4 = i / i2;
            if (i % i2 == 0) {
                return i4;
            }
            throw new UnwarrantedOptimismException(Double.valueOf(i / i2), i3);
        } catch (ArithmeticException unused) {
            if ($assertionsDisabled || i2 == 0) {
                throw new UnwarrantedOptimismException(Double.valueOf(i > 0 ? Double.POSITIVE_INFINITY : i < 0 ? Double.NEGATIVE_INFINITY : Double.NaN), i3);
            }
            throw new AssertionError();
        }
    }

    public static int remExact(int i, int i2, int i3) {
        try {
            return i % i2;
        } catch (ArithmeticException unused) {
            if ($assertionsDisabled || i2 == 0) {
                throw new UnwarrantedOptimismException(Double.valueOf(Double.NaN), i3);
            }
            throw new AssertionError();
        }
    }

    public static int decrementExact(int i, int i2) {
        try {
            return Math.decrementExact(i);
        } catch (ArithmeticException unused) {
            throw new UnwarrantedOptimismException(Double.valueOf(i - 1.0d), i2);
        }
    }

    public static int incrementExact(int i, int i2) {
        try {
            return Math.incrementExact(i);
        } catch (ArithmeticException unused) {
            throw new UnwarrantedOptimismException(Double.valueOf(i + 1.0d), i2);
        }
    }

    public static int negateExact(int i, int i2) {
        try {
            if (i == 0) {
                throw new UnwarrantedOptimismException(Double.valueOf(-0.0d), i2);
            }
            return Math.negateExact(i);
        } catch (ArithmeticException unused) {
            throw new UnwarrantedOptimismException(Double.valueOf(-i), i2);
        }
    }

    public static int getAccessorTypeIndex(Type type) {
        return getAccessorTypeIndex(type.getTypeClass());
    }

    public static int getAccessorTypeIndex(Class cls) {
        if (cls == null) {
            return -1;
        }
        if (cls == Integer.TYPE) {
            return 0;
        }
        if (cls == Double.TYPE) {
            return 1;
        }
        if (!cls.isPrimitive()) {
            return 2;
        }
        return -1;
    }

    public static Type getAccessorType(int i) {
        return (Type) ACCESSOR_TYPES.get(i);
    }

    public static int getNumberOfAccessorTypes() {
        return ACCESSOR_TYPES.size();
    }

    private static double parseRadix(char[] cArr, int i, int i2, int i3) {
        int i4 = 0;
        for (int i5 = i; i5 < i2; i5++) {
            if (digit(cArr[i5], i3) == -1) {
                return Double.NaN;
            }
            i4++;
        }
        if (i4 == 0) {
            return Double.NaN;
        }
        double dDigit = 0.0d;
        for (int i6 = i; i6 < i + i4; i6++) {
            dDigit = (dDigit * i3) + digit(cArr[i6], i3);
        }
        return dDigit;
    }

    private static double toNumberGeneric(Object obj) {
        if (obj == null) {
            return 0.0d;
        }
        if (obj instanceof String) {
            return toNumber((String) obj);
        }
        if (obj instanceof ConsString) {
            return toNumber(obj.toString());
        }
        if (obj instanceof Boolean) {
            return toNumber((Boolean) obj);
        }
        if (obj instanceof ScriptObject) {
            return toNumber((ScriptObject) obj);
        }
        if (obj instanceof Undefined) {
            return Double.NaN;
        }
        return toNumber(toPrimitive(obj, Number.class));
    }

    private static Object invoke(MethodHandle methodHandle, Object obj) {
        try {
            return (Object) methodHandle.invoke(obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public static MethodHandle unboxConstant(Object obj) {
        if (obj != null) {
            if (obj.getClass() == Integer.class) {
                return Lookup.f31MH.constant(Integer.TYPE, Integer.valueOf(((Integer) obj).intValue()));
            }
            if (obj.getClass() == Double.class) {
                return Lookup.f31MH.constant(Double.TYPE, Double.valueOf(((Double) obj).doubleValue()));
            }
        }
        return Lookup.f31MH.constant(Object.class, obj);
    }

    public static Class unboxedFieldType(Object obj) {
        if (obj == null) {
            return Object.class;
        }
        if (obj.getClass() == Integer.class) {
            return Integer.TYPE;
        }
        if (obj.getClass() == Double.class) {
            return Double.TYPE;
        }
        return Object.class;
    }

    private static final List toUnmodifiableList(MethodHandle[] methodHandleArr) {
        return Collections.unmodifiableList(Arrays.asList(methodHandleArr));
    }
}

package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaArgumentConverters.class */
final class JavaArgumentConverters {
    private static final MethodHandle TO_BOOLEAN = findOwnMH("toBoolean", Boolean.class, new Class[]{Object.class});
    private static final MethodHandle TO_STRING = findOwnMH("toString", String.class, new Class[]{Object.class});
    private static final MethodHandle TO_DOUBLE = findOwnMH("toDouble", Double.class, new Class[]{Object.class});
    private static final MethodHandle TO_NUMBER = findOwnMH("toNumber", Number.class, new Class[]{Object.class});
    private static final MethodHandle TO_LONG = findOwnMH("toLong", Long.class, new Class[]{Object.class});
    private static final MethodHandle TO_LONG_PRIMITIVE = findOwnMH("toLongPrimitive", Long.TYPE, new Class[]{Object.class});
    private static final MethodHandle TO_CHAR = findOwnMH("toChar", Character.class, new Class[]{Object.class});
    private static final MethodHandle TO_CHAR_PRIMITIVE = findOwnMH("toCharPrimitive", Character.TYPE, new Class[]{Object.class});
    private static final Map CONVERTERS = new HashMap();

    static {
        CONVERTERS.put(Number.class, TO_NUMBER);
        CONVERTERS.put(String.class, TO_STRING);
        CONVERTERS.put(Boolean.TYPE, JSType.TO_BOOLEAN.methodHandle());
        CONVERTERS.put(Boolean.class, TO_BOOLEAN);
        CONVERTERS.put(Character.TYPE, TO_CHAR_PRIMITIVE);
        CONVERTERS.put(Character.class, TO_CHAR);
        CONVERTERS.put(Double.TYPE, JSType.TO_NUMBER.methodHandle());
        CONVERTERS.put(Double.class, TO_DOUBLE);
        CONVERTERS.put(Long.TYPE, TO_LONG_PRIMITIVE);
        CONVERTERS.put(Long.class, TO_LONG);
        putLongConverter(Byte.class);
        putLongConverter(Short.class);
        putLongConverter(Integer.class);
        putDoubleConverter(Float.class);
    }

    private JavaArgumentConverters() {
    }

    static MethodHandle getConverter(Class cls) {
        return (MethodHandle) CONVERTERS.get(cls);
    }

    private static Boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj == null || obj == ScriptRuntime.UNDEFINED) {
            return null;
        }
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            return Boolean.valueOf((dDoubleValue == 0.0d || Double.isNaN(dDoubleValue)) ? false : true);
        }
        if ((obj instanceof String) || (obj instanceof ConsString)) {
            return Boolean.valueOf(((CharSequence) obj).length() > 0);
        }
        if (obj instanceof ScriptObject) {
            return true;
        }
        throw assertUnexpectedType(obj);
    }

    private static Character toChar(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            int iIntValue = ((Number) obj).intValue();
            if (iIntValue >= 0 && iIntValue <= 65535) {
                return Character.valueOf((char) iIntValue);
            }
            throw ECMAErrors.typeError("cant.convert.number.to.char", new String[0]);
        }
        String string = toString(obj);
        if (string == null) {
            return null;
        }
        if (string.length() != 1) {
            throw ECMAErrors.typeError("cant.convert.string.to.char", new String[0]);
        }
        return Character.valueOf(string.charAt(0));
    }

    static char toCharPrimitive(Object obj) {
        Character ch = toChar(obj);
        if (ch == null) {
            return (char) 0;
        }
        return ch.charValue();
    }

    static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSType.toString(obj);
    }

    private static Double toDouble(Object obj) {
        Object primitive = obj;
        while (true) {
            Object obj2 = primitive;
            if (obj2 == null) {
                return null;
            }
            if (obj2 instanceof Double) {
                return (Double) obj2;
            }
            if (obj2 instanceof Number) {
                return Double.valueOf(((Number) obj2).doubleValue());
            }
            if (obj2 instanceof String) {
                return Double.valueOf(JSType.toNumber((String) obj2));
            }
            if (obj2 instanceof ConsString) {
                return Double.valueOf(JSType.toNumber(obj2.toString()));
            }
            if (obj2 instanceof Boolean) {
                return Double.valueOf(((Boolean) obj2).booleanValue() ? 1.0d : 0.0d);
            }
            if (obj2 instanceof ScriptObject) {
                primitive = JSType.toPrimitive(obj2, Number.class);
            } else {
                if (obj2 == ScriptRuntime.UNDEFINED) {
                    return Double.valueOf(Double.NaN);
                }
                throw assertUnexpectedType(obj2);
            }
        }
    }

    private static Number toNumber(Object obj) {
        Object primitive = obj;
        while (true) {
            Object obj2 = primitive;
            if (obj2 == null) {
                return null;
            }
            if (obj2 instanceof Number) {
                return (Number) obj2;
            }
            if (obj2 instanceof String) {
                return Double.valueOf(JSType.toNumber((String) obj2));
            }
            if (obj2 instanceof ConsString) {
                return Double.valueOf(JSType.toNumber(obj2.toString()));
            }
            if (obj2 instanceof Boolean) {
                return Double.valueOf(((Boolean) obj2).booleanValue() ? 1.0d : 0.0d);
            }
            if (obj2 instanceof ScriptObject) {
                primitive = JSType.toPrimitive(obj2, Number.class);
            } else {
                if (obj2 == ScriptRuntime.UNDEFINED) {
                    return Double.valueOf(Double.NaN);
                }
                throw assertUnexpectedType(obj2);
            }
        }
    }

    private static Long toLong(Object obj) {
        Object primitive = obj;
        while (true) {
            Object obj2 = primitive;
            if (obj2 == null) {
                return null;
            }
            if (obj2 instanceof Long) {
                return (Long) obj2;
            }
            if (obj2 instanceof Integer) {
                return Long.valueOf(((Integer) obj2).longValue());
            }
            if (obj2 instanceof Double) {
                Double d = (Double) obj2;
                if (Double.isInfinite(d.doubleValue())) {
                    return 0L;
                }
                return Long.valueOf(d.longValue());
            }
            if (obj2 instanceof Float) {
                Float f = (Float) obj2;
                if (Float.isInfinite(f.floatValue())) {
                    return 0L;
                }
                return Long.valueOf(f.longValue());
            }
            if (obj2 instanceof Number) {
                return Long.valueOf(((Number) obj2).longValue());
            }
            if ((obj2 instanceof String) || (obj2 instanceof ConsString)) {
                return Long.valueOf(JSType.toLong(obj2));
            }
            if (obj2 instanceof Boolean) {
                return Long.valueOf(((Boolean) obj2).booleanValue() ? 1L : 0L);
            }
            if (obj2 instanceof ScriptObject) {
                primitive = JSType.toPrimitive(obj2, Number.class);
            } else {
                if (obj2 == ScriptRuntime.UNDEFINED) {
                    return null;
                }
                throw assertUnexpectedType(obj2);
            }
        }
    }

    private static AssertionError assertUnexpectedType(Object obj) {
        return new AssertionError("Unexpected type" + obj.getClass().getName() + ". Guards should have prevented this");
    }

    private static long toLongPrimitive(Object obj) {
        Long l = toLong(obj);
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), JavaArgumentConverters.class, str, Lookup.f31MH.type(cls, clsArr));
    }

    private static void putDoubleConverter(Class cls) {
        Class<?> primitiveType = TypeUtilities.getPrimitiveType(cls);
        CONVERTERS.put(primitiveType, Lookup.f31MH.explicitCastArguments(JSType.TO_NUMBER.methodHandle(), JSType.TO_NUMBER.methodHandle().type().changeReturnType(primitiveType)));
        CONVERTERS.put(cls, Lookup.f31MH.filterReturnValue(TO_DOUBLE, findOwnMH(primitiveType.getName() + "Value", cls, new Class[]{Double.class})));
    }

    private static void putLongConverter(Class cls) {
        Class<?> primitiveType = TypeUtilities.getPrimitiveType(cls);
        CONVERTERS.put(primitiveType, Lookup.f31MH.explicitCastArguments(TO_LONG_PRIMITIVE, TO_LONG_PRIMITIVE.type().changeReturnType(primitiveType)));
        CONVERTERS.put(cls, Lookup.f31MH.filterReturnValue(TO_LONG, findOwnMH(primitiveType.getName() + "Value", cls, new Class[]{Long.class})));
    }

    private static Byte byteValue(Long l) {
        if (l == null) {
            return null;
        }
        return Byte.valueOf(l.byteValue());
    }

    private static Short shortValue(Long l) {
        if (l == null) {
            return null;
        }
        return Short.valueOf(l.shortValue());
    }

    private static Integer intValue(Long l) {
        if (l == null) {
            return null;
        }
        return Integer.valueOf(l.intValue());
    }

    private static Float floatValue(Double d) {
        if (d == null) {
            return null;
        }
        return Float.valueOf(d.floatValue());
    }
}

package net.ccbluex.liquidbounce.utils.render;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001:\u0002/0B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004H\u0007J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010&\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010'\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010(\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010)\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0007\u00a8\u00061"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils;", "", "()V", "apply", "", "type", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "order", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", PropertyDescriptor.VALUE, "easeInBack", "x", "easeInBounce", "easeInCirc", "easeInCubic", "easeInElastic", "easeInExpo", "easeInOutBack", "easeInOutBounce", "easeInOutCirc", "easeInOutCubic", "easeInOutElastic", "easeInOutExpo", "easeInOutQuad", "easeInOutQuart", "easeInOutQuint", "easeInOutSine", "easeInQuad", "easeInQuart", "easeInQuint", "easeInSine", "easeOutBack", "easeOutBounce", "animeX", "easeOutCirc", "easeOutCubic", "easeOutElastic", "easeOutExpo", "easeOutQuad", "easeOutQuart", "easeOutQuint", "easeOutSine", "getEnumEasingList", "Lnet/ccbluex/liquidbounce/value/ListValue;", "name", "", "getEnumEasingOrderList", "EnumEasingOrder", "EnumEasingType", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/EaseUtils.class */
public final class EaseUtils {
    public static final EaseUtils INSTANCE = new EaseUtils();

    private EaseUtils() {
    }

    @JvmStatic
    public static final double easeInSine(double d) {
        return 1.0d - Math.cos((d * 3.141592653589793d) / 2.0d);
    }

    @JvmStatic
    public static final double easeOutSine(double d) {
        return Math.sin((d * 3.141592653589793d) / 2.0d);
    }

    @JvmStatic
    public static final double easeInOutSine(double d) {
        return (-(Math.cos(3.141592653589793d * d) - 1.0d)) / 2.0d;
    }

    @JvmStatic
    public static final double easeInOutQuad(double d) {
        return d < 0.5d ? 2.0d * d * d : 1.0d - (Math.pow(((-2.0d) * d) + 2.0d, 2.0d) / 2.0d);
    }

    @JvmStatic
    public static final double easeOutCubic(double d) {
        return 1.0d - Math.pow(1.0d - d, 3.0d);
    }

    @JvmStatic
    public static final double easeInOutCubic(double d) {
        return d < 0.5d ? 4.0d * d * d * d : 1.0d - (Math.pow(((-2.0d) * d) + 2.0d, 3.0d) / 2.0d);
    }

    @JvmStatic
    public static final double easeOutQuart(double d) {
        return 1.0d - Math.pow(1.0d - d, 4.0d);
    }

    @JvmStatic
    public static final double easeInOutQuart(double d) {
        return d < 0.5d ? 8.0d * d * d * d * d : 1.0d - (Math.pow(((-2.0d) * d) + 2.0d, 4.0d) / 2.0d);
    }

    @JvmStatic
    public static final double easeOutQuint(double d) {
        return 1.0d - Math.pow(1.0d - d, 5.0d);
    }

    @JvmStatic
    public static final double easeInOutQuint(double d) {
        return d < 0.5d ? 16.0d * d * d * d * d * d : 1.0d - (Math.pow(((-2.0d) * d) + 2.0d, 5.0d) / 2.0d);
    }

    @JvmStatic
    public static final double easeInExpo(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        return Math.pow(2.0d, (10.0d * d) - 10.0d);
    }

    @JvmStatic
    public static final double easeOutExpo(double d) {
        if (d == 1.0d) {
            return 1.0d;
        }
        return 1.0d - Math.pow(2.0d, (-10.0d) * d);
    }

    @JvmStatic
    public static final double easeInOutExpo(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return 1.0d;
        }
        if (d < 0.5d) {
            return Math.pow(2.0d, (20.0d * d) - 10.0d) / 2.0d;
        }
        return (2.0d - Math.pow(2.0d, ((-20.0d) * d) + 10.0d)) / 2.0d;
    }

    @JvmStatic
    public static final double easeInCirc(double d) {
        return 1.0d - Math.sqrt(1.0d - Math.pow(d, 2.0d));
    }

    @JvmStatic
    public static final double easeOutCirc(double d) {
        return Math.sqrt(1.0d - Math.pow(d - 1.0d, 2.0d));
    }

    @JvmStatic
    public static final double easeInOutCirc(double d) {
        return d < 0.5d ? (1.0d - Math.sqrt(1.0d - Math.pow(2.0d * d, 2.0d))) / 2.0d : (Math.sqrt(1.0d - Math.pow(((-2.0d) * d) + 2.0d, 2.0d)) + 1.0d) / 2.0d;
    }

    @JvmStatic
    public static final double easeOutBack(double d) {
        return 1.0d + (2.70158d * Math.pow(d - 1.0d, 3.0d)) + (1.70158d * Math.pow(d - 1.0d, 2.0d));
    }

    @JvmStatic
    public static final double easeInOutBack(double d) {
        if (d < 0.5d) {
            return (Math.pow(2.0d * d, 2.0d) * ((7.189819d * d) - 2.5949095d)) / 2.0d;
        }
        return ((Math.pow((2.0d * d) - 2.0d, 2.0d) * ((3.5949095d * ((d * 2.0d) - 2.0d)) + 2.5949095d)) + 2.0d) / 2.0d;
    }

    @JvmStatic
    public static final double easeInElastic(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return 1.0d;
        }
        return Math.pow(-2.0d, (10.0d * d) - 10.0d) * Math.sin(((d * 10.0d) - 10.75d) * 2.0943951023931953d);
    }

    @JvmStatic
    public static final double easeOutElastic(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return 1.0d;
        }
        return (Math.pow(2.0d, (-10.0d) * d) * Math.sin(((d * 10.0d) - 0.75d) * 2.0943951023931953d)) + 1.0d;
    }

    @JvmStatic
    public static final double easeInOutElastic(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return 1.0d;
        }
        if (d < 0.5d) {
            return (-(Math.pow(2.0d, (20.0d * d) - 10.0d) * Math.sin(((20.0d * d) - 11.125d) * 1.3962634015954636d))) / 2.0d;
        }
        return ((Math.pow(2.0d, ((-20.0d) * d) + 10.0d) * Math.sin(((20.0d * d) - 11.125d) * 1.3962634015954636d)) / 2.0d) + 1.0d;
    }

    @JvmStatic
    public static final double easeInBounce(double d) {
        double d2;
        double d3 = 1.0d - d;
        if (d3 < 0.36363636363636365d) {
            d2 = 7.5625d * d3 * d3;
        } else if (d3 < 0.7272727272727273d) {
            double d4 = d3 - 1.5d;
            d2 = (7.5625d * (d4 / 2.75d) * d4) + 0.75d;
        } else if (d3 < 0.9090909090909091d) {
            double d5 = d3 - 2.25d;
            d2 = (7.5625d * (d5 / 2.75d) * d5) + 0.9375d;
        } else {
            double d6 = d3 - 2.625d;
            d2 = (7.5625d * (d6 / 2.75d) * d6) + 0.984375d;
        }
        return 1.0d - d2;
    }

    @JvmStatic
    public static final double easeInOutBounce(double d) {
        double d2;
        double d3;
        if (d < 0.5d) {
            double d4 = 1.0d - (2.0d * d);
            if (d4 < 0.36363636363636365d) {
                d3 = 7.5625d * d4 * d4;
            } else if (d4 < 0.7272727272727273d) {
                double d5 = d4 - 1.5d;
                d3 = (7.5625d * (d5 / 2.75d) * d5) + 0.75d;
            } else if (d4 < 0.9090909090909091d) {
                double d6 = d4 - 2.25d;
                d3 = (7.5625d * (d6 / 2.75d) * d6) + 0.9375d;
            } else {
                double d7 = d4 - 2.625d;
                d3 = (7.5625d * (d7 / 2.75d) * d7) + 0.984375d;
            }
            return (1.0d - d3) / 2.0d;
        }
        double d8 = (2.0d * d) - 1.0d;
        if (d8 < 0.36363636363636365d) {
            d2 = 7.5625d * d8 * d8;
        } else if (d8 < 0.7272727272727273d) {
            double d9 = d8 - 1.5d;
            d2 = (7.5625d * (d9 / 2.75d) * d9) + 0.75d;
        } else if (d8 < 0.9090909090909091d) {
            double d10 = d8 - 2.25d;
            d2 = (7.5625d * (d10 / 2.75d) * d10) + 0.9375d;
        } else {
            double d11 = d8 - 2.625d;
            d2 = (7.5625d * (d11 / 2.75d) * d11) + 0.984375d;
        }
        return (1.0d + d2) / 2.0d;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0010"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "", "(Ljava/lang/String;I)V", "toFriendlyName", "", "NONE", "SINE", "QUAD", "CUBIC", "QUART", "QUINT", "EXPO", "CIRC", "BACK", "ELASTIC", "BOUNCE", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType.class */
    public enum EnumEasingType {
        NONE,
        SINE,
        QUAD,
        CUBIC,
        QUART,
        QUINT,
        EXPO,
        CIRC,
        BACK,
        ELASTIC,
        BOUNCE;

        @NotNull
        public final String toFriendlyName() {
            String string = toString();
            StringBuilder sb = new StringBuilder();
            if (string == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring = string.substring(0, 1);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            if (strSubstring == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String upperCase = strSubstring.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            StringBuilder sbAppend = sb.append(upperCase);
            int length = string.length();
            if (string == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring2 = string.substring(1, length);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            if (strSubstring2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = strSubstring2.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            return sbAppend.append(lowerCase).toString();
        }
    }

    @JvmStatic
    public static final double apply(@NotNull EnumEasingType type, @NotNull EnumEasingOrder order, double d) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(order, "order");
        if (type == EnumEasingType.NONE) {
            return d;
        }
        String str = "ease" + order.getMethodName() + type.toFriendlyName();
        for (Method method : INSTANCE.getClass().getDeclaredMethods()) {
            Intrinsics.checkExpressionValueIsNotNull(method, "method");
            if (method.getName().equals(str)) {
                Object objInvoke = method.invoke(INSTANCE, Double.valueOf(d));
                if (objInvoke == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
                }
                return ((Double) objInvoke).doubleValue();
            }
        }
        ClientUtils.getLogger().warn("Cannot found easing method: " + str);
        return d;
    }

    @JvmStatic
    @NotNull
    public static final ListValue getEnumEasingList(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        ArrayList arrayList = new ArrayList();
        for (EnumEasingType enumEasingType : EnumEasingType.values()) {
            arrayList.add(enumEasingType.toFriendlyName());
        }
        Object[] array = arrayList.toArray(new String[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return new ListValue(name, (String[]) array, (String) arrayList.get(0));
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "", "methodName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMethodName", "()Ljava/lang/String;", "FAST_AT_START", "FAST_AT_END", "FAST_AT_START_AND_END", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder.class */
    public enum EnumEasingOrder {
        FAST_AT_START("Out"),
        FAST_AT_END("In"),
        FAST_AT_START_AND_END("InOut");


        @NotNull
        private final String methodName;

        @NotNull
        public final String getMethodName() {
            return this.methodName;
        }

        EnumEasingOrder(String str) {
            this.methodName = str;
        }
    }

    @JvmStatic
    @NotNull
    public static final ListValue getEnumEasingOrderList(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        ArrayList arrayList = new ArrayList();
        for (EnumEasingOrder enumEasingOrder : EnumEasingOrder.values()) {
            arrayList.add(enumEasingOrder.toString());
        }
        Object[] array = arrayList.toArray(new String[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return new ListValue(name, (String[]) array, (String) arrayList.get(0));
    }
}

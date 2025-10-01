package kotlin.time;

import java.util.concurrent.TimeUnit;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd^\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0087@\u0018\ufffd\ufffd s2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001sB\u0014\b\ufffd\ufffd\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\ufffd\ufffdH\u0096\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b'\u0010(J\u001b\u0010)\u001a\u00020\ufffd\ufffd2\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b+\u0010,J\u001b\u0010)\u001a\u00020\ufffd\ufffd2\u0006\u0010*\u001a\u00020\tH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b+\u0010-J\u001b\u0010)\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\ufffd\ufffdH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b.\u0010,J\u0013\u0010/\u001a\u0002002\b\u0010&\u001a\u0004\u0018\u000101H\u00d6\u0003J\t\u00102\u001a\u00020\tH\u00d6\u0001J\r\u00103\u001a\u000200\u00a2\u0006\u0004\b4\u00105J\r\u00106\u001a\u000200\u00a2\u0006\u0004\b7\u00105J\r\u00108\u001a\u000200\u00a2\u0006\u0004\b9\u00105J\r\u0010:\u001a\u000200\u00a2\u0006\u0004\b;\u00105J\u001b\u0010<\u001a\u00020\ufffd\ufffd2\u0006\u0010&\u001a\u00020\ufffd\ufffdH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b=\u0010,J\u001b\u0010>\u001a\u00020\ufffd\ufffd2\u0006\u0010&\u001a\u00020\ufffd\ufffdH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b?\u0010,J\u0017\u0010@\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a2\u0006\u0004\bA\u0010(J\u001b\u0010B\u001a\u00020\ufffd\ufffd2\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bC\u0010,J\u001b\u0010B\u001a\u00020\ufffd\ufffd2\u0006\u0010*\u001a\u00020\tH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bC\u0010-J\u008d\u0001\u0010D\u001a\u0002HE\"\u0004\b\ufffd\ufffd\u0010E2u\u0010F\u001aq\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0GH\u0086\b\u00a2\u0006\u0004\bO\u0010PJx\u0010D\u001a\u0002HE\"\u0004\b\ufffd\ufffd\u0010E2`\u0010F\u001a\\\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0QH\u0086\b\u00a2\u0006\u0004\bO\u0010RJc\u0010D\u001a\u0002HE\"\u0004\b\ufffd\ufffd\u0010E2K\u0010F\u001aG\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0SH\u0086\b\u00a2\u0006\u0004\bO\u0010TJN\u0010D\u001a\u0002HE\"\u0004\b\ufffd\ufffd\u0010E26\u0010F\u001a2\u0012\u0013\u0012\u00110V\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0UH\u0086\b\u00a2\u0006\u0004\bO\u0010WJ\u0019\u0010X\u001a\u00020\u00032\n\u0010Y\u001a\u00060Zj\u0002`[\u00a2\u0006\u0004\b\\\u0010]J\u0019\u0010^\u001a\u00020\t2\n\u0010Y\u001a\u00060Zj\u0002`[\u00a2\u0006\u0004\b_\u0010`J\r\u0010a\u001a\u00020b\u00a2\u0006\u0004\bc\u0010dJ\u0019\u0010e\u001a\u00020V2\n\u0010Y\u001a\u00060Zj\u0002`[\u00a2\u0006\u0004\bf\u0010gJ\r\u0010h\u001a\u00020V\u00a2\u0006\u0004\bi\u0010jJ\r\u0010k\u001a\u00020V\u00a2\u0006\u0004\bl\u0010jJ\u000f\u0010m\u001a\u00020bH\u0016\u00a2\u0006\u0004\bn\u0010dJ#\u0010m\u001a\u00020b2\n\u0010Y\u001a\u00060Zj\u0002`[2\b\b\u0002\u0010o\u001a\u00020\t\u00a2\u0006\u0004\bn\u0010pJ\u0013\u0010q\u001a\u00020\ufffd\ufffdH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\br\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\ufffd\ufffd8F\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0011\u0010\u0010\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0011\u0010\u0012\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0011\u0010\u0014\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0011\u0010\u0016\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0011\u0010\u0018\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0011\u0010\u001a\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001c\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u001a\u0010\"\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0080\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006t"}, m27d2 = {"Lkotlin/time/Duration;", "", PropertyDescriptor.VALUE, "", "constructor-impl", "(D)D", "absoluteValue", "getAbsoluteValue-impl", "hoursComponent", "", "hoursComponent$annotations", "()V", "getHoursComponent-impl", "(D)I", "inDays", "getInDays-impl", "inHours", "getInHours-impl", "inMicroseconds", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds-impl", "inMinutes", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds-impl", "inSeconds", "getInSeconds-impl", "minutesComponent", "minutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "nanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "secondsComponent$annotations", "getSecondsComponent-impl", "compareTo", "other", "compareTo-LRDsOJo", "(DD)I", "div", "scale", "div-impl", "(DD)D", "(DI)D", "div-LRDsOJo", "equals", "", "", "hashCode", "isFinite", "isFinite-impl", "(D)Z", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "plus", "plus-LRDsOJo", "precision", "precision-impl", "times", "times-impl", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(DLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(DLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(DLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "", "(DLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(DLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(DLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(D)Ljava/lang/String;", "toLong", "toLong-impl", "(DLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "(D)J", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(DLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-impl", "Companion", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/Duration.class */
public final class Duration implements Comparable {
    private final double value;
    private static final double INFINITE;
    public static final Companion Companion = new Companion(null);
    private static final double ZERO = 0.0d;

    /* renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public int m1482compareToLRDsOJo(double d) {
        return m1493compareToLRDsOJo(this.value, d);
    }

    @NotNull
    public String toString() {
        return m1514toStringimpl(this.value);
    }

    @NotNull
    /* renamed from: box-impl, reason: not valid java name */
    public static final Duration m1518boximpl(double d) {
        return new Duration(d);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m1519hashCodeimpl(double d) {
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m1520equalsimpl(double d, @Nullable Object obj) {
        return (obj instanceof Duration) && Double.compare(d, ((Duration) obj).m1521unboximpl()) == 0;
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final double m1521unboximpl() {
        return this.value;
    }

    public int hashCode() {
        return m1519hashCodeimpl(this.value);
    }

    public boolean equals(Object obj) {
        return m1520equalsimpl(this.value, obj);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return m1482compareToLRDsOJo(((Duration) obj).m1521unboximpl());
    }

    private Duration(double d) {
        this.value = d;
    }

    static {
        DoubleCompanionObject doubleCompanionObject = DoubleCompanionObject.INSTANCE;
        INFINITE = Double.POSITIVE_INFINITY;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000fR\u0016\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, m27d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE", "()D", "D", "ZERO", "getZERO", "convert", "", PropertyDescriptor.VALUE, "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/time/Duration$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final double getZERO() {
            return Duration.ZERO;
        }

        public final double getINFINITE() {
            return Duration.INFINITE;
        }

        public final double convert(double d, @NotNull TimeUnit sourceUnit, @NotNull TimeUnit targetUnit) {
            Intrinsics.checkParameterIsNotNull(sourceUnit, "sourceUnit");
            Intrinsics.checkParameterIsNotNull(targetUnit, "targetUnit");
            return DurationUnitKt.convertDurationUnit(d, sourceUnit, targetUnit);
        }
    }

    /* renamed from: unaryMinus-impl, reason: not valid java name */
    public static final double m1483unaryMinusimpl(double d) {
        return -d;
    }

    /* renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final double m1484plusLRDsOJo(double d, double d2) {
        return d + d2;
    }

    /* renamed from: minus-LRDsOJo, reason: not valid java name */
    public static final double m1485minusLRDsOJo(double d, double d2) {
        return d - d2;
    }

    /* renamed from: times-impl, reason: not valid java name */
    public static final double m1486timesimpl(double d, int i) {
        return d * i;
    }

    /* renamed from: times-impl, reason: not valid java name */
    public static final double m1487timesimpl(double d, double d2) {
        return d * d2;
    }

    /* renamed from: div-impl, reason: not valid java name */
    public static final double m1488divimpl(double d, int i) {
        return d / i;
    }

    /* renamed from: div-impl, reason: not valid java name */
    public static final double m1489divimpl(double d, double d2) {
        return d / d2;
    }

    /* renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m1490isInfiniteimpl(double d) {
        return Double.isInfinite(d);
    }

    /* renamed from: isFinite-impl, reason: not valid java name */
    public static final boolean m1491isFiniteimpl(double d) {
        return (Double.isInfinite(d) || Double.isNaN(d)) ? false : true;
    }

    /* renamed from: getAbsoluteValue-impl, reason: not valid java name */
    public static final double m1492getAbsoluteValueimpl(double d) {
        return (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1)) < 0 ? m1483unaryMinusimpl(d) : d;
    }

    /* renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m1493compareToLRDsOJo(double d, double d2) {
        return Double.compare(d, d2);
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final Object m1494toComponentsimpl(double d, @NotNull Function5 action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m1505getInDaysimpl(d)), Integer.valueOf(m1498getHoursComponentimpl(d)), Integer.valueOf(m1499getMinutesComponentimpl(d)), Integer.valueOf(m1500getSecondsComponentimpl(d)), Integer.valueOf(m1501getNanosecondsComponentimpl(d)));
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final Object m1495toComponentsimpl(double d, @NotNull Function4 action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m1506getInHoursimpl(d)), Integer.valueOf(m1499getMinutesComponentimpl(d)), Integer.valueOf(m1500getSecondsComponentimpl(d)), Integer.valueOf(m1501getNanosecondsComponentimpl(d)));
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final Object m1496toComponentsimpl(double d, @NotNull Function3 action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m1507getInMinutesimpl(d)), Integer.valueOf(m1500getSecondsComponentimpl(d)), Integer.valueOf(m1501getNanosecondsComponentimpl(d)));
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final Object m1497toComponentsimpl(double d, @NotNull Function2 action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Long.valueOf((long) m1508getInSecondsimpl(d)), Integer.valueOf(m1501getNanosecondsComponentimpl(d)));
    }

    /* renamed from: getHoursComponent-impl, reason: not valid java name */
    public static final int m1498getHoursComponentimpl(double d) {
        return (int) (m1506getInHoursimpl(d) % 24.0d);
    }

    /* renamed from: getMinutesComponent-impl, reason: not valid java name */
    public static final int m1499getMinutesComponentimpl(double d) {
        return (int) (m1507getInMinutesimpl(d) % 60.0d);
    }

    /* renamed from: getSecondsComponent-impl, reason: not valid java name */
    public static final int m1500getSecondsComponentimpl(double d) {
        return (int) (m1508getInSecondsimpl(d) % 60.0d);
    }

    /* renamed from: getNanosecondsComponent-impl, reason: not valid java name */
    public static final int m1501getNanosecondsComponentimpl(double d) {
        return (int) (m1511getInNanosecondsimpl(d) % 1.0E9d);
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    public static final double m1502toDoubleimpl(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return DurationUnitKt.convertDurationUnit(d, DurationKt.getStorageUnit(), unit);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m1503toLongimpl(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return (long) m1502toDoubleimpl(d, unit);
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    public static final int m1504toIntimpl(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return (int) m1502toDoubleimpl(d, unit);
    }

    /* renamed from: getInDays-impl, reason: not valid java name */
    public static final double m1505getInDaysimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.DAYS);
    }

    /* renamed from: getInHours-impl, reason: not valid java name */
    public static final double m1506getInHoursimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.HOURS);
    }

    /* renamed from: getInMinutes-impl, reason: not valid java name */
    public static final double m1507getInMinutesimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.MINUTES);
    }

    /* renamed from: getInSeconds-impl, reason: not valid java name */
    public static final double m1508getInSecondsimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.SECONDS);
    }

    /* renamed from: getInMilliseconds-impl, reason: not valid java name */
    public static final double m1509getInMillisecondsimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.MILLISECONDS);
    }

    /* renamed from: getInMicroseconds-impl, reason: not valid java name */
    public static final double m1510getInMicrosecondsimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.MICROSECONDS);
    }

    /* renamed from: getInNanoseconds-impl, reason: not valid java name */
    public static final double m1511getInNanosecondsimpl(double d) {
        return m1502toDoubleimpl(d, TimeUnit.NANOSECONDS);
    }

    /* renamed from: toLongNanoseconds-impl, reason: not valid java name */
    public static final long m1512toLongNanosecondsimpl(double d) {
        return m1503toLongimpl(d, TimeUnit.NANOSECONDS);
    }

    /* renamed from: toLongMilliseconds-impl, reason: not valid java name */
    public static final long m1513toLongMillisecondsimpl(double d) {
        return m1503toLongimpl(d, TimeUnit.MILLISECONDS);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m1514toStringimpl(double d) {
        TimeUnit timeUnit;
        int i;
        String toExactDecimals;
        if (m1490isInfiniteimpl(d)) {
            return String.valueOf(d);
        }
        if (d == 0.0d) {
            return "0s";
        }
        double dM1511getInNanosecondsimpl = m1511getInNanosecondsimpl(m1492getAbsoluteValueimpl(d));
        boolean z = false;
        int i2 = 0;
        if (dM1511getInNanosecondsimpl < 1.0E-6d) {
            z = true;
            timeUnit = TimeUnit.SECONDS;
        } else if (dM1511getInNanosecondsimpl < 1.0d) {
            i2 = 7;
            timeUnit = TimeUnit.NANOSECONDS;
        } else if (dM1511getInNanosecondsimpl < 1000.0d) {
            timeUnit = TimeUnit.NANOSECONDS;
        } else if (dM1511getInNanosecondsimpl < 1000000.0d) {
            timeUnit = TimeUnit.MICROSECONDS;
        } else if (dM1511getInNanosecondsimpl < 1.0E9d) {
            timeUnit = TimeUnit.MILLISECONDS;
        } else if (dM1511getInNanosecondsimpl < 1.0E12d) {
            timeUnit = TimeUnit.SECONDS;
        } else if (dM1511getInNanosecondsimpl < 6.0E13d) {
            timeUnit = TimeUnit.MINUTES;
        } else if (dM1511getInNanosecondsimpl < 3.6E15d) {
            timeUnit = TimeUnit.HOURS;
        } else if (dM1511getInNanosecondsimpl < 8.64E20d) {
            timeUnit = TimeUnit.DAYS;
        } else {
            z = true;
            timeUnit = TimeUnit.DAYS;
        }
        TimeUnit timeUnit2 = timeUnit;
        double dM1502toDoubleimpl = m1502toDoubleimpl(d, timeUnit2);
        StringBuilder sb = new StringBuilder();
        if (z) {
            toExactDecimals = FormatToDecimalsKt.formatScientific(dM1502toDoubleimpl);
        } else if (i2 > 0) {
            toExactDecimals = FormatToDecimalsKt.formatUpToDecimals(dM1502toDoubleimpl, i2);
        } else {
            double dAbs = Math.abs(dM1502toDoubleimpl);
            sb = sb;
            if (dAbs < 1.0d) {
                i = 3;
            } else if (dAbs < 10.0d) {
                i = 2;
            } else {
                i = dAbs < 100.0d ? 1 : 0;
            }
            toExactDecimals = FormatToDecimalsKt.formatToExactDecimals(dM1502toDoubleimpl, i);
        }
        return sb.append(toExactDecimals).append(DurationUnitKt.shortName(timeUnit2)).toString();
    }

    /* renamed from: toString-impl$default, reason: not valid java name */
    public static String m1516toStringimpl$default(double d, TimeUnit timeUnit, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return m1515toStringimpl(d, timeUnit, i);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static final String m1515toStringimpl(double d, @NotNull TimeUnit unit, int i) {
        String scientific;
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("decimals must be not negative, but was " + i).toString());
        }
        if (m1490isInfiniteimpl(d)) {
            return String.valueOf(d);
        }
        double dM1502toDoubleimpl = m1502toDoubleimpl(d, unit);
        StringBuilder sb = new StringBuilder();
        if (Math.abs(dM1502toDoubleimpl) < 1.0E14d) {
            scientific = FormatToDecimalsKt.formatToExactDecimals(dM1502toDoubleimpl, RangesKt.coerceAtMost(i, 12));
        } else {
            scientific = FormatToDecimalsKt.formatScientific(dM1502toDoubleimpl);
        }
        return sb.append(scientific).append(DurationUnitKt.shortName(unit)).toString();
    }

    @NotNull
    /* renamed from: toIsoString-impl, reason: not valid java name */
    public static final String m1517toIsoStringimpl(double d) {
        StringBuilder sb = new StringBuilder();
        if (d < 0.0d) {
            sb.append('-');
        }
        sb.append("PT");
        double dM1492getAbsoluteValueimpl = m1492getAbsoluteValueimpl(d);
        int iM1506getInHoursimpl = (int) m1506getInHoursimpl(dM1492getAbsoluteValueimpl);
        int iM1499getMinutesComponentimpl = m1499getMinutesComponentimpl(dM1492getAbsoluteValueimpl);
        int iM1500getSecondsComponentimpl = m1500getSecondsComponentimpl(dM1492getAbsoluteValueimpl);
        int iM1501getNanosecondsComponentimpl = m1501getNanosecondsComponentimpl(dM1492getAbsoluteValueimpl);
        boolean z = iM1506getInHoursimpl != 0;
        boolean z2 = (iM1500getSecondsComponentimpl == 0 && iM1501getNanosecondsComponentimpl == 0) ? false : true;
        boolean z3 = iM1499getMinutesComponentimpl != 0 || (z2 && z);
        if (z) {
            sb.append(iM1506getInHoursimpl).append('H');
        }
        if (z3) {
            sb.append(iM1499getMinutesComponentimpl).append('M');
        }
        if (z2 || (!z && !z3)) {
            sb.append(iM1500getSecondsComponentimpl);
            if (iM1501getNanosecondsComponentimpl != 0) {
                sb.append('.');
                String strPadStart = StringsKt.padStart(String.valueOf(iM1501getNanosecondsComponentimpl), 9, '0');
                if (iM1501getNanosecondsComponentimpl % 1000000 == 0) {
                    sb.append((CharSequence) strPadStart, 0, 3);
                } else if (iM1501getNanosecondsComponentimpl % 1000 == 0) {
                    sb.append((CharSequence) strPadStart, 0, 6);
                } else {
                    sb.append(strPadStart);
                }
            }
            sb.append('S');
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}

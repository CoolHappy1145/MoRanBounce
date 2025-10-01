package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdn\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\ufffd\ufffd f2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0097\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#H\u00d6\u0003J\t\u0010$\u001a\u00020\rH\u00d6\u0001J\u0013\u0010%\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\ufffd\ufffdH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b*\u0010\u000fJ\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b+\u0010\u0012J\u001b\u0010)\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b1\u0010\u000fJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b2\u0010\u0012J\u001b\u00100\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b:\u0010\u000fJ\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b;\u0010\u0012J\u001b\u00109\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b?\u0010\u000fJ\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b@\u0010\u0012J\u001b\u0010>\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020\u0003H\u0087\b\u00a2\u0006\u0004\bD\u0010\u0005J\u0010\u0010E\u001a\u00020FH\u0087\b\u00a2\u0006\u0004\bG\u0010HJ\u0010\u0010I\u001a\u00020JH\u0087\b\u00a2\u0006\u0004\bK\u0010LJ\u0010\u0010M\u001a\u00020\rH\u0087\b\u00a2\u0006\u0004\bN\u0010OJ\u0010\u0010P\u001a\u00020QH\u0087\b\u00a2\u0006\u0004\bR\u0010SJ\u0010\u0010T\u001a\u00020UH\u0087\b\u00a2\u0006\u0004\bV\u0010WJ\u000f\u0010X\u001a\u00020YH\u0016\u00a2\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\ufffd\ufffdH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b]\u0010\u0005J\u0013\u0010^\u001a\u00020\u0010H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b_\u0010OJ\u0013\u0010`\u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\ba\u0010SJ\u0013\u0010b\u001a\u00020\u0016H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bc\u0010WJ\u001b\u0010d\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\ufffd\ufffdX\u0081\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\u0006\u0010\u0007\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006g"}, m27d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "data$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "(B)I", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/UByte.class */
public final class UByte implements Comparable {
    private final byte data;
    public static final byte MIN_VALUE = 0;
    public static final byte MAX_VALUE = -1;
    public static final int SIZE_BYTES = 1;
    public static final int SIZE_BITS = 8;
    public static final Companion Companion = new Companion(null);

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private int m511compareTo7apg3OU(byte b) {
        return m512compareTo7apg3OU(this.data, b);
    }

    @NotNull
    public String toString() {
        return m546toStringimpl(this.data);
    }

    @NotNull
    /* renamed from: box-impl, reason: not valid java name */
    public static final UByte m547boximpl(byte b) {
        return new UByte(b);
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m548equalsimpl(byte b, @Nullable Object obj) {
        if (obj instanceof UByte) {
            return b == ((UByte) obj).m549unboximpl();
        }
        return false;
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final byte m549unboximpl() {
        return this.data;
    }

    public int hashCode() {
        return this.data;
    }

    public boolean equals(Object obj) {
        return m548equalsimpl(this.data, obj);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return m511compareTo7apg3OU(((UByte) obj).m549unboximpl());
    }

    @PublishedApi
    private UByte(byte b) {
        this.data = b;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086T\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086T\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, m27d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/UByte$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static int m512compareTo7apg3OU(byte b, byte b2) {
        int i = b & 255;
        int i2 = b2 & 255;
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static final int m513compareToxj2QHRw(byte b, short s) {
        int i = b & 255;
        int i2 = s & 65535;
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m514compareToWZ4Q5Ns(byte b, int i) {
        return UnsignedKt.uintCompare(b & 255, i);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m515compareToVKZWuLQ(byte b, long j) {
        return (((b & 255) ^ Long.MIN_VALUE) > (j ^ Long.MIN_VALUE) ? 1 : (((b & 255) ^ Long.MIN_VALUE) == (j ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m516plus7apg3OU(byte b, byte b2) {
        return (b & 255) + (b2 & 255);
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m517plusxj2QHRw(byte b, short s) {
        return (b & 255) + (s & 65535);
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m518plusWZ4Q5Ns(byte b, int i) {
        return (b & 255) + i;
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m519plusVKZWuLQ(byte b, long j) {
        return (b & 255) + j;
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m520minus7apg3OU(byte b, byte b2) {
        return (b & 255) - (b2 & 255);
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m521minusxj2QHRw(byte b, short s) {
        return (b & 255) - (s & 65535);
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m522minusWZ4Q5Ns(byte b, int i) {
        return (b & 255) - i;
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m523minusVKZWuLQ(byte b, long j) {
        return (b & 255) - j;
    }

    @InlineOnly
    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m524times7apg3OU(byte b, byte b2) {
        return (b & 255) * (b2 & 255);
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m525timesxj2QHRw(byte b, short s) {
        return (b & 255) * (s & 65535);
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m526timesWZ4Q5Ns(byte b, int i) {
        return (b & 255) * i;
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m527timesVKZWuLQ(byte b, long j) {
        return (b & 255) * j;
    }

    @InlineOnly
    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m528div7apg3OU(byte b, byte b2) {
        return UnsignedKt.m765uintDivideJ1ME1BU(b & 255, b2 & 255);
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m529divxj2QHRw(byte b, short s) {
        return UnsignedKt.m765uintDivideJ1ME1BU(b & 255, s & 65535);
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m530divWZ4Q5Ns(byte b, int i) {
        return UnsignedKt.m765uintDivideJ1ME1BU(b & 255, i);
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m531divVKZWuLQ(byte b, long j) {
        return UnsignedKt.m767ulongDivideeb3DHEI(b & 255, j);
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m532rem7apg3OU(byte b, byte b2) {
        return UnsignedKt.m766uintRemainderJ1ME1BU(b & 255, b2 & 255);
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m533remxj2QHRw(byte b, short s) {
        return UnsignedKt.m766uintRemainderJ1ME1BU(b & 255, s & 65535);
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m534remWZ4Q5Ns(byte b, int i) {
        return UnsignedKt.m766uintRemainderJ1ME1BU(b & 255, i);
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m535remVKZWuLQ(byte b, long j) {
        return UnsignedKt.m768ulongRemaindereb3DHEI(b & 255, j);
    }

    @InlineOnly
    /* renamed from: inc-impl, reason: not valid java name */
    private static final byte m536incimpl(byte b) {
        return (byte) (b + 1);
    }

    @InlineOnly
    /* renamed from: dec-impl, reason: not valid java name */
    private static final byte m537decimpl(byte b) {
        return (byte) (b - 1);
    }

    @InlineOnly
    /* renamed from: rangeTo-7apg3OU, reason: not valid java name */
    private static final UIntRange m538rangeTo7apg3OU(byte b, byte b2) {
        return new UIntRange(b & 255, b2 & 255, null);
    }

    @InlineOnly
    /* renamed from: and-7apg3OU, reason: not valid java name */
    private static final byte m539and7apg3OU(byte b, byte b2) {
        return (byte) (b & b2);
    }

    @InlineOnly
    /* renamed from: or-7apg3OU, reason: not valid java name */
    private static final byte m540or7apg3OU(byte b, byte b2) {
        return (byte) (b | b2);
    }

    @InlineOnly
    /* renamed from: xor-7apg3OU, reason: not valid java name */
    private static final byte m541xor7apg3OU(byte b, byte b2) {
        return (byte) (b ^ b2);
    }

    @InlineOnly
    /* renamed from: inv-impl, reason: not valid java name */
    private static final byte m542invimpl(byte b) {
        return (byte) (b ^ (-1));
    }

    @InlineOnly
    /* renamed from: toUShort-impl, reason: not valid java name */
    private static final short m543toUShortimpl(byte b) {
        return (short) (b & 255);
    }

    @InlineOnly
    /* renamed from: toUInt-impl, reason: not valid java name */
    private static final int m544toUIntimpl(byte b) {
        return b & 255;
    }

    @InlineOnly
    /* renamed from: toULong-impl, reason: not valid java name */
    private static final long m545toULongimpl(byte b) {
        return b & 255;
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m546toStringimpl(byte b) {
        return String.valueOf(b & 255);
    }
}

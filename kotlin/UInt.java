package kotlin;

import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdn\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\ufffd\ufffd j2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001jB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0097\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0018\u0010\u0005J\u001b\u0010\u0019\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001a\u0010\u000fJ\u001b\u0010\u0019\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001b\u0010\u000bJ\u001b\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u0019\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u0016J\u0013\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001J\u0013\u0010#\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b$\u0010\u0005J\u0013\u0010%\u001a\u00020\ufffd\ufffdH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b&\u0010\u0005J\u001b\u0010'\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b(\u0010\u000fJ\u001b\u0010'\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b)\u0010\u000bJ\u001b\u0010'\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b*\u0010\u001dJ\u001b\u0010'\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b+\u0010\u0016J\u001b\u0010,\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b-\u0010\u000bJ\u001b\u0010.\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b/\u0010\u000fJ\u001b\u0010.\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b0\u0010\u000bJ\u001b\u0010.\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b1\u0010\u001dJ\u001b\u0010.\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b2\u0010\u0016J\u001b\u00103\u001a\u0002042\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b5\u00106J\u001b\u00107\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b8\u0010\u000fJ\u001b\u00107\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b9\u0010\u000bJ\u001b\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b:\u0010\u001dJ\u001b\u00107\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b;\u0010\u0016J\u001b\u0010<\u001a\u00020\ufffd\ufffd2\u0006\u0010=\u001a\u00020\u0003H\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\ufffd\ufffd2\u0006\u0010=\u001a\u00020\u0003H\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b@\u0010\u000bJ\u001b\u0010A\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bB\u0010\u000fJ\u001b\u0010A\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bC\u0010\u000bJ\u001b\u0010A\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bD\u0010\u001dJ\u001b\u0010A\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bE\u0010\u0016J\u0010\u0010F\u001a\u00020GH\u0087\b\u00a2\u0006\u0004\bH\u0010IJ\u0010\u0010J\u001a\u00020KH\u0087\b\u00a2\u0006\u0004\bL\u0010MJ\u0010\u0010N\u001a\u00020OH\u0087\b\u00a2\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020\u0003H\u0087\b\u00a2\u0006\u0004\bS\u0010\u0005J\u0010\u0010T\u001a\u00020UH\u0087\b\u00a2\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b\u00a2\u0006\u0004\bZ\u0010[J\u000f\u0010\\\u001a\u00020]H\u0016\u00a2\u0006\u0004\b^\u0010_J\u0013\u0010`\u001a\u00020\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\ba\u0010IJ\u0013\u0010b\u001a\u00020\ufffd\ufffdH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bc\u0010\u0005J\u0013\u0010d\u001a\u00020\u0011H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\be\u0010WJ\u0013\u0010f\u001a\u00020\u0014H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bg\u0010[J\u001b\u0010h\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bi\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\ufffd\ufffdX\u0081\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\u0006\u0010\u0007\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006k"}, m27d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "data$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-impl", "shr", "shr-impl", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/UInt.class */
public final class UInt implements Comparable {
    private final int data;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = -1;
    public static final int SIZE_BYTES = 4;
    public static final int SIZE_BITS = 32;
    public static final Companion Companion = new Companion(null);

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private int m565compareToWZ4Q5Ns(int i) {
        return m568compareToWZ4Q5Ns(this.data, i);
    }

    @NotNull
    public String toString() {
        return m604toStringimpl(this.data);
    }

    @NotNull
    /* renamed from: box-impl, reason: not valid java name */
    public static final UInt m605boximpl(int i) {
        return new UInt(i);
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m606equalsimpl(int i, @Nullable Object obj) {
        if (obj instanceof UInt) {
            return i == ((UInt) obj).m607unboximpl();
        }
        return false;
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final int m607unboximpl() {
        return this.data;
    }

    public int hashCode() {
        return this.data;
    }

    public boolean equals(Object obj) {
        return m606equalsimpl(this.data, obj);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return m565compareToWZ4Q5Ns(((UInt) obj).m607unboximpl());
    }

    @PublishedApi
    private UInt(int i) {
        this.data = i;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086T\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086T\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, m27d2 = {"Lkotlin/UInt$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UInt;", "I", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/UInt$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m566compareTo7apg3OU(int i, byte b) {
        return UnsignedKt.uintCompare(i, b & 255);
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static final int m567compareToxj2QHRw(int i, short s) {
        return UnsignedKt.uintCompare(i, s & 65535);
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static int m568compareToWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m569compareToVKZWuLQ(int i, long j) {
        return (((i & JSType.MAX_UINT) ^ Long.MIN_VALUE) > (j ^ Long.MIN_VALUE) ? 1 : (((i & JSType.MAX_UINT) ^ Long.MIN_VALUE) == (j ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m570plus7apg3OU(int i, byte b) {
        return i + (b & 255);
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m571plusxj2QHRw(int i, short s) {
        return i + (s & 65535);
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m572plusWZ4Q5Ns(int i, int i2) {
        return i + i2;
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m573plusVKZWuLQ(int i, long j) {
        return (i & JSType.MAX_UINT) + j;
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m574minus7apg3OU(int i, byte b) {
        return i - (b & 255);
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m575minusxj2QHRw(int i, short s) {
        return i - (s & 65535);
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m576minusWZ4Q5Ns(int i, int i2) {
        return i - i2;
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m577minusVKZWuLQ(int i, long j) {
        return (i & JSType.MAX_UINT) - j;
    }

    @InlineOnly
    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m578times7apg3OU(int i, byte b) {
        return i * (b & 255);
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m579timesxj2QHRw(int i, short s) {
        return i * (s & 65535);
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m580timesWZ4Q5Ns(int i, int i2) {
        return i * i2;
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m581timesVKZWuLQ(int i, long j) {
        return (i & JSType.MAX_UINT) * j;
    }

    @InlineOnly
    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m582div7apg3OU(int i, byte b) {
        return UnsignedKt.m765uintDivideJ1ME1BU(i, b & 255);
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m583divxj2QHRw(int i, short s) {
        return UnsignedKt.m765uintDivideJ1ME1BU(i, s & 65535);
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m584divWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.m765uintDivideJ1ME1BU(i, i2);
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m585divVKZWuLQ(int i, long j) {
        return UnsignedKt.m767ulongDivideeb3DHEI(i & JSType.MAX_UINT, j);
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m586rem7apg3OU(int i, byte b) {
        return UnsignedKt.m766uintRemainderJ1ME1BU(i, b & 255);
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m587remxj2QHRw(int i, short s) {
        return UnsignedKt.m766uintRemainderJ1ME1BU(i, s & 65535);
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m588remWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.m766uintRemainderJ1ME1BU(i, i2);
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m589remVKZWuLQ(int i, long j) {
        return UnsignedKt.m768ulongRemaindereb3DHEI(i & JSType.MAX_UINT, j);
    }

    @InlineOnly
    /* renamed from: inc-impl, reason: not valid java name */
    private static final int m590incimpl(int i) {
        return i + 1;
    }

    @InlineOnly
    /* renamed from: dec-impl, reason: not valid java name */
    private static final int m591decimpl(int i) {
        return i - 1;
    }

    @InlineOnly
    /* renamed from: rangeTo-WZ4Q5Ns, reason: not valid java name */
    private static final UIntRange m592rangeToWZ4Q5Ns(int i, int i2) {
        return new UIntRange(i, i2, null);
    }

    @InlineOnly
    /* renamed from: shl-impl, reason: not valid java name */
    private static final int m593shlimpl(int i, int i2) {
        return i << i2;
    }

    @InlineOnly
    /* renamed from: shr-impl, reason: not valid java name */
    private static final int m594shrimpl(int i, int i2) {
        return i >>> i2;
    }

    @InlineOnly
    /* renamed from: and-WZ4Q5Ns, reason: not valid java name */
    private static final int m595andWZ4Q5Ns(int i, int i2) {
        return i & i2;
    }

    @InlineOnly
    /* renamed from: or-WZ4Q5Ns, reason: not valid java name */
    private static final int m596orWZ4Q5Ns(int i, int i2) {
        return i | i2;
    }

    @InlineOnly
    /* renamed from: xor-WZ4Q5Ns, reason: not valid java name */
    private static final int m597xorWZ4Q5Ns(int i, int i2) {
        return i ^ i2;
    }

    @InlineOnly
    /* renamed from: inv-impl, reason: not valid java name */
    private static final int m598invimpl(int i) {
        return i ^ (-1);
    }

    @InlineOnly
    /* renamed from: toUByte-impl, reason: not valid java name */
    private static final byte m599toUByteimpl(int i) {
        return (byte) i;
    }

    @InlineOnly
    /* renamed from: toUShort-impl, reason: not valid java name */
    private static final short m600toUShortimpl(int i) {
        return (short) i;
    }

    @InlineOnly
    /* renamed from: toULong-impl, reason: not valid java name */
    private static final long m601toULongimpl(int i) {
        return i & JSType.MAX_UINT;
    }

    @InlineOnly
    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m602toFloatimpl(int i) {
        return (float) ((i & Integer.MAX_VALUE) + (((i >>> 31) << 30) * 2.0d));
    }

    @InlineOnly
    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m603toDoubleimpl(int i) {
        return (i & Integer.MAX_VALUE) + (((i >>> 31) << 30) * 2.0d);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m604toStringimpl(int i) {
        return String.valueOf(i & JSType.MAX_UINT);
    }
}

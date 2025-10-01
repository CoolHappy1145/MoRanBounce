package kotlin;

import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdj\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\ufffd\ufffd m2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001mB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0097\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b \u0010\u000bJ\u001b\u0010\u001b\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b!\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010\t\u001a\u0004\u0018\u00010%H\u00d6\u0003J\t\u0010&\u001a\u00020\rH\u00d6\u0001J\u0013\u0010'\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b(\u0010\u0005J\u0013\u0010)\u001a\u00020\ufffd\ufffdH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b*\u0010\u0005J\u001b\u0010+\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b,\u0010\u001dJ\u001b\u0010+\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b-\u0010\u001fJ\u001b\u0010+\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b.\u0010\u000bJ\u001b\u0010+\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b/\u0010\"J\u001b\u00100\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b1\u0010\u000bJ\u001b\u00102\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b3\u0010\u001dJ\u001b\u00102\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b4\u0010\u001fJ\u001b\u00102\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b5\u0010\u000bJ\u001b\u00102\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b6\u0010\"J\u001b\u00107\u001a\u0002082\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b9\u0010:J\u001b\u0010;\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b<\u0010\u001dJ\u001b\u0010;\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b=\u0010\u001fJ\u001b\u0010;\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b>\u0010\u000bJ\u001b\u0010;\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b?\u0010\"J\u001b\u0010@\u001a\u00020\ufffd\ufffd2\u0006\u0010A\u001a\u00020\rH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bB\u0010\u001fJ\u001b\u0010C\u001a\u00020\ufffd\ufffd2\u0006\u0010A\u001a\u00020\rH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bD\u0010\u001fJ\u001b\u0010E\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bF\u0010\u001dJ\u001b\u0010E\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bG\u0010\u001fJ\u001b\u0010E\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bH\u0010\u000bJ\u001b\u0010E\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bI\u0010\"J\u0010\u0010J\u001a\u00020KH\u0087\b\u00a2\u0006\u0004\bL\u0010MJ\u0010\u0010N\u001a\u00020OH\u0087\b\u00a2\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\u0087\b\u00a2\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020\rH\u0087\b\u00a2\u0006\u0004\bW\u0010XJ\u0010\u0010Y\u001a\u00020\u0003H\u0087\b\u00a2\u0006\u0004\bZ\u0010\u0005J\u0010\u0010[\u001a\u00020\\H\u0087\b\u00a2\u0006\u0004\b]\u0010^J\u000f\u0010_\u001a\u00020`H\u0016\u00a2\u0006\u0004\ba\u0010bJ\u0013\u0010c\u001a\u00020\u000eH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bd\u0010MJ\u0013\u0010e\u001a\u00020\u0011H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bf\u0010XJ\u0013\u0010g\u001a\u00020\ufffd\ufffdH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bh\u0010\u0005J\u0013\u0010i\u001a\u00020\u0016H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bj\u0010^J\u001b\u0010k\u001a\u00020\ufffd\ufffd2\u0006\u0010\t\u001a\u00020\ufffd\ufffdH\u0087\f\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bl\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\ufffd\ufffdX\u0081\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\u0006\u0010\u0007\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006n"}, m27d2 = {"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "data$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-impl", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-impl", "shr", "shr-impl", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(J)B", "toDouble", "", "toDouble-impl", "(J)D", "toFloat", "", "toFloat-impl", "(J)F", "toInt", "toInt-impl", "(J)I", "toLong", "toLong-impl", "toShort", "", "toShort-impl", "(J)S", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"})
@ExperimentalUnsignedTypes
/* loaded from: L-out.jar:kotlin/ULong.class */
public final class ULong implements Comparable {
    private final long data;
    public static final long MIN_VALUE = 0;
    public static final long MAX_VALUE = -1;
    public static final int SIZE_BYTES = 8;
    public static final int SIZE_BITS = 64;
    public static final Companion Companion = new Companion(null);

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private int m623compareToVKZWuLQ(long j) {
        return m627compareToVKZWuLQ(this.data, j);
    }

    @NotNull
    public String toString() {
        return m662toStringimpl(this.data);
    }

    @NotNull
    /* renamed from: box-impl, reason: not valid java name */
    public static final ULong m663boximpl(long j) {
        return new ULong(j);
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m664equalsimpl(long j, @Nullable Object obj) {
        if (obj instanceof ULong) {
            return (j > ((ULong) obj).m665unboximpl() ? 1 : (j == ((ULong) obj).m665unboximpl() ? 0 : -1)) == 0;
        }
        return false;
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final long m665unboximpl() {
        return this.data;
    }

    public int hashCode() {
        long j = this.data;
        return (int) (j ^ (j >>> 32));
    }

    public boolean equals(Object obj) {
        return m664equalsimpl(this.data, obj);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return m623compareToVKZWuLQ(((ULong) obj).m665unboximpl());
    }

    @PublishedApi
    private ULong(long j) {
        this.data = j;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086T\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086T\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, m27d2 = {"Lkotlin/ULong$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/ULong;", "J", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/ULong$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m624compareTo7apg3OU(long j, byte b) {
        return ((j ^ Long.MIN_VALUE) > ((b & 255) ^ Long.MIN_VALUE) ? 1 : ((j ^ Long.MIN_VALUE) == ((b & 255) ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static final int m625compareToxj2QHRw(long j, short s) {
        return ((j ^ Long.MIN_VALUE) > ((s & 65535) ^ Long.MIN_VALUE) ? 1 : ((j ^ Long.MIN_VALUE) == ((s & 65535) ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m626compareToWZ4Q5Ns(long j, int i) {
        return ((j ^ Long.MIN_VALUE) > ((i & JSType.MAX_UINT) ^ Long.MIN_VALUE) ? 1 : ((j ^ Long.MIN_VALUE) == ((i & JSType.MAX_UINT) ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static int m627compareToVKZWuLQ(long j, long j2) {
        return ((j ^ Long.MIN_VALUE) > (j2 ^ Long.MIN_VALUE) ? 1 : ((j ^ Long.MIN_VALUE) == (j2 ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final long m628plus7apg3OU(long j, byte b) {
        return j + (b & 255);
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final long m629plusxj2QHRw(long j, short s) {
        return j + (s & 65535);
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final long m630plusWZ4Q5Ns(long j, int i) {
        return j + (i & JSType.MAX_UINT);
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m631plusVKZWuLQ(long j, long j2) {
        return j + j2;
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final long m632minus7apg3OU(long j, byte b) {
        return j - (b & 255);
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final long m633minusxj2QHRw(long j, short s) {
        return j - (s & 65535);
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final long m634minusWZ4Q5Ns(long j, int i) {
        return j - (i & JSType.MAX_UINT);
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m635minusVKZWuLQ(long j, long j2) {
        return j - j2;
    }

    @InlineOnly
    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final long m636times7apg3OU(long j, byte b) {
        return j * (b & 255);
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final long m637timesxj2QHRw(long j, short s) {
        return j * (s & 65535);
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final long m638timesWZ4Q5Ns(long j, int i) {
        return j * (i & JSType.MAX_UINT);
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m639timesVKZWuLQ(long j, long j2) {
        return j * j2;
    }

    @InlineOnly
    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final long m640div7apg3OU(long j, byte b) {
        return UnsignedKt.m767ulongDivideeb3DHEI(j, b & 255);
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final long m641divxj2QHRw(long j, short s) {
        return UnsignedKt.m767ulongDivideeb3DHEI(j, s & 65535);
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final long m642divWZ4Q5Ns(long j, int i) {
        return UnsignedKt.m767ulongDivideeb3DHEI(j, i & JSType.MAX_UINT);
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m643divVKZWuLQ(long j, long j2) {
        return UnsignedKt.m767ulongDivideeb3DHEI(j, j2);
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final long m644rem7apg3OU(long j, byte b) {
        return UnsignedKt.m768ulongRemaindereb3DHEI(j, b & 255);
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final long m645remxj2QHRw(long j, short s) {
        return UnsignedKt.m768ulongRemaindereb3DHEI(j, s & 65535);
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final long m646remWZ4Q5Ns(long j, int i) {
        return UnsignedKt.m768ulongRemaindereb3DHEI(j, i & JSType.MAX_UINT);
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m647remVKZWuLQ(long j, long j2) {
        return UnsignedKt.m768ulongRemaindereb3DHEI(j, j2);
    }

    @InlineOnly
    /* renamed from: inc-impl, reason: not valid java name */
    private static final long m648incimpl(long j) {
        return j + 1;
    }

    @InlineOnly
    /* renamed from: dec-impl, reason: not valid java name */
    private static final long m649decimpl(long j) {
        return j - 1;
    }

    @InlineOnly
    /* renamed from: rangeTo-VKZWuLQ, reason: not valid java name */
    private static final ULongRange m650rangeToVKZWuLQ(long j, long j2) {
        return new ULongRange(j, j2, null);
    }

    @InlineOnly
    /* renamed from: shl-impl, reason: not valid java name */
    private static final long m651shlimpl(long j, int i) {
        return j << i;
    }

    @InlineOnly
    /* renamed from: shr-impl, reason: not valid java name */
    private static final long m652shrimpl(long j, int i) {
        return j >>> i;
    }

    @InlineOnly
    /* renamed from: and-VKZWuLQ, reason: not valid java name */
    private static final long m653andVKZWuLQ(long j, long j2) {
        return j & j2;
    }

    @InlineOnly
    /* renamed from: or-VKZWuLQ, reason: not valid java name */
    private static final long m654orVKZWuLQ(long j, long j2) {
        return j | j2;
    }

    @InlineOnly
    /* renamed from: xor-VKZWuLQ, reason: not valid java name */
    private static final long m655xorVKZWuLQ(long j, long j2) {
        return j ^ j2;
    }

    @InlineOnly
    /* renamed from: inv-impl, reason: not valid java name */
    private static final long m656invimpl(long j) {
        return j ^ (-1);
    }

    @InlineOnly
    /* renamed from: toUByte-impl, reason: not valid java name */
    private static final byte m657toUByteimpl(long j) {
        return (byte) j;
    }

    @InlineOnly
    /* renamed from: toUShort-impl, reason: not valid java name */
    private static final short m658toUShortimpl(long j) {
        return (short) j;
    }

    @InlineOnly
    /* renamed from: toUInt-impl, reason: not valid java name */
    private static final int m659toUIntimpl(long j) {
        return (int) j;
    }

    @InlineOnly
    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m660toFloatimpl(long j) {
        return (float) (((j >>> 11) * 2048.0d) + (j & 2047));
    }

    @InlineOnly
    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m661toDoubleimpl(long j) {
        return ((j >>> 11) * 2048.0d) + (j & 2047);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m662toStringimpl(long j) {
        return UnsignedKt.ulongToString(j);
    }
}

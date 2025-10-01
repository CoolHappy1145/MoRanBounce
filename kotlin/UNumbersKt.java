package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd&\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b)\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0005H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\t\u0010\n\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\f\u0010\r\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000f\u0010\u0004\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u0005H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u0007\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\n\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0012\u0010\r\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0014\u0010\u0004\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u0005H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0007\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0016\u0010\n\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0017\u0010\r\u001a\u001f\u0010\u0018\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a\u001f\u0010\u0018\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u001f\u0010\u0018\u001a\u00020\b*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a\u001f\u0010\u0018\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b \u0010!\u001a\u001f\u0010\"\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b#\u0010\u001b\u001a\u001f\u0010\"\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b$\u0010\u001d\u001a\u001f\u0010\"\u001a\u00020\b*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b%\u0010\u001f\u001a\u001f\u0010\"\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b&\u0010!\u001a\u0017\u0010'\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b(\u0010)\u001a\u0017\u0010'\u001a\u00020\u0005*\u00020\u0005H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b*\u0010\u0007\u001a\u0017\u0010'\u001a\u00020\b*\u00020\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b+\u0010,\u001a\u0017\u0010'\u001a\u00020\u000b*\u00020\u000bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b-\u0010.\u001a\u0017\u0010/\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b0\u0010)\u001a\u0017\u0010/\u001a\u00020\u0005*\u00020\u0005H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b1\u0010\u0007\u001a\u0017\u0010/\u001a\u00020\b*\u00020\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b2\u0010,\u001a\u0017\u0010/\u001a\u00020\u000b*\u00020\u000bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b3\u0010.\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00064"}, m27d2 = {"countLeadingZeroBits", "", "Lkotlin/UByte;", "countLeadingZeroBits-7apg3OU", "(B)I", "Lkotlin/UInt;", "countLeadingZeroBits-WZ4Q5Ns", "(I)I", "Lkotlin/ULong;", "countLeadingZeroBits-VKZWuLQ", "(J)I", "Lkotlin/UShort;", "countLeadingZeroBits-xj2QHRw", "(S)I", "countOneBits", "countOneBits-7apg3OU", "countOneBits-WZ4Q5Ns", "countOneBits-VKZWuLQ", "countOneBits-xj2QHRw", "countTrailingZeroBits", "countTrailingZeroBits-7apg3OU", "countTrailingZeroBits-WZ4Q5Ns", "countTrailingZeroBits-VKZWuLQ", "countTrailingZeroBits-xj2QHRw", "rotateLeft", "bitCount", "rotateLeft-LxnNnR4", "(BI)B", "rotateLeft-V7xB4Y4", "(II)I", "rotateLeft-JSWoG40", "(JI)J", "rotateLeft-olVBNx4", "(SI)S", "rotateRight", "rotateRight-LxnNnR4", "rotateRight-V7xB4Y4", "rotateRight-JSWoG40", "rotateRight-olVBNx4", "takeHighestOneBit", "takeHighestOneBit-7apg3OU", "(B)B", "takeHighestOneBit-WZ4Q5Ns", "takeHighestOneBit-VKZWuLQ", "(J)J", "takeHighestOneBit-xj2QHRw", "(S)S", "takeLowestOneBit", "takeLowestOneBit-7apg3OU", "takeLowestOneBit-WZ4Q5Ns", "takeLowestOneBit-VKZWuLQ", "takeLowestOneBit-xj2QHRw", "kotlin-stdlib"})
@JvmName(name = "UNumbersKt")
/* loaded from: L-out.jar:kotlin/UNumbersKt.class */
public final class UNumbersKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countOneBits-WZ4Q5Ns, reason: not valid java name */
    private static final int m682countOneBitsWZ4Q5Ns(int i) {
        return Integer.bitCount(i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countLeadingZeroBits-WZ4Q5Ns, reason: not valid java name */
    private static final int m683countLeadingZeroBitsWZ4Q5Ns(int i) {
        return Integer.numberOfLeadingZeros(i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countTrailingZeroBits-WZ4Q5Ns, reason: not valid java name */
    private static final int m684countTrailingZeroBitsWZ4Q5Ns(int i) {
        return Integer.numberOfTrailingZeros(i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeHighestOneBit-WZ4Q5Ns, reason: not valid java name */
    private static final int m685takeHighestOneBitWZ4Q5Ns(int i) {
        return Integer.highestOneBit(i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeLowestOneBit-WZ4Q5Ns, reason: not valid java name */
    private static final int m686takeLowestOneBitWZ4Q5Ns(int i) {
        return Integer.lowestOneBit(i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateLeft-V7xB4Y4, reason: not valid java name */
    private static final int m687rotateLeftV7xB4Y4(int i, int i2) {
        return Integer.rotateLeft(i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateRight-V7xB4Y4, reason: not valid java name */
    private static final int m688rotateRightV7xB4Y4(int i, int i2) {
        return Integer.rotateRight(i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countOneBits-VKZWuLQ, reason: not valid java name */
    private static final int m689countOneBitsVKZWuLQ(long j) {
        return Long.bitCount(j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countLeadingZeroBits-VKZWuLQ, reason: not valid java name */
    private static final int m690countLeadingZeroBitsVKZWuLQ(long j) {
        return Long.numberOfLeadingZeros(j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countTrailingZeroBits-VKZWuLQ, reason: not valid java name */
    private static final int m691countTrailingZeroBitsVKZWuLQ(long j) {
        return Long.numberOfTrailingZeros(j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeHighestOneBit-VKZWuLQ, reason: not valid java name */
    private static final long m692takeHighestOneBitVKZWuLQ(long j) {
        return Long.highestOneBit(j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeLowestOneBit-VKZWuLQ, reason: not valid java name */
    private static final long m693takeLowestOneBitVKZWuLQ(long j) {
        return Long.lowestOneBit(j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateLeft-JSWoG40, reason: not valid java name */
    private static final long m694rotateLeftJSWoG40(long j, int i) {
        return Long.rotateLeft(j, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateRight-JSWoG40, reason: not valid java name */
    private static final long m695rotateRightJSWoG40(long j, int i) {
        return Long.rotateRight(j, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countOneBits-7apg3OU, reason: not valid java name */
    private static final int m696countOneBits7apg3OU(byte b) {
        return Integer.bitCount(b & 255);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countLeadingZeroBits-7apg3OU, reason: not valid java name */
    private static final int m697countLeadingZeroBits7apg3OU(byte b) {
        return Integer.numberOfLeadingZeros(b & 255) - 24;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countTrailingZeroBits-7apg3OU, reason: not valid java name */
    private static final int m698countTrailingZeroBits7apg3OU(byte b) {
        return Integer.numberOfTrailingZeros(b | 256);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeHighestOneBit-7apg3OU, reason: not valid java name */
    private static final byte m699takeHighestOneBit7apg3OU(byte b) {
        return (byte) Integer.highestOneBit(b & 255);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeLowestOneBit-7apg3OU, reason: not valid java name */
    private static final byte m700takeLowestOneBit7apg3OU(byte b) {
        return (byte) Integer.lowestOneBit(b & 255);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateLeft-LxnNnR4, reason: not valid java name */
    private static final byte m701rotateLeftLxnNnR4(byte b, int i) {
        return NumbersKt.rotateLeft(b, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateRight-LxnNnR4, reason: not valid java name */
    private static final byte m702rotateRightLxnNnR4(byte b, int i) {
        return NumbersKt.rotateRight(b, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countOneBits-xj2QHRw, reason: not valid java name */
    private static final int m703countOneBitsxj2QHRw(short s) {
        return Integer.bitCount(s & 65535);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countLeadingZeroBits-xj2QHRw, reason: not valid java name */
    private static final int m704countLeadingZeroBitsxj2QHRw(short s) {
        return Integer.numberOfLeadingZeros(s & 65535) - 16;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: countTrailingZeroBits-xj2QHRw, reason: not valid java name */
    private static final int m705countTrailingZeroBitsxj2QHRw(short s) {
        return Integer.numberOfTrailingZeros(s | 65536);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeHighestOneBit-xj2QHRw, reason: not valid java name */
    private static final short m706takeHighestOneBitxj2QHRw(short s) {
        return (short) Integer.highestOneBit(s & 65535);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: takeLowestOneBit-xj2QHRw, reason: not valid java name */
    private static final short m707takeLowestOneBitxj2QHRw(short s) {
        return (short) Integer.lowestOneBit(s & 65535);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateLeft-olVBNx4, reason: not valid java name */
    private static final short m708rotateLeftolVBNx4(short s, int i) {
        return NumbersKt.rotateLeft(s, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: rotateRight-olVBNx4, reason: not valid java name */
    private static final short m709rotateRightolVBNx4(short s, int i) {
        return NumbersKt.rotateRight(s, i);
    }
}

package kotlin.comparisons;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UnsignedKt;
import kotlin.internal.InlineOnly;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\"\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\"\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0007\u0010\b\u001a\"\u0010\ufffd\ufffd\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\n\u0010\u000b\u001a+\u0010\ufffd\ufffd\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\f\u0010\r\u001a\"\u0010\ufffd\ufffd\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a+\u0010\ufffd\ufffd\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\"\u0010\ufffd\ufffd\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a+\u0010\ufffd\ufffd\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\"\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0019\u0010\u0005\u001a+\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001a\u0010\b\u001a\"\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001b\u0010\u000b\u001a+\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\r\u001a\"\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001d\u0010\u0010\u001a+\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u0012\u001a\"\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001f\u0010\u0015\u001a+\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b \u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006!"}, m27d2 = {"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-5PvTz6A", "minOf-VKSA0NQ", "kotlin-stdlib"}, m28xs = "kotlin/comparisons/UComparisonsKt")
/* loaded from: L-out.jar:kotlin/comparisons/UComparisonsKt___UComparisonsKt.class */
class UComparisonsKt___UComparisonsKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: maxOf-J1ME1BU, reason: not valid java name */
    public static final int m1309maxOfJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) >= 0 ? i : i2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: maxOf-eb3DHEI, reason: not valid java name */
    public static final long m1310maxOfeb3DHEI(long j, long j2) {
        return (j ^ Long.MIN_VALUE) >= (j2 ^ Long.MIN_VALUE) ? j : j2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: maxOf-Kr8caGY, reason: not valid java name */
    public static final byte m1311maxOfKr8caGY(byte b, byte b2) {
        int i = b & 255;
        int i2 = b2 & 255;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) >= 0 ? b : b2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: maxOf-5PvTz6A, reason: not valid java name */
    public static final short m1312maxOf5PvTz6A(short s, short s2) {
        int i = s & 65535;
        int i2 = s2 & 65535;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) >= 0 ? s : s2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: maxOf-WZ9TVnA, reason: not valid java name */
    private static final int m1313maxOfWZ9TVnA(int i, int i2, int i3) {
        return UComparisonsKt.m1309maxOfJ1ME1BU(i, UComparisonsKt.m1309maxOfJ1ME1BU(i2, i3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: maxOf-sambcqE, reason: not valid java name */
    private static final long m1314maxOfsambcqE(long j, long j2, long j3) {
        return UComparisonsKt.m1310maxOfeb3DHEI(j, UComparisonsKt.m1310maxOfeb3DHEI(j2, j3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: maxOf-b33U2AM, reason: not valid java name */
    private static final byte m1315maxOfb33U2AM(byte b, byte b2, byte b3) {
        return UComparisonsKt.m1311maxOfKr8caGY(b, UComparisonsKt.m1311maxOfKr8caGY(b2, b3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: maxOf-VKSA0NQ, reason: not valid java name */
    private static final short m1316maxOfVKSA0NQ(short s, short s2, short s3) {
        return UComparisonsKt.m1312maxOf5PvTz6A(s, UComparisonsKt.m1312maxOf5PvTz6A(s2, s3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: minOf-J1ME1BU, reason: not valid java name */
    public static final int m1317minOfJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) <= 0 ? i : i2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: minOf-eb3DHEI, reason: not valid java name */
    public static final long m1318minOfeb3DHEI(long j, long j2) {
        return (j ^ Long.MIN_VALUE) <= (j2 ^ Long.MIN_VALUE) ? j : j2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: minOf-Kr8caGY, reason: not valid java name */
    public static final byte m1319minOfKr8caGY(byte b, byte b2) {
        int i = b & 255;
        int i2 = b2 & 255;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) <= 0 ? b : b2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: minOf-5PvTz6A, reason: not valid java name */
    public static final short m1320minOf5PvTz6A(short s, short s2) {
        int i = s & 65535;
        int i2 = s2 & 65535;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) <= 0 ? s : s2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: minOf-WZ9TVnA, reason: not valid java name */
    private static final int m1321minOfWZ9TVnA(int i, int i2, int i3) {
        return UComparisonsKt.m1317minOfJ1ME1BU(i, UComparisonsKt.m1317minOfJ1ME1BU(i2, i3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: minOf-sambcqE, reason: not valid java name */
    private static final long m1322minOfsambcqE(long j, long j2, long j3) {
        return UComparisonsKt.m1318minOfeb3DHEI(j, UComparisonsKt.m1318minOfeb3DHEI(j2, j3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: minOf-b33U2AM, reason: not valid java name */
    private static final byte m1323minOfb33U2AM(byte b, byte b2, byte b3) {
        return UComparisonsKt.m1319minOfKr8caGY(b, UComparisonsKt.m1319minOfKr8caGY(b2, b3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: minOf-VKSA0NQ, reason: not valid java name */
    private static final short m1324minOfVKSA0NQ(short s, short s2, short s3) {
        return UComparisonsKt.m1320minOf5PvTz6A(s, UComparisonsKt.m1320minOf5PvTz6A(s2, s3));
    }
}

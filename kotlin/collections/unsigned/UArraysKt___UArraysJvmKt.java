package kotlin.collections.unsigned;

import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.collections.AbstractList;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd>\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\u001a\u001c\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u000bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\f\u0010\r\u001a\u001c\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u000fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00022\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001d\u0010\u001e\u001a\u001f\u0010\u001f\u001a\u00020\u0002*\u00020\u00032\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b!\u0010\"\u001a\u001f\u0010\u001f\u001a\u00020\u0006*\u00020\u00072\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b#\u0010$\u001a\u001f\u0010\u001f\u001a\u00020\n*\u00020\u000b2\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b%\u0010&\u001a\u001f\u0010\u001f\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b'\u0010(\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006)"}, m27d2 = {"asList", "", "Lkotlin/UByte;", "Lkotlin/UByteArray;", "asList-GBYM_sE", "([B)Ljava/util/List;", "Lkotlin/UInt;", "Lkotlin/UIntArray;", "asList--ajY-9A", "([I)Ljava/util/List;", "Lkotlin/ULong;", "Lkotlin/ULongArray;", "asList-QwZRm1k", "([J)Ljava/util/List;", "Lkotlin/UShort;", "Lkotlin/UShortArray;", "asList-rL5Bavg", "([S)Ljava/util/List;", "binarySearch", "", "element", "fromIndex", "toIndex", "binarySearch-WpHrYlw", "([BBII)I", "binarySearch-2fe2U9s", "([IIII)I", "binarySearch-K6DWlUc", "([JJII)I", "binarySearch-EtDCXyQ", "([SSII)I", "elementAt", "index", "elementAt-PpDY95g", "([BI)B", "elementAt-qFRl0hI", "([II)I", "elementAt-r7IrZao", "([JI)J", "elementAt-nggk6HY", "([SI)S", "kotlin-stdlib"}, m28xs = "kotlin/collections/unsigned/UArraysKt", m29pn = "kotlin.collections")
/* loaded from: L-out.jar:kotlin/collections/unsigned/UArraysKt___UArraysJvmKt.class */
class UArraysKt___UArraysJvmKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: elementAt-qFRl0hI, reason: not valid java name */
    private static final int m819elementAtqFRl0hI(@NotNull int[] iArr, int i) {
        return UIntArray.m610getimpl(iArr, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: elementAt-r7IrZao, reason: not valid java name */
    private static final long m820elementAtr7IrZao(@NotNull long[] jArr, int i) {
        return ULongArray.m668getimpl(jArr, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: elementAt-PpDY95g, reason: not valid java name */
    private static final byte m821elementAtPpDY95g(@NotNull byte[] bArr, int i) {
        return UByteArray.m552getimpl(bArr, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: elementAt-nggk6HY, reason: not valid java name */
    private static final short m822elementAtnggk6HY(@NotNull short[] sArr, int i) {
        return UShortArray.m751getimpl(sArr, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: asList--ajY-9A, reason: not valid java name */
    public static final List m823asListajY9A(@NotNull int[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$1(asList);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: asList-QwZRm1k, reason: not valid java name */
    public static final List m824asListQwZRm1k(@NotNull long[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$2(asList);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: asList-GBYM_sE, reason: not valid java name */
    public static final List m825asListGBYM_sE(@NotNull byte[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$3(asList);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: asList-rL5Bavg, reason: not valid java name */
    public static final List m826asListrL5Bavg(@NotNull short[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$4(asList);
    }

    /* renamed from: binarySearch-2fe2U9s$default, reason: not valid java name */
    public static int m828binarySearch2fe2U9s$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        return UArraysKt.m827binarySearch2fe2U9s(iArr, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: binarySearch-2fe2U9s, reason: not valid java name */
    public static final int m827binarySearch2fe2U9s(@NotNull int[] binarySearch, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i2, i3, binarySearch.length);
        int i4 = i2;
        int i5 = i3 - 1;
        while (i4 <= i5) {
            int i6 = (i4 + i5) >>> 1;
            int iUintCompare = UnsignedKt.uintCompare(binarySearch[i6], i);
            if (iUintCompare < 0) {
                i4 = i6 + 1;
            } else if (iUintCompare > 0) {
                i5 = i6 - 1;
            } else {
                return i6;
            }
        }
        return -(i4 + 1);
    }

    /* renamed from: binarySearch-K6DWlUc$default, reason: not valid java name */
    public static int m830binarySearchK6DWlUc$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        return UArraysKt.m829binarySearchK6DWlUc(jArr, j, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: binarySearch-K6DWlUc, reason: not valid java name */
    public static final int m829binarySearchK6DWlUc(@NotNull long[] binarySearch, long j, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, binarySearch.length);
        int i3 = i;
        int i4 = i2 - 1;
        while (i3 <= i4) {
            int i5 = (i3 + i4) >>> 1;
            long j2 = binarySearch[i5] ^ Long.MIN_VALUE;
            long j3 = j ^ Long.MIN_VALUE;
            if (j2 < j3) {
                i3 = i5 + 1;
            } else if (j2 > j3) {
                i4 = i5 - 1;
            } else {
                return i5;
            }
        }
        return -(i3 + 1);
    }

    /* renamed from: binarySearch-WpHrYlw$default, reason: not valid java name */
    public static int m832binarySearchWpHrYlw$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return UArraysKt.m831binarySearchWpHrYlw(bArr, b, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: binarySearch-WpHrYlw, reason: not valid java name */
    public static final int m831binarySearchWpHrYlw(@NotNull byte[] binarySearch, byte b, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, binarySearch.length);
        int i3 = b & 255;
        int i4 = i;
        int i5 = i2 - 1;
        while (i4 <= i5) {
            int i6 = (i4 + i5) >>> 1;
            int iUintCompare = UnsignedKt.uintCompare(binarySearch[i6], i3);
            if (iUintCompare < 0) {
                i4 = i6 + 1;
            } else if (iUintCompare > 0) {
                i5 = i6 - 1;
            } else {
                return i6;
            }
        }
        return -(i4 + 1);
    }

    /* renamed from: binarySearch-EtDCXyQ$default, reason: not valid java name */
    public static int m834binarySearchEtDCXyQ$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        return UArraysKt.m833binarySearchEtDCXyQ(sArr, s, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: binarySearch-EtDCXyQ, reason: not valid java name */
    public static final int m833binarySearchEtDCXyQ(@NotNull short[] binarySearch, short s, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, binarySearch.length);
        int i3 = s & 65535;
        int i4 = i;
        int i5 = i2 - 1;
        while (i4 <= i5) {
            int i6 = (i4 + i5) >>> 1;
            int iUintCompare = UnsignedKt.uintCompare(binarySearch[i6], i3);
            if (iUintCompare < 0) {
                i4 = i6 + 1;
            } else if (iUintCompare > 0) {
                i5 = i6 - 1;
            } else {
                return i6;
            }
        }
        return -(i4 + 1);
    }
}

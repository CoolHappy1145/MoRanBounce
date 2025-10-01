package kotlin.collections;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd0\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\t\u0010\n\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\f\u0010\r\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006$"}, m27d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/UArraySortingKt.class */
public final class UArraySortingKt {
    @ExperimentalUnsignedTypes
    /* renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m786partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        byte bM552getimpl = UByteArray.m552getimpl(bArr, (i + i2) / 2);
        while (i3 <= i4) {
            while (true) {
                int iM552getimpl = UByteArray.m552getimpl(bArr, i3) & 255;
                int i5 = bM552getimpl & 255;
                if ((iM552getimpl < i5 ? '\uffff' : iM552getimpl == i5 ? (char) 0 : (char) 1) >= 0) {
                    break;
                }
                i3++;
            }
            while (true) {
                int iM552getimpl2 = UByteArray.m552getimpl(bArr, i4) & 255;
                int i6 = bM552getimpl & 255;
                if ((iM552getimpl2 < i6 ? '\uffff' : iM552getimpl2 == i6 ? (char) 0 : (char) 1) <= 0) {
                    break;
                }
                i4--;
            }
            if (i3 <= i4) {
                byte bM552getimpl2 = UByteArray.m552getimpl(bArr, i3);
                bArr[i3] = UByteArray.m552getimpl(bArr, i4);
                bArr[i4] = bM552getimpl2;
                i3++;
                i4--;
            }
        }
        return i3;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m787quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM786partition4UcCI2c = m786partition4UcCI2c(bArr, i, i2);
        if (i < iM786partition4UcCI2c - 1) {
            m787quickSort4UcCI2c(bArr, i, iM786partition4UcCI2c - 1);
        }
        if (iM786partition4UcCI2c < i2) {
            m787quickSort4UcCI2c(bArr, iM786partition4UcCI2c, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m788partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        short sM751getimpl = UShortArray.m751getimpl(sArr, (i + i2) / 2);
        while (i3 <= i4) {
            while (true) {
                int iM751getimpl = UShortArray.m751getimpl(sArr, i3) & 65535;
                int i5 = sM751getimpl & 65535;
                if ((iM751getimpl < i5 ? '\uffff' : iM751getimpl == i5 ? (char) 0 : (char) 1) >= 0) {
                    break;
                }
                i3++;
            }
            while (true) {
                int iM751getimpl2 = UShortArray.m751getimpl(sArr, i4) & 65535;
                int i6 = sM751getimpl & 65535;
                if ((iM751getimpl2 < i6 ? '\uffff' : iM751getimpl2 == i6 ? (char) 0 : (char) 1) <= 0) {
                    break;
                }
                i4--;
            }
            if (i3 <= i4) {
                short sM751getimpl2 = UShortArray.m751getimpl(sArr, i3);
                sArr[i3] = UShortArray.m751getimpl(sArr, i4);
                sArr[i4] = sM751getimpl2;
                i3++;
                i4--;
            }
        }
        return i3;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m789quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM788partitionAa5vz7o = m788partitionAa5vz7o(sArr, i, i2);
        if (i < iM788partitionAa5vz7o - 1) {
            m789quickSortAa5vz7o(sArr, i, iM788partitionAa5vz7o - 1);
        }
        if (iM788partitionAa5vz7o < i2) {
            m789quickSortAa5vz7o(sArr, iM788partitionAa5vz7o, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m790partitionoBK06Vg(int[] iArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        int iM610getimpl = UIntArray.m610getimpl(iArr, (i + i2) / 2);
        while (i3 <= i4) {
            while (UnsignedKt.uintCompare(UIntArray.m610getimpl(iArr, i3), iM610getimpl) < 0) {
                i3++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m610getimpl(iArr, i4), iM610getimpl) > 0) {
                i4--;
            }
            if (i3 <= i4) {
                int iM610getimpl2 = UIntArray.m610getimpl(iArr, i3);
                iArr[i3] = UIntArray.m610getimpl(iArr, i4);
                iArr[i4] = iM610getimpl2;
                i3++;
                i4--;
            }
        }
        return i3;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m791quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM790partitionoBK06Vg = m790partitionoBK06Vg(iArr, i, i2);
        if (i < iM790partitionoBK06Vg - 1) {
            m791quickSortoBK06Vg(iArr, i, iM790partitionoBK06Vg - 1);
        }
        if (iM790partitionoBK06Vg < i2) {
            m791quickSortoBK06Vg(iArr, iM790partitionoBK06Vg, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m792partitionnroSd4(long[] jArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        long jM668getimpl = ULongArray.m668getimpl(jArr, (i + i2) / 2);
        while (i3 <= i4) {
            while ((ULongArray.m668getimpl(jArr, i3) ^ Long.MIN_VALUE) < (jM668getimpl ^ Long.MIN_VALUE)) {
                i3++;
            }
            while ((ULongArray.m668getimpl(jArr, i4) ^ Long.MIN_VALUE) > (jM668getimpl ^ Long.MIN_VALUE)) {
                i4--;
            }
            if (i3 <= i4) {
                long jM668getimpl2 = ULongArray.m668getimpl(jArr, i3);
                jArr[i3] = ULongArray.m668getimpl(jArr, i4);
                jArr[i4] = jM668getimpl2;
                i3++;
                i4--;
            }
        }
        return i3;
    }

    @ExperimentalUnsignedTypes
    /* renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m793quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM792partitionnroSd4 = m792partitionnroSd4(jArr, i, i2);
        if (i < iM792partitionnroSd4 - 1) {
            m793quickSortnroSd4(jArr, i, iM792partitionnroSd4 - 1);
        }
        if (iM792partitionnroSd4 < i2) {
            m793quickSortnroSd4(jArr, iM792partitionnroSd4, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray-GBYM_sE, reason: not valid java name */
    public static final void m794sortArrayGBYM_sE(@NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m787quickSort4UcCI2c(array, 0, array.length - 1);
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray-rL5Bavg, reason: not valid java name */
    public static final void m795sortArrayrL5Bavg(@NotNull short[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m789quickSortAa5vz7o(array, 0, array.length - 1);
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray--ajY-9A, reason: not valid java name */
    public static final void m796sortArrayajY9A(@NotNull int[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m791quickSortoBK06Vg(array, 0, array.length - 1);
    }

    @ExperimentalUnsignedTypes
    /* renamed from: sortArray-QwZRm1k, reason: not valid java name */
    public static final void m797sortArrayQwZRm1k(@NotNull long[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m793quickSortnroSd4(array, 0, array.length - 1);
    }
}

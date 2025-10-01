package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdF\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, m27d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, m28xs = "kotlin/collections/UCollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/UCollectionsKt___UCollectionsKt.class */
class UCollectionsKt___UCollectionsKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final byte[] toUByteArray(@NotNull Collection toUByteArray) {
        Intrinsics.checkParameterIsNotNull(toUByteArray, "$this$toUByteArray");
        byte[] bArrM557constructorimpl = UByteArray.m557constructorimpl(toUByteArray.size());
        int i = 0;
        Iterator it = toUByteArray.iterator();
        while (it.hasNext()) {
            int i2 = i;
            i++;
            bArrM557constructorimpl[i2] = ((UByte) it.next()).m549unboximpl();
        }
        return bArrM557constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final int[] toUIntArray(@NotNull Collection toUIntArray) {
        Intrinsics.checkParameterIsNotNull(toUIntArray, "$this$toUIntArray");
        int[] iArrM615constructorimpl = UIntArray.m615constructorimpl(toUIntArray.size());
        int i = 0;
        Iterator it = toUIntArray.iterator();
        while (it.hasNext()) {
            int i2 = i;
            i++;
            iArrM615constructorimpl[i2] = ((UInt) it.next()).m607unboximpl();
        }
        return iArrM615constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final long[] toULongArray(@NotNull Collection toULongArray) {
        Intrinsics.checkParameterIsNotNull(toULongArray, "$this$toULongArray");
        long[] jArrM673constructorimpl = ULongArray.m673constructorimpl(toULongArray.size());
        int i = 0;
        Iterator it = toULongArray.iterator();
        while (it.hasNext()) {
            int i2 = i;
            i++;
            jArrM673constructorimpl[i2] = ((ULong) it.next()).m665unboximpl();
        }
        return jArrM673constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final short[] toUShortArray(@NotNull Collection toUShortArray) {
        Intrinsics.checkParameterIsNotNull(toUShortArray, "$this$toUShortArray");
        short[] sArrM756constructorimpl = UShortArray.m756constructorimpl(toUShortArray.size());
        int i = 0;
        Iterator it = toUShortArray.iterator();
        while (it.hasNext()) {
            int i2 = i;
            i++;
            sArrM756constructorimpl[i2] = ((UShort) it.next()).m748unboximpl();
        }
        return sArrM756constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUInt")
    @ExperimentalUnsignedTypes
    public static final int sumOfUInt(@NotNull Iterable sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int iM607unboximpl = 0;
        Iterator it = sum.iterator();
        while (it.hasNext()) {
            iM607unboximpl += ((UInt) it.next()).m607unboximpl();
        }
        return iM607unboximpl;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfULong")
    @ExperimentalUnsignedTypes
    public static final long sumOfULong(@NotNull Iterable sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        long jM665unboximpl = 0;
        Iterator it = sum.iterator();
        while (it.hasNext()) {
            jM665unboximpl += ((ULong) it.next()).m665unboximpl();
        }
        return jM665unboximpl;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUByte")
    @ExperimentalUnsignedTypes
    public static final int sumOfUByte(@NotNull Iterable sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int iM549unboximpl = 0;
        Iterator it = sum.iterator();
        while (it.hasNext()) {
            iM549unboximpl += ((UByte) it.next()).m549unboximpl() & 255;
        }
        return iM549unboximpl;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUShort")
    @ExperimentalUnsignedTypes
    public static final int sumOfUShort(@NotNull Iterable sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int iM748unboximpl = 0;
        Iterator it = sum.iterator();
        while (it.hasNext()) {
            iM748unboximpl += ((UShort) it.next()).m748unboximpl() & 65535;
        }
        return iM748unboximpl;
    }
}

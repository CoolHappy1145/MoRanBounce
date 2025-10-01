package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\"\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u000b\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, m27d2 = {"sum", "Lkotlin/UInt;", "Lkotlin/sequences/Sequence;", "Lkotlin/UByte;", "sumOfUByte", "(Lkotlin/sequences/Sequence;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Lkotlin/sequences/Sequence;)J", "Lkotlin/UShort;", "sumOfUShort", "kotlin-stdlib"}, m28xs = "kotlin/sequences/USequencesKt")
/* loaded from: L-out.jar:kotlin/sequences/USequencesKt___USequencesKt.class */
class USequencesKt___USequencesKt {
    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUInt")
    @ExperimentalUnsignedTypes
    public static final int sumOfUInt(@NotNull Sequence sum) {
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
    public static final long sumOfULong(@NotNull Sequence sum) {
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
    public static final int sumOfUByte(@NotNull Sequence sum) {
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
    public static final int sumOfUShort(@NotNull Sequence sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int iM748unboximpl = 0;
        Iterator it = sum.iterator();
        while (it.hasNext()) {
            iM748unboximpl += ((UShort) it.next()).m748unboximpl() & 65535;
        }
        return iM748unboximpl;
    }
}

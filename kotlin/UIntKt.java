package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd,\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\ufffd\ufffd\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0010\n\n\u0002\b\u0002\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0003\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0004H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0006H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0007\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\t\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\nH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000b\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\fH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, m27d2 = {"toUInt", "Lkotlin/UInt;", "", "(B)I", "", "(D)I", "", "(F)I", "", "(I)I", "", "(J)I", "", "(S)I", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/UIntKt.class */
public final class UIntKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int toUInt(byte b) {
        return b;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int toUInt(short s) {
        return s;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int toUInt(int i) {
        return i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int toUInt(long j) {
        return (int) j;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int toUInt(float f) {
        return UnsignedKt.doubleToUInt(f);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int toUInt(double d) {
        return UnsignedKt.doubleToUInt(d);
    }
}

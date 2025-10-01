package kotlin.text;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd.\n\ufffd\ufffd\n\u0002\u0010\u0001\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0010\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\ufffd\ufffd\u001a\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u000b\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\f\u001a\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u000f\u001a\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\u0010\u001a\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, m27d2 = {"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt__StringNumberConversionsKt.class */
class StringsKt__StringNumberConversionsKt extends StringsKt__StringNumberConversionsJVMKt {
    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String toByteOrNull) {
        Intrinsics.checkParameterIsNotNull(toByteOrNull, "$this$toByteOrNull");
        return StringsKt.toByteOrNull(toByteOrNull, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String toByteOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(toByteOrNull, "$this$toByteOrNull");
        Integer intOrNull = StringsKt.toIntOrNull(toByteOrNull, i);
        if (intOrNull == null) {
            return null;
        }
        int iIntValue = intOrNull.intValue();
        if (iIntValue < -128 || iIntValue > 127) {
            return null;
        }
        return Byte.valueOf((byte) iIntValue);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String toShortOrNull) {
        Intrinsics.checkParameterIsNotNull(toShortOrNull, "$this$toShortOrNull");
        return StringsKt.toShortOrNull(toShortOrNull, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String toShortOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(toShortOrNull, "$this$toShortOrNull");
        Integer intOrNull = StringsKt.toIntOrNull(toShortOrNull, i);
        if (intOrNull == null) {
            return null;
        }
        int iIntValue = intOrNull.intValue();
        if (iIntValue < -32768 || iIntValue > 32767) {
            return null;
        }
        return Short.valueOf((short) iIntValue);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String toIntOrNull) {
        Intrinsics.checkParameterIsNotNull(toIntOrNull, "$this$toIntOrNull");
        return StringsKt.toIntOrNull(toIntOrNull, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String toIntOrNull, int i) {
        int i2;
        boolean z;
        int i3;
        Intrinsics.checkParameterIsNotNull(toIntOrNull, "$this$toIntOrNull");
        CharsKt.checkRadix(i);
        int length = toIntOrNull.length();
        if (length == 0) {
            return null;
        }
        char cCharAt = toIntOrNull.charAt(0);
        if (cCharAt < '0') {
            if (length == 1) {
                return null;
            }
            i2 = 1;
            if (cCharAt == '-') {
                z = true;
                i3 = Integer.MIN_VALUE;
            } else if (cCharAt == '+') {
                z = false;
                i3 = -2147483647;
            } else {
                return null;
            }
        } else {
            i2 = 0;
            z = false;
            i3 = -2147483647;
        }
        int i4 = -59652323;
        int i5 = 0;
        for (int i6 = i2; i6 < length; i6++) {
            int iDigitOf = CharsKt.digitOf(toIntOrNull.charAt(i6), i);
            if (iDigitOf < 0) {
                return null;
            }
            if (i5 < i4) {
                if (i4 == -59652323) {
                    i4 = i3 / i;
                    if (i5 < i4) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            int i7 = i5 * i;
            if (i7 < i3 + iDigitOf) {
                return null;
            }
            i5 = i7 - iDigitOf;
        }
        return z ? Integer.valueOf(i5) : Integer.valueOf(-i5);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String toLongOrNull) {
        Intrinsics.checkParameterIsNotNull(toLongOrNull, "$this$toLongOrNull");
        return StringsKt.toLongOrNull(toLongOrNull, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String toLongOrNull, int i) {
        int i2;
        boolean z;
        long j;
        Intrinsics.checkParameterIsNotNull(toLongOrNull, "$this$toLongOrNull");
        CharsKt.checkRadix(i);
        int length = toLongOrNull.length();
        if (length == 0) {
            return null;
        }
        char cCharAt = toLongOrNull.charAt(0);
        if (cCharAt < '0') {
            if (length == 1) {
                return null;
            }
            i2 = 1;
            if (cCharAt == '-') {
                z = true;
                j = Long.MIN_VALUE;
            } else if (cCharAt == '+') {
                z = false;
                j = -9223372036854775807L;
            } else {
                return null;
            }
        } else {
            i2 = 0;
            z = false;
            j = -9223372036854775807L;
        }
        long j2 = -256204778801521550L;
        long j3 = 0;
        for (int i3 = i2; i3 < length; i3++) {
            int iDigitOf = CharsKt.digitOf(toLongOrNull.charAt(i3), i);
            if (iDigitOf < 0) {
                return null;
            }
            if (j3 < j2) {
                if (j2 == -256204778801521550L) {
                    j2 = j / i;
                    if (j3 < j2) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            long j4 = j3 * i;
            if (j4 < j + iDigitOf) {
                return null;
            }
            j3 = j4 - iDigitOf;
        }
        return z ? Long.valueOf(j3) : Long.valueOf(-j3);
    }

    @NotNull
    public static final Void numberFormatError(@NotNull String input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        throw new NumberFormatException("Invalid number format: '" + input + '\'');
    }
}

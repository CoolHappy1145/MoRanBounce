package kotlin.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

@Deprecated(message = "Provided for binary compatibility", level = DeprecationLevel.HIDDEN)
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdl\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\t\b\u00c7\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u0005H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\tH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u000fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u0005H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\tH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b \u0010!J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\"\u0010#J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u000fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b$\u0010%J\u001e\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b)\u0010*J\u001e\u0010&\u001a\u00020+*\u00020\t2\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b,\u0010-J\u001e\u0010&\u001a\u00020.*\u00020\f2\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b/\u00100J\u001e\u0010&\u001a\u000201*\u00020\u000f2\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b2\u00103J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020'05*\u00020\u0005H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b6\u00107J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020+05*\u00020\tH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b8\u00109J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020.05*\u00020\fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b:\u0010;J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020105*\u00020\u000fH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b<\u0010=\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006>"}, m27d2 = {"Lkotlin/collections/UArraysKt;", "", "()V", "contentEquals", "", "Lkotlin/UByteArray;", "other", "contentEquals-kdPth3s", "([B[B)Z", "Lkotlin/UIntArray;", "contentEquals-ctEhBpI", "([I[I)Z", "Lkotlin/ULongArray;", "contentEquals-us8wMrg", "([J[J)Z", "Lkotlin/UShortArray;", "contentEquals-mazbYpA", "([S[S)Z", "contentHashCode", "", "contentHashCode-GBYM_sE", "([B)I", "contentHashCode--ajY-9A", "([I)I", "contentHashCode-QwZRm1k", "([J)I", "contentHashCode-rL5Bavg", "([S)I", "contentToString", "", "contentToString-GBYM_sE", "([B)Ljava/lang/String;", "contentToString--ajY-9A", "([I)Ljava/lang/String;", "contentToString-QwZRm1k", "([J)Ljava/lang/String;", "contentToString-rL5Bavg", "([S)Ljava/lang/String;", "random", "Lkotlin/UByte;", "Lkotlin/random/Random;", "random-oSF2wD8", "([BLkotlin/random/Random;)B", "Lkotlin/UInt;", "random-2D5oskM", "([ILkotlin/random/Random;)I", "Lkotlin/ULong;", "random-JzugnMA", "([JLkotlin/random/Random;)J", "Lkotlin/UShort;", "random-s5X_as8", "([SLkotlin/random/Random;)S", "toTypedArray", "", "toTypedArray-GBYM_sE", "([B)[Lkotlin/UByte;", "toTypedArray--ajY-9A", "([I)[Lkotlin/UInt;", "toTypedArray-QwZRm1k", "([J)[Lkotlin/ULong;", "toTypedArray-rL5Bavg", "([S)[Lkotlin/UShort;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/UArraysKt.class */
public final class UArraysKt {
    public static final UArraysKt INSTANCE = new UArraysKt();

    private UArraysKt() {
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: random-2D5oskM, reason: not valid java name */
    public static final int m798random2D5oskM(@NotNull int[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (random.length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UIntArray.m610getimpl(random, random2.nextInt(random.length));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: random-JzugnMA, reason: not valid java name */
    public static final long m799randomJzugnMA(@NotNull long[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (random.length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        return ULongArray.m668getimpl(random, random2.nextInt(random.length));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: random-oSF2wD8, reason: not valid java name */
    public static final byte m800randomoSF2wD8(@NotNull byte[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (random.length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UByteArray.m552getimpl(random, random2.nextInt(random.length));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: random-s5X_as8, reason: not valid java name */
    public static final short m801randoms5X_as8(@NotNull short[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (random.length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UShortArray.m751getimpl(random, random2.nextInt(random.length));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentEquals-ctEhBpI, reason: not valid java name */
    public static final boolean m802contentEqualsctEhBpI(@NotNull int[] contentEquals, @NotNull int[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentEquals-us8wMrg, reason: not valid java name */
    public static final boolean m803contentEqualsus8wMrg(@NotNull long[] contentEquals, @NotNull long[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentEquals-kdPth3s, reason: not valid java name */
    public static final boolean m804contentEqualskdPth3s(@NotNull byte[] contentEquals, @NotNull byte[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentEquals-mazbYpA, reason: not valid java name */
    public static final boolean m805contentEqualsmazbYpA(@NotNull short[] contentEquals, @NotNull short[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentHashCode--ajY-9A, reason: not valid java name */
    public static final int m806contentHashCodeajY9A(@NotNull int[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentHashCode-QwZRm1k, reason: not valid java name */
    public static final int m807contentHashCodeQwZRm1k(@NotNull long[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentHashCode-GBYM_sE, reason: not valid java name */
    public static final int m808contentHashCodeGBYM_sE(@NotNull byte[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* renamed from: contentHashCode-rL5Bavg, reason: not valid java name */
    public static final int m809contentHashCoderL5Bavg(@NotNull short[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: contentToString--ajY-9A, reason: not valid java name */
    public static final String m810contentToStringajY9A(@NotNull int[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UIntArray.m616boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: contentToString-QwZRm1k, reason: not valid java name */
    public static final String m811contentToStringQwZRm1k(@NotNull long[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(ULongArray.m674boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: contentToString-GBYM_sE, reason: not valid java name */
    public static final String m812contentToStringGBYM_sE(@NotNull byte[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UByteArray.m558boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: contentToString-rL5Bavg, reason: not valid java name */
    public static final String m813contentToStringrL5Bavg(@NotNull short[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UShortArray.m757boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: toTypedArray--ajY-9A, reason: not valid java name */
    public static final UInt[] m814toTypedArrayajY9A(@NotNull int[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int length = toTypedArray.length;
        UInt[] uIntArr = new UInt[length];
        for (int i = 0; i < length; i++) {
            uIntArr[i] = UInt.m605boximpl(UIntArray.m610getimpl(toTypedArray, i));
        }
        return uIntArr;
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: toTypedArray-QwZRm1k, reason: not valid java name */
    public static final ULong[] m815toTypedArrayQwZRm1k(@NotNull long[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int length = toTypedArray.length;
        ULong[] uLongArr = new ULong[length];
        for (int i = 0; i < length; i++) {
            uLongArr[i] = ULong.m663boximpl(ULongArray.m668getimpl(toTypedArray, i));
        }
        return uLongArr;
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: toTypedArray-GBYM_sE, reason: not valid java name */
    public static final UByte[] m816toTypedArrayGBYM_sE(@NotNull byte[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int length = toTypedArray.length;
        UByte[] uByteArr = new UByte[length];
        for (int i = 0; i < length; i++) {
            uByteArr[i] = UByte.m547boximpl(UByteArray.m552getimpl(toTypedArray, i));
        }
        return uByteArr;
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: toTypedArray-rL5Bavg, reason: not valid java name */
    public static final UShort[] m817toTypedArrayrL5Bavg(@NotNull short[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int length = toTypedArray.length;
        UShort[] uShortArr = new UShort[length];
        for (int i = 0; i < length; i++) {
            uShortArr[i] = UShort.m746boximpl(UShortArray.m751getimpl(toTypedArray, i));
        }
        return uShortArr;
    }
}

package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.apache.log4j.spi.Configurator;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdH\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\u0007\u001a\u00020\b\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001\u00a2\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003\u00a2\u0006\u0002\u0010\u0016\u001a8\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\ufffd\ufffd\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\b\u00a2\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\ufffd\ufffd\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\ufffd\ufffd\u00a2\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003\u00a2\u0006\u0002\u0010!\u00a8\u0006\""}, m27d2 = {"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, m28xs = "kotlin/collections/ArraysKt")
/* loaded from: L-out.jar:kotlin/collections/ArraysKt__ArraysKt.class */
class ArraysKt__ArraysKt extends ArraysKt__ArraysJVMKt {
    @NotNull
    public static final List flatten(@NotNull Object[][] flatten) {
        Intrinsics.checkParameterIsNotNull(flatten, "$this$flatten");
        int length = 0;
        for (Object[] objArr : flatten) {
            length += objArr.length;
        }
        ArrayList arrayList = new ArrayList(length);
        for (Object[] objArr2 : flatten) {
            CollectionsKt.addAll(arrayList, objArr2);
        }
        return arrayList;
    }

    @NotNull
    public static final Pair unzip(@NotNull Pair[] unzip) {
        Intrinsics.checkParameterIsNotNull(unzip, "$this$unzip");
        ArrayList arrayList = new ArrayList(unzip.length);
        ArrayList arrayList2 = new ArrayList(unzip.length);
        for (Pair pair : unzip) {
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.m32to(arrayList, arrayList2);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object ifEmpty(Object[] objArr, Function0 function0) {
        return objArr.length == 0 ? function0.invoke() : objArr;
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @JvmName(name = "contentDeepEquals")
    public static final boolean contentDeepEquals(@NotNull Object[] contentDeepEqualsImpl, @NotNull Object[] other) {
        Intrinsics.checkParameterIsNotNull(contentDeepEqualsImpl, "$this$contentDeepEqualsImpl");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (contentDeepEqualsImpl == other) {
            return true;
        }
        if (contentDeepEqualsImpl.length != other.length) {
            return false;
        }
        int length = contentDeepEqualsImpl.length;
        for (int i = 0; i < length; i++) {
            Object obj = contentDeepEqualsImpl[i];
            Object obj2 = other[i];
            if (obj != obj2) {
                if (obj == null || obj2 == null) {
                    return false;
                }
                if ((obj instanceof Object[]) && (obj2 instanceof Object[])) {
                    if (!ArraysKt.contentDeepEquals((Object[]) obj, (Object[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof byte[]) && (obj2 instanceof byte[])) {
                    if (!Arrays.equals((byte[]) obj, (byte[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof short[]) && (obj2 instanceof short[])) {
                    if (!Arrays.equals((short[]) obj, (short[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof int[]) && (obj2 instanceof int[])) {
                    if (!Arrays.equals((int[]) obj, (int[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof long[]) && (obj2 instanceof long[])) {
                    if (!Arrays.equals((long[]) obj, (long[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof float[]) && (obj2 instanceof float[])) {
                    if (!Arrays.equals((float[]) obj, (float[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof double[]) && (obj2 instanceof double[])) {
                    if (!Arrays.equals((double[]) obj, (double[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof char[]) && (obj2 instanceof char[])) {
                    if (!Arrays.equals((char[]) obj, (char[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof boolean[]) && (obj2 instanceof boolean[])) {
                    if (!Arrays.equals((boolean[]) obj, (boolean[]) obj2)) {
                        return false;
                    }
                } else if ((obj instanceof UByteArray) && (obj2 instanceof UByteArray)) {
                    if (!kotlin.collections.unsigned.UArraysKt.m1069contentEqualskdPth3s(((UByteArray) obj).m563unboximpl(), ((UByteArray) obj2).m563unboximpl())) {
                        return false;
                    }
                } else if ((obj instanceof UShortArray) && (obj2 instanceof UShortArray)) {
                    if (!kotlin.collections.unsigned.UArraysKt.m1070contentEqualsmazbYpA(((UShortArray) obj).m762unboximpl(), ((UShortArray) obj2).m762unboximpl())) {
                        return false;
                    }
                } else if ((obj instanceof UIntArray) && (obj2 instanceof UIntArray)) {
                    if (!kotlin.collections.unsigned.UArraysKt.m1067contentEqualsctEhBpI(((UIntArray) obj).m621unboximpl(), ((UIntArray) obj2).m621unboximpl())) {
                        return false;
                    }
                } else if ((obj instanceof ULongArray) && (obj2 instanceof ULongArray)) {
                    if (!kotlin.collections.unsigned.UArraysKt.m1068contentEqualsus8wMrg(((ULongArray) obj).m679unboximpl(), ((ULongArray) obj2).m679unboximpl())) {
                        return false;
                    }
                } else if (!Intrinsics.areEqual(obj, obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "contentDeepToString")
    @NotNull
    @PublishedApi
    public static final String contentDeepToString(@NotNull Object[] contentDeepToStringImpl) {
        Intrinsics.checkParameterIsNotNull(contentDeepToStringImpl, "$this$contentDeepToStringImpl");
        StringBuilder sb = new StringBuilder((RangesKt.coerceAtMost(contentDeepToStringImpl.length, 429496729) * 5) + 2);
        contentDeepToStringInternal$ArraysKt__ArraysKt(contentDeepToStringImpl, sb, new ArrayList());
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder(capacity).\u2026builderAction).toString()");
        return string;
    }

    private static final void contentDeepToStringInternal$ArraysKt__ArraysKt(@NotNull Object[] objArr, StringBuilder sb, List list) {
        if (list.contains(objArr)) {
            sb.append("[...]");
            return;
        }
        list.add(objArr);
        sb.append('[');
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            Object obj = objArr[i];
            if (obj == null) {
                sb.append(Configurator.NULL);
            } else if (obj instanceof Object[]) {
                contentDeepToStringInternal$ArraysKt__ArraysKt((Object[]) obj, sb, list);
            } else if (obj instanceof byte[]) {
                String string = Arrays.toString((byte[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
                sb.append(string);
            } else if (obj instanceof short[]) {
                String string2 = Arrays.toString((short[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string2, "java.util.Arrays.toString(this)");
                sb.append(string2);
            } else if (obj instanceof int[]) {
                String string3 = Arrays.toString((int[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string3, "java.util.Arrays.toString(this)");
                sb.append(string3);
            } else if (obj instanceof long[]) {
                String string4 = Arrays.toString((long[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string4, "java.util.Arrays.toString(this)");
                sb.append(string4);
            } else if (obj instanceof float[]) {
                String string5 = Arrays.toString((float[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string5, "java.util.Arrays.toString(this)");
                sb.append(string5);
            } else if (obj instanceof double[]) {
                String string6 = Arrays.toString((double[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string6, "java.util.Arrays.toString(this)");
                sb.append(string6);
            } else if (obj instanceof char[]) {
                String string7 = Arrays.toString((char[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string7, "java.util.Arrays.toString(this)");
                sb.append(string7);
            } else if (obj instanceof boolean[]) {
                String string8 = Arrays.toString((boolean[]) obj);
                Intrinsics.checkExpressionValueIsNotNull(string8, "java.util.Arrays.toString(this)");
                sb.append(string8);
            } else if (obj instanceof UByteArray) {
                sb.append(kotlin.collections.unsigned.UArraysKt.m1077contentToStringGBYM_sE(((UByteArray) obj).m563unboximpl()));
            } else if (obj instanceof UShortArray) {
                sb.append(kotlin.collections.unsigned.UArraysKt.m1078contentToStringrL5Bavg(((UShortArray) obj).m762unboximpl()));
            } else if (obj instanceof UIntArray) {
                sb.append(kotlin.collections.unsigned.UArraysKt.m1075contentToStringajY9A(((UIntArray) obj).m621unboximpl()));
            } else if (obj instanceof ULongArray) {
                sb.append(kotlin.collections.unsigned.UArraysKt.m1076contentToStringQwZRm1k(((ULongArray) obj).m679unboximpl()));
            } else {
                sb.append(obj.toString());
            }
        }
        sb.append(']');
        list.remove(CollectionsKt.getLastIndex(list));
    }
}

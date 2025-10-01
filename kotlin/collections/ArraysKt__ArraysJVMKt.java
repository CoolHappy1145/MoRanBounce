package kotlin.collections;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd2\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\b\u0002\u001a/\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006\u001a\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001a!\u0010\n\u001a\u00020\u0005\"\u0004\b\ufffd\ufffd\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001H\u0001\u00a2\u0006\u0004\b\u000b\u0010\f\u001a,\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\u0006\b\ufffd\ufffd\u0010\u0002\u0018\u0001*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0001H\u0086\b\u00a2\u0006\u0002\u0010\u000e\u001a\u0015\u0010\u000f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\b\u001a&\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\ufffd\ufffd\u0010\u0002\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u0015H\u0086\b\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, m27d2 = {"arrayOfNulls", "", "T", "reference", "size", "", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRangeToIndexCheck", "", "toIndex", "contentDeepHashCodeImpl", "contentDeepHashCode", "([Ljava/lang/Object;)I", "orEmpty", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "", "charset", "Ljava/nio/charset/Charset;", "toTypedArray", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "kotlin-stdlib"}, m28xs = "kotlin/collections/ArraysKt")
/* loaded from: L-out.jar:kotlin/collections/ArraysKt__ArraysJVMKt.class */
class ArraysKt__ArraysJVMKt {
    @NotNull
    public static final Object[] orEmpty(@Nullable Object[] objArr) {
        if (objArr != null) {
            return objArr;
        }
        Intrinsics.reifiedOperationMarker(0, "T?");
        return new Object[0];
    }

    @InlineOnly
    private static final String toString(@NotNull byte[] bArr, Charset charset) {
        return new String(bArr, charset);
    }

    @NotNull
    public static final Object[] toTypedArray(@NotNull Collection toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Intrinsics.reifiedOperationMarker(0, "T?");
        Object[] array = toTypedArray.toArray(new Object[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return array;
    }

    @NotNull
    public static final Object[] arrayOfNulls(@NotNull Object[] reference, int i) throws NegativeArraySizeException {
        Intrinsics.checkParameterIsNotNull(reference, "reference");
        Object objNewInstance = Array.newInstance(reference.getClass().getComponentType(), i);
        if (objNewInstance == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return (Object[]) objNewInstance;
    }

    @SinceKotlin(version = "1.3")
    public static final void copyOfRangeToIndexCheck(int i, int i2) {
        if (i > i2) {
            throw new IndexOutOfBoundsException("toIndex (" + i + ") is greater than size (" + i2 + ").");
        }
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @JvmName(name = "contentDeepHashCode")
    public static final int contentDeepHashCode(@NotNull Object[] contentDeepHashCodeImpl) {
        Intrinsics.checkParameterIsNotNull(contentDeepHashCodeImpl, "$this$contentDeepHashCodeImpl");
        return Arrays.deepHashCode(contentDeepHashCodeImpl);
    }
}

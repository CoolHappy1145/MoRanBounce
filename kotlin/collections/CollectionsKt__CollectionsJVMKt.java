package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd2\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u0011\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0081\b\u001a\u0011\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0081\b\u001a\"\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0081\b\u00a2\u0006\u0002\u0010\n\u001a4\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0006\"\u0004\b\ufffd\ufffd\u0010\u000b2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0006H\u0081\b\u00a2\u0006\u0002\u0010\r\u001a\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f\"\u0004\b\ufffd\ufffd\u0010\u000b2\u0006\u0010\u0010\u001a\u0002H\u000b\u00a2\u0006\u0002\u0010\u0011\u001a1\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\b\ufffd\ufffd\u0010\u000b*\n\u0012\u0006\b\u0001\u0012\u0002H\u000b0\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0015\u001a\u001f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f\"\u0004\b\ufffd\ufffd\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0017H\u0087\b\u00a8\u0006\u0018"}, m27d2 = {"checkCountOverflow", "", "count", "checkIndexOverflow", "index", "copyToArrayImpl", "", "", "collection", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "T", "array", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "listOf", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "copyToArrayOfAny", "isVarargs", "", "([Ljava/lang/Object;Z)[Ljava/lang/Object;", "toList", "Ljava/util/Enumeration;", "kotlin-stdlib"}, m28xs = "kotlin/collections/CollectionsKt")
/* loaded from: L-out.jar:kotlin/collections/CollectionsKt__CollectionsJVMKt.class */
class CollectionsKt__CollectionsJVMKt {
    @NotNull
    public static final List listOf(Object obj) {
        List listSingletonList = Collections.singletonList(obj);
        Intrinsics.checkExpressionValueIsNotNull(listSingletonList, "java.util.Collections.singletonList(element)");
        return listSingletonList;
    }

    @InlineOnly
    private static final List toList(@NotNull Enumeration enumeration) {
        ArrayList list = Collections.list(enumeration);
        Intrinsics.checkExpressionValueIsNotNull(list, "java.util.Collections.list(this)");
        return list;
    }

    @InlineOnly
    private static final Object[] copyToArrayImpl(Collection collection) {
        return CollectionToArray.toArray(collection);
    }

    @InlineOnly
    private static final Object[] copyToArrayImpl(Collection collection, Object[] objArr) {
        if (objArr == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        Object[] array = CollectionToArray.toArray(collection, objArr);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return array;
    }

    @NotNull
    public static final Object[] copyToArrayOfAny(@NotNull Object[] copyToArrayOfAny, boolean z) {
        Intrinsics.checkParameterIsNotNull(copyToArrayOfAny, "$this$copyToArrayOfAny");
        if (z && Intrinsics.areEqual(copyToArrayOfAny.getClass(), Object[].class)) {
            return copyToArrayOfAny;
        }
        Object[] objArrCopyOf = Arrays.copyOf(copyToArrayOfAny, copyToArrayOfAny.length, Object[].class);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(\u2026 Array<Any?>::class.java)");
        return objArrCopyOf;
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @InlineOnly
    private static final int checkIndexOverflow(int i) {
        if (i < 0) {
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                CollectionsKt.throwIndexOverflow();
            } else {
                throw new ArithmeticException("Index overflow has happened.");
            }
        }
        return i;
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @InlineOnly
    private static final int checkCountOverflow(int i) {
        if (i < 0) {
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                CollectionsKt.throwCountOverflow();
            } else {
                throw new ArithmeticException("Count overflow has happened.");
            }
        }
        return i;
    }
}

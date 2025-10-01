package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd2\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007\u00a2\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007\u00a2\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b\u00a2\u0006\u0002\u0010\u0014\"\u0018\u0010\ufffd\ufffd\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0015"}, m27d2 = {"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name = "CollectionToArray")
/* loaded from: L-out.jar:kotlin/jvm/internal/CollectionToArray.class */
public final class CollectionToArray {
    private static final Object[] EMPTY = new Object[0];
    private static final int MAX_SIZE = 2147483645;

    @JvmName(name = "toArray")
    @NotNull
    public static final Object[] toArray(@NotNull Collection collection, @Nullable Object[] objArr) {
        Object[] objArr2;
        Intrinsics.checkParameterIsNotNull(collection, "collection");
        if (objArr == null) {
            throw new NullPointerException();
        }
        int size = collection.size();
        if (size == 0) {
            if (objArr.length > 0) {
                objArr[0] = null;
            }
            return objArr;
        }
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            if (objArr.length > 0) {
                objArr[0] = null;
            }
            return objArr;
        }
        if (size <= objArr.length) {
            objArr2 = objArr;
        } else {
            Object objNewInstance = Array.newInstance(objArr.getClass().getComponentType(), size);
            if (objNewInstance == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            }
            objArr2 = (Object[]) objNewInstance;
        }
        Object[] objArr3 = objArr2;
        int i = 0;
        while (true) {
            int i2 = i;
            i++;
            objArr3[i2] = it.next();
            if (i >= objArr3.length) {
                if (!it.hasNext()) {
                    return objArr3;
                }
                int i3 = ((i * 3) + 1) >>> 1;
                if (i3 <= i) {
                    if (i >= MAX_SIZE) {
                        throw new OutOfMemoryError();
                    }
                    i3 = MAX_SIZE;
                }
                Object[] objArrCopyOf = Arrays.copyOf(objArr3, i3);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "Arrays.copyOf(result, newSize)");
                objArr3 = objArrCopyOf;
            } else if (!it.hasNext()) {
                Object[] objArr4 = objArr3;
                if (objArr4 == objArr) {
                    objArr[i] = null;
                    return objArr;
                }
                Object[] objArrCopyOf2 = Arrays.copyOf(objArr4, i);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf2, "Arrays.copyOf(result, size)");
                return objArrCopyOf2;
            }
        }
    }

    private static final Object[] toArrayImpl(Collection collection, Function0 function0, Function1 function1, Function2 function2) {
        int size = collection.size();
        if (size == 0) {
            return (Object[]) function0.invoke();
        }
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            return (Object[]) function0.invoke();
        }
        Object[] objArr = (Object[]) function1.invoke(Integer.valueOf(size));
        int i = 0;
        while (true) {
            int i2 = i;
            i++;
            objArr[i2] = it.next();
            if (i >= objArr.length) {
                if (!it.hasNext()) {
                    return objArr;
                }
                int i3 = ((i * 3) + 1) >>> 1;
                if (i3 <= i) {
                    if (i >= MAX_SIZE) {
                        throw new OutOfMemoryError();
                    }
                    i3 = MAX_SIZE;
                }
                Object[] objArrCopyOf = Arrays.copyOf(objArr, i3);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "Arrays.copyOf(result, newSize)");
                objArr = objArrCopyOf;
            } else if (!it.hasNext()) {
                return (Object[]) function2.invoke(objArr, Integer.valueOf(i));
            }
        }
    }

    @JvmName(name = "toArray")
    @NotNull
    public static final Object[] toArray(@NotNull Collection collection) {
        Intrinsics.checkParameterIsNotNull(collection, "collection");
        int size = collection.size();
        if (size == 0) {
            return EMPTY;
        }
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            return EMPTY;
        }
        Object[] objArr = new Object[size];
        int i = 0;
        while (true) {
            int i2 = i;
            i++;
            objArr[i2] = it.next();
            if (i >= objArr.length) {
                if (!it.hasNext()) {
                    return objArr;
                }
                int i3 = ((i * 3) + 1) >>> 1;
                if (i3 <= i) {
                    if (i >= MAX_SIZE) {
                        throw new OutOfMemoryError();
                    }
                    i3 = MAX_SIZE;
                }
                Object[] objArrCopyOf = Arrays.copyOf(objArr, i3);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "Arrays.copyOf(result, newSize)");
                objArr = objArrCopyOf;
            } else if (!it.hasNext()) {
                Object[] objArrCopyOf2 = Arrays.copyOf(objArr, i);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf2, "Arrays.copyOf(result, size)");
                return objArrCopyOf2;
            }
        }
    }
}

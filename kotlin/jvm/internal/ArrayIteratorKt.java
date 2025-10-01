package kotlin.jvm.internal;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a%\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, m27d2 = {"iterator", "", "T", "array", "", "([Ljava/lang/Object;)Ljava/util/Iterator;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/jvm/internal/ArrayIteratorKt.class */
public final class ArrayIteratorKt {
    @NotNull
    public static final Iterator iterator(@NotNull Object[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        return new ArrayIterator(array);
    }
}

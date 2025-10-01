package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0010\n\ufffd\ufffd\n\u0002\u0010\u0011\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a!\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000b\b\ufffd\ufffd\u0010\u0002\u0018\u0001\u00a2\u0006\u0002\b\u0003H\u0086\b\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, m27d2 = {"emptyArray", "", "T", "Lkotlin/internal/PureReifiable;", "()[Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ArrayIntrinsicsKt.class */
public final class ArrayIntrinsicsKt {
    @NotNull
    public static final Object[] emptyArray() {
        Intrinsics.reifiedOperationMarker(0, "T?");
        return new Object[0];
    }
}

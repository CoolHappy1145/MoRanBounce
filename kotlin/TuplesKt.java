package kotlin;

import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0016\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a2\u0010\ufffd\ufffd\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0003H\u0086\u0004\u00a2\u0006\u0002\u0010\u0005\u001a\"\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\b*\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b0\u0001\u001a(\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\b*\u0014\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b0\t\u00a8\u0006\n"}, m27d2 = {"to", "Lkotlin/Pair;", "A", "B", "that", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "toList", "", "T", "Lkotlin/Triple;", "kotlin-stdlib"})
@JvmName(name = "TuplesKt")
/* loaded from: L-out.jar:kotlin/TuplesKt.class */
public final class TuplesKt {
    @NotNull
    /* renamed from: to */
    public static final Pair m32to(Object obj, Object obj2) {
        return new Pair(obj, obj2);
    }

    @NotNull
    public static final List toList(@NotNull Pair toList) {
        Intrinsics.checkParameterIsNotNull(toList, "$this$toList");
        return CollectionsKt.listOf(new Object[]{toList.getFirst(), toList.getSecond()});
    }

    @NotNull
    public static final List toList(@NotNull Triple toList) {
        Intrinsics.checkParameterIsNotNull(toList, "$this$toList");
        return CollectionsKt.listOf(new Object[]{toList.getFirst(), toList.getSecond(), toList.getThird()});
    }
}

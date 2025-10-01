package kotlin.comparisons;

import java.util.Comparator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0010\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aG\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\b\u001a?\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\t\u001aG\u0010\n\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\b\u001a?\u0010\n\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\t\u00a8\u0006\u000b"}, m27d2 = {"maxOf", "T", "a", "b", "c", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "minOf", "kotlin-stdlib"}, m28xs = "kotlin/comparisons/ComparisonsKt")
/* loaded from: L-out.jar:kotlin/comparisons/ComparisonsKt___ComparisonsKt.class */
class ComparisonsKt___ComparisonsKt extends ComparisonsKt___ComparisonsJvmKt {
    @SinceKotlin(version = "1.1")
    public static final Object maxOf(Object obj, Object obj2, Object obj3, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return ComparisonsKt.maxOf(obj, ComparisonsKt.maxOf(obj2, obj3, comparator), comparator);
    }

    @SinceKotlin(version = "1.1")
    public static final Object maxOf(Object obj, Object obj2, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return comparator.compare(obj, obj2) >= 0 ? obj : obj2;
    }

    @SinceKotlin(version = "1.1")
    public static final Object minOf(Object obj, Object obj2, Object obj3, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return ComparisonsKt.minOf(obj, ComparisonsKt.minOf(obj2, obj3, comparator), comparator);
    }

    @SinceKotlin(version = "1.1")
    public static final Object minOf(Object obj, Object obj2, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return comparator.compare(obj, obj2) <= 0 ? obj : obj2;
    }
}

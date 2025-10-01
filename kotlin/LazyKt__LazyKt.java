package kotlin;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0018\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002\u00a2\u0006\u0002\u0010\u0004\u001a4\u0010\u0005\u001a\u0002H\u0002\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0087\n\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, m27d2 = {"lazyOf", "Lkotlin/Lazy;", "T", PropertyDescriptor.VALUE, "(Ljava/lang/Object;)Lkotlin/Lazy;", "getValue", "thisRef", "", "property", "Lkotlin/reflect/KProperty;", "(Lkotlin/Lazy;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "kotlin-stdlib"}, m28xs = "kotlin/LazyKt")
/* loaded from: L-out.jar:kotlin/LazyKt__LazyKt.class */
class LazyKt__LazyKt extends LazyKt__LazyJVMKt {
    @NotNull
    public static final Lazy lazyOf(Object obj) {
        return new InitializedLazyImpl(obj);
    }

    @InlineOnly
    private static final Object getValue(@NotNull Lazy getValue, Object obj, KProperty kProperty) {
        Intrinsics.checkParameterIsNotNull(getValue, "$this$getValue");
        return getValue.getValue();
    }
}

package kotlin.coroutines.intrinsics;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0016\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\ufffd\ufffd\u0010\u00072\u001c\b\u0004\u0010\b\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\tH\u0087H\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000b\"\u001a\u0010\ufffd\ufffd\u001a\u00020\u00018FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, m27d2 = {"COROUTINE_SUSPENDED", "", "COROUTINE_SUSPENDED$annotations", "()V", "getCOROUTINE_SUSPENDED", "()Ljava/lang/Object;", "suspendCoroutineUninterceptedOrReturn", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, m28xs = "kotlin/coroutines/intrinsics/IntrinsicsKt")
/* loaded from: L-out.jar:kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsKt.class */
class IntrinsicsKt__IntrinsicsKt extends IntrinsicsKt__IntrinsicsJvmKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object suspendCoroutineUninterceptedOrReturn(Function1 function1, Continuation continuation) {
        throw new NotImplementedError("Implementation of suspendCoroutineUninterceptedOrReturn is intrinsic");
    }

    @NotNull
    public static final Object getCOROUTINE_SUSPENDED() {
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }
}

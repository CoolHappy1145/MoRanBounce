package kotlin.coroutines.experimental.intrinsics;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.SinceKotlin;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\u001a5\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u001c\b\u0004\u0010\u0002\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003H\u0087H\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006\u001a5\u0010\u0007\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u001c\b\u0004\u0010\u0002\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003H\u0087H\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006\u001a\u001f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004\"\u0004\b\ufffd\ufffd\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b\u0082\u0002\u0004\n\u0002\b\t\u00a8\u0006\t"}, m27d2 = {"suspendCoroutineOrReturn", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/experimental/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "suspendCoroutineUninterceptedOrReturn", "intercepted", "kotlin-stdlib-coroutines"}, m28xs = "kotlin/coroutines/experimental/intrinsics/IntrinsicsKt")
/* loaded from: L-out.jar:kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsKt.class */
class IntrinsicsKt__IntrinsicsKt extends IntrinsicsKt__IntrinsicsJvmKt {
    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Object suspendCoroutineOrReturn(Function1 function1, Continuation continuation) {
        return function1.invoke(CoroutineIntrinsics.normalizeContinuation(continuation));
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final Object suspendCoroutineUninterceptedOrReturn(Function1 function1, Continuation continuation) {
        throw new NotImplementedError("Implementation of suspendCoroutineUninterceptedOrReturn is intrinsic");
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final Continuation intercepted(@NotNull Continuation continuation) {
        throw new NotImplementedError("Implementation of intercepted is intrinsic");
    }
}

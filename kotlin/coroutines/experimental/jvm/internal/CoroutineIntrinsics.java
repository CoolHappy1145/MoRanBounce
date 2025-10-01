package kotlin.coroutines.experimental.jvm.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\ufffd\ufffd\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\u00a8\u0006\u0007"}, m27d2 = {"interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines"})
@JvmName(name = "CoroutineIntrinsics")
/* loaded from: L-out.jar:kotlin/coroutines/experimental/jvm/internal/CoroutineIntrinsics.class */
public final class CoroutineIntrinsics {
    @NotNull
    public static final Continuation normalizeContinuation(@NotNull Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        Continuation continuation2 = continuation;
        if (!(continuation2 instanceof CoroutineImpl)) {
            continuation2 = null;
        }
        CoroutineImpl coroutineImpl = (CoroutineImpl) continuation2;
        if (coroutineImpl != null) {
            Continuation facade = coroutineImpl.getFacade();
            if (facade != null) {
                return facade;
            }
        }
        return continuation;
    }

    @NotNull
    public static final Continuation interceptContinuationIfNeeded(@NotNull CoroutineContext context, @NotNull Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) context.get(ContinuationInterceptor.Key);
        if (continuationInterceptor != null) {
            Continuation continuationInterceptContinuation = continuationInterceptor.interceptContinuation(continuation);
            if (continuationInterceptContinuation != null) {
                return continuationInterceptContinuation;
            }
        }
        return continuation;
    }
}

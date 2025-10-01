package kotlin.coroutines.experimental.migration;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004\u00a2\u0006\u0002\u0010\u0005J\u001e\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u000fH\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0010R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, m27d2 = {"Lkotlin/coroutines/experimental/migration/ContinuationMigration;", "T", "Lkotlin/coroutines/Continuation;", "continuation", "Lkotlin/coroutines/experimental/Continuation;", "(Lkotlin/coroutines/experimental/Continuation;)V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getContinuation", "()Lkotlin/coroutines/experimental/Continuation;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/migration/ContinuationMigration.class */
final class ContinuationMigration implements Continuation {

    @NotNull
    private final CoroutineContext context;

    @NotNull
    private final kotlin.coroutines.experimental.Continuation continuation;

    @NotNull
    public final kotlin.coroutines.experimental.Continuation getContinuation() {
        return this.continuation;
    }

    public ContinuationMigration(@NotNull kotlin.coroutines.experimental.Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        this.continuation = continuation;
        this.context = CoroutinesMigrationKt.toCoroutineContext(this.continuation.getContext());
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return this.context;
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object obj) {
        if (!(obj instanceof Result.Failure)) {
            this.continuation.resume(obj);
        }
        Throwable thM502exceptionOrNullimpl = Result.m502exceptionOrNullimpl(obj);
        if (thM502exceptionOrNullimpl != null) {
            this.continuation.resumeWithException(thM502exceptionOrNullimpl);
        }
    }
}

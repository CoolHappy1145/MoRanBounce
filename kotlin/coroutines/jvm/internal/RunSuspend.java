package kotlin.coroutines.jvm.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0006\u0010\u000e\u001a\u00020\u0002J\u001e\u0010\u000f\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\tH\u0016\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R%\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\tX\u0086\u000e\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, m27d2 = {"Lkotlin/coroutines/jvm/internal/RunSuspend;", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "result", "Lkotlin/Result;", "getResult", "()Lkotlin/Result;", "setResult", "(Lkotlin/Result;)V", "await", "resumeWith", "(Ljava/lang/Object;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/coroutines/jvm/internal/RunSuspend.class */
final class RunSuspend implements Continuation {

    @Nullable
    private Result result;

    @Override // kotlin.coroutines.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Nullable
    public final Result getResult() {
        return this.result;
    }

    public final void setResult(@Nullable Result result) {
        this.result = result;
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object obj) {
        synchronized (this) {
            this.result = Result.m504boximpl(obj);
            if (this == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
            }
            notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void await() {
        synchronized (this) {
            while (true) {
                Result result = this.result;
                if (result == null) {
                    if (this == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                    wait();
                } else {
                    ResultKt.throwOnFailure(result.m507unboximpl());
                }
            }
        }
    }
}

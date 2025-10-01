package kotlin.coroutines.experimental.jvm.internal;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\b&\u0018\ufffd\ufffd2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0010\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0007J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\u0010\u0014\u001a\u0004\u0018\u00010\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016J\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016J\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u00022\b\u0010\u0016\u001a\u0004\u0018\u00010\u00022\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H$J\u0012\u0010\u0019\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0018\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00038\u0004@\u0004X\u0085\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u00058\u0004@\u0004X\u0085\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001b"}, m27d2 = {"Lkotlin/coroutines/experimental/jvm/internal/CoroutineImpl;", "Lkotlin/jvm/internal/Lambda;", "", "Lkotlin/coroutines/experimental/Continuation;", "arity", "", "completion", "(ILkotlin/coroutines/experimental/Continuation;)V", "_context", "Lkotlin/coroutines/experimental/CoroutineContext;", "_facade", "context", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "facade", "getFacade", "()Lkotlin/coroutines/experimental/Continuation;", "label", "create", "", PropertyDescriptor.VALUE, "doResume", "data", "exception", "", "resume", "resumeWithException", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/jvm/internal/CoroutineImpl.class */
public abstract class CoroutineImpl extends Lambda implements Continuation {

    @JvmField
    protected int label;
    private final CoroutineContext _context;
    private Continuation _facade;

    @JvmField
    @Nullable
    protected Continuation completion;

    @Nullable
    protected abstract Object doResume(@Nullable Object obj, @Nullable Throwable th);

    public CoroutineImpl(int i, @Nullable Continuation continuation) {
        super(i);
        this.completion = continuation;
        this.label = this.completion != null ? 0 : -1;
        Continuation continuation2 = this.completion;
        this._context = continuation2 != null ? continuation2.getContext() : null;
    }

    @Override // kotlin.coroutines.experimental.Continuation
    @NotNull
    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this._context;
        if (coroutineContext == null) {
            Intrinsics.throwNpe();
        }
        return coroutineContext;
    }

    @NotNull
    public final Continuation getFacade() {
        if (this._facade == null) {
            CoroutineContext coroutineContext = this._context;
            if (coroutineContext == null) {
                Intrinsics.throwNpe();
            }
            this._facade = CoroutineIntrinsics.interceptContinuationIfNeeded(coroutineContext, this);
        }
        Continuation continuation = this._facade;
        if (continuation == null) {
            Intrinsics.throwNpe();
        }
        return continuation;
    }

    @Override // kotlin.coroutines.experimental.Continuation
    public void resume(@Nullable Object obj) {
        Continuation continuation = this.completion;
        if (continuation == null) {
            Intrinsics.throwNpe();
        }
        try {
            Object objDoResume = doResume(obj, null);
            if (objDoResume != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                if (continuation == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                }
                continuation.resume(objDoResume);
            }
        } catch (Throwable th) {
            continuation.resumeWithException(th);
        }
    }

    @Override // kotlin.coroutines.experimental.Continuation
    public void resumeWithException(@NotNull Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Continuation continuation = this.completion;
        if (continuation == null) {
            Intrinsics.throwNpe();
        }
        try {
            Object objDoResume = doResume(null, exception);
            if (objDoResume != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                if (continuation == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                }
                continuation.resume(objDoResume);
            }
        } catch (Throwable th) {
            continuation.resumeWithException(th);
        }
    }

    @NotNull
    public Continuation create(@NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        throw new IllegalStateException("create(Continuation) has not been overridden");
    }

    @NotNull
    public Continuation create(@Nullable Object obj, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        throw new IllegalStateException("create(Any?;Continuation) has not been overridden");
    }
}

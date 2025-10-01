package kotlin.coroutines.experimental;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0001\u0018\ufffd\ufffd \u0015*\u0006\b\ufffd\ufffd\u0010\u0001 \ufffd\ufffd2\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0002\u0015\u0016B\u0015\b\u0011\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002\u00a2\u0006\u0002\u0010\u0004B\u001f\b\ufffd\ufffd\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\n\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0001J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\ufffd\ufffdH\u0016\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0017"}, m27d2 = {"Lkotlin/coroutines/experimental/SafeContinuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "delegate", "(Lkotlin/coroutines/experimental/Continuation;)V", "initialResult", "", "(Lkotlin/coroutines/experimental/Continuation;Ljava/lang/Object;)V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "result", "getResult", "resume", "", PropertyDescriptor.VALUE, "(Ljava/lang/Object;)V", "resumeWithException", "exception", "", "Companion", "Fail", "kotlin-stdlib-coroutines"})
@PublishedApi
/* loaded from: L-out.jar:kotlin/coroutines/experimental/SafeContinuation.class */
public final class SafeContinuation implements Continuation {
    private Object result;
    private final Continuation delegate;
    public static final Companion Companion = new Companion(null);
    private static final Object UNDECIDED = new Object();
    private static final Object RESUMED = new Object();
    private static final AtomicReferenceFieldUpdater RESULT = AtomicReferenceFieldUpdater.newUpdater(SafeContinuation.class, Object.class, "result");

    public SafeContinuation(@NotNull Continuation delegate, @Nullable Object obj) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        this.delegate = delegate;
        this.result = obj;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @PublishedApi
    public SafeContinuation(@NotNull Continuation delegate) {
        this(delegate, UNDECIDED);
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
    }

    @Override // kotlin.coroutines.experimental.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return this.delegate.getContext();
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002RZ\u0010\u0003\u001aF\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0001 \u0006*\"\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u00040\u00048\u0002X\u0083\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\u0007\u0010\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\n"}, m27d2 = {"Lkotlin/coroutines/experimental/SafeContinuation$Companion;", "", "()V", "RESULT", "Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;", "Lkotlin/coroutines/experimental/SafeContinuation;", "kotlin.jvm.PlatformType", "RESULT$annotations", "RESUMED", "UNDECIDED", "kotlin-stdlib-coroutines"})
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/SafeContinuation$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0003\n\u0002\b\u0004\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/coroutines/experimental/SafeContinuation$Fail;", "", "exception", "", "(Ljava/lang/Throwable;)V", "getException", "()Ljava/lang/Throwable;", "kotlin-stdlib-coroutines"})
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/SafeContinuation$Fail.class */
    private static final class Fail {

        @NotNull
        private final Throwable exception;

        @NotNull
        public final Throwable getException() {
            return this.exception;
        }

        public Fail(@NotNull Throwable exception) {
            Intrinsics.checkParameterIsNotNull(exception, "exception");
            this.exception = exception;
        }
    }

    @Override // kotlin.coroutines.experimental.Continuation
    public void resume(Object obj) {
        while (true) {
            Object obj2 = this.result;
            if (obj2 == UNDECIDED) {
                if (RESULT.compareAndSet(this, UNDECIDED, obj)) {
                    return;
                }
            } else {
                if (obj2 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                }
                if (RESULT.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), RESUMED)) {
                    this.delegate.resume(obj);
                    return;
                }
            }
        }
    }

    @Override // kotlin.coroutines.experimental.Continuation
    public void resumeWithException(@NotNull Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        while (true) {
            Object obj = this.result;
            if (obj == UNDECIDED) {
                if (RESULT.compareAndSet(this, UNDECIDED, new Fail(exception))) {
                    return;
                }
            } else {
                if (obj != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                }
                if (RESULT.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), RESUMED)) {
                    this.delegate.resumeWithException(exception);
                    return;
                }
            }
        }
    }

    @PublishedApi
    @Nullable
    public final Object getResult() throws Throwable {
        Object obj = this.result;
        if (obj == UNDECIDED) {
            if (RESULT.compareAndSet(this, UNDECIDED, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
                return IntrinsicsKt.getCOROUTINE_SUSPENDED();
            }
            obj = this.result;
        }
        if (obj == RESUMED) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (obj instanceof Fail) {
            throw ((Fail) obj).getException();
        }
        return obj;
    }
}

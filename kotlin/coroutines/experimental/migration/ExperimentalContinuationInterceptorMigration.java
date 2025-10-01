package kotlin.coroutines.experimental.migration;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\ufffd\ufffd\u0010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalContinuationInterceptorMigration;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "interceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "(Lkotlin/coroutines/ContinuationInterceptor;)V", "getInterceptor", "()Lkotlin/coroutines/ContinuationInterceptor;", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/experimental/CoroutineContext$Key;", "interceptContinuation", "Lkotlin/coroutines/experimental/Continuation;", "T", "continuation", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/migration/ExperimentalContinuationInterceptorMigration.class */
final class ExperimentalContinuationInterceptorMigration implements ContinuationInterceptor {

    @NotNull
    private final kotlin.coroutines.ContinuationInterceptor interceptor;

    @NotNull
    public final kotlin.coroutines.ContinuationInterceptor getInterceptor() {
        return this.interceptor;
    }

    public ExperimentalContinuationInterceptorMigration(@NotNull kotlin.coroutines.ContinuationInterceptor interceptor) {
        Intrinsics.checkParameterIsNotNull(interceptor, "interceptor");
        this.interceptor = interceptor;
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element, kotlin.coroutines.experimental.CoroutineContext
    @Nullable
    public CoroutineContext.Element get(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return ContinuationInterceptor.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element, kotlin.coroutines.experimental.CoroutineContext
    public Object fold(Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        return ContinuationInterceptor.DefaultImpls.fold(this, obj, operation);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element, kotlin.coroutines.experimental.CoroutineContext
    @NotNull
    public CoroutineContext minusKey(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return ContinuationInterceptor.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return ContinuationInterceptor.DefaultImpls.plus(this, context);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element
    @NotNull
    public CoroutineContext.Key getKey() {
        return ContinuationInterceptor.Key;
    }

    @Override // kotlin.coroutines.experimental.ContinuationInterceptor
    @NotNull
    public Continuation interceptContinuation(@NotNull Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        return CoroutinesMigrationKt.toExperimentalContinuation(this.interceptor.interceptContinuation(CoroutinesMigrationKt.toContinuation(continuation)));
    }
}

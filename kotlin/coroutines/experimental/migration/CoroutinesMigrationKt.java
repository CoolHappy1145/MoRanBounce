package kotlin.coroutines.experimental.migration;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u001e\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0005H\u0007\u001a\f\u0010\f\u001a\u00020\t*\u00020\bH\u0007\u001a^\u0010\r\u001a\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\"\u0004\b\ufffd\ufffd\u0010\u000f\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u0011*\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eH\ufffd\ufffd\u001aL\u0010\r\u001a\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013\"\u0004\b\ufffd\ufffd\u0010\u000f\"\u0004\b\u0001\u0010\u0011*\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013H\ufffd\ufffd\u001a:\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\"\u0004\b\ufffd\ufffd\u0010\u0011*\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014H\ufffd\ufffd\u00a8\u0006\u0015"}, m27d2 = {"toContinuation", "Lkotlin/coroutines/Continuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "toContinuationInterceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "toCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "toExperimentalContinuation", "toExperimentalContinuationInterceptor", "toExperimentalCoroutineContext", "toExperimentalSuspendFunction", "Lkotlin/Function3;", "T1", "T2", "R", "", "Lkotlin/Function2;", "Lkotlin/Function1;", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/migration/CoroutinesMigrationKt.class */
public final class CoroutinesMigrationKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation toExperimentalContinuation(@NotNull kotlin.coroutines.Continuation toExperimentalContinuation) {
        Intrinsics.checkParameterIsNotNull(toExperimentalContinuation, "$this$toExperimentalContinuation");
        kotlin.coroutines.Continuation continuation = toExperimentalContinuation;
        if (!(continuation instanceof ContinuationMigration)) {
            continuation = null;
        }
        ContinuationMigration continuationMigration = (ContinuationMigration) continuation;
        if (continuationMigration != null) {
            Continuation continuation2 = continuationMigration.getContinuation();
            if (continuation2 != null) {
                return continuation2;
            }
        }
        return new ExperimentalContinuationMigration(toExperimentalContinuation);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final kotlin.coroutines.Continuation toContinuation(@NotNull Continuation toContinuation) {
        Intrinsics.checkParameterIsNotNull(toContinuation, "$this$toContinuation");
        Continuation continuation = toContinuation;
        if (!(continuation instanceof ExperimentalContinuationMigration)) {
            continuation = null;
        }
        ExperimentalContinuationMigration experimentalContinuationMigration = (ExperimentalContinuationMigration) continuation;
        if (experimentalContinuationMigration != null) {
            kotlin.coroutines.Continuation continuation2 = experimentalContinuationMigration.getContinuation();
            if (continuation2 != null) {
                return continuation2;
            }
        }
        return new ContinuationMigration(toContinuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x004d  */
    @SinceKotlin(version = "1.3")
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineContext toExperimentalCoroutineContext(@NotNull kotlin.coroutines.CoroutineContext toExperimentalCoroutineContext) {
        EmptyCoroutineContext context;
        Intrinsics.checkParameterIsNotNull(toExperimentalCoroutineContext, "$this$toExperimentalCoroutineContext");
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) toExperimentalCoroutineContext.get(ContinuationInterceptor.Key);
        ContextMigration contextMigration = (ContextMigration) toExperimentalCoroutineContext.get(ContextMigration.Key);
        kotlin.coroutines.CoroutineContext coroutineContextMinusKey = toExperimentalCoroutineContext.minusKey(ContinuationInterceptor.Key).minusKey(ContextMigration.Key);
        if (contextMigration != null) {
            context = contextMigration.getContext();
            if (context == null) {
                context = EmptyCoroutineContext.INSTANCE;
            }
        }
        CoroutineContext coroutineContext = context;
        CoroutineContext coroutineContextPlus = coroutineContextMinusKey == kotlin.coroutines.EmptyCoroutineContext.INSTANCE ? coroutineContext : coroutineContext.plus(new ExperimentalContextMigration(coroutineContextMinusKey));
        return continuationInterceptor == null ? coroutineContextPlus : coroutineContextPlus.plus(toExperimentalContinuationInterceptor(continuationInterceptor));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x004d  */
    @SinceKotlin(version = "1.3")
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final kotlin.coroutines.CoroutineContext toCoroutineContext(@NotNull CoroutineContext toCoroutineContext) {
        kotlin.coroutines.EmptyCoroutineContext context;
        Intrinsics.checkParameterIsNotNull(toCoroutineContext, "$this$toCoroutineContext");
        kotlin.coroutines.experimental.ContinuationInterceptor continuationInterceptor = (kotlin.coroutines.experimental.ContinuationInterceptor) toCoroutineContext.get(kotlin.coroutines.experimental.ContinuationInterceptor.Key);
        ExperimentalContextMigration experimentalContextMigration = (ExperimentalContextMigration) toCoroutineContext.get(ExperimentalContextMigration.Key);
        CoroutineContext coroutineContextMinusKey = toCoroutineContext.minusKey(kotlin.coroutines.experimental.ContinuationInterceptor.Key).minusKey(ExperimentalContextMigration.Key);
        if (experimentalContextMigration != null) {
            context = experimentalContextMigration.getContext();
            if (context == null) {
                context = kotlin.coroutines.EmptyCoroutineContext.INSTANCE;
            }
        }
        kotlin.coroutines.CoroutineContext coroutineContext = context;
        kotlin.coroutines.CoroutineContext coroutineContextPlus = coroutineContextMinusKey == EmptyCoroutineContext.INSTANCE ? coroutineContext : coroutineContext.plus(new ContextMigration(coroutineContextMinusKey));
        return continuationInterceptor == null ? coroutineContextPlus : coroutineContextPlus.plus(toContinuationInterceptor(continuationInterceptor));
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final kotlin.coroutines.experimental.ContinuationInterceptor toExperimentalContinuationInterceptor(@NotNull ContinuationInterceptor toExperimentalContinuationInterceptor) {
        Intrinsics.checkParameterIsNotNull(toExperimentalContinuationInterceptor, "$this$toExperimentalContinuationInterceptor");
        ContinuationInterceptor continuationInterceptor = toExperimentalContinuationInterceptor;
        if (!(continuationInterceptor instanceof ContinuationInterceptorMigration)) {
            continuationInterceptor = null;
        }
        ContinuationInterceptorMigration continuationInterceptorMigration = (ContinuationInterceptorMigration) continuationInterceptor;
        if (continuationInterceptorMigration != null) {
            kotlin.coroutines.experimental.ContinuationInterceptor interceptor = continuationInterceptorMigration.getInterceptor();
            if (interceptor != null) {
                return interceptor;
            }
        }
        return new ExperimentalContinuationInterceptorMigration(toExperimentalContinuationInterceptor);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final ContinuationInterceptor toContinuationInterceptor(@NotNull kotlin.coroutines.experimental.ContinuationInterceptor toContinuationInterceptor) {
        Intrinsics.checkParameterIsNotNull(toContinuationInterceptor, "$this$toContinuationInterceptor");
        kotlin.coroutines.experimental.ContinuationInterceptor continuationInterceptor = toContinuationInterceptor;
        if (!(continuationInterceptor instanceof ExperimentalContinuationInterceptorMigration)) {
            continuationInterceptor = null;
        }
        ExperimentalContinuationInterceptorMigration experimentalContinuationInterceptorMigration = (ExperimentalContinuationInterceptorMigration) continuationInterceptor;
        if (experimentalContinuationInterceptorMigration != null) {
            ContinuationInterceptor interceptor = experimentalContinuationInterceptorMigration.getInterceptor();
            if (interceptor != null) {
                return interceptor;
            }
        }
        return new ContinuationInterceptorMigration(toContinuationInterceptor);
    }

    @NotNull
    public static final Function1 toExperimentalSuspendFunction(@NotNull Function1 toExperimentalSuspendFunction) {
        Intrinsics.checkParameterIsNotNull(toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction0Migration(toExperimentalSuspendFunction);
    }

    @NotNull
    public static final Function2 toExperimentalSuspendFunction(@NotNull Function2 toExperimentalSuspendFunction) {
        Intrinsics.checkParameterIsNotNull(toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction1Migration(toExperimentalSuspendFunction);
    }

    @NotNull
    public static final Function3 toExperimentalSuspendFunction(@NotNull Function3 toExperimentalSuspendFunction) {
        Intrinsics.checkParameterIsNotNull(toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction2Migration(toExperimentalSuspendFunction);
    }
}

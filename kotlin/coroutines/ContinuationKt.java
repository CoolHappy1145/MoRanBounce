package kotlin.coroutines;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd>\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0002\b\u0004\u001a<\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\b2\u0006\u0010\t\u001a\u00020\u00012\u001a\b\u0004\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\f\u0012\u0004\u0012\u00020\r0\u000bH\u0087\b\u00f8\u0001\ufffd\ufffd\u001a3\u0010\u000e\u001a\u0002H\b\"\u0004\b\ufffd\ufffd\u0010\b2\u001a\b\u0004\u0010\u000f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0004\u0012\u00020\r0\u000bH\u0087H\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0010\u001aD\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\"\u0004\b\ufffd\ufffd\u0010\b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0014\u001a]\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\"\u0004\b\ufffd\ufffd\u0010\u0015\"\u0004\b\u0001\u0010\b*#\b\u0001\u0012\u0004\u0012\u0002H\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0016\u00a2\u0006\u0002\b\u00172\u0006\u0010\u0018\u001a\u0002H\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0019\u001a&\u0010\u001a\u001a\u00020\r\"\u0004\b\ufffd\ufffd\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00072\u0006\u0010\u001b\u001a\u0002H\bH\u0087\b\u00a2\u0006\u0002\u0010\u001c\u001a!\u0010\u001d\u001a\u00020\r\"\u0004\b\ufffd\ufffd\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00072\u0006\u0010\u001e\u001a\u00020\u001fH\u0087\b\u001a>\u0010 \u001a\u00020\r\"\u0004\b\ufffd\ufffd\u0010\b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010!\u001aW\u0010 \u001a\u00020\r\"\u0004\b\ufffd\ufffd\u0010\u0015\"\u0004\b\u0001\u0010\b*#\b\u0001\u0012\u0004\u0012\u0002H\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0016\u00a2\u0006\u0002\b\u00172\u0006\u0010\u0018\u001a\u0002H\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\"\"\u001b\u0010\ufffd\ufffd\u001a\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, m27d2 = {"coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "coroutineContext$annotations", "()V", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "Continuation", "Lkotlin/coroutines/Continuation;", "T", "context", "resumeWith", "Lkotlin/Function1;", "Lkotlin/Result;", "", "suspendCoroutine", "block", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCoroutine", "", "completion", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resume", PropertyDescriptor.VALUE, "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V", "resumeWithException", "exception", "", "startCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/coroutines/ContinuationKt.class */
public final class ContinuationKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final void resume(@NotNull Continuation continuation, Object obj) {
        Result.Companion companion = Result.Companion;
        continuation.resumeWith(obj);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final void resumeWithException(@NotNull Continuation continuation, Throwable th) {
        Result.Companion companion = Result.Companion;
        continuation.resumeWith(ResultKt.createFailure(th));
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Continuation Continuation(CoroutineContext coroutineContext, Function1 function1) {
        return new Continuation(coroutineContext, function1) { // from class: kotlin.coroutines.ContinuationKt.Continuation.1
            final CoroutineContext $context;
            final Function1 $resumeWith;

            {
                this.$context = coroutineContext;
                this.$resumeWith = function1;
            }

            @Override // kotlin.coroutines.Continuation
            @NotNull
            public CoroutineContext getContext() {
                return this.$context;
            }

            @Override // kotlin.coroutines.Continuation
            public void resumeWith(@NotNull Object obj) {
                this.$resumeWith.invoke(Result.m504boximpl(obj));
            }
        };
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation createCoroutine(@NotNull Function1 createCoroutine, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(createCoroutine, "$this$createCoroutine");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        return new SafeContinuation(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(createCoroutine, completion)), IntrinsicsKt.getCOROUTINE_SUSPENDED());
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation createCoroutine(@NotNull Function2 createCoroutine, Object obj, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(createCoroutine, "$this$createCoroutine");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        return new SafeContinuation(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(createCoroutine, obj, completion)), IntrinsicsKt.getCOROUTINE_SUSPENDED());
    }

    @SinceKotlin(version = "1.3")
    public static final void startCoroutine(@NotNull Function1 startCoroutine, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(startCoroutine, "$this$startCoroutine");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        Continuation continuationIntercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(startCoroutine, completion));
        Unit unit = Unit.INSTANCE;
        Result.Companion companion = Result.Companion;
        continuationIntercepted.resumeWith(unit);
    }

    @SinceKotlin(version = "1.3")
    public static final void startCoroutine(@NotNull Function2 startCoroutine, Object obj, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(startCoroutine, "$this$startCoroutine");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        Continuation continuationIntercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(startCoroutine, obj, completion));
        Unit unit = Unit.INSTANCE;
        Result.Companion companion = Result.Companion;
        continuationIntercepted.resumeWith(unit);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object suspendCoroutine(Function1 function1, Continuation continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        function1.invoke(safeContinuation);
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private static final CoroutineContext getCoroutineContext() {
        throw new NotImplementedError("Implemented as intrinsic");
    }
}

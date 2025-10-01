package kotlin.coroutines.experimental.intrinsics;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd2\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a:\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u00072\u0010\b\u0004\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fH\u0082\b\u00a2\u0006\u0002\b\r\u001aD\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0010\u001a]\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\ufffd\ufffd\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012\u00a2\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0015\u001aA\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\ufffd\ufffd\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001aZ\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\ufffd\ufffd\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012\u00a2\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0018\"\u001a\u0010\ufffd\ufffd\u001a\u00020\u00018FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\t\u00a8\u0006\u0019"}, m27d2 = {"COROUTINE_SUSPENDED", "", "COROUTINE_SUSPENDED$annotations", "()V", "getCOROUTINE_SUSPENDED", "()Ljava/lang/Object;", "buildContinuationByInvokeCall", "Lkotlin/coroutines/experimental/Continuation;", "", "T", "completion", "block", "Lkotlin/Function0;", "buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnchecked", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"}, m28xs = "kotlin/coroutines/experimental/intrinsics/IntrinsicsKt")
/* loaded from: L-out.jar:kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsJvmKt.class */
class IntrinsicsKt__IntrinsicsJvmKt {
    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Object startCoroutineUninterceptedOrReturn(@NotNull Function1 function1, Continuation continuation) {
        if (function1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
        }
        return ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(continuation);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Object startCoroutineUninterceptedOrReturn(@NotNull Function2 function2, Object obj, Continuation continuation) {
        if (function2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
        }
        return ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(obj, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Continuation createCoroutineUnchecked(@NotNull final Function1 createCoroutineUnchecked, @NotNull final Continuation completion) {
        Intrinsics.checkParameterIsNotNull(createCoroutineUnchecked, "$this$createCoroutineUnchecked");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        if (createCoroutineUnchecked instanceof CoroutineImpl) {
            Continuation continuationCreate = ((CoroutineImpl) createCoroutineUnchecked).create(completion);
            if (continuationCreate == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
            }
            return ((CoroutineImpl) continuationCreate).getFacade();
        }
        return CoroutineIntrinsics.interceptContinuationIfNeeded(completion.getContext(), new Continuation(completion, createCoroutineUnchecked, completion) { // from class: kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$1
            final Continuation $completion;
            final Function1 $this_createCoroutineUnchecked$inlined;
            final Continuation $completion$inlined;

            {
                this.$completion = completion;
                this.$this_createCoroutineUnchecked$inlined = createCoroutineUnchecked;
                this.$completion$inlined = completion;
            }

            @Override // kotlin.coroutines.experimental.Continuation
            public void resume(Object obj) {
                resume((Unit) obj);
            }

            @Override // kotlin.coroutines.experimental.Continuation
            @NotNull
            public CoroutineContext getContext() {
                return this.$completion.getContext();
            }

            public void resume(@NotNull Unit value) {
                Intrinsics.checkParameterIsNotNull(value, "value");
                Continuation continuation = this.$completion;
                try {
                    Function1 function1 = this.$this_createCoroutineUnchecked$inlined;
                    if (function1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
                    }
                    Object objInvoke = ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(this.$completion$inlined);
                    if (objInvoke != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        if (continuation == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                        }
                        continuation.resume(objInvoke);
                    }
                } catch (Throwable th) {
                    continuation.resumeWithException(th);
                }
            }

            @Override // kotlin.coroutines.experimental.Continuation
            public void resumeWithException(@NotNull Throwable exception) {
                Intrinsics.checkParameterIsNotNull(exception, "exception");
                this.$completion.resumeWithException(exception);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Continuation createCoroutineUnchecked(@NotNull final Function2 createCoroutineUnchecked, final Object obj, @NotNull final Continuation completion) {
        Intrinsics.checkParameterIsNotNull(createCoroutineUnchecked, "$this$createCoroutineUnchecked");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        if (createCoroutineUnchecked instanceof CoroutineImpl) {
            Continuation continuationCreate = ((CoroutineImpl) createCoroutineUnchecked).create(obj, completion);
            if (continuationCreate == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
            }
            return ((CoroutineImpl) continuationCreate).getFacade();
        }
        return CoroutineIntrinsics.interceptContinuationIfNeeded(completion.getContext(), new Continuation(completion, createCoroutineUnchecked, obj, completion) { // from class: kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2
            final Continuation $completion;
            final Function2 $this_createCoroutineUnchecked$inlined;
            final Object $receiver$inlined;
            final Continuation $completion$inlined;

            {
                this.$completion = completion;
                this.$this_createCoroutineUnchecked$inlined = createCoroutineUnchecked;
                this.$receiver$inlined = obj;
                this.$completion$inlined = completion;
            }

            @Override // kotlin.coroutines.experimental.Continuation
            public void resume(Object obj2) {
                resume((Unit) obj2);
            }

            @Override // kotlin.coroutines.experimental.Continuation
            @NotNull
            public CoroutineContext getContext() {
                return this.$completion.getContext();
            }

            public void resume(@NotNull Unit value) {
                Intrinsics.checkParameterIsNotNull(value, "value");
                Continuation continuation = this.$completion;
                try {
                    Function2 function2 = this.$this_createCoroutineUnchecked$inlined;
                    if (function2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
                    }
                    Object objInvoke = ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(this.$receiver$inlined, this.$completion$inlined);
                    if (objInvoke != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        if (continuation == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                        }
                        continuation.resume(objInvoke);
                    }
                } catch (Throwable th) {
                    continuation.resumeWithException(th);
                }
            }

            @Override // kotlin.coroutines.experimental.Continuation
            public void resumeWithException(@NotNull Throwable exception) {
                Intrinsics.checkParameterIsNotNull(exception, "exception");
                this.$completion.resumeWithException(exception);
            }
        });
    }

    @NotNull
    public static final Object getCOROUTINE_SUSPENDED() {
        return kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}

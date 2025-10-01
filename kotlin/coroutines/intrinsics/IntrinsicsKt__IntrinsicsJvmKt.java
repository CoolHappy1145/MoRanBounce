package kotlin.coroutines.intrinsics;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd&\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aF\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b\u00a2\u0006\u0002\b\b\u001aD\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\n\u001a]\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0012\u001aZ\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, m27d2 = {"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, m28xs = "kotlin/coroutines/intrinsics/IntrinsicsKt")
/* loaded from: L-out.jar:kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt.class */
class IntrinsicsKt__IntrinsicsJvmKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object startCoroutineUninterceptedOrReturn(@NotNull Function1 function1, Continuation continuation) {
        if (function1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        }
        return ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(continuation);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object startCoroutineUninterceptedOrReturn(@NotNull Function2 function2, Object obj, Continuation continuation) {
        if (function2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        }
        return ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(obj, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation createCoroutineUnintercepted(@NotNull final Function1 createCoroutineUnintercepted, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(createCoroutineUnintercepted, "$this$createCoroutineUnintercepted");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        final Continuation<?> continuationProbeCoroutineCreated = DebugProbesKt.probeCoroutineCreated(completion);
        if (createCoroutineUnintercepted instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) createCoroutineUnintercepted).create(continuationProbeCoroutineCreated);
        }
        final CoroutineContext context = continuationProbeCoroutineCreated.getContext();
        if (context == EmptyCoroutineContext.INSTANCE) {
            if (continuationProbeCoroutineCreated == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }
            return new RestrictedContinuationImpl(continuationProbeCoroutineCreated, continuationProbeCoroutineCreated, createCoroutineUnintercepted) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1
                private int label;
                final Continuation $completion;
                final Function1 $this_createCoroutineUnintercepted$inlined;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(continuationProbeCoroutineCreated);
                    this.$completion = continuationProbeCoroutineCreated;
                    this.$this_createCoroutineUnintercepted$inlined = createCoroutineUnintercepted;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @Nullable
                protected Object invokeSuspend(@NotNull Object obj) {
                    switch (this.label) {
                        case 0:
                            this.label = 1;
                            ResultKt.throwOnFailure(obj);
                            C0333xa50de660 c0333xa50de660 = this;
                            Function1 function1 = this.$this_createCoroutineUnintercepted$inlined;
                            if (function1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                            }
                            return ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(c0333xa50de660);
                        case 1:
                            this.label = 2;
                            ResultKt.throwOnFailure(obj);
                            return obj;
                        default:
                            throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                }
            };
        }
        if (continuationProbeCoroutineCreated == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        }
        return new ContinuationImpl(continuationProbeCoroutineCreated, context, continuationProbeCoroutineCreated, context, createCoroutineUnintercepted) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2
            private int label;
            final Continuation $completion;
            final CoroutineContext $context;
            final Function1 $this_createCoroutineUnintercepted$inlined;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(continuationProbeCoroutineCreated, context);
                this.$completion = continuationProbeCoroutineCreated;
                this.$context = context;
                this.$this_createCoroutineUnintercepted$inlined = createCoroutineUnintercepted;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            protected Object invokeSuspend(@NotNull Object obj) {
                switch (this.label) {
                    case 0:
                        this.label = 1;
                        ResultKt.throwOnFailure(obj);
                        C0334xa50de661 c0334xa50de661 = this;
                        Function1 function1 = this.$this_createCoroutineUnintercepted$inlined;
                        if (function1 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                        }
                        return ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(c0334xa50de661);
                    case 1:
                        this.label = 2;
                        ResultKt.throwOnFailure(obj);
                        return obj;
                    default:
                        throw new IllegalStateException("This coroutine had already completed".toString());
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation createCoroutineUnintercepted(@NotNull final Function2 createCoroutineUnintercepted, final Object obj, @NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(createCoroutineUnintercepted, "$this$createCoroutineUnintercepted");
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        final Continuation<?> continuationProbeCoroutineCreated = DebugProbesKt.probeCoroutineCreated(completion);
        if (createCoroutineUnintercepted instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) createCoroutineUnintercepted).create(obj, continuationProbeCoroutineCreated);
        }
        final CoroutineContext context = continuationProbeCoroutineCreated.getContext();
        if (context == EmptyCoroutineContext.INSTANCE) {
            if (continuationProbeCoroutineCreated == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }
            return new RestrictedContinuationImpl(continuationProbeCoroutineCreated, continuationProbeCoroutineCreated, createCoroutineUnintercepted, obj) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3
                private int label;
                final Continuation $completion;
                final Function2 $this_createCoroutineUnintercepted$inlined;
                final Object $receiver$inlined;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(continuationProbeCoroutineCreated);
                    this.$completion = continuationProbeCoroutineCreated;
                    this.$this_createCoroutineUnintercepted$inlined = createCoroutineUnintercepted;
                    this.$receiver$inlined = obj;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @Nullable
                protected Object invokeSuspend(@NotNull Object obj2) {
                    switch (this.label) {
                        case 0:
                            this.label = 1;
                            ResultKt.throwOnFailure(obj2);
                            C0335xa50de662 c0335xa50de662 = this;
                            Function2 function2 = this.$this_createCoroutineUnintercepted$inlined;
                            if (function2 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                            }
                            return ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(this.$receiver$inlined, c0335xa50de662);
                        case 1:
                            this.label = 2;
                            ResultKt.throwOnFailure(obj2);
                            return obj2;
                        default:
                            throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                }
            };
        }
        if (continuationProbeCoroutineCreated == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        }
        return new ContinuationImpl(continuationProbeCoroutineCreated, context, continuationProbeCoroutineCreated, context, createCoroutineUnintercepted, obj) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4
            private int label;
            final Continuation $completion;
            final CoroutineContext $context;
            final Function2 $this_createCoroutineUnintercepted$inlined;
            final Object $receiver$inlined;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(continuationProbeCoroutineCreated, context);
                this.$completion = continuationProbeCoroutineCreated;
                this.$context = context;
                this.$this_createCoroutineUnintercepted$inlined = createCoroutineUnintercepted;
                this.$receiver$inlined = obj;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            protected Object invokeSuspend(@NotNull Object obj2) {
                switch (this.label) {
                    case 0:
                        this.label = 1;
                        ResultKt.throwOnFailure(obj2);
                        C0336xa50de663 c0336xa50de663 = this;
                        Function2 function2 = this.$this_createCoroutineUnintercepted$inlined;
                        if (function2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                        }
                        return ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(this.$receiver$inlined, c0336xa50de663);
                    case 1:
                        this.label = 2;
                        ResultKt.throwOnFailure(obj2);
                        return obj2;
                    default:
                        throw new IllegalStateException("This coroutine had already completed".toString());
                }
            }
        };
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation intercepted(@NotNull Continuation intercepted) {
        Intrinsics.checkParameterIsNotNull(intercepted, "$this$intercepted");
        Continuation continuation = intercepted;
        if (!(continuation instanceof ContinuationImpl)) {
            continuation = null;
        }
        ContinuationImpl continuationImpl = (ContinuationImpl) continuation;
        if (continuationImpl != null) {
            Continuation continuationIntercepted = continuationImpl.intercepted();
            if (continuationIntercepted != null) {
                return continuationIntercepted;
            }
        }
        return intercepted;
    }

    @SinceKotlin(version = "1.3")
    private static final Continuation createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(final Continuation continuation, final Function1 function1) {
        final CoroutineContext context = continuation.getContext();
        if (context == EmptyCoroutineContext.INSTANCE) {
            if (continuation == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }
            return new RestrictedContinuationImpl(function1, continuation, continuation) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1
                private int label;
                final Function1 $block;
                final Continuation $completion;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(continuation);
                    this.$block = function1;
                    this.$completion = continuation;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @Nullable
                protected Object invokeSuspend(@NotNull Object obj) {
                    switch (this.label) {
                        case 0:
                            this.label = 1;
                            ResultKt.throwOnFailure(obj);
                            return this.$block.invoke(this);
                        case 1:
                            this.label = 2;
                            ResultKt.throwOnFailure(obj);
                            return obj;
                        default:
                            throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                }
            };
        }
        if (continuation == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        }
        return new ContinuationImpl(function1, continuation, context, continuation, context) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2
            private int label;
            final Function1 $block;
            final Continuation $completion;
            final CoroutineContext $context;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(continuation, context);
                this.$block = function1;
                this.$completion = continuation;
                this.$context = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            protected Object invokeSuspend(@NotNull Object obj) {
                switch (this.label) {
                    case 0:
                        this.label = 1;
                        ResultKt.throwOnFailure(obj);
                        return this.$block.invoke(this);
                    case 1:
                        this.label = 2;
                        ResultKt.throwOnFailure(obj);
                        return obj;
                    default:
                        throw new IllegalStateException("This coroutine had already completed".toString());
                }
            }
        };
    }
}

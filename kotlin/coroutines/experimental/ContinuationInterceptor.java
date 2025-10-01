package kotlin.coroutines.experimental;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\ufffd\ufffd \u00062\u00020\u0001:\u0001\u0006J\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\ufffd\ufffd\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H&\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/coroutines/experimental/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "interceptContinuation", "Lkotlin/coroutines/experimental/Continuation;", "T", "continuation", "Key", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/ContinuationInterceptor.class */
public interface ContinuationInterceptor extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/ContinuationInterceptor$DefaultImpls.class */
    public static final class DefaultImpls {
        @Nullable
        public static CoroutineContext.Element get(ContinuationInterceptor continuationInterceptor, @NotNull CoroutineContext.Key key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            return CoroutineContext.Element.DefaultImpls.get(continuationInterceptor, key);
        }

        public static Object fold(ContinuationInterceptor continuationInterceptor, Object obj, @NotNull Function2 operation) {
            Intrinsics.checkParameterIsNotNull(operation, "operation");
            return CoroutineContext.Element.DefaultImpls.fold(continuationInterceptor, obj, operation);
        }

        @NotNull
        public static CoroutineContext minusKey(ContinuationInterceptor continuationInterceptor, @NotNull CoroutineContext.Key key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            return CoroutineContext.Element.DefaultImpls.minusKey(continuationInterceptor, key);
        }

        @NotNull
        public static CoroutineContext plus(ContinuationInterceptor continuationInterceptor, @NotNull CoroutineContext context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            return CoroutineContext.Element.DefaultImpls.plus(continuationInterceptor, context);
        }
    }

    @NotNull
    Continuation interceptContinuation(@NotNull Continuation continuation);

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, m27d2 = {"Lkotlin/coroutines/experimental/ContinuationInterceptor$Key;", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "()V", "kotlin-stdlib-coroutines"})
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/ContinuationInterceptor$Key.class */
    public static final class Key implements CoroutineContext.Key {
        static final Key $$INSTANCE = new Key();

        private Key() {
        }
    }
}

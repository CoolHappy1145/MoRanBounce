package kotlin.coroutines;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\bg\u0018\ufffd\ufffd \u000f2\u00020\u0001:\u0001\u000fJ(\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\ufffd\ufffd\u0010\u0003*\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0096\u0002\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\t0\b\"\u0004\b\ufffd\ufffd\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\bH&J\u0014\u0010\u000b\u001a\u00020\f2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016J\u0014\u0010\r\u001a\u00020\u000e2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a8\u0006\u0010"}, m27d2 = {"Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/CoroutineContext$Element;", PropertyDescriptor.GET, "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "minusKey", "Lkotlin/coroutines/CoroutineContext;", "releaseInterceptedContinuation", "", "Key", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/coroutines/ContinuationInterceptor.class */
public interface ContinuationInterceptor extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    @NotNull
    Continuation interceptContinuation(@NotNull Continuation continuation);

    void releaseInterceptedContinuation(@NotNull Continuation continuation);

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    @Nullable
    CoroutineContext.Element get(@NotNull CoroutineContext.Key key);

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    @NotNull
    CoroutineContext minusKey(@NotNull CoroutineContext.Key key);

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, m27d2 = {"Lkotlin/coroutines/ContinuationInterceptor$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/coroutines/ContinuationInterceptor$Key.class */
    public static final class Key implements CoroutineContext.Key {
        static final Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/coroutines/ContinuationInterceptor$DefaultImpls.class */
    public static final class DefaultImpls {
        public static Object fold(ContinuationInterceptor continuationInterceptor, Object obj, @NotNull Function2 operation) {
            Intrinsics.checkParameterIsNotNull(operation, "operation");
            return CoroutineContext.Element.DefaultImpls.fold(continuationInterceptor, obj, operation);
        }

        @NotNull
        public static CoroutineContext plus(ContinuationInterceptor continuationInterceptor, @NotNull CoroutineContext context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            return CoroutineContext.Element.DefaultImpls.plus(continuationInterceptor, context);
        }

        public static void releaseInterceptedContinuation(ContinuationInterceptor continuationInterceptor, @NotNull Continuation continuation) {
            Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        }

        @Nullable
        public static CoroutineContext.Element get(ContinuationInterceptor continuationInterceptor, @NotNull CoroutineContext.Key key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            if (key != ContinuationInterceptor.Key) {
                return null;
            }
            if (continuationInterceptor == null) {
                throw new TypeCastException("null cannot be cast to non-null type E");
            }
            return continuationInterceptor;
        }

        @NotNull
        public static CoroutineContext minusKey(ContinuationInterceptor continuationInterceptor, @NotNull CoroutineContext.Key key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            return key == ContinuationInterceptor.Key ? EmptyCoroutineContext.INSTANCE : continuationInterceptor;
        }
    }
}

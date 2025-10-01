package kotlin.coroutines.experimental;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\ufffd\ufffd2\u00020\u0001:\u0002\u0011\u0012J5\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\ufffd\ufffd\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u00032\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002H\u00030\u0006H&\u00a2\u0006\u0002\u0010\bJ(\u0010\t\u001a\u0004\u0018\u0001H\n\"\b\b\ufffd\ufffd\u0010\n*\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\fH\u00a6\u0002\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u00020\ufffd\ufffd2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH&J\u0011\u0010\u000f\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0010\u001a\u00020\ufffd\ufffdH\u0096\u0002\u00a8\u0006\u0013"}, m27d2 = {"Lkotlin/coroutines/experimental/CoroutineContext;", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", PropertyDescriptor.GET, "E", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "minusKey", "plus", "context", "Element", "Key", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/CoroutineContext.class */
public interface CoroutineContext {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0010\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0003\u00a8\u0006\u0004"}, m27d2 = {"Lkotlin/coroutines/experimental/CoroutineContext$Key;", "E", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "", "kotlin-stdlib-coroutines"})
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/CoroutineContext$Key.class */
    public interface Key {
    }

    @Nullable
    Element get(@NotNull Key key);

    Object fold(Object obj, @NotNull Function2 function2);

    @NotNull
    CoroutineContext plus(@NotNull CoroutineContext coroutineContext);

    @NotNull
    CoroutineContext minusKey(@NotNull Key key);

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/CoroutineContext$DefaultImpls.class */
    public static final class DefaultImpls {
        @NotNull
        public static CoroutineContext plus(CoroutineContext coroutineContext, @NotNull CoroutineContext context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            return context == EmptyCoroutineContext.INSTANCE ? coroutineContext : (CoroutineContext) context.fold(coroutineContext, new Function2() { // from class: kotlin.coroutines.experimental.CoroutineContext.plus.1
                @Override // kotlin.jvm.functions.Function2
                public Object invoke(Object obj, Object obj2) {
                    return invoke((CoroutineContext) obj, (Element) obj2);
                }

                @NotNull
                public final CoroutineContext invoke(@NotNull CoroutineContext acc, @NotNull Element element) {
                    CombinedContext combinedContext;
                    Intrinsics.checkParameterIsNotNull(acc, "acc");
                    Intrinsics.checkParameterIsNotNull(element, "element");
                    CoroutineContext coroutineContextMinusKey = acc.minusKey(element.getKey());
                    if (coroutineContextMinusKey == EmptyCoroutineContext.INSTANCE) {
                        return element;
                    }
                    ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) coroutineContextMinusKey.get(ContinuationInterceptor.Key);
                    if (continuationInterceptor == null) {
                        combinedContext = new CombinedContext(coroutineContextMinusKey, element);
                    } else {
                        CoroutineContext coroutineContextMinusKey2 = coroutineContextMinusKey.minusKey(ContinuationInterceptor.Key);
                        combinedContext = coroutineContextMinusKey2 == EmptyCoroutineContext.INSTANCE ? new CombinedContext(element, continuationInterceptor) : new CombinedContext(new CombinedContext(coroutineContextMinusKey2, element), continuationInterceptor);
                    }
                    return combinedContext;
                }
            });
        }
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\ufffd\ufffd2\u00020\u0001J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\ufffd\ufffd\u0010\u00072\u0006\u0010\b\u001a\u0002H\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\ufffd\ufffd\u0012\u0004\u0012\u0002H\u00070\nH\u0016\u00a2\u0006\u0002\u0010\u000bJ(\u0010\f\u001a\u0004\u0018\u0001H\r\"\b\b\ufffd\ufffd\u0010\r*\u00020\ufffd\ufffd2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\r0\u0003H\u0096\u0002\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0010"}, m27d2 = {"Lkotlin/coroutines/experimental/CoroutineContext$Element;", "Lkotlin/coroutines/experimental/CoroutineContext;", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/experimental/CoroutineContext$Key;", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", PropertyDescriptor.GET, "E", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "minusKey", "kotlin-stdlib-coroutines"})
    /* loaded from: L-out.jar:kotlin/coroutines/experimental/CoroutineContext$Element.class */
    public interface Element extends CoroutineContext {
        @NotNull
        Key getKey();

        @Override // kotlin.coroutines.experimental.CoroutineContext
        @Nullable
        Element get(@NotNull Key key);

        @Override // kotlin.coroutines.experimental.CoroutineContext
        Object fold(Object obj, @NotNull Function2 function2);

        @Override // kotlin.coroutines.experimental.CoroutineContext
        @NotNull
        CoroutineContext minusKey(@NotNull Key key);

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
        /* loaded from: L-out.jar:kotlin/coroutines/experimental/CoroutineContext$Element$DefaultImpls.class */
        public static final class DefaultImpls {
            @NotNull
            public static CoroutineContext plus(Element element, @NotNull CoroutineContext context) {
                Intrinsics.checkParameterIsNotNull(context, "context");
                return DefaultImpls.plus(element, context);
            }

            @Nullable
            public static Element get(Element element, @NotNull Key key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                if (element.getKey() != key) {
                    return null;
                }
                if (element == null) {
                    throw new TypeCastException("null cannot be cast to non-null type E");
                }
                return element;
            }

            public static Object fold(Element element, Object obj, @NotNull Function2 operation) {
                Intrinsics.checkParameterIsNotNull(operation, "operation");
                return operation.invoke(obj, element);
            }

            @NotNull
            public static CoroutineContext minusKey(Element element, @NotNull Key key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                return element.getKey() == key ? EmptyCoroutineContext.INSTANCE : element;
            }
        }
    }
}

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
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\b'\u0018\ufffd\ufffd2\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0002\u0010\u0004R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/coroutines/experimental/AbstractCoroutineContextElement;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/experimental/CoroutineContext$Key;", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/AbstractCoroutineContextElement.class */
public abstract class AbstractCoroutineContextElement implements CoroutineContext.Element {

    @NotNull
    private final CoroutineContext.Key key;

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element
    @NotNull
    public CoroutineContext.Key getKey() {
        return this.key;
    }

    public AbstractCoroutineContextElement(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.key = key;
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element, kotlin.coroutines.experimental.CoroutineContext
    @Nullable
    public CoroutineContext.Element get(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return CoroutineContext.Element.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element, kotlin.coroutines.experimental.CoroutineContext
    public Object fold(Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        return CoroutineContext.Element.DefaultImpls.fold(this, obj, operation);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext.Element, kotlin.coroutines.experimental.CoroutineContext
    @NotNull
    public CoroutineContext minusKey(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return CoroutineContext.Element.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return CoroutineContext.Element.DefaultImpls.plus(this, context);
    }
}

package kotlin.coroutines.experimental;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\ufffd\ufffdH\u0002J\u0013\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\ufffd\ufffd\u0010\u00122\u0006\u0010\u0013\u001a\u0002H\u00122\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00120\u0015H\u0016\u00a2\u0006\u0002\u0010\u0016J(\u0010\u0017\u001a\u0004\u0018\u0001H\u0018\"\b\b\ufffd\ufffd\u0010\u0018*\u00020\u00042\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0096\u0002\u00a2\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0014\u0010\u001e\u001a\u00020\u00012\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0016J\b\u0010\u001f\u001a\u00020\u001dH\u0002J\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\u00a8\u0006\""}, m27d2 = {"Lkotlin/coroutines/experimental/CombinedContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "left", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "(Lkotlin/coroutines/experimental/CoroutineContext;Lkotlin/coroutines/experimental/CoroutineContext$Element;)V", "getElement", "()Lkotlin/coroutines/experimental/CoroutineContext$Element;", "getLeft", "()Lkotlin/coroutines/experimental/CoroutineContext;", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", PropertyDescriptor.GET, "E", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/CombinedContext.class */
public final class CombinedContext implements CoroutineContext {

    @NotNull
    private final CoroutineContext left;

    @NotNull
    private final CoroutineContext.Element element;

    @NotNull
    public final CoroutineContext getLeft() {
        return this.left;
    }

    @NotNull
    public final CoroutineContext.Element getElement() {
        return this.element;
    }

    public CombinedContext(@NotNull CoroutineContext left, @NotNull CoroutineContext.Element element) {
        Intrinsics.checkParameterIsNotNull(left, "left");
        Intrinsics.checkParameterIsNotNull(element, "element");
        this.left = left;
        this.element = element;
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return CoroutineContext.DefaultImpls.plus(this, context);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext
    @Nullable
    public CoroutineContext.Element get(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        CoroutineContext coroutineContext = this;
        while (true) {
            CombinedContext combinedContext = (CombinedContext) coroutineContext;
            CoroutineContext.Element element = combinedContext.element.get(key);
            if (element != null) {
                return element;
            }
            CoroutineContext coroutineContext2 = combinedContext.left;
            if (coroutineContext2 instanceof CombinedContext) {
                coroutineContext = coroutineContext2;
            } else {
                return coroutineContext2.get(key);
            }
        }
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext
    public Object fold(Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        return operation.invoke(this.left.fold(obj, operation), this.element);
    }

    @Override // kotlin.coroutines.experimental.CoroutineContext
    @NotNull
    public CoroutineContext minusKey(@NotNull CoroutineContext.Key key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        if (this.element.get(key) != null) {
            return this.left;
        }
        CoroutineContext coroutineContextMinusKey = this.left.minusKey(key);
        return coroutineContextMinusKey == this.left ? this : coroutineContextMinusKey == EmptyCoroutineContext.INSTANCE ? this.element : new CombinedContext(coroutineContextMinusKey, this.element);
    }

    private final int size() {
        if (this.left instanceof CombinedContext) {
            return ((CombinedContext) this.left).size() + 1;
        }
        return 2;
    }

    private final boolean contains(CoroutineContext.Element element) {
        return Intrinsics.areEqual(get(element.getKey()), element);
    }

    private final boolean containsAll(CombinedContext combinedContext) {
        CombinedContext combinedContext2 = combinedContext;
        while (true) {
            CombinedContext combinedContext3 = combinedContext2;
            if (!contains(combinedContext3.element)) {
                return false;
            }
            CoroutineContext coroutineContext = combinedContext3.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                if (coroutineContext == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.CoroutineContext.Element");
                }
                return contains((CoroutineContext.Element) coroutineContext);
            }
            combinedContext2 = (CombinedContext) coroutineContext;
        }
    }

    public boolean equals(@Nullable Object obj) {
        return this == obj || ((obj instanceof CombinedContext) && ((CombinedContext) obj).size() == size() && ((CombinedContext) obj).containsAll(this));
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    @NotNull
    public String toString() {
        return "[" + ((String) fold("", new Function2() { // from class: kotlin.coroutines.experimental.CombinedContext.toString.1
            @Override // kotlin.jvm.functions.Function2
            public Object invoke(Object obj, Object obj2) {
                return invoke((String) obj, (CoroutineContext.Element) obj2);
            }

            @NotNull
            public final String invoke(@NotNull String acc, @NotNull CoroutineContext.Element element) {
                Intrinsics.checkParameterIsNotNull(acc, "acc");
                Intrinsics.checkParameterIsNotNull(element, "element");
                return acc.length() == 0 ? element.toString() : acc + ", " + element;
            }
        })) + "]";
    }
}

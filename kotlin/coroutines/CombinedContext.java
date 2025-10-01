package kotlin.coroutines;

import java.io.Serializable;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdL\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\ufffd\ufffd2\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\ufffd\ufffdH\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016\u00a2\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\ufffd\ufffd\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0096\u0002\u00a2\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\""}, m27d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", PropertyDescriptor.GET, "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/coroutines/CombinedContext.class */
public final class CombinedContext implements CoroutineContext, Serializable {
    private final CoroutineContext left;
    private final CoroutineContext.Element element;

    public CombinedContext(@NotNull CoroutineContext left, @NotNull CoroutineContext.Element element) {
        Intrinsics.checkParameterIsNotNull(left, "left");
        Intrinsics.checkParameterIsNotNull(element, "element");
        this.left = left;
        this.element = element;
    }

    @Override // kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return CoroutineContext.DefaultImpls.plus(this, context);
    }

    @Override // kotlin.coroutines.CoroutineContext
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

    @Override // kotlin.coroutines.CoroutineContext
    public Object fold(Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        return operation.invoke(this.left.fold(obj, operation), this.element);
    }

    @Override // kotlin.coroutines.CoroutineContext
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
        CombinedContext combinedContext = this;
        int i = 2;
        while (true) {
            CoroutineContext coroutineContext = combinedContext.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                coroutineContext = null;
            }
            CombinedContext combinedContext2 = (CombinedContext) coroutineContext;
            if (combinedContext2 == null) {
                return i;
            }
            combinedContext = combinedContext2;
            i++;
        }
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
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
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
        return "[" + ((String) fold("", new Function2() { // from class: kotlin.coroutines.CombinedContext.toString.1
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

    private final Object writeReplace() {
        int size = size();
        CoroutineContext[] coroutineContextArr = new CoroutineContext[size];
        Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 0;
        fold(Unit.INSTANCE, new Function2(coroutineContextArr, intRef) { // from class: kotlin.coroutines.CombinedContext.writeReplace.1
            final CoroutineContext[] $elements;
            final Ref.IntRef $index;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                this.$elements = coroutineContextArr;
                this.$index = intRef;
            }

            @Override // kotlin.jvm.functions.Function2
            public Object invoke(Object obj, Object obj2) {
                invoke((Unit) obj, (CoroutineContext.Element) obj2);
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull Unit unit, @NotNull CoroutineContext.Element element) {
                Intrinsics.checkParameterIsNotNull(unit, "<anonymous parameter 0>");
                Intrinsics.checkParameterIsNotNull(element, "element");
                CoroutineContext[] coroutineContextArr2 = this.$elements;
                Ref.IntRef intRef2 = this.$index;
                int i = intRef2.element;
                intRef2.element = i + 1;
                coroutineContextArr2[i] = element;
            }
        });
        if (intRef.element == size) {
            return new Serialized(coroutineContextArr);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\b\u0002\u0018\ufffd\ufffd \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, m27d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/coroutines/CombinedContext$Serialized.class */
    private static final class Serialized implements Serializable {

        @NotNull
        private final CoroutineContext[] elements;
        private static final long serialVersionUID = 0;
        public static final Companion Companion = new Companion(null);

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/coroutines/CombinedContext$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"})
        /* loaded from: L-out.jar:kotlin/coroutines/CombinedContext$Serialized$Companion.class */
        public static final class Companion {
            private Companion() {
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        @NotNull
        public final CoroutineContext[] getElements() {
            return this.elements;
        }

        public Serialized(@NotNull CoroutineContext[] elements) {
            Intrinsics.checkParameterIsNotNull(elements, "elements");
            this.elements = elements;
        }

        private final Object readResolve() {
            CoroutineContext[] coroutineContextArr = this.elements;
            Object objPlus = EmptyCoroutineContext.INSTANCE;
            for (CoroutineContext coroutineContext : coroutineContextArr) {
                objPlus = ((CoroutineContext) objPlus).plus(coroutineContext);
            }
            return objPlus;
        }
    }
}

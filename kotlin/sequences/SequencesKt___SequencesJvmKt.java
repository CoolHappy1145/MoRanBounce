package kotlin.sequences;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd0\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a(\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001aA\u0010\u0005\u001a\u0002H\u0006\"\u0010\b\ufffd\ufffd\u0010\u0006*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0007\"\u0004\b\u0001\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\b\u001a\u0002H\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u00a2\u0006\u0002\u0010\t\u001a&\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u000e\b\ufffd\ufffd\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\r*\b\u0012\u0004\u0012\u0002H\f0\u0001\u001a8\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u0004\b\ufffd\ufffd\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00012\u001a\u0010\u000e\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\f0\u000fj\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\f`\u0010\u00a8\u0006\u0011"}, m27d2 = {"filterIsInstance", "Lkotlin/sequences/Sequence;", "R", "klass", Constants.CLASS_DESC, "filterIsInstanceTo", "C", "", "destination", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "toSortedSet", "Ljava/util/SortedSet;", "T", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "kotlin-stdlib"}, m28xs = "kotlin/sequences/SequencesKt")
/* loaded from: L-out.jar:kotlin/sequences/SequencesKt___SequencesJvmKt.class */
class SequencesKt___SequencesJvmKt extends SequencesKt__SequencesKt {
    @NotNull
    public static final Sequence filterIsInstance(@NotNull Sequence filterIsInstance, @NotNull Class klass) {
        Intrinsics.checkParameterIsNotNull(filterIsInstance, "$this$filterIsInstance");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        Sequence sequenceFilter = SequencesKt.filter(filterIsInstance, new Function1(klass) { // from class: kotlin.sequences.SequencesKt___SequencesJvmKt.filterIsInstance.1
            final Class $klass;

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return Boolean.valueOf(m1441invoke(obj));
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$klass = klass;
            }

            /* renamed from: invoke, reason: collision with other method in class */
            public final boolean m1441invoke(@Nullable Object obj) {
                return this.$klass.isInstance(obj);
            }
        });
        if (sequenceFilter == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.sequences.Sequence<R>");
        }
        return sequenceFilter;
    }

    @NotNull
    public static final Collection filterIsInstanceTo(@NotNull Sequence filterIsInstanceTo, @NotNull Collection destination, @NotNull Class klass) {
        Intrinsics.checkParameterIsNotNull(filterIsInstanceTo, "$this$filterIsInstanceTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        for (Object obj : filterIsInstanceTo) {
            if (klass.isInstance(obj)) {
                destination.add(obj);
            }
        }
        return destination;
    }

    @NotNull
    public static final SortedSet toSortedSet(@NotNull Sequence toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) SequencesKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet toSortedSet(@NotNull Sequence toSortedSet, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return (SortedSet) SequencesKt.toCollection(toSortedSet, new TreeSet(comparator));
    }
}

package kotlin.comparisons;

import java.util.Comparator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd<\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a;\u0010\ufffd\ufffd\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aY\u0010\ufffd\ufffd\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005\u00a2\u0006\u0002\u0010\t\u001aW\u0010\ufffd\ufffd\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a;\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aW\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a-\u0010\r\u001a\u00020\u000e\"\f\b\ufffd\ufffd\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u000f\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0010\u001a\u0004\u0018\u0001H\u0002\u00a2\u0006\u0002\u0010\u0011\u001a>\u0010\u0012\u001a\u00020\u000e\"\u0004\b\ufffd\ufffd\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u00a2\u0006\u0002\u0010\u0013\u001aY\u0010\u0012\u001a\u00020\u000e\"\u0004\b\ufffd\ufffd\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005\u00a2\u0006\u0002\u0010\u0014\u001aZ\u0010\u0012\u001a\u00020\u000e\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\n2\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n`\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u00a2\u0006\u0002\u0010\u0015\u001aG\u0010\u0016\u001a\u00020\u000e\"\u0004\b\ufffd\ufffd\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022 \u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\bH\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0014\u001a&\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a-\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\u0003\u001a-\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\u0003\u001a&\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a0\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\u001aO\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\u0003H\u0086\u0004\u001aO\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001aO\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001am\u0010!\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u000328\b\u0004\u0010\"\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000e0#H\u0087\b\u001aO\u0010&\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002`\u0003H\u0086\u0004\u00a8\u0006'"}, m27d2 = {"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"}, m28xs = "kotlin/comparisons/ComparisonsKt")
/* loaded from: L-out.jar:kotlin/comparisons/ComparisonsKt__ComparisonsKt.class */
class ComparisonsKt__ComparisonsKt {
    public static final int compareValuesBy(Object obj, Object obj2, @NotNull Function1[] selectors) {
        Intrinsics.checkParameterIsNotNull(selectors, "selectors");
        if (selectors.length > 0) {
            return compareValuesByImpl$ComparisonsKt__ComparisonsKt(obj, obj2, selectors);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    private static final int compareValuesByImpl$ComparisonsKt__ComparisonsKt(Object obj, Object obj2, Function1[] function1Arr) {
        for (Function1 function1 : function1Arr) {
            int iCompareValues = ComparisonsKt.compareValues((Comparable) function1.invoke(obj), (Comparable) function1.invoke(obj2));
            if (iCompareValues != 0) {
                return iCompareValues;
            }
        }
        return 0;
    }

    @InlineOnly
    private static final int compareValuesBy(Object obj, Object obj2, Function1 function1) {
        return ComparisonsKt.compareValues((Comparable) function1.invoke(obj), (Comparable) function1.invoke(obj2));
    }

    @InlineOnly
    private static final int compareValuesBy(Object obj, Object obj2, Comparator comparator, Function1 function1) {
        return comparator.compare(function1.invoke(obj), function1.invoke(obj2));
    }

    public static final int compareValues(@Nullable Comparable comparable, @Nullable Comparable comparable2) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return -1;
        }
        if (comparable2 == null) {
            return 1;
        }
        return comparable.compareTo(comparable2);
    }

    @NotNull
    public static final Comparator compareBy(@NotNull Function1[] selectors) {
        Intrinsics.checkParameterIsNotNull(selectors, "selectors");
        if (selectors.length > 0) {
            return new Comparator(selectors) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.compareBy.1
                final Function1[] $selectors;

                {
                    this.$selectors = selectors;
                }

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValuesByImpl$ComparisonsKt__ComparisonsKt(obj, obj2, this.$selectors);
                }
            };
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\n\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n\u00a2\u0006\u0004\b\u0006\u0010\u0007"}, m27d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"})
    /* renamed from: kotlin.comparisons.ComparisonsKt__ComparisonsKt$compareBy$2 */
    /* loaded from: L-out.jar:kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2.class */
    public static final class C03092 implements Comparator {
        final Function1 $selector;

        public C03092(Function1 function1) {
            this.$selector = function1;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt.compareValues((Comparable) this.$selector.invoke(obj), (Comparable) this.$selector.invoke(obj2));
        }
    }

    @InlineOnly
    private static final Comparator compareBy(Function1 function1) {
        return new C03092(function1);
    }

    @InlineOnly
    private static final Comparator compareBy(Comparator comparator, Function1 function1) {
        return new Comparator(comparator, function1) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.compareBy.3
            final Comparator $comparator;
            final Function1 $selector;

            {
                this.$comparator = comparator;
                this.$selector = function1;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.$comparator.compare(this.$selector.invoke(obj), this.$selector.invoke(obj2));
            }
        };
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\n\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n\u00a2\u0006\u0004\b\u0006\u0010\u0007"}, m27d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"})
    /* renamed from: kotlin.comparisons.ComparisonsKt__ComparisonsKt$compareByDescending$1 */
    /* loaded from: L-out.jar:kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1.class */
    public static final class C03111 implements Comparator {
        final Function1 $selector;

        public C03111(Function1 function1) {
            this.$selector = function1;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt.compareValues((Comparable) this.$selector.invoke(obj2), (Comparable) this.$selector.invoke(obj));
        }
    }

    @InlineOnly
    private static final Comparator compareByDescending(Function1 function1) {
        return new C03111(function1);
    }

    @InlineOnly
    private static final Comparator compareByDescending(Comparator comparator, Function1 function1) {
        return new Comparator(comparator, function1) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.compareByDescending.2
            final Comparator $comparator;
            final Function1 $selector;

            {
                this.$comparator = comparator;
                this.$selector = function1;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.$comparator.compare(this.$selector.invoke(obj2), this.$selector.invoke(obj));
            }
        };
    }

    @InlineOnly
    private static final Comparator thenBy(@NotNull Comparator comparator, Function1 function1) {
        return new Comparator(comparator, function1) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.thenBy.1
            final Comparator $this_thenBy;
            final Function1 $selector;

            {
                this.$this_thenBy = comparator;
                this.$selector = function1;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_thenBy.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues((Comparable) this.$selector.invoke(obj), (Comparable) this.$selector.invoke(obj2));
            }
        };
    }

    @InlineOnly
    private static final Comparator thenBy(@NotNull Comparator comparator, Comparator comparator2, Function1 function1) {
        return new Comparator(comparator, comparator2, function1) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.thenBy.2
            final Comparator $this_thenBy;
            final Comparator $comparator;
            final Function1 $selector;

            {
                this.$this_thenBy = comparator;
                this.$comparator = comparator2;
                this.$selector = function1;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_thenBy.compare(obj, obj2);
                return iCompare != 0 ? iCompare : this.$comparator.compare(this.$selector.invoke(obj), this.$selector.invoke(obj2));
            }
        };
    }

    @InlineOnly
    private static final Comparator thenByDescending(@NotNull Comparator comparator, Function1 function1) {
        return new Comparator(comparator, function1) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.thenByDescending.1
            final Comparator $this_thenByDescending;
            final Function1 $selector;

            {
                this.$this_thenByDescending = comparator;
                this.$selector = function1;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_thenByDescending.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues((Comparable) this.$selector.invoke(obj2), (Comparable) this.$selector.invoke(obj));
            }
        };
    }

    @InlineOnly
    private static final Comparator thenByDescending(@NotNull Comparator comparator, Comparator comparator2, Function1 function1) {
        return new Comparator(comparator, comparator2, function1) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.thenByDescending.2
            final Comparator $this_thenByDescending;
            final Comparator $comparator;
            final Function1 $selector;

            {
                this.$this_thenByDescending = comparator;
                this.$comparator = comparator2;
                this.$selector = function1;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_thenByDescending.compare(obj, obj2);
                return iCompare != 0 ? iCompare : this.$comparator.compare(this.$selector.invoke(obj2), this.$selector.invoke(obj));
            }
        };
    }

    @InlineOnly
    private static final Comparator thenComparator(@NotNull Comparator comparator, Function2 function2) {
        return new Comparator(comparator, function2) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.thenComparator.1
            final Comparator $this_thenComparator;
            final Function2 $comparison;

            {
                this.$this_thenComparator = comparator;
                this.$comparison = function2;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_thenComparator.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ((Number) this.$comparison.invoke(obj, obj2)).intValue();
            }
        };
    }

    @NotNull
    public static final Comparator then(@NotNull Comparator then, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(then, "$this$then");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return new Comparator(then, comparator) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.then.1
            final Comparator $this_then;
            final Comparator $comparator;

            {
                this.$this_then = then;
                this.$comparator = comparator;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_then.compare(obj, obj2);
                return iCompare != 0 ? iCompare : this.$comparator.compare(obj, obj2);
            }
        };
    }

    @NotNull
    public static final Comparator thenDescending(@NotNull Comparator thenDescending, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(thenDescending, "$this$thenDescending");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return new Comparator(thenDescending, comparator) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.thenDescending.1
            final Comparator $this_thenDescending;
            final Comparator $comparator;

            {
                this.$this_thenDescending = thenDescending;
                this.$comparator = comparator;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = this.$this_thenDescending.compare(obj, obj2);
                return iCompare != 0 ? iCompare : this.$comparator.compare(obj2, obj);
            }
        };
    }

    @NotNull
    public static final Comparator nullsFirst(@NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return new Comparator(comparator) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.nullsFirst.1
            final Comparator $comparator;

            {
                this.$comparator = comparator;
            }

            @Override // java.util.Comparator
            public final int compare(@Nullable Object obj, @Nullable Object obj2) {
                if (obj == obj2) {
                    return 0;
                }
                if (obj == null) {
                    return -1;
                }
                if (obj2 == null) {
                    return 1;
                }
                return this.$comparator.compare(obj, obj2);
            }
        };
    }

    @InlineOnly
    private static final Comparator nullsFirst() {
        return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
    }

    @NotNull
    public static final Comparator nullsLast(@NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return new Comparator(comparator) { // from class: kotlin.comparisons.ComparisonsKt__ComparisonsKt.nullsLast.1
            final Comparator $comparator;

            {
                this.$comparator = comparator;
            }

            @Override // java.util.Comparator
            public final int compare(@Nullable Object obj, @Nullable Object obj2) {
                if (obj == obj2) {
                    return 0;
                }
                if (obj == null) {
                    return 1;
                }
                if (obj2 == null) {
                    return -1;
                }
                return this.$comparator.compare(obj, obj2);
            }
        };
    }

    @InlineOnly
    private static final Comparator nullsLast() {
        return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
    }

    @NotNull
    public static final Comparator naturalOrder() {
        NaturalOrderComparator naturalOrderComparator = NaturalOrderComparator.INSTANCE;
        if (naturalOrderComparator == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
        }
        return naturalOrderComparator;
    }

    @NotNull
    public static final Comparator reverseOrder() {
        ReverseOrderComparator reverseOrderComparator = ReverseOrderComparator.INSTANCE;
        if (reverseOrderComparator == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
        }
        return reverseOrderComparator;
    }

    @NotNull
    public static final Comparator reversed(@NotNull Comparator reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        if (reversed instanceof ReversedComparator) {
            return ((ReversedComparator) reversed).getComparator();
        }
        if (Intrinsics.areEqual(reversed, NaturalOrderComparator.INSTANCE)) {
            ReverseOrderComparator reverseOrderComparator = ReverseOrderComparator.INSTANCE;
            if (reverseOrderComparator == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
            }
            return reverseOrderComparator;
        }
        if (!Intrinsics.areEqual(reversed, ReverseOrderComparator.INSTANCE)) {
            return new ReversedComparator(reversed);
        }
        NaturalOrderComparator naturalOrderComparator = NaturalOrderComparator.INSTANCE;
        if (naturalOrderComparator == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
        }
        return naturalOrderComparator;
    }
}

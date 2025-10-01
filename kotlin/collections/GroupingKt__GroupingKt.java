package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd@\n\ufffd\ufffd\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009b\u0001\u0010\ufffd\ufffd\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u001a\u00b4\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u00a2\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007\u00a2\u0006\u0002\u0010\u0016\u001a\u00bc\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u001a|\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b\u00a2\u0006\u0002\u0010\u001c\u001a\u00d5\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u00a2\u0006\u0002\u0010\u001e\u001a\u0090\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b\u00a2\u0006\u0002\u0010\u001f\u001a\u0088\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\ufffd\ufffd\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u001a\u00a1\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u00a2\u0006\u0002\u0010#\u00a8\u0006$"}, m27d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"}, m28xs = "kotlin/collections/GroupingKt")
/* loaded from: L-out.jar:kotlin/collections/GroupingKt__GroupingKt.class */
class GroupingKt__GroupingKt extends GroupingKt__GroupingJVMKt {
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map aggregate(@NotNull Grouping aggregate, @NotNull Function4 operation) {
        Intrinsics.checkParameterIsNotNull(aggregate, "$this$aggregate");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator itSourceIterator = aggregate.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = aggregate.keyOf(next);
            Object obj = linkedHashMap.get(objKeyOf);
            linkedHashMap.put(objKeyOf, operation.invoke(objKeyOf, obj, next, Boolean.valueOf(obj == null && !linkedHashMap.containsKey(objKeyOf))));
        }
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map aggregateTo(@NotNull Grouping aggregateTo, @NotNull Map destination, @NotNull Function4 operation) {
        Intrinsics.checkParameterIsNotNull(aggregateTo, "$this$aggregateTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Iterator itSourceIterator = aggregateTo.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = aggregateTo.keyOf(next);
            Object obj = destination.get(objKeyOf);
            destination.put(objKeyOf, operation.invoke(objKeyOf, obj, next, Boolean.valueOf(obj == null && !destination.containsKey(objKeyOf))));
        }
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map fold(@NotNull Grouping fold, @NotNull Function2 initialValueSelector, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(fold, "$this$fold");
        Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator itSourceIterator = fold.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = fold.keyOf(next);
            Object obj = linkedHashMap.get(objKeyOf);
            linkedHashMap.put(objKeyOf, operation.invoke(objKeyOf, obj == null && !linkedHashMap.containsKey(objKeyOf) ? initialValueSelector.invoke(objKeyOf, next) : obj, next));
        }
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map foldTo(@NotNull Grouping foldTo, @NotNull Map destination, @NotNull Function2 initialValueSelector, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(foldTo, "$this$foldTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Iterator itSourceIterator = foldTo.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = foldTo.keyOf(next);
            Object obj = destination.get(objKeyOf);
            destination.put(objKeyOf, operation.invoke(objKeyOf, obj == null && !destination.containsKey(objKeyOf) ? initialValueSelector.invoke(objKeyOf, next) : obj, next));
        }
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map fold(@NotNull Grouping fold, Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(fold, "$this$fold");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator itSourceIterator = fold.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = fold.keyOf(next);
            Object obj2 = linkedHashMap.get(objKeyOf);
            linkedHashMap.put(objKeyOf, operation.invoke(obj2 == null && !linkedHashMap.containsKey(objKeyOf) ? obj : obj2, next));
        }
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map foldTo(@NotNull Grouping foldTo, @NotNull Map destination, Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(foldTo, "$this$foldTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Iterator itSourceIterator = foldTo.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = foldTo.keyOf(next);
            Object obj2 = destination.get(objKeyOf);
            destination.put(objKeyOf, operation.invoke(obj2 == null && !destination.containsKey(objKeyOf) ? obj : obj2, next));
        }
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map reduce(@NotNull Grouping reduce, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(reduce, "$this$reduce");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator itSourceIterator = reduce.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = reduce.keyOf(next);
            Object obj = linkedHashMap.get(objKeyOf);
            linkedHashMap.put(objKeyOf, obj == null && !linkedHashMap.containsKey(objKeyOf) ? next : operation.invoke(objKeyOf, obj, next));
        }
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map reduceTo(@NotNull Grouping reduceTo, @NotNull Map destination, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(reduceTo, "$this$reduceTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Iterator itSourceIterator = reduceTo.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            Object objKeyOf = reduceTo.keyOf(next);
            Object obj = destination.get(objKeyOf);
            destination.put(objKeyOf, obj == null && !destination.containsKey(objKeyOf) ? next : operation.invoke(objKeyOf, obj, next));
        }
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map eachCountTo(@NotNull Grouping eachCountTo, @NotNull Map destination) {
        Intrinsics.checkParameterIsNotNull(eachCountTo, "$this$eachCountTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Iterator itSourceIterator = eachCountTo.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object objKeyOf = eachCountTo.keyOf(itSourceIterator.next());
            Object obj = destination.get(objKeyOf);
            destination.put(objKeyOf, Integer.valueOf(((Number) (obj == null && !destination.containsKey(objKeyOf) ? 0 : obj)).intValue() + 1));
        }
        return destination;
    }
}

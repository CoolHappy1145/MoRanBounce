package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.internal.HidesMembers;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdh\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0010\u001c\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\ufffd\ufffd\u001aG\u0010\ufffd\ufffd\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a$\u0010\b\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010\b\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a9\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\n\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001a6\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\f\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001a'\u0010\r\u001a\u00020\u000e\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001aG\u0010\r\u001a\u00020\u000e\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aY\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b\u001ar\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b\u00a2\u0006\u0002\u0010\u0017\u001aG\u0010\u0018\u001a\u00020\u0019\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b\u001aS\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001aY\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b\u001ar\u0010\u001e\u001a\u0002H\u0014\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b\u00a2\u0006\u0002\u0010\u0017\u001al\u0010\u001f\u001a\u0002H\u0014\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u00a2\u0006\u0002\u0010\u0017\u001ae\u0010 \u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0087\b\u001ai\u0010#\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\ufffd\ufffd\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\ufffd\ufffd\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&H\u0087\b\u001ae\u0010'\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001af\u0010(\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\ufffd\ufffd\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\ufffd\ufffd\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&\u001a$\u0010)\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010)\u001a\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aV\u0010*\u001a\u0002H+\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010+*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002H+2\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b\u00a2\u0006\u0002\u0010,\u001a6\u0010-\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030.0\u0010\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u00a8\u0006/"}, m27d2 = {"all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "flatMap", "", "R", "transform", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "forEach", "", "action", "map", "mapNotNull", "", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "minBy", "minWith", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib"}, m28xs = "kotlin/collections/MapsKt")
/* loaded from: L-out.jar:kotlin/collections/MapsKt___MapsKt.class */
class MapsKt___MapsKt extends MapsKt__MapsKt {
    @NotNull
    public static final List toList(@NotNull Map toList) {
        Intrinsics.checkParameterIsNotNull(toList, "$this$toList");
        if (toList.size() == 0) {
            return CollectionsKt.emptyList();
        }
        Iterator it = toList.entrySet().iterator();
        if (!it.hasNext()) {
            return CollectionsKt.emptyList();
        }
        Map.Entry entry = (Map.Entry) it.next();
        if (!it.hasNext()) {
            return CollectionsKt.listOf(new Pair(entry.getKey(), entry.getValue()));
        }
        ArrayList arrayList = new ArrayList(toList.size());
        arrayList.add(new Pair(entry.getKey(), entry.getValue()));
        do {
            Map.Entry entry2 = (Map.Entry) it.next();
            arrayList.add(new Pair(entry2.getKey(), entry2.getValue()));
        } while (it.hasNext());
        return arrayList;
    }

    @NotNull
    public static final List flatMap(@NotNull Map flatMap, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(flatMap, "$this$flatMap");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList();
        Iterator it = flatMap.entrySet().iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, (Iterable) transform.invoke((Map.Entry) it.next()));
        }
        return arrayList;
    }

    @NotNull
    public static final Collection flatMapTo(@NotNull Map flatMapTo, @NotNull Collection destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(flatMapTo, "$this$flatMapTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Iterator it = flatMapTo.entrySet().iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(destination, (Iterable) transform.invoke((Map.Entry) it.next()));
        }
        return destination;
    }

    @NotNull
    public static final List map(@NotNull Map map, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(map, "$this$map");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList(map.size());
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(transform.invoke((Map.Entry) it.next()));
        }
        return arrayList;
    }

    @NotNull
    public static final List mapNotNull(@NotNull Map mapNotNull, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapNotNull, "$this$mapNotNull");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList();
        Iterator it = mapNotNull.entrySet().iterator();
        while (it.hasNext()) {
            Object objInvoke = transform.invoke((Map.Entry) it.next());
            if (objInvoke != null) {
                arrayList.add(objInvoke);
            }
        }
        return arrayList;
    }

    @NotNull
    public static final Collection mapNotNullTo(@NotNull Map mapNotNullTo, @NotNull Collection destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapNotNullTo, "$this$mapNotNullTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Iterator it = mapNotNullTo.entrySet().iterator();
        while (it.hasNext()) {
            Object objInvoke = transform.invoke((Map.Entry) it.next());
            if (objInvoke != null) {
                destination.add(objInvoke);
            }
        }
        return destination;
    }

    @NotNull
    public static final Collection mapTo(@NotNull Map mapTo, @NotNull Collection destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapTo, "$this$mapTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Iterator it = mapTo.entrySet().iterator();
        while (it.hasNext()) {
            destination.add(transform.invoke((Map.Entry) it.next()));
        }
        return destination;
    }

    public static final boolean all(@NotNull Map all, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(all, "$this$all");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        if (all.isEmpty()) {
            return true;
        }
        Iterator it = all.entrySet().iterator();
        while (it.hasNext()) {
            if (!((Boolean) predicate.invoke((Map.Entry) it.next())).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final boolean any(@NotNull Map any) {
        Intrinsics.checkParameterIsNotNull(any, "$this$any");
        return !any.isEmpty();
    }

    public static final boolean any(@NotNull Map any, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(any, "$this$any");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        if (any.isEmpty()) {
            return false;
        }
        Iterator it = any.entrySet().iterator();
        while (it.hasNext()) {
            if (((Boolean) predicate.invoke((Map.Entry) it.next())).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @InlineOnly
    private static final int count(@NotNull Map map) {
        return map.size();
    }

    public static final int count(@NotNull Map count, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(count, "$this$count");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        if (count.isEmpty()) {
            return 0;
        }
        int i = 0;
        Iterator it = count.entrySet().iterator();
        while (it.hasNext()) {
            if (((Boolean) predicate.invoke((Map.Entry) it.next())).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    @HidesMembers
    public static final void forEach(@NotNull Map forEach, @NotNull Function1 action) {
        Intrinsics.checkParameterIsNotNull(forEach, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        Iterator it = forEach.entrySet().iterator();
        while (it.hasNext()) {
            action.invoke((Map.Entry) it.next());
        }
    }

    @InlineOnly
    private static final Map.Entry maxBy(@NotNull Map map, Function1 function1) {
        Object obj;
        Iterator it = map.entrySet().iterator();
        if (it.hasNext()) {
            Object next = it.next();
            if (it.hasNext()) {
                Comparable comparable = (Comparable) function1.invoke(next);
                do {
                    Object next2 = it.next();
                    Comparable comparable2 = (Comparable) function1.invoke(next2);
                    if (comparable.compareTo(comparable2) < 0) {
                        next = next2;
                        comparable = comparable2;
                    }
                } while (it.hasNext());
                obj = next;
            } else {
                obj = next;
            }
        } else {
            obj = null;
        }
        return (Map.Entry) obj;
    }

    @InlineOnly
    private static final Map.Entry maxWith(@NotNull Map map, Comparator comparator) {
        return (Map.Entry) CollectionsKt.maxWith(map.entrySet(), comparator);
    }

    @Nullable
    public static final Map.Entry minBy(@NotNull Map minBy, @NotNull Function1 selector) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(minBy, "$this$minBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        Iterator it = minBy.entrySet().iterator();
        if (it.hasNext()) {
            Object next = it.next();
            if (it.hasNext()) {
                Comparable comparable = (Comparable) selector.invoke(next);
                do {
                    Object next2 = it.next();
                    Comparable comparable2 = (Comparable) selector.invoke(next2);
                    if (comparable.compareTo(comparable2) > 0) {
                        next = next2;
                        comparable = comparable2;
                    }
                } while (it.hasNext());
                obj = next;
            } else {
                obj = next;
            }
        } else {
            obj = null;
        }
        return (Map.Entry) obj;
    }

    @Nullable
    public static final Map.Entry minWith(@NotNull Map minWith, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(minWith, "$this$minWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return (Map.Entry) CollectionsKt.minWith(minWith.entrySet(), comparator);
    }

    public static final boolean none(@NotNull Map none) {
        Intrinsics.checkParameterIsNotNull(none, "$this$none");
        return none.isEmpty();
    }

    public static final boolean none(@NotNull Map none, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(none, "$this$none");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        if (none.isEmpty()) {
            return true;
        }
        Iterator it = none.entrySet().iterator();
        while (it.hasNext()) {
            if (((Boolean) predicate.invoke((Map.Entry) it.next())).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map onEach(@NotNull Map onEach, @NotNull Function1 action) {
        Intrinsics.checkParameterIsNotNull(onEach, "$this$onEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        Iterator it = onEach.entrySet().iterator();
        while (it.hasNext()) {
            action.invoke((Map.Entry) it.next());
        }
        return onEach;
    }

    @InlineOnly
    private static final Iterable asIterable(@NotNull Map map) {
        return map.entrySet();
    }

    @NotNull
    public static final Sequence asSequence(@NotNull Map asSequence) {
        Intrinsics.checkParameterIsNotNull(asSequence, "$this$asSequence");
        return CollectionsKt.asSequence(asSequence.entrySet());
    }
}

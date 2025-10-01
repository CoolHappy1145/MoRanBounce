package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdD\n\ufffd\ufffd\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a2\u0010\ufffd\ufffd\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005\u001aY\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u00032*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00050\n\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005\u00a2\u0006\u0002\u0010\u000b\u001a@\u0010\f\u001a\u0002H\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\r2\u0006\u0010\u000e\u001a\u0002H\u00022\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0010H\u0086\b\u00a2\u0006\u0002\u0010\u0011\u001a\u0019\u0010\u0012\u001a\u00020\u0013*\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u0001H\u0087\b\u001a2\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\ufffd\ufffd\u001a1\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0081\b\u001a:\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u000e\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\u001a@\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0010\u0018\u001a\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u0019\u00a8\u0006\u001a"}, m27d2 = {"mapOf", "", "K", "V", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "", "pairs", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "comparator", "Ljava/util/Comparator;", "kotlin-stdlib"}, m28xs = "kotlin/collections/MapsKt")
/* loaded from: L-out.jar:kotlin/collections/MapsKt__MapsJVMKt.class */
class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
    @NotNull
    public static final Map mapOf(@NotNull Pair pair) {
        Intrinsics.checkParameterIsNotNull(pair, "pair");
        Map mapSingletonMap = Collections.singletonMap(pair.getFirst(), pair.getSecond());
        Intrinsics.checkExpressionValueIsNotNull(mapSingletonMap, "java.util.Collections.si\u2026(pair.first, pair.second)");
        return mapSingletonMap;
    }

    public static final Object getOrPut(@NotNull ConcurrentMap getOrPut, Object obj, @NotNull Function0 defaultValue) {
        Intrinsics.checkParameterIsNotNull(getOrPut, "$this$getOrPut");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        Object obj2 = getOrPut.get(obj);
        if (obj2 != null) {
            return obj2;
        }
        Object objInvoke = defaultValue.invoke();
        Object objPutIfAbsent = getOrPut.putIfAbsent(obj, objInvoke);
        return objPutIfAbsent != null ? objPutIfAbsent : objInvoke;
    }

    @NotNull
    public static final SortedMap toSortedMap(@NotNull Map toSortedMap) {
        Intrinsics.checkParameterIsNotNull(toSortedMap, "$this$toSortedMap");
        return new TreeMap(toSortedMap);
    }

    @NotNull
    public static final SortedMap toSortedMap(@NotNull Map toSortedMap, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(toSortedMap, "$this$toSortedMap");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        TreeMap treeMap = new TreeMap(comparator);
        treeMap.putAll(toSortedMap);
        return treeMap;
    }

    @NotNull
    public static final SortedMap sortedMapOf(@NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        TreeMap treeMap = new TreeMap();
        MapsKt.putAll(treeMap, pairs);
        return treeMap;
    }

    @InlineOnly
    private static final Properties toProperties(@NotNull Map map) {
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    @InlineOnly
    private static final Map toSingletonMapOrSelf(@NotNull Map map) {
        return MapsKt.toSingletonMap(map);
    }

    @NotNull
    public static final Map toSingletonMap(@NotNull Map toSingletonMap) {
        Intrinsics.checkParameterIsNotNull(toSingletonMap, "$this$toSingletonMap");
        Map.Entry entry = (Map.Entry) toSingletonMap.entrySet().iterator().next();
        Map mapSingletonMap = Collections.singletonMap(entry.getKey(), entry.getValue());
        Intrinsics.checkExpressionValueIsNotNull(mapSingletonMap, "java.util.Collections.singletonMap(key, value)");
        Intrinsics.checkExpressionValueIsNotNull(mapSingletonMap, "with(entries.iterator().\u2026ingletonMap(key, value) }");
        return mapSingletonMap;
    }
}

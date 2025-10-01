package kotlin.collections;

import java.util.Map;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010%\n\u0002\b\u0002\u001a3\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001aQ\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u0003\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032!\u0010\b\u001a\u001d\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u0002H\u00010\t\u001aX\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\f\"\u0004\b\ufffd\ufffd\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\f2!\u0010\b\u001a\u001d\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u0002H\u00010\tH\u0007\u00a2\u0006\u0002\b\r\u00a8\u0006\u000e"}, m27d2 = {"getOrImplicitDefault", "V", "K", "", "key", "getOrImplicitDefaultNullable", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "withDefault", "defaultValue", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "withDefaultMutable", "kotlin-stdlib"}, m28xs = "kotlin/collections/MapsKt")
/* loaded from: L-out.jar:kotlin/collections/MapsKt__MapWithDefaultKt.class */
class MapsKt__MapWithDefaultKt {
    @PublishedApi
    @JvmName(name = "getOrImplicitDefaultNullable")
    public static final Object getOrImplicitDefaultNullable(@NotNull Map getOrImplicitDefault, Object obj) {
        Intrinsics.checkParameterIsNotNull(getOrImplicitDefault, "$this$getOrImplicitDefault");
        if (getOrImplicitDefault instanceof MapWithDefault) {
            return ((MapWithDefault) getOrImplicitDefault).getOrImplicitDefault(obj);
        }
        Object obj2 = getOrImplicitDefault.get(obj);
        if (obj2 == null && !getOrImplicitDefault.containsKey(obj)) {
            throw new NoSuchElementException("Key " + obj + " is missing in the map.");
        }
        return obj2;
    }

    @NotNull
    public static final Map withDefault(@NotNull Map withDefault, @NotNull Function1 defaultValue) {
        Intrinsics.checkParameterIsNotNull(withDefault, "$this$withDefault");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        return withDefault instanceof MapWithDefault ? MapsKt.withDefault(((MapWithDefault) withDefault).getMap(), defaultValue) : new MapWithDefaultImpl(withDefault, defaultValue);
    }

    @JvmName(name = "withDefaultMutable")
    @NotNull
    public static final Map withDefaultMutable(@NotNull Map withDefault, @NotNull Function1 defaultValue) {
        Intrinsics.checkParameterIsNotNull(withDefault, "$this$withDefault");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        return withDefault instanceof MutableMapWithDefault ? MapsKt.withDefaultMutable(((MutableMapWithDefault) withDefault).getMap(), defaultValue) : new MutableMapWithDefaultImpl(withDefault, defaultValue);
    }
}

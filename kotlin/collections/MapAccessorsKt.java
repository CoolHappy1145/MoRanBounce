package kotlin.collections;

import java.util.Map;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd.\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u001aK\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\b\b\u0001\u0010\u0001*\u0002H\u0002*\u0015\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0004\u0012\t\u0012\u0007H\u0002\u00a2\u0006\u0002\b\u00050\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0087\n\u00a2\u0006\u0002\u0010\n\u001a@\u0010\ufffd\ufffd\u001a\u0002H\u0002\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u000b2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0087\b\u00a2\u0006\u0004\b\f\u0010\n\u001aO\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u0002\"\b\b\u0001\u0010\u0001*\u0002H\u0002*\u0017\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0004\u0012\u000b\b\u0001\u0012\u0007H\u0002\u00a2\u0006\u0002\b\u00050\u000b2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0087\n\u00a2\u0006\u0004\b\r\u0010\n\u001aF\u0010\u000e\u001a\u00020\u000f\"\u0004\b\ufffd\ufffd\u0010\u0002*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00020\u000b2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0010\u001a\u0002H\u0002H\u0087\n\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, m27d2 = {"getValue", "V1", "V", "", "", "Lkotlin/internal/Exact;", "thisRef", "", "property", "Lkotlin/reflect/KProperty;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "", "getVarContravariant", "getVar", "setValue", "", PropertyDescriptor.VALUE, "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"})
@JvmName(name = "MapAccessorsKt")
/* loaded from: L-out.jar:kotlin/collections/MapAccessorsKt.class */
public final class MapAccessorsKt {
    @InlineOnly
    private static final Object getValue(@NotNull Map getValue, Object obj, KProperty kProperty) {
        Intrinsics.checkParameterIsNotNull(getValue, "$this$getValue");
        return MapsKt.getOrImplicitDefaultNullable(getValue, kProperty.getName());
    }

    @JvmName(name = "getVar")
    @InlineOnly
    private static final Object getVar(@NotNull Map getValue, Object obj, KProperty kProperty) {
        Intrinsics.checkParameterIsNotNull(getValue, "$this$getValue");
        return MapsKt.getOrImplicitDefaultNullable(getValue, kProperty.getName());
    }

    @JvmName(name = "getVarContravariant")
    @InlineOnly
    @Deprecated(message = "Use getValue() with two type parameters instead", level = DeprecationLevel.ERROR)
    @LowPriorityInOverloadResolution
    private static final Object getVarContravariant(@NotNull Map map, Object obj, KProperty kProperty) {
        return MapsKt.getOrImplicitDefaultNullable(map, kProperty.getName());
    }

    @InlineOnly
    private static final void setValue(@NotNull Map setValue, Object obj, KProperty kProperty, Object obj2) {
        Intrinsics.checkParameterIsNotNull(setValue, "$this$setValue");
        setValue.put(kProperty.getName(), obj2);
    }
}

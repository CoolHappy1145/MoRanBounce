package kotlin.properties;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \ufffd\ufffd*\u0006\b\u0001\u0010\u0002 \u00012\u00020\u0003J\"\u0010\u0004\u001a\u00028\u00012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u00a6\u0002\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lkotlin/properties/ReadOnlyProperty;", "R", "T", "", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/properties/ReadOnlyProperty.class */
public interface ReadOnlyProperty {
    Object getValue(Object obj, @NotNull KProperty kProperty);
}

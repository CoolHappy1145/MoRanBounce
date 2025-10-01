package kotlin.properties;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \ufffd\ufffd*\u0004\b\u0001\u0010\u00022\u00020\u0003J\"\u0010\u0004\u001a\u00028\u00012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u00a6\u0002\u00a2\u0006\u0002\u0010\bJ*\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\u000b\u001a\u00028\u0001H\u00a6\u0002\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, m27d2 = {"Lkotlin/properties/ReadWriteProperty;", "R", "T", "", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", PropertyDescriptor.VALUE, "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/properties/ReadWriteProperty.class */
public interface ReadWriteProperty {
    Object getValue(Object obj, @NotNull KProperty kProperty);

    void setValue(Object obj, @NotNull KProperty kProperty, Object obj2);
}

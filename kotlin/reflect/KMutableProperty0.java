package kotlin.reflect;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KMutableProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\fJ\u0015\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\ufffd\ufffdH&\u00a2\u0006\u0002\u0010\u000bR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\r"}, m27d2 = {"Lkotlin/reflect/KMutableProperty0;", "R", "Lkotlin/reflect/KProperty0;", "Lkotlin/reflect/KMutableProperty;", "setter", "Lkotlin/reflect/KMutableProperty0$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty0$Setter;", PropertyDescriptor.SET, "", PropertyDescriptor.VALUE, "(Ljava/lang/Object;)V", "Setter", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/reflect/KMutableProperty0.class */
public interface KMutableProperty0 extends KProperty0, KMutableProperty {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00040\u0003\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/reflect/KMutableProperty0$Setter;", "R", "Lkotlin/reflect/KMutableProperty$Setter;", "Lkotlin/Function1;", "", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/reflect/KMutableProperty0$Setter.class */
    public interface Setter extends KMutableProperty.Setter, Function1 {
    }

    void set(Object obj);

    @NotNull
    Setter getSetter();
}

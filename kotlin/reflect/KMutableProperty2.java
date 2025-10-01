package kotlin.reflect;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.reflect.KMutableProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\bf\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\b\u0012\u0004\u0012\u0002H\u00030\u0005:\u0001\u0010J%\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\ufffd\ufffd2\u0006\u0010\r\u001a\u00028\u00012\u0006\u0010\u000e\u001a\u00028\u0002H&\u00a2\u0006\u0002\u0010\u000fR$\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0011"}, m27d2 = {"Lkotlin/reflect/KMutableProperty2;", "D", "E", "R", "Lkotlin/reflect/KProperty2;", "Lkotlin/reflect/KMutableProperty;", "setter", "Lkotlin/reflect/KMutableProperty2$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty2$Setter;", PropertyDescriptor.SET, "", "receiver1", "receiver2", PropertyDescriptor.VALUE, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "Setter", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/reflect/KMutableProperty2.class */
public interface KMutableProperty2 extends KProperty2, KMutableProperty {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u0002*\u0004\b\u0005\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00060\u0005\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/reflect/KMutableProperty2$Setter;", "D", "E", "R", "Lkotlin/reflect/KMutableProperty$Setter;", "Lkotlin/Function3;", "", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/reflect/KMutableProperty2$Setter.class */
    public interface Setter extends KMutableProperty.Setter, Function3 {
    }

    void set(Object obj, Object obj2, Object obj3);

    @Override // kotlin.reflect.KMutableProperty, kotlin.reflect.KMutableProperty0
    @NotNull
    Setter getSetter();
}

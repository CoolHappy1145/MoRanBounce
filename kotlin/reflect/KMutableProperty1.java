package kotlin.reflect;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KMutableProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\u000eJ\u001d\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\ufffd\ufffd2\u0006\u0010\f\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\rR\u001e\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0006X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/reflect/KMutableProperty1;", "T", "R", "Lkotlin/reflect/KProperty1;", "Lkotlin/reflect/KMutableProperty;", "setter", "Lkotlin/reflect/KMutableProperty1$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty1$Setter;", PropertyDescriptor.SET, "", "receiver", PropertyDescriptor.VALUE, "(Ljava/lang/Object;Ljava/lang/Object;)V", "Setter", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/reflect/KMutableProperty1.class */
public interface KMutableProperty1 extends KProperty1, KMutableProperty {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00050\u0004\u00a8\u0006\u0006"}, m27d2 = {"Lkotlin/reflect/KMutableProperty1$Setter;", "T", "R", "Lkotlin/reflect/KMutableProperty$Setter;", "Lkotlin/Function2;", "", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/reflect/KMutableProperty1$Setter.class */
    public interface Setter extends KMutableProperty.Setter, Function2 {
    }

    void set(Object obj, Object obj2);

    @Override // kotlin.reflect.KMutableProperty, kotlin.reflect.KMutableProperty0
    @NotNull
    Setter getSetter();
}

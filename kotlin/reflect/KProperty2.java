package kotlin.reflect;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\bf\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0006\b\u0002\u0010\u0003 \u00012\b\u0012\u0004\u0012\u0002H\u00030\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005:\u0001\u0010J\u001d\u0010\n\u001a\u00028\u00022\u0006\u0010\u000b\u001a\u00028\ufffd\ufffd2\u0006\u0010\f\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00028\ufffd\ufffd2\u0006\u0010\f\u001a\u00028\u0001H'\u00a2\u0006\u0002\u0010\rR$\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0011"}, m27d2 = {"Lkotlin/reflect/KProperty2;", "D", "E", "R", "Lkotlin/reflect/KProperty;", "Lkotlin/Function2;", "getter", "Lkotlin/reflect/KProperty2$Getter;", "getGetter", "()Lkotlin/reflect/KProperty2$Getter;", PropertyDescriptor.GET, "receiver1", "receiver2", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getDelegate", "", "Getter", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/reflect/KProperty2.class */
public interface KProperty2 extends KProperty, Function2 {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0004\b\u0003\u0010\u0001*\u0004\b\u0004\u0010\u0002*\u0006\b\u0005\u0010\u0003 \u00012\b\u0012\u0004\u0012\u0002H\u00030\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005\u00a8\u0006\u0006"}, m27d2 = {"Lkotlin/reflect/KProperty2$Getter;", "D", "E", "R", "Lkotlin/reflect/KProperty$Getter;", "Lkotlin/Function2;", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/reflect/KProperty2$Getter.class */
    public interface Getter extends KProperty.Getter, Function2 {
    }

    Object get(Object obj, Object obj2);

    @SinceKotlin(version = "1.1")
    @Nullable
    Object getDelegate(Object obj, Object obj2);

    @Override // kotlin.reflect.KProperty, kotlin.reflect.KProperty0
    @NotNull
    Getter getGetter();
}

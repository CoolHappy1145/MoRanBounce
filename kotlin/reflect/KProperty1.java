package kotlin.reflect;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\bf\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001\u000eJ\u0015\u0010\t\u001a\u00028\u00012\u0006\u0010\n\u001a\u00028\ufffd\ufffdH&\u00a2\u0006\u0002\u0010\u000bJ\u0017\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\n\u001a\u00028\ufffd\ufffdH'\u00a2\u0006\u0002\u0010\u000bR\u001e\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0006X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/reflect/KProperty1;", "T", "R", "Lkotlin/reflect/KProperty;", "Lkotlin/Function1;", "getter", "Lkotlin/reflect/KProperty1$Getter;", "getGetter", "()Lkotlin/reflect/KProperty1$Getter;", PropertyDescriptor.GET, "receiver", "(Ljava/lang/Object;)Ljava/lang/Object;", "getDelegate", "", "Getter", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/reflect/KProperty1.class */
public interface KProperty1 extends KProperty, Function1 {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0004\b\u0002\u0010\u0001*\u0006\b\u0003\u0010\u0002 \u00012\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/reflect/KProperty1$Getter;", "T", "R", "Lkotlin/reflect/KProperty$Getter;", "Lkotlin/Function1;", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/reflect/KProperty1$Getter.class */
    public interface Getter extends KProperty.Getter, Function1 {
    }

    Object get(Object obj);

    @SinceKotlin(version = "1.1")
    @Nullable
    Object getDelegate(Object obj);

    @Override // kotlin.reflect.KProperty, kotlin.reflect.KProperty0
    @NotNull
    Getter getGetter();
}

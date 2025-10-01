package kotlin.properties;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\b&\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J)\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\f\u001a\u00028\ufffd\ufffd2\u0006\u0010\r\u001a\u00028\ufffd\ufffdH\u0014\u00a2\u0006\u0002\u0010\u000eJ)\u0010\u000f\u001a\u00020\u00102\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\f\u001a\u00028\ufffd\ufffd2\u0006\u0010\r\u001a\u00028\ufffd\ufffdH\u0014\u00a2\u0006\u0002\u0010\u0011J$\u0010\u0012\u001a\u00028\ufffd\ufffd2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0096\u0002\u00a2\u0006\u0002\u0010\u0014J,\u0010\u0015\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\u0006\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010\u0016R\u0010\u0010\u0006\u001a\u00028\ufffd\ufffdX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0007\u00a8\u0006\u0017"}, m27d2 = {"Lkotlin/properties/ObservableProperty;", "T", "Lkotlin/properties/ReadWriteProperty;", "", "initialValue", "(Ljava/lang/Object;)V", PropertyDescriptor.VALUE, Constants.OBJECT_DESC, "afterChange", "", "property", "Lkotlin/reflect/KProperty;", "oldValue", "newValue", "(Lkotlin/reflect/KProperty;Ljava/lang/Object;Ljava/lang/Object;)V", "beforeChange", "", "(Lkotlin/reflect/KProperty;Ljava/lang/Object;Ljava/lang/Object;)Z", "getValue", "thisRef", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/properties/ObservableProperty.class */
public abstract class ObservableProperty implements ReadWriteProperty {
    private Object value;

    public ObservableProperty(Object obj) {
        this.value = obj;
    }

    protected boolean beforeChange(@NotNull KProperty property, Object obj, Object obj2) {
        Intrinsics.checkParameterIsNotNull(property, "property");
        return true;
    }

    protected void afterChange(@NotNull KProperty property, Object obj, Object obj2) {
        Intrinsics.checkParameterIsNotNull(property, "property");
    }

    @Override // kotlin.properties.ReadWriteProperty
    public Object getValue(@Nullable Object obj, @NotNull KProperty property) {
        Intrinsics.checkParameterIsNotNull(property, "property");
        return this.value;
    }

    @Override // kotlin.properties.ReadWriteProperty
    public void setValue(@Nullable Object obj, @NotNull KProperty property, Object obj2) {
        Intrinsics.checkParameterIsNotNull(property, "property");
        Object obj3 = this.value;
        if (!beforeChange(property, obj3, obj2)) {
            return;
        }
        this.value = obj2;
        afterChange(property, obj3, obj2);
    }
}

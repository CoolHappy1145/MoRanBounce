package kotlin.properties;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\ufffd\ufffd\u0010\u0005*\u00020\u0001J}\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\ufffd\ufffd\u0010\u00052\u0006\u0010\u0007\u001a\u0002H\u00052Q\b\u0004\u0010\b\u001aK\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0013\u0012\u0011H\u0005\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u0011H\u0005\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00100\tH\u0086\b\u00a2\u0006\u0002\u0010\u0011J}\u0010\u0012\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\ufffd\ufffd\u0010\u00052\u0006\u0010\u0007\u001a\u0002H\u00052Q\b\u0004\u0010\b\u001aK\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0013\u0012\u0011H\u0005\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u0011H\u0005\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00130\tH\u0086\b\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0014"}, m27d2 = {"Lkotlin/properties/Delegates;", "", "()V", "notNull", "Lkotlin/properties/ReadWriteProperty;", "T", "observable", "initialValue", "onChange", "Lkotlin/Function3;", "Lkotlin/reflect/KProperty;", "Lkotlin/ParameterName;", "name", "property", "oldValue", "newValue", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Lkotlin/properties/ReadWriteProperty;", "vetoable", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/properties/Delegates.class */
public final class Delegates {
    public static final Delegates INSTANCE = new Delegates();

    private Delegates() {
    }

    @NotNull
    public final ReadWriteProperty notNull() {
        return new NotNullVar();
    }

    @NotNull
    public final ReadWriteProperty observable(Object obj, @NotNull Function3 onChange) {
        Intrinsics.checkParameterIsNotNull(onChange, "onChange");
        return new ObservableProperty(onChange, obj, obj) { // from class: kotlin.properties.Delegates.observable.1
            final Function3 $onChange;
            final Object $initialValue;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(obj);
                this.$onChange = onChange;
                this.$initialValue = obj;
            }

            @Override // kotlin.properties.ObservableProperty
            protected void afterChange(@NotNull KProperty property, Object obj2, Object obj3) {
                Intrinsics.checkParameterIsNotNull(property, "property");
                this.$onChange.invoke(property, obj2, obj3);
            }
        };
    }

    @NotNull
    public final ReadWriteProperty vetoable(Object obj, @NotNull Function3 onChange) {
        Intrinsics.checkParameterIsNotNull(onChange, "onChange");
        return new ObservableProperty(onChange, obj, obj) { // from class: kotlin.properties.Delegates.vetoable.1
            final Function3 $onChange;
            final Object $initialValue;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(obj);
                this.$onChange = onChange;
                this.$initialValue = obj;
            }

            @Override // kotlin.properties.ObservableProperty
            protected boolean beforeChange(@NotNull KProperty property, Object obj2, Object obj3) {
                Intrinsics.checkParameterIsNotNull(property, "property");
                return ((Boolean) this.$onChange.invoke(property, obj2, obj3)).booleanValue();
            }
        };
    }
}

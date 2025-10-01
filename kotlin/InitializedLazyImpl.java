package kotlin;

import java.io.Serializable;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\ufffd\ufffd\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0016\u0010\u0005\u001a\u00028\ufffd\ufffdX\u0096\u0004\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000e"}, m27d2 = {"Lkotlin/InitializedLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", PropertyDescriptor.VALUE, "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", Constants.OBJECT_DESC, "isInitialized", "", "toString", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/InitializedLazyImpl.class */
public final class InitializedLazyImpl implements Lazy, Serializable {
    private final Object value;

    @Override // kotlin.Lazy
    public Object getValue() {
        return this.value;
    }

    public InitializedLazyImpl(Object obj) {
        this.value = obj;
    }

    @NotNull
    public String toString() {
        return String.valueOf(getValue());
    }
}

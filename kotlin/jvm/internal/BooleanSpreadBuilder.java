package kotlin.jvm.internal;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0018\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0002J\f\u0010\f\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lkotlin/jvm/internal/BooleanSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", PropertyDescriptor.VALUE, "", "toArray", "getSize", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/jvm/internal/BooleanSpreadBuilder.class */
public final class BooleanSpreadBuilder extends PrimitiveSpreadBuilder {
    private final boolean[] values;

    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    public int getSize(Object obj) {
        return getSize((boolean[]) obj);
    }

    public BooleanSpreadBuilder(int i) {
        super(i);
        this.values = new boolean[i];
    }

    protected int getSize(@NotNull boolean[] getSize) {
        Intrinsics.checkParameterIsNotNull(getSize, "$this$getSize");
        return getSize.length;
    }

    public final void add(boolean z) {
        boolean[] zArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        zArr[position] = z;
    }

    @NotNull
    public final boolean[] toArray() {
        return (boolean[]) toArray(this.values, new boolean[size()]);
    }
}

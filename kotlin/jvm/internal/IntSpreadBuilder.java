package kotlin.jvm.internal;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u0006\u0010\n\u001a\u00020\u0002J\f\u0010\u000b\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\f"}, m27d2 = {"Lkotlin/jvm/internal/IntSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", PropertyDescriptor.VALUE, "toArray", "getSize", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/jvm/internal/IntSpreadBuilder.class */
public final class IntSpreadBuilder extends PrimitiveSpreadBuilder {
    private final int[] values;

    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    public int getSize(Object obj) {
        return getSize((int[]) obj);
    }

    public IntSpreadBuilder(int i) {
        super(i);
        this.values = new int[i];
    }

    protected int getSize(@NotNull int[] getSize) {
        Intrinsics.checkParameterIsNotNull(getSize, "$this$getSize");
        return getSize.length;
    }

    public final void add(int i) {
        int[] iArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        iArr[position] = i;
    }

    @NotNull
    public final int[] toArray() {
        return (int[]) toArray(this.values, new int[size()]);
    }
}

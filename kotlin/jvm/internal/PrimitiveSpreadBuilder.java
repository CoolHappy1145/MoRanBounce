package kotlin.jvm.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\b&\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0012J\b\u0010\u0003\u001a\u00020\u0004H\u0004J\u001d\u0010\u0013\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0014\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0015\u001a\u00028\ufffd\ufffdH\u0004\u00a2\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0004*\u00028\ufffd\ufffdH$\u00a2\u0006\u0002\u0010\u0018R\u001a\u0010\u0006\u001a\u00020\u0004X\u0084\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\ufffd\ufffd0\u000bX\u0082\u0004\u00a2\u0006\n\n\u0002\u0010\u000e\u0012\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, m27d2 = {"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "T", "", "size", "", "(I)V", "position", "getPosition", "()I", "setPosition", "spreads", "", "spreads$annotations", "()V", "[Ljava/lang/Object;", "addSpread", "", "spreadArgument", "(Ljava/lang/Object;)V", "toArray", "values", "result", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getSize", "(Ljava/lang/Object;)I", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/jvm/internal/PrimitiveSpreadBuilder.class */
public abstract class PrimitiveSpreadBuilder {
    private int position;
    private final Object[] spreads;
    private final int size;

    protected abstract int getSize(@NotNull Object obj);

    public PrimitiveSpreadBuilder(int i) {
        this.size = i;
        this.spreads = new Object[this.size];
    }

    protected final int getPosition() {
        return this.position;
    }

    protected final void setPosition(int i) {
        this.position = i;
    }

    public final void addSpread(@NotNull Object spreadArgument) {
        Intrinsics.checkParameterIsNotNull(spreadArgument, "spreadArgument");
        Object[] objArr = this.spreads;
        int i = this.position;
        this.position = i + 1;
        objArr[i] = spreadArgument;
    }

    protected final int size() {
        int size = 0;
        int i = 0;
        int i2 = this.size - 1;
        if (0 <= i2) {
            while (true) {
                int i3 = size;
                Object obj = this.spreads[i];
                size = i3 + (obj != null ? getSize(obj) : 1);
                if (i == i2) {
                    break;
                }
                i++;
            }
        }
        return size;
    }

    @NotNull
    protected final Object toArray(@NotNull Object values, @NotNull Object result) {
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(result, "result");
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = this.size - 1;
        if (0 <= i4) {
            while (true) {
                Object obj = this.spreads[i3];
                if (obj != null) {
                    if (i2 < i3) {
                        System.arraycopy(values, i2, result, i, i3 - i2);
                        i += i3 - i2;
                    }
                    int size = getSize(obj);
                    System.arraycopy(obj, 0, result, i, size);
                    i += size;
                    i2 = i3 + 1;
                }
                if (i3 == i4) {
                    break;
                }
                i3++;
            }
        }
        if (i2 < this.size) {
            System.arraycopy(values, i2, result, i, this.size - i2);
        }
        return result;
    }
}

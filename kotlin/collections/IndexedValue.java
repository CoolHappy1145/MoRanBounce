package kotlin.collections;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\b\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0004H\u00c6\u0003J\u000e\u0010\r\u001a\u00028\ufffd\ufffdH\u00c6\u0003\u00a2\u0006\u0002\u0010\nJ(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\ufffd\ufffd2\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00028\ufffd\ufffdH\u00c6\u0001\u00a2\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0004H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, m27d2 = {"Lkotlin/collections/IndexedValue;", "T", "", "index", "", PropertyDescriptor.VALUE, "(ILjava/lang/Object;)V", "getIndex", "()I", "getValue", "()Ljava/lang/Object;", Constants.OBJECT_DESC, "component1", "component2", "copy", "(ILjava/lang/Object;)Lkotlin/collections/IndexedValue;", "equals", "", "other", "hashCode", "toString", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/IndexedValue.class */
public final class IndexedValue {
    private final int index;
    private final Object value;

    public final int component1() {
        return this.index;
    }

    public final Object component2() {
        return this.value;
    }

    @NotNull
    public final IndexedValue copy(int i, Object obj) {
        return new IndexedValue(i, obj);
    }

    public static IndexedValue copy$default(IndexedValue indexedValue, int i, Object obj, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            i = indexedValue.index;
        }
        if ((i2 & 2) != 0) {
            obj = indexedValue.value;
        }
        return indexedValue.copy(i, obj);
    }

    @NotNull
    public String toString() {
        return "IndexedValue(index=" + this.index + ", value=" + this.value + ")";
    }

    public int hashCode() {
        int i = this.index * 31;
        Object obj = this.value;
        return i + (obj != null ? obj.hashCode() : 0);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IndexedValue)) {
            return false;
        }
        IndexedValue indexedValue = (IndexedValue) obj;
        return (this.index == indexedValue.index) && Intrinsics.areEqual(this.value, indexedValue.value);
    }

    public final int getIndex() {
        return this.index;
    }

    public final Object getValue() {
        return this.value;
    }

    public IndexedValue(int i, Object obj) {
        this.index = i;
        this.value = obj;
    }
}

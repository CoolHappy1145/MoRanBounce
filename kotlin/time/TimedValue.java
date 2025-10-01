package kotlin.time;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0087\b\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u00020\u0002B\u0018\u0012\u0006\u0010\u0003\u001a\u00028\ufffd\ufffd\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00028\ufffd\ufffdH\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\bJ-\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\ufffd\ufffd2\b\b\u0002\u0010\u0003\u001a\u00028\ufffd\ufffd2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u0005\u00f8\u0001\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, m27d2 = {"Lkotlin/time/TimedValue;", "T", "", PropertyDescriptor.VALUE, "duration", "Lkotlin/time/Duration;", "(Ljava/lang/Object;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getDuration", "()D", "D", "getValue", "()Ljava/lang/Object;", Constants.OBJECT_DESC, "component1", "component2", "copy", "copy-RFiDyg4", "(Ljava/lang/Object;D)Lkotlin/time/TimedValue;", "equals", "", "other", "hashCode", "", "toString", "", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/TimedValue.class */
public final class TimedValue {
    private final Object value;
    private final double duration;

    public final Object component1() {
        return this.value;
    }

    public final double component2() {
        return this.duration;
    }

    @NotNull
    /* renamed from: copy-RFiDyg4, reason: not valid java name */
    public final TimedValue m1530copyRFiDyg4(Object obj, double d) {
        return new TimedValue(obj, d);
    }

    /* renamed from: copy-RFiDyg4$default, reason: not valid java name */
    public static TimedValue m1531copyRFiDyg4$default(TimedValue timedValue, Object obj, double d, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = timedValue.value;
        }
        if ((i & 2) != 0) {
            d = timedValue.duration;
        }
        return timedValue.m1530copyRFiDyg4(obj, d);
    }

    @NotNull
    public String toString() {
        return "TimedValue(value=" + this.value + ", duration=" + Duration.m1514toStringimpl(this.duration) + ")";
    }

    public int hashCode() {
        Object obj = this.value;
        int iHashCode = (obj != null ? obj.hashCode() : 0) * 31;
        return iHashCode + ((int) (iHashCode ^ (Double.doubleToLongBits(this.duration) >>> 32)));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimedValue)) {
            return false;
        }
        TimedValue timedValue = (TimedValue) obj;
        return Intrinsics.areEqual(this.value, timedValue.value) && Double.compare(this.duration, timedValue.duration) == 0;
    }

    public final Object getValue() {
        return this.value;
    }

    public final double getDuration() {
        return this.duration;
    }

    private TimedValue(Object obj, double d) {
        this.value = obj;
        this.duration = d;
    }

    public TimedValue(Object obj, double d, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, d);
    }
}

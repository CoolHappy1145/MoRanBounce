package kotlin.system;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\b\u001a\u0017\u0010\u0005\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\b\u00a8\u0006\u0006"}, m27d2 = {"measureNanoTime", "", "block", "Lkotlin/Function0;", "", "measureTimeMillis", "kotlin-stdlib"})
@JvmName(name = "TimingKt")
/* loaded from: L-out.jar:kotlin/system/TimingKt.class */
public final class TimingKt {
    public static final long measureTimeMillis(@NotNull Function0 block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        long jCurrentTimeMillis = System.currentTimeMillis();
        block.invoke();
        return System.currentTimeMillis() - jCurrentTimeMillis;
    }

    public static final long measureNanoTime(@NotNull Function0 block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        long jNanoTime = System.nanoTime();
        block.invoke();
        return System.nanoTime() - jNanoTime;
    }
}

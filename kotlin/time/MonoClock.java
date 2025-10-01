package kotlin.time;

import java.util.concurrent.TimeUnit;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u00c7\u0002\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, m27d2 = {"Lkotlin/time/MonoClock;", "Lkotlin/time/AbstractLongClock;", "Lkotlin/time/Clock;", "()V", "read", "", "toString", "", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/MonoClock.class */
public final class MonoClock extends AbstractLongClock implements Clock {
    public static final MonoClock INSTANCE = new MonoClock();

    private MonoClock() {
        super(TimeUnit.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongClock
    protected long read() {
        return System.nanoTime();
    }
}

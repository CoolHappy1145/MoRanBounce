package kotlin.time;

import java.util.concurrent.TimeUnit;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.LongCompanionObject;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, m27d2 = {"Lkotlin/time/TestClock;", "Lkotlin/time/AbstractLongClock;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: L-out.jar:kotlin/time/TestClock.class */
public final class TestClock extends AbstractLongClock {
    private long reading;

    public TestClock() {
        super(TimeUnit.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongClock
    protected long read() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m1528plusAssignLRDsOJo(double d) {
        long j;
        double dM1502toDoubleimpl = Duration.m1502toDoubleimpl(d, getUnit());
        long j2 = (long) dM1502toDoubleimpl;
        if (j2 != Long.MIN_VALUE && j2 != LongCompanionObject.MAX_VALUE) {
            long j3 = this.reading + j2;
            if ((this.reading ^ j2) >= 0 && (this.reading ^ j3) < 0) {
                m1529overflowLRDsOJo(d);
            }
            j = j3;
        } else {
            double d2 = this.reading + dM1502toDoubleimpl;
            if (d2 > 9.223372036854776E18d || d2 < -9.223372036854776E18d) {
                m1529overflowLRDsOJo(d);
            }
            j = (long) d2;
        }
        this.reading = j;
    }

    /* renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m1529overflowLRDsOJo(double d) {
        throw new IllegalStateException("TestClock will overflow if its reading " + this.reading + "ns is advanced by " + Duration.m1514toStringimpl(d) + '.');
    }
}

package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u000f\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/MoveEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "x", "", "y", "z", "(DDD)V", "isSafeWalk", "", "()Z", "setSafeWalk", "(Z)V", "getX", "()D", "setX", "(D)V", "getY", "setY", "getZ", "setZ", "zero", "", "zeroXZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/MoveEvent.class */
public final class MoveEvent extends CancellableEvent {
    private boolean isSafeWalk;

    /* renamed from: x */
    private double f114x;

    /* renamed from: y */
    private double f115y;

    /* renamed from: z */
    private double f116z;

    public final double getX() {
        return this.f114x;
    }

    public final void setX(double d) {
        this.f114x = d;
    }

    public final double getY() {
        return this.f115y;
    }

    public final void setY(double d) {
        this.f115y = d;
    }

    public final double getZ() {
        return this.f116z;
    }

    public final void setZ(double d) {
        this.f116z = d;
    }

    public MoveEvent(double d, double d2, double d3) {
        this.f114x = d;
        this.f115y = d2;
        this.f116z = d3;
    }

    public final boolean isSafeWalk() {
        return this.isSafeWalk;
    }

    public final void setSafeWalk(boolean z) {
        this.isSafeWalk = z;
    }

    public final void zero() {
        this.f114x = 0.0d;
        this.f115y = 0.0d;
        this.f116z = 0.0d;
    }

    public final void zeroXZ() {
        this.f114x = 0.0d;
        this.f116z = 0.0d;
    }
}

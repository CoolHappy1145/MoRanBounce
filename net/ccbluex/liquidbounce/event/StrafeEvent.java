package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\b\u0018\ufffd\ufffd2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\b\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "strafe", "", "forward", "friction", "(FFF)V", "getForward", "()F", "getFriction", "getStrafe", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/StrafeEvent.class */
public final class StrafeEvent extends CancellableEvent {
    private final float strafe;
    private final float forward;
    private final float friction;

    public final float getStrafe() {
        return this.strafe;
    }

    public final float getForward() {
        return this.forward;
    }

    public final float getFriction() {
        return this.friction;
    }

    public StrafeEvent(float f, float f2, float f3) {
        this.strafe = f;
        this.forward = f2;
        this.friction = f3;
    }
}

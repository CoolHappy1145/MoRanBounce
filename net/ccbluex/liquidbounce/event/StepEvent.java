package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/StepEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "stepHeight", "", "(F)V", "getStepHeight", "()F", "setStepHeight", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/StepEvent.class */
public final class StepEvent extends Event {
    private float stepHeight;

    public final float getStepHeight() {
        return this.stepHeight;
    }

    public final void setStepHeight(float f) {
        this.stepHeight = f;
    }

    public StepEvent(float f) {
        this.stepHeight = f;
    }
}

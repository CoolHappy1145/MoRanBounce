package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/MotionEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "eventState", "Lnet/ccbluex/liquidbounce/event/EventState;", "(Lnet/ccbluex/liquidbounce/event/EventState;)V", "getEventState", "()Lnet/ccbluex/liquidbounce/event/EventState;", "isPre", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/MotionEvent.class */
public final class MotionEvent extends Event {

    @NotNull
    private final EventState eventState;

    @NotNull
    public final EventState getEventState() {
        return this.eventState;
    }

    public MotionEvent(@NotNull EventState eventState) {
        Intrinsics.checkParameterIsNotNull(eventState, "eventState");
        this.eventState = eventState;
    }

    public final boolean isPre() {
        return this.eventState == EventState.PRE;
    }
}

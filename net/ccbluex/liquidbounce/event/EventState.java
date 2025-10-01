package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/EventState;", "", "stateName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getStateName", "()Ljava/lang/String;", "PRE", "POST", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/EventState.class */
public enum EventState {
    PRE("PRE"),
    POST("POST");


    @NotNull
    private final String stateName;

    @NotNull
    public final String getStateName() {
        return this.stateName;
    }

    EventState(String str) {
        this.stateName = str;
    }
}

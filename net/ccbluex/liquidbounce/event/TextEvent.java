package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\ufffd\ufffd2\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/TextEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "setText", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/TextEvent.class */
public final class TextEvent extends Event {

    @Nullable
    private String text;

    @Nullable
    public final String getText() {
        return this.text;
    }

    public final void setText(@Nullable String str) {
        this.text = str;
    }

    public TextEvent(@Nullable String str) {
        this.text = str;
    }
}

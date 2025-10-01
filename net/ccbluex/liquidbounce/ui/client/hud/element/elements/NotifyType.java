package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;", "", "renderColor", "Ljava/awt/Color;", "(Ljava/lang/String;ILjava/awt/Color;)V", "getRenderColor", "()Ljava/awt/Color;", "setRenderColor", "(Ljava/awt/Color;)V", "SUCCESS", "ERROR", "WARNING", "INFO", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType.class */
public enum NotifyType {
    SUCCESS(new Color(6348902)),
    ERROR(new Color(16723770)),
    WARNING(new Color(16121088)),
    INFO(new Color(6590631));


    @NotNull
    private Color renderColor;

    @NotNull
    public final Color getRenderColor() {
        return this.renderColor;
    }

    public final void setRenderColor(@NotNull Color color) {
        Intrinsics.checkParameterIsNotNull(color, "<set-?>");
        this.renderColor = color;
    }

    NotifyType(Color color) {
        this.renderColor = color;
    }
}

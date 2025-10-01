package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\ufffd\ufffdH&J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "", "chatStyle", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IChatStyle;", "getChatStyle", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IChatStyle;", "formattedText", "", "getFormattedText", "()Ljava/lang/String;", "unformattedText", "getUnformattedText", "appendSibling", "", "component", "appendText", "text", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent.class */
public interface IIChatComponent {
    @NotNull
    String getUnformattedText();

    @NotNull
    IChatStyle getChatStyle();

    @NotNull
    String getFormattedText();

    void appendText(@NotNull String str);

    void appendSibling(@NotNull IIChatComponent iIChatComponent);
}

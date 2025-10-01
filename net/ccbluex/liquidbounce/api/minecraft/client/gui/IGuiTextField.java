package net.ccbluex.liquidbounce.api.minecraft.client.gui;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\b\bf\u0018\ufffd\ufffd2\u00020\u0001J\b\u0010\u0015\u001a\u00020\u0016H&J\u0018\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bH&J \u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\bH&J\u0018\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bH&J\b\u0010 \u001a\u00020\u0016H&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00020\u000eX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\n\u00a8\u0006!"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGui;", "isFocused", "", "()Z", "setFocused", "(Z)V", "maxStringLength", "", "getMaxStringLength", "()I", "setMaxStringLength", "(I)V", "text", "", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "xPosition", "getXPosition", "drawTextBox", "", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseX", "mouseY", "mouseButton", "textboxKeyTyped", "updateCursorCounter", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField.class */
public interface IGuiTextField extends IGui {
    int getXPosition();

    @NotNull
    String getText();

    void setText(@NotNull String str);

    boolean isFocused();

    void setFocused(boolean z);

    int getMaxStringLength();

    void setMaxStringLength(int i);

    void updateCursorCounter();

    boolean textboxKeyTyped(char c, int i);

    void drawTextBox();

    void mouseClicked(int i, int i2, int i3);

    boolean keyTyped(char c, int i);
}

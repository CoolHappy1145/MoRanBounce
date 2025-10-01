package net.ccbluex.liquidbounce.api.minecraft.client.gui;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\ufffd\ufffd2\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGui;", "displayString", "", "getDisplayString", "()Ljava/lang/String;", "setDisplayString", "(Ljava/lang/String;)V", "enabled", "", "getEnabled", "()Z", "setEnabled", "(Z)V", "id", "", "getId", "()I", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton.class */
public interface IGuiButton extends IGui {
    @NotNull
    String getDisplayString();

    void setDisplayString(@NotNull String str);

    int getId();

    boolean getEnabled();

    void setEnabled(boolean z);
}

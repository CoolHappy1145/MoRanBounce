package net.ccbluex.liquidbounce.p005ui.client;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiModsMenu;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiModsMenu.class */
public final class GuiModsMenu extends WrappedGuiScreen {
    private final IGuiScreen prevGui;

    public GuiModsMenu(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    public void initGui() {
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 48, "Forge Mods"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 48 + 25, "Scripts"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 48 + 50, "Back"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        switch (button.getId()) {
            case 0:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.createGuiModList(getRepresentedScreen()));
                break;
            case 1:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiScripts(getRepresentedScreen())));
                break;
            case 2:
                MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
                break;
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        Fonts.fontBold180.drawCenteredString("Mods", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 5.0f, 4673984, true);
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(c, i);
        }
    }
}

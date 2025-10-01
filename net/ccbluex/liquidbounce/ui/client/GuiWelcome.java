package net.ccbluex.liquidbounce.p005ui.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiWelcome;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "()V", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiWelcome.class */
public final class GuiWelcome extends WrappedGuiScreen {
    public void initGui() {
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, getRepresentedScreen().getHeight() - 40, "Ok"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        IFontRenderer iFontRenderer = Fonts.font35;
        iFontRenderer.drawCenteredString("Thank you for downloading and installing our client!", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 70.0f, 16777215, true);
        iFontRenderer.drawCenteredString("Here is some information you might find useful if you are using LiquidBounce for the first time.", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 70.0f + iFontRenderer.getFontHeight(), 16777215, true);
        iFontRenderer.drawCenteredString("\u00a7lClickGUI:", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 3), 16777215, true);
        StringBuilder sbAppend = new StringBuilder().append("Press ");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(ClickGUI.class);
        if (module == null) {
            Intrinsics.throwNpe();
        }
        iFontRenderer.drawCenteredString(sbAppend.append(Keyboard.getKeyName(module.getKeyBind())).append(" to open up the ClickGUI").toString(), getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8) + 80.0f + (iFontRenderer.getFontHeight() * 4), 16777215, true);
        iFontRenderer.drawCenteredString("Right-click modules with a '+' next to them to edit their settings.", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 5), 16777215, true);
        iFontRenderer.drawCenteredString("Hover a module to see it's description.", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 6), 16777215, true);
        iFontRenderer.drawCenteredString("\u00a7lImportant Commands:", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 8), 16777215, true);
        iFontRenderer.drawCenteredString(".bind <module> <key> / .bind <module> none", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 9), 16777215, true);
        iFontRenderer.drawCenteredString(".autosettings load <name> / .autosettings list", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 10), 16777215, true);
        iFontRenderer.drawCenteredString("\u00a7lNeed help? Feel free to contact us!", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 12), 16777215, true);
        iFontRenderer.drawCenteredString("YouTube: https://youtube.com/ccbluex", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 13), 16777215, true);
        iFontRenderer.drawCenteredString("Twitter: https://twitter.com/ccbluex", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 14), 16777215, true);
        iFontRenderer.drawCenteredString("Forum: https://forum.ccbluex.net/", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 80.0f + (iFontRenderer.getFontHeight() * 15), 16777215, true);
        super.drawScreen(i, i2, f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        Fonts.font40.drawCenteredString("Welcome!", (getRepresentedScreen().getWidth() / 2) / 2.0f, ((getRepresentedScreen().getHeight() / 8.0f) / 2.0f) + 20.0f, new Color(0, 140, 255).getRGB(), true);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            return;
        }
        super.keyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getId() == 1) {
            MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiMainMenu()));
        }
    }
}

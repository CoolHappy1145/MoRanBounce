package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub;

import jdk.nashorn.internal.codegen.SharedScopeCall;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiChangeName.class */
public class GuiChangeName extends WrappedGuiScreen {
    private final GuiAltManager prevGui;
    private IGuiTextField name;
    private String status;

    public GuiChangeName(GuiAltManager guiAltManager) {
        this.prevGui = guiAltManager;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(1, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 96, "Change"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(0, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 120, "Back"));
        this.name = classProvider.createGuiTextField(2, Fonts.font40, (this.representedScreen.getWidth() / 2) - 100, 60, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.name.setFocused(true);
        this.name.setText(f157mc.getSession().getUsername());
        this.name.setMaxStringLength(16);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        this.representedScreen.drawBackground(0);
        RenderUtils.drawRect(30, 30, this.representedScreen.getWidth() - 30, this.representedScreen.getHeight() - 30, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Change Name", this.representedScreen.getWidth() / 2.0f, 34.0f, 16777215);
        Fonts.font40.drawCenteredString(this.status == null ? "" : this.status, this.representedScreen.getWidth() / 2.0f, (this.representedScreen.getHeight() / 4.0f) + 84.0f, 16777215);
        this.name.drawTextBox();
        if (this.name.getText().isEmpty() && !this.name.isFocused()) {
            Fonts.font40.drawCenteredString("\u00a77Username", (this.representedScreen.getWidth() / 2.0f) - 74.0f, 66.0f, 16777215);
        }
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(IGuiButton iGuiButton) {
        switch (iGuiButton.getId()) {
            case 0:
                f157mc.displayGuiScreen(this.prevGui.representedScreen);
                break;
            case 1:
                if (this.name.getText().isEmpty()) {
                    this.status = "\u00a7cEnter a name!";
                    break;
                } else if (!this.name.getText().equalsIgnoreCase(f157mc.getSession().getUsername())) {
                    this.status = "\u00a7cJust change the upper and lower case!";
                    break;
                } else {
                    f157mc.setSession(classProvider.createSession(this.name.getText(), f157mc.getSession().getPlayerId(), f157mc.getSession().getToken(), f157mc.getSession().getSessionType()));
                    LiquidBounce.eventManager.callEvent(new SessionEvent());
                    this.status = "\u00a7aChanged name to \u00a77" + this.name.getText() + "\u00a7c.";
                    this.prevGui.status = this.status;
                    f157mc.displayGuiScreen(this.prevGui.representedScreen);
                    break;
                }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            f157mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
            return;
        }
        if (this.name.isFocused()) {
            this.name.textboxKeyTyped(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        this.name.mouseClicked(i, i2, i3);
        super.mouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        this.name.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}

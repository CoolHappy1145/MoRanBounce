package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDirectLogin.class */
public class GuiDirectLogin extends WrappedGuiScreen {
    private final IGuiScreen prevGui;
    private IGuiButton loginButton;
    private IGuiButton clipboardLoginButton;
    private IGuiTextField username;
    private IGuiTextField password;
    private String status = "\u00a77Idle...";

    public GuiDirectLogin(GuiAltManager guiAltManager) {
        this.prevGui = guiAltManager.representedScreen;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        List buttonList = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButtonCreateGuiButton = classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 72, "Login");
        this.loginButton = iGuiButtonCreateGuiButton;
        buttonList.add(iGuiButtonCreateGuiButton);
        List buttonList2 = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButtonCreateGuiButton2 = classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 96, "Clipboard Login");
        this.clipboardLoginButton = iGuiButtonCreateGuiButton2;
        buttonList2.add(iGuiButtonCreateGuiButton2);
        getRepresentedScreen().getButtonList().add(classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 120, "Back"));
        this.username = classProvider.createGuiTextField(2, Fonts.font40, (getRepresentedScreen().getWidth() / 2) - 100, 60, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(Integer.MAX_VALUE);
        this.password = classProvider.createGuiPasswordField(3, Fonts.font40, (getRepresentedScreen().getWidth() / 2) - 100, 85, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.password.setMaxStringLength(Integer.MAX_VALUE);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30, 30, getRepresentedScreen().getWidth() - 30, getRepresentedScreen().getHeight() - 30, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Direct Login", getRepresentedScreen().getWidth() / 2.0f, 34.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status == null ? "" : this.status, getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 4.0f) + 60.0f, 16777215);
        this.username.drawTextBox();
        this.password.drawTextBox();
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            Fonts.font40.drawCenteredString("\u00a77Username / E-Mail", (getRepresentedScreen().getWidth() / 2.0f) - 55.0f, 66.0f, 16777215);
        }
        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            Fonts.font40.drawCenteredString("\u00a77Password", (getRepresentedScreen().getWidth() / 2.0f) - 74.0f, 91.0f, 16777215);
        }
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(IGuiButton iGuiButton) throws IOException {
        if (iGuiButton.getEnabled()) {
            switch (iGuiButton.getId()) {
                case 0:
                    f157mc.displayGuiScreen(this.prevGui);
                    break;
                case 1:
                    if (this.username.getText().isEmpty()) {
                        this.status = "\u00a7cYou have to fill in both fields!";
                        break;
                    } else {
                        this.loginButton.setEnabled(false);
                        this.clipboardLoginButton.setEnabled(false);
                        new Thread(this::lambda$actionPerformed$0).start();
                        break;
                    }
                case 2:
                    try {
                        String str = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        String[] strArrSplit = str.split(CallSiteDescriptor.TOKEN_DELIMITER, 2);
                        if (!str.contains(CallSiteDescriptor.TOKEN_DELIMITER) || strArrSplit.length != 2) {
                            this.status = "\u00a7cInvalid clipboard data. (Use: E-Mail:Password)";
                            break;
                        } else {
                            this.loginButton.setEnabled(false);
                            this.clipboardLoginButton.setEnabled(false);
                            new Thread(() -> {
                                r2.lambda$actionPerformed$1(r3);
                            }).start();
                            break;
                        }
                    } catch (UnsupportedFlavorException e) {
                        this.status = "\u00a7cClipboard flavor unsupported!";
                        ClientUtils.getLogger().error("Failed to read data from clipboard.", e);
                        return;
                    } catch (IOException e2) {
                        this.status = "\u00a7cUnknown error! (See log)";
                        ClientUtils.getLogger().error(e2);
                        return;
                    }
            }
        }
    }

    private void lambda$actionPerformed$0() {
        this.status = "\u00a7aLogging in...";
        if (this.password.getText().isEmpty()) {
            this.status = GuiAltManager.login(new MinecraftAccount(ColorUtils.translateAlternateColorCodes(this.username.getText())));
        } else {
            this.status = GuiAltManager.login(new MinecraftAccount(this.username.getText(), this.password.getText()));
        }
        this.loginButton.setEnabled(true);
        this.clipboardLoginButton.setEnabled(true);
    }

    private void lambda$actionPerformed$1(String[] strArr) {
        this.status = "\u00a7aLogging in...";
        this.status = GuiAltManager.login(new MinecraftAccount(strArr[0], strArr[1]));
        this.loginButton.setEnabled(true);
        this.clipboardLoginButton.setEnabled(true);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) throws IOException {
        switch (i) {
            case 1:
                f157mc.displayGuiScreen(this.prevGui);
                break;
            case OPCode.EXACTN_IC /* 15 */:
                TabUtils.tab(new IGuiTextField[]{this.username, this.password});
                break;
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                actionPerformed(this.loginButton);
                break;
            default:
                if (this.username.isFocused()) {
                    this.username.textboxKeyTyped(c, i);
                }
                if (this.password.isFocused()) {
                    this.password.textboxKeyTyped(c, i);
                }
                super.keyTyped(c, i);
                break;
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        this.username.mouseClicked(i, i2, i3);
        this.password.mouseClicked(i, i2, i3);
        super.mouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}

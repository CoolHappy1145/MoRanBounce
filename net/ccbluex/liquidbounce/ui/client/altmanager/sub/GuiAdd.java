package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.net.Proxy;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiAdd.class */
public class GuiAdd extends WrappedGuiScreen {
    private final GuiAltManager prevGui;
    private IGuiButton addButton;
    private IGuiButton clipboardButton;
    private IGuiTextField username;
    private IGuiTextField password;
    private String status = "\u00a77Idle...";

    public GuiAdd(GuiAltManager guiAltManager) {
        this.prevGui = guiAltManager;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        List buttonList = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton = classProvider.createGuiButton(1, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 72, "Add");
        this.addButton = iGuiButtonCreateGuiButton;
        buttonList.add(iGuiButtonCreateGuiButton);
        List buttonList2 = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton2 = classProvider.createGuiButton(2, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 96, "Clipboard");
        this.clipboardButton = iGuiButtonCreateGuiButton2;
        buttonList2.add(iGuiButtonCreateGuiButton2);
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(0, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 120, "Back"));
        this.username = classProvider.createGuiTextField(2, Fonts.font40, (this.representedScreen.getWidth() / 2) - 100, 60, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(Integer.MAX_VALUE);
        this.password = classProvider.createGuiPasswordField(3, Fonts.font40, (this.representedScreen.getWidth() / 2) - 100, 85, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.password.setMaxStringLength(Integer.MAX_VALUE);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        this.representedScreen.drawBackground(0);
        RenderUtils.drawRect(30, 30, this.representedScreen.getWidth() - 30, this.representedScreen.getHeight() - 30, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Add Account", this.representedScreen.getWidth() / 2.0f, 34.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status == null ? "" : this.status, this.representedScreen.getWidth() / 2.0f, (this.representedScreen.getHeight() / 4.0f) + 60.0f, 16777215);
        this.username.drawTextBox();
        this.password.drawTextBox();
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            Fonts.font40.drawCenteredString("\u00a77Username / E-Mail", (this.representedScreen.getWidth() / 2) - 55, 66.0f, 16777215);
        }
        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            Fonts.font40.drawCenteredString("\u00a77Password", (this.representedScreen.getWidth() / 2) - 74, 91.0f, 16777215);
        }
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(IGuiButton iGuiButton) {
        if (!iGuiButton.getEnabled()) {
        }
        switch (iGuiButton.getId()) {
            case 0:
                f157mc.displayGuiScreen(this.prevGui.representedScreen);
                break;
            case 1:
                if (LiquidBounce.fileManager.accountsConfig.accountExists(this.username.getText())) {
                    this.status = "\u00a7cThe account has already been added.";
                    break;
                } else {
                    addAccount(this.username.getText(), this.password.getText());
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
                        addAccount(strArrSplit[0], strArrSplit[1]);
                        break;
                    }
                } catch (UnsupportedFlavorException e) {
                    this.status = "\u00a7cClipboard flavor unsupported!";
                    ClientUtils.getLogger().error("Failed to read data from clipboard.", e);
                    return;
                }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        switch (i) {
            case 1:
                f157mc.displayGuiScreen(this.prevGui.representedScreen);
                break;
            case OPCode.EXACTN_IC /* 15 */:
                TabUtils.tab(new IGuiTextField[]{this.username, this.password});
                break;
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                actionPerformed(this.addButton);
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

    private void addAccount(String str, String str2) {
        if (LiquidBounce.fileManager.accountsConfig.accountExists(str)) {
            this.status = "\u00a7cThe account has already been added.";
            return;
        }
        this.addButton.setEnabled(false);
        this.clipboardButton.setEnabled(false);
        MinecraftAccount minecraftAccount = new MinecraftAccount(str, str2);
        new Thread(() -> {
            r2.lambda$addAccount$0(r3);
        }).start();
    }

    private void lambda$addAccount$0(MinecraftAccount minecraftAccount) {
        if (!minecraftAccount.isCracked()) {
            this.status = "\u00a7aChecking...";
            try {
                AltService.EnumAltService currentService = GuiAltManager.altService.getCurrentService();
                if (currentService != AltService.EnumAltService.MOJANG) {
                    GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                }
                YggdrasilUserAuthentication yggdrasilUserAuthenticationCreateUserAuthentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
                yggdrasilUserAuthenticationCreateUserAuthentication.setUsername(minecraftAccount.getName());
                yggdrasilUserAuthenticationCreateUserAuthentication.setPassword(minecraftAccount.getPassword());
                yggdrasilUserAuthenticationCreateUserAuthentication.logIn();
                minecraftAccount.setAccountName(yggdrasilUserAuthenticationCreateUserAuthentication.getSelectedProfile().getName());
                if (currentService == AltService.EnumAltService.THEALTENING) {
                    GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                }
            } catch (NullPointerException | AuthenticationException | IllegalAccessException | NoSuchFieldException unused) {
                this.status = "\u00a7cThe account doesn't work.";
                this.addButton.setEnabled(true);
                this.clipboardButton.setEnabled(true);
                return;
            }
        }
        LiquidBounce.fileManager.accountsConfig.getAccounts().add(minecraftAccount);
        LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
        this.status = "\u00a7aThe account has been added.";
        this.prevGui.status = this.status;
        f157mc.displayGuiScreen(this.prevGui.representedScreen);
    }
}

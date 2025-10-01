package net.ccbluex.liquidbounce.p005ui.client.altmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.thealtening.AltService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JOptionPane;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.text.Typography;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiAdd;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiChangeName;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiDirectLogin;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiSessionLogin;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.altgenerator.GuiMCLeaks;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.mcleaks.MCLeaks;
import org.apache.log4j.net.SyslogAppender;
import org.json.HTTP;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager.class */
public class GuiAltManager extends WrappedGuiScreen {
    public static final AltService altService = new AltService();
    private static final Map GENERATORS = new HashMap();
    private final IGuiScreen prevGui;
    public String status = "\u00a77Idle...";
    private IGuiButton loginButton;
    private IGuiButton randomButton;
    private GuiList altsList;
    private IGuiTextField searchField;

    public GuiAltManager(IGuiScreen iGuiScreen) {
        this.prevGui = iGuiScreen;
    }

    public static void loadGenerators() {
        try {
            JsonElement jsonElement = new JsonParser().parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/generators.json"));
            if (jsonElement.isJsonObject()) {
                jsonElement.getAsJsonObject().entrySet().forEach(GuiAltManager::lambda$loadGenerators$0);
            }
        } catch (Throwable th) {
            ClientUtils.getLogger().error("Failed to load enabled generators.", th);
        }
    }

    private static void lambda$loadGenerators$0(Map.Entry entry) {
    }

    public static String login(MinecraftAccount minecraftAccount) {
        if (minecraftAccount == null) {
            return "";
        }
        if (altService.getCurrentService() != AltService.EnumAltService.MOJANG) {
            try {
                altService.switchService(AltService.EnumAltService.MOJANG);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", e);
            }
        }
        if (minecraftAccount.isCracked()) {
            LoginUtils.loginCracked(minecraftAccount.getName());
            MCLeaks.remove();
            return "\u00a7cYour name is now \u00a78" + minecraftAccount.getName() + "\u00a7c.";
        }
        LoginUtils.LoginResult loginResultLogin = LoginUtils.login(minecraftAccount.getName(), minecraftAccount.getPassword());
        if (loginResultLogin == LoginUtils.LoginResult.LOGGED) {
            MCLeaks.remove();
            String username = f157mc.getSession().getUsername();
            minecraftAccount.setAccountName(username);
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
            return "\u00a7cYour name is now \u00a7f\u00a7l" + username + "\u00a7c.";
        }
        if (loginResultLogin == LoginUtils.LoginResult.WRONG_PASSWORD) {
            return "\u00a7cWrong password.";
        }
        if (loginResultLogin == LoginUtils.LoginResult.NO_CONTACT) {
            return "\u00a7cCannot contact authentication server.";
        }
        if (loginResultLogin == LoginUtils.LoginResult.INVALID_ACCOUNT_DATA) {
            return "\u00a7cInvalid username or password.";
        }
        if (loginResultLogin == LoginUtils.LoginResult.MIGRATED) {
            return "\u00a7cAccount migrated.";
        }
        return "";
    }

    public void initGui() {
        int iMax = Math.max(this.representedScreen.getWidth() / 8, 70);
        this.searchField = classProvider.createGuiTextField(2, Fonts.font40, (this.representedScreen.getWidth() - iMax) - 10, 10, iMax, 20);
        this.searchField.setMaxStringLength(Integer.MAX_VALUE);
        this.altsList = new GuiList(this, this.representedScreen);
        this.altsList.represented.registerScrollButtons(7, 8);
        int i = -1;
        for (int i2 = 0; i2 < LiquidBounce.fileManager.accountsConfig.getAccounts().size(); i2++) {
            MinecraftAccount minecraftAccount = (MinecraftAccount) LiquidBounce.fileManager.accountsConfig.getAccounts().get(i2);
            if (minecraftAccount != null && (((minecraftAccount.getPassword() == null || minecraftAccount.getPassword().isEmpty()) && minecraftAccount.getName() != null && minecraftAccount.getName().equals(f157mc.getSession().getUsername())) || (minecraftAccount.getAccountName() != null && minecraftAccount.getAccountName().equals(f157mc.getSession().getUsername())))) {
                i = i2;
                break;
            }
        }
        this.altsList.elementClicked(i, false, 0, 0);
        this.altsList.represented.scrollBy(i * this.altsList.represented.getSlotHeight());
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(1, this.representedScreen.getWidth() - 80, 46, 70, 20, "Add"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(2, this.representedScreen.getWidth() - 80, 70, 70, 20, "Remove"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(7, this.representedScreen.getWidth() - 80, 94, 70, 20, "Import"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(12, this.representedScreen.getWidth() - 80, 118, 70, 20, "Export"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(8, this.representedScreen.getWidth() - 80, 142, 70, 20, "Copy"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(0, this.representedScreen.getWidth() - 80, this.representedScreen.getHeight() - 65, 70, 20, "Back"));
        List buttonList = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton = classProvider.createGuiButton(3, 5, 46, 90, 20, "Login");
        this.loginButton = iGuiButtonCreateGuiButton;
        buttonList.add(iGuiButtonCreateGuiButton);
        List buttonList2 = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton2 = classProvider.createGuiButton(4, 5, 70, 90, 20, "Random");
        this.randomButton = iGuiButtonCreateGuiButton2;
        buttonList2.add(iGuiButtonCreateGuiButton2);
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(6, 5, 94, 90, 20, "Direct Login"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(88, 5, 118, 90, 20, "Change Name"));
        if (((Boolean) GENERATORS.getOrDefault("mcleaks", true)).booleanValue()) {
            this.representedScreen.getButtonList().add(classProvider.createGuiButton(5, 5, 147, 90, 20, "MCLeaks"));
        }
        if (((Boolean) GENERATORS.getOrDefault("thealtening", true)).booleanValue()) {
            this.representedScreen.getButtonList().add(classProvider.createGuiButton(9, 5, Typography.leftGuillemete, 90, 20, "TheAltening"));
        }
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(10, 5, 195, 90, 20, "Session Login"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(11, 5, 224, 90, 20, "Cape"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        this.representedScreen.drawBackground(0);
        this.altsList.represented.drawScreen(i, i2, f);
        Fonts.font40.drawCenteredString("AltManager", this.representedScreen.getWidth() / 2.0f, 6.0f, 16777215);
        Fonts.font35.drawCenteredString(this.searchField.getText().isEmpty() ? LiquidBounce.fileManager.accountsConfig.getAccounts().size() + " Alts" : this.altsList.accounts.size() + " Search Results", this.representedScreen.getWidth() / 2.0f, 18.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, this.representedScreen.getWidth() / 2.0f, 32.0f, 16777215);
        Fonts.font35.drawStringWithShadow("\u00a77User: \u00a7a" + (MCLeaks.isAltActive() ? MCLeaks.getSession().getUsername() : f157mc.getSession().getUsername()), 6, 6, 16777215);
        Fonts.font35.drawStringWithShadow("\u00a77Type: \u00a7a" + (altService.getCurrentService() == AltService.EnumAltService.THEALTENING ? "TheAltening" : MCLeaks.isAltActive() ? "MCLeaks" : UserUtils.INSTANCE.isValidTokenOffline(f157mc.getSession().getToken()) ? "Premium" : "Cracked"), 6, 15, 16777215);
        this.searchField.drawTextBox();
        if (this.searchField.getText().isEmpty() && !this.searchField.isFocused()) {
            Fonts.font40.drawStringWithShadow("\u00a77Search...", this.searchField.getXPosition() + 4, 17, 16777215);
        }
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(IGuiButton iGuiButton) throws IOException {
        if (!iGuiButton.getEnabled()) {
        }
        switch (iGuiButton.getId()) {
            case 0:
                f157mc.displayGuiScreen(this.prevGui);
                break;
            case 1:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiAdd(this)));
                break;
            case 2:
                if (this.altsList.getSelectedSlot() == -1 || this.altsList.getSelectedSlot() >= this.altsList.getSize()) {
                    this.status = "\u00a7cSelect an account.";
                    break;
                } else {
                    LiquidBounce.fileManager.accountsConfig.removeAccount((MinecraftAccount) this.altsList.accounts.get(this.altsList.getSelectedSlot()));
                    LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
                    this.status = "\u00a7aThe account has been removed.";
                    this.altsList.updateAccounts(this.searchField.getText());
                    break;
                }
                break;
            case 3:
                if (this.altsList.getSelectedSlot() != -1 && this.altsList.getSelectedSlot() < this.altsList.getSize()) {
                    this.loginButton.setEnabled(false);
                    this.randomButton.setEnabled(false);
                    new Thread(this::lambda$actionPerformed$1, "AltLogin").start();
                    break;
                } else {
                    this.status = "\u00a7cSelect an account.";
                    break;
                }
                break;
            case 4:
                if (this.altsList.accounts.size() <= 0) {
                    this.status = "\u00a7cThe list is empty.";
                    break;
                } else {
                    int iNextInt = new Random().nextInt(this.altsList.accounts.size());
                    if (iNextInt < this.altsList.getSize()) {
                        this.altsList.selectedSlot = iNextInt;
                    }
                    this.loginButton.setEnabled(false);
                    this.randomButton.setEnabled(false);
                    new Thread(() -> {
                        r2.lambda$actionPerformed$2(r3);
                    }, "AltLogin").start();
                    break;
                }
            case 5:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiMCLeaks(this)));
                break;
            case 6:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiDirectLogin(this)));
                break;
            case 7:
                File fileOpenFileChooser = MiscUtils.openFileChooser();
                if (fileOpenFileChooser != null) {
                    FileReader fileReader = new FileReader(fileOpenFileChooser);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            String[] strArrSplit = line.split(CallSiteDescriptor.TOKEN_DELIMITER, 2);
                            if (!LiquidBounce.fileManager.accountsConfig.accountExists(strArrSplit[0])) {
                                if (strArrSplit.length > 1) {
                                    LiquidBounce.fileManager.accountsConfig.addAccount(strArrSplit[0], strArrSplit[1]);
                                } else {
                                    LiquidBounce.fileManager.accountsConfig.addAccount(strArrSplit[0]);
                                }
                            }
                        } else {
                            fileReader.close();
                            bufferedReader.close();
                            this.altsList.updateAccounts(this.searchField.getText());
                            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
                            this.status = "\u00a7aThe accounts were imported successfully.";
                            break;
                        }
                    }
                }
                break;
            case 8:
                if (this.altsList.getSelectedSlot() == -1 || this.altsList.getSelectedSlot() >= this.altsList.getSize()) {
                    this.status = "\u00a7cSelect an account.";
                    break;
                } else {
                    MinecraftAccount minecraftAccount = (MinecraftAccount) this.altsList.accounts.get(this.altsList.getSelectedSlot());
                    if (minecraftAccount != null) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(minecraftAccount.getName() + CallSiteDescriptor.TOKEN_DELIMITER + minecraftAccount.getPassword()), (ClipboardOwner) null);
                        this.status = "\u00a7aCopied account into your clipboard.";
                        break;
                    }
                }
                break;
            case 9:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiTheAltening(this)));
                break;
            case 10:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiSessionLogin(this)));
                break;
            case 11:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiDonatorCape(this)));
                break;
            case 12:
                if (LiquidBounce.fileManager.accountsConfig.getAccounts().size() == 0) {
                    this.status = "\u00a7cThe list is empty.";
                    break;
                } else {
                    File fileSaveFileChooser = MiscUtils.saveFileChooser();
                    if (fileSaveFileChooser != null && !fileSaveFileChooser.isDirectory()) {
                        try {
                            if (!fileSaveFileChooser.exists()) {
                                fileSaveFileChooser.createNewFile();
                            }
                            FileWriter fileWriter = new FileWriter(fileSaveFileChooser);
                            for (MinecraftAccount minecraftAccount2 : LiquidBounce.fileManager.accountsConfig.getAccounts()) {
                                if (minecraftAccount2.isCracked()) {
                                    fileWriter.write(minecraftAccount2.getName() + HTTP.CRLF);
                                } else {
                                    fileWriter.write(minecraftAccount2.getName() + CallSiteDescriptor.TOKEN_DELIMITER + minecraftAccount2.getPassword() + HTTP.CRLF);
                                }
                            }
                            fileWriter.flush();
                            fileWriter.close();
                            JOptionPane.showMessageDialog((Component) null, "Exported successfully!", "AltManager", 1);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            MiscUtils.showErrorPopup("Error", "Exception class: " + e.getClass().getName() + "\nMessage: " + e.getMessage());
                            return;
                        }
                    }
                }
                break;
            case SyslogAppender.LOG_FTP /* 88 */:
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiChangeName(this)));
                break;
        }
    }

    private void lambda$actionPerformed$1() {
        MinecraftAccount minecraftAccount = (MinecraftAccount) this.altsList.accounts.get(this.altsList.getSelectedSlot());
        this.status = "\u00a7aLogging in...";
        this.status = login(minecraftAccount);
        this.loginButton.setEnabled(true);
        this.randomButton.setEnabled(true);
    }

    private void lambda$actionPerformed$2(int i) {
        MinecraftAccount minecraftAccount = (MinecraftAccount) this.altsList.accounts.get(i);
        this.status = "\u00a7aLogging in...";
        this.status = login(minecraftAccount);
        this.loginButton.setEnabled(true);
        this.randomButton.setEnabled(true);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (this.searchField.isFocused()) {
            this.searchField.textboxKeyTyped(c, i);
            this.altsList.updateAccounts(this.searchField.getText());
        }
        switch (i) {
            case 1:
                f157mc.displayGuiScreen(this.prevGui);
                return;
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                this.altsList.elementClicked(this.altsList.getSelectedSlot(), true, 0, 0);
                break;
            case SharedScopeCall.FAST_SCOPE_GET_THRESHOLD /* 200 */:
                int selectedSlot = this.altsList.getSelectedSlot() - 1;
                if (selectedSlot < 0) {
                    selectedSlot = 0;
                }
                this.altsList.elementClicked(selectedSlot, false, 0, 0);
                break;
            case 201:
                this.altsList.represented.scrollBy((-this.representedScreen.getHeight()) + 100);
                return;
            case 208:
                int selectedSlot2 = this.altsList.getSelectedSlot() + 1;
                if (selectedSlot2 >= this.altsList.getSize()) {
                    selectedSlot2 = this.altsList.getSize() - 1;
                }
                this.altsList.elementClicked(selectedSlot2, false, 0, 0);
                break;
            case 209:
                this.altsList.represented.scrollBy(this.representedScreen.getHeight() - 100);
                break;
        }
        this.representedScreen.superKeyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void handleMouseInput() {
        this.representedScreen.superHandleMouseInput();
        this.altsList.represented.handleMouseInput();
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        this.searchField.mouseClicked(i, i2, i3);
        this.representedScreen.superMouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        this.searchField.updateCursorCounter();
    }

    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList.class */
    private class GuiList extends WrappedGuiSlot {
        private List accounts;
        private int selectedSlot;
        final GuiAltManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        GuiList(GuiAltManager guiAltManager, IGuiScreen iGuiScreen) {
            super(MinecraftInstance.f157mc, iGuiScreen.getWidth(), iGuiScreen.getHeight(), 40, iGuiScreen.getHeight() - 40, 30);
            this.this$0 = guiAltManager;
            updateAccounts(null);
        }

        private void updateAccounts(String str) {
            if (str == null || str.isEmpty()) {
                this.accounts = LiquidBounce.fileManager.accountsConfig.getAccounts();
                return;
            }
            String lowerCase = str.toLowerCase();
            this.accounts = new ArrayList();
            for (MinecraftAccount minecraftAccount : LiquidBounce.fileManager.accountsConfig.getAccounts()) {
                if ((minecraftAccount.getName() != null && minecraftAccount.getName().toLowerCase().contains(lowerCase)) || (minecraftAccount.getAccountName() != null && minecraftAccount.getAccountName().toLowerCase().contains(lowerCase))) {
                    this.accounts.add(minecraftAccount);
                }
            }
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public boolean isSelected(int i) {
            return this.selectedSlot == i;
        }

        int getSelectedSlot() {
            if (this.selectedSlot > this.accounts.size()) {
                this.selectedSlot = -1;
            }
            return this.selectedSlot;
        }

        public void setSelectedSlot(int i) {
            this.selectedSlot = i;
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public int getSize() {
            return this.accounts.size();
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public void elementClicked(int i, boolean z, int i2, int i3) {
            this.selectedSlot = i;
            if (z) {
                if (this.this$0.altsList.getSelectedSlot() != -1 && this.this$0.altsList.getSelectedSlot() < this.this$0.altsList.getSize() && this.this$0.loginButton.getEnabled()) {
                    this.this$0.loginButton.setEnabled(false);
                    this.this$0.randomButton.setEnabled(false);
                    new Thread(this::lambda$elementClicked$0, "AltManagerLogin").start();
                    return;
                }
                this.this$0.status = "\u00a7cSelect an account.";
            }
        }

        private void lambda$elementClicked$0() {
            MinecraftAccount minecraftAccount = (MinecraftAccount) this.accounts.get(this.this$0.altsList.getSelectedSlot());
            this.this$0.status = "\u00a7aLogging in...";
            this.this$0.status = "\u00a7c" + GuiAltManager.login(minecraftAccount);
            this.this$0.loginButton.setEnabled(true);
            this.this$0.randomButton.setEnabled(true);
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public void drawSlot(int i, int i2, int i3, int i4, int i5, int i6) {
            MinecraftAccount minecraftAccount = (MinecraftAccount) this.accounts.get(i);
            Fonts.font40.drawCenteredString(minecraftAccount.getAccountName() == null ? minecraftAccount.getName() : minecraftAccount.getAccountName(), this.this$0.representedScreen.getWidth() / 2, i3 + 2, Color.WHITE.getRGB(), true);
            Fonts.font40.drawCenteredString(minecraftAccount.isCracked() ? "Cracked" : minecraftAccount.getAccountName() == null ? "Premium" : minecraftAccount.getName(), this.this$0.representedScreen.getWidth() / 2, i3 + 15, minecraftAccount.isCracked() ? Color.GRAY.getRGB() : minecraftAccount.getAccountName() == null ? Color.GREEN.getRGB() : Color.LIGHT_GRAY.getRGB(), true);
        }
    }
}

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.Session;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiDisconnected.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiDisconnected.class */
public abstract class MixinGuiDisconnected extends MixinGuiScreen {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0");

    @Shadow
    private int field_175353_i;
    private GuiButton reconnectButton;
    private GuiSlider autoReconnectDelaySlider;
    private GuiButton forgeBypassButton;
    private int reconnectTimer;

    @Inject(method = {"initGui"}, m59at = {@InterfaceC0563At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        this.reconnectTimer = 0;
        List list = this.field_146292_n;
        GuiButton guiButton = new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 22, 98, 20, "Reconnect");
        this.reconnectButton = guiButton;
        list.add(guiButton);
        drawReconnectDelaySlider();
        this.field_146292_n.add(new GuiButton(3, (this.field_146294_l / 2) - 100, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 44, 98, 20, GuiTheAltening.Companion.getApiKey().isEmpty() ? "Random alt" : "New TheAltening alt"));
        this.field_146292_n.add(new GuiButton(4, (this.field_146294_l / 2) + 2, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 44, 98, 20, "Random username"));
        List list2 = this.field_146292_n;
        GuiButton guiButton2 = new GuiButton(5, (this.field_146294_l / 2) - 100, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 66, "Bypass AntiForge: " + (AntiForge.enabled ? "On" : "Off"));
        this.forgeBypassButton = guiButton2;
        list2.add(guiButton2);
        updateSliderText();
    }

    @Inject(method = {"actionPerformed"}, m59at = {@InterfaceC0563At("HEAD")})
    private void actionPerformed(GuiButton guiButton, CallbackInfo callbackInfo) {
        switch (guiButton.field_146127_k) {
            case 1:
                ServerUtils.connectToLastServer();
                break;
            case 3:
                if (!GuiTheAltening.Companion.getApiKey().isEmpty()) {
                    try {
                        AccountData accountData = new TheAltening(GuiTheAltening.Companion.getApiKey()).getAccountData();
                        GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                        YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                        yggdrasilUserAuthentication.setUsername(accountData.getToken());
                        yggdrasilUserAuthentication.setPassword(LiquidBounce.CLIENT_NAME);
                        yggdrasilUserAuthentication.logIn();
                        this.field_146297_k.field_71449_j = new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                        LiquidBounce.eventManager.callEvent(new SessionEvent());
                        ServerUtils.connectToLastServer();
                        break;
                    } catch (Throwable th) {
                        ClientUtils.getLogger().error("Failed to login into random account from TheAltening.", th);
                    }
                }
                List accounts = LiquidBounce.fileManager.accountsConfig.getAccounts();
                if (!accounts.isEmpty()) {
                    GuiAltManager.login((MinecraftAccount) accounts.get(new Random().nextInt(accounts.size())));
                    ServerUtils.connectToLastServer();
                    break;
                }
                break;
            case 4:
                LoginUtils.loginCracked(RandomUtils.randomString(RandomUtils.nextInt(5, 16)));
                ServerUtils.connectToLastServer();
                break;
            case 5:
                AntiForge.enabled = !AntiForge.enabled;
                this.forgeBypassButton.field_146126_j = "Bypass AntiForge: " + (AntiForge.enabled ? "On" : "Off");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                break;
        }
    }

    public void func_73876_c() {
        if (AutoReconnect.INSTANCE.isEnabled()) {
            this.reconnectTimer++;
            if (this.reconnectTimer > AutoReconnect.INSTANCE.getDelay() / 50) {
                ServerUtils.connectToLastServer();
            }
        }
    }

    @Inject(method = {"drawScreen"}, m59at = {@InterfaceC0563At("RETURN")})
    private void drawScreen(CallbackInfo callbackInfo) {
        if (AutoReconnect.INSTANCE.isEnabled()) {
            updateReconnectButton();
        }
    }

    private void drawReconnectDelaySlider() {
        List list = this.field_146292_n;
        GuiSlider guiSlider = new GuiSlider(2, (this.field_146294_l / 2) + 2, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 22, 98, 20, "AutoReconnect: ", "ms", 1000.0d, 60000.0d, AutoReconnect.INSTANCE.getDelay(), false, true, this::lambda$drawReconnectDelaySlider$0);
        this.autoReconnectDelaySlider = guiSlider;
        list.add(guiSlider);
    }

    private void lambda$drawReconnectDelaySlider$0(GuiSlider guiSlider) {
        AutoReconnect.INSTANCE.setDelay(guiSlider.getValueInt());
        this.reconnectTimer = 0;
        updateReconnectButton();
        updateSliderText();
    }

    private void updateSliderText() {
        if (this.autoReconnectDelaySlider == null) {
            return;
        }
        if (!AutoReconnect.INSTANCE.isEnabled()) {
            this.autoReconnectDelaySlider.field_146126_j = "AutoReconnect: Off";
        } else {
            this.autoReconnectDelaySlider.field_146126_j = "AutoReconnect: " + DECIMAL_FORMAT.format(AutoReconnect.INSTANCE.getDelay() / 1000.0d) + "s";
        }
    }

    private void updateReconnectButton() {
        if (this.reconnectButton != null) {
            this.reconnectButton.field_146126_j = "Reconnect" + (AutoReconnect.INSTANCE.isEnabled() ? " (" + ((AutoReconnect.INSTANCE.getDelay() / 1000) - (this.reconnectTimer / 20)) + ")" : "");
        }
    }
}

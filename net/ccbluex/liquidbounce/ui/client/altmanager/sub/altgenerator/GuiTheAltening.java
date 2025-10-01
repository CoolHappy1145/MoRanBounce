package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.altgenerator;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.mcleaks.MCLeaks;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdH\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\ufffd\ufffd \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0016J \u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0016J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012H\u0016J\b\u0010\u001d\u001a\u00020\u000eH\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006 "}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "apiKeyField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "generateButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "loginButton", "status", "", "tokenField", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening.class */
public final class GuiTheAltening extends WrappedGuiScreen {
    private IGuiButton loginButton;
    private IGuiButton generateButton;
    private IGuiTextField apiKeyField;
    private IGuiTextField tokenField;
    private String status;
    private final GuiAltManager prevGui;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static String apiKey = "";

    public GuiTheAltening(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = "";
    }

    public static final IGuiButton access$getLoginButton$p(GuiTheAltening guiTheAltening) {
        IGuiButton iGuiButton = guiTheAltening.loginButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }
        return iGuiButton;
    }

    public static final IGuiButton access$getGenerateButton$p(GuiTheAltening guiTheAltening) {
        IGuiButton iGuiButton = guiTheAltening.generateButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
        }
        return iGuiButton;
    }

    public static final IGuiTextField access$getTokenField$p(GuiTheAltening guiTheAltening) {
        IGuiTextField iGuiTextField = guiTheAltening.tokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        return iGuiTextField;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening$Companion;", "", "()V", "apiKey", "", "getApiKey", "()Ljava/lang/String;", "setApiKey", "(Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final String getApiKey() {
            return GuiTheAltening.apiKey;
        }

        public final void setApiKey(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            GuiTheAltening.apiKey = str;
        }
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.loginButton = MinecraftInstance.classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 100, 75, "Login");
        List buttonList = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButton = this.loginButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }
        buttonList.add(iGuiButton);
        this.generateButton = MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, 140, "Generate");
        List buttonList2 = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButton2 = this.generateButton;
        if (iGuiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
        }
        buttonList2.add(iGuiButton2);
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, (getRepresentedScreen().getWidth() / 2) - 100, getRepresentedScreen().getHeight() - 54, 98, 20, "Buy"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) + 2, getRepresentedScreen().getHeight() - 54, 98, 20, "Back"));
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IFontRenderer iFontRenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font40");
        this.tokenField = iClassProvider.createGuiTextField(666, iFontRenderer, (getRepresentedScreen().getWidth() / 2) - 100, 50, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        IGuiTextField iGuiTextField = this.tokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField.setFocused(true);
        IGuiTextField iGuiTextField2 = this.tokenField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField2.setMaxStringLength(Integer.MAX_VALUE);
        IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
        IFontRenderer iFontRenderer2 = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer2, "Fonts.font40");
        this.apiKeyField = iClassProvider2.createGuiPasswordField(1337, iFontRenderer2, (getRepresentedScreen().getWidth() / 2) - 100, 115, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        IGuiTextField iGuiTextField3 = this.apiKeyField;
        if (iGuiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        iGuiTextField3.setMaxStringLength(18);
        IGuiTextField iGuiTextField4 = this.apiKeyField;
        if (iGuiTextField4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        iGuiTextField4.setText(apiKey);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0f, 30.0f, getRepresentedScreen().getWidth() - 30.0f, getRepresentedScreen().getHeight() - 30.0f, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("TheAltening", getRepresentedScreen().getWidth() / 2.0f, 6.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, getRepresentedScreen().getWidth() / 2.0f, 18.0f, 16777215);
        IGuiTextField iGuiTextField = this.apiKeyField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        iGuiTextField.drawTextBox();
        IGuiTextField iGuiTextField2 = this.tokenField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField2.drawTextBox();
        Fonts.font40.drawCenteredString("\u00a77Token:", (getRepresentedScreen().getWidth() / 2.0f) - 84.0f, 40.0f, 16777215);
        Fonts.font40.drawCenteredString("\u00a77API-Key:", (getRepresentedScreen().getWidth() / 2.0f) - 78.0f, 105.0f, 16777215);
        Fonts.font40.drawCenteredString("\u00a77Use coupon code 'liquidbounce' for 20% off!", getRepresentedScreen().getWidth() / 2.0f, getRepresentedScreen().getHeight() - 65.0f, 16777215);
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getEnabled()) {
            switch (button.getId()) {
                case 0:
                    MinecraftInstance.f157mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
                    break;
                case 1:
                    IGuiButton iGuiButton = this.loginButton;
                    if (iGuiButton == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                    }
                    iGuiButton.setEnabled(false);
                    IGuiButton iGuiButton2 = this.generateButton;
                    if (iGuiButton2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("generateButton");
                    }
                    iGuiButton2.setEnabled(false);
                    IGuiTextField iGuiTextField = this.apiKeyField;
                    if (iGuiTextField == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
                    }
                    apiKey = iGuiTextField.getText();
                    TheAltening.Asynchronous asynchronous = new TheAltening.Asynchronous(new TheAltening(apiKey));
                    this.status = "\u00a7cGenerating account...";
                    asynchronous.getAccountData().thenAccept(new Consumer(this) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening.actionPerformed.1
                        final GuiTheAltening this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public void accept(Object obj) {
                            accept((AccountData) obj);
                        }

                        public final void accept(AccountData account) {
                            GuiTheAltening guiTheAltening;
                            String str;
                            GuiTheAltening guiTheAltening2 = this.this$0;
                            StringBuilder sbAppend = new StringBuilder().append("\u00a7aGenerated account: \u00a7b\u00a7l");
                            Intrinsics.checkExpressionValueIsNotNull(account, "account");
                            guiTheAltening2.status = sbAppend.append(account.getUsername()).toString();
                            try {
                                this.this$0.status = "\u00a7cSwitching Alt Service...";
                                GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                                this.this$0.status = "\u00a7cLogging in...";
                                YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                                yggdrasilUserAuthentication.setUsername(account.getToken());
                                yggdrasilUserAuthentication.setPassword(LiquidBounce.CLIENT_NAME);
                                GuiTheAltening guiTheAltening3 = this.this$0;
                                try {
                                    guiTheAltening = guiTheAltening3;
                                    yggdrasilUserAuthentication.logIn();
                                    IMinecraft iMinecraft = MinecraftInstance.f157mc;
                                    IClassProvider iClassProvider = MinecraftInstance.classProvider;
                                    GameProfile selectedProfile = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile, "yggdrasilUserAuthentication.selectedProfile");
                                    String name = selectedProfile.getName();
                                    Intrinsics.checkExpressionValueIsNotNull(name, "yggdrasilUserAuthentication.selectedProfile.name");
                                    GameProfile selectedProfile2 = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile2, "yggdrasilUserAuthenticat\u2026         .selectedProfile");
                                    String string = selectedProfile2.getId().toString();
                                    Intrinsics.checkExpressionValueIsNotNull(string, "yggdrasilUserAuthenticat\u2026ctedProfile.id.toString()");
                                    String authenticatedToken = yggdrasilUserAuthentication.getAuthenticatedToken();
                                    Intrinsics.checkExpressionValueIsNotNull(authenticatedToken, "yggdrasilUserAuthentication.authenticatedToken");
                                    iMinecraft.setSession(iClassProvider.createSession(name, string, authenticatedToken, "mojang"));
                                    LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                                    MCLeaks.remove();
                                    GuiAltManager guiAltManager = this.this$0.prevGui;
                                    StringBuilder sbAppend2 = new StringBuilder().append("\u00a7aYour name is now \u00a7b\u00a7l");
                                    GameProfile selectedProfile3 = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile3, "yggdrasilUserAuthentication.selectedProfile");
                                    guiAltManager.status = sbAppend2.append(selectedProfile3.getName()).append("\u00a7c.").toString();
                                    MinecraftInstance.f157mc.displayGuiScreen(this.this$0.prevGui.getRepresentedScreen());
                                    str = "";
                                } catch (AuthenticationException e) {
                                    guiTheAltening = guiTheAltening3;
                                    GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                                    ClientUtils.getLogger().error("Failed to login.", e);
                                    str = "\u00a7cFailed to login: " + e.getMessage();
                                }
                                guiTheAltening.status = str;
                            } catch (Throwable th) {
                                this.this$0.status = "\u00a7cFailed to login. Unknown error.";
                                ClientUtils.getLogger().error("Failed to login.", th);
                            }
                            GuiTheAltening.access$getLoginButton$p(this.this$0).setEnabled(true);
                            GuiTheAltening.access$getGenerateButton$p(this.this$0).setEnabled(true);
                        }
                    }).handle((BiFunction<? super Void, Throwable, ? extends U>) new BiFunction(this) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening.actionPerformed.2
                        final GuiTheAltening this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // java.util.function.BiFunction
                        public Object apply(Object obj, Object obj2) {
                            apply((Void) obj, (Throwable) obj2);
                            return Unit.INSTANCE;
                        }

                        public final void apply(Void r5, Throwable th) {
                            this.this$0.status = "\u00a7cFailed to generate account.";
                            ClientUtils.getLogger().error("Failed to generate account.", th);
                        }
                    }).whenComplete((BiConsumer<? super U, ? super Throwable>) new BiConsumer(this) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening.actionPerformed.3
                        final GuiTheAltening this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // java.util.function.BiConsumer
                        public void accept(Object obj, Object obj2) {
                            accept((Unit) obj, (Throwable) obj2);
                        }

                        public final void accept(Unit unit, Throwable th) {
                            GuiTheAltening.access$getLoginButton$p(this.this$0).setEnabled(true);
                            GuiTheAltening.access$getGenerateButton$p(this.this$0).setEnabled(true);
                        }
                    });
                    break;
                case 2:
                    IGuiButton iGuiButton3 = this.loginButton;
                    if (iGuiButton3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                    }
                    iGuiButton3.setEnabled(false);
                    IGuiButton iGuiButton4 = this.generateButton;
                    if (iGuiButton4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("generateButton");
                    }
                    iGuiButton4.setEnabled(false);
                    new Thread(new Runnable(this) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening.actionPerformed.4
                        final GuiTheAltening this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            GuiTheAltening guiTheAltening;
                            String string;
                            try {
                                this.this$0.status = "\u00a7cSwitching Alt Service...";
                                GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                                this.this$0.status = "\u00a7cLogging in...";
                                YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                                yggdrasilUserAuthentication.setUsername(GuiTheAltening.access$getTokenField$p(this.this$0).getText());
                                yggdrasilUserAuthentication.setPassword(LiquidBounce.CLIENT_NAME);
                                GuiTheAltening guiTheAltening2 = this.this$0;
                                try {
                                    guiTheAltening = guiTheAltening2;
                                    yggdrasilUserAuthentication.logIn();
                                    IMinecraft iMinecraft = MinecraftInstance.f157mc;
                                    IClassProvider iClassProvider = MinecraftInstance.classProvider;
                                    GameProfile selectedProfile = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile, "yggdrasilUserAuthentication.selectedProfile");
                                    String name = selectedProfile.getName();
                                    Intrinsics.checkExpressionValueIsNotNull(name, "yggdrasilUserAuthentication.selectedProfile.name");
                                    GameProfile selectedProfile2 = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile2, "yggdrasilUserAuthenticat\u2026         .selectedProfile");
                                    String string2 = selectedProfile2.getId().toString();
                                    Intrinsics.checkExpressionValueIsNotNull(string2, "yggdrasilUserAuthenticat\u2026ctedProfile.id.toString()");
                                    String authenticatedToken = yggdrasilUserAuthentication.getAuthenticatedToken();
                                    Intrinsics.checkExpressionValueIsNotNull(authenticatedToken, "yggdrasilUserAuthentication.authenticatedToken");
                                    iMinecraft.setSession(iClassProvider.createSession(name, string2, authenticatedToken, "mojang"));
                                    LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                                    MCLeaks.remove();
                                    GuiAltManager guiAltManager = this.this$0.prevGui;
                                    StringBuilder sbAppend = new StringBuilder().append("\u00a7aYour name is now \u00a7b\u00a7l");
                                    GameProfile selectedProfile3 = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile3, "yggdrasilUserAuthentication.selectedProfile");
                                    guiAltManager.status = sbAppend.append(selectedProfile3.getName()).append("\u00a7c.").toString();
                                    MinecraftInstance.f157mc.displayGuiScreen(this.this$0.prevGui.getRepresentedScreen());
                                    StringBuilder sbAppend2 = new StringBuilder().append("\u00a7aYour name is now \u00a7b\u00a7l");
                                    GameProfile selectedProfile4 = yggdrasilUserAuthentication.getSelectedProfile();
                                    Intrinsics.checkExpressionValueIsNotNull(selectedProfile4, "yggdrasilUserAuthentication.selectedProfile");
                                    string = sbAppend2.append(selectedProfile4.getName()).append("\u00a7c.").toString();
                                } catch (AuthenticationException e) {
                                    guiTheAltening = guiTheAltening2;
                                    GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                                    ClientUtils.getLogger().error("Failed to login.", e);
                                    string = "\u00a7cFailed to login: " + e.getMessage();
                                }
                                guiTheAltening.status = string;
                            } catch (Throwable th) {
                                ClientUtils.getLogger().error("Failed to login.", th);
                                this.this$0.status = "\u00a7cFailed to login. Unknown error.";
                            }
                            GuiTheAltening.access$getLoginButton$p(this.this$0).setEnabled(true);
                            GuiTheAltening.access$getGenerateButton$p(this.this$0).setEnabled(true);
                        }
                    }).start();
                    break;
                case 3:
                    MiscUtils.showURL("https://thealtening.com/?ref=liquidbounce");
                    break;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
            return;
        }
        IGuiTextField iGuiTextField = this.apiKeyField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        if (iGuiTextField.isFocused()) {
            IGuiTextField iGuiTextField2 = this.apiKeyField;
            if (iGuiTextField2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            }
            iGuiTextField2.textboxKeyTyped(c, i);
        }
        IGuiTextField iGuiTextField3 = this.tokenField;
        if (iGuiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        if (iGuiTextField3.isFocused()) {
            IGuiTextField iGuiTextField4 = this.tokenField;
            if (iGuiTextField4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }
            iGuiTextField4.textboxKeyTyped(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        IGuiTextField iGuiTextField = this.apiKeyField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        iGuiTextField.mouseClicked(i, i2, i3);
        IGuiTextField iGuiTextField2 = this.tokenField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField2.mouseClicked(i, i2, i3);
        super.mouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        IGuiTextField iGuiTextField = this.apiKeyField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        iGuiTextField.updateCursorCounter();
        IGuiTextField iGuiTextField2 = this.tokenField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField2.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        IGuiTextField iGuiTextField = this.apiKeyField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }
        apiKey = iGuiTextField.getText();
    }
}

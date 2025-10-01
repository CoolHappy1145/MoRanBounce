package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub;

import com.thealtening.AltService;
import java.util.List;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.mcleaks.MCLeaks;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiSessionLogin;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "loginButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "sessionTokenField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "status", "", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiSessionLogin.class */
public final class GuiSessionLogin extends WrappedGuiScreen {
    private IGuiButton loginButton;
    private IGuiTextField sessionTokenField;
    private String status;
    private final GuiAltManager prevGui;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiSessionLogin$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[LoginUtils.LoginResult.values().length];

        static {
            $EnumSwitchMapping$0[LoginUtils.LoginResult.LOGGED.ordinal()] = 1;
            $EnumSwitchMapping$0[LoginUtils.LoginResult.FAILED_PARSE_TOKEN.ordinal()] = 2;
            $EnumSwitchMapping$0[LoginUtils.LoginResult.INVALID_ACCOUNT_DATA.ordinal()] = 3;
        }
    }

    public GuiSessionLogin(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = "";
    }

    public static final IGuiTextField access$getSessionTokenField$p(GuiSessionLogin guiSessionLogin) {
        IGuiTextField iGuiTextField = guiSessionLogin.sessionTokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        return iGuiTextField;
    }

    public static final IGuiButton access$getLoginButton$p(GuiSessionLogin guiSessionLogin) {
        IGuiButton iGuiButton = guiSessionLogin.loginButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }
        return iGuiButton;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.loginButton = MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 96, "Login");
        List buttonList = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButton = this.loginButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }
        buttonList.add(iGuiButton);
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 120, "Back"));
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IFontRenderer iFontRenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font40");
        this.sessionTokenField = iClassProvider.createGuiTextField(666, iFontRenderer, (getRepresentedScreen().getWidth() / 2) - 100, 80, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        IGuiTextField iGuiTextField = this.sessionTokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        iGuiTextField.setFocused(true);
        IGuiTextField iGuiTextField2 = this.sessionTokenField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        iGuiTextField2.setMaxStringLength(Integer.MAX_VALUE);
        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0f, 30.0f, getRepresentedScreen().getWidth() - 30.0f, getRepresentedScreen().getHeight() - 30.0f, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("Session Login", getRepresentedScreen().getWidth() / 2.0f, 36.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 4.0f) + 80.0f, 16777215);
        IGuiTextField iGuiTextField = this.sessionTokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        iGuiTextField.drawTextBox();
        Fonts.font40.drawCenteredString("\u00a77Session Token:", (getRepresentedScreen().getWidth() / 2.0f) - 65.0f, 66.0f, 16777215);
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
                    this.status = "\u00a7aLogging in...";
                    ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiSessionLogin.actionPerformed.1
                        final GuiSessionLogin this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                            this.this$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public Object invoke() throws IllegalArgumentException {
                            m1687invoke();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: collision with other method in class */
                        public final void m1687invoke() throws IllegalArgumentException {
                            String str;
                            LoginUtils.LoginResult loginResultLoginSessionId = LoginUtils.loginSessionId(GuiSessionLogin.access$getSessionTokenField$p(this.this$0).getText());
                            GuiSessionLogin guiSessionLogin = this.this$0;
                            switch (WhenMappings.$EnumSwitchMapping$0[loginResultLoginSessionId.ordinal()]) {
                                case 1:
                                    AltService altService = GuiAltManager.altService;
                                    Intrinsics.checkExpressionValueIsNotNull(altService, "GuiAltManager.altService");
                                    if (altService.getCurrentService() != AltService.EnumAltService.MOJANG) {
                                        try {
                                            guiSessionLogin = guiSessionLogin;
                                            GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                                        } catch (IllegalAccessException e) {
                                            guiSessionLogin = guiSessionLogin;
                                            ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", e);
                                        } catch (NoSuchFieldException e2) {
                                            guiSessionLogin = guiSessionLogin;
                                            ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", e2);
                                        }
                                    }
                                    MCLeaks.remove();
                                    str = "\u00a7cYour name is now \u00a7f\u00a7l" + MinecraftInstance.f157mc.getSession().getUsername() + "\u00a7c";
                                    break;
                                case 2:
                                    str = "\u00a7cFailed to parse Session ID!";
                                    break;
                                case 3:
                                    str = "\u00a7cInvalid Session ID!";
                                    break;
                                default:
                                    str = "";
                                    break;
                            }
                            guiSessionLogin.status = str;
                            GuiSessionLogin.access$getLoginButton$p(this.this$0).setEnabled(true);
                        }
                    }, 31, null);
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
        IGuiTextField iGuiTextField = this.sessionTokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        if (iGuiTextField.isFocused()) {
            IGuiTextField iGuiTextField2 = this.sessionTokenField;
            if (iGuiTextField2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            }
            iGuiTextField2.textboxKeyTyped(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        IGuiTextField iGuiTextField = this.sessionTokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        iGuiTextField.mouseClicked(i, i2, i3);
        super.mouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        IGuiTextField iGuiTextField = this.sessionTokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        iGuiTextField.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}

package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.altgenerator;

import com.thealtening.AltService;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
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
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.mcleaks.Callback;
import net.mcleaks.MCLeaks;
import net.mcleaks.RedeemResponse;
import net.mcleaks.Session;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J \u0010\u0018\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000fH\u0016J\b\u0010\u001a\u001a\u00020\nH\u0016J\b\u0010\u001b\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001c"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiMCLeaks;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "status", "", "tokenField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiMCLeaks.class */
public final class GuiMCLeaks extends WrappedGuiScreen {
    private IGuiTextField tokenField;
    private String status;
    private final GuiAltManager prevGui;

    public GuiMCLeaks(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    public void updateScreen() {
        IGuiTextField iGuiTextField = this.tokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField.updateCursorCounter();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        if (MCLeaks.isAltActive()) {
            StringBuilder sbAppend = new StringBuilder().append("\u00a7aToken active. Using \u00a79");
            Session session = MCLeaks.getSession();
            Intrinsics.checkExpressionValueIsNotNull(session, "MCLeaks.getSession()");
            this.status = sbAppend.append(session.getUsername()).append("\u00a7a to login!").toString();
        }
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 65, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20, "Login"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 100, getRepresentedScreen().getHeight() - 54, 98, 20, "Get Token"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, (getRepresentedScreen().getWidth() / 2) + 2, getRepresentedScreen().getHeight() - 54, 98, 20, "Back"));
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IFontRenderer iFontRenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font40");
        this.tokenField = iClassProvider.createGuiTextField(0, iFontRenderer, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 40, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        IGuiTextField iGuiTextField = this.tokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField.setFocused(true);
        IGuiTextField iGuiTextField2 = this.tokenField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField2.setMaxStringLength(16);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getEnabled()) {
            switch (button.getId()) {
                case 1:
                    IGuiTextField iGuiTextField = this.tokenField;
                    if (iGuiTextField == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                    }
                    if (iGuiTextField.getText().length() != 16) {
                        this.status = "\u00a7cThe token has to be 16 characters long!";
                        break;
                    } else {
                        button.setEnabled(false);
                        button.setDisplayString("Please wait ...");
                        IGuiTextField iGuiTextField2 = this.tokenField;
                        if (iGuiTextField2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                        }
                        MCLeaks.redeem(iGuiTextField2.getText(), new Callback(this, button) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiMCLeaks.actionPerformed.1
                            final GuiMCLeaks this$0;
                            final IGuiButton $button;

                            {
                                this.this$0 = this;
                                this.$button = button;
                            }

                            @Override // net.mcleaks.Callback
                            public final void done(Object obj) {
                                if (obj instanceof String) {
                                    this.this$0.status = "\u00a7c" + obj;
                                    this.$button.setEnabled(true);
                                    this.$button.setDisplayString("Login");
                                    return;
                                }
                                if (obj == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type net.mcleaks.RedeemResponse");
                                }
                                RedeemResponse redeemResponse = (RedeemResponse) obj;
                                MCLeaks.refresh(new Session(redeemResponse.getUsername(), redeemResponse.getToken()));
                                try {
                                    GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                                } catch (Exception e) {
                                    ClientUtils.getLogger().error("Failed to change alt service to Mojang.", e);
                                }
                                this.this$0.status = "\u00a7aYour token was redeemed successfully!";
                                this.$button.setEnabled(true);
                                this.$button.setDisplayString("Login");
                                this.this$0.prevGui.status = this.this$0.status;
                                MinecraftInstance.f157mc.displayGuiScreen(this.this$0.prevGui.getRepresentedScreen());
                            }
                        });
                        break;
                    }
                case 2:
                    MiscUtils.showURL("https://mcleaks.net/");
                    break;
                case 3:
                    MinecraftInstance.f157mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
                    break;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        switch (i) {
            case 1:
                MinecraftInstance.f157mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
                break;
            case OPCode.EXACTN_IC /* 15 */:
                IGuiTextField iGuiTextField = this.tokenField;
                if (iGuiTextField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                }
                IGuiTextField iGuiTextField2 = this.tokenField;
                if (iGuiTextField2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                }
                iGuiTextField.setFocused(!iGuiTextField2.isFocused());
                break;
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
            case 156:
                actionPerformed((IGuiButton) getRepresentedScreen().getButtonList().get(1));
                break;
            default:
                IGuiTextField iGuiTextField3 = this.tokenField;
                if (iGuiTextField3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                }
                iGuiTextField3.textboxKeyTyped(c, i);
                break;
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        super.mouseClicked(i, i2, i3);
        IGuiTextField iGuiTextField = this.tokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField.mouseClicked(i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0f, 30.0f, getRepresentedScreen().getWidth() - 30.0f, getRepresentedScreen().getHeight() - 30.0f, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("MCLeaks", getRepresentedScreen().getWidth() / 2.0f, 6.0f, 16777215);
        Fonts.font40.drawString("Token:", (getRepresentedScreen().getWidth() / 2.0f) - 100.0f, (getRepresentedScreen().getHeight() / 4.0f) + 30.0f, 10526880);
        String str = this.status;
        if (str != null) {
            Fonts.font40.drawCenteredString(str, getRepresentedScreen().getWidth() / 2.0f, 18.0f, 16777215);
        }
        IGuiTextField iGuiTextField = this.tokenField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }
        iGuiTextField.drawTextBox();
        super.drawScreen(i, i2, f);
    }
}

package net.ccbluex.liquidbounce.p005ui.client.altmanager.sub;

import java.util.List;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\ufffd\ufffd \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "stateButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "status", "", "transferCodeField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape.class */
public final class GuiDonatorCape extends WrappedGuiScreen {
    private IGuiButton stateButton;
    private IGuiTextField transferCodeField;
    private String status;
    private final GuiAltManager prevGui;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static String transferCode = "";
    private static boolean capeEnabled = true;

    public GuiDonatorCape(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = "";
    }

    public static final IGuiTextField access$getTransferCodeField$p(GuiDonatorCape guiDonatorCape) {
        IGuiTextField iGuiTextField = guiDonatorCape.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        return iGuiTextField;
    }

    public static final IGuiButton access$getStateButton$p(GuiDonatorCape guiDonatorCape) {
        IGuiButton iGuiButton = guiDonatorCape.stateButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stateButton");
        }
        return iGuiButton;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape$Companion;", "", "()V", "capeEnabled", "", "getCapeEnabled", "()Z", "setCapeEnabled", "(Z)V", "transferCode", "", "getTransferCode", "()Ljava/lang/String;", "setTransferCode", "(Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final String getTransferCode() {
            return GuiDonatorCape.transferCode;
        }

        public final void setTransferCode(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            GuiDonatorCape.transferCode = str;
        }

        public final boolean getCapeEnabled() {
            return GuiDonatorCape.capeEnabled;
        }

        public final void setCapeEnabled(boolean z) {
            GuiDonatorCape.capeEnabled = z;
        }
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.stateButton = MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, 105, "Disable Cape");
        List buttonList = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButton = this.stateButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stateButton");
        }
        buttonList.add(iGuiButton);
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 96, "Donate to get Cape"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 120, "Back"));
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IFontRenderer iFontRenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font40");
        this.transferCodeField = iClassProvider.createGuiPasswordField(666, iFontRenderer, (getRepresentedScreen().getWidth() / 2) - 100, 80, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        IGuiTextField iGuiTextField = this.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        iGuiTextField.setFocused(true);
        IGuiTextField iGuiTextField2 = this.transferCodeField;
        if (iGuiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        iGuiTextField2.setMaxStringLength(Integer.MAX_VALUE);
        IGuiTextField iGuiTextField3 = this.transferCodeField;
        if (iGuiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        iGuiTextField3.setText(transferCode);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        String str;
        getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0f, 30.0f, getRepresentedScreen().getWidth() - 30.0f, getRepresentedScreen().getHeight() - 30.0f, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("Donator Cape", getRepresentedScreen().getWidth() / 2.0f, 36.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 4.0f) + 80.0f, 16777215);
        IGuiTextField iGuiTextField = this.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        iGuiTextField.drawTextBox();
        Fonts.font40.drawCenteredString("\u00a77Transfer Code:", (getRepresentedScreen().getWidth() / 2.0f) - 65.0f, 66.0f, 16777215);
        IGuiButton iGuiButton = this.stateButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stateButton");
        }
        if (capeEnabled) {
            str = "Disable Cape";
        } else {
            str = "Enable Cape";
        }
        iGuiButton.setDisplayString(str);
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
                    IGuiButton iGuiButton = this.stateButton;
                    if (iGuiButton == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("stateButton");
                    }
                    iGuiButton.setEnabled(false);
                    ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this) { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape.actionPerformed.1
                        final GuiDonatorCape this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                            this.this$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public Object invoke() {
                            m1685invoke();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: collision with other method in class */
                        public final void m1685invoke() {
                            HttpPut httpPut;
                            String str;
                            CloseableHttpClient closeableHttpClientCreateDefault = HttpClients.createDefault();
                            BasicHeader[] basicHeaderArr = {new BasicHeader("Content-Type", "application/json"), new BasicHeader("Authorization", GuiDonatorCape.access$getTransferCodeField$p(this.this$0).getText())};
                            if (GuiDonatorCape.Companion.getCapeEnabled()) {
                                httpPut = new HttpDelete("http://capes.liquidbounce.net/api/v1/cape/self");
                            } else {
                                httpPut = new HttpPut("http://capes.liquidbounce.net/api/v1/cape/self");
                            }
                            HttpRequestBase httpRequestBase = httpPut;
                            httpRequestBase.setHeaders(basicHeaderArr);
                            CloseableHttpResponse response = closeableHttpClientCreateDefault.execute(httpRequestBase);
                            Intrinsics.checkExpressionValueIsNotNull(response, "response");
                            StatusLine statusLine = response.getStatusLine();
                            Intrinsics.checkExpressionValueIsNotNull(statusLine, "response.statusLine");
                            int statusCode = statusLine.getStatusCode();
                            GuiDonatorCape guiDonatorCape = this.this$0;
                            if (statusCode == 204) {
                                GuiDonatorCape.Companion.setCapeEnabled(!GuiDonatorCape.Companion.getCapeEnabled());
                                if (GuiDonatorCape.Companion.getCapeEnabled()) {
                                    str = "\u00a7aSuccessfully enabled cape";
                                } else {
                                    str = "\u00a7aSuccessfully disabled cape";
                                }
                            } else {
                                str = "\u00a7cFailed to toggle cape (" + statusCode + ')';
                            }
                            guiDonatorCape.status = str;
                            GuiDonatorCape.access$getStateButton$p(this.this$0).setEnabled(true);
                        }
                    }, 31, null);
                    break;
                case 2:
                    MiscUtils.showURL("https://donate.liquidbounce.net");
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
        IGuiTextField iGuiTextField = this.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        if (iGuiTextField.isFocused()) {
            IGuiTextField iGuiTextField2 = this.transferCodeField;
            if (iGuiTextField2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
            }
            iGuiTextField2.textboxKeyTyped(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        IGuiTextField iGuiTextField = this.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        iGuiTextField.mouseClicked(i, i2, i3);
        super.mouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        IGuiTextField iGuiTextField = this.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        iGuiTextField.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        IGuiTextField iGuiTextField = this.transferCodeField;
        if (iGuiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }
        transferCode = iGuiTextField.getText();
    }
}

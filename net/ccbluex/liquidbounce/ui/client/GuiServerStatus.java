package net.ccbluex.liquidbounce.p005ui.client;

import com.google.gson.Gson;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\tH\u0016J\u0018\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\b\u0010\u0017\u001a\u00020\tH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiServerStatus;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "status", "Ljava/util/HashMap;", "", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "loadInformations", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiServerStatus.class */
public final class GuiServerStatus extends WrappedGuiScreen {
    private final HashMap status;
    private final IGuiScreen prevGui;

    public GuiServerStatus(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = new HashMap();
    }

    public void initGui() {
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 145, "Back"));
        ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this) { // from class: net.ccbluex.liquidbounce.ui.client.GuiServerStatus.initGui.1
            final GuiServerStatus this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                m1682invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: collision with other method in class */
            public final void m1682invoke() {
                this.this$0.loadInformations();
            }
        }, 31, null);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        int height = (getRepresentedScreen().getHeight() / 4) + 40;
        RenderUtils.drawRect((getRepresentedScreen().getWidth() / 2.0f) - 115.0f, height - 5.0f, (getRepresentedScreen().getWidth() / 2.0f) + 115.0f, (getRepresentedScreen().getHeight() / 4.0f) + 43.0f + (this.status.keySet().isEmpty() ? 10 : this.status.keySet().size() * Fonts.font40.getFontHeight()), Integer.MIN_VALUE);
        if (this.status.isEmpty()) {
            Color color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
            Fonts.font40.drawCenteredString("Loading...", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 4.0f) + 40.0f, color.getRGB());
        } else {
            for (String str : this.status.keySet()) {
                String str2 = (String) this.status.get(str);
                IFontRenderer iFontRenderer = Fonts.font40;
                StringBuilder sbAppend = new StringBuilder().append("\u00a7c\u00a7l").append(str).append(": ").append(StringsKt.equals(str2, "red", true) ? "\u00a7c" : StringsKt.equals(str2, "yellow", true) ? "\u00a7e" : "\u00a7a");
                String str3 = StringsKt.equals(str2, "red", true) ? "Offline" : StringsKt.equals(str2, "yellow", true) ? "Slow" : "Online";
                Color color2 = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(color2, "Color.WHITE");
                iFontRenderer.drawCenteredString(sbAppend.append(str3).toString(), getRepresentedScreen().getWidth() / 2.0f, height, color2.getRGB());
                height += Fonts.font40.getFontHeight();
            }
        }
        Fonts.fontBold180.drawCenteredString("Server Status", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 5.0f, 4673984, true);
        super.drawScreen(i, i2, f);
    }

    private final void loadInformations() {
        this.status.clear();
        Object objFromJson = new Gson().fromJson(HttpUtils.get("https://status.mojang.com/check"), List.class);
        if (objFromJson == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<kotlin.collections.Map<kotlin.String, kotlin.String>>");
        }
        Iterator it = ((List) objFromJson).iterator();
        while (it.hasNext()) {
            for (Map.Entry entry : ((Map) it.next()).entrySet()) {
                this.status.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getId() == 1) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(c, i);
        }
    }
}

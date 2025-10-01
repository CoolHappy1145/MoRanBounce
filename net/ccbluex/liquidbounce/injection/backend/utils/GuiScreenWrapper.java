package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.injection.backend.GuiButtonImpl;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\r\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\bH\u0016J\b\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0014J \u0010\u0019\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0014J \u0010\u001b\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fH\u0014J\b\u0010\u001d\u001a\u00020\bH\u0016J\u001e\u0010\u001e\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u001f\u001a\u00020\bJ\u0016\u0010 \u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fJ\u001e\u0010!\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fJ\u001e\u0010\"\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fJ\b\u0010#\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006$"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/utils/GuiScreenWrapper;", "Lnet/minecraft/client/gui/GuiScreen;", "wrapped", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;)V", "getWrapped", "()Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "doesGuiPauseGame", "", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "superDrawScreen", "superHandleMouseInput", "superKeyTyped", "superMouseClicked", "superMouseReleased", "updateScreen", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/GuiScreenWrapper.class */
public final class GuiScreenWrapper extends GuiScreen {

    @NotNull
    private final WrappedGuiScreen wrapped;

    @NotNull
    public final WrappedGuiScreen getWrapped() {
        return this.wrapped;
    }

    public GuiScreenWrapper(@NotNull WrappedGuiScreen wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    public void func_73863_a(int i, int i2, float f) {
        this.wrapped.drawScreen(i, i2, f);
    }

    public void func_73866_w_() {
        WrappedGuiScreen wrappedGuiScreen = this.wrapped;
    }

    protected void func_73864_a(int i, int i2, int i3) {
        this.wrapped.mouseClicked(i, i2, i3);
    }

    public void func_73876_c() {
        WrappedGuiScreen wrappedGuiScreen = this.wrapped;
    }

    public void func_146274_d() {
        this.wrapped.handleMouseInput();
    }

    protected void func_73869_a(char c, int i) {
        this.wrapped.keyTyped(c, i);
    }

    protected void func_146284_a(@Nullable GuiButton guiButton) {
        if (guiButton != null) {
            this.wrapped.actionPerformed(new GuiButtonImpl(guiButton));
        }
    }

    public void func_146281_b() {
        WrappedGuiScreen wrappedGuiScreen = this.wrapped;
    }

    protected void func_146286_b(int i, int i2, int i3) {
        this.wrapped.mouseReleased(i, i2, i3);
    }

    public boolean func_73868_f() {
        WrappedGuiScreen wrappedGuiScreen = this.wrapped;
        return false;
    }

    public final void superMouseReleased(int i, int i2, int i3) {
        super.func_146286_b(i, i2, i3);
    }

    public final void superKeyTyped(char c, int i) {
        super.func_73869_a(c, i);
    }

    public final void superHandleMouseInput() {
        super.func_146274_d();
    }

    public final void superMouseClicked(int i, int i2, int i3) {
        super.func_73864_a(i, i2, i3);
    }

    public final void superDrawScreen(int i, int i2, float f) {
        super.func_73863_a(i, i2, f);
    }
}

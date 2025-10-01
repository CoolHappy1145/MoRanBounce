package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiSlotWrapper;
import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot;
import net.minecraft.client.gui.GuiSlot;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J(\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0016J\b\u0010\u0019\u001a\u00020\u000eH\u0016J\u0018\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u0006H\u0016J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0006H\u0016J\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u0016H\u0016J\u0010\u0010!\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\""}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GuiSlotImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiSlot;", "wrapped", "Lnet/minecraft/client/gui/GuiSlot;", "(Lnet/minecraft/client/gui/GuiSlot;)V", "slotHeight", "", "getSlotHeight", "()I", "width", "getWidth", "getWrapped", "()Lnet/minecraft/client/gui/GuiSlot;", "drawScreen", "", "mouseX", "mouseY", "partialTicks", "", "elementClicked", "index", "doubleClick", "", "var3", "var4", "handleMouseInput", "registerScrollButtons", "down", "up", "scrollBy", PropertyDescriptor.VALUE, "setEnableScissor", "flag", "setListWidth", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GuiSlotImpl.class */
public final class GuiSlotImpl implements IGuiSlot {

    @NotNull
    private final GuiSlot wrapped;

    @NotNull
    public final GuiSlot getWrapped() {
        return this.wrapped;
    }

    public GuiSlotImpl(@NotNull GuiSlot wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public int getWidth() {
        return this.wrapped.field_148155_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public int getSlotHeight() {
        return this.wrapped.field_148149_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void scrollBy(int i) {
        this.wrapped.func_148145_f(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void registerScrollButtons(int i, int i2) {
        this.wrapped.func_148134_d(i, i2);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void drawScreen(int i, int i2, float f) {
        this.wrapped.func_148128_a(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void elementClicked(int i, boolean z, int i2, int i3) {
        GuiSlot guiSlot = this.wrapped;
        if (guiSlot == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiSlotWrapper");
        }
        ((GuiSlotWrapper) guiSlot).func_148144_a(i, z, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void handleMouseInput() {
        this.wrapped.func_178039_p();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void setListWidth(int i) {
        IMixinGuiSlot iMixinGuiSlot = this.wrapped;
        if (iMixinGuiSlot == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot");
        }
        iMixinGuiSlot.setListWidth(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot
    public void setEnableScissor(boolean z) {
        IMixinGuiSlot iMixinGuiSlot = this.wrapped;
        if (iMixinGuiSlot == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot");
        }
        iMixinGuiSlot.setEnableScissor(z);
    }
}

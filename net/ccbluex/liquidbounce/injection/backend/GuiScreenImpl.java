package net.ccbluex.liquidbounce.injection.backend;

import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiGameOver;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiChest;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiContainer;
import net.ccbluex.liquidbounce.api.util.WrappedMutableList;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdj\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0011H\u0016J\b\u0010#\u001a\u00020!H\u0016J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'H\u0096\u0002J \u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020!H\u0016J\u0018\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0011H\u0016J \u00102\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u00103\u001a\u00020\u0011H\u0016J \u00104\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u00105\u001a\u00020\u0011H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016\u00a8\u00066"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GuiScreenImpl;", "T", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/ccbluex/liquidbounce/injection/backend/GuiImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "wrapped", "(Lnet/minecraft/client/gui/GuiScreen;)V", "buttonList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "getButtonList", "()Ljava/util/List;", "fontRendererObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "getFontRendererObj", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", PropertyDescriptor.VALUE, "", "height", "getHeight", "()I", "setHeight", "(I)V", "width", "getWidth", "setWidth", "asGuiChest", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiChest;", "asGuiContainer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiContainer;", "asGuiGameOver", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiGameOver;", "drawBackground", "", "i", "drawDefaultBackground", "equals", "", "other", "", "superDrawScreen", "mouseX", "mouseY", "partialTicks", "", "superHandleMouseInput", "superKeyTyped", "typedChar", "", "keyCode", "superMouseClicked", "mouseButton", "superMouseReleased", "state", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GuiScreenImpl.class */
public class GuiScreenImpl extends GuiImpl implements IGuiScreen {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiScreenImpl(@NotNull GuiScreen wrapped) {
        super((Gui) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    @NotNull
    public IFontRenderer getFontRendererObj() {
        FontRenderer fontRenderer = getWrapped().field_146289_q;
        Intrinsics.checkExpressionValueIsNotNull(fontRenderer, "wrapped.fontRenderer");
        return new FontRendererImpl(fontRenderer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    @NotNull
    public List getButtonList() {
        return new WrappedMutableList(getWrapped().field_146292_n, GuiScreenImpl$buttonList$1.INSTANCE, GuiScreenImpl$buttonList$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    @NotNull
    public IGuiContainer asGuiContainer() {
        GuiContainer wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.inventory.GuiContainer");
        }
        return new GuiContainerImpl(wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    @NotNull
    public IGuiGameOver asGuiGameOver() {
        GuiGameOver wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.GuiGameOver");
        }
        return new GuiGameOverImpl(wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    @NotNull
    public IGuiChest asGuiChest() {
        GuiChest wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.inventory.GuiChest");
        }
        return new GuiChestImpl(wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void superMouseReleased(int i, int i2, int i3) {
        GuiScreenWrapper wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        }
        wrapped.superMouseReleased(i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void drawBackground(int i) {
        getWrapped().func_146278_c(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void drawDefaultBackground() {
        getWrapped().func_146276_q_();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void superKeyTyped(char c, int i) {
        GuiScreenWrapper wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        }
        wrapped.superKeyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void superHandleMouseInput() {
        GuiScreenWrapper wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        }
        wrapped.superHandleMouseInput();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void superMouseClicked(int i, int i2, int i3) {
        GuiScreenWrapper wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        }
        wrapped.superMouseClicked(i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public void superDrawScreen(int i, int i2, float f) {
        GuiScreenWrapper wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        }
        wrapped.superDrawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public int getHeight() {
        return getWrapped().field_146295_m;
    }

    public void setHeight(int i) {
        getWrapped().field_146295_m = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen
    public int getWidth() {
        return getWrapped().field_146294_l;
    }

    public void setWidth(int i) {
        getWrapped().field_146294_l = i;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof GuiScreenImpl) && Intrinsics.areEqual(((GuiScreenImpl) obj).getWrapped(), getWrapped());
    }
}

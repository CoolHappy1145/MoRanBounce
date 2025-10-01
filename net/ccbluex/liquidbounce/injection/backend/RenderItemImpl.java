package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.minecraft.client.renderer.RenderItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J \u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J(\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/RenderItemImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "wrapped", "Lnet/minecraft/client/renderer/RenderItem;", "(Lnet/minecraft/client/renderer/RenderItem;)V", "getWrapped", "()Lnet/minecraft/client/renderer/RenderItem;", PropertyDescriptor.VALUE, "", "zLevel", "getZLevel", "()F", "setZLevel", "(F)V", "equals", "", "other", "", "renderItemAndEffectIntoGUI", "", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "x", "", "y", "renderItemIntoGUI", "renderItemOverlays", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/RenderItemImpl.class */
public final class RenderItemImpl implements IRenderItem {

    @NotNull
    private final RenderItem wrapped;

    @NotNull
    public final RenderItem getWrapped() {
        return this.wrapped;
    }

    public RenderItemImpl(@NotNull RenderItem wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem
    public float getZLevel() {
        return this.wrapped.field_77023_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem
    public void setZLevel(float f) {
        this.wrapped.field_77023_b = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem
    public void renderItemAndEffectIntoGUI(@NotNull IItemStack stack, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        this.wrapped.func_180450_b(((ItemStackImpl) stack).getWrapped(), i, i2);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem
    public void renderItemIntoGUI(@NotNull IItemStack stack, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        this.wrapped.func_175042_a(((ItemStackImpl) stack).getWrapped(), i, i2);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem
    public void renderItemOverlays(@NotNull IFontRenderer fontRenderer, @NotNull IItemStack stack, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fontRenderer, "fontRenderer");
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        this.wrapped.func_175030_a(((FontRendererImpl) fontRenderer).getWrapped(), ((ItemStackImpl) stack).getWrapped(), i, i2);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof RenderItemImpl) && Intrinsics.areEqual(((RenderItemImpl) obj).wrapped, this.wrapped);
    }
}

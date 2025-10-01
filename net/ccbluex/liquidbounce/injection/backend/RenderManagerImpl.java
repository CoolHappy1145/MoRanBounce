package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0010\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010#\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J0\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\fH\u0016J \u0010.\u001a\u00020\u00062\u0006\u0010(\u001a\u00020/2\u0006\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\u0006H\u0016J8\u00102\u001a\u00020\u00062\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u00020\u00142\u0006\u00108\u001a\u00020\f2\u0006\u00109\u001a\u00020\fH\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR$\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u000e\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016R\u0014\u0010\u001b\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0016R\u0014\u0010\u001d\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0016R\u0014\u0010\u001f\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b!\u0010\"\u00a8\u0006:"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/RenderManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", "wrapped", "Lnet/minecraft/client/renderer/entity/RenderManager;", "(Lnet/minecraft/client/renderer/entity/RenderManager;)V", PropertyDescriptor.VALUE, "", "isRenderShadow", "()Z", "setRenderShadow", "(Z)V", "playerViewX", "", "getPlayerViewX", "()F", "playerViewY", "getPlayerViewY", "setPlayerViewY", "(F)V", "renderPosX", "", "getRenderPosX", "()D", "renderPosY", "getRenderPosY", "renderPosZ", "getRenderPosZ", "viewerPosX", "getViewerPosX", "viewerPosY", "getViewerPosY", "viewerPosZ", "getViewerPosZ", "getWrapped", "()Lnet/minecraft/client/renderer/entity/RenderManager;", "equals", "other", "", "renderEntityAt", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "x", "y", "z", "partialTicks", "renderEntityStatic", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "renderPartialTicks", "hideDebugBox", "renderEntityWithPosYaw", "entityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "d", "d1", "d2", "fl", "fl1", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/RenderManagerImpl.class */
public final class RenderManagerImpl implements IRenderManager {

    @NotNull
    private final RenderManager wrapped;

    @NotNull
    public final RenderManager getWrapped() {
        return this.wrapped;
    }

    public RenderManagerImpl(@NotNull RenderManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public boolean isRenderShadow() {
        return this.wrapped.func_178627_a();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public void setRenderShadow(boolean z) {
        this.wrapped.func_178633_a(z);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public double getViewerPosX() {
        return this.wrapped.field_78730_l;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public double getViewerPosY() {
        return this.wrapped.field_78731_m;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public double getViewerPosZ() {
        return this.wrapped.field_78728_n;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public float getPlayerViewX() {
        return this.wrapped.field_78732_j;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public float getPlayerViewY() {
        return this.wrapped.field_78735_i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public void setPlayerViewY(float f) {
        this.wrapped.func_178631_a(f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public double getRenderPosX() {
        return this.wrapped.field_78725_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public double getRenderPosY() {
        return this.wrapped.field_78726_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public double getRenderPosZ() {
        return this.wrapped.field_78723_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public boolean renderEntityStatic(@NotNull IEntity entity, float f, boolean z) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        this.wrapped.func_188388_a(((EntityImpl) entity).getWrapped(), f, z);
        return true;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public void renderEntityAt(@NotNull ITileEntity entity, double d, double d2, double d3, float f) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        TileEntityRendererDispatcher.field_147556_a.func_147549_a(((TileEntityImpl) entity).getWrapped(), d, d2, d3, f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager
    public boolean renderEntityWithPosYaw(@NotNull IEntityLivingBase entityLivingBase, double d, double d2, double d3, float f, float f2) {
        Intrinsics.checkParameterIsNotNull(entityLivingBase, "entityLivingBase");
        this.wrapped.func_188391_a((EntityLivingBase) ((EntityLivingBaseImpl) entityLivingBase).getWrapped(), d, d2, d3, f, f2, true);
        return true;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof RenderManagerImpl) && Intrinsics.areEqual(((RenderManagerImpl) obj).wrapped, this.wrapped);
    }
}

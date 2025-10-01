package net.ccbluex.liquidbounce.api.minecraft.renderer.entity;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd@\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\ufffd\ufffd2\u00020\u0001J0\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\bH&J \u0010%\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020&2\u0006\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u0003H&J8\u0010)\u001a\u00020\u00032\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u00102\u0006\u0010-\u001a\u00020\u00102\u0006\u0010.\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u00020\bH&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\n\"\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0012\u0010\u0015\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0012R\u0012\u0010\u0017\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0012R\u0012\u0010\u0019\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0012R\u0012\u0010\u001b\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012\u00a8\u00061"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", "", "isRenderShadow", "", "()Z", "setRenderShadow", "(Z)V", "playerViewX", "", "getPlayerViewX", "()F", "playerViewY", "getPlayerViewY", "setPlayerViewY", "(F)V", "renderPosX", "", "getRenderPosX", "()D", "renderPosY", "getRenderPosY", "renderPosZ", "getRenderPosZ", "viewerPosX", "getViewerPosX", "viewerPosY", "getViewerPosY", "viewerPosZ", "getViewerPosZ", "renderEntityAt", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "x", "y", "z", "partialTicks", "renderEntityStatic", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "renderPartialTicks", "hideDebugBox", "renderEntityWithPosYaw", "entityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "d", "d1", "d2", "fl", "fl1", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager.class */
public interface IRenderManager {
    boolean isRenderShadow();

    void setRenderShadow(boolean z);

    double getViewerPosX();

    double getViewerPosY();

    double getViewerPosZ();

    float getPlayerViewX();

    float getPlayerViewY();

    void setPlayerViewY(float f);

    double getRenderPosX();

    double getRenderPosY();

    double getRenderPosZ();

    boolean renderEntityStatic(@NotNull IEntity iEntity, float f, boolean z);

    void renderEntityAt(@NotNull ITileEntity iTileEntity, double d, double d2, double d3, float f);

    boolean renderEntityWithPosYaw(@NotNull IEntityLivingBase iEntityLivingBase, double d, double d2, double d3, float f, float f2);
}

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Projectiles;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorRedValue", "interpolateHSB", "Ljava/awt/Color;", "startColor", "endColor", "process", "", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Projectiles", description = "Allows you to see where arrows will land.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Projectiles.class */
public final class Projectiles extends Module {
    private final ListValue colorMode = new ListValue("Color", new String[]{"Custom", "BowPower", "Rainbow"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        IWorldClient theWorld;
        IItemStack heldItem;
        float f;
        float f2;
        double d;
        double d2;
        double d3;
        IMovingObjectPosition iMovingObjectPositionCalculateIntercept;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || (theWorld = MinecraftInstance.f157mc.getTheWorld()) == null || (heldItem = thePlayer.getHeldItem()) == null) {
            return;
        }
        IItem item = heldItem.getItem();
        IRenderManager renderManager = MinecraftInstance.f157mc.getRenderManager();
        boolean z = false;
        float f3 = 1.5f;
        float f4 = 0.99f;
        if (MinecraftInstance.classProvider.isItemBow(item)) {
            if (!thePlayer.isUsingItem()) {
                return;
            }
            z = true;
            f = 0.05f;
            f2 = 0.3f;
            float itemInUseDuration = thePlayer.getItemInUseDuration() / 20.0f;
            float f5 = ((itemInUseDuration * itemInUseDuration) + (itemInUseDuration * 2.0f)) / 3.0f;
            if (f5 < 0.1f) {
                return;
            }
            if (f5 > 1.0f) {
                f5 = 1.0f;
            }
            f3 = f5 * 3.0f;
        } else if (MinecraftInstance.classProvider.isItemFishingRod(item)) {
            f = 0.04f;
            f2 = 0.25f;
            f4 = 0.92f;
        } else if (MinecraftInstance.classProvider.isItemPotion(item) && heldItem.isSplash()) {
            f = 0.05f;
            f2 = 0.25f;
            f3 = 0.5f;
        } else {
            if (!MinecraftInstance.classProvider.isItemSnowball(item) && !MinecraftInstance.classProvider.isItemEnderPearl(item) && !MinecraftInstance.classProvider.isItemEgg(item)) {
                return;
            }
            f = 0.03f;
            f2 = 0.25f;
        }
        float yaw = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getYaw() : thePlayer.getRotationYaw();
        float pitch = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getPitch() : thePlayer.getRotationPitch();
        float f6 = (yaw / 180.0f) * 3.1415927f;
        float f7 = (pitch / 180.0f) * 3.1415927f;
        double renderPosX = renderManager.getRenderPosX() - (((float) Math.cos(f6)) * 0.16f);
        double renderPosY = (renderManager.getRenderPosY() + thePlayer.getEyeHeight()) - 0.10000000149011612d;
        double renderPosZ = renderManager.getRenderPosZ() - (((float) Math.sin(f6)) * 0.16f);
        double dCos = (-((float) Math.sin(f6))) * ((float) Math.cos(f7)) * (z ? 1.0d : 0.4d);
        double d4 = (-((float) Math.sin(((pitch + ((MinecraftInstance.classProvider.isItemPotion(item) && heldItem.isSplash()) ? -20 : 0)) / 180.0f) * 3.1415927f))) * (z ? 1.0d : 0.4d);
        double dCos2 = ((float) Math.cos(f6)) * ((float) Math.cos(f7)) * (z ? 1.0d : 0.4d);
        double dSqrt = Math.sqrt((dCos * dCos) + (d4 * d4) + (dCos2 * dCos2));
        double d5 = (dCos / dSqrt) * f3;
        double d6 = (d4 / dSqrt) * f3;
        double d7 = (dCos2 / dSqrt) * f3;
        IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = (IMovingObjectPosition) null;
        boolean z2 = false;
        boolean z3 = false;
        ITessellator tessellatorInstance = MinecraftInstance.classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        GL11.glDepthMask(false);
        RenderUtils.enableGlCap(new int[]{SGL.GL_BLEND, SGL.GL_LINE_SMOOTH});
        RenderUtils.disableGlCap(new int[]{SGL.GL_DEPTH_TEST, 3008, SGL.GL_TEXTURE_2D});
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        String str = (String) this.colorMode.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -2055857797:
                if (lowerCase.equals("bowpower")) {
                    Color color = Color.RED;
                    Intrinsics.checkExpressionValueIsNotNull(color, "Color.RED");
                    Color color2 = Color.GREEN;
                    Intrinsics.checkExpressionValueIsNotNull(color2, "Color.GREEN");
                    RenderUtils.glColor(interpolateHSB(color, color2, (f3 / 30.0f) * 10.0f));
                    break;
                }
                break;
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    RenderUtils.glColor(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 255));
                    break;
                }
                break;
            case 973576630:
                if (lowerCase.equals("rainbow")) {
                    RenderUtils.glColor(ColorUtils.rainbow());
                    break;
                }
                break;
        }
        GL11.glLineWidth(2.0f);
        worldRenderer.begin(3, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        while (!z2 && renderPosY > 0.0d) {
            iMovingObjectPositionRayTraceBlocks = theWorld.rayTraceBlocks(new WVec3(renderPosX, renderPosY, renderPosZ), new WVec3(renderPosX + d5, renderPosY + d6, renderPosZ + d7), false, true, false);
            WVec3 wVec3 = new WVec3(renderPosX, renderPosY, renderPosZ);
            WVec3 wVec32 = new WVec3(renderPosX + d5, renderPosY + d6, renderPosZ + d7);
            if (iMovingObjectPositionRayTraceBlocks != null) {
                z2 = true;
                wVec32 = new WVec3(iMovingObjectPositionRayTraceBlocks.getHitVec().getXCoord(), iMovingObjectPositionRayTraceBlocks.getHitVec().getYCoord(), iMovingObjectPositionRayTraceBlocks.getHitVec().getZCoord());
            }
            IAxisAlignedBB iAxisAlignedBBExpand = MinecraftInstance.classProvider.createAxisAlignedBB(renderPosX - f2, renderPosY - f2, renderPosZ - f2, renderPosX + f2, renderPosY + f2, renderPosZ + f2).addCoord(d5, d6, d7).expand(1.0d, 1.0d, 1.0d);
            int iFloor = (int) Math.floor((iAxisAlignedBBExpand.getMinX() - 2.0d) / 16.0d);
            int iFloor2 = (int) Math.floor((iAxisAlignedBBExpand.getMaxX() + 2.0d) / 16.0d);
            int iFloor3 = (int) Math.floor((iAxisAlignedBBExpand.getMinZ() - 2.0d) / 16.0d);
            int iFloor4 = (int) Math.floor((iAxisAlignedBBExpand.getMaxZ() + 2.0d) / 16.0d);
            ArrayList<IEntity> arrayList = new ArrayList();
            int i = iFloor;
            if (i <= iFloor2) {
                while (true) {
                    int i2 = iFloor3;
                    if (i2 <= iFloor4) {
                        while (true) {
                            theWorld.getChunkFromChunkCoords(i, i2).getEntitiesWithinAABBForEntity(thePlayer, iAxisAlignedBBExpand, arrayList, null);
                            if (i2 != iFloor4) {
                                i2++;
                            }
                        }
                    }
                    if (i != iFloor2) {
                        i++;
                    }
                }
            }
            for (IEntity iEntity : arrayList) {
                if (iEntity.canBeCollidedWith() && (!Intrinsics.areEqual(iEntity, thePlayer)) && (iMovingObjectPositionCalculateIntercept = iEntity.getEntityBoundingBox().expand(f2, f2, f2).calculateIntercept(wVec3, wVec32)) != null) {
                    z3 = true;
                    z2 = true;
                    iMovingObjectPositionRayTraceBlocks = iMovingObjectPositionCalculateIntercept;
                }
            }
            renderPosX += d5;
            renderPosY += d6;
            renderPosZ += d7;
            IIBlockState blockState = theWorld.getBlockState(new WBlockPos(renderPosX, renderPosY, renderPosZ));
            if (Intrinsics.areEqual(blockState.getBlock().getMaterial(blockState), MinecraftInstance.classProvider.getMaterialEnum(MaterialType.WATER))) {
                d5 *= 0.6d;
                d = d6 * 0.6d;
                d2 = d7;
                d3 = 0.6d;
            } else {
                d5 *= f4;
                d = d6 * f4;
                d2 = d7;
                d3 = f4;
            }
            d7 = d2 * d3;
            d6 = d - f;
            worldRenderer.pos(renderPosX - renderManager.getRenderPosX(), renderPosY - renderManager.getRenderPosY(), renderPosZ - renderManager.getRenderPosZ()).endVertex();
        }
        tessellatorInstance.draw();
        GL11.glPushMatrix();
        GL11.glTranslated(renderPosX - renderManager.getRenderPosX(), renderPosY - renderManager.getRenderPosY(), renderPosZ - renderManager.getRenderPosZ());
        if (iMovingObjectPositionRayTraceBlocks != null) {
            IEnumFacing sideHit = iMovingObjectPositionRayTraceBlocks.getSideHit();
            if (sideHit == null) {
                Intrinsics.throwNpe();
            }
            switch (sideHit.getAxisOrdinal()) {
                case 0:
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                    break;
                case 2:
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
            }
            if (z3) {
                RenderUtils.glColor(new Color(255, 0, 0, 150));
            }
        }
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        Cylinder cylinder = new Cylinder();
        cylinder.setDrawStyle(100011);
        cylinder.draw(0.2f, 0.0f, 0.0f, 60, 1);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        RenderUtils.resetCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Nullable
    public final Color interpolateHSB(@NotNull Color startColor, @NotNull Color endColor, float f) {
        Intrinsics.checkParameterIsNotNull(startColor, "startColor");
        Intrinsics.checkParameterIsNotNull(endColor, "endColor");
        float[] fArrRGBtoHSB = Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), (float[]) null);
        float[] fArrRGBtoHSB2 = Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), (float[]) null);
        float f2 = (fArrRGBtoHSB[2] + fArrRGBtoHSB2[2]) / 2.0f;
        float f3 = (fArrRGBtoHSB[1] + fArrRGBtoHSB2[1]) / 2.0f;
        float f4 = fArrRGBtoHSB[0] > fArrRGBtoHSB2[0] ? fArrRGBtoHSB[0] : fArrRGBtoHSB2[0];
        float f5 = fArrRGBtoHSB[0] > fArrRGBtoHSB2[0] ? fArrRGBtoHSB2[0] : fArrRGBtoHSB[0];
        return Color.getHSBColor(((f4 - f5) * f) + f5, f3, f2);
    }
}

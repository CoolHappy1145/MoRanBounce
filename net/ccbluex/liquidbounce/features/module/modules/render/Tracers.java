package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Tracers;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorRedValue", "thicknessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "drawTraces", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "color", "Ljava/awt/Color;", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Tracers", description = "Draws a line to targets around you.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Tracers.class */
public final class Tracers extends Module {
    private final ListValue colorMode = new ListValue("Color", new String[]{"Custom", "DistanceColor", "Rainbow"}, "Custom");
    private final FloatValue thicknessValue = new FloatValue("Thickness", 2.0f, 1.0f, 5.0f);
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Color colorRainbow;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glEnable(SGL.GL_LINE_SMOOTH);
            GL11.glLineWidth(((Number) this.thicknessValue.get()).floatValue());
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glBegin(1);
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            for (IEntity iEntity : theWorld.getLoadedEntityList()) {
                if ((!Intrinsics.areEqual(iEntity, thePlayer)) && EntityUtils.isSelected(iEntity, false)) {
                    int distanceToEntity = (int) (thePlayer.getDistanceToEntity(iEntity) * 2.0f);
                    if (distanceToEntity > 255) {
                        distanceToEntity = 255;
                    }
                    String str = (String) this.colorMode.get();
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase = str.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    if (EntityUtils.isFriend(iEntity)) {
                        colorRainbow = new Color(0, 0, 255, 150);
                    } else if (lowerCase.equals("custom")) {
                        colorRainbow = new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 150);
                    } else if (lowerCase.equals("distancecolor")) {
                        colorRainbow = new Color(255 - distanceToEntity, distanceToEntity, 0, 150);
                    } else {
                        colorRainbow = lowerCase.equals("rainbow") ? ColorUtils.rainbow() : new Color(255, 255, 255, 150);
                    }
                    drawTraces(iEntity, colorRainbow);
                }
            }
            GL11.glEnd();
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_LINE_SMOOTH);
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(SGL.GL_BLEND);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private final void drawTraces(IEntity iEntity, Color color) {
        if (MinecraftInstance.f157mc.getThePlayer() != null) {
            double lastTickPosX = (iEntity.getLastTickPosX() + ((iEntity.getPosX() - iEntity.getLastTickPosX()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getRenderPosX();
            double lastTickPosY = (iEntity.getLastTickPosY() + ((iEntity.getPosY() - iEntity.getLastTickPosY()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getRenderPosY();
            double lastTickPosZ = (iEntity.getLastTickPosZ() + ((iEntity.getPosZ() - iEntity.getLastTickPosZ()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getRenderPosZ();
            WVec3 wVec3RotateYaw = new WVec3(0.0d, 0.0d, 1.0d).rotatePitch((float) (-Math.toRadians(r0.getRotationPitch()))).rotateYaw((float) (-Math.toRadians(r0.getRotationYaw())));
            RenderUtils.glColor(color);
            GL11.glVertex3d(wVec3RotateYaw.getXCoord(), r0.getEyeHeight() + wVec3RotateYaw.getYCoord(), wVec3RotateYaw.getZCoord());
            GL11.glVertex3d(lastTickPosX, lastTickPosY, lastTickPosZ);
            GL11.glVertex3d(lastTickPosX, lastTickPosY, lastTickPosZ);
            GL11.glVertex3d(lastTickPosX, lastTickPosY + iEntity.getHeight(), lastTickPosZ);
        }
    }
}

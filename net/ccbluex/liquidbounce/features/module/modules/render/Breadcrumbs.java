package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u0012\u0010\u0015\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0007J\u0012\u0010\u0018\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0019H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Breadcrumbs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getColorBlueValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "getColorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getColorRainbow", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "getColorRedValue", "positions", "Ljava/util/LinkedList;", "", "onDisable", "", "onEnable", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Breadcrumbs", description = "Leaves a trail behind you.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Breadcrumbs.class */
public final class Breadcrumbs extends Module {

    @NotNull
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);

    @NotNull
    private final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);

    @NotNull
    private final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);

    @NotNull
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final LinkedList positions = new LinkedList();

    @NotNull
    public final IntegerValue getColorRedValue() {
        return this.colorRedValue;
    }

    @NotNull
    public final IntegerValue getColorGreenValue() {
        return this.colorGreenValue;
    }

    @NotNull
    public final IntegerValue getColorBlueValue() {
        return this.colorBlueValue;
    }

    @NotNull
    public final BoolValue getColorRainbow() {
        return this.colorRainbow;
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        Color colorRainbow = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
        synchronized (this.positions) {
            GL11.glPushMatrix();
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(SGL.GL_LINE_SMOOTH);
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            MinecraftInstance.f157mc.getEntityRenderer().disableLightmap();
            GL11.glBegin(3);
            RenderUtils.glColor(colorRainbow);
            double viewerPosX = MinecraftInstance.f157mc.getRenderManager().getViewerPosX();
            double viewerPosY = MinecraftInstance.f157mc.getRenderManager().getViewerPosY();
            double viewerPosZ = MinecraftInstance.f157mc.getRenderManager().getViewerPosZ();
            Iterator it = this.positions.iterator();
            while (it.hasNext()) {
                double[] dArr = (double[]) it.next();
                GL11.glVertex3d(dArr[0] - viewerPosX, dArr[1] - viewerPosY, dArr[2] - viewerPosZ);
            }
            GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
            GL11.glEnd();
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glDisable(SGL.GL_LINE_SMOOTH);
            GL11.glDisable(SGL.GL_BLEND);
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            GL11.glPopMatrix();
            Unit unit = Unit.INSTANCE;
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        synchronized (this.positions) {
            LinkedList linkedList = this.positions;
            double[] dArr = new double[3];
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            dArr[0] = thePlayer.getPosX();
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            dArr[1] = thePlayer2.getEntityBoundingBox().getMinY();
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            dArr[2] = thePlayer3.getPosZ();
            linkedList.add(dArr);
        }
    }

    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            synchronized (this.positions) {
                this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (thePlayer.getEyeHeight() * 0.5f), thePlayer.getPosZ()});
                this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
            }
        }
    }

    public void onDisable() {
        synchronized (this.positions) {
            this.positions.clear();
            Unit unit = Unit.INSTANCE;
        }
    }
}

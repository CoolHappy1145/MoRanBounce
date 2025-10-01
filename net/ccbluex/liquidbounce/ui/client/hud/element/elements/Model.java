package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "Model")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Model;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "customPitch", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "customYaw", "pitchMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rotate", "", "rotateDirection", "", "yawMode", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawEntityOnScreen", "", "yaw", "pitch", "entityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Model.class */
public final class Model extends Element {
    private final ListValue yawMode;
    private final FloatValue customYaw;
    private final ListValue pitchMode;
    private final FloatValue customPitch;
    private float rotate;
    private boolean rotateDirection;

    public Model() {
        this(0.0d, 0.0d, 3, null);
    }

    public Model(double d, double d2) {
        super(d, d2, 0.0f, null, 12, null);
        this.yawMode = new ListValue("Yaw", new String[]{"Player", "Animation", "Custom"}, "Animation");
        this.customYaw = new FloatValue("CustomYaw", 0.0f, -180.0f, 180.0f);
        this.pitchMode = new ListValue("Pitch", new String[]{"Player", "Custom"}, "Player");
        this.customPitch = new FloatValue("CustomPitch", 0.0f, -90.0f, 90.0f);
    }

    public Model(double d, double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 40.0d : d, (i & 2) != 0 ? 100.0d : d2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0174  */
    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Border drawElement() {
        float rotationYaw;
        float rotationPitch;
        String str = (String) this.yawMode.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    rotationYaw = ((Number) this.customYaw.get()).floatValue();
                    break;
                } else {
                    rotationYaw = 0.0f;
                    break;
                }
            case -985752863:
                if (lowerCase.equals("player")) {
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer == null) {
                        Intrinsics.throwNpe();
                    }
                    rotationYaw = thePlayer.getRotationYaw();
                    break;
                }
                break;
            case 1118509956:
                if (lowerCase.equals("animation")) {
                    int i = RenderUtils.deltaTime;
                    if (this.rotateDirection) {
                        if (this.rotate <= 70.0f) {
                            this.rotate += 0.12f * i;
                        } else {
                            this.rotateDirection = false;
                            this.rotate = 70.0f;
                        }
                    } else if (this.rotate >= -70.0f) {
                        this.rotate -= 0.12f * i;
                    } else {
                        this.rotateDirection = true;
                        this.rotate = -70.0f;
                    }
                    rotationYaw = this.rotate;
                    break;
                }
                break;
        }
        float f = rotationYaw;
        String str2 = (String) this.pitchMode.get();
        if (str2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase2 = str2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase2.hashCode()) {
            case -1349088399:
                if (lowerCase2.equals("custom")) {
                    rotationPitch = ((Number) this.customPitch.get()).floatValue();
                    break;
                } else {
                    rotationPitch = 0.0f;
                    break;
                }
            case -985752863:
                if (lowerCase2.equals("player")) {
                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer2 == null) {
                        Intrinsics.throwNpe();
                    }
                    rotationPitch = thePlayer2.getRotationPitch();
                    break;
                }
                break;
        }
        float f2 = rotationPitch;
        float fAbs = f2 > 0.0f ? -f2 : Math.abs(f2);
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        drawEntityOnScreen(f, fAbs, thePlayer3);
        return new Border(30.0f, 10.0f, -30.0f, -100.0f);
    }

    private final void drawEntityOnScreen(float f, float f2, IEntityLivingBase iEntityLivingBase) {
        MinecraftInstance.classProvider.getGlStateManager().resetColor();
        MinecraftInstance.classProvider.getGlStateManager().enableColorMaterial();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 50.0f);
        GL11.glScalef(-50.0f, 50.0f, 50.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        float renderYawOffset = iEntityLivingBase.getRenderYawOffset();
        float rotationYaw = iEntityLivingBase.getRotationYaw();
        float rotationPitch = iEntityLivingBase.getRotationPitch();
        float prevRotationYawHead = iEntityLivingBase.getPrevRotationYawHead();
        float rotationYawHead = iEntityLivingBase.getRotationYawHead();
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        MinecraftInstance.functions.enableStandardItemLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((-((float) Math.atan(f2 / 40.0f))) * 20.0f, 1.0f, 0.0f, 0.0f);
        iEntityLivingBase.setRenderYawOffset(((float) Math.atan(f / 40.0f)) * 20.0f);
        iEntityLivingBase.setRotationYaw(((float) Math.atan(f / 40.0f)) * 40.0f);
        iEntityLivingBase.setRotationPitch((-((float) Math.atan(f2 / 40.0f))) * 20.0f);
        iEntityLivingBase.setRotationYawHead(iEntityLivingBase.getRotationYaw());
        iEntityLivingBase.setPrevRotationYawHead(iEntityLivingBase.getRotationYaw());
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        IRenderManager renderManager = MinecraftInstance.f157mc.getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(iEntityLivingBase, 0.0d, 0.0d, 0.0d, 0.0f, 1.0f);
        renderManager.setRenderShadow(true);
        iEntityLivingBase.setRenderYawOffset(renderYawOffset);
        iEntityLivingBase.setRotationYaw(rotationYaw);
        iEntityLivingBase.setRotationPitch(rotationPitch);
        iEntityLivingBase.setPrevRotationYawHead(prevRotationYawHead);
        iEntityLivingBase.setRotationYawHead(rotationYawHead);
        GL11.glPopMatrix();
        MinecraftInstance.functions.disableStandardItemLighting();
        MinecraftInstance.classProvider.getGlStateManager().disableRescaleNormal();
        MinecraftInstance.functions.setActiveTextureLightMapTexUnit();
        MinecraftInstance.classProvider.getGlStateManager().disableTexture2D();
        MinecraftInstance.functions.setActiveTextureDefaultTexUnit();
        MinecraftInstance.classProvider.getGlStateManager().resetColor();
    }
}

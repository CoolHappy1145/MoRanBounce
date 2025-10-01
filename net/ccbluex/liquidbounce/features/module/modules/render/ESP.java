package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p005ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.WorldToScreen;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/* compiled from: ESP.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdR\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd  2\u00020\u0001:\u0001 B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006!"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/ESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "CSGOWidth", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "colorTeam", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "outlineWidth", "shaderGlowRadius", "shaderOutlineRadius", "tag", "", "getTag", "()Ljava/lang/String;", "wireframeWidth", "getColor", "Ljava/awt/Color;", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "Companion", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "ESP", description = "Allows you to see targets through walls.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/ESP.class */
public final class ESP extends Module {

    @JvmField
    @NotNull
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "OtherBox", "WireFrame", "2D", "CSGO", "Real2D", "Outline", "ShaderOutline", "ShaderGlow"}, "Box");

    @JvmField
    @NotNull
    public final FloatValue outlineWidth = new FloatValue("Outline-Width", 3.0f, 0.5f, 5.0f);

    @JvmField
    @NotNull
    public final FloatValue wireframeWidth = new FloatValue("WireFrame-Width", 2.0f, 0.5f, 5.0f);
    private final FloatValue shaderOutlineRadius = new FloatValue("ShaderOutline-Radius", 1.35f, 1.0f, 2.0f);
    private final FloatValue shaderGlowRadius = new FloatValue("ShaderGlow-Radius", 2.3f, 2.0f, 3.0f);
    private final FloatValue CSGOWidth = new FloatValue("CSGO-Width", 2.0f, 0.5f, 5.0f);
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final BoolValue colorTeam = new BoolValue("Team", false);
    public static final Companion Companion = new Companion(null);

    @JvmField
    public static boolean renderNameTags = true;

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        String mode = (String) this.modeValue.get();
        Matrix4f mvMatrix = WorldToScreen.getMatrix(SGL.GL_MODELVIEW_MATRIX);
        Matrix4f projectionMatrix = WorldToScreen.getMatrix(2983);
        boolean real2d = StringsKt.equals(mode, "real2d", true);
        boolean csgo = StringsKt.equals(mode, "csgo", true);
        if (csgo) {
            GL11.glPushAttrib(8192);
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0d, MinecraftInstance.f157mc.getDisplayWidth(), MinecraftInstance.f157mc.getDisplayHeight(), 0.0d, -1.0d, 1.0d);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
            GL11.glDepthMask(true);
            GL11.glLineWidth(1.0f);
        }
        if (real2d) {
            GL11.glPushAttrib(8192);
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0d, MinecraftInstance.f157mc.getDisplayWidth(), MinecraftInstance.f157mc.getDisplayHeight(), 0.0d, -1.0d, 1.0d);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
            GL11.glDepthMask(true);
            GL11.glLineWidth(1.0f);
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        for (IEntity entity : theWorld.getLoadedEntityList()) {
            if ((!Intrinsics.areEqual(entity, MinecraftInstance.f157mc.getThePlayer())) && EntityUtils.isSelected(entity, false)) {
                IEntityLivingBase entityLiving = entity.asEntityLivingBase();
                Color color = getColor(entityLiving);
                if (mode == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = mode.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1171135301:
                        if (lowerCase.equals("otherbox")) {
                            break;
                        } else {
                            break;
                        }
                    case -934973296:
                        if (lowerCase.equals("real2d")) {
                            IRenderManager renderManager = MinecraftInstance.f157mc.getRenderManager();
                            ITimer timer = MinecraftInstance.f157mc.getTimer();
                            IAxisAlignedBB bb = entityLiving.getEntityBoundingBox().offset(-entityLiving.getPosX(), -entityLiving.getPosY(), -entityLiving.getPosZ()).offset(entityLiving.getLastTickPosX() + ((entityLiving.getPosX() - entityLiving.getLastTickPosX()) * timer.getRenderPartialTicks()), entityLiving.getLastTickPosY() + ((entityLiving.getPosY() - entityLiving.getLastTickPosY()) * timer.getRenderPartialTicks()), entityLiving.getLastTickPosZ() + ((entityLiving.getPosZ() - entityLiving.getLastTickPosZ()) * timer.getRenderPartialTicks())).offset(-renderManager.getRenderPosX(), -renderManager.getRenderPosY(), -renderManager.getRenderPosZ());
                            double[][] boxVertices = (double[][]) new double[]{new double[]{bb.getMinX(), bb.getMinY(), bb.getMinZ()}, new double[]{bb.getMinX(), bb.getMaxY(), bb.getMinZ()}, new double[]{bb.getMaxX(), bb.getMaxY(), bb.getMinZ()}, new double[]{bb.getMaxX(), bb.getMinY(), bb.getMinZ()}, new double[]{bb.getMinX(), bb.getMinY(), bb.getMaxZ()}, new double[]{bb.getMinX(), bb.getMaxY(), bb.getMaxZ()}, new double[]{bb.getMaxX(), bb.getMaxY(), bb.getMaxZ()}, new double[]{bb.getMaxX(), bb.getMinY(), bb.getMaxZ()}};
                            float minX = FloatCompanionObject.INSTANCE.getMAX_VALUE();
                            float minY = FloatCompanionObject.INSTANCE.getMAX_VALUE();
                            float maxX = -1.0f;
                            float maxY = -1.0f;
                            for (double[] boxVertex : boxVertices) {
                                Vector2f screenPos = WorldToScreen.worldToScreen(new Vector3f((float) boxVertex[0], (float) boxVertex[1], (float) boxVertex[2]), mvMatrix, projectionMatrix, MinecraftInstance.f157mc.getDisplayWidth(), MinecraftInstance.f157mc.getDisplayHeight());
                                if (screenPos != null) {
                                    minX = Math.min(screenPos.x, minX);
                                    minY = Math.min(screenPos.y, minY);
                                    maxX = Math.max(screenPos.x, maxX);
                                    maxY = Math.max(screenPos.y, maxY);
                                }
                            }
                            if (minX > 0 || minY > 0 || maxX <= MinecraftInstance.f157mc.getDisplayWidth() || maxY <= MinecraftInstance.f157mc.getDisplayWidth()) {
                                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                                GL11.glBegin(2);
                                GL11.glVertex2f(minX, minY);
                                GL11.glVertex2f(minX, maxY);
                                GL11.glVertex2f(maxX, maxY);
                                GL11.glVertex2f(maxX, minY);
                                GL11.glEnd();
                                break;
                            } else {
                                break;
                            }
                        } else {
                            continue;
                        }
                        break;
                    case 1650:
                        if (lowerCase.equals("2d")) {
                            IRenderManager renderManager2 = MinecraftInstance.f157mc.getRenderManager();
                            ITimer timer2 = MinecraftInstance.f157mc.getTimer();
                            double posX = (entityLiving.getLastTickPosX() + ((entityLiving.getPosX() - entityLiving.getLastTickPosX()) * timer2.getRenderPartialTicks())) - renderManager2.getRenderPosX();
                            double posY = (entityLiving.getLastTickPosY() + ((entityLiving.getPosY() - entityLiving.getLastTickPosY()) * timer2.getRenderPartialTicks())) - renderManager2.getRenderPosY();
                            double posZ = (entityLiving.getLastTickPosZ() + ((entityLiving.getPosZ() - entityLiving.getLastTickPosZ()) * timer2.getRenderPartialTicks())) - renderManager2.getRenderPosZ();
                            int rgb = color.getRGB();
                            Color color2 = Color.BLACK;
                            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
                            RenderUtils.draw2D(entityLiving, posX, posY, posZ, rgb, color2.getRGB());
                            break;
                        } else {
                            continue;
                        }
                    case 97739:
                        if (lowerCase.equals("box")) {
                            break;
                        } else {
                            break;
                        }
                    case 3063128:
                        if (lowerCase.equals("csgo")) {
                            IRenderManager renderManager3 = MinecraftInstance.f157mc.getRenderManager();
                            ITimer timer3 = MinecraftInstance.f157mc.getTimer();
                            IAxisAlignedBB bb2 = entityLiving.getEntityBoundingBox().offset(-entityLiving.getPosX(), -entityLiving.getPosY(), -entityLiving.getPosZ()).offset(entityLiving.getLastTickPosX() + ((entityLiving.getPosX() - entityLiving.getLastTickPosX()) * timer3.getRenderPartialTicks()), entityLiving.getLastTickPosY() + ((entityLiving.getPosY() - entityLiving.getLastTickPosY()) * timer3.getRenderPartialTicks()), entityLiving.getLastTickPosZ() + ((entityLiving.getPosZ() - entityLiving.getLastTickPosZ()) * timer3.getRenderPartialTicks())).offset(-renderManager3.getRenderPosX(), -renderManager3.getRenderPosY(), -renderManager3.getRenderPosZ());
                            double[][] boxVertices2 = (double[][]) new double[]{new double[]{bb2.getMinX(), bb2.getMinY(), bb2.getMinZ()}, new double[]{bb2.getMinX(), bb2.getMaxY(), bb2.getMinZ()}, new double[]{bb2.getMaxX(), bb2.getMaxY(), bb2.getMinZ()}, new double[]{bb2.getMaxX(), bb2.getMinY(), bb2.getMinZ()}, new double[]{bb2.getMinX(), bb2.getMinY(), bb2.getMaxZ()}, new double[]{bb2.getMinX(), bb2.getMaxY(), bb2.getMaxZ()}, new double[]{bb2.getMaxX(), bb2.getMaxY(), bb2.getMaxZ()}, new double[]{bb2.getMaxX(), bb2.getMinY(), bb2.getMaxZ()}};
                            float minX2 = MinecraftInstance.f157mc.getDisplayWidth();
                            float minY2 = MinecraftInstance.f157mc.getDisplayHeight();
                            float maxX2 = 0.0f;
                            float maxY2 = 0.0f;
                            for (double[] boxVertex2 : boxVertices2) {
                                Vector2f screenPos2 = WorldToScreen.worldToScreen(new Vector3f((float) boxVertex2[0], (float) boxVertex2[1], (float) boxVertex2[2]), mvMatrix, projectionMatrix, MinecraftInstance.f157mc.getDisplayWidth(), MinecraftInstance.f157mc.getDisplayHeight());
                                if (screenPos2 != null) {
                                    minX2 = RangesKt.coerceAtMost(screenPos2.x, minX2);
                                    minY2 = RangesKt.coerceAtMost(screenPos2.y, minY2);
                                    maxX2 = RangesKt.coerceAtLeast(screenPos2.x, maxX2);
                                    maxY2 = RangesKt.coerceAtLeast(screenPos2.y, maxY2);
                                }
                            }
                            if (minX2 != MinecraftInstance.f157mc.getDisplayWidth() && minY2 != MinecraftInstance.f157mc.getDisplayHeight() && maxX2 != 0.0f && maxY2 != 0.0f) {
                                float width = ((Number) this.CSGOWidth.get()).floatValue() * ((maxY2 - minY2) / 50);
                                RenderUtils.drawRect(minX2 - width, minY2 - width, minX2, maxY2, color);
                                RenderUtils.drawRect(maxX2, minY2 - width, maxX2 + width, maxY2 + width, color);
                                RenderUtils.drawRect(minX2 - width, maxY2, maxX2, maxY2 + width, color);
                                RenderUtils.drawRect(minX2 - width, minY2 - width, maxX2, minY2, color);
                                float hpSize = ((maxY2 + width) - minY2) * (entityLiving.getHealth() / entityLiving.getMaxHealth());
                                RenderUtils.drawRect(minX2 - (width * 3), minY2 - width, minX2 - (width * 2), maxY2 + width, Color.RED);
                                RenderUtils.drawRect(minX2 - (width * 3), maxY2 - hpSize, minX2 - (width * 2), maxY2 + width, Color.GREEN);
                                break;
                            } else {
                                break;
                            }
                        } else {
                            continue;
                        }
                        break;
                }
                RenderUtils.drawEntityBox(entity, color, !StringsKt.equals(mode, "otherbox", true));
            }
        }
        if (real2d) {
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (csgo) {
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        GlowShader glowShader;
        float fFloatValue;
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String mode = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(mode, "(this as java.lang.String).toLowerCase()");
        if (StringsKt.equals(mode, "shaderoutline", true)) {
            glowShader = OutlineShader.OUTLINE_SHADER;
        } else {
            glowShader = StringsKt.equals(mode, "shaderglow", true) ? GlowShader.GLOW_SHADER : null;
        }
        if (glowShader != null) {
            FramebufferShader shader = glowShader;
            shader.startDraw(event.getPartialTicks());
            renderNameTags = false;
            try {
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                for (IEntity entity : theWorld.getLoadedEntityList()) {
                    if (EntityUtils.isSelected(entity, false)) {
                        MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(entity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                    }
                }
            } catch (Exception ex) {
                ClientUtils.getLogger().error("An error occurred while rendering all entities for shader esp", ex);
            }
            renderNameTags = true;
            if (StringsKt.equals(mode, "shaderoutline", true)) {
                fFloatValue = ((Number) this.shaderOutlineRadius.get()).floatValue();
            } else {
                fFloatValue = StringsKt.equals(mode, "shaderglow", true) ? ((Number) this.shaderGlowRadius.get()).floatValue() : 1.0f;
            }
            float radius = fFloatValue;
            shader.stopDraw(getColor(null), radius, 1.0f);
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    @NotNull
    public final Color getColor(@Nullable IEntity entity) {
        int index;
        ESP $this$run = this;
        if (entity != null && MinecraftInstance.classProvider.isEntityLivingBase(entity)) {
            IEntityLivingBase entityLivingBase = entity.asEntityLivingBase();
            if (entityLivingBase.getHurtTime() > 0) {
                Color color = Color.RED;
                Intrinsics.checkExpressionValueIsNotNull(color, "Color.RED");
                return color;
            }
            if (EntityUtils.isFriend(entityLivingBase)) {
                Color color2 = Color.BLUE;
                Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLUE");
                return color2;
            }
            if (((Boolean) $this$run.colorTeam.get()).booleanValue()) {
                IIChatComponent displayName = entityLivingBase.getDisplayName();
                if (displayName != null) {
                    String formattedText = displayName.getFormattedText();
                    if (formattedText == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    char[] chars = formattedText.toCharArray();
                    Intrinsics.checkExpressionValueIsNotNull(chars, "(this as java.lang.String).toCharArray()");
                    int color3 = Integer.MAX_VALUE;
                    int i = 0;
                    int length = chars.length;
                    while (true) {
                        if (i < length) {
                            if (chars[i] != '\u00a7' || i + 1 >= chars.length || (index = GameFontRenderer.Companion.getColorIndex(chars[i + 1])) < 0 || index > 15) {
                                i++;
                            } else {
                                color3 = ColorUtils.hexColors[index];
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    return new Color(color3);
                }
            }
        }
        return ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0005"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/ESP$Companion;", "", "()V", "renderNameTags", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/ESP$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

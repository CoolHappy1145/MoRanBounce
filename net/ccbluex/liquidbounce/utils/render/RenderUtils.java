package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/RenderUtils.class */
public final class RenderUtils extends MinecraftInstance {
    private static final Map glCapMap = new HashMap();
    private static final int[] DISPLAY_LISTS_2D = new int[4];
    public static int deltaTime;
    private static final Frustum frustrum;

    static {
        for (int i = 0; i < DISPLAY_LISTS_2D.length; i++) {
            DISPLAY_LISTS_2D[i] = GL11.glGenLists(1);
        }
        GL11.glNewList(DISPLAY_LISTS_2D[0], SGL.GL_COMPILE);
        quickDrawRect(-7.0f, 2.0f, -4.0f, 3.0f);
        quickDrawRect(4.0f, 2.0f, 7.0f, 3.0f);
        quickDrawRect(-7.0f, 0.5f, -6.0f, 3.0f);
        quickDrawRect(6.0f, 0.5f, 7.0f, 3.0f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[1], SGL.GL_COMPILE);
        quickDrawRect(-7.0f, 3.0f, -4.0f, 3.3f);
        quickDrawRect(4.0f, 3.0f, 7.0f, 3.3f);
        quickDrawRect(-7.3f, 0.5f, -7.0f, 3.3f);
        quickDrawRect(7.0f, 0.5f, 7.3f, 3.3f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[2], SGL.GL_COMPILE);
        quickDrawRect(4.0f, -20.0f, 7.0f, -19.0f);
        quickDrawRect(-7.0f, -20.0f, -4.0f, -19.0f);
        quickDrawRect(6.0f, -20.0f, 7.0f, -17.5f);
        quickDrawRect(-7.0f, -20.0f, -6.0f, -17.5f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[3], SGL.GL_COMPILE);
        quickDrawRect(7.0f, -20.0f, 7.3f, -17.5f);
        quickDrawRect(-7.3f, -20.0f, -7.0f, -17.5f);
        quickDrawRect(4.0f, -20.3f, 7.3f, -20.0f);
        quickDrawRect(-7.3f, -20.3f, -4.0f, -20.0f);
        GL11.glEndList();
        frustrum = new Frustum();
    }

    public static void rectangleBordered(double d, double d2, double d3, double d4, double d5, int i, int i2) {
        rectangle(d + d5, d2 + d5, d3 - d5, d4 - d5, i);
        rectangle(d + d5, d2, d3 - d5, d2 + d5, i2);
        rectangle(d, d2, d + d5, d4, i2);
        rectangle(d3 - d5, d2, d3, d4, i2);
        rectangle(d + d5, d4 - d5, d3 - d5, d4, i2);
    }

    public static int getRainbow(int i, int i2, float f, float f2) {
        return Color.getHSBColor(((System.currentTimeMillis() + (i2 * i)) % 2000) / 2000.0f, f2, f).getRGB();
    }

    public static Color getGradientOffset(Color color, Color color2, double d) {
        if (d > 1.0d) {
            double d2 = d % 1.0d;
            d = ((int) d) % 2 == 0 ? d2 : 1.0d - d2;
        }
        double d3 = 1.0d - d;
        return new Color((int) ((color.getRed() * d3) + (color2.getRed() * d)), (int) ((color.getGreen() * d3) + (color2.getGreen() * d)), (int) ((color.getBlue() * d3) + (color2.getBlue() * d)));
    }

    public static int getRainbowOpaque(int i, float f, float f2, int i2) {
        return Color.HSBtoRGB(((System.currentTimeMillis() + i2) % (i * 1000)) / (i * 1000), f, f2);
    }

    public static int SkyRainbow(int i, float f, float f2) {
        double dCeil = (Math.ceil(System.currentTimeMillis() + (i * 109)) / 5.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (dCeil / 360.0d))) < 0.5d ? -((float) (dCeil / 360.0d)) : (float) (dCeil / 360.0d), f, f2).getRGB();
    }

    public static Color skyRainbow(int i, float f, float f2) {
        double dCeil = (Math.ceil(System.currentTimeMillis() + (i * 109)) / 5.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (dCeil / 360.0d))) < 0.5d ? -((float) (dCeil / 360.0d)) : (float) (dCeil / 360.0d), f, f2);
    }

    public static int Astolfo(int i, float f, float f2) {
        double dCeil = (Math.ceil(System.currentTimeMillis() + (i * 130)) / 6.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (dCeil / 360.0d))) < 0.5d ? -((float) (dCeil / 360.0d)) : (float) (dCeil / 360.0d), f, f2).getRGB();
    }

    public static int Astolfo(int i, float f, float f2, int i2, int i3, float f3) {
        double dCeil = (Math.ceil(System.currentTimeMillis() + (i * i2)) / i3) % f3;
        return Color.getHSBColor(((double) ((float) (dCeil / ((double) f3)))) < 0.5d ? -((float) (dCeil / f3)) : (float) (dCeil / f3), f2, f).getRGB();
    }

    public static void customRounded(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i) {
        float f9 = ((i >> 24) & 255) / 255.0f;
        float f10 = ((i >> 16) & 255) / 255.0f;
        float f11 = ((i >> 8) & 255) / 255.0f;
        float f12 = (i & 255) / 255.0f;
        if (f > f3) {
            f = f3;
            f3 = f;
        }
        if (f2 > f4) {
            f2 = f4;
            f4 = f2;
        }
        double d = f + f5;
        double d2 = f2 + f5;
        double d3 = f3 - f6;
        double d4 = f2 + f6;
        double d5 = f3 - f7;
        double d6 = f4 - f7;
        double d7 = f + f8;
        double d8 = f4 - f8;
        GL11.glPushMatrix();
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(f10, f11, f12, f9);
        GL11.glBegin(9);
        if (f7 <= 0.0f) {
            GL11.glVertex2d(d5, d6);
        } else {
            double d9 = 0.0d;
            while (true) {
                double d10 = d9;
                if (d10 > 90.0d) {
                    break;
                }
                GL11.glVertex2d(d5 + (Math.sin(d10 * 0.017453292519943295d) * f7), d6 + (Math.cos(d10 * 0.017453292519943295d) * f7));
                d9 = d10 + 1.0d;
            }
        }
        if (f6 <= 0.0f) {
            GL11.glVertex2d(d3, d4);
        } else {
            double d11 = 90.0d;
            while (true) {
                double d12 = d11;
                if (d12 > 180.0d) {
                    break;
                }
                GL11.glVertex2d(d3 + (Math.sin(d12 * 0.017453292519943295d) * f6), d4 + (Math.cos(d12 * 0.017453292519943295d) * f6));
                d11 = d12 + 1.0d;
            }
        }
        if (f5 <= 0.0f) {
            GL11.glVertex2d(d, d2);
        } else {
            double d13 = 180.0d;
            while (true) {
                double d14 = d13;
                if (d14 > 270.0d) {
                    break;
                }
                GL11.glVertex2d(d + (Math.sin(d14 * 0.017453292519943295d) * f5), d2 + (Math.cos(d14 * 0.017453292519943295d) * f5));
                d13 = d14 + 1.0d;
            }
        }
        if (f8 <= 0.0f) {
            GL11.glVertex2d(d7, d8);
        } else {
            double d15 = 270.0d;
            while (true) {
                double d16 = d15;
                if (d16 > 360.0d) {
                    break;
                }
                GL11.glVertex2d(d7 + (Math.sin(d16 * 0.017453292519943295d) * f8), d8 + (Math.cos(d16 * 0.017453292519943295d) * f8));
                d15 = d16 + 1.0d;
            }
        }
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void drawImage3(ResourceLocation resourceLocation, float f, float f2, int i, int i2, float f3, float f4, float f5, float f6) {
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(f3, f4, f5, f6);
        GL11.glTranslatef(f, f2, f);
        mc2.func_110434_K().func_110577_a(resourceLocation);
        Gui.func_146110_a(0, 0, 0.0f, 0.0f, i, i2, i, i2);
        GL11.glTranslatef(-f, -f2, -f);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
    }

    public static void drawGradient(double d, double d2, double d3, double d4, int i, int i2) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        GL11.glVertex2d(d3, d2);
        GL11.glVertex2d(d, d2);
        GL11.glColor4f(((i2 >> 16) & 255) / 255.0f, ((i2 >> 8) & 255) / 255.0f, (i2 & 255) / 255.0f, ((i2 >> 24) & 255) / 255.0f);
        GL11.glVertex2d(d, d4);
        GL11.glVertex2d(d3, d4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
        GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
    }

    public static void rectangle(double d, double d2, double d3, double d4, int i) {
        if (d < d3) {
            d = d3;
            d3 = d;
        }
        if (d2 < d4) {
            d2 = d4;
            d4 = d2;
        }
        IWorldRenderer worldRenderer = classProvider.getTessellatorInstance().getWorldRenderer();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.func_179131_c(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        worldRenderer.begin(7, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(d, d4, 0.0d).endVertex();
        worldRenderer.pos(d3, d4, 0.0d).endVertex();
        worldRenderer.pos(d3, d2, 0.0d).endVertex();
        worldRenderer.pos(d, d2, 0.0d).endVertex();
        classProvider.getTessellatorInstance().draw();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawImage2(ResourceLocation resourceLocation, float f, float f2, int i, int i2) {
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(f, f2, f);
        mc2.func_110434_K().func_110577_a(resourceLocation);
        drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, i, i2, i, i2);
        GL11.glTranslatef(-f, -f2, -f);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
    }

    public static void quickDrawHead(IResourceLocation iResourceLocation, int i, int i2, int i3, int i4) {
        f157mc.getTextureManager().bindTexture(iResourceLocation);
        drawScaledCustomSizeModalRect(i, i2, 8.0f, 8.0f, 8, 8, i3, i4, 64.0f, 64.0f);
        drawScaledCustomSizeModalRect(i, i2, 40.0f, 8.0f, 8, 8, i3, i4, 64.0f, 64.0f);
    }

    public static void drawGradientSidewaysV(double d, double d2, double d3, double d4, int i, int i2) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        GL11.glVertex2d(d, d4);
        GL11.glVertex2d(d3, d4);
        GL11.glColor4f(((i2 >> 16) & 255) / 255.0f, ((i2 >> 8) & 255) / 255.0f, (i2 & 255) / 255.0f, ((i2 >> 24) & 255) / 255.0f);
        GL11.glVertex2d(d3, d2);
        GL11.glVertex2d(d, d2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
        Gui.func_73734_a(0, 0, 0, 0, 0);
    }

    public static void enableSmoothLine(float f) {
        GL11.glDisable(3008);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(f);
    }

    public static void disableSmoothLine() {
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawBorderedRectBlur(float f, float f2, float f3, float f4, float f5, int i, int i2) {
        drawRectBlurFix(f, f2, f3, f4, i2);
        drawBorder(f, f2, f3, f4, f5, i);
    }

    public static void drawRectBlurFix(float f, float f2, float f3, float f4, int i) {
        GL11.glPushMatrix();
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        glColor(i);
        GL11.glBegin(7);
        GL11.glVertex2d(f3, f2);
        GL11.glVertex2d(f, f2);
        GL11.glVertex2d(f, f4);
        GL11.glVertex2d(f3, f4);
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static boolean isInViewFrustrum(IEntity iEntity) {
        return isInViewFrustrum(iEntity.getEntityBoundingBox()) || iEntity.getIgnoreFrustumCheck();
    }

    private static boolean isInViewFrustrum(IAxisAlignedBB iAxisAlignedBB) {
        IEntity renderViewEntity = f157mc.getRenderViewEntity();
        frustrum.func_78547_a(renderViewEntity.getPosX(), renderViewEntity.getPosY(), renderViewEntity.getPosZ());
        return frustrum.func_78546_a((AxisAlignedBB) iAxisAlignedBB);
    }

    public static void quickDrawGradientSideways(double d, double d2, double d3, double d4, int i, int i2) {
        GL11.glBegin(7);
        glColor(i);
        GL11.glVertex2d(d, d2);
        GL11.glVertex2d(d, d4);
        glColor(i2);
        GL11.glVertex2d(d3, d4);
        GL11.glVertex2d(d3, d2);
        GL11.glEnd();
    }

    public static void drawRoundedRect(float f, float f2, float f3, float f4, float f5, int i) {
        drawRoundedRect(f, f2, f3, f4, f5, i, true);
    }

    public static void drawHead(IResourceLocation iResourceLocation, int i, int i2, int i3, int i4) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        f157mc.getTextureManager().bindTexture(iResourceLocation);
        drawScaledCustomSizeModalRect(i, i2, 8.0f, 8.0f, 8, 8, i3, i4, 64.0f, 64.0f);
        drawScaledCustomSizeModalRect(i, i2, 40.0f, 8.0f, 8, 8, i3, i4, 64.0f, 64.0f);
    }

    public static void drawCircleRect(float f, float f2, float f3, float f4, float f5, int i) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        glColor(i);
        quickRenderCircle(f3 - f5, f4 - f5, 0.0d, 90.0d, f5, f5);
        quickRenderCircle(f + f5, f4 - f5, 90.0d, 180.0d, f5, f5);
        quickRenderCircle(f + f5, f2 + f5, 180.0d, 270.0d, f5, f5);
        quickRenderCircle(f3 - f5, f2 + f5, 270.0d, 360.0d, f5, f5);
        quickDrawRect(f + f5, f2 + f5, f3 - f5, f4 - f5);
        quickDrawRect(f, f2 + f5, f + f5, f4 - f5);
        quickDrawRect(f3 - f5, f2 + f5, f3, f4 - f5);
        quickDrawRect(f + f5, f2, f3 - f5, f2 + f5);
        quickDrawRect(f + f5, f4 - f5, f3 - f5, f4);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawCircleRectFix(float f, float f2, float f3, float f4, float f5, int i) {
        GlStateManager.func_179124_c(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.func_179131_c(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        quickRenderCircle(f3 - f5, f4 - f5, 0.0d, 90.0d, f5, f5);
        quickRenderCircle(f + f5, f4 - f5, 90.0d, 180.0d, f5, f5);
        quickRenderCircle(f + f5, f2 + f5, 180.0d, 270.0d, f5, f5);
        quickRenderCircle(f3 - f5, f2 + f5, 270.0d, 360.0d, f5, f5);
        quickDrawRect(f + f5, f2 + f5, f3 - f5, f4 - f5);
        quickDrawRect(f, f2 + f5, f + f5, f4 - f5);
        quickDrawRect(f3 - f5, f2 + f5, f3, f4 - f5);
        quickDrawRect(f + f5, f2, f3 - f5, f2 + f5);
        quickDrawRect(f + f5, f4 - f5, f3 - f5, f4);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void quickRenderCircle(double d, double d2, double d3, double d4, double d5, double d6) {
        if (d3 > d4) {
            d4 = d3;
            d3 = d4;
        }
        GL11.glBegin(6);
        GL11.glVertex2d(d, d2);
        double d7 = d4;
        while (true) {
            double d8 = d7;
            if (d8 >= d3) {
                GL11.glVertex2d(d + (Math.cos((d8 * 3.141592653589793d) / 180.0d) * d5), d2 + (Math.sin((d8 * 3.141592653589793d) / 180.0d) * d6));
                d7 = d8 - 4.0d;
            } else {
                GL11.glVertex2d(d, d2);
                GL11.glEnd();
                return;
            }
        }
    }

    public static void drawEntityOnScreen(int i, int i2, int i3, IEntityLivingBase iEntityLivingBase) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179142_g();
        GlStateManager.func_179137_b(i, i2, 50.0d);
        GlStateManager.func_179152_a(-i3, i3, i3);
        GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179137_b(0.0d, 0.0d, 0.0d);
        float renderYawOffset = iEntityLivingBase.getRenderYawOffset();
        float rotationYaw = iEntityLivingBase.getRotationYaw();
        float rotationPitch = iEntityLivingBase.getRotationPitch();
        float prevRotationYawHead = iEntityLivingBase.getPrevRotationYawHead();
        float rotationYawHead = iEntityLivingBase.getRotationYawHead();
        iEntityLivingBase.setRenderYawOffset(0.0f);
        iEntityLivingBase.setRotationYaw(0.0f);
        iEntityLivingBase.setRotationPitch(90.0f);
        iEntityLivingBase.setRotationYawHead(iEntityLivingBase.getRotationYaw());
        iEntityLivingBase.setPrevRotationYawHead(iEntityLivingBase.getRotationYaw());
        IRenderManager renderManager = f157mc.getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(iEntityLivingBase, 0.0d, 0.0d, 0.0d, 0.0f, 1.0f);
        renderManager.setRenderShadow(true);
        iEntityLivingBase.setRenderYawOffset(renderYawOffset);
        iEntityLivingBase.setRotationYaw(rotationYaw);
        iEntityLivingBase.setRotationPitch(rotationPitch);
        iEntityLivingBase.setPrevRotationYawHead(prevRotationYawHead);
        iEntityLivingBase.setRotationYawHead(rotationYawHead);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    }

    public static void drawShadow(int i, int i2, int i3, int i4) {
        IScaledResolution iScaledResolutionCreateScaledResolution = classProvider.createScaledResolution(f157mc);
        drawTexturedRect(i - 9, i2 - 9, 9, 9, "paneltopleft", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i - 9, i2 + i4, 9, 9, "panelbottomleft", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i + i3, i2 + i4, 9, 9, "panelbottomright", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i + i3, i2 - 9, 9, 9, "paneltopright", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i - 9, i2, 9, i4, "panelleft", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i + i3, i2, 9, i4, "panelright", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i, i2 - 9, i3, 9, "paneltop", iScaledResolutionCreateScaledResolution);
        drawTexturedRect(i, i2 + i4, i3, 9, "panelbottom", iScaledResolutionCreateScaledResolution);
    }

    public static void drawTexturedRect(int i, int i2, int i3, int i4, String str, IScaledResolution iScaledResolution) {
        GL11.glPushMatrix();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        f157mc.getTextureManager().bindTexture(classProvider.createResourceLocation("liquidbounce/cool/potionrender/" + str + ".png"));
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawModalRectWithCustomSizedTexture(i, i2, 0.0f, 0.0f, i3, i4, i3, i4);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GL11.glPopMatrix();
    }

    public static void drawRoundedRect(float f, float f2, float f3, float f4, float f5, int i, boolean z) {
        float f6 = ((i >> 24) & 255) / 255.0f;
        float f7 = ((i >> 16) & 255) / 255.0f;
        float f8 = ((i >> 8) & 255) / 255.0f;
        float f9 = (i & 255) / 255.0f;
        if (f > f3) {
            f = f3;
            f3 = f;
        }
        if (f2 > f4) {
            f2 = f4;
            f4 = f2;
        }
        double d = f + f5;
        double d2 = f2 + f5;
        double d3 = f3 - f5;
        double d4 = f4 - f5;
        if (z) {
            GL11.glPushMatrix();
        }
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(f7, f8, f9, f6);
        GL11.glBegin(9);
        double d5 = 0.0d;
        while (true) {
            double d6 = d5;
            if (d6 > 90.0d) {
                break;
            }
            GL11.glVertex2d(d3 + (Math.sin(d6 * 0.017453292519943295d) * f5), d4 + (Math.cos(d6 * 0.017453292519943295d) * f5));
            d5 = d6 + 0.5d;
        }
        double d7 = 90.0d;
        while (true) {
            double d8 = d7;
            if (d8 > 180.0d) {
                break;
            }
            GL11.glVertex2d(d3 + (Math.sin(d8 * 0.017453292519943295d) * f5), d2 + (Math.cos(d8 * 0.017453292519943295d) * f5));
            d7 = d8 + 0.5d;
        }
        double d9 = 180.0d;
        while (true) {
            double d10 = d9;
            if (d10 > 270.0d) {
                break;
            }
            GL11.glVertex2d(d + (Math.sin(d10 * 0.017453292519943295d) * f5), d2 + (Math.cos(d10 * 0.017453292519943295d) * f5));
            d9 = d10 + 0.5d;
        }
        double d11 = 270.0d;
        while (true) {
            double d12 = d11;
            if (d12 > 360.0d) {
                break;
            }
            GL11.glVertex2d(d + (Math.sin(d12 * 0.017453292519943295d) * f5), d4 + (Math.cos(d12 * 0.017453292519943295d) * f5));
            d11 = d12 + 0.5d;
        }
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        if (z) {
            GL11.glPopMatrix();
        }
    }

    public static void drawArc(float f, float f2, double d, int i, int i2, double d2, int i3) {
        double d3 = d * 2.0d;
        float f3 = f * 2.0f;
        float f4 = f2 * 2.0f;
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth(i3);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glColor4f(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        GL11.glBegin(3);
        for (int i4 = i2; i4 <= d2; i4++) {
            GL11.glVertex2d(f3 + (Math.sin((i4 * 3.141592653589793d) / 180.0d) * d3), f4 + (Math.cos((i4 * 3.141592653589793d) / 180.0d) * d3));
        }
        GL11.glEnd();
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawBlockBox2(WBlockPos wBlockPos, Color color, boolean z, boolean z2, float f) {
        IRenderManager renderManager = f157mc.getRenderManager();
        ITimer timer = f157mc.getTimer();
        double x = wBlockPos.getX() - renderManager.getRenderPosX();
        double y = wBlockPos.getY() - renderManager.getRenderPosY();
        double z3 = wBlockPos.getZ() - renderManager.getRenderPosZ();
        IAxisAlignedBB iAxisAlignedBBCreateAxisAlignedBB = classProvider.createAxisAlignedBB(x, y, z3, x + 1.0d, y + 1.0d, z3 + 1.0d);
        IBlock block = BlockUtils.getBlock(wBlockPos);
        if (block != null) {
            IEntityPlayerSP thePlayer = f157mc.getThePlayer();
            iAxisAlignedBBCreateAxisAlignedBB = block.getSelectedBoundingBox(f157mc.getTheWorld(), f157mc.getTheWorld().getBlockState(wBlockPos), wBlockPos).expand(0.0020000000949949026d, 0.0020000000949949026d, 0.0020000000949949026d).offset(-(thePlayer.getLastTickPosX() + ((thePlayer.getPosX() - thePlayer.getLastTickPosX()) * timer.getRenderPartialTicks())), -(thePlayer.getLastTickPosY() + ((thePlayer.getPosY() - thePlayer.getLastTickPosY()) * timer.getRenderPartialTicks())), -(thePlayer.getLastTickPosZ() + ((thePlayer.getPosZ() - thePlayer.getLastTickPosZ()) * timer.getRenderPartialTicks())));
        }
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        enableGlCap(SGL.GL_BLEND);
        disableGlCap(new int[]{SGL.GL_TEXTURE_2D, SGL.GL_DEPTH_TEST});
        GL11.glDepthMask(false);
        if (z2) {
            glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : z ? 26 : 35);
            drawFilledBox(iAxisAlignedBBCreateAxisAlignedBB);
        }
        if (z) {
            GL11.glLineWidth(f);
            enableGlCap(SGL.GL_LINE_SMOOTH);
            glColor(color);
            drawSelectionBoundingBox(iAxisAlignedBBCreateAxisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawOutlinedString(String str, int i, int i2, int i3, int i4) {
        f157mc.getFontRendererObj().drawString(str, (int) (i - 1.0f), i2, i4);
        f157mc.getFontRendererObj().drawString(str, (int) (i + 1.0f), i2, i4);
        f157mc.getFontRendererObj().drawString(str, i, (int) (i2 + 1.0f), i4);
        f157mc.getFontRendererObj().drawString(str, i, (int) (i2 - 1.0f), i4);
        f157mc.getFontRendererObj().drawString(str, i, i2, i3);
    }

    public static void drawGradientSideways(double d, double d2, double d3, double d4, int i, int i2) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        GL11.glVertex2d(d, d2);
        GL11.glVertex2d(d, d4);
        GL11.glColor4f(((i2 >> 16) & 255) / 255.0f, ((i2 >> 8) & 255) / 255.0f, (i2 & 255) / 255.0f, ((i2 >> 24) & 255) / 255.0f);
        GL11.glVertex2d(d3, d4);
        GL11.glVertex2d(d3, d2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
    }

    public static void drawBlockBox(WBlockPos wBlockPos, Color color, boolean z) {
        IRenderManager renderManager = f157mc.getRenderManager();
        ITimer timer = f157mc.getTimer();
        double x = wBlockPos.getX() - renderManager.getRenderPosX();
        double y = wBlockPos.getY() - renderManager.getRenderPosY();
        double z2 = wBlockPos.getZ() - renderManager.getRenderPosZ();
        IAxisAlignedBB iAxisAlignedBBCreateAxisAlignedBB = classProvider.createAxisAlignedBB(x, y, z2, x + 1.0d, y + 1.0d, z2 + 1.0d);
        IBlock block = BlockUtils.getBlock(wBlockPos);
        if (block != null) {
            IEntityPlayerSP thePlayer = f157mc.getThePlayer();
            iAxisAlignedBBCreateAxisAlignedBB = block.getSelectedBoundingBox(f157mc.getTheWorld(), f157mc.getTheWorld().getBlockState(wBlockPos), wBlockPos).expand(0.0020000000949949026d, 0.0020000000949949026d, 0.0020000000949949026d).offset(-(thePlayer.getLastTickPosX() + ((thePlayer.getPosX() - thePlayer.getLastTickPosX()) * timer.getRenderPartialTicks())), -(thePlayer.getLastTickPosY() + ((thePlayer.getPosY() - thePlayer.getLastTickPosY()) * timer.getRenderPartialTicks())), -(thePlayer.getLastTickPosZ() + ((thePlayer.getPosZ() - thePlayer.getLastTickPosZ()) * timer.getRenderPartialTicks())));
        }
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        enableGlCap(SGL.GL_BLEND);
        disableGlCap(new int[]{SGL.GL_TEXTURE_2D, SGL.GL_DEPTH_TEST});
        GL11.glDepthMask(false);
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : z ? 26 : 35);
        drawFilledBox(iAxisAlignedBBCreateAxisAlignedBB);
        if (z) {
            GL11.glLineWidth(1.0f);
            enableGlCap(SGL.GL_LINE_SMOOTH);
            glColor(color);
            drawSelectionBoundingBox(iAxisAlignedBBCreateAxisAlignedBB);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawSelectionBoundingBox(IAxisAlignedBB iAxisAlignedBB) {
        ITessellator tessellatorInstance = classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        worldRenderer.begin(3, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        tessellatorInstance.draw();
    }

    public static void drawEntityBox(IEntity iEntity, Color color, boolean z) {
        IRenderManager renderManager = f157mc.getRenderManager();
        ITimer timer = f157mc.getTimer();
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        enableGlCap(SGL.GL_BLEND);
        disableGlCap(new int[]{SGL.GL_TEXTURE_2D, SGL.GL_DEPTH_TEST});
        GL11.glDepthMask(false);
        double lastTickPosX = (iEntity.getLastTickPosX() + ((iEntity.getPosX() - iEntity.getLastTickPosX()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosX();
        double lastTickPosY = (iEntity.getLastTickPosY() + ((iEntity.getPosY() - iEntity.getLastTickPosY()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosY();
        double lastTickPosZ = (iEntity.getLastTickPosZ() + ((iEntity.getPosZ() - iEntity.getLastTickPosZ()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosZ();
        IAxisAlignedBB entityBoundingBox = iEntity.getEntityBoundingBox();
        IAxisAlignedBB iAxisAlignedBBCreateAxisAlignedBB = classProvider.createAxisAlignedBB(((entityBoundingBox.getMinX() - iEntity.getPosX()) + lastTickPosX) - 0.05d, (entityBoundingBox.getMinY() - iEntity.getPosY()) + lastTickPosY, ((entityBoundingBox.getMinZ() - iEntity.getPosZ()) + lastTickPosZ) - 0.05d, (entityBoundingBox.getMaxX() - iEntity.getPosX()) + lastTickPosX + 0.05d, (entityBoundingBox.getMaxY() - iEntity.getPosY()) + lastTickPosY + 0.15d, (entityBoundingBox.getMaxZ() - iEntity.getPosZ()) + lastTickPosZ + 0.05d);
        if (z) {
            GL11.glLineWidth(1.0f);
            enableGlCap(SGL.GL_LINE_SMOOTH);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            drawSelectionBoundingBox(iAxisAlignedBBCreateAxisAlignedBB);
        }
        glColor(color.getRed(), color.getGreen(), color.getBlue(), z ? 26 : 35);
        drawFilledBox(iAxisAlignedBBCreateAxisAlignedBB);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawEntityBox(IEntity iEntity, Color color, boolean z, boolean z2, float f) {
        IRenderManager renderManager = f157mc.getRenderManager();
        ITimer timer = f157mc.getTimer();
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        enableGlCap(SGL.GL_BLEND);
        disableGlCap(new int[]{SGL.GL_TEXTURE_2D, SGL.GL_DEPTH_TEST});
        GL11.glDepthMask(false);
        double lastTickPosX = (iEntity.getLastTickPosX() + ((iEntity.getPosX() - iEntity.getLastTickPosX()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosX();
        double lastTickPosY = (iEntity.getLastTickPosY() + ((iEntity.getPosY() - iEntity.getLastTickPosY()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosY();
        double lastTickPosZ = (iEntity.getLastTickPosZ() + ((iEntity.getPosZ() - iEntity.getLastTickPosZ()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosZ();
        IAxisAlignedBB entityBoundingBox = iEntity.getEntityBoundingBox();
        IAxisAlignedBB iAxisAlignedBBCreateAxisAlignedBB = classProvider.createAxisAlignedBB(((entityBoundingBox.getMinX() - iEntity.getPosX()) + lastTickPosX) - 0.05d, (entityBoundingBox.getMinY() - iEntity.getPosY()) + lastTickPosY, ((entityBoundingBox.getMinZ() - iEntity.getPosZ()) + lastTickPosZ) - 0.05d, (entityBoundingBox.getMaxX() - iEntity.getPosX()) + lastTickPosX + 0.05d, (entityBoundingBox.getMaxY() - iEntity.getPosY()) + lastTickPosY + 0.15d, (entityBoundingBox.getMaxZ() - iEntity.getPosZ()) + lastTickPosZ + 0.05d);
        if (z) {
            GL11.glLineWidth(f);
            enableGlCap(SGL.GL_LINE_SMOOTH);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            drawSelectionBoundingBox(iAxisAlignedBBCreateAxisAlignedBB);
        }
        if (z2) {
            glColor(color.getRed(), color.getGreen(), color.getBlue(), z ? 26 : 35);
            drawFilledBox(iAxisAlignedBBCreateAxisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawAxisAlignedBB(IAxisAlignedBB iAxisAlignedBB, Color color) {
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        glColor(color);
        drawFilledBox(iAxisAlignedBB);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
    }

    public static void drawPlatform(double d, Color color, double d2) {
        double renderPosY = d - f157mc.getRenderManager().getRenderPosY();
        drawAxisAlignedBB(classProvider.createAxisAlignedBB(d2, renderPosY + 0.02d, d2, -d2, renderPosY, -d2), color);
    }

    public static void drawPlatform(IEntity iEntity, Color color) {
        IRenderManager renderManager = f157mc.getRenderManager();
        ITimer timer = f157mc.getTimer();
        IAxisAlignedBB iAxisAlignedBBOffset = iEntity.getEntityBoundingBox().offset(-iEntity.getPosX(), -iEntity.getPosY(), -iEntity.getPosZ()).offset((iEntity.getLastTickPosX() + ((iEntity.getPosX() - iEntity.getLastTickPosX()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosX(), (iEntity.getLastTickPosY() + ((iEntity.getPosY() - iEntity.getLastTickPosY()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosY(), (iEntity.getLastTickPosZ() + ((iEntity.getPosZ() - iEntity.getLastTickPosZ()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosZ());
        drawAxisAlignedBB(classProvider.createAxisAlignedBB(iAxisAlignedBBOffset.getMinX(), iAxisAlignedBBOffset.getMaxY() + 0.2d, iAxisAlignedBBOffset.getMinZ(), iAxisAlignedBBOffset.getMaxX(), iAxisAlignedBBOffset.getMaxY() + 0.26d, iAxisAlignedBBOffset.getMaxZ()), color);
    }

    public static void drawFilledBox(IAxisAlignedBB iAxisAlignedBB) {
        ITessellator tessellatorInstance = classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        worldRenderer.begin(7, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMinX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMaxY(), iAxisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(iAxisAlignedBB.getMaxX(), iAxisAlignedBB.getMinY(), iAxisAlignedBB.getMaxZ()).endVertex();
        tessellatorInstance.draw();
    }

    public static void quickDrawRect(float f, float f2, float f3, float f4) {
        GL11.glBegin(7);
        GL11.glVertex2d(f3, f2);
        GL11.glVertex2d(f, f2);
        GL11.glVertex2d(f, f4);
        GL11.glVertex2d(f3, f4);
        GL11.glEnd();
    }

    public static void drawRectPotion(float f, float f2, float f3, float f4, int i) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        glColor(i);
        GL11.glBegin(7);
        GL11.glVertex2d(f3, f2);
        GL11.glVertex2d(f, f2);
        GL11.glVertex2d(f, f4);
        GL11.glVertex2d(f3, f4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
    }

    public static void drawRect(float f, float f2, float f3, float f4, int i) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        glColor(i);
        GL11.glBegin(7);
        GL11.glVertex2f(f3, f2);
        GL11.glVertex2f(f, f2);
        GL11.glVertex2f(f, f4);
        GL11.glVertex2f(f3, f4);
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
    }

    public static void drawRect(double d, double d2, double d3, double d4, int i) {
        if (d < d3) {
            d = d3;
            d3 = d;
        }
        if (d2 < d4) {
            d2 = d4;
            d4 = d2;
        }
        ITessellator tessellatorInstance = classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.func_179131_c(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        worldRenderer.begin(7, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(d, d4, 0.0d).endVertex();
        worldRenderer.pos(d3, d4, 0.0d).endVertex();
        worldRenderer.pos(d3, d2, 0.0d).endVertex();
        worldRenderer.pos(d, d2, 0.0d).endVertex();
        tessellatorInstance.draw();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawRect(int i, int i2, int i3, int i4, int i5) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        glColor(i5);
        GL11.glBegin(7);
        GL11.glVertex2i(i3, i2);
        GL11.glVertex2i(i, i2);
        GL11.glVertex2i(i, i4);
        GL11.glVertex2i(i3, i4);
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
    }

    public static void quickDrawRect(float f, float f2, float f3, float f4, int i) {
        glColor(i);
        GL11.glBegin(7);
        GL11.glVertex2d(f3, f2);
        GL11.glVertex2d(f, f2);
        GL11.glVertex2d(f, f4);
        GL11.glVertex2d(f3, f4);
        GL11.glEnd();
    }

    public static void drawRect(float f, float f2, float f3, float f4, Color color) {
        drawRect(f, f2, f3, f4, color.getRGB());
    }

    public static void drawBorderedRect(float f, float f2, float f3, float f4, float f5, int i, int i2) {
        drawRect(f, f2, f3, f4, i2);
        drawBorder(f, f2, f3, f4, f5, i);
    }

    public static void drawBorder(float f, float f2, float f3, float f4, float f5, int i) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        glColor(i);
        GL11.glLineWidth(f5);
        GL11.glBegin(2);
        GL11.glVertex2d(f3, f2);
        GL11.glVertex2d(f, f2);
        GL11.glVertex2d(f, f4);
        GL11.glVertex2d(f3, f4);
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
    }

    public static void quickDrawBorderedRect(float f, float f2, float f3, float f4, float f5, int i, int i2) {
        quickDrawRect(f, f2, f3, f4, i2);
        glColor(i);
        GL11.glLineWidth(f5);
        GL11.glBegin(2);
        GL11.glVertex2d(f3, f2);
        GL11.glVertex2d(f, f2);
        GL11.glVertex2d(f, f4);
        GL11.glVertex2d(f3, f4);
        GL11.glEnd();
    }

    public static void drawLoadingCircle(float f, float f2) {
        for (int i = 0; i < 4; i++) {
            int iNanoTime = (int) (((System.nanoTime() / 5000000) * i) % 360);
            drawCircle(f, f2, i * 10, iNanoTime - 180, iNanoTime);
        }
    }

    public static void drawCircle(float f, float f2, float f3, int i, int i2) {
        classProvider.getGlStateManager().enableBlend();
        classProvider.getGlStateManager().disableTexture2D();
        classProvider.getGlStateManager().tryBlendFuncSeparate(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        glColor(Color.WHITE);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        float f4 = i2;
        while (true) {
            float f5 = f4;
            if (f5 >= i) {
                GL11.glVertex2f((float) (f + (Math.cos((f5 * 3.141592653589793d) / 180.0d) * f3 * 1.001f)), (float) (f2 + (Math.sin((f5 * 3.141592653589793d) / 180.0d) * f3 * 1.001f)));
                f4 = f5 - 4.0f;
            } else {
                GL11.glEnd();
                GL11.glDisable(SGL.GL_LINE_SMOOTH);
                classProvider.getGlStateManager().enableTexture2D();
                classProvider.getGlStateManager().disableBlend();
                return;
            }
        }
    }

    public static void drawFilledCircle(int i, int i2, float f, Color color) {
        GL11.glPushAttrib(8192);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glBegin(6);
        for (int i3 = 0; i3 < 50; i3++) {
            float fSin = (float) (f * Math.sin(i3 * 0.12566370614359174d));
            float fCos = (float) (f * Math.cos(i3 * 0.12566370614359174d));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(i + fSin, i2 + fCos);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawImage(IResourceLocation iResourceLocation, int i, int i2, int i3, int i4) {
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDepthMask(false);
        GL14.glBlendFuncSeparate(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        f157mc.getTextureManager().bindTexture(iResourceLocation);
        drawModalRectWithCustomSizedTexture(i, i2, 0.0f, 0.0f, i3, i4, i3, i4);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
    }

    public static void drawFilledCircle(float f, float f2, float f3, int i) {
        float f4 = ((i >> 24) & 255) / 255.0f;
        float f5 = ((i >> 16) & 255) / 255.0f;
        float f6 = ((i >> 8) & 255) / 255.0f;
        float f7 = (i & 255) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glBegin(6);
        for (int i2 = 0; i2 < 50; i2++) {
            float fSin = (float) (f3 * Math.sin(i2 * 0.12566370614359174d));
            float fCos = (float) (f3 * Math.cos(i2 * 0.12566370614359174d));
            GL11.glColor4f(f5, f6, f7, f4);
            GL11.glVertex2f(f + fSin, f2 + fCos);
        }
        GlStateManager.func_179124_c(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(int i, int i2, float f, float f2, int i3, int i4, float f3, float f4) {
        float f5 = 1.0f / f3;
        float f6 = 1.0f / f4;
        ITessellator tessellatorInstance = classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        worldRenderer.begin(7, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        worldRenderer.pos(i, i2 + i4, 0.0d).tex(f * f5, (f2 + i4) * f6).endVertex();
        worldRenderer.pos(i + i3, i2 + i4, 0.0d).tex((f + i3) * f5, (f2 + i4) * f6).endVertex();
        worldRenderer.pos(i + i3, i2, 0.0d).tex((f + i3) * f5, f2 * f6).endVertex();
        worldRenderer.pos(i, i2, 0.0d).tex(f * f5, f2 * f6).endVertex();
        tessellatorInstance.draw();
    }

    public static void glColor(int i, int i2, int i3, int i4) {
        GL11.glColor4f(i / 255.0f, i2 / 255.0f, i3 / 255.0f, i4 / 255.0f);
    }

    public static void glColor(Color color) {
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private static void glColor(int i) {
        glColor((i >> 16) & 255, (i >> 8) & 255, i & 255, (i >> 24) & 255);
    }

    public static void draw2D(IEntityLivingBase iEntityLivingBase, double d, double d2, double d3, int i, int i2) {
        GL11.glPushMatrix();
        GL11.glTranslated(d, d2, d3);
        GL11.glRotated(-f157mc.getRenderManager().getPlayerViewY(), 0.0d, 1.0d, 0.0d);
        GL11.glScaled(-0.1d, -0.1d, 0.1d);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        glColor(i);
        GL11.glCallList(DISPLAY_LISTS_2D[0]);
        glColor(i2);
        GL11.glCallList(DISPLAY_LISTS_2D[1]);
        GL11.glTranslated(0.0d, 21.0d + ((-(iEntityLivingBase.getEntityBoundingBox().getMaxY() - iEntityLivingBase.getEntityBoundingBox().getMinY())) * 12.0d), 0.0d);
        glColor(i);
        GL11.glCallList(DISPLAY_LISTS_2D[2]);
        glColor(i2);
        GL11.glCallList(DISPLAY_LISTS_2D[3]);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void draw2D(WBlockPos wBlockPos, int i, int i2) {
        IRenderManager renderManager = f157mc.getRenderManager();
        double x = (wBlockPos.getX() + 0.5d) - renderManager.getRenderPosX();
        double y = wBlockPos.getY() - renderManager.getRenderPosY();
        double z = (wBlockPos.getZ() + 0.5d) - renderManager.getRenderPosZ();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotated(-f157mc.getRenderManager().getPlayerViewY(), 0.0d, 1.0d, 0.0d);
        GL11.glScaled(-0.1d, -0.1d, 0.1d);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        glColor(i);
        GL11.glCallList(DISPLAY_LISTS_2D[0]);
        glColor(i2);
        GL11.glCallList(DISPLAY_LISTS_2D[1]);
        GL11.glTranslated(0.0d, 9.0d, 0.0d);
        glColor(i);
        GL11.glCallList(DISPLAY_LISTS_2D[2]);
        glColor(i2);
        GL11.glCallList(DISPLAY_LISTS_2D[3]);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void renderNameTag(String str, double d, double d2, double d3) {
        IRenderManager renderManager = f157mc.getRenderManager();
        GL11.glPushMatrix();
        GL11.glTranslated(d - renderManager.getRenderPosX(), d2 - renderManager.getRenderPosY(), d3 - renderManager.getRenderPosZ());
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-f157mc.getRenderManager().getPlayerViewY(), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f157mc.getRenderManager().getPlayerViewX(), 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-0.05f, -0.05f, 0.05f);
        setGlCap(2896, false);
        setGlCap(SGL.GL_DEPTH_TEST, false);
        setGlCap(SGL.GL_BLEND, true);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        int stringWidth = Fonts.font35.getStringWidth(str) / 2;
        drawRect((-stringWidth) - 1, -1, stringWidth + 1, Fonts.font35.getFontHeight(), Integer.MIN_VALUE);
        Fonts.font35.drawString(str, -stringWidth, 1.5f, Color.WHITE.getRGB(), true);
        resetCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public static void drawLine(double d, double d2, double d3, double d4, float f) {
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glLineWidth(f);
        GL11.glBegin(1);
        GL11.glVertex2d(d, d2);
        GL11.glVertex2d(d3, d4);
        GL11.glEnd();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
    }

    public static void makeScissorBox(float f, float f2, float f3, float f4) {
        int scaleFactor = classProvider.createScaledResolution(f157mc).getScaleFactor();
        GL11.glScissor((int) (f * scaleFactor), (int) ((r0.getScaledHeight() - f4) * scaleFactor), (int) ((f3 - f) * scaleFactor), (int) ((f4 - f2) * scaleFactor));
    }

    public static void resetCaps() {
        glCapMap.forEach((v0, v1) -> {
            setGlState(v0, v1);
        });
    }

    public static void enableGlCap(int i) {
        setGlCap(i, true);
    }

    public static void enableGlCap(int[] iArr) {
        for (int i : iArr) {
            setGlCap(i, true);
        }
    }

    public static void disableGlCap(int i) {
        setGlCap(i, true);
    }

    public static void disableGlCap(int[] iArr) {
        for (int i : iArr) {
            setGlCap(i, false);
        }
    }

    public static void setGlCap(int i, boolean z) {
        glCapMap.put(Integer.valueOf(i), Boolean.valueOf(GL11.glGetBoolean(i)));
        setGlState(i, z);
    }

    public static void setGlState(int i, boolean z) {
        if (z) {
            GL11.glEnable(i);
        } else {
            GL11.glDisable(i);
        }
    }

    public static void drawScaledCustomSizeModalRect(int i, int i2, float f, float f2, int i3, int i4, int i5, int i6, float f3, float f4) {
        float f5 = 1.0f / f3;
        float f6 = 1.0f / f4;
        ITessellator tessellatorInstance = classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        worldRenderer.begin(7, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        worldRenderer.pos(i, i2 + i6, 0.0d).tex(f * f5, (f2 + i4) * f6).endVertex();
        worldRenderer.pos(i + i5, i2 + i6, 0.0d).tex((f + i3) * f5, (f2 + i4) * f6).endVertex();
        worldRenderer.pos(i + i5, i2, 0.0d).tex((f + i3) * f5, f2 * f6).endVertex();
        worldRenderer.pos(i, i2, 0.0d).tex(f * f5, f2 * f6).endVertex();
        tessellatorInstance.draw();
    }
}

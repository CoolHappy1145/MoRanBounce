package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/UiUtils.class */
public class UiUtils {
    public static int width() {
        return new ScaledResolution(Minecraft.func_71410_x()).func_78326_a();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.func_71410_x()).func_78328_b();
    }

    public static void drawRect2(float f, float f2, float f3, float f4, int i, boolean z) {
        float f5 = 1.0f;
        if (z) {
            for (int i2 = 0; i2 < 2; i2++) {
                drawRect(f - f5, f2 - f5, f3 + f5, f4 + f5, new Color(0, 0, 0, 50).getRGB());
                f5 = (float) (f5 - 0.5d);
            }
        }
        drawRect(f, f2, f3, f4, i);
    }

    public static void drawImage(ResourceLocation resourceLocation, int i, int i2, int i3, int i4) {
        new ScaledResolution(Minecraft.func_71410_x());
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
        Gui.func_146110_a(i, i2, 0.0f, 0.0f, i3, i4, i3, i4);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
    }

    public static void drawImage(ResourceLocation resourceLocation, int i, int i2, int i3, int i4, Color color) {
        new ScaledResolution(Minecraft.func_71410_x());
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(color.getRed() / 255.0f, color.getBlue() / 255.0f, color.getRed() / 255.0f, 1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
        Gui.func_146110_a(i, i2, 0.0f, 0.0f, i3, i4, i3, i4);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
    }

    public static void drawOutLineRect(double d, double d2, double d3, double d4, double d5, int i, int i2) {
        drawRect(d + d5, d2 + d5, d3 - d5, d4 - d5, i);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d + d5, d2, d3 - d5, d2 + d5, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d, d2, d + d5, d4, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d3 - d5, d2, d3, d4, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d + d5, d4 - d5, d3 - d5, d4, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawRoundRect(double d, double d2, double d3, double d4, int i) {
        drawRect(d + 1.0d, d2, d3 - 1.0d, d4, i);
        drawRect(d, d2 + 1.0d, d + 1.0d, d4 - 1.0d, i);
        drawRect(d + 1.0d, d2 + 1.0d, d + 0.5d, d2 + 0.5d, i);
        drawRect(d + 1.0d, d2 + 1.0d, d + 0.5d, d2 + 0.5d, i);
        drawRect(d3 - 1.0d, d2 + 1.0d, d3 - 0.5d, d2 + 0.5d, i);
        drawRect(d3 - 1.0d, d2 + 1.0d, d3, d4 - 1.0d, i);
        drawRect(d + 1.0d, d4 - 1.0d, d + 0.5d, d4 - 0.5d, i);
        drawRect(d3 - 1.0d, d4 - 1.0d, d3 - 0.5d, d4 - 0.5d, i);
    }

    public static void drawRoundRectWithRect(double d, double d2, double d3, double d4, int i) {
        drawRect(d + 1.0d, d2, d3 - 1.0d, d4, i);
        drawRect(d, d2 + 1.0d, d + 1.0d, d4 - 1.0d, i);
        drawRect(d + 1.0d, d2 + 1.0d, d + 0.5d, d2 + 0.5d, i);
        drawRect(d + 1.0d, d2 + 1.0d, d + 0.5d, d2 + 0.5d, i);
        drawRect(d3 - 1.0d, d2 + 1.0d, d3 - 0.5d, d2 + 0.5d, i);
        drawRect(d3 - 1.0d, d2 + 1.0d, d3, d4 - 1.0d, i);
        drawRect(d + 1.0d, d4 - 1.0d, d + 0.5d, d4 - 0.5d, i);
        drawRect(d3 - 1.0d, d4 - 1.0d, d3 - 0.5d, d4 - 0.5d, i);
    }

    public static void startGlScissor(int i, int i2, int i3, int i4) {
        Minecraft minecraftFunc_71410_x = Minecraft.func_71410_x();
        int i5 = 1;
        int i6 = minecraftFunc_71410_x.field_71474_y.field_74335_Z;
        if (i6 == 0) {
            i6 = 1000;
        }
        while (i5 < i6 && minecraftFunc_71410_x.field_71443_c / (i5 + 1) >= 320 && minecraftFunc_71410_x.field_71440_d / (i5 + 1) >= 240) {
            i5++;
        }
        GL11.glPushMatrix();
        GL11.glEnable(SGL.GL_SCISSOR_TEST);
        GL11.glScissor(i * i5, minecraftFunc_71410_x.field_71440_d - ((i2 + i4) * i5), i3 * i5, i4 * i5);
    }

    public static void stopGlScissor() {
        GL11.glDisable(SGL.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
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

    public static void outlineRect(double d, double d2, double d3, double d4, double d5, int i, int i2) {
        drawRect(d + d5, d2 + d5, d3 - d5, d4 - d5, i);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d + d5, d2, d3 - d5, d2 + d5, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d, d2, d + d5, d4, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d3 - d5, d2, d3, d4, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(d + d5, d4 - d5, d3 - d5, d4, i2);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawRect(double d, double d2, double d3, double d4, int i) {
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        RenderUtils.glColor(new Color(i));
        GL11.glBegin(7);
        GL11.glVertex2d(d3, d2);
        GL11.glVertex2d(d, d2);
        GL11.glVertex2d(d, d4);
        GL11.glVertex2d(d3, d4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
    }
}

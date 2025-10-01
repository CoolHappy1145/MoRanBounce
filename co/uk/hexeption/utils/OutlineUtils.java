package co.p000uk.hexeption.utils;

import java.awt.Color;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:co/uk/hexeption/utils/OutlineUtils.class */
public class OutlineUtils {
    public static void renderOne(float f) {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(2896);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(f);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc(SGL.GL_EQUAL, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderFour(Color color) {
        setColor(color);
        GL11.glDepthMask(false);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0f, 240.0f);
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4352);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glEnable(2896);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }

    public static void setColor(Color color) {
        GL11.glColor4d(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public static void checkSetupFBO() {
        Framebuffer framebufferFunc_147110_a = Minecraft.func_71410_x().func_147110_a();
        if (framebufferFunc_147110_a != null && framebufferFunc_147110_a.field_147624_h > -1) {
            setupFBO(framebufferFunc_147110_a);
            framebufferFunc_147110_a.field_147624_h = -1;
        }
    }

    private static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.field_147624_h);
        int iGlGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, iGlGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, iGlGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, iGlGenRenderbuffersEXT);
    }
}

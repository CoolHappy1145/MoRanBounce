package net.ccbluex.liquidbounce.utils.render;

import kotlin.jvm.internal.CharCompanionObject;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/Stencil.class */
public class Stencil {

    /* renamed from: mc */
    static Minecraft f178mc = Minecraft.func_71410_x();

    public static void dispose() {
        GL11.glDisable(2960);
        GlStateManager.func_179118_c();
        GlStateManager.func_179084_k();
    }

    public static void erase(boolean z) {
        GL11.glStencilFunc(z ? SGL.GL_EQUAL : SGL.GL_NOTEQUAL, 1, CharCompanionObject.MAX_VALUE);
        GL11.glStencilOp(7680, 7680, 7681);
        GlStateManager.func_179135_a(true, true, true, true);
        GlStateManager.func_179141_d();
        GlStateManager.func_179147_l();
        GL11.glAlphaFunc(516, 0.0f);
    }

    public static void write(boolean z) {
        checkSetupFBO();
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(SGL.GL_ALWAYS, 1, CharCompanionObject.MAX_VALUE);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!z) {
            GlStateManager.func_179135_a(false, false, false, false);
        }
    }

    public static void write(boolean z, Framebuffer framebuffer) {
        checkSetupFBO(framebuffer);
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(SGL.GL_ALWAYS, 1, CharCompanionObject.MAX_VALUE);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!z) {
            GlStateManager.func_179135_a(false, false, false, false);
        }
    }

    public static void checkSetupFBO() {
        Framebuffer framebufferFunc_147110_a = f178mc.func_147110_a();
        if (framebufferFunc_147110_a != null && framebufferFunc_147110_a.field_147624_h > -1) {
            setupFBO(framebufferFunc_147110_a);
            framebufferFunc_147110_a.field_147624_h = -1;
        }
    }

    public static void checkSetupFBO(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.field_147624_h > -1) {
            setupFBO(framebuffer);
            framebuffer.field_147624_h = -1;
        }
    }

    public static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.field_147624_h);
        int iGlGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, iGlGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, iGlGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, iGlGenRenderbuffersEXT);
    }
}

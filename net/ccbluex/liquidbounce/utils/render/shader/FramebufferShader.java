package net.ccbluex.liquidbounce.utils.render.shader;

import java.awt.Color;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/shader/FramebufferShader.class */
public abstract class FramebufferShader extends Shader {
    private static Framebuffer framebuffer;
    protected float red;
    protected float green;
    protected float blue;
    protected float alpha;
    protected float radius;
    protected float quality;
    private boolean entityShadows;

    public FramebufferShader(String str) {
        super(str);
        this.alpha = 1.0f;
        this.radius = 2.0f;
        this.quality = 1.0f;
    }

    public void startDraw(float f) {
        classProvider.getGlStateManager().enableAlpha();
        classProvider.getGlStateManager().pushMatrix();
        classProvider.getGlStateManager().pushAttrib();
        framebuffer = setupFrameBuffer(framebuffer);
        framebuffer.func_147614_f();
        framebuffer.func_147610_a(true);
        this.entityShadows = f157mc.getGameSettings().getEntityShadows();
        f157mc.getGameSettings().setEntityShadows(false);
        f157mc.getEntityRenderer().setupCameraTransform(f, 0);
    }

    public void stopDraw(Color color, float f, float f2) {
        f157mc.getGameSettings().setEntityShadows(this.entityShadows);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        f157mc.getFramebuffer().bindFramebuffer(true);
        this.red = color.getRed() / 255.0f;
        this.green = color.getGreen() / 255.0f;
        this.blue = color.getBlue() / 255.0f;
        this.alpha = color.getAlpha() / 255.0f;
        this.radius = f;
        this.quality = f2;
        f157mc.getEntityRenderer().disableLightmap();
        RenderHelper.func_74518_a();
        startShader();
        f157mc.getEntityRenderer().setupOverlayRendering();
        drawFramebuffer(framebuffer);
        stopShader();
        f157mc.getEntityRenderer().disableLightmap();
        classProvider.getGlStateManager().popMatrix();
        classProvider.getGlStateManager().popAttrib();
    }

    public Framebuffer setupFrameBuffer(Framebuffer framebuffer2) {
        if (framebuffer2 != null) {
            framebuffer2.func_147608_a();
        }
        return new Framebuffer(f157mc.getDisplayWidth(), f157mc.getDisplayHeight(), true);
    }

    public void drawFramebuffer(Framebuffer framebuffer2) {
        IScaledResolution iScaledResolutionCreateScaledResolution = classProvider.createScaledResolution(f157mc);
        GL11.glBindTexture(SGL.GL_TEXTURE_2D, framebuffer2.field_147617_g);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0d, 1.0d);
        GL11.glVertex2d(0.0d, 0.0d);
        GL11.glTexCoord2d(0.0d, 0.0d);
        GL11.glVertex2d(0.0d, iScaledResolutionCreateScaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0d, 0.0d);
        GL11.glVertex2d(iScaledResolutionCreateScaledResolution.getScaledWidth(), iScaledResolutionCreateScaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0d, 1.0d);
        GL11.glVertex2d(iScaledResolutionCreateScaledResolution.getScaledWidth(), 0.0d);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
}

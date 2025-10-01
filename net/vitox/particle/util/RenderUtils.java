package net.vitox.particle.util;

import net.ccbluex.liquidbounce.utils.Skid.SGL;
import org.lwjgl.opengl.GL11;

/* loaded from: L-out.jar:net/vitox/particle/util/RenderUtils.class */
public class RenderUtils {
    public static void connectPoints(float f, float f2, float f3, float f4) {
        GL11.glPushMatrix();
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glLineWidth(0.5f);
        GL11.glBegin(1);
        GL11.glVertex2f(f, f2);
        GL11.glVertex2f(f3, f4);
        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float f, float f2, float f3, int i) {
        GL11.glColor4f(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glLineWidth(1.0f);
        GL11.glBegin(9);
        for (int i2 = 0; i2 <= 360; i2++) {
            GL11.glVertex2d(f + (Math.sin((i2 * 3.141592653589793d) / 180.0d) * f3), f2 + (Math.cos((i2 * 3.141592653589793d) / 180.0d) * f3));
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

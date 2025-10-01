package net.ccbluex.liquidbounce.utils.Skid;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.EXTSecondaryColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/ImmediateModeOGLRenderer.class */
public class ImmediateModeOGLRenderer implements SGL {
    private int width;
    private int height;
    private final float[] current = {1.0f, 1.0f, 1.0f, 1.0f};
    protected float alphaScale = 1.0f;

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void initDisplay(int i, int i2) {
        this.width = i;
        this.height = i2;
        GL11.glGetString(7939);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glShadeModel(7425);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0d);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, i, i2);
        GL11.glMatrixMode(5888);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void enterOrtho(int i, int i2) {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0d, this.width, this.height, 0.0d, 1.0d, -1.0d);
        GL11.glMatrixMode(5888);
        GL11.glTranslatef((this.width - i) / 2, (this.height - i2) / 2, 0.0f);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glBegin(int i) {
        GL11.glBegin(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glBindTexture(int i, int i2) {
        GL11.glBindTexture(i, i2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glBlendFunc(int i, int i2) {
        GL11.glBlendFunc(i, i2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glCallList(int i) {
        GL11.glCallList(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glClear(int i) {
        GL11.glClear(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glClearColor(float f, float f2, float f3, float f4) {
        GL11.glClearColor(f, f2, f3, f4);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glClipPlane(int i, DoubleBuffer doubleBuffer) {
        GL11.glClipPlane(i, doubleBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glColor4f(float f, float f2, float f3, float f4) {
        float f5 = f4 * this.alphaScale;
        this.current[0] = f;
        this.current[1] = f2;
        this.current[2] = f3;
        this.current[3] = f5;
        GL11.glColor4f(f, f2, f3, f5);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glColorMask(boolean z, boolean z2, boolean z3, boolean z4) {
        GL11.glColorMask(z, z2, z3, z4);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glCopyTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        GL11.glCopyTexImage2D(i, i2, i3, i4, i5, i6, i7, i8);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glDeleteTextures(IntBuffer intBuffer) {
        GL11.glDeleteTextures(intBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glDisable(int i) {
        GL11.glDisable(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glEnable(int i) {
        GL11.glEnable(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glEnd() {
        GL11.glEnd();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glEndList() {
        GL11.glEndList();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public int glGenLists(int i) {
        return GL11.glGenLists(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glGetFloat(int i, FloatBuffer floatBuffer) {
        GL11.glGetFloat(i, floatBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glGetInteger(int i, IntBuffer intBuffer) {
        GL11.glGetInteger(i, intBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glGetTexImage(int i, int i2, int i3, int i4, ByteBuffer byteBuffer) {
        GL11.glGetTexImage(i, i2, i3, i4, byteBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glLineWidth(float f) {
        GL11.glLineWidth(f);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glLoadIdentity() {
        GL11.glLoadIdentity();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glNewList(int i, int i2) {
        GL11.glNewList(i, i2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glPointSize(float f) {
        GL11.glPointSize(f);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glPopMatrix() {
        GL11.glPopMatrix();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glPushMatrix() {
        GL11.glPushMatrix();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glReadPixels(int i, int i2, int i3, int i4, int i5, int i6, ByteBuffer byteBuffer) {
        GL11.glReadPixels(i, i2, i3, i4, i5, i6, byteBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glRotatef(float f, float f2, float f3, float f4) {
        GL11.glRotatef(f, f2, f3, f4);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glScalef(float f, float f2, float f3) {
        GL11.glScalef(f, f2, f3);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glScissor(int i, int i2, int i3, int i4) {
        GL11.glScissor(i, i2, i3, i4);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexCoord2f(float f, float f2) {
        GL11.glTexCoord2f(f, f2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexEnvi(int i, int i2, int i3) {
        GL11.glTexEnvi(i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTranslatef(float f, float f2, float f3) {
        GL11.glTranslatef(f, f2, f3);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glVertex2f(float f, float f2) {
        GL11.glVertex2f(f, f2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glVertex3f(float f, float f2, float f3) {
        GL11.glVertex3f(f, f2, f3);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexParameteri(int i, int i2, int i3) {
        GL11.glTexParameteri(i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public float[] getCurrentColor() {
        return this.current;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glDeleteLists(int i, int i2) {
        GL11.glDeleteLists(i, i2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glClearDepth(float f) {
        GL11.glClearDepth(f);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glDepthFunc(int i) {
        GL11.glDepthFunc(i);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glDepthMask(boolean z) {
        GL11.glDepthMask(z);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void setGlobalAlphaScale(float f) {
        this.alphaScale = f;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glLoadMatrix(FloatBuffer floatBuffer) {
        GL11.glLoadMatrix(floatBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glGenTextures(IntBuffer intBuffer) {
        GL11.glGenTextures(intBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glGetError() {
        GL11.glGetError();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ByteBuffer byteBuffer) {
        GL11.glTexImage2D(i, i2, i3, i4, i5, i6, i7, i8, byteBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ByteBuffer byteBuffer) {
        GL11.glTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, byteBuffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public boolean canTextureMirrorClamp() {
        return GLContext.getCapabilities().GL_EXT_texture_mirror_clamp;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public boolean canSecondaryColor() {
        return GLContext.getCapabilities().GL_EXT_secondary_color;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glSecondaryColor3ubEXT(byte b, byte b2, byte b3) {
        EXTSecondaryColor.glSecondaryColor3ubEXT(b, b2, b3);
    }
}

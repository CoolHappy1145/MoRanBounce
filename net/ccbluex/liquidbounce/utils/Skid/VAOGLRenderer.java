package net.ccbluex.liquidbounce.utils.Skid;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import org.apache.log4j.Priority;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/VAOGLRenderer.class */
public class VAOGLRenderer extends ImmediateModeOGLRenderer {
    private static final int TOLERANCE = 20;
    public static final int NONE = -1;
    public static final int MAX_VERTS = 5000;
    private int vertIndex;
    private int currentType = -1;
    private final float[] color = {1.0f, 1.0f, 1.0f, 1.0f};
    private final float[] tex = {0.0f, 0.0f};
    private final float[] verts = new float[15000];
    private final float[] cols = new float[Priority.INFO_INT];
    private final float[] texs = new float[15000];
    private final FloatBuffer vertices = BufferUtils.createFloatBuffer(15000);
    private final FloatBuffer colors = BufferUtils.createFloatBuffer(Priority.INFO_INT);
    private final FloatBuffer textures = BufferUtils.createFloatBuffer(10000);
    private int listMode = 0;

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void initDisplay(int width, int height) {
        super.initDisplay(width, height);
        startBuffer();
        GL11.glEnableClientState(32884);
        GL11.glEnableClientState(32888);
        GL11.glEnableClientState(32886);
    }

    private void startBuffer() {
        this.vertIndex = 0;
    }

    private void flushBuffer() {
        if (this.vertIndex == 0 || this.currentType == -1) {
            return;
        }
        if (this.vertIndex < 20) {
            GL11.glBegin(this.currentType);
            for (int i = 0; i < this.vertIndex; i++) {
                GL11.glColor4f(this.cols[(i * 4) + 0], this.cols[(i * 4) + 1], this.cols[(i * 4) + 2], this.cols[(i * 4) + 3]);
                GL11.glTexCoord2f(this.texs[(i * 2) + 0], this.texs[(i * 2) + 1]);
                GL11.glVertex3f(this.verts[(i * 3) + 0], this.verts[(i * 3) + 1], this.verts[(i * 3) + 2]);
            }
            GL11.glEnd();
            this.currentType = -1;
            return;
        }
        this.vertices.clear();
        this.colors.clear();
        this.textures.clear();
        this.vertices.put(this.verts, 0, this.vertIndex * 3);
        this.colors.put(this.cols, 0, this.vertIndex * 4);
        this.textures.put(this.texs, 0, this.vertIndex * 2);
        this.vertices.flip();
        this.colors.flip();
        this.textures.flip();
        GL11.glVertexPointer(3, 0, this.vertices);
        GL11.glColorPointer(4, 0, this.colors);
        GL11.glTexCoordPointer(2, 0, this.textures);
        GL11.glDrawArrays(this.currentType, 0, this.vertIndex);
        this.currentType = -1;
    }

    private void applyBuffer() {
        if (this.listMode > 0) {
            return;
        }
        if (this.vertIndex != 0) {
            flushBuffer();
            startBuffer();
        }
        super.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.SGL
    public void flush() {
        super.flush();
        applyBuffer();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glBegin(int geomType) {
        if (this.listMode > 0) {
            super.glBegin(geomType);
        } else if (this.currentType != geomType) {
            applyBuffer();
            this.currentType = geomType;
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glColor4f(float r, float g, float b, float a) {
        float a2 = a * this.alphaScale;
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
        this.color[3] = a2;
        if (this.listMode > 0) {
            super.glColor4f(r, g, b, a2);
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glEnd() {
        if (this.listMode > 0) {
            super.glEnd();
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexCoord2f(float u, float v) {
        if (this.listMode > 0) {
            super.glTexCoord2f(u, v);
        } else {
            this.tex[0] = u;
            this.tex[1] = v;
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glVertex2f(float x, float y) {
        if (this.listMode > 0) {
            super.glVertex2f(x, y);
        } else {
            glVertex3f(x, y, 0.0f);
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glVertex3f(float x, float y, float z) {
        if (this.listMode > 0) {
            super.glVertex3f(x, y, z);
            return;
        }
        this.verts[(this.vertIndex * 3) + 0] = x;
        this.verts[(this.vertIndex * 3) + 1] = y;
        this.verts[(this.vertIndex * 3) + 2] = z;
        this.cols[(this.vertIndex * 4) + 0] = this.color[0];
        this.cols[(this.vertIndex * 4) + 1] = this.color[1];
        this.cols[(this.vertIndex * 4) + 2] = this.color[2];
        this.cols[(this.vertIndex * 4) + 3] = this.color[3];
        this.texs[(this.vertIndex * 2) + 0] = this.tex[0];
        this.texs[(this.vertIndex * 2) + 1] = this.tex[1];
        this.vertIndex++;
        if (this.vertIndex > 4950 && isSplittable(this.vertIndex, this.currentType)) {
            int type = this.currentType;
            applyBuffer();
            this.currentType = type;
        }
    }

    private boolean isSplittable(int count, int type) {
        switch (type) {
            case 4:
                if (count % 3 == 0) {
                }
                break;
            case 7:
                if (count % 4 == 0) {
                }
                break;
            case 6913:
                if (count % 2 == 0) {
                }
                break;
        }
        return false;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glBindTexture(int target, int id) {
        applyBuffer();
        super.glBindTexture(target, id);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glBlendFunc(int src, int dest) {
        applyBuffer();
        super.glBlendFunc(src, dest);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glCallList(int id) {
        applyBuffer();
        super.glCallList(id);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glClear(int value) {
        applyBuffer();
        super.glClear(value);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glClipPlane(int plane, DoubleBuffer buffer) {
        applyBuffer();
        super.glClipPlane(plane, buffer);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        applyBuffer();
        super.glColorMask(red, green, blue, alpha);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glDisable(int item) {
        applyBuffer();
        super.glDisable(item);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glEnable(int item) {
        applyBuffer();
        super.glEnable(item);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glLineWidth(float width) {
        applyBuffer();
        super.glLineWidth(width);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glPointSize(float size) {
        applyBuffer();
        super.glPointSize(size);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glPopMatrix() {
        applyBuffer();
        super.glPopMatrix();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glPushMatrix() {
        applyBuffer();
        super.glPushMatrix();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glRotatef(float angle, float x, float y, float z) {
        applyBuffer();
        super.glRotatef(angle, x, y, z);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glScalef(float x, float y, float z) {
        applyBuffer();
        super.glScalef(x, y, z);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glScissor(int x, int y, int width, int height) {
        applyBuffer();
        super.glScissor(x, y, width, height);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTexEnvi(int target, int mode, int value) {
        applyBuffer();
        super.glTexEnvi(target, mode, value);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glTranslatef(float x, float y, float z) {
        applyBuffer();
        super.glTranslatef(x, y, z);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glEndList() {
        this.listMode--;
        super.glEndList();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glNewList(int id, int option) {
        this.listMode++;
        super.glNewList(id, option);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public float[] getCurrentColor() {
        return this.color;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.ImmediateModeOGLRenderer, net.ccbluex.liquidbounce.utils.Skid.SGL
    public void glLoadMatrix(FloatBuffer buffer) {
        flushBuffer();
        super.glLoadMatrix(buffer);
    }
}

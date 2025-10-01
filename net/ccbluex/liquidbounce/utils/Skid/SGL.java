package net.ccbluex.liquidbounce.utils.Skid;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/SGL.class */
public interface SGL {
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_RGBA = 6408;
    public static final int GL_RGB = 6407;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_LINEAR = 9729;
    public static final int GL_NEAREST = 9728;
    public static final int GL_TEXTURE_MIN_FILTER = 10241;
    public static final int GL_TEXTURE_MAG_FILTER = 10240;
    public static final int GL_POINT_SMOOTH = 2832;
    public static final int GL_POLYGON_SMOOTH = 2881;
    public static final int GL_LINE_SMOOTH = 2848;
    public static final int GL_SCISSOR_TEST = 3089;
    public static final int GL_MODULATE = 8448;
    public static final int GL_TEXTURE_ENV = 8960;
    public static final int GL_TEXTURE_ENV_MODE = 8704;
    public static final int GL_QUADS = 7;
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_SRC_ALPHA = 770;
    public static final int GL_ONE = 1;
    public static final int GL_ONE_MINUS_DST_ALPHA = 773;
    public static final int GL_DST_ALPHA = 772;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static final int GL_COMPILE = 4864;
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_BLEND = 3042;
    public static final int GL_COLOR_CLEAR_VALUE = 3106;
    public static final int GL_LINE_WIDTH = 2849;
    public static final int GL_CLIP_PLANE0 = 12288;
    public static final int GL_CLIP_PLANE1 = 12289;
    public static final int GL_CLIP_PLANE2 = 12290;
    public static final int GL_CLIP_PLANE3 = 12291;
    public static final int GL_COMPILE_AND_EXECUTE = 4865;
    public static final int GL_RGBA8 = 6408;
    public static final int GL_RGBA16 = 32859;
    public static final int GL_BGRA = 32993;
    public static final int GL_MIRROR_CLAMP_TO_EDGE_EXT = 34627;
    public static final int GL_TEXTURE_WRAP_S = 10242;
    public static final int GL_TEXTURE_WRAP_T = 10243;
    public static final int GL_CLAMP = 10496;
    public static final int GL_COLOR_SUM_EXT = 33880;
    public static final int GL_ALWAYS = 519;
    public static final int GL_DEPTH_TEST = 2929;
    public static final int GL_NOTEQUAL = 517;
    public static final int GL_EQUAL = 514;
    public static final int GL_SRC_COLOR = 768;
    public static final int GL_ONE_MINUS_SRC_COLOR = 769;
    public static final int GL_MODELVIEW_MATRIX = 2982;

    void flush();

    void initDisplay(int i, int i2);

    void enterOrtho(int i, int i2);

    void glClearColor(float f, float f2, float f3, float f4);

    void glClipPlane(int i, DoubleBuffer doubleBuffer);

    void glScissor(int i, int i2, int i3, int i4);

    void glLineWidth(float f);

    void glClear(int i);

    void glColorMask(boolean z, boolean z2, boolean z3, boolean z4);

    void glLoadIdentity();

    void glGetInteger(int i, IntBuffer intBuffer);

    void glGetFloat(int i, FloatBuffer floatBuffer);

    void glEnable(int i);

    void glDisable(int i);

    void glBindTexture(int i, int i2);

    void glGetTexImage(int i, int i2, int i3, int i4, ByteBuffer byteBuffer);

    void glDeleteTextures(IntBuffer intBuffer);

    void glColor4f(float f, float f2, float f3, float f4);

    void glTexCoord2f(float f, float f2);

    void glVertex3f(float f, float f2, float f3);

    void glVertex2f(float f, float f2);

    void glRotatef(float f, float f2, float f3, float f4);

    void glTranslatef(float f, float f2, float f3);

    void glBegin(int i);

    void glEnd();

    void glTexEnvi(int i, int i2, int i3);

    void glPointSize(float f);

    void glScalef(float f, float f2, float f3);

    void glPushMatrix();

    void glPopMatrix();

    void glBlendFunc(int i, int i2);

    int glGenLists(int i);

    void glNewList(int i, int i2);

    void glEndList();

    void glCallList(int i);

    void glCopyTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    void glReadPixels(int i, int i2, int i3, int i4, int i5, int i6, ByteBuffer byteBuffer);

    void glTexParameteri(int i, int i2, int i3);

    float[] getCurrentColor();

    void glDeleteLists(int i, int i2);

    void glDepthMask(boolean z);

    void glClearDepth(float f);

    void glDepthFunc(int i);

    void setGlobalAlphaScale(float f);

    void glLoadMatrix(FloatBuffer floatBuffer);

    void glGenTextures(IntBuffer intBuffer);

    void glGetError();

    void glTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ByteBuffer byteBuffer);

    void glTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ByteBuffer byteBuffer);

    boolean canTextureMirrorClamp();

    boolean canSecondaryColor();

    void glSecondaryColor3ubEXT(byte b, byte b2, byte b3);
}

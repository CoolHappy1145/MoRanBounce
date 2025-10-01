package net.ccbluex.liquidbounce.utils.Skid;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/LineStripRenderer.class */
public interface LineStripRenderer {
    boolean applyGLLineFixes();

    void start();

    void end();

    void vertex(float f, float f2);

    void color(float f, float f2, float f3, float f4);

    void setWidth(float f);

    void setAntiAlias(boolean z);

    void setLineCaps(boolean z);
}

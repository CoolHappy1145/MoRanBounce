package net.ccbluex.liquidbounce.utils.Skid;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/DefaultLineStripRenderer.class */
public class DefaultLineStripRenderer implements LineStripRenderer {

    /* renamed from: GL */
    private final SGL f161GL = Renderer.get();

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void end() {
        this.f161GL.glEnd();
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void setAntiAlias(boolean z) {
        if (z) {
            this.f161GL.glEnable(SGL.GL_LINE_SMOOTH);
        } else {
            this.f161GL.glDisable(SGL.GL_LINE_SMOOTH);
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void setWidth(float f) {
        this.f161GL.glLineWidth(f);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void start() {
        this.f161GL.glBegin(3);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void vertex(float f, float f2) {
        this.f161GL.glVertex2f(f, f2);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void color(float f, float f2, float f3, float f4) {
        this.f161GL.glColor4f(f, f2, f3, f4);
    }
}

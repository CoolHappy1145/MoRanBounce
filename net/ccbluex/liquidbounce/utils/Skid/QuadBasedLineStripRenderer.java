package net.ccbluex.liquidbounce.utils.Skid;

import org.apache.log4j.Priority;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/QuadBasedLineStripRenderer.class */
public class QuadBasedLineStripRenderer implements LineStripRenderer {
    private boolean antialias;
    private int pts;
    private int cpt;
    private boolean renderHalf;

    /* renamed from: GL */
    private final SGL f170GL = Renderer.get();
    private float width = 1.0f;
    private final DefaultLineStripRenderer def = new DefaultLineStripRenderer();
    private boolean lineCaps = false;
    private final float[] points = new float[Priority.INFO_INT];
    private final float[] colours = new float[Priority.ERROR_INT];

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void setLineCaps(boolean z) {
        this.lineCaps = z;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void start() {
        if (this.width == 1.0f) {
            this.def.start();
            return;
        }
        this.pts = 0;
        this.cpt = 0;
        this.f170GL.flush();
        float[] currentColor = this.f170GL.getCurrentColor();
        color(currentColor[0], currentColor[1], currentColor[2], currentColor[3]);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void end() {
        if (this.width == 1.0f) {
            this.def.end();
        } else {
            renderLines(this.points, this.pts);
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void vertex(float f, float f2) {
        if (this.width == 1.0f) {
            this.def.vertex(f, f2);
            return;
        }
        this.points[this.pts * 2] = f;
        this.points[(this.pts * 2) + 1] = f2;
        this.pts++;
        int i = this.pts - 1;
        color(this.colours[i * 4], this.colours[(i * 4) + 1], this.colours[(i * 4) + 2], this.colours[(i * 4) + 3]);
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void setWidth(float f) {
        this.width = f;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void setAntiAlias(boolean z) {
        this.def.setAntiAlias(z);
        this.antialias = z;
    }

    public void renderLines(float[] fArr, int i) {
        if (this.antialias) {
            this.f170GL.glEnable(SGL.GL_POLYGON_SMOOTH);
            renderLinesImpl(fArr, i, this.width + 1.0f);
        }
        this.f170GL.glDisable(SGL.GL_POLYGON_SMOOTH);
        renderLinesImpl(fArr, i, this.width);
        if (this.antialias) {
            this.f170GL.glEnable(SGL.GL_POLYGON_SMOOTH);
        }
    }

    public void renderLinesImpl(float[] fArr, int i, float f) {
        float f2 = f / 2.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        this.f170GL.glBegin(7);
        for (int i2 = 0; i2 < i + 1; i2++) {
            int i3 = i2;
            int i4 = i2 + 1;
            int i5 = i2 - 1;
            if (i5 < 0) {
                i5 += i;
            }
            if (i4 >= i) {
                i4 -= i;
            }
            if (i3 >= i) {
                i3 -= i;
            }
            float f7 = fArr[i3 * 2];
            float f8 = fArr[(i3 * 2) + 1];
            float f9 = fArr[i4 * 2];
            float f10 = fArr[(i4 * 2) + 1];
            float f11 = f9 - f7;
            float f12 = f10 - f8;
            if (f11 != 0.0f || f12 != 0.0f) {
                float fSqrt = (float) Math.sqrt((f11 * f11) + (f12 * f12));
                float f13 = f11 * f2;
                float f14 = f12 * f2;
                float f15 = f13 / fSqrt;
                float f16 = f14 / fSqrt;
                float f17 = -f15;
                if (i2 != 0) {
                    bindColor(i5);
                    this.f170GL.glVertex3f(f3, f4, 0.0f);
                    this.f170GL.glVertex3f(f5, f6, 0.0f);
                    bindColor(i3);
                    this.f170GL.glVertex3f(f7 + f16, f8 + f17, 0.0f);
                    this.f170GL.glVertex3f(f7 - f16, f8 - f17, 0.0f);
                }
                f3 = f9 - f16;
                f4 = f10 - f17;
                f5 = f9 + f16;
                f6 = f10 + f17;
                if (i2 < i - 1) {
                    bindColor(i3);
                    this.f170GL.glVertex3f(f7 + f16, f8 + f17, 0.0f);
                    this.f170GL.glVertex3f(f7 - f16, f8 - f17, 0.0f);
                    bindColor(i4);
                    this.f170GL.glVertex3f(f9 - f16, f10 - f17, 0.0f);
                    this.f170GL.glVertex3f(f9 + f16, f10 + f17, 0.0f);
                }
            }
        }
        this.f170GL.glEnd();
        float fCeil = f2 <= 12.5f ? 5.0f : 180.0f / ((float) Math.ceil(f2 / 2.5d));
        if (this.lineCaps) {
            float f18 = fArr[2] - fArr[0];
            float f19 = fArr[3] - fArr[1];
            float degrees = ((float) Math.toDegrees(Math.atan2(f19, f18))) + 90.0f;
            if (f18 != 0.0f || f19 != 0.0f) {
                this.f170GL.glBegin(6);
                bindColor(0);
                this.f170GL.glVertex2f(fArr[0], fArr[1]);
                int i6 = 0;
                while (true) {
                    int i7 = i6;
                    if (i7 >= 180.0f + fCeil) {
                        break;
                    }
                    float radians = (float) Math.toRadians(degrees + i7);
                    this.f170GL.glVertex2f(fArr[0] + ((float) (Math.cos(radians) * f2)), fArr[1] + ((float) (Math.sin(radians) * f2)));
                    i6 = (int) (i7 + fCeil);
                }
                this.f170GL.glEnd();
            }
        }
        if (this.lineCaps) {
            float f20 = fArr[(i * 2) - 2] - fArr[(i * 2) - 4];
            float f21 = fArr[(i * 2) - 1] - fArr[(i * 2) - 3];
            float degrees2 = ((float) Math.toDegrees(Math.atan2(f21, f20))) - 90.0f;
            if (f20 != 0.0f || f21 != 0.0f) {
                this.f170GL.glBegin(6);
                bindColor(i - 1);
                this.f170GL.glVertex2f(fArr[(i * 2) - 2], fArr[(i * 2) - 1]);
                int i8 = 0;
                while (true) {
                    int i9 = i8;
                    if (i9 < 180.0f + fCeil) {
                        float radians2 = (float) Math.toRadians(degrees2 + i9);
                        this.f170GL.glVertex2f(fArr[(i * 2) - 2] + ((float) (Math.cos(radians2) * f2)), fArr[(i * 2) - 1] + ((float) (Math.sin(radians2) * f2)));
                        i8 = (int) (i9 + fCeil);
                    } else {
                        this.f170GL.glEnd();
                        return;
                    }
                }
            }
        }
    }

    private void bindColor(int i) {
        if (i < this.cpt) {
            if (this.renderHalf) {
                this.f170GL.glColor4f(this.colours[i * 4] * 0.5f, this.colours[(i * 4) + 1] * 0.5f, this.colours[(i * 4) + 2] * 0.5f, this.colours[(i * 4) + 3] * 0.5f);
            } else {
                this.f170GL.glColor4f(this.colours[i * 4], this.colours[(i * 4) + 1], this.colours[(i * 4) + 2], this.colours[(i * 4) + 3]);
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public void color(float f, float f2, float f3, float f4) {
        if (this.width == 1.0f) {
            this.def.color(f, f2, f3, f4);
            return;
        }
        this.colours[this.pts * 4] = f;
        this.colours[(this.pts * 4) + 1] = f2;
        this.colours[(this.pts * 4) + 2] = f3;
        this.colours[(this.pts * 4) + 3] = f4;
        this.cpt++;
    }

    @Override // net.ccbluex.liquidbounce.utils.Skid.LineStripRenderer
    public boolean applyGLLineFixes() {
        if (this.width == 1.0f) {
            DefaultLineStripRenderer defaultLineStripRenderer = this.def;
            return true;
        }
        DefaultLineStripRenderer defaultLineStripRenderer2 = this.def;
        return true;
    }
}

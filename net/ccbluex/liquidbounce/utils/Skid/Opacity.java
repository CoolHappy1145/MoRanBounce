package net.ccbluex.liquidbounce.utils.Skid;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/Opacity.class */
public class Opacity {
    private float opacity;
    private long lastMS = System.currentTimeMillis();

    public Opacity(int i) {
        this.opacity = i;
    }

    public void interpolate(float f) {
        float f2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.lastMS;
        this.lastMS = jCurrentTimeMillis;
        float f3 = this.opacity;
        long j2 = j;
        float f4 = f3 - f;
        if (j2 < 1) {
            j2 = 1;
        }
        if (f4 > 20) {
            f2 = (float) (f3 - (((double) ((((long) 20) * j2) / 16)) < 0.25d ? 0.5d : (20 * j2) / 16));
            if (f2 < f) {
                f2 = f;
            }
        } else if (f4 < (-20)) {
            f2 = (float) (f3 + (((double) ((((long) 20) * j2) / 16)) < 0.25d ? 0.5d : (20 * j2) / 16));
            if (f2 > f) {
                f2 = f;
            }
        } else {
            f2 = f;
        }
        this.opacity = f2;
    }

    public void interp(float f, int i) {
        float f2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.lastMS;
        this.lastMS = jCurrentTimeMillis;
        float f3 = this.opacity;
        long j2 = j;
        float f4 = f3 - f;
        if (j2 < 1) {
            j2 = 1;
        }
        if (f4 > i) {
            f2 = (float) (f3 - (((double) ((((long) i) * j2) / 16)) < 0.25d ? 0.5d : (i * j2) / 16));
            if (f2 < f) {
                f2 = f;
            }
        } else if (f4 < (-i)) {
            f2 = (float) (f3 + (((double) ((((long) i) * j2) / 16)) < 0.25d ? 0.5d : (i * j2) / 16));
            if (f2 > f) {
                f2 = f;
            }
        } else {
            f2 = f;
        }
        this.opacity = f2;
    }

    public float getOpacity() {
        return (int) this.opacity;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }
}

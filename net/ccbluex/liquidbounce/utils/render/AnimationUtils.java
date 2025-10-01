package net.ccbluex.liquidbounce.utils.render;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/AnimationUtils.class */
public class AnimationUtils {
    public static double animate(double d, double d2, double d3) {
        if (d2 == d) {
            return d2;
        }
        boolean z = d > d2;
        if (d3 < 0.0d) {
            d3 = 0.0d;
        } else if (d3 > 1.0d) {
            d3 = 1.0d;
        }
        double dMax = (Math.max(d, d2) - Math.min(d, d2)) * d3;
        if (dMax < 0.1d) {
            dMax = 0.1d;
        }
        if (z) {
            d2 += dMax;
            if (d2 >= d) {
                d2 = d;
            }
        } else if (d < d2) {
            d2 -= dMax;
            if (d2 <= d) {
                d2 = d;
            }
        }
        return d2;
    }

    public static float animate(float f, float f2, float f3) {
        if (f2 == f) {
            return f2;
        }
        boolean z = f > f2;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        double dMax = (Math.max(f, f2) - Math.min(f, f2)) * f3;
        if (dMax < 0.1d) {
            dMax = 0.1d;
        }
        if (z) {
            f2 += (float) dMax;
            if (f2 >= f) {
                f2 = f;
            }
        } else if (f < f2) {
            f2 -= (float) dMax;
            if (f2 <= f) {
                f2 = f;
            }
        }
        return f2;
    }
}

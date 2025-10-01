package net.ccbluex.liquidbounce.utils.Skid;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/AnimationUtil.class */
public class AnimationUtil {
    public static float moveTowards(float f, float f2, float f3, float f4) {
        float fMax = (f2 - f) * f3;
        if (fMax > 0.0f) {
            fMax = Math.min(f2 - f, Math.max(f4, fMax));
        } else if (fMax < 0.0f) {
            fMax = Math.max(f2 - f, Math.min(-f4, fMax));
        }
        return f + fMax;
    }

    public static float animate(float f, float f2, double d) {
        boolean z = f > f2;
        if (d < 0.0d) {
            d = 0.0d;
        } else if (d > 1.0d) {
            d = 1.0d;
        }
        float fMax = (float) ((Math.max(f, f2) - Math.min(f, f2)) * d);
        return z ? f2 + fMax : f2 - fMax;
    }

    public static double animate(double d, double d2, double d3) {
        boolean z = d > d2;
        if (d3 < 0.0d) {
            d3 = 0.0d;
        } else if (d3 > 1.0d) {
            d3 = 1.0d;
        }
        double dMax = (Math.max(d, d2) - Math.min(d, d2)) * d3;
        if (dMax < 0.10000000149011612d) {
            dMax = 0.10000000149011612d;
        }
        return z ? d2 + dMax : d2 - dMax;
    }

    public static float mvoeUD(float f, float f2, float f3) {
        return moveUD(f, f2, 0.125f, f3);
    }

    public static float moveUD(float f, float f2, float f3, float f4) {
        float fMax = (f2 - f) * f3;
        if (fMax > 10.0f) {
            fMax = Math.min(f2 - f, Math.max(f4, fMax));
        } else if (fMax < 10.0f) {
            fMax = Math.max(f2 - f, Math.min(-f4, fMax));
        }
        return f + fMax;
    }
}

package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/ColorManager.class */
public class ColorManager {
    public static int getRainbow(int i, int i2) {
        return Color.getHSBColor(((System.currentTimeMillis() + i2) % i) / i, 0.8f, 1.0f).getRGB();
    }

    public static int getRainbow2(int i, int i2) {
        return Color.getHSBColor(((System.currentTimeMillis() + i2) % i) / i, 0.8f, 0.8f).getRGB();
    }

    public static int fluxRainbow(int i, long j, float f) {
        return Color.getHSBColor((float) ((Math.ceil(((System.currentTimeMillis() + j) / 8) + (i / 20.0d)) % 360.0d) / 360.0d), f, 1.0f).getRGB();
    }

    public static int Astolfo(int i) {
        double dCeil = (Math.ceil(System.currentTimeMillis() + (i * 109)) / 5.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (dCeil / 360.0d))) < 0.5d ? -((float) (dCeil / 360.0d)) : (float) (dCeil / 360.0d), 0.5f, 1.0f).getRGB();
    }

    public static Color astolfoRainbow(int i, float f, float f2, double d) {
        double dCeil = (Math.ceil((System.currentTimeMillis() / d) + (i * 109)) / 5.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (dCeil / 360.0d))) < 0.5d ? -((float) (dCeil / 360.0d)) : (float) (dCeil / 360.0d), f2, f);
    }

    public static Color getChromaRainbow(double d, double d2) {
        return new Color(Color.HSBtoRGB(((float) (((System.currentTimeMillis() - ((d * 10.0d) * 1.0d)) - ((d2 * 10.0d) * 1.0d)) % 2000.0d)) / 2000.0f, 0.8f, 0.8f));
    }

    /* renamed from: as */
    public static int m48as() {
        int[] iArr = {0};
        iArr[0] = iArr[0] + 1;
        return getRainbow3(iArr[0] * 20);
    }

    public static int getRainbow3(int i) {
        return Color.getHSBColor(-((float) (Math.ceil((System.currentTimeMillis() + (i * 2)) / 5) / 360.0d)), 0.5f, 1.0f).getRGB();
    }

    public static int astolfoRainbow(int i, int i2, int i3) {
        double dCeil = (Math.ceil(System.currentTimeMillis() + (i * i3)) / i2) % 360.0d;
        return Color.getHSBColor(((double) ((float) (dCeil / 360.0d))) < 0.5d ? -((float) (dCeil / 360.0d)) : (float) (dCeil / 360.0d), 0.5f, 1.0f).getRGB();
    }

    public static int getNewRainbow(int i, long j, float f) {
        return Color.getHSBColor((float) ((Math.ceil(((System.currentTimeMillis() + j) / 8) + (i / 20.0d)) % 360.0d) / 360.0d), f, 1.0f).getRGB();
    }

    public static Color rainbow(long j, float f, float f2) {
        Color color = new Color((int) Long.parseLong(Integer.toHexString(Color.HSBtoRGB(((j + ((1.0f + f) * 2.0E8f)) / 1.0E10f) % 1.0f, 1.0f, 1.0f)), 16));
        return new Color((color.getRed() / 255.0f) * f2, (color.getGreen() / 255.0f) * f2, (color.getBlue() / 255.0f) * f2, color.getAlpha() / 255.0f);
    }
}

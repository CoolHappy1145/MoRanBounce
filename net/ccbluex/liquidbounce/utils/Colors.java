package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import java.text.NumberFormat;
import net.minecraft.entity.EntityLivingBase;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Colors.class */
public class Colors {
    public static int getColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        return 0 | (color.getAlpha() << 24) | (red << 16) | (green << 8) | color.getBlue();
    }

    public static int getColor(int i) {
        return 0 | (255 << 24) | (i << 16) | (i << 8) | i;
    }

    public static int getColor(int i, int i2) {
        return 0 | (i2 << 24) | (i << 16) | (i << 8) | i;
    }

    public static int getColor(int i, int i2, int i3) {
        return 0 | (255 << 24) | (i << 16) | (i2 << 8) | i3;
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float fFunc_110143_aJ = entityLivingBase.func_110143_aJ();
        float[] fArr = {0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colorArr = {new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        return fFunc_110143_aJ >= 0.0f ? blendColors(fArr, colorArr, fFunc_110143_aJ / entityLivingBase.func_110138_aP()).brighter() : colorArr[0];
    }

    public static Color blendColors(float[] fArr, Color[] colorArr, float f) {
        if (fArr == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colorArr == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fArr.length != colorArr.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        int[] iArr = new int[2];
        int length = 0;
        while (length < fArr.length && fArr[length] <= f) {
            length++;
        }
        if (length >= fArr.length) {
            length = fArr.length - 1;
        }
        iArr[0] = length - 1;
        iArr[1] = length;
        float[] fArr2 = {fArr[iArr[0]], fArr[iArr[1]]};
        Color[] colorArr2 = {colorArr[iArr[0]], colorArr[iArr[1]]};
        return blend(colorArr2[0], colorArr2[1], 1.0f - ((f - fArr2[0]) / (fArr2[1] - fArr2[0])));
    }

    public static Color blend(Color color, Color color2, double d) {
        float f = (float) d;
        float f2 = 1.0f - f;
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        color.getColorComponents(fArr);
        color2.getColorComponents(fArr2);
        float f3 = (fArr[0] * f) + (fArr2[0] * f2);
        float f4 = (fArr[1] * f) + (fArr2[1] * f2);
        float f5 = (fArr[2] * f) + (fArr2[2] * f2);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > 255.0f) {
            f3 = 255.0f;
        }
        if (f4 < 0.0f) {
            f4 = 0.0f;
        } else if (f4 > 255.0f) {
            f4 = 255.0f;
        }
        if (f5 < 0.0f) {
            f5 = 0.0f;
        } else if (f5 > 255.0f) {
            f5 = 255.0f;
        }
        Color color3 = null;
        try {
            color3 = new Color(f3, f4, f5);
        } catch (IllegalArgumentException e) {
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            System.out.println(numberInstance.format(f3) + "; " + numberInstance.format(f4) + "; " + numberInstance.format(f5));
            e.printStackTrace();
        }
        return color3;
    }

    public static int getRainbow(int i, int i2) {
        return Color.getHSBColor(((System.currentTimeMillis() + i2) % i) / i, 0.4f, 1.0f).getRGB();
    }

    public static int getRainbow2(int i, int i2) {
        return Color.getHSBColor(((System.currentTimeMillis() + i2) % i) / i, 0.5f, 0.555f).getRGB();
    }

    public static int getRainbow3(int i, int i2) {
        return Color.getHSBColor(((System.currentTimeMillis() + i2) % i) / i, 0.8f, 1.001f).getRGB();
    }
}

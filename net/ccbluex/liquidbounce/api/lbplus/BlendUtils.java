package net.ccbluex.liquidbounce.api.lbplus;

import java.awt.Color;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/lbplus/BlendUtils.class */
public enum BlendUtils {
    GREEN("\u00a7A"),
    GOLD("\u00a76"),
    RED("\u00a7C");

    String colorCode;

    BlendUtils(String str) {
        this.colorCode = str;
    }

    public static Color getColorWithOpacity(Color color, int i) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), i);
    }

    public static Color getHealthColor(float f, float f2) {
        return blendColors(new float[]{0.0f, 0.5f, 1.0f}, new Color[]{new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN}, f / f2).brighter();
    }

    public static Color blendColors(float[] fArr, Color[] colorArr, float f) {
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
        float[] colorComponents = color.getColorComponents(new float[3]);
        float[] colorComponents2 = color2.getColorComponents(new float[3]);
        float f3 = (colorComponents[0] * f) + (colorComponents2[0] * f2);
        float f4 = (colorComponents[1] * f) + (colorComponents2[1] * f2);
        float f5 = (colorComponents[2] * f) + (colorComponents2[2] * f2);
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
        } catch (IllegalArgumentException unused) {
        }
        return color3;
    }
}

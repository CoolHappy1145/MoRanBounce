package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.function.Supplier;
import kotlin.text.Typography;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/Palette.class */
public enum Palette {
    GREEN(Palette::lambda$static$0),
    WHITE(Palette::lambda$static$1),
    PURPLE(Palette::lambda$static$2),
    DARK_PURPLE(Palette::lambda$static$3),
    BLUE(Palette::lambda$static$4);

    private final Supplier colorSupplier;

    private static Color lambda$static$0() {
        return new Color(0, 255, 138);
    }

    private static Color lambda$static$1() {
        return Color.WHITE;
    }

    private static Color lambda$static$2() {
        return new Color(198, 139, 255);
    }

    private static Color lambda$static$3() {
        return new Color(133, 46, Typography.times);
    }

    private static Color lambda$static$4() {
        return new Color(116, 202, 255);
    }

    Palette(Supplier supplier) {
        this.colorSupplier = supplier;
    }

    public static Color fade(Color color) {
        return fade(color, 2, 100, 2.0f);
    }

    public static Color fade(Color color, int i, int i2, float f) {
        float[] fArr = {0.0f, 0.0f, (0.5f + (0.5f * Math.abs(((((System.currentTimeMillis() % 2000) / 1000.0f) + ((i / i2) * 2.0f)) % f) - 1.0f))) % 2.0f};
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArr);
        return new Color(Color.HSBtoRGB(fArr[0], fArr[1], fArr[2]));
    }

    public static Color fade1(Color color) {
        return fade1(color, 2, 100);
    }

    public static Color fade1(Color color, int i, int i2) {
        float[] fArr = {0.0f, 0.0f, (0.5f + (0.5f * Math.abs(((((System.currentTimeMillis() % 2000) / 1000.0f) + ((i / i2) * 2.0f)) % 2.0f) - 1.0f))) % 2.0f};
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArr);
        return new Color(Color.HSBtoRGB(fArr[0], fArr[1], fArr[2]));
    }

    public static Color fade2(Color color, int i, int i2) {
        float[] fArr = {0.0f, 0.0f, (0.5f + (0.5f * Math.abs(((((System.currentTimeMillis() % 10000) / 1000.0f) + ((i / i2) * 2.0f)) % 2.0f) - 1.0f))) % 2.0f};
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArr);
        return new Color(Color.HSBtoRGB(fArr[0], fArr[1], fArr[2]));
    }

    public Color getColor() {
        return (Color) this.colorSupplier.get();
    }
}

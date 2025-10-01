package net.ccbluex.liquidbounce.utils;

import java.awt.Color;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/HanaBiColors.class */
public enum HanaBiColors {
    BLACK(-16711423),
    BLUE(-12028161),
    DARKBLUE(-12621684),
    GREEN(-9830551),
    DARKGREEN(-9320847),
    WHITE(-65794),
    AQUA(-7820064),
    DARKAQUA(-12621684),
    GREY(-9868951),
    DARKGREY(-14342875),
    RED(-65536),
    DARKRED(-8388608),
    ORANGE(-29696),
    DARKORANGE(-2263808),
    YELLOW(-256),
    DARKYELLOW(-2702025),
    MAGENTA(-18751),
    DARKMAGENTA(-2252579);


    /* renamed from: c */
    public int f156c;

    HanaBiColors(int i) {
        this.f156c = i;
    }

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
}

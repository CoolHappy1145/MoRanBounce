package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ColorUtils.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdb\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\u0015\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\f\n\u0002\b\u0010\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J*\u0010\u0013\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0007J\u0018\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0018H\u0007J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J\u001a\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u001b\u001a\u00020\u0016H\u0007J\u0018\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0018H\u0007J \u0010#\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010%\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J\"\u0010&\u001a\u00020\u00112\u0006\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u00182\b\b\u0002\u0010\u001b\u001a\u00020\u0016H\u0007JL\u0010)\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u00162\b\b\u0002\u0010*\u001a\u00020\u00182\b\b\u0002\u0010+\u001a\u00020\u00182\b\b\u0002\u0010,\u001a\u00020\u00162\b\b\u0002\u0010-\u001a\u00020\u00162\b\b\u0002\u0010.\u001a\u00020\u00182\b\b\u0002\u0010/\u001a\u00020\u0018H\u0007J\u000e\u00100\u001a\u0002012\u0006\u00102\u001a\u000203J\u0010\u00104\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J\b\u00105\u001a\u00020\u0011H\u0007J\u0010\u00105\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0018H\u0007J\u0010\u00105\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0016H\u0007J\u0010\u00105\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J\u0018\u00105\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0018H\u0007J\u0018\u00105\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0016H\u0007J \u00106\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u00107\u001a\u00020\u00182\u0006\u00108\u001a\u00020\u0018H\u0007J\u0010\u00109\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J\u0010\u0010:\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0016H\u0007J\u000e\u0010;\u001a\u00020\u001f2\u0006\u0010<\u001a\u00020\u001fJ\u0018\u0010=\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0016H\u0007J\u0010\u0010>\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0007J(\u0010?\u001a\u00020\u00112\u0006\u0010@\u001a\u00020\u00162\u0006\u0010A\u001a\u00020\u00182\u0006\u0010B\u001a\u00020\u00182\u0006\u0010C\u001a\u00020DH\u0007J\u0014\u0010E\u001a\u0004\u0018\u00010\u001f2\b\u0010F\u001a\u0004\u0018\u00010\u001fH\u0007J\u0010\u0010G\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020\u001fH\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006I"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/ColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "HUD", "Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "allowedCharactersArray", "", "getAllowedCharactersArray", "()[C", "hexColors", "", "startTime", "", "ALLColor", "Ljava/awt/Color;", "offset", "LiquidSlowly", "time", "count", "", "qd", "", "sq", "TwoRainbow", "alpha", "blueRainbow", "colorCode", "code", "", "darker", "color", "percentage", "fade", "index", "greenRainbow", "healthColor", "hp", "maxHP", "hslRainbow", "lowest", "bigest", "indexOffset", "timeSplit", "saturation", "brightness", "isAllowedCharacter", "", "character", "", "originalrainbow", "rainbow", "rainbow3", "rainbowSpeed", "rainbowBright", "rainbowW", "rainbowWithAlpha", "randomMagicText", "text", "reAlpha", "redRainbow", "skyRainbow", "var2", "bright", "st", "speed", "", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/ColorUtils.class */
public final class ColorUtils {
    private static final HUD HUD;
    private static final Pattern COLOR_PATTERN;
    private static final long startTime;

    @JvmField
    @NotNull
    public static final int[] hexColors;
    public static final ColorUtils INSTANCE = new ColorUtils();

    @NotNull
    private static final char[] allowedCharactersArray = {'/', '\n', '\r', '\t', 0, '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

    private ColorUtils() {
    }

    static {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(HUD.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
        }
        HUD = (HUD) module;
        COLOR_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
        startTime = System.currentTimeMillis();
        hexColors = new int[16];
        for (int i = 0; i < 16; i++) {
            int i2 = i;
            int baseColor = ((i2 >> 3) & 1) * 85;
            int red = (((i2 >> 2) & 1) * 170) + baseColor + (i2 == 6 ? 85 : 0);
            int green = (((i2 >> 1) & 1) * 170) + baseColor;
            int blue = ((i2 & 1) * 170) + baseColor;
            hexColors[i2] = ((red & 255) << 16) | ((green & 255) << 8) | (blue & 255);
        }
    }

    @NotNull
    public final char[] getAllowedCharactersArray() {
        return allowedCharactersArray;
    }

    public final boolean isAllowedCharacter(char character) {
        return (character == '\u00a7' || character < ' ' || character == '\u007f') ? false : true;
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, int alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow3(long offset, float rainbowSpeed, float rainbowBright) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, rainbowSpeed, rainbowBright));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbowW(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 0.6f, 1.0f));
        return new Color(0.0f, (currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color redRainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 0.5f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, 0.0f, 0.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color greenRainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 0.5f, 1.0f));
        return new Color(0.0f, (currentColor.getGreen() / 255.0f) * 1.0f, 0.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color blueRainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 0.5f, 1.0f));
        return new Color(0.0f, 0.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color fade(@NotNull Color color, int index, int count) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        float[] hsb = {0.0f, 0.0f, brightness % 2.0f};
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((((System.currentTimeMillis() % 2000) / 1000.0f) + ((index / count) * 2.0f)) % 2.0f) - 1.0f);
        float brightness2 = 0.5f + (0.5f * brightness);
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @JvmStatic
    @NotNull
    public static final Color darker(@NotNull Color color, float percentage) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color((int) (color.getRed() * percentage), (int) (color.getGreen() * percentage), (int) (color.getBlue() * percentage), (int) (color.getAlpha() * percentage));
    }

    @JvmStatic
    @Nullable
    public static final String stripColor(@Nullable String input) {
        Pattern pattern = COLOR_PATTERN;
        if (input != null) {
            return pattern.matcher(input).replaceAll("");
        }
        return null;
    }

    @JvmStatic
    @NotNull
    public static final Color skyRainbow(int var2, float bright, float st, double speed) {
        double v1 = (Math.ceil((System.currentTimeMillis() / speed) + (var2 * 109)) / 5) % 360.0d;
        Color hSBColor = Color.getHSBColor(360.0d / 360.0d < 0.5d ? -((float) (v1 / 360.0d)) : (float) (v1 / 360.0d), st, bright);
        Intrinsics.checkExpressionValueIsNotNull(hSBColor, "Color.getHSBColor(if ((3\u2026.toFloat() }, st, bright)");
        return hSBColor;
    }

    public static /* synthetic */ Color hslRainbow$default(int i, float f, float f2, int i2, int i3, float f3, float f4, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            f = ((Number) HUD.getRainbowStart().get()).floatValue();
        }
        if ((i4 & 4) != 0) {
            f2 = ((Number) HUD.getRainbowStop().get()).floatValue();
        }
        if ((i4 & 8) != 0) {
            i2 = 300;
        }
        if ((i4 & 16) != 0) {
            i3 = ((Number) HUD.getRainbowSpeed().get()).intValue();
        }
        if ((i4 & 32) != 0) {
            f3 = ((Number) HUD.getRainbowSaturation().get()).floatValue();
        }
        if ((i4 & 64) != 0) {
            f4 = ((Number) HUD.getRainbowBrightness().get()).floatValue();
        }
        return hslRainbow(i, f, f2, i2, i3, f3, f4);
    }

    @JvmStatic
    @NotNull
    public static final Color hslRainbow(int index, float lowest, float bigest, int indexOffset, int timeSplit, float saturation, float brightness) {
        Color hSBColor = Color.getHSBColor((Math.abs((((((int) (System.currentTimeMillis() - startTime)) + (index * indexOffset)) / timeSplit) % 2) - 1) * (bigest - lowest)) + lowest, saturation, brightness);
        Intrinsics.checkExpressionValueIsNotNull(hSBColor, "Color.getHSBColor((abs((\u2026st,saturation,brightness)");
        return hSBColor;
    }

    @JvmStatic
    @NotNull
    public static final String translateAlternateColorCodes(@NotNull String textToTranslate) {
        Intrinsics.checkParameterIsNotNull(textToTranslate, "textToTranslate");
        char[] chars = textToTranslate.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(chars, "(this as java.lang.String).toCharArray()");
        int length = chars.length - 1;
        for (int i = 0; i < length; i++) {
            if (chars[i] == '&' && StringsKt.contains((CharSequence) "0123456789AaBbCcDdEeFfKkLlMmNnOoRr", chars[i + 1], true)) {
                chars[i] = '\u00a7';
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }
        return new String(chars);
    }

    @NotNull
    public final String randomMagicText(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        StringBuilder stringBuilder = new StringBuilder();
        char[] charArray = text.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        for (char c : charArray) {
            if (isAllowedCharacter(c)) {
                int index = new Random().nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\ufffd\ufffd\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\ufffd\ufffd".length());
                char[] charArray2 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\ufffd\ufffd\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\ufffd\ufffd".toCharArray();
                Intrinsics.checkExpressionValueIsNotNull(charArray2, "(this as java.lang.String).toCharArray()");
                stringBuilder.append(charArray2[index]);
            }
        }
        String string = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "stringBuilder.toString()");
        return string;
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow() {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + 400000) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(float alpha) {
        return rainbow(400000L, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int alpha) {
        return rainbow(400000L, alpha / 255);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, int alpha) {
        return rainbow(offset, alpha / 255);
    }

    @JvmStatic
    @NotNull
    public static final Color ALLColor(long offset) {
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            Intrinsics.throwNpe();
        }
        Color currentColor = new Color(Color.HSBtoRGB((float) ((r2.getTicksExisted() / 50.0d) + (Math.sin(1.6d) % 1)), 0.4f, 0.9f));
        return new Color(currentColor.getRGB());
    }

    @JvmStatic
    @NotNull
    public static final Color rainbowWithAlpha(int alpha) {
        return reAlpha(hslRainbow$default(1, 0.0f, 0.0f, 0, 0, 0.0f, 0.0f, 126, null), alpha);
    }

    public static /* synthetic */ Color healthColor$default(float f, float f2, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 255;
        }
        return healthColor(f, f2, i);
    }

    @JvmStatic
    @NotNull
    public static final Color healthColor(float hp, float maxHP, int alpha) {
        int pct = (int) ((hp / maxHP) * 255.0f);
        return new Color(Math.max(Math.min(255 - pct, 255), 0), Math.max(Math.min(pct, 255), 0), 0, alpha);
    }

    public static /* synthetic */ Color colorCode$default(String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 255;
        }
        return colorCode(str, i);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    @JvmStatic
    @NotNull
    public static final Color colorCode(@NotNull String code, int alpha) {
        Intrinsics.checkParameterIsNotNull(code, "code");
        String lowerCase = code.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 48:
                if (lowerCase.equals("0")) {
                    return new Color(0, 0, 0, alpha);
                }
                break;
            case OPCode.MEMORY_START_PUSH /* 49 */:
                if (lowerCase.equals("1")) {
                    return new Color(0, 0, 170, alpha);
                }
                break;
            case OPCode.MEMORY_END_PUSH /* 50 */:
                if (lowerCase.equals("2")) {
                    return new Color(0, 170, 0, alpha);
                }
                break;
            case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                if (lowerCase.equals("3")) {
                    return new Color(0, 170, 170, alpha);
                }
                break;
            case OPCode.MEMORY_END /* 52 */:
                if (lowerCase.equals("4")) {
                    return new Color(170, 0, 0, alpha);
                }
                break;
            case OPCode.MEMORY_END_REC /* 53 */:
                if (lowerCase.equals("5")) {
                    return new Color(170, 0, 170, alpha);
                }
                break;
            case OPCode.FAIL /* 54 */:
                if (lowerCase.equals("6")) {
                    return new Color(255, 170, 0, alpha);
                }
                break;
            case OPCode.JUMP /* 55 */:
                if (lowerCase.equals("7")) {
                    return new Color(170, 170, 170, alpha);
                }
                break;
            case 56:
                if (lowerCase.equals("8")) {
                    return new Color(85, 85, 85, alpha);
                }
                break;
            case OPCode.POP /* 57 */:
                if (lowerCase.equals("9")) {
                    return new Color(85, 85, 255, alpha);
                }
                break;
            case 97:
                if (lowerCase.equals("a")) {
                    return new Color(85, 255, 85, alpha);
                }
                break;
            case 98:
                if (lowerCase.equals("b")) {
                    return new Color(85, 255, 255, alpha);
                }
                break;
            case 99:
                if (lowerCase.equals("c")) {
                    return new Color(255, 85, 85, alpha);
                }
                break;
            case Shell.COMMANDLINE_ERROR /* 100 */:
                if (lowerCase.equals("d")) {
                    return new Color(255, 85, 255, alpha);
                }
                break;
            case Shell.COMPILATION_ERROR /* 101 */:
                if (lowerCase.equals("e")) {
                    return new Color(255, 255, 85, alpha);
                }
                break;
        }
        return new Color(255, 255, 255, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color originalrainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @Nullable
    public static final Color LiquidSlowly(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((time + (count * (-3000000.0f))) / 2) / 1.0E9f, qd, sq));
        return new Color((color.getRed() / 255.0f) * 1, (color.getGreen() / 255.0f) * 1, (color.getBlue() / 255.0f) * 1, color.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color TwoRainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB(((System.nanoTime() + offset) / 9.0E10f) % 1, 0.75f, 0.8f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, alpha);
    }
}

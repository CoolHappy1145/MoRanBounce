package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.api.lbplus.ColorMixer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "NewGUI", description = "next generation clickgui.", category = ModuleCategory.RENDER, canEnable = false)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/NewGUI.class */
public class NewGUI extends Module {
    public static final BoolValue fastRenderValue = new BoolValue("FastRender", false);
    private static final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Sky", "Rainbow", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
    private static final IntegerValue colorRedValue = new IntegerValue("Red", 0, 0, 255);
    private static final IntegerValue colorGreenValue = new IntegerValue("Green", 140, 0, 255);
    private static final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private static final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private static final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private static final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    public double slide;
    public double progress = 0.0d;

    public void onEnable() {
        this.progress = 0.0d;
        this.slide = 0.0d;
    }

    public static Color getAccentColor() {
        Color color;
        color = new Color(255, 255, 255, 255);
        switch (((String) colorModeValue.get()).toLowerCase()) {
            case "custom":
                color = new Color(((Integer) colorRedValue.get()).intValue(), ((Integer) colorGreenValue.get()).intValue(), ((Integer) colorBlueValue.get()).intValue());
                break;
            case "rainbow":
                color = new Color(RenderUtils.getRainbowOpaque(((Integer) mixerSecondsValue.get()).intValue(), ((Float) saturationValue.get()).floatValue(), ((Float) brightnessValue.get()).floatValue(), 0));
                break;
            case "sky":
                color = RenderUtils.skyRainbow(0, ((Float) saturationValue.get()).floatValue(), ((Float) brightnessValue.get()).floatValue());
                break;
            case "liquidslowly":
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, ((Float) saturationValue.get()).floatValue(), ((Float) brightnessValue.get()).floatValue());
                break;
            case "fade":
                color = ColorUtils.fade(new Color(((Integer) colorRedValue.get()).intValue(), ((Integer) colorGreenValue.get()).intValue(), ((Integer) colorBlueValue.get()).intValue()), 0, 100);
                break;
            case "mixer":
                color = ColorMixer.getMixedColor(0, ((Integer) mixerSecondsValue.get()).intValue());
                break;
        }
        return color;
    }
}

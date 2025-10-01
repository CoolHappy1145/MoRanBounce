package net.ccbluex.liquidbounce.api.lbplus;

import java.awt.Color;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(name = "ColorMixer", description = "Mix two colors together.", category = ModuleCategory.RENDER, canEnable = false)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/lbplus/ColorMixer.class */
public class ColorMixer extends Module {
    public static IntegerValue col1RedValue = new IntegerValue("Color1-Red", 255, 0, 255);
    public static IntegerValue col1GreenValue = new IntegerValue("Color1-Green", 255, 0, 255);
    public static IntegerValue col1BlueValue = new IntegerValue("Color1-Blue", 255, 0, 255);
    public static IntegerValue col2RedValue = new IntegerValue("Color2-Red", 255, 0, 255);
    public static IntegerValue col2GreenValue = new IntegerValue("Color2-Green", 255, 0, 255);
    public static IntegerValue col2BlueValue = new IntegerValue("Color2-Blue", 255, 0, 255);

    public static Color getMixedColor(int i, int i2) {
        Color color = new Color(((Integer) col1RedValue.get()).intValue(), ((Integer) col1GreenValue.get()).intValue(), ((Integer) col1BlueValue.get()).intValue());
        return BlendUtils.blendColors(new float[]{0.0f, 0.5f, 1.0f}, new Color[]{color, new Color(((Integer) col2RedValue.get()).intValue(), ((Integer) col2GreenValue.get()).intValue(), ((Integer) col2BlueValue.get()).intValue()), color}, ((System.currentTimeMillis() + i) % (i2 * 1000)) / (i2 * 1000));
    }
}

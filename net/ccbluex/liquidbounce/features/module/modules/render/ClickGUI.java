package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.NewUi;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.style.styles.LiquidBounceStyle;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.style.styles.NullStyle;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "ClickGUI", description = "Opens the ClickGUI.", category = ModuleCategory.RENDER, keyBind = OPCode.FAIL, canEnable = false)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/ClickGUI.class */
public class ClickGUI extends Module {
    private final ListValue styleValue = new ListValue(this, "Style", new String[]{"LiquidBounce", "Null", "Slowly"}, "Slowly") { // from class: net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI.1
        final ClickGUI this$0;

        {
            this.this$0 = this;
        }

        protected void onChanged(Object obj, Object obj2) {
            onChanged((String) obj, (String) obj2);
        }

        protected void onChanged(String str, String str2) {
            this.this$0.updateStyle();
        }
    };
    private final ListValue clickguiValue = new ListValue(this, "ClickGuiMod", new String[]{"LiquidBounce", "LoserLine"}, "LiquidBounce") { // from class: net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI.2
        final ClickGUI this$0;

        {
            this.this$0 = this;
        }

        protected void onChanged(Object obj, Object obj2) {
            onChanged((String) obj, (String) obj2);
        }

        protected void onChanged(String str, String str2) {
            this.this$0.updateClickgui();
        }
    };
    public final FloatValue scaleValue = new FloatValue("Scale", 1.0f, 0.7f, 2.0f);
    public final IntegerValue maxElementsValue = new IntegerValue("MaxElements", 15, 1, 20);
    public static final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    public static final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    public static final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    public static final BoolValue colorRainbow = new BoolValue("Rainbow", false);

    public static Color generateColor() {
        return ((Boolean) colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Integer) colorRedValue.get()).intValue(), ((Integer) colorGreenValue.get()).intValue(), ((Integer) colorBlueValue.get()).intValue());
    }

    public void onEnable() {
        updateStyle();
        updateClickgui();
    }

    private void updateStyle() {
        switch (((String) this.styleValue.get()).toLowerCase()) {
            case "liquidbounce":
                LiquidBounce.clickGui.style = new LiquidBounceStyle();
                break;
            case "null":
                LiquidBounce.clickGui.style = new NullStyle();
                break;
            case "slowly":
                LiquidBounce.clickGui.style = new SlowlyStyle();
                break;
        }
    }

    private void updateClickgui() {
        switch (((String) this.clickguiValue.get()).toLowerCase()) {
            case "liquidbounce":
                f157mc.displayGuiScreen(classProvider.wrapGuiScreen(LiquidBounce.clickGui));
                break;
            case "loserline":
                mc2.func_147108_a(NewUi.getInstance());
                break;
        }
    }

    @EventTarget(ignoreCondition = true)
    public void onPacket(PacketEvent packetEvent) {
        if (classProvider.isSPacketCloseWindow(packetEvent.getPacket()) && classProvider.isClickGui(f157mc.getCurrentScreen())) {
            packetEvent.cancelEvent();
        }
    }
}

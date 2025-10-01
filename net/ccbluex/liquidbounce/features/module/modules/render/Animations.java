package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "Animations", description = "Animations", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Animations.class */
public class Animations extends Module {
    public static final FloatValue itemXValue = new FloatValue("Item-X", 0.0f, -2.0f, 2.0f);
    public static final FloatValue itemYValue = new FloatValue("Item-Y", 0.0f, -2.0f, 2.0f);
    public static final FloatValue itemZValue = new FloatValue("Item-Z", 0.0f, -2.0f, 2.0f);
    public static final FloatValue itemScaleValue = new FloatValue("Item-scale", 0.8f, 0.1f, 1.0f);
    public static final BoolValue heldValue = new BoolValue("Held", true);
    public static final BoolValue shieldValue = new BoolValue("Shield", false);
    public static final BoolValue SwingAnimValue = new BoolValue("SwingAnim", false);
    public static final BoolValue SPValue = new BoolValue("Progress", true);
    public static final BoolValue old189AutoBlockPacketValue = new BoolValue("Old1.8.9AutoBlockPacket", false);
    public static final BoolValue oldSPValue = new BoolValue("Progress1.8", true);
    public static final FloatValue SpeedRotate = new FloatValue("Rotate-Speed", 1.0f, 0.0f, 10.0f);
    public static final ListValue transformFirstPersonRotate = new ListValue("RotateMode", new String[]{"RotateY", "RotateXY", "Custom", "None"}, "RotateY");
    public static final FloatValue customRotate1 = new FloatValue("CustomRotateXAxis", 0.0f, -180.0f, 180.0f);
    public static final FloatValue customRotate2 = new FloatValue("CustomRotateYAxis", 0.0f, -180.0f, 180.0f);
    public static final FloatValue customRotate3 = new FloatValue("CustomRotateZAxis", 0.0f, -180.0f, 180.0f);
    public static final IntegerValue SpeedSwing = new IntegerValue("Swing-Speed", 4, 0, 20);
    public static final ListValue Sword = new ListValue("Sword", new String[]{"Old", "1.7", "WindMill", "Push", "Smooth", "SigmaOld", "BigGod", "Jello"}, "1.7");
}

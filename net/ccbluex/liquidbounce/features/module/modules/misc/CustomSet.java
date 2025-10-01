package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/CustomSet;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "toggleSoundValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "playSound", "", "enable", "", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "CustomSet", description = "Custom Set", category = ModuleCategory.MISC, canEnable = false)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/CustomSet.class */
public final class CustomSet extends Module {
    private final ListValue toggleSoundValue = new ListValue("ToggleSound", new String[]{"None", "Click", "Stone_Pressureplate"}, "Click");

    public final void playSound(boolean z) {
        String str = (String) this.toggleSoundValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 94750088:
                if (lowerCase.equals("click")) {
                    MinecraftInstance.f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                    return;
                }
                return;
            case 945986967:
                if (lowerCase.equals("stone_pressureplate")) {
                    if (z) {
                        MinecraftInstance.f157mc.getSoundHandler().playSound("block.stone_pressureplate.click_on", 0.6f);
                        return;
                    } else {
                        MinecraftInstance.f157mc.getSoundHandler().playSound("block.stone_pressureplate.click_off", 0.5f);
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }
}

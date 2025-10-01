package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/TabUtils.class */
public final class TabUtils {
    public static void tab(IGuiTextField[] iGuiTextFieldArr) {
        for (int i = 0; i < iGuiTextFieldArr.length; i++) {
            IGuiTextField iGuiTextField = iGuiTextFieldArr[i];
            if (iGuiTextField.isFocused()) {
                iGuiTextField.setFocused(false);
                int i2 = i + 1;
                if (i2 >= iGuiTextFieldArr.length) {
                    i2 = 0;
                }
                iGuiTextFieldArr[i2].setFocused(true);
                return;
            }
        }
    }
}

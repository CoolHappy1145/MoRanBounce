package net.ccbluex.liquidbounce.injection.implementations;

import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/implementations/IMixinGuiContainer.class */
public interface IMixinGuiContainer {
    void publicHandleMouseClick(Slot slot, int i, int i2, ClickType clickType);
}

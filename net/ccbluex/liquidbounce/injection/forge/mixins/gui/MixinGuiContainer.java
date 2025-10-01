package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({GuiContainer.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiContainer.class */
public abstract class MixinGuiContainer implements IMixinGuiContainer {
    @Shadow
    protected abstract void func_184098_a(Slot slot, int i, int i2, ClickType clickType);

    @Override // net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer
    public void publicHandleMouseClick(Slot slot, int i, int i2, ClickType clickType) {
        func_184098_a(slot, i, i2, clickType);
    }
}

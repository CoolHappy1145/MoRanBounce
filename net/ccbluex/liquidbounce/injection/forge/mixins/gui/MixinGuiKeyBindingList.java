package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({GuiKeyBindingList.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiKeyBindingList.class */
public abstract class MixinGuiKeyBindingList extends GuiSlot {
    public MixinGuiKeyBindingList(Minecraft minecraft, int i, int i2, int i3, int i4, int i5) {
        super(minecraft, i, i2, i3, i4, i5);
    }

    @Overwrite
    protected int func_148137_d() {
        return this.field_148155_a - 5;
    }
}

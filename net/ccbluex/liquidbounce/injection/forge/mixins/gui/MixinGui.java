package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Gui.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGui.class */
public abstract class MixinGui {

    @Shadow
    protected float field_73735_i;

    @Shadow
    public abstract void func_73729_b(int i, int i2, int i3, int i4, int i5, int i6);
}

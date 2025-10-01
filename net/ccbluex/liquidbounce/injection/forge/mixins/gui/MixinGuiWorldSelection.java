package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiWorldSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiWorldSelection.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiWorldSelection.class */
public abstract class MixinGuiWorldSelection extends MixinGuiScreen {
    @Inject(method = {"drawScreen"}, m59at = {@InterfaceC0563At("HEAD")})
    private void injectDrawDefaultBackground(int i, int i2, float f, CallbackInfo callbackInfo) {
        func_146276_q_();
    }
}

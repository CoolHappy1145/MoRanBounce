package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiIngameMenu.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiIngameMenu.class */
public abstract class MixinGuiIngameMenu extends MixinGuiScreen {
    @Inject(method = {"initGui"}, m59at = {@InterfaceC0563At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        if (!this.field_146297_k.func_71387_A()) {
            this.field_146292_n.add(new GuiButton(1337, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 128, "Reconnect"));
        }
    }

    @Inject(method = {"actionPerformed"}, m59at = {@InterfaceC0563At("HEAD")})
    private void actionPerformed(GuiButton guiButton, CallbackInfo callbackInfo) {
        if (guiButton.field_146127_k == 1337) {
            this.field_146297_k.field_71441_e.func_72882_A();
            ServerUtils.connectToLastServer();
        }
    }
}

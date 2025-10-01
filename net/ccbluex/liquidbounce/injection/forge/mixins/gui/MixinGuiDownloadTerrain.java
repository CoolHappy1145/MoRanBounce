package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiDownloadTerrain.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiDownloadTerrain.class */
public abstract class MixinGuiDownloadTerrain extends MixinGuiScreen {
    @Inject(method = {"initGui"}, m59at = {@InterfaceC0563At("RETURN")})
    private void injectDisconnectButton(CallbackInfo callbackInfo) {
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 120 + 12, I18n.func_135052_a("gui.cancel", new Object[0])));
    }

    protected void injectedActionPerformed(GuiButton guiButton) {
        if (guiButton.field_146127_k == 0) {
            boolean zFunc_71387_A = this.field_146297_k.func_71387_A();
            boolean zFunc_181540_al = this.field_146297_k.func_181540_al();
            guiButton.field_146124_l = false;
            this.field_146297_k.field_71441_e.func_72882_A();
            this.field_146297_k.func_71403_a((WorldClient) null);
            if (zFunc_71387_A) {
                this.field_146297_k.func_147108_a(new GuiMainMenu());
            } else if (zFunc_181540_al) {
                new RealmsBridge().switchToRealms(new GuiMainMenu());
            } else {
                this.field_146297_k.func_147108_a(new GuiMultiplayer(new GuiMainMenu()));
            }
        }
    }
}

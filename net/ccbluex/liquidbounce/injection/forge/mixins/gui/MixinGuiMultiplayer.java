package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.List;
import jdk.nashorn.tools.Shell;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.injection.backend.GuiScreenImplKt;
import net.ccbluex.liquidbounce.p005ui.client.GuiAntiForge;
import net.ccbluex.liquidbounce.p005ui.client.tools.GuiTools;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiMultiplayer.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiMultiplayer.class */
public abstract class MixinGuiMultiplayer extends MixinGuiScreen {
    private GuiButton bungeeCordSpoofButton;

    @Inject(method = {"initGui"}, m59at = {@InterfaceC0563At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        this.field_146292_n.add(new GuiButton(997, 5, 8, 98, 20, "AntiForge"));
        List list = this.field_146292_n;
        GuiButton guiButton = new GuiButton(998, 108, 8, 98, 20, "BungeeCord Spoof: " + (BungeeCordSpoof.enabled ? "On" : "Off"));
        this.bungeeCordSpoofButton = guiButton;
        list.add(guiButton);
        this.field_146292_n.add(new GuiButton(999, this.field_146294_l - Shell.INTERNAL_ERROR, 8, 98, 20, "Tools"));
    }

    @Inject(method = {"actionPerformed"}, m59at = {@InterfaceC0563At("HEAD")})
    private void actionPerformed(GuiButton guiButton, CallbackInfo callbackInfo) {
        switch (guiButton.field_146127_k) {
            case 997:
                this.field_146297_k.func_147108_a(GuiScreenImplKt.unwrap(LiquidBounce.wrapper.getClassProvider().wrapGuiScreen(new GuiAntiForge(GuiScreenImplKt.wrap((GuiScreen) this)))));
                break;
            case 998:
                BungeeCordSpoof.enabled = !BungeeCordSpoof.enabled;
                this.bungeeCordSpoofButton.field_146126_j = "BungeeCord Spoof: " + (BungeeCordSpoof.enabled ? "On" : "Off");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                break;
            case 999:
                this.field_146297_k.func_147108_a(GuiScreenImplKt.unwrap(LiquidBounce.wrapper.getClassProvider().wrapGuiScreen(new GuiTools(GuiScreenImplKt.wrap((GuiScreen) this)))));
                break;
        }
    }
}

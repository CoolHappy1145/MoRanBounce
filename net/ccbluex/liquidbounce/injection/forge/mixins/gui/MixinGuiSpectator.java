package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiSpectator.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiSpectator.class */
public class MixinGuiSpectator {
    @Inject(method = {"renderTooltip"}, m59at = {@InterfaceC0563At("RETURN")})
    private void renderTooltipPost(ScaledResolution scaledResolution, float f, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new Render2DEvent(f));
        AWTFontRenderer.Companion.garbageCollectionTick();
    }
}

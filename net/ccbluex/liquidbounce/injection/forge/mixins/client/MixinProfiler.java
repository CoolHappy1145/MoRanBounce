package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.minecraft.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Profiler.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinProfiler.class */
public class MixinProfiler {
    @Inject(method = {"startSection(Ljava/lang/String;)V"}, m59at = {@InterfaceC0563At("HEAD")})
    private void startSection(String str, CallbackInfo callbackInfo) {
        if (str.equals("bossHealth") && ClassUtils.hasClass("net.labymod.api.LabyModAPI")) {
            LiquidBounce.eventManager.callEvent(new Render2DEvent(0.0f));
        }
    }
}

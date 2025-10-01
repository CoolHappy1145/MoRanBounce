package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.RenderEntityEvent;
import net.ccbluex.liquidbounce.injection.backend.EntityImplKt;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Render.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRender.class */
public abstract class MixinRender {
    @Shadow
    protected abstract boolean func_180548_c(Entity entity);

    @Inject(method = {"doRender"}, m59at = {@InterfaceC0563At("HEAD")})
    private void doRender(Entity entity, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new RenderEntityEvent(EntityImplKt.wrap(entity), d, d2, d3, f, f2));
    }
}

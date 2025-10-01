package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityMobSpawnerRenderer.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntityMobSpawnerRenderer.class */
public class MixinTileEntityMobSpawnerRenderer {
    @Inject(method = {"renderMob"}, cancellable = true, m59at = {@InterfaceC0563At("HEAD")})
    private static void injectPaintingSpawnerFix(MobSpawnerBaseLogic mobSpawnerBaseLogic, double d, double d2, double d3, float f, CallbackInfo callbackInfo) {
        Entity entityFunc_184994_d = mobSpawnerBaseLogic.func_184994_d();
        if (entityFunc_184994_d == null || (entityFunc_184994_d instanceof EntityPainting)) {
            callbackInfo.cancel();
        }
    }
}

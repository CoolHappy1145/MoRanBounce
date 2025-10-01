package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.injection.backend.BlockImplKt;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityRendererDispatcher.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntityRendererDispatcher.class */
public class MixinTileEntityRendererDispatcher {
    @Inject(method = {"render(Lnet/minecraft/tileentity/TileEntity;FI)V"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void renderTileEntity(TileEntity tileEntity, float f, int i, CallbackInfo callbackInfo) {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xRay.getState() && !xRay.getXrayBlocks().contains(BlockImplKt.wrap(tileEntity.func_145838_q()))) {
            callbackInfo.cancel();
        }
    }
}

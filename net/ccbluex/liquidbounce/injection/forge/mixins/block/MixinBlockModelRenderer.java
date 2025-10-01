package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.injection.backend.BlockImplKt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockModelRenderer.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockModelRenderer.class */
public class MixinBlockModelRenderer {
    @Inject(method = {"renderModelSmooth"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    public void renderModelSmooth(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, BufferBuilder bufferBuilder, boolean z, long j, CallbackInfoReturnable callbackInfoReturnable) {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (((XRay) Objects.requireNonNull(xRay)).getState() && !xRay.getXrayBlocks().contains(BlockImplKt.wrap(iBlockState.func_177230_c()))) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"renderModelFlat"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void renderModelStandard(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, BufferBuilder bufferBuilder, boolean z, long j, CallbackInfoReturnable callbackInfoReturnable) {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (((XRay) Objects.requireNonNull(xRay)).getState() && !xRay.getXrayBlocks().contains(BlockImplKt.wrap(iBlockState.func_177230_c()))) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}

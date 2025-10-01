package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({WorldClient.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/world/MixinWorldClient.class */
public class MixinWorldClient {
    @ModifyVariable(method = {"showBarrierParticles"}, m63at = @InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", shift = InterfaceC0563At.Shift.AFTER), ordinal = 0)
    private boolean handleBarriers(boolean z) {
        TrueSight trueSight = (TrueSight) LiquidBounce.moduleManager.getModule(TrueSight.class);
        return z || (trueSight.getState() && ((Boolean) trueSight.getBarriersValue().get()).booleanValue());
    }
}

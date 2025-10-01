package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockLiquid.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockLiquid.class */
public class MixinBlockLiquid {
    @Inject(method = {"canCollideCheck"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void onCollideCheck(CallbackInfoReturnable callbackInfoReturnable) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(Liquids.class))).getState()) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    @Inject(method = {"modifyAcceleration"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void onModifyAcceleration(CallbackInfoReturnable callbackInfoReturnable) {
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        if (noSlow.getState() && ((Boolean) noSlow.getLiquidPushValue().get()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(new Vec3d(0.0d, 0.0d, 0.0d));
        }
    }
}

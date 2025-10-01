package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.minecraft.block.BlockSoulSand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BlockSoulSand.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockSoulSand.class */
public class MixinBlockSoulSand {
    @Inject(method = {"onEntityCollidedWithBlock"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void onEntityCollidedWithBlock(CallbackInfo callbackInfo) {
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        if (((NoSlow) Objects.requireNonNull(noSlow)).getState() && ((Boolean) noSlow.getSoulsandValue().get()).booleanValue()) {
            callbackInfo.cancel();
        }
    }
}

package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({World.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/world/MixinWorld.class */
public abstract class MixinWorld {
    @Shadow
    public abstract IBlockState func_180495_p(BlockPos blockPos);
}

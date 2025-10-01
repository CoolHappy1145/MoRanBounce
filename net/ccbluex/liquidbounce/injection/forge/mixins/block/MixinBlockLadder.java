package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({BlockLadder.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockLadder.class */
public abstract class MixinBlockLadder extends MixinBlock {

    @Shadow
    @Final
    public static PropertyDirection field_176382_a;

    @Shadow
    @Final
    protected static AxisAlignedBB field_185690_e;

    @Shadow
    @Final
    protected static AxisAlignedBB field_185689_d;

    @Shadow
    @Final
    protected static AxisAlignedBB field_185688_c;

    @Shadow
    @Final
    protected static AxisAlignedBB field_185687_b;

    @Overwrite
    public AxisAlignedBB func_185496_a(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        if (iBlockState.func_177230_c() instanceof BlockLadder) {
            FastClimb fastClimb = (FastClimb) LiquidBounce.moduleManager.getModule(FastClimb.class);
            if (((FastClimb) Objects.requireNonNull(fastClimb)).getState() && ((String) fastClimb.getModeValue().get()).equalsIgnoreCase("AAC3.0.0")) {
                switch (C04601.$SwitchMap$net$minecraft$util$EnumFacing[iBlockState.func_177229_b(field_176382_a).ordinal()]) {
                    case 1:
                        return new AxisAlignedBB(0.0d, 0.0d, 0.009999990463256836d, 1.0d, 1.0d, 1.0d);
                    case 2:
                        return new AxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.9900000095367432d);
                    case 3:
                        return new AxisAlignedBB(0.009999990463256836d, 0.0d, 0.0d, 1.0d, 1.0d, 1.0d);
                    case 4:
                    default:
                        return new AxisAlignedBB(0.0d, 0.0d, 0.0d, 0.9900000095367432d, 1.0d, 1.0d);
                }
            }
        }
        switch (C04601.$SwitchMap$net$minecraft$util$EnumFacing[iBlockState.func_177229_b(field_176382_a).ordinal()]) {
            case 1:
                return field_185690_e;
            case 2:
                return field_185689_d;
            case 3:
                return field_185688_c;
            case 4:
            default:
                return field_185687_b;
        }
    }

    /* renamed from: net.ccbluex.liquidbounce.injection.forge.mixins.block.MixinBlockLadder$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockLadder$1.class */
    static /* synthetic */ class C04601 {
        static final int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}

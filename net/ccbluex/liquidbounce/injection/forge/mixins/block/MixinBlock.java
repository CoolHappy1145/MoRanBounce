package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.ccbluex.liquidbounce.injection.backend.AxisAlignedBBImplKt;
import net.ccbluex.liquidbounce.injection.backend.BlockImplKt;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlock.class */
public abstract class MixinBlock {

    @Shadow
    @Final
    protected BlockStateContainer field_176227_L;

    @Shadow
    public abstract AxisAlignedBB func_180646_a(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos);

    @Overwrite
    protected static void func_185492_a(BlockPos blockPos, AxisAlignedBB axisAlignedBB, List list, @Nullable AxisAlignedBB axisAlignedBB2) {
        if (axisAlignedBB2 != null) {
            AxisAlignedBB axisAlignedBBFunc_186670_a = axisAlignedBB2.func_186670_a(blockPos);
            WorldClient worldClient = Minecraft.func_71410_x().field_71441_e;
            if (worldClient != null) {
                BlockBBEvent blockBBEvent = new BlockBBEvent(BackendExtentionsKt.wrap(blockPos), BlockImplKt.wrap(worldClient.func_180495_p(blockPos).func_177230_c()), AxisAlignedBBImplKt.wrap(axisAlignedBBFunc_186670_a));
                LiquidBounce.eventManager.callEvent(blockBBEvent);
                axisAlignedBBFunc_186670_a = blockBBEvent.getBoundingBox() == null ? null : AxisAlignedBBImplKt.unwrap(blockBBEvent.getBoundingBox());
            }
            if (axisAlignedBBFunc_186670_a != null && axisAlignedBB.func_72326_a(axisAlignedBBFunc_186670_a)) {
                list.add(axisAlignedBBFunc_186670_a);
            }
        }
    }

    @Inject(method = {"shouldSideBeRendered"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void shouldSideBeRendered(CallbackInfoReturnable callbackInfoReturnable) {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (((XRay) Objects.requireNonNull(xRay)).getState()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(xRay.getXrayBlocks().contains(BlockImplKt.wrap((Block) this))));
        }
    }

    @Inject(method = {"isCollidable"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void isCollidable(CallbackInfoReturnable callbackInfoReturnable) {
        GhostHand ghostHand = (GhostHand) LiquidBounce.moduleManager.getModule(GhostHand.class);
        if (((GhostHand) Objects.requireNonNull(ghostHand)).getState() && ((Integer) ghostHand.getBlockValue().get()).intValue() != Block.func_149682_b((Block) this)) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"getAmbientOcclusionLightValue"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getAmbientOcclusionLightValue(CallbackInfoReturnable callbackInfoReturnable) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(XRay.class))).getState()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(1.0f));
        }
    }

    @Inject(method = {"getPlayerRelativeBlockHardness"}, m59at = {@InterfaceC0563At("RETURN")}, cancellable = true)
    public void modifyBreakSpeed(IBlockState iBlockState, EntityPlayer entityPlayer, World world, BlockPos blockPos, CallbackInfoReturnable callbackInfoReturnable) {
        float fFloatValue = ((Float) callbackInfoReturnable.getReturnValue()).floatValue();
        NoSlowBreak noSlowBreak = (NoSlowBreak) LiquidBounce.moduleManager.getModule(NoSlowBreak.class);
        if (((NoSlowBreak) Objects.requireNonNull(noSlowBreak)).getState()) {
            if (((Boolean) noSlowBreak.getWaterValue().get()).booleanValue() && entityPlayer.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i(entityPlayer)) {
                fFloatValue *= 5.0f;
            }
            if (((Boolean) noSlowBreak.getAirValue().get()).booleanValue() && !entityPlayer.field_70122_E) {
                fFloatValue *= 5.0f;
            }
        } else if (entityPlayer.field_70122_E) {
            NoFall noFall = (NoFall) LiquidBounce.moduleManager.getModule(NoFall.class);
            Criticals criticals = (Criticals) LiquidBounce.moduleManager.getModule(Criticals.class);
            if ((((NoFall) Objects.requireNonNull(noFall)).getState() && ((String) noFall.modeValue.get()).equalsIgnoreCase("NoGround")) || (((Criticals) Objects.requireNonNull(criticals)).getState() && ((String) criticals.getModeValue().get()).equalsIgnoreCase("NoGround"))) {
                fFloatValue /= 5.0f;
            }
        }
        callbackInfoReturnable.setReturnValue(Float.valueOf(fFloatValue));
    }
}

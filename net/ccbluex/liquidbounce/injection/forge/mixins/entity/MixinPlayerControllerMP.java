package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import me.aquavit.liquidsense.utils.Debug;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.injection.backend.EntityImplKt;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerControllerMP.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinPlayerControllerMP.class */
public abstract class MixinPlayerControllerMP {

    @Shadow
    protected Minecraft field_78776_a;

    @Shadow
    private GameType field_78779_k = GameType.SURVIVAL;

    @Shadow
    private NetHandlerPlayClient field_78774_b;

    @Shadow
    public abstract void func_78750_j();

    @Shadow
    public abstract float func_78757_d();

    @Inject(method = {"attackEntity"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V")})
    private void attackEntity(EntityPlayer entityPlayer, Entity entity, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new AttackEvent(EntityImplKt.wrap(entity)));
    }

    @Inject(method = {"getIsHittingBlock"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getIsHittingBlock(CallbackInfoReturnable callbackInfoReturnable) {
        if (LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"windowClick"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void windowClick(int i, int i2, int i3, ClickType clickType, EntityPlayer entityPlayer, CallbackInfoReturnable callbackInfoReturnable) {
        ClickWindowEvent clickWindowEvent = new ClickWindowEvent(i, i2, i3, BackendExtentionsKt.toInt(clickType));
        LiquidBounce.eventManager.callEvent(clickWindowEvent);
        if (clickWindowEvent.isCancelled()) {
            callbackInfoReturnable.cancel();
        }
    }

    @Overwrite
    public EnumActionResult func_187099_a(EntityPlayerSP entityPlayerSP, WorldClient worldClient, BlockPos blockPos, EnumFacing enumFacing, Vec3d vec3d, EnumHand enumHand) {
        func_78750_j();
        ItemStack itemStackFunc_184586_b = entityPlayerSP.func_184586_b(enumHand);
        float fFunc_177958_n = (float) (vec3d.field_72450_a - blockPos.func_177958_n());
        float fFunc_177956_o = (float) (vec3d.field_72448_b - blockPos.func_177956_o());
        float fFunc_177952_p = (float) (vec3d.field_72449_c - blockPos.func_177952_p());
        boolean zFunc_180639_a = false;
        if (!this.field_78776_a.field_71441_e.func_175723_af().func_177746_a(blockPos)) {
            return EnumActionResult.FAIL;
        }
        PlayerInteractEvent.RightClickBlock rightClickBlockOnRightClickBlock = ForgeHooks.onRightClickBlock(entityPlayerSP, enumHand, blockPos, enumFacing, ForgeHooks.rayTraceEyeHitVec(entityPlayerSP, func_78757_d() + 1.0f));
        if (rightClickBlockOnRightClickBlock.isCanceled()) {
            this.field_78774_b.func_147297_a(new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p));
            return rightClickBlockOnRightClickBlock.getCancellationResult();
        }
        EnumActionResult enumActionResultFunc_179546_a = EnumActionResult.PASS;
        if (this.field_78779_k != GameType.SPECTATOR) {
            EnumActionResult enumActionResultOnItemUseFirst = itemStackFunc_184586_b.onItemUseFirst(entityPlayerSP, worldClient, blockPos, enumHand, enumFacing, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p);
            if (enumActionResultOnItemUseFirst != EnumActionResult.PASS) {
                this.field_78774_b.func_147297_a(new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p));
                return enumActionResultOnItemUseFirst;
            }
            IBlockState iBlockStateFunc_180495_p = worldClient.func_180495_p(blockPos);
            boolean z = entityPlayerSP.func_184614_ca().doesSneakBypassUse(worldClient, blockPos, entityPlayerSP) && entityPlayerSP.func_184592_cb().doesSneakBypassUse(worldClient, blockPos, entityPlayerSP);
            if (!entityPlayerSP.func_70093_af() || z || rightClickBlockOnRightClickBlock.getUseBlock() == Event.Result.ALLOW) {
                if (rightClickBlockOnRightClickBlock.getUseBlock() != Event.Result.DENY) {
                    zFunc_180639_a = iBlockStateFunc_180495_p.func_177230_c().func_180639_a(worldClient, blockPos, iBlockStateFunc_180495_p, entityPlayerSP, enumHand, enumFacing, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p);
                }
                if (zFunc_180639_a) {
                    enumActionResultFunc_179546_a = EnumActionResult.SUCCESS;
                }
            }
            if (!zFunc_180639_a && (itemStackFunc_184586_b.func_77973_b() instanceof ItemBlock) && !itemStackFunc_184586_b.func_77973_b().func_179222_a(worldClient, blockPos, enumFacing, entityPlayerSP, itemStackFunc_184586_b)) {
                return EnumActionResult.FAIL;
            }
        }
        this.field_78774_b.func_147297_a(new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p));
        if ((zFunc_180639_a || this.field_78779_k == GameType.SPECTATOR) && rightClickBlockOnRightClickBlock.getUseItem() != Event.Result.ALLOW) {
            return EnumActionResult.SUCCESS;
        }
        if (itemStackFunc_184586_b.func_190926_b()) {
            return EnumActionResult.PASS;
        }
        if (entityPlayerSP.func_184811_cZ().func_185141_a(itemStackFunc_184586_b.func_77973_b())) {
            return EnumActionResult.PASS;
        }
        if ((itemStackFunc_184586_b.func_77973_b() instanceof ItemBlock) && !entityPlayerSP.func_189808_dh()) {
            Block blockFunc_179223_d = itemStackFunc_184586_b.func_77973_b().func_179223_d();
            if ((blockFunc_179223_d instanceof BlockCommandBlock) || (blockFunc_179223_d instanceof BlockStructure)) {
                return EnumActionResult.FAIL;
            }
        }
        if (this.field_78779_k.func_77145_d()) {
            int iFunc_77960_j = itemStackFunc_184586_b.func_77960_j();
            int iFunc_190916_E = itemStackFunc_184586_b.func_190916_E();
            if (rightClickBlockOnRightClickBlock.getUseItem() != Event.Result.DENY) {
                EnumActionResult enumActionResultFunc_179546_a2 = itemStackFunc_184586_b.func_179546_a(entityPlayerSP, worldClient, blockPos, enumHand, enumFacing, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p);
                itemStackFunc_184586_b.func_77964_b(iFunc_77960_j);
                itemStackFunc_184586_b.func_190920_e(iFunc_190916_E);
                return enumActionResultFunc_179546_a2;
            }
            return enumActionResultFunc_179546_a;
        }
        ItemStack itemStackFunc_77946_l = itemStackFunc_184586_b.func_77946_l();
        if (rightClickBlockOnRightClickBlock.getUseItem() != Event.Result.DENY) {
            enumActionResultFunc_179546_a = itemStackFunc_184586_b.func_179546_a(entityPlayerSP, worldClient, blockPos, enumHand, enumFacing, fFunc_177958_n, fFunc_177956_o, fFunc_177952_p);
        }
        if (itemStackFunc_184586_b.func_190926_b()) {
            ForgeEventFactory.onPlayerDestroyItem(entityPlayerSP, itemStackFunc_77946_l, enumHand);
        }
        return enumActionResultFunc_179546_a;
    }

    @Overwrite
    public EnumActionResult func_187101_a(EntityPlayer entityPlayer, World world, EnumHand enumHand) {
        ItemStack itemStackFunc_184586_b = entityPlayer.func_184586_b(enumHand);
        ItemStack itemStack = new ItemStack(Items.field_185159_cQ);
        if ((this.field_78776_a.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && ((Boolean) Animations.shieldValue.get()).booleanValue()) {
            this.field_78776_a.field_71439_g.field_71071_by.field_184439_c.set(0, itemStack);
        }
        if (this.field_78779_k == GameType.SPECTATOR) {
            return EnumActionResult.PASS;
        }
        func_78750_j();
        if (((Boolean) Animations.old189AutoBlockPacketValue.get()).booleanValue() && (this.field_78776_a.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
            this.field_78774_b.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
        } else {
            this.field_78774_b.func_147297_a(new CPacketPlayerTryUseItem(enumHand));
        }
        Debug.thePlayerisBlocking = true;
        if (entityPlayer.func_184811_cZ().func_185141_a(itemStackFunc_184586_b.func_77973_b())) {
            return EnumActionResult.PASS;
        }
        EnumActionResult enumActionResultOnItemRightClick = ForgeHooks.onItemRightClick(entityPlayer, enumHand);
        if (enumActionResultOnItemRightClick != null) {
            return enumActionResultOnItemRightClick;
        }
        int iFunc_190916_E = itemStackFunc_184586_b.func_190916_E();
        ActionResult actionResultFunc_77957_a = itemStackFunc_184586_b.func_77957_a(world, entityPlayer, enumHand);
        ItemStack itemStack2 = (ItemStack) actionResultFunc_77957_a.func_188398_b();
        if (itemStack2 != itemStackFunc_184586_b || itemStack2.func_190916_E() != iFunc_190916_E) {
            entityPlayer.func_184611_a(enumHand, itemStack2);
            if (itemStack2.func_190926_b()) {
                ForgeEventFactory.onPlayerDestroyItem(entityPlayer, itemStackFunc_184586_b, enumHand);
            }
        }
        return actionResultFunc_77957_a.func_188397_a();
    }

    @Overwrite
    public void func_78766_c(EntityPlayer entityPlayer) {
        Debug.thePlayerisBlocking = false;
        func_78750_j();
        this.field_78774_b.func_147297_a(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
        entityPlayer.func_184597_cx();
    }
}

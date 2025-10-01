package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\f\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0013\u0010#\u001a\u00020\u00192\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\b\u0010&\u001a\u00020\u0019H\u0016J\u0018\u0010'\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J:\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020 2\u0006\u00100\u001a\u00020\"2\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u00020*H\u0016J \u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=H\u0016J \u0010>\u001a\u00020\u00192\u0006\u0010)\u001a\u0002092\u0006\u0010?\u001a\u00020;2\u0006\u0010@\u001a\u00020.H\u0016J\b\u0010A\u001a\u000204H\u0016J\b\u0010B\u001a\u000204H\u0016J0\u0010C\u001a\u0002042\u0006\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\u00062\u0006\u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020*H\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00198VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006I"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PlayerControllerMPImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "wrapped", "Lnet/minecraft/client/multiplayer/PlayerControllerMP;", "(Lnet/minecraft/client/multiplayer/PlayerControllerMP;)V", PropertyDescriptor.VALUE, "", "blockHitDelay", "getBlockHitDelay", "()I", "setBlockHitDelay", "(I)V", "blockReachDistance", "", "getBlockReachDistance", "()F", "curBlockDamageMP", "getCurBlockDamageMP", "setCurBlockDamageMP", "(F)V", "currentGameType", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "getCurrentGameType", "()Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "isInCreativeMode", "", "()Z", "isNotCreative", "getWrapped", "()Lnet/minecraft/client/multiplayer/PlayerControllerMP;", "clickBlock", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "enumFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "equals", "other", "", "extendedReach", "onPlayerDestroyBlock", "onPlayerRightClick", "playerSP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "wWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "wItemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "wPosition", "wSideOpposite", "wHitVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "onStoppedUsingItem", "", "thePlayer", "processRightClick", "Lnet/minecraft/util/EnumActionResult;", "Entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "World", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "Hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "sendUseItem", "theWorld", "itemStack", "syncCurrentPlayItem", "updateController", "windowClick", "windowId", "slot", "mouseButton", "mode", "player", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PlayerControllerMPImpl.class */
public final class PlayerControllerMPImpl implements IPlayerControllerMP {

    @NotNull
    private final PlayerControllerMP wrapped;

    @NotNull
    public final PlayerControllerMP getWrapped() {
        return this.wrapped;
    }

    public PlayerControllerMPImpl(@NotNull PlayerControllerMP wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean isNotCreative() {
        return this.wrapped.func_78762_g();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public float getBlockReachDistance() {
        return this.wrapped.func_78757_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    @NotNull
    public IWorldSettings.WGameType getCurrentGameType() {
        GameType gameTypeFunc_178889_l = this.wrapped.func_178889_l();
        Intrinsics.checkExpressionValueIsNotNull(gameTypeFunc_178889_l, "wrapped.currentGameType");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$6[gameTypeFunc_178889_l.ordinal()]) {
            case 1:
                return IWorldSettings.WGameType.NOT_SET;
            case 2:
                return IWorldSettings.WGameType.SURVIVAL;
            case 3:
                return IWorldSettings.WGameType.CREATIVE;
            case 4:
                return IWorldSettings.WGameType.ADVENTUR;
            case 5:
                return IWorldSettings.WGameType.SPECTATOR;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean isInCreativeMode() {
        return this.wrapped.func_78758_h();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public float getCurBlockDamageMP() {
        return this.wrapped.field_78770_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public void setCurBlockDamageMP(float f) {
        this.wrapped.field_78770_f = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public int getBlockHitDelay() {
        return this.wrapped.field_78781_i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public void setBlockHitDelay(int i) {
        this.wrapped.field_78781_i = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public void windowClick(int i, int i2, int i3, int i4, @NotNull IEntityPlayerSP player) {
        ClickType clickType;
        Intrinsics.checkParameterIsNotNull(player, "player");
        PlayerControllerMP playerControllerMP = this.wrapped;
        switch (i4) {
            case 0:
                clickType = ClickType.PICKUP;
                break;
            case 1:
                clickType = ClickType.QUICK_MOVE;
                break;
            case 2:
                clickType = ClickType.SWAP;
                break;
            case 3:
                clickType = ClickType.CLONE;
                break;
            case 4:
                clickType = ClickType.THROW;
                break;
            case 5:
                clickType = ClickType.QUICK_CRAFT;
                break;
            case 6:
                clickType = ClickType.PICKUP_ALL;
                break;
            default:
                throw new IllegalArgumentException("Invalid mode " + i4);
        }
        playerControllerMP.func_187098_a(i, i2, i3, clickType, (EntityPlayerSP) ((EntityPlayerSPImpl) player).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public void updateController() {
        this.wrapped.func_78765_e();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean sendUseItem(@NotNull IEntityPlayer playerSP, @NotNull IWorld theWorld, @NotNull IItemStack itemStack) {
        Intrinsics.checkParameterIsNotNull(playerSP, "playerSP");
        Intrinsics.checkParameterIsNotNull(theWorld, "theWorld");
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");
        EntityPlayer wrapped = ((EntityPlayerImpl) playerSP).getWrapped();
        World wrapped2 = ((WorldImpl) theWorld).getWrapped();
        ItemStack wrapped3 = ((ItemStackImpl) itemStack).getWrapped();
        if (this.wrapped.func_178889_l() == GameType.SPECTATOR) {
            return false;
        }
        this.wrapped.func_78750_j();
        Minecraft minecraftFunc_71410_x = Minecraft.func_71410_x();
        Intrinsics.checkExpressionValueIsNotNull(minecraftFunc_71410_x, "Minecraft.getMinecraft()");
        NetHandlerPlayClient netHandlerPlayClientFunc_147114_u = minecraftFunc_71410_x.func_147114_u();
        if (netHandlerPlayClientFunc_147114_u == null) {
            Intrinsics.throwNpe();
        }
        netHandlerPlayClientFunc_147114_u.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        if (wrapped.func_184811_cZ().func_185141_a(wrapped3.func_77973_b())) {
            return false;
        }
        EnumActionResult enumActionResultOnItemRightClick = ForgeHooks.onItemRightClick(wrapped, EnumHand.MAIN_HAND);
        if (enumActionResultOnItemRightClick != null) {
            return enumActionResultOnItemRightClick == EnumActionResult.SUCCESS;
        }
        int iFunc_190916_E = wrapped3.func_190916_E();
        ActionResult result = wrapped3.func_77957_a(wrapped2, wrapped, EnumHand.MAIN_HAND);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        ItemStack resultStack = (ItemStack) result.func_188398_b();
        if ((!Intrinsics.areEqual(resultStack, wrapped3)) || resultStack.func_190916_E() != iFunc_190916_E) {
            wrapped.func_184611_a(EnumHand.MAIN_HAND, resultStack);
            Intrinsics.checkExpressionValueIsNotNull(resultStack, "resultStack");
            if (resultStack.func_190926_b()) {
                ForgeEventFactory.onPlayerDestroyItem(wrapped, wrapped3, EnumHand.MAIN_HAND);
            }
        }
        return result.func_188397_a() == EnumActionResult.SUCCESS;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean onPlayerRightClick(@NotNull IEntityPlayerSP playerSP, @NotNull IWorldClient wWorld, @Nullable IItemStack iItemStack, @NotNull WBlockPos wPosition, @NotNull IEnumFacing wSideOpposite, @NotNull WVec3 wHitVec) {
        Intrinsics.checkParameterIsNotNull(playerSP, "playerSP");
        Intrinsics.checkParameterIsNotNull(wWorld, "wWorld");
        Intrinsics.checkParameterIsNotNull(wPosition, "wPosition");
        Intrinsics.checkParameterIsNotNull(wSideOpposite, "wSideOpposite");
        Intrinsics.checkParameterIsNotNull(wHitVec, "wHitVec");
        return this.wrapped.func_187099_a(((EntityPlayerSPImpl) playerSP).getWrapped(), ((WorldClientImpl) wWorld).getWrapped(), new BlockPos(wPosition.getX(), wPosition.getY(), wPosition.getZ()), ((EnumFacingImpl) wSideOpposite).getWrapped(), new Vec3d(wHitVec.getXCoord(), wHitVec.getYCoord(), wHitVec.getZCoord()), EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public void onStoppedUsingItem(@NotNull IEntityPlayerSP thePlayer) {
        Intrinsics.checkParameterIsNotNull(thePlayer, "thePlayer");
        this.wrapped.func_78766_c((EntityPlayerSP) ((EntityPlayerSPImpl) thePlayer).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public void syncCurrentPlayItem() {
        this.wrapped.func_78750_j();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean clickBlock(@NotNull WBlockPos blockPos, @NotNull IEnumFacing enumFacing) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Intrinsics.checkParameterIsNotNull(enumFacing, "enumFacing");
        return this.wrapped.func_180511_b(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), ((EnumFacingImpl) enumFacing).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean onPlayerDestroyBlock(@NotNull WBlockPos blockPos, @NotNull IEnumFacing enumFacing) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Intrinsics.checkParameterIsNotNull(enumFacing, "enumFacing");
        return this.wrapped.func_187103_a(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    @NotNull
    public EnumActionResult processRightClick(@NotNull IEntityPlayer Entity, @NotNull IWorld World, @NotNull WEnumHand Hand) {
        EnumHand enumHand;
        Intrinsics.checkParameterIsNotNull(Entity, "Entity");
        Intrinsics.checkParameterIsNotNull(World, "World");
        Intrinsics.checkParameterIsNotNull(Hand, "Hand");
        PlayerControllerMP playerControllerMP = this.wrapped;
        EntityPlayer wrapped = ((EntityPlayerImpl) Entity).getWrapped();
        World wrapped2 = ((WorldImpl) World).getWrapped();
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$17[Hand.ordinal()]) {
            case 1:
                enumHand = EnumHand.MAIN_HAND;
                break;
            case 2:
                enumHand = EnumHand.OFF_HAND;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        EnumActionResult enumActionResultFunc_187101_a = playerControllerMP.func_187101_a(wrapped, wrapped2, enumHand);
        Intrinsics.checkExpressionValueIsNotNull(enumActionResultFunc_187101_a, "wrapped.processRightClic\u2026d.unwrap(),Hand.unwrap())");
        return enumActionResultFunc_187101_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP
    public boolean extendedReach() {
        return this.wrapped.func_78749_i();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PlayerControllerMPImpl) && Intrinsics.areEqual(((PlayerControllerMPImpl) obj).wrapped, this.wrapped);
    }
}

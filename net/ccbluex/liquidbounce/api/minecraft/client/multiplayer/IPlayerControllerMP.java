package net.ccbluex.liquidbounce.api.minecraft.client.multiplayer;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.minecraft.util.EnumActionResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdt\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&J\b\u0010\u001d\u001a\u00020\u0015H&J\u0018\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&J:\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020)H&J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020!H&J \u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H&J \u00105\u001a\u00020\u00152\u0006\u0010 \u001a\u0002002\u0006\u0010\"\u001a\u0002022\u0006\u0010$\u001a\u00020%H&J\b\u00106\u001a\u00020+H&J\b\u00107\u001a\u00020+H&J0\u00108\u001a\u00020+2\u0006\u00109\u001a\u00020\u00032\u0006\u0010:\u001a\u00020\u00032\u0006\u0010;\u001a\u00020\u00032\u0006\u0010<\u001a\u00020\u00032\u0006\u0010=\u001a\u00020!H&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000b\"\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016\u00a8\u0006>"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "", "blockHitDelay", "", "getBlockHitDelay", "()I", "setBlockHitDelay", "(I)V", "blockReachDistance", "", "getBlockReachDistance", "()F", "curBlockDamageMP", "getCurBlockDamageMP", "setCurBlockDamageMP", "(F)V", "currentGameType", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "getCurrentGameType", "()Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "isInCreativeMode", "", "()Z", "isNotCreative", "clickBlock", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "enumFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "extendedReach", "onPlayerDestroyBlock", "onPlayerRightClick", "playerSP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "theWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "position", "sideOpposite", "hitVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "onStoppedUsingItem", "", "thePlayer", "processRightClick", "Lnet/minecraft/util/EnumActionResult;", "Entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "World", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "Hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "sendUseItem", "syncCurrentPlayItem", "updateController", "windowClick", "windowId", "slot", "mouseButton", "mode", "player", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP.class */
public interface IPlayerControllerMP {
    boolean isNotCreative();

    float getBlockReachDistance();

    @NotNull
    IWorldSettings.WGameType getCurrentGameType();

    boolean isInCreativeMode();

    float getCurBlockDamageMP();

    void setCurBlockDamageMP(float f);

    int getBlockHitDelay();

    void setBlockHitDelay(int i);

    void windowClick(int i, int i2, int i3, int i4, @NotNull IEntityPlayerSP iEntityPlayerSP);

    void updateController();

    void syncCurrentPlayItem();

    boolean sendUseItem(@NotNull IEntityPlayer iEntityPlayer, @NotNull IWorld iWorld, @NotNull IItemStack iItemStack);

    boolean onPlayerRightClick(@NotNull IEntityPlayerSP iEntityPlayerSP, @NotNull IWorldClient iWorldClient, @Nullable IItemStack iItemStack, @NotNull WBlockPos wBlockPos, @NotNull IEnumFacing iEnumFacing, @NotNull WVec3 wVec3);

    @NotNull
    EnumActionResult processRightClick(@NotNull IEntityPlayer iEntityPlayer, @NotNull IWorld iWorld, @NotNull WEnumHand wEnumHand);

    void onStoppedUsingItem(@NotNull IEntityPlayerSP iEntityPlayerSP);

    boolean clickBlock(@NotNull WBlockPos wBlockPos, @NotNull IEnumFacing iEnumFacing);

    boolean onPlayerDestroyBlock(@NotNull WBlockPos wBlockPos, @NotNull IEnumFacing iEnumFacing);

    boolean extendedReach();
}

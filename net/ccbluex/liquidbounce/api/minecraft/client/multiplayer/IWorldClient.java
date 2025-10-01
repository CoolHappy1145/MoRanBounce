package net.ccbluex.liquidbounce.api.minecraft.client.multiplayer;

import java.util.Collection;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.minecraft.block.state.IBlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdJ\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004H&J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0004H&J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J \u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H&J\b\u0010\u0019\u001a\u00020\u000eH&J$\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u0010H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0006R\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "loadedEntityList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getLoadedEntityList", "()Ljava/util/Collection;", "loadedTileEntityList", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "getLoadedTileEntityList", "playerEntities", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "getPlayerEntities", "addEntityToWorld", "", "entityId", "", "fakePlayer", "removeEntity", "name", "removeEntityFromWorld", "sendBlockBreakProgress", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "damage", "sendQuittingDisconnectingPacket", "setBlockState", "", "blockstate", "Lnet/minecraft/block/state/IBlockState;", "size", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient.class */
public interface IWorldClient extends IWorld {
    @NotNull
    Collection getPlayerEntities();

    @NotNull
    Collection getLoadedEntityList();

    @NotNull
    Collection getLoadedTileEntityList();

    void sendQuittingDisconnectingPacket();

    void sendBlockBreakProgress(int i, @NotNull WBlockPos wBlockPos, int i2);

    void addEntityToWorld(int i, @NotNull IEntity iEntity);

    void removeEntityFromWorld(int i);

    void removeEntity(@NotNull IEntity iEntity);

    boolean setBlockState(@Nullable WBlockPos wBlockPos, @Nullable IBlockState iBlockState, int i);
}

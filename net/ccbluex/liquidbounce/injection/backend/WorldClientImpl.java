package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdT\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0014H\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0016J$\u0010\u001e\u001a\u00020\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020\u0014H\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\n\u00a8\u0006#"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/WorldClientImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/WorldImpl;", "Lnet/minecraft/client/multiplayer/WorldClient;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "wrapped", "(Lnet/minecraft/client/multiplayer/WorldClient;)V", "loadedEntityList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getLoadedEntityList", "()Ljava/util/Collection;", "loadedTileEntityList", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "getLoadedTileEntityList", "playerEntities", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "getPlayerEntities", "addEntityToWorld", "", "entityId", "", "fakePlayer", "removeEntity", "entity", "removeEntityFromWorld", "sendBlockBreakProgress", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "damage", "sendQuittingDisconnectingPacket", "setBlockState", "", "blockstate", "Lnet/minecraft/block/state/IBlockState;", "size", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldClientImpl.class */
public final class WorldClientImpl extends WorldImpl implements IWorldClient {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WorldClientImpl(@NotNull WorldClient wrapped) {
        super((World) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    @NotNull
    public Collection getPlayerEntities() {
        return new WrappedCollection(getWrapped().field_73010_i, WorldClientImpl$playerEntities$1.INSTANCE, WorldClientImpl$playerEntities$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    @NotNull
    public Collection getLoadedEntityList() {
        return new WrappedCollection(getWrapped().field_72996_f, WorldClientImpl$loadedEntityList$1.INSTANCE, WorldClientImpl$loadedEntityList$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    @NotNull
    public Collection getLoadedTileEntityList() {
        return new WrappedCollection(getWrapped().field_147482_g, WorldClientImpl$loadedTileEntityList$1.INSTANCE, WorldClientImpl$loadedTileEntityList$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    public void sendQuittingDisconnectingPacket() {
        getWrapped().func_72882_A();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    public void sendBlockBreakProgress(int i, @NotNull WBlockPos blockPos, int i2) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        getWrapped().func_175715_c(i, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), i2);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    public void addEntityToWorld(int i, @NotNull IEntity fakePlayer) {
        Intrinsics.checkParameterIsNotNull(fakePlayer, "fakePlayer");
        getWrapped().func_73027_a(i, ((EntityImpl) fakePlayer).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    public void removeEntityFromWorld(int i) {
        getWrapped().func_73028_b(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    public void removeEntity(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        getWrapped().func_72900_e(((EntityImpl) entity).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient
    public boolean setBlockState(@Nullable WBlockPos wBlockPos, @Nullable IBlockState iBlockState, int i) {
        WorldClient wrapped = getWrapped();
        if (wBlockPos == null) {
            Intrinsics.throwNpe();
        }
        return wrapped.func_180501_a(new BlockPos(wBlockPos.getX(), wBlockPos.getY(), wBlockPos.getZ()), iBlockState, i);
    }
}

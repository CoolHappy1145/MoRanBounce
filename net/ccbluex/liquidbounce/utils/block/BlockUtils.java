package net.ccbluex.liquidbounce.utils.block;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdX\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J*\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0018\u0010\n\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00040\u000bj\u0002`\rH\u0007J*\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0018\u0010\n\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00040\u000bj\u0002`\rH\u0007J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0011\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0087\bJ\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\f0\u001d2\u0006\u0010\u001e\u001a\u00020\u0013H\u0007\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/block/BlockUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "canBeClicked", "", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "collideBlock", "axisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "collide", "Lkotlin/Function1;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "Lnet/ccbluex/liquidbounce/utils/block/Collidable;", "collideBlockIntersects", "getBlock", "getBlockName", "", "id", "", "getCenterDistance", "", "getMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "getState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "isFullBlock", "isReplaceable", "searchBlocks", "", "radius", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/block/BlockUtils.class */
public final class BlockUtils extends MinecraftInstance {
    public static final BlockUtils INSTANCE = new BlockUtils();

    private BlockUtils() {
    }

    @JvmStatic
    @Nullable
    public static final IBlock getBlock(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld != null) {
            IIBlockState blockState = theWorld.getBlockState(blockPos);
            if (blockState != null) {
                return blockState.getBlock();
            }
        }
        return null;
    }

    @JvmStatic
    @Nullable
    public static final IMaterial getMaterial(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IIBlockState state = getState(blockPos);
        if (state != null) {
            IBlock block = state.getBlock();
            if (block != null) {
                return block.getMaterial(state);
            }
        }
        return null;
    }

    @JvmStatic
    public static final boolean isReplaceable(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IMaterial material = getMaterial(blockPos);
        if (material != null) {
            return material.isReplaceable();
        }
        return false;
    }

    @JvmStatic
    @Nullable
    public static final IIBlockState getState(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld != null) {
            return theWorld.getBlockState(blockPos);
        }
        return null;
    }

    @JvmStatic
    public static final boolean canBeClicked(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IBlock block = getBlock(blockPos);
        if (block != null ? block.canCollideCheck(getState(blockPos), false) : false) {
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            if (theWorld.getWorldBorder().contains(blockPos)) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    @NotNull
    public static final String getBlockName(int i) {
        IBlock blockById = MinecraftInstance.functions.getBlockById(i);
        if (blockById == null) {
            Intrinsics.throwNpe();
        }
        return blockById.getLocalizedName();
    }

    @JvmStatic
    public static final boolean isFullBlock(@NotNull WBlockPos blockPos) {
        IAxisAlignedBB collisionBoundingBox;
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IBlock block = getBlock(blockPos);
        if (block == null) {
            return false;
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IWorldClient iWorldClient = theWorld;
        IIBlockState state = getState(blockPos);
        return state != null && (collisionBoundingBox = block.getCollisionBoundingBox(iWorldClient, blockPos, state)) != null && collisionBoundingBox.getMaxX() - collisionBoundingBox.getMinX() == 1.0d && collisionBoundingBox.getMaxY() - collisionBoundingBox.getMinY() == 1.0d && collisionBoundingBox.getMaxZ() - collisionBoundingBox.getMinZ() == 1.0d;
    }

    @JvmStatic
    public static final double getCenterDistance(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        return thePlayer.getDistance(blockPos.getX() + 0.5d, blockPos.getY() + 0.5d, blockPos.getZ() + 0.5d);
    }

    @JvmStatic
    @NotNull
    public static final Map searchBlocks(int i) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            return linkedHashMap;
        }
        int i2 = i;
        int i3 = (-i) + 1;
        if (i2 >= i3) {
            while (true) {
                int i4 = i;
                int i5 = (-i) + 1;
                if (i4 >= i5) {
                    while (true) {
                        int i6 = i;
                        int i7 = (-i) + 1;
                        if (i6 >= i7) {
                            while (true) {
                                WBlockPos wBlockPos = new WBlockPos(((int) thePlayer.getPosX()) + i2, ((int) thePlayer.getPosY()) + i4, ((int) thePlayer.getPosZ()) + i6);
                                IBlock block = getBlock(wBlockPos);
                                if (block != null) {
                                    linkedHashMap.put(wBlockPos, block);
                                }
                                if (i6 == i7) {
                                    break;
                                }
                                i6--;
                            }
                        }
                        if (i4 == i5) {
                            break;
                        }
                        i4--;
                    }
                }
                if (i2 == i3) {
                    break;
                }
                i2--;
            }
        }
        return linkedHashMap;
    }

    @JvmStatic
    public static final boolean collideBlock(@NotNull IAxisAlignedBB axisAlignedBB, @NotNull Function1 collide) {
        Intrinsics.checkParameterIsNotNull(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkParameterIsNotNull(collide, "collide");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        long jFloor = ((int) Math.floor(thePlayer.getEntityBoundingBox().getMaxX())) + 1;
        for (long jFloor2 = (int) Math.floor(thePlayer.getEntityBoundingBox().getMinX()); jFloor2 < jFloor; jFloor2++) {
            int iFloor = ((int) Math.floor(thePlayer.getEntityBoundingBox().getMaxZ())) + 1;
            for (int iFloor2 = (int) Math.floor(thePlayer.getEntityBoundingBox().getMinZ()); iFloor2 < iFloor; iFloor2++) {
                if (!((Boolean) collide.invoke(getBlock(new WBlockPos(jFloor2, axisAlignedBB.getMinY(), iFloor2)))).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @JvmStatic
    public static final boolean collideBlockIntersects(@NotNull IAxisAlignedBB axisAlignedBB, @NotNull Function1 collide) {
        IIBlockState state;
        Intrinsics.checkParameterIsNotNull(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkParameterIsNotNull(collide, "collide");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        int iFloor = ((int) Math.floor(thePlayer.getEntityBoundingBox().getMaxX())) + 1;
        for (int iFloor2 = (int) Math.floor(thePlayer.getEntityBoundingBox().getMinX()); iFloor2 < iFloor; iFloor2++) {
            int iFloor3 = ((int) Math.floor(thePlayer.getEntityBoundingBox().getMaxZ())) + 1;
            for (int iFloor4 = (int) Math.floor(thePlayer.getEntityBoundingBox().getMinZ()); iFloor4 < iFloor3; iFloor4++) {
                WBlockPos wBlockPos = new WBlockPos(iFloor2, axisAlignedBB.getMinY(), iFloor4);
                IBlock block = getBlock(wBlockPos);
                if (((Boolean) collide.invoke(block)).booleanValue() && (state = getState(wBlockPos)) != null) {
                    IAxisAlignedBB collisionBoundingBox = block != null ? block.getCollisionBoundingBox(theWorld, wBlockPos, state) : null;
                    if (collisionBoundingBox == null) {
                        continue;
                    } else if (thePlayer.getEntityBoundingBox().intersectsWith(collisionBoundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

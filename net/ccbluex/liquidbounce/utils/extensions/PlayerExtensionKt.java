package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffdV\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u0016\u001a\n\u0010\u001a\u001a\u00020\u0019*\u00020\u001b\u001a\n\u0010\u001c\u001a\u00020\u0019*\u00020\u0016\u001a$\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\u0001\u001a\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020#\u001a\u0014\u0010$\u001a\u0004\u0018\u00010\u001e*\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0015\"\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0015\u0010\u000b\u001a\u00020\f*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006%"}, m27d2 = {"hurtPercent", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getHurtPercent", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)F", "ping", "", "getPing", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)I", "renderHurtTime", "getRenderHurtTime", "skin", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getSkin", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getNearestPointBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "eye", "box", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "getDistanceToEntityBox", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "entity", "isAnimal", "", "isClientFriend", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "isMob", "rayTraceWithCustomRotation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "blockReachDistance", "yaw", "pitch", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "rayTraceWithServerSideRotation", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/extensions/PlayerExtensionKt.class */
public final class PlayerExtensionKt {
    public static final double getDistanceToEntityBox(@NotNull IEntity getDistanceToEntityBox, @NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(getDistanceToEntityBox, "$this$getDistanceToEntityBox");
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        WVec3 positionEyes = getDistanceToEntityBox.getPositionEyes(1.0f);
        WVec3 nearestPointBB = getNearestPointBB(positionEyes, entity.getEntityBoundingBox());
        return Math.sqrt(Math.pow(Math.abs(nearestPointBB.getXCoord() - positionEyes.getXCoord()), 2.0d) + Math.pow(Math.abs(nearestPointBB.getYCoord() - positionEyes.getYCoord()), 2.0d) + Math.pow(Math.abs(nearestPointBB.getZCoord() - positionEyes.getZCoord()), 2.0d));
    }

    @NotNull
    public static final WVec3 getNearestPointBB(@NotNull WVec3 eye, @NotNull IAxisAlignedBB box) {
        Intrinsics.checkParameterIsNotNull(eye, "eye");
        Intrinsics.checkParameterIsNotNull(box, "box");
        double[] dArr = new double[3];
        dArr[0] = eye.getXCoord();
        dArr[1] = eye.getYCoord();
        dArr[2] = eye.getZCoord();
        double[] dArr2 = {box.getMinX(), box.getMinY(), box.getMinZ()};
        double[] dArr3 = {box.getMaxX(), box.getMaxY(), box.getMaxZ()};
        for (int i = 0; i <= 2; i++) {
            if (dArr[i] > dArr3[i]) {
                dArr[i] = dArr3[i];
            } else if (dArr[i] < dArr2[i]) {
                dArr[i] = dArr2[i];
            }
        }
        return new WVec3(dArr[0], dArr[1], dArr[2]);
    }

    public static final boolean isAnimal(@NotNull IEntity isAnimal) {
        Intrinsics.checkParameterIsNotNull(isAnimal, "$this$isAnimal");
        return MinecraftInstance.classProvider.isEntityAnimal(isAnimal) || MinecraftInstance.classProvider.isEntitySquid(isAnimal) || MinecraftInstance.classProvider.isEntityGolem(isAnimal) || MinecraftInstance.classProvider.isEntityBat(isAnimal);
    }

    public static final boolean isMob(@NotNull IEntity isMob) {
        Intrinsics.checkParameterIsNotNull(isMob, "$this$isMob");
        return MinecraftInstance.classProvider.isEntityMob(isMob) || MinecraftInstance.classProvider.isEntityVillager(isMob) || MinecraftInstance.classProvider.isEntitySlime(isMob) || MinecraftInstance.classProvider.isEntityGhast(isMob) || MinecraftInstance.classProvider.isEntityDragon(isMob) || MinecraftInstance.classProvider.isEntityShulker(isMob);
    }

    @Nullable
    public static final IMovingObjectPosition rayTraceWithCustomRotation(@NotNull IEntity rayTraceWithCustomRotation, double d, float f, float f2) {
        Intrinsics.checkParameterIsNotNull(rayTraceWithCustomRotation, "$this$rayTraceWithCustomRotation");
        WVec3 positionEyes = rayTraceWithCustomRotation.getPositionEyes(1.0f);
        WVec3 vectorForRotation = ClientUtils.getVectorForRotation(f2, f);
        WVec3 wVec3 = new WVec3(positionEyes.getXCoord() + (vectorForRotation.getXCoord() * d), positionEyes.getYCoord() + (vectorForRotation.getYCoord() * d), positionEyes.getZCoord() + (vectorForRotation.getZCoord() * d));
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        return theWorld.rayTraceBlocks(positionEyes, wVec3, false, false, true);
    }

    public static final float getRenderHurtTime(@NotNull IEntityLivingBase renderHurtTime) {
        Intrinsics.checkParameterIsNotNull(renderHurtTime, "$this$renderHurtTime");
        return renderHurtTime.getHurtTime() - (renderHurtTime.getHurtTime() != 0 ? MinecraftInstance.f157mc.getTimer().getRenderPartialTicks() : 0.0f);
    }

    @Nullable
    public static final IMovingObjectPosition rayTraceWithCustomRotation(@NotNull IEntity rayTraceWithCustomRotation, double d, @NotNull Rotation rotation) {
        Intrinsics.checkParameterIsNotNull(rayTraceWithCustomRotation, "$this$rayTraceWithCustomRotation");
        Intrinsics.checkParameterIsNotNull(rotation, "rotation");
        return rayTraceWithCustomRotation(rayTraceWithCustomRotation, d, rotation.getYaw(), rotation.getPitch());
    }

    public static final float getHurtPercent(@NotNull IEntityLivingBase hurtPercent) {
        Intrinsics.checkParameterIsNotNull(hurtPercent, "$this$hurtPercent");
        return getRenderHurtTime(hurtPercent) / 10.0f;
    }

    @NotNull
    public static final IResourceLocation getSkin(@NotNull IEntityLivingBase skin) {
        IResourceLocation locationSkin;
        Intrinsics.checkParameterIsNotNull(skin, "$this$skin");
        if (MinecraftInstance.classProvider.isEntityPlayer(skin)) {
            INetworkPlayerInfo playerInfo = MinecraftInstance.f157mc.getNetHandler().getPlayerInfo(skin.getUniqueID());
            locationSkin = playerInfo != null ? playerInfo.getLocationSkin() : null;
        } else {
            locationSkin = null;
        }
        return locationSkin != null ? locationSkin : MinecraftInstance.functions.getDefaultSkinLegacy();
    }

    public static final int getPing(@NotNull IEntityLivingBase ping) {
        Integer numValueOf;
        Intrinsics.checkParameterIsNotNull(ping, "$this$ping");
        if (MinecraftInstance.classProvider.isEntityPlayer(ping)) {
            INetworkPlayerInfo playerInfo = MinecraftInstance.f157mc.getNetHandler().getPlayerInfo(ping.getUniqueID());
            numValueOf = playerInfo != null ? Integer.valueOf(RangesKt.coerceAtLeast(playerInfo.getResponseTime(), 0)) : null;
        } else {
            numValueOf = null;
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return -1;
    }

    @Nullable
    public static final IMovingObjectPosition rayTraceWithServerSideRotation(@NotNull IEntity rayTraceWithServerSideRotation, double d) {
        Intrinsics.checkParameterIsNotNull(rayTraceWithServerSideRotation, "$this$rayTraceWithServerSideRotation");
        Rotation rotation = RotationUtils.serverRotation;
        Intrinsics.checkExpressionValueIsNotNull(rotation, "RotationUtils.serverRotation");
        return rayTraceWithCustomRotation(rayTraceWithServerSideRotation, d, rotation);
    }

    public static final boolean isClientFriend(@NotNull IEntityPlayer isClientFriend) {
        Intrinsics.checkParameterIsNotNull(isClientFriend, "$this$isClientFriend");
        String name = isClientFriend.getName();
        if (name != null) {
            return LiquidBounce.INSTANCE.getFileManager().friendsConfig.isFriend(ColorUtils.stripColor(name));
        }
        return false;
    }
}

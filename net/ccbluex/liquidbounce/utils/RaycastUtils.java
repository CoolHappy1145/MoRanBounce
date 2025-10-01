package net.ccbluex.liquidbounce.utils;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001:\u0001\fB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/RaycastUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "raycastEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", AsmConstants.CODERANGE, "", "yaw", "", "pitch", "entityFilter", "Lnet/ccbluex/liquidbounce/utils/RaycastUtils$EntityFilter;", "EntityFilter", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/RaycastUtils.class */
public final class RaycastUtils extends MinecraftInstance {
    public static final RaycastUtils INSTANCE = new RaycastUtils();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&\u00a8\u0006\u0006"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/RaycastUtils$EntityFilter;", "", "canRaycast", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/RaycastUtils$EntityFilter.class */
    public interface EntityFilter {
        boolean canRaycast(@Nullable IEntity iEntity);
    }

    private RaycastUtils() {
    }

    @JvmStatic
    @Nullable
    public static final IEntity raycastEntity(double d, @NotNull EntityFilter entityFilter) {
        Intrinsics.checkParameterIsNotNull(entityFilter, "entityFilter");
        return INSTANCE.raycastEntity(d, RotationUtils.serverRotation.getYaw(), RotationUtils.serverRotation.getPitch(), entityFilter);
    }

    private final IEntity raycastEntity(double d, float f, float f2, EntityFilter entityFilter) {
        IEntity renderViewEntity = MinecraftInstance.f157mc.getRenderViewEntity();
        if (renderViewEntity != null && MinecraftInstance.f157mc.getTheWorld() != null) {
            double d2 = d;
            WVec3 positionEyes = renderViewEntity.getPositionEyes(1.0f);
            float fCos = (float) Math.cos(((-f) * 0.017453292f) - 3.1415927f);
            float fSin = (float) Math.sin(((-f) * 0.017453292f) - 3.1415927f);
            float f3 = (float) (-Math.cos((-f2) * 0.01745329238474369d));
            WVec3 wVec3 = new WVec3(fSin * f3, (float) Math.sin((-f2) * 0.01745329238474369d), fCos * f3);
            WVec3 wVec32 = new WVec3(positionEyes.getXCoord() + (wVec3.getXCoord() * d2), positionEyes.getYCoord() + (wVec3.getYCoord() * d2), positionEyes.getZCoord() + (wVec3.getZCoord() * d2));
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            IEntity iEntity = (IEntity) null;
            for (IEntity iEntity2 : theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(wVec3.getXCoord() * d2, wVec3.getYCoord() * d2, wVec3.getZCoord() * d2).expand(1.0d, 1.0d, 1.0d), new Function1() { // from class: net.ccbluex.liquidbounce.utils.RaycastUtils$raycastEntity$entityList$1
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj) {
                    return Boolean.valueOf(invoke((IEntity) obj));
                }

                public final boolean invoke(@Nullable IEntity iEntity3) {
                    return iEntity3 != null && !(MinecraftInstance.classProvider.isEntityPlayer(iEntity3) && iEntity3.asEntityPlayer().isSpectator()) && iEntity3.canBeCollidedWith();
                }
            })) {
                if (entityFilter.canRaycast(iEntity2)) {
                    double collisionBorderSize = iEntity2.getCollisionBorderSize();
                    IAxisAlignedBB iAxisAlignedBBExpand = iEntity2.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                    IMovingObjectPosition iMovingObjectPositionCalculateIntercept = iAxisAlignedBBExpand.calculateIntercept(positionEyes, wVec32);
                    if (iAxisAlignedBBExpand.isVecInside(positionEyes)) {
                        if (d2 >= 0.0d) {
                            iEntity = iEntity2;
                            d2 = 0.0d;
                        }
                    } else if (iMovingObjectPositionCalculateIntercept != null) {
                        double dDistanceTo = positionEyes.distanceTo(iMovingObjectPositionCalculateIntercept.getHitVec());
                        if (dDistanceTo < d2 || d2 == 0.0d) {
                            if (!Intrinsics.areEqual(iEntity2, renderViewEntity.getRidingEntity()) || renderViewEntity.canRiderInteract()) {
                                iEntity = iEntity2;
                                d2 = dDistanceTo;
                            } else if (d2 == 0.0d) {
                                iEntity = iEntity2;
                            }
                        }
                    }
                }
            }
            return iEntity;
        }
        return null;
    }
}

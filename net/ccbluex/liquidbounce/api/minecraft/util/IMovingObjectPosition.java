package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\ufffd\ufffd2\u00020\u0001:\u0001\u0016R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getBlockPos", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "entityHit", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getEntityHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "hitVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "getHitVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "sideHit", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getSideHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "typeOfHit", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "getTypeOfHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "WMovingObjectType", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition.class */
public interface IMovingObjectPosition {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "", "(Ljava/lang/String;I)V", "MISS", "ENTITY", "BLOCK", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType.class */
    public enum WMovingObjectType {
        MISS,
        ENTITY,
        BLOCK
    }

    @Nullable
    IEntity getEntityHit();

    @Nullable
    WBlockPos getBlockPos();

    @Nullable
    IEnumFacing getSideHit();

    @NotNull
    WVec3 getHitVec();

    @NotNull
    WMovingObjectType getTypeOfHit();
}

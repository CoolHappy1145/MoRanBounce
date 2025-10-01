package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/MovingObjectPositionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "wrapped", "Lnet/minecraft/util/math/RayTraceResult;", "(Lnet/minecraft/util/math/RayTraceResult;)V", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getBlockPos", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "entityHit", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getEntityHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "hitVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "getHitVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "sideHit", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getSideHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "typeOfHit", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "getTypeOfHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "getWrapped", "()Lnet/minecraft/util/math/RayTraceResult;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/MovingObjectPositionImpl.class */
public final class MovingObjectPositionImpl implements IMovingObjectPosition {

    @NotNull
    private final RayTraceResult wrapped;

    @NotNull
    public final RayTraceResult getWrapped() {
        return this.wrapped;
    }

    public MovingObjectPositionImpl(@NotNull RayTraceResult wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition
    @Nullable
    public IEntity getEntityHit() {
        Entity entity = this.wrapped.field_72308_g;
        if (entity != null) {
            return new EntityImpl(entity);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition
    @Nullable
    public WBlockPos getBlockPos() {
        BlockPos blockPosFunc_178782_a = this.wrapped.func_178782_a();
        if (blockPosFunc_178782_a != null) {
            return new WBlockPos(blockPosFunc_178782_a.func_177958_n(), blockPosFunc_178782_a.func_177956_o(), blockPosFunc_178782_a.func_177952_p());
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition
    @Nullable
    public IEnumFacing getSideHit() {
        EnumFacing enumFacing = this.wrapped.field_178784_b;
        if (enumFacing != null) {
            return new EnumFacingImpl(enumFacing);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition
    @NotNull
    public WVec3 getHitVec() {
        Vec3d vec3d = this.wrapped.field_72307_f;
        Intrinsics.checkExpressionValueIsNotNull(vec3d, "wrapped.hitVec");
        return new WVec3(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition
    @NotNull
    public IMovingObjectPosition.WMovingObjectType getTypeOfHit() {
        RayTraceResult.Type type = this.wrapped.field_72313_a;
        Intrinsics.checkExpressionValueIsNotNull(type, "wrapped.typeOfHit");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1:
                return IMovingObjectPosition.WMovingObjectType.MISS;
            case 2:
                return IMovingObjectPosition.WMovingObjectType.BLOCK;
            case 3:
                return IMovingObjectPosition.WMovingObjectType.ENTITY;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof MovingObjectPositionImpl) && Intrinsics.areEqual(((MovingObjectPositionImpl) obj).wrapped, this.wrapped);
    }
}

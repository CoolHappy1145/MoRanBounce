package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0016J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\b\u0010\u001b\u001a\u00020\u0015H\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0016J\b\u0010\u001d\u001a\u00020\u0015H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00018VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EnumFacingImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "wrapped", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/EnumFacing;)V", "axisOrdinal", "", "getAxisOrdinal", "()I", "directionVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "getDirectionVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "index", "getIndex", "opposite", "getOpposite", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getWrapped", "()Lnet/minecraft/util/EnumFacing;", "equals", "", "other", "", "isDown", "isEast", "isNorth", "isSouth", "isUp", "isWest", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EnumFacingImpl.class */
public final class EnumFacingImpl implements IEnumFacing {

    @NotNull
    private final EnumFacing wrapped;

    @NotNull
    public final EnumFacing getWrapped() {
        return this.wrapped;
    }

    public EnumFacingImpl(@NotNull EnumFacing wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public boolean isNorth() {
        return this.wrapped == EnumFacing.NORTH;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public boolean isSouth() {
        return this.wrapped == EnumFacing.SOUTH;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public boolean isEast() {
        return this.wrapped == EnumFacing.EAST;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public boolean isWest() {
        return this.wrapped == EnumFacing.WEST;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public boolean isUp() {
        return this.wrapped == EnumFacing.UP;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public boolean isDown() {
        return this.wrapped == EnumFacing.DOWN;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    @NotNull
    public IEnumFacing getOpposite() {
        EnumFacing enumFacingFunc_176734_d = this.wrapped.func_176734_d();
        Intrinsics.checkExpressionValueIsNotNull(enumFacingFunc_176734_d, "wrapped.opposite");
        return new EnumFacingImpl(enumFacingFunc_176734_d);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    @NotNull
    public WVec3i getDirectionVec() {
        Vec3i vec3iFunc_176730_m = this.wrapped.func_176730_m();
        Intrinsics.checkExpressionValueIsNotNull(vec3iFunc_176730_m, "wrapped.directionVec");
        return new WVec3i(vec3iFunc_176730_m.func_177958_n(), vec3iFunc_176730_m.func_177956_o(), vec3iFunc_176730_m.func_177952_p());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public int getAxisOrdinal() {
        return this.wrapped.func_176740_k().ordinal();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing
    public int getIndex() {
        return this.wrapped.func_176745_a();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof EnumFacingImpl) && ((EnumFacingImpl) obj).wrapped == this.wrapped;
    }
}

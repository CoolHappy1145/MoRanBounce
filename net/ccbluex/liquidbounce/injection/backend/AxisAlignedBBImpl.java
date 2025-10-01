package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0013\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\n\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0016J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0016J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002J \u0010\"\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u0001H\u0016J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u001cH\u0016J \u0010'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006+"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/AxisAlignedBBImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "wrapped", "Lnet/minecraft/util/math/AxisAlignedBB;", "(Lnet/minecraft/util/math/AxisAlignedBB;)V", "maxX", "", "getMaxX", "()D", "maxY", "getMaxY", "maxZ", "getMaxZ", "minX", "getMinX", "minY", "getMinY", "minZ", "getMinZ", "getWrapped", "()Lnet/minecraft/util/math/AxisAlignedBB;", "addCoord", "x", "y", "z", "calculateIntercept", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "from", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "to", "equals", "", "other", "", "expand", "intersectsWith", "boundingBox", "isVecInside", "vec", "offset", "sx", "sy", "sz", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/AxisAlignedBBImpl.class */
public final class AxisAlignedBBImpl implements IAxisAlignedBB {

    @NotNull
    private final AxisAlignedBB wrapped;

    @NotNull
    public final AxisAlignedBB getWrapped() {
        return this.wrapped;
    }

    public AxisAlignedBBImpl(@NotNull AxisAlignedBB wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    @NotNull
    public IAxisAlignedBB addCoord(double d, double d2, double d3) {
        AxisAlignedBB axisAlignedBBFunc_72321_a = this.wrapped.func_72321_a(d, d2, d3);
        Intrinsics.checkExpressionValueIsNotNull(axisAlignedBBFunc_72321_a, "wrapped.expand(x, y, z)");
        return new AxisAlignedBBImpl(axisAlignedBBFunc_72321_a);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    @NotNull
    public IAxisAlignedBB expand(double d, double d2, double d3) {
        AxisAlignedBB axisAlignedBBFunc_72314_b = this.wrapped.func_72314_b(d, d2, d3);
        Intrinsics.checkExpressionValueIsNotNull(axisAlignedBBFunc_72314_b, "wrapped.grow(x, y, z)");
        return new AxisAlignedBBImpl(axisAlignedBBFunc_72314_b);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    @Nullable
    public IMovingObjectPosition calculateIntercept(@NotNull WVec3 from, @NotNull WVec3 to) {
        Intrinsics.checkParameterIsNotNull(from, "from");
        Intrinsics.checkParameterIsNotNull(to, "to");
        RayTraceResult rayTraceResultFunc_72327_a = this.wrapped.func_72327_a(new Vec3d(from.getXCoord(), from.getYCoord(), from.getZCoord()), new Vec3d(to.getXCoord(), to.getYCoord(), to.getZCoord()));
        if (rayTraceResultFunc_72327_a != null) {
            return new MovingObjectPositionImpl(rayTraceResultFunc_72327_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public boolean isVecInside(@NotNull WVec3 vec) {
        Intrinsics.checkParameterIsNotNull(vec, "vec");
        return this.wrapped.func_72318_a(new Vec3d(vec.getXCoord(), vec.getYCoord(), vec.getZCoord()));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    @NotNull
    public IAxisAlignedBB offset(double d, double d2, double d3) {
        AxisAlignedBB axisAlignedBBFunc_72317_d = this.wrapped.func_72317_d(d, d2, d3);
        Intrinsics.checkExpressionValueIsNotNull(axisAlignedBBFunc_72317_d, "wrapped.offset(sx, sy, sz)");
        return new AxisAlignedBBImpl(axisAlignedBBFunc_72317_d);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public boolean intersectsWith(@NotNull IAxisAlignedBB boundingBox) {
        Intrinsics.checkParameterIsNotNull(boundingBox, "boundingBox");
        return this.wrapped.func_72326_a(((AxisAlignedBBImpl) boundingBox).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public double getMinX() {
        return this.wrapped.field_72340_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public double getMinY() {
        return this.wrapped.field_72338_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public double getMinZ() {
        return this.wrapped.field_72339_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public double getMaxX() {
        return this.wrapped.field_72336_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public double getMaxY() {
        return this.wrapped.field_72337_e;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
    public double getMaxZ() {
        return this.wrapped.field_72334_f;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof AxisAlignedBBImpl) && Intrinsics.areEqual(((AxisAlignedBBImpl) obj).wrapped, this.wrapped);
    }
}

package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\tJ\u0011\u0010\u000e\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\ufffd\ufffdH\u0086\bJ!\u0010\u0010\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006H\u0086\bJ\u000e\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\ufffd\ufffdJ\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\u00020\ufffd\ufffd2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\ufffd\ufffd2\u0006\u0010\u001e\u001a\u00020\u001cJ\u0011\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\ufffd\ufffdH\u0086\bR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000b\u00a8\u0006 "}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;)V", "xCoord", "", "yCoord", "zCoord", "(DDD)V", "getXCoord", "()D", "getYCoord", "getZCoord", "add", "vec", "addVector", "x", "y", "z", "distanceTo", "equals", "", "other", "hashCode", "", "rotatePitch", "pitch", "", "rotateYaw", "yaw", "squareDistanceTo", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/WVec3.class */
public final class WVec3 {
    private final double xCoord;
    private final double yCoord;
    private final double zCoord;

    public WVec3(double d, double d2, double d3) {
        this.xCoord = d;
        this.yCoord = d2;
        this.zCoord = d3;
    }

    public final double getXCoord() {
        return this.xCoord;
    }

    public final double getYCoord() {
        return this.yCoord;
    }

    public final double getZCoord() {
        return this.zCoord;
    }

    @NotNull
    public final WVec3 addVector(double d, double d2, double d3) {
        return new WVec3(getXCoord() + d, getYCoord() + d2, getZCoord() + d3);
    }

    public final double distanceTo(@NotNull WVec3 vec) {
        Intrinsics.checkParameterIsNotNull(vec, "vec");
        double d = vec.xCoord - this.xCoord;
        double d2 = vec.yCoord - this.yCoord;
        double d3 = vec.zCoord - this.zCoord;
        return Math.sqrt((d * d) + (d2 * d2) + (d3 * d3));
    }

    public final double squareDistanceTo(@NotNull WVec3 vec) {
        Intrinsics.checkParameterIsNotNull(vec, "vec");
        double xCoord = vec.getXCoord() - getXCoord();
        double yCoord = vec.getYCoord() - getYCoord();
        double zCoord = vec.getZCoord() - getZCoord();
        return (xCoord * xCoord) + (yCoord * yCoord) + (zCoord * zCoord);
    }

    @NotNull
    public final WVec3 add(@NotNull WVec3 vec) {
        Intrinsics.checkParameterIsNotNull(vec, "vec");
        return new WVec3(getXCoord() + vec.getXCoord(), getYCoord() + vec.getYCoord(), getZCoord() + vec.getZCoord());
    }

    @NotNull
    public final WVec3 rotatePitch(float f) {
        float fCos = (float) Math.cos(f);
        float fSin = (float) Math.sin(f);
        return new WVec3(this.xCoord, (this.yCoord * fCos) + (this.zCoord * fSin), (this.zCoord * fCos) - (this.yCoord * fSin));
    }

    @NotNull
    public final WVec3 rotateYaw(float f) {
        float fCos = (float) Math.cos(f);
        float fSin = (float) Math.sin(f);
        return new WVec3((this.xCoord * fCos) + (this.zCoord * fSin), this.yCoord, (this.zCoord * fCos) - (this.xCoord * fSin));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), obj != null ? obj.getClass() : null)) {
            return false;
        }
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.util.WVec3");
        }
        return this.xCoord == ((WVec3) obj).xCoord && this.yCoord == ((WVec3) obj).yCoord && this.zCoord == ((WVec3) obj).zCoord;
    }

    public int hashCode() {
        return (31 * ((31 * Double.hashCode(this.xCoord)) + Double.hashCode(this.yCoord))) + Double.hashCode(this.zCoord);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WVec3(@NotNull WVec3i blockPos) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
    }
}

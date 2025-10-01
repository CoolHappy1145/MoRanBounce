package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\ufffd\ufffd2\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016R\u0011\u0010\u0002\u001a\u00020\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\n\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "", "x", "", "y", "z", "(DDD)V", "", "(III)V", "getX", "()I", "getY", "getZ", "equals", "", "other", "hashCode", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/WVec3i.class */
public class WVec3i {

    /* renamed from: x */
    private final int f106x;

    /* renamed from: y */
    private final int f107y;

    /* renamed from: z */
    private final int f108z;

    public WVec3i(int i, int i2, int i3) {
        this.f106x = i;
        this.f107y = i2;
        this.f108z = i3;
    }

    public final int getX() {
        return this.f106x;
    }

    public final int getY() {
        return this.f107y;
    }

    public final int getZ() {
        return this.f108z;
    }

    public WVec3i(double d, double d2, double d3) {
        this((int) Math.floor(d), (int) Math.floor(d2), (int) Math.floor(d3));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), obj != null ? obj.getClass() : null)) {
            return false;
        }
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.util.WVec3i");
        }
        return this.f106x == ((WVec3i) obj).f106x && this.f107y == ((WVec3i) obj).f107y && this.f108z == ((WVec3i) obj).f108z;
    }

    public int hashCode() {
        return (31 * ((31 * this.f106x) + this.f107y)) + this.f108z;
    }
}

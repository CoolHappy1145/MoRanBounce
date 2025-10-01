package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\ufffd\ufffd \u001a2\u00020\u0001:\u0001\u001aB\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\n\u0012\u0006\u0010\u0004\u001a\u00020\n\u0012\u0006\u0010\u0005\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\ufffd\ufffd2\u0006\u0010\r\u001a\u00020\u0001J\u0006\u0010\u000e\u001a\u00020\ufffd\ufffdJ\u000e\u0010\u000e\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\nJ\u0006\u0010\u0010\u001a\u00020\ufffd\ufffdJ\u000e\u0010\u0010\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\nJ\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\u0006\u0010\u0013\u001a\u00020\ufffd\ufffdJ\u000e\u0010\u0013\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\nJ\u001a\u0010\u0014\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000f\u001a\u00020\nH\u0007J\u0006\u0010\u0017\u001a\u00020\ufffd\ufffdJ\u000e\u0010\u0017\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\nJ\u0006\u0010\u0018\u001a\u00020\ufffd\ufffdJ\u000e\u0010\u0018\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\nJ\u0006\u0010\u0019\u001a\u00020\ufffd\ufffdJ\u000e\u0010\u0019\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\n\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "x", "", "y", "z", "(DDD)V", "source", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;)V", "", "(III)V", "add", "p_add_1_", "down", "n", "east", "getBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "north", "offset", "side", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "south", "up", "west", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/WBlockPos.class */
public final class WBlockPos extends WVec3i {
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final WBlockPos ORIGIN = new WBlockPos(0, 0, 0);

    @JvmOverloads
    @NotNull
    public final WBlockPos offset(@NotNull IEnumFacing iEnumFacing) {
        return offset$default(this, iEnumFacing, 0, 2, null);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos$Companion;", "", "()V", "ORIGIN", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getORIGIN", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/WBlockPos$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final WBlockPos getORIGIN() {
            return WBlockPos.ORIGIN;
        }
    }

    public WBlockPos(int i, int i2, int i3) {
        super(i, i2, i3);
    }

    public WBlockPos(double d, double d2, double d3) {
        this((int) Math.floor(d), (int) Math.floor(d2), (int) Math.floor(d3));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WBlockPos(@NotNull IEntity source) {
        this(source.getPosX(), source.getPosY(), source.getPosZ());
        Intrinsics.checkParameterIsNotNull(source, "source");
    }

    @NotNull
    public final WBlockPos add(int i, int i2, int i3) {
        return (i == 0 && i2 == 0 && i3 == 0) ? this : new WBlockPos(getX() + i, getY() + i2, getZ() + i3);
    }

    @NotNull
    public final WBlockPos add(@NotNull WVec3i p_add_1_) {
        Intrinsics.checkParameterIsNotNull(p_add_1_, "p_add_1_");
        return (p_add_1_.getX() == 0 && p_add_1_.getY() == 0 && p_add_1_.getZ() == 0) ? this : new WBlockPos(getX() + p_add_1_.getX(), getY() + p_add_1_.getY(), getZ() + p_add_1_.getZ());
    }

    public static WBlockPos offset$default(WBlockPos wBlockPos, IEnumFacing iEnumFacing, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1;
        }
        return wBlockPos.offset(iEnumFacing, i);
    }

    @JvmOverloads
    @NotNull
    public final WBlockPos offset(@NotNull IEnumFacing side, int i) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        return i == 0 ? this : new WBlockPos(getX() + (side.getDirectionVec().getX() * i), getY() + (side.getDirectionVec().getY() * i), getZ() + (side.getDirectionVec().getZ() * i));
    }

    @NotNull
    /* renamed from: up */
    public final WBlockPos m43up() {
        return m44up(1);
    }

    @NotNull
    /* renamed from: up */
    public final WBlockPos m44up(int i) {
        return offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.UP), i);
    }

    @NotNull
    public final WBlockPos down() {
        return down(1);
    }

    @NotNull
    public final WBlockPos down(int i) {
        return offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.DOWN), i);
    }

    @NotNull
    public final WBlockPos west() {
        return west(1);
    }

    @NotNull
    public final WBlockPos west(int i) {
        return offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.WEST), i);
    }

    @NotNull
    public final WBlockPos east() {
        return east(1);
    }

    @NotNull
    public final WBlockPos east(int i) {
        return offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.EAST), i);
    }

    @NotNull
    public final WBlockPos north() {
        return north(1);
    }

    @NotNull
    public final WBlockPos north(int i) {
        return offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.NORTH), i);
    }

    @NotNull
    public final WBlockPos south() {
        return south(1);
    }

    @NotNull
    public final WBlockPos south(int i) {
        return offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.SOUTH), i);
    }

    @Nullable
    public final IBlock getBlock() {
        return BlockUtils.getBlock(this);
    }
}

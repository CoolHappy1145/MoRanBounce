package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u0010\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0012\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "boundingBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;)V", "getBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "getBoundingBox", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "setBoundingBox", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;)V", "x", "", "getX", "()I", "y", "getY", "z", "getZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/BlockBBEvent.class */
public final class BlockBBEvent extends Event {

    /* renamed from: x */
    private final int f111x;

    /* renamed from: y */
    private final int f112y;

    /* renamed from: z */
    private final int f113z;

    @NotNull
    private final IBlock block;

    @Nullable
    private IAxisAlignedBB boundingBox;

    @NotNull
    public final IBlock getBlock() {
        return this.block;
    }

    @Nullable
    public final IAxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public final void setBoundingBox(@Nullable IAxisAlignedBB iAxisAlignedBB) {
        this.boundingBox = iAxisAlignedBB;
    }

    public BlockBBEvent(@NotNull WBlockPos blockPos, @NotNull IBlock block, @Nullable IAxisAlignedBB iAxisAlignedBB) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Intrinsics.checkParameterIsNotNull(block, "block");
        this.block = block;
        this.boundingBox = iAxisAlignedBB;
        this.f111x = blockPos.getX();
        this.f112y = blockPos.getY();
        this.f113z = blockPos.getZ();
    }

    public final int getX() {
        return this.f111x;
    }

    public final int getY() {
        return this.f112y;
    }

    public final int getZ() {
        return this.f113z;
    }
}

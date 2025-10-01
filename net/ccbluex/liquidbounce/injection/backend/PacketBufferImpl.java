package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.network.IPacketBuffer;
import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PacketBufferImpl;", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "wrapped", "Lnet/minecraft/network/PacketBuffer;", "(Lnet/minecraft/network/PacketBuffer;)V", "getWrapped", "()Lnet/minecraft/network/PacketBuffer;", "equals", "", "other", "", "writeBytes", "", "payload", "", "writeItemStackToBuffer", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "writeString", "vanilla", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PacketBufferImpl.class */
public final class PacketBufferImpl implements IPacketBuffer {

    @NotNull
    private final PacketBuffer wrapped;

    @NotNull
    public final PacketBuffer getWrapped() {
        return this.wrapped;
    }

    public PacketBufferImpl(@NotNull PacketBuffer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.network.IPacketBuffer
    public void writeBytes(@NotNull byte[] payload) {
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        this.wrapped.writeBytes(payload);
    }

    @Override // net.ccbluex.liquidbounce.api.network.IPacketBuffer
    public void writeItemStackToBuffer(@NotNull IItemStack itemStack) {
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");
        this.wrapped.func_150788_a(((ItemStackImpl) itemStack).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.network.IPacketBuffer
    @NotNull
    public IPacketBuffer writeString(@NotNull String vanilla) {
        Intrinsics.checkParameterIsNotNull(vanilla, "vanilla");
        this.wrapped.func_180714_a(vanilla);
        return this;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PacketBufferImpl) && Intrinsics.areEqual(((PacketBufferImpl) obj).wrapped, this.wrapped);
    }
}

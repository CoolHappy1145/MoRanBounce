package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacketByINetHandlerPlayServer;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PacketByINetHandlerPlayServerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacketByINetHandlerPlayServer;", "wrapped", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "(Lnet/minecraft/network/Packet;)V", "getWrapped", "()Lnet/minecraft/network/Packet;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PacketByINetHandlerPlayServerImpl.class */
public final class PacketByINetHandlerPlayServerImpl implements IPacketByINetHandlerPlayServer {

    @NotNull
    private final Packet wrapped;

    @NotNull
    public final Packet getWrapped() {
        return this.wrapped;
    }

    public PacketByINetHandlerPlayServerImpl(@NotNull Packet wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }
}

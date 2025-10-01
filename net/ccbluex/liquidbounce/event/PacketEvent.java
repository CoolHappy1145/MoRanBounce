package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "(Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;)V", "getPacket", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/PacketEvent.class */
public final class PacketEvent extends CancellableEvent {

    @NotNull
    private final IPacket packet;

    @NotNull
    public final IPacket getPacket() {
        return this.packet;
    }

    public PacketEvent(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        this.packet = packet;
    }
}

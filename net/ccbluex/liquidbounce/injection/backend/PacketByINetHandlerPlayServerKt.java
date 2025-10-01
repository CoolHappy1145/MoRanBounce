package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacketByINetHandlerPlayServer;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0013\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0086\b\u001a\u0013\u0010\u0004\u001a\u00020\u0003*\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0086\b\u00a8\u0006\u0005"}, m27d2 = {"unwrap", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacketByINetHandlerPlayServer;", "wrap", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PacketByINetHandlerPlayServerKt.class */
public final class PacketByINetHandlerPlayServerKt {
    @NotNull
    public static final Packet unwrap(@NotNull IPacketByINetHandlerPlayServer unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return ((PacketByINetHandlerPlayServerImpl) unwrap).getWrapped();
    }

    @NotNull
    public static final IPacketByINetHandlerPlayServer wrap(@NotNull Packet wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new PacketByINetHandlerPlayServerImpl(wrap);
    }
}

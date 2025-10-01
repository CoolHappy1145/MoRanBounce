package net.ccbluex.liquidbounce.api.minecraft.network.play.client;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\ufffd\ufffd2\u00020\u0001:\u0001\u0002\u00a8\u0006\u0003"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "WEnumState", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus.class */
public interface ICPacketClientStatus extends IPacket {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "", "(Ljava/lang/String;I)V", "PERFORM_RESPAWN", "REQUEST_STATS", "OPEN_INVENTORY_ACHIEVEMENT", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState.class */
    public enum WEnumState {
        PERFORM_RESPAWN,
        REQUEST_STATS,
        OPEN_INVENTORY_ACHIEVEMENT
    }
}

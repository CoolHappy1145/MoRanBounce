package net.ccbluex.liquidbounce.api.minecraft.network.play.client;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.network.IPacketBuffer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\ufffd\ufffd2\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "channelName", "", "getChannelName", "()Ljava/lang/String;", "data", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "getData", "()Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "setData", "(Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload.class */
public interface ICPacketCustomPayload extends IPacket {
    @NotNull
    IPacketBuffer getData();

    void setData(@NotNull IPacketBuffer iPacketBuffer);

    @NotNull
    String getChannelName();
}

package net.ccbluex.liquidbounce.chat;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u0003H&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH&\u00a8\u0006\u0010"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/ClientListener;", "", "onConnect", "", "onConnected", "onDisconnect", "onError", "cause", "", "onHandshake", "success", "", "onLogon", "onPacket", "packet", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/ClientListener.class */
public interface ClientListener {
    void onConnect();

    void onConnected();

    void onHandshake(boolean z);

    void onDisconnect();

    void onLogon();

    void onPacket(@NotNull Packet packet);

    void onError(@NotNull Throwable th);
}

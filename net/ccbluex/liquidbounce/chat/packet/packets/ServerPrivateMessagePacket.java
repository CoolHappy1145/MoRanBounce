package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerPrivateMessagePacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "receiver", "", "content", "(Ljava/lang/String;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getReceiver", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/packets/ServerPrivateMessagePacket.class */
public final class ServerPrivateMessagePacket implements Packet {

    @SerializedName("receiver")
    @NotNull
    private final String receiver;

    @SerializedName("content")
    @NotNull
    private final String content;

    @NotNull
    public final String component1() {
        return this.receiver;
    }

    @NotNull
    public final String component2() {
        return this.content;
    }

    @NotNull
    public final ServerPrivateMessagePacket copy(@NotNull String receiver, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(receiver, "receiver");
        Intrinsics.checkParameterIsNotNull(content, "content");
        return new ServerPrivateMessagePacket(receiver, content);
    }

    public static ServerPrivateMessagePacket copy$default(ServerPrivateMessagePacket serverPrivateMessagePacket, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = serverPrivateMessagePacket.receiver;
        }
        if ((i & 2) != 0) {
            str2 = serverPrivateMessagePacket.content;
        }
        return serverPrivateMessagePacket.copy(str, str2);
    }

    @NotNull
    public String toString() {
        return "ServerPrivateMessagePacket(receiver=" + this.receiver + ", content=" + this.content + ")";
    }

    public int hashCode() {
        String str = this.receiver;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.content;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServerPrivateMessagePacket)) {
            return false;
        }
        ServerPrivateMessagePacket serverPrivateMessagePacket = (ServerPrivateMessagePacket) obj;
        return Intrinsics.areEqual(this.receiver, serverPrivateMessagePacket.receiver) && Intrinsics.areEqual(this.content, serverPrivateMessagePacket.content);
    }

    public ServerPrivateMessagePacket(@NotNull String receiver, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(receiver, "receiver");
        Intrinsics.checkParameterIsNotNull(content, "content");
        this.receiver = receiver;
        this.content = content;
    }

    @NotNull
    public final String getReceiver() {
        return this.receiver;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }
}

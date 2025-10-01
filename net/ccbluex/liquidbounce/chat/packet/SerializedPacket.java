package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/SerializedPacket;", "", "packetName", "", "packetContent", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;)V", "getPacketContent", "()Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "getPacketName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/SerializedPacket.class */
public final class SerializedPacket {

    @SerializedName("m")
    @NotNull
    private final String packetName;

    @SerializedName("c")
    @Nullable
    private final Packet packetContent;

    @NotNull
    public final String component1() {
        return this.packetName;
    }

    @Nullable
    public final Packet component2() {
        return this.packetContent;
    }

    @NotNull
    public final SerializedPacket copy(@NotNull String packetName, @Nullable Packet packet) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        return new SerializedPacket(packetName, packet);
    }

    public static SerializedPacket copy$default(SerializedPacket serializedPacket, String str, Packet packet, int i, Object obj) {
        if ((i & 1) != 0) {
            str = serializedPacket.packetName;
        }
        if ((i & 2) != 0) {
            packet = serializedPacket.packetContent;
        }
        return serializedPacket.copy(str, packet);
    }

    @NotNull
    public String toString() {
        return "SerializedPacket(packetName=" + this.packetName + ", packetContent=" + this.packetContent + ")";
    }

    public int hashCode() {
        String str = this.packetName;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        Packet packet = this.packetContent;
        return iHashCode + (packet != null ? packet.hashCode() : 0);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SerializedPacket)) {
            return false;
        }
        SerializedPacket serializedPacket = (SerializedPacket) obj;
        return Intrinsics.areEqual(this.packetName, serializedPacket.packetName) && Intrinsics.areEqual(this.packetContent, serializedPacket.packetContent);
    }

    public SerializedPacket(@NotNull String packetName, @Nullable Packet packet) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        this.packetName = packetName;
        this.packetContent = packet;
    }

    @NotNull
    public final String getPacketName() {
        return this.packetName;
    }

    @Nullable
    public final Packet getPacketContent() {
        return this.packetContent;
    }
}

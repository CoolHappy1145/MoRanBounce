package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientSuccessPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "reason", "", "(Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/packets/ClientSuccessPacket.class */
public final class ClientSuccessPacket implements Packet {

    @SerializedName("reason")
    @NotNull
    private final String reason;

    @NotNull
    public final String component1() {
        return this.reason;
    }

    @NotNull
    public final ClientSuccessPacket copy(@NotNull String reason) {
        Intrinsics.checkParameterIsNotNull(reason, "reason");
        return new ClientSuccessPacket(reason);
    }

    public static ClientSuccessPacket copy$default(ClientSuccessPacket clientSuccessPacket, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clientSuccessPacket.reason;
        }
        return clientSuccessPacket.copy(str);
    }

    @NotNull
    public String toString() {
        return "ClientSuccessPacket(reason=" + this.reason + ")";
    }

    public int hashCode() {
        String str = this.reason;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof ClientSuccessPacket) && Intrinsics.areEqual(this.reason, ((ClientSuccessPacket) obj).reason);
        }
        return true;
    }

    public ClientSuccessPacket(@NotNull String reason) {
        Intrinsics.checkParameterIsNotNull(reason, "reason");
        this.reason = reason;
    }

    @NotNull
    public final String getReason() {
        return this.reason;
    }
}

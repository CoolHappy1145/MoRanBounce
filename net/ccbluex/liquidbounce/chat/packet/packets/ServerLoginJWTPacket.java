package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerLoginJWTPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "token", "", "allowMessages", "", "(Ljava/lang/String;Z)V", "getAllowMessages", "()Z", "getToken", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/packets/ServerLoginJWTPacket.class */
public final class ServerLoginJWTPacket implements Packet {

    @SerializedName("token")
    @NotNull
    private final String token;

    @SerializedName("allow_messages")
    private final boolean allowMessages;

    @NotNull
    public final String component1() {
        return this.token;
    }

    public final boolean component2() {
        return this.allowMessages;
    }

    @NotNull
    public final ServerLoginJWTPacket copy(@NotNull String token, boolean z) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        return new ServerLoginJWTPacket(token, z);
    }

    public static ServerLoginJWTPacket copy$default(ServerLoginJWTPacket serverLoginJWTPacket, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = serverLoginJWTPacket.token;
        }
        if ((i & 2) != 0) {
            z = serverLoginJWTPacket.allowMessages;
        }
        return serverLoginJWTPacket.copy(str, z);
    }

    @NotNull
    public String toString() {
        return "ServerLoginJWTPacket(token=" + this.token + ", allowMessages=" + this.allowMessages + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.token;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        boolean z = this.allowMessages;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode + i;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServerLoginJWTPacket)) {
            return false;
        }
        ServerLoginJWTPacket serverLoginJWTPacket = (ServerLoginJWTPacket) obj;
        return Intrinsics.areEqual(this.token, serverLoginJWTPacket.token) && this.allowMessages == serverLoginJWTPacket.allowMessages;
    }

    public ServerLoginJWTPacket(@NotNull String token, boolean z) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        this.token = token;
        this.allowMessages = z;
    }

    @NotNull
    public final String getToken() {
        return this.token;
    }

    public final boolean getAllowMessages() {
        return this.allowMessages;
    }
}

package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0012\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerLoginMojangPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "name", "", "uuid", "Ljava/util/UUID;", "allowMessages", "", "(Ljava/lang/String;Ljava/util/UUID;Z)V", "getAllowMessages", "()Z", "getName", "()Ljava/lang/String;", "getUuid", "()Ljava/util/UUID;", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/packets/ServerLoginMojangPacket.class */
public final class ServerLoginMojangPacket implements Packet {

    @SerializedName("name")
    @NotNull
    private final String name;

    @SerializedName("uuid")
    @NotNull
    private final UUID uuid;

    @SerializedName("allow_messages")
    private final boolean allowMessages;

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final UUID component2() {
        return this.uuid;
    }

    public final boolean component3() {
        return this.allowMessages;
    }

    @NotNull
    public final ServerLoginMojangPacket copy(@NotNull String name, @NotNull UUID uuid, boolean z) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        return new ServerLoginMojangPacket(name, uuid, z);
    }

    public static ServerLoginMojangPacket copy$default(ServerLoginMojangPacket serverLoginMojangPacket, String str, UUID uuid, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = serverLoginMojangPacket.name;
        }
        if ((i & 2) != 0) {
            uuid = serverLoginMojangPacket.uuid;
        }
        if ((i & 4) != 0) {
            z = serverLoginMojangPacket.allowMessages;
        }
        return serverLoginMojangPacket.copy(str, uuid, z);
    }

    @NotNull
    public String toString() {
        return "ServerLoginMojangPacket(name=" + this.name + ", uuid=" + this.uuid + ", allowMessages=" + this.allowMessages + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        UUID uuid = this.uuid;
        int iHashCode2 = (iHashCode + (uuid != null ? uuid.hashCode() : 0)) * 31;
        boolean z = this.allowMessages;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode2 + i;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServerLoginMojangPacket)) {
            return false;
        }
        ServerLoginMojangPacket serverLoginMojangPacket = (ServerLoginMojangPacket) obj;
        return Intrinsics.areEqual(this.name, serverLoginMojangPacket.name) && Intrinsics.areEqual(this.uuid, serverLoginMojangPacket.uuid) && this.allowMessages == serverLoginMojangPacket.allowMessages;
    }

    public ServerLoginMojangPacket(@NotNull String name, @NotNull UUID uuid, boolean z) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        this.name = name;
        this.uuid = uuid;
        this.allowMessages = z;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    public final boolean getAllowMessages() {
        return this.allowMessages;
    }
}

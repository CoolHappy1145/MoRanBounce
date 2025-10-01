package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J'\u0010\u0010\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\tR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientMessagePacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "id", "", "user", "Lnet/ccbluex/liquidbounce/chat/User;", "content", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/chat/User;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getId", "getUser", "()Lnet/ccbluex/liquidbounce/chat/User;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/packets/ClientMessagePacket.class */
public final class ClientMessagePacket implements Packet {

    /* renamed from: id */
    @SerializedName("author_id")
    @NotNull
    private final String f109id;

    @SerializedName("author_info")
    @NotNull
    private final User user;

    @SerializedName("content")
    @NotNull
    private final String content;

    @NotNull
    public final String component1() {
        return this.f109id;
    }

    @NotNull
    public final User component2() {
        return this.user;
    }

    @NotNull
    public final String component3() {
        return this.content;
    }

    @NotNull
    public final ClientMessagePacket copy(@NotNull String id, @NotNull User user, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull(user, "user");
        Intrinsics.checkParameterIsNotNull(content, "content");
        return new ClientMessagePacket(id, user, content);
    }

    public static ClientMessagePacket copy$default(ClientMessagePacket clientMessagePacket, String str, User user, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clientMessagePacket.f109id;
        }
        if ((i & 2) != 0) {
            user = clientMessagePacket.user;
        }
        if ((i & 4) != 0) {
            str2 = clientMessagePacket.content;
        }
        return clientMessagePacket.copy(str, user, str2);
    }

    @NotNull
    public String toString() {
        return "ClientMessagePacket(id=" + this.f109id + ", user=" + this.user + ", content=" + this.content + ")";
    }

    public int hashCode() {
        String str = this.f109id;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        User user = this.user;
        int iHashCode2 = (iHashCode + (user != null ? user.hashCode() : 0)) * 31;
        String str2 = this.content;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClientMessagePacket)) {
            return false;
        }
        ClientMessagePacket clientMessagePacket = (ClientMessagePacket) obj;
        return Intrinsics.areEqual(this.f109id, clientMessagePacket.f109id) && Intrinsics.areEqual(this.user, clientMessagePacket.user) && Intrinsics.areEqual(this.content, clientMessagePacket.content);
    }

    public ClientMessagePacket(@NotNull String id, @NotNull User user, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull(user, "user");
        Intrinsics.checkParameterIsNotNull(content, "content");
        this.f109id = id;
        this.user = user;
        this.content = content;
    }

    @NotNull
    public final String getId() {
        return this.f109id;
    }

    @NotNull
    public final User getUser() {
        return this.user;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }
}

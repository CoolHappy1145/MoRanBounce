package net.ccbluex.liquidbounce.chat;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/User;", "", "name", "", "uuid", "Ljava/util/UUID;", "(Ljava/lang/String;Ljava/util/UUID;)V", "getName", "()Ljava/lang/String;", "getUuid", "()Ljava/util/UUID;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/User.class */
public final class User {

    @SerializedName("name")
    @NotNull
    private final String name;

    @SerializedName("uuid")
    @NotNull
    private final UUID uuid;

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final UUID component2() {
        return this.uuid;
    }

    @NotNull
    public final User copy(@NotNull String name, @NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        return new User(name, uuid);
    }

    public static User copy$default(User user, String str, UUID uuid, int i, Object obj) {
        if ((i & 1) != 0) {
            str = user.name;
        }
        if ((i & 2) != 0) {
            uuid = user.uuid;
        }
        return user.copy(str, uuid);
    }

    @NotNull
    public String toString() {
        return "User(name=" + this.name + ", uuid=" + this.uuid + ")";
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        UUID uuid = this.uuid;
        return iHashCode + (uuid != null ? uuid.hashCode() : 0);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return Intrinsics.areEqual(this.name, user.name) && Intrinsics.areEqual(this.uuid, user.uuid);
    }

    public User(@NotNull String name, @NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        this.name = name;
        this.uuid = uuid;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }
}

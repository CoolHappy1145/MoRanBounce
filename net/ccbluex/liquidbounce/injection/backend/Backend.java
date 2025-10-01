package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\ufffd\ufffd\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\r\u001a\u00020\u000eH\u0086\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/Backend;", "", "()V", "MINECRAFT_VERSION", "", "MINECRAFT_VERSION_MAJOR", "", "MINECRAFT_VERSION_MINOR", "MINECRAFT_VERSION_PATCH", "REPRESENTED_BACKEND_VERSION", "Lnet/ccbluex/liquidbounce/api/MinecraftVersion;", "getREPRESENTED_BACKEND_VERSION", "()Lnet/ccbluex/liquidbounce/api/MinecraftVersion;", "BACKEND_UNSUPPORTED", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/Backend.class */
public final class Backend {

    @NotNull
    public static final String MINECRAFT_VERSION = "1.12.2";
    public static final int MINECRAFT_VERSION_MAJOR = 1;
    public static final int MINECRAFT_VERSION_MINOR = 12;
    public static final int MINECRAFT_VERSION_PATCH = 2;
    public static final Backend INSTANCE = new Backend();

    @NotNull
    private static final MinecraftVersion REPRESENTED_BACKEND_VERSION = MinecraftVersion.MC_1_12;

    private Backend() {
    }

    @NotNull
    public final MinecraftVersion getREPRESENTED_BACKEND_VERSION() {
        return REPRESENTED_BACKEND_VERSION;
    }

    @NotNull
    public final Void BACKEND_UNSUPPORTED() {
        throw new NotImplementedError("1.12.2 doesn't support this feature'");
    }
}

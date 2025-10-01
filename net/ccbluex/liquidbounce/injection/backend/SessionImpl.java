package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.ISession;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SessionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "wrapped", "Lnet/minecraft/util/Session;", "(Lnet/minecraft/util/Session;)V", "playerId", "", "getPlayerId", "()Ljava/lang/String;", "profile", "Lcom/mojang/authlib/GameProfile;", "getProfile", "()Lcom/mojang/authlib/GameProfile;", "sessionType", "getSessionType", "token", "getToken", "username", "getUsername", "getWrapped", "()Lnet/minecraft/util/Session;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SessionImpl.class */
public final class SessionImpl implements ISession {

    @NotNull
    private final Session wrapped;

    @NotNull
    public final Session getWrapped() {
        return this.wrapped;
    }

    public SessionImpl(@NotNull Session wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ISession
    @NotNull
    public GameProfile getProfile() {
        GameProfile gameProfileFunc_148256_e = this.wrapped.func_148256_e();
        Intrinsics.checkExpressionValueIsNotNull(gameProfileFunc_148256_e, "wrapped.profile");
        return gameProfileFunc_148256_e;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ISession
    @NotNull
    public String getUsername() {
        String strFunc_111285_a = this.wrapped.func_111285_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_111285_a, "wrapped.username");
        return strFunc_111285_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ISession
    @NotNull
    public String getPlayerId() {
        String strFunc_148255_b = this.wrapped.func_148255_b();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_148255_b, "wrapped.playerID");
        return strFunc_148255_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ISession
    @NotNull
    public String getSessionType() {
        return this.wrapped.field_152429_d.name();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ISession
    @NotNull
    public String getToken() {
        String strFunc_148254_d = this.wrapped.func_148254_d();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_148254_d, "wrapped.token");
        return strFunc_148254_d;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof SessionImpl) && Intrinsics.areEqual(((SessionImpl) obj).wrapped, this.wrapped);
    }
}

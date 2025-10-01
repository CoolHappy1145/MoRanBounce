package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0014\u0010\u0013\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\bR\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ServerDataImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "wrapped", "Lnet/minecraft/client/multiplayer/ServerData;", "(Lnet/minecraft/client/multiplayer/ServerData;)V", "gameVersion", "", "getGameVersion", "()Ljava/lang/String;", "pingToServer", "", "getPingToServer", "()J", "populationInfo", "getPopulationInfo", "serverIP", "getServerIP", "serverMOTD", "getServerMOTD", "serverName", "getServerName", "version", "", "getVersion", "()I", "getWrapped", "()Lnet/minecraft/client/multiplayer/ServerData;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ServerDataImpl.class */
public final class ServerDataImpl implements IServerData {

    @NotNull
    private final ServerData wrapped;

    @NotNull
    public final ServerData getWrapped() {
        return this.wrapped;
    }

    public ServerDataImpl(@NotNull ServerData wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    public long getPingToServer() {
        return this.wrapped.field_78844_e;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    public int getVersion() {
        return this.wrapped.field_82821_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    @NotNull
    public String getGameVersion() {
        String str = this.wrapped.field_82822_g;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.gameVersion");
        return str;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    @NotNull
    public String getServerMOTD() {
        String str = this.wrapped.field_78843_d;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.serverMOTD");
        return str;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    @NotNull
    public String getPopulationInfo() {
        String str = this.wrapped.field_78846_c;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.populationInfo");
        return str;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    @NotNull
    public String getServerName() {
        String str = this.wrapped.field_78847_a;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.serverName");
        return str;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData
    @NotNull
    public String getServerIP() {
        String str = this.wrapped.field_78845_b;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.serverIP");
        return str;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ServerDataImpl) && Intrinsics.areEqual(((ServerDataImpl) obj).wrapped, this.wrapped);
    }
}

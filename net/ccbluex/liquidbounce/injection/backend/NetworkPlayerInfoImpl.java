package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/NetworkPlayerInfoImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/INetworkPlayerInfo;", "wrapped", "Lnet/minecraft/client/network/NetworkPlayerInfo;", "(Lnet/minecraft/client/network/NetworkPlayerInfo;)V", "displayName", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "getDisplayName", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "gameProfile", "Lcom/mojang/authlib/GameProfile;", "getGameProfile", "()Lcom/mojang/authlib/GameProfile;", "locationSkin", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getLocationSkin", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "playerTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "getPlayerTeam", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "responseTime", "", "getResponseTime", "()I", "getWrapped", "()Lnet/minecraft/client/network/NetworkPlayerInfo;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/NetworkPlayerInfoImpl.class */
public final class NetworkPlayerInfoImpl implements INetworkPlayerInfo {

    @NotNull
    private final NetworkPlayerInfo wrapped;

    @NotNull
    public final NetworkPlayerInfo getWrapped() {
        return this.wrapped;
    }

    public NetworkPlayerInfoImpl(@NotNull NetworkPlayerInfo wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo
    @NotNull
    public IResourceLocation getLocationSkin() {
        ResourceLocation resourceLocationFunc_178837_g = this.wrapped.func_178837_g();
        Intrinsics.checkExpressionValueIsNotNull(resourceLocationFunc_178837_g, "wrapped.locationSkin");
        return new ResourceLocationImpl(resourceLocationFunc_178837_g);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo
    public int getResponseTime() {
        return this.wrapped.func_178853_c();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo
    @NotNull
    public GameProfile getGameProfile() {
        GameProfile gameProfileFunc_178845_a = this.wrapped.func_178845_a();
        Intrinsics.checkExpressionValueIsNotNull(gameProfileFunc_178845_a, "wrapped.gameProfile");
        return gameProfileFunc_178845_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo
    @Nullable
    public ITeam getPlayerTeam() {
        Team teamFunc_178850_i = this.wrapped.func_178850_i();
        if (teamFunc_178850_i != null) {
            return new TeamImpl(teamFunc_178850_i);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo
    @Nullable
    public IIChatComponent getDisplayName() {
        ITextComponent iTextComponentFunc_178854_k = this.wrapped.func_178854_k();
        if (iTextComponentFunc_178854_k != null) {
            return new IChatComponentImpl(iTextComponentFunc_178854_k);
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof NetworkPlayerInfoImpl) && Intrinsics.areEqual(((NetworkPlayerInfoImpl) obj).wrapped, this.wrapped);
    }
}

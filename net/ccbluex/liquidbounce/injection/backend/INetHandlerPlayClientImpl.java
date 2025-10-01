package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.NetworkManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/INetHandlerPlayClientImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "wrapped", "Lnet/minecraft/client/network/NetHandlerPlayClient;", "(Lnet/minecraft/client/network/NetHandlerPlayClient;)V", "networkManager", "Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "getNetworkManager", "()Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "playerInfoMap", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/INetworkPlayerInfo;", "getPlayerInfoMap", "()Ljava/util/Collection;", "getWrapped", "()Lnet/minecraft/client/network/NetHandlerPlayClient;", "addToSendQueue", "", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "equals", "", "other", "", "getPlayerInfo", "uuid", "Ljava/util/UUID;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/INetHandlerPlayClientImpl.class */
public final class INetHandlerPlayClientImpl implements IINetHandlerPlayClient {

    @NotNull
    private final NetHandlerPlayClient wrapped;

    @NotNull
    public final NetHandlerPlayClient getWrapped() {
        return this.wrapped;
    }

    public INetHandlerPlayClientImpl(@NotNull NetHandlerPlayClient wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient
    @NotNull
    public INetworkManager getNetworkManager() {
        NetworkManager networkManagerFunc_147298_b = this.wrapped.func_147298_b();
        Intrinsics.checkExpressionValueIsNotNull(networkManagerFunc_147298_b, "wrapped.networkManager");
        return new NetworkManagerImpl(networkManagerFunc_147298_b);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient
    @NotNull
    public Collection getPlayerInfoMap() {
        return new WrappedCollection(this.wrapped.func_175106_d(), INetHandlerPlayClientImpl$playerInfoMap$1.INSTANCE, INetHandlerPlayClientImpl$playerInfoMap$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient
    @Nullable
    public INetworkPlayerInfo getPlayerInfo(@NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        NetworkPlayerInfo networkPlayerInfoFunc_175102_a = this.wrapped.func_175102_a(uuid);
        if (networkPlayerInfoFunc_175102_a != null) {
            return new NetworkPlayerInfoImpl(networkPlayerInfoFunc_175102_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient
    public void addToSendQueue(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        this.wrapped.func_147297_a(((PacketImpl) packet).getWrapped());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof INetHandlerPlayClientImpl) && Intrinsics.areEqual(((INetHandlerPlayClientImpl) obj).wrapped, this.wrapped);
    }
}

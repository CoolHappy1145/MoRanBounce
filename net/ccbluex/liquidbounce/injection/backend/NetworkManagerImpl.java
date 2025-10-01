package net.ccbluex.liquidbounce.injection.backend;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import javax.crypto.SecretKey;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/NetworkManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "wrapped", "Lnet/minecraft/network/NetworkManager;", "(Lnet/minecraft/network/NetworkManager;)V", "getWrapped", "()Lnet/minecraft/network/NetworkManager;", "enableEncryption", "", "secretKey", "Ljavax/crypto/SecretKey;", "equals", "", "other", "", "sendPacket", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "listener", "Lkotlin/Function0;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/NetworkManagerImpl.class */
public final class NetworkManagerImpl implements INetworkManager {

    @NotNull
    private final NetworkManager wrapped;

    @NotNull
    public final NetworkManager getWrapped() {
        return this.wrapped;
    }

    public NetworkManagerImpl(@NotNull NetworkManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.INetworkManager
    public void sendPacket(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        this.wrapped.func_179290_a(((PacketImpl) packet).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.INetworkManager
    public void sendPacket(@NotNull IPacket packet, @NotNull Function0 listener) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        this.wrapped.func_179288_a(((PacketImpl) packet).getWrapped(), new GenericFutureListener(listener) { // from class: net.ccbluex.liquidbounce.injection.backend.NetworkManagerImpl.sendPacket.1
            final Function0 $listener;

            {
                this.$listener = listener;
            }

            public final void operationComplete(Future future) {
                this.$listener.invoke();
            }
        }, new GenericFutureListener[0]);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.INetworkManager
    public void enableEncryption(@NotNull SecretKey secretKey) {
        Intrinsics.checkParameterIsNotNull(secretKey, "secretKey");
        this.wrapped.func_150727_a(secretKey);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof NetworkManagerImpl) && Intrinsics.areEqual(((NetworkManagerImpl) obj).wrapped, this.wrapped);
    }
}

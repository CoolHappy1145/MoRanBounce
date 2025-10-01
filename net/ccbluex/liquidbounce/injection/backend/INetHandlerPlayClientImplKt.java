package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\r\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0004H\u0086\b\u00a8\u0006\u0005"}, m27d2 = {"unwrap", "Lnet/minecraft/network/play/INetHandlerPlayClient;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "wrap", "Lnet/minecraft/client/network/NetHandlerPlayClient;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/INetHandlerPlayClientImplKt.class */
public final class INetHandlerPlayClientImplKt {
    @NotNull
    public static final INetHandlerPlayClient unwrap(@NotNull IINetHandlerPlayClient unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return ((INetHandlerPlayClientImpl) unwrap).getWrapped();
    }

    @NotNull
    public static final IINetHandlerPlayClient wrap(@NotNull NetHandlerPlayClient wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new INetHandlerPlayClientImpl(wrap);
    }
}

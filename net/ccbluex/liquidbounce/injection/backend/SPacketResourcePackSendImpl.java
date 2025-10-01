package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\n\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SPacketResourcePackSendImpl;", "T", "Lnet/minecraft/network/play/server/SPacketResourcePackSend;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketResourcePackSend;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketResourcePackSend;)V", "hash", "", "getHash", "()Ljava/lang/String;", "url", "getUrl", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SPacketResourcePackSendImpl.class */
public final class SPacketResourcePackSendImpl extends PacketImpl implements ISPacketResourcePackSend {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SPacketResourcePackSendImpl(@NotNull SPacketResourcePackSend wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend
    @NotNull
    public String getUrl() {
        String strFunc_179783_a = getWrapped().func_179783_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_179783_a, "wrapped.url");
        return strFunc_179783_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend
    @NotNull
    public String getHash() {
        String strFunc_179784_b = getWrapped().func_179784_b();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_179784_b, "wrapped.hash");
        return strFunc_179784_b;
    }
}

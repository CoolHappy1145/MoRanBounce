package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketChatMessage;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketChatMessageImpl;", "T", "Lnet/minecraft/network/play/client/CPacketChatMessage;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketChatMessage;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketChatMessage;)V", PropertyDescriptor.VALUE, "", "message", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketChatMessageImpl.class */
public final class CPacketChatMessageImpl extends PacketImpl implements ICPacketChatMessage {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketChatMessageImpl(@NotNull CPacketChatMessage wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketChatMessage
    @NotNull
    public String getMessage() {
        String str = getWrapped().field_149440_a;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.message");
        return str;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketChatMessage
    public void setMessage(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        getWrapped().field_149440_a = value;
    }
}

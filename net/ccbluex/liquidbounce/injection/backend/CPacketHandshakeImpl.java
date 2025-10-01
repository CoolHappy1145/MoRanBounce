package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IEnumConnectionState;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketHandshakeImpl;", "T", "Lnet/minecraft/network/handshake/client/C00Handshake;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/handshake/client/ICPacketHandshake;", "wrapped", "(Lnet/minecraft/network/handshake/client/C00Handshake;)V", PropertyDescriptor.VALUE, "", "ip", "getIp", "()Ljava/lang/String;", "setIp", "(Ljava/lang/String;)V", "port", "", "getPort", "()I", "requestedState", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IEnumConnectionState;", "getRequestedState", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/IEnumConnectionState;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketHandshakeImpl.class */
public final class CPacketHandshakeImpl extends PacketImpl implements ICPacketHandshake {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketHandshakeImpl(@NotNull C00Handshake wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake
    public int getPort() {
        return getWrapped().field_149599_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake
    @NotNull
    public String getIp() {
        String str = getWrapped().field_149598_b;
        Intrinsics.checkExpressionValueIsNotNull(str, "wrapped.ip");
        return str;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake
    public void setIp(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        getWrapped().field_149598_b = value;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake
    @NotNull
    public IEnumConnectionState getRequestedState() {
        EnumConnectionState enumConnectionStateFunc_149594_c = getWrapped().func_149594_c();
        Intrinsics.checkExpressionValueIsNotNull(enumConnectionStateFunc_149594_c, "wrapped.requestedState");
        return new EnumConnectionStateImpl(enumConnectionStateFunc_149594_c);
    }
}

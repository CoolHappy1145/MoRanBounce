package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.api.network.IPacketBuffer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR$\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketCustomPayloadImpl;", "T", "Lnet/minecraft/network/play/client/CPacketCustomPayload;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketCustomPayload;)V", "channelName", "", "getChannelName", "()Ljava/lang/String;", PropertyDescriptor.VALUE, "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "data", "getData", "()Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "setData", "(Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketCustomPayloadImpl.class */
public final class CPacketCustomPayloadImpl extends PacketImpl implements ICPacketCustomPayload {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketCustomPayloadImpl(@NotNull CPacketCustomPayload wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload
    @NotNull
    public IPacketBuffer getData() {
        PacketBuffer packetBuffer = getWrapped().field_149561_c;
        Intrinsics.checkExpressionValueIsNotNull(packetBuffer, "wrapped.data");
        return new PacketBufferImpl(packetBuffer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload
    public void setData(@NotNull IPacketBuffer value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        getWrapped().field_149561_c = ((PacketBufferImpl) value).getWrapped();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload
    @NotNull
    public String getChannelName() {
        String strFunc_149559_c = getWrapped().func_149559_c();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_149559_c, "wrapped.channelName");
        return strFunc_149559_c;
    }
}

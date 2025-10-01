package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\u000fR$\u0010\u0013\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\u000f\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SPacketEntityVelocityImpl;", "T", "Lnet/minecraft/network/play/server/SPacketEntityVelocity;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntityVelocity;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketEntityVelocity;)V", "entityID", "", "getEntityID", "()I", PropertyDescriptor.VALUE, "motionX", "getMotionX", "setMotionX", "(I)V", "motionY", "getMotionY", "setMotionY", "motionZ", "getMotionZ", "setMotionZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SPacketEntityVelocityImpl.class */
public final class SPacketEntityVelocityImpl extends PacketImpl implements ISPacketEntityVelocity {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SPacketEntityVelocityImpl(@NotNull SPacketEntityVelocity wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public int getMotionX() {
        return getWrapped().field_149415_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public void setMotionX(int i) {
        getWrapped().field_149415_b = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public int getMotionY() {
        return getWrapped().field_149416_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public void setMotionY(int i) {
        getWrapped().field_149416_c = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public int getMotionZ() {
        return getWrapped().field_149414_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public void setMotionZ(int i) {
        getWrapped().field_149414_d = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity
    public int getEntityID() {
        return getWrapped().func_149412_c();
    }
}

package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\n\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R$\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\rR\u0014\u0010\u0017\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SPacketPosLookImpl;", "T", "Lnet/minecraft/network/play/server/SPacketPlayerPosLook;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketPosLook;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketPlayerPosLook;)V", PropertyDescriptor.VALUE, "", "pitch", "getPitch", "()F", "setPitch", "(F)V", "x", "", "getX", "()D", "y", "getY", "yaw", "getYaw", "setYaw", "z", "getZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SPacketPosLookImpl.class */
public final class SPacketPosLookImpl extends PacketImpl implements ISPacketPosLook {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SPacketPosLookImpl(@NotNull SPacketPlayerPosLook wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public float getYaw() {
        return getWrapped().field_148936_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public void setYaw(float f) {
        getWrapped().field_148936_d = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public float getPitch() {
        return getWrapped().field_148937_e;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public void setPitch(float f) {
        getWrapped().field_148937_e = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public double getX() {
        return getWrapped().func_148932_c();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public double getY() {
        return getWrapped().func_148928_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
    public double getZ() {
        return getWrapped().func_148933_e();
    }
}

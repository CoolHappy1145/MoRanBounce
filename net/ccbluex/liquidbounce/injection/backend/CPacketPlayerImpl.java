package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u000f\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00118V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\rR$\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\u001a8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR$\u0010 \u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\u001a8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR$\u0010#\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00118V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010\u0014\"\u0004\b%\u0010\u0016R$\u0010&\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\u001a8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b'\u0010\u001d\"\u0004\b(\u0010\u001f\u00a8\u0006)"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketPlayerImpl;", "T", "Lnet/minecraft/network/play/client/CPacketPlayer;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketPlayer;)V", PropertyDescriptor.VALUE, "", "moving", "getMoving", "()Z", "setMoving", "(Z)V", "onGround", "getOnGround", "setOnGround", "", "pitch", "getPitch", "()F", "setPitch", "(F)V", "rotating", "getRotating", "setRotating", "", "x", "getX", "()D", "setX", "(D)V", "y", "getY", "setY", "yaw", "getYaw", "setYaw", "z", "getZ", "setZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketPlayerImpl.class */
public final class CPacketPlayerImpl extends PacketImpl implements ICPacketPlayer {
    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public boolean isRotating() {
        return getRotating();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketPlayerImpl(@NotNull CPacketPlayer wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public double getX() {
        return getWrapped().field_149479_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setX(double d) {
        getWrapped().field_149479_a = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public double getY() {
        return getWrapped().field_149477_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setY(double d) {
        getWrapped().field_149477_b = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public double getZ() {
        return getWrapped().field_149478_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setZ(double d) {
        getWrapped().field_149478_c = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public float getYaw() {
        return getWrapped().field_149476_e;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setYaw(float f) {
        getWrapped().field_149476_e = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public float getPitch() {
        return getWrapped().field_149473_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setPitch(float f) {
        getWrapped().field_149473_f = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public boolean getOnGround() {
        return getWrapped().field_149474_g;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setOnGround(boolean z) {
        getWrapped().field_149474_g = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public boolean getMoving() {
        return getWrapped().field_149480_h;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setMoving(boolean z) {
        getWrapped().field_149480_h = z;
    }

    public boolean getRotating() {
        return getWrapped().field_149481_i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
    public void setRotating(boolean z) {
        getWrapped().field_149481_i = z;
    }
}

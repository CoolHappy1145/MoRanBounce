package net.ccbluex.liquidbounce.api.minecraft.network.play.server;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\n\bf\u0018\ufffd\ufffd2\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0018\u0010\u000e\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u0012\u0010\u0011\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketPosLook;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "pitch", "", "getPitch", "()F", "setPitch", "(F)V", "x", "", "getX", "()D", "y", "getY", "yaw", "getYaw", "setYaw", "z", "getZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketPosLook.class */
public interface ISPacketPosLook extends IPacket {
    float getYaw();

    void setYaw(float f);

    float getPitch();

    void setPitch(float f);

    double getX();

    double getY();

    double getZ();
}

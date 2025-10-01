package net.ccbluex.liquidbounce.api.minecraft.network.play.client;

import kotlin.Metadata;
import kotlin.jvm.JvmName;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u000e\bf\u0018\ufffd\ufffd2\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00038gX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u0018\u0010\u0014\u001a\u00020\u0015X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u0015X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0017\"\u0004\b\u001c\u0010\u0019R\u0018\u0010\u001d\u001a\u00020\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u000e\"\u0004\b\u001f\u0010\u0010R\u0018\u0010 \u001a\u00020\u0015X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019\u00a8\u0006#"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "moving", "", "getMoving", "()Z", "setMoving", "(Z)V", "onGround", "getOnGround", "setOnGround", "pitch", "", "getPitch", "()F", "setPitch", "(F)V", "rotating", "isRotating", "setRotating", "x", "", "getX", "()D", "setX", "(D)V", "y", "getY", "setY", "yaw", "getYaw", "setYaw", "z", "getZ", "setZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer.class */
public interface ICPacketPlayer extends IPacket {
    double getX();

    void setX(double d);

    double getY();

    void setY(double d);

    double getZ();

    void setZ(double d);

    float getYaw();

    void setYaw(float f);

    float getPitch();

    void setPitch(float f);

    boolean getOnGround();

    void setOnGround(boolean z);

    boolean getMoving();

    void setMoving(boolean z);

    @JvmName(name = "isRotating")
    boolean isRotating();

    void setRotating(boolean z);
}

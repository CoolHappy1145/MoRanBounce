package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacketByINetHandlerPlayServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0005H\u0007J\u0016\u0010\u0011\u001a\u00020\u000e2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0007R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR*\u0010\u0007\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0004j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/PacketUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "packets", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacketByINetHandlerPlayServer;", "Lkotlin/collections/ArrayList;", "packetsE", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "handleSendPacket", "", "packet", "sendPacket", "", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "sendPacketNoEvent", "sendPacketNoEventF", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/PacketUtils.class */
public final class PacketUtils extends MinecraftInstance {
    public static final PacketUtils INSTANCE = new PacketUtils();
    private static final ArrayList packets = new ArrayList();
    private static final ArrayList packetsE = new ArrayList();

    private PacketUtils() {
    }

    @JvmStatic
    public static final boolean handleSendPacket(@NotNull IPacketByINetHandlerPlayServer packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        if (packets.contains(packet)) {
            packets.remove(packet);
            return true;
        }
        return false;
    }

    @JvmStatic
    public static final void sendPacket(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(packet);
    }

    @JvmStatic
    public static final void sendPacketNoEventF(@NotNull Packet packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        packetsE.add(packet);
        Minecraft mc2 = MinecraftInstance.mc2;
        Intrinsics.checkExpressionValueIsNotNull(mc2, "mc2");
        NetHandlerPlayClient netHandlerPlayClientFunc_147114_u = mc2.func_147114_u();
        if (netHandlerPlayClientFunc_147114_u == null) {
            Intrinsics.throwNpe();
        }
        netHandlerPlayClientFunc_147114_u.func_147297_a(packet);
    }

    @JvmStatic
    public static final void sendPacketNoEvent(@NotNull IPacketByINetHandlerPlayServer packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        packets.add(packet);
        MinecraftInstance.f157mc.getNetHandler().addToSendQueue((IPacket) packet);
    }
}

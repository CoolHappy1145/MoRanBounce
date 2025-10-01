package net.ccbluex.liquidbounce.features.special;

import io.netty.buffer.Unpooled;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/special/AntiForge.class */
public class AntiForge extends MinecraftInstance implements Listenable {
    public static boolean enabled = false;
    public static boolean blockFML = false;
    public static boolean blockProxyPacket = false;
    public static boolean blockPayloadPackets = false;

    @EventTarget
    public void onPacket(PacketEvent packetEvent) {
        IPacket packet = packetEvent.getPacket();
        if (enabled && !f157mc.isIntegratedServerRunning()) {
            try {
                if (blockProxyPacket && packet.getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
                    packetEvent.cancelEvent();
                }
                if (blockPayloadPackets && classProvider.isCPacketCustomPayload(packet)) {
                    ICPacketCustomPayload iCPacketCustomPayloadAsCPacketCustomPayload = packet.asCPacketCustomPayload();
                    if (!iCPacketCustomPayloadAsCPacketCustomPayload.getChannelName().startsWith("MC|")) {
                        packetEvent.cancelEvent();
                    } else if (iCPacketCustomPayloadAsCPacketCustomPayload.getChannelName().equalsIgnoreCase("MC|Brand")) {
                        iCPacketCustomPayloadAsCPacketCustomPayload.setData(classProvider.createPacketBuffer(Unpooled.buffer()).writeString("vanilla"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

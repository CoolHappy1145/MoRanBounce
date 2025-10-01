package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import io.netty.channel.ChannelHandlerContext;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.injection.backend.PacketImplKt;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetworkManager.class */
public class MixinNetworkManager {
    @Inject(method = {"channelRead0"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void read(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent(PacketImplKt.wrap(packet));
        LiquidBounce.eventManager.callEvent(packetEvent);
        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void send(Packet packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent(PacketImplKt.wrap(packet));
        LiquidBounce.eventManager.callEvent(packetEvent);
        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

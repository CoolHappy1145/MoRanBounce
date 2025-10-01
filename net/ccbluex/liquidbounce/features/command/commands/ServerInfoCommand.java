package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016\u00a2\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ServerInfoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "ip", "", "port", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/ServerInfoCommand.class */
public final class ServerInfoCommand extends Command implements Listenable {

    /* renamed from: ip */
    private String f120ip;
    private int port;

    public ServerInfoCommand() {
        super("serverinfo", new String[0]);
        LiquidBounce.INSTANCE.getEventManager().registerListener(this);
        this.f120ip = "";
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (MinecraftInstance.f157mc.getCurrentServerData() == null) {
            chat("This command does not work in single player.");
            return;
        }
        IServerData currentServerData = MinecraftInstance.f157mc.getCurrentServerData();
        if (currentServerData != null) {
            chat("Server infos:");
            chat("\u00a77Name: \u00a78" + currentServerData.getServerName());
            chat("\u00a77IP: \u00a78" + this.f120ip + ':' + this.port);
            chat("\u00a77Players: \u00a78" + currentServerData.getPopulationInfo());
            chat("\u00a77MOTD: \u00a78" + currentServerData.getServerMOTD());
            chat("\u00a77ServerVersion: \u00a78" + currentServerData.getGameVersion());
            chat("\u00a77ProtocolVersion: \u00a78" + currentServerData.getVersion());
            chat("\u00a77Ping: \u00a78" + currentServerData.getPingToServer());
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketHandshake(packet)) {
            ICPacketHandshake iCPacketHandshakeAsCPacketHandshake = packet.asCPacketHandshake();
            this.f120ip = iCPacketHandshakeAsCPacketHandshake.getIp();
            this.port = iCPacketHandshakeAsCPacketHandshake.getPort();
        }
    }
}

package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.p005ui.client.GuiMainMenu;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/ServerUtils.class */
public final class ServerUtils extends MinecraftInstance {
    public static IServerData serverData;

    public static void connectToLastServer() {
        if (serverData == null) {
            return;
        }
        f157mc.displayGuiScreen(classProvider.createGuiConnecting(classProvider.createGuiMultiplayer(classProvider.wrapGuiScreen(new GuiMainMenu())), f157mc, serverData));
    }

    public static String getRemoteIp() {
        IServerData currentServerData;
        String serverIP = "Singleplayer";
        if (f157mc.getTheWorld().isRemote() && (currentServerData = f157mc.getCurrentServerData()) != null) {
            serverIP = currentServerData.getServerIP();
        }
        return serverIP;
    }
}

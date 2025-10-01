package net.ccbluex.liquidbounce.utils;

import com.google.gson.JsonObject;
import java.awt.Color;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import kotlin.Unit;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.network.login.server.ISPacketEncryptionRequest;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/ClientUtils.class */
public final class ClientUtils extends MinecraftInstance {
    private static final Logger logger = LogManager.getLogger("LiquidBounce");

    public static Logger getLogger() {
        return logger;
    }

    public static void disableFastRender() {
        LiquidBounce.wrapper.getFunctions().disableFastRender();
    }

    public static void sendEncryption(INetworkManager iNetworkManager, SecretKey secretKey, PublicKey publicKey, ISPacketEncryptionRequest iSPacketEncryptionRequest) {
        iNetworkManager.sendPacket(classProvider.createCPacketEncryptionResponse(secretKey, publicKey, iSPacketEncryptionRequest.getVerifyToken()), () -> {
            return lambda$sendEncryption$0(r2, r3);
        });
    }

    private static Unit lambda$sendEncryption$0(INetworkManager iNetworkManager, SecretKey secretKey) {
        iNetworkManager.enableEncryption(secretKey);
        return null;
    }

    public static WVec3 getVectorForRotation(float f, float f2) {
        float fCos = (float) Math.cos(((-f2) * 0.017453292f) - 3.1415927f);
        float fSin = (float) Math.sin(((-f2) * 0.017453292f) - 3.1415927f);
        float fCos2 = (float) Math.cos((-f) * 0.017453292f);
        return new WVec3(fSin * fCos2, (float) Math.sin((-f) * 0.017453292f), fCos * fCos2);
    }

    public static int reAlpha(int i, float f) {
        Color color = new Color(i);
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), f).getRGB();
    }

    public static void displayChatMessage(String str) {
        if (f157mc.getThePlayer() == null) {
            getLogger().info("(MCChat)" + str);
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", str);
        f157mc.getThePlayer().addChatMessage(LiquidBounce.wrapper.getFunctions().jsonToComponent(jsonObject.toString()));
    }
}

package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.p005ui.client.GuiBackground;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.value.Value;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/ValuesConfig.class */
public class ValuesConfig extends FileConfig {
    public ValuesConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        JsonObject jsonObject = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (jsonObject instanceof JsonNull) {
            return;
        }
        for (Map.Entry entry : jsonObject.entrySet()) {
            if (((String) entry.getKey()).equalsIgnoreCase("CommandPrefix")) {
                LiquidBounce.commandManager.setPrefix(((JsonElement) entry.getValue()).getAsCharacter());
            } else if (((String) entry.getKey()).equalsIgnoreCase("targets")) {
                JsonObject jsonObject2 = (JsonObject) entry.getValue();
                if (jsonObject2.has("TargetPlayer")) {
                    EntityUtils.targetPlayer = jsonObject2.get("TargetPlayer").getAsBoolean();
                }
                if (jsonObject2.has("TargetMobs")) {
                    EntityUtils.targetMobs = jsonObject2.get("TargetMobs").getAsBoolean();
                }
                if (jsonObject2.has("TargetAnimals")) {
                    EntityUtils.targetAnimals = jsonObject2.get("TargetAnimals").getAsBoolean();
                }
                if (jsonObject2.has("TargetInvisible")) {
                    EntityUtils.targetInvisible = jsonObject2.get("TargetInvisible").getAsBoolean();
                }
                if (jsonObject2.has("TargetDead")) {
                    EntityUtils.targetDead = jsonObject2.get("TargetDead").getAsBoolean();
                }
            } else if (((String) entry.getKey()).equalsIgnoreCase("features")) {
                JsonObject jsonObject3 = (JsonObject) entry.getValue();
                if (jsonObject3.has("AntiForge")) {
                    AntiForge.enabled = jsonObject3.get("AntiForge").getAsBoolean();
                }
                if (jsonObject3.has("AntiForgeFML")) {
                    AntiForge.blockFML = jsonObject3.get("AntiForgeFML").getAsBoolean();
                }
                if (jsonObject3.has("AntiForgeProxy")) {
                    AntiForge.blockProxyPacket = jsonObject3.get("AntiForgeProxy").getAsBoolean();
                }
                if (jsonObject3.has("AntiForgePayloads")) {
                    AntiForge.blockPayloadPackets = jsonObject3.get("AntiForgePayloads").getAsBoolean();
                }
                if (jsonObject3.has("BungeeSpoof")) {
                    BungeeCordSpoof.enabled = jsonObject3.get("BungeeSpoof").getAsBoolean();
                }
                if (jsonObject3.has("AutoReconnectDelay")) {
                    AutoReconnect.INSTANCE.setDelay(jsonObject3.get("AutoReconnectDelay").getAsInt());
                }
            } else if (((String) entry.getKey()).equalsIgnoreCase("thealtening")) {
                JsonObject jsonObject4 = (JsonObject) entry.getValue();
                if (jsonObject4.has("API-Key")) {
                    GuiTheAltening.Companion.setApiKey(jsonObject4.get("API-Key").getAsString());
                }
            } else if (((String) entry.getKey()).equalsIgnoreCase("liquidchat")) {
                JsonObject jsonObject5 = (JsonObject) entry.getValue();
                if (jsonObject5.has("token")) {
                    LiquidChat.Companion.setJwtToken(jsonObject5.get("token").getAsString());
                }
            } else if (((String) entry.getKey()).equalsIgnoreCase("DonatorCape")) {
                JsonObject jsonObject6 = (JsonObject) entry.getValue();
                if (jsonObject6.has("TransferCode")) {
                    GuiDonatorCape.Companion.setTransferCode(jsonObject6.get("TransferCode").getAsString());
                }
                if (jsonObject6.has("CapeEnabled")) {
                    GuiDonatorCape.Companion.setCapeEnabled(jsonObject6.get("CapeEnabled").getAsBoolean());
                }
            } else if (((String) entry.getKey()).equalsIgnoreCase("Background")) {
                JsonObject jsonObject7 = (JsonObject) entry.getValue();
                if (jsonObject7.has("Enabled")) {
                    GuiBackground.Companion.setEnabled(jsonObject7.get("Enabled").getAsBoolean());
                }
                if (jsonObject7.has("Particles")) {
                    GuiBackground.Companion.setParticles(jsonObject7.get("Particles").getAsBoolean());
                }
            } else {
                Module module = LiquidBounce.moduleManager.getModule((String) entry.getKey());
                if (module != null) {
                    JsonObject jsonObject8 = (JsonObject) entry.getValue();
                    for (Value value : module.getValues()) {
                        JsonElement jsonElement = jsonObject8.get(value.getName());
                        if (jsonElement != null) {
                            value.fromJson(jsonElement);
                        }
                    }
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CommandPrefix", Character.valueOf(LiquidBounce.commandManager.getPrefix()));
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("TargetPlayer", Boolean.valueOf(EntityUtils.targetPlayer));
        jsonObject2.addProperty("TargetMobs", Boolean.valueOf(EntityUtils.targetMobs));
        jsonObject2.addProperty("TargetAnimals", Boolean.valueOf(EntityUtils.targetAnimals));
        jsonObject2.addProperty("TargetInvisible", Boolean.valueOf(EntityUtils.targetInvisible));
        jsonObject2.addProperty("TargetDead", Boolean.valueOf(EntityUtils.targetDead));
        jsonObject.add("targets", jsonObject2);
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty("AntiForge", Boolean.valueOf(AntiForge.enabled));
        jsonObject3.addProperty("AntiForgeFML", Boolean.valueOf(AntiForge.blockFML));
        jsonObject3.addProperty("AntiForgeProxy", Boolean.valueOf(AntiForge.blockProxyPacket));
        jsonObject3.addProperty("AntiForgePayloads", Boolean.valueOf(AntiForge.blockPayloadPackets));
        jsonObject3.addProperty("BungeeSpoof", Boolean.valueOf(BungeeCordSpoof.enabled));
        jsonObject3.addProperty("AutoReconnectDelay", Integer.valueOf(AutoReconnect.INSTANCE.getDelay()));
        jsonObject.add("features", jsonObject3);
        JsonObject jsonObject4 = new JsonObject();
        jsonObject4.addProperty("API-Key", GuiTheAltening.Companion.getApiKey());
        jsonObject.add("thealtening", jsonObject4);
        JsonObject jsonObject5 = new JsonObject();
        jsonObject5.addProperty("token", LiquidChat.Companion.getJwtToken());
        jsonObject.add("liquidchat", jsonObject5);
        JsonObject jsonObject6 = new JsonObject();
        jsonObject6.addProperty("TransferCode", GuiDonatorCape.Companion.getTransferCode());
        jsonObject6.addProperty("CapeEnabled", Boolean.valueOf(GuiDonatorCape.Companion.getCapeEnabled()));
        jsonObject.add("DonatorCape", jsonObject6);
        JsonObject jsonObject7 = new JsonObject();
        jsonObject7.addProperty("Enabled", Boolean.valueOf(GuiBackground.Companion.getEnabled()));
        jsonObject7.addProperty("Particles", Boolean.valueOf(GuiBackground.Companion.getParticles()));
        jsonObject.add("Background", jsonObject7);
        LiquidBounce.moduleManager.getModules().stream().filter(ValuesConfig::lambda$saveConfig$0).forEach((v1) -> {
            lambda$saveConfig$2(r1, v1);
        });
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }

    private static boolean lambda$saveConfig$0(Module module) {
        return !module.getValues().isEmpty();
    }

    private static void lambda$saveConfig$2(JsonObject jsonObject, Module module) {
        JsonObject jsonObject2 = new JsonObject();
        module.getValues().forEach((v1) -> {
            lambda$null$1(r1, v1);
        });
        jsonObject.add(module.getName(), jsonObject2);
    }

    private static void lambda$null$1(JsonObject jsonObject, Value value) {
        jsonObject.add(value.getName(), value.mo1758toJson());
    }
}

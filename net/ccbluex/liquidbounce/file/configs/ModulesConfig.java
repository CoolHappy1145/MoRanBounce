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
import java.util.Iterator;
import java.util.Map;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/ModulesConfig.class */
public class ModulesConfig extends FileConfig {
    public ModulesConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (jsonElement instanceof JsonNull) {
            return;
        }
        for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
            Module module = LiquidBounce.moduleManager.getModule((String) entry.getKey());
            if (module != null) {
                JsonObject jsonObject = (JsonObject) entry.getValue();
                module.setState(jsonObject.get("State").getAsBoolean());
                module.setKeyBind(jsonObject.get("KeyBind").getAsInt());
                if (jsonObject.has("Array")) {
                    module.setArray(jsonObject.get("Array").getAsBoolean());
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        JsonObject jsonObject = new JsonObject();
        Iterator it = LiquidBounce.moduleManager.getModules().iterator();
        while (it.hasNext()) {
            Module module = (Module) it.next();
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("State", Boolean.valueOf(module.getState()));
            jsonObject2.addProperty("KeyBind", Integer.valueOf(module.getKeyBind()));
            jsonObject2.addProperty("Array", Boolean.valueOf(module.getArray()));
            jsonObject.add(module.getName(), jsonObject2);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }
}

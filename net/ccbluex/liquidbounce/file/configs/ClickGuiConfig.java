package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/ClickGuiConfig.class */
public class ClickGuiConfig extends FileConfig {
    public ClickGuiConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        JsonObject jsonObject = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (jsonObject instanceof JsonNull) {
            return;
        }
        JsonObject jsonObject2 = jsonObject;
        for (Panel panel : LiquidBounce.clickGui.panels) {
            if (jsonObject2.has(panel.getName())) {
                try {
                    JsonObject asJsonObject = jsonObject2.getAsJsonObject(panel.getName());
                    panel.setOpen(asJsonObject.get("open").getAsBoolean());
                    panel.setVisible(asJsonObject.get("visible").getAsBoolean());
                    panel.setX(asJsonObject.get("posX").getAsInt());
                    panel.setY(asJsonObject.get("posY").getAsInt());
                    for (Element element : panel.getElements()) {
                        if (element instanceof ModuleElement) {
                            ModuleElement moduleElement = (ModuleElement) element;
                            if (asJsonObject.has(moduleElement.getModule().getName())) {
                                try {
                                    moduleElement.setShowSettings(asJsonObject.getAsJsonObject(moduleElement.getModule().getName()).get("Settings").getAsBoolean());
                                } catch (Exception e) {
                                    ClientUtils.getLogger().error("Error while loading clickgui module element with the name '" + moduleElement.getModule().getName() + "' (Panel Name: " + panel.getName() + ").", e);
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    ClientUtils.getLogger().error("Error while loading clickgui panel with the name '" + panel.getName() + "'.", e2);
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        JsonObject jsonObject = new JsonObject();
        for (Panel panel : LiquidBounce.clickGui.panels) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("open", Boolean.valueOf(panel.getOpen()));
            jsonObject2.addProperty("visible", Boolean.valueOf(panel.isVisible()));
            jsonObject2.addProperty("posX", Integer.valueOf(panel.getX()));
            jsonObject2.addProperty("posY", Integer.valueOf(panel.getY()));
            for (Element element : panel.getElements()) {
                if (element instanceof ModuleElement) {
                    ModuleElement moduleElement = (ModuleElement) element;
                    JsonObject jsonObject3 = new JsonObject();
                    jsonObject3.addProperty("Settings", Boolean.valueOf(moduleElement.isShowSettings()));
                    jsonObject2.add(moduleElement.getModule().getName(), jsonObject3);
                }
            }
            jsonObject.add(panel.getName(), jsonObject2);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }
}

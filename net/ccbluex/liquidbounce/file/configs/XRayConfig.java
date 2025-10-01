package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/XRayConfig.class */
public class XRayConfig extends FileConfig {
    public XRayConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
            return;
        }
        JsonArray asJsonArray = new JsonParser().parse(new BufferedReader(new FileReader(getFile()))).getAsJsonArray();
        xRay.getXrayBlocks().clear();
        Iterator it = asJsonArray.iterator();
        while (it.hasNext()) {
            try {
                IBlock blockFromName = LiquidBounce.wrapper.getFunctions().getBlockFromName(((JsonElement) it.next()).getAsString());
                if (xRay.getXrayBlocks().contains(blockFromName)) {
                    ClientUtils.getLogger().error("[FileManager] Skipped xray block '" + blockFromName.getRegistryName() + "' because the block is already added.");
                } else {
                    xRay.getXrayBlocks().add(blockFromName);
                }
            } catch (Throwable th) {
                ClientUtils.getLogger().error("[FileManager] Failed to add block to xray.", th);
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
            return;
        }
        JsonArray jsonArray = new JsonArray();
        Iterator it = xRay.getXrayBlocks().iterator();
        while (it.hasNext()) {
            jsonArray.add(FileManager.PRETTY_GSON.toJsonTree(Integer.valueOf(LiquidBounce.wrapper.getFunctions().getIdFromBlock((IBlock) it.next()))));
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }
}

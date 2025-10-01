package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.p005ui.client.hud.Config;
import org.apache.commons.io.FileUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/HudConfig.class */
public class HudConfig extends FileConfig {
    public HudConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        LiquidBounce.hud.clearElements();
        LiquidBounce.hud = new Config(FileUtils.readFileToString(getFile())).toHUD();
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(new Config(LiquidBounce.hud).toJson());
        printWriter.close();
    }
}

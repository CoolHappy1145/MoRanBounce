package net.ccbluex.liquidbounce.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.file.configs.ClickGuiConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.file.configs.HudConfig;
import net.ccbluex.liquidbounce.file.configs.ModulesConfig;
import net.ccbluex.liquidbounce.file.configs.ShortcutsConfig;
import net.ccbluex.liquidbounce.file.configs.ValuesConfig;
import net.ccbluex.liquidbounce.file.configs.XRayConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/FileManager.class */
public class FileManager extends MinecraftInstance {
    public final File dir = new File(f157mc.getDataDir(), "LiquidSense-1.12");
    public final File fontsDir = new File(this.dir, "fonts");
    public final File settingsDir = new File(this.dir, "settings");
    public final FileConfig modulesConfig = new ModulesConfig(new File(this.dir, "modules.json"));
    public final FileConfig valuesConfig = new ValuesConfig(new File(this.dir, "values.json"));
    public final FileConfig clickGuiConfig = new ClickGuiConfig(new File(this.dir, "clickgui.json"));
    public final AccountsConfig accountsConfig = new AccountsConfig(new File(this.dir, "accounts.json"));
    public final FriendsConfig friendsConfig = new FriendsConfig(new File(this.dir, "friends.json"));
    public final FileConfig xrayConfig = new XRayConfig(new File(this.dir, "xray-blocks.json"));
    public final FileConfig hudConfig = new HudConfig(new File(this.dir, "hud.json"));
    public final FileConfig shortcutsConfig = new ShortcutsConfig(new File(this.dir, "shortcuts.json"));
    public final File backgroundFile = new File(this.dir, "userbackground.png");
    public boolean firstStart = false;
    public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    public FileManager() {
        setupFolder();
        loadBackground();
    }

    public void setupFolder() {
        if (!this.dir.exists()) {
            this.dir.mkdir();
            this.firstStart = true;
        }
        if (!this.fontsDir.exists()) {
            this.fontsDir.mkdir();
        }
        if (!this.settingsDir.exists()) {
            this.settingsDir.mkdir();
        }
    }

    public void loadAllConfigs() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.getType() == FileConfig.class) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    loadConfig((FileConfig) field.get(this));
                } catch (IllegalAccessException e) {
                    ClientUtils.getLogger().error("Failed to load config file of field " + field.getName() + ".", e);
                }
            }
        }
    }

    public void loadConfigs(FileConfig[] fileConfigArr) {
        for (FileConfig fileConfig : fileConfigArr) {
            loadConfig(fileConfig);
        }
    }

    public void loadConfig(FileConfig fileConfig) {
        if (!fileConfig.hasConfig()) {
            ClientUtils.getLogger().info("[FileManager] Skipped loading config: " + fileConfig.getFile().getName() + ".");
            saveConfig(fileConfig, true);
            return;
        }
        try {
            fileConfig.loadConfig();
            ClientUtils.getLogger().info("[FileManager] Loaded config: " + fileConfig.getFile().getName() + ".");
        } catch (Throwable th) {
            ClientUtils.getLogger().error("[FileManager] Failed to load config file: " + fileConfig.getFile().getName() + ".", th);
        }
    }

    public void saveAllConfigs() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.getType() == FileConfig.class) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    saveConfig((FileConfig) field.get(this));
                } catch (IllegalAccessException e) {
                    ClientUtils.getLogger().error("[FileManager] Failed to save config file of field " + field.getName() + ".", e);
                }
            }
        }
    }

    public void saveConfigs(FileConfig[] fileConfigArr) {
        for (FileConfig fileConfig : fileConfigArr) {
            saveConfig(fileConfig);
        }
    }

    public void saveConfig(FileConfig fileConfig) {
        saveConfig(fileConfig, false);
    }

    private void saveConfig(FileConfig fileConfig, boolean z) {
        if (!z && LiquidBounce.INSTANCE.isStarting()) {
            return;
        }
        try {
            if (!fileConfig.hasConfig()) {
                fileConfig.createConfig();
            }
            fileConfig.saveConfig();
            ClientUtils.getLogger().info("[FileManager] Saved config: " + fileConfig.getFile().getName() + ".");
        } catch (Throwable th) {
            ClientUtils.getLogger().error("[FileManager] Failed to save config file: " + fileConfig.getFile().getName() + ".", th);
        }
    }

    public void loadBackground() {
        if (this.backgroundFile.exists()) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(this.backgroundFile));
                if (bufferedImage == null) {
                    return;
                }
                LiquidBounce.INSTANCE.setBackground(classProvider.createResourceLocation(LiquidBounce.CLIENT_NAME.toLowerCase() + "/background.png"));
                f157mc.getTextureManager().loadTexture(LiquidBounce.INSTANCE.getBackground(), classProvider.createDynamicTexture(bufferedImage));
                ClientUtils.getLogger().info("[FileManager] Loaded background.");
            } catch (Exception e) {
                ClientUtils.getLogger().error("[FileManager] Failed to load background.", e);
            }
        }
    }
}

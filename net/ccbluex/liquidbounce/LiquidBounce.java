package net.ccbluex.liquidbounce;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.Wrapper;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.cape.CapeAPI;
import net.ccbluex.liquidbounce.discord.ClientRichPresence;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventManager;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.features.special.CombatManager;
import net.ccbluex.liquidbounce.features.special.DonatorCape;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p005ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import net.ccbluex.liquidbounce.tabs.BlocksTab;
import net.ccbluex.liquidbounce.tabs.ExploitsTab;
import net.ccbluex.liquidbounce.tabs.HeadsTab;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.CombatHelper;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010]\u001a\u00020^J\u0006\u0010_\u001a\u00020^R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010(\u001a\u00020)X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010.\u001a\u00020/X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b:\u0010<\"\u0004\b=\u0010>R\u001a\u0010?\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001a\u0010D\u001a\u00020EX\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR$\u0010J\u001a\u00020K8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\ufffd\ufffd\u0012\u0004\bL\u0010\u0002\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u001a\u0010Q\u001a\u00020RX\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\u001a\u0010W\u001a\u00020XX\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\\u00a8\u0006`"}, m27d2 = {"Lnet/ccbluex/liquidbounce/LiquidBounce;", "", "()V", "CLIENT_CLOUD", "", "CLIENT_CREATOR", "CLIENT_NAME", "CLIENT_VERSION", "", "MINECRAFT_VERSION", "background", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getBackground", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "setBackground", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;)V", "clickGui", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "getClickGui", "()Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "setClickGui", "(Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;)V", "clientRichPresence", "Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "getClientRichPresence", "()Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "setClientRichPresence", "(Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;)V", "combatManager", "Lnet/ccbluex/liquidbounce/features/special/CombatManager;", "getCombatManager", "()Lnet/ccbluex/liquidbounce/features/special/CombatManager;", "setCombatManager", "(Lnet/ccbluex/liquidbounce/features/special/CombatManager;)V", "commandManager", "Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "getCommandManager", "()Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "setCommandManager", "(Lnet/ccbluex/liquidbounce/features/command/CommandManager;)V", "eventManager", "Lnet/ccbluex/liquidbounce/event/EventManager;", "getEventManager", "()Lnet/ccbluex/liquidbounce/event/EventManager;", "setEventManager", "(Lnet/ccbluex/liquidbounce/event/EventManager;)V", "fileManager", "Lnet/ccbluex/liquidbounce/file/FileManager;", "getFileManager", "()Lnet/ccbluex/liquidbounce/file/FileManager;", "setFileManager", "(Lnet/ccbluex/liquidbounce/file/FileManager;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "getHud", "()Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "setHud", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "isStarting", "", "()Z", "setStarting", "(Z)V", "latestVersion", "getLatestVersion", "()I", "setLatestVersion", "(I)V", "moduleManager", "Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "getModuleManager", "()Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "setModuleManager", "(Lnet/ccbluex/liquidbounce/features/module/ModuleManager;)V", "playTimeStart", "", "playTimeStart$annotations", "getPlayTimeStart", "()J", "setPlayTimeStart", "(J)V", "scriptManager", "Lnet/ccbluex/liquidbounce/script/ScriptManager;", "getScriptManager", "()Lnet/ccbluex/liquidbounce/script/ScriptManager;", "setScriptManager", "(Lnet/ccbluex/liquidbounce/script/ScriptManager;)V", "wrapper", "Lnet/ccbluex/liquidbounce/api/Wrapper;", "getWrapper", "()Lnet/ccbluex/liquidbounce/api/Wrapper;", "setWrapper", "(Lnet/ccbluex/liquidbounce/api/Wrapper;)V", "startClient", "", "stopClient", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/LiquidBounce.class */
public final class LiquidBounce {
    private static long playTimeStart;

    @NotNull
    public static final String CLIENT_NAME = "LiquidSense";
    public static final int CLIENT_VERSION = 1;

    @NotNull
    public static final String CLIENT_CREATOR = "CCBlueX";

    @NotNull
    public static final String MINECRAFT_VERSION = "1.12.2";

    @NotNull
    public static final String CLIENT_CLOUD = "https://cloud.liquidbounce.net/LiquidBounce";
    private static boolean isStarting;

    @NotNull
    public static ModuleManager moduleManager;

    @NotNull
    public static CommandManager commandManager;

    @NotNull
    public static EventManager eventManager;

    @NotNull
    public static CombatManager combatManager;

    @NotNull
    public static FileManager fileManager;

    @NotNull
    public static ScriptManager scriptManager;

    @NotNull
    public static HUD hud;

    @NotNull
    public static ClickGui clickGui;

    @NotNull
    public static ClientRichPresence clientRichPresence;
    private static int latestVersion;

    @Nullable
    private static IResourceLocation background;

    @NotNull
    public static Wrapper wrapper;
    public static final LiquidBounce INSTANCE = new LiquidBounce();

    private LiquidBounce() {
    }

    public static final long getPlayTimeStart() {
        return playTimeStart;
    }

    public static final void setPlayTimeStart(long j) {
        playTimeStart = j;
    }

    public final boolean isStarting() {
        return isStarting;
    }

    public final void setStarting(boolean z) {
        isStarting = z;
    }

    @NotNull
    public final ModuleManager getModuleManager() {
        ModuleManager moduleManager2 = moduleManager;
        if (moduleManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moduleManager");
        }
        return moduleManager2;
    }

    public final void setModuleManager(@NotNull ModuleManager moduleManager2) {
        Intrinsics.checkParameterIsNotNull(moduleManager2, "<set-?>");
        moduleManager = moduleManager2;
    }

    @NotNull
    public final CommandManager getCommandManager() {
        CommandManager commandManager2 = commandManager;
        if (commandManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        }
        return commandManager2;
    }

    public final void setCommandManager(@NotNull CommandManager commandManager2) {
        Intrinsics.checkParameterIsNotNull(commandManager2, "<set-?>");
        commandManager = commandManager2;
    }

    @NotNull
    public final EventManager getEventManager() {
        EventManager eventManager2 = eventManager;
        if (eventManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        return eventManager2;
    }

    public final void setEventManager(@NotNull EventManager eventManager2) {
        Intrinsics.checkParameterIsNotNull(eventManager2, "<set-?>");
        eventManager = eventManager2;
    }

    @NotNull
    public final CombatManager getCombatManager() {
        CombatManager combatManager2 = combatManager;
        if (combatManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("combatManager");
        }
        return combatManager2;
    }

    public final void setCombatManager(@NotNull CombatManager combatManager2) {
        Intrinsics.checkParameterIsNotNull(combatManager2, "<set-?>");
        combatManager = combatManager2;
    }

    @NotNull
    public final FileManager getFileManager() {
        FileManager fileManager2 = fileManager;
        if (fileManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        return fileManager2;
    }

    public final void setFileManager(@NotNull FileManager fileManager2) {
        Intrinsics.checkParameterIsNotNull(fileManager2, "<set-?>");
        fileManager = fileManager2;
    }

    @NotNull
    public final ScriptManager getScriptManager() {
        ScriptManager scriptManager2 = scriptManager;
        if (scriptManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
        }
        return scriptManager2;
    }

    public final void setScriptManager(@NotNull ScriptManager scriptManager2) {
        Intrinsics.checkParameterIsNotNull(scriptManager2, "<set-?>");
        scriptManager = scriptManager2;
    }

    @NotNull
    public final HUD getHud() {
        HUD hud2 = hud;
        if (hud2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
        }
        return hud2;
    }

    public final void setHud(@NotNull HUD hud2) {
        Intrinsics.checkParameterIsNotNull(hud2, "<set-?>");
        hud = hud2;
    }

    @NotNull
    public final ClickGui getClickGui() {
        ClickGui clickGui2 = clickGui;
        if (clickGui2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clickGui");
        }
        return clickGui2;
    }

    public final void setClickGui(@NotNull ClickGui clickGui2) {
        Intrinsics.checkParameterIsNotNull(clickGui2, "<set-?>");
        clickGui = clickGui2;
    }

    @NotNull
    public final ClientRichPresence getClientRichPresence() {
        ClientRichPresence clientRichPresence2 = clientRichPresence;
        if (clientRichPresence2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientRichPresence");
        }
        return clientRichPresence2;
    }

    public final void setClientRichPresence(@NotNull ClientRichPresence clientRichPresence2) {
        Intrinsics.checkParameterIsNotNull(clientRichPresence2, "<set-?>");
        clientRichPresence = clientRichPresence2;
    }

    public final int getLatestVersion() {
        return latestVersion;
    }

    public final void setLatestVersion(int i) {
        latestVersion = i;
    }

    @Nullable
    public final IResourceLocation getBackground() {
        return background;
    }

    public final void setBackground(@Nullable IResourceLocation iResourceLocation) {
        background = iResourceLocation;
    }

    @NotNull
    public final Wrapper getWrapper() {
        Wrapper wrapper2 = wrapper;
        if (wrapper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wrapper");
        }
        return wrapper2;
    }

    public final void setWrapper(@NotNull Wrapper wrapper2) {
        Intrinsics.checkParameterIsNotNull(wrapper2, "<set-?>");
        wrapper = wrapper2;
    }

    public final void startClient() {
        isStarting = true;
        ClientUtils.getLogger().info("Starting LiquidSense b1, by CCBlueX");
        fileManager = new FileManager();
        eventManager = new EventManager();
        EventManager eventManager2 = eventManager;
        if (eventManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager2.registerListener(new RotationUtils());
        EventManager eventManager3 = eventManager;
        if (eventManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager3.registerListener(new AntiForge());
        EventManager eventManager4 = eventManager;
        if (eventManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager4.registerListener(new BungeeCordSpoof());
        EventManager eventManager5 = eventManager;
        if (eventManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager5.registerListener(new DonatorCape());
        EventManager eventManager6 = eventManager;
        if (eventManager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager6.registerListener(new InventoryUtils());
        EventManager eventManager7 = eventManager;
        if (eventManager7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager7.registerListener(new CombatHelper());
        commandManager = new CommandManager();
        Fonts.loadFonts();
        moduleManager = new ModuleManager();
        ModuleManager moduleManager2 = moduleManager;
        if (moduleManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moduleManager");
        }
        moduleManager2.registerModules();
        try {
            Remapper.INSTANCE.loadSrg();
            scriptManager = new ScriptManager();
            ScriptManager scriptManager2 = scriptManager;
            if (scriptManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
            }
            scriptManager2.loadScripts();
            ScriptManager scriptManager3 = scriptManager;
            if (scriptManager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
            }
            scriptManager3.enableScripts();
        } catch (Throwable th) {
            ClientUtils.getLogger().error("Failed to load scripts.", th);
        }
        CommandManager commandManager2 = commandManager;
        if (commandManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        }
        commandManager2.registerCommands();
        FileManager fileManager2 = fileManager;
        if (fileManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        FileConfig[] fileConfigArr = new FileConfig[6];
        FileManager fileManager3 = fileManager;
        if (fileManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileConfigArr[0] = fileManager3.modulesConfig;
        FileManager fileManager4 = fileManager;
        if (fileManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileConfigArr[1] = fileManager4.valuesConfig;
        FileManager fileManager5 = fileManager;
        if (fileManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileConfigArr[2] = fileManager5.accountsConfig;
        FileManager fileManager6 = fileManager;
        if (fileManager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileConfigArr[3] = fileManager6.friendsConfig;
        FileManager fileManager7 = fileManager;
        if (fileManager7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileConfigArr[4] = fileManager7.xrayConfig;
        FileManager fileManager8 = fileManager;
        if (fileManager8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileConfigArr[5] = fileManager8.shortcutsConfig;
        fileManager2.loadConfigs(fileConfigArr);
        clickGui = new ClickGui();
        FileManager fileManager9 = fileManager;
        if (fileManager9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        FileManager fileManager10 = fileManager;
        if (fileManager10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileManager9.loadConfig(fileManager10.clickGuiConfig);
        if (ClassUtils.INSTANCE.hasForge()) {
            new BlocksTab();
            new ExploitsTab();
            new HeadsTab();
        }
        try {
            CapeAPI.INSTANCE.registerCapeService();
        } catch (Throwable th2) {
            ClientUtils.getLogger().error("Failed to register cape service", th2);
        }
        hud = HUD.Companion.createDefault();
        FileManager fileManager11 = fileManager;
        if (fileManager11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        FileManager fileManager12 = fileManager;
        if (fileManager12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileManager11.loadConfig(fileManager12.hudConfig);
        ClientUtils.disableFastRender();
        try {
            JsonObject jsonObject = new JsonParser().parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/versions.json"));
            if ((jsonObject instanceof JsonObject) && jsonObject.has("1.12.2")) {
                JsonElement jsonElement = jsonObject.get("1.12.2");
                Intrinsics.checkExpressionValueIsNotNull(jsonElement, "jsonObj[MINECRAFT_VERSION]");
                latestVersion = jsonElement.getAsInt();
            }
        } catch (Throwable th3) {
            ClientUtils.getLogger().error("Failed to check for updates.", th3);
        }
        GuiAltManager.loadGenerators();
        isStarting = false;
    }

    public final void stopClient() {
        EventManager eventManager2 = eventManager;
        if (eventManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }
        eventManager2.callEvent(new ClientShutdownEvent());
        FileManager fileManager2 = fileManager;
        if (fileManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }
        fileManager2.saveAllConfigs();
    }
}

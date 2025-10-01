package net.ccbluex.liquidbounce.features.command.commands;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ReloadCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/ReloadCommand.class */
public final class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", new String[]{"configreload"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        chat("Reloading...");
        chat("\u00a7c\u00a7lReloading commands...");
        LiquidBounce.INSTANCE.setCommandManager(new CommandManager());
        LiquidBounce.INSTANCE.getCommandManager().registerCommands();
        LiquidBounce.INSTANCE.setStarting(true);
        LiquidBounce.INSTANCE.getScriptManager().disableScripts();
        LiquidBounce.INSTANCE.getScriptManager().unloadScripts();
        Iterator it = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
        while (it.hasNext()) {
            Module module = (Module) it.next();
            ModuleManager moduleManager = LiquidBounce.INSTANCE.getModuleManager();
            Intrinsics.checkExpressionValueIsNotNull(module, "module");
            moduleManager.generateCommand$LiquidSense(module);
        }
        chat("\u00a7c\u00a7lReloading scripts...");
        LiquidBounce.INSTANCE.getScriptManager().loadScripts();
        LiquidBounce.INSTANCE.getScriptManager().enableScripts();
        chat("\u00a7c\u00a7lReloading fonts...");
        Fonts.loadFonts();
        chat("\u00a7c\u00a7lReloading modules...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        LiquidBounce.INSTANCE.setStarting(false);
        chat("\u00a7c\u00a7lReloading values...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
        chat("\u00a7c\u00a7lReloading accounts...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().accountsConfig);
        chat("\u00a7c\u00a7lReloading friends...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().friendsConfig);
        chat("\u00a7c\u00a7lReloading xray...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
        chat("\u00a7c\u00a7lReloading HUD...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
        chat("\u00a7c\u00a7lReloading ClickGUI...");
        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
        chat("Reloaded.");
    }
}

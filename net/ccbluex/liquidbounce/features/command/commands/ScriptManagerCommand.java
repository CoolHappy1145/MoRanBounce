package net.ccbluex.liquidbounce.features.command.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ScriptManagerCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/ScriptManagerCommand.class */
public final class ScriptManagerCommand extends Command {
    public ScriptManagerCommand() {
        super("scriptmanager", new String[]{"scripts"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "import", true)) {
                try {
                    File fileOpenFileChooser = MiscUtils.openFileChooser();
                    if (fileOpenFileChooser != null) {
                        String fileName = fileOpenFileChooser.getName();
                        Intrinsics.checkExpressionValueIsNotNull(fileName, "fileName");
                        if (StringsKt.endsWith$default(fileName, ".js", false, 2, (Object) null)) {
                            LiquidBounce.INSTANCE.getScriptManager().importScript(fileOpenFileChooser);
                            LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                            LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                            chat("Successfully imported script.");
                            return;
                        }
                        if (StringsKt.endsWith$default(fileName, ".zip", false, 2, (Object) null)) {
                            ZipFile zipFile = new ZipFile(fileOpenFileChooser);
                            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                            ArrayList arrayList = new ArrayList();
                            while (enumerationEntries.hasMoreElements()) {
                                ZipEntry entry = enumerationEntries.nextElement();
                                Intrinsics.checkExpressionValueIsNotNull(entry, "entry");
                                String entryName = entry.getName();
                                File file = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), entryName);
                                if (entry.isDirectory()) {
                                    file.mkdir();
                                } else {
                                    InputStream inputStream = zipFile.getInputStream(entry);
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    IOUtils.copy(inputStream, fileOutputStream);
                                    fileOutputStream.close();
                                    inputStream.close();
                                    Intrinsics.checkExpressionValueIsNotNull(entryName, "entryName");
                                    if (!StringsKt.contains$default((CharSequence) entryName, (CharSequence) "/", false, 2, (Object) null)) {
                                        arrayList.add(file);
                                    }
                                }
                            }
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                LiquidBounce.INSTANCE.getScriptManager().loadScript((File) it.next());
                            }
                            LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                            LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                            LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                            chat("Successfully imported script.");
                            return;
                        }
                        chat("The file extension has to be .js or .zip");
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    ClientUtils.getLogger().error("Something went wrong while importing a script.", th);
                    chat(th.getClass().getName() + ": " + th.getMessage());
                    return;
                }
            }
            if (StringsKt.equals(args[1], "delete", true)) {
                try {
                    if (args.length <= 2) {
                        chatSyntax("scriptmanager delete <index>");
                        return;
                    }
                    int i = Integer.parseInt(args[2]);
                    List scripts = LiquidBounce.INSTANCE.getScriptManager().getScripts();
                    if (i >= scripts.size()) {
                        chat("Index " + i + " is too high.");
                        return;
                    }
                    LiquidBounce.INSTANCE.getScriptManager().deleteScript((Script) scripts.get(i));
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                    chat("Successfully deleted script.");
                    return;
                } catch (NumberFormatException unused) {
                    chatSyntaxError();
                    return;
                } catch (Throwable th2) {
                    ClientUtils.getLogger().error("Something went wrong while deleting a script.", th2);
                    chat(th2.getClass().getName() + ": " + th2.getMessage());
                    return;
                }
            }
            if (StringsKt.equals(args[1], "reload", true)) {
                try {
                    LiquidBounce.INSTANCE.setCommandManager(new CommandManager());
                    LiquidBounce.INSTANCE.getCommandManager().registerCommands();
                    LiquidBounce.INSTANCE.setStarting(true);
                    LiquidBounce.INSTANCE.getScriptManager().disableScripts();
                    LiquidBounce.INSTANCE.getScriptManager().unloadScripts();
                    Iterator it2 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
                    while (it2.hasNext()) {
                        Module module = (Module) it2.next();
                        ModuleManager moduleManager = LiquidBounce.INSTANCE.getModuleManager();
                        Intrinsics.checkExpressionValueIsNotNull(module, "module");
                        moduleManager.generateCommand$LiquidSense(module);
                    }
                    LiquidBounce.INSTANCE.getScriptManager().loadScripts();
                    LiquidBounce.INSTANCE.getScriptManager().enableScripts();
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
                    LiquidBounce.INSTANCE.setStarting(false);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    chat("Successfully reloaded all scripts.");
                    return;
                } catch (Throwable th3) {
                    ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", th3);
                    chat(th3.getClass().getName() + ": " + th3.getMessage());
                    return;
                }
            }
            if (StringsKt.equals(args[1], "folder", true)) {
                try {
                    Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
                    chat("Successfully opened scripts folder.");
                    return;
                } catch (Throwable th4) {
                    ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", th4);
                    chat(th4.getClass().getName() + ": " + th4.getMessage());
                    return;
                }
            }
            return;
        }
        ScriptManager scriptManager = LiquidBounce.INSTANCE.getScriptManager();
        if (!scriptManager.getScripts().isEmpty()) {
            chat("\u00a7c\u00a7lScripts");
            int i2 = 0;
            for (Object obj : scriptManager.getScripts()) {
                int i3 = i2;
                i2++;
                if (i3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Script script = (Script) obj;
                chat(i3 + ": \u00a7a\u00a7l" + script.getScriptName() + " \u00a7a\u00a7lv" + script.getScriptVersion() + " \u00a73by \u00a7a\u00a7l" + ArraysKt.joinToString$default(script.getScriptAuthors(), ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
            }
        }
        chatSyntax("scriptmanager <import/delete/reload/folder>");
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"delete", "import", "folder", "reload"});
                ArrayList arrayList = new ArrayList();
                for (Object obj : listListOf) {
                    if (StringsKt.startsWith((String) obj, args[0], true)) {
                        arrayList.add(obj);
                    }
                }
                break;
        }
        return CollectionsKt.emptyList();
    }
}

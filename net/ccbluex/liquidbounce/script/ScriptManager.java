package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007J\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000bJ\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000bJ\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u000fJ\u0006\u0010\u0019\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\ufffd\ufffdR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/ScriptManager;", "", "()V", "scriptFileExtension", "", "scripts", "", "Lnet/ccbluex/liquidbounce/script/Script;", "getScripts", "()Ljava/util/List;", "scriptsFolder", "Ljava/io/File;", "getScriptsFolder", "()Ljava/io/File;", "deleteScript", "", "script", "disableScripts", "enableScripts", "importScript", "file", "loadScript", "scriptFile", "loadScripts", "reloadScripts", "unloadScripts", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/ScriptManager.class */
public final class ScriptManager {

    @NotNull
    private final List scripts = new ArrayList();

    @NotNull
    private final File scriptsFolder = new File(LiquidBounce.INSTANCE.getFileManager().dir, "scripts");
    private final String scriptFileExtension = ".js";

    @NotNull
    public final List getScripts() {
        return this.scripts;
    }

    @NotNull
    public final File getScriptsFolder() {
        return this.scriptsFolder;
    }

    public final void loadScripts() {
        if (!this.scriptsFolder.exists()) {
            this.scriptsFolder.mkdir();
        }
        File[] fileArrListFiles = this.scriptsFolder.listFiles(new FileFilter(this) { // from class: net.ccbluex.liquidbounce.script.ScriptManager.loadScripts.1
            final ScriptManager this$0;

            {
                this.this$0 = this;
            }

            @Override // java.io.FileFilter
            public final boolean accept(File it) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                String name = it.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "it.name");
                return StringsKt.endsWith$default(name, this.this$0.scriptFileExtension, false, 2, (Object) null);
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(fileArrListFiles, "scriptsFolder.listFiles(\u2026h(scriptFileExtension) })");
        for (File it : fileArrListFiles) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            loadScript(it);
        }
    }

    public final void unloadScripts() {
        this.scripts.clear();
    }

    public final void loadScript(@NotNull File scriptFile) {
        Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");
        try {
            this.scripts.add(new Script(scriptFile));
            ClientUtils.getLogger().info("[ScriptAPI] Successfully loaded script '" + scriptFile.getName() + "'.");
        } catch (Throwable th) {
            ClientUtils.getLogger().error("[ScriptAPI] Failed to load script '" + scriptFile.getName() + "'.", th);
        }
    }

    public final void enableScripts() {
        Iterator it = this.scripts.iterator();
        while (it.hasNext()) {
            ((Script) it.next()).onEnable();
        }
    }

    public final void disableScripts() {
        Iterator it = this.scripts.iterator();
        while (it.hasNext()) {
            ((Script) it.next()).onDisable();
        }
    }

    public final void importScript(@NotNull File file) {
        Intrinsics.checkParameterIsNotNull(file, "file");
        File file2 = new File(this.scriptsFolder, file.getName());
        FilesKt.copyTo$default(file, file2, false, 0, 6, null);
        loadScript(file2);
        ClientUtils.getLogger().info("[ScriptAPI]  Successfully imported script '" + file2.getName() + "'.");
    }

    public final void deleteScript(@NotNull Script script) {
        Intrinsics.checkParameterIsNotNull(script, "script");
        script.onDisable();
        this.scripts.remove(script);
        script.getScriptFile().delete();
        ClientUtils.getLogger().info("[ScriptAPI]  Successfully deleted script '" + script.getScriptFile().getName() + "'.");
    }

    public final void reloadScripts() {
        disableScripts();
        unloadScripts();
        loadScripts();
        enableScripts();
        ClientUtils.getLogger().info("[ScriptAPI]  Successfully reloaded scripts.");
    }
}

package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.script.ScriptEngine;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.script.api.ScriptCommand;
import net.ccbluex.liquidbounce.script.api.ScriptModule;
import net.ccbluex.liquidbounce.script.api.ScriptTab;
import net.ccbluex.liquidbounce.script.api.global.Chat;
import net.ccbluex.liquidbounce.script.api.global.Item;
import net.ccbluex.liquidbounce.script.api.global.Setting;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdR\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0012\u0018\ufffd\ufffd2\u00020\u0001:\u00017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0007H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010\u00072\u0006\u0010)\u001a\u00020\u0007H\u0002J\u000e\u0010*\u001a\u00020&2\u0006\u0010\u0002\u001a\u00020\u0007J\u0016\u0010+\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\bJ\u0006\u0010-\u001a\u00020&J\u0006\u0010.\u001a\u00020&J\u0016\u0010/\u001a\u00020&2\u0006\u00100\u001a\u00020\b2\u0006\u00101\u001a\u00020\bJ\u0016\u00102\u001a\u00020&2\u0006\u00103\u001a\u00020\b2\u0006\u00101\u001a\u00020\bJ\u000e\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\bJ\b\u00106\u001a\u00020&H\u0002R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010X\u0086.\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0007X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010 \u001a\u00020\u0007X\u0086.\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b!\u0010\u001c\"\u0004\b\"\u0010\u001eR\u000e\u0010#\u001a\u00020$X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u00068"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/Script;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "scriptFile", "Ljava/io/File;", "(Ljava/io/File;)V", "events", "Ljava/util/HashMap;", "", "Ljdk/nashorn/api/scripting/JSObject;", "Lkotlin/collections/HashMap;", "registeredCommands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "registeredModules", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scriptAuthors", "", "getScriptAuthors", "()[Ljava/lang/String;", "setScriptAuthors", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "scriptEngine", "Ljavax/script/ScriptEngine;", "getScriptFile", "()Ljava/io/File;", "scriptName", "getScriptName", "()Ljava/lang/String;", "setScriptName", "(Ljava/lang/String;)V", "scriptText", "scriptVersion", "getScriptVersion", "setScriptVersion", "state", "", "callEvent", "", "eventName", "getMagicComment", "name", "import", "on", InjectionInfo.DEFAULT_PREFIX, "onDisable", "onEnable", "registerCommand", "commandObject", "callback", "registerModule", "moduleObject", "registerTab", "tabObject", "supportLegacyScripts", "RegisterScript", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/Script.class */
public final class Script extends MinecraftInstance {
    private final ScriptEngine scriptEngine;
    private final String scriptText;

    @NotNull
    public String scriptName;

    @NotNull
    public String scriptVersion;

    @NotNull
    public String[] scriptAuthors;
    private boolean state;
    private final HashMap events;
    private final List registeredModules;
    private final List registeredCommands;

    @NotNull
    private final File scriptFile;

    @NotNull
    public final File getScriptFile() {
        return this.scriptFile;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Script(@NotNull File scriptFile) {
        String[] strArr;
        List listSplit$default;
        Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");
        this.scriptFile = scriptFile;
        this.scriptText = FilesKt.readText$default(this.scriptFile, null, 1, null);
        this.events = new HashMap();
        this.registeredModules = new ArrayList();
        this.registeredCommands = new ArrayList();
        String magicComment = getMagicComment("engine_flags");
        if (magicComment == null || (listSplit$default = StringsKt.split$default((CharSequence) magicComment, new String[]{","}, false, 0, 6, (Object) null)) == null) {
            strArr = new String[0];
        } else {
            Object[] array = listSplit$default.toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            strArr = (String[]) array;
            if (strArr == null) {
            }
        }
        String[] strArr2 = strArr;
        ScriptEngine scriptEngine = new NashornScriptEngineFactory().getScriptEngine((String[]) Arrays.copyOf(strArr2, strArr2.length));
        Intrinsics.checkExpressionValueIsNotNull(scriptEngine, "NashornScriptEngineFacto\u2026criptEngine(*engineFlags)");
        this.scriptEngine = scriptEngine;
        this.scriptEngine.put("Chat", StaticClass.forClass(Chat.class));
        this.scriptEngine.put("Setting", StaticClass.forClass(Setting.class));
        this.scriptEngine.put("Item", StaticClass.forClass(Item.class));
        this.scriptEngine.put("mc", MinecraftInstance.f157mc);
        this.scriptEngine.put("moduleManager", LiquidBounce.INSTANCE.getModuleManager());
        this.scriptEngine.put("commandManager", LiquidBounce.INSTANCE.getCommandManager());
        this.scriptEngine.put("scriptManager", LiquidBounce.INSTANCE.getScriptManager());
        this.scriptEngine.put("registerScript", new RegisterScript(this));
        supportLegacyScripts();
        this.scriptEngine.eval(this.scriptText);
        callEvent("load");
    }

    @NotNull
    public final String getScriptName() {
        String str = this.scriptName;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptName");
        }
        return str;
    }

    public final void setScriptName(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.scriptName = str;
    }

    @NotNull
    public final String getScriptVersion() {
        String str = this.scriptVersion;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptVersion");
        }
        return str;
    }

    public final void setScriptVersion(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.scriptVersion = str;
    }

    @NotNull
    public final String[] getScriptAuthors() {
        String[] strArr = this.scriptAuthors;
        if (strArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptAuthors");
        }
        return strArr;
    }

    public final void setScriptAuthors(@NotNull String[] strArr) {
        Intrinsics.checkParameterIsNotNull(strArr, "<set-?>");
        this.scriptAuthors = strArr;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\ufffd\ufffd2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0016\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/Script$RegisterScript;", "Ljava/util/function/Function;", "Ljdk/nashorn/api/scripting/JSObject;", "Lnet/ccbluex/liquidbounce/script/Script;", "(Lnet/ccbluex/liquidbounce/script/Script;)V", "apply", "scriptObject", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/Script$RegisterScript.class */
    public final class RegisterScript implements Function {
        final Script this$0;

        @Override // java.util.function.Function
        public Object apply(Object obj) {
            return apply((JSObject) obj);
        }

        public RegisterScript(Script script) {
            this.this$0 = script;
        }

        @NotNull
        public Script apply(@NotNull JSObject scriptObject) {
            Intrinsics.checkParameterIsNotNull(scriptObject, "scriptObject");
            Script script = this.this$0;
            Object member = scriptObject.getMember("name");
            if (member == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
            script.setScriptName((String) member);
            Script script2 = this.this$0;
            Object member2 = scriptObject.getMember("version");
            if (member2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
            script2.setScriptVersion((String) member2);
            Script script3 = this.this$0;
            Object objConvert = ScriptUtils.convert(scriptObject.getMember("authors"), String[].class);
            if (objConvert == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            }
            script3.setScriptAuthors((String[]) objConvert);
            return this.this$0;
        }
    }

    public final void registerModule(@NotNull JSObject moduleObject, @NotNull JSObject callback) {
        Intrinsics.checkParameterIsNotNull(moduleObject, "moduleObject");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        ScriptModule scriptModule = new ScriptModule(moduleObject);
        LiquidBounce.INSTANCE.getModuleManager().registerModule((Module) scriptModule);
        this.registeredModules.add(scriptModule);
        callback.call(moduleObject, new Object[]{scriptModule});
    }

    public final void registerCommand(@NotNull JSObject commandObject, @NotNull JSObject callback) {
        Intrinsics.checkParameterIsNotNull(commandObject, "commandObject");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        ScriptCommand scriptCommand = new ScriptCommand(commandObject);
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(scriptCommand);
        this.registeredCommands.add(scriptCommand);
        callback.call(commandObject, new Object[]{scriptCommand});
    }

    public final void registerTab(@NotNull JSObject tabObject) {
        Intrinsics.checkParameterIsNotNull(tabObject, "tabObject");
        new ScriptTab(tabObject);
    }

    private final String getMagicComment(String str) {
        for (String str2 : StringsKt.lines(this.scriptText)) {
            if (!StringsKt.startsWith$default(str2, "///", false, 2, (Object) null)) {
                return null;
            }
            if (str2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring = str2.substring(3);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
            List listSplit$default = StringsKt.split$default((CharSequence) strSubstring, new String[]{"="}, false, 2, 2, (Object) null);
            String str3 = (String) CollectionsKt.first(listSplit$default);
            if (str3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            if (Intrinsics.areEqual(StringsKt.trim((CharSequence) str3).toString(), str)) {
                String str4 = (String) CollectionsKt.last(listSplit$default);
                if (str4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                return StringsKt.trim((CharSequence) str4).toString();
            }
        }
        return null;
    }

    private final void supportLegacyScripts() {
        if (!Intrinsics.areEqual(getMagicComment("api_version"), "2")) {
            ClientUtils.getLogger().info("[ScriptAPI] Running script '" + this.scriptFile.getName() + "' with legacy support.");
            URL resource = LiquidBounce.class.getResource("/assets/minecraft/liquidbounce/scriptapi/legacy.js");
            Intrinsics.checkExpressionValueIsNotNull(resource, "LiquidBounce::class.java\u2026nce/scriptapi/legacy.js\")");
            this.scriptEngine.eval(new String(TextStreamsKt.readBytes(resource), Charsets.UTF_8));
        }
    }

    /* renamed from: on */
    public final void m45on(@NotNull String eventName, @NotNull JSObject handler) {
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        this.events.put(eventName, handler);
    }

    public final void onEnable() {
        if (this.state) {
            return;
        }
        callEvent("enable");
        this.state = true;
    }

    public final void onDisable() {
        if (this.state) {
            Iterator it = this.registeredModules.iterator();
            while (it.hasNext()) {
                LiquidBounce.INSTANCE.getModuleManager().unregisterModule((Module) it.next());
            }
            Iterator it2 = this.registeredCommands.iterator();
            while (it2.hasNext()) {
                LiquidBounce.INSTANCE.getCommandManager().unregisterCommand((Command) it2.next());
            }
            callEvent("disable");
            this.state = false;
        }
    }

    /* renamed from: import, reason: not valid java name */
    public final void m1669import(@NotNull String scriptFile) {
        Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");
        this.scriptEngine.eval(FilesKt.readText$default(new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), scriptFile), null, 1, null));
    }

    private final void callEvent(String str) {
        try {
            JSObject jSObject = (JSObject) this.events.get(str);
            if (jSObject != null) {
                jSObject.call(null, new Object[0]);
            }
        } catch (Throwable th) {
            Logger logger = ClientUtils.getLogger();
            StringBuilder sbAppend = new StringBuilder().append("[ScriptAPI] Exception in script '");
            String str2 = this.scriptName;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptName");
            }
            logger.error(sbAppend.append(str2).append("'!").toString(), th);
        }
    }
}

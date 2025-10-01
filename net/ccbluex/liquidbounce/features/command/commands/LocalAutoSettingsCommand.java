package net.ccbluex.liquidbounce.features.command.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0006H\u0002\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/LocalAutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "getLocalSettings", "Ljava/io/File;", "()[Ljava/io/File;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/LocalAutoSettingsCommand.class */
public final class LocalAutoSettingsCommand extends Command {
    public LocalAutoSettingsCommand() {
        super("localautosettings", new String[]{"localsetting", "localsettings", "localconfig"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        String lowerCase;
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "load", true)) {
                if (args.length > 2) {
                    File file = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, args[2]);
                    if (file.exists()) {
                        try {
                            chat("\u00a79Loading settings...");
                            String text$default = FilesKt.readText$default(file, null, 1, null);
                            chat("\u00a79Set settings...");
                            SettingsUtils.INSTANCE.executeScript(text$default);
                            chat("\u00a76Settings applied successfully.");
                            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Configs", "Updated Settings", NotifyType.SUCCESS, 0, 0, 24, null));
                            playEdit();
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    chat("\u00a7cSettings file does not exist!");
                    return;
                }
                chatSyntax("localautosettings load <name>");
                return;
            }
            if (StringsKt.equals(args[1], "save", true)) {
                if (args.length > 2) {
                    File file2 = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, args[2]);
                    try {
                        if (file2.exists()) {
                            file2.delete();
                        }
                        file2.createNewFile();
                        if (args.length > 3) {
                            String completeString = StringUtils.toCompleteString(args, 3);
                            Intrinsics.checkExpressionValueIsNotNull(completeString, "StringUtils.toCompleteString(args, 3)");
                            if (completeString == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }
                            lowerCase = completeString.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                        } else {
                            lowerCase = "values";
                        }
                        String str = lowerCase;
                        boolean z = StringsKt.contains$default((CharSequence) str, (CharSequence) "all", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "values", false, 2, (Object) null);
                        boolean z2 = StringsKt.contains$default((CharSequence) str, (CharSequence) "all", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "binds", false, 2, (Object) null);
                        boolean z3 = StringsKt.contains$default((CharSequence) str, (CharSequence) "all", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "states", false, 2, (Object) null);
                        if (!z && !z2 && !z3) {
                            chatSyntaxError();
                            return;
                        }
                        chat("\u00a79Creating settings...");
                        String strGenerateScript = SettingsUtils.INSTANCE.generateScript(z, z2, z3);
                        chat("\u00a79Saving settings...");
                        FilesKt.writeText$default(file2, strGenerateScript, null, 2, null);
                        chat("\u00a76Settings saved successfully.");
                        return;
                    } catch (Throwable th) {
                        chat("\u00a7cFailed to create local config: \u00a73" + th.getMessage());
                        ClientUtils.getLogger().error("Failed to create local config.", th);
                        return;
                    }
                }
                chatSyntax("localsettings save <name> [all/values/binds/states]...");
                return;
            }
            if (StringsKt.equals(args[1], "delete", true)) {
                if (args.length > 2) {
                    File file3 = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, args[2]);
                    if (file3.exists()) {
                        file3.delete();
                        chat("\u00a76Settings file deleted successfully.");
                        return;
                    } else {
                        chat("\u00a7cSettings file does not exist!");
                        return;
                    }
                }
                chatSyntax("localsettings delete <name>");
                return;
            }
            if (StringsKt.equals(args[1], "list", true)) {
                chat("\u00a7cSettings:");
                File[] localSettings = getLocalSettings();
                if (localSettings != null) {
                    for (File file4 : localSettings) {
                        chat("> " + file4.getName());
                    }
                    return;
                }
                return;
            }
        }
        chatSyntax("localsettings <load/save/list/delete>");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00e2, code lost:
    
        if (r0.equals("load") != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ee, code lost:
    
        if (r0.equals("delete") != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00f1, code lost:
    
        r0 = getLocalSettings();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00f6, code lost:
    
        if (r0 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0100, code lost:
    
        return kotlin.collections.CollectionsKt.emptyList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0101, code lost:
    
        r0 = new java.util.ArrayList(r0.length);
        r0 = r0.length;
        r16 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0128, code lost:
    
        if (r16 >= r0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x012b, code lost:
    
        r0.add(r0[r16].getName());
        r16 = r16 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0151, code lost:
    
        r0 = r0;
        r0 = new java.util.ArrayList();
        r0 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x017b, code lost:
    
        if (r0.hasNext() == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x017e, code lost:
    
        r0 = r0.next();
        r0 = (java.lang.String) r0;
        kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, "it");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x019e, code lost:
    
        if (kotlin.text.StringsKt.startsWith(r0, r6[1], true) == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01a1, code lost:
    
        r0.add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01b3, code lost:
    
        return r0;
     */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"delete", "list", "load", "save"});
                ArrayList arrayList = new ArrayList();
                for (Object obj : listListOf) {
                    if (StringsKt.startsWith((String) obj, args[0], true)) {
                        arrayList.add(obj);
                    }
                }
                return arrayList;
            case 2:
                String str = args[0];
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1335458389:
                        break;
                    case 3327206:
                        break;
                    default:
                        return CollectionsKt.emptyList();
                }
            default:
                return CollectionsKt.emptyList();
        }
    }

    private final File[] getLocalSettings() {
        return LiquidBounce.INSTANCE.getFileManager().settingsDir.listFiles();
    }
}

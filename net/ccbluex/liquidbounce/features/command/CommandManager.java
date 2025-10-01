package net.ccbluex.liquidbounce.features.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindsCommand;
import net.ccbluex.liquidbounce.features.command.commands.EnchantCommand;
import net.ccbluex.liquidbounce.features.command.commands.FriendCommand;
import net.ccbluex.liquidbounce.features.command.commands.GiveCommand;
import net.ccbluex.liquidbounce.features.command.commands.HClipCommand;
import net.ccbluex.liquidbounce.features.command.commands.HelpCommand;
import net.ccbluex.liquidbounce.features.command.commands.HideCommand;
import net.ccbluex.liquidbounce.features.command.commands.HoloStandCommand;
import net.ccbluex.liquidbounce.features.command.commands.HurtCommand;
import net.ccbluex.liquidbounce.features.command.commands.LocalAutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.LoginCommand;
import net.ccbluex.liquidbounce.features.command.commands.PanicCommand;
import net.ccbluex.liquidbounce.features.command.commands.PingCommand;
import net.ccbluex.liquidbounce.features.command.commands.PrefixCommand;
import net.ccbluex.liquidbounce.features.command.commands.ReloadCommand;
import net.ccbluex.liquidbounce.features.command.commands.RemoteViewCommand;
import net.ccbluex.liquidbounce.features.command.commands.RenameCommand;
import net.ccbluex.liquidbounce.features.command.commands.SayCommand;
import net.ccbluex.liquidbounce.features.command.commands.ScriptManagerCommand;
import net.ccbluex.liquidbounce.features.command.commands.ServerInfoCommand;
import net.ccbluex.liquidbounce.features.command.commands.ShortcutCommand;
import net.ccbluex.liquidbounce.features.command.commands.TacoCommand;
import net.ccbluex.liquidbounce.features.command.commands.TargetCommand;
import net.ccbluex.liquidbounce.features.command.commands.ToggleCommand;
import net.ccbluex.liquidbounce.features.command.commands.UsernameCommand;
import net.ccbluex.liquidbounce.features.command.commands.VClipCommand;
import net.ccbluex.liquidbounce.features.command.shortcuts.Shortcut;
import net.ccbluex.liquidbounce.features.command.shortcuts.ShortcutParser;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\nJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001c\u001a\u00020\nJ\u001d\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\nH\u0002\u00a2\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0005J\u0006\u0010!\u001a\u00020\u001aJ\u0016\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010#\u001a\u00020\nJ\u0010\u0010$\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u0005J\u000e\u0010%\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\nR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006&"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "", "()V", "commands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "getCommands", "()Ljava/util/List;", "latestAutoComplete", "", "", "getLatestAutoComplete", "()[Ljava/lang/String;", "setLatestAutoComplete", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "prefix", "", "getPrefix", "()C", "setPrefix", "(C)V", "autoComplete", "", "input", "executeCommands", "", "getCommand", "name", "getCompletions", "(Ljava/lang/String;)[Ljava/lang/String;", "registerCommand", "command", "registerCommands", "registerShortcut", "script", "unregisterCommand", "unregisterShortcut", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/CommandManager.class */
public final class CommandManager {

    @NotNull
    private final List commands = new ArrayList();

    @NotNull
    private String[] latestAutoComplete = new String[0];
    private char prefix = '.';

    @NotNull
    public final List getCommands() {
        return this.commands;
    }

    @NotNull
    public final String[] getLatestAutoComplete() {
        return this.latestAutoComplete;
    }

    public final void setLatestAutoComplete(@NotNull String[] strArr) {
        Intrinsics.checkParameterIsNotNull(strArr, "<set-?>");
        this.latestAutoComplete = strArr;
    }

    public final char getPrefix() {
        return this.prefix;
    }

    public final void setPrefix(char c) {
        this.prefix = c;
    }

    public final void registerCommands() {
        registerCommand(new BindCommand());
        registerCommand(new VClipCommand());
        registerCommand(new HClipCommand());
        registerCommand(new HelpCommand());
        registerCommand(new SayCommand());
        registerCommand(new FriendCommand());
        registerCommand(new AutoSettingsCommand());
        registerCommand(new LocalAutoSettingsCommand());
        registerCommand(new ServerInfoCommand());
        registerCommand(new ToggleCommand());
        registerCommand(new HurtCommand());
        registerCommand(new GiveCommand());
        registerCommand(new UsernameCommand());
        registerCommand(new TargetCommand());
        registerCommand(new TacoCommand());
        registerCommand(new BindsCommand());
        registerCommand(new HoloStandCommand());
        registerCommand(new PanicCommand());
        registerCommand(new PingCommand());
        registerCommand(new RenameCommand());
        registerCommand(new EnchantCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new LoginCommand());
        registerCommand(new ScriptManagerCommand());
        registerCommand(new RemoteViewCommand());
        registerCommand(new PrefixCommand());
        registerCommand(new ShortcutCommand());
        registerCommand(new HideCommand());
    }

    public final void executeCommands(@NotNull String input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        for (Command command : this.commands) {
            Object[] array = StringsKt.split$default((CharSequence) input, new String[]{" "}, false, 0, 6, (Object) null).toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            String[] strArr = (String[]) array;
            if (StringsKt.equals(strArr[0], String.valueOf(this.prefix) + command.getCommand(), true)) {
                command.execute(strArr);
                return;
            }
            for (String str : command.getAlias()) {
                if (StringsKt.equals(strArr[0], String.valueOf(this.prefix) + str, true)) {
                    command.execute(strArr);
                    return;
                }
            }
        }
        ClientUtils.displayChatMessage("\u00a7cCommand not found. Type " + this.prefix + "help to view all commands.");
    }

    public final boolean autoComplete(@NotNull String input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        CommandManager commandManager = this;
        String[] completions = getCompletions(input);
        if (completions == null) {
            commandManager = commandManager;
            completions = new String[0];
        }
        commandManager.latestAutoComplete = completions;
        if (StringsKt.startsWith$default((CharSequence) input, this.prefix, false, 2, (Object) null)) {
            if (!(this.latestAutoComplete.length == 0)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x019e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String[] getCompletions(String str) {
        String command;
        boolean z;
        boolean z2;
        List listTabComplete;
        if (str.length() > 0) {
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            char[] charArray = str.toCharArray();
            Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
            if (charArray[0] == this.prefix) {
                List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{" "}, false, 0, 6, (Object) null);
                if (listSplit$default.size() > 1) {
                    String str2 = (String) listSplit$default.get(0);
                    if (str2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String strSubstring = str2.substring(1);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
                    Command command2 = getCommand(strSubstring);
                    if (command2 != null) {
                        Object[] array = CollectionsKt.drop(listSplit$default, 1).toArray(new String[0]);
                        if (array == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                        }
                        listTabComplete = command2.tabComplete((String[]) array);
                    } else {
                        listTabComplete = null;
                    }
                    List list = listTabComplete;
                    if (list == null) {
                        return null;
                    }
                    Object[] array2 = list.toArray(new String[0]);
                    if (array2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                    return (String[]) array2;
                }
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring2 = str.substring(1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                List list2 = this.commands;
                ArrayList arrayList = new ArrayList();
                for (Object obj : list2) {
                    Command command3 = (Command) obj;
                    if (!StringsKt.startsWith(command3.getCommand(), strSubstring2, true)) {
                        String[] alias = command3.getAlias();
                        int length = alias.length;
                        int i = 0;
                        while (true) {
                            if (i < length) {
                                if (StringsKt.startsWith(alias[i], strSubstring2, true)) {
                                    z2 = true;
                                    break;
                                }
                                i++;
                            } else {
                                z2 = false;
                                break;
                            }
                        }
                        z = z2;
                    }
                    if (z) {
                        arrayList.add(obj);
                    }
                }
                ArrayList<Command> arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                for (Command command4 : arrayList2) {
                    if (StringsKt.startsWith(command4.getCommand(), strSubstring2, true)) {
                        command = command4.getCommand();
                    } else {
                        for (String str3 : command4.getAlias()) {
                            if (StringsKt.startsWith(str3, strSubstring2, true)) {
                                command = str3;
                            }
                        }
                        throw new NoSuchElementException("Array contains no element matching the predicate.");
                    }
                    arrayList3.add(String.valueOf(this.prefix) + command);
                }
                Object[] array3 = arrayList3.toArray(new String[0]);
                if (array3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
                return (String[]) array3;
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0075  */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Command getCommand(@NotNull String name) {
        Object obj;
        boolean z;
        Intrinsics.checkParameterIsNotNull(name, "name");
        Iterator it = this.commands.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            Command command = (Command) next;
            if (!StringsKt.equals(command.getCommand(), name, true)) {
                String[] alias = command.getAlias();
                int length = alias.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        if (StringsKt.equals(alias[i], name, true)) {
                            z = true;
                            break;
                        }
                        i++;
                    } else {
                        z = false;
                        break;
                    }
                }
                boolean z2 = z;
                if (z2) {
                    obj = next;
                    break;
                }
            }
        }
        return (Command) obj;
    }

    public final boolean registerCommand(@NotNull Command command) {
        Intrinsics.checkParameterIsNotNull(command, "command");
        return this.commands.add(command);
    }

    public final void registerShortcut(@NotNull String name, @NotNull String script) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(script, "script");
        if (getCommand(name) != null) {
            throw new IllegalArgumentException("Command already exists!");
        }
        List<List> list = ShortcutParser.INSTANCE.parse(script);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (List list2 : list) {
            Command command = getCommand((String) list2.get(0));
            if (command == null) {
                throw new IllegalArgumentException("Command " + ((String) list2.get(0)) + " not found!");
            }
            Object[] array = list2.toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            arrayList.add(new Pair(command, array));
        }
        registerCommand(new Shortcut(name, arrayList));
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().shortcutsConfig);
    }

    public final boolean unregisterShortcut(@NotNull final String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        boolean zRemoveIf = this.commands.removeIf(new Predicate(name) { // from class: net.ccbluex.liquidbounce.features.command.CommandManager$unregisterShortcut$removed$1
            final String $name;

            {
                this.$name = name;
            }

            @Override // java.util.function.Predicate
            public boolean test(Object obj) {
                return test((Command) obj);
            }

            public final boolean test(@NotNull Command it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return (it instanceof Shortcut) && StringsKt.equals(it.getCommand(), this.$name, true);
            }
        });
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().shortcutsConfig);
        return zRemoveIf;
    }

    public final boolean unregisterCommand(@Nullable Command command) {
        List list = this.commands;
        if (list == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        }
        return TypeIntrinsics.asMutableCollection(list).remove(command);
    }
}

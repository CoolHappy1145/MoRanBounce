package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ShortcutCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/ShortcutCommand.class */
public final class ShortcutCommand extends Command {
    public ShortcutCommand() {
        super("shortcut", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 3 && StringsKt.equals(args[1], "add", true)) {
            try {
                CommandManager commandManager = LiquidBounce.INSTANCE.getCommandManager();
                String str = args[2];
                String completeString = StringUtils.toCompleteString(args, 3);
                Intrinsics.checkExpressionValueIsNotNull(completeString, "StringUtils.toCompleteString(args, 3)");
                commandManager.registerShortcut(str, completeString);
                chat("Successfully added shortcut.");
                return;
            } catch (IllegalArgumentException e) {
                String message = e.getMessage();
                if (message == null) {
                    Intrinsics.throwNpe();
                }
                chat(message);
                return;
            }
        }
        if (args.length >= 3 && StringsKt.equals(args[1], "remove", true)) {
            if (LiquidBounce.INSTANCE.getCommandManager().unregisterShortcut(args[2])) {
                chat("Successfully removed shortcut.");
                return;
            } else {
                chat("Shortcut does not exist.");
                return;
            }
        }
        chat("shortcut <add <shortcut_name> <script>/remove <shortcut_name>>");
    }
}

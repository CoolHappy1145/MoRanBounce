package net.ccbluex.liquidbounce.features.command.commands;

import java.util.Comparator;
import java.util.List;
import joptsimple.internal.Strings;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HelpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/HelpCommand.class */
public final class HelpCommand extends Command {
    public HelpCommand() {
        super("help", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws NumberFormatException {
        int i;
        Intrinsics.checkParameterIsNotNull(args, "args");
        int i2 = 1;
        if (args.length > 1) {
            try {
                i2 = Integer.parseInt(args[1]);
            } catch (NumberFormatException unused) {
                chatSyntaxError();
            }
        }
        if (i2 <= 0) {
            chat("The number you have entered is too low, it must be over 0");
            return;
        }
        double size = LiquidBounce.INSTANCE.getCommandManager().getCommands().size() / 8.0d;
        if (size > ((int) size)) {
            i = ((int) size) + 1;
        } else {
            i = (int) size;
        }
        int i3 = i;
        if (i2 > i3) {
            chat("The number you have entered is too big, it must be under " + i3 + '.');
            return;
        }
        chat("\u00a7c\u00a7lHelp");
        ClientUtils.displayChatMessage("\u00a77> Page: \u00a78" + i2 + " / " + i3);
        List listSortedWith = CollectionsKt.sortedWith(LiquidBounce.INSTANCE.getCommandManager().getCommands(), new Comparator() { // from class: net.ccbluex.liquidbounce.features.command.commands.HelpCommand$execute$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(((Command) obj).getCommand(), ((Command) obj2).getCommand());
            }
        });
        for (int i4 = 8 * (i2 - 1); i4 < 8 * i2 && i4 < listSortedWith.size(); i4++) {
            Command command = (Command) listSortedWith.get(i4);
            ClientUtils.displayChatMessage("\u00a76> \u00a77" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + command.getCommand() + (command.getAlias().length == 0 ? "" : " \u00a77(\u00a78" + Strings.join(command.getAlias(), "\u00a77, \u00a78") + "\u00a77)"));
        }
        ClientUtils.displayChatMessage("\u00a7a------------\n\u00a77> \u00a7c" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + "help \u00a78<\u00a77\u00a7lpage\u00a78>");
    }
}

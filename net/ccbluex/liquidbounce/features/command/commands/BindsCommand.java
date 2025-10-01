package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/BindsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/BindsCommand.class */
public final class BindsCommand extends Command {
    public BindsCommand() {
        super("binds", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1 && StringsKt.equals(args[1], "clear", true)) {
            Iterator it = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
            while (it.hasNext()) {
                ((Module) it.next()).setKeyBind(0);
            }
            chat("Removed all binds.");
            return;
        }
        chat("\u00a7c\u00a7lBinds");
        TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
        ArrayList<Module> arrayList = new ArrayList();
        for (Object obj : modules) {
            if (((Module) obj).getKeyBind() != 0) {
                arrayList.add(obj);
            }
        }
        for (Module module : arrayList) {
            ClientUtils.displayChatMessage("\u00a76> \u00a7c" + module.getName() + ": \u00a7a\u00a7l" + Keyboard.getKeyName(module.getKeyBind()));
        }
        chatSyntax("binds clear");
    }
}

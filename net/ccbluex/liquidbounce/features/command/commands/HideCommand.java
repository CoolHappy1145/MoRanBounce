package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HideCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/HideCommand.class */
public final class HideCommand extends Command {
    public HideCommand() {
        super("hide", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "list", true)) {
                chat("\u00a7c\u00a7lHidden");
                TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
                ArrayList arrayList = new ArrayList();
                for (Object obj : modules) {
                    if (!((Module) obj).getArray()) {
                        arrayList.add(obj);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ClientUtils.displayChatMessage("\u00a76> \u00a7c" + ((Module) it.next()).getName());
                }
                return;
            }
            if (StringsKt.equals(args[1], "clear", true)) {
                Iterator it2 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
                while (it2.hasNext()) {
                    ((Module) it2.next()).setArray(true);
                }
                chat("Cleared hidden modules.");
                return;
            }
            if (StringsKt.equals(args[1], "reset", true)) {
                Iterator it3 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
                while (it3.hasNext()) {
                    Module module = (Module) it3.next();
                    module.setArray(((ModuleInfo) module.getClass().getAnnotation(ModuleInfo.class)).array());
                }
                chat("Reset hidden modules.");
                return;
            }
            Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(args[1]);
            if (module2 == null) {
                chat("Module \u00a7a\u00a7l" + args[1] + "\u00a73 not found.");
                return;
            }
            module2.setArray(!module2.getArray());
            chat("Module \u00a7a\u00a7l" + module2.getName() + "\u00a73 is now \u00a7a\u00a7l" + (module2.getArray() ? "visible" : "invisible") + "\u00a73 on the array list.");
            playEdit();
            return;
        }
        chatSyntax("hide <module/list/clear/reset>");
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        String str = args[0];
        switch (args.length) {
            case 1:
                TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(modules, 10));
                Iterator it = modules.iterator();
                while (it.hasNext()) {
                    arrayList.add(((Module) it.next()).getName());
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : arrayList2) {
                    if (StringsKt.startsWith((String) obj, str, true)) {
                        arrayList3.add(obj);
                    }
                }
                break;
        }
        return CollectionsKt.emptyList();
    }
}

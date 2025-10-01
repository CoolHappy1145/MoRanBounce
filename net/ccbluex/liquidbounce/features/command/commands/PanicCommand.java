package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PanicCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/PanicCommand.class */
public final class PanicCommand extends Command {
    public PanicCommand() {
        super("panic", new String[0]);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:38:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x025e A[LOOP:1: B:62:0x0254->B:64:0x025e, LOOP_END] */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void execute(@NotNull String[] args) {
        String str;
        Iterator it;
        Intrinsics.checkParameterIsNotNull(args, "args");
        TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
        ArrayList arrayList = new ArrayList();
        for (Object obj : modules) {
            if (((Module) obj).getState()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (args.length > 1) {
            if (args[1].length() > 0) {
                String str2 = args[1];
                if (str2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case 96673:
                        if (lowerCase.equals("all")) {
                            str = "all";
                        } else {
                            ModuleCategory[] moduleCategoryArrValues = ModuleCategory.values();
                            ArrayList arrayList3 = new ArrayList();
                            for (ModuleCategory moduleCategory : moduleCategoryArrValues) {
                                if (StringsKt.equals(moduleCategory.getDisplayName(), args[1], true)) {
                                    arrayList3.add(moduleCategory);
                                }
                            }
                            ArrayList arrayList4 = arrayList3;
                            if (arrayList4.isEmpty()) {
                                chat("Category " + args[1] + " not found");
                                return;
                            }
                            ModuleCategory moduleCategory2 = (ModuleCategory) arrayList4.get(0);
                            ArrayList arrayList5 = new ArrayList();
                            for (Object obj2 : arrayList2) {
                                if (((Module) obj2).getCategory() == moduleCategory2) {
                                    arrayList5.add(obj2);
                                }
                            }
                            arrayList2 = arrayList5;
                            str = "all " + moduleCategory2.getDisplayName();
                        }
                        it = arrayList2.iterator();
                        while (it.hasNext()) {
                            ((Module) it.next()).setState(false);
                        }
                        chat("Disabled " + str + " modules.");
                        return;
                    case 1128455843:
                        if (lowerCase.equals("nonrender")) {
                            ArrayList arrayList6 = new ArrayList();
                            for (Object obj3 : arrayList2) {
                                if (((Module) obj3).getCategory() != ModuleCategory.RENDER) {
                                    arrayList6.add(obj3);
                                }
                            }
                            arrayList2 = arrayList6;
                            str = "all non-render";
                        }
                        it = arrayList2.iterator();
                        while (it.hasNext()) {
                        }
                        chat("Disabled " + str + " modules.");
                        return;
                }
            }
        }
        chatSyntax("panic <all/nonrender/combat/player/movement/render/world/misc/exploit/fun>");
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
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"all", "nonrender", "combat", "player", "movement", "render", "world", "misc", "exploit", "fun"});
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

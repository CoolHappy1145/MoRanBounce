package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TargetCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/TargetCommand.class */
public final class TargetCommand extends Command {
    public TargetCommand() {
        super("target", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "players", true)) {
                EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                chat("\u00a77Target player toggled " + (EntityUtils.targetPlayer ? "on" : "off") + '.');
                playEdit();
                return;
            } else if (StringsKt.equals(args[1], "mobs", true)) {
                EntityUtils.targetMobs = !EntityUtils.targetMobs;
                chat("\u00a77Target mobs toggled " + (EntityUtils.targetMobs ? "on" : "off") + '.');
                playEdit();
                return;
            } else if (StringsKt.equals(args[1], "animals", true)) {
                EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                chat("\u00a77Target animals toggled " + (EntityUtils.targetAnimals ? "on" : "off") + '.');
                playEdit();
                return;
            } else if (StringsKt.equals(args[1], "invisible", true)) {
                EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                chat("\u00a77Target Invisible toggled " + (EntityUtils.targetInvisible ? "on" : "off") + '.');
                playEdit();
                return;
            }
        }
        chatSyntax("target <players/mobs/animals/invisible>");
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
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"players", "mobs", "animals", "invisible"});
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

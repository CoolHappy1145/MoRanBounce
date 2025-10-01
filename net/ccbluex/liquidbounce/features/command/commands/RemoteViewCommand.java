package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/RemoteViewCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/RemoteViewCommand.class */
public final class RemoteViewCommand extends Command {
    public RemoteViewCommand() {
        super("remoteview", new String[]{"rv"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length < 2) {
            if (!Intrinsics.areEqual(MinecraftInstance.f157mc.getRenderViewEntity(), MinecraftInstance.f157mc.getThePlayer())) {
                MinecraftInstance.f157mc.setRenderViewEntity(MinecraftInstance.f157mc.getThePlayer());
                return;
            } else {
                chatSyntax("remoteview <username>");
                return;
            }
        }
        String str = args[1];
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        for (IEntity iEntity : theWorld.getLoadedEntityList()) {
            if (Intrinsics.areEqual(str, iEntity.getName())) {
                MinecraftInstance.f157mc.setRenderViewEntity(iEntity);
                chat("Now viewing perspective of \u00a78" + iEntity.getName() + "\u00a73.");
                chat("Execute \u00a78" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + "remoteview \u00a73again to go back to yours.");
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x009c  */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List tabComplete(@NotNull String[] args) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                Collection playerEntities = theWorld.getPlayerEntities();
                ArrayList arrayList = new ArrayList();
                for (Object obj : playerEntities) {
                    IEntityPlayer iEntityPlayer = (IEntityPlayer) obj;
                    if (iEntityPlayer.getName() != null) {
                        String name = iEntityPlayer.getName();
                        if (name == null) {
                            Intrinsics.throwNpe();
                        }
                        z = StringsKt.startsWith(name, args[0], true);
                    }
                    if (z) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    String name2 = ((IEntityPlayer) it.next()).getName();
                    if (name2 == null) {
                        Intrinsics.throwNpe();
                    }
                    arrayList3.add(name2);
                }
                return arrayList3;
            default:
                return CollectionsKt.emptyList();
        }
    }
}

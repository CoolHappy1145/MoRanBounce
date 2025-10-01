package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/FriendCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/FriendCommand.class */
public final class FriendCommand extends Command {
    public FriendCommand() {
        super("friend", new String[]{"friends"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            FriendsConfig friendsConfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
            if (StringsKt.equals(args[1], "add", true)) {
                if (args.length > 2) {
                    String str = args[2];
                    if (str.length() == 0) {
                        chat("The name is empty.");
                        return;
                    }
                    if (args.length > 3 ? friendsConfig.addFriend(str, StringUtils.toCompleteString(args, 3)) : friendsConfig.addFriend(str)) {
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(friendsConfig);
                        chat("\u00a7a\u00a7l" + str + "\u00a73 was added to your friend list.");
                        playEdit();
                        return;
                    }
                    chat("The name is already in the list.");
                    return;
                }
                chatSyntax("friend add <name> [alias]");
                return;
            }
            if (StringsKt.equals(args[1], "remove", true)) {
                if (args.length > 2) {
                    String str2 = args[2];
                    if (friendsConfig.removeFriend(str2)) {
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(friendsConfig);
                        chat("\u00a7a\u00a7l" + str2 + "\u00a73 was removed from your friend list.");
                        playEdit();
                        return;
                    }
                    chat("This name is not in the list.");
                    return;
                }
                chatSyntax("friend remove <name>");
                return;
            }
            if (StringsKt.equals(args[1], "clear", true)) {
                Intrinsics.checkExpressionValueIsNotNull(friendsConfig, "friendsConfig");
                int size = friendsConfig.getFriends().size();
                friendsConfig.clearFriends();
                LiquidBounce.INSTANCE.getFileManager().saveConfig(friendsConfig);
                chat("Removed " + size + " friend(s).");
                return;
            }
            if (StringsKt.equals(args[1], "list", true)) {
                chat("Your Friends:");
                Intrinsics.checkExpressionValueIsNotNull(friendsConfig, "friendsConfig");
                for (FriendsConfig.Friend friend : friendsConfig.getFriends()) {
                    StringBuilder sbAppend = new StringBuilder().append("\u00a77> \u00a7a\u00a7l");
                    Intrinsics.checkExpressionValueIsNotNull(friend, "friend");
                    chat(sbAppend.append(friend.getPlayerName()).append(" \u00a7c(\u00a77\u00a7l").append(friend.getAlias()).append("\u00a7c)").toString());
                }
                chat("You have \u00a7c" + friendsConfig.getFriends().size() + "\u00a73 friends.");
                return;
            }
        }
        chatSyntax("friend <add/remove/list/clear>");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"add", "remove", "list", "clear"});
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
                    case -934610812:
                        if (lowerCase.equals("remove")) {
                            FriendsConfig friendsConfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
                            Intrinsics.checkExpressionValueIsNotNull(friendsConfig, "LiquidBounce.fileManager.friendsConfig");
                            List friends = friendsConfig.getFriends();
                            Intrinsics.checkExpressionValueIsNotNull(friends, "LiquidBounce.fileManager.friendsConfig.friends");
                            List<FriendsConfig.Friend> list = friends;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                            for (FriendsConfig.Friend it : list) {
                                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                                arrayList2.add(it.getPlayerName());
                            }
                            ArrayList arrayList3 = arrayList2;
                            ArrayList arrayList4 = new ArrayList();
                            for (Object obj2 : arrayList3) {
                                String it2 = (String) obj2;
                                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                                if (StringsKt.startsWith(it2, args[1], true)) {
                                    arrayList4.add(obj2);
                                }
                            }
                            return arrayList4;
                        }
                        break;
                    case 96417:
                        if (lowerCase.equals("add")) {
                            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                            if (theWorld == null) {
                                Intrinsics.throwNpe();
                            }
                            Collection playerEntities = theWorld.getPlayerEntities();
                            ArrayList arrayList5 = new ArrayList();
                            for (Object obj3 : playerEntities) {
                                String name = ((IEntityPlayer) obj3).getName();
                                if (name != null ? StringsKt.startsWith(name, args[1], true) : false) {
                                    arrayList5.add(obj3);
                                }
                            }
                            ArrayList arrayList6 = arrayList5;
                            ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
                            Iterator it3 = arrayList6.iterator();
                            while (it3.hasNext()) {
                                String name2 = ((IEntityPlayer) it3.next()).getName();
                                if (name2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                arrayList7.add(name2);
                            }
                            return arrayList7;
                        }
                        break;
                }
                return CollectionsKt.emptyList();
            default:
                return CollectionsKt.emptyList();
        }
    }
}

package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/GiveCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/GiveCommand.class */
public final class GiveCommand extends Command {
    public GiveCommand() {
        super("give", new String[]{"item", "i", PropertyDescriptor.GET});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws NumberFormatException {
        Intrinsics.checkParameterIsNotNull(args, "args");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (MinecraftInstance.f157mc.getPlayerController().isNotCreative()) {
                chat("\u00a7c\u00a7lError: \u00a73You need to be in creative mode.");
                return;
            }
            if (args.length > 1) {
                IItemStack iItemStackCreateItem = ItemUtils.createItem(StringUtils.toCompleteString(args, 1));
                if (iItemStackCreateItem == null) {
                    chatSyntaxError();
                    return;
                }
                int i = -1;
                int i2 = 36;
                while (true) {
                    if (i2 > 44) {
                        break;
                    }
                    if (thePlayer.getInventoryContainer().getSlot(i2).getStack() != null) {
                        i2++;
                    } else {
                        i = i2;
                        break;
                    }
                }
                if (i == -1) {
                    int i3 = 9;
                    while (true) {
                        if (i3 > 44) {
                            break;
                        }
                        if (thePlayer.getInventoryContainer().getSlot(i3).getStack() != null) {
                            i3++;
                        } else {
                            i = i3;
                            break;
                        }
                    }
                }
                if (i != -1) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCreativeInventoryAction(i, iItemStackCreateItem));
                    chat("\u00a77Given [\u00a78" + iItemStackCreateItem.getDisplayName() + "\u00a77] * \u00a78" + iItemStackCreateItem.getStackSize() + "\u00a77 to \u00a78" + MinecraftInstance.f157mc.getSession().getUsername() + "\u00a77.");
                    return;
                } else {
                    chat("Your inventory is full.");
                    return;
                }
            }
            chatSyntax("give <item> [amount] [data] [datatag]");
        }
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
                Collection itemRegistryKeys = MinecraftInstance.functions.getItemRegistryKeys();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(itemRegistryKeys, 10));
                Iterator it = itemRegistryKeys.iterator();
                while (it.hasNext()) {
                    String resourcePath = ((IResourceLocation) it.next()).getResourcePath();
                    if (resourcePath == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase = resourcePath.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    arrayList.add(lowerCase);
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : arrayList2) {
                    if (StringsKt.startsWith((String) obj, args[0], true)) {
                        arrayList3.add(obj);
                    }
                }
                return arrayList3;
            default:
                return CollectionsKt.emptyList();
        }
    }
}

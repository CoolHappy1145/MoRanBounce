package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HoloStandCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/HoloStandCommand.class */
public final class HoloStandCommand extends Command {
    public HoloStandCommand() {
        super("holostand", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws NumberFormatException {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 4) {
            if (MinecraftInstance.f157mc.getPlayerController().isNotCreative()) {
                chat("\u00a7c\u00a7lError: \u00a73You need to be in creative mode.");
                return;
            }
            try {
                double d = Double.parseDouble(args[1]);
                double d2 = Double.parseDouble(args[2]);
                double d3 = Double.parseDouble(args[3]);
                String message = StringUtils.toCompleteString(args, 4);
                IItemStack iItemStackCreateItemStack = MinecraftInstance.classProvider.createItemStack(MinecraftInstance.classProvider.getItemEnum(ItemType.ARMOR_STAND));
                INBTTagCompound iNBTTagCompoundCreateNBTTagCompound = MinecraftInstance.classProvider.createNBTTagCompound();
                INBTTagCompound iNBTTagCompoundCreateNBTTagCompound2 = MinecraftInstance.classProvider.createNBTTagCompound();
                iNBTTagCompoundCreateNBTTagCompound2.setInteger("Invisible", 1);
                Intrinsics.checkExpressionValueIsNotNull(message, "message");
                iNBTTagCompoundCreateNBTTagCompound2.setString("CustomName", message);
                iNBTTagCompoundCreateNBTTagCompound2.setInteger("CustomNameVisible", 1);
                iNBTTagCompoundCreateNBTTagCompound2.setInteger("NoGravity", 1);
                INBTTagList iNBTTagListCreateNBTTagList = MinecraftInstance.classProvider.createNBTTagList();
                iNBTTagListCreateNBTTagList.appendTag(MinecraftInstance.classProvider.createNBTTagDouble(d));
                iNBTTagListCreateNBTTagList.appendTag(MinecraftInstance.classProvider.createNBTTagDouble(d2));
                iNBTTagListCreateNBTTagList.appendTag(MinecraftInstance.classProvider.createNBTTagDouble(d3));
                iNBTTagCompoundCreateNBTTagCompound2.setTag("Pos", iNBTTagListCreateNBTTagList);
                iNBTTagCompoundCreateNBTTagCompound.setTag("EntityTag", iNBTTagCompoundCreateNBTTagCompound2);
                iItemStackCreateItemStack.setTagCompound(iNBTTagCompoundCreateNBTTagCompound);
                iItemStackCreateItemStack.setStackDisplayName("\u00a7c\u00a7lHolo\u00a7eStand");
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCreativeInventoryAction(36, iItemStackCreateItemStack));
                chat("The HoloStand was successfully added to your inventory.");
                return;
            } catch (NumberFormatException unused) {
                chatSyntaxError();
                return;
            }
        }
        chatSyntax("holostand <x> <y> <z> <message...>");
    }
}

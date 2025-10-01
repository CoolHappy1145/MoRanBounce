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
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/EnchantCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/EnchantCommand.class */
public final class EnchantCommand extends Command {
    public EnchantCommand() {
        super("enchant", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws NumberFormatException {
        int effectId;
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 2) {
            if (MinecraftInstance.f157mc.getPlayerController().isNotCreative()) {
                chat("\u00a7c\u00a7lError: \u00a73You need to be in creative mode.");
                return;
            }
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            IItemStack heldItem = thePlayer != null ? thePlayer.getHeldItem() : null;
            if ((heldItem != null ? heldItem.getItem() : null) == null) {
                chat("\u00a7c\u00a7lError: \u00a73You need to hold an item.");
                return;
            }
            try {
                effectId = Integer.parseInt(args[1]);
            } catch (NumberFormatException unused) {
                IEnchantment enchantmentByLocation = MinecraftInstance.functions.getEnchantmentByLocation(args[1]);
                if (enchantmentByLocation == null) {
                    chat("There is no enchantment with the name '" + args[1] + '\'');
                    return;
                }
                effectId = enchantmentByLocation.getEffectId();
            }
            int i = effectId;
            IEnchantment enchantmentById = MinecraftInstance.functions.getEnchantmentById(i);
            if (enchantmentById == null) {
                chat("There is no enchantment with the ID '" + i + '\'');
                return;
            }
            try {
                int i2 = Integer.parseInt(args[2]);
                heldItem.addEnchantment(enchantmentById, i2);
                chat(enchantmentById.getTranslatedName(i2) + " added to " + heldItem.getDisplayName() + '.');
                return;
            } catch (NumberFormatException unused2) {
                chatSyntaxError();
                return;
            }
        }
        chatSyntax("enchant <type> [level]");
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
                Collection enchantments = MinecraftInstance.functions.getEnchantments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enchantments, 10));
                Iterator it = enchantments.iterator();
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

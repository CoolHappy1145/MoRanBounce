package net.ccbluex.liquidbounce.utils.item;

import java.util.Objects;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.Contract;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/item/ItemUtils.class */
public final class ItemUtils extends MinecraftInstance {
    public static IItemStack createItem(String str) throws NumberFormatException {
        try {
            String strReplace = str.replace('&', '\u00a7');
            IItem iItemCreateItem = classProvider.createItem();
            String[] strArrSplit = null;
            int i = 1;
            int i2 = 0;
            for (int i3 = 0; i3 <= Math.min(12, strReplace.length() - 2); i3++) {
                strArrSplit = strReplace.substring(i3).split(Pattern.quote(" "));
                iItemCreateItem = functions.getObjectFromItemRegistry(classProvider.createResourceLocation(strArrSplit[0]));
                if (iItemCreateItem != null) {
                    break;
                }
            }
            if (iItemCreateItem == null) {
                return null;
            }
            if (((String[]) Objects.requireNonNull(strArrSplit)).length >= 2 && strArrSplit[1].matches("\\d+")) {
                i = Integer.parseInt(strArrSplit[1]);
            }
            if (strArrSplit.length >= 3 && strArrSplit[2].matches("\\d+")) {
                i2 = Integer.parseInt(strArrSplit[2]);
            }
            IItemStack iItemStackCreateItemStack = classProvider.createItemStack(iItemCreateItem, i, i2);
            if (strArrSplit.length >= 4) {
                StringBuilder sb = new StringBuilder();
                for (int i4 = 3; i4 < strArrSplit.length; i4++) {
                    sb.append(" ").append(strArrSplit[i4]);
                }
                iItemStackCreateItemStack.setTagCompound(classProvider.getJsonToNBTInstance().getTagFromJson(sb.toString()));
            }
            return iItemStackCreateItemStack;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getEnchantment(IItemStack iItemStack, IEnchantment iEnchantment) {
        if (iItemStack == null || iItemStack.getEnchantmentTagList() == null || iItemStack.getEnchantmentTagList().hasNoTags()) {
            return 0;
        }
        for (int i = 0; i < iItemStack.getEnchantmentTagList().tagCount(); i++) {
            INBTTagCompound compoundTagAt = iItemStack.getEnchantmentTagList().getCompoundTagAt(i);
            if ((compoundTagAt.hasKey("ench") && compoundTagAt.getShort("ench") == iEnchantment.getEffectId()) || (compoundTagAt.hasKey("id") && compoundTagAt.getShort("id") == iEnchantment.getEffectId())) {
                return compoundTagAt.getShort("lvl");
            }
        }
        return 0;
    }

    public static int getEnchantmentCount(IItemStack iItemStack) {
        if (iItemStack == null || iItemStack.getEnchantmentTagList() == null || iItemStack.getEnchantmentTagList().hasNoTags()) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < iItemStack.getEnchantmentTagList().tagCount(); i2++) {
            INBTTagCompound compoundTagAt = iItemStack.getEnchantmentTagList().getCompoundTagAt(i2);
            if (compoundTagAt.hasKey("ench") || compoundTagAt.hasKey("id")) {
                i++;
            }
        }
        return i;
    }

    @Contract("null -> true")
    public static boolean isStackEmpty(IItemStack iItemStack) {
        return iItemStack == null || classProvider.isItemAir(iItemStack.getItem());
    }
}

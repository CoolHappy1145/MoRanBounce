package net.ccbluex.liquidbounce.utils.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/item/ArmorComparator.class */
public class ArmorComparator extends MinecraftInstance implements Comparator {
    private static final IEnchantment[] DAMAGE_REDUCTION_ENCHANTMENTS = {classProvider.getEnchantmentEnum(EnchantmentType.PROTECTION), classProvider.getEnchantmentEnum(EnchantmentType.PROJECTILE_PROTECTION), classProvider.getEnchantmentEnum(EnchantmentType.FIRE_PROTECTION), classProvider.getEnchantmentEnum(EnchantmentType.BLAST_PROTECTION)};
    private static final float[] ENCHANTMENT_FACTORS = {1.5f, 0.4f, 0.39f, 0.38f};
    private static final float[] ENCHANTMENT_DAMAGE_REDUCTION_FACTOR = {0.04f, 0.08f, 0.15f, 0.08f};
    private static final IEnchantment[] OTHER_ENCHANTMENTS = {classProvider.getEnchantmentEnum(EnchantmentType.FEATHER_FALLING), classProvider.getEnchantmentEnum(EnchantmentType.THORNS), classProvider.getEnchantmentEnum(EnchantmentType.RESPIRATION), classProvider.getEnchantmentEnum(EnchantmentType.AQUA_AFFINITY), classProvider.getEnchantmentEnum(EnchantmentType.UNBREAKING)};
    private static final float[] OTHER_ENCHANTMENT_FACTORS = {3.0f, 1.0f, 0.1f, 0.05f, 0.01f};

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return compare((ArmorPiece) obj, (ArmorPiece) obj2);
    }

    public static double round(double d, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        return BigDecimal.valueOf(d).setScale(i, RoundingMode.HALF_UP).doubleValue();
    }

    public int compare(ArmorPiece armorPiece, ArmorPiece armorPiece2) {
        int iCompare = Double.compare(round(getThresholdedDamageReduction(armorPiece2.getItemStack()), 3), round(getThresholdedDamageReduction(armorPiece.getItemStack()), 3));
        if (iCompare == 0) {
            int iCompare2 = Double.compare(round(getEnchantmentThreshold(armorPiece.getItemStack()), 3), round(getEnchantmentThreshold(armorPiece2.getItemStack()), 3));
            if (iCompare2 == 0) {
                int iCompare3 = Integer.compare(ItemUtils.getEnchantmentCount(armorPiece.getItemStack()), ItemUtils.getEnchantmentCount(armorPiece2.getItemStack()));
                if (iCompare3 != 0) {
                    return iCompare3;
                }
                IItemArmor iItemArmorAsItemArmor = armorPiece.getItemStack().getItem().asItemArmor();
                IItemArmor iItemArmorAsItemArmor2 = armorPiece2.getItemStack().getItem().asItemArmor();
                int iCompare4 = Integer.compare(iItemArmorAsItemArmor.getArmorMaterial().getDurability(iItemArmorAsItemArmor.getArmorType()), iItemArmorAsItemArmor2.getArmorMaterial().getDurability(iItemArmorAsItemArmor2.getArmorType()));
                if (iCompare4 != 0) {
                    return iCompare4;
                }
                return Integer.compare(iItemArmorAsItemArmor.getArmorMaterial().getEnchantability(), iItemArmorAsItemArmor2.getArmorMaterial().getEnchantability());
            }
            return iCompare2;
        }
        return iCompare;
    }

    private float getThresholdedDamageReduction(IItemStack iItemStack) {
        IItemArmor iItemArmorAsItemArmor = iItemStack.getItem().asItemArmor();
        return getDamageReduction(iItemArmorAsItemArmor.getArmorMaterial().getDamageReductionAmount(iItemArmorAsItemArmor.getArmorType()), 0) * (1.0f - getThresholdedEnchantmentDamageReduction(iItemStack));
    }

    private float getDamageReduction(int i, int i2) {
        return 1.0f - (Math.min(20.0f, Math.max(i / 5.0f, i - (1.0f / (2.0f + (i2 / 4.0f))))) / 25.0f);
    }

    private float getThresholdedEnchantmentDamageReduction(IItemStack iItemStack) {
        float enchantment = 0.0f;
        for (int i = 0; i < DAMAGE_REDUCTION_ENCHANTMENTS.length; i++) {
            enchantment += ItemUtils.getEnchantment(iItemStack, DAMAGE_REDUCTION_ENCHANTMENTS[i]) * ENCHANTMENT_FACTORS[i] * ENCHANTMENT_DAMAGE_REDUCTION_FACTOR[i];
        }
        return enchantment;
    }

    private float getEnchantmentThreshold(IItemStack iItemStack) {
        float enchantment = 0.0f;
        for (int i = 0; i < OTHER_ENCHANTMENTS.length; i++) {
            enchantment += ItemUtils.getEnchantment(iItemStack, OTHER_ENCHANTMENTS[i]) * OTHER_ENCHANTMENT_FACTORS[i];
        }
        return enchantment;
    }
}

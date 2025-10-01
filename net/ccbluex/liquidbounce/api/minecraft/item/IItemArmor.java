package net.ccbluex.liquidbounce.api.minecraft.item;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.minecraft.IArmorMaterial;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "armorMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "getArmorMaterial", "()Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "armorType", "", "getArmorType", "()I", "getColor", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItemArmor.class */
public interface IItemArmor extends IItem {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItemArmor$DefaultImpls.class */
    public static final class DefaultImpls {
        @NotNull
        public static IItem getItemByID(IItemArmor iItemArmor, int i) {
            return IItem.DefaultImpls.getItemByID(iItemArmor, i);
        }
    }

    @NotNull
    IArmorMaterial getArmorMaterial();

    int getArmorType();

    int getColor(@NotNull IItemStack iItemStack);
}

package net.ccbluex.liquidbounce.utils.item;

import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/item/ArmorPiece.class */
public class ArmorPiece {
    private final IItemStack itemStack;
    private final int slot;

    public ArmorPiece(IItemStack iItemStack, int i) {
        this.itemStack = iItemStack;
        this.slot = i;
    }

    public int getArmorType() {
        return this.itemStack.getItem().asItemArmor().getArmorType();
    }

    public int getSlot() {
        return this.slot;
    }

    public IItemStack getItemStack() {
        return this.itemStack;
    }
}

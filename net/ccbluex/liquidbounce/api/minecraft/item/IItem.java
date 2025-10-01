package net.ccbluex.liquidbounce.api.minecraft.item;

import kotlin.Metadata;
import kotlin.TypeCastException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "asItemArmor", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "asItemBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBlock;", "asItemBucket", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "asItemPotion", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "asItemSword", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemSword;", "getItemByID", "id", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItem.class */
public interface IItem {
    @NotNull
    String getUnlocalizedName();

    @NotNull
    IItemArmor asItemArmor();

    @NotNull
    IItemPotion asItemPotion();

    @NotNull
    IItemBlock asItemBlock();

    @NotNull
    IItemSword asItemSword();

    @NotNull
    IItemBucket asItemBucket();

    @NotNull
    IItem getItemByID(int i);

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItem$DefaultImpls.class */
    public static final class DefaultImpls {
        @NotNull
        public static IItem getItemByID(IItem iItem, int i) {
            IItem iItemFunc_150899_d = Item.func_150899_d(i);
            if (iItemFunc_150899_d == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.item.IItem");
            }
            return iItemFunc_150899_d;
        }
    }
}

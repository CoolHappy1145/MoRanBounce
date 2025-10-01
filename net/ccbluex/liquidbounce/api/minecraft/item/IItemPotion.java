package net.ccbluex.liquidbounce.api.minecraft.item;

import java.util.Collection;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getEffects", "", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItemPotion.class */
public interface IItemPotion extends IItem {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItemPotion$DefaultImpls.class */
    public static final class DefaultImpls {
        @NotNull
        public static IItem getItemByID(IItemPotion iItemPotion, int i) {
            return IItem.DefaultImpls.getItemByID(iItemPotion, i);
        }
    }

    @NotNull
    Collection getEffects(@NotNull IItemStack iItemStack);
}

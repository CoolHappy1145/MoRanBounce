package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBucket;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "T", "Lnet/minecraft/item/Item;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "wrapped", "(Lnet/minecraft/item/Item;)V", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "getWrapped", "()Lnet/minecraft/item/Item;", "Lnet/minecraft/item/Item;", "asItemArmor", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "asItemBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBlock;", "asItemBucket", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "asItemPotion", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "asItemSword", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemSword;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemImpl.class */
public class ItemImpl implements IItem {

    @NotNull
    private final Item wrapped;

    @NotNull
    public final Item getWrapped() {
        return this.wrapped;
    }

    public ItemImpl(@NotNull Item wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public IItem getItemByID(int i) {
        return IItem.DefaultImpls.getItemByID(this, i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public String getUnlocalizedName() {
        String strFunc_77658_a = this.wrapped.func_77658_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_77658_a, "wrapped.unlocalizedName");
        return strFunc_77658_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public IItemArmor asItemArmor() {
        ItemArmor itemArmor = this.wrapped;
        if (itemArmor == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
        }
        return new ItemArmorImpl(itemArmor);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public IItemPotion asItemPotion() {
        ItemPotion itemPotion = this.wrapped;
        if (itemPotion == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
        }
        return new ItemPotionImpl(itemPotion);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public IItemBlock asItemBlock() {
        ItemBlock itemBlock = this.wrapped;
        if (itemBlock == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
        }
        return new ItemBlockImpl(itemBlock);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public IItemSword asItemSword() {
        ItemSword itemSword = this.wrapped;
        if (itemSword == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemSword");
        }
        return new ItemSwordImpl(itemSword);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public IItemBucket asItemBucket() {
        ItemBucket itemBucket = this.wrapped;
        if (itemBucket == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemBucket");
        }
        return new ItemBucketImpl(itemBucket);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && Intrinsics.areEqual(((ItemImpl) obj).wrapped, this.wrapped);
    }
}

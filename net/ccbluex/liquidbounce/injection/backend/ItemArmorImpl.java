package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.minecraft.IArmorMaterial;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ItemArmorImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemArmor;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "wrapped", "(Lnet/minecraft/item/ItemArmor;)V", "armorMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "getArmorMaterial", "()Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "armorType", "", "getArmorType", "()I", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "getColor", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemArmorImpl.class */
public final class ItemArmorImpl extends ItemImpl implements IItemArmor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ItemArmorImpl(@NotNull ItemArmor wrapped) {
        super((Item) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor
    @NotNull
    public IArmorMaterial getArmorMaterial() {
        ItemArmor.ArmorMaterial armorMaterialFunc_82812_d = getWrapped().func_82812_d();
        Intrinsics.checkExpressionValueIsNotNull(armorMaterialFunc_82812_d, "wrapped.armorMaterial");
        return new ArmorMaterialImpl(armorMaterialFunc_82812_d);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor
    public int getArmorType() {
        EntityEquipmentSlot entityEquipmentSlot = getWrapped().field_77881_a;
        Intrinsics.checkExpressionValueIsNotNull(entityEquipmentSlot, "wrapped.armorType");
        return entityEquipmentSlot.func_188454_b();
    }

    @Override // net.ccbluex.liquidbounce.injection.backend.ItemImpl, net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public String getUnlocalizedName() {
        String strFunc_77658_a = getWrapped().func_77658_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_77658_a, "wrapped.unlocalizedName");
        return strFunc_77658_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor
    public int getColor(@NotNull IItemStack stack) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        return getWrapped().func_82814_b(((ItemStackImpl) stack).getWrapped());
    }
}

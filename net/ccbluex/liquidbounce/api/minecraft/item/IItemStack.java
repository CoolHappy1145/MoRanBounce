package net.ccbluex.liquidbounce.api.minecraft.item;

import java.util.Collection;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdn\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0003H&J\u0016\u0010.\u001a\b\u0012\u0004\u0012\u0002000/2\u0006\u00101\u001a\u00020\tH&J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H&J\b\u00106\u001a\u000207H&J\u0010\u00108\u001a\u00020\ufffd\ufffd2\u0006\u0010\b\u001a\u00020\tH&J\u0018\u00109\u001a\u00020*2\u0006\u00101\u001a\u00020\t2\u0006\u0010:\u001a\u00020;H&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\rX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u0012\u0010\u0017\u001a\u00020\u0018X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0005R\u0012\u0010\u001d\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0005R\u0012\u0010\u001f\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0005R\u001a\u0010!\u001a\u0004\u0018\u00010\"X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0012\u0010'\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u000b\u00a8\u0006<"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "", "animationsToGo", "", "getAnimationsToGo", "()I", "setAnimationsToGo", "(I)V", "displayName", "", "getDisplayName", "()Ljava/lang/String;", "enchantmentTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "getEnchantmentTagList", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "itemDamage", "getItemDamage", "setItemDamage", "itemDelay", "", "getItemDelay", "()J", "maxDamage", "getMaxDamage", "maxItemUseDuration", "getMaxItemUseDuration", "stackSize", "getStackSize", "tagCompound", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "getTagCompound", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "setTagCompound", "(Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;)V", "unlocalizedName", "getUnlocalizedName", "addEnchantment", "", "enchantment", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "level", "getAttributeModifier", "", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "key", "getStrVsBlock", "", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "isSplash", "", "setStackDisplayName", "setTagInfo", "nbt", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/item/IItemStack.class */
public interface IItemStack {
    @NotNull
    String getDisplayName();

    @NotNull
    String getUnlocalizedName();

    int getMaxItemUseDuration();

    @Nullable
    INBTTagList getEnchantmentTagList();

    @Nullable
    INBTTagCompound getTagCompound();

    void setTagCompound(@Nullable INBTTagCompound iNBTTagCompound);

    int getStackSize();

    int getItemDamage();

    void setItemDamage(int i);

    int getAnimationsToGo();

    void setAnimationsToGo(int i);

    int getMaxDamage();

    @Nullable
    IItem getItem();

    long getItemDelay();

    float getStrVsBlock(@NotNull IIBlockState iIBlockState);

    void setTagInfo(@NotNull String str, @NotNull INBTBase iNBTBase);

    @NotNull
    IItemStack setStackDisplayName(@NotNull String str);

    void addEnchantment(@NotNull IEnchantment iEnchantment, int i);

    @NotNull
    Collection getAttributeModifier(@NotNull String str);

    boolean isSplash();
}

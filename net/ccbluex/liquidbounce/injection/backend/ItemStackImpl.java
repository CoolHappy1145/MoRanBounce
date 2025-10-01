package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.entity.p004ai.attributes.IAttributeModifier;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdv\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u0006H\u0016J\u0016\u00104\u001a\b\u0012\u0004\u0012\u000206052\u0006\u00107\u001a\u00020\rH\u0016J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0016J\b\u0010<\u001a\u00020=H\u0016J\u0010\u0010>\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010?\u001a\u0002002\u0006\u00107\u001a\u00020\r2\u0006\u0010@\u001a\u00020AH\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R$\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0019\u0010\t\"\u0004\b\u001a\u0010\u000bR\u0014\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010\tR\u0014\u0010!\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\tR\u0014\u0010#\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010\tR(\u0010&\u001a\u0004\u0018\u00010%2\b\u0010\u0005\u001a\u0004\u0018\u00010%8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0014\u0010+\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b-\u0010.\u00a8\u0006B"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ItemStackImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "wrapped", "Lnet/minecraft/item/ItemStack;", "(Lnet/minecraft/item/ItemStack;)V", PropertyDescriptor.VALUE, "", "animationsToGo", "getAnimationsToGo", "()I", "setAnimationsToGo", "(I)V", "displayName", "", "getDisplayName", "()Ljava/lang/String;", "enchantmentTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "getEnchantmentTagList", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "itemDamage", "getItemDamage", "setItemDamage", "itemDelay", "", "getItemDelay", "()J", "maxDamage", "getMaxDamage", "maxItemUseDuration", "getMaxItemUseDuration", "stackSize", "getStackSize", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "tagCompound", "getTagCompound", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "setTagCompound", "(Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;)V", "unlocalizedName", "getUnlocalizedName", "getWrapped", "()Lnet/minecraft/item/ItemStack;", "addEnchantment", "", "enchantment", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "level", "getAttributeModifier", "", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "key", "getStrVsBlock", "", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "isSplash", "", "setStackDisplayName", "setTagInfo", "nbt", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemStackImpl.class */
public final class ItemStackImpl implements IItemStack {

    @NotNull
    private final ItemStack wrapped;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/entity/ai/attributes/AttributeModifier;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ItemStackImpl$getAttributeModifier$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemStackImpl$getAttributeModifier$1.class */
    static final /* synthetic */ class C04461 extends FunctionReference implements Function1 {
        public static final C04461 INSTANCE = new C04461();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(AttributeModifierImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04461() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IAttributeModifier) obj);
        }

        @NotNull
        public final AttributeModifier invoke(@NotNull IAttributeModifier p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((AttributeModifierImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "p1", "Lnet/minecraft/entity/ai/attributes/AttributeModifier;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ItemStackImpl$getAttributeModifier$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemStackImpl$getAttributeModifier$2.class */
    static final /* synthetic */ class C04472 extends FunctionReference implements Function1 {
        public static final C04472 INSTANCE = new C04472();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(AttributeModifierImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04472() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((AttributeModifier) obj);
        }

        @NotNull
        public final IAttributeModifier invoke(@NotNull AttributeModifier p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new AttributeModifierImpl(p1);
        }
    }

    @NotNull
    public final ItemStack getWrapped() {
        return this.wrapped;
    }

    public ItemStackImpl(@NotNull ItemStack wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public float getStrVsBlock(@NotNull IIBlockState block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        return this.wrapped.func_150997_a(((IBlockStateImpl) block).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public void setTagInfo(@NotNull String key, @NotNull INBTBase nbt) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(nbt, "nbt");
        this.wrapped.func_77983_a(key, ((NBTBaseImpl) nbt).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @NotNull
    public IItemStack setStackDisplayName(@NotNull String displayName) {
        Intrinsics.checkParameterIsNotNull(displayName, "displayName");
        ItemStack itemStackFunc_151001_c = this.wrapped.func_151001_c(displayName);
        Intrinsics.checkExpressionValueIsNotNull(itemStackFunc_151001_c, "wrapped.setStackDisplayName(displayName)");
        return new ItemStackImpl(itemStackFunc_151001_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public void addEnchantment(@NotNull IEnchantment enchantment, int i) {
        Intrinsics.checkParameterIsNotNull(enchantment, "enchantment");
        this.wrapped.func_77966_a(((EnchantmentImpl) enchantment).getWrapped(), i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @NotNull
    public Collection getAttributeModifier(@NotNull String key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return new WrappedCollection(this.wrapped.func_111283_C(EntityEquipmentSlot.MAINHAND).get(key), C04461.INSTANCE, C04472.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public boolean isSplash() {
        return Intrinsics.areEqual(this.wrapped.func_77973_b(), Items.field_185155_bH);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @NotNull
    public String getDisplayName() {
        String strFunc_82833_r = this.wrapped.func_82833_r();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_82833_r, "wrapped.displayName");
        return strFunc_82833_r;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @NotNull
    public String getUnlocalizedName() {
        String strFunc_77977_a = this.wrapped.func_77977_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_77977_a, "wrapped.unlocalizedName");
        return strFunc_77977_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public int getMaxItemUseDuration() {
        return this.wrapped.func_77988_m();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @Nullable
    public INBTTagList getEnchantmentTagList() {
        NBTTagList nBTTagListFunc_77986_q = this.wrapped.func_77986_q();
        if (nBTTagListFunc_77986_q != null) {
            return new NBTTagListImpl(nBTTagListFunc_77986_q);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @Nullable
    public INBTTagCompound getTagCompound() {
        NBTTagCompound nBTTagCompoundFunc_77978_p = this.wrapped.func_77978_p();
        if (nBTTagCompoundFunc_77978_p != null) {
            return new NBTTagCompoundImpl(nBTTagCompoundFunc_77978_p);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public void setTagCompound(@Nullable INBTTagCompound iNBTTagCompound) {
        NBTTagCompound nBTTagCompound;
        ItemStack itemStack = this.wrapped;
        if (iNBTTagCompound == null) {
            nBTTagCompound = null;
        } else {
            if (iNBTTagCompound == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.NBTTagCompoundImpl");
            }
            itemStack = itemStack;
            nBTTagCompound = (NBTTagCompound) ((NBTTagCompoundImpl) iNBTTagCompound).getWrapped();
        }
        itemStack.func_77982_d(nBTTagCompound);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public int getStackSize() {
        return this.wrapped.field_77994_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public int getMaxDamage() {
        return this.wrapped.func_77958_k();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public int getItemDamage() {
        return this.wrapped.func_77952_i();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public void setItemDamage(int i) {
        this.wrapped.func_77964_b(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    @Nullable
    public IItem getItem() {
        Item itemFunc_77973_b = this.wrapped.func_77973_b();
        if (itemFunc_77973_b != null) {
            return new ItemImpl(itemFunc_77973_b);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public long getItemDelay() {
        IMixinItemStack iMixinItemStack = this.wrapped;
        if (iMixinItemStack == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack");
        }
        return iMixinItemStack.getItemDelay();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public int getAnimationsToGo() {
        return this.wrapped.func_190921_D();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
    public void setAnimationsToGo(int i) {
        this.wrapped.func_190915_d(i);
    }
}

package net.ccbluex.liquidbounce.injection.backend;

import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.IWrappedArray;
import net.ccbluex.liquidbounce.api.util.WrappedListArrayAdapter;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0018\u001a\u00020\u000bH\u0016J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002J\n\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0018\u001a\u00020\u000bH\u0016R\u001c\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\tR\u001c\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/InventoryPlayerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "wrapped", "Lnet/minecraft/entity/player/InventoryPlayer;", "(Lnet/minecraft/entity/player/InventoryPlayer;)V", "armorInventory", "Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getArmorInventory", "()Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", PropertyDescriptor.VALUE, "", "currentItem", "getCurrentItem", "()I", "setCurrentItem", "(I)V", "mainInventory", "getMainInventory", "offHandInventory", "getOffHandInventory", "getWrapped", "()Lnet/minecraft/entity/player/InventoryPlayer;", "armorItemInSlot", "slot", "equals", "", "other", "", "getCurrentItemInHand", "getStackInSlot", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/InventoryPlayerImpl.class */
public final class InventoryPlayerImpl implements IInventoryPlayer {

    @NotNull
    private final InventoryPlayer wrapped;

    @NotNull
    public final InventoryPlayer getWrapped() {
        return this.wrapped;
    }

    public InventoryPlayerImpl(@NotNull InventoryPlayer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    @NotNull
    public IWrappedArray getMainInventory() {
        List list = this.wrapped.field_70462_a;
        Intrinsics.checkExpressionValueIsNotNull(list, "wrapped.mainInventory");
        return new WrappedListArrayAdapter(list, new Function1() { // from class: net.ccbluex.liquidbounce.injection.backend.InventoryPlayerImpl$mainInventory$1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((IItemStack) obj);
            }

            @Nullable
            public final ItemStack invoke(@Nullable IItemStack iItemStack) {
                if (iItemStack == null) {
                    return null;
                }
                if (iItemStack == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ItemStackImpl");
                }
                return ((ItemStackImpl) iItemStack).getWrapped();
            }
        }, new Function1() { // from class: net.ccbluex.liquidbounce.injection.backend.InventoryPlayerImpl$mainInventory$2
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((ItemStack) obj);
            }

            @Nullable
            public final IItemStack invoke(ItemStack itemStack) {
                if (itemStack != null) {
                    return new ItemStackImpl(itemStack);
                }
                return null;
            }
        });
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    @NotNull
    public IWrappedArray getArmorInventory() {
        List list = this.wrapped.field_70460_b;
        Intrinsics.checkExpressionValueIsNotNull(list, "wrapped.armorInventory");
        return new WrappedListArrayAdapter(list, new Function1() { // from class: net.ccbluex.liquidbounce.injection.backend.InventoryPlayerImpl$armorInventory$1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((IItemStack) obj);
            }

            @Nullable
            public final ItemStack invoke(@Nullable IItemStack iItemStack) {
                if (iItemStack == null) {
                    return null;
                }
                if (iItemStack == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ItemStackImpl");
                }
                return ((ItemStackImpl) iItemStack).getWrapped();
            }
        }, new Function1() { // from class: net.ccbluex.liquidbounce.injection.backend.InventoryPlayerImpl$armorInventory$2
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((ItemStack) obj);
            }

            @Nullable
            public final IItemStack invoke(ItemStack itemStack) {
                if (itemStack != null) {
                    return new ItemStackImpl(itemStack);
                }
                return null;
            }
        });
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    @NotNull
    public IWrappedArray getOffHandInventory() {
        List list = this.wrapped.field_184439_c;
        Intrinsics.checkExpressionValueIsNotNull(list, "wrapped.offHandInventory");
        return new WrappedListArrayAdapter(list, new Function1() { // from class: net.ccbluex.liquidbounce.injection.backend.InventoryPlayerImpl$offHandInventory$1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((IItemStack) obj);
            }

            @Nullable
            public final ItemStack invoke(@Nullable IItemStack iItemStack) {
                if (iItemStack == null) {
                    return null;
                }
                if (iItemStack == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ItemStackImpl");
                }
                return ((ItemStackImpl) iItemStack).getWrapped();
            }
        }, new Function1() { // from class: net.ccbluex.liquidbounce.injection.backend.InventoryPlayerImpl$offHandInventory$2
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((ItemStack) obj);
            }

            @Nullable
            public final IItemStack invoke(ItemStack itemStack) {
                if (itemStack != null) {
                    return new ItemStackImpl(itemStack);
                }
                return null;
            }
        });
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    public int getCurrentItem() {
        return this.wrapped.field_70461_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    public void setCurrentItem(int i) {
        this.wrapped.field_70461_c = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    @Nullable
    public IItemStack getStackInSlot(int i) {
        ItemStack itemStackFunc_70301_a = this.wrapped.func_70301_a(i);
        if (itemStackFunc_70301_a != null) {
            return new ItemStackImpl(itemStackFunc_70301_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    @Nullable
    public IItemStack armorItemInSlot(int i) {
        ItemStack itemStackFunc_70440_f = this.wrapped.func_70440_f(3 - i);
        if (itemStackFunc_70440_f != null) {
            return new ItemStackImpl(itemStackFunc_70440_f);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer
    @Nullable
    public IItemStack getCurrentItemInHand() {
        ItemStack itemStackFunc_70448_g = this.wrapped.func_70448_g();
        if (itemStackFunc_70448_g != null) {
            return new ItemStackImpl(itemStackFunc_70448_g);
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof InventoryPlayerImpl) && Intrinsics.areEqual(((InventoryPlayerImpl) obj).wrapped, this.wrapped);
    }
}

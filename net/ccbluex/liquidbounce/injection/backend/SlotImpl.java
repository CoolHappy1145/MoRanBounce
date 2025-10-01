package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SlotImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "wrapped", "Lnet/minecraft/inventory/Slot;", "(Lnet/minecraft/inventory/Slot;)V", "hasStack", "", "getHasStack", "()Z", "slotNumber", "", "getSlotNumber", "()I", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getStack", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getWrapped", "()Lnet/minecraft/inventory/Slot;", "equals", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SlotImpl.class */
public final class SlotImpl implements ISlot {

    @NotNull
    private final Slot wrapped;

    @NotNull
    public final Slot getWrapped() {
        return this.wrapped;
    }

    public SlotImpl(@NotNull Slot wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot
    public int getSlotNumber() {
        return this.wrapped.field_75222_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot
    @Nullable
    public IItemStack getStack() {
        ItemStack itemStackFunc_75211_c = this.wrapped.func_75211_c();
        if (itemStackFunc_75211_c != null) {
            return new ItemStackImpl(itemStackFunc_75211_c);
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof SlotImpl) && Intrinsics.areEqual(((SlotImpl) obj).wrapped, this.wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot
    public boolean getHasStack() {
        return this.wrapped.func_75216_d();
    }
}

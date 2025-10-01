package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GuiContainerImpl;", "T", "Lnet/minecraft/client/gui/inventory/GuiContainer;", "Lnet/ccbluex/liquidbounce/injection/backend/GuiScreenImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiContainer;", "wrapped", "(Lnet/minecraft/client/gui/inventory/GuiContainer;)V", "inventorySlots", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "getInventorySlots", "()Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "handleMouseClick", "", "slot", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "slotNumber", "", "clickedButton", "clickType", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GuiContainerImpl.class */
public class GuiContainerImpl extends GuiScreenImpl implements IGuiContainer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiContainerImpl(@NotNull GuiContainer wrapped) {
        super((GuiScreen) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiContainer
    public void handleMouseClick(@NotNull ISlot slot, int i, int i2, int i3) {
        ClickType clickType;
        Intrinsics.checkParameterIsNotNull(slot, "slot");
        IMixinGuiContainer wrapped = getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer");
        }
        IMixinGuiContainer iMixinGuiContainer = wrapped;
        Slot wrapped2 = ((SlotImpl) slot).getWrapped();
        switch (i3) {
            case 0:
                clickType = ClickType.PICKUP;
                break;
            case 1:
                clickType = ClickType.QUICK_MOVE;
                break;
            case 2:
                clickType = ClickType.SWAP;
                break;
            case 3:
                clickType = ClickType.CLONE;
                break;
            case 4:
                clickType = ClickType.THROW;
                break;
            case 5:
                clickType = ClickType.QUICK_CRAFT;
                break;
            case 6:
                clickType = ClickType.PICKUP_ALL;
                break;
            default:
                throw new IllegalArgumentException("Invalid mode " + i3);
        }
        iMixinGuiContainer.publicHandleMouseClick(wrapped2, i, i2, clickType);
    }

    @Nullable
    public IContainer getInventorySlots() {
        Container container = getWrapped().field_147002_h;
        if (container != null) {
            return new ContainerImpl(container);
        }
        return null;
    }
}

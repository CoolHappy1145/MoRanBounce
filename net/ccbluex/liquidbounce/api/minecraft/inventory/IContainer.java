package net.ccbluex.liquidbounce.api.minecraft.inventory;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "", "windowId", "", "getWindowId", "()I", "getSlot", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "id", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/inventory/IContainer.class */
public interface IContainer {
    int getWindowId();

    @NotNull
    ISlot getSlot(int i);
}

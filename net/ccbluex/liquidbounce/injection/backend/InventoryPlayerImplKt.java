package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\u0086\b\u00a8\u0006\u0004"}, m27d2 = {"unwrap", "Lnet/minecraft/entity/player/InventoryPlayer;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "wrap", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/InventoryPlayerImplKt.class */
public final class InventoryPlayerImplKt {
    @NotNull
    public static final InventoryPlayer unwrap(@NotNull IInventoryPlayer unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return ((InventoryPlayerImpl) unwrap).getWrapped();
    }

    @NotNull
    public static final IInventoryPlayer wrap(@NotNull InventoryPlayer wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new InventoryPlayerImpl(wrap);
    }
}

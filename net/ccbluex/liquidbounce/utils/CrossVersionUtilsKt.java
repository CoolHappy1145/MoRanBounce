package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0016\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\t\u0010\ufffd\ufffd\u001a\u00020\u0001H\u0086\b\u001a\u001b\u0010\u0002\u001a\u00020\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\b\u00a8\u0006\u0007"}, m27d2 = {"createOpenInventoryPacket", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "createUseItemPacket", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/CrossVersionUtilsKt.class */
public final class CrossVersionUtilsKt {
    @NotNull
    public static final IPacket createUseItemPacket(@Nullable IItemStack iItemStack, @NotNull WEnumHand hand) {
        Intrinsics.checkParameterIsNotNull(hand, "hand");
        return WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand);
    }

    @NotNull
    public static final IPacket createOpenInventoryPacket() {
        IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
        IEntityPlayerSP thePlayer = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        return classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.OPEN_INVENTORY);
    }
}

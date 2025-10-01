package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketEntityActionImpl;", "T", "Lnet/minecraft/network/play/client/CPacketEntityAction;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketEntityAction;)V", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "getAction", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketEntityActionImpl.class */
public final class CPacketEntityActionImpl extends PacketImpl implements ICPacketEntityAction {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketEntityActionImpl(@NotNull CPacketEntityAction wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction
    @NotNull
    public ICPacketEntityAction.WAction getAction() {
        CPacketEntityAction.Action actionFunc_180764_b = getWrapped().func_180764_b();
        Intrinsics.checkExpressionValueIsNotNull(actionFunc_180764_b, "wrapped.action");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$14[actionFunc_180764_b.ordinal()]) {
            case 1:
                return ICPacketEntityAction.WAction.START_SNEAKING;
            case 2:
                return ICPacketEntityAction.WAction.STOP_SNEAKING;
            case 3:
                return ICPacketEntityAction.WAction.STOP_SLEEPING;
            case 4:
                return ICPacketEntityAction.WAction.START_SPRINTING;
            case 5:
                return ICPacketEntityAction.WAction.STOP_SPRINTING;
            case 6:
                return ICPacketEntityAction.WAction.OPEN_INVENTORY;
            case 7:
                return ICPacketEntityAction.WAction.START_RIDING_JUMP;
            case 8:
                return ICPacketEntityAction.WAction.STOP_RIDING_JUMP;
            case 9:
                return ICPacketEntityAction.WAction.START_FALL_FLYING;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}

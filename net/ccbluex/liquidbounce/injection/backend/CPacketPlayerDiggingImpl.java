package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketPlayerDiggingImpl;", "T", "Lnet/minecraft/network/play/client/CPacketPlayerDigging;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketPlayerDigging;)V", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "getAction", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "position", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getPosition", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketPlayerDiggingImpl.class */
public final class CPacketPlayerDiggingImpl extends PacketImpl implements ICPacketPlayerDigging {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketPlayerDiggingImpl(@NotNull CPacketPlayerDigging wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging
    @NotNull
    public WBlockPos getPosition() {
        BlockPos blockPosFunc_179715_a = getWrapped().func_179715_a();
        Intrinsics.checkExpressionValueIsNotNull(blockPosFunc_179715_a, "wrapped.position");
        int iFunc_177958_n = blockPosFunc_179715_a.func_177958_n();
        BlockPos blockPosFunc_179715_a2 = getWrapped().func_179715_a();
        Intrinsics.checkExpressionValueIsNotNull(blockPosFunc_179715_a2, "wrapped.position");
        int iFunc_177956_o = blockPosFunc_179715_a2.func_177956_o();
        BlockPos blockPosFunc_179715_a3 = getWrapped().func_179715_a();
        Intrinsics.checkExpressionValueIsNotNull(blockPosFunc_179715_a3, "wrapped.position");
        return new WBlockPos(iFunc_177958_n, iFunc_177956_o, blockPosFunc_179715_a3.func_177952_p());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging
    @NotNull
    public IEnumFacing getFacing() {
        EnumFacing enumFacingFunc_179714_b = getWrapped().func_179714_b();
        Intrinsics.checkExpressionValueIsNotNull(enumFacingFunc_179714_b, "wrapped.facing");
        return new EnumFacingImpl(enumFacingFunc_179714_b);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging
    @NotNull
    public ICPacketPlayerDigging.WAction getAction() {
        CPacketPlayerDigging.Action actionFunc_180762_c = getWrapped().func_180762_c();
        Intrinsics.checkExpressionValueIsNotNull(actionFunc_180762_c, "wrapped.action");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$9[actionFunc_180762_c.ordinal()]) {
            case 1:
                return ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK;
            case 2:
                return ICPacketPlayerDigging.WAction.DROP_ALL_ITEMS;
            case 3:
                return ICPacketPlayerDigging.WAction.DROP_ITEM;
            case 4:
                return ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM;
            case 5:
                return ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
            case 6:
                return ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
            case 7:
                return ICPacketPlayerDigging.WAction.SWAP_HELD_ITEMS;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}

package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CPacketUseEntityImpl;", "T", "Lnet/minecraft/network/play/client/CPacketUseEntity;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketUseEntity;)V", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "getAction", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "getEntityFromWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CPacketUseEntityImpl.class */
public final class CPacketUseEntityImpl extends PacketImpl implements ICPacketUseEntity {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CPacketUseEntityImpl(@NotNull CPacketUseEntity wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity
    @NotNull
    public ICPacketUseEntity.WAction getAction() {
        CPacketUseEntity.Action actionFunc_149565_c = getWrapped().func_149565_c();
        Intrinsics.checkExpressionValueIsNotNull(actionFunc_149565_c, "wrapped.action");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$7[actionFunc_149565_c.ordinal()]) {
            case 1:
                return ICPacketUseEntity.WAction.INTERACT;
            case 2:
                return ICPacketUseEntity.WAction.ATTACK;
            case 3:
                return ICPacketUseEntity.WAction.INTERACT_AT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity
    @NotNull
    public IEntity getEntityFromWorld(@NotNull IWorld world) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Entity entityFunc_149564_a = getWrapped().func_149564_a(((WorldImpl) world).getWrapped());
        if (entityFunc_149564_a == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(entityFunc_149564_a, "wrapped.getEntityFromWorld(world.unwrap())!!");
        return new EntityImpl(entityFunc_149564_a);
    }
}

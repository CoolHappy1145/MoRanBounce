package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiFireBall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rotationValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "swingValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AntiFireBall", description = ":/", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AntiFireBall.class */
public final class AntiFireBall extends Module {
    private final ListValue swingValue = new ListValue("Swing", new String[]{"Normal", "Packet", "None"}, "Normal");
    private final BoolValue rotationValue = new BoolValue("Rotation", true);
    private final MSTimer timer = new MSTimer();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        for (IEntity iEntity : theWorld.getLoadedEntityList()) {
            if (MinecraftInstance.classProvider.isEntityFireball(iEntity)) {
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                if (r0.getDistanceToEntity(iEntity) < 5.5d && this.timer.hasTimePassed(300L)) {
                    if (((Boolean) this.rotationValue.get()).booleanValue()) {
                        RotationUtils.setTargetRotation(RotationUtils.getRotationsNonLivingEntity(iEntity));
                    }
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketUseEntity(iEntity, ICPacketUseEntity.WAction.ATTACK));
                    if (Intrinsics.areEqual((String) this.swingValue.get(), "Normal")) {
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer2.swingItem();
                    } else if (Intrinsics.areEqual((String) this.swingValue.get(), "Packet")) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketAnimation());
                    }
                    this.timer.reset();
                    return;
                }
            }
        }
    }
}

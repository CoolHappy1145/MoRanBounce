package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/TeleportCubeCraft;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/TeleportCubeCraft.class */
public final class TeleportCubeCraft extends SpeedMode {
    private final MSTimer timer;

    public TeleportCubeCraft() {
        super("TeleportCubeCraft");
        this.timer = new MSTimer();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MovementUtils.isMoving()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getOnGround() && this.timer.hasTimePassed(300L)) {
                double direction = MovementUtils.getDirection();
                Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
                if (speed == null) {
                    Intrinsics.throwNpe();
                }
                float fFloatValue = ((Number) speed.getCubecraftPortLengthValue().get()).floatValue();
                event.setX((-Math.sin(direction)) * fFloatValue);
                event.setZ(Math.cos(direction) * fFloatValue);
                this.timer.reset();
            }
        }
    }
}

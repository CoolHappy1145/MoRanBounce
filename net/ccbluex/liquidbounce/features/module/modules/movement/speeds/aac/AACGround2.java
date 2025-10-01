package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016\u00a8\u0006\n"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACGround2;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACGround2.class */
public final class AACGround2 extends SpeedMode {
    public AACGround2() {
        super("AACGround2");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (!MovementUtils.isMoving()) {
            return;
        }
        ITimer timer = MinecraftInstance.f157mc.getTimer();
        Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        if (speed == null) {
            Intrinsics.throwNpe();
        }
        timer.setTimerSpeed(((Number) speed.getAacGroundTimerValue().get()).floatValue());
        MovementUtils.strafe(0.02f);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public void onDisable() {
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
    }
}

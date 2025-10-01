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
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/CustomSpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onEnable", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/CustomSpeed.class */
public final class CustomSpeed extends SpeedMode {
    public CustomSpeed() {
        super("Custom");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (MovementUtils.isMoving()) {
            Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
            if (speed != null) {
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(((Number) speed.getCustomTimerValue().get()).floatValue());
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer.getOnGround()) {
                    MovementUtils.strafe(((Number) speed.getCustomSpeedValue().get()).floatValue());
                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer2 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer2.setMotionY(((Number) speed.getCustomYValue().get()).floatValue());
                    return;
                }
                if (!((Boolean) speed.getCustomStrafeValue().get()).booleanValue()) {
                    MovementUtils.strafe$default(0.0f, 1, null);
                    return;
                } else {
                    MovementUtils.strafe(((Number) speed.getCustomSpeedValue().get()).floatValue());
                    return;
                }
            }
            return;
        }
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer3.setMotionZ(0.0d);
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer5 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer4.setMotionX(thePlayer5.getMotionZ());
    }

    public void onEnable() {
        Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        if (speed != null) {
            if (((Boolean) speed.getResetXZValue().get()).booleanValue()) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer.setMotionZ(0.0d);
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer2.setMotionX(thePlayer3.getMotionZ());
            }
            if (((Boolean) speed.getResetYValue().get()).booleanValue()) {
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer4.setMotionY(0.0d);
            }
        }
    }

    public void onDisable() {
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

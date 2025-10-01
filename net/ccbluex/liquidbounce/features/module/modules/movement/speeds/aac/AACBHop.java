package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016\u00a8\u0006\n"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACBHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACBHop.class */
public final class AACBHop extends SpeedMode {
    public AACBHop() {
        super("AACBHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || thePlayer.isInWater()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.08f);
            if (thePlayer.getOnGround()) {
                thePlayer.setMotionY(0.399d);
                float rotationYaw = thePlayer.getRotationYaw() * 0.017453292f;
                thePlayer.setMotionX(thePlayer.getMotionX() - (((float) Math.sin(rotationYaw)) * 0.2f));
                thePlayer.setMotionZ(thePlayer.getMotionZ() + (((float) Math.cos(rotationYaw)) * 0.2f));
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(2.0f);
                return;
            }
            thePlayer.setMotionY(thePlayer.getMotionY() * 0.97d);
            thePlayer.setMotionX(thePlayer.getMotionX() * 1.008d);
            thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.008d);
            return;
        }
        thePlayer.setMotionX(0.0d);
        thePlayer.setMotionZ(0.0d);
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public void onDisable() {
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
    }
}

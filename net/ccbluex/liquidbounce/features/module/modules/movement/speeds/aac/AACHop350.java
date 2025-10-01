package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016J\b\u0010\t\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0007H\u0016\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACHop350;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "handleEvents", "", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACHop350.class */
public final class AACHop350 extends SpeedMode implements Listenable {
    public AACHop350() {
        super("AACHop3.5.0");
        LiquidBounce.INSTANCE.getEventManager().registerListener(this);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && event.getEventState() == EventState.POST && MovementUtils.isMoving() && !thePlayer.isInWater() && !thePlayer.isInLava()) {
            thePlayer.setJumpMovementFactor(thePlayer.getJumpMovementFactor() + 0.00208f);
            if (thePlayer.getFallDistance() <= 1.0f) {
                if (thePlayer.getOnGround()) {
                    thePlayer.jump();
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.0118000507354736d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.0118000507354736d);
                } else {
                    thePlayer.setMotionY(thePlayer.getMotionY() - 0.014700000174343586d);
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.0013799667358398d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.0013799667358398d);
                }
            }
        }
    }

    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && thePlayer.getOnGround()) {
            thePlayer.setMotionZ(0.0d);
            thePlayer.setMotionX(thePlayer.getMotionZ());
        }
    }

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            thePlayer.setJumpMovementFactor(0.02f);
        }
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return isActive();
    }
}

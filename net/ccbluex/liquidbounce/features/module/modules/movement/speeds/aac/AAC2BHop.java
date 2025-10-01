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

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC2BHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC2BHop.class */
public final class AAC2BHop extends SpeedMode {
    public AAC2BHop() {
        super("AAC2BHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isInWater()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            if (thePlayer.getOnGround()) {
                thePlayer.jump();
                thePlayer.setMotionX(thePlayer.getMotionX() * 1.02d);
                thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.02d);
                return;
            } else {
                if (thePlayer.getMotionY() > -0.2d) {
                    thePlayer.setJumpMovementFactor(0.08f);
                    thePlayer.setMotionY(thePlayer.getMotionY() + 0.01431d);
                    thePlayer.setJumpMovementFactor(0.07f);
                    return;
                }
                return;
            }
        }
        thePlayer.setMotionX(0.0d);
        thePlayer.setMotionZ(0.0d);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

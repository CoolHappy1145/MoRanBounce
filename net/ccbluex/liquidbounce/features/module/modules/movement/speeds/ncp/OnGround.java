package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/OnGround;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/OnGround.class */
public final class OnGround extends SpeedMode {
    public OnGround() {
        super("OnGround");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || !MovementUtils.isMoving() || thePlayer.getFallDistance() > 3.994d || thePlayer.isInWater() || thePlayer.isOnLadder() || thePlayer.isCollidedHorizontally()) {
            return;
        }
        thePlayer.setPosY(thePlayer.getPosY() - 0.3993000090122223d);
        thePlayer.setMotionY(-1000.0d);
        thePlayer.setCameraPitch(0.3f);
        thePlayer.setDistanceWalkedModified(44.0f);
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        if (thePlayer.getOnGround()) {
            thePlayer.setPosY(thePlayer.getPosY() + 0.3993000090122223d);
            thePlayer.setMotionY(0.3993000090122223d);
            thePlayer.setDistanceWalkedOnStepModified(44.0f);
            thePlayer.setMotionX(thePlayer.getMotionX() * 1.590000033378601d);
            thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.590000033378601d);
            thePlayer.setCameraPitch(0.0f);
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.199f);
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/Boost;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "ground", "", "motionDelay", "", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "shouldSpeedUp", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/Boost.class */
public final class Boost extends SpeedMode {
    private int motionDelay;
    private float ground;

    public Boost() {
        super("Boost");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            double d = 3.1981d;
            double d2 = 4.69d;
            boolean z = true;
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            if (!theWorld.getCollidingBoundingBoxes(thePlayer, thePlayer.getEntityBoundingBox().offset(thePlayer.getMotionX() / 4.69d, 0.0d, thePlayer.getMotionZ() / 4.69d)).isEmpty()) {
                z = false;
            }
            if (thePlayer.getOnGround() && this.ground < 1.0f) {
                this.ground += 0.2f;
            }
            if (!thePlayer.getOnGround()) {
                this.ground = 0.0f;
            }
            if (this.ground == 1.0f && shouldSpeedUp()) {
                if (!thePlayer.getSprinting()) {
                    d2 = 5.49d;
                }
                if (thePlayer.getMoveStrafing() != 0.0f) {
                    d = 3.0981d;
                    d2 += 0.5d;
                }
                if (thePlayer.isInWater()) {
                    d -= 0.1d;
                }
                this.motionDelay++;
                switch (this.motionDelay) {
                    case 1:
                        thePlayer.setMotionX(thePlayer.getMotionX() * d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * d);
                        break;
                    case 2:
                        thePlayer.setMotionX(thePlayer.getMotionX() / 1.458d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() / 1.458d);
                        break;
                    case 4:
                        if (z) {
                            thePlayer.setPosition(thePlayer.getPosX() + (thePlayer.getMotionX() / d2), thePlayer.getPosY(), thePlayer.getPosZ() + (thePlayer.getMotionZ() / d2));
                        }
                        this.motionDelay = 0;
                        break;
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    private final boolean shouldSpeedUp() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (!thePlayer.isInLava()) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer2.isOnLadder()) {
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                if (!thePlayer3.isSneaking() && MovementUtils.isMoving()) {
                    return true;
                }
            }
        }
        return false;
    }
}

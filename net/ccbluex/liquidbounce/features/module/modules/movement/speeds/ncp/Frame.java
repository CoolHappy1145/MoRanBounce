package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/Frame;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "motionTicks", "", "move", "", "tickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/Frame.class */
public final class Frame extends SpeedMode {
    private int motionTicks;
    private boolean move;
    private final TickTimer tickTimer;

    public Frame() {
        super("Frame");
        this.tickTimer = new TickTimer();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getMovementInput().getMoveForward() <= 0.0f) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer2.getMovementInput().getMoveStrafe() <= 0.0f) {
                return;
            }
        }
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer3.getOnGround()) {
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer4.jump();
            if (this.motionTicks == 1) {
                this.tickTimer.reset();
                if (this.move) {
                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer5 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer5.setMotionX(0.0d);
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer6.setMotionZ(0.0d);
                    this.move = false;
                }
                this.motionTicks = 0;
            } else {
                this.motionTicks = 1;
            }
        } else if (!this.move && this.motionTicks == 1 && this.tickTimer.hasTimePassed(5)) {
            IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer7 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer7.setMotionX(thePlayer7.getMotionX() * 4.25d);
            IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer8 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer8.setMotionZ(thePlayer8.getMotionZ() * 4.25d);
            this.move = true;
        }
        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer9 == null) {
            Intrinsics.throwNpe();
        }
        if (!thePlayer9.getOnGround()) {
            MovementUtils.strafe$default(0.0f, 1, null);
        }
        this.tickTimer.update();
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

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

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/NCPYPort;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "jumps", "", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/NCPYPort.class */
public final class NCPYPort extends SpeedMode {
    private int jumps;

    public NCPYPort() {
        super("NCPYPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isOnLadder()) {
            return;
        }
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer2.isInWater()) {
            return;
        }
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer3.isInLava()) {
            return;
        }
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer4.isInWeb() || !MovementUtils.isMoving()) {
            return;
        }
        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer5 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer5.isInWater()) {
            return;
        }
        if (this.jumps >= 4) {
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer6.getOnGround()) {
                this.jumps = 0;
            }
        }
        IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer7 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer7.getOnGround()) {
            IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer8 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer8.setMotionY(this.jumps <= 1 ? 0.42d : 0.4d);
            IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer9 == null) {
                Intrinsics.throwNpe();
            }
            float rotationYaw = thePlayer9.getRotationYaw() * 0.017453292f;
            IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer10 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer10.setMotionX(thePlayer10.getMotionX() - (((float) Math.sin(rotationYaw)) * 0.2f));
            IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer11 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer11.setMotionZ(thePlayer11.getMotionZ() + (((float) Math.cos(rotationYaw)) * 0.2f));
            this.jumps++;
        } else if (this.jumps <= 1) {
            IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer12 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer12.setMotionY(-5.0d);
        }
        MovementUtils.strafe$default(0.0f, 1, null);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

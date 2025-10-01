package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/spectre/SpectreOnGround;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "speedUp", "", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/spectre/SpectreOnGround.class */
public final class SpectreOnGround extends SpeedMode {
    private int speedUp;

    public SpectreOnGround() {
        super("SpectreOnGround");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MovementUtils.isMoving()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getMovementInput().getJump()) {
                return;
            }
            if (this.speedUp >= 10) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer2.getOnGround()) {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer3.setMotionX(0.0d);
                    IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer4 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer4.setMotionZ(0.0d);
                    this.speedUp = 0;
                    return;
                }
                return;
            }
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer5.getOnGround() && MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().isKeyDown()) {
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                float rotationYaw = thePlayer6.getRotationYaw() * 0.017453292f;
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer7.setMotionX(thePlayer7.getMotionX() - (((float) Math.sin(rotationYaw)) * 0.145f));
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer8.setMotionZ(thePlayer8.getMotionZ() + (((float) Math.cos(rotationYaw)) * 0.145f));
                IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer9 == null) {
                    Intrinsics.throwNpe();
                }
                event.setX(thePlayer9.getMotionX());
                event.setY(0.005d);
                IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer10 == null) {
                    Intrinsics.throwNpe();
                }
                event.setZ(thePlayer10.getMotionZ());
                this.speedUp++;
            }
        }
    }
}

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aquavit/AAC4SlowHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onEnable", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aquavit/AAC4SlowHop.class */
public final class AAC4SlowHop extends SpeedMode {
    public AAC4SlowHop() {
        super("AAC4SlowHop");
    }

    public void onDisable() {
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        thePlayer.setSpeedInAir(0.02f);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isInWater()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer2.getOnGround()) {
                MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().setPressed(false);
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer3.jump();
            }
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer4.getOnGround()) {
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                if (r0.getFallDistance() <= 0.1d) {
                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer5 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer5.setSpeedInAir(0.03f);
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.45f);
                }
            }
            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                Intrinsics.throwNpe();
            }
            if (r0.getFallDistance() > 0.1d) {
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                if (r0.getFallDistance() < 1.3d) {
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer6.setSpeedInAir(0.0105f);
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.7f);
                }
            }
            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                Intrinsics.throwNpe();
            }
            if (r0.getFallDistance() >= 1.3d) {
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer7.setSpeedInAir(0.0105f);
                return;
            }
            return;
        }
        IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer8 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer8.setMotionX(0.0d);
        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer9 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer9.setMotionZ(0.0d);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/HypixelHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/HypixelHop.class */
public final class HypixelHop extends SpeedMode {
    public HypixelHop() {
        super("HypixelHop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (MovementUtils.isMoving()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getOnGround()) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer2.jump();
                float speed = MovementUtils.INSTANCE.getSpeed() < 0.56f ? MovementUtils.INSTANCE.getSpeed() * 1.045f : 0.56f;
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer3.getOnGround()) {
                    IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer4 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer4.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer5 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer5.getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED)) == null) {
                            Intrinsics.throwNpe();
                        }
                        speed *= 1.0f + (0.13f * (1 + r4.getAmplifier()));
                    }
                }
                MovementUtils.strafe(speed);
                return;
            }
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer6.getMotionY() < 0.2d) {
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer7.setMotionY(thePlayer7.getMotionY() - 0.02d);
            }
            MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * 1.01889f);
            return;
        }
        IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer8 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer8.setMotionZ(0.0d);
        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer9 == null) {
            Intrinsics.throwNpe();
        }
        IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer10 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer9.setMotionX(thePlayer10.getMotionZ());
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

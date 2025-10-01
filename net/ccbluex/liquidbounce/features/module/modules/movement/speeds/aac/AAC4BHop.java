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

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u0006H\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC4BHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "legitHop", "", "onDisable", "", "onEnable", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC4BHop.class */
public final class AAC4BHop extends SpeedMode {
    private boolean legitHop;

    public AAC4BHop() {
        super("AAC4BHop");
    }

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        thePlayer.setSpeedInAir(0.02f);
    }

    public void onTick() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (MovementUtils.isMoving()) {
                if (this.legitHop) {
                    if (thePlayer.getOnGround()) {
                        thePlayer.jump();
                        thePlayer.setOnGround(false);
                        this.legitHop = false;
                        return;
                    }
                    return;
                }
                if (thePlayer.getOnGround()) {
                    thePlayer.setOnGround(false);
                    MovementUtils.strafe(0.375f);
                    thePlayer.jump();
                    thePlayer.setMotionY(0.41d);
                    return;
                }
                thePlayer.setSpeedInAir(0.0211f);
                return;
            }
            thePlayer.setMotionX(0.0d);
            thePlayer.setMotionZ(0.0d);
            this.legitHop = true;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public void onEnable() {
        this.legitHop = true;
    }
}

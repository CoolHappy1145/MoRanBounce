package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACPort;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACPort.class */
public final class AACPort extends SpeedMode {
    public AACPort() {
        super("AACPort");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || !MovementUtils.isMoving()) {
            return;
        }
        float rotationYaw = thePlayer.getRotationYaw() * 0.017453292f;
        double d = 0.2d;
        while (true) {
            double d2 = d;
            Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
            if (speed == null) {
                Intrinsics.throwNpe();
            }
            if (d2 <= ((Number) speed.getPortMax().get()).doubleValue()) {
                double posX = thePlayer.getPosX() - (((float) Math.sin(rotationYaw)) * d2);
                double posZ = thePlayer.getPosZ() + (((float) Math.cos(rotationYaw)) * d2);
                if (thePlayer.getPosY() >= ((int) thePlayer.getPosY()) + 0.5d || MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(posX, thePlayer.getPosY(), posZ)))) {
                    thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, thePlayer.getPosY(), posZ, true));
                    d = d2 + 0.2d;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }
}

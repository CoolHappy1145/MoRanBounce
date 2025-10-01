package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/MineplexGround;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "speed", "", "spoofSlot", "", "onDisable", "", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/MineplexGround.class */
public final class MineplexGround extends SpeedMode {
    private boolean spoofSlot;
    private float speed;

    public MineplexGround() {
        super("MineplexGround");
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
                if (thePlayer2.getInventory().getCurrentItemInHand() != null) {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer3.isUsingItem()) {
                        return;
                    }
                    this.spoofSlot = false;
                    for (int i = 36; i <= 44; i++) {
                        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer4 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer4.getInventory().getStackInSlot(i) == null) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(i - 36));
                            this.spoofSlot = true;
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
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
                if (!thePlayer2.isUsingItem()) {
                    if (!this.spoofSlot) {
                        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer3 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer3.getInventory().getCurrentItemInHand() != null) {
                            ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lMineplex\u00a7aSpeed\u00a78] \u00a7cYou need one empty slot.");
                            return;
                        }
                    }
                    IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer4 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX = thePlayer4.getPosX();
                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer5 == null) {
                        Intrinsics.throwNpe();
                    }
                    double minY = thePlayer5.getEntityBoundingBox().getMinY() - 1.0d;
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    WBlockPos wBlockPos = new WBlockPos(posX, minY, thePlayer6.getPosZ());
                    WVec3 wVec3 = new WVec3(wBlockPos);
                    WVec3 wVec32 = new WVec3(wVec3.getXCoord() + 0.4d, wVec3.getYCoord() + 0.4d, wVec3.getZCoord() + 0.4d);
                    WVec3 wVec33 = new WVec3(MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP).getDirectionVec());
                    WVec3 wVec34 = new WVec3(wVec32.getXCoord() + wVec33.getXCoord(), wVec32.getYCoord() + wVec33.getYCoord(), wVec32.getZCoord() + wVec33.getZCoord());
                    IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                    IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer7 == null) {
                        Intrinsics.throwNpe();
                    }
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    playerController.onPlayerRightClick(thePlayer7, theWorld, null, wBlockPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP), new WVec3(wVec34.getXCoord() * 0.4000000059604645d, wVec34.getYCoord() * 0.4000000059604645d, wVec34.getZCoord() * 0.4000000059604645d));
                    Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
                    if (speed == null) {
                        Intrinsics.throwNpe();
                    }
                    float fFloatValue = ((Number) speed.getMineplexGroundSpeedValue().get()).floatValue();
                    if (fFloatValue > this.speed) {
                        this.speed += fFloatValue / 8.0f;
                    }
                    if (this.speed >= fFloatValue) {
                        this.speed = fFloatValue;
                    }
                    MovementUtils.strafe(this.speed);
                    if (!this.spoofSlot) {
                        IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider iClassProvider = MinecraftInstance.classProvider;
                        IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer8 == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler.addToSendQueue(iClassProvider.createCPacketHeldItemChange(thePlayer8.getInventory().getCurrentItem()));
                        return;
                    }
                    return;
                }
            }
        }
        this.speed = 0.0f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public void onDisable() {
        this.speed = 0.0f;
        IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        netHandler.addToSendQueue(iClassProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
    }
}

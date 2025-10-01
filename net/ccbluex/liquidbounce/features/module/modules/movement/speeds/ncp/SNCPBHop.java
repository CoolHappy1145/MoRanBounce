package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovementInput;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/SNCPBHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "baseMoveSpeed", "", "getBaseMoveSpeed", "()D", "lastDist", "level", "", "moveSpeed", "timerDelay", "onDisable", "", "onEnable", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "round", PropertyDescriptor.VALUE, LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/SNCPBHop.class */
public final class SNCPBHop extends SpeedMode {
    private int level;
    private double moveSpeed;
    private double lastDist;
    private int timerDelay;

    public SNCPBHop() {
        super("SNCPBHop");
        this.level = 1;
        this.moveSpeed = 0.2873d;
    }

    public void onEnable() {
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        this.lastDist = 0.0d;
        this.moveSpeed = 0.0d;
        this.level = 4;
    }

    public void onDisable() {
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        this.moveSpeed = getBaseMoveSpeed();
        this.level = 0;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        double posX = thePlayer.getPosX();
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double prevPosX = posX - thePlayer2.getPrevPosX();
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        double posZ = thePlayer3.getPosZ();
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        double prevPosZ = posZ - thePlayer4.getPrevPosZ();
        this.lastDist = Math.sqrt((prevPosX * prevPosX) + (prevPosZ * prevPosZ));
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0299  */
    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.timerDelay++;
        int i = this.timerDelay;
        this.timerDelay %= 5;
        if (this.timerDelay != 0) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        } else {
            if (MovementUtils.isMoving()) {
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(32767.0f);
            }
            if (MovementUtils.isMoving()) {
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.3f);
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer.setMotionX(thePlayer.getMotionX() * 1.0199999809265137d);
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer2.setMotionZ(thePlayer2.getMotionZ() * 1.0199999809265137d);
            }
        }
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer3.getOnGround() && MovementUtils.isMoving()) {
            this.level = 2;
        }
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        double posY = thePlayer4.getPosY();
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            Intrinsics.throwNpe();
        }
        if (round(posY - ((int) r2.getPosY())) == round(0.138d)) {
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer5.setMotionY(thePlayer5.getMotionY() - 0.08d);
            event.setY(event.getY() - 0.09316090325960147d);
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer6.setPosY(thePlayer6.getPosY() - 0.09316090325960147d);
        }
        if (this.level == 1) {
            IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer7 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer7.getMoveForward() == 0.0f) {
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer8.getMoveStrafing() == 0.0f) {
                    if (this.level == 2) {
                        this.level = 3;
                        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer9 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer9.setMotionY(0.399399995803833d);
                        event.setY(0.399399995803833d);
                        this.moveSpeed *= 2.149d;
                    } else if (this.level == 3) {
                        this.level = 4;
                        this.moveSpeed = this.lastDist - (0.66d * (this.lastDist - getBaseMoveSpeed()));
                    } else if (this.level == 88) {
                        this.moveSpeed = getBaseMoveSpeed();
                        this.lastDist = 0.0d;
                        this.level = 89;
                    } else {
                        if (this.level == 89) {
                            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                            if (theWorld == null) {
                                Intrinsics.throwNpe();
                            }
                            IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer10 == null) {
                                Intrinsics.throwNpe();
                            }
                            IEntityPlayerSP iEntityPlayerSP = thePlayer10;
                            IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer11 == null) {
                                Intrinsics.throwNpe();
                            }
                            IAxisAlignedBB entityBoundingBox = thePlayer11.getEntityBoundingBox();
                            IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer12 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (!(!theWorld.getCollidingBoundingBoxes(iEntityPlayerSP, entityBoundingBox.offset(0.0d, thePlayer12.getMotionY(), 0.0d)).isEmpty())) {
                                IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer13 == null) {
                                    Intrinsics.throwNpe();
                                }
                                if (thePlayer13.isCollidedVertically()) {
                                    this.level = 1;
                                }
                            }
                            this.lastDist = 0.0d;
                            this.moveSpeed = getBaseMoveSpeed();
                            return;
                        }
                        IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld2 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer14 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP iEntityPlayerSP2 = thePlayer14;
                        IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer15 == null) {
                            Intrinsics.throwNpe();
                        }
                        IAxisAlignedBB entityBoundingBox2 = thePlayer15.getEntityBoundingBox();
                        IEntityPlayerSP thePlayer16 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer16 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (!(!theWorld2.getCollidingBoundingBoxes(iEntityPlayerSP2, entityBoundingBox2.offset(0.0d, thePlayer16.getMotionY(), 0.0d)).isEmpty())) {
                            IEntityPlayerSP thePlayer17 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer17 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (!thePlayer17.isCollidedVertically()) {
                                this.moveSpeed = this.lastDist - (this.lastDist / 159.0d);
                            }
                        }
                        this.moveSpeed = getBaseMoveSpeed();
                        this.lastDist = 0.0d;
                        this.level = 88;
                        return;
                    }
                }
            }
            this.level = 2;
            this.moveSpeed = (1.35d * getBaseMoveSpeed()) - 0.01d;
        }
        this.moveSpeed = RangesKt.coerceAtLeast(this.moveSpeed, getBaseMoveSpeed());
        IEntityPlayerSP thePlayer18 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer18 == null) {
            Intrinsics.throwNpe();
        }
        IMovementInput movementInput = thePlayer18.getMovementInput();
        float moveForward = movementInput.getMoveForward();
        float moveStrafe = movementInput.getMoveStrafe();
        IEntityPlayerSP thePlayer19 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer19 == null) {
            Intrinsics.throwNpe();
        }
        float rotationYaw = thePlayer19.getRotationYaw();
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            event.setX(0.0d);
            event.setZ(0.0d);
        } else if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += moveForward > 0.0f ? -45 : 45;
                moveStrafe = 0.0f;
            } else if (moveStrafe <= -1.0f) {
                rotationYaw += moveForward > 0.0f ? 45 : -45;
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            } else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        double dCos = Math.cos(Math.toRadians(rotationYaw + 90.0d));
        double dSin = Math.sin(Math.toRadians(rotationYaw + 90.0d));
        event.setX((moveForward * this.moveSpeed * dCos) + (moveStrafe * this.moveSpeed * dSin));
        event.setZ(((moveForward * this.moveSpeed) * dSin) - ((moveStrafe * this.moveSpeed) * dCos));
        IEntityPlayerSP thePlayer20 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer20 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer20.setStepHeight(0.6f);
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            event.setX(0.0d);
            event.setZ(0.0d);
        }
    }

    private final double getBaseMoveSpeed() {
        double amplifier = 0.2873d;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer2.getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED)) == null) {
                Intrinsics.throwNpe();
            }
            amplifier = 0.2873d * (1.0d + (0.2d * (r3.getAmplifier() + 1)));
        }
        return amplifier;
    }

    private final double round(double d) {
        BigDecimal scale = new BigDecimal(d).setScale(3, RoundingMode.HALF_UP);
        Intrinsics.checkExpressionValueIsNotNull(scale, "bigDecimal.setScale(3, RoundingMode.HALF_UP)");
        return scale.doubleValue();
    }
}

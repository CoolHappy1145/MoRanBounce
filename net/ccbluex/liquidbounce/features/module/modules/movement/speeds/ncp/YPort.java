package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0016J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/YPort;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "baseMoveSpeed", "", "getBaseMoveSpeed", "()D", "lastDist", "level", "", "moveSpeed", "safeJump", "", "timerDelay", "getBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "offset", "axisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "onMotion", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "round", PropertyDescriptor.VALUE, LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/YPort.class */
public final class YPort extends SpeedMode {
    private double moveSpeed;
    private int level;
    private double lastDist;
    private int timerDelay;
    private boolean safeJump;

    public YPort() {
        super("YPort");
        this.moveSpeed = 0.2873d;
        this.level = 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0143  */
    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMotion() {
        if (!this.safeJump && !MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer.isOnLadder()) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (!thePlayer2.isInsideOfMaterial(MinecraftInstance.classProvider.getMaterialEnum(MaterialType.WATER))) {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (!thePlayer3.isInsideOfMaterial(MinecraftInstance.classProvider.getMaterialEnum(MaterialType.LAVA))) {
                        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer4 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (!thePlayer4.isInWater()) {
                            if (MinecraftInstance.classProvider.isBlockAir(getBlock(-1.1d)) || MinecraftInstance.classProvider.isBlockAir(getBlock(-1.1d))) {
                                if (!MinecraftInstance.classProvider.isBlockAir(getBlock(-0.1d))) {
                                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer5 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    if (thePlayer5.getMotionX() != 0.0d) {
                                        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                                        if (thePlayer6 == null) {
                                            Intrinsics.throwNpe();
                                        }
                                        if (thePlayer6.getMotionZ() != 0.0d) {
                                            IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                                            if (thePlayer7 == null) {
                                                Intrinsics.throwNpe();
                                            }
                                            if (!thePlayer7.getOnGround()) {
                                                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                                                if (thePlayer8 == null) {
                                                    Intrinsics.throwNpe();
                                                }
                                                if (thePlayer8.getFallDistance() < 3.0f) {
                                                    if (MinecraftInstance.f157mc.getThePlayer() == null) {
                                                        Intrinsics.throwNpe();
                                                    }
                                                    if (r0.getFallDistance() > 0.05d) {
                                                        if (this.level == 3) {
                                                            IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                                                            if (thePlayer9 == null) {
                                                                Intrinsics.throwNpe();
                                                            }
                                                            thePlayer9.setMotionY(-0.3994d);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer10 == null) {
            Intrinsics.throwNpe();
        }
        double posX = thePlayer10.getPosX();
        IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer11 == null) {
            Intrinsics.throwNpe();
        }
        double prevPosX = posX - thePlayer11.getPrevPosX();
        IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer12 == null) {
            Intrinsics.throwNpe();
        }
        double posZ = thePlayer12.getPosZ();
        IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer13 == null) {
            Intrinsics.throwNpe();
        }
        double prevPosZ = posZ - thePlayer13.getPrevPosZ();
        this.lastDist = Math.sqrt((prevPosX * prevPosX) + (prevPosZ * prevPosZ));
        if (!MovementUtils.isMoving()) {
            this.safeJump = true;
            return;
        }
        IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer14 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer14.getOnGround()) {
            this.safeJump = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x026d  */
    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.timerDelay++;
        this.timerDelay %= 5;
        if (this.timerDelay != 0) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        } else {
            if (MovementUtils.INSTANCE.hasMotion()) {
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(32767.0f);
            }
            if (MovementUtils.INSTANCE.hasMotion()) {
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
        if (thePlayer3.getOnGround() && MovementUtils.INSTANCE.hasMotion()) {
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
                    } else {
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
                        if (theWorld.getCollidingBoundingBoxes(iEntityPlayerSP, entityBoundingBox.offset(0.0d, thePlayer12.getMotionY(), 0.0d)).size() <= 0) {
                            IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer13 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer13.isCollidedVertically()) {
                                this.level = 1;
                            }
                            this.moveSpeed = this.lastDist - (this.lastDist / 159.0d);
                        }
                    }
                }
            }
            this.level = 2;
            this.moveSpeed = (1.38d * getBaseMoveSpeed()) - 0.01d;
        }
        this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
        IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer14 == null) {
            Intrinsics.throwNpe();
        }
        float moveForward = thePlayer14.getMovementInput().getMoveForward();
        IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer15 == null) {
            Intrinsics.throwNpe();
        }
        float moveStrafe = thePlayer15.getMovementInput().getMoveStrafe();
        IEntityPlayerSP thePlayer16 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer16 == null) {
            Intrinsics.throwNpe();
        }
        float rotationYaw = thePlayer16.getRotationYaw();
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
        IEntityPlayerSP thePlayer17 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer17 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer17.setStepHeight(0.6f);
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
            amplifier = 0.2873d * (1.0d + (0.2d * (r0.getAmplifier() + 1)));
        }
        return amplifier;
    }

    private final IBlock getBlock(IAxisAlignedBB iAxisAlignedBB) {
        int iFloor = ((int) Math.floor(iAxisAlignedBB.getMaxX())) + 1;
        for (int iFloor2 = (int) Math.floor(iAxisAlignedBB.getMinX()); iFloor2 < iFloor; iFloor2++) {
            int iFloor3 = ((int) Math.floor(iAxisAlignedBB.getMaxZ())) + 1;
            for (int iFloor4 = (int) Math.floor(iAxisAlignedBB.getMinZ()); iFloor4 < iFloor3; iFloor4++) {
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                IBlock block = theWorld.getBlockState(new WBlockPos(iFloor2, (int) iAxisAlignedBB.getMinY(), iFloor4)).getBlock();
                if (block != null) {
                    return block;
                }
            }
        }
        return null;
    }

    private final IBlock getBlock(double d) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        return getBlock(thePlayer.getEntityBoundingBox().offset(0.0d, d, 0.0d));
    }

    private final double round(double d) {
        BigDecimal scale = new BigDecimal(d).setScale(3, RoundingMode.HALF_UP);
        Intrinsics.checkExpressionValueIsNotNull(scale, "bd.setScale(3, RoundingMode.HALF_UP)");
        return scale.doubleValue();
    }
}

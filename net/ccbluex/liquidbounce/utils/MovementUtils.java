package net.ccbluex.liquidbounce.utils;

import java.math.BigDecimal;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.utils.Skid.MotionData;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0004H\u0007J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\tJ\b\u0010\u0017\u001a\u00020\tH\u0007J\u000e\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0004J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u0004J\u0010\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\rH\u0007R\u001a\u0010\u0003\u001a\u00020\u00048FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\t8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u0002\u001a\u0004\b\b\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/MovementUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "direction", "", "direction$annotations", "getDirection", "()D", "isMoving", "", "isMoving$annotations", "()Z", "speed", "", "getSpeed", "()F", "forward", "", "length", "getBlockSpeed", "entityIn", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "hasMotion", "isBlockUnder", "isOnGround", "height", "setMotion", "setPosition", "posAndMotion", "Lnet/ccbluex/liquidbounce/utils/Skid/MotionData;", "strafe", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/MovementUtils.class */
public final class MovementUtils extends MinecraftInstance {
    public static final MovementUtils INSTANCE = new MovementUtils();

    @JvmStatic
    @JvmOverloads
    public static final void strafe() {
        strafe$default(0.0f, 1, null);
    }

    private MovementUtils() {
    }

    public final float getSpeed() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        double motionX = thePlayer.getMotionX();
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double motionX2 = motionX * thePlayer2.getMotionX();
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        double motionZ = thePlayer3.getMotionZ();
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        return (float) Math.sqrt(motionX2 + (motionZ * thePlayer4.getMotionZ()));
    }

    public static final boolean isMoving() {
        if (MinecraftInstance.f157mc.getThePlayer() != null) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getMovementInput().getMoveForward() == 0.0f) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer2.getMovementInput().getMoveStrafe() != 0.0f) {
                }
            }
            return true;
        }
        return false;
    }

    public final boolean hasMotion() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getMotionX() != 0.0d) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer2.getMotionZ() != 0.0d) {
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer3.getMotionY() != 0.0d) {
                    return true;
                }
            }
        }
        return false;
    }

    public final double getBlockSpeed(@NotNull IEntityLivingBase entityIn) {
        Intrinsics.checkParameterIsNotNull(entityIn, "entityIn");
        return BigDecimal.valueOf(Math.sqrt(Math.pow(entityIn.getPosX() - entityIn.getPrevPosX(), 2.0d) + Math.pow(entityIn.getPosZ() - entityIn.getPrevPosZ(), 2.0d)) * 20.0d).setScale(1, 4).doubleValue();
    }

    public final boolean isOnGround(double d) {
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IEntityPlayerSP iEntityPlayerSP = thePlayer;
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        return !theWorld.getCollidingBoundingBoxes(iEntityPlayerSP, thePlayer2.getEntityBoundingBox().offset(0.0d, -d, 0.0d)).isEmpty();
    }

    public static void strafe$default(float f, int i, Object obj) {
        if ((i & 1) != 0) {
            f = INSTANCE.getSpeed();
        }
        strafe(f);
    }

    @JvmStatic
    @JvmOverloads
    public static final void strafe(float f) {
        if (isMoving()) {
            double direction = getDirection();
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            thePlayer.setMotionX((-Math.sin(direction)) * f);
            thePlayer.setMotionZ(Math.cos(direction) * f);
        }
    }

    @JvmStatic
    public static final boolean isBlockUnder() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getPosY() < 0.0d) {
            return false;
        }
        double d = 0.0d;
        while (true) {
            double d2 = d;
            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                Intrinsics.throwNpe();
            }
            if (d2 < ((int) r1.getPosY()) + 2) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                IAxisAlignedBB iAxisAlignedBBOffset = thePlayer2.getEntityBoundingBox().offset(0.0d, -d2, 0.0d);
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                if (!theWorld.getCollidingBoundingBoxes(thePlayer3, iAxisAlignedBBOffset).isEmpty()) {
                    return true;
                }
                d = d2 + 2.0d;
            } else {
                return false;
            }
        }
    }

    @JvmStatic
    public static final void setPosition(@NotNull MotionData posAndMotion) {
        Intrinsics.checkParameterIsNotNull(posAndMotion, "posAndMotion");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        thePlayer.setPosition(posAndMotion.f167x, posAndMotion.f168y, posAndMotion.f169z);
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer2.setMotionX(posAndMotion.motionX);
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer3.setMotionY(posAndMotion.motionY);
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer4.setMotionZ(posAndMotion.motionZ);
    }

    public final void setMotion(double d) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        double moveForward = thePlayer.getMovementInput().getMoveForward();
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double moveStrafe = thePlayer2.getMovementInput().getMoveStrafe();
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        float rotationYaw = thePlayer3.getRotationYaw();
        if (moveForward == 0.0d && moveStrafe == 0.0d) {
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer4.setMotionX(0.0d);
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer5.setMotionZ(0.0d);
            return;
        }
        if (moveForward != 0.0d) {
            if (moveStrafe > 0.0d) {
                rotationYaw += moveForward > 0.0d ? -45 : 45;
            } else if (moveStrafe < 0.0d) {
                rotationYaw += moveForward > 0.0d ? 45 : -45;
            }
            moveStrafe = 0.0d;
            if (moveForward > 0.0d) {
                moveForward = 1.0d;
            } else if (moveForward < 0.0d) {
                moveForward = -1.0d;
            }
        }
        double dCos = Math.cos(Math.toRadians(rotationYaw + 90.0d));
        double dSin = Math.sin(Math.toRadians(rotationYaw + 90.0d));
        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer6 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer6.setMotionX((moveForward * d * dCos) + (moveStrafe * d * dSin));
        IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer7 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer7.setMotionZ(((moveForward * d) * dSin) - ((moveStrafe * d) * dCos));
    }

    @JvmStatic
    public static final void forward(double d) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        double radians = Math.toRadians(thePlayer.getRotationYaw());
        thePlayer.setPosition(thePlayer.getPosX() + ((-Math.sin(radians)) * d), thePlayer.getPosY(), thePlayer.getPosZ() + (Math.cos(radians) * d));
    }

    public static final double getDirection() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        float rotationYaw = thePlayer.getRotationYaw();
        if (thePlayer.getMoveForward() < 0.0f) {
            rotationYaw += 180.0f;
        }
        float f = 1.0f;
        if (thePlayer.getMoveForward() < 0.0f) {
            f = -0.5f;
        } else if (thePlayer.getMoveForward() > 0.0f) {
            f = 0.5f;
        }
        if (thePlayer.getMoveStrafing() > 0.0f) {
            rotationYaw -= 90.0f * f;
        }
        if (thePlayer.getMoveStrafing() < 0.0f) {
            rotationYaw += 90.0f * f;
        }
        return Math.toRadians(rotationYaw);
    }
}

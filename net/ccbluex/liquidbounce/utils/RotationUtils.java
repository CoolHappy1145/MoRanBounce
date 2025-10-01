package net.ccbluex.liquidbounce.utils;

import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/RotationUtils.class */
public final class RotationUtils extends MinecraftInstance implements Listenable {
    private static int keepLength;
    private static int revTick;
    public static Rotation targetRotation;
    private static final Random random = new Random();
    public static Rotation serverRotation = new Rotation(0.0f, 0.0f);
    public static boolean keepCurrentRotation = false;

    /* renamed from: x */
    private static double f158x = random.nextDouble();

    /* renamed from: y */
    private static double f159y = random.nextDouble();

    /* renamed from: z */
    private static double f160z = random.nextDouble();

    public static VecRotation faceBlock(WBlockPos wBlockPos) {
        if (wBlockPos == null) {
            return null;
        }
        VecRotation vecRotation = null;
        double d = 0.1d;
        while (true) {
            double d2 = d;
            if (d2 < 0.9d) {
                double d3 = 0.1d;
                while (true) {
                    double d4 = d3;
                    if (d4 < 0.9d) {
                        double d5 = 0.1d;
                        while (true) {
                            double d6 = d5;
                            if (d6 < 0.9d) {
                                WVec3 wVec3 = new WVec3(f157mc.getThePlayer().getPosX(), f157mc.getThePlayer().getEntityBoundingBox().getMinY() + f157mc.getThePlayer().getEyeHeight(), f157mc.getThePlayer().getPosZ());
                                WVec3 wVec3AddVector = new WVec3(wBlockPos).addVector(d2, d4, d6);
                                double dDistanceTo = wVec3.distanceTo(wVec3AddVector);
                                double xCoord = wVec3AddVector.getXCoord() - wVec3.getXCoord();
                                double yCoord = wVec3AddVector.getYCoord() - wVec3.getYCoord();
                                double zCoord = wVec3AddVector.getZCoord() - wVec3.getZCoord();
                                double dSqrt = Math.sqrt((xCoord * xCoord) + (zCoord * zCoord));
                                float degrees = (((float) Math.toDegrees(Math.atan2(zCoord, xCoord))) - 90.0f) % 360.0f;
                                if (degrees >= 180.0f) {
                                    degrees -= 360.0f;
                                }
                                if (degrees < -180.0f) {
                                    degrees += 360.0f;
                                }
                                float f = degrees;
                                float f2 = ((float) (-Math.toDegrees(Math.atan2(yCoord, dSqrt)))) % 360.0f;
                                if (f2 >= 180.0f) {
                                    f2 -= 360.0f;
                                }
                                if (f2 < -180.0f) {
                                    f2 += 360.0f;
                                }
                                Rotation rotation = new Rotation(f, f2);
                                WVec3 vectorForRotation = getVectorForRotation(rotation);
                                IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = f157mc.getTheWorld().rayTraceBlocks(wVec3, wVec3.addVector(vectorForRotation.getXCoord() * dDistanceTo, vectorForRotation.getYCoord() * dDistanceTo, vectorForRotation.getZCoord() * dDistanceTo), false, false, true);
                                if (iMovingObjectPositionRayTraceBlocks != null && iMovingObjectPositionRayTraceBlocks.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK) {
                                    VecRotation vecRotation2 = new VecRotation(wVec3AddVector, rotation);
                                    if (vecRotation == null || getRotationDifference(vecRotation2.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                                        vecRotation = vecRotation2;
                                    }
                                }
                                d5 = d6 + 0.1d;
                            }
                        }
                        d3 = d4 + 0.1d;
                    }
                }
                d = d2 + 0.1d;
            } else {
                return vecRotation;
            }
        }
    }

    public static void setTargetRotationReverse(Rotation rotation, int i, int i2) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        rotation.fixedSensitivity(f157mc.getGameSettings().getMouseSensitivity());
        targetRotation = rotation;
        keepLength = i;
        revTick = i2 + 1;
    }

    public static VecRotation calculateCenter(String str, String str2, double d, IAxisAlignedBB iAxisAlignedBB, boolean z, boolean z2) {
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;
        double d8;
        double d9;
        double d10;
        double d11;
        double d12;
        double d13;
        double d14;
        double d15;
        double d16;
        WVec3 wVec3;
        VecRotation vecRotation = null;
        d2 = 0.15d;
        d3 = 0.85d;
        d4 = 0.1d;
        d5 = 0.15d;
        d6 = 1.0d;
        d7 = 0.1d;
        d8 = 0.15d;
        d9 = 0.85d;
        d10 = 0.1d;
        WVec3 wVec32 = null;
        switch (str) {
            case "LiquidBounce":
                d2 = 0.15d;
                d3 = 0.85d;
                d4 = 0.1d;
                d5 = 0.15d;
                d6 = 1.0d;
                d7 = 0.1d;
                d8 = 0.15d;
                d9 = 0.85d;
                d10 = 0.1d;
                break;
            case "Full":
                d2 = 0.0d;
                d3 = 1.0d;
                d4 = 0.1d;
                d5 = 0.0d;
                d6 = 1.0d;
                d7 = 0.1d;
                d8 = 0.0d;
                d9 = 1.0d;
                d10 = 0.1d;
                break;
            case "HalfUp":
                d2 = 0.1d;
                d3 = 0.9d;
                d4 = 0.1d;
                d5 = 0.5d;
                d6 = 0.9d;
                d7 = 0.1d;
                d8 = 0.1d;
                d9 = 0.9d;
                d10 = 0.1d;
                break;
            case "HalfDown":
                d2 = 0.1d;
                d3 = 0.9d;
                d4 = 0.1d;
                d5 = 0.1d;
                d6 = 0.5d;
                d7 = 0.1d;
                d8 = 0.1d;
                d9 = 0.9d;
                d10 = 0.1d;
                break;
            case "CenterSimple":
                d2 = 0.45d;
                d3 = 0.55d;
                d4 = 0.0125d;
                d5 = 0.65d;
                d6 = 0.75d;
                d7 = 0.0125d;
                d8 = 0.45d;
                d9 = 0.55d;
                d10 = 0.0125d;
                break;
            case "CenterLine":
                d2 = 0.45d;
                d3 = 0.55d;
                d4 = 0.0125d;
                d5 = 0.1d;
                d6 = 0.9d;
                d7 = 0.1d;
                d8 = 0.45d;
                d9 = 0.55d;
                d10 = 0.0125d;
                break;
        }
        double d17 = d2;
        while (true) {
            double d18 = d17;
            if (d18 < d3) {
                double d19 = d5;
                while (true) {
                    double d20 = d19;
                    if (d20 < d6) {
                        double d21 = d8;
                        while (true) {
                            double d22 = d21;
                            if (d22 < d9) {
                                WVec3 wVec33 = new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * d18), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * d20), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * d22));
                                Rotation rotation = toRotation(wVec33, z);
                                if (z2 || isVisible(wVec33)) {
                                    VecRotation vecRotation2 = new VecRotation(wVec33, rotation);
                                    if (vecRotation == null || getRotationDifference(vecRotation2.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                                        vecRotation = vecRotation2;
                                        wVec32 = wVec33;
                                    }
                                }
                                d21 = d22 + d10;
                            }
                        }
                        d19 = d20 + d7;
                    }
                }
                d17 = d18 + d4;
            } else {
                if (vecRotation == null || str2 == "Off") {
                    return vecRotation;
                }
                double dNextDouble = random.nextDouble();
                double dNextDouble2 = random.nextDouble();
                double dNextDouble3 = random.nextDouble();
                double maxX = iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX();
                double maxY = iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY();
                double maxZ = iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ();
                double d23 = 999999.0d;
                if (maxX <= 999999.0d) {
                    d23 = maxX;
                }
                if (maxY <= d23) {
                    d23 = maxY;
                }
                if (maxZ <= d23) {
                    d23 = maxZ;
                }
                d11 = dNextDouble * d23 * d;
                d12 = dNextDouble2 * d23 * d;
                d13 = dNextDouble3 * d23 * d;
                d14 = (d23 * d) / maxX;
                d15 = (d23 * d) / maxY;
                d16 = (d23 * d) / maxZ;
                wVec3 = new WVec3((wVec32.getXCoord() - (d14 * (wVec32.getXCoord() - iAxisAlignedBB.getMinX()))) + d11, (wVec32.getYCoord() - (d15 * (wVec32.getYCoord() - iAxisAlignedBB.getMinY()))) + d12, (wVec32.getZCoord() - (d16 * (wVec32.getZCoord() - iAxisAlignedBB.getMinZ()))) + d13);
                switch (str2) {
                    case "Horizonal":
                        wVec3 = new WVec3((wVec32.getXCoord() - (d14 * (wVec32.getXCoord() - iAxisAlignedBB.getMinX()))) + d11, wVec32.getYCoord(), (wVec32.getZCoord() - (d16 * (wVec32.getZCoord() - iAxisAlignedBB.getMinZ()))) + d13);
                        break;
                    case "Vertical":
                        wVec3 = new WVec3(wVec32.getXCoord(), (wVec32.getYCoord() - (d15 * (wVec32.getYCoord() - iAxisAlignedBB.getMinY()))) + d12, wVec32.getZCoord());
                        break;
                }
                return new VecRotation(wVec3, toRotation(wVec3, z));
            }
        }
    }

    public static Rotation OtherRotation(IAxisAlignedBB iAxisAlignedBB, WVec3 wVec3, boolean z, boolean z2, float f) {
        WVec3 wVec32 = new WVec3(f157mc.getThePlayer().getPosX(), f157mc.getThePlayer().getEntityBoundingBox().getMinY() + f157mc.getThePlayer().getEyeHeight(), f157mc.getThePlayer().getPosZ());
        WVec3 positionEyes = f157mc.getThePlayer().getPositionEyes(1.0f);
        VecRotation vecRotation = null;
        double d = 0.15d;
        while (true) {
            double d2 = d;
            if (d2 >= 0.85d) {
                break;
            }
            double d3 = 0.15d;
            while (true) {
                double d4 = d3;
                if (d4 < 1.0d) {
                    double d5 = 0.15d;
                    while (true) {
                        double d6 = d5;
                        if (d6 < 0.85d) {
                            WVec3 wVec33 = new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * d2), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * d4), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * d6));
                            Rotation rotation = toRotation(wVec33, z);
                            if (positionEyes.distanceTo(wVec33) <= f && (z2 || isVisible(wVec33))) {
                                VecRotation vecRotation2 = new VecRotation(wVec33, rotation);
                                if (vecRotation == null) {
                                    vecRotation = vecRotation2;
                                }
                            }
                            d5 = d6 + 0.1d;
                        }
                    }
                    d3 = d4 + 0.1d;
                }
            }
            d = d2 + 0.1d;
        }
        if (z) {
            wVec32.addVector(f157mc.getThePlayer().getMotionX(), f157mc.getThePlayer().getMotionY(), f157mc.getThePlayer().getMotionZ());
        }
        double xCoord = wVec3.getXCoord() - wVec32.getXCoord();
        double yCoord = wVec3.getYCoord() - wVec32.getYCoord();
        double zCoord = wVec3.getZCoord() - wVec32.getZCoord();
        float degrees = (((float) Math.toDegrees(Math.atan2(zCoord, xCoord))) - 90.0f) % 360.0f;
        if (degrees >= 180.0f) {
            degrees -= 360.0f;
        }
        if (degrees < -180.0f) {
            degrees += 360.0f;
        }
        float f2 = degrees;
        float f3 = ((float) (-Math.toDegrees(Math.atan2(yCoord, Math.sqrt((xCoord * xCoord) + (zCoord * zCoord)))))) % 360.0f;
        if (f3 >= 180.0f) {
            f3 -= 360.0f;
        }
        if (f3 < -180.0f) {
            f3 += 360.0f;
        }
        return new Rotation(f2, f3);
    }

    public static Rotation getRotationsNonLivingEntity(IEntity iEntity) {
        return getRotations(iEntity.getPosX(), iEntity.getPosY() + ((iEntity.getEntityBoundingBox().getMaxY() - iEntity.getEntityBoundingBox().getMinY()) * 0.5d), iEntity.getPosZ());
    }

    public static void faceBow(IEntity iEntity, boolean z, boolean z2, float f) {
        IEntityPlayerSP thePlayer = f157mc.getThePlayer();
        double posX = (iEntity.getPosX() + (z2 ? (iEntity.getPosX() - iEntity.getPrevPosX()) * f : 0.0d)) - (thePlayer.getPosX() + (z2 ? thePlayer.getPosX() - thePlayer.getPrevPosX() : 0.0d));
        double minY = ((((iEntity.getEntityBoundingBox().getMinY() + (z2 ? (iEntity.getEntityBoundingBox().getMinY() - iEntity.getPrevPosY()) * f : 0.0d)) + iEntity.getEyeHeight()) - 0.15d) - (thePlayer.getEntityBoundingBox().getMinY() + (z2 ? thePlayer.getPosY() - thePlayer.getPrevPosY() : 0.0d))) - thePlayer.getEyeHeight();
        double posZ = (iEntity.getPosZ() + (z2 ? (iEntity.getPosZ() - iEntity.getPrevPosZ()) * f : 0.0d)) - (thePlayer.getPosZ() + (z2 ? thePlayer.getPosZ() - thePlayer.getPrevPosZ() : 0.0d));
        double dSqrt = Math.sqrt((posX * posX) + (posZ * posZ));
        float itemInUseDuration = LiquidBounce.moduleManager.getModule(FastBow.class).getState() ? 1.0f : thePlayer.getItemInUseDuration() / 20.0f;
        float f2 = ((itemInUseDuration * itemInUseDuration) + (itemInUseDuration * 2.0f)) / 3.0f;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        Rotation rotation = new Rotation(((float) ((Math.atan2(posZ, posX) * 180.0d) / 3.141592653589793d)) - 90.0f, (float) (-Math.toDegrees(Math.atan(((f2 * f2) - Math.sqrt((((f2 * f2) * f2) * f2) - (0.006000000052154064d * ((0.006000000052154064d * (dSqrt * dSqrt)) + ((2.0d * minY) * (f2 * f2)))))) / (0.006000000052154064d * dSqrt)))));
        if (z) {
            setTargetRotation(rotation);
        } else {
            limitAngleChange(new Rotation(thePlayer.getRotationYaw(), thePlayer.getRotationPitch()), rotation, 10 + new Random().nextInt(6)).toPlayer(f157mc.getThePlayer());
        }
    }

    public static Rotation getRotations(double d, double d2, double d3) {
        IEntityPlayerSP thePlayer = f157mc.getThePlayer();
        double posX = d - thePlayer.getPosX();
        double posY = d2 - (thePlayer.getPosY() + thePlayer.getEyeHeight());
        double posZ = d3 - thePlayer.getPosZ();
        return new Rotation(((float) ((Math.atan2(posZ, posX) * 180.0d) / 3.141592653589793d)) - 90.0f, (float) (-((Math.atan2(posY, Math.sqrt((posX * posX) + (posZ * posZ))) * 180.0d) / 3.141592653589793d)));
    }

    public static Rotation toRotation(WVec3 wVec3, boolean z) {
        WVec3 wVec32 = new WVec3(f157mc.getThePlayer().getPosX(), f157mc.getThePlayer().getEntityBoundingBox().getMinY() + f157mc.getThePlayer().getEyeHeight(), f157mc.getThePlayer().getPosZ());
        if (z) {
            wVec32.addVector(f157mc.getThePlayer().getMotionX(), f157mc.getThePlayer().getMotionY(), f157mc.getThePlayer().getMotionZ());
        }
        double xCoord = wVec3.getXCoord() - wVec32.getXCoord();
        double yCoord = wVec3.getYCoord() - wVec32.getYCoord();
        double zCoord = wVec3.getZCoord() - wVec32.getZCoord();
        float degrees = (((float) Math.toDegrees(Math.atan2(zCoord, xCoord))) - 90.0f) % 360.0f;
        if (degrees >= 180.0f) {
            degrees -= 360.0f;
        }
        if (degrees < -180.0f) {
            degrees += 360.0f;
        }
        float f = degrees;
        float f2 = ((float) (-Math.toDegrees(Math.atan2(yCoord, Math.sqrt((xCoord * xCoord) + (zCoord * zCoord)))))) % 360.0f;
        if (f2 >= 180.0f) {
            f2 -= 360.0f;
        }
        if (f2 < -180.0f) {
            f2 += 360.0f;
        }
        return new Rotation(f, f2);
    }

    public static WVec3 getCenter(IAxisAlignedBB iAxisAlignedBB) {
        return new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * 0.5d), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * 0.5d), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * 0.5d));
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x01d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static VecRotation searchCenter(IAxisAlignedBB iAxisAlignedBB, boolean z, boolean z2, boolean z3, boolean z4, float f) {
        if (z) {
            WVec3 wVec3 = new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * ((f158x * 0.3d) + 1.0d)), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * ((f159y * 0.3d) + 1.0d)), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * ((f160z * 0.3d) + 1.0d)));
            return new VecRotation(wVec3, toRotation(wVec3, z3));
        }
        Rotation rotation = toRotation(new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * f158x * 0.8d), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * f159y * 0.8d), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * f160z * 0.8d)), z3);
        WVec3 positionEyes = f157mc.getThePlayer().getPositionEyes(1.0f);
        VecRotation vecRotation = null;
        double d = 0.15d;
        while (true) {
            double d2 = d;
            if (d2 < 0.85d) {
                double d3 = 0.15d;
                while (true) {
                    double d4 = d3;
                    if (d4 < 1.0d) {
                        double d5 = 0.15d;
                        while (true) {
                            double d6 = d5;
                            if (d6 < 0.85d) {
                                WVec3 wVec32 = new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * d2), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * d4), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * d6));
                                Rotation rotation2 = toRotation(wVec32, z3);
                                if (positionEyes.distanceTo(wVec32) <= f && (z4 || isVisible(wVec32))) {
                                    VecRotation vecRotation2 = new VecRotation(wVec32, rotation2);
                                    if (vecRotation == null) {
                                        vecRotation = vecRotation2;
                                    } else if (z2) {
                                        if (getRotationDifference(vecRotation2.getRotation(), rotation) < getRotationDifference(vecRotation.getRotation(), rotation)) {
                                        }
                                    } else if (getRotationDifference(vecRotation2.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                                    }
                                }
                                d5 = d6 + 0.1d;
                            }
                        }
                        d3 = d4 + 0.1d;
                    }
                }
                d = d2 + 0.1d;
            } else {
                return vecRotation;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x01d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static VecRotation lockView(IAxisAlignedBB iAxisAlignedBB, boolean z, boolean z2, boolean z3, boolean z4, float f) {
        if (z) {
            WVec3 wVec3 = new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * ((f158x * 0.3d) + 1.0d)), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * ((f159y * 0.3d) + 1.0d)), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * ((f160z * 0.3d) + 1.0d)));
            return new VecRotation(wVec3, toRotation(wVec3, z3));
        }
        Rotation rotation = toRotation(new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * f158x * 0.8d), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * f159y * 0.8d), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * f160z * 0.8d)), z3);
        WVec3 positionEyes = f157mc.getThePlayer().getPositionEyes(1.0f);
        VecRotation vecRotation = null;
        double d = 0.45d;
        while (true) {
            double d2 = d;
            if (d2 < 0.55d) {
                double d3 = 0.65d;
                while (true) {
                    double d4 = d3;
                    if (d4 < 0.75d) {
                        double d5 = 0.45d;
                        while (true) {
                            double d6 = d5;
                            if (d6 < 0.55d) {
                                WVec3 wVec32 = new WVec3(iAxisAlignedBB.getMinX() + ((iAxisAlignedBB.getMaxX() - iAxisAlignedBB.getMinX()) * d2), iAxisAlignedBB.getMinY() + ((iAxisAlignedBB.getMaxY() - iAxisAlignedBB.getMinY()) * d4), iAxisAlignedBB.getMinZ() + ((iAxisAlignedBB.getMaxZ() - iAxisAlignedBB.getMinZ()) * d6));
                                Rotation rotation2 = toRotation(wVec32, z3);
                                if (positionEyes.distanceTo(wVec32) <= f && (z4 || isVisible(wVec32))) {
                                    VecRotation vecRotation2 = new VecRotation(wVec32, rotation2);
                                    if (vecRotation == null) {
                                        vecRotation = vecRotation2;
                                    } else if (z2) {
                                        if (getRotationDifference(vecRotation2.getRotation(), rotation) < getRotationDifference(vecRotation.getRotation(), rotation)) {
                                        }
                                    } else if (getRotationDifference(vecRotation2.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                                    }
                                }
                                d5 = d6 + 0.0125d;
                            }
                        }
                        d3 = d4 + 0.0125d;
                    }
                }
                d = d2 + 0.0125d;
            } else {
                return vecRotation;
            }
        }
    }

    public static double getRotationDifference(IEntity iEntity) {
        return getRotationDifference(toRotation(getCenter(iEntity.getEntityBoundingBox()), true), new Rotation(f157mc.getThePlayer().getRotationYaw(), f157mc.getThePlayer().getRotationPitch()));
    }

    public static double getRotationDifference(Rotation rotation) {
        if (serverRotation == null) {
            return 0.0d;
        }
        return getRotationDifference(rotation, serverRotation);
    }

    public static double getRotationDifference(Rotation rotation, Rotation rotation2) {
        return Math.hypot(((((rotation.getYaw() - rotation2.getYaw()) % 360.0f) + 540.0f) % 360.0f) - 180.0f, rotation.getPitch() - rotation2.getPitch());
    }

    @NotNull
    public static Rotation limitAngleChange(Rotation rotation, Rotation rotation2, float f) {
        float yaw = ((((rotation2.getYaw() - rotation.getYaw()) % 360.0f) + 540.0f) % 360.0f) - 180.0f;
        float pitch = ((((rotation2.getPitch() - rotation.getPitch()) % 360.0f) + 540.0f) % 360.0f) - 180.0f;
        return new Rotation(rotation.getYaw() + (yaw > f ? f : Math.max(yaw, -f)), rotation.getPitch() + (pitch > f ? f : Math.max(pitch, -f)));
    }

    public static Rotation getHypixelRotations(WVec3 wVec3, boolean z) {
        WVec3 wVec32 = new WVec3(mc2.field_71439_g.field_70165_t, mc2.field_71439_g.func_174813_aQ().field_72338_b + mc2.field_71439_g.func_70047_e(), mc2.field_71439_g.field_70161_v);
        double xCoord = wVec3.getXCoord() - wVec32.getXCoord();
        double yCoord = wVec3.getYCoord() - wVec32.getYCoord();
        return new Rotation(((float) ((Math.atan2(wVec3.getZCoord() - wVec32.getZCoord(), xCoord) * 180.0d) / 3.141592653589793d)) - 90.0f, (float) (((-Math.atan2(yCoord, MathHelper.func_76133_a((xCoord * xCoord) + (r0 * r0)))) * 180.0d) / 3.141592653589793d));
    }

    public static WVec3 getVectorForRotation(Rotation rotation) {
        float fCos = (float) Math.cos(((-rotation.getYaw()) * 0.017453292f) - 3.1415927f);
        float fSin = (float) Math.sin(((-rotation.getYaw()) * 0.017453292f) - 3.1415927f);
        float f = (float) (-Math.cos((-rotation.getPitch()) * 0.017453292f));
        return new WVec3(fSin * f, (float) Math.sin((-rotation.getPitch()) * 0.017453292f), fCos * f);
    }

    public static boolean isFaced(IEntity iEntity, double d) {
        return RaycastUtils.raycastEntity(d, (v1) -> {
            return lambda$isFaced$0(r1, v1);
        }) != null;
    }

    private static boolean lambda$isFaced$0(IEntity iEntity, IEntity iEntity2) {
        return iEntity != null && iEntity.equals(iEntity2);
    }

    public static boolean isVisible(WVec3 wVec3) {
        return f157mc.getTheWorld().rayTraceBlocks(new WVec3(f157mc.getThePlayer().getPosX(), f157mc.getThePlayer().getEntityBoundingBox().getMinY() + ((double) f157mc.getThePlayer().getEyeHeight()), f157mc.getThePlayer().getPosZ()), wVec3) == null;
    }

    @EventTarget
    public void onTick(TickEvent tickEvent) {
        if (targetRotation != null) {
            keepLength--;
            if (keepLength <= 0) {
                reset();
            }
        }
        if (random.nextGaussian() > 0.8d) {
            f158x = Math.random();
        }
        if (random.nextGaussian() > 0.8d) {
            f159y = Math.random();
        }
        if (random.nextGaussian() > 0.8d) {
            f160z = Math.random();
        }
    }

    public static void setTargetRotation(Rotation rotation, int i) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        rotation.fixedSensitivity(f157mc.getGameSettings().getMouseSensitivity());
        targetRotation = rotation;
        keepLength = i;
    }

    public static void setTargetRotation(Rotation rotation) {
        setTargetRotation(rotation, 0);
    }

    @EventTarget
    public void onPacket(PacketEvent packetEvent) {
        IPacket packet = packetEvent.getPacket();
        if (classProvider.isCPacketPlayer(packet)) {
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = packet.asCPacketPlayer();
            if (targetRotation != null && !keepCurrentRotation && (targetRotation.getYaw() != serverRotation.getYaw() || targetRotation.getPitch() != serverRotation.getPitch())) {
                iCPacketPlayerAsCPacketPlayer.setYaw(targetRotation.getYaw());
                iCPacketPlayerAsCPacketPlayer.setPitch(targetRotation.getPitch());
                iCPacketPlayerAsCPacketPlayer.setRotating(true);
            }
            if (iCPacketPlayerAsCPacketPlayer.isRotating()) {
                serverRotation = new Rotation(iCPacketPlayerAsCPacketPlayer.getYaw(), iCPacketPlayerAsCPacketPlayer.getPitch());
            }
        }
    }

    public static void reset() {
        keepLength = 0;
        targetRotation = null;
    }
}

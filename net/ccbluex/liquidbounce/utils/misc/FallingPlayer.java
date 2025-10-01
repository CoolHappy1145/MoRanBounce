package net.ccbluex.liquidbounce.utils.misc;

import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.Nullable;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/FallingPlayer.class */
public class FallingPlayer extends MinecraftInstance {

    /* renamed from: x */
    private double f173x;

    /* renamed from: y */
    private double f174y;

    /* renamed from: z */
    private double f175z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private float strafe;
    private float forward;

    public FallingPlayer(double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3) {
        this.f173x = d;
        this.f174y = d2;
        this.f175z = d3;
        this.motionX = d4;
        this.motionY = d5;
        this.motionZ = d6;
        this.yaw = f;
        this.strafe = f2;
        this.forward = f3;
    }

    private void calculateForTick() {
        this.strafe *= 0.98f;
        this.forward *= 0.98f;
        float f = (this.strafe * this.strafe) + (this.forward * this.forward);
        if (f >= 1.0E-4f) {
            float fSqrt = (float) Math.sqrt(f);
            if (fSqrt < 1.0f) {
                fSqrt = 1.0f;
            }
            float jumpMovementFactor = f157mc.getThePlayer().getJumpMovementFactor() / fSqrt;
            this.strafe *= jumpMovementFactor;
            this.forward *= jumpMovementFactor;
            float fSin = (float) Math.sin((this.yaw * 3.1415927f) / 180.0f);
            float fCos = (float) Math.cos((this.yaw * 3.1415927f) / 180.0f);
            this.motionX += (this.strafe * fCos) - (this.forward * fSin);
            this.motionZ += (this.forward * fCos) + (this.strafe * fSin);
        }
        this.motionY -= 0.08d;
        this.motionX *= 0.91d;
        this.motionY *= 0.9800000190734863d;
        this.motionY *= 0.91d;
        this.motionZ *= 0.91d;
        this.f173x += this.motionX;
        this.f174y += this.motionY;
        this.f175z += this.motionZ;
    }

    public CollisionResult findCollision(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            WVec3 wVec3 = new WVec3(this.f173x, this.f174y, this.f175z);
            calculateForTick();
            WVec3 wVec32 = new WVec3(this.f173x, this.f174y, this.f175z);
            float width = f157mc.getThePlayer().getWidth() / 2.0f;
            WBlockPos wBlockPosRayTrace = rayTrace(wVec3, wVec32);
            if (wBlockPosRayTrace != null) {
                return new CollisionResult(wBlockPosRayTrace, i2);
            }
            WBlockPos wBlockPosRayTrace2 = rayTrace(wVec3.addVector(width, 0.0d, width), wVec32);
            if (wBlockPosRayTrace2 != null) {
                return new CollisionResult(wBlockPosRayTrace2, i2);
            }
            WBlockPos wBlockPosRayTrace3 = rayTrace(wVec3.addVector(-width, 0.0d, width), wVec32);
            if (wBlockPosRayTrace3 != null) {
                return new CollisionResult(wBlockPosRayTrace3, i2);
            }
            WBlockPos wBlockPosRayTrace4 = rayTrace(wVec3.addVector(width, 0.0d, -width), wVec32);
            if (wBlockPosRayTrace4 != null) {
                return new CollisionResult(wBlockPosRayTrace4, i2);
            }
            WBlockPos wBlockPosRayTrace5 = rayTrace(wVec3.addVector(-width, 0.0d, -width), wVec32);
            if (wBlockPosRayTrace5 != null) {
                return new CollisionResult(wBlockPosRayTrace5, i2);
            }
            WBlockPos wBlockPosRayTrace6 = rayTrace(wVec3.addVector(width, 0.0d, width / 2.0f), wVec32);
            if (wBlockPosRayTrace6 != null) {
                return new CollisionResult(wBlockPosRayTrace6, i2);
            }
            WBlockPos wBlockPosRayTrace7 = rayTrace(wVec3.addVector(-width, 0.0d, width / 2.0f), wVec32);
            if (wBlockPosRayTrace7 != null) {
                return new CollisionResult(wBlockPosRayTrace7, i2);
            }
            WBlockPos wBlockPosRayTrace8 = rayTrace(wVec3.addVector(width / 2.0f, 0.0d, width), wVec32);
            if (wBlockPosRayTrace8 != null) {
                return new CollisionResult(wBlockPosRayTrace8, i2);
            }
            WBlockPos wBlockPosRayTrace9 = rayTrace(wVec3.addVector(width / 2.0f, 0.0d, -width), wVec32);
            if (wBlockPosRayTrace9 != null) {
                return new CollisionResult(wBlockPosRayTrace9, i2);
            }
        }
        return null;
    }

    @Nullable
    private WBlockPos rayTrace(WVec3 wVec3, WVec3 wVec32) {
        IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = f157mc.getTheWorld().rayTraceBlocks(wVec3, wVec32, true);
        if (iMovingObjectPositionRayTraceBlocks != null && iMovingObjectPositionRayTraceBlocks.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK && iMovingObjectPositionRayTraceBlocks.getSideHit().isUp()) {
            return iMovingObjectPositionRayTraceBlocks.getBlockPos();
        }
        return null;
    }

    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/FallingPlayer$CollisionResult.class */
    public static class CollisionResult {
        private final WBlockPos pos;
        private final int tick;

        public CollisionResult(WBlockPos wBlockPos, int i) {
            this.pos = wBlockPos;
            this.tick = i;
        }

        public WBlockPos getPos() {
            return this.pos;
        }

        public int getTick() {
            return this.tick;
        }
    }
}

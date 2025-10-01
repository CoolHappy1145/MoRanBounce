package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cJ\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/Rotation;", "", "yaw", "", "pitch", "(FF)V", "getPitch", "()F", "setPitch", "(F)V", "getYaw", "setYaw", "applyStrafeToPlayer", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "component1", "component2", "copy", "equals", "", "other", "fixedSensitivity", "sensitivity", "hashCode", "", "toPlayer", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "toString", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Rotation.class */
public final class Rotation {
    private float yaw;
    private float pitch;

    public final float component1() {
        return this.yaw;
    }

    public final float component2() {
        return this.pitch;
    }

    @NotNull
    public final Rotation copy(float f, float f2) {
        return new Rotation(f, f2);
    }

    public static Rotation copy$default(Rotation rotation, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = rotation.yaw;
        }
        if ((i & 2) != 0) {
            f2 = rotation.pitch;
        }
        return rotation.copy(f, f2);
    }

    @NotNull
    public String toString() {
        return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ")";
    }

    public int hashCode() {
        return (Float.hashCode(this.yaw) * 31) + Float.hashCode(this.pitch);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rotation)) {
            return false;
        }
        Rotation rotation = (Rotation) obj;
        return Float.compare(this.yaw, rotation.yaw) == 0 && Float.compare(this.pitch, rotation.pitch) == 0;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float f) {
        this.pitch = f;
    }

    public Rotation(float f, float f2) {
        this.yaw = f;
        this.pitch = f2;
    }

    public final void toPlayer(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        if (Float.isNaN(this.yaw) || Float.isNaN(this.pitch)) {
            return;
        }
        fixedSensitivity(MinecraftInstance.f157mc.getGameSettings().getMouseSensitivity());
        player.setRotationYaw(this.yaw);
        player.setRotationPitch(this.pitch);
    }

    public final void fixedSensitivity(float f) {
        float f2 = (f * 0.6f) + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        Rotation rotation = RotationUtils.serverRotation;
        float f4 = this.yaw - rotation.yaw;
        this.yaw = rotation.yaw + (f4 - (f4 % f3));
        float f5 = this.pitch - rotation.pitch;
        this.pitch = rotation.pitch + (f5 - (f5 % f3));
    }

    public final void applyStrafeToPlayer(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        int iFunc_76142_g = (int) ((MathHelper.func_76142_g(((thePlayer.getRotationYaw() - this.yaw) - 23.5f) - 135.0f) + 180.0f) / 45.0f);
        float f = this.yaw;
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float f2 = 0.0f;
        float f3 = 0.0f;
        switch (iFunc_76142_g) {
            case 0:
                f2 = forward;
                f3 = strafe;
                break;
            case 1:
                f2 = 0.0f + forward + strafe;
                f3 = (0.0f - forward) + strafe;
                break;
            case 2:
                f2 = strafe;
                f3 = -forward;
                break;
            case 3:
                f2 = (0.0f - forward) + strafe;
                f3 = (0.0f - forward) - strafe;
                break;
            case 4:
                f2 = -forward;
                f3 = -strafe;
                break;
            case 5:
                f2 = (0.0f - forward) - strafe;
                f3 = (0.0f + forward) - strafe;
                break;
            case 6:
                f2 = -strafe;
                f3 = forward;
                break;
            case 7:
                f2 = (0.0f + forward) - strafe;
                f3 = 0.0f + forward + strafe;
                break;
        }
        if (f2 > 1.0f || ((f2 < 0.9f && f2 > 0.3f) || f2 < -1.0f || (f2 > -0.9f && f2 < -0.3f))) {
            f2 *= 0.5f;
        }
        if (f3 > 1.0f || ((f3 < 0.9f && f3 > 0.3f) || f3 < -1.0f || (f3 > -0.9f && f3 < -0.3f))) {
            f3 *= 0.5f;
        }
        float f4 = (f3 * f3) + (f2 * f2);
        if (f4 >= 1.0E-4f) {
            float fFunc_76129_c = MathHelper.func_76129_c(f4);
            if (fFunc_76129_c < 1.0f) {
                fFunc_76129_c = 1.0f;
            }
            float f5 = friction / fFunc_76129_c;
            float f6 = f3 * f5;
            float f7 = f2 * f5;
            float fFunc_76126_a = MathHelper.func_76126_a((float) ((f * 3.141592653589793d) / 180.0d));
            float fFunc_76134_b = MathHelper.func_76134_b((float) ((f * 3.141592653589793d) / 180.0d));
            thePlayer.setMotionX(thePlayer.getMotionX() + ((f6 * fFunc_76134_b) - (f7 * fFunc_76126_a)));
            thePlayer.setMotionZ(thePlayer.getMotionZ() + (f7 * fFunc_76134_b) + (f6 * fFunc_76126_a));
        }
    }
}

package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0017H\u0007J\u0012\u0010\u0018\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/LongJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "canBoost", "", "canMineplexBoost", "jumped", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "ncpBoostValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "teleported", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "LongJump", description = "Allows you to jump further.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LongJump.class */
public final class LongJump extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "AACv1", "AACv2", "AACv3", "Mineplex", "Mineplex2", "Mineplex3"}, "NCP");
    private final FloatValue ncpBoostValue = new FloatValue("NCPBoost", 4.25f, 1.0f, 10.0f);
    private final BoolValue autoJumpValue = new BoolValue("AutoJump", false);
    private boolean jumped;
    private boolean canBoost;
    private boolean teleported;
    private boolean canMineplexBoost;

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:53:0x017f  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        if (LadderJump.jumped) {
            MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * 1.08f);
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (this.jumped) {
                String str = (String) this.modeValue.get();
                if (thePlayer.getOnGround() || thePlayer.getCapabilities().isFlying()) {
                    this.jumped = false;
                    this.canMineplexBoost = false;
                    if (StringsKt.equals(str, "NCP", true)) {
                        thePlayer.setMotionX(0.0d);
                        thePlayer.setMotionZ(0.0d);
                        return;
                    }
                    return;
                }
                LongJump longJump = this;
                if (str != null) {
                    String lowerCase = str.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    switch (lowerCase.hashCode()) {
                        case -1362669950:
                            if (lowerCase.equals("mineplex")) {
                                thePlayer.setMotionY(thePlayer.getMotionY() + 0.01321d);
                                thePlayer.setJumpMovementFactor(0.08f);
                                MovementUtils.strafe$default(0.0f, 1, null);
                                break;
                            }
                            break;
                        case 108891:
                            if (lowerCase.equals("ncp")) {
                                MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * (longJump.canBoost ? ((Number) longJump.ncpBoostValue.get()).floatValue() : 1.0f));
                                longJump.canBoost = false;
                                break;
                            }
                            break;
                        case 92570110:
                            if (lowerCase.equals("aacv1")) {
                                thePlayer.setMotionY(thePlayer.getMotionY() + 0.05999d);
                                MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * 1.08f);
                                break;
                            }
                            break;
                        case 92570111:
                            if (lowerCase.equals("aacv2")) {
                                thePlayer.setJumpMovementFactor(0.09f);
                                thePlayer.setMotionY(thePlayer.getMotionY() + 0.01321d);
                                thePlayer.setJumpMovementFactor(0.08f);
                                MovementUtils.strafe$default(0.0f, 1, null);
                                break;
                            }
                            break;
                        case 92570112:
                            if (lowerCase.equals("aacv3") && thePlayer.getFallDistance() > 0.5f && !longJump.teleported) {
                                IEnumFacing horizontalFacing = thePlayer.getHorizontalFacing();
                                double d = 0.0d;
                                double d2 = 0.0d;
                                if (horizontalFacing.isNorth()) {
                                    d2 = -3.0d;
                                } else if (horizontalFacing.isEast()) {
                                    d = 3.0d;
                                } else if (horizontalFacing.isSouth()) {
                                    d2 = 3.0d;
                                } else if (horizontalFacing.isWest()) {
                                    d = -3.0d;
                                }
                                thePlayer.setPosition(thePlayer.getPosX() + d, thePlayer.getPosY(), thePlayer.getPosZ() + d2);
                                longJump.teleported = true;
                                break;
                            }
                            break;
                        case 706904560:
                            if (lowerCase.equals("mineplex2") && longJump.canMineplexBoost) {
                                thePlayer.setJumpMovementFactor(0.1f);
                                if (thePlayer.getFallDistance() > 1.5f) {
                                    thePlayer.setJumpMovementFactor(0.0f);
                                    thePlayer.setMotionY(-10.0d);
                                }
                                MovementUtils.strafe$default(0.0f, 1, null);
                                break;
                            }
                            break;
                        case 706904561:
                            if (lowerCase.equals("mineplex3")) {
                            }
                            break;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            }
            if (((Boolean) this.autoJumpValue.get()).booleanValue() && thePlayer.getOnGround() && MovementUtils.isMoving()) {
                this.jumped = true;
                thePlayer.jump();
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            String str = (String) this.modeValue.get();
            if (StringsKt.equals(str, "mineplex3", true)) {
                if (thePlayer.getFallDistance() != 0.0f) {
                    thePlayer.setMotionY(thePlayer.getMotionY() + 0.037d);
                }
            } else if (StringsKt.equals(str, "ncp", true) && !MovementUtils.isMoving() && this.jumped) {
                thePlayer.setMotionX(0.0d);
                thePlayer.setMotionZ(0.0d);
                event.zeroXZ();
            }
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.jumped = true;
        this.canBoost = true;
        this.teleported = false;
        if (getState()) {
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1362669950:
                    if (lowerCase.equals("mineplex")) {
                        event.setMotion(event.getMotion() * 4.08f);
                        return;
                    }
                    return;
                case 706904560:
                    if (lowerCase.equals("mineplex2")) {
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer.isCollidedHorizontally()) {
                            event.setMotion(2.31f);
                            this.canMineplexBoost = true;
                            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer2 == null) {
                                Intrinsics.throwNpe();
                            }
                            thePlayer2.setOnGround(false);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}

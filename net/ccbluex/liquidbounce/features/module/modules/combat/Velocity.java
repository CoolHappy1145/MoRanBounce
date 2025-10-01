package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020$2\u0006\u0010&\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020$2\u0006\u0010&\u001a\u00020+H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006,"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Velocity;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacPushXZReducerValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacPushYReducerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "debug", "horizontalValue", "hytCount", "", "jump", "", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "nottntvelocity", "onlyaura", "onlyground", "reverse2StrengthValue", "reverseHurt", "reverseStrengthValue", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "velocityInput", "velocityTick", "velocityTickValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "verticalValue", "onDisable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Velocity", description = "Allows you to modify the amount of knockback you take.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Velocity.class */
public final class Velocity extends Module {
    private boolean velocityInput;
    private int velocityTick;
    private boolean usedTimer;
    private boolean reverseHurt;
    private boolean jump;
    private final FloatValue horizontalValue = new FloatValue("Horizontal", 0.0f, 0.0f, 1.0f);
    private final FloatValue verticalValue = new FloatValue("Vertical", 0.0f, 0.0f, 1.0f);

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"OldAAC4", "Simple", "AAC4Reduce", "AAC5Reduce", "HytMotion", "NoXZ", "Grim-Motion", "Grim-Packet", "Custom", "AAC4", "MatrixSimple", "Tick", "CanCelS12", "AAC", "AACPush", "AACZero", "Reverse", "SmoothReverse", "Jump", "Glitch"}, "Simple");
    private final FloatValue reverseStrengthValue = new FloatValue("ReverseStrength", 1.0f, 0.1f, 1.0f);
    private final FloatValue reverse2StrengthValue = new FloatValue("SmoothReverseStrength", 0.05f, 0.02f, 0.1f);
    private final FloatValue aacPushXZReducerValue = new FloatValue("AACPushXZReducer", 2.0f, 1.0f, 3.0f);
    private final BoolValue aacPushYReducerValue = new BoolValue("AACPushYReducer", true);
    private final IntegerValue velocityTickValue = new IntegerValue("VelocityTick", 1, 0, 10);
    private final BoolValue nottntvelocity = new BoolValue("NoTNTVelocity", false);
    private final BoolValue debug = new BoolValue("VelocityDebug", false);
    private final BoolValue onlyground = new BoolValue("OnlyGround", false);
    private final BoolValue onlyaura = new BoolValue("OnlyKillAura", false);
    private MSTimer velocityTimer = new MSTimer();
    private int hytCount = 24;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            thePlayer.setSpeedInAir(0.02f);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.velocityInput) {
            this.velocityTick++;
        } else {
            this.velocityTick = 0;
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (this.hytCount < 24) {
                this.hytCount++;
            }
            if (!((Boolean) this.onlyaura.get()).booleanValue() || LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class).getState()) {
                if ((((Boolean) this.onlyground.get()).booleanValue() && !thePlayer.getOnGround()) || thePlayer.isInWater() || thePlayer.isInLava() || thePlayer.isInWeb()) {
                    return;
                }
                if (this.usedTimer) {
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                    this.usedTimer = false;
                }
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1970553484:
                        if (lowerCase.equals("smoothreverse")) {
                            if (!this.velocityInput) {
                                thePlayer.setSpeedInAir(0.02f);
                                return;
                            }
                            if (thePlayer.getHurtTime() > 0) {
                                this.reverseHurt = true;
                            }
                            if (!thePlayer.getOnGround()) {
                                if (this.reverseHurt) {
                                    thePlayer.setSpeedInAir(((Number) this.reverse2StrengthValue.get()).floatValue());
                                    return;
                                }
                                return;
                            } else {
                                if (this.velocityTimer.hasTimePassed(80L)) {
                                    this.velocityInput = false;
                                    this.reverseHurt = false;
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    case -1513652168:
                        if (lowerCase.equals("aac5reduce")) {
                            if (thePlayer.getHurtTime() > 1 && this.velocityInput) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.81d);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.81d);
                            }
                            if (this.velocityInput) {
                                if ((thePlayer.getHurtTime() < 5 || thePlayer.getOnGround()) && this.velocityTimer.hasTimePassed(120L)) {
                                    this.velocityInput = false;
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    case -1379020040:
                        if (lowerCase.equals("oldaac4")) {
                            if (!thePlayer.getOnGround()) {
                                if (this.velocityInput) {
                                    thePlayer.setSpeedInAir(0.02f);
                                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.6d);
                                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.6d);
                                    return;
                                }
                                return;
                            }
                            if (this.velocityTimer.hasTimePassed(80L)) {
                                this.velocityInput = false;
                                thePlayer.setSpeedInAir(0.02f);
                                return;
                            }
                            return;
                        }
                        return;
                    case -1243181771:
                        if (lowerCase.equals("glitch")) {
                            thePlayer.setNoClip(this.velocityInput);
                            if (thePlayer.getHurtTime() == 7) {
                                thePlayer.setMotionY(0.4d);
                            }
                            this.velocityInput = false;
                            return;
                        }
                        return;
                    case -1234547235:
                        if (lowerCase.equals("aacpush")) {
                            if (this.jump) {
                                if (thePlayer.getOnGround()) {
                                    this.jump = false;
                                }
                            } else {
                                if (thePlayer.getHurtTime() > 0 && thePlayer.getMotionX() != 0.0d && thePlayer.getMotionZ() != 0.0d) {
                                    thePlayer.setOnGround(true);
                                }
                                if (thePlayer.getHurtResistantTime() > 0 && ((Boolean) this.aacPushYReducerValue.get()).booleanValue() && !LiquidBounce.INSTANCE.getModuleManager().get(Speed.class).getState()) {
                                    thePlayer.setMotionY(thePlayer.getMotionY() - 0.014999993d);
                                }
                            }
                            if (thePlayer.getHurtResistantTime() >= 19) {
                                float fFloatValue = ((Number) this.aacPushXZReducerValue.get()).floatValue();
                                thePlayer.setMotionX(thePlayer.getMotionX() / fFloatValue);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() / fFloatValue);
                                return;
                            }
                            return;
                        }
                        return;
                    case -1234264725:
                        if (lowerCase.equals("aaczero")) {
                            if (thePlayer.getHurtTime() > 0) {
                                if (!this.velocityInput || thePlayer.getOnGround() || thePlayer.getFallDistance() > 2.0f) {
                                    return;
                                }
                                thePlayer.setMotionY(thePlayer.getMotionY() - 1.0d);
                                thePlayer.setAirBorne(true);
                                thePlayer.setOnGround(true);
                                return;
                            }
                            this.velocityInput = false;
                            return;
                        }
                        return;
                    case -931850252:
                        if (lowerCase.equals("grim-motion") && thePlayer.getHurtTime() > 0) {
                            thePlayer.addVelocity(-1.0E-7d, -1.0E-7d, -1.0E-7d);
                            thePlayer.addVelocity(-1.0E-7d, -1.0E-7d, -1.0E-7d);
                            thePlayer.addVelocity(-1.0E-7d, -1.0E-7d, -1.0E-7d);
                            return;
                        }
                        return;
                    case 96323:
                        if (lowerCase.equals("aac") && this.velocityInput && this.velocityTimer.hasTimePassed(80L)) {
                            thePlayer.setMotionX(thePlayer.getMotionX() * ((Number) this.horizontalValue.get()).doubleValue());
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * ((Number) this.horizontalValue.get()).doubleValue());
                            this.velocityInput = false;
                            return;
                        }
                        return;
                    case 3273774:
                        if (lowerCase.equals("jump") && thePlayer.getHurtTime() > 0 && thePlayer.getOnGround()) {
                            thePlayer.setMotionY(0.42d);
                            float rotationYaw = thePlayer.getRotationYaw() * 0.017453292f;
                            thePlayer.setMotionX(thePlayer.getMotionX() - (((float) Math.sin(rotationYaw)) * 0.2d));
                            thePlayer.setMotionZ(thePlayer.getMotionZ() + (((float) Math.cos(rotationYaw)) * 0.2d));
                            return;
                        }
                        return;
                    case 3559837:
                        if (lowerCase.equals("tick")) {
                            if (this.velocityTick > ((Number) this.velocityTickValue.get()).intValue()) {
                                if (thePlayer.getMotionY() > 0.0d) {
                                    thePlayer.setMotionY(0.0d);
                                }
                                thePlayer.setMotionX(0.0d);
                                thePlayer.setMotionZ(0.0d);
                                thePlayer.setJumpMovementFactor(-1.0E-5f);
                                this.velocityInput = false;
                            }
                            if (thePlayer.getOnGround() && this.velocityTick > 1) {
                                this.velocityInput = false;
                                return;
                            }
                            return;
                        }
                        return;
                    case 1099846370:
                        if (!lowerCase.equals("reverse") || !this.velocityInput) {
                            return;
                        }
                        if (!thePlayer.getOnGround()) {
                            MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * ((Number) this.reverseStrengthValue.get()).floatValue());
                            return;
                        } else {
                            if (this.velocityTimer.hasTimePassed(80L)) {
                                this.velocityInput = false;
                                return;
                            }
                            return;
                        }
                    case 1893811447:
                        if (lowerCase.equals("aac4reduce")) {
                            if (thePlayer.getHurtTime() > 0 && !thePlayer.getOnGround() && this.velocityInput && this.velocityTimer.hasTimePassed(80L)) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.62d);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.62d);
                            }
                            if (this.velocityInput) {
                                if ((thePlayer.getHurtTime() < 4 || thePlayer.getOnGround()) && this.velocityTimer.hasTimePassed(120L)) {
                                    this.velocityInput = false;
                                    return;
                                }
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
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            IPacket packet = event.getPacket();
            if (((Boolean) this.nottntvelocity.get()).booleanValue() && MinecraftInstance.classProvider.isSPacketExplosion(packet)) {
                event.cancelEvent();
            }
            if (MinecraftInstance.classProvider.isSPacketEntityVelocity(packet)) {
                ISPacketEntityVelocity iSPacketEntityVelocityAsSPacketEntityVelocity = packet.asSPacketEntityVelocity();
                this.velocityTimer.reset();
                this.velocityTick = 0;
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld != null) {
                    if (theWorld.getEntityByID(iSPacketEntityVelocityAsSPacketEntityVelocity.getEntityID()) == null || (!Intrinsics.areEqual(r0, thePlayer))) {
                        return;
                    }
                    if (((Boolean) this.debug.get()).booleanValue()) {
                        ClientUtils.displayChatMessage("\u00a7b[\u00a7bCoolSenseDebug]\u00a7dVelocity MotionY \u00a7f>> " + thePlayer.getMotionY());
                    }
                    this.velocityTimer.reset();
                    String str = (String) this.modeValue.get();
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase = str.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    switch (lowerCase.hashCode()) {
                        case -1970553484:
                            if (!lowerCase.equals("smoothreverse")) {
                                return;
                            }
                            break;
                        case -1702153037:
                            if (lowerCase.equals("matrixsimple")) {
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionX((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionX() * 0.36d));
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionZ((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionZ() * 0.36d));
                                if (thePlayer.getOnGround()) {
                                    iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionX((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionX() * 0.9d));
                                    iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionZ((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionZ() * 0.9d));
                                    return;
                                }
                                return;
                            }
                            return;
                        case -1513652168:
                            if (!lowerCase.equals("aac5reduce")) {
                                return;
                            }
                            break;
                        case -1379020040:
                            if (!lowerCase.equals("oldaac4")) {
                                return;
                            }
                            break;
                        case -1243181771:
                            if (lowerCase.equals("glitch") && thePlayer.getOnGround()) {
                                this.velocityInput = true;
                                event.cancelEvent();
                                return;
                            }
                            return;
                        case -1234264725:
                            if (!lowerCase.equals("aaczero")) {
                                return;
                            }
                            break;
                        case -902286926:
                            if (lowerCase.equals("simple")) {
                                float fFloatValue = ((Number) this.horizontalValue.get()).floatValue();
                                float fFloatValue2 = ((Number) this.verticalValue.get()).floatValue();
                                if (fFloatValue == 0.0f && fFloatValue2 == 0.0f) {
                                    event.cancelEvent();
                                }
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionX((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionX() * fFloatValue));
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionY((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionY() * fFloatValue2));
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionZ((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionZ() * fFloatValue));
                                return;
                            }
                            return;
                        case -859396922:
                            if (lowerCase.equals("grim-packet")) {
                                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer2.setMotionX(thePlayer2.getMotionX() - 0.01001400514d);
                                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer3.setMotionZ(thePlayer3.getMotionZ() + 1.011101E-4d);
                                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer4 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer4.setMotionY(thePlayer4.getMotionY() - 0.001010101d);
                                return;
                            }
                            return;
                        case 96323:
                            if (!lowerCase.equals("aac")) {
                                return;
                            }
                            break;
                        case 3387523:
                            if (lowerCase.equals("noxz")) {
                                if (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionX() == 0 && iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionZ() == 0) {
                                    return;
                                }
                                Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
                                if (module == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                                }
                                IEntityLivingBase nearByEntity = LiquidBounce.INSTANCE.getCombatManager().getNearByEntity(((Number) ((KillAura) module).getRangeValue().get()).floatValue() + 1.0f);
                                if (nearByEntity != null) {
                                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer5 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer5.setMotionX(0.0d);
                                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer6 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer6.setMotionZ(0.0d);
                                    iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionX(0);
                                    iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionZ(0);
                                    int i = 0;
                                    int i2 = this.hytCount;
                                    if (0 <= i2) {
                                        while (true) {
                                            IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                                            if (thePlayer7 == null) {
                                                Intrinsics.throwNpe();
                                            }
                                            thePlayer7.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketUseEntity(nearByEntity, ICPacketUseEntity.WAction.ATTACK));
                                            IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                                            if (thePlayer8 == null) {
                                                Intrinsics.throwNpe();
                                            }
                                            thePlayer8.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketAnimation());
                                            if (i != i2) {
                                                i++;
                                            }
                                        }
                                    }
                                    if (this.hytCount > 12) {
                                        this.hytCount -= 5;
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        case 3559837:
                            if (lowerCase.equals("tick")) {
                                this.velocityInput = true;
                                float fFloatValue3 = ((Number) this.horizontalValue.get()).floatValue();
                                float fFloatValue4 = ((Number) this.verticalValue.get()).floatValue();
                                if (fFloatValue3 == 0.0f && fFloatValue4 == 0.0f) {
                                    event.cancelEvent();
                                }
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionX((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionX() * fFloatValue3));
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionY((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionY() * fFloatValue4));
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionZ((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionZ() * fFloatValue3));
                                return;
                            }
                            return;
                        case 476593434:
                            if (lowerCase.equals("cancels12")) {
                                event.cancelEvent();
                                return;
                            }
                            return;
                        case 1099846370:
                            if (!lowerCase.equals("reverse")) {
                                return;
                            }
                            break;
                        case 1893811447:
                            if (lowerCase.equals("aac4reduce")) {
                                this.velocityInput = true;
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionX((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionX() * 0.6d));
                                iSPacketEntityVelocityAsSPacketEntityVelocity.setMotionZ((int) (iSPacketEntityVelocityAsSPacketEntityVelocity.getMotionZ() * 0.6d));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                    this.velocityInput = true;
                }
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || thePlayer.isInWater() || thePlayer.isInLava() || thePlayer.isInWeb()) {
            return;
        }
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1379020040:
                if (lowerCase.equals("oldaac4") && thePlayer.getHurtTime() > 0) {
                    event.cancelEvent();
                    this.velocityInput = false;
                    return;
                }
                return;
            case -1234547235:
                if (lowerCase.equals("aacpush")) {
                    this.jump = true;
                    if (!thePlayer.isCollidedVertically()) {
                        event.cancelEvent();
                        return;
                    }
                    return;
                }
                return;
            case -1234264725:
                if (lowerCase.equals("aaczero") && thePlayer.getHurtTime() > 0) {
                    event.cancelEvent();
                    return;
                }
                return;
            default:
                return;
        }
    }
}

package net.ccbluex.liquidbounce.features.module.modules.movement;

import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdl\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\bH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020(H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006)"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Step;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "heightValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "isAACStep", "", "isStep", "jumpHeightValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "ncpNextStep", "", "spartanSwitch", "stepX", "", "stepY", "stepZ", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStepConfirm", "Lnet/ccbluex/liquidbounce/event/StepConfirmEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Step", description = "Allows you to step up blocks.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Step.class */
public final class Step extends Module {
    private boolean isStep;
    private double stepX;
    private double stepY;
    private double stepZ;
    private int ncpNextStep;
    private boolean spartanSwitch;
    private boolean isAACStep;
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Jump", "NCP", "MotionNCP", "OldNCP", "AAC", "LAAC", "AAC3.3.4", "Spartan", "Rewinside"}, "NCP");
    private final FloatValue heightValue = new FloatValue("Height", 1.0f, 0.6f, 10.0f);
    private final FloatValue jumpHeightValue = new FloatValue("JumpHeight", 0.42f, 0.37f, 0.42f);
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);
    private final MSTimer timer = new MSTimer();

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            thePlayer.setStepHeight(0.5f);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (StringsKt.equals(str, "jump", true) && thePlayer.isCollidedHorizontally() && thePlayer.getOnGround() && !MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                fakeJump();
                thePlayer.setMotionY(((Number) this.jumpHeightValue.get()).floatValue());
                return;
            }
            if (StringsKt.equals(str, "laac", true)) {
                if (thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb()) {
                    if (thePlayer.getOnGround() && this.timer.hasTimePassed(((Number) this.delayValue.get()).intValue())) {
                        this.isStep = true;
                        fakeJump();
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.620000001490116d);
                        float rotationYaw = thePlayer.getRotationYaw() * 0.017453292f;
                        thePlayer.setMotionX(thePlayer.getMotionX() - (((float) Math.sin(rotationYaw)) * 0.2d));
                        thePlayer.setMotionZ(thePlayer.getMotionZ() + (((float) Math.cos(rotationYaw)) * 0.2d));
                        this.timer.reset();
                    }
                    thePlayer.setOnGround(true);
                    return;
                }
                this.isStep = false;
                return;
            }
            if (StringsKt.equals(str, "aac3.3.4", true)) {
                if (thePlayer.isCollidedHorizontally() && MovementUtils.isMoving()) {
                    if (thePlayer.getOnGround() && couldStep()) {
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.26d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.26d);
                        thePlayer.jump();
                        this.isAACStep = true;
                    }
                    if (this.isAACStep) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - 0.015d);
                        if (!thePlayer.isUsingItem() && thePlayer.getMovementInput().getMoveStrafe() == 0.0f) {
                            thePlayer.setJumpMovementFactor(0.3f);
                            return;
                        }
                        return;
                    }
                    return;
                }
                this.isAACStep = false;
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && StringsKt.equals(str, "motionncp", true) && thePlayer.isCollidedHorizontally() && !MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
            if (thePlayer.getOnGround() && couldStep()) {
                fakeJump();
                thePlayer.setMotionY(0.0d);
                event.setY(0.41999998688698d);
                this.ncpNextStep = 1;
                return;
            }
            if (this.ncpNextStep == 1) {
                event.setY(0.33319999363422d);
                this.ncpNextStep = 2;
            } else if (this.ncpNextStep == 2) {
                double direction = MovementUtils.getDirection();
                event.setY(0.24813599859094704d);
                event.setX((-Math.sin(direction)) * 0.7d);
                event.setZ(Math.cos(direction) * 0.7d);
                this.ncpNextStep = 0;
            }
        }
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Phase.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }
            if (module.getState()) {
                event.setStepHeight(0.0f);
                return;
            }
            Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
            if (module2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Fly");
            }
            Fly fly = (Fly) module2;
            if (fly.getState()) {
                String str = (String) fly.getModeValue().get();
                if (StringsKt.equals(str, "Hypixel", true) || StringsKt.equals(str, "OtherHypixel", true) || StringsKt.equals(str, "LatestHypixel", true) || StringsKt.equals(str, "Rewinside", true) || (StringsKt.equals(str, "Mineplex", true) && thePlayer.getInventory().getCurrentItemInHand() == null)) {
                    event.setStepHeight(0.0f);
                    return;
                }
            }
            String str2 = (String) this.modeValue.get();
            if (!thePlayer.getOnGround() || !this.timer.hasTimePassed(((Number) this.delayValue.get()).intValue()) || StringsKt.equals(str2, "Jump", true) || StringsKt.equals(str2, "MotionNCP", true) || StringsKt.equals(str2, "LAAC", true) || StringsKt.equals(str2, "AAC3.3.4", true)) {
                thePlayer.setStepHeight(0.5f);
                event.setStepHeight(0.5f);
                return;
            }
            float fFloatValue = ((Number) this.heightValue.get()).floatValue();
            thePlayer.setStepHeight(fFloatValue);
            event.setStepHeight(fFloatValue);
            if (event.getStepHeight() > 0.5f) {
                this.isStep = true;
                this.stepX = thePlayer.getPosX();
                this.stepY = thePlayer.getPosY();
                this.stepZ = thePlayer.getPosZ();
            }
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onStepConfirm(@NotNull StepConfirmEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || !this.isStep) {
            return;
        }
        if (thePlayer.getEntityBoundingBox().getMinY() - this.stepY > 0.5d) {
            String str = (String) this.modeValue.get();
            if (StringsKt.equals(str, "NCP", true) || StringsKt.equals(str, "AAC", true)) {
                fakeJump();
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                this.timer.reset();
            } else if (StringsKt.equals(str, "Spartan", true)) {
                fakeJump();
                if (this.spartanSwitch) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147d, this.stepZ, false));
                } else {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.6d, this.stepZ, false));
                }
                this.spartanSwitch = !this.spartanSwitch;
                this.timer.reset();
            } else if (StringsKt.equals(str, "Rewinside", true)) {
                fakeJump();
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698d, this.stepZ, false));
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212d, this.stepZ, false));
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147d, this.stepZ, false));
                this.timer.reset();
            }
        }
        this.isStep = false;
        this.stepX = 0.0d;
        this.stepY = 0.0d;
        this.stepZ = 0.0d;
    }

    @EventTarget(ignoreCondition = true)
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) && this.isStep && StringsKt.equals((String) this.modeValue.get(), "OldNCP", true)) {
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = packet.asCPacketPlayer();
            iCPacketPlayerAsCPacketPlayer.setY(iCPacketPlayerAsCPacketPlayer.getY() + 0.07d);
            this.isStep = false;
        }
    }

    private final void fakeJump() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            thePlayer.setAirBorne(true);
            thePlayer.triggerAchievement(MinecraftInstance.classProvider.getStatEnum(StatType.JUMP_STAT));
        }
    }

    private final boolean couldStep() {
        double direction = MovementUtils.getDirection();
        double d = (-Math.sin(direction)) * 0.4d;
        double dCos = Math.cos(direction) * 0.4d;
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        return theWorld.getCollisionBoxes(thePlayer.getEntityBoundingBox().offset(d, 1.001335979112147d, dCos)).isEmpty();
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}

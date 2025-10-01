package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdP\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiAFK;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "jumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "moveValue", "randomTimerDelay", "", "rotateValue", "rotationAngleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "shouldMove", "", "swingDelayTimer", "swingDelayValue", "swingValue", "getRandomMoveKeyBind", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AntiAFK", description = "Prevents you from getting kicked for being AFK.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/AntiAFK.class */
public final class AntiAFK extends Module {
    private boolean shouldMove;
    private final MSTimer swingDelayTimer = new MSTimer();
    private final MSTimer delayTimer = new MSTimer();
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Old", "Random", "Custom"}, "Random");
    private final IntegerValue swingDelayValue = new IntegerValue("SwingDelay", 100, 0, 1000);
    private final IntegerValue rotationDelayValue = new IntegerValue("RotationDelay", 100, 0, 1000);
    private final FloatValue rotationAngleValue = new FloatValue("RotationAngle", 1.0f, -180.0f, 180.0f);
    private final BoolValue jumpValue = new BoolValue("Jump", true);
    private final BoolValue moveValue = new BoolValue("Move", true);
    private final BoolValue rotateValue = new BoolValue("Rotate", true);
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private long randomTimerDelay = 500;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1349088399:
                    if (lowerCase.equals("custom")) {
                        if (((Boolean) this.moveValue.get()).booleanValue()) {
                            MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().setPressed(true);
                        }
                        if (((Boolean) this.jumpValue.get()).booleanValue() && thePlayer.getOnGround()) {
                            thePlayer.jump();
                        }
                        if (((Boolean) this.rotateValue.get()).booleanValue() && this.delayTimer.hasTimePassed(((Number) this.rotationDelayValue.get()).intValue())) {
                            thePlayer.setRotationYaw(thePlayer.getRotationYaw() + ((Number) this.rotationAngleValue.get()).floatValue());
                            if (thePlayer.getRotationPitch() <= -90.0f || thePlayer.getRotationPitch() >= 90.0f) {
                                thePlayer.setRotationPitch(0.0f);
                            }
                            thePlayer.setRotationPitch(thePlayer.getRotationPitch() + ((RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f) * 2.0f) - 1.0f));
                            this.delayTimer.reset();
                        }
                        if (((Boolean) this.swingValue.get()).booleanValue() && !thePlayer.isSwingInProgress() && this.swingDelayTimer.hasTimePassed(((Number) this.swingDelayValue.get()).intValue())) {
                            thePlayer.swingItem();
                            this.swingDelayTimer.reset();
                            return;
                        }
                        return;
                    }
                    return;
                case -938285885:
                    if (lowerCase.equals("random")) {
                        IKeyBinding randomMoveKeyBind = getRandomMoveKeyBind();
                        if (randomMoveKeyBind == null) {
                            Intrinsics.throwNpe();
                        }
                        randomMoveKeyBind.setPressed(this.shouldMove);
                        if (this.delayTimer.hasTimePassed(this.randomTimerDelay)) {
                            this.shouldMove = false;
                            this.randomTimerDelay = 500L;
                            switch (RandomUtils.nextInt(0, 6)) {
                                case 0:
                                    if (thePlayer.getOnGround()) {
                                        thePlayer.jump();
                                    }
                                    this.delayTimer.reset();
                                    return;
                                case 1:
                                    if (!thePlayer.isSwingInProgress()) {
                                        thePlayer.swingItem();
                                    }
                                    this.delayTimer.reset();
                                    return;
                                case 2:
                                    this.randomTimerDelay = RandomUtils.nextInt(0, 1000);
                                    this.shouldMove = true;
                                    this.delayTimer.reset();
                                    return;
                                case 3:
                                    thePlayer.getInventory().setCurrentItem(RandomUtils.nextInt(0, 9));
                                    MinecraftInstance.f157mc.getPlayerController().updateController();
                                    this.delayTimer.reset();
                                    return;
                                case 4:
                                    thePlayer.setRotationYaw(thePlayer.getRotationYaw() + RandomUtils.INSTANCE.nextFloat(-180.0f, 180.0f));
                                    this.delayTimer.reset();
                                    return;
                                case 5:
                                    if (thePlayer.getRotationPitch() <= -90.0f || thePlayer.getRotationPitch() >= 90.0f) {
                                        thePlayer.setRotationPitch(0.0f);
                                    }
                                    thePlayer.setRotationPitch(thePlayer.getRotationPitch() + RandomUtils.INSTANCE.nextFloat(-10.0f, 10.0f));
                                    this.delayTimer.reset();
                                    return;
                                default:
                                    return;
                            }
                        }
                        return;
                    }
                    return;
                case 110119:
                    if (lowerCase.equals("old")) {
                        MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().setPressed(true);
                        if (this.delayTimer.hasTimePassed(500L)) {
                            thePlayer.setRotationYaw(thePlayer.getRotationYaw() + 180.0f);
                            this.delayTimer.reset();
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

    private final IKeyBinding getRandomMoveKeyBind() {
        switch (RandomUtils.nextInt(0, 4)) {
            case 0:
                return MinecraftInstance.f157mc.getGameSettings().getKeyBindRight();
            case 1:
                return MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft();
            case 2:
                return MinecraftInstance.f157mc.getGameSettings().getKeyBindBack();
            case 3:
                return MinecraftInstance.f157mc.getGameSettings().getKeyBindForward();
            default:
                return null;
        }
    }

    public void onDisable() {
        if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindForward())) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().setPressed(false);
        }
    }
}

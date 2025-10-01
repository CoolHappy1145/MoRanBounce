package net.ccbluex.liquidbounce.features.module.modules.combat;

import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/SuperKnockback;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hurtTimeValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onlyGroundValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onlyMoveValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "SuperKnockback", description = "Increases knockback dealt to other entities.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/SuperKnockback.class */
public final class SuperKnockback extends Module {
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"ExtraPacket", "WTap", "Packet", "Vanilla"}, "ExtraPacket");
    private final BoolValue onlyMoveValue = new BoolValue("OnlyMove", false);
    private final BoolValue onlyGroundValue = new BoolValue("OnlyGround", false);
    private final IntegerValue delay = new IntegerValue("Delay", 0, 0, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);

    @NotNull
    private final MSTimer timer = new MSTimer();

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        IEntityPlayerSP thePlayer;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isEntityLivingBase(event.getTargetEntity())) {
            IEntity targetEntity = event.getTargetEntity();
            if (targetEntity == null) {
                Intrinsics.throwNpe();
            }
            if (targetEntity.asEntityLivingBase().getHurtTime() <= ((Number) this.hurtTimeValue.get()).intValue() && this.timer.hasTimePassed(((Number) this.delay.get()).intValue())) {
                if (MovementUtils.isMoving() || !((Boolean) this.onlyMoveValue.get()).booleanValue()) {
                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if ((thePlayer2.getOnGround() || !((Boolean) this.onlyGroundValue.get()).booleanValue()) && (thePlayer = MinecraftInstance.f157mc.getThePlayer()) != null) {
                        String str = (String) this.modeValue.get();
                        if (str == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String lowerCase = str.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                        switch (lowerCase.hashCode()) {
                            case -2117036904:
                                if (lowerCase.equals("extrapacket")) {
                                    if (thePlayer.getSprinting()) {
                                        thePlayer.setSprinting(true);
                                    }
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.STOP_SPRINTING));
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SPRINTING));
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.STOP_SPRINTING));
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SPRINTING));
                                    thePlayer.setServerSprintState(true);
                                    break;
                                }
                                break;
                            case -995865464:
                                if (lowerCase.equals("packet")) {
                                    if (thePlayer.getSprinting()) {
                                        thePlayer.setSprinting(true);
                                    }
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.STOP_SPRINTING));
                                    thePlayer.setServerSprintState(true);
                                    break;
                                }
                                break;
                            case 3659724:
                                if (lowerCase.equals("wtap")) {
                                    if (thePlayer.getSprinting()) {
                                        thePlayer.setSprinting(false);
                                    }
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SPRINTING));
                                    thePlayer.setServerSprintState(true);
                                    break;
                                }
                                break;
                            case 233102203:
                                if (lowerCase.equals("vanilla")) {
                                    if (thePlayer.getSprinting()) {
                                        thePlayer.setSprinting(true);
                                    }
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.STOP_SPRINTING));
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SPRINTING));
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.STOP_SPRINTING));
                                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SPRINTING));
                                    thePlayer.setServerSprintState(true);
                                    break;
                                }
                                break;
                        }
                        this.timer.reset();
                    }
                }
            }
        }
    }
}

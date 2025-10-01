package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Regen;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "healthValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "noAirValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "potionEffectValue", "resetTimer", "", "speedValue", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Regen", description = "Regenerates your health much faster.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/Regen.class */
public final class Regen extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "OldSpartan", "PotionRegenHyt", "Packet", "NewSpartanPacket"}, "Vanilla");
    private final IntegerValue healthValue = new IntegerValue("Health", 18, 0, 20);
    private final IntegerValue speedValue = new IntegerValue("Speed", 100, 1, 100);
    private final BoolValue noAirValue = new BoolValue("NoAir", false);
    private final BoolValue potionEffectValue = new BoolValue("PotionEffect", false);
    private boolean resetTimer;

    public void onDisable() {
        if (this.resetTimer) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        }
        this.resetTimer = false;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.resetTimer) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        }
        this.resetTimer = false;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if ((!((Boolean) this.noAirValue.get()).booleanValue() || thePlayer.getOnGround()) && !thePlayer.getCapabilities().isCreativeMode() && thePlayer.isEntityAlive() && thePlayer.getHealth() < ((Number) this.healthValue.get()).floatValue()) {
                if (((Boolean) this.potionEffectValue.get()).booleanValue() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION))) {
                    return;
                }
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1799076468:
                        if (!lowerCase.equals("oldspartan") || MovementUtils.isMoving() || !thePlayer.getOnGround()) {
                            return;
                        }
                        for (int i = 0; i < 9; i++) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                        }
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.45f);
                        this.resetTimer = true;
                        return;
                    case -995865464:
                        if (lowerCase.equals("packet")) {
                            int iIntValue = ((Number) this.speedValue.get()).intValue();
                            for (int i2 = 0; i2 < iIntValue; i2++) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                            }
                            return;
                        }
                        return;
                    case 233102203:
                        if (lowerCase.equals("vanilla")) {
                            int iIntValue2 = ((Number) this.speedValue.get()).intValue();
                            for (int i3 = 0; i3 < iIntValue2; i3++) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }
                            return;
                        }
                        return;
                    case 523070011:
                        if (lowerCase.equals("newspartanpacket")) {
                            for (int i4 = 0; i4 < 10; i4++) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerLook(90.0f, 90.0f, false));
                                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX = thePlayer2.getPosX();
                                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY = thePlayer3.getPosY() + 1.0E-5d;
                                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer4 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler.addToSendQueue(iClassProvider.createCPacketPlayerPosition(posX, posY, thePlayer4.getPosZ(), true));
                                IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer5 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX2 = thePlayer5.getPosX();
                                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer6 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY2 = thePlayer6.getPosY();
                                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer7 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler2.addToSendQueue(iClassProvider2.createCPacketPlayerPosition(posX2, posY2, thePlayer7.getPosZ(), false));
                            }
                            MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.45f);
                            this.resetTimer = true;
                            return;
                        }
                        return;
                    case 1107650233:
                        if (!lowerCase.equals("potionregenhyt") || !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION))) {
                            return;
                        }
                        for (int i5 = 0; i5 < 2; i5++) {
                            if (thePlayer.getOnGround()) {
                                IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer8 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX3 = thePlayer8.getPosX();
                                IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer9 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY3 = thePlayer9.getPosY();
                                IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer10 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posZ = thePlayer10.getPosZ();
                                float rotationYaw = thePlayer.getRotationYaw();
                                float rotationPitch = thePlayer.getRotationPitch();
                                IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer11 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler3.addToSendQueue(iClassProvider3.createCPacketPlayerPosLook(posX3, posY3, posZ, rotationYaw, rotationPitch, thePlayer11.getOnGround()));
                            }
                        }
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.45f);
                        this.resetTimer = true;
                        return;
                    default:
                        return;
                }
            }
        }
    }
}

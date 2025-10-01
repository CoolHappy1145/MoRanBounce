package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdT\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/FastUse;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "customSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customTimer", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "delayValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "noMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "", "onDisable", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "FastUse", description = "Allows you to use items faster.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/FastUse.class */
public final class FastUse extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Instant", "NCP", "Grim-Blink", "AAC", "CustomDelay", "Hyt", "VulCan"}, "NCP");
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    private final IntegerValue delayValue = new IntegerValue("CustomDelay", 0, 0, 300);
    private final IntegerValue customSpeedValue = new IntegerValue("CustomSpeed", 2, 1, 35);
    private final FloatValue customTimer = new FloatValue("CustomTimer", 1.1f, 0.5f, 2.0f);
    private final MSTimer msTimer = new MSTimer();
    private boolean usedTimer;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (this.usedTimer) {
                MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                this.usedTimer = false;
            }
            if (!thePlayer.isUsingItem()) {
                this.msTimer.reset();
                return;
            }
            IItemStack itemInUse = thePlayer.getItemInUse();
            if (itemInUse == null) {
                Intrinsics.throwNpe();
            }
            IItem item = itemInUse.getItem();
            if (MinecraftInstance.classProvider.isItemFood(item) || MinecraftInstance.classProvider.isItemBucketMilk(item) || MinecraftInstance.classProvider.isItemPotion(item)) {
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1773359950:
                        if (lowerCase.equals("customdelay")) {
                            MinecraftInstance.f157mc.getTimer().setTimerSpeed(((Number) this.customTimer.get()).floatValue());
                            this.usedTimer = true;
                            if (!this.msTimer.hasTimePassed(((Number) this.delayValue.get()).intValue())) {
                                return;
                            }
                            int iIntValue = ((Number) this.customSpeedValue.get()).intValue();
                            for (int i = 0; i < iIntValue; i++) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }
                            this.msTimer.reset();
                            return;
                        }
                        return;
                    case -805359837:
                        if (lowerCase.equals("vulcan")) {
                            MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.4f);
                            this.usedTimer = true;
                            if (thePlayer.getOnGround()) {
                                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler.addToSendQueue(iClassProvider.createCPacketPlayer(thePlayer2.getOnGround()));
                                IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX = thePlayer3.getPosX();
                                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer4 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY = thePlayer4.getPosY();
                                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer5 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posZ = thePlayer5.getPosZ();
                                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer6 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler2.addToSendQueue(iClassProvider2.createCPacketPlayerPosition(posX, posY, posZ, thePlayer6.getOnGround()));
                                return;
                            }
                            return;
                        }
                        return;
                    case 96323:
                        if (lowerCase.equals("aac")) {
                            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.22f);
                            this.usedTimer = true;
                            return;
                        }
                        return;
                    case 103811:
                        if (lowerCase.equals("hyt")) {
                            MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.45f);
                            this.usedTimer = true;
                            if (thePlayer.getOnGround()) {
                                IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer7 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX2 = thePlayer7.getPosX();
                                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer8 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY2 = thePlayer8.getPosY();
                                IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer9 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posZ2 = thePlayer9.getPosZ();
                                float rotationYaw = thePlayer.getRotationYaw();
                                float rotationPitch = thePlayer.getRotationPitch();
                                IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer10 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler3.addToSendQueue(iClassProvider3.createCPacketPlayerPosLook(posX2, posY2, posZ2, rotationYaw, rotationPitch, thePlayer10.getOnGround()));
                                return;
                            }
                            return;
                        }
                        return;
                    case 108891:
                        if (lowerCase.equals("ncp") && thePlayer.getItemInUseDuration() > 14) {
                            for (int i2 = 0; i2 < 20; i2++) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }
                            MinecraftInstance.f157mc.getPlayerController().onStoppedUsingItem(thePlayer);
                            return;
                        }
                        return;
                    case 1957570017:
                        if (lowerCase.equals("instant")) {
                            for (int i3 = 0; i3 < 35; i3++) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }
                            MinecraftInstance.f157mc.getPlayerController().onStoppedUsingItem(thePlayer);
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
            IItemStack itemInUse = thePlayer.getItemInUse();
            if (itemInUse == null) {
                Intrinsics.throwNpe();
            }
            IItem item = itemInUse.getItem();
            IPacket packet = event.getPacket();
            if (MinecraftInstance.classProvider.isItemFood(item) || MinecraftInstance.classProvider.isItemBucketMilk(item) || MinecraftInstance.classProvider.isItemPotion(item)) {
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1010149538:
                        if (lowerCase.equals("grim-blink")) {
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
                            double posY = thePlayer3.getPosY();
                            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer4 == null) {
                                Intrinsics.throwNpe();
                            }
                            double posZ = thePlayer4.getPosZ();
                            float rotationYaw = thePlayer.getRotationYaw();
                            float rotationPitch = thePlayer.getRotationPitch();
                            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer5 == null) {
                                Intrinsics.throwNpe();
                            }
                            netHandler.addToSendQueue(iClassProvider.createCPacketPlayerPosLook(posX, posY, posZ, rotationYaw, rotationPitch, thePlayer5.getOnGround()));
                            if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
                                event.cancelEvent();
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
    public final void onMove(@Nullable MoveEvent moveEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || moveEvent == null || !getState() || !thePlayer.isUsingItem() || !((Boolean) this.noMoveValue.get()).booleanValue()) {
            return;
        }
        IItemStack itemInUse = thePlayer.getItemInUse();
        if (itemInUse == null) {
            Intrinsics.throwNpe();
        }
        IItem item = itemInUse.getItem();
        if (MinecraftInstance.classProvider.isItemFood(item) || MinecraftInstance.classProvider.isItemBucketMilk(item) || MinecraftInstance.classProvider.isItemPotion(item)) {
            moveEvent.zero();
        }
    }

    public void onDisable() {
        if (this.usedTimer) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
            this.usedTimer = false;
        }
    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get();
    }
}

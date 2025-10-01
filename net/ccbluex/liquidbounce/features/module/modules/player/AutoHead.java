package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\u0012\u0010\u001a\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoHead;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "eating", "", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "noAbsorption", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "doEat", "", "warn", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoHead", description = "Auto Eat Head(Gapple).", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoHead.class */
public final class AutoHead extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Auto", "LegitAuto", "Once", "Head"}, "Once");
    private final FloatValue healthValue = new FloatValue("Health", 10.0f, 1.0f, 20.0f);
    private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, 1000);
    private final BoolValue noAbsorption = new BoolValue("NoAbsorption", true);
    private final MSTimer timer = new MSTimer();
    private int eating = -1;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    public void onEnable() {
        this.eating = -1;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        int iFindItem;
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1961575192:
                if (lowerCase.equals("legitauto")) {
                    if (this.eating == -1) {
                        int iFindItem2 = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.GOLDEN_APPLE));
                        if (iFindItem2 == -1) {
                            return;
                        }
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindItem2 - 36));
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.OFF_HAND));
                        this.eating = 0;
                        return;
                    }
                    if (this.eating > 35) {
                        IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider iClassProvider = MinecraftInstance.classProvider;
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler.addToSendQueue(iClassProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                        this.timer.reset();
                        return;
                    }
                    return;
                }
                return;
            case 3005871:
                if (!lowerCase.equals("auto") || !this.timer.hasTimePassed(((Number) this.delayValue.get()).intValue())) {
                    return;
                }
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer2.getHealth() <= ((Number) this.healthValue.get()).floatValue()) {
                    doEat(false);
                    this.timer.reset();
                    return;
                }
                return;
            case 3198432:
                if (!lowerCase.equals("head") || !this.timer.hasTimePassed(((Number) this.delayValue.get()).intValue())) {
                    return;
                }
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer3.getHealth() <= ((Number) this.healthValue.get()).floatValue() && (iFindItem = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.SKULL))) != -1) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindItem - 36));
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.OFF_HAND));
                    IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                    IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                    IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer4 == null) {
                        Intrinsics.throwNpe();
                    }
                    netHandler2.addToSendQueue(iClassProvider2.createCPacketHeldItemChange(thePlayer4.getInventory().getCurrentItem()));
                    this.timer.reset();
                    return;
                }
                return;
            case 3415681:
                if (lowerCase.equals("once")) {
                    doEat(true);
                    setState(false);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private final void doEat(boolean z) {
        int iFindItem;
        if ((!((Boolean) this.noAbsorption.get()).booleanValue() || z) && (iFindItem = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.GOLDEN_APPLE))) != -1) {
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindItem - 36));
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.OFF_HAND));
            for (int i = 0; i < 35; i++) {
                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                netHandler.addToSendQueue(iClassProvider.createCPacketPlayer(thePlayer.getOnGround()));
            }
            IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            netHandler2.addToSendQueue(iClassProvider2.createCPacketHeldItemChange(thePlayer2.getInventory().getCurrentItem()));
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}

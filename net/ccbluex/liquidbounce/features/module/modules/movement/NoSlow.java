package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.aquavit.liquidsense.utils.Debug;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdr\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020\rH\u0002J\u0006\u0010-\u001a\u00020\rJ\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020/2\u0006\u00100\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020/2\u0006\u00100\u001a\u000205H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0014\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010!\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\"\u0010\u0016R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u00066"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Debug", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blockForwardMultiplier", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blockStrafeMultiplier", "bowForwardMultiplier", "bowStrafeMultiplier", "consumeForwardMultiplier", "consumeStrafeMultiplier", "isBlocking", "", "()Z", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "getKillAura", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "lastBlockingStat", "liquidPushValue", "getLiquidPushValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getMsTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "nextTemp", "packetBuf", "Ljava/util/LinkedList;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacketByINetHandlerPlayServer;", "soulsandValue", "getSoulsandValue", "tag", "", "getTag", "()Ljava/lang/String;", "waitC03", "getMultiplier", "", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "isForward", "isBlock", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onSlowDown", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NoSlow", description = "Cancels slowness effects caused by soulsand and using items.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoSlow.class */
public final class NoSlow extends Module {
    private final FloatValue blockForwardMultiplier = new FloatValue("BlockForwardMultiplier", 1.0f, 0.2f, 1.0f);
    private final FloatValue blockStrafeMultiplier = new FloatValue("BlockStrafeMultiplier", 1.0f, 0.2f, 1.0f);
    private final FloatValue consumeForwardMultiplier = new FloatValue("ConsumeForwardMultiplier", 1.0f, 0.2f, 1.0f);
    private final FloatValue consumeStrafeMultiplier = new FloatValue("ConsumeStrafeMultiplier", 1.0f, 0.2f, 1.0f);
    private final FloatValue bowForwardMultiplier = new FloatValue("BowForwardMultiplier", 1.0f, 0.2f, 1.0f);
    private final FloatValue bowStrafeMultiplier = new FloatValue("BowStrafeMultiplier", 1.0f, 0.2f, 1.0f);
    private final ListValue mode = new ListValue("Mode", new String[]{"Packet", "AAC5", "Vanilla", "Vulcan", "GrimAC", "OnlyC08", "Hyt"}, "Packet");
    private final BoolValue Debug = new BoolValue("Debug", false);

    @NotNull
    private final BoolValue soulsandValue = new BoolValue("Soulsand", true);

    @NotNull
    private final BoolValue liquidPushValue = new BoolValue("LiquidPush", true);

    @NotNull
    private final KillAura killAura;

    @NotNull
    private final MSTimer msTimer;
    private LinkedList packetBuf;
    private boolean nextTemp;
    private boolean waitC03;
    private boolean lastBlockingStat;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoSlow$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];
        public static final int[] $EnumSwitchMapping$1;

        static {
            $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
            $EnumSwitchMapping$1 = new int[EventState.values().length];
            $EnumSwitchMapping$1[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$1[EventState.POST.ordinal()] = 2;
        }
    }

    public NoSlow() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        this.killAura = (KillAura) module;
        this.msTimer = new MSTimer();
        this.packetBuf = new LinkedList();
    }

    @NotNull
    public final BoolValue getSoulsandValue() {
        return this.soulsandValue;
    }

    @NotNull
    public final BoolValue getLiquidPushValue() {
        return this.liquidPushValue;
    }

    @NotNull
    public final KillAura getKillAura() {
        return this.killAura;
    }

    @NotNull
    public final MSTimer getMsTimer() {
        return this.msTimer;
    }

    public final boolean isBlock() {
        Boolean thePlayerisBlocking = Debug.thePlayerisBlocking;
        Intrinsics.checkExpressionValueIsNotNull(thePlayerisBlocking, "thePlayerisBlocking");
        return thePlayerisBlocking.booleanValue() || this.killAura.getBlockingStatus();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        event.getPacket();
        if (((Boolean) this.Debug.get()).booleanValue()) {
            if (MinecraftInstance.classProvider.isCPacketPlayerDigging(event.getPacket())) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer.addChatMessage(MinecraftInstance.classProvider.createChatComponentText("C07"));
            }
            if (MinecraftInstance.classProvider.isCPacketTryUseItem(event.getPacket())) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer2.addChatMessage(MinecraftInstance.classProvider.createChatComponentText("C08"));
            }
            if (MinecraftInstance.classProvider.isCPacketPlayerBlockPlacement(event.getPacket())) {
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer3.addChatMessage(MinecraftInstance.classProvider.createChatComponentText("C08Old"));
            }
        }
    }

    private final boolean isBlocking() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isUsingItem() || this.killAura.getBlockingStatus()) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer2.getHeldItem() != null) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                IItemStack heldItem = thePlayer3.getHeldItem();
                if (heldItem == null) {
                    Intrinsics.throwNpe();
                }
                if (iClassProvider.isItemSword(heldItem.getItem())) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        IItemStack heldItem;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || (heldItem = thePlayer.getHeldItem()) == null || !MinecraftInstance.classProvider.isItemSword(heldItem.getItem()) || !isBlock()) {
            return;
        }
        String str = (String) this.mode.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1313658241:
                if (!lowerCase.equals("onlyc08")) {
                    return;
                }
                break;
            case -995865464:
                if (lowerCase.equals("packet")) {
                    switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                        case 1:
                            Debug.thePlayerisBlocking = false;
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                            Debug.thePlayerisBlocking = true;
                            return;
                        case 2:
                            Debug.thePlayerisBlocking = false;
                            thePlayer.getInventory().getCurrentItemInHand();
                            PacketImpl packetImplCreateCPacketTryUseItem = WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(WEnumHand.MAIN_HAND);
                            thePlayer.getInventory().getCurrentItemInHand();
                            PacketImpl packetImplCreateCPacketTryUseItem2 = WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(WEnumHand.OFF_HAND);
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(packetImplCreateCPacketTryUseItem);
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(packetImplCreateCPacketTryUseItem2);
                            Debug.thePlayerisBlocking = true;
                            return;
                        default:
                            return;
                    }
                }
                return;
            case 103811:
                if (lowerCase.equals("hyt") && event.getEventState() == EventState.PRE) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                    return;
                }
                return;
            case 2986066:
                if (!lowerCase.equals("aac5")) {
                    return;
                }
                break;
            default:
                return;
        }
        switch (WhenMappings.$EnumSwitchMapping$1[event.getEventState().ordinal()]) {
            case 1:
            default:
                return;
            case 2:
                Debug.thePlayerisBlocking = false;
                thePlayer.getInventory().getCurrentItemInHand();
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(WEnumHand.MAIN_HAND));
                Debug.thePlayerisBlocking = true;
                return;
        }
    }

    @EventTarget
    public final void onSlowDown(@NotNull SlowDownEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IItemStack heldItem = thePlayer.getHeldItem();
        IItem item = heldItem != null ? heldItem.getItem() : null;
        event.setForward(getMultiplier(item, true));
        event.setStrafe(getMultiplier(item, false));
    }

    private final float getMultiplier(IItem iItem, boolean z) {
        if (MinecraftInstance.classProvider.isItemFood(iItem) || MinecraftInstance.classProvider.isItemPotion(iItem) || MinecraftInstance.classProvider.isItemBucketMilk(iItem)) {
            return z ? ((Number) this.consumeForwardMultiplier.get()).floatValue() : ((Number) this.consumeStrafeMultiplier.get()).floatValue();
        }
        if (MinecraftInstance.classProvider.isItemSword(iItem)) {
            return z ? ((Number) this.blockForwardMultiplier.get()).floatValue() : ((Number) this.blockStrafeMultiplier.get()).floatValue();
        }
        if (MinecraftInstance.classProvider.isItemBow(iItem)) {
            return z ? ((Number) this.bowForwardMultiplier.get()).floatValue() : ((Number) this.bowStrafeMultiplier.get()).floatValue();
        }
        return 0.2f;
    }

    @NotNull
    public String getTag() {
        return (String) this.mode.get();
    }
}

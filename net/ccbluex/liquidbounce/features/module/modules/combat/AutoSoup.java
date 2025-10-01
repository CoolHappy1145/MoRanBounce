package net.ccbluex.liquidbounce.features.module.modules.combat;

import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoSoup;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bowlValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "simulateInventoryValue", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoSoup", description = "Makes you automatically eat soup whenever your health is low.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoSoup.class */
public final class AutoSoup extends Module {
    private final FloatValue healthValue = new FloatValue("Health", 15.0f, 0.0f, 20.0f);
    private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);
    private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);
    private final BoolValue simulateInventoryValue = new BoolValue("SimulateInventory", true);
    private final ListValue bowlValue = new ListValue("Bowl", new String[]{"Drop", "Move", "Stay"}, "Drop");
    private final MSTimer timer = new MSTimer();

    @NotNull
    public String getTag() {
        return String.valueOf(((Number) this.healthValue.get()).floatValue());
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer;
        if (this.timer.hasTimePassed(((Number) this.delayValue.get()).intValue()) && (thePlayer = MinecraftInstance.f157mc.getThePlayer()) != null) {
            int iFindItem = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.MUSHROOM_STEW));
            if (thePlayer.getHealth() <= ((Number) this.healthValue.get()).floatValue() && iFindItem != -1) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindItem - 36));
                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                thePlayer.getInventory().getStackInSlot(iFindItem);
                netHandler.addToSendQueue(WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(WEnumHand.MAIN_HAND));
                if (StringsKt.equals((String) this.bowlValue.get(), "Drop", true)) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.DROP_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                }
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                this.timer.reset();
                return;
            }
            int iFindItem2 = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.BOWL));
            if (StringsKt.equals((String) this.bowlValue.get(), "Move", true) && iFindItem2 != -1) {
                if (((Boolean) this.openInventoryValue.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen())) {
                    return;
                }
                boolean z = false;
                int i = 9;
                while (true) {
                    if (i <= 36) {
                        IItemStack stackInSlot = thePlayer.getInventory().getStackInSlot(i);
                        if (stackInSlot == null) {
                            z = true;
                            break;
                        } else if (!Intrinsics.areEqual(stackInSlot.getItem(), MinecraftInstance.classProvider.getItemEnum(ItemType.BOWL)) || stackInSlot.getStackSize() >= 64) {
                            i++;
                        } else {
                            z = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (z) {
                    if (!MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen()) && ((Boolean) this.simulateInventoryValue.get()).booleanValue()) {
                        IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
                        IEntityPlayerSP thePlayer2 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler2.addToSendQueue(classProvider.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.OPEN_INVENTORY));
                    }
                    MinecraftInstance.f157mc.getPlayerController().windowClick(0, iFindItem2, 0, 1, thePlayer);
                }
            }
            int iFindItem3 = InventoryUtils.findItem(9, 36, MinecraftInstance.classProvider.getItemEnum(ItemType.MUSHROOM_STEW));
            if (iFindItem3 != -1 && InventoryUtils.hasSpaceHotbar()) {
                if (((Boolean) this.openInventoryValue.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen())) {
                    return;
                }
                boolean z2 = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen()) && ((Boolean) this.simulateInventoryValue.get()).booleanValue();
                if (z2) {
                    IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
                    IClassProvider classProvider2 = WrapperImpl.INSTANCE.getClassProvider();
                    IEntityPlayerSP thePlayer3 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    netHandler3.addToSendQueue(classProvider2.createCPacketEntityAction(thePlayer3, ICPacketEntityAction.WAction.OPEN_INVENTORY));
                }
                MinecraftInstance.f157mc.getPlayerController().windowClick(0, iFindItem3, 0, 1, thePlayer);
                if (z2) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCloseWindow());
                }
                this.timer.reset();
            }
        }
    }
}

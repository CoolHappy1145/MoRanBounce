package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.CrossVersionUtilsKt;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorComparator;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(name = "AutoArmor", description = "Automatically equips the best armor in your inventory.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoArmor.class */
public class AutoArmor extends Module {
    public static final ArmorComparator ARMOR_COMPARATOR = new ArmorComparator();
    private long delay;
    private final IntegerValue minDelayValue = new IntegerValue(this, "MinDelay", 100, 0, 400) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor.1
        final AutoArmor this$0;

        {
            this.this$0 = this;
        }

        protected void onChanged(Object obj, Object obj2) {
            onChanged((Integer) obj, (Integer) obj2);
        }

        protected void onChanged(Integer num, Integer num2) {
            int iIntValue = ((Integer) this.this$0.maxDelayValue.get()).intValue();
            if (iIntValue < num2.intValue()) {
                set((Object) Integer.valueOf(iIntValue));
            }
        }
    };
    private final IntegerValue maxDelayValue = new IntegerValue(this, "MaxDelay", SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 0, 400) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor.2
        final AutoArmor this$0;

        {
            this.this$0 = this;
        }

        protected void onChanged(Object obj, Object obj2) {
            onChanged((Integer) obj, (Integer) obj2);
        }

        protected void onChanged(Integer num, Integer num2) {
            int iIntValue = ((Integer) this.this$0.minDelayValue.get()).intValue();
            if (iIntValue > num2.intValue()) {
                set((Object) Integer.valueOf(iIntValue));
            }
        }
    };
    private final BoolValue invOpenValue = new BoolValue("InvOpen", false);
    private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    private final IntegerValue itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
    private final BoolValue hotbarValue = new BoolValue("Hotbar", true);
    private boolean locked = false;

    @EventTarget
    public void onRender3D(Render3DEvent render3DEvent) {
        if (InventoryUtils.CLICK_TIMER.hasTimePassed(this.delay) && f157mc.getThePlayer() != null) {
            if (f157mc.getThePlayer().getOpenContainer() != null && f157mc.getThePlayer().getOpenContainer().getWindowId() != 0) {
                return;
            }
            ArmorPiece[] armorPieceArr = new ArmorPiece[4];
            for (Map.Entry entry : ((Map) IntStream.range(0, 36).filter(this::lambda$onRender3D$0).mapToObj(AutoArmor::lambda$onRender3D$1).collect(Collectors.groupingBy((v0) -> {
                return v0.getArmorType();
            }))).entrySet()) {
                armorPieceArr[((Integer) entry.getKey()).intValue()] = (ArmorPiece) ((List) entry.getValue()).stream().max(ARMOR_COMPARATOR).orElse(null);
            }
            for (int i = 0; i < 4; i++) {
                ArmorPiece armorPiece = armorPieceArr[i];
                if (armorPiece != null) {
                    int i2 = 3 - i;
                    ArmorPiece armorPiece2 = new ArmorPiece(f157mc.getThePlayer().getInventory().armorItemInSlot(i2), -1);
                    if (ItemUtils.isStackEmpty(armorPiece2.getItemStack()) || !classProvider.isItemArmor(armorPiece2.getItemStack().getItem()) || ARMOR_COMPARATOR.compare(armorPiece2, armorPiece) < 0) {
                        if (!ItemUtils.isStackEmpty(armorPiece2.getItemStack()) && move(8 - (3 - i2), true)) {
                            this.locked = true;
                            return;
                        } else if (ItemUtils.isStackEmpty(f157mc.getThePlayer().getInventory().armorItemInSlot(i2)) && move(armorPiece.getSlot(), false)) {
                            this.locked = true;
                            return;
                        }
                    }
                }
            }
            this.locked = false;
        }
    }

    private boolean lambda$onRender3D$0(int i) {
        IItemStack stackInSlot = f157mc.getThePlayer().getInventory().getStackInSlot(i);
        return stackInSlot != null && classProvider.isItemArmor(stackInSlot.getItem()) && (i < 9 || System.currentTimeMillis() - stackInSlot.getItemDelay() >= ((long) ((Integer) this.itemDelayValue.get()).intValue()));
    }

    private static ArmorPiece lambda$onRender3D$1(int i) {
        return new ArmorPiece(f157mc.getThePlayer().getInventory().getStackInSlot(i), i);
    }

    public boolean isLocked() {
        return !getState() || this.locked;
    }

    private boolean move(int i, boolean z) {
        if (!z && i < 9 && ((Boolean) this.hotbarValue.get()).booleanValue() && !classProvider.isGuiInventory(f157mc.getCurrentScreen())) {
            f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketHeldItemChange(i));
            f157mc.getNetHandler().addToSendQueue(CrossVersionUtilsKt.createUseItemPacket(f157mc.getThePlayer().getInventoryContainer().getSlot(i).getStack(), WEnumHand.MAIN_HAND));
            f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketHeldItemChange(f157mc.getThePlayer().getInventory().getCurrentItem()));
            this.delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());
            return true;
        }
        if (((Boolean) this.noMoveValue.get()).booleanValue() && MovementUtils.isMoving()) {
            return false;
        }
        if ((!((Boolean) this.invOpenValue.get()).booleanValue() || classProvider.isGuiInventory(f157mc.getCurrentScreen())) && i != -1) {
            boolean z2 = ((Boolean) this.simulateInventory.get()).booleanValue() && !classProvider.isGuiInventory(f157mc.getCurrentScreen());
            if (z2) {
                f157mc.getNetHandler().addToSendQueue(CrossVersionUtilsKt.createOpenInventoryPacket());
            }
            boolean z3 = z;
            if (z3) {
                Iterator it = f157mc.getThePlayer().getInventory().getMainInventory().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (ItemUtils.isStackEmpty((IItemStack) it.next())) {
                        z3 = false;
                        break;
                    }
                }
            }
            if (z3) {
                f157mc.getPlayerController().windowClick(f157mc.getThePlayer().getInventoryContainer().getWindowId(), i, 1, 4, f157mc.getThePlayer());
            } else {
                IPlayerControllerMP playerController = f157mc.getPlayerController();
                int windowId = f157mc.getThePlayer().getInventoryContainer().getWindowId();
                int i2 = (!z && i < 9) ? i + 36 : i;
                playerController.windowClick(windowId, i2, 0, 1, f157mc.getThePlayer());
            }
            this.delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());
            if (z2) {
                f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketCloseWindow());
                return true;
            }
            return true;
        }
        return false;
    }
}

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiChest;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IIInventory;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.util.IWrappedArray;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdj\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%H\u0003J\u0012\u0010&\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010'H\u0007J\u001b\u0010(\u001a\u00020\u00102\b\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020,H\u0082\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000f\u001a\u00020\u00108BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006-"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestStealer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoCloseMaxDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "autoCloseMinDelayValue", "autoCloseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoCloseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "chestTitleValue", "closeOnFullValue", "contentReceived", "", "delayTimer", "fullInventory", "", "getFullInventory", "()Z", "maxDelayValue", "minDelayValue", "nextCloseDelay", "", "nextDelay", "noCompassValue", "onlyItemsValue", "takeRandomizedValue", "isEmpty", "chest", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiChest;", "move", "", "screen", "slot", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "shouldTake", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "inventoryCleaner", "Lnet/ccbluex/liquidbounce/features/module/modules/player/InventoryCleaner;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "ChestStealer", description = "Automatically steals all items from a chest.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/ChestStealer.class */
public final class ChestStealer extends Module {
    private final IntegerValue maxDelayValue;
    private final IntegerValue minDelayValue;
    private final BoolValue takeRandomizedValue;
    private final BoolValue onlyItemsValue;
    private final BoolValue noCompassValue;
    private final BoolValue autoCloseValue;
    private final IntegerValue autoCloseMaxDelayValue;
    private final IntegerValue autoCloseMinDelayValue;
    private final BoolValue closeOnFullValue;
    private final BoolValue chestTitleValue;
    private final MSTimer delayTimer;
    private long nextDelay;
    private final MSTimer autoCloseTimer;
    private long nextCloseDelay;
    private int contentReceived;

    public ChestStealer() {
        final String str = "MaxDelay";
        final int i = SharedScopeCall.FAST_SCOPE_GET_THRESHOLD;
        final int i2 = 0;
        final int i3 = 400;
        this.maxDelayValue = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$maxDelayValue$1
            final ChestStealer this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i4, int i5) {
                int iIntValue = ((Number) this.this$0.minDelayValue.get()).intValue();
                if (iIntValue > i5) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.nextDelay = TimeUtils.randomDelay(((Number) this.this$0.minDelayValue.get()).intValue(), ((Number) get()).intValue());
            }
        };
        final String str2 = "MinDelay";
        final int i4 = 150;
        final int i5 = 0;
        final int i6 = 400;
        this.minDelayValue = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$minDelayValue$1
            final ChestStealer this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i7, int i8) {
                int iIntValue = ((Number) this.this$0.maxDelayValue.get()).intValue();
                if (iIntValue < i8) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.nextDelay = TimeUtils.randomDelay(((Number) get()).intValue(), ((Number) this.this$0.maxDelayValue.get()).intValue());
            }
        };
        this.takeRandomizedValue = new BoolValue("TakeRandomized", false);
        this.onlyItemsValue = new BoolValue("OnlyItems", false);
        this.noCompassValue = new BoolValue("NoCompass", false);
        this.autoCloseValue = new BoolValue("AutoClose", true);
        final String str3 = "AutoCloseMaxDelay";
        final int i7 = 0;
        final int i8 = 0;
        final int i9 = 400;
        this.autoCloseMaxDelayValue = new IntegerValue(this, str3, i7, i8, i9) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$autoCloseMaxDelayValue$1
            final ChestStealer this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i10, int i11) {
                int iIntValue = ((Number) this.this$0.autoCloseMinDelayValue.get()).intValue();
                if (iIntValue > i11) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.nextCloseDelay = TimeUtils.randomDelay(((Number) this.this$0.autoCloseMinDelayValue.get()).intValue(), ((Number) get()).intValue());
            }
        };
        final String str4 = "AutoCloseMinDelay";
        final int i10 = 0;
        final int i11 = 0;
        final int i12 = 400;
        this.autoCloseMinDelayValue = new IntegerValue(this, str4, i10, i11, i12) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$autoCloseMinDelayValue$1
            final ChestStealer this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i13, int i14) {
                int iIntValue = ((Number) this.this$0.autoCloseMaxDelayValue.get()).intValue();
                if (iIntValue < i14) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.nextCloseDelay = TimeUtils.randomDelay(((Number) get()).intValue(), ((Number) this.this$0.autoCloseMaxDelayValue.get()).intValue());
            }
        };
        this.closeOnFullValue = new BoolValue("CloseOnFull", true);
        this.chestTitleValue = new BoolValue("ChestTitle", false);
        this.delayTimer = new MSTimer();
        this.nextDelay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
        this.autoCloseTimer = new MSTimer();
        this.nextCloseDelay = TimeUtils.randomDelay(((Number) this.autoCloseMinDelayValue.get()).intValue(), ((Number) this.autoCloseMaxDelayValue.get()).intValue());
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0089  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        ArrayList arrayList;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (!MinecraftInstance.classProvider.isGuiChest(MinecraftInstance.f157mc.getCurrentScreen()) || MinecraftInstance.f157mc.getCurrentScreen() == null || !this.delayTimer.hasTimePassed(this.nextDelay)) {
            this.autoCloseTimer.reset();
            return;
        }
        IGuiScreen currentScreen = MinecraftInstance.f157mc.getCurrentScreen();
        if (currentScreen == null) {
            Intrinsics.throwNpe();
        }
        IGuiChest iGuiChestAsGuiChest = currentScreen.asGuiChest();
        if (((Boolean) this.noCompassValue.get()).booleanValue()) {
            IItemStack currentItemInHand = thePlayer.getInventory().getCurrentItemInHand();
            if (currentItemInHand != null) {
                IItem item = currentItemInHand.getItem();
                String unlocalizedName = item != null ? item.getUnlocalizedName() : null;
                if (Intrinsics.areEqual(unlocalizedName, "item.compass")) {
                    return;
                }
            }
        }
        if (((Boolean) this.chestTitleValue.get()).booleanValue()) {
            if (iGuiChestAsGuiChest.getLowerChestInventory() == null) {
                return;
            }
            IIInventory lowerChestInventory = iGuiChestAsGuiChest.getLowerChestInventory();
            if (lowerChestInventory == null) {
                Intrinsics.throwNpe();
            }
            String name = lowerChestInventory.getName();
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IItem objectFromItemRegistry = MinecraftInstance.functions.getObjectFromItemRegistry(MinecraftInstance.classProvider.createResourceLocation("minecraft:chest"));
            if (objectFromItemRegistry == null) {
                Intrinsics.throwNpe();
            }
            if (!StringsKt.contains$default((CharSequence) name, (CharSequence) iClassProvider.createItemStack(objectFromItemRegistry).getDisplayName(), false, 2, (Object) null)) {
                return;
            }
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(InventoryCleaner.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner");
        }
        InventoryCleaner inventoryCleaner = (InventoryCleaner) module;
        if (!isEmpty(iGuiChestAsGuiChest) && (!((Boolean) this.closeOnFullValue.get()).booleanValue() || !getFullInventory())) {
            this.autoCloseTimer.reset();
            if (((Boolean) this.takeRandomizedValue.get()).booleanValue()) {
                do {
                    arrayList = new ArrayList();
                    int inventoryRows = iGuiChestAsGuiChest.getInventoryRows() * 9;
                    for (int i = 0; i < inventoryRows; i++) {
                        IContainer inventorySlots = iGuiChestAsGuiChest.getInventorySlots();
                        if (inventorySlots == null) {
                            Intrinsics.throwNpe();
                        }
                        ISlot slot = inventorySlots.getSlot(i);
                        IItemStack stack = slot.getStack();
                        if (stack != null && ((!((Boolean) this.onlyItemsValue.get()).booleanValue() || !MinecraftInstance.classProvider.isItemBlock(stack.getItem())) && (!inventoryCleaner.getState() || inventoryCleaner.isUseful(stack, -1)))) {
                            arrayList.add(slot);
                        }
                    }
                    move(iGuiChestAsGuiChest, (ISlot) arrayList.get(Random.Default.nextInt(arrayList.size())));
                    if (!this.delayTimer.hasTimePassed(this.nextDelay)) {
                        return;
                    }
                } while (!arrayList.isEmpty());
                return;
            }
            int inventoryRows2 = iGuiChestAsGuiChest.getInventoryRows() * 9;
            for (int i2 = 0; i2 < inventoryRows2; i2++) {
                IContainer inventorySlots2 = iGuiChestAsGuiChest.getInventorySlots();
                if (inventorySlots2 == null) {
                    Intrinsics.throwNpe();
                }
                ISlot slot2 = inventorySlots2.getSlot(i2);
                IItemStack stack2 = slot2.getStack();
                if (this.delayTimer.hasTimePassed(this.nextDelay)) {
                    if ((stack2 == null || ItemUtils.isStackEmpty(stack2) || (((Boolean) this.onlyItemsValue.get()).booleanValue() && MinecraftInstance.classProvider.isItemBlock(stack2.getItem())) || (inventoryCleaner.getState() && !inventoryCleaner.isUseful(stack2, -1))) ? false : true) {
                        move(iGuiChestAsGuiChest, slot2);
                    }
                }
            }
            return;
        }
        if (((Boolean) this.autoCloseValue.get()).booleanValue()) {
            IContainer inventorySlots3 = iGuiChestAsGuiChest.getInventorySlots();
            if (inventorySlots3 == null) {
                Intrinsics.throwNpe();
            }
            if (inventorySlots3.getWindowId() == this.contentReceived && this.autoCloseTimer.hasTimePassed(this.nextCloseDelay)) {
                thePlayer.closeScreen();
                this.nextCloseDelay = TimeUtils.randomDelay(((Number) this.autoCloseMinDelayValue.get()).intValue(), ((Number) this.autoCloseMaxDelayValue.get()).intValue());
            }
        }
    }

    @EventTarget
    private final void onPacket(PacketEvent packetEvent) {
        IPacket packet = packetEvent.getPacket();
        if (MinecraftInstance.classProvider.isSPacketWindowItems(packet)) {
            this.contentReceived = packet.asSPacketWindowItems().getWindowId();
        }
    }

    private final boolean shouldTake(IItemStack iItemStack, InventoryCleaner inventoryCleaner) {
        return (iItemStack == null || ItemUtils.isStackEmpty(iItemStack) || (((Boolean) this.onlyItemsValue.get()).booleanValue() && MinecraftInstance.classProvider.isItemBlock(iItemStack.getItem())) || (inventoryCleaner.getState() && !inventoryCleaner.isUseful(iItemStack, -1))) ? false : true;
    }

    private final void move(IGuiChest iGuiChest, ISlot iSlot) {
        iGuiChest.handleMouseClick(iSlot, iSlot.getSlotNumber(), 0, 1);
        this.delayTimer.reset();
        this.nextDelay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
    }

    private final boolean isEmpty(IGuiChest iGuiChest) {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(InventoryCleaner.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner");
        }
        InventoryCleaner inventoryCleaner = (InventoryCleaner) module;
        int inventoryRows = iGuiChest.getInventoryRows() * 9;
        for (int i = 0; i < inventoryRows; i++) {
            IContainer inventorySlots = iGuiChest.getInventorySlots();
            if (inventorySlots == null) {
                Intrinsics.throwNpe();
            }
            IItemStack stack = inventorySlots.getSlot(i).getStack();
            if ((stack == null || ItemUtils.isStackEmpty(stack) || (((Boolean) this.onlyItemsValue.get()).booleanValue() && MinecraftInstance.classProvider.isItemBlock(stack.getItem())) || (inventoryCleaner.getState() && !inventoryCleaner.isUseful(stack, -1))) ? false : true) {
                return false;
            }
        }
        return true;
    }

    private final boolean getFullInventory() {
        IWrappedArray mainInventory;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            IInventoryPlayer inventory = thePlayer.getInventory();
            if (inventory != null && (mainInventory = inventory.getMainInventory()) != null) {
                IWrappedArray iWrappedArray = mainInventory;
                if ((iWrappedArray instanceof Collection) && ((Collection) iWrappedArray).isEmpty()) {
                    return true;
                }
                Iterator it = iWrappedArray.iterator();
                while (it.hasNext()) {
                    if (ItemUtils.isStackEmpty((IItemStack) it.next())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

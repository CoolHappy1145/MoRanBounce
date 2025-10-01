package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.entity.p004ai.attributes.IAttributeModifier;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdb\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J!\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020 2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002\u00a2\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020#2\u0006\u0010(\u001a\u00020 J(\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020#0)2\b\b\u0002\u0010*\u001a\u00020 2\b\b\u0002\u0010+\u001a\u00020 H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0007J\b\u00100\u001a\u00020-H\u0002J\u0010\u00101\u001a\u00020\r2\u0006\u0010!\u001a\u00020 H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u00062"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/InventoryCleaner;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "hotbarValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "ignoreVehiclesValue", "invOpenValue", "itemDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "items", "", "", "[Ljava/lang/String;", "maxDelayValue", "minDelayValue", "noMoveValue", "randomSlotValue", "simulateInventory", "sortSlot1Value", "Lnet/ccbluex/liquidbounce/value/ListValue;", "sortSlot2Value", "sortSlot3Value", "sortSlot4Value", "sortSlot5Value", "sortSlot6Value", "sortSlot7Value", "sortSlot8Value", "sortSlot9Value", "sortValue", "findBetterItem", "", "targetSlot", "slotStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "(ILnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;)Ljava/lang/Integer;", "isUseful", "", "itemStack", "slot", "", "start", AsmConstants.END, "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sortHotbar", "type", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "InventoryCleaner", description = "Automatically throws away useless items.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/InventoryCleaner.class */
public final class InventoryCleaner extends Module {
    private final IntegerValue maxDelayValue;
    private final IntegerValue minDelayValue;
    private final BoolValue invOpenValue = new BoolValue("InvOpen", false);
    private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    private final BoolValue ignoreVehiclesValue = new BoolValue("IgnoreVehicles", false);
    private final BoolValue hotbarValue = new BoolValue("Hotbar", true);
    private final BoolValue randomSlotValue = new BoolValue("RandomSlot", false);
    private final BoolValue sortValue = new BoolValue("Sort", true);
    private final IntegerValue itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
    private final String[] items = {"None", "Ignore", "Sword", "Bow", "Pickaxe", "Axe", "Food", "Block", "Water", "Gapple", "Pearl"};
    private final ListValue sortSlot1Value = new ListValue("SortSlot-1", this.items, "Sword");
    private final ListValue sortSlot2Value = new ListValue("SortSlot-2", this.items, "Bow");
    private final ListValue sortSlot3Value = new ListValue("SortSlot-3", this.items, "Pickaxe");
    private final ListValue sortSlot4Value = new ListValue("SortSlot-4", this.items, "Axe");
    private final ListValue sortSlot5Value = new ListValue("SortSlot-5", this.items, "None");
    private final ListValue sortSlot6Value = new ListValue("SortSlot-6", this.items, "None");
    private final ListValue sortSlot7Value = new ListValue("SortSlot-7", this.items, "Food");
    private final ListValue sortSlot8Value = new ListValue("SortSlot-8", this.items, "Block");
    private final ListValue sortSlot9Value = new ListValue("SortSlot-9", this.items, "Block");
    private long delay;

    public InventoryCleaner() {
        final String str = "MaxDelay";
        final int i = 600;
        final int i2 = 0;
        final int i3 = 1000;
        this.maxDelayValue = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner$maxDelayValue$1
            final InventoryCleaner this$0;

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
            }
        };
        final String str2 = "MinDelay";
        final int i4 = 400;
        final int i5 = 0;
        final int i6 = 1000;
        this.minDelayValue = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner$minDelayValue$1
            final InventoryCleaner this$0;

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
            }
        };
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && InventoryUtils.CLICK_TIMER.hasTimePassed(this.delay)) {
            if (MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen()) || !((Boolean) this.invOpenValue.get()).booleanValue()) {
                if (!((Boolean) this.noMoveValue.get()).booleanValue() || !MovementUtils.isMoving()) {
                    if (thePlayer.getOpenContainer() != null) {
                        IContainer openContainer = thePlayer.getOpenContainer();
                        if (openContainer == null) {
                            Intrinsics.throwNpe();
                        }
                        if (openContainer.getWindowId() != 0) {
                            return;
                        }
                    }
                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(AutoArmor.class);
                    if (module == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor");
                    }
                    if (((AutoArmor) module).isLocked()) {
                        return;
                    }
                    if (((Boolean) this.sortValue.get()).booleanValue()) {
                        sortHotbar();
                    }
                    while (InventoryUtils.CLICK_TIMER.hasTimePassed(this.delay)) {
                        Map mapItems = items(9, ((Boolean) this.hotbarValue.get()).booleanValue() ? 45 : 36);
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        for (Map.Entry entry : mapItems.entrySet()) {
                            if (!isUseful((IItemStack) entry.getValue(), ((Number) entry.getKey()).intValue())) {
                                linkedHashMap.put(entry.getKey(), entry.getValue());
                            }
                        }
                        List mutableList = CollectionsKt.toMutableList((Collection) linkedHashMap.keySet());
                        if (((Boolean) this.randomSlotValue.get()).booleanValue()) {
                            Collections.shuffle(mutableList);
                        }
                        Integer num = (Integer) CollectionsKt.firstOrNull(mutableList);
                        if (num != null) {
                            int iIntValue = num.intValue();
                            boolean z = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen()) && ((Boolean) this.simulateInventory.get()).booleanValue();
                            if (z) {
                                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
                                IEntityPlayerSP thePlayer2 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler.addToSendQueue(classProvider.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.OPEN_INVENTORY));
                            }
                            IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                            IContainer openContainer2 = thePlayer.getOpenContainer();
                            if (openContainer2 == null) {
                                Intrinsics.throwNpe();
                            }
                            playerController.windowClick(openContainer2.getWindowId(), iIntValue, 1, 4, thePlayer);
                            if (z) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCloseWindow());
                            }
                            this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:186:0x01ca A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:189:? A[LOOP:4: B:49:0x0149->B:189:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isUseful(@NotNull IItemStack itemStack, int i) {
        boolean z;
        IItem item;
        boolean z2;
        boolean z3;
        boolean z4;
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");
        try {
            item = itemStack.getItem();
        } catch (Exception e) {
            ClientUtils.getLogger().error("(InventoryCleaner) Failed to check item: " + itemStack.getUnlocalizedName() + '.', e);
            z = true;
        }
        if (MinecraftInstance.classProvider.isItemSword(item) || MinecraftInstance.classProvider.isItemTool(item)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                return true;
            }
            if (i >= 36) {
                Integer numFindBetterItem = findBetterItem(i - 36, thePlayer.getInventory().getStackInSlot(i - 36));
                int i2 = i - 36;
                if (numFindBetterItem != null && numFindBetterItem.intValue() == i2) {
                    return true;
                }
            }
            for (int i3 = 0; i3 <= 8; i3++) {
                if (((StringsKt.equals(type(i3), "sword", true) && MinecraftInstance.classProvider.isItemSword(item)) || ((StringsKt.equals(type(i3), "pickaxe", true) && MinecraftInstance.classProvider.isItemPickaxe(item)) || (StringsKt.equals(type(i3), "axe", true) && MinecraftInstance.classProvider.isItemAxe(item)))) && findBetterItem(i3, thePlayer.getInventory().getStackInSlot(i3)) == null) {
                    return true;
                }
            }
            IAttributeModifier iAttributeModifier = (IAttributeModifier) CollectionsKt.firstOrNull(itemStack.getAttributeModifier("generic.attackDamage"));
            double amount = (iAttributeModifier != null ? iAttributeModifier.getAmount() : 0.0d) + (1.25d * ItemUtils.getEnchantment(itemStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS)));
            Map mapItems = items(0, 45);
            if (!mapItems.isEmpty()) {
                Iterator it = mapItems.entrySet().iterator();
                while (true) {
                    if (it.hasNext()) {
                        IItemStack iItemStack = (IItemStack) ((Map.Entry) it.next()).getValue();
                        if (!(!Intrinsics.areEqual(iItemStack, itemStack)) || !Intrinsics.areEqual(iItemStack.getClass(), itemStack.getClass())) {
                            z3 = false;
                            if (!z3) {
                                z2 = false;
                                break;
                            }
                        } else {
                            IAttributeModifier iAttributeModifier2 = (IAttributeModifier) CollectionsKt.firstOrNull(iItemStack.getAttributeModifier("generic.attackDamage"));
                            if (amount < (iAttributeModifier2 != null ? iAttributeModifier2.getAmount() : 0.0d) + (1.25d * ItemUtils.getEnchantment(iItemStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS)))) {
                                z3 = true;
                            }
                            if (!z3) {
                            }
                        }
                        return z;
                    }
                    z2 = true;
                    break;
                }
            }
            z2 = true;
        } else if (MinecraftInstance.classProvider.isItemBow(item)) {
            int enchantment = ItemUtils.getEnchantment(itemStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER));
            Map mapItems$default = items$default(this, 0, 0, 3, null);
            if (!mapItems$default.isEmpty()) {
                Iterator it2 = mapItems$default.entrySet().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        IItemStack iItemStack2 = (IItemStack) ((Map.Entry) it2.next()).getValue();
                        if ((Intrinsics.areEqual(itemStack, iItemStack2) ^ true) && MinecraftInstance.classProvider.isItemBow(iItemStack2.getItem()) && enchantment < ItemUtils.getEnchantment(iItemStack2, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER))) {
                            z2 = false;
                            break;
                        }
                    } else {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = true;
            }
        } else if (MinecraftInstance.classProvider.isItemArmor(item)) {
            ArmorPiece armorPiece = new ArmorPiece(itemStack, i);
            Map mapItems$default2 = items$default(this, 0, 0, 3, null);
            if (!mapItems$default2.isEmpty()) {
                Iterator it3 = mapItems$default2.entrySet().iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Map.Entry entry = (Map.Entry) it3.next();
                        int iIntValue = ((Number) entry.getKey()).intValue();
                        IItemStack iItemStack3 = (IItemStack) entry.getValue();
                        if ((!Intrinsics.areEqual(iItemStack3, itemStack)) && MinecraftInstance.classProvider.isItemArmor(iItemStack3.getItem())) {
                            ArmorPiece armorPiece2 = new ArmorPiece(iItemStack3, iIntValue);
                            if (armorPiece2.getArmorType() != armorPiece.getArmorType()) {
                                z4 = false;
                            } else {
                                z4 = AutoArmor.ARMOR_COMPARATOR.compare(armorPiece, armorPiece2) <= 0;
                            }
                        } else {
                            z4 = false;
                        }
                        if (z4) {
                            z2 = false;
                            break;
                        }
                    } else {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = true;
            }
        } else if (Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.compass")) {
            Map mapItems2 = items(0, 45);
            if (!mapItems2.isEmpty()) {
                Iterator it4 = mapItems2.entrySet().iterator();
                while (true) {
                    if (it4.hasNext()) {
                        IItemStack iItemStack4 = (IItemStack) ((Map.Entry) it4.next()).getValue();
                        if ((Intrinsics.areEqual(itemStack, iItemStack4) ^ true) && Intrinsics.areEqual(iItemStack4.getUnlocalizedName(), "item.compass")) {
                            z2 = false;
                            break;
                        }
                    } else {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = true;
            }
        } else {
            z2 = MinecraftInstance.classProvider.isItemFood(item) || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.arrow") || (MinecraftInstance.classProvider.isItemBlock(item) && !StringsKt.contains$default((CharSequence) itemStack.getUnlocalizedName(), (CharSequence) "flower", false, 2, (Object) null)) || MinecraftInstance.classProvider.isItemBed(item) || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.diamond") || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.ingotIron") || MinecraftInstance.classProvider.isItemPotion(item) || MinecraftInstance.classProvider.isItemEnderPearl(item) || MinecraftInstance.classProvider.isItemEnchantedBook(item) || MinecraftInstance.classProvider.isItemBucket(item) || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.stick") || (((Boolean) this.ignoreVehiclesValue.get()).booleanValue() && (MinecraftInstance.classProvider.isItemBoat(item) || MinecraftInstance.classProvider.isItemMinecart(item)));
        }
        z = z2;
        return z;
    }

    private final void sortHotbar() {
        IEntityPlayerSP thePlayer;
        for (int i = 0; i <= 8 && (thePlayer = MinecraftInstance.f157mc.getThePlayer()) != null; i++) {
            Integer numFindBetterItem = findBetterItem(i, thePlayer.getInventory().getStackInSlot(i));
            if (numFindBetterItem != null) {
                int iIntValue = numFindBetterItem.intValue();
                if (iIntValue != i) {
                    boolean z = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen()) && ((Boolean) this.simulateInventory.get()).booleanValue();
                    if (z) {
                        IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
                        IEntityPlayerSP thePlayer2 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler.addToSendQueue(classProvider.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.OPEN_INVENTORY));
                    }
                    MinecraftInstance.f157mc.getPlayerController().windowClick(0, iIntValue < 9 ? iIntValue + 36 : iIntValue, i, 2, thePlayer);
                    if (z) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCloseWindow());
                    }
                    this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
                    return;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:247:0x069b  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x074d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final Integer findBetterItem(int i, IItemStack iItemStack) {
        boolean z;
        boolean z2;
        int enchantment;
        InventoryCleaner$findBetterItem$currentTypeChecker$3 inventoryCleaner$findBetterItem$currentTypeChecker$3;
        int i2;
        String strType = type(i);
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            return null;
        }
        if (strType == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = strType.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1253135533:
                if (!lowerCase.equals("gapple")) {
                    return null;
                }
                int i3 = 0;
                for (Object obj : thePlayer.getInventory().getMainInventory()) {
                    int i4 = i3;
                    i3++;
                    if (i4 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IItemStack iItemStack2 = (IItemStack) obj;
                    if (iItemStack2 != null) {
                        IItem item = iItemStack2.getItem();
                        if (item == null) {
                            Intrinsics.throwNpe();
                        }
                        if (MinecraftInstance.classProvider.isItemAppleGold(item) && !StringsKt.equals(type(i4), "Gapple", true)) {
                            if (!ItemUtils.isStackEmpty(iItemStack)) {
                                z2 = !MinecraftInstance.classProvider.isItemAppleGold(iItemStack != null ? iItemStack.getItem() : null);
                            }
                            if (z2) {
                                return Integer.valueOf(i4);
                            }
                            return null;
                        }
                    }
                }
                return null;
            case -578028723:
                if (!lowerCase.equals("pickaxe")) {
                    return null;
                }
                break;
            case 97038:
                if (!lowerCase.equals("axe")) {
                    return null;
                }
                break;
            case 97738:
                if (lowerCase.equals("bow")) {
                    int i5 = MinecraftInstance.classProvider.isItemBow(iItemStack != null ? iItemStack.getItem() : null) ? i : -1;
                    if (i5 != -1) {
                        enchantment = ItemUtils.getEnchantment(iItemStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER));
                    } else {
                        enchantment = 0;
                    }
                    int i6 = enchantment;
                    int i7 = 0;
                    for (Object obj2 : thePlayer.getInventory().getMainInventory()) {
                        int i8 = i7;
                        i7++;
                        if (i8 < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        IItemStack iItemStack3 = (IItemStack) obj2;
                        if (MinecraftInstance.classProvider.isItemBow(iItemStack3 != null ? iItemStack3.getItem() : null) && !StringsKt.equals(type(i8), strType, true)) {
                            if (i5 == -1) {
                                i5 = i8;
                            } else {
                                int enchantment2 = ItemUtils.getEnchantment(iItemStack3, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER));
                                if (ItemUtils.getEnchantment(iItemStack3, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER)) > i6) {
                                    i5 = i8;
                                    i6 = enchantment2;
                                }
                            }
                        }
                    }
                    if (i5 != -1) {
                        return Integer.valueOf(i5);
                    }
                    return null;
                }
                return null;
            case 3148894:
                if (!lowerCase.equals("food")) {
                    return null;
                }
                int i9 = 0;
                for (Object obj3 : thePlayer.getInventory().getMainInventory()) {
                    int i10 = i9;
                    i9++;
                    if (i10 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IItemStack iItemStack4 = (IItemStack) obj3;
                    if (iItemStack4 != null) {
                        IItem item2 = iItemStack4.getItem();
                        if (MinecraftInstance.classProvider.isItemFood(item2) && !MinecraftInstance.classProvider.isItemAppleGold(item2) && !StringsKt.equals(type(i10), "Food", true)) {
                            if (ItemUtils.isStackEmpty(iItemStack) || !MinecraftInstance.classProvider.isItemFood(item2)) {
                                return Integer.valueOf(i10);
                            }
                            return null;
                        }
                    }
                }
                return null;
            case 93832333:
                if (!lowerCase.equals("block")) {
                    return null;
                }
                int i11 = 0;
                for (Object obj4 : thePlayer.getInventory().getMainInventory()) {
                    int i12 = i11;
                    i11++;
                    if (i12 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IItemStack iItemStack5 = (IItemStack) obj4;
                    if (iItemStack5 != null) {
                        IItem item3 = iItemStack5.getItem();
                        if (item3 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (MinecraftInstance.classProvider.isItemBlock(item3) && !InventoryUtils.BLOCK_BLACKLIST.contains(item3.asItemBlock().getBlock()) && !StringsKt.equals(type(i12), "Block", true)) {
                            if (ItemUtils.isStackEmpty(iItemStack) || !MinecraftInstance.classProvider.isItemBlock(item3)) {
                                return Integer.valueOf(i12);
                            }
                            return null;
                        }
                    }
                }
                return null;
            case 106540102:
                if (!lowerCase.equals("pearl")) {
                    return null;
                }
                int i13 = 0;
                for (Object obj5 : thePlayer.getInventory().getMainInventory()) {
                    int i14 = i13;
                    i13++;
                    if (i14 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IItemStack iItemStack6 = (IItemStack) obj5;
                    if (iItemStack6 != null) {
                        if (MinecraftInstance.classProvider.isItemEnderPearl(iItemStack6.getItem()) && !StringsKt.equals(type(i14), "Pearl", true)) {
                            if (!ItemUtils.isStackEmpty(iItemStack)) {
                                z = !MinecraftInstance.classProvider.isItemEnderPearl(iItemStack != null ? iItemStack.getItem() : null);
                            }
                            if (z) {
                                return Integer.valueOf(i14);
                            }
                            return null;
                        }
                    }
                }
                return null;
            case 109860349:
                if (!lowerCase.equals("sword")) {
                    return null;
                }
                break;
            case 112903447:
                if (!lowerCase.equals("water")) {
                    return null;
                }
                int i15 = 0;
                for (Object obj6 : thePlayer.getInventory().getMainInventory()) {
                    int i16 = i15;
                    i15++;
                    if (i16 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IItemStack iItemStack7 = (IItemStack) obj6;
                    if (iItemStack7 != null) {
                        IItem item4 = iItemStack7.getItem();
                        if (item4 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (MinecraftInstance.classProvider.isItemBucket(item4) && Intrinsics.areEqual(item4.asItemBucket().isFull(), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_WATER)) && !StringsKt.equals(type(i16), "Water", true)) {
                            if (ItemUtils.isStackEmpty(iItemStack) || !MinecraftInstance.classProvider.isItemBucket(item4) || (Intrinsics.areEqual(item4.asItemBucket().isFull(), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_WATER)) ^ true)) {
                                return Integer.valueOf(i16);
                            }
                            return null;
                        }
                    }
                }
                return null;
            default:
                return null;
        }
        if (StringsKt.equals(strType, "Sword", true)) {
            inventoryCleaner$findBetterItem$currentTypeChecker$3 = new Function1() { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner$findBetterItem$currentTypeChecker$1
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj7) {
                    return Boolean.valueOf(invoke((IItem) obj7));
                }

                public final boolean invoke(@Nullable IItem iItem) {
                    return MinecraftInstance.classProvider.isItemSword(iItem);
                }
            };
        } else if (StringsKt.equals(strType, "Pickaxe", true)) {
            inventoryCleaner$findBetterItem$currentTypeChecker$3 = new Function1() { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner$findBetterItem$currentTypeChecker$2
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj7) {
                    return Boolean.valueOf(invoke((IItem) obj7));
                }

                public final boolean invoke(@Nullable IItem iItem) {
                    return MinecraftInstance.classProvider.isItemPickaxe(iItem);
                }
            };
        } else {
            if (!StringsKt.equals(strType, "Axe", true)) {
                return null;
            }
            inventoryCleaner$findBetterItem$currentTypeChecker$3 = new Function1() { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner$findBetterItem$currentTypeChecker$3
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj7) {
                    return Boolean.valueOf(invoke((IItem) obj7));
                }

                public final boolean invoke(@Nullable IItem iItem) {
                    return MinecraftInstance.classProvider.isItemAxe(iItem);
                }
            };
        }
        Function1 function1 = inventoryCleaner$findBetterItem$currentTypeChecker$3;
        if (((Boolean) function1.invoke(iItemStack != null ? iItemStack.getItem() : null)).booleanValue()) {
            i2 = i;
        } else {
            i2 = -1;
        }
        int i17 = i2;
        int i18 = 0;
        for (Object obj7 : thePlayer.getInventory().getMainInventory()) {
            int i19 = i18;
            i18++;
            if (i19 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IItemStack iItemStack8 = (IItemStack) obj7;
            if (iItemStack8 != null && ((Boolean) function1.invoke(iItemStack8.getItem())).booleanValue() && !StringsKt.equals(type(i19), strType, true)) {
                if (i17 == -1) {
                    i17 = i19;
                } else {
                    IAttributeModifier iAttributeModifier = (IAttributeModifier) CollectionsKt.firstOrNull(iItemStack8.getAttributeModifier("generic.attackDamage"));
                    double amount = (iAttributeModifier != null ? iAttributeModifier.getAmount() : 0.0d) + (1.25d * ItemUtils.getEnchantment(iItemStack8, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS)));
                    IItemStack stackInSlot = thePlayer.getInventory().getStackInSlot(i17);
                    if (stackInSlot != null) {
                        IAttributeModifier iAttributeModifier2 = (IAttributeModifier) CollectionsKt.firstOrNull(stackInSlot.getAttributeModifier("generic.attackDamage"));
                        if ((iAttributeModifier2 != null ? iAttributeModifier2.getAmount() : 0.0d) + (1.25d * ItemUtils.getEnchantment(stackInSlot, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS))) < amount) {
                            i17 = i19;
                        }
                    }
                }
            }
        }
        if (i17 != -1 || i17 == i) {
            return Integer.valueOf(i17);
        }
        return null;
    }

    static Map items$default(InventoryCleaner inventoryCleaner, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 45;
        }
        return inventoryCleaner.items(i, i2);
    }

    private final Map items(int i, int i2) {
        IItemStack stack;
        int i3;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i4 = i2 - 1;
        if (i4 >= i) {
            while (true) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer != null) {
                    IContainer inventoryContainer = thePlayer.getInventoryContainer();
                    if (inventoryContainer != null) {
                        ISlot slot = inventoryContainer.getSlot(i4);
                        if (slot != null && (stack = slot.getStack()) != null && !ItemUtils.isStackEmpty(stack) && ((36 > (i3 = i4) || 44 < i3 || !StringsKt.equals(type(i4), "Ignore", true)) && System.currentTimeMillis() - stack.getItemDelay() >= ((Number) this.itemDelayValue.get()).longValue())) {
                            linkedHashMap.put(Integer.valueOf(i4), stack);
                        }
                    }
                }
                if (i4 == i) {
                    break;
                }
                i4--;
            }
        }
        return linkedHashMap;
    }

    private final String type(int i) {
        switch (i) {
            case 0:
                return (String) this.sortSlot1Value.get();
            case 1:
                return (String) this.sortSlot2Value.get();
            case 2:
                return (String) this.sortSlot3Value.get();
            case 3:
                return (String) this.sortSlot4Value.get();
            case 4:
                return (String) this.sortSlot5Value.get();
            case 5:
                return (String) this.sortSlot6Value.get();
            case 6:
                return (String) this.sortSlot7Value.get();
            case 7:
                return (String) this.sortSlot8Value.get();
            case 8:
                return (String) this.sortSlot9Value.get();
            default:
                return "";
        }
    }
}

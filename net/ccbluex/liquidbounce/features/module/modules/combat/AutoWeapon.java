package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.entity.p004ai.attributes.IAttributeModifier;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoWeapon;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackEnemy", "", "silentValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "spoofedSlot", "", "ticksValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "update", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoWeapon", description = "Automatically selects the best weapon in your hotbar.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoWeapon.class */
public final class AutoWeapon extends Module {
    private final BoolValue silentValue = new BoolValue("SpoofItem", false);
    private final IntegerValue ticksValue = new IntegerValue("SpoofTicks", 10, 1, 20);
    private boolean attackEnemy;
    private int spoofedSlot;

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.attackEnemy = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x013d  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPacket(@NotNull PacketEvent event) {
        Object obj;
        boolean z;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isCPacketUseEntity(event.getPacket()) && event.getPacket().asCPacketUseEntity().getAction() == ICPacketUseEntity.WAction.ATTACK && this.attackEnemy) {
            this.attackEnemy = false;
            IntRange intRange = new IntRange(0, 8);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
            Iterator it = intRange.iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                Integer numValueOf = Integer.valueOf(iNextInt);
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                arrayList.add(new Pair(numValueOf, thePlayer.getInventory().getStackInSlot(iNextInt)));
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : arrayList2) {
                Pair pair = (Pair) obj2;
                if (pair.getSecond() != null) {
                    IClassProvider iClassProvider = MinecraftInstance.classProvider;
                    Object second = pair.getSecond();
                    if (second == null) {
                        Intrinsics.throwNpe();
                    }
                    if (!iClassProvider.isItemSword(((IItemStack) second).getItem())) {
                        IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                        Object second2 = pair.getSecond();
                        if (second2 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (!iClassProvider2.isItemTool(((IItemStack) second2).getItem())) {
                            z = false;
                        }
                    }
                    z = true;
                }
                if (z) {
                    arrayList3.add(obj2);
                }
            }
            Iterator it2 = arrayList3.iterator();
            if (it2.hasNext()) {
                Object next = it2.next();
                if (it2.hasNext()) {
                    Object second3 = ((Pair) next).getSecond();
                    if (second3 == null) {
                        Intrinsics.throwNpe();
                    }
                    double amount = ((IAttributeModifier) CollectionsKt.first(((IItemStack) second3).getAttributeModifier("generic.attackDamage"))).getAmount() + (1.25d * ItemUtils.getEnchantment((IItemStack) r0.getSecond(), MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS)));
                    do {
                        Object next2 = it2.next();
                        Object second4 = ((Pair) next2).getSecond();
                        if (second4 == null) {
                            Intrinsics.throwNpe();
                        }
                        double amount2 = ((IAttributeModifier) CollectionsKt.first(((IItemStack) second4).getAttributeModifier("generic.attackDamage"))).getAmount() + (1.25d * ItemUtils.getEnchantment((IItemStack) r0.getSecond(), MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS)));
                        if (Double.compare(amount, amount2) < 0) {
                            next = next2;
                            amount = amount2;
                        }
                    } while (it2.hasNext());
                    obj = next;
                } else {
                    obj = next;
                }
            } else {
                obj = null;
            }
            Pair pair2 = (Pair) obj;
            if (pair2 != null) {
                int iIntValue = ((Number) pair2.component1()).intValue();
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (iIntValue == thePlayer2.getInventory().getCurrentItem()) {
                    return;
                }
                if (((Boolean) this.silentValue.get()).booleanValue()) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iIntValue));
                    this.spoofedSlot = ((Number) this.ticksValue.get()).intValue();
                } else {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer3.getInventory().setCurrentItem(iIntValue);
                    MinecraftInstance.f157mc.getPlayerController().updateController();
                }
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(event.getPacket());
                event.cancelEvent();
            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent update) {
        Intrinsics.checkParameterIsNotNull(update, "update");
        if (this.spoofedSlot > 0) {
            if (this.spoofedSlot == 1) {
                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                netHandler.addToSendQueue(iClassProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
            }
            this.spoofedSlot--;
        }
    }
}

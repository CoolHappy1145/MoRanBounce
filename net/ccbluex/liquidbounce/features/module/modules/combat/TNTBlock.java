package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/TNTBlock;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoSwordValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blocked", "", "fuseValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onMotionUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "TNTBlock", description = "Automatically blocks with your sword when TNT around you explodes.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/TNTBlock.class */
public final class TNTBlock extends Module {
    private final IntegerValue fuseValue = new IntegerValue("Fuse", 10, 0, 80);
    private final FloatValue rangeValue = new FloatValue("Range", 9.0f, 1.0f, 20.0f);
    private final BoolValue autoSwordValue = new BoolValue("AutoSword", true);
    private boolean blocked;

    @EventTarget
    public final void onMotionUpdate(@Nullable MotionEvent motionEvent) {
        IWorldClient theWorld;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || (theWorld = MinecraftInstance.f157mc.getTheWorld()) == null) {
            return;
        }
        for (IEntity iEntity : theWorld.getLoadedEntityList()) {
            if (MinecraftInstance.classProvider.isEntityTNTPrimed(iEntity) && thePlayer.getDistanceToEntity(iEntity) <= ((Number) this.rangeValue.get()).floatValue() && iEntity.asEntityTNTPrimed().getFuse() <= ((Number) this.fuseValue.get()).intValue()) {
                if (((Boolean) this.autoSwordValue.get()).booleanValue()) {
                    int i = -1;
                    float f = 1.0f;
                    for (int i2 = 0; i2 <= 8; i2++) {
                        IItemStack stackInSlot = thePlayer.getInventory().getStackInSlot(i2);
                        if (stackInSlot != null && MinecraftInstance.classProvider.isItemSword(stackInSlot.getItem())) {
                            IItem item = stackInSlot.getItem();
                            if (item == null) {
                                Intrinsics.throwNpe();
                            }
                            float damageVsEntity = item.asItemSword().getDamageVsEntity() + 4.0f;
                            if (damageVsEntity > f) {
                                f = damageVsEntity;
                                i = i2;
                            }
                        }
                    }
                    if (i != -1 && i != thePlayer.getInventory().getCurrentItem()) {
                        thePlayer.getInventory().setCurrentItem(i);
                        MinecraftInstance.f157mc.getPlayerController().updateController();
                    }
                }
                if (thePlayer.getHeldItem() != null) {
                    IClassProvider iClassProvider = MinecraftInstance.classProvider;
                    IItemStack heldItem = thePlayer.getHeldItem();
                    if (heldItem == null) {
                        Intrinsics.throwNpe();
                    }
                    if (iClassProvider.isItemSword(heldItem.getItem())) {
                        MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().setPressed(true);
                        this.blocked = true;
                        return;
                    }
                    return;
                }
                return;
            }
        }
        if (this.blocked && !MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem())) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().setPressed(false);
            this.blocked = false;
        }
    }
}

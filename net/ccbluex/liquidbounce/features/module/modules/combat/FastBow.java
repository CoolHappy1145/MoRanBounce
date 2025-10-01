package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/FastBow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "packetsValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "FastBow", description = "Turns your bow into a machine gun.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/FastBow.class */
public final class FastBow extends Module {
    private final IntegerValue packetsValue = new IntegerValue("Packets", 20, 3, 20);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        float rotationYaw;
        float rotationPitch;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || !thePlayer.isUsingItem()) {
            return;
        }
        IItemStack currentItemInHand = thePlayer.getInventory().getCurrentItemInHand();
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer2.getInventory().getCurrentItemInHand() != null) {
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            IItemStack currentItemInHand2 = thePlayer3.getInventory().getCurrentItemInHand();
            if (currentItemInHand2 == null) {
                Intrinsics.throwNpe();
            }
            if (iClassProvider.isItemBow(currentItemInHand2.getItem())) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerBlockPlacement(WBlockPos.Companion.getORIGIN(), 255, currentItemInHand, 0.0f, 0.0f, 0.0f));
                if (RotationUtils.targetRotation != null) {
                    rotationYaw = RotationUtils.targetRotation.getYaw();
                } else {
                    rotationYaw = thePlayer.getRotationYaw();
                }
                float f = rotationYaw;
                if (RotationUtils.targetRotation != null) {
                    rotationPitch = RotationUtils.targetRotation.getPitch();
                } else {
                    rotationPitch = thePlayer.getRotationPitch();
                }
                float f2 = rotationPitch;
                int iIntValue = ((Number) this.packetsValue.get()).intValue();
                for (int i = 0; i < iIntValue; i++) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerLook(f, f2, true));
                }
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                if (currentItemInHand == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer.setItemInUseCount(currentItemInHand.getMaxItemUseDuration() - 1);
            }
        }
    }
}

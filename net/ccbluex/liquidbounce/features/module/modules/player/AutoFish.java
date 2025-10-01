package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.MinecraftVersion;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoFish;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rodOutTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoFish", description = "Automatically catches fish when using a rod.", category = ModuleCategory.PLAYER, supportedVersions = {MinecraftVersion.MC_1_8})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoFish.class */
public final class AutoFish extends Module {
    private final MSTimer rodOutTimer = new MSTimer();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if ((thePlayer != null ? thePlayer.getHeldItem() : null) != null) {
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IItemStack heldItem = thePlayer.getHeldItem();
            if (heldItem == null) {
                Intrinsics.throwNpe();
            }
            if (!iClassProvider.isItemFishingRod(heldItem.getItem())) {
                return;
            }
            IEntity fishEntity = thePlayer.getFishEntity();
            if ((this.rodOutTimer.hasTimePassed(500L) && fishEntity == null) || (fishEntity != null && fishEntity.getMotionX() == 0.0d && fishEntity.getMotionZ() == 0.0d && fishEntity.getMotionY() != 0.0d)) {
                MinecraftInstance.f157mc.rightClickMouse();
                this.rodOutTimer.reset();
            }
        }
    }
}

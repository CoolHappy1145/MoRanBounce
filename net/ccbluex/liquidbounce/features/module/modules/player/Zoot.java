package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Zoot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "badEffectsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fireValue", "noAirValue", "hasBadEffect", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Zoot", description = "Removes all bad potion effects/fire.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/Zoot.class */
public final class Zoot extends Module {
    private final BoolValue badEffectsValue = new BoolValue("BadEffects", true);
    private final BoolValue fireValue = new BoolValue("Fire", true);
    private final BoolValue noAirValue = new BoolValue("NoAir", false);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (!((Boolean) this.noAirValue.get()).booleanValue() || thePlayer.getOnGround()) {
                if (((Boolean) this.badEffectsValue.get()).booleanValue()) {
                    Iterator it = thePlayer.getActivePotionEffects().iterator();
                    if (it.hasNext()) {
                        Object next = it.next();
                        if (it.hasNext()) {
                            int duration = ((IPotionEffect) next).getDuration();
                            do {
                                Object next2 = it.next();
                                int duration2 = ((IPotionEffect) next2).getDuration();
                                if (duration < duration2) {
                                    next = next2;
                                    duration = duration2;
                                }
                            } while (it.hasNext());
                            obj = next;
                        } else {
                            obj = next;
                        }
                    } else {
                        obj = null;
                    }
                    IPotionEffect iPotionEffect = (IPotionEffect) obj;
                    if (iPotionEffect != null) {
                        int duration3 = iPotionEffect.getDuration() / 20;
                        for (int i = 0; i < duration3; i++) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                        }
                    }
                }
                if (((Boolean) this.fireValue.get()).booleanValue() && !thePlayer.getCapabilities().isCreativeMode() && thePlayer.isBurning()) {
                    for (int i2 = 0; i2 < 9; i2++) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                    }
                }
            }
        }
    }

    private final boolean hasBadEffect() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            return thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.HUNGER)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SLOWDOWN)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.DIG_SLOWDOWN)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.HARM)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.CONFUSION)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.WEAKNESS)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.WITHER)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.POISON));
        }
        return false;
    }
}

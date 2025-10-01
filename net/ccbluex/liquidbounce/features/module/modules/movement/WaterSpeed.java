package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/WaterSpeed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "WaterSpeed", description = "Allows you to swim faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/WaterSpeed.class */
public final class WaterSpeed extends Module {
    private final FloatValue speedValue = new FloatValue("Speed", 1.2f, 1.1f, 1.5f);

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && thePlayer.isInWater() && MinecraftInstance.classProvider.isBlockLiquid(BlockUtils.getBlock(thePlayer.getPosition()))) {
            float fFloatValue = ((Number) this.speedValue.get()).floatValue();
            thePlayer.setMotionX(thePlayer.getMotionX() * fFloatValue);
            thePlayer.setMotionZ(thePlayer.getMotionZ() * fFloatValue);
        }
    }
}

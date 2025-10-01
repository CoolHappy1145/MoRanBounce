package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/NoSlowBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAirValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "waterValue", "getWaterValue", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NoSlowBreak", description = "Automatically adjusts breaking speed when using modules that influence it.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/NoSlowBreak.class */
public final class NoSlowBreak extends Module {

    @NotNull
    private final BoolValue airValue = new BoolValue("Air", true);

    @NotNull
    private final BoolValue waterValue = new BoolValue("Water", false);

    @NotNull
    public final BoolValue getAirValue() {
        return this.airValue;
    }

    @NotNull
    public final BoolValue getWaterValue() {
        return this.waterValue;
    }
}

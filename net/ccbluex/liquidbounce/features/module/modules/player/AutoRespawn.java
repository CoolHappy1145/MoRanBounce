package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoRespawn;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoRespawn", description = "Automatically respawns you after dying.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoRespawn.class */
public final class AutoRespawn extends Module {
    private final BoolValue instantValue = new BoolValue("Instant", true);

    /* JADX WARN: Removed duplicated region for block: B:26:0x008d  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUpdate(@NotNull UpdateEvent event) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Ghost.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }
            if (module.getState()) {
                return;
            }
            if (((Boolean) this.instantValue.get()).booleanValue()) {
                z = thePlayer.getHealth() == 0.0f || thePlayer.isDead();
            } else if (MinecraftInstance.classProvider.isGuiGameOver(MinecraftInstance.f157mc.getCurrentScreen())) {
                IGuiScreen currentScreen = MinecraftInstance.f157mc.getCurrentScreen();
                if (currentScreen == null) {
                    Intrinsics.throwNpe();
                }
                z = currentScreen.asGuiGameOver().getEnableButtonsTimer() >= 20;
            }
            if (z) {
                thePlayer.respawnPlayer();
                MinecraftInstance.f157mc.displayGuiScreen(null);
            }
        }
    }
}

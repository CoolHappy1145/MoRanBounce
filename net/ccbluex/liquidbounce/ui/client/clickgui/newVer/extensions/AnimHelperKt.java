package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.extensions;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.NewGUI;
import net.ccbluex.liquidbounce.utils.render.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\n\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0006\u001a\"\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001\u001a\u001a\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u00a8\u0006\u0007"}, m27d2 = {"animLinear", "", "speed", "min", "max", "animSmooth", "target", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/extensions/AnimHelperKt.class */
public final class AnimHelperKt {
    public static final float animSmooth(float f, float f2, float f3) {
        return ((Boolean) NewGUI.fastRenderValue.get()).booleanValue() ? f2 : AnimationUtils.animate(f2, f, f3 * RenderUtils.deltaTime * 0.025f);
    }

    public static final float animLinear(float f, float f2, float f3, float f4) {
        return ((Boolean) NewGUI.fastRenderValue.get()).booleanValue() ? f2 < 0.0f ? f3 : f4 : RangesKt.coerceIn(f + f2, f3, f4);
    }
}

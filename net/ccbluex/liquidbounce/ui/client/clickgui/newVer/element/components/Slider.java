package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.components;

import java.awt.Color;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.ColorManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u001e\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/components/Slider;", "", "()V", "smooth", "", PropertyDescriptor.VALUE, "onDraw", "", "x", "y", "width", "accentColor", "Ljava/awt/Color;", "setValue", "desired", "min", "max", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/components/Slider.class */
public final class Slider {
    private float smooth;
    private float value;

    public final void onDraw(float f, float f2, float f3, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        this.smooth = AnimHelperKt.animSmooth(this.smooth, this.value, 0.5f);
        RenderUtils.drawRoundedRect(f - 1.0f, f2 - 1.0f, f + f3 + 1.0f, f2 + 1.0f, 1.0f, ColorManager.INSTANCE.getUnusedSlider().getRGB());
        RenderUtils.drawRoundedRect(f - 1.0f, f2 - 1.0f, f + (f3 * (this.smooth / 100.0f)) + 1.0f, f2 + 1.0f, 1.0f, accentColor.getRGB());
        RenderUtils.drawFilledCircle((int) (f + (f3 * (this.smooth / 100.0f))), (int) f2, 5.0f, Color.white);
        RenderUtils.drawFilledCircle((int) (f + (f3 * (this.smooth / 100.0f))), (int) f2, 3.0f, ColorManager.INSTANCE.getBackground());
    }

    public final void setValue(float f, float f2, float f3) {
        this.value = ((f - f2) / (f3 - f2)) * 100.0f;
    }
}

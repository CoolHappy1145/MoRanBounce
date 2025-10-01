package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.components;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.lbplus.BlendUtils;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J6\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/components/ToggleSwitch;", "", "()V", "smooth", "", "state", "", "getState", "()Z", "setState", "(Z)V", "onDraw", "", "x", "y", "width", "height", "bgColor", "Ljava/awt/Color;", "accentColor", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/components/ToggleSwitch.class */
public final class ToggleSwitch {
    private float smooth;
    private boolean state;

    public final boolean getState() {
        return this.state;
    }

    public final void setState(boolean z) {
        this.state = z;
    }

    public final void onDraw(float f, float f2, float f3, float f4, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        this.smooth = AnimHelperKt.animLinear(this.smooth, (this.state ? 0.2f : -0.2f) * RenderUtils.deltaTime * 0.045f, 0.0f, 1.0f);
        Color borderColor = BlendUtils.blendColors(new float[]{0.0f, 1.0f}, new Color[]{new Color(160, 160, 160), accentColor}, this.smooth);
        Color mainColor = BlendUtils.blendColors(new float[]{0.0f, 1.0f}, new Color[]{bgColor, accentColor}, this.smooth);
        Color colorBlendColors = BlendUtils.blendColors(new float[]{0.0f, 1.0f}, new Color[]{new Color(160, 160, 160), bgColor}, this.smooth);
        Intrinsics.checkExpressionValueIsNotNull(borderColor, "borderColor");
        RenderUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f + f3 + 0.5f, f2 + f4 + 0.5f, (f4 + 1.0f) / 2.0f, borderColor.getRGB());
        Intrinsics.checkExpressionValueIsNotNull(mainColor, "mainColor");
        RenderUtils.drawRoundedRect(f, f2, f + f3, f2 + f4, f4 / 2.0f, mainColor.getRGB());
        RenderUtils.drawFilledCircle((int) (f + ((1.0f - this.smooth) * (2.0f + ((f4 - 4.0f) / 2.0f))) + (this.smooth * ((f3 - 2.0f) - ((f4 - 4.0f) / 2.0f)))), (int) (f2 + 2.0f + ((f4 - 4.0f) / 2.0f)), (f4 - 4.0f) / 2.0f, colorBlendColors);
    }
}

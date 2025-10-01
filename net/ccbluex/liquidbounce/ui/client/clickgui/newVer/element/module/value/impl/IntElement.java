package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.ColorManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.components.Slider;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Marker;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J0\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0016J0\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/IntElement;", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/ValueElement;", "", "savedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "(Lnet/ccbluex/liquidbounce/value/IntegerValue;)V", "dragged", "", "getSavedValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "slider", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/components/Slider;", "drawElement", "", "mouseX", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "onRelease", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/IntElement.class */
public final class IntElement extends ValueElement {
    private final Slider slider;
    private boolean dragged;

    @NotNull
    private final IntegerValue savedValue;

    @NotNull
    public final IntegerValue getSavedValue() {
        return this.savedValue;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntElement(@NotNull IntegerValue savedValue) {
        super(savedValue);
        Intrinsics.checkParameterIsNotNull(savedValue, "savedValue");
        this.savedValue = savedValue;
        this.slider = new Slider();
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement
    public float drawElement(int i, int i2, float f, float f2, float f3, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        float stringWidth = 30.0f + Fonts.font40.getStringWidth(String.valueOf(this.savedValue.getMaximum()));
        int stringWidth2 = Fonts.font40.getStringWidth(String.valueOf(this.savedValue.getMaximum()));
        int stringWidth3 = Fonts.font40.getStringWidth(String.valueOf(this.savedValue.getMinimum()));
        float stringWidth4 = ((((f3 - 50.0f) - Fonts.font40.getStringWidth(getValue().getName())) - stringWidth2) - stringWidth3) - stringWidth;
        float f4 = ((((f + f3) - 20.0f) - stringWidth4) - stringWidth2) - stringWidth;
        if (this.dragged) {
            this.savedValue.set((Object) Integer.valueOf(RangesKt.coerceIn((int) (this.savedValue.getMinimum() + (((this.savedValue.getMaximum() - this.savedValue.getMinimum()) / stringWidth4) * (i - f4))), this.savedValue.getMinimum(), this.savedValue.getMaximum())));
        }
        Fonts.font40.getStringWidth(String.valueOf(((Number) this.savedValue.get()).intValue()));
        Fonts.font40.drawString(getValue().getName(), f + 10.0f, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        Fonts.font40.drawString(String.valueOf(this.savedValue.getMaximum()), (((f + f3) - 10.0f) - stringWidth2) - stringWidth, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        Fonts.font40.drawString(String.valueOf(this.savedValue.getMinimum()), (((((f + f3) - 30.0f) - stringWidth4) - stringWidth2) - stringWidth3) - stringWidth, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        this.slider.setValue(RangesKt.coerceIn(((Number) this.savedValue.get()).intValue(), this.savedValue.getMinimum(), this.savedValue.getMaximum()), this.savedValue.getMinimum(), this.savedValue.getMaximum());
        this.slider.onDraw(((((f + f3) - 20.0f) - stringWidth4) - stringWidth2) - stringWidth, f2 + 10.0f, stringWidth4, accentColor);
        RenderUtils.drawRoundedRect(((f + f3) - 5.0f) - stringWidth, f2 + 2.0f, (f + f3) - 10.0f, f2 + 18.0f, 4.0f, ColorManager.INSTANCE.getButton().getRGB());
        RenderUtils.customRounded((f + f3) - 18.0f, f2 + 2.0f, (f + f3) - 10.0f, f2 + 18.0f, 0.0f, 4.0f, 4.0f, 0.0f, ColorManager.INSTANCE.getButtonOutline().getRGB());
        RenderUtils.customRounded(((f + f3) - 5.0f) - stringWidth, f2 + 2.0f, ((f + f3) + 3.0f) - stringWidth, f2 + 18.0f, 4.0f, 0.0f, 0.0f, 4.0f, ColorManager.INSTANCE.getButtonOutline().getRGB());
        Fonts.font40.drawString(String.valueOf(((Number) this.savedValue.get()).intValue()), ((f + f3) + 6.0f) - stringWidth, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        Fonts.font40.drawString("-", ((f + f3) - 3.0f) - stringWidth, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        Fonts.font40.drawString(Marker.ANY_NON_NULL_MARKER, (f + f3) - 17.0f, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        return getValueHeight();
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement
    public void onClick(int i, int i2, float f, float f2, float f3) {
        float stringWidth = 30.0f + Fonts.font40.getStringWidth(String.valueOf(this.savedValue.getMaximum()));
        int stringWidth2 = Fonts.font40.getStringWidth(String.valueOf(this.savedValue.getMaximum()));
        if (((float) i) >= ((((f + f3) - 30.0f) - (((((f3 - 50.0f) - ((float) Fonts.font40.getStringWidth(getValue().getName()))) - ((float) stringWidth2)) - ((float) Fonts.font40.getStringWidth(String.valueOf(this.savedValue.getMinimum())))) - stringWidth)) - stringWidth) - ((float) stringWidth2) && ((float) i) < (((f + f3) - 10.0f) - stringWidth) - ((float) stringWidth2) && ((float) i2) >= f2 + 5.0f && ((float) i2) < f2 + 15.0f) {
            this.dragged = true;
        }
        if (((float) i) >= ((f + f3) - 5.0f) - stringWidth && ((float) i) < ((f + f3) + 3.0f) - stringWidth && ((float) i2) >= f2 + 2.0f && ((float) i2) < f2 + 18.0f) {
            this.savedValue.set((Object) Integer.valueOf(RangesKt.coerceIn(((Number) this.savedValue.get()).intValue() - 1, this.savedValue.getMinimum(), this.savedValue.getMaximum())));
        }
        if (((float) i) >= (f + f3) - 18.0f && ((float) i) < (f + f3) - 10.0f && ((float) i2) >= f2 + 2.0f && ((float) i2) < f2 + 18.0f) {
            this.savedValue.set((Object) Integer.valueOf(RangesKt.coerceIn(((Number) this.savedValue.get()).intValue() + 1, this.savedValue.getMinimum(), this.savedValue.getMaximum())));
        }
    }

    public void onRelease(int i, int i2, float f, float f2, float f3) {
        if (this.dragged) {
            this.dragged = false;
        }
    }
}

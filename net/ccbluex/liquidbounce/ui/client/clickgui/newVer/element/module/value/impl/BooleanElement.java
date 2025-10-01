package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.impl;

import java.awt.Color;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.components.Checkbox;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J0\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/BooleanElement;", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/ValueElement;", "", PropertyDescriptor.VALUE, "Lnet/ccbluex/liquidbounce/value/BoolValue;", "(Lnet/ccbluex/liquidbounce/value/BoolValue;)V", "checkbox", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/components/Checkbox;", "drawElement", "", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/BooleanElement.class */
public final class BooleanElement extends ValueElement {
    private final Checkbox checkbox;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BooleanElement(@NotNull BoolValue value) {
        super(value);
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.checkbox = new Checkbox();
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement
    public float drawElement(int i, int i2, float f, float f2, float f3, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        this.checkbox.setState(((Boolean) getValue().get()).booleanValue());
        this.checkbox.onDraw(f + 10.0f, f2 + 5.0f, 10.0f, 10.0f, bgColor, accentColor);
        Fonts.font40.drawString(getValue().getName(), f + 25.0f, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        return getValueHeight();
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement
    public void onClick(int i, int i2, float f, float f2, float f3) {
        if (isDisplayable()) {
            if (((float) i) >= f && ((float) i) < f + f3 && ((float) i2) >= f2 && ((float) i2) < f2 + 20.0f) {
                getValue().set(Boolean.valueOf(!((Boolean) getValue().get()).booleanValue()));
            }
        }
    }
}

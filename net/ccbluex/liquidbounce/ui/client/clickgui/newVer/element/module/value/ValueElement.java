package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value;

import java.awt.Color;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0003\b&\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H&J\u0006\u0010\u0018\u001a\u00020\u0019J0\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH&J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0010H\u0016J0\u0010 \u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006!"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/ValueElement;", "T", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", PropertyDescriptor.VALUE, "Lnet/ccbluex/liquidbounce/value/Value;", "(Lnet/ccbluex/liquidbounce/value/Value;)V", "getValue", "()Lnet/ccbluex/liquidbounce/value/Value;", "valueHeight", "", "getValueHeight", "()F", "setValueHeight", "(F)V", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "isDisplayable", "", "onClick", "", "onKeyPress", "typed", "", "keyCode", "onRelease", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/ValueElement.class */
public abstract class ValueElement extends MinecraftInstance {
    private float valueHeight;

    @NotNull
    private final Value value;

    public abstract float drawElement(int i, int i2, float f, float f2, float f3, @NotNull Color color, @NotNull Color color2);

    public abstract void onClick(int i, int i2, float f, float f2, float f3);

    @NotNull
    public final Value getValue() {
        return this.value;
    }

    public ValueElement(@NotNull Value value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.value = value;
        this.valueHeight = 20.0f;
    }

    public final float getValueHeight() {
        return this.valueHeight;
    }

    public final void setValueHeight(float f) {
        this.valueHeight = f;
    }
}

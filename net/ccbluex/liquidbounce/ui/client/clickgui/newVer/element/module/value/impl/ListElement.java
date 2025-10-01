package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.ColorManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd \u001d2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J0\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/ListElement;", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/ValueElement;", "", "saveValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "(Lnet/ccbluex/liquidbounce/value/ListValue;)V", "expandHeight", "", "expansion", "", "maxSubWidth", "", "getSaveValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "unusedValues", "", "getUnusedValues", "()Ljava/util/List;", "drawElement", "mouseX", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/ListElement.class */
public final class ListElement extends ValueElement {
    private float expandHeight;
    private boolean expansion;
    private final int maxSubWidth;

    @NotNull
    private final ListValue saveValue;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final IResourceLocation expanding = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("loserline/expand.png");

    @NotNull
    public final ListValue getSaveValue() {
        return this.saveValue;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ListElement(@NotNull ListValue saveValue) {
        super(saveValue);
        Intrinsics.checkParameterIsNotNull(saveValue, "saveValue");
        this.saveValue = saveValue;
        String[] values = this.saveValue.getValues();
        ArrayList arrayList = new ArrayList(values.length);
        for (String str : values) {
            arrayList.add(Integer.valueOf(-Fonts.font40.getStringWidth(str)));
        }
        Integer num = (Integer) CollectionsKt.firstOrNull(CollectionsKt.sorted(arrayList));
        this.maxSubWidth = (-(num != null ? num.intValue() : 0)) + 20;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/ListElement$Companion;", "", "()V", "expanding", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getExpanding", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/value/impl/ListElement$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final IResourceLocation getExpanding() {
            return ListElement.expanding;
        }
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement
    public float drawElement(int i, int i2, float f, float f2, float f3, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        this.expandHeight = AnimHelperKt.animSmooth(this.expandHeight, this.expansion ? 16.0f * (this.saveValue.getValues().length - 1.0f) : 0.0f, 0.5f);
        float length = this.expandHeight / (16.0f * (this.saveValue.getValues().length - 1.0f));
        Fonts.font40.drawString(getValue().getName(), f + 10.0f, ((f2 + 10.0f) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
        RenderUtils.drawRoundedRect(((f + f3) - 18.0f) - this.maxSubWidth, f2 + 2.0f, (f + f3) - 10.0f, f2 + 18.0f + this.expandHeight, 4.0f, ColorManager.INSTANCE.getButton().getRGB());
        GlStateManager.func_179117_G();
        GL11.glPushMatrix();
        GL11.glTranslatef((f + f3) - 20.0f, f2 + 10.0f, 0.0f);
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f * length, 0.0f, 0.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.drawImage(expanding, -4, -4, 8, 8);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        Fonts.font40.drawString((String) getValue().get(), ((f + f3) - 14.0f) - this.maxSubWidth, f2 + 6.0f, -1);
        GL11.glPushMatrix();
        GlStateManager.func_179109_b(((f + f3) - 14.0f) - this.maxSubWidth, f2 + 7.0f, 0.0f);
        GlStateManager.func_179152_a(length, length, length);
        float f4 = 0.0f;
        if (length > 0.0f) {
            Iterator it = getUnusedValues().iterator();
            while (it.hasNext()) {
                Fonts.font40.drawString((String) it.next(), 0.0f, ((16.0f + f4) * length) - 1.0f, new Color(0.5f, 0.5f, 0.5f, RangesKt.coerceIn(length, 0.0f, 1.0f)).getRGB());
                f4 += 16.0f;
            }
        }
        GL11.glPopMatrix();
        setValueHeight(20.0f + this.expandHeight);
        return getValueHeight();
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.value.ValueElement
    public void onClick(int i, int i2, float f, float f2, float f3) {
        if (isDisplayable()) {
            if (((float) i) >= f && ((float) i) < f + f3 && ((float) i2) >= f2 + 2.0f && ((float) i2) < f2 + 18.0f) {
                this.expansion = !this.expansion;
            }
        }
        if (this.expansion) {
            float f4 = 0.0f;
            for (String str : getUnusedValues()) {
                if (((float) i) >= ((f + f3) - 14.0f) - ((float) this.maxSubWidth) && ((float) i) < (f + f3) - 10.0f && ((float) i2) >= (f2 + 18.0f) + f4 && ((float) i2) < (f2 + 34.0f) + f4) {
                    getValue().set(str);
                    this.expansion = false;
                    return;
                }
                f4 += 16.0f;
            }
        }
    }

    @NotNull
    public final List getUnusedValues() {
        String[] values = this.saveValue.getValues();
        ArrayList arrayList = new ArrayList();
        for (String str : values) {
            if (!Intrinsics.areEqual(str, (String) getValue().get())) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }
}

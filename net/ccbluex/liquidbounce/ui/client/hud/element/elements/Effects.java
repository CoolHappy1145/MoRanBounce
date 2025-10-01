package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import org.jetbrains.annotations.NotNull;

@ElementInfo(name = "Effects")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0010"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "shadow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects.class */
public final class Effects extends Element {
    private final FontValue fontValue;
    private final BoolValue shadow;

    public Effects() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Effects(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        IFontRenderer iFontRenderer = Fonts.font35;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font35");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.shadow = new BoolValue("Shadow", true);
    }

    public Effects(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 2.0d : d, (i & 2) != 0 ? 10.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN) : side);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        String str;
        float fontHeight = 0.0f;
        float f = 0.0f;
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        for (IPotionEffect iPotionEffect : thePlayer.getActivePotionEffects()) {
            IPotion potionById = MinecraftInstance.functions.getPotionById(iPotionEffect.getPotionID());
            if (iPotionEffect.getAmplifier() == 1) {
                str = "II";
            } else if (iPotionEffect.getAmplifier() == 2) {
                str = "III";
            } else if (iPotionEffect.getAmplifier() == 3) {
                str = "IV";
            } else if (iPotionEffect.getAmplifier() == 4) {
                str = "V";
            } else if (iPotionEffect.getAmplifier() == 5) {
                str = "VI";
            } else if (iPotionEffect.getAmplifier() == 6) {
                str = "VII";
            } else if (iPotionEffect.getAmplifier() == 7) {
                str = "VIII";
            } else if (iPotionEffect.getAmplifier() == 8) {
                str = "IX";
            } else if (iPotionEffect.getAmplifier() == 9) {
                str = "X";
            } else {
                str = iPotionEffect.getAmplifier() > 10 ? "X+" : "I";
            }
            String str2 = MinecraftInstance.functions.formatI18n(potionById.getName(), new String[0]) + ' ' + str + "\u00a7f: \u00a77" + iPotionEffect.getDurationString();
            float stringWidth = iFontRenderer.getStringWidth(str2);
            if (f < stringWidth) {
                f = stringWidth;
            }
            iFontRenderer.drawString(str2, -stringWidth, fontHeight, potionById.getLiquidColor(), ((Boolean) this.shadow.get()).booleanValue());
            fontHeight -= iFontRenderer.getFontHeight();
        }
        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        if (f == 0.0f) {
            f = 40.0f;
        }
        if (fontHeight == 0.0f) {
            fontHeight = -10.0f;
        }
        return new Border(2.0f, iFontRenderer.getFontHeight(), (-f) - 2.0f, (fontHeight + iFontRenderer.getFontHeight()) - 2.0f);
    }
}

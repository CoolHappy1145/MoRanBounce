package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "Armor")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Armor;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "blueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "greenValue", "redValue", "saturationValue", "speed", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Armor.class */
public final class Armor extends Element {
    private final ListValue colorModeValue;
    private final FloatValue brightnessValue;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final FloatValue saturationValue;
    private final IntegerValue speed;

    public Armor() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Armor(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.colorModeValue = new ListValue("Text-Color", new String[]{"Custom", "Astolfo"}, "Custom");
        this.brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
        this.redValue = new IntegerValue("Text-R", 255, 0, 255);
        this.greenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.blueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 0.9f, 0.0f, 1.0f);
        this.speed = new IntegerValue("AllSpeed", 0, 0, 400);
    }

    public Armor(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? -8.0d : d, (i & 2) != 0 ? 57.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN) : side);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        int iAstolfo;
        int i = 0;
        if (MinecraftInstance.f157mc.getPlayerController().isNotCreative()) {
            GL11.glPushMatrix();
            IRenderItem renderItem = MinecraftInstance.f157mc.getRenderItem();
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            int i2 = 1;
            int i3 = 0;
            int i4 = thePlayer.isInsideOfMaterial(MinecraftInstance.classProvider.getMaterialEnum(MaterialType.WATER)) ? -10 : 0;
            String str = (String) this.colorModeValue.get();
            int rgb = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()).getRGB();
            boolean zEquals = StringsKt.equals(str, "Rainbow", true);
            for (int i5 = 0; i5 <= 3; i5++) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer2.getInventory().getArmorInventory().get(i5) != null) {
                    i += 20;
                }
            }
            RenderUtils.drawRect(-2.0f, -4.0f, 2.0f + i, 29.0f, new Color(50, 50, 50, 60));
            for (int i6 = 3; i6 >= 0; i6--) {
                if (zEquals) {
                    iAstolfo = 0;
                } else {
                    iAstolfo = StringsKt.equals(str, "Astolfo", true) ? RenderUtils.Astolfo(i6 * ((Number) this.speed.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : rgb;
                }
                int i7 = iAstolfo;
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                IItemStack iItemStack = (IItemStack) thePlayer3.getInventory().getArmorInventory().get(i6);
                if (iItemStack != null) {
                    RenderUtils.drawGradientSidewaysV(i2, 0.0d, i2 + 18.0d, 17.0d, i7, new Color(140, 140, 140, 40).getRGB());
                    Fonts.Sfui24.drawStringWithShadow(String.valueOf(iItemStack.getMaxDamage() - iItemStack.getItemDamage()), i2 + 4, 20, i7);
                    RenderUtils.drawRect(i2, 25.0f, i2 + 18.0f, 26.0f, new Color(140, 140, 140, 220).getRGB());
                    RenderUtils.drawRect(i2, 25.0f, i2 + ((18.0f * (iItemStack.getMaxDamage() - iItemStack.getItemDamage())) / iItemStack.getMaxDamage()), 26.0f, i7);
                    renderItem.renderItemIntoGUI(iItemStack, i2 + 1, i4);
                    i2 += 20;
                    i3++;
                }
            }
            MinecraftInstance.classProvider.getGlStateManager().enableAlpha();
            MinecraftInstance.classProvider.getGlStateManager().disableBlend();
            MinecraftInstance.classProvider.getGlStateManager().disableLighting();
            MinecraftInstance.classProvider.getGlStateManager().disableCull();
            GL11.glPopMatrix();
        }
        return new Border(-2.0f, -4.0f, 82.0f, 29.0f);
    }
}

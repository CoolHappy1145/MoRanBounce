package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.apache.log4j.net.SyslogAppender;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "Target")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "decimalFormat", "Ljava/text/DecimalFormat;", "easingHealth", "", "fadeSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "lastTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawHead", "", "skin", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "width", "", "height", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Target.class */
public final class Target extends Element {
    private final DecimalFormat decimalFormat;
    private final FloatValue fadeSpeed;
    private float easingHealth;
    private IEntity lastTarget;

    public Target() {
        super(0.0d, 0.0d, 0.0f, null, 15, null);
        this.decimalFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        this.fadeSpeed = new FloatValue("FadeSpeed", 2.0f, 1.0f, 9.0f);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        int stringWidth;
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        IEntityLivingBase target = ((KillAura) module).getTarget();
        if (MinecraftInstance.classProvider.isEntityPlayer(target) && target != null) {
            if ((!Intrinsics.areEqual(target, this.lastTarget)) || this.easingHealth < 0.0f || this.easingHealth > target.getMaxHealth() || Math.abs(this.easingHealth - target.getHealth()) < 0.01d) {
                this.easingHealth = target.getHealth();
            }
            int i = 38;
            String name = target.getName();
            if (name != null) {
                i = 38;
                stringWidth = Fonts.font40.getStringWidth(name);
            } else {
                stringWidth = 0;
            }
            float fCoerceAtLeast = RangesKt.coerceAtLeast(i + stringWidth, 118);
            Color color = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.BLACK");
            int rgb = color.getRGB();
            Color color2 = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
            RenderUtils.drawBorderedRect(0.0f, 0.0f, fCoerceAtLeast, 36.0f, 3.0f, rgb, color2.getRGB());
            if (this.easingHealth > target.getHealth()) {
                RenderUtils.drawRect(0.0f, 34.0f, (this.easingHealth / target.getMaxHealth()) * fCoerceAtLeast, 36.0f, new Color(252, 185, 65).getRGB());
            }
            RenderUtils.drawRect(0.0f, 34.0f, (target.getHealth() / target.getMaxHealth()) * fCoerceAtLeast, 36.0f, new Color(252, 96, 66).getRGB());
            if (this.easingHealth < target.getHealth()) {
                RenderUtils.drawRect((this.easingHealth / target.getMaxHealth()) * fCoerceAtLeast, 34.0f, (target.getHealth() / target.getMaxHealth()) * fCoerceAtLeast, 36.0f, new Color(44, 201, SyslogAppender.LOG_LOCAL2).getRGB());
            }
            this.easingHealth += ((target.getHealth() - this.easingHealth) / ((float) Math.pow(2.0d, 10.0f - ((Number) this.fadeSpeed.get()).floatValue()))) * RenderUtils.deltaTime;
            String name2 = target.getName();
            if (name2 != null) {
                Fonts.font40.drawString(name2, 36, 3, 16777215);
            }
            IFontRenderer iFontRenderer = Fonts.font35;
            StringBuilder sbAppend = new StringBuilder().append("Distance: ");
            DecimalFormat decimalFormat = this.decimalFormat;
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            iFontRenderer.drawString(sbAppend.append(decimalFormat.format(PlayerExtensionKt.getDistanceToEntityBox(thePlayer, target))).toString(), 36, 15, 16777215);
            INetworkPlayerInfo playerInfo = MinecraftInstance.f157mc.getNetHandler().getPlayerInfo(target.getUniqueID());
            if (playerInfo != null) {
                Fonts.font35.drawString("Ping: " + RangesKt.coerceAtLeast(playerInfo.getResponseTime(), 0), 36, 24, 16777215);
                drawHead(playerInfo.getLocationSkin(), 30, 30);
            }
        }
        this.lastTarget = target;
        return new Border(0.0f, 0.0f, 120.0f, 36.0f);
    }

    private final void drawHead(IResourceLocation iResourceLocation, int i, int i2) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        MinecraftInstance.f157mc.getTextureManager().bindTexture(iResourceLocation);
        RenderUtils.drawScaledCustomSizeModalRect(2, 2, 8.0f, 8.0f, 8, 8, i, i2, 64.0f, 64.0f);
    }
}

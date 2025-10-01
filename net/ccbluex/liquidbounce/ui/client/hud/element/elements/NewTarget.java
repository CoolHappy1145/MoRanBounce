package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.math.MathKt;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Typography;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.apache.log4j.net.SyslogAppender;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "NewTargets")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdJ\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\nH\u0002J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\nH\u0002J\u0018\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0018\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\nH\u0002J\u0018\u0010!\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0018\u0010\"\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0012\u0010#\u001a\u00020\n2\b\u0010$\u001a\u0004\u0018\u00010\u0013H\u0002J\n\u0010%\u001a\u0004\u0018\u00010\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006&"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NewTarget;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "animSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "changeTime", "", "decimalFormat", "Ljava/text/DecimalFormat;", "displayPercent", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "lastChangeHealth", "lastHealth", "lastUpdate", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "prevTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "switchAnimSpeedValue", "switchModeValue", "drawAstolfo", "", "target", "nowAnimHP", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawFlux", "drawLiquid", "easingHealth", "drawMoon", "drawNovo", "drawRise", "drawZamorozka", "getHealth", "entity", "getTBorder", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/NewTarget.class */
public final class NewTarget extends Element {
    private final ListValue modeValue;
    private final ListValue switchModeValue;
    private final IntegerValue animSpeedValue;
    private final IntegerValue switchAnimSpeedValue;
    private final FontValue fontValue;
    private IEntityLivingBase prevTarget;
    private float lastHealth;
    private float lastChangeHealth;
    private long changeTime;
    private float displayPercent;
    private long lastUpdate;
    private final DecimalFormat decimalFormat;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0010\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\n\u00a2\u0006\u0002\b\u0005"}, m27d2 = {"renderSideway", "", "x", "", "x1", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.NewTarget$drawRise$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/NewTarget$drawRise$1.class */
    static final class C04751 extends Lambda implements Function2 {
        public static final C04751 INSTANCE = new C04751();

        C04751() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public Object invoke(Object obj, Object obj2) {
            invoke(((Number) obj).intValue(), ((Number) obj2).intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i, int i2) {
            RenderUtils.quickDrawGradientSideways(i, 39.0d, i2, 45.0d, ColorUtils.hslRainbow$default(i, 0.0f, 0.0f, 10, 0, 0.0f, 0.0f, 118, null).getRGB(), ColorUtils.hslRainbow$default(i2, 0.0f, 0.0f, 10, 0, 0.0f, 0.0f, 118, null).getRGB());
        }
    }

    public NewTarget() {
        super(-46.0d, -40.0d, 1.0f, new Side(Side.Horizontal.MIDDLE, Side.Vertical.MIDDLE));
        this.modeValue = new ListValue("Mode", new String[]{"Novoline", "Astolfo", "Liquid", "Flux", "Rise", "Zamorozka"}, "Rise");
        this.switchModeValue = new ListValue("SwitchMode", new String[]{"Slide", "Zoom", "None"}, "Slide");
        this.animSpeedValue = new IntegerValue("AnimSpeed", 10, 5, 20);
        this.switchAnimSpeedValue = new IntegerValue("SwitchAnimSpeed", 20, 5, 40);
        IFontRenderer iFontRenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font40");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.lastHealth = 20.0f;
        this.lastChangeHealth = 20.0f;
        this.changeTime = System.currentTimeMillis();
        this.lastUpdate = System.currentTimeMillis();
        this.decimalFormat = new DecimalFormat("0.0");
    }

    private final float getHealth(IEntityLivingBase iEntityLivingBase) {
        if (iEntityLivingBase == null || iEntityLivingBase.isDead()) {
            return 0.0f;
        }
        return iEntityLivingBase.getHealth();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        float health;
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        IEntityLivingBase target = ((KillAura) module).getTarget();
        long jCurrentTimeMillis = System.currentTimeMillis();
        float fFloatValue = (jCurrentTimeMillis - this.lastUpdate) / (((Number) this.switchAnimSpeedValue.get()).floatValue() * 50.0f);
        this.lastUpdate = System.currentTimeMillis();
        if (MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
            target = MinecraftInstance.f157mc.getThePlayer();
        }
        if (target != null) {
            this.prevTarget = target;
        }
        if (this.prevTarget == null) {
            return getTBorder();
        }
        if (target != null) {
            if (this.displayPercent < 1.0f) {
                this.displayPercent += fFloatValue;
            }
            if (this.displayPercent > 1.0f) {
                this.displayPercent = 1.0f;
            }
        } else {
            if (this.displayPercent > 0.0f) {
                this.displayPercent -= fFloatValue;
            }
            if (this.displayPercent < 0.0f) {
                this.displayPercent = 0.0f;
                this.prevTarget = (IEntityLivingBase) null;
                return getTBorder();
            }
        }
        if (getHealth(this.prevTarget) != this.lastHealth) {
            this.lastChangeHealth = this.lastHealth;
            this.lastHealth = getHealth(this.prevTarget);
            this.changeTime = jCurrentTimeMillis;
        }
        if (jCurrentTimeMillis - (((Number) this.animSpeedValue.get()).intValue() * 50) < this.changeTime) {
            health = getHealth(this.prevTarget) + ((this.lastChangeHealth - getHealth(this.prevTarget)) * (1.0f - ((jCurrentTimeMillis - this.changeTime) / (((Number) this.animSpeedValue.get()).floatValue() * 50.0f))));
        } else {
            health = getHealth(this.prevTarget);
        }
        float f = health;
        String str = (String) this.switchModeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 3744723:
                if (lowerCase.equals("zoom")) {
                    Border tBorder = getTBorder();
                    if (tBorder == null) {
                        return null;
                    }
                    GL11.glScalef(this.displayPercent, this.displayPercent, this.displayPercent);
                    GL11.glTranslatef(((tBorder.getX2() * 0.5f) * (1.0f - this.displayPercent)) / this.displayPercent, ((tBorder.getY2() * 0.5f) * (1.0f - this.displayPercent)) / this.displayPercent, 0.0f);
                    break;
                }
                break;
            case 109526449:
                if (lowerCase.equals("slide")) {
                    double d = 1.0d - this.displayPercent;
                    double d2 = d * d * d * d * d;
                    IClassProvider iClassProvider = MinecraftInstance.classProvider;
                    IMinecraft mc = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
                    GL11.glTranslated((iClassProvider.createScaledResolution(mc).getScaledWidth() - getRenderX()) * d2, 0.0d, 0.0d);
                    break;
                }
                break;
        }
        String str2 = (String) this.modeValue.get();
        if (str2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase2 = str2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase2.hashCode()) {
            case -1102567108:
                if (lowerCase2.equals("liquid")) {
                    IEntityLivingBase iEntityLivingBase = this.prevTarget;
                    if (iEntityLivingBase == null) {
                        Intrinsics.throwNpe();
                    }
                    drawLiquid(iEntityLivingBase, f);
                    break;
                }
                break;
            case -703561496:
                if (lowerCase2.equals("astolfo")) {
                    IEntityLivingBase iEntityLivingBase2 = this.prevTarget;
                    if (iEntityLivingBase2 == null) {
                        Intrinsics.throwNpe();
                    }
                    drawAstolfo(iEntityLivingBase2, f);
                    break;
                }
                break;
            case 3146217:
                if (lowerCase2.equals("flux")) {
                    IEntityLivingBase iEntityLivingBase3 = this.prevTarget;
                    if (iEntityLivingBase3 == null) {
                        Intrinsics.throwNpe();
                    }
                    drawFlux(iEntityLivingBase3, f);
                    break;
                }
                break;
            case 3500745:
                if (lowerCase2.equals("rise")) {
                    IEntityLivingBase iEntityLivingBase4 = this.prevTarget;
                    if (iEntityLivingBase4 == null) {
                        Intrinsics.throwNpe();
                    }
                    drawRise(iEntityLivingBase4, f);
                    break;
                }
                break;
            case 1322963594:
                if (lowerCase2.equals("zamorozka")) {
                    IEntityLivingBase iEntityLivingBase5 = this.prevTarget;
                    if (iEntityLivingBase5 == null) {
                        Intrinsics.throwNpe();
                    }
                    drawZamorozka(iEntityLivingBase5, f);
                    break;
                }
                break;
            case 1648341806:
                if (lowerCase2.equals("novoline")) {
                    IEntityLivingBase iEntityLivingBase6 = this.prevTarget;
                    if (iEntityLivingBase6 == null) {
                        Intrinsics.throwNpe();
                    }
                    drawNovo(iEntityLivingBase6, f);
                    break;
                }
                break;
        }
        return getTBorder();
    }

    private final void drawAstolfo(IEntityLivingBase iEntityLivingBase, float f) {
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        Color colorSkyRainbow = ColorUtils.skyRainbow(1, 1.0f, 0.9f, 5.0d);
        float maxHealth = f / iEntityLivingBase.getMaxHealth();
        RenderUtils.drawRect(0.0f, 0.0f, 140.0f, 60.0f, new Color(0, 0, 0, 110).getRGB());
        RenderUtils.drawRect(3.0f, 55.0f, 137.0f, 58.0f, ColorUtils.reAlpha(colorSkyRainbow, 100).getRGB());
        RenderUtils.drawRect(3.0f, 55.0f, 3.0f + (maxHealth * 134.0f), 58.0f, colorSkyRainbow.getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.drawEntityOnScreen(18, 46, 20, iEntityLivingBase);
        String name = iEntityLivingBase.getName();
        if (name == null) {
            Intrinsics.throwNpe();
        }
        iFontRenderer.drawStringWithShadow(name, 37, 6, -1);
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        iFontRenderer.drawString(MathKt.roundToInt(getHealth(iEntityLivingBase)) + " \u2764", 19, 9, colorSkyRainbow.getRGB());
        GL11.glPopMatrix();
    }

    private final void drawNovo(IEntityLivingBase iEntityLivingBase, float f) {
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        Color colorHealthColor$default = ColorUtils.healthColor$default(getHealth(iEntityLivingBase), iEntityLivingBase.getMaxHealth(), 0, 4, null);
        Color colorDarker = ColorUtils.darker(colorHealthColor$default, 0.6f);
        float fRoundToInt = 33.0f + (MathKt.roundToInt((getHealth(iEntityLivingBase) / iEntityLivingBase.getMaxHealth()) * 10000.0f) / 100);
        RenderUtils.drawRect(0.0f, 0.0f, 140.0f, 40.0f, new Color(40, 40, 40).getRGB());
        String name = iEntityLivingBase.getName();
        if (name == null) {
            Intrinsics.throwNpe();
        }
        Color color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
        iFontRenderer.drawString(name, 33, 5, color.getRGB());
        RenderUtils.drawEntityOnScreen(20, 35, 15, iEntityLivingBase);
        RenderUtils.drawRect(fRoundToInt, 18.0f, 33.0f + (MathKt.roundToInt((f / iEntityLivingBase.getMaxHealth()) * 10000.0f) / 100), 25.0f, colorDarker);
        RenderUtils.drawRect(33.0f, 18.0f, fRoundToInt, 25.0f, colorHealthColor$default);
        Color color2 = Color.RED;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.RED");
        iFontRenderer.drawString("\u2764", 33, 30, color2.getRGB());
        String str = this.decimalFormat.format(Float.valueOf(getHealth(iEntityLivingBase)));
        Intrinsics.checkExpressionValueIsNotNull(str, "decimalFormat.format(getHealth(target))");
        Color color3 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
        iFontRenderer.drawString(str, 43, 30, color3.getRGB());
    }

    private final void drawLiquid(IEntityLivingBase iEntityLivingBase, float f) {
        IFontRenderer iFontRenderer = Fonts.font40;
        String name = iEntityLivingBase.getName();
        if (name == null) {
            Intrinsics.throwNpe();
        }
        float fCoerceAtLeast = RangesKt.coerceAtLeast(38 + iFontRenderer.getStringWidth(name), 118);
        Color color = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.BLACK");
        int rgb = color.getRGB();
        Color color2 = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
        RenderUtils.drawBorderedRect(0.0f, 0.0f, fCoerceAtLeast, 36.0f, 3.0f, rgb, color2.getRGB());
        if (f > getHealth(iEntityLivingBase)) {
            RenderUtils.drawRect(0.0f, 34.0f, (f / iEntityLivingBase.getMaxHealth()) * fCoerceAtLeast, 36.0f, new Color(252, 185, 65).getRGB());
        }
        RenderUtils.drawRect(0.0f, 34.0f, (getHealth(iEntityLivingBase) / iEntityLivingBase.getMaxHealth()) * fCoerceAtLeast, 36.0f, new Color(252, 96, 66).getRGB());
        if (f < getHealth(iEntityLivingBase)) {
            RenderUtils.drawRect((f / iEntityLivingBase.getMaxHealth()) * fCoerceAtLeast, 34.0f, (getHealth(iEntityLivingBase) / iEntityLivingBase.getMaxHealth()) * fCoerceAtLeast, 36.0f, new Color(44, 201, SyslogAppender.LOG_LOCAL2).getRGB());
        }
        String name2 = iEntityLivingBase.getName();
        IFontRenderer iFontRenderer2 = Fonts.font40;
        if (name2 == null) {
            Intrinsics.throwNpe();
        }
        iFontRenderer2.drawString(name2, 36, 3, 16777215);
        IFontRenderer iFontRenderer3 = Fonts.font35;
        StringBuilder sbAppend = new StringBuilder().append("Distance: ");
        DecimalFormat decimalFormat = this.decimalFormat;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        iFontRenderer3.drawString(sbAppend.append(decimalFormat.format(PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntityLivingBase))).toString(), 36, 15, 16777215);
        RenderUtils.drawHead(PlayerExtensionKt.getSkin(iEntityLivingBase), 2, 2, 30, 30);
        INetworkPlayerInfo playerInfo = MinecraftInstance.f157mc.getNetHandler().getPlayerInfo(iEntityLivingBase.getUniqueID());
        if (playerInfo != null) {
            Fonts.font35.drawString("Ping: " + RangesKt.coerceAtLeast(playerInfo.getResponseTime(), 0), 36, 24, 16777215);
        }
    }

    private final void drawZamorozka(IEntityLivingBase iEntityLivingBase, float f) {
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        RenderUtils.drawCircleRect(0.0f, 0.0f, 150.0f, 55.0f, 5.0f, new Color(0, 0, 0, 70).getRGB());
        RenderUtils.drawRect(7.0f, 7.0f, 35.0f, 40.0f, new Color(0, 0, 0, 70).getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.drawEntityOnScreen(21, 38, 15, iEntityLivingBase);
        RenderUtils.drawCircleRect(7.0f, 45.0f, 143.0f, 50.0f, 2.5f, new Color(0, 0, 0, 70).getRGB());
        RenderUtils.drawCircleRect(7.0f, 45.0f, 7.0f + RangesKt.coerceAtLeast((f / iEntityLivingBase.getMaxHealth()) * 136.0f, 5.0f), 50.0f, 2.5f, ColorUtils.rainbowWithAlpha(90).getRGB());
        RenderUtils.drawCircleRect(7.0f, 45.0f, 7.0f + RangesKt.coerceAtLeast((iEntityLivingBase.getHealth() / iEntityLivingBase.getMaxHealth()) * 136.0f, 5.0f), 50.0f, 2.5f, ColorUtils.rainbow().getRGB());
        RenderUtils.drawCircleRect(43.0f, 15.0f - iFontRenderer.getFontHeight(), 143.0f, 17.0f, (iFontRenderer.getFontHeight() + 1) * 0.45f, new Color(0, 0, 0, 70).getRGB());
        iFontRenderer.drawCenteredString(iEntityLivingBase.getName() + ' ' + (PlayerExtensionKt.getPing(iEntityLivingBase) != -1 ? "\u00a7f" + PlayerExtensionKt.getPing(iEntityLivingBase) + "ms" : ""), 93.0f, 16.0f - iFontRenderer.getFontHeight(), ColorUtils.rainbow().getRGB(), false);
        String str = "Health: " + this.decimalFormat.format(Float.valueOf(f)) + " \u00a77/ " + this.decimalFormat.format(Float.valueOf(iEntityLivingBase.getMaxHealth()));
        int fontHeight = 11 + iFontRenderer.getFontHeight();
        Color color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
        iFontRenderer.drawString(str, 43, fontHeight, color.getRGB());
        StringBuilder sbAppend = new StringBuilder().append("Distance: ");
        DecimalFormat decimalFormat = this.decimalFormat;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        String string = sbAppend.append(decimalFormat.format(PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntityLivingBase))).toString();
        int fontHeight2 = 11 + (iFontRenderer.getFontHeight() * 2);
        Color color2 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.WHITE");
        iFontRenderer.drawString(string, 43, fontHeight2, color2.getRGB());
    }

    private final void drawMoon(IEntityLivingBase iEntityLivingBase, float f) {
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        String str = this.decimalFormat.format(Float.valueOf(f));
        int iCoerceAtLeast = RangesKt.coerceAtLeast(iFontRenderer.getStringWidth(iEntityLivingBase.getName() + "  " + str + " hp"), 75);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        float fontHeight = 5 + iFontRenderer.getFontHeight() + 3.0f;
        Intrinsics.checkExpressionValueIsNotNull(this.decimalFormat.format(Float.valueOf(iEntityLivingBase.getMaxHealth())), "decimalFormat.format(target.maxHealth)");
        IntProgression intProgressionStep = RangesKt.step(new IntRange(5, (int) (5.0f + ((135 - iFontRenderer.getStringWidth(r3)) * (f / iEntityLivingBase.getMaxHealth())))), 5);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (true) {
                RenderUtils.quickDrawGradientSideways(first - 5.0d, 0.0d, (45.0d + iCoerceAtLeast) - 1.0d, 1.0d, ColorUtils.hslRainbow$default(first, 0.0f, 0.0f, 10, 0, 0.0f, 0.0f, 118, null).getRGB(), ColorUtils.hslRainbow$default(RangesKt.coerceAtMost(first + 5, r0), 0.0f, 0.0f, 0, 0, 0.0f, 0.0f, 118, null).getRGB());
                if (first == last) {
                    break;
                } else {
                    first += step;
                }
            }
        }
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.drawRect(37.0f, fontHeight + 5.0f, 37.0f + iCoerceAtLeast, fontHeight + 13.0f, new Color(0, 0, 0, 100).getRGB());
        if (iEntityLivingBase.getHealth() <= iEntityLivingBase.getMaxHealth()) {
            RenderUtils.drawCircleRect(37.0f, fontHeight + 5.0f, 37.0f + (MathKt.roundToInt((f / iEntityLivingBase.getMaxHealth()) * 8100.0f) / 100), fontHeight + 13.0f, 3.0f, new Color(0, 255, 0).getRGB());
        }
        if (iEntityLivingBase.getHealth() < iEntityLivingBase.getMaxHealth() / 2.0f) {
            RenderUtils.drawCircleRect(37.0f, fontHeight + 5.0f, 37.0f + (MathKt.roundToInt((f / iEntityLivingBase.getMaxHealth()) * 8100.0f) / 100), fontHeight + 13.0f, 3.0f, new Color(255, 255, 0).getRGB());
        }
        if (iEntityLivingBase.getHealth() < iEntityLivingBase.getMaxHealth() / 4.0f) {
            RenderUtils.drawCircleRect(37.0f, fontHeight + 5.0f, 37.0f + (MathKt.roundToInt((f / iEntityLivingBase.getMaxHealth()) * 8100.0f) / 100), fontHeight + 13.0f, 3.0f, new Color(255, 0, 0).getRGB());
        }
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String name = iEntityLivingBase.getName();
        if (name == null) {
            Intrinsics.throwNpe();
        }
        Color color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
        iFontRenderer.drawString(name, 37, 5, color.getRGB());
        RenderUtils.drawHead(PlayerExtensionKt.getSkin(iEntityLivingBase), 2, 2, 32, 32);
        GL11.glScaled(0.7d, 0.7d, 0.7d);
        Color color2 = Color.LIGHT_GRAY;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.LIGHT_GRAY");
        iFontRenderer.drawString(str + " hp", 53, 23, color2.getRGB());
    }

    private final void drawRise(IEntityLivingBase iEntityLivingBase, float f) {
        float f2;
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        RenderUtils.drawCircleRect(0.0f, 0.0f, 150.0f, 50.0f, 5.0f, new Color(0, 0, 0, 130).getRGB());
        float hurtPercent = PlayerExtensionKt.getHurtPercent(iEntityLivingBase);
        if (hurtPercent == 0.0f) {
            f2 = 1.0f;
        } else if (hurtPercent < 0.5f) {
            f2 = 1.0f - ((0.2f * hurtPercent) * 2.0f);
        } else {
            f2 = 0.8f + (0.2f * (hurtPercent - 0.5f) * 2.0f);
        }
        float f3 = f2;
        GL11.glPushMatrix();
        GL11.glTranslatef(5.0f, 5.0f, 0.0f);
        GL11.glScalef(f3, f3, f3);
        GL11.glTranslatef((15.0f * (1.0f - f3)) / f3, (15.0f * (1.0f - f3)) / f3, 0.0f);
        GL11.glColor4f(1.0f, 1.0f - hurtPercent, 1.0f - hurtPercent, 1.0f);
        RenderUtils.quickDrawHead(PlayerExtensionKt.getSkin(iEntityLivingBase), 0, 0, 30, 30);
        GL11.glPopMatrix();
        String str = "Name " + iEntityLivingBase.getName();
        Color color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
        iFontRenderer.drawString(str, 40, 11, color.getRGB());
        StringBuilder sbAppend = new StringBuilder().append("Distance ");
        DecimalFormat decimalFormat = this.decimalFormat;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        String string = sbAppend.append(decimalFormat.format(PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntityLivingBase))).append(" Hurt ").append(iEntityLivingBase.getHurtTime()).toString();
        int fontHeight = 11 + iFontRenderer.getFontHeight();
        Color color2 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.WHITE");
        iFontRenderer.drawString(string, 40, fontHeight, color2.getRGB());
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        C04751 c04751 = C04751.INSTANCE;
        Intrinsics.checkExpressionValueIsNotNull(this.decimalFormat.format(Float.valueOf(iEntityLivingBase.getMaxHealth())), "decimalFormat.format(target.maxHealth)");
        int stringWidth = (int) (5.0f + ((135 - iFontRenderer.getStringWidth(r3)) * (f / iEntityLivingBase.getMaxHealth())));
        IntProgression intProgressionStep = RangesKt.step(new IntRange(5, stringWidth), 5);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (true) {
                c04751.invoke(first, RangesKt.coerceAtMost(first + 5, stringWidth));
                if (first == last) {
                    break;
                } else {
                    first += step;
                }
            }
        }
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String str2 = this.decimalFormat.format(Float.valueOf(f));
        Intrinsics.checkExpressionValueIsNotNull(str2, "decimalFormat.format(easingHealth)");
        int fontHeight2 = 43 - (iFontRenderer.getFontHeight() / 2);
        Color color3 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
        iFontRenderer.drawString(str2, stringWidth + 5, fontHeight2, color3.getRGB());
    }

    private final void drawFlux(IEntityLivingBase iEntityLivingBase, float f) {
        IFontRenderer iFontRenderer = Fonts.font40;
        String name = iEntityLivingBase.getName();
        if (name == null) {
            Intrinsics.throwNpe();
        }
        float fCoerceAtLeast = RangesKt.coerceAtLeast(38 + iFontRenderer.getStringWidth(name), 70);
        RenderUtils.drawRect(0.0f, 0.0f, fCoerceAtLeast, 34.0f, new Color(40, 40, 40).getRGB());
        Color color = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.BLACK");
        RenderUtils.drawRect(2.0f, 22.0f, fCoerceAtLeast - 2.0f, 24.0f, color.getRGB());
        Color color2 = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
        RenderUtils.drawRect(2.0f, 28.0f, fCoerceAtLeast - 2.0f, 30.0f, color2.getRGB());
        RenderUtils.drawRect(2.0f, 22.0f, 2.0f + ((f / iEntityLivingBase.getMaxHealth()) * (fCoerceAtLeast - 4.0f)), 24.0f, new Color(231, Typography.paragraph, 0).getRGB());
        RenderUtils.drawRect(2.0f, 22.0f, 2.0f + ((getHealth(iEntityLivingBase) / iEntityLivingBase.getMaxHealth()) * (fCoerceAtLeast - 4.0f)), 24.0f, new Color(0, 224, 84).getRGB());
        RenderUtils.drawRect(2.0f, 28.0f, 2.0f + ((iEntityLivingBase.getTotalArmorValue() / 20.0f) * (fCoerceAtLeast - 4.0f)), 30.0f, new Color(77, 128, 255).getRGB());
        IFontRenderer iFontRenderer2 = Fonts.font40;
        String name2 = iEntityLivingBase.getName();
        if (name2 == null) {
            Intrinsics.throwNpe();
        }
        Color color3 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
        iFontRenderer2.drawString(name2, 22, 3, color3.getRGB());
        GL11.glPushMatrix();
        GL11.glScaled(0.7d, 0.7d, 0.7d);
        Color color4 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color4, "Color.WHITE");
        Fonts.font35.drawString("Health: " + this.decimalFormat.format(Float.valueOf(getHealth(iEntityLivingBase))), 31.428572f, (4 + Fonts.font40.getFontHeight()) / 0.7f, color4.getRGB());
        GL11.glPopMatrix();
        RenderUtils.drawHead(PlayerExtensionKt.getSkin(iEntityLivingBase), 2, 2, 16, 16);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final Border getTBorder() {
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1102567108:
                if (lowerCase.equals("liquid")) {
                    IFontRenderer iFontRenderer = Fonts.font40;
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer.getName() == null) {
                        Intrinsics.throwNpe();
                    }
                    return new Border(0.0f, 0.0f, 38.0f + RangesKt.coerceAtLeast(iFontRenderer.getStringWidth(r6), 118), 36.0f);
                }
                return null;
            case -703561496:
                if (lowerCase.equals("astolfo")) {
                    return new Border(0.0f, 0.0f, 140.0f, 60.0f);
                }
                return null;
            case 3146217:
                if (lowerCase.equals("flux")) {
                    IFontRenderer iFontRenderer2 = Fonts.font40;
                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer2.getName() == null) {
                        Intrinsics.throwNpe();
                    }
                    return new Border(0.0f, 0.0f, RangesKt.coerceAtLeast(38 + iFontRenderer2.getStringWidth(r6), 70), 34.0f);
                }
                return null;
            case 3500745:
                if (lowerCase.equals("rise")) {
                    return new Border(0.0f, 0.0f, 150.0f, 55.0f);
                }
                return null;
            case 1322963594:
                if (lowerCase.equals("zamorozka")) {
                    return new Border(0.0f, 0.0f, 150.0f, 55.0f);
                }
                return null;
            case 1648341806:
                if (lowerCase.equals("novoline")) {
                    return new Border(0.0f, 0.0f, 140.0f, 40.0f);
                }
                return null;
            case 1949242831:
                if (lowerCase.equals("exhibition")) {
                    return new Border(0.0f, 0.0f, 140.0f, 45.0f);
                }
                return null;
            default:
                return null;
        }
    }
}

package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "TabGUI")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdn\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\ufffd\ufffd2\u00020\u0001:\u0002:;B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\n\u0010/\u001a\u0004\u0018\u000100H\u0016J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020#H\u0016J\u0010\u00106\u001a\u0002022\u0006\u00107\u001a\u000208H\u0002J\b\u00109\u001a\u000202H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020#X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020#X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0018\u0010'\u001a\f\u0012\b\u0012\u00060)R\u00020\ufffd\ufffd0(X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010*\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010+\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010,\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010-\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010.\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006<"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "arrowsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "backgroundAlphaValue", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "blueValue", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRainbow", "borderRedValue", "borderStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "borderValue", "categoryMenu", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "itemY", "", "rainbowX", "rainbowY", "rectangleRainbow", "redValue", "selectedCategory", "", "selectedModule", "tabHeight", "tabY", "tabs", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab;", "textFade", "textPositionY", "textShadow", "upperCaseValue", "width", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleKey", "", "c", "", "keyCode", "parseAction", "action", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action;", "updateAnimation", "Action", "Tab", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI.class */
public final class TabGUI extends Element {
    private final FloatValue rainbowX;
    private final FloatValue rainbowY;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue alphaValue;
    private final BoolValue rectangleRainbow;
    private final IntegerValue backgroundRedValue;
    private final IntegerValue backgroundGreenValue;
    private final IntegerValue backgroundBlueValue;
    private final IntegerValue backgroundAlphaValue;
    private final BoolValue borderValue;
    private final FloatValue borderStrength;
    private final IntegerValue borderRedValue;
    private final IntegerValue borderGreenValue;
    private final IntegerValue borderBlueValue;
    private final IntegerValue borderAlphaValue;
    private final BoolValue borderRainbow;
    private final BoolValue arrowsValue;
    private final FontValue fontValue;
    private final BoolValue textShadow;
    private final BoolValue textFade;
    private final FloatValue textPositionY;
    private final FloatValue width;
    private final FloatValue tabHeight;
    private final BoolValue upperCaseValue;
    private final List tabs;
    private boolean categoryMenu;
    private int selectedCategory;
    private int selectedModule;
    private float tabY;
    private float itemY;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action;", "", "(Ljava/lang/String;I)V", "UP", "DOWN", "LEFT", "RIGHT", "TOGGLE", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action.class */
    public enum Action {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        TOGGLE
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[Action.values().length];

        static {
            $EnumSwitchMapping$0[Action.UP.ordinal()] = 1;
            $EnumSwitchMapping$0[Action.DOWN.ordinal()] = 2;
            $EnumSwitchMapping$0[Action.LEFT.ordinal()] = 3;
            $EnumSwitchMapping$0[Action.RIGHT.ordinal()] = 4;
            $EnumSwitchMapping$0[Action.TOGGLE.ordinal()] = 5;
        }
    }

    public TabGUI() {
        this(0.0d, 0.0d, 3, null);
    }

    public TabGUI(double d, double d2) {
        super(d, d2, 0.0f, null, 12, null);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0f, -2000.0f, 2000.0f);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0f, -2000.0f, 2000.0f);
        this.redValue = new IntegerValue("Rectangle Red", 0, 0, 255);
        this.greenValue = new IntegerValue("Rectangle Green", 148, 0, 255);
        this.blueValue = new IntegerValue("Rectangle Blue", 255, 0, 255);
        this.alphaValue = new IntegerValue("Rectangle Alpha", 140, 0, 255);
        this.rectangleRainbow = new BoolValue("Rectangle Rainbow", false);
        this.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
        this.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
        this.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
        this.backgroundAlphaValue = new IntegerValue("Background Alpha", 150, 0, 255);
        this.borderValue = new BoolValue("Border", true);
        this.borderStrength = new FloatValue("Border Strength", 2.0f, 1.0f, 5.0f);
        this.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
        this.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
        this.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
        this.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
        this.borderRainbow = new BoolValue("Border Rainbow", false);
        this.arrowsValue = new BoolValue("Arrows", true);
        IFontRenderer iFontRenderer = Fonts.font35;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font35");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.textShadow = new BoolValue("TextShadow", false);
        this.textFade = new BoolValue("TextFade", true);
        this.textPositionY = new FloatValue("TextPosition-Y", 2.0f, 0.0f, 5.0f);
        this.width = new FloatValue("Width", 60.0f, 55.0f, 100.0f);
        this.tabHeight = new FloatValue("TabHeight", 12.0f, 10.0f, 15.0f);
        this.upperCaseValue = new BoolValue("UpperCase", false);
        this.tabs = new ArrayList();
        this.categoryMenu = true;
        ModuleCategory[] moduleCategoryArrValues = ModuleCategory.values();
        int length = moduleCategoryArrValues.length;
        for (int i = 0; i < length; i++) {
            ModuleCategory moduleCategory = moduleCategoryArrValues[i];
            Tab tab = new Tab(this, moduleCategory.getDisplayName());
            TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
            ArrayList arrayList = new ArrayList();
            for (Object obj : modules) {
                if (moduleCategory == ((Module) obj).getCategory()) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                tab.getModules().add((Module) it.next());
            }
            this.tabs.add(tab);
        }
    }

    public TabGUI(double d, double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 5.0d : d, (i & 2) != 0 ? 25.0d : d2);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        Color color;
        Color color2;
        String tabName;
        float textFade;
        float fFloatValue;
        updateAnimation();
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        boolean zBooleanValue = ((Boolean) this.rectangleRainbow.get()).booleanValue();
        Color color3 = new Color(((Number) this.backgroundRedValue.get()).intValue(), ((Number) this.backgroundGreenValue.get()).intValue(), ((Number) this.backgroundBlueValue.get()).intValue(), ((Number) this.backgroundAlphaValue.get()).intValue());
        if (!((Boolean) this.borderRainbow.get()).booleanValue()) {
            color = new Color(((Number) this.borderRedValue.get()).intValue(), ((Number) this.borderGreenValue.get()).intValue(), ((Number) this.borderBlueValue.get()).intValue(), ((Number) this.borderAlphaValue.get()).intValue());
        } else {
            color = Color.black;
        }
        Color borderColor = color;
        float size = this.tabs.size() * ((Number) this.tabHeight.get()).floatValue();
        RenderUtils.drawRect(1.0f, 0.0f, ((Number) this.width.get()).floatValue(), size, color3.getRGB());
        if (((Boolean) this.borderValue.get()).booleanValue()) {
            RainbowShader.Companion companion = RainbowShader.Companion;
            boolean zBooleanValue2 = ((Boolean) this.borderRainbow.get()).booleanValue();
            float fFloatValue2 = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
            float fFloatValue3 = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
            float fCurrentTimeMillis = (System.currentTimeMillis() % 10000) / 10000.0f;
            RainbowShader rainbowShader = RainbowShader.INSTANCE;
            if (zBooleanValue2) {
                rainbowShader.setStrengthX(fFloatValue2);
                rainbowShader.setStrengthY(fFloatValue3);
                rainbowShader.setOffset(fCurrentTimeMillis);
                rainbowShader.startShader();
            }
            RainbowShader rainbowShader2 = rainbowShader;
            Throwable th = (Throwable) null;
            try {
                float fFloatValue4 = ((Number) this.width.get()).floatValue();
                float fFloatValue5 = ((Number) this.borderStrength.get()).floatValue();
                Intrinsics.checkExpressionValueIsNotNull(borderColor, "borderColor");
                RenderUtils.drawBorder(1.0f, 0.0f, fFloatValue4, size, fFloatValue5, borderColor.getRGB());
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(rainbowShader2, th);
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (!zBooleanValue) {
            color2 = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue(), ((Number) this.alphaValue.get()).intValue());
        } else {
            color2 = Color.black;
        }
        Color rectColor = color2;
        RainbowShader.Companion companion2 = RainbowShader.Companion;
        float fFloatValue6 = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
        float fFloatValue7 = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
        float fCurrentTimeMillis2 = (System.currentTimeMillis() % 10000) / 10000.0f;
        RainbowShader rainbowShader3 = RainbowShader.INSTANCE;
        if (zBooleanValue) {
            rainbowShader3.setStrengthX(fFloatValue6);
            rainbowShader3.setStrengthY(fFloatValue7);
            rainbowShader3.setOffset(fCurrentTimeMillis2);
            rainbowShader3.startShader();
        }
        RainbowShader rainbowShader4 = rainbowShader3;
        Throwable th3 = (Throwable) null;
        try {
            RenderUtils.drawRect(1.0f, (1.0f + this.tabY) - 1.0f, ((Number) this.width.get()).floatValue(), this.tabY + ((Number) this.tabHeight.get()).floatValue(), rectColor);
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(rainbowShader4, th3);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            float fFloatValue8 = 1.0f;
            int i = 0;
            for (Object obj : this.tabs) {
                int i2 = i;
                i++;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Tab tab = (Tab) obj;
                if (((Boolean) this.upperCaseValue.get()).booleanValue()) {
                    String tabName2 = tab.getTabName();
                    if (tabName2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    tabName = tabName2.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(tabName, "(this as java.lang.String).toUpperCase()");
                } else {
                    tabName = tab.getTabName();
                }
                String str = tabName;
                if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                    textFade = ((((Number) this.width.get()).floatValue() - iFontRenderer.getStringWidth(str)) - tab.getTextFade()) - 3.0f;
                } else {
                    textFade = tab.getTextFade() + 5.0f;
                }
                iFontRenderer.drawString(str, textFade, fFloatValue8 + ((Number) this.textPositionY.get()).floatValue(), this.selectedCategory == i2 ? 16777215 : new Color(210, 210, 210).getRGB(), ((Boolean) this.textShadow.get()).booleanValue());
                if (((Boolean) this.arrowsValue.get()).booleanValue()) {
                    if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                        iFontRenderer.drawString((this.categoryMenu || this.selectedCategory != i2) ? "<" : ">", 3.0f, fFloatValue8 + 2.0f, 16777215, ((Boolean) this.textShadow.get()).booleanValue());
                    } else {
                        iFontRenderer.drawString((this.categoryMenu || this.selectedCategory != i2) ? ">" : "<", ((Number) this.width.get()).floatValue() - 8.0f, fFloatValue8 + 2.0f, 16777215, ((Boolean) this.textShadow.get()).booleanValue());
                    }
                }
                if (i2 == this.selectedCategory && !this.categoryMenu) {
                    if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                        fFloatValue = 1.0f - tab.getMenuWidth();
                    } else {
                        fFloatValue = ((Number) this.width.get()).floatValue() + 5.0f;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(rectColor, "rectColor");
                    int rgb = rectColor.getRGB();
                    int rgb2 = color3.getRGB();
                    Intrinsics.checkExpressionValueIsNotNull(borderColor, "borderColor");
                    tab.drawTab(fFloatValue, fFloatValue8, rgb, rgb2, borderColor.getRGB(), ((Number) this.borderStrength.get()).floatValue(), ((Boolean) this.upperCaseValue.get()).booleanValue(), iFontRenderer, ((Boolean) this.borderRainbow.get()).booleanValue(), zBooleanValue);
                }
                fFloatValue8 += ((Number) this.tabHeight.get()).floatValue();
            }
            AWTFontRenderer.Companion.setAssumeNonVolatile(false);
            return new Border(1.0f, 0.0f, ((Number) this.width.get()).floatValue(), size);
        } catch (Throwable th4) {
            throw th4;
        }
    }

    public void handleKey(char c, int i) {
        switch (i) {
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                parseAction(Action.TOGGLE);
                break;
            case SharedScopeCall.FAST_SCOPE_GET_THRESHOLD /* 200 */:
                parseAction(Action.UP);
                break;
            case 203:
                parseAction(getSide().getHorizontal() == Side.Horizontal.RIGHT ? Action.RIGHT : Action.LEFT);
                break;
            case 205:
                parseAction(getSide().getHorizontal() == Side.Horizontal.RIGHT ? Action.LEFT : Action.RIGHT);
                break;
            case 208:
                parseAction(Action.DOWN);
                break;
        }
    }

    private final void updateAnimation() {
        int i = RenderUtils.deltaTime;
        float fFloatValue = ((Number) this.tabHeight.get()).floatValue() * this.selectedCategory;
        if (((int) this.tabY) == ((int) fFloatValue)) {
            this.tabY = fFloatValue;
        } else if (fFloatValue > this.tabY) {
            this.tabY += 0.1f * i;
        } else {
            this.tabY -= 0.1f * i;
        }
        float fFloatValue2 = ((Number) this.tabHeight.get()).floatValue() * this.selectedModule;
        if (((int) this.itemY) == ((int) fFloatValue2)) {
            this.itemY = fFloatValue2;
        } else if (fFloatValue2 > this.itemY) {
            this.itemY += 0.1f * i;
        } else {
            this.itemY -= 0.1f * i;
        }
        if (this.categoryMenu) {
            this.itemY = 0.0f;
        }
        if (!((Boolean) this.textFade.get()).booleanValue()) {
            for (Tab tab : this.tabs) {
                if (tab.getTextFade() > 0.0f) {
                    tab.setTextFade(tab.getTextFade() - (0.05f * i));
                }
                if (tab.getTextFade() < 0.0f) {
                    tab.setTextFade(0.0f);
                }
            }
            return;
        }
        int i2 = 0;
        for (Object obj : this.tabs) {
            int i3 = i2;
            i2++;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Tab tab2 = (Tab) obj;
            if (i3 == this.selectedCategory) {
                if (tab2.getTextFade() < 4.0f) {
                    tab2.setTextFade(tab2.getTextFade() + (0.05f * i));
                }
                if (tab2.getTextFade() > 4.0f) {
                    tab2.setTextFade(4.0f);
                }
            } else {
                if (tab2.getTextFade() > 0.0f) {
                    tab2.setTextFade(tab2.getTextFade() - (0.05f * i));
                }
                if (tab2.getTextFade() < 0.0f) {
                    tab2.setTextFade(0.0f);
                }
            }
        }
    }

    private final void parseAction(Action action) {
        boolean z = false;
        switch (WhenMappings.$EnumSwitchMapping$0[action.ordinal()]) {
            case 1:
                if (this.categoryMenu) {
                    this.selectedCategory--;
                    int i = this.selectedCategory;
                    if (this.selectedCategory < 0) {
                        this.selectedCategory = this.tabs.size() - 1;
                        this.tabY = ((Number) this.tabHeight.get()).floatValue() * this.selectedCategory;
                        break;
                    }
                } else {
                    this.selectedModule--;
                    int i2 = this.selectedModule;
                    if (this.selectedModule < 0) {
                        this.selectedModule = ((Tab) this.tabs.get(this.selectedCategory)).getModules().size() - 1;
                        this.itemY = ((Number) this.tabHeight.get()).floatValue() * this.selectedModule;
                        break;
                    }
                }
                break;
            case 2:
                if (this.categoryMenu) {
                    this.selectedCategory++;
                    int i3 = this.selectedCategory;
                    if (this.selectedCategory > this.tabs.size() - 1) {
                        this.selectedCategory = 0;
                        this.tabY = ((Number) this.tabHeight.get()).floatValue() * this.selectedCategory;
                        break;
                    }
                } else {
                    this.selectedModule++;
                    int i4 = this.selectedModule;
                    if (this.selectedModule > ((Tab) this.tabs.get(this.selectedCategory)).getModules().size() - 1) {
                        this.selectedModule = 0;
                        this.itemY = ((Number) this.tabHeight.get()).floatValue() * this.selectedModule;
                        break;
                    }
                }
                break;
            case 3:
                if (!this.categoryMenu) {
                    this.categoryMenu = true;
                    break;
                }
                break;
            case 4:
                if (!this.categoryMenu) {
                    z = true;
                    break;
                } else {
                    this.categoryMenu = false;
                    this.selectedModule = 0;
                    break;
                }
            case 5:
                if (!this.categoryMenu) {
                    z = true;
                    break;
                }
                break;
        }
        if (z) {
            ((Module) ((Tab) this.tabs.get(this.selectedCategory)).getModules().get(this.selectedModule)).toggle();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JV\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006&"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab;", "", "tabName", "", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI;Ljava/lang/String;)V", "menuWidth", "", "getMenuWidth", "()I", "setMenuWidth", "(I)V", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "getModules", "()Ljava/util/List;", "getTabName", "()Ljava/lang/String;", "textFade", "", "getTextFade", "()F", "setTextFade", "(F)V", "drawTab", "", "x", "y", "color", "backgroundColor", "borderColor", "borderStrength", "upperCase", "", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "borderRainbow", "rectRainbow", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab.class */
    private final class Tab {

        @NotNull
        private final List modules;
        private int menuWidth;
        private float textFade;

        @NotNull
        private final String tabName;
        final TabGUI this$0;

        @NotNull
        public final String getTabName() {
            return this.tabName;
        }

        public Tab(@NotNull TabGUI tabGUI, String tabName) {
            Intrinsics.checkParameterIsNotNull(tabName, "tabName");
            this.this$0 = tabGUI;
            this.tabName = tabName;
            this.modules = new ArrayList();
        }

        @NotNull
        public final List getModules() {
            return this.modules;
        }

        public final int getMenuWidth() {
            return this.menuWidth;
        }

        public final void setMenuWidth(int i) {
            this.menuWidth = i;
        }

        public final float getTextFade() {
            return this.textFade;
        }

        public final void setTextFade(float f) {
            this.textFade = f;
        }

        public final void drawTab(float f, float f2, int i, int i2, int i3, float f3, boolean z, @NotNull IFontRenderer fontRenderer, boolean z2, boolean z3) {
            String name;
            String name2;
            String name3;
            Intrinsics.checkParameterIsNotNull(fontRenderer, "fontRenderer");
            int stringWidth = 0;
            for (Module module : this.modules) {
                IFontRenderer iFontRenderer = fontRenderer;
                if (z) {
                    String name4 = module.getName();
                    if (name4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String upperCase = name4.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
                    iFontRenderer = iFontRenderer;
                    name2 = upperCase;
                } else {
                    name2 = module.getName();
                }
                if (iFontRenderer.getStringWidth(name2) + 4 > stringWidth) {
                    IFontRenderer iFontRenderer2 = fontRenderer;
                    if (z) {
                        String name5 = module.getName();
                        if (name5 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String upperCase2 = name5.toUpperCase();
                        Intrinsics.checkExpressionValueIsNotNull(upperCase2, "(this as java.lang.String).toUpperCase()");
                        iFontRenderer2 = iFontRenderer2;
                        name3 = upperCase2;
                    } else {
                        name3 = module.getName();
                    }
                    stringWidth = (int) (iFontRenderer2.getStringWidth(name3) + 7.0f);
                }
            }
            this.menuWidth = stringWidth;
            float size = this.modules.size() * ((Number) this.this$0.tabHeight.get()).floatValue();
            if (((Boolean) this.this$0.borderValue.get()).booleanValue()) {
                RainbowShader.Companion companion = RainbowShader.Companion;
                float fFloatValue = ((Number) this.this$0.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.this$0.rainbowX.get()).floatValue();
                float fFloatValue2 = ((Number) this.this$0.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.this$0.rainbowY.get()).floatValue();
                float fCurrentTimeMillis = (System.currentTimeMillis() % 10000) / 10000.0f;
                RainbowShader rainbowShader = RainbowShader.INSTANCE;
                if (z2) {
                    rainbowShader.setStrengthX(fFloatValue);
                    rainbowShader.setStrengthY(fFloatValue2);
                    rainbowShader.setOffset(fCurrentTimeMillis);
                    rainbowShader.startShader();
                }
                RainbowShader rainbowShader2 = rainbowShader;
                Throwable th = (Throwable) null;
                try {
                    RenderUtils.drawBorder(f - 1.0f, f2 - 1.0f, (f + this.menuWidth) - 2.0f, (f2 + size) - 1.0f, f3, i3);
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(rainbowShader2, th);
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            RenderUtils.drawRect(f - 1.0f, f2 - 1.0f, (f + this.menuWidth) - 2.0f, (f2 + size) - 1.0f, i2);
            RainbowShader.Companion companion2 = RainbowShader.Companion;
            float fFloatValue3 = ((Number) this.this$0.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.this$0.rainbowX.get()).floatValue();
            float fFloatValue4 = ((Number) this.this$0.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.this$0.rainbowY.get()).floatValue();
            float fCurrentTimeMillis2 = (System.currentTimeMillis() % 10000) / 10000.0f;
            RainbowShader rainbowShader3 = RainbowShader.INSTANCE;
            if (z3) {
                rainbowShader3.setStrengthX(fFloatValue3);
                rainbowShader3.setStrengthY(fFloatValue4);
                rainbowShader3.setOffset(fCurrentTimeMillis2);
                rainbowShader3.startShader();
            }
            RainbowShader rainbowShader4 = rainbowShader3;
            Throwable th3 = (Throwable) null;
            try {
                RenderUtils.drawRect(f - 1.0f, (f2 + this.this$0.itemY) - 1.0f, (f + this.menuWidth) - 2.0f, ((f2 + this.this$0.itemY) + ((Number) this.this$0.tabHeight.get()).floatValue()) - 1.0f, i);
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(rainbowShader4, th3);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                int i4 = 0;
                for (Object obj : this.modules) {
                    int i5 = i4;
                    i4++;
                    if (i5 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Module module2 = (Module) obj;
                    int rgb = module2.getState() ? 16777215 : new Color(205, 205, 205).getRGB();
                    IFontRenderer iFontRenderer3 = fontRenderer;
                    if (z) {
                        String name6 = module2.getName();
                        if (name6 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String upperCase3 = name6.toUpperCase();
                        Intrinsics.checkExpressionValueIsNotNull(upperCase3, "(this as java.lang.String).toUpperCase()");
                        iFontRenderer3 = iFontRenderer3;
                        name = upperCase3;
                    } else {
                        name = module2.getName();
                    }
                    iFontRenderer3.drawString(name, f + 2.0f, f2 + (((Number) this.this$0.tabHeight.get()).floatValue() * i5) + ((Number) this.this$0.textPositionY.get()).floatValue(), rgb, ((Boolean) this.this$0.textShadow.get()).booleanValue());
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }
}

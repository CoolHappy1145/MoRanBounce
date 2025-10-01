package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.Colors;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorManager;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "Arraylist", single = true)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\n\u0010@\u001a\u0004\u0018\u00010AH\u0016J\b\u0010B\u001a\u00020CH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010#\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010)\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010*\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010+\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010,\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010-\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010.\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010/\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00100\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00101\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00102\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00103\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00104\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00105\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00106\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00107\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00108\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00109\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010:\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010;\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010<\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010=\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010>\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010?\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006D"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "Rianbowb", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "RianbowbValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "Rianbowg", "Rianbowr", "RianbowsValue", "RianbowspeedValue", "animationMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "animationSpeed", "astolfoRainbowIndex", "astolfoRainbowOffset", "backgroundColorAlphaValue", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorModeValue", "backgroundColorRedValue", "brightnessValue", "colorModeValue", "counter", "", "customRainbowSpeed", "fakeName", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "hue", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "outLineRectWidth", "rainbow3Offset", "rainbowTick", "rainbowX", "rainbowY", "rectColorAlphaValue", "rectColorBlueValue", "rectColorGreenValue", "rectColorModeValue", "rectColorRedValue", "rectValue", "rectWidth", "saturationValue", "shadow", "shadowTextMode", "spaceValue", "tags", "tagsArrayColor", "textHeightValue", "textYValue", "upperCaseValue", "x2", "y2", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "updateElement", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist.class */
public final class Arraylist extends Element {
    private final IntegerValue RianbowspeedValue;
    private final FloatValue RianbowbValue;
    private final FloatValue RianbowsValue;
    private final IntegerValue Rianbowr;
    private final IntegerValue Rianbowb;
    private final IntegerValue Rianbowg;
    private final IntegerValue outLineRectWidth;
    private final BoolValue fakeName;
    private final ListValue animationMode;
    private final IntegerValue rainbow3Offset;
    private final FloatValue customRainbowSpeed;
    private final IntegerValue astolfoRainbowOffset;
    private final IntegerValue astolfoRainbowIndex;
    private final FloatValue animationSpeed;
    private final FloatValue rainbowX;
    private final FloatValue rainbowY;
    private final ListValue colorModeValue;
    private final ListValue rectColorModeValue;
    private final IntegerValue rectColorRedValue;
    private final IntegerValue rectColorGreenValue;
    private final IntegerValue rectColorBlueValue;
    private final IntegerValue rectColorAlphaValue;
    private final FloatValue saturationValue;
    private final FloatValue brightnessValue;
    private final BoolValue tags;
    private final BoolValue shadow;
    private final ListValue shadowTextMode;
    private final ListValue backgroundColorModeValue;
    private final IntegerValue backgroundColorRedValue;
    private final IntegerValue backgroundColorGreenValue;
    private final IntegerValue backgroundColorBlueValue;
    private final IntegerValue backgroundColorAlphaValue;
    private final ListValue rectValue;
    private final BoolValue upperCaseValue;
    private final FloatValue spaceValue;
    private final FloatValue textHeightValue;
    private final FloatValue textYValue;
    private final BoolValue tagsArrayColor;
    private final FontValue fontValue;
    private final IntegerValue rectWidth;

    /* renamed from: x2 */
    private int f148x2;

    /* renamed from: y2 */
    private float f149y2;
    private int rainbowTick;
    private int counter;
    private float hue;
    private List modules;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[Side.Horizontal.values().length];
        public static final int[] $EnumSwitchMapping$1;

        static {
            $EnumSwitchMapping$0[Side.Horizontal.RIGHT.ordinal()] = 1;
            $EnumSwitchMapping$0[Side.Horizontal.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$0[Side.Horizontal.LEFT.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[Side.Horizontal.values().length];
            $EnumSwitchMapping$1[Side.Horizontal.RIGHT.ordinal()] = 1;
            $EnumSwitchMapping$1[Side.Horizontal.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$1[Side.Horizontal.LEFT.ordinal()] = 3;
        }
    }

    public Arraylist() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Arraylist(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.RianbowspeedValue = new IntegerValue("BRainbowSpeed", 90, 1, 90);
        this.RianbowbValue = new FloatValue("BRainbow-Saturation", 1.0f, 0.0f, 1.0f);
        this.RianbowsValue = new FloatValue("BRainbow-Brightness", 1.0f, 0.0f, 1.0f);
        this.Rianbowr = new IntegerValue("BRainbow-R", 0, 0, 255);
        this.Rianbowb = new IntegerValue("BRainbow-B", 50, 0, 64);
        this.Rianbowg = new IntegerValue("BRainbow-G", 50, 0, 64);
        this.outLineRectWidth = new IntegerValue("OutLineRectWidth", 3, 3, 6);
        this.fakeName = new BoolValue("NameBreak", true);
        this.animationMode = new ListValue("Animation", new String[]{"Slide", "Normal"}, "Slide");
        this.rainbow3Offset = new IntegerValue("RainbowOffset", 16, 1, 30);
        this.customRainbowSpeed = new FloatValue("RainbowSpeed", 0.6f, 0.1f, 1.0f);
        this.astolfoRainbowOffset = new IntegerValue("AstolfoRainbowOffset", 5, 1, 20);
        this.astolfoRainbowIndex = new IntegerValue("AstolfoRainbowIndex", 109, 1, 300);
        this.animationSpeed = new FloatValue("AnimationSpeed", 0.08f, 0.01f, 0.5f);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0f, -2000.0f, 2000.0f);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0f, -2000.0f, 2000.0f);
        this.colorModeValue = new ListValue("Text-Color", new String[]{"Custom", "Random", "Rainbow", "CustomRainbow", "Bainbow", "RiseRainbow", "OtherRainbow", "Rainbow2", "Rainbow3", "Astolfo"}, "Astolfo");
        this.rectColorModeValue = new ListValue("Rect-Color", new String[]{"Custom", "Random", "CustomRainbow", "Rainbow", "Bainbow", "OtherRainbow", "Rainbow2", "Rainbow3", "Astolfo", "RiseRainbow"}, "Astolfo");
        this.rectColorRedValue = new IntegerValue("Rect-R", 255, 0, 255);
        this.rectColorGreenValue = new IntegerValue("Rect-G", 255, 0, 255);
        this.rectColorBlueValue = new IntegerValue("Rect-B", 255, 0, 255);
        this.rectColorAlphaValue = new IntegerValue("Rect-Alpha", 255, 0, 255);
        this.saturationValue = new FloatValue("Random-Saturation", 0.9f, 0.0f, 1.0f);
        this.brightnessValue = new FloatValue("Random-Brightness", 1.0f, 0.0f, 1.0f);
        this.tags = new BoolValue("Tags", true);
        this.shadow = new BoolValue("ShadowText", true);
        this.shadowTextMode = new ListValue("ShadowTextMode", new String[]{"Minecraft", "New"}, "New");
        this.backgroundColorModeValue = new ListValue("Background-Color", new String[]{"Custom", "Random", "Rainbow"}, "Custom");
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255);
        this.rectValue = new ListValue("Rect", new String[]{"None", "Left", "Right", "OutLine"}, "None");
        this.upperCaseValue = new BoolValue("UpperCase", false);
        this.spaceValue = new FloatValue("Space", 0.0f, 0.0f, 5.0f);
        this.textHeightValue = new FloatValue("TextHeight", 11.0f, 1.0f, 20.0f);
        this.textYValue = new FloatValue("TextY", 1.0f, 0.0f, 20.0f);
        this.tagsArrayColor = new BoolValue("TagsArrayColor", false);
        IFontRenderer iFontRenderer = Fonts.Sfui24;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.Sfui24");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.rectWidth = new IntegerValue("LeftRectWidth", 1, 1, 3);
        this.modules = CollectionsKt.emptyList();
    }

    public Arraylist(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0d : d, (i & 2) != 0 ? 2.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.UP) : side);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0870  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0887  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x088d  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x089e  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x08a4 A[Catch: Throwable -> 0x08c8, all -> 0x08cd, TryCatch #7 {all -> 0x08cd, Throwable -> 0x08c8, blocks: (B:130:0x080e, B:131:0x0825, B:132:0x0840, B:141:0x0871, B:145:0x088e, B:152:0x08b7, B:148:0x08a4, B:135:0x084e, B:139:0x0860), top: B:539:0x080e }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x08f7  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0900  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x090a  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x099b  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x099f  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x09c2  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x09c6  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x09ea  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0a3c  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0a40  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0a53  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0a57 A[Catch: Throwable -> 0x0c03, all -> 0x0c08, TryCatch #9 {all -> 0x0c08, Throwable -> 0x0c03, blocks: (B:179:0x0a16, B:183:0x0a41, B:226:0x0bf1, B:186:0x0a57, B:189:0x0a68, B:191:0x0a74, B:192:0x0a83, B:194:0x0a8f, B:195:0x0a9f, B:197:0x0aab, B:198:0x0abb, B:200:0x0ac7, B:201:0x0ad7, B:204:0x0ae8, B:206:0x0af4, B:207:0x0b12, B:209:0x0b1e, B:210:0x0b2e, B:212:0x0b3a, B:213:0x0b4a, B:215:0x0b56, B:216:0x0b66, B:218:0x0b72, B:219:0x0b8e, B:221:0x0b9a, B:222:0x0bc0, B:224:0x0bcc), top: B:535:0x0a16 }] */
    /* JADX WARN: Removed duplicated region for block: B:377:0x1377  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x1384  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x1388 A[Catch: Throwable -> 0x13ac, all -> 0x13b1, TryCatch #10 {all -> 0x13b1, Throwable -> 0x13ac, blocks: (B:367:0x1311, B:368:0x1329, B:369:0x1344, B:378:0x1378, B:385:0x139b, B:381:0x1388, B:372:0x1352, B:376:0x1364), top: B:533:0x1311 }] */
    /* JADX WARN: Removed duplicated region for block: B:395:0x13da  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x13de  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x1401  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x1405  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x1429  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x146b  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x146f A[Catch: Throwable -> 0x15a4, all -> 0x15a9, TryCatch #8 {all -> 0x15a9, Throwable -> 0x15a4, blocks: (B:405:0x1455, B:430:0x1592, B:408:0x146f, B:411:0x1480, B:413:0x148c, B:414:0x149b, B:416:0x14a7, B:417:0x14fb, B:420:0x150c, B:422:0x1518, B:423:0x1536, B:425:0x1542, B:426:0x155e, B:428:0x156a), top: B:537:0x1455 }] */
    /* JADX WARN: Removed duplicated region for block: B:440:0x15d6  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x15da  */
    /* JADX WARN: Removed duplicated region for block: B:444:0x15fd  */
    /* JADX WARN: Removed duplicated region for block: B:445:0x1601  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x162a  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x1663  */
    /* JADX WARN: Removed duplicated region for block: B:552:0x0c20 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:555:0x0475 A[SYNTHETIC] */
    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Border drawElement() {
        String strTagName;
        int iIntValue;
        int i;
        boolean zEquals;
        RainbowFontShader rainbowFontShader;
        Throwable th;
        int iAstolfoRainbow;
        boolean zEquals2;
        RainbowShader rainbowShader;
        Throwable th2;
        int iAstolfoRainbow2;
        String strTagName2;
        int iIntValue2;
        int i2;
        Integer numValueOf;
        boolean zEquals3;
        RainbowFontShader rainbowFontShader2;
        Throwable th3;
        int rgb;
        int rgb2;
        String strTagName3;
        String strTagName4;
        int[] iArr = {0};
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            Intrinsics.throwNpe();
        }
        Color color = new Color(Color.HSBtoRGB(((float) ((r0.getTicksExisted() / 50.0d) + Math.sin((this.rainbowTick / 50) * 1.6d))) % 1.0f, 0.5f, 1.0f));
        new Color(0, color.getRed(), color.getBlue()).getRGB();
        if (this.hue > 255.0f) {
            this.hue = 0.0f;
        }
        float f = this.hue;
        float f2 = this.hue + 85.0f;
        float f3 = this.hue + 170.0f;
        if (f > 255.0f) {
            f = 0.0f;
        }
        if (f2 > 255.0f) {
            f2 -= 255.0f;
        }
        if (f3 > 255.0f) {
            f3 -= 255.0f;
        }
        Color color33 = Color.getHSBColor(f / 255.0f, 0.9f, 1.0f);
        Color color332 = Color.getHSBColor(f2 / 255.0f, 0.9f, 1.0f);
        Color color333 = Color.getHSBColor(f3 / 255.0f, 0.9f, 1.0f);
        Intrinsics.checkExpressionValueIsNotNull(color33, "color33");
        color33.getRGB();
        Intrinsics.checkExpressionValueIsNotNull(color332, "color332");
        color332.getRGB();
        Intrinsics.checkExpressionValueIsNotNull(color333, "color333");
        color333.getRGB();
        this.hue += 1.0f;
        this.rainbowTick++;
        if (this.rainbowTick >= 100) {
            this.rainbowTick = 0;
        }
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IMinecraft mc = MinecraftInstance.f157mc;
        Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
        iClassProvider.createScaledResolution(mc);
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        int i3 = RenderUtils.deltaTime;
        Iterator it = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
        while (it.hasNext()) {
            Module module = (Module) it.next();
            if (module.getArray() && (module.getState() || module.getSlide() != 0.0f)) {
                if (!((Boolean) this.tags.get()).booleanValue()) {
                    strTagName4 = module.fakeName(((Boolean) this.fakeName.get()).booleanValue());
                } else if (((Boolean) this.tagsArrayColor.get()).booleanValue()) {
                    strTagName4 = module.colorlessTagName(((Boolean) this.fakeName.get()).booleanValue());
                } else {
                    strTagName4 = module.tagName(((Boolean) this.fakeName.get()).booleanValue());
                }
                String str = strTagName4;
                if (((Boolean) this.upperCaseValue.get()).booleanValue()) {
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String upperCase = str.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
                    str = upperCase;
                }
                int stringWidth = iFontRenderer.getStringWidth(str);
                if (module.getState()) {
                    if (module.getSlide() < stringWidth) {
                        float slideStep = (module.getSlideStep() / stringWidth) - 1.0f;
                        module.setSlide(((slideStep * slideStep * slideStep) + 1.0f) * stringWidth);
                        module.setSlideStep(module.getSlideStep() + (i3 / 4.0f));
                    }
                } else if (module.getSlide() > 0.0f) {
                    float slideStep2 = (module.getSlideStep() / stringWidth) - 1.0f;
                    module.setSlide(((slideStep2 * slideStep2 * slideStep2) + 1.0f) * stringWidth);
                    module.setSlideStep(module.getSlideStep() - (i3 / 4.0f));
                }
                module.setSlide(RangesKt.coerceIn(module.getSlide(), 0.0f, stringWidth));
                module.setSlideStep(RangesKt.coerceIn(module.getSlideStep(), 0.0f, stringWidth));
            }
        }
        String str2 = (String) this.colorModeValue.get();
        String str3 = (String) this.rectColorModeValue.get();
        String str4 = (String) this.backgroundColorModeValue.get();
        int rgb3 = new Color(((Number) ClickGUI.colorRedValue.get()).intValue(), ((Number) ClickGUI.colorGreenValue.get()).intValue(), ((Number) ClickGUI.colorBlueValue.get()).intValue(), 1).getRGB();
        int rgb4 = new Color(((Number) this.rectColorRedValue.get()).intValue(), ((Number) this.rectColorGreenValue.get()).intValue(), ((Number) this.rectColorBlueValue.get()).intValue(), ((Number) this.rectColorAlphaValue.get()).intValue()).getRGB();
        float fFloatValue = ((Number) this.spaceValue.get()).floatValue();
        float fFloatValue2 = ((Number) this.textHeightValue.get()).floatValue();
        float fFloatValue3 = ((Number) this.textYValue.get()).floatValue();
        String str5 = (String) this.rectValue.get();
        int rgb5 = new Color(((Number) this.backgroundColorRedValue.get()).intValue(), ((Number) this.backgroundColorGreenValue.get()).intValue(), ((Number) this.backgroundColorBlueValue.get()).intValue(), ((Number) this.backgroundColorAlphaValue.get()).intValue()).getRGB();
        boolean zBooleanValue = ((Boolean) this.shadow.get()).booleanValue();
        float f4 = fFloatValue2 + fFloatValue;
        float fFloatValue4 = ((Number) this.saturationValue.get()).floatValue();
        float fFloatValue5 = ((Number) this.brightnessValue.get()).floatValue();
        float fFloatValue6 = ((Number) this.RianbowbValue.get()).floatValue();
        float fFloatValue7 = ((Number) this.RianbowsValue.get()).floatValue();
        switch (WhenMappings.$EnumSwitchMapping$0[getSide().getHorizontal().ordinal()]) {
            case 1:
            case 2:
                int i4 = 0;
                for (Object obj : this.modules) {
                    int i5 = i4;
                    i4++;
                    if (i5 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Module module2 = (Module) obj;
                    if (!((Boolean) this.tags.get()).booleanValue()) {
                        strTagName2 = module2.fakeName(((Boolean) this.fakeName.get()).booleanValue());
                    } else if (((Boolean) this.tagsArrayColor.get()).booleanValue()) {
                        strTagName2 = module2.colorlessTagName(((Boolean) this.fakeName.get()).booleanValue());
                    } else {
                        strTagName2 = module2.tagName(((Boolean) this.fakeName.get()).booleanValue());
                    }
                    String str6 = strTagName2;
                    if (((Boolean) this.upperCaseValue.get()).booleanValue()) {
                        if (str6 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String upperCase2 = str6.toUpperCase();
                        Intrinsics.checkExpressionValueIsNotNull(upperCase2, "(this as java.lang.String).toUpperCase()");
                        str6 = upperCase2;
                    }
                    float f5 = (-module2.getSlide()) - 2.0f;
                    float f6 = (getSide().getVertical() == Side.Vertical.DOWN ? -f4 : f4) * (getSide().getVertical() == Side.Vertical.DOWN ? i5 + 1 : i5);
                    Color customRainbowColour = Palette.fade2(new Color(rgb3), this.modules.indexOf(module2), iFontRenderer.getFontHeight());
                    Color customRectRainbowColor = Palette.fade2(new Color(rgb3), this.modules.indexOf(module2), iFontRenderer.getFontHeight());
                    Color hSBColor = Color.getHSBColor(module2.getHue(), fFloatValue4, fFloatValue5);
                    Intrinsics.checkExpressionValueIsNotNull(hSBColor, "Color.getHSBColor(module\u2026, saturation, brightness)");
                    int rgb6 = hSBColor.getRGB();
                    if (module2.getState()) {
                        String str7 = (String) this.animationMode.get();
                        switch (str7.hashCode()) {
                            case -1955878649:
                                if (str7.equals("Normal")) {
                                    module2.getTranslate().interpolate2(f5, f6, ((Number) this.animationSpeed.get()).floatValue());
                                    break;
                                }
                                break;
                            case 79973777:
                                if (str7.equals("Slide")) {
                                    module2.getTranslate().interpolate(f5, f6, ((Number) this.animationSpeed.get()).floatValue());
                                    break;
                                }
                                break;
                        }
                    } else {
                        String str8 = (String) this.animationMode.get();
                        switch (str8.hashCode()) {
                            case -1955878649:
                                if (str8.equals("Normal")) {
                                    module2.getTranslate().interpolate2(f5, f6, ((Number) this.animationSpeed.get()).floatValue());
                                    break;
                                }
                                break;
                            case 79973777:
                                if (str8.equals("Slide")) {
                                    module2.getTranslate().interpolate(f5, f6, ((Number) this.animationSpeed.get()).floatValue());
                                    break;
                                }
                                break;
                        }
                    }
                    boolean zEquals4 = StringsKt.equals(str4, "Rainbow", true);
                    if (0.0f + ((14.4f / MinecraftInstance.f157mc.getDebugFPS()) / 100.0f) > 1.0f) {
                    }
                    iArr[0] = iArr[0] + 1;
                    this.counter++;
                    if (zBooleanValue && Intrinsics.areEqual((String) this.shadowTextMode.get(), "New")) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(0.5d, 0.5d, 0.0d);
                        iFontRenderer.drawString(str6, module2.getTranslate().getX() - (StringsKt.equals((String) this.rectValue.get(), "right", true) ? 3 : 0), module2.getTranslate().getY() + fFloatValue3, new Color(0, 0, 0, 180).getRGB(), false);
                        GL11.glTranslated(-0.5d, -0.5d, 0.0d);
                        GL11.glPopMatrix();
                    }
                    RainbowShader.Companion companion = RainbowShader.Companion;
                    float fFloatValue8 = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                    float fFloatValue9 = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                    float fCurrentTimeMillis = (System.currentTimeMillis() % 10000) / 10000.0f;
                    RainbowShader rainbowShader2 = RainbowShader.INSTANCE;
                    if (zEquals4) {
                        rainbowShader2.setStrengthX(fFloatValue8);
                        rainbowShader2.setStrengthY(fFloatValue9);
                        rainbowShader2.setOffset(fCurrentTimeMillis);
                        rainbowShader2.startShader();
                    }
                    RainbowShader rainbowShader3 = rainbowShader2;
                    Throwable th4 = (Throwable) null;
                    try {
                        float x = module2.getTranslate().getX();
                        switch (str5.hashCode()) {
                            case 78959100:
                                if (str5.equals("Right")) {
                                    iIntValue2 = 5;
                                } else {
                                    iIntValue2 = 2;
                                }
                                float f7 = x - iIntValue2;
                                float y = module2.getTranslate().getY();
                                float f8 = !StringsKt.equals(str5, "right", true) ? -3.0f : 0.0f;
                                float y2 = module2.getTranslate().getY() + fFloatValue2;
                                if (zEquals4) {
                                    i2 = StringsKt.equals(str4, "Random", true) ? rgb6 : rgb5;
                                } else {
                                    i2 = -16777216;
                                }
                                RenderUtils.drawRect(f7, y, f8, y2, i2);
                                Unit unit = Unit.INSTANCE;
                                CloseableKt.closeFinally(rainbowShader3, th4);
                                Color colorLiquidSlowly = ColorUtils.LiquidSlowly(System.nanoTime(), i5 * ((Number) this.RianbowspeedValue.get()).intValue(), fFloatValue6, fFloatValue7);
                                numValueOf = colorLiquidSlowly == null ? Integer.valueOf(colorLiquidSlowly.getRGB()) : null;
                                if (numValueOf == null) {
                                    Intrinsics.throwNpe();
                                }
                                Color color2 = new Color(numValueOf.intValue());
                                int rgb7 = new Color(((Number) this.Rianbowr.get()).intValue(), (color2.getGreen() / 2) + ((Number) this.Rianbowb.get()).intValue(), (color2.getGreen() / 2) + ((Number) this.Rianbowb.get()).intValue() + ((Number) this.Rianbowg.get()).intValue()).getRGB();
                                zEquals3 = StringsKt.equals(str2, "Rainbow", true);
                                IntegerValue integerValue = new IntegerValue("RainbowSpeed", 1, 1, 10);
                                float fFloatValue10 = ((Number) this.rainbowX.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                float fFloatValue11 = ((Number) this.rainbowY.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                float fCurrentTimeMillis2 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                if (zEquals3) {
                                    RainbowFontShader.INSTANCE.setStrengthX(fFloatValue10);
                                    RainbowFontShader.INSTANCE.setStrengthY(fFloatValue11);
                                    RainbowFontShader.INSTANCE.setOffset(fCurrentTimeMillis2);
                                    RainbowFontShader.INSTANCE.startShader();
                                }
                                rainbowFontShader2 = RainbowFontShader.INSTANCE;
                                th3 = (Throwable) null;
                                try {
                                    String str9 = str6;
                                    float x2 = module2.getTranslate().getX() - (!StringsKt.equals((String) this.rectValue.get(), "right", true) ? 3 : 0);
                                    float y3 = module2.getTranslate().getY() + fFloatValue3;
                                    if (!zEquals3) {
                                        rgb = 0;
                                    } else if (StringsKt.equals(str2, "Random", true)) {
                                        rgb = rgb6;
                                    } else if (StringsKt.equals(str2, "CustomRainbow", true)) {
                                        Intrinsics.checkExpressionValueIsNotNull(customRainbowColour, "customRainbowColour");
                                        rgb = customRainbowColour.getRGB();
                                    } else if (StringsKt.equals(str2, "OtherRainbow", true)) {
                                        rgb = Colors.getRainbow(-2000, (int) (f6 * 8.0f));
                                    } else if (StringsKt.equals(str2, "VanillaRainbow", true)) {
                                        rgb = ColorUtils.rainbow(700000000 * i5).getRGB();
                                    } else if (StringsKt.equals(str2, "TestRainbow", true)) {
                                        rgb = ColorUtils.rainbowW(400000000 * i5).getRGB();
                                    } else if (StringsKt.equals(str2, "Bainbow", true)) {
                                        rgb = rgb7;
                                    } else if (StringsKt.equals(str2, "Rainbow2", true)) {
                                        rgb = ColorUtils.rainbow3(400000000 * i5, ((Number) this.customRainbowSpeed.get()).floatValue(), 1.0f).getRGB();
                                    } else if (StringsKt.equals(str2, "RedRainbow", true)) {
                                        rgb = ColorUtils.redRainbow(400000000 * i5).getRGB();
                                    } else if (StringsKt.equals(str2, "BlueRainbow", true)) {
                                        rgb = ColorUtils.blueRainbow(400000000 * i5).getRGB();
                                    } else if (StringsKt.equals(str2, "GreenRainbow", true)) {
                                        rgb = ColorUtils.greenRainbow(400000000 * i5).getRGB();
                                    } else if (StringsKt.equals(str2, "Rainbow3", true)) {
                                        rgb = ColorManager.getRainbow2(2000, -((int) (f6 * ((Number) this.rainbow3Offset.get()).intValue())));
                                    } else if (StringsKt.equals(str2, "Astolfo", true)) {
                                        rgb = ColorManager.astolfoRainbow(iArr[0] * 100, ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue());
                                    } else {
                                        rgb = StringsKt.equals(str2, "RiseRainbow", true) ? ColorUtils.hslRainbow$default(i5 + 1, 0.0f, 0.0f, 100 * ((Number) integerValue.get()).intValue(), 0, 0.0f, 0.0f, 118, null).getRGB() : rgb3;
                                    }
                                    iFontRenderer.drawString(str9, x2, y3, rgb, zBooleanValue);
                                    CloseableKt.closeFinally(rainbowFontShader2, th3);
                                    if (StringsKt.equals(str5, "none", true)) {
                                        boolean zEquals5 = StringsKt.equals(str3, "Rainbow", true);
                                        RainbowShader.Companion companion2 = RainbowShader.Companion;
                                        float fFloatValue12 = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                        float fFloatValue13 = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                        float fCurrentTimeMillis3 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                        RainbowShader rainbowShader4 = RainbowShader.INSTANCE;
                                        if (zEquals5) {
                                            rainbowShader4.setStrengthX(fFloatValue12);
                                            rainbowShader4.setStrengthY(fFloatValue13);
                                            rainbowShader4.setOffset(fCurrentTimeMillis3);
                                            rainbowShader4.startShader();
                                        }
                                        RainbowShader rainbowShader5 = rainbowShader4;
                                        Throwable th5 = (Throwable) null;
                                        try {
                                            if (zEquals5) {
                                                rgb2 = 0;
                                            } else if (StringsKt.equals(str3, "Random", true)) {
                                                rgb2 = rgb6;
                                            } else if (StringsKt.equals(str3, "CustomRainbow", true)) {
                                                Intrinsics.checkExpressionValueIsNotNull(customRectRainbowColor, "customRectRainbowColor");
                                                rgb2 = customRectRainbowColor.getRGB();
                                            } else if (StringsKt.equals(str3, "OtherRainbow", true)) {
                                                rgb2 = Colors.getRainbow(-2000, (int) (f6 * 8.0f));
                                            } else if (StringsKt.equals(str3, "Rainbow2", true)) {
                                                rgb2 = ColorUtils.rainbow3(400000000 * i5, ((Number) this.customRainbowSpeed.get()).floatValue(), 1.0f).getRGB();
                                            } else if (StringsKt.equals(str3, "Bainbow", true)) {
                                                rgb2 = rgb7;
                                            } else if (StringsKt.equals(str3, "Rainbow3", true)) {
                                                rgb2 = ColorManager.getRainbow2(2000, -((int) (f6 * ((Number) this.rainbow3Offset.get()).intValue())));
                                            } else if (StringsKt.equals(str3, "Astolfo", true)) {
                                                rgb2 = ColorManager.astolfoRainbow(iArr[0] * 100, ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue());
                                            } else {
                                                rgb2 = StringsKt.equals(str2, "RiseRainbow", true) ? ColorUtils.hslRainbow$default(i5 + 1, 0.0f, 0.0f, 100 * ((Number) integerValue.get()).intValue(), 0, 0.0f, 0.0f, 118, null).getRGB() : rgb4;
                                            }
                                            int i6 = rgb2;
                                            if (StringsKt.equals(str5, "left", true)) {
                                                RenderUtils.drawRect(module2.getTranslate().getX() - (2 + ((Number) this.rectWidth.get()).intValue()), module2.getTranslate().getY(), module2.getTranslate().getX() - 2.0f, module2.getTranslate().getY() + fFloatValue2, i6);
                                            } else if (StringsKt.equals(str5, "right", true)) {
                                                RenderUtils.drawRect(-3.0f, module2.getTranslate().getY(), 0.0f, module2.getTranslate().getY() + fFloatValue2, i6);
                                            } else if (StringsKt.equals(str5, "OutLine", true)) {
                                                RenderUtils.drawRect(module2.getTranslate().getX() - ((Number) this.outLineRectWidth.get()).floatValue(), module2.getTranslate().getY(), (module2.getTranslate().getX() - ((Number) this.outLineRectWidth.get()).floatValue()) + 1.0f, module2.getTranslate().getY() + fFloatValue2, i6);
                                                if (Intrinsics.areEqual(module2, (Module) this.modules.get(0))) {
                                                    Gui.func_73734_a((int) (f5 - ((Number) this.outLineRectWidth.get()).floatValue()), (int) f6, 0, ((int) f6) - 1, i6);
                                                }
                                                if (!Intrinsics.areEqual(module2, (Module) this.modules.get(0))) {
                                                    if (!((Boolean) this.tags.get()).booleanValue()) {
                                                        strTagName3 = ((Module) this.modules.get(i5 - 1)).fakeName(((Boolean) this.fakeName.get()).booleanValue());
                                                    } else if (((Boolean) this.tagsArrayColor.get()).booleanValue()) {
                                                        strTagName3 = ((Module) this.modules.get(i5 - 1)).colorlessTagName(((Boolean) this.fakeName.get()).booleanValue());
                                                    } else {
                                                        strTagName3 = ((Module) this.modules.get(i5 - 1)).tagName(((Boolean) this.fakeName.get()).booleanValue());
                                                    }
                                                    String str10 = strTagName3;
                                                    if (((Boolean) this.upperCaseValue.get()).booleanValue()) {
                                                        if (str10 == null) {
                                                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                                        }
                                                        String upperCase3 = str10.toUpperCase();
                                                        Intrinsics.checkExpressionValueIsNotNull(upperCase3, "(this as java.lang.String).toUpperCase()");
                                                        str10 = upperCase3;
                                                    }
                                                    RenderUtils.drawRect((module2.getTranslate().getX() - ((Number) this.outLineRectWidth.get()).floatValue()) - (iFontRenderer.getStringWidth(str10) - iFontRenderer.getStringWidth(str6)), module2.getTranslate().getY(), (module2.getTranslate().getX() - ((Number) this.outLineRectWidth.get()).floatValue()) + 1.0f, module2.getTranslate().getY() + 2.0f, i6);
                                                    if (Intrinsics.areEqual(module2, (Module) this.modules.get(this.modules.size() - 1))) {
                                                        RenderUtils.drawRect(f5 - ((Number) this.outLineRectWidth.get()).floatValue(), f6 + fFloatValue2, 0.0f, f6 + fFloatValue2 + 1.0f, i6);
                                                    }
                                                }
                                            }
                                            Unit unit2 = Unit.INSTANCE;
                                            CloseableKt.closeFinally(rainbowShader5, th5);
                                        } catch (Throwable th6) {
                                            throw th6;
                                        }
                                    }
                                } catch (Throwable th7) {
                                    throw th7;
                                }
                            case 557454402:
                                if (str5.equals("OutLine")) {
                                    iIntValue2 = ((Number) this.outLineRectWidth.get()).intValue();
                                }
                                float f72 = x - iIntValue2;
                                float y4 = module2.getTranslate().getY();
                                if (!StringsKt.equals(str5, "right", true)) {
                                }
                                float y22 = module2.getTranslate().getY() + fFloatValue2;
                                if (zEquals4) {
                                }
                                RenderUtils.drawRect(f72, y4, f8, y22, i2);
                                Unit unit3 = Unit.INSTANCE;
                                CloseableKt.closeFinally(rainbowShader3, th4);
                                Color colorLiquidSlowly2 = ColorUtils.LiquidSlowly(System.nanoTime(), i5 * ((Number) this.RianbowspeedValue.get()).intValue(), fFloatValue6, fFloatValue7);
                                numValueOf = colorLiquidSlowly2 == null ? Integer.valueOf(colorLiquidSlowly2.getRGB()) : null;
                                if (numValueOf == null) {
                                }
                                Color color22 = new Color(numValueOf.intValue());
                                int rgb72 = new Color(((Number) this.Rianbowr.get()).intValue(), (color22.getGreen() / 2) + ((Number) this.Rianbowb.get()).intValue(), (color22.getGreen() / 2) + ((Number) this.Rianbowb.get()).intValue() + ((Number) this.Rianbowg.get()).intValue()).getRGB();
                                zEquals3 = StringsKt.equals(str2, "Rainbow", true);
                                IntegerValue integerValue2 = new IntegerValue("RainbowSpeed", 1, 1, 10);
                                float fFloatValue102 = ((Number) this.rainbowX.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                float fFloatValue112 = ((Number) this.rainbowY.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                float fCurrentTimeMillis22 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                if (zEquals3) {
                                }
                                rainbowFontShader2 = RainbowFontShader.INSTANCE;
                                th3 = (Throwable) null;
                                String str92 = str6;
                                float x22 = module2.getTranslate().getX() - (!StringsKt.equals((String) this.rectValue.get(), "right", true) ? 3 : 0);
                                float y32 = module2.getTranslate().getY() + fFloatValue3;
                                if (!zEquals3) {
                                }
                                iFontRenderer.drawString(str92, x22, y32, rgb, zBooleanValue);
                                CloseableKt.closeFinally(rainbowFontShader2, th3);
                                if (StringsKt.equals(str5, "none", true)) {
                                }
                                break;
                        }
                    } catch (Throwable th8) {
                        throw th8;
                    }
                }
                break;
            case 3:
                int i7 = 0;
                for (Object obj2 : this.modules) {
                    int i8 = i7;
                    i7++;
                    if (i8 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Module module3 = (Module) obj2;
                    if (!((Boolean) this.tags.get()).booleanValue()) {
                        strTagName = module3.fakeName(((Boolean) this.fakeName.get()).booleanValue());
                    } else if (((Boolean) this.tagsArrayColor.get()).booleanValue()) {
                        strTagName = module3.colorlessTagName(((Boolean) this.fakeName.get()).booleanValue());
                    } else {
                        strTagName = module3.tagName(((Boolean) this.fakeName.get()).booleanValue());
                    }
                    String str11 = strTagName;
                    if (((Boolean) this.upperCaseValue.get()).booleanValue()) {
                        if (str11 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String upperCase4 = str11.toUpperCase();
                        Intrinsics.checkExpressionValueIsNotNull(upperCase4, "(this as java.lang.String).toUpperCase()");
                        str11 = upperCase4;
                    }
                    int stringWidth2 = iFontRenderer.getStringWidth(str11);
                    float f9 = (-(stringWidth2 - module3.getSlide())) + (StringsKt.equals(str5, "left", true) ? 5 : 2);
                    float f10 = (getSide().getVertical() == Side.Vertical.DOWN ? -f4 : f4) * (getSide().getVertical() == Side.Vertical.DOWN ? i8 + 1 : i8);
                    Color hSBColor2 = Color.getHSBColor(module3.getHue(), fFloatValue4, fFloatValue5);
                    Intrinsics.checkExpressionValueIsNotNull(hSBColor2, "Color.getHSBColor(module\u2026, saturation, brightness)");
                    int rgb8 = hSBColor2.getRGB();
                    boolean zEquals6 = StringsKt.equals(str4, "Rainbow", true);
                    Color customRainbowColour2 = Palette.fade2(new Color(rgb3), this.modules.indexOf(module3), iFontRenderer.getFontHeight());
                    Color customRectRainbowColor2 = Palette.fade2(new Color(rgb3), this.modules.indexOf(module3), iFontRenderer.getFontHeight());
                    Color colorLiquidSlowly3 = ColorUtils.LiquidSlowly(System.nanoTime(), i8 * ((Number) this.RianbowspeedValue.get()).intValue(), fFloatValue6, fFloatValue7);
                    Integer numValueOf2 = colorLiquidSlowly3 != null ? Integer.valueOf(colorLiquidSlowly3.getRGB()) : null;
                    if (numValueOf2 == null) {
                        Intrinsics.throwNpe();
                    }
                    Color color3 = new Color(numValueOf2.intValue());
                    int rgb9 = new Color(((Number) this.Rianbowr.get()).intValue(), (color3.getGreen() / 2) + ((Number) this.Rianbowb.get()).intValue(), (color3.getGreen() / 2) + ((Number) this.Rianbowb.get()).intValue() + ((Number) this.Rianbowg.get()).intValue()).getRGB();
                    RainbowShader.Companion companion3 = RainbowShader.Companion;
                    float fFloatValue14 = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                    float fFloatValue15 = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                    float fCurrentTimeMillis4 = (System.currentTimeMillis() % 10000) / 10000.0f;
                    RainbowShader rainbowShader6 = RainbowShader.INSTANCE;
                    if (zEquals6) {
                        rainbowShader6.setStrengthX(fFloatValue14);
                        rainbowShader6.setStrengthY(fFloatValue15);
                        rainbowShader6.setOffset(fCurrentTimeMillis4);
                        rainbowShader6.startShader();
                    }
                    RainbowShader rainbowShader7 = rainbowShader6;
                    Throwable th9 = (Throwable) null;
                    try {
                        float f11 = f9 + stringWidth2;
                        switch (str5.hashCode()) {
                            case 78959100:
                                if (str5.equals("Right")) {
                                    iIntValue = 5;
                                } else {
                                    iIntValue = 2;
                                }
                                float f12 = f11 + iIntValue;
                                float f13 = f10 + fFloatValue2;
                                if (zEquals6) {
                                    i = StringsKt.equals(str4, "Random", true) ? rgb8 : rgb5;
                                } else {
                                    i = 0;
                                }
                                RenderUtils.drawRect(0.0f, f10, f12, f13, i);
                                Unit unit4 = Unit.INSTANCE;
                                CloseableKt.closeFinally(rainbowShader7, th9);
                                zEquals = StringsKt.equals(str2, "Rainbow", true);
                                float fFloatValue16 = ((Number) this.rainbowX.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                float fFloatValue17 = ((Number) this.rainbowY.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                float fCurrentTimeMillis5 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                if (zEquals) {
                                    RainbowFontShader.INSTANCE.setStrengthX(fFloatValue16);
                                    RainbowFontShader.INSTANCE.setStrengthY(fFloatValue17);
                                    RainbowFontShader.INSTANCE.setOffset(fCurrentTimeMillis5);
                                    RainbowFontShader.INSTANCE.startShader();
                                }
                                rainbowFontShader = RainbowFontShader.INSTANCE;
                                th = (Throwable) null;
                                try {
                                    String str12 = str11;
                                    float f14 = f10 + fFloatValue3;
                                    if (!zEquals) {
                                        iAstolfoRainbow = 0;
                                    } else if (StringsKt.equals(str2, "Random", true)) {
                                        iAstolfoRainbow = rgb8;
                                    } else if (StringsKt.equals(str2, "CustomRainbow", true)) {
                                        Intrinsics.checkExpressionValueIsNotNull(customRainbowColour2, "customRainbowColour");
                                        iAstolfoRainbow = customRainbowColour2.getRGB();
                                    } else if (StringsKt.equals(str2, "OtherRainbow", true)) {
                                        Color colorFade = Palette.fade(new Color(((Number) ClickGUI.colorRedValue.get()).intValue(), ((Number) ClickGUI.colorGreenValue.get()).intValue(), ((Number) ClickGUI.colorBlueValue.get()).intValue()), 100, (CollectionsKt.indexOf(LiquidBounce.INSTANCE.getModuleManager().getModules(), module3) * 2) + 10, 2.0f);
                                        Intrinsics.checkExpressionValueIsNotNull(colorFade, "Palette.fade(Color(Click\u2026xOf(module) * 2 + 10, 2F)");
                                        iAstolfoRainbow = colorFade.getRGB();
                                    } else if (StringsKt.equals(str2, "Bainbow", true)) {
                                        iAstolfoRainbow = rgb9;
                                    } else if (StringsKt.equals(str2, "Rainbow2", true)) {
                                        iAstolfoRainbow = ColorUtils.rainbow3(400000000 * i8, ((Number) this.customRainbowSpeed.get()).floatValue(), 1.0f).getRGB();
                                    } else if (StringsKt.equals(str2, "Rainbow3", true)) {
                                        iAstolfoRainbow = ColorManager.getRainbow2(2000, -((int) (f10 * ((Number) this.rainbow3Offset.get()).intValue())));
                                    } else {
                                        iAstolfoRainbow = StringsKt.equals(str2, "Astolfo", true) ? ColorManager.astolfoRainbow(iArr[0] * 100, ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue()) : rgb3;
                                    }
                                    iFontRenderer.drawString(str12, f9, f14, iAstolfoRainbow, zBooleanValue);
                                    CloseableKt.closeFinally(rainbowFontShader, th);
                                    zEquals2 = StringsKt.equals(str3, "Rainbow", true);
                                    RainbowShader.Companion companion4 = RainbowShader.Companion;
                                    float fFloatValue18 = ((Number) this.rainbowX.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                    float fFloatValue19 = ((Number) this.rainbowY.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                    float fCurrentTimeMillis6 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                    RainbowShader rainbowShader8 = RainbowShader.INSTANCE;
                                    if (zEquals2) {
                                        rainbowShader8.setStrengthX(fFloatValue18);
                                        rainbowShader8.setStrengthY(fFloatValue19);
                                        rainbowShader8.setOffset(fCurrentTimeMillis6);
                                        rainbowShader8.startShader();
                                    }
                                    rainbowShader = rainbowShader8;
                                    th2 = (Throwable) null;
                                    try {
                                        if (!StringsKt.equals(str5, "none", true)) {
                                            if (zEquals2) {
                                                iAstolfoRainbow2 = 0;
                                            } else if (StringsKt.equals(str3, "Random", true)) {
                                                iAstolfoRainbow2 = rgb8;
                                            } else if (StringsKt.equals(str3, "CustomRainbow", true)) {
                                                Intrinsics.checkExpressionValueIsNotNull(customRectRainbowColor2, "customRectRainbowColor");
                                                iAstolfoRainbow2 = customRectRainbowColor2.getRGB();
                                            } else if (StringsKt.equals(str3, "OtherRainbow", true)) {
                                                Color colorFade2 = Palette.fade(new Color(((Number) this.rectColorRedValue.get()).intValue(), ((Number) this.rectColorGreenValue.get()).intValue(), ((Number) this.rectColorBlueValue.get()).intValue(), ((Number) this.rectColorAlphaValue.get()).intValue()), 100, (CollectionsKt.indexOf(LiquidBounce.INSTANCE.getModuleManager().getModules(), module3) * 2) + 10, 2.0f);
                                                Intrinsics.checkExpressionValueIsNotNull(colorFade2, "Palette.fade(Color(rectC\u2026xOf(module) * 2 + 10, 2F)");
                                                iAstolfoRainbow2 = colorFade2.getRGB();
                                            } else if (StringsKt.equals(str3, "Bainbow", true)) {
                                                iAstolfoRainbow2 = rgb9;
                                            } else if (StringsKt.equals(str3, "Rainbow2", true)) {
                                                iAstolfoRainbow2 = ColorUtils.rainbow3(400000000 * i8, ((Number) this.customRainbowSpeed.get()).floatValue(), 1.0f).getRGB();
                                            } else if (StringsKt.equals(str3, "Rainbow3", true)) {
                                                iAstolfoRainbow2 = ColorManager.getRainbow2(2000, -((int) (f10 * ((Number) this.rainbow3Offset.get()).intValue())));
                                            } else {
                                                iAstolfoRainbow2 = StringsKt.equals(str3, "Astolfo", true) ? ColorManager.astolfoRainbow(iArr[0] * 100, ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue()) : rgb4;
                                            }
                                            int i9 = iAstolfoRainbow2;
                                            if (StringsKt.equals(str5, "left", true)) {
                                                RenderUtils.drawRect(0.0f, f10 - 1.0f, 3.0f, f10 + fFloatValue2, i9);
                                            } else if (StringsKt.equals(str5, "right", true)) {
                                                RenderUtils.drawRect(f9 + stringWidth2 + 2.0f, f10, f9 + stringWidth2 + 2.0f + 3.0f, f10 + fFloatValue2, i9);
                                            }
                                        }
                                        Unit unit5 = Unit.INSTANCE;
                                        CloseableKt.closeFinally(rainbowShader, th2);
                                    } catch (Throwable th10) {
                                        throw th10;
                                    }
                                } catch (Throwable th11) {
                                    throw th11;
                                }
                            case 557454402:
                                if (str5.equals("OutLine")) {
                                    iIntValue = ((Number) this.outLineRectWidth.get()).intValue() - stringWidth2;
                                }
                                float f122 = f11 + iIntValue;
                                float f132 = f10 + fFloatValue2;
                                if (zEquals6) {
                                }
                                RenderUtils.drawRect(0.0f, f10, f122, f132, i);
                                Unit unit42 = Unit.INSTANCE;
                                CloseableKt.closeFinally(rainbowShader7, th9);
                                zEquals = StringsKt.equals(str2, "Rainbow", true);
                                float fFloatValue162 = ((Number) this.rainbowX.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                float fFloatValue172 = ((Number) this.rainbowY.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                float fCurrentTimeMillis52 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                if (zEquals) {
                                }
                                rainbowFontShader = RainbowFontShader.INSTANCE;
                                th = (Throwable) null;
                                String str122 = str11;
                                float f142 = f10 + fFloatValue3;
                                if (!zEquals) {
                                }
                                iFontRenderer.drawString(str122, f9, f142, iAstolfoRainbow, zBooleanValue);
                                CloseableKt.closeFinally(rainbowFontShader, th);
                                zEquals2 = StringsKt.equals(str3, "Rainbow", true);
                                RainbowShader.Companion companion42 = RainbowShader.Companion;
                                float fFloatValue182 = ((Number) this.rainbowX.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
                                float fFloatValue192 = ((Number) this.rainbowY.get()).floatValue() != 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
                                float fCurrentTimeMillis62 = (System.currentTimeMillis() % 10000) / 10000.0f;
                                RainbowShader rainbowShader82 = RainbowShader.INSTANCE;
                                if (zEquals2) {
                                }
                                rainbowShader = rainbowShader82;
                                th2 = (Throwable) null;
                                if (!StringsKt.equals(str5, "none", true)) {
                                }
                                Unit unit52 = Unit.INSTANCE;
                                CloseableKt.closeFinally(rainbowShader, th2);
                                break;
                        }
                    } catch (Throwable th12) {
                        throw th12;
                    }
                }
                break;
        }
        if (MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
            this.f148x2 = Integer.MIN_VALUE;
            if (this.modules.isEmpty()) {
                if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                    return new Border(0.0f, -1.0f, 20.0f, 20.0f);
                }
                return new Border(0.0f, -1.0f, -20.0f, 20.0f);
            }
            for (Module module4 : this.modules) {
                switch (WhenMappings.$EnumSwitchMapping$1[getSide().getHorizontal().ordinal()]) {
                    case 1:
                    case 2:
                        int i10 = (-((int) module4.getSlide())) - 2;
                        if (this.f148x2 == Integer.MIN_VALUE || i10 < this.f148x2) {
                            this.f148x2 = i10;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        int slide = ((int) module4.getSlide()) + 14;
                        if (this.f148x2 == Integer.MIN_VALUE || slide > this.f148x2) {
                            this.f148x2 = slide;
                            break;
                        } else {
                            break;
                        }
                        break;
                }
            }
            this.f149y2 = (getSide().getVertical() == Side.Vertical.DOWN ? -f4 : f4) * this.modules.size();
            return new Border(0.0f, 0.0f, this.f148x2 - 7.0f, this.f149y2 - (getSide().getVertical() == Side.Vertical.DOWN ? 1.0f : 0.0f));
        }
        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        GlStateManager.func_179117_G();
        return null;
    }

    public void updateElement() {
        TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
        ArrayList arrayList = new ArrayList();
        for (Object obj : modules) {
            Module module = (Module) obj;
            if (module.getArray() && module.getSlide() > 0.0f) {
                arrayList.add(obj);
            }
        }
        this.modules = CollectionsKt.sortedWith(arrayList, new Comparator(this) { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist$updateElement$$inlined$sortedBy$1
            final Arraylist this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                String strFakeName;
                String strFakeName2;
                Module module2 = (Module) obj2;
                IFontRenderer iFontRenderer = (IFontRenderer) this.this$0.fontValue.get();
                if (((Boolean) this.this$0.upperCaseValue.get()).booleanValue()) {
                    String strFakeName3 = !((Boolean) this.this$0.tags.get()).booleanValue() ? module2.fakeName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : ((Boolean) this.this$0.tagsArrayColor.get()).booleanValue() ? module2.colorlessTagName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : module2.tagName(((Boolean) this.this$0.fakeName.get()).booleanValue());
                    if (strFakeName3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String upperCase = strFakeName3.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
                    iFontRenderer = iFontRenderer;
                    strFakeName = upperCase;
                } else {
                    strFakeName = !((Boolean) this.this$0.tags.get()).booleanValue() ? module2.fakeName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : ((Boolean) this.this$0.tagsArrayColor.get()).booleanValue() ? module2.colorlessTagName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : module2.tagName(((Boolean) this.this$0.fakeName.get()).booleanValue());
                }
                Integer numValueOf = Integer.valueOf(-iFontRenderer.getStringWidth(strFakeName));
                Module module3 = (Module) obj3;
                IFontRenderer iFontRenderer2 = (IFontRenderer) this.this$0.fontValue.get();
                if (((Boolean) this.this$0.upperCaseValue.get()).booleanValue()) {
                    String strFakeName4 = !((Boolean) this.this$0.tags.get()).booleanValue() ? module3.fakeName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : ((Boolean) this.this$0.tagsArrayColor.get()).booleanValue() ? module3.colorlessTagName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : module3.tagName(((Boolean) this.this$0.fakeName.get()).booleanValue());
                    if (strFakeName4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String upperCase2 = strFakeName4.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(upperCase2, "(this as java.lang.String).toUpperCase()");
                    iFontRenderer2 = iFontRenderer2;
                    strFakeName2 = upperCase2;
                } else {
                    strFakeName2 = !((Boolean) this.this$0.tags.get()).booleanValue() ? module3.fakeName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : ((Boolean) this.this$0.tagsArrayColor.get()).booleanValue() ? module3.colorlessTagName(((Boolean) this.this$0.fakeName.get()).booleanValue()) : module3.tagName(((Boolean) this.this$0.fakeName.get()).booleanValue());
                }
                return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(-iFontRenderer2.getStringWidth(strFakeName2)));
            }
        });
    }
}

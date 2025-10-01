package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.ColorManager;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;

@ElementInfo(name = "Text2")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\ufffd\ufffd G2\u00020\u0001:\u0001GB-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u00105\u001a\u000206H\u0016J(\u00107\u001a\u0002082\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00062\u0006\u00109\u001a\u00020\u00062\u0006\u0010:\u001a\u00020\u0006H\u0002J\u0012\u0010;\u001a\u0004\u0018\u00010\u00182\u0006\u0010<\u001a\u00020\u0018H\u0002J\u0018\u0010=\u001a\u0002082\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u0016H\u0016J \u0010A\u001a\u0002082\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010B\u001a\u00020\u0016H\u0016J\u0010\u0010C\u001a\u00020\u00182\u0006\u0010<\u001a\u00020\u0018H\u0002J\u000e\u0010D\u001a\u00020\ufffd\ufffd2\u0006\u0010>\u001a\u00020EJ\b\u0010F\u001a\u000208H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0017\u001a\u00020\u00188BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020#X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010'\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010)\u001a\u00020*X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010+\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010,\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010-\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010.\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010/\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00100\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00101\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00102\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00103\u001a\u000204X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006H"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "animation", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "animationDelay", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "backgroundValue", "bgalphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "bgblueValue", "bggreenValue", "bgredValue", "blueValue", "count", "", "display", "", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "editMode", "", "editTicks", "fadeDistanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gradientAmountValue", "greenValue", "lineValue", "prevClick", "", "rainbow", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rainbowSaturation", "rainbowSpeed", "rainbowX", "rainbowY", "redValue", "reverse", "shadow", "skeetRectValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawExhiRect", "", "x2", "y2", "getReplacement", AsmConstants.STR, "handleKey", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "Ljava/awt/Color;", "updateElement", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2.class */
public final class Text2 extends Element {
    private final TextValue displayString;
    private final BoolValue lineValue;
    private final IntegerValue gradientAmountValue;
    private final BoolValue skeetRectValue;
    private final BoolValue backgroundValue;
    private final IntegerValue bgredValue;
    private final IntegerValue bggreenValue;
    private final IntegerValue bgblueValue;
    private final IntegerValue bgalphaValue;
    private final BoolValue animation;
    private final FloatValue animationDelay;
    private final ListValue rainbow;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue rainbowSpeed;
    private final FloatValue rainbowSaturation;
    private final IntegerValue fadeDistanceValue;
    private final FloatValue rainbowX;
    private final FloatValue rainbowY;
    private final BoolValue shadow;
    private FontValue fontValue;
    private boolean editMode;
    private int editTicks;
    private long prevClick;
    private String displayText;
    private int count;
    private boolean reverse;
    private MSTimer timer;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @NotNull
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");

    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public Text2() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Text2(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.displayString = new TextValue("DisplayText", "");
        this.lineValue = new BoolValue("Line", true);
        this.gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);
        this.skeetRectValue = new BoolValue("SkeetRect", false);
        this.backgroundValue = new BoolValue("Background", true);
        this.bgredValue = new IntegerValue("Background-Red", 0, 0, 255);
        this.bggreenValue = new IntegerValue("Background-Green", 0, 0, 255);
        this.bgblueValue = new IntegerValue("Background-Blue", 0, 0, 255);
        this.bgalphaValue = new IntegerValue("Background-Alpha", 120, 0, 255);
        this.animation = new BoolValue("Animation", false);
        this.animationDelay = new FloatValue("AnimationDelay", 1.0f, 0.01f, 5.0f);
        this.rainbow = new ListValue("Rainbow", new String[]{"Normal", "Fade", "Astolfo", "Other", "None"}, "Fade");
        this.redValue = new IntegerValue("Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Blue", 255, 0, 255);
        this.rainbowSpeed = new IntegerValue("RainbowSpeed", 10, 1, 10);
        this.rainbowSaturation = new FloatValue("RainbowSaturation", 0.5f, 0.01f, 1.0f);
        this.fadeDistanceValue = new IntegerValue("FadeDistance", 50, 1, 100);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0f, -2000.0f, 2000.0f);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0f, -2000.0f, 2000.0f);
        this.shadow = new BoolValue("Shadow", true);
        IFontRenderer iFontRenderer = Fonts.font35;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font35");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.displayText = getDisplay();
        this.timer = new MSTimer();
    }

    public Text2(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 10.0d : d, (i & 2) != 0 ? 10.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? Side.Companion.m1697default() : side);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\u000eR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "HOUR_FORMAT", "getHOUR_FORMAT", "defaultClient", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final SimpleDateFormat getDATE_FORMAT() {
            return Text2.DATE_FORMAT;
        }

        @NotNull
        public final SimpleDateFormat getHOUR_FORMAT() {
            return Text2.HOUR_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT() {
            return Text2.DECIMAL_FORMAT;
        }

        @NotNull
        public final Text2 defaultClient() {
            Text2 text2 = new Text2(5.0d, 5.0d, 1.0f, null, 8, null);
            text2.displayString.set("Destiny");
            text2.shadow.set(true);
            FontValue fontValue = text2.fontValue;
            IFontRenderer iFontRenderer = Fonts.minecraftFont;
            Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.minecraftFont");
            fontValue.set(iFontRenderer);
            text2.setColor(new Color(255, 255, 255));
            return text2;
        }
    }

    private final String getDisplay() {
        String str;
        if ((((CharSequence) this.displayString.get()).length() == 0) && !this.editMode) {
            str = "Text Element";
        } else {
            str = (String) this.displayString.get();
        }
        return multiReplace(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x045e, code lost:
    
        if (r6.equals("cps") != false) goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0478, code lost:
    
        if (r6.equals("lcps") != false) goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0521, code lost:
    
        return java.lang.String.valueOf(net.ccbluex.liquidbounce.utils.CPSCounter.getCPS(net.ccbluex.liquidbounce.utils.CPSCounter.MouseButton.LEFT));
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0536 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String getReplacement(String str) {
        if (MinecraftInstance.f157mc.getThePlayer() != null) {
            switch (str.hashCode()) {
                case 48:
                    if (str.equals("0")) {
                        return "\u00a70";
                    }
                    break;
                case OPCode.MEMORY_START_PUSH /* 49 */:
                    if (str.equals("1")) {
                        return "\u00a71";
                    }
                    break;
                case OPCode.MEMORY_END_PUSH /* 50 */:
                    if (str.equals("2")) {
                        return "\u00a72";
                    }
                    break;
                case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                    if (str.equals("3")) {
                        return "\u00a73";
                    }
                    break;
                case OPCode.MEMORY_END /* 52 */:
                    if (str.equals("4")) {
                        return "\u00a74";
                    }
                    break;
                case OPCode.MEMORY_END_REC /* 53 */:
                    if (str.equals("5")) {
                        return "\u00a75";
                    }
                    break;
                case OPCode.FAIL /* 54 */:
                    if (str.equals("6")) {
                        return "\u00a76";
                    }
                    break;
                case OPCode.JUMP /* 55 */:
                    if (str.equals("7")) {
                        return "\u00a77";
                    }
                    break;
                case 56:
                    if (str.equals("8")) {
                        return "\u00a78";
                    }
                    break;
                case OPCode.POP /* 57 */:
                    if (str.equals("9")) {
                        return "\u00a79";
                    }
                    break;
                case 97:
                    if (str.equals("a")) {
                        return "\u00a7a";
                    }
                    break;
                case 98:
                    if (str.equals("b")) {
                        return "\u00a7b";
                    }
                    break;
                case 99:
                    if (str.equals("c")) {
                        return "\u00a7c";
                    }
                    break;
                case Shell.COMMANDLINE_ERROR /* 100 */:
                    if (str.equals("d")) {
                        return "\u00a7d";
                    }
                    break;
                case Shell.COMPILATION_ERROR /* 101 */:
                    if (str.equals("e")) {
                        return "\u00a7e";
                    }
                    break;
                case Shell.RUNTIME_ERROR /* 102 */:
                    if (str.equals("f")) {
                        return "\u00a7f";
                    }
                    break;
                case 107:
                    if (str.equals("k")) {
                        return "\u00a7k";
                    }
                    break;
                case 108:
                    if (str.equals("l")) {
                        return "\u00a7l";
                    }
                    break;
                case 109:
                    if (str.equals("m")) {
                        return "\u00a7m";
                    }
                    break;
                case 110:
                    if (str.equals("n")) {
                        return "\u00a7n";
                    }
                    break;
                case 111:
                    if (str.equals("o")) {
                        return "\u00a7o";
                    }
                    break;
                case 114:
                    if (str.equals("r")) {
                        return "\u00a7r";
                    }
                    break;
                case 120:
                    if (str.equals("x")) {
                        DecimalFormat decimalFormat = DECIMAL_FORMAT;
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        return decimalFormat.format(thePlayer.getPosX());
                    }
                    break;
                case 121:
                    if (str.equals("y")) {
                        DecimalFormat decimalFormat2 = DECIMAL_FORMAT;
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        return decimalFormat2.format(thePlayer2.getPosY());
                    }
                    break;
                case 122:
                    if (str.equals("z")) {
                        DecimalFormat decimalFormat3 = DECIMAL_FORMAT;
                        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer3 == null) {
                            Intrinsics.throwNpe();
                        }
                        return decimalFormat3.format(thePlayer3.getPosZ());
                    }
                    break;
                case 118532:
                    if (str.equals("xdp")) {
                        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer4 == null) {
                            Intrinsics.throwNpe();
                        }
                        return String.valueOf(thePlayer4.getPosX());
                    }
                    break;
                case 119493:
                    if (str.equals("ydp")) {
                        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer5 == null) {
                            Intrinsics.throwNpe();
                        }
                        return String.valueOf(thePlayer5.getPosY());
                    }
                    break;
                case 120454:
                    if (str.equals("zdp")) {
                        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer6 == null) {
                            Intrinsics.throwNpe();
                        }
                        return String.valueOf(thePlayer6.getPosZ());
                    }
                    break;
                case 3441010:
                    if (str.equals("ping")) {
                        return String.valueOf(EntityUtils.getPing(MinecraftInstance.f157mc.getThePlayer()));
                    }
                    break;
                case 109641799:
                    if (str.equals("speed")) {
                        DecimalFormat decimalFormat4 = DECIMAL_FORMAT;
                        MovementUtils movementUtils = MovementUtils.INSTANCE;
                        IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer7 == null) {
                            Intrinsics.throwNpe();
                        }
                        return decimalFormat4.format(movementUtils.getBlockSpeed(thePlayer7));
                    }
                    break;
                case 110364485:
                    if (str.equals("timer")) {
                        return DECIMAL_FORMAT.format(Float.valueOf(MinecraftInstance.f157mc.getTimer().getTimerSpeed()));
                    }
                    break;
            }
        }
        switch (str.hashCode()) {
            case -265713450:
                if (str.equals("username")) {
                    return MinecraftInstance.f157mc.getSession().getUsername();
                }
                return null;
            case 98726:
                break;
            case 101609:
                if (str.equals("fps")) {
                    return String.valueOf(Minecraft.func_175610_ah());
                }
                return null;
            case 3076014:
                if (str.equals("date")) {
                    return DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                }
                return null;
            case 3316154:
                break;
            case 3345945:
                if (str.equals("mcps")) {
                    return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.MIDDLE));
                }
                return null;
            case 3494900:
                if (str.equals("rcps")) {
                    return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT));
                }
                return null;
            case 3560141:
                if (str.equals("time")) {
                    return HOUR_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                }
                return null;
            case 771880589:
                if (str.equals("clientVersion")) {
                    return "b1";
                }
                return null;
            case 1102251254:
                if (str.equals("clientName")) {
                    return LiquidBounce.CLIENT_NAME;
                }
                break;
            case 1379103690:
                if (str.equals("serverIp")) {
                    return ServerUtils.getRemoteIp();
                }
                return null;
            case 1448827361:
                if (str.equals("clientCreator")) {
                    return LiquidBounce.CLIENT_CREATOR;
                }
                break;
        }
    }

    private final String multiReplace(String str) {
        int i = -1;
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) == '%') {
                if (i != -1) {
                    if (i + 1 != i2) {
                        int i3 = i + 1;
                        if (str == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String strSubstring = str.substring(i3, i2);
                        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        String replacement = getReplacement(strSubstring);
                        if (replacement != null) {
                            sb.append(replacement);
                            i = -1;
                        }
                    }
                    sb.append((CharSequence) str, i, i2);
                    i = i2;
                } else {
                    i = i2;
                }
            } else if (i == -1) {
                sb.append(str.charAt(i2));
            }
        }
        if (i != -1) {
            sb.append((CharSequence) str, i, str.length());
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "result.toString()");
        return string;
    }

    private final void drawExhiRect(float f, float f2, float f3, float f4) {
        RenderUtils.drawRect(f - 1.5f, f2 - 1.5f, f3 + 1.5f, f4 + 1.5f, new Color(8, 8, 8).getRGB());
        RenderUtils.drawRect(f - 1.0f, f2 - 1.0f, f3 + 1.0f, f4 + 1.0f, new Color(49, 49, 49).getRGB());
        RenderUtils.drawBorderedRect(f + 2.0f, f2 + 2.0f, f3 - 2.0f, f4 - 2.0f, 0.5f, new Color(18, 18, 18).getRGB(), new Color(28, 28, 28).getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        int[] iArr = {0};
        int rgb = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()).getRGB();
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        int rgb2 = rgb + new Color(0, 0, 0, 50).getRGB();
        String str = (String) this.rainbow.get();
        int stringWidth = 0;
        if (((Boolean) this.backgroundValue.get()).booleanValue()) {
            RenderUtils.drawRect(-2.0f, -2.0f, iFontRenderer.getStringWidth(this.displayText) + 2.0f, iFontRenderer.getFontHeight() + 3.0f, new Color(((Number) this.bgredValue.get()).intValue(), ((Number) this.bggreenValue.get()).intValue(), ((Number) this.bgblueValue.get()).intValue(), ((Number) this.bgalphaValue.get()).intValue()));
        }
        if (((Boolean) this.lineValue.get()).booleanValue()) {
            double stringWidth2 = iFontRenderer.getStringWidth(this.displayText) + 4.0f;
            int i = 0;
            int iIntValue = ((Number) this.gradientAmountValue.get()).intValue() - 1;
            if (0 <= iIntValue) {
                while (true) {
                    RenderUtils.drawGradientSideways((-2.0d) + ((i / ((Number) this.gradientAmountValue.get()).intValue()) * stringWidth2), -3.0d, (-2.0d) + (((i + 1) / ((Number) this.gradientAmountValue.get()).intValue()) * stringWidth2), -2.0d, new Color(((Number) ClickGUI.colorRedValue.get()).intValue(), ((Number) ClickGUI.colorGreenValue.get()).intValue(), ((Number) ClickGUI.colorBlueValue.get()).intValue()).getRGB(), new Color(((Number) ClickGUI.colorRedValue.get()).intValue(), ((Number) ClickGUI.colorGreenValue.get()).intValue(), ((Number) ClickGUI.colorBlueValue.get()).intValue()).getRGB());
                    if (i == iIntValue) {
                        break;
                    }
                    i++;
                }
            }
        }
        if (((Boolean) this.skeetRectValue.get()).booleanValue()) {
            drawExhiRect(-4.0f, ((Boolean) this.lineValue.get()).booleanValue() ? -5.0f : -4.0f, iFontRenderer.getStringWidth(this.displayText) + 4.0f, iFontRenderer.getFontHeight() + 2.0f);
        }
        boolean zAreEqual = Intrinsics.areEqual(str, "Normal");
        float fFloatValue = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
        float fFloatValue2 = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
        float fCurrentTimeMillis = (System.currentTimeMillis() % 10000) / 10000.0f;
        if (zAreEqual) {
            RainbowFontShader.INSTANCE.setStrengthX(fFloatValue);
            RainbowFontShader.INSTANCE.setStrengthY(fFloatValue2);
            RainbowFontShader.INSTANCE.setOffset(fCurrentTimeMillis);
            RainbowFontShader.INSTANCE.startShader();
        }
        RainbowFontShader rainbowFontShader = RainbowFontShader.INSTANCE;
        Throwable th = (Throwable) null;
        try {
            if (Intrinsics.areEqual(str, "Fade")) {
                String str2 = this.displayText;
                if (str2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                char[] charArray = str2.toCharArray();
                Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
                for (char c : charArray) {
                    Color colorFade1 = Palette.fade1(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), iArr[0] * ((Number) this.fadeDistanceValue.get()).intValue(), this.displayText.length() * 130);
                    Intrinsics.checkExpressionValueIsNotNull(colorFade1, "Palette.fade1(Color(redV\u2026displayText.length * 130)");
                    iFontRenderer.drawString(String.valueOf(c), 0.0f + stringWidth, 0.0f, colorFade1.getRGB(), ((Boolean) this.shadow.get()).booleanValue());
                    stringWidth += iFontRenderer.getStringWidth(String.valueOf(c));
                    iArr[0] = iArr[0] + 1;
                    iArr[0] = RangesKt.coerceIn(iArr[0], 0, this.displayText.length());
                }
                Unit unit = Unit.INSTANCE;
            } else if (Intrinsics.areEqual(str, "Other")) {
                String str3 = this.displayText;
                if (str3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                char[] charArray2 = str3.toCharArray();
                Intrinsics.checkExpressionValueIsNotNull(charArray2, "(this as java.lang.String).toCharArray()");
                for (char c2 : charArray2) {
                    iFontRenderer.drawString(String.valueOf(c2), 0.0f + stringWidth, 0.0f, ColorManager.fluxRainbow(-100, iArr[0] * (-50) * ((Number) this.rainbowSpeed.get()).intValue(), ((Number) this.rainbowSaturation.get()).floatValue()), ((Boolean) this.shadow.get()).booleanValue());
                    stringWidth += iFontRenderer.getStringWidth(String.valueOf(c2));
                    iArr[0] = iArr[0] + 1;
                    iArr[0] = RangesKt.coerceIn(iArr[0], 0, this.displayText.length());
                }
                Unit unit2 = Unit.INSTANCE;
            } else if (Intrinsics.areEqual(str, "Astolfo")) {
                String str4 = this.displayText;
                if (str4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                char[] charArray3 = str4.toCharArray();
                Intrinsics.checkExpressionValueIsNotNull(charArray3, "(this as java.lang.String).toCharArray()");
                for (char c3 : charArray3) {
                    iFontRenderer.drawString(String.valueOf(c3), 0.0f + stringWidth, 0.0f, ColorManager.Astolfo(iArr[0] * 100), ((Boolean) this.shadow.get()).booleanValue());
                    stringWidth += iFontRenderer.getStringWidth(String.valueOf(c3));
                    iArr[0] = iArr[0] + 1;
                    iArr[0] = RangesKt.coerceIn(iArr[0], 0, this.displayText.length());
                }
                Unit unit3 = Unit.INSTANCE;
            } else {
                Integer.valueOf(iFontRenderer.drawString(this.displayText, 0.0f, 0.0f, Intrinsics.areEqual(str, "None") ^ true ? 0 : rgb, ((Boolean) this.shadow.get()).booleanValue()));
            }
            CloseableKt.closeFinally(rainbowFontShader, th);
            if (this.editMode && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
                this.editMode = false;
                updateElement();
            }
            return new Border(-2.0f, -2.0f, iFontRenderer.getStringWidth(this.displayText) + 2.0f, iFontRenderer.getFontHeight());
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public void updateElement() {
        String display;
        this.editTicks += 5;
        if (this.editTicks > 80) {
            this.editTicks = 0;
        }
        if (this.count < 0 || this.count > getDisplay().length()) {
            this.count = 0;
            this.reverse = false;
        }
        if (!this.editMode && ((Boolean) this.animation.get()).booleanValue() && this.timer.hasTimePassed((long) (((Number) this.animationDelay.get()).floatValue() * 1000.0f))) {
            if (this.reverse) {
                this.count--;
            } else {
                this.count++;
            }
            if (this.count == getDisplay().length() || this.count == 0) {
                this.reverse = !this.reverse;
            }
            this.timer.reset();
        }
        if (((Boolean) this.animation.get()).booleanValue()) {
            String display2 = getDisplay();
            int i = this.count;
            if (display2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            display = display2.substring(0, i);
            Intrinsics.checkExpressionValueIsNotNull(display, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        } else {
            display = getDisplay();
        }
        this.displayText = this.editMode ? (String) this.displayString.get() : display;
    }

    public void handleMouseClick(double d, double d2, int i) {
        if (isInBorder(d, d2) && i == 0) {
            if (System.currentTimeMillis() - this.prevClick <= 250) {
                this.editMode = true;
            }
            this.prevClick = System.currentTimeMillis();
            return;
        }
        this.editMode = false;
    }

    public void handleKey(char c, int i) {
        if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
            if (i == 14) {
                if (((CharSequence) this.displayString.get()).length() > 0) {
                    TextValue textValue = this.displayString;
                    String str = (String) this.displayString.get();
                    int length = ((String) this.displayString.get()).length() - 1;
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String strSubstring = str.substring(0, length);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    textValue.set(strSubstring);
                }
                updateElement();
                return;
            }
            if (ChatAllowedCharacters.func_71566_a(c) || c == '\u00a7') {
                this.displayString.set(((String) this.displayString.get()) + c);
            }
            updateElement();
        }
    }

    @NotNull
    public final Text2 setColor(@NotNull Color c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        this.redValue.set((Object) Integer.valueOf(c.getRed()));
        this.greenValue.set((Object) Integer.valueOf(c.getGreen()));
        this.blueValue.set((Object) Integer.valueOf(c.getBlue()));
        return this;
    }
}

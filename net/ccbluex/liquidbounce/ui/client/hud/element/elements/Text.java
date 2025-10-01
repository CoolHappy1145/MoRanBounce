package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.UiUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Text.kt */
@ElementInfo(name = "Text")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\ufffd\ufffd 72\u00020\u0001:\u00017B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\n\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0012\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010+\u001a\u00020\rH\u0002J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0016H\u0016J \u00101\u001a\u00020-2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u00102\u001a\u00020\u0016H\u0016J\u0010\u00103\u001a\u00020\r2\u0006\u0010+\u001a\u00020\rH\u0002J\u000e\u00104\u001a\u00020\ufffd\ufffd2\u0006\u0010.\u001a\u000205J\b\u00106\u001a\u00020-H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010#\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010'\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u00068"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "blueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "display", "", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "editMode", "", "editTicks", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "only", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "op", "outline", "prevClick", "", "rainbow", "rainbowX", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rainbowY", "rect", "redValue", "shadow", "sk", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getReplacement", AsmConstants.STR, "handleKey", "", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "Ljava/awt/Color;", "updateElement", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text.class */
public final class Text extends Element {
    private final TextValue displayString;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final BoolValue rainbow;
    private final FloatValue rainbowX;
    private final FloatValue rainbowY;
    private final BoolValue shadow;
    private final BoolValue outline;
    private final BoolValue rect;

    /* renamed from: op */
    private final BoolValue f152op;

    /* renamed from: sk */
    private final BoolValue f153sk;
    private final BoolValue only;
    private FontValue fontValue;
    private boolean editMode;
    private int editTicks;
    private long prevClick;
    private String displayText;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMddyy");

    @NotNull
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");

    @NotNull
    private static final DecimalFormat Y_FORMAT = new DecimalFormat("0.000000000");

    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public Text() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Text(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.displayString = new TextValue("DisplayText", "");
        this.redValue = new IntegerValue("Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Blue", 255, 0, 255);
        this.rainbow = new BoolValue("Rainbow", false);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0f, -2000.0f, 2000.0f);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0f, -2000.0f, 2000.0f);
        this.shadow = new BoolValue("Shadow", false);
        this.outline = new BoolValue("Outline", false);
        this.rect = new BoolValue("Rect", false);
        this.f152op = new BoolValue("OneTapRect", false);
        this.f153sk = new BoolValue("SkeetRect", true);
        this.only = new BoolValue("OnlyWhtie", false);
        IFontRenderer iFontRenderer = Fonts.minecraftFont;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.minecraftFont");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.displayText = getDisplay();
    }

    public /* synthetic */ Text(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 10.0d : d, (i & 2) != 0 ? 10.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? Side.Companion.m1697default() : side);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "HOUR_FORMAT", "getHOUR_FORMAT", "Y_FORMAT", "getY_FORMAT", "defaultClient", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final SimpleDateFormat getDATE_FORMAT() {
            return Text.DATE_FORMAT;
        }

        @NotNull
        public final SimpleDateFormat getHOUR_FORMAT() {
            return Text.HOUR_FORMAT;
        }

        @NotNull
        public final DecimalFormat getY_FORMAT() {
            return Text.Y_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT() {
            return Text.DECIMAL_FORMAT;
        }

        @NotNull
        public final Text defaultClient() {
            Text text = new Text(2.0d, 2.0d, 1.0f, null, 8, null);
            text.displayString.set("%clientname%");
            text.shadow.set(true);
            FontValue fontValue = text.fontValue;
            IFontRenderer iFontRenderer = Fonts.minecraftFont;
            Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.minecraftFont");
            fontValue.set(iFontRenderer);
            text.setColor(new Color(SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 50, 50));
            return text;
        }
    }

    private final String getDisplay() {
        String str;
        if ((((CharSequence) this.displayString.get()).length() == 0) && !this.editMode) {
            str = "Text Element";
        } else {
            str = (String) this.displayString.get();
        }
        String textContent = str;
        return multiReplace(textContent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x03fc, code lost:
    
        if (r9.equals("cps") != false) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0416, code lost:
    
        if (r9.equals("lcps") != false) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x04aa, code lost:
    
        return java.lang.String.valueOf(net.ccbluex.liquidbounce.utils.CPSCounter.getCPS(net.ccbluex.liquidbounce.utils.CPSCounter.MouseButton.LEFT));
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:207:0x04bf A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String getReplacement(String str) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
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
                        return DECIMAL_FORMAT.format(thePlayer.getPosX());
                    }
                    break;
                case 121:
                    if (str.equals("y")) {
                        return DECIMAL_FORMAT.format(thePlayer.getPosY());
                    }
                    break;
                case 122:
                    if (str.equals("z")) {
                        return DECIMAL_FORMAT.format(thePlayer.getPosZ());
                    }
                    break;
                case 118532:
                    if (str.equals("xdp")) {
                        return String.valueOf(thePlayer.getPosX());
                    }
                    break;
                case 119493:
                    if (str.equals("ydp")) {
                        return String.valueOf(thePlayer.getPosY());
                    }
                    break;
                case 120454:
                    if (str.equals("zdp")) {
                        return String.valueOf(thePlayer.getPosZ());
                    }
                    break;
                case 3441010:
                    if (str.equals("ping")) {
                        return String.valueOf(EntityUtils.getPing(thePlayer));
                    }
                    break;
                case 2134260957:
                    if (str.equals("velocity")) {
                        return DECIMAL_FORMAT.format(Math.sqrt((thePlayer.getMotionX() * thePlayer.getMotionX()) + (thePlayer.getMotionZ() * thePlayer.getMotionZ())));
                    }
                    break;
            }
        }
        switch (str.hashCode()) {
            case -892772691:
                if (str.equals("clientversion")) {
                    return "b1";
                }
                return null;
            case -265713450:
                if (str.equals("username")) {
                    return MinecraftInstance.f157mc.getSession().getUsername();
                }
                return null;
            case -215825919:
                if (str.equals("clientcreator")) {
                    return LiquidBounce.CLIENT_CREATOR;
                }
                break;
            case 98726:
                break;
            case 101609:
                if (str.equals("fps")) {
                    return String.valueOf(MinecraftInstance.f157mc.getDebugFPS());
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
            case 1103204566:
                if (str.equals("clientname")) {
                    return LiquidBounce.CLIENT_NAME;
                }
                break;
            case 1379104682:
                if (str.equals("serverip")) {
                    return ServerUtils.getRemoteIp();
                }
                return null;
        }
    }

    private final String multiReplace(String str) {
        int lastPercent = -1;
        StringBuilder result = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == '%') {
                if (lastPercent != -1) {
                    if (lastPercent + 1 != i) {
                        int i2 = lastPercent + 1;
                        if (str == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String strSubstring = str.substring(i2, i);
                        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        String replacement = getReplacement(strSubstring);
                        if (replacement != null) {
                            result.append(replacement);
                            lastPercent = -1;
                        }
                    }
                    result.append((CharSequence) str, lastPercent, i);
                    lastPercent = i;
                } else {
                    lastPercent = i;
                }
            } else if (lastPercent == -1) {
                result.append(str.charAt(i));
            }
        }
        if (lastPercent != -1) {
            result.append((CharSequence) str, lastPercent, str.length());
        }
        String string = result.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "result.toString()");
        return string;
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        int rgb;
        int color = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()).getRGB();
        int colord = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()).getRGB() + new Color(0, 0, 0, 50).getRGB();
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
        if (((Boolean) this.rect.get()).booleanValue()) {
            RenderUtils.drawRect(-2.0f, -2.0f, fontRenderer.getStringWidth(this.displayText) + 1, fontRenderer.getFontHeight(), new Color(0, 0, 0, 150).getRGB());
        }
        String str = this.displayText;
        if (((Boolean) this.rainbow.get()).booleanValue()) {
            rgb = ColorUtils.rainbow(400000000L).getRGB();
        } else {
            rgb = ((Boolean) this.only.get()).booleanValue() ? -1 : color;
        }
        fontRenderer.drawString(str, 0.0f, 0.0f, rgb, ((Boolean) this.shadow.get()).booleanValue());
        if (((Boolean) this.f152op.get()).booleanValue()) {
            RenderUtils.drawRect(-4.0f, -8.0f, fontRenderer.getStringWidth(this.displayText) + 3, fontRenderer.getFontHeight(), new Color(43, 43, 43).getRGB());
            RenderUtils.drawGradientSideways(-3.0d, -7.0d, fontRenderer.getStringWidth(this.displayText) + 2.0d, -3.0d, ((Boolean) this.rainbow.get()).booleanValue() ? ColorUtils.rainbow(400000000L).getRGB() + new Color(0, 0, 0, 40).getRGB() : colord, ((Boolean) this.rainbow.get()).booleanValue() ? ColorUtils.rainbow(400000000L).getRGB() : color);
        }
        if (((Boolean) this.f153sk.get()).booleanValue()) {
            UiUtils.drawRect(-11.0d, -9.5d, fontRenderer.getStringWidth(this.displayText) + 9, fontRenderer.getFontHeight() + 6, new Color(0, 0, 0).getRGB());
            UiUtils.outlineRect(-10.0d, -8.5d, fontRenderer.getStringWidth(this.displayText) + 8, fontRenderer.getFontHeight() + 5, 8.0d, new Color(59, 59, 59).getRGB(), new Color(59, 59, 59).getRGB());
            UiUtils.outlineRect(-9.0d, -7.5d, fontRenderer.getStringWidth(this.displayText) + 7, fontRenderer.getFontHeight() + 4, 4.0d, new Color(59, 59, 59).getRGB(), new Color(40, 40, 40).getRGB());
            UiUtils.outlineRect(-4.0d, -3.0d, fontRenderer.getStringWidth(this.displayText) + 2, fontRenderer.getFontHeight() + 0, 1.0d, new Color(18, 18, 18).getRGB(), new Color(0, 0, 0).getRGB());
        }
        if (((Boolean) this.outline.get()).booleanValue()) {
            String info = this.displayText;
            GlStateManager.func_179117_G();
            int stringWidth = fontRenderer.getStringWidth(this.displayText);
            int stringWidth2 = fontRenderer.getStringWidth(this.displayText);
            Color color2 = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
            RenderUtils.drawOutlinedString(info, stringWidth, stringWidth2, 0, color2.getRGB());
        }
        boolean rainbow = ((Boolean) this.rainbow.get()).booleanValue();
        float x$iv = ((Number) this.rainbowX.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowX.get()).floatValue();
        float y$iv = ((Number) this.rainbowY.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowY.get()).floatValue();
        float offset$iv = (System.currentTimeMillis() % 10000) / 10000.0f;
        if (rainbow) {
            RainbowFontShader.INSTANCE.setStrengthX(x$iv);
            RainbowFontShader.INSTANCE.setStrengthY(y$iv);
            RainbowFontShader.INSTANCE.setOffset(offset$iv);
            RainbowFontShader.INSTANCE.startShader();
        }
        RainbowFontShader rainbowFontShader = RainbowFontShader.INSTANCE;
        Throwable th = (Throwable) null;
        try {
            try {
                fontRenderer.drawString(this.displayText, 0.0f, 0.0f, rainbow ? 0 : color, ((Boolean) this.shadow.get()).booleanValue());
                if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen()) && this.editTicks <= 40) {
                    fontRenderer.drawString("_", fontRenderer.getStringWidth(this.displayText) + 2.0f, 0.0f, rainbow ? ColorUtils.rainbow(400000000L).getRGB() : color, ((Boolean) this.shadow.get()).booleanValue());
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(rainbowFontShader, th);
                if (this.editMode && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
                    this.editMode = false;
                    updateElement();
                }
                return new Border(-2.0f, -2.0f, fontRenderer.getStringWidth(this.displayText) + 2.0f, fontRenderer.getFontHeight());
            } finally {
            }
        } catch (Throwable th2) {
            CloseableKt.closeFinally(rainbowFontShader, th);
            throw th2;
        }
    }

    public void updateElement() {
        this.editTicks += 5;
        if (this.editTicks > 80) {
            this.editTicks = 0;
        }
        this.displayText = this.editMode ? (String) this.displayString.get() : getDisplay();
    }

    public void handleMouseClick(double x, double y, int mouseButton) {
        if (isInBorder(x, y) && mouseButton == 0) {
            if (System.currentTimeMillis() - this.prevClick <= 250) {
                this.editMode = true;
            }
            this.prevClick = System.currentTimeMillis();
            return;
        }
        this.editMode = false;
    }

    public void handleKey(char c, int keyCode) {
        if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
            if (keyCode == 14) {
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
            if (ColorUtils.INSTANCE.isAllowedCharacter(c) || c == '\u00a7') {
                this.displayString.set(((String) this.displayString.get()) + c);
            }
            updateElement();
        }
    }

    @NotNull
    public final Text setColor(@NotNull Color c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        this.redValue.set((Object) Integer.valueOf(c.getRed()));
        this.greenValue.set((Object) Integer.valueOf(c.getGreen()));
        this.blueValue.set((Object) Integer.valueOf(c.getBlue()));
        return this;
    }
}

package net.ccbluex.liquidbounce.p005ui.client.hud.designer;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/* compiled from: EditorPanel.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\b\u0018\ufffd\ufffd2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\u0010\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010&\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u001e\u0010'\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J\u0018\u0010)\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u0016R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001c\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u001f\u00a8\u0006*"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "hudDesigner", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "x", "", "y", "(Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;II)V", "create", "", "getCreate", "()Z", "setCreate", "(Z)V", "currentElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "drag", "dragX", "dragY", "<set-?>", "height", "getHeight", "()I", "mouseDown", "realHeight", "getRealHeight", "scroll", "width", "getWidth", "getX", "setX", "(I)V", "getY", "setY", "", "mouseX", "mouseY", "drawCreate", "drawEditor", "drawPanel", "wheel", "drawSelection", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel.class */
public final class EditorPanel extends MinecraftInstance {
    private int width;
    private int height;
    private int realHeight;
    private boolean drag;
    private int dragX;
    private int dragY;
    private boolean mouseDown;
    private int scroll;
    private boolean create;
    private Element currentElement;
    private final GuiHudDesigner hudDesigner;

    /* renamed from: x */
    private int f139x;

    /* renamed from: y */
    private int f140y;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[Side.Horizontal.values().length];
        public static final int[] $EnumSwitchMapping$1;

        static {
            $EnumSwitchMapping$0[Side.Horizontal.LEFT.ordinal()] = 1;
            $EnumSwitchMapping$0[Side.Horizontal.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$0[Side.Horizontal.RIGHT.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[Side.Vertical.values().length];
            $EnumSwitchMapping$1[Side.Vertical.UP.ordinal()] = 1;
            $EnumSwitchMapping$1[Side.Vertical.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$1[Side.Vertical.DOWN.ordinal()] = 3;
        }
    }

    public final int getX() {
        return this.f139x;
    }

    public final void setX(int i) {
        this.f139x = i;
    }

    public final int getY() {
        return this.f140y;
    }

    public final void setY(int i) {
        this.f140y = i;
    }

    public EditorPanel(@NotNull GuiHudDesigner hudDesigner, int x, int y) {
        Intrinsics.checkParameterIsNotNull(hudDesigner, "hudDesigner");
        this.hudDesigner = hudDesigner;
        this.f139x = x;
        this.f140y = y;
        this.width = 80;
        this.height = 20;
        this.realHeight = 20;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getRealHeight() {
        return this.realHeight;
    }

    public final boolean getCreate() {
        return this.create;
    }

    public final void setCreate(boolean z) {
        this.create = z;
    }

    public final void drawPanel(int mouseX, int mouseY, int wheel) {
        drag(mouseX, mouseY);
        if (!Intrinsics.areEqual(this.currentElement, this.hudDesigner.getSelectedElement())) {
            this.scroll = 0;
        }
        this.currentElement = this.hudDesigner.getSelectedElement();
        int currMouseY = mouseY;
        boolean shouldScroll = this.realHeight > 200;
        if (shouldScroll) {
            GL11.glPushMatrix();
            RenderUtils.makeScissorBox(this.f139x, this.f140y + 1.0f, this.f139x + this.width, this.f140y + 200.0f);
            GL11.glEnable(SGL.GL_SCISSOR_TEST);
            if (this.f140y + SharedScopeCall.FAST_SCOPE_GET_THRESHOLD < currMouseY) {
                currMouseY = -1;
            }
            if (mouseX >= this.f139x && mouseX <= this.f139x + this.width && currMouseY >= this.f140y && currMouseY <= this.f140y + SharedScopeCall.FAST_SCOPE_GET_THRESHOLD && Mouse.hasWheel()) {
                if (wheel < 0 && (-this.scroll) + 205 <= this.realHeight) {
                    this.scroll -= 12;
                } else if (wheel > 0) {
                    this.scroll += 12;
                    if (this.scroll > 0) {
                        this.scroll = 0;
                    }
                }
            }
        }
        RenderUtils.drawRect(this.f139x, this.f140y + 12, this.f139x + this.width, this.f140y + this.realHeight, new Color(27, 34, 40).getRGB());
        if (this.create) {
            drawCreate(mouseX, currMouseY);
        } else if (this.currentElement != null) {
            drawEditor(mouseX, currMouseY);
        } else {
            drawSelection(mouseX, currMouseY);
        }
        if (shouldScroll) {
            RenderUtils.drawRect((this.f139x + this.width) - 5, this.f140y + 15, (this.f139x + this.width) - 2, this.f140y + 197, new Color(41, 41, 41).getRGB());
            float v = 197 * ((-this.scroll) / (this.realHeight - 170.0f));
            RenderUtils.drawRect((this.f139x + this.width) - 5.0f, this.f140y + 15 + v, (this.f139x + this.width) - 2.0f, this.f140y + 20 + v, new Color(37, 126, 255).getRGB());
            GL11.glDisable(SGL.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }
        this.mouseDown = Mouse.isButtonDown(0);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:40:0x014e
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:280)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:79)
        */
    private final void drawCreate(int r9, int r10) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p005ui.client.hud.designer.EditorPanel.drawCreate(int, int):void");
    }

    private final void drawSelection(int mouseX, int mouseY) {
        this.height = 15 + this.scroll;
        this.realHeight = 15;
        this.width = 120;
        Color color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
        Fonts.font35.drawString("\u00a7lCreate element", this.f139x + 2.0f, this.f140y + this.height, color.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
            this.create = true;
        }
        this.height += 10;
        this.realHeight += 10;
        Color color2 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.WHITE");
        Fonts.font35.drawString("\u00a7lReset", this.f139x + 2, this.f140y + this.height, color2.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
            LiquidBounce.INSTANCE.setHud(HUD.Companion.createDefault());
        }
        this.height += 15;
        this.realHeight += 15;
        Color color3 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
        Fonts.font35.drawString("\u00a7lAvailable Elements", this.f139x + 2.0f, this.f140y + this.height, color3.getRGB());
        this.height += 10;
        this.realHeight += 10;
        for (Element element : LiquidBounce.INSTANCE.getHud().getElements()) {
            IFontRenderer iFontRenderer = Fonts.font35;
            String name = element.getName();
            int i = this.f139x + 2;
            int i2 = this.f140y + this.height;
            Color color4 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color4, "Color.WHITE");
            iFontRenderer.drawString(name, i, i2, color4.getRGB());
            int stringWidth = Fonts.font35.getStringWidth(element.getName());
            if (this.width < stringWidth + 8) {
                this.width = stringWidth + 8;
            }
            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
                this.hudDesigner.setSelectedElement(element);
            }
            this.height += 10;
            this.realHeight += 10;
        }
        int i3 = this.f139x;
        int i4 = this.f140y;
        int i5 = this.f139x + this.width;
        int i6 = this.f140y + 12;
        Color colorGenerateColor = ClickGUI.generateColor();
        Intrinsics.checkExpressionValueIsNotNull(colorGenerateColor, "ClickGUI.generateColor()");
        RenderUtils.drawRect(i3, i4, i5, i6, colorGenerateColor.getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Color color5 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color5, "Color.WHITE");
        Fonts.font35.drawString("\u00a7lEditor", this.f139x + 2.0f, this.f140y + 3.5f, color5.getRGB());
    }

    private final void drawEditor(int mouseX, int mouseY) throws IllegalAccessException, IllegalArgumentException {
        int rgb;
        int rgb2;
        String str;
        double scaledHeight;
        double scaledWidth;
        this.height = this.scroll + 15;
        this.realHeight = 15;
        int prevWidth = this.width;
        this.width = 100;
        Element element = this.currentElement;
        if (element != null) {
            IFontRenderer iFontRenderer = Fonts.font35;
            StringBuilder sbAppend = new StringBuilder().append("X: ");
            Object[] objArr = {Double.valueOf(element.getRenderX())};
            String str2 = String.format("%.2f", Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(str2, "java.lang.String.format(this, *args)");
            StringBuilder sbAppend2 = sbAppend.append(str2).append(" (");
            Object[] objArr2 = {Double.valueOf(element.getX())};
            String str3 = String.format("%.2f", Arrays.copyOf(objArr2, objArr2.length));
            Intrinsics.checkExpressionValueIsNotNull(str3, "java.lang.String.format(this, *args)");
            String string = sbAppend2.append(str3).append(')').toString();
            int i = this.f139x + 2;
            int i2 = this.f140y + this.height;
            Color color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
            iFontRenderer.drawString(string, i, i2, color.getRGB());
            this.height += 10;
            this.realHeight += 10;
            IFontRenderer iFontRenderer2 = Fonts.font35;
            StringBuilder sbAppend3 = new StringBuilder().append("Y: ");
            Object[] objArr3 = {Double.valueOf(element.getRenderY())};
            String str4 = String.format("%.2f", Arrays.copyOf(objArr3, objArr3.length));
            Intrinsics.checkExpressionValueIsNotNull(str4, "java.lang.String.format(this, *args)");
            StringBuilder sbAppend4 = sbAppend3.append(str4).append(" (");
            Object[] objArr4 = {Double.valueOf(element.getY())};
            String str5 = String.format("%.2f", Arrays.copyOf(objArr4, objArr4.length));
            Intrinsics.checkExpressionValueIsNotNull(str5, "java.lang.String.format(this, *args)");
            String string2 = sbAppend4.append(str5).append(')').toString();
            int i3 = this.f139x + 2;
            int i4 = this.f140y + this.height;
            Color color2 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.WHITE");
            iFontRenderer2.drawString(string2, i3, i4, color2.getRGB());
            this.height += 10;
            this.realHeight += 10;
            IFontRenderer iFontRenderer3 = Fonts.font35;
            StringBuilder sbAppend5 = new StringBuilder().append("Scale: ");
            Object[] objArr5 = {Float.valueOf(element.getScale())};
            String str6 = String.format("%.2f", Arrays.copyOf(objArr5, objArr5.length));
            Intrinsics.checkExpressionValueIsNotNull(str6, "java.lang.String.format(this, *args)");
            String string3 = sbAppend5.append(str6).toString();
            int i5 = this.f139x + 2;
            int i6 = this.f140y + this.height;
            Color color3 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
            iFontRenderer3.drawString(string3, i5, i6, color3.getRGB());
            this.height += 10;
            this.realHeight += 10;
            IFontRenderer iFontRenderer4 = Fonts.font35;
            int i7 = this.f139x + 2;
            int i8 = this.f140y + this.height;
            Color color4 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color4, "Color.WHITE");
            iFontRenderer4.drawString("H:", i7, i8, color4.getRGB());
            IFontRenderer iFontRenderer5 = Fonts.font35;
            String sideName = element.getSide().getHorizontal().getSideName();
            int i9 = this.f139x + 12;
            int i10 = this.f140y + this.height;
            Color color5 = Color.GRAY;
            Intrinsics.checkExpressionValueIsNotNull(color5, "Color.GRAY");
            iFontRenderer5.drawString(sideName, i9, i10, color5.getRGB());
            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
                Side.Horizontal[] values = Side.Horizontal.values();
                int currIndex = ArraysKt.indexOf(values, element.getSide().getHorizontal());
                double x = element.getRenderX();
                element.getSide().setHorizontal(values[currIndex + 1 >= values.length ? 0 : currIndex + 1]);
                switch (WhenMappings.$EnumSwitchMapping$0[element.getSide().getHorizontal().ordinal()]) {
                    case 1:
                        scaledWidth = x;
                        break;
                    case 2:
                        IClassProvider iClassProvider = MinecraftInstance.classProvider;
                        IMinecraft mc = MinecraftInstance.f157mc;
                        Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
                        scaledWidth = (iClassProvider.createScaledResolution(mc).getScaledWidth() / 2) - x;
                        break;
                    case 3:
                        IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                        IMinecraft mc2 = MinecraftInstance.f157mc;
                        Intrinsics.checkExpressionValueIsNotNull(mc2, "mc");
                        scaledWidth = iClassProvider2.createScaledResolution(mc2).getScaledWidth() - x;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                element.setX(scaledWidth);
            }
            this.height += 10;
            this.realHeight += 10;
            IFontRenderer iFontRenderer6 = Fonts.font35;
            int i11 = this.f139x + 2;
            int i12 = this.f140y + this.height;
            Color color6 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color6, "Color.WHITE");
            iFontRenderer6.drawString("V:", i11, i12, color6.getRGB());
            IFontRenderer iFontRenderer7 = Fonts.font35;
            String sideName2 = element.getSide().getVertical().getSideName();
            int i13 = this.f139x + 12;
            int i14 = this.f140y + this.height;
            Color color7 = Color.GRAY;
            Intrinsics.checkExpressionValueIsNotNull(color7, "Color.GRAY");
            iFontRenderer7.drawString(sideName2, i13, i14, color7.getRGB());
            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
                Side.Vertical[] values2 = Side.Vertical.values();
                int currIndex2 = ArraysKt.indexOf(values2, element.getSide().getVertical());
                double y = element.getRenderY();
                element.getSide().setVertical(values2[currIndex2 + 1 >= values2.length ? 0 : currIndex2 + 1]);
                switch (WhenMappings.$EnumSwitchMapping$1[element.getSide().getVertical().ordinal()]) {
                    case 1:
                        scaledHeight = y;
                        break;
                    case 2:
                        IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                        IMinecraft mc3 = MinecraftInstance.f157mc;
                        Intrinsics.checkExpressionValueIsNotNull(mc3, "mc");
                        scaledHeight = (iClassProvider3.createScaledResolution(mc3).getScaledHeight() / 2) - y;
                        break;
                    case 3:
                        IClassProvider iClassProvider4 = MinecraftInstance.classProvider;
                        IMinecraft mc4 = MinecraftInstance.f157mc;
                        Intrinsics.checkExpressionValueIsNotNull(mc4, "mc");
                        scaledHeight = iClassProvider4.createScaledResolution(mc4).getScaledHeight() - y;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                element.setY(scaledHeight);
            }
            this.height += 10;
            this.realHeight += 10;
            for (Value value : element.getValues()) {
                if (value instanceof BoolValue) {
                    IFontRenderer iFontRenderer8 = Fonts.font35;
                    String name = value.getName();
                    int i15 = this.f139x + 2;
                    int i16 = this.f140y + this.height;
                    if (((Boolean) ((BoolValue) value).get()).booleanValue()) {
                        Color color8 = Color.WHITE;
                        Intrinsics.checkExpressionValueIsNotNull(color8, "Color.WHITE");
                        rgb = color8.getRGB();
                    } else {
                        Color color9 = Color.GRAY;
                        Intrinsics.checkExpressionValueIsNotNull(color9, "Color.GRAY");
                        rgb = color9.getRGB();
                    }
                    iFontRenderer8.drawString(name, i15, i16, rgb);
                    int stringWidth = Fonts.font35.getStringWidth(value.getName());
                    if (this.width < stringWidth + 8) {
                        this.width = stringWidth + 8;
                    }
                    if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
                        ((BoolValue) value).set(Boolean.valueOf(!((Boolean) ((BoolValue) value).get()).booleanValue()));
                    }
                    this.height += 10;
                    this.realHeight += 10;
                } else if (value instanceof FloatValue) {
                    float current = ((Number) ((FloatValue) value).get()).floatValue();
                    float min = ((FloatValue) value).getMinimum();
                    float max = ((FloatValue) value).getMaximum();
                    StringBuilder sbAppend6 = new StringBuilder().append(value.getName()).append(": \u00a7c");
                    Object[] objArr6 = {Float.valueOf(current)};
                    String str7 = String.format("%.2f", Arrays.copyOf(objArr6, objArr6.length));
                    Intrinsics.checkExpressionValueIsNotNull(str7, "java.lang.String.format(this, *args)");
                    String text = sbAppend6.append(str7).toString();
                    IFontRenderer iFontRenderer9 = Fonts.font35;
                    int i17 = this.f139x + 2;
                    int i18 = this.f140y + this.height;
                    Color color10 = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color10, "Color.WHITE");
                    iFontRenderer9.drawString(text, i17, i18, color10.getRGB());
                    int stringWidth2 = Fonts.font35.getStringWidth(text);
                    if (this.width < stringWidth2 + 8) {
                        this.width = stringWidth2 + 8;
                    }
                    RenderUtils.drawRect(this.f139x + 8.0f, this.f140y + this.height + 12.0f, (this.f139x + prevWidth) - 8.0f, this.f140y + this.height + 13.0f, Color.WHITE);
                    float sliderValue = this.f139x + (((prevWidth - 18.0f) * (current - min)) / (max - min));
                    RenderUtils.drawRect(8.0f + sliderValue, this.f140y + this.height + 9.0f, sliderValue + 11.0f, this.f140y + this.height + 15.0f, new Color(37, 126, 255).getRGB());
                    if (mouseX >= this.f139x + 8 && mouseX <= this.f139x + prevWidth && mouseY >= this.f140y + this.height + 9 && mouseY <= this.f140y + this.height + 15 && Mouse.isButtonDown(0)) {
                        float num$iv = ((mouseX - this.f139x) - 8.0f) / (prevWidth - 18.0f);
                        float curr = num$iv < 0.0f ? 0.0f : num$iv > 1.0f ? 1.0f : num$iv;
                        ((FloatValue) value).set((Object) Float.valueOf(min + ((max - min) * curr)));
                    }
                    this.height += 20;
                    this.realHeight += 20;
                } else if (value instanceof IntegerValue) {
                    int current2 = ((Number) ((IntegerValue) value).get()).intValue();
                    int min2 = ((IntegerValue) value).getMinimum();
                    int max2 = ((IntegerValue) value).getMaximum();
                    String text2 = value.getName() + ": \u00a7c" + current2;
                    IFontRenderer iFontRenderer10 = Fonts.font35;
                    int i19 = this.f139x + 2;
                    int i20 = this.f140y + this.height;
                    Color color11 = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color11, "Color.WHITE");
                    iFontRenderer10.drawString(text2, i19, i20, color11.getRGB());
                    int stringWidth3 = Fonts.font35.getStringWidth(text2);
                    if (this.width < stringWidth3 + 8) {
                        this.width = stringWidth3 + 8;
                    }
                    RenderUtils.drawRect(this.f139x + 8.0f, this.f140y + this.height + 12.0f, (this.f139x + prevWidth) - 8.0f, this.f140y + this.height + 13.0f, Color.WHITE);
                    float sliderValue2 = this.f139x + (((prevWidth - 18.0f) * (current2 - min2)) / (max2 - min2));
                    RenderUtils.drawRect(8.0f + sliderValue2, this.f140y + this.height + 9.0f, sliderValue2 + 11.0f, this.f140y + this.height + 15.0f, new Color(37, 126, 255).getRGB());
                    if (mouseX >= this.f139x + 8 && mouseX <= this.f139x + prevWidth && mouseY >= this.f140y + this.height + 9 && mouseY <= this.f140y + this.height + 15 && Mouse.isButtonDown(0)) {
                        float num$iv2 = ((mouseX - this.f139x) - 8.0f) / (prevWidth - 18.0f);
                        float curr2 = num$iv2 < 0.0f ? 0.0f : num$iv2 > 1.0f ? 1.0f : num$iv2;
                        ((IntegerValue) value).set((Object) Integer.valueOf((int) (min2 + ((max2 - min2) * curr2))));
                    }
                    this.height += 20;
                    this.realHeight += 20;
                } else if (value instanceof ListValue) {
                    IFontRenderer iFontRenderer11 = Fonts.font35;
                    String name2 = value.getName();
                    int i21 = this.f139x + 2;
                    int i22 = this.f140y + this.height;
                    Color color12 = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color12, "Color.WHITE");
                    iFontRenderer11.drawString(name2, i21, i22, color12.getRGB());
                    this.height += 10;
                    this.realHeight += 10;
                    for (String s : ((ListValue) value).getValues()) {
                        String text3 = "\u00a7c> \u00a7r" + s;
                        IFontRenderer iFontRenderer12 = Fonts.font35;
                        int i23 = this.f139x + 2;
                        int i24 = this.f140y + this.height;
                        if (Intrinsics.areEqual(s, (String) ((ListValue) value).get())) {
                            Color color13 = Color.WHITE;
                            Intrinsics.checkExpressionValueIsNotNull(color13, "Color.WHITE");
                            rgb2 = color13.getRGB();
                        } else {
                            Color color14 = Color.GRAY;
                            Intrinsics.checkExpressionValueIsNotNull(color14, "Color.GRAY");
                            rgb2 = color14.getRGB();
                        }
                        iFontRenderer12.drawString(text3, i23, i24, rgb2);
                        int stringWidth4 = Fonts.font35.getStringWidth(text3);
                        if (this.width < stringWidth4 + 8) {
                            this.width = stringWidth4 + 8;
                        }
                        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
                            ((ListValue) value).set(s);
                        }
                        this.height += 10;
                        this.realHeight += 10;
                    }
                } else if (value instanceof FontValue) {
                    IFontRenderer fontRenderer = (IFontRenderer) ((FontValue) value).get();
                    if (fontRenderer.isGameFontRenderer()) {
                        str = "Font: " + fontRenderer.getGameFontRenderer().getDefaultFont().getFont().getName() + " - " + fontRenderer.getGameFontRenderer().getDefaultFont().getFont().getSize();
                    } else {
                        str = Intrinsics.areEqual(fontRenderer, Fonts.minecraftFont) ? "Font: Minecraft" : "Font: Unknown";
                    }
                    String text4 = str;
                    IFontRenderer iFontRenderer13 = Fonts.font35;
                    int i25 = this.f139x + 2;
                    int i26 = this.f140y + this.height;
                    Color color15 = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color15, "Color.WHITE");
                    iFontRenderer13.drawString(text4, i25, i26, color15.getRGB());
                    int stringWidth5 = Fonts.font35.getStringWidth(text4);
                    if (this.width < stringWidth5 + 8) {
                        this.width = stringWidth5 + 8;
                    }
                    if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y + this.height && mouseY <= this.f140y + this.height + 10) {
                        List fonts = Fonts.getFonts();
                        Intrinsics.checkExpressionValueIsNotNull(fonts, "fonts");
                        List $this$forEachIndexed$iv = fonts;
                        int index$iv = 0;
                        for (Object item$iv : $this$forEachIndexed$iv) {
                            int index = index$iv;
                            index$iv++;
                            if (index < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            IFontRenderer font = (IFontRenderer) item$iv;
                            if (Intrinsics.areEqual(font, fontRenderer)) {
                                FontValue fontValue = (FontValue) value;
                                Object obj = fonts.get(index + 1 >= fonts.size() ? 0 : index + 1);
                                Intrinsics.checkExpressionValueIsNotNull(obj, "fonts[if (index + 1 >= f\u2026s.size) 0 else index + 1]");
                                fontValue.set(obj);
                            }
                        }
                    }
                    this.height += 10;
                    this.realHeight += 10;
                }
            }
            int i27 = this.f139x;
            int i28 = this.f140y;
            int i29 = this.f139x + this.width;
            int i30 = this.f140y + 12;
            Color colorGenerateColor = ClickGUI.generateColor();
            Intrinsics.checkExpressionValueIsNotNull(colorGenerateColor, "ClickGUI.generateColor()");
            RenderUtils.drawRect(i27, i28, i29, i30, colorGenerateColor.getRGB());
            Color color16 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color16, "Color.WHITE");
            Fonts.font35.drawString("\u00a7l" + element.getName(), this.f139x + 2.0f, this.f140y + 3.5f, color16.getRGB());
            if (!element.getInfo().force()) {
                float deleteWidth = ((this.f139x + this.width) - Fonts.font35.getStringWidth("\u00a7lDelete")) - 2.0f;
                Color color17 = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(color17, "Color.WHITE");
                Fonts.font35.drawString("\u00a7lDelete", deleteWidth, this.f140y + 3.5f, color17.getRGB());
                if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= deleteWidth && mouseX <= this.f139x + this.width && mouseY >= this.f140y && mouseY <= this.f140y + 10) {
                    LiquidBounce.INSTANCE.getHud().removeElement(element);
                }
            }
        }
    }

    private final void drag(int mouseX, int mouseY) {
        if (mouseX >= this.f139x && mouseX <= this.f139x + this.width && mouseY >= this.f140y && mouseY <= this.f140y + 12 && Mouse.isButtonDown(0) && !this.mouseDown) {
            this.drag = true;
            this.dragX = mouseX - this.f139x;
            this.dragY = mouseY - this.f140y;
        }
        if (Mouse.isButtonDown(0) && this.drag) {
            this.f139x = mouseX - this.dragX;
            this.f140y = mouseY - this.dragY;
        } else {
            this.drag = false;
        }
    }
}

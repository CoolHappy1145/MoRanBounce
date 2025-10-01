package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "SpeedGraph")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010'\u001a\u00020(H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010!\u001a\u0012\u0012\u0004\u0012\u00020\u00030\"j\b\u0012\u0004\u0012\u00020\u0003`#X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006)"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/SpeedGraph;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "bdBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "bdGreenValue", "bdRedValue", "bgAlphaValue", "bgBlueValue", "bgGreenValue", "bgRedValue", "boarderValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "clBlueValue", "clGreenValue", "clRedValue", "colorBlueValue", "colorGreenValue", "colorRedValue", "currentLineValue", "height", "lastSpeed", "lastTick", "", "smoothness", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "speedList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "thickness", "width", "yMultiplier", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/SpeedGraph.class */
public final class SpeedGraph extends Element {
    private final FloatValue yMultiplier;
    private final IntegerValue height;
    private final IntegerValue width;
    private final FloatValue thickness;
    private final FloatValue smoothness;
    private final IntegerValue colorRedValue;
    private final IntegerValue colorGreenValue;
    private final IntegerValue colorBlueValue;
    private final IntegerValue bgRedValue;
    private final IntegerValue bgGreenValue;
    private final IntegerValue bgBlueValue;
    private final IntegerValue bgAlphaValue;
    private final IntegerValue bdRedValue;
    private final IntegerValue bdGreenValue;
    private final IntegerValue bdBlueValue;
    private final BoolValue boarderValue;
    private final BoolValue currentLineValue;
    private final IntegerValue clRedValue;
    private final IntegerValue clGreenValue;
    private final IntegerValue clBlueValue;
    private final ArrayList speedList;
    private int lastTick;
    private double lastSpeed;

    public SpeedGraph() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpeedGraph(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.yMultiplier = new FloatValue("yMultiplier", 7.0f, 1.0f, 20.0f);
        this.height = new IntegerValue("Height", 50, 30, 150);
        this.width = new IntegerValue("Width", 150, 100, 300);
        this.thickness = new FloatValue("Thickness", 2.0f, 1.0f, 3.0f);
        this.smoothness = new FloatValue("Smoothness", 0.5f, 0.0f, 1.0f);
        this.colorRedValue = new IntegerValue("R", 0, 0, 255);
        this.colorGreenValue = new IntegerValue("G", 111, 0, 255);
        this.colorBlueValue = new IntegerValue("B", 255, 0, 255);
        this.bgRedValue = new IntegerValue("BGRed", 0, 0, 255);
        this.bgGreenValue = new IntegerValue("BGGreen", 0, 0, 255);
        this.bgBlueValue = new IntegerValue("BGBlue", 0, 0, 255);
        this.bgAlphaValue = new IntegerValue("BGAlpha", 150, 0, 255);
        this.bdRedValue = new IntegerValue("BDRed", 255, 0, 255);
        this.bdGreenValue = new IntegerValue("BDGreen", 255, 0, 255);
        this.bdBlueValue = new IntegerValue("BDBlue", 255, 0, 255);
        this.boarderValue = new BoolValue("Boarder", false);
        this.currentLineValue = new BoolValue("CurrentLine", false);
        this.clRedValue = new IntegerValue("CLRed", 0, 0, 255);
        this.clGreenValue = new IntegerValue("CLGreen", 255, 0, 255);
        this.clBlueValue = new IntegerValue("CLBlue", 0, 0, 255);
        this.speedList = new ArrayList();
        this.lastTick = -1;
        this.lastSpeed = 0.01d;
    }

    public SpeedGraph(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 75.0d : d, (i & 2) != 0 ? 110.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN) : side);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        int iIntValue = ((Number) this.width.get()).intValue();
        int i = this.lastTick;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (i != thePlayer.getTicksExisted()) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            this.lastTick = thePlayer2.getTicksExisted();
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            double posZ = thePlayer3.getPosZ();
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            double prevPosZ = thePlayer4.getPrevPosZ();
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            double posX = thePlayer5.getPosX();
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            double prevPosX = thePlayer6.getPrevPosX();
            double dSqrt = Math.sqrt(((posZ - prevPosZ) * (posZ - prevPosZ)) + ((posX - prevPosX) * (posX - prevPosX)));
            if (dSqrt < 0.0d) {
                dSqrt = -dSqrt;
            }
            double dDoubleValue = (((this.lastSpeed * 0.9d) + (dSqrt * 0.1d)) * ((Number) this.smoothness.get()).doubleValue()) + (dSqrt * (1.0f - ((Number) this.smoothness.get()).floatValue()));
            this.lastSpeed = dDoubleValue;
            this.speedList.add(Double.valueOf(dDoubleValue));
            while (this.speedList.size() > iIntValue) {
                this.speedList.remove(0);
            }
        }
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        GL11.glLineWidth(((Number) this.thickness.get()).floatValue());
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        RenderUtils.glColor(((Number) this.bgRedValue.get()).intValue(), ((Number) this.bgGreenValue.get()).intValue(), ((Number) this.bgBlueValue.get()).intValue(), ((Number) this.bgAlphaValue.get()).intValue());
        RenderUtils.quickDrawRect(0.0f, 0.0f, iIntValue, ((Number) this.height.get()).floatValue() + 2.0f);
        GL11.glBegin(1);
        int size = this.speedList.size();
        RenderUtils.glColor(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 255);
        int i2 = size > iIntValue ? size - iIntValue : 0;
        int i3 = size - 1;
        for (int i4 = i2; i4 < i3; i4++) {
            double dDoubleValue2 = ((Number) this.speedList.get(i4)).doubleValue() * 10.0d * ((Number) this.yMultiplier.get()).doubleValue();
            double dDoubleValue3 = ((Number) this.speedList.get(i4 + 1)).doubleValue() * 10.0d * ((Number) this.yMultiplier.get()).doubleValue();
            GL11.glVertex2d(i4 - i2, (((Number) this.height.get()).intValue() + 1) - RangesKt.coerceAtMost(dDoubleValue2, ((Number) this.height.get()).intValue()));
            GL11.glVertex2d((i4 + 1.0d) - i2, (((Number) this.height.get()).intValue() + 1) - RangesKt.coerceAtMost(dDoubleValue3, ((Number) this.height.get()).intValue()));
        }
        GL11.glEnd();
        if (((Boolean) this.currentLineValue.get()).booleanValue()) {
            Double d = (Double) CollectionsKt.lastOrNull((List) this.speedList);
            double dDoubleValue4 = (d != null ? d.doubleValue() : 0.0d) * 10.0d * ((Number) this.yMultiplier.get()).doubleValue();
            RenderUtils.glColor(((Number) this.clRedValue.get()).intValue(), ((Number) this.clGreenValue.get()).intValue(), ((Number) this.clBlueValue.get()).intValue(), 255);
            GL11.glBegin(1);
            GL11.glVertex2d(0.0d, (((Number) this.height.get()).intValue() + 1) - RangesKt.coerceAtMost(dDoubleValue4, ((Number) this.height.get()).intValue()));
            GL11.glVertex2d(iIntValue, (((Number) this.height.get()).intValue() + 1) - RangesKt.coerceAtMost(dDoubleValue4, ((Number) this.height.get()).intValue()));
            GL11.glEnd();
        }
        if (((Boolean) this.boarderValue.get()).booleanValue()) {
            RenderUtils.glColor(((Number) this.bdRedValue.get()).intValue(), ((Number) this.bdGreenValue.get()).intValue(), ((Number) this.bdBlueValue.get()).intValue(), 255);
            GL11.glBegin(3);
            GL11.glVertex2d(0.0d, 0.0d);
            GL11.glVertex2d(iIntValue, 0.0d);
            GL11.glVertex2d(iIntValue, ((Number) this.height.get()).intValue() + 2.0d);
            GL11.glVertex2d(0.0d, ((Number) this.height.get()).intValue() + 2.0d);
            GL11.glVertex2d(0.0d, 0.0d);
            GL11.glEnd();
        }
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glEnable(SGL.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(SGL.GL_BLEND);
        GlStateManager.func_179117_G();
        return new Border(0.0f, 0.0f, iIntValue, ((Number) this.height.get()).intValue() + 2.0f);
    }
}

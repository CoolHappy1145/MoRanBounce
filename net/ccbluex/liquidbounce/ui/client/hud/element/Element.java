package net.ccbluex.liquidbounce.p005ui.client.hud.element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdb\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010>\u001a\u00020\u0011H\u0016J\b\u0010?\u001a\u00020@H\u0016J\n\u0010A\u001a\u0004\u0018\u00010\u000bH&J\u0018\u0010B\u001a\u00020@2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0016J \u0010G\u001a\u00020@2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010H\u001a\u00020FH\u0016J\u0018\u0010I\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\b\u0010J\u001a\u00020@H\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R$\u0010'\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R$\u0010,\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R&\u0010\u0005\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b/\u0010 \"\u0004\b0\u0010\"R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001e\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000307068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b8\u00109R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b:\u0010)\"\u0004\b;\u0010+R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b<\u0010)\"\u0004\b=\u0010+\u00a8\u0006K"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "border", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getBorder", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setBorder", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;)V", "drag", "", "getDrag", "()Z", "setDrag", "(Z)V", "info", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "name", "", "getName", "()Ljava/lang/String;", "prevMouseX", "getPrevMouseX", "()F", "setPrevMouseX", "(F)V", "prevMouseY", "getPrevMouseY", "setPrevMouseY", PropertyDescriptor.VALUE, "renderX", "getRenderX", "()D", "setRenderX", "(D)V", "renderY", "getRenderY", "setRenderY", "getScale", "setScale", "getSide", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "setSide", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "getX", "setX", "getY", "setY", "createElement", "destroyElement", "", "drawElement", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseButton", "isInBorder", "updateElement", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Element.class */
public abstract class Element extends MinecraftInstance {

    @NotNull
    private final ElementInfo info;
    private float scale;

    @Nullable
    private Border border;
    private boolean drag;
    private float prevMouseX;
    private float prevMouseY;

    /* renamed from: x */
    private double f145x;

    /* renamed from: y */
    private double f146y;

    @NotNull
    private Side side;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Element$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[Side.Horizontal.values().length];
        public static final int[] $EnumSwitchMapping$1;
        public static final int[] $EnumSwitchMapping$2;
        public static final int[] $EnumSwitchMapping$3;

        static {
            $EnumSwitchMapping$0[Side.Horizontal.LEFT.ordinal()] = 1;
            $EnumSwitchMapping$0[Side.Horizontal.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$0[Side.Horizontal.RIGHT.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[Side.Horizontal.values().length];
            $EnumSwitchMapping$1[Side.Horizontal.LEFT.ordinal()] = 1;
            $EnumSwitchMapping$1[Side.Horizontal.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$1[Side.Horizontal.RIGHT.ordinal()] = 3;
            $EnumSwitchMapping$2 = new int[Side.Vertical.values().length];
            $EnumSwitchMapping$2[Side.Vertical.UP.ordinal()] = 1;
            $EnumSwitchMapping$2[Side.Vertical.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$2[Side.Vertical.DOWN.ordinal()] = 3;
            $EnumSwitchMapping$3 = new int[Side.Vertical.values().length];
            $EnumSwitchMapping$3[Side.Vertical.UP.ordinal()] = 1;
            $EnumSwitchMapping$3[Side.Vertical.MIDDLE.ordinal()] = 2;
            $EnumSwitchMapping$3[Side.Vertical.DOWN.ordinal()] = 3;
        }
    }

    @Nullable
    public abstract Border drawElement();

    public Element() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public final double getX() {
        return this.f145x;
    }

    public final void setX(double d) {
        this.f145x = d;
    }

    public final double getY() {
        return this.f146y;
    }

    public final void setY(double d) {
        this.f146y = d;
    }

    public Element(double d, double d2, float f, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.f145x = d;
        this.f146y = d2;
        this.side = side;
        ElementInfo elementInfo = (ElementInfo) getClass().getAnnotation(ElementInfo.class);
        if (elementInfo != null) {
            this.info = elementInfo;
            this.scale = 1.0f;
            setScale(f);
            return;
        }
        throw new IllegalArgumentException("Passed element with missing element info");
    }

    public Element(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 2.0d : d, (i & 2) != 0 ? 2.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? Side.Companion.m1697default() : side);
    }

    @NotNull
    public final Side getSide() {
        return this.side;
    }

    public final void setSide(@NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "<set-?>");
        this.side = side;
    }

    @NotNull
    public final ElementInfo getInfo() {
        return this.info;
    }

    public final void setScale(float f) {
        if (this.info.disableScale()) {
            return;
        }
        this.scale = f;
    }

    public final float getScale() {
        if (this.info.disableScale()) {
            return 1.0f;
        }
        return this.scale;
    }

    @NotNull
    public final String getName() {
        return this.info.name();
    }

    public final double getRenderX() {
        switch (WhenMappings.$EnumSwitchMapping$0[this.side.getHorizontal().ordinal()]) {
            case 1:
                return this.f145x;
            case 2:
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IMinecraft mc = MinecraftInstance.f157mc;
                Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
                return (iClassProvider.createScaledResolution(mc).getScaledWidth() / 2) - this.f145x;
            case 3:
                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                IMinecraft mc2 = MinecraftInstance.f157mc;
                Intrinsics.checkExpressionValueIsNotNull(mc2, "mc");
                return iClassProvider2.createScaledResolution(mc2).getScaledWidth() - this.f145x;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final void setRenderX(double d) {
        switch (WhenMappings.$EnumSwitchMapping$1[this.side.getHorizontal().ordinal()]) {
            case 1:
                this.f145x += d;
                return;
            case 2:
            case 3:
                this.f145x -= d;
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final double getRenderY() {
        switch (WhenMappings.$EnumSwitchMapping$2[this.side.getVertical().ordinal()]) {
            case 1:
                return this.f146y;
            case 2:
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IMinecraft mc = MinecraftInstance.f157mc;
                Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
                return (iClassProvider.createScaledResolution(mc).getScaledHeight() / 2) - this.f146y;
            case 3:
                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                IMinecraft mc2 = MinecraftInstance.f157mc;
                Intrinsics.checkExpressionValueIsNotNull(mc2, "mc");
                return iClassProvider2.createScaledResolution(mc2).getScaledHeight() - this.f146y;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final void setRenderY(double d) {
        switch (WhenMappings.$EnumSwitchMapping$3[this.side.getVertical().ordinal()]) {
            case 1:
                this.f146y += d;
                return;
            case 2:
            case 3:
                this.f146y -= d;
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Nullable
    public final Border getBorder() {
        return this.border;
    }

    public final void setBorder(@Nullable Border border) {
        this.border = border;
    }

    public final boolean getDrag() {
        return this.drag;
    }

    public final void setDrag(boolean z) {
        this.drag = z;
    }

    public final float getPrevMouseX() {
        return this.prevMouseX;
    }

    public final void setPrevMouseX(float f) {
        this.prevMouseX = f;
    }

    public final float getPrevMouseY() {
        return this.prevMouseY;
    }

    public final void setPrevMouseY(float f) {
        this.prevMouseY = f;
    }

    @NotNull
    public List getValues() {
        Field[] declaredFields = getClass().getDeclaredFields();
        Intrinsics.checkExpressionValueIsNotNull(declaredFields, "javaClass.declaredFields");
        ArrayList arrayList = new ArrayList(declaredFields.length);
        for (Field valueField : declaredFields) {
            Intrinsics.checkExpressionValueIsNotNull(valueField, "valueField");
            valueField.setAccessible(true);
            arrayList.add(valueField.get(this));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (obj instanceof Value) {
                arrayList3.add(obj);
            }
        }
        return arrayList3;
    }

    public boolean isInBorder(double d, double d2) {
        Border border = this.border;
        if (border != null) {
            return ((double) Math.min(border.getX(), border.getX2())) <= d && ((double) Math.min(border.getY(), border.getY2())) <= d2 && ((double) Math.max(border.getX(), border.getX2())) >= d && ((double) Math.max(border.getY(), border.getY2())) >= d2;
        }
        return false;
    }
}

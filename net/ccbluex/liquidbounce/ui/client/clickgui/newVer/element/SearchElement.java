package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.ColorManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.IconManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.ModuleElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdJ\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\f\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018JT\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00152\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010\u0017\u001a\u00020\u0018J(\u0010%\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002JL\u0010&\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010'\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#JL\u0010(\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010'\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#J\u0018\u0010)\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0003H\u0002JD\u0010*\u001a\u00020\u00132\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#J\u0006\u0010.\u001a\u00020\u0013R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\n\u00a8\u0006/"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/SearchElement;", "", "xPos", "", "yPos", "width", "height", "(FFFF)V", "animScrollHeight", "getHeight", "()F", "lastHeight", "scrollHeight", "searchBox", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/SearchBox;", "getWidth", "getXPos", "getYPos", "drawBox", "", "mouseX", "", "mouseY", "accentColor", "Ljava/awt/Color;", "drawPanel", "", "mX", "mY", "x", "y", "w", "h", "wheel", "ces", "Ljava/util/List;", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/CategoryElement;", "drawScroll", "handleMouseClick", "mouseButton", "handleMouseRelease", "handleScrolling", "handleTyping", "typedChar", "", "keyCode", "isTyping", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/SearchElement.class */
public final class SearchElement {
    private float scrollHeight;
    private float animScrollHeight;
    private float lastHeight;
    private final SearchBox searchBox;
    private final float xPos;
    private final float yPos;
    private final float width;
    private final float height;

    public final float getXPos() {
        return this.xPos;
    }

    public final float getYPos() {
        return this.yPos;
    }

    public final float getWidth() {
        return this.width;
    }

    public final float getHeight() {
        return this.height;
    }

    public SearchElement(float f, float f2, float f3, float f4) {
        this.xPos = f;
        this.yPos = f2;
        this.width = f3;
        this.height = f4;
        this.searchBox = new SearchBox(0, ((int) this.xPos) + 2, ((int) this.yPos) + 2, ((int) this.width) - 4, ((int) this.height) - 2);
    }

    public final boolean drawBox(int i, int i2, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        RenderUtils.drawRoundedRect(this.xPos - 0.5f, this.yPos - 0.5f, this.xPos + this.width + 0.5f, this.yPos + this.height + 0.5f, 4.0f, ColorManager.INSTANCE.getButtonOutline().getRGB());
        Stencil.write(true);
        RenderUtils.drawRoundedRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, 4.0f, ColorManager.INSTANCE.getTextBox().getRGB());
        Stencil.erase(true);
        if (this.searchBox.func_146206_l()) {
            RenderUtils.drawRect(this.xPos, (this.yPos + this.height) - 1.0f, this.xPos + this.width, this.yPos + this.height, accentColor.getRGB());
            this.searchBox.func_146194_f();
        } else if (this.searchBox.func_146179_b().length() <= 0) {
            this.searchBox.func_146180_a("Search");
            this.searchBox.func_146194_f();
            this.searchBox.func_146180_a("");
        } else {
            this.searchBox.func_146194_f();
        }
        Stencil.dispose();
        GlStateManager.func_179118_c();
        RenderUtils.drawImage(IconManager.INSTANCE.getSearch(), (int) ((this.xPos + this.width) - 15.0f), (int) (this.yPos + 5.0f), 10, 10);
        GlStateManager.func_179141_d();
        return this.searchBox.func_146179_b().length() > 0;
    }

    public final void drawPanel(int i, int i2, float f, float f2, float f3, float f4, int i3, @NotNull List ces, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(ces, "ces");
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        int i4 = i2;
        this.lastHeight = 0.0f;
        Iterator it = ces.iterator();
        while (it.hasNext()) {
            for (ModuleElement moduleElement : ((CategoryElement) it.next()).getModuleElements()) {
                String name = moduleElement.getModule().getName();
                String strFunc_146179_b = this.searchBox.func_146179_b();
                Intrinsics.checkExpressionValueIsNotNull(strFunc_146179_b, "searchBox.text");
                if (StringsKt.startsWith(name, strFunc_146179_b, true)) {
                    this.lastHeight += moduleElement.getAnimHeight() + 40.0f;
                }
            }
        }
        if (this.lastHeight >= 10.0f) {
            this.lastHeight -= 10.0f;
        }
        handleScrolling(i3, f4);
        drawScroll(f, f2 + 50.0f, f3, f4);
        Fonts.font35.drawString("Search", f + 10.0f, f2 + 10.0f, -1);
        Fonts.font20.drawString("Search", f - 170.0f, f2 - 12.0f, -1);
        RenderUtils.drawImage(IconManager.INSTANCE.getBack(), (int) (f - 190.0f), (int) (f2 - 15.0f), 10, 10);
        float animHeight = f2 + 50.0f;
        if (i4 < f2 + 50.0f || i4 >= f2 + f4) {
            i4 = -1;
        }
        RenderUtils.makeScissorBox(f, f2 + 50.0f, f + f3, f2 + f4);
        GL11.glEnable(SGL.GL_SCISSOR_TEST);
        Iterator it2 = ces.iterator();
        while (it2.hasNext()) {
            for (ModuleElement moduleElement2 : ((CategoryElement) it2.next()).getModuleElements()) {
                String name2 = moduleElement2.getModule().getName();
                String strFunc_146179_b2 = this.searchBox.func_146179_b();
                Intrinsics.checkExpressionValueIsNotNull(strFunc_146179_b2, "searchBox.text");
                if (StringsKt.startsWith(name2, strFunc_146179_b2, true)) {
                    if (animHeight + this.animScrollHeight <= f2 + f4 && animHeight + this.animScrollHeight + 40.0f + moduleElement2.getAnimHeight() >= f2 + 50.0f) {
                        animHeight += moduleElement2.drawElement(i, i4, f, animHeight + this.animScrollHeight, f3, 40.0f, accentColor);
                    } else {
                        animHeight += 40.0f + moduleElement2.getAnimHeight();
                    }
                }
            }
        }
        GL11.glDisable(SGL.GL_SCISSOR_TEST);
    }

    private final void handleScrolling(int i, float f) {
        if (i != 0) {
            if (i > 0) {
                this.scrollHeight += 50.0f;
            } else {
                this.scrollHeight -= 50.0f;
            }
        }
        if (this.lastHeight > f - 60.0f) {
            this.scrollHeight = RangesKt.coerceIn(this.scrollHeight, ((-this.lastHeight) + f) - 60.0f, 0.0f);
        } else {
            this.scrollHeight = 0.0f;
        }
        this.animScrollHeight = AnimHelperKt.animSmooth(this.animScrollHeight, this.scrollHeight, 0.5f);
    }

    private final void drawScroll(float f, float f2, float f3, float f4) {
        if (this.lastHeight > f4 - 60.0f) {
            float fCoerceIn = ((f4 - 60.0f) - ((f4 - 60.0f) * ((f4 - 60.0f) / this.lastHeight))) * RangesKt.coerceIn(Math.abs(this.animScrollHeight / (((-this.lastHeight) + f4) - 60.0f)), 0.0f, 1.0f);
            RenderUtils.drawRoundedRect((f + f3) - 6.0f, f2 + 5.0f + fCoerceIn, (f + f3) - 4.0f, f2 + 5.0f + ((f4 - 60.0f) * ((f4 - 60.0f) / this.lastHeight)) + fCoerceIn, 1.0f, 1358954495);
        }
    }

    public final void handleMouseClick(int i, int i2, int i3, float f, float f2, float f3, float f4, @NotNull List ces) {
        Intrinsics.checkParameterIsNotNull(ces, "ces");
        if (((float) i) >= f - 200.0f && ((float) i) < f - 170.0f && ((float) i2) >= f2 - 20.0f && ((float) i2) < f2) {
            this.searchBox.func_146180_a("");
            return;
        }
        int i4 = i2;
        this.searchBox.func_146192_a(i, i4, i3);
        if (this.searchBox.func_146179_b().length() <= 0) {
            return;
        }
        if (i4 < f2 + 50.0f || i4 >= f2 + f4) {
            i4 = -1;
        }
        float animHeight = f2 + 50.0f;
        Iterator it = ces.iterator();
        while (it.hasNext()) {
            for (ModuleElement moduleElement : ((CategoryElement) it.next()).getModuleElements()) {
                String name = moduleElement.getModule().getName();
                String strFunc_146179_b = this.searchBox.func_146179_b();
                Intrinsics.checkExpressionValueIsNotNull(strFunc_146179_b, "searchBox.text");
                if (StringsKt.startsWith(name, strFunc_146179_b, true)) {
                    moduleElement.handleClick(i, i4, f, animHeight + this.animScrollHeight, f3, 40.0f);
                    animHeight += 40.0f + moduleElement.getAnimHeight();
                }
            }
        }
    }

    public final void handleMouseRelease(int i, int i2, int i3, float f, float f2, float f3, float f4, @NotNull List ces) {
        Intrinsics.checkParameterIsNotNull(ces, "ces");
        int i4 = i2;
        if (this.searchBox.func_146179_b().length() <= 0) {
            return;
        }
        if (i4 < f2 + 50.0f || i4 >= f2 + f4) {
            i4 = -1;
        }
        float animHeight = f2 + 50.0f;
        Iterator it = ces.iterator();
        while (it.hasNext()) {
            for (ModuleElement moduleElement : ((CategoryElement) it.next()).getModuleElements()) {
                String name = moduleElement.getModule().getName();
                String strFunc_146179_b = this.searchBox.func_146179_b();
                Intrinsics.checkExpressionValueIsNotNull(strFunc_146179_b, "searchBox.text");
                if (StringsKt.startsWith(name, strFunc_146179_b, true)) {
                    moduleElement.handleRelease(i, i4, f, animHeight + this.animScrollHeight, f3, 40.0f);
                    animHeight += 40.0f + moduleElement.getAnimHeight();
                }
            }
        }
    }

    public final boolean handleTyping(char c, int i, float f, float f2, float f3, float f4, @NotNull List ces) {
        Intrinsics.checkParameterIsNotNull(ces, "ces");
        this.searchBox.func_146201_a(c, i);
        if (this.searchBox.func_146179_b().length() <= 0) {
            return false;
        }
        Iterator it = ces.iterator();
        while (it.hasNext()) {
            for (ModuleElement moduleElement : ((CategoryElement) it.next()).getModuleElements()) {
                String name = moduleElement.getModule().getName();
                String strFunc_146179_b = this.searchBox.func_146179_b();
                Intrinsics.checkExpressionValueIsNotNull(strFunc_146179_b, "searchBox.text");
                if (StringsKt.startsWith(name, strFunc_146179_b, true) && moduleElement.handleKeyTyped(c, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isTyping() {
        return this.searchBox.func_146179_b().length() > 0;
    }
}

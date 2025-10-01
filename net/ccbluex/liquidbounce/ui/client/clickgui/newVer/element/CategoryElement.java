package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.ColorManager;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.ModuleElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdT\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J6\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006JF\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020(J(\u0010)\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006H\u0002J\u0016\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001dJ>\u0010.\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u001d2\u0006\u0010/\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006J>\u00100\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u001d2\u0006\u0010/\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006J\u0018\u00101\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u00062"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/CategoryElement;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "(Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;)V", "animScrollHeight", "", "getCategory", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "focused", "", "getFocused", "()Z", "setFocused", "(Z)V", "lastHeight", "moduleElements", "", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/module/ModuleElement;", "getModuleElements", "()Ljava/util/List;", "name", "", "getName", "()Ljava/lang/String;", "scrollHeight", "drawLabel", "", "mouseX", "", "mouseY", "x", "y", "width", "height", "drawPanel", "mX", "mY", "wheel", "accentColor", "Ljava/awt/Color;", "drawScroll", "handleKeyTyped", "keyTyped", "", "keyCode", "handleMouseClick", "mouseButton", "handleMouseRelease", "handleScrolling", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/CategoryElement.class */
public final class CategoryElement extends MinecraftInstance {

    @NotNull
    private final String name;
    private boolean focused;
    private float scrollHeight;
    private float animScrollHeight;
    private float lastHeight;

    @NotNull
    private final List moduleElements;

    @NotNull
    private final ModuleCategory category;

    @NotNull
    public final ModuleCategory getCategory() {
        return this.category;
    }

    public CategoryElement(@NotNull ModuleCategory category) {
        Intrinsics.checkParameterIsNotNull(category, "category");
        this.category = category;
        this.name = this.category.getDisplayName();
        this.moduleElements = new ArrayList();
        TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
        ArrayList arrayList = new ArrayList();
        for (Object obj : modules) {
            if (((Module) obj).getCategory() == this.category) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.moduleElements.add(new ModuleElement((Module) it.next()));
        }
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final boolean getFocused() {
        return this.focused;
    }

    public final void setFocused(boolean z) {
        this.focused = z;
    }

    @NotNull
    public final List getModuleElements() {
        return this.moduleElements;
    }

    public final void drawLabel(int i, int i2, float f, float f2, float f3, float f4) {
        if (this.focused) {
            RenderUtils.drawRoundedRect(f + 3.0f, f2 + 3.0f, (f + f3) - 3.0f, (f2 + f4) - 3.0f, 3.0f, ColorManager.INSTANCE.getDropDown().getRGB());
        } else {
            if (((float) i) >= f && ((float) i) < f + f3 && ((float) i2) >= f2 && ((float) i2) < f2 + f4) {
                RenderUtils.drawRoundedRect(f + 3.0f, f2 + 3.0f, (f + f3) - 3.0f, (f2 + f4) - 3.0f, 3.0f, ColorManager.INSTANCE.getBorder().getRGB());
            }
        }
        Fonts.font40.drawString(this.name, f + 10.0f, ((f2 + (f4 / 2.0f)) - (Fonts.font40.getFontHeight() / 2.0f)) + 2.0f, -1);
    }

    public final void drawPanel(int i, int i2, float f, float f2, float f3, float f4, int i3, @NotNull Color accentColor) {
        Intrinsics.checkParameterIsNotNull(accentColor, "accentColor");
        int i4 = i2;
        this.lastHeight = 0.0f;
        Iterator it = this.moduleElements.iterator();
        while (it.hasNext()) {
            this.lastHeight += 40.0f + ((ModuleElement) it.next()).getAnimHeight();
        }
        if (this.lastHeight >= 10.0f) {
            this.lastHeight -= 10.0f;
        }
        handleScrolling(i3, f4);
        drawScroll(f, f2 + 50.0f, f3, f4);
        Fonts.font35.drawString(ChatFormatting.GRAY + "Modules > " + ChatFormatting.RESET + this.name, f + 10.0f, f2 + 10.0f, -1);
        Fonts.font20.drawString(String.valueOf(this.name), f - 190.0f, f2 - 12.0f, -1);
        if (i4 < f2 + 50.0f || i4 >= f2 + f4) {
            i4 = -1;
        }
        RenderUtils.makeScissorBox(f, f2 + 50.0f, f + f3, f2 + f4);
        GL11.glEnable(SGL.GL_SCISSOR_TEST);
        float animHeight = f2 + 50.0f;
        for (ModuleElement moduleElement : this.moduleElements) {
            if (animHeight + this.animScrollHeight <= f2 + f4 && animHeight + this.animScrollHeight + 40.0f + moduleElement.getAnimHeight() >= f2 + 50.0f) {
                animHeight += moduleElement.drawElement(i, i4, f, animHeight + this.animScrollHeight, f3, 40.0f, accentColor);
            } else {
                animHeight += 40.0f + moduleElement.getAnimHeight();
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

    public final void handleMouseClick(int i, int i2, int i3, float f, float f2, float f3, float f4) {
        int i4 = i2;
        if (i4 < f2 + 50.0f || i4 >= f2 + f4) {
            i4 = -1;
        }
        float animHeight = f2 + 50.0f;
        if (i3 == 0) {
            for (ModuleElement moduleElement : this.moduleElements) {
                moduleElement.handleClick(i, i4, f, animHeight + this.animScrollHeight, f3, 40.0f);
                animHeight += 40.0f + moduleElement.getAnimHeight();
            }
        }
    }

    public final void handleMouseRelease(int i, int i2, int i3, float f, float f2, float f3, float f4) {
        int i4 = i2;
        if (i4 < f2 + 50.0f || i4 >= f2 + f4) {
            i4 = -1;
        }
        float animHeight = f2 + 50.0f;
        if (i3 == 0) {
            for (ModuleElement moduleElement : this.moduleElements) {
                moduleElement.handleRelease(i, i4, f, animHeight + this.animScrollHeight, f3, 40.0f);
                animHeight += 40.0f + moduleElement.getAnimHeight();
            }
        }
    }

    public final boolean handleKeyTyped(char c, int i) {
        Iterator it = this.moduleElements.iterator();
        while (it.hasNext()) {
            if (((ModuleElement) it.next()).handleKeyTyped(c, i)) {
                return true;
            }
        }
        return false;
    }
}

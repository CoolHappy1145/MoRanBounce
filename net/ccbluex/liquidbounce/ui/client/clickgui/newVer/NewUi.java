package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.render.NewGUI;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.CategoryElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.SearchElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element.module.ModuleElement;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/NewUi.class */
public class NewUi extends GuiScreen {
    private static NewUi instance;
    private SearchElement searchElement;
    public final List categoryElements = new ArrayList();
    private float startYAnim = this.field_146295_m / 2.0f;
    private float endYAnim = this.field_146295_m / 2.0f;
    private float fading = 0.0f;

    public static final NewUi getInstance() {
        if (instance != null) {
            return instance;
        }
        NewUi newUi = new NewUi();
        instance = newUi;
        return newUi;
    }

    public static void resetInstance() {
        instance = new NewUi();
    }

    private NewUi() {
        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            this.categoryElements.add(new CategoryElement(moduleCategory));
        }
        ((CategoryElement) this.categoryElements.get(0)).setFocused(true);
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        Iterator it = this.categoryElements.iterator();
        while (it.hasNext()) {
            for (ModuleElement moduleElement : ((CategoryElement) it.next()).getModuleElements()) {
                if (moduleElement.listeningKeybind()) {
                    moduleElement.resetState();
                }
            }
        }
        this.searchElement = new SearchElement(40.0f, 115.0f, 180.0f, 20.0f);
        super.func_73866_w_();
    }

    public void func_146281_b() {
        for (CategoryElement categoryElement : this.categoryElements) {
            if (categoryElement.getFocused()) {
                categoryElement.handleMouseRelease(-1, -1, 0, 0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        Keyboard.enableRepeatEvents(false);
    }

    public void func_73863_a(int i, int i2, float f) {
        drawFullSized(i, i2, f, NewGUI.getAccentColor());
    }

    private void drawFullSized(int i, int i2, float f, Color color) {
        RenderUtils.drawRoundedRect(30.0f, 30.0f, this.field_146294_l - 30.0f, this.field_146295_m - 30.0f, 8.0f, -15724528);
        if (((float) i) >= ((float) this.field_146294_l) - 54.0f && ((float) i) < ((float) this.field_146294_l) - 30.0f && ((float) i2) >= 30.0f && ((float) i2) < 50.0f) {
            this.fading += 0.2f * RenderUtils.deltaTime * 0.045f;
        } else {
            this.fading -= (0.2f * RenderUtils.deltaTime) * 0.045f;
        }
        this.fading = MathHelper.func_76131_a(this.fading, 0.0f, 1.0f);
        RenderUtils.customRounded(this.field_146294_l - 54.0f, 30.0f, this.field_146294_l - 30.0f, 50.0f, 0.0f, 8.0f, 0.0f, 8.0f, new Color(1.0f, 0.0f, 0.0f, this.fading).getRGB());
        GlStateManager.func_179118_c();
        RenderUtils.drawImage(IconManager.removeIcon, this.field_146294_l - 47, 35, 10, 10);
        GlStateManager.func_179141_d();
        Stencil.write(true);
        RenderUtils.drawFilledCircle(65, 80, 25.0f, new Color(45, 45, 45));
        Stencil.erase(true);
        Stencil.dispose();
        if (Fonts.font35.getStringWidth(this.field_146297_k.field_71439_g.func_146103_bH().getName()) > 70) {
            Fonts.font35.drawString(Fonts.font35.getStringWidth(this.field_146297_k.field_71439_g.func_146103_bH().getName()) + "...", 100, (78 - Fonts.font35.getFontHeight()) + 15, -1);
        } else {
            Fonts.font35.drawString(this.field_146297_k.field_71439_g.func_146103_bH().getName(), 100, (78 - Fonts.font35.getFontHeight()) + 15, -1);
        }
        if (this.searchElement.drawBox(i, i2, color)) {
            this.searchElement.drawPanel(i, i2, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80, Mouse.getDWheel(), this.categoryElements, color);
            return;
        }
        float f2 = 140.0f;
        for (CategoryElement categoryElement : this.categoryElements) {
            categoryElement.drawLabel(i, i2, 30.0f, f2, 200.0f, 24.0f);
            if (categoryElement.getFocused()) {
                this.startYAnim = ((Boolean) NewGUI.fastRenderValue.get()).booleanValue() ? f2 + 6.0f : AnimationUtils.animate(f2 + 6.0f, this.startYAnim, (this.startYAnim - (f2 + 5.0f) > 0.0f ? 0.65f : 0.55f) * RenderUtils.deltaTime * 0.025f);
                this.endYAnim = ((Boolean) NewGUI.fastRenderValue.get()).booleanValue() ? (f2 + 24.0f) - 6.0f : AnimationUtils.animate((f2 + 24.0f) - 6.0f, this.endYAnim, (this.endYAnim - ((f2 + 24.0f) - 5.0f) < 0.0f ? 0.65f : 0.55f) * RenderUtils.deltaTime * 0.025f);
                categoryElement.drawPanel(i, i2, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80, Mouse.getDWheel(), color);
            }
            f2 += 24.0f;
        }
        RenderUtils.drawRoundedRect(32.0f, this.startYAnim, 34.0f, this.endYAnim, 1.0f, color.getRGB());
        super.func_73863_a(i, i2, f);
    }

    protected void func_73864_a(int i, int i2, int i3) {
        if (((float) i) >= ((float) this.field_146294_l) - 54.0f && ((float) i) < ((float) this.field_146294_l) - 30.0f && ((float) i2) >= 30.0f && ((float) i2) < 50.0f) {
            this.field_146297_k.func_147108_a((GuiScreen) null);
            return;
        }
        float f = 140.0f;
        this.searchElement.handleMouseClick(i, i2, i3, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80, this.categoryElements);
        if (!this.searchElement.isTyping()) {
            for (CategoryElement categoryElement : this.categoryElements) {
                if (categoryElement.getFocused()) {
                    categoryElement.handleMouseClick(i, i2, i3, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80);
                }
                if ((((float) i) >= 30.0f && ((float) i) < 230.0f && ((float) i2) >= f && ((float) i2) < f + 24.0f) && !this.searchElement.isTyping()) {
                    this.categoryElements.forEach(NewUi::lambda$mouseClicked$0);
                    categoryElement.setFocused(true);
                    return;
                }
                f += 24.0f;
            }
        }
    }

    private static void lambda$mouseClicked$0(CategoryElement categoryElement) {
        categoryElement.setFocused(false);
    }

    protected void func_73869_a(char c, int i) {
        for (CategoryElement categoryElement : this.categoryElements) {
            if (categoryElement.getFocused() && categoryElement.handleKeyTyped(c, i)) {
                return;
            }
        }
        if (this.searchElement.handleTyping(c, i, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80, this.categoryElements)) {
            return;
        }
        super.func_73869_a(c, i);
    }

    protected void func_146286_b(int i, int i2, int i3) {
        this.searchElement.handleMouseRelease(i, i2, i3, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80, this.categoryElements);
        if (!this.searchElement.isTyping()) {
            for (CategoryElement categoryElement : this.categoryElements) {
                if (categoryElement.getFocused()) {
                    categoryElement.handleMouseRelease(i, i2, i3, 230.0f, 50.0f, this.field_146294_l - CharacterType.f90D, this.field_146295_m - 80);
                }
            }
        }
        super.func_146286_b(i, i2, i3);
    }
}

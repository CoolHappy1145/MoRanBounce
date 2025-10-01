package net.ccbluex.liquidbounce.p005ui.client.clickgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.p005ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/ClickGui.class */
public class ClickGui extends WrappedGuiScreen {
    public final List panels = new ArrayList();
    private final IResourceLocation hudIcon = classProvider.createResourceLocation("liquidbounce/custom_hud_icon.png");
    public Style style = new SlowlyStyle();
    private Panel clickedPanel;
    private int mouseX;
    private int mouseY;

    public ClickGui() {
        int i = 5;
        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            this.panels.add(new Panel(this, moduleCategory.getDisplayName(), 100, i, 100, 18, false, moduleCategory) { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.1
                final ModuleCategory val$category;
                final ClickGui this$0;

                {
                    this.this$0 = this;
                    this.val$category = moduleCategory;
                }

                @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.Panel
                public void setupItems() {
                    Iterator it = LiquidBounce.moduleManager.getModules().iterator();
                    while (it.hasNext()) {
                        Module module = (Module) it.next();
                        if (module.getCategory() == this.val$category) {
                            getElements().add(new ModuleElement(module));
                        }
                    }
                }
            });
            i += 20;
        }
        this.panels.add(new Panel(this, "Targets", 100, i + 20, 100, 18, false) { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2
            final ClickGui this$0;

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.Panel
            public void setupItems() {
                getElements().add(new ButtonElement(this, "Players") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.1
                    final C04742 this$1;

                    {
                        this.this$1 = this;
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public void createButton(String str) {
                        this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(str);
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Players";
                        this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int i2, int i3, int i4) {
                        if (i4 == 0 && isHovering(i2, i3) && isVisible()) {
                            EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                            this.displayName = "Players";
                            this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                    }
                });
                getElements().add(new ButtonElement(this, "Mobs") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.2
                    final C04742 this$1;

                    {
                        this.this$1 = this;
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public void createButton(String str) {
                        this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(str);
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Mobs";
                        this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int i2, int i3, int i4) {
                        if (i4 == 0 && isHovering(i2, i3) && isVisible()) {
                            EntityUtils.targetMobs = !EntityUtils.targetMobs;
                            this.displayName = "Mobs";
                            this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                    }
                });
                getElements().add(new ButtonElement(this, "Animals") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.3
                    final C04742 this$1;

                    {
                        this.this$1 = this;
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public void createButton(String str) {
                        this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(str);
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Animals";
                        this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int i2, int i3, int i4) {
                        if (i4 == 0 && isHovering(i2, i3) && isVisible()) {
                            EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                            this.displayName = "Animals";
                            this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                    }
                });
                getElements().add(new ButtonElement(this, "Invisible") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.4
                    final C04742 this$1;

                    {
                        this.this$1 = this;
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public void createButton(String str) {
                        this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(str);
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Invisible";
                        this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int i2, int i3, int i4) {
                        if (i4 == 0 && isHovering(i2, i3) && isVisible()) {
                            EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                            this.displayName = "Invisible";
                            this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                    }
                });
                getElements().add(new ButtonElement(this, "Dead") { // from class: net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui.2.5
                    final C04742 this$1;

                    {
                        this.this$1 = this;
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public void createButton(String str) {
                        this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(str);
                    }

                    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
                    public String getDisplayName() {
                        this.displayName = "Dead";
                        this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int i2, int i3, int i4) {
                        if (i4 == 0 && isHovering(i2, i3) && isVisible()) {
                            EntityUtils.targetDead = !EntityUtils.targetDead;
                            this.displayName = "Dead";
                            this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                    }
                });
            }
        });
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        if (Mouse.isButtonDown(0) && i >= 5 && i <= 50 && i2 <= this.representedScreen.getHeight() - 5 && i2 >= this.representedScreen.getHeight() - 50) {
            f157mc.displayGuiScreen(classProvider.wrapGuiScreen(new GuiHudDesigner()));
        }
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        double dFloatValue = ((Float) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get()).floatValue();
        int i3 = (int) (i / dFloatValue);
        int i4 = (int) (i2 / dFloatValue);
        this.mouseX = i3;
        this.mouseY = i4;
        this.representedScreen.drawDefaultBackground();
        RenderUtils.drawImage(this.hudIcon, 9, this.representedScreen.getHeight() - 41, 32, 32);
        GL11.glScaled(dFloatValue, dFloatValue, dFloatValue);
        for (Panel panel : this.panels) {
            panel.updateFade(RenderUtils.deltaTime);
            panel.drawScreen(i3, i4, f);
        }
        for (Panel panel2 : this.panels) {
            for (Element element : panel2.getElements()) {
                if (element instanceof ModuleElement) {
                    ModuleElement moduleElement = (ModuleElement) element;
                    if (i3 != 0 && i4 != 0 && moduleElement.isHovering(i3, i4) && moduleElement.isVisible() && element.getY() <= panel2.getY() + panel2.getFade()) {
                        this.style.drawDescription(i3, i4, moduleElement.getModule().getDescription());
                    }
                }
            }
        }
        if (Mouse.hasWheel()) {
            int dWheel = Mouse.getDWheel();
            for (int size = this.panels.size() - 1; size >= 0 && !((Panel) this.panels.get(size)).handleScroll(i3, i4, dWheel); size--) {
            }
        }
        classProvider.getGlStateManager().disableLighting();
        functions.disableStandardItemLighting();
        GL11.glScaled(1.0d, 1.0d, 1.0d);
        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        super.drawScreen(i3, i4, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        double dFloatValue = ((Float) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get()).floatValue();
        int i4 = (int) (i / dFloatValue);
        int i5 = (int) (i2 / dFloatValue);
        for (Panel panel : this.panels) {
            panel.mouseClicked(i4, i5, i3);
            panel.drag = false;
            if (i3 == 0 && panel.isHovering(i4, i5)) {
                this.clickedPanel = panel;
            }
        }
        if (this.clickedPanel != null) {
            this.clickedPanel.f135x2 = this.clickedPanel.f133x - i4;
            this.clickedPanel.f136y2 = this.clickedPanel.f134y - i5;
            this.clickedPanel.drag = true;
            this.panels.remove(this.clickedPanel);
            this.panels.add(this.clickedPanel);
            this.clickedPanel = null;
        }
        super.mouseClicked(i4, i5, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseReleased(int i, int i2, int i3) {
        double dFloatValue = ((Float) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get()).floatValue();
        int i4 = (int) (i / dFloatValue);
        int i5 = (int) (i2 / dFloatValue);
        Iterator it = this.panels.iterator();
        while (it.hasNext()) {
            ((Panel) it.next()).mouseReleased(i4, i5, i3);
        }
    }

    public void updateScreen() {
        Iterator it = this.panels.iterator();
        while (it.hasNext()) {
            for (Element element : ((Panel) it.next()).getElements()) {
                if (element instanceof ButtonElement) {
                    ButtonElement buttonElement = (ButtonElement) element;
                    if (buttonElement.isHovering(this.mouseX, this.mouseY)) {
                        if (buttonElement.hoverTime < 7) {
                            buttonElement.hoverTime++;
                        }
                    } else if (buttonElement.hoverTime > 0) {
                        buttonElement.hoverTime--;
                    }
                }
                if (element instanceof ModuleElement) {
                    if (((ModuleElement) element).getModule().getState()) {
                        if (((ModuleElement) element).slowlyFade < 255) {
                            ((ModuleElement) element).slowlyFade += 20;
                        }
                    } else if (((ModuleElement) element).slowlyFade > 0) {
                        ((ModuleElement) element).slowlyFade -= 20;
                    }
                    if (((ModuleElement) element).slowlyFade > 255) {
                        ((ModuleElement) element).slowlyFade = 255;
                    }
                    if (((ModuleElement) element).slowlyFade < 0) {
                        ((ModuleElement) element).slowlyFade = 0;
                    }
                }
            }
        }
    }

    public void onGuiClosed() {
        LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.clickGuiConfig);
    }
}

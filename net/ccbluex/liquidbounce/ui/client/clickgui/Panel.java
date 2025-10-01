package net.ccbluex.liquidbounce.p005ui.client.clickgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/Panel.class */
public abstract class Panel extends MinecraftInstance {
    private final String name;

    /* renamed from: x */
    public int f133x;

    /* renamed from: y */
    public int f134y;

    /* renamed from: x2 */
    public int f135x2;

    /* renamed from: y2 */
    public int f136y2;
    private final int width;
    private final int height;
    private int scroll;
    private int dragged;
    private boolean open;
    public boolean drag;
    private float elementsHeight;
    private float fade;
    private final List<Element> elements = new ArrayList();
    private boolean scrollbar = false;
    private boolean visible = true;

    public abstract void setupItems();

    public Panel(String name, int x, int y, int width, int height, boolean open) {
        this.name = name;
        this.f133x = x;
        this.f134y = y;
        this.width = width;
        this.height = height;
        this.open = open;
        setupItems();
    }

    public void drawScreen(int mouseX, int mouseY, float button) {
        if (!this.visible) {
            return;
        }
        int maxElements = ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue();
        if (this.drag) {
            int nx = this.f135x2 + mouseX;
            int ny = this.f136y2 + mouseY;
            if (nx > -1) {
                this.f133x = nx;
            }
            if (ny > -1) {
                this.f134y = ny;
            }
        }
        this.elementsHeight = getElementsHeight() - 1;
        boolean scrollbar = this.elements.size() >= maxElements;
        if (this.scrollbar != scrollbar) {
            this.scrollbar = scrollbar;
        }
        LiquidBounce.clickGui.style.drawPanel(mouseX, mouseY, this);
        int y = (this.f134y + this.height) - 2;
        int count = 0;
        for (Element element : this.elements) {
            count++;
            if (count > this.scroll && count < this.scroll + maxElements + 1 && this.scroll < this.elements.size()) {
                element.setLocation(this.f133x, y);
                element.setWidth(getWidth());
                if (y <= getY() + this.fade) {
                    element.drawScreen(mouseX, mouseY, button);
                }
                y += element.getHeight() + 1;
                element.setVisible(true);
            } else {
                element.setVisible(false);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible) {
            return;
        }
        if (mouseButton == 1 && isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            f157mc.getSoundHandler().playSound("entity.arrow.shoot", 1.0f);
            return;
        }
        for (Element element : this.elements) {
            if (element.getY() <= getY() + this.fade) {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return;
        }
        this.drag = false;
        if (!this.open) {
            return;
        }
        for (Element element : this.elements) {
            element.mouseReleased(mouseX, mouseY, state);
        }
    }

    public boolean handleScroll(int mouseX, int mouseY, int wheel) {
        int maxElements = ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue();
        if (mouseX >= getX() && mouseX <= getX() + 100 && mouseY >= getY() && mouseY <= getY() + 19 + this.elementsHeight) {
            if (wheel < 0 && this.scroll < this.elements.size() - maxElements) {
                this.scroll++;
                if (this.scroll < 0) {
                    this.scroll = 0;
                }
            } else if (wheel > 0) {
                this.scroll--;
                if (this.scroll < 0) {
                    this.scroll = 0;
                }
            }
            if (wheel < 0) {
                if (this.dragged < this.elements.size() - maxElements) {
                    this.dragged++;
                    return true;
                }
                return true;
            }
            if (wheel > 0 && this.dragged >= 1) {
                this.dragged--;
                return true;
            }
            return true;
        }
        return false;
    }

    void updateFade(int delta) {
        if (this.open) {
            if (this.fade < this.elementsHeight) {
                this.fade += 0.4f * delta;
            }
            if (this.fade > this.elementsHeight) {
                this.fade = (int) this.elementsHeight;
                return;
            }
            return;
        }
        if (this.fade > 0.0f) {
            this.fade -= 0.4f * delta;
        }
        if (this.fade < 0.0f) {
            this.fade = 0.0f;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.f133x;
    }

    public int getY() {
        return this.f134y;
    }

    public void setX(int dragX) {
        this.f133x = dragX;
    }

    public void setY(int dragY) {
        this.f134y = dragY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getScrollbar() {
        return this.scrollbar;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public int getFade() {
        return (int) this.fade;
    }

    public int getDragged() {
        return this.dragged;
    }

    private int getElementsHeight() {
        int height = 0;
        int count = 0;
        for (Element element : this.elements) {
            if (count < ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue()) {
                height += element.getHeight() + 1;
                count++;
            }
        }
        return height;
    }

    boolean isHovering(int mouseX, int mouseY) {
        float textWidth = f157mc.getFontRendererObj().getStringWidth(StringUtils.func_76338_a(this.name)) - 100.0f;
        if (mouseX >= (this.f133x - (textWidth / 2.0f)) - 19.0f && mouseX <= (this.f133x - (textWidth / 2.0f)) + f157mc.getFontRendererObj().getStringWidth(StringUtils.func_76338_a(this.name)) + 19.0f && mouseY >= this.f134y) {
            if (mouseY <= (this.f134y + this.height) - (this.open ? 2 : 0)) {
                return true;
            }
        }
        return false;
    }
}

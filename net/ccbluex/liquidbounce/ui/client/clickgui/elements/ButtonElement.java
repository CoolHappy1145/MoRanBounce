package net.ccbluex.liquidbounce.p005ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/elements/ButtonElement.class */
public class ButtonElement extends Element {
    protected String displayName;
    protected int color = 16777215;
    public int hoverTime;

    public ButtonElement(String str) {
        createButton(str);
    }

    public void createButton(String str) {
        this.displayName = str;
    }

    public void drawScreen(int i, int i2, float f) {
        LiquidBounce.clickGui.style.drawButtonElement(i, i2, this);
    }

    public boolean isHovering(int i, int i2) {
        return i >= getX() && i <= getX() + getWidth() && i2 >= getY() && i2 <= getY() + 16;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public int getColor() {
        return this.color;
    }
}

package net.ccbluex.liquidbounce.p005ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/elements/Element.class */
public class Element extends MinecraftInstance {

    /* renamed from: x */
    private int f137x;

    /* renamed from: y */
    private int f138y;
    private int width;
    private int height;
    private boolean visible;

    public void setLocation(int i, int i2) {
        this.f137x = i;
        this.f138y = i2;
    }

    public int getX() {
        return this.f137x;
    }

    public void setX(int i) {
        this.f137x = i;
    }

    public int getY() {
        return this.f138y;
    }

    public void setY(int i) {
        this.f138y = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean z) {
        this.visible = z;
    }
}

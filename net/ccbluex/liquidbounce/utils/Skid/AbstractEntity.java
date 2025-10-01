package net.ccbluex.liquidbounce.utils.Skid;

import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/AbstractEntity.class */
public abstract class AbstractEntity extends MinecraftInstance {
    private float positionX;
    private float positionY;
    private float prevDragX = 0.0f;
    private float prevDragY = 0.0f;
    private long prevClickTime = System.nanoTime();
    private boolean mouseClicked = false;
    private boolean valueLoaded = false;
    private boolean rightMouseClicked = false;
    public boolean isDragging = false;
    public boolean isHovering = false;
    private float scale = 1.0f;

    protected abstract Border drawElement();

    public AbstractEntity(float f, float f2) {
        this.positionX = f;
        this.positionY = f2;
    }

    public final float getPositionX() {
        return this.positionX;
    }

    public final float getPositionY() {
        return this.positionY;
    }

    public final void setPositionX(float f) {
        this.positionX = f;
    }

    public final void setPositionY(float f) {
        this.positionY = f;
    }

    public final long getPrevClickTime() {
        return this.prevClickTime;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }
}

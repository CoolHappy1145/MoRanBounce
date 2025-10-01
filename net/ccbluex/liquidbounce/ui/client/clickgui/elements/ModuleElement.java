package net.ccbluex.liquidbounce.p005ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/elements/ModuleElement.class */
public class ModuleElement extends ButtonElement {
    private final Module module;
    private boolean showSettings;
    private float settingsWidth;
    private boolean wasPressed;
    public int slowlySettingsYPos;
    public int slowlyFade;

    public ModuleElement(Module module) {
        super(null);
        this.settingsWidth = 0.0f;
        this.displayName = module.getName();
        this.module = module;
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement
    public void drawScreen(int i, int i2, float f) {
        LiquidBounce.clickGui.style.drawModuleElement(i, i2, this);
    }

    public void mouseClicked(int i, int i2, int i3) {
        if (i3 == 0 && isHovering(i, i2) && isVisible()) {
            this.module.toggle();
            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
        }
        if (i3 == 1 && isHovering(i, i2) && isVisible()) {
            this.showSettings = !this.showSettings;
            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
        }
    }

    public Module getModule() {
        return this.module;
    }

    public boolean isShowSettings() {
        return this.showSettings;
    }

    public void setShowSettings(boolean z) {
        this.showSettings = z;
    }

    public boolean isntPressed() {
        return !this.wasPressed;
    }

    public void updatePressed() {
        this.wasPressed = Mouse.isButtonDown(0);
    }

    public float getSettingsWidth() {
        return this.settingsWidth;
    }

    public void setSettingsWidth(float f) {
        this.settingsWidth = f;
    }
}

package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer;

import java.awt.Color;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0006\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/ColorManager;", "", "()V", "background", "Ljava/awt/Color;", "getBackground", "()Ljava/awt/Color;", "border", "getBorder", "button", "getButton", "buttonOutline", "getButtonOutline", "dropDown", "getDropDown", "moduleBackground", "getModuleBackground", "sliderButton", "getSliderButton", "textBox", "getTextBox", "unusedSlider", "getUnusedSlider", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/ColorManager.class */
public final class ColorManager {
    public static final ColorManager INSTANCE = new ColorManager();

    @NotNull
    private static final Color background = new Color(32, 32, 32);

    @NotNull
    private static final Color textBox = new Color(31, 31, 31);

    @NotNull
    private static final Color dropDown = new Color(45, 45, 45);

    @NotNull
    private static final Color button = new Color(52, 52, 52);

    @NotNull
    private static final Color moduleBackground = new Color(39, 39, 39);

    @NotNull
    private static final Color unusedSlider = new Color(154, 154, 154);

    @NotNull
    private static final Color sliderButton = new Color(69, 69, 69);

    @NotNull
    private static final Color border = new Color(25, 25, 25);

    @NotNull
    private static final Color buttonOutline = new Color(59, 59, 59);

    private ColorManager() {
    }

    @NotNull
    public final Color getBackground() {
        return background;
    }

    @NotNull
    public final Color getTextBox() {
        return textBox;
    }

    @NotNull
    public final Color getDropDown() {
        return dropDown;
    }

    @NotNull
    public final Color getButton() {
        return button;
    }

    @NotNull
    public final Color getModuleBackground() {
        return moduleBackground;
    }

    @NotNull
    public final Color getUnusedSlider() {
        return unusedSlider;
    }

    @NotNull
    public final Color getSliderButton() {
        return sliderButton;
    }

    @NotNull
    public final Color getBorder() {
        return border;
    }

    @NotNull
    public final Color getButtonOutline() {
        return buttonOutline;
    }
}

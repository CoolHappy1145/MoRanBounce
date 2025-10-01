package net.ccbluex.liquidbounce.p005ui.client.hud.designer;

import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J \u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0010H\u0016J\b\u0010\u001d\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "()V", "buttonAction", "", "editorPanel", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "selectedElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getSelectedElement", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "setSelectedElement", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;)V", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner.class */
public final class GuiHudDesigner extends WrappedGuiScreen {
    private EditorPanel editorPanel = new EditorPanel(this, 2, 2);

    @Nullable
    private Element selectedElement;
    private boolean buttonAction;

    @Nullable
    public final Element getSelectedElement() {
        return this.selectedElement;
    }

    public final void setSelectedElement(@Nullable Element element) {
        this.selectedElement = element;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.editorPanel = new EditorPanel(this, getRepresentedScreen().getWidth() / 2, getRepresentedScreen().getHeight() / 2);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        LiquidBounce.INSTANCE.getHud().render(true);
        LiquidBounce.INSTANCE.getHud().handleMouseMove(i, i2);
        if (!CollectionsKt.contains(LiquidBounce.INSTANCE.getHud().getElements(), this.selectedElement)) {
            this.selectedElement = (Element) null;
        }
        int dWheel = Mouse.getDWheel();
        this.editorPanel.drawPanel(i, i2, dWheel);
        if (dWheel != 0) {
            for (Element element : LiquidBounce.INSTANCE.getHud().getElements()) {
                if (element.isInBorder((i / element.getScale()) - element.getRenderX(), (i2 / element.getScale()) - element.getRenderY())) {
                    element.setScale(element.getScale() + (dWheel > 0 ? 0.05f : -0.05f));
                    return;
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        getRepresentedScreen().superMouseClicked(i, i2, i3);
        if (this.buttonAction) {
            this.buttonAction = false;
            return;
        }
        LiquidBounce.INSTANCE.getHud().handleMouseClick(i, i2, i3);
        if (i < this.editorPanel.getX() || i > this.editorPanel.getX() + this.editorPanel.getWidth() || i2 < this.editorPanel.getY() || i2 > this.editorPanel.getY() + Math.min(this.editorPanel.getRealHeight(), SharedScopeCall.FAST_SCOPE_GET_THRESHOLD)) {
            this.selectedElement = (Element) null;
            this.editorPanel.setCreate(false);
        }
        if (i3 == 0) {
            for (Element element : LiquidBounce.INSTANCE.getHud().getElements()) {
                if (element.isInBorder((i / element.getScale()) - element.getRenderX(), (i2 / element.getScale()) - element.getRenderY())) {
                    this.selectedElement = element;
                    return;
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseReleased(int i, int i2, int i3) {
        getRepresentedScreen().superMouseReleased(i, i2, i3);
        LiquidBounce.INSTANCE.getHud().handleMouseReleased();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        switch (i) {
            case 1:
                this.selectedElement = (Element) null;
                this.editorPanel.setCreate(false);
                break;
            case 211:
                if (211 == i && this.selectedElement != null) {
                    HUD hud = LiquidBounce.INSTANCE.getHud();
                    Element element = this.selectedElement;
                    if (element == null) {
                        Intrinsics.throwNpe();
                    }
                    hud.removeElement(element);
                    break;
                }
                break;
            default:
                LiquidBounce.INSTANCE.getHud().handleKey(c, i);
                break;
        }
        super.keyTyped(c, i);
    }
}

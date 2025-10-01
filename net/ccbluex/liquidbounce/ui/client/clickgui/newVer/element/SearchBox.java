package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer.element;

import kotlin.Metadata;
import kotlin.TypeCastException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.injection.backend.FontRendererImpl;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.minecraft.client.gui.GuiTextField;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/SearchBox;", "Lnet/minecraft/client/gui/GuiTextField;", "componentId", "", "x", "y", "width", "height", "(IIIII)V", "getEnableBackgroundDrawing", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/element/SearchBox.class */
public final class SearchBox extends GuiTextField {
    /* JADX WARN: Illegal instructions before constructor call */
    public SearchBox(int i, int i2, int i3, int i4, int i5) {
        IFontRenderer iFontRenderer = Fonts.font40;
        if (iFontRenderer == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.FontRendererImpl");
        }
        super(i, ((FontRendererImpl) iFontRenderer).getWrapped(), i2, i3, i4, i5);
    }
}

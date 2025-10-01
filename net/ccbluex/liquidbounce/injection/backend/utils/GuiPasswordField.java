package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/utils/GuiPasswordField;", "Lnet/minecraft/client/gui/GuiTextField;", "componentId", "", "fontrendererObj", "Lnet/minecraft/client/gui/FontRenderer;", "x", "y", "par5Width", "par6Height", "(ILnet/minecraft/client/gui/FontRenderer;IIII)V", "drawTextBox", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/GuiPasswordField.class */
public final class GuiPasswordField extends GuiTextField {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiPasswordField(int i, @NotNull FontRenderer fontrendererObj, int i2, int i3, int i4, int i5) {
        super(i, fontrendererObj, i2, i3, i4, i5);
        Intrinsics.checkParameterIsNotNull(fontrendererObj, "fontrendererObj");
    }

    public void func_146194_f() {
        String strFunc_146179_b = func_146179_b();
        StringBuilder sb = new StringBuilder();
        String text = func_146179_b();
        Intrinsics.checkExpressionValueIsNotNull(text, "text");
        int length = text.length();
        for (int i = 0; i < length; i++) {
            sb.append('*');
        }
        func_146180_a(sb.toString());
        super.func_146194_f();
        func_146180_a(strFunc_146179_b);
    }
}

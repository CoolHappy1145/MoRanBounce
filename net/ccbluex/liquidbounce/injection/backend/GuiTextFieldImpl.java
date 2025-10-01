package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\b\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0013\u0010\u001d\u001a\u00020\u00062\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\u0018\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000bH\u0016J \u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u000bH\u0016J\u0018\u0010(\u001a\u00020\u00062\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000bH\u0016J\b\u0010)\u001a\u00020\u001cH\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00118V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u000e\u00a8\u0006*"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GuiTextFieldImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "wrapped", "Lnet/minecraft/client/gui/GuiTextField;", "(Lnet/minecraft/client/gui/GuiTextField;)V", PropertyDescriptor.VALUE, "", "isFocused", "()Z", "setFocused", "(Z)V", "", "maxStringLength", "getMaxStringLength", "()I", "setMaxStringLength", "(I)V", "", "text", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "getWrapped", "()Lnet/minecraft/client/gui/GuiTextField;", "xPosition", "getXPosition", "drawTextBox", "", "equals", "other", "", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseX", "mouseY", "mouseButton", "textboxKeyTyped", "updateCursorCounter", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GuiTextFieldImpl.class */
public final class GuiTextFieldImpl implements IGuiTextField {

    @NotNull
    private final GuiTextField wrapped;

    @NotNull
    public final GuiTextField getWrapped() {
        return this.wrapped;
    }

    public GuiTextFieldImpl(@NotNull GuiTextField wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public int getXPosition() {
        return this.wrapped.field_146209_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    @NotNull
    public String getText() {
        String strFunc_146179_b = this.wrapped.func_146179_b();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_146179_b, "wrapped.text");
        return strFunc_146179_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public void setText(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.wrapped.func_146180_a(value);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public boolean isFocused() {
        return this.wrapped.func_146206_l();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public void setFocused(boolean z) {
        this.wrapped.func_146195_b(z);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public int getMaxStringLength() {
        return this.wrapped.func_146208_g();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public void setMaxStringLength(int i) {
        this.wrapped.func_146203_f(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public void updateCursorCounter() {
        this.wrapped.func_146178_a();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public boolean textboxKeyTyped(char c, int i) {
        return this.wrapped.func_146201_a(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public void drawTextBox() {
        this.wrapped.func_146194_f();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public void mouseClicked(int i, int i2, int i3) {
        this.wrapped.func_146192_a(i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField
    public boolean keyTyped(char c, int i) {
        return this.wrapped.func_146201_a(c, i);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof GuiTextFieldImpl) && Intrinsics.areEqual(((GuiTextFieldImpl) obj).wrapped, this.wrapped);
    }
}

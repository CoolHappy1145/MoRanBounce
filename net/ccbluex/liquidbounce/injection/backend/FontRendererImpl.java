package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.ccbluex.liquidbounce.injection.backend.utils.FontRendererWrapper;
import net.ccbluex.liquidbounce.p005ui.font.GameFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J0\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J(\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J0\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J(\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J(\u0010\u0016\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\u0013\u0010\u0017\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/FontRendererImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "wrapped", "Lnet/minecraft/client/gui/FontRenderer;", "(Lnet/minecraft/client/gui/FontRenderer;)V", "fontHeight", "", "getFontHeight", "()I", "getWrapped", "()Lnet/minecraft/client/gui/FontRenderer;", "drawCenteredString", "text", "", "x", "", "y", "color", "shadow", "", "drawString", AsmConstants.STR, "drawStringWithShadow", "equals", "other", "", "getGameFontRenderer", "Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "getStringWidth", "isGameFontRenderer", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/FontRendererImpl.class */
public final class FontRendererImpl implements IFontRenderer {

    @NotNull
    private final FontRenderer wrapped;

    @NotNull
    public final FontRenderer getWrapped() {
        return this.wrapped;
    }

    public FontRendererImpl(@NotNull FontRenderer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int getFontHeight() {
        return this.wrapped.field_78288_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int getStringWidth(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.func_78256_a(str);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int drawString(@NotNull String str, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.func_78276_b(str, i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int drawString(@NotNull String str, float f, float f2, int i) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.func_78276_b(str, (int) f, (int) f2, i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int drawString(@NotNull String str, float f, float f2, int i, boolean z) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.func_175065_a(str, f, f2, i, z);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int drawCenteredString(@NotNull String text, float f, float f2, int i) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return drawString(text, f - (getStringWidth(text) / 2.0f), f2, i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int drawCenteredString(@NotNull String text, float f, float f2, int i, boolean z) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return drawString(text, f - (getStringWidth(text) / 2.0f), f2, i, z);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public int drawStringWithShadow(@NotNull String text, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return this.wrapped.func_175063_a(text, i, i2, i3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    public boolean isGameFontRenderer() {
        return this.wrapped instanceof FontRendererWrapper;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer
    @NotNull
    public GameFontRenderer getGameFontRenderer() {
        FontRenderer fontRenderer = this.wrapped;
        if (fontRenderer == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.FontRendererWrapper");
        }
        IWrappedFontRenderer wrapped = ((FontRendererWrapper) fontRenderer).getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.ui.font.GameFontRenderer");
        }
        return (GameFontRenderer) wrapped;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof FontRendererImpl) && Intrinsics.areEqual(((FontRendererImpl) obj).wrapped, this.wrapped);
    }
}

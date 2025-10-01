package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J2\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0016J*\u0010\u0011\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0016\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/utils/FontRendererWrapper;", "Lnet/minecraft/client/gui/FontRenderer;", "wrapped", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "(Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;)V", "getWrapped", "()Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "drawString", "", "text", "", "x", "", "y", "color", "dropShadow", "", "drawStringWithShadow", "getCharWidth", "character", "", "getColorCode", "getStringWidth", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/FontRendererWrapper.class */
public final class FontRendererWrapper extends FontRenderer {

    @NotNull
    private final IWrappedFontRenderer wrapped;

    @NotNull
    public final IWrappedFontRenderer getWrapped() {
        return this.wrapped;
    }

    public FontRendererWrapper(@NotNull IWrappedFontRenderer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        GameSettings gameSettings = Minecraft.func_71410_x().field_71474_y;
        ResourceLocation resourceLocation = new ResourceLocation("textures/font/ascii.png");
        Minecraft minecraftFunc_71410_x = Minecraft.func_71410_x();
        Intrinsics.checkExpressionValueIsNotNull(minecraftFunc_71410_x, "Minecraft.getMinecraft()");
        super(gameSettings, resourceLocation, minecraftFunc_71410_x.func_110434_K(), false);
        this.wrapped = wrapped;
    }

    public int func_78276_b(@Nullable String str, int i, int i2, int i3) {
        return this.wrapped.drawString(str, i, i2, i3);
    }

    public int func_175065_a(@Nullable String str, float f, float f2, int i, boolean z) {
        return this.wrapped.drawString(str, f, f2, i, z);
    }

    public int func_175063_a(@Nullable String str, float f, float f2, int i) {
        return this.wrapped.drawStringWithShadow(str, f, f2, i);
    }

    public int func_175064_b(char c) {
        return this.wrapped.getColorCode(c);
    }

    public int func_78256_a(@Nullable String str) {
        return this.wrapped.getStringWidth(str);
    }

    public int func_78263_a(char c) {
        return this.wrapped.getCharWidth(c);
    }
}

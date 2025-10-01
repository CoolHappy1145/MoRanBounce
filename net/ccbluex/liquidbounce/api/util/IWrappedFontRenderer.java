package net.ccbluex.liquidbounce.api.util;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0004\bf\u0018\ufffd\ufffd2\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H&J0\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J*\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H&J2\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J*\u0010\u000e\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0011H&J\u0012\u0010\u0014\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u0005H&\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "", "drawCenteredString", "", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "getCharWidth", "character", "", "getColorCode", "charCode", "getStringWidth", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/IWrappedFontRenderer.class */
public interface IWrappedFontRenderer {
    int drawString(@Nullable String str, float f, float f2, int i);

    int drawStringWithShadow(@Nullable String str, float f, float f2, int i);

    int drawCenteredString(@NotNull String str, float f, float f2, int i, boolean z);

    int drawCenteredString(@NotNull String str, float f, float f2, int i);

    int drawString(@Nullable String str, float f, float f2, int i, boolean z);

    int getColorCode(char c);

    int getStringWidth(@Nullable String str);

    int getCharWidth(char c);
}

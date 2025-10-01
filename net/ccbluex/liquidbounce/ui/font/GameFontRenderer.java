package net.ccbluex.liquidbounce.p005ui.font;

import java.awt.Color;
import java.awt.Font;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/* compiled from: GameFontRenderer.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0005\u0018\ufffd\ufffd +2\u00020\u0001:\u0001+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J0\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J*\u0010\u001f\u001a\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J2\u0010\u001f\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J*\u0010!\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J<\u0010\"\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u001e2\b\b\u0002\u0010$\u001a\u00020\u001eH\u0002J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020'H\u0016J\u0012\u0010*\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u0018H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0014\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010\u00a8\u0006,"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "font", "Ljava/awt/Font;", "(Ljava/awt/Font;)V", "boldFont", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "boldItalicFont", "defaultFont", "getDefaultFont", "()Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "setDefaultFont", "(Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;)V", "fontHeight", "", "getFontHeight", "()I", "height", "getHeight", "italicFont", "size", "getSize", "drawCenteredString", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "drawText", "ignoreColor", "rainbow", "getCharWidth", "character", "", "getColorCode", "charCode", "getStringWidth", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/GameFontRenderer.class */
public final class GameFontRenderer implements IWrappedFontRenderer {
    private final int fontHeight;

    @NotNull
    private AWTFontRenderer defaultFont;
    private AWTFontRenderer boldFont;
    private AWTFontRenderer italicFont;
    private AWTFontRenderer boldItalicFont;
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final int getColorIndex(char type) {
        return Companion.getColorIndex(type);
    }

    public GameFontRenderer(@NotNull Font font) {
        Intrinsics.checkParameterIsNotNull(font, "font");
        this.defaultFont = new AWTFontRenderer(font, 0, 0, 6, null);
        Font fontDeriveFont = font.deriveFont(1);
        Intrinsics.checkExpressionValueIsNotNull(fontDeriveFont, "font.deriveFont(Font.BOLD)");
        this.boldFont = new AWTFontRenderer(fontDeriveFont, 0, 0, 6, null);
        Font fontDeriveFont2 = font.deriveFont(2);
        Intrinsics.checkExpressionValueIsNotNull(fontDeriveFont2, "font.deriveFont(Font.ITALIC)");
        this.italicFont = new AWTFontRenderer(fontDeriveFont2, 0, 0, 6, null);
        Font fontDeriveFont3 = font.deriveFont(3);
        Intrinsics.checkExpressionValueIsNotNull(fontDeriveFont3, "font.deriveFont(Font.BOLD or Font.ITALIC)");
        this.boldItalicFont = new AWTFontRenderer(fontDeriveFont3, 0, 0, 6, null);
        this.fontHeight = getHeight();
    }

    public final int getFontHeight() {
        return this.fontHeight;
    }

    @NotNull
    public final AWTFontRenderer getDefaultFont() {
        return this.defaultFont;
    }

    public final void setDefaultFont(@NotNull AWTFontRenderer aWTFontRenderer) {
        Intrinsics.checkParameterIsNotNull(aWTFontRenderer, "<set-?>");
        this.defaultFont = aWTFontRenderer;
    }

    public final int getHeight() {
        return this.defaultFont.getHeight() / 2;
    }

    public final int getSize() {
        return this.defaultFont.getFont().getSize();
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int drawString(@Nullable String s, float x, float y, int color) {
        return drawString(s, x, y, color, false);
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int drawStringWithShadow(@Nullable String text, float x, float y, int color) {
        return drawString(text, x, y, color, true);
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int drawCenteredString(@NotNull String s, float x, float y, int color, boolean shadow) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return drawString(s, x - (getStringWidth(s) / 2.0f), y, color, shadow);
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int drawCenteredString(@NotNull String s, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return drawStringWithShadow(s, x - (getStringWidth(s) / 2.0f), y, color);
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int drawString(@Nullable String text, float x, float y, int color, boolean shadow) {
        TextEvent event = new TextEvent(text);
        LiquidBounce.INSTANCE.getEventManager().callEvent(event);
        String currentText = event.getText();
        if (currentText == null) {
            return 0;
        }
        float currY = y - 3.0f;
        boolean rainbow = RainbowFontShader.INSTANCE.isInUse();
        if (shadow) {
            GL20.glUseProgram(0);
            drawText$default(this, currentText, x + 1.0f, currY + 1.0f, new Color(0, 0, 0, 150).getRGB(), true, false, 32, null);
        }
        return drawText(currentText, x, currY, color, false, rainbow);
    }

    static /* synthetic */ int drawText$default(GameFontRenderer gameFontRenderer, String str, float f, float f2, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 32) != 0) {
            z2 = false;
        }
        return gameFontRenderer.drawText(str, f, f2, i, z, z2);
    }

    private final int drawText(String text, float x, float y, int color, boolean ignoreColor, boolean rainbow) {
        AWTFontRenderer aWTFontRenderer;
        if (text == null) {
            return 0;
        }
        if (text.length() == 0) {
            return (int) x;
        }
        int rainbowShaderId = RainbowFontShader.INSTANCE.getProgramId();
        if (rainbow) {
            GL20.glUseProgram(rainbowShaderId);
        }
        GL11.glTranslated(x - 1.5d, y + 0.5d, 0.0d);
        WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().enableAlpha();
        WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().enableBlend();
        WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().tryBlendFuncSeparate(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().enableTexture2D();
        int currentColor = color;
        if ((currentColor & (-67108864)) == 0) {
            currentColor |= -16777216;
        }
        int alpha = (currentColor >> 24) & 255;
        if (StringsKt.contains$default((CharSequence) text, (CharSequence) "\u00a7", false, 2, (Object) null)) {
            Iterable parts = StringsKt.split$default((CharSequence) text, new String[]{"\u00a7"}, false, 0, 6, (Object) null);
            AWTFontRenderer aWTFontRenderer2 = this.defaultFont;
            double width = 0.0d;
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikeThrough = false;
            boolean underline = false;
            Iterable $this$forEachIndexed$iv = parts;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index = index$iv;
                index$iv++;
                if (index < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                String part = (String) item$iv;
                if (!(part.length() == 0)) {
                    if (index == 0) {
                        aWTFontRenderer2.drawString(part, width, 0.0d, currentColor);
                        width += aWTFontRenderer2.getStringWidth(part);
                    } else {
                        if (part == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String words = part.substring(1);
                        Intrinsics.checkExpressionValueIsNotNull(words, "(this as java.lang.String).substring(startIndex)");
                        char type = part.charAt(0);
                        int colorIndex = Companion.getColorIndex(type);
                        if (0 <= colorIndex && 15 >= colorIndex) {
                            if (!ignoreColor) {
                                currentColor = ColorUtils.hexColors[colorIndex] | (alpha << 24);
                                if (rainbow) {
                                    GL20.glUseProgram(0);
                                }
                            }
                            bold = false;
                            italic = false;
                            randomCase = false;
                            underline = false;
                            strikeThrough = false;
                        } else if (colorIndex == 16) {
                            randomCase = true;
                        } else if (colorIndex == 17) {
                            bold = true;
                        } else if (colorIndex == 18) {
                            strikeThrough = true;
                        } else if (colorIndex == 19) {
                            underline = true;
                        } else if (colorIndex == 20) {
                            italic = true;
                        } else if (colorIndex == 21) {
                            currentColor = color;
                            if ((currentColor & (-67108864)) == 0) {
                                currentColor |= -16777216;
                            }
                            if (rainbow) {
                                GL20.glUseProgram(rainbowShaderId);
                            }
                            bold = false;
                            italic = false;
                            randomCase = false;
                            underline = false;
                            strikeThrough = false;
                        }
                        if (bold && italic) {
                            aWTFontRenderer = this.boldItalicFont;
                        } else if (bold) {
                            aWTFontRenderer = this.boldFont;
                        } else if (italic) {
                            aWTFontRenderer = this.italicFont;
                        } else {
                            aWTFontRenderer = this.defaultFont;
                        }
                        aWTFontRenderer2 = aWTFontRenderer;
                        aWTFontRenderer2.drawString(randomCase ? ColorUtils.INSTANCE.randomMagicText(words) : words, width, 0.0d, currentColor);
                        if (strikeThrough) {
                            RenderUtils.drawLine((width / 2.0d) + 1, aWTFontRenderer2.getHeight() / 3.0d, ((width + aWTFontRenderer2.getStringWidth(words)) / 2.0d) + 1, aWTFontRenderer2.getHeight() / 3.0d, this.fontHeight / 16.0f);
                        }
                        if (underline) {
                            RenderUtils.drawLine((width / 2.0d) + 1, aWTFontRenderer2.getHeight() / 2.0d, ((width + aWTFontRenderer2.getStringWidth(words)) / 2.0d) + 1, aWTFontRenderer2.getHeight() / 2.0d, this.fontHeight / 16.0f);
                        }
                        width += aWTFontRenderer2.getStringWidth(words);
                    }
                }
            }
        } else {
            this.defaultFont.drawString(text, 0.0d, 0.0d, currentColor);
        }
        WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().disableBlend();
        GL11.glTranslated(-(x - 1.5d), -(y + 0.5d), 0.0d);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return (int) (x + getStringWidth(text));
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int getColorCode(char charCode) {
        return ColorUtils.hexColors[Companion.getColorIndex(charCode)];
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int getStringWidth(@Nullable String text) {
        AWTFontRenderer aWTFontRenderer;
        TextEvent event = new TextEvent(text);
        LiquidBounce.INSTANCE.getEventManager().callEvent(event);
        String currentText = event.getText();
        if (currentText == null) {
            return 0;
        }
        if (StringsKt.contains$default((CharSequence) currentText, (CharSequence) "\u00a7", false, 2, (Object) null)) {
            Iterable parts = StringsKt.split$default((CharSequence) currentText, new String[]{"\u00a7"}, false, 0, 6, (Object) null);
            AWTFontRenderer aWTFontRenderer2 = this.defaultFont;
            int width = 0;
            boolean bold = false;
            boolean italic = false;
            Iterable $this$forEachIndexed$iv = parts;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index = index$iv;
                index$iv++;
                if (index < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                String part = (String) item$iv;
                if (!(part.length() == 0)) {
                    if (index == 0) {
                        width += aWTFontRenderer2.getStringWidth(part);
                    } else {
                        if (part == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String words = part.substring(1);
                        Intrinsics.checkExpressionValueIsNotNull(words, "(this as java.lang.String).substring(startIndex)");
                        char type = part.charAt(0);
                        int colorIndex = Companion.getColorIndex(type);
                        if (colorIndex < 16) {
                            bold = false;
                            italic = false;
                        } else if (colorIndex == 17) {
                            bold = true;
                        } else if (colorIndex == 20) {
                            italic = true;
                        } else if (colorIndex == 21) {
                            bold = false;
                            italic = false;
                        }
                        if (bold && italic) {
                            aWTFontRenderer = this.boldItalicFont;
                        } else if (bold) {
                            aWTFontRenderer = this.boldFont;
                        } else if (italic) {
                            aWTFontRenderer = this.italicFont;
                        } else {
                            aWTFontRenderer = this.defaultFont;
                        }
                        aWTFontRenderer2 = aWTFontRenderer;
                        width += aWTFontRenderer2.getStringWidth(words);
                    }
                }
            }
            return width / 2;
        }
        return this.defaultFont.getStringWidth(currentText) / 2;
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer
    public int getCharWidth(char character) {
        return getStringWidth(String.valueOf(character));
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\f\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion;", "", "()V", "getColorIndex", "", "type", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

package net.ccbluex.liquidbounce.p005ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u0007\u0018\ufffd\ufffd ,2\u00020\u0001:\u0002+,B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J&\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020'2\u0006\u0010 \u001a\u00020'2\u0006\u0010(\u001a\u00020\u0005J\u000e\u0010)\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\nJ\u0018\u0010*\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0018\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0014\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006-"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "font", "Ljava/awt/Font;", "startChar", "", "stopChar", "(Ljava/awt/Font;II)V", "cachedStrings", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "Lkotlin/collections/HashMap;", "charLocations", "", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "[Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "getFont", "()Ljava/awt/Font;", "fontHeight", "height", "getHeight", "()I", "textureHeight", "textureID", "textureWidth", "collectGarbage", "", "drawChar", "char", "x", "", "y", "drawCharToImage", "Ljava/awt/image/BufferedImage;", "ch", "", "drawString", "text", "", "color", "getStringWidth", "renderBitmap", "CharLocation", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/AWTFontRenderer.class */
public final class AWTFontRenderer extends MinecraftInstance {
    private int fontHeight;
    private final CharLocation[] charLocations;
    private final HashMap cachedStrings;
    private int textureID;
    private int textureWidth;
    private int textureHeight;

    @NotNull
    private final Font font;
    private static boolean assumeNonVolatile;
    private static int gcTicks;
    private static final int GC_TICKS = 600;
    private static final int CACHED_FONT_REMOVAL_TIME = 30000;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final ArrayList activeFontRenderers = new ArrayList();

    @NotNull
    public final Font getFont() {
        return this.font;
    }

    public AWTFontRenderer(@NotNull Font font, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(font, "font");
        this.font = font;
        this.fontHeight = -1;
        this.charLocations = new CharLocation[i2];
        this.cachedStrings = new HashMap();
        renderBitmap(i, i2);
        activeFontRenderers.add(this);
    }

    public AWTFontRenderer(Font font, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(font, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 255 : i2);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffdR!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion;", "", "()V", "CACHED_FONT_REMOVAL_TIME", "", "GC_TICKS", "activeFontRenderers", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lkotlin/collections/ArrayList;", "getActiveFontRenderers", "()Ljava/util/ArrayList;", "assumeNonVolatile", "", "getAssumeNonVolatile", "()Z", "setAssumeNonVolatile", "(Z)V", "gcTicks", "garbageCollectionTick", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean getAssumeNonVolatile() {
            return AWTFontRenderer.assumeNonVolatile;
        }

        public final void setAssumeNonVolatile(boolean z) {
            AWTFontRenderer.assumeNonVolatile = z;
        }

        @NotNull
        public final ArrayList getActiveFontRenderers() {
            return AWTFontRenderer.activeFontRenderers;
        }

        public final void garbageCollectionTick() {
            int i = AWTFontRenderer.gcTicks;
            AWTFontRenderer.gcTicks = i + 1;
            if (i <= AWTFontRenderer.GC_TICKS) {
                return;
            }
            Iterator it = getActiveFontRenderers().iterator();
            while (it.hasNext()) {
                ((AWTFontRenderer) it.next()).collectGarbage();
            }
            AWTFontRenderer.gcTicks = 0;
        }
    }

    private final void collectGarbage() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = this.cachedStrings;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (jCurrentTimeMillis - ((CachedFont) entry.getValue()).getLastUsage() > 30000) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            GL11.glDeleteLists(((CachedFont) entry2.getValue()).getDisplayList(), 1);
            ((CachedFont) entry2.getValue()).setDeleted(true);
            this.cachedStrings.remove(entry2.getKey());
        }
    }

    public final int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public final void drawString(@NotNull String text, double d, double d2, int i) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        GL11.glPushMatrix();
        GL11.glScaled(0.25d, 0.25d, 0.25d);
        GL11.glTranslated(d * 2.0d, (d2 * 2.0d) - 2.0d, 0.0d);
        MinecraftInstance.classProvider.getGlStateManager().bindTexture(this.textureID);
        float f = ((i >> 16) & 255) / 255.0f;
        float f2 = ((i >> 8) & 255) / 255.0f;
        float f3 = (i & 255) / 255.0f;
        float f4 = ((i >> 24) & 255) / 255.0f;
        GL11.glColor4f(f, f2, f3, f4);
        double width = 0.0d;
        CachedFont cachedFont = (CachedFont) this.cachedStrings.get(text);
        if (cachedFont != null) {
            GL11.glCallList(cachedFont.getDisplayList());
            cachedFont.setLastUsage(System.currentTimeMillis());
            GL11.glPopMatrix();
            return;
        }
        int iGlGenLists = -1;
        if (assumeNonVolatile) {
            iGlGenLists = GL11.glGenLists(1);
            GL11.glNewList(iGlGenLists, SGL.GL_COMPILE_AND_EXECUTE);
        }
        GL11.glBegin(7);
        char[] charArray = text.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        for (char c : charArray) {
            if (c >= this.charLocations.length) {
                GL11.glEnd();
                GL11.glScaled(4.0d, 4.0d, 4.0d);
                MinecraftInstance.f157mc.getFontRendererObj().drawString(String.valueOf(c), (((float) width) * 0.25f) + 1.0f, 2.0f, i, false);
                width += MinecraftInstance.f157mc.getFontRendererObj().getStringWidth(String.valueOf(c)) * 4.0d;
                GL11.glScaled(0.25d, 0.25d, 0.25d);
                MinecraftInstance.classProvider.getGlStateManager().bindTexture(this.textureID);
                GL11.glColor4f(f, f2, f3, f4);
                GL11.glBegin(7);
            } else {
                CharLocation charLocation = this.charLocations[c];
                if (charLocation != null) {
                    drawChar(charLocation, (float) width, 0.0f);
                    width += charLocation.getWidth() - 8.0d;
                }
            }
        }
        GL11.glEnd();
        if (assumeNonVolatile) {
            this.cachedStrings.put(text, new CachedFont(iGlGenLists, System.currentTimeMillis(), false, 4, null));
            GL11.glEndList();
        }
        GL11.glPopMatrix();
    }

    private final void drawChar(CharLocation charLocation, float f, float f2) {
        float width = charLocation.getWidth();
        float height = charLocation.getHeight();
        float x = charLocation.getX();
        float f3 = x / this.textureWidth;
        float y = charLocation.getY() / this.textureHeight;
        float f4 = width / this.textureWidth;
        float f5 = height / this.textureHeight;
        GL11.glTexCoord2f(f3, y);
        GL11.glVertex2f(f, f2);
        GL11.glTexCoord2f(f3, y + f5);
        GL11.glVertex2f(f, f2 + height);
        GL11.glTexCoord2f(f3 + f4, y + f5);
        GL11.glVertex2f(f + width, f2 + height);
        GL11.glTexCoord2f(f3 + f4, y);
        GL11.glVertex2f(f + width, f2);
    }

    private final void renderBitmap(int i, int i2) {
        Image[] imageArr = new BufferedImage[i2];
        int height = 0;
        int width = 0;
        int i3 = 0;
        for (int i4 = i; i4 < i2; i4++) {
            BufferedImage bufferedImageDrawCharToImage = drawCharToImage((char) i4);
            CharLocation charLocation = new CharLocation(width, i3, bufferedImageDrawCharToImage.getWidth(), bufferedImageDrawCharToImage.getHeight());
            if (charLocation.getHeight() > this.fontHeight) {
                this.fontHeight = charLocation.getHeight();
            }
            if (charLocation.getHeight() > height) {
                height = charLocation.getHeight();
            }
            this.charLocations[i4] = charLocation;
            imageArr[i4] = bufferedImageDrawCharToImage;
            width += charLocation.getWidth();
            if (width > 2048) {
                if (width > this.textureWidth) {
                    this.textureWidth = width;
                }
                width = 0;
                i3 += height;
                height = 0;
            }
        }
        this.textureHeight = i3 + height;
        BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
        Graphics2D graphics = bufferedImage.getGraphics();
        if (graphics == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
        }
        Graphics2D graphics2D = graphics;
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
        graphics2D.setColor(Color.white);
        for (int i5 = i; i5 < i2; i5++) {
            if (imageArr[i5] != null && this.charLocations[i5] != null) {
                Image image = imageArr[i5];
                CharLocation charLocation2 = this.charLocations[i5];
                if (charLocation2 == null) {
                    Intrinsics.throwNpe();
                }
                int x = charLocation2.getX();
                CharLocation charLocation3 = this.charLocations[i5];
                if (charLocation3 == null) {
                    Intrinsics.throwNpe();
                }
                graphics2D.drawImage(image, x, charLocation3.getY(), (ImageObserver) null);
            }
        }
        this.textureID = TextureUtil.func_110989_a(TextureUtil.func_110996_a(), bufferedImage, true, true);
    }

    private final BufferedImage drawCharToImage(char c) {
        Graphics2D graphics = new BufferedImage(1, 1, 2).getGraphics();
        if (graphics == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
        }
        Graphics2D graphics2D = graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(this.font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int iCharWidth = fontMetrics.charWidth(c) + 8;
        if (iCharWidth <= 0) {
            iCharWidth = 7;
        }
        Intrinsics.checkExpressionValueIsNotNull(fontMetrics, "fontMetrics");
        int height = fontMetrics.getHeight() + 3;
        if (height <= 0) {
            height = this.font.getSize();
        }
        BufferedImage bufferedImage = new BufferedImage(iCharWidth, height, 2);
        Graphics2D graphics2 = bufferedImage.getGraphics();
        if (graphics2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
        }
        Graphics2D graphics2D2 = graphics2;
        graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D2.setFont(this.font);
        graphics2D2.setColor(Color.WHITE);
        graphics2D2.drawString(String.valueOf(c), 3, 1 + fontMetrics.getAscent());
        return bufferedImage;
    }

    public final int getStringWidth(@NotNull String text) {
        char c;
        Intrinsics.checkParameterIsNotNull(text, "text");
        int width = 0;
        char[] charArray = text.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        for (char c2 : charArray) {
            CharLocation[] charLocationArr = this.charLocations;
            if (c2 < this.charLocations.length) {
                c = c2;
            } else {
                c = 3;
            }
            CharLocation charLocation = charLocationArr[c];
            if (charLocation != null) {
                width += charLocation.getWidth() - 8;
            }
        }
        return width / 2;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0082\b\u0018\ufffd\ufffd2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0016\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "", "x", "", "y", "width", "height", "(IIII)V", "getHeight", "()I", "setHeight", "(I)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation.class */
    private static final class CharLocation {

        /* renamed from: x */
        private int f154x;

        /* renamed from: y */
        private int f155y;
        private int width;
        private int height;

        public final int component1() {
            return this.f154x;
        }

        public final int component2() {
            return this.f155y;
        }

        public final int component3() {
            return this.width;
        }

        public final int component4() {
            return this.height;
        }

        @NotNull
        public final CharLocation copy(int i, int i2, int i3, int i4) {
            return new CharLocation(i, i2, i3, i4);
        }

        public static CharLocation copy$default(CharLocation charLocation, int i, int i2, int i3, int i4, int i5, Object obj) {
            if ((i5 & 1) != 0) {
                i = charLocation.f154x;
            }
            if ((i5 & 2) != 0) {
                i2 = charLocation.f155y;
            }
            if ((i5 & 4) != 0) {
                i3 = charLocation.width;
            }
            if ((i5 & 8) != 0) {
                i4 = charLocation.height;
            }
            return charLocation.copy(i, i2, i3, i4);
        }

        @NotNull
        public String toString() {
            return "CharLocation(x=" + this.f154x + ", y=" + this.f155y + ", width=" + this.width + ", height=" + this.height + ")";
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.f154x) * 31) + Integer.hashCode(this.f155y)) * 31) + Integer.hashCode(this.width)) * 31) + Integer.hashCode(this.height);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CharLocation)) {
                return false;
            }
            CharLocation charLocation = (CharLocation) obj;
            return this.f154x == charLocation.f154x && this.f155y == charLocation.f155y && this.width == charLocation.width && this.height == charLocation.height;
        }

        public final int getX() {
            return this.f154x;
        }

        public final void setX(int i) {
            this.f154x = i;
        }

        public final int getY() {
            return this.f155y;
        }

        public final void setY(int i) {
            this.f155y = i;
        }

        public final int getWidth() {
            return this.width;
        }

        public final void setWidth(int i) {
            this.width = i;
        }

        public final int getHeight() {
            return this.height;
        }

        public final void setHeight(int i) {
            this.height = i;
        }

        public CharLocation(int i, int i2, int i3, int i4) {
            this.f154x = i;
            this.f155y = i2;
            this.width = i3;
            this.height = i4;
        }
    }
}

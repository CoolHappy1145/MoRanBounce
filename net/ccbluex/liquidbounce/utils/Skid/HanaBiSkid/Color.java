package net.ccbluex.liquidbounce.utils.Skid.HanaBiSkid;

import java.io.Serializable;
import java.nio.FloatBuffer;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import net.ccbluex.liquidbounce.utils.Skid.Renderer;
import net.ccbluex.liquidbounce.utils.Skid.SGL;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/HanaBiSkid/Color.class */
public class Color implements Serializable {
    private static final long serialVersionUID = 1393939;

    /* renamed from: GL */
    protected SGL f162GL = Renderer.get();
    public static final Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color white = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    public static final Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    public static final Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color cyan = new Color(0.0f, 1.0f, 1.0f, 1.0f);
    public static final Color darkGray = new Color(0.3f, 0.3f, 0.3f, 1.0f);
    public static final Color lightGray = new Color(0.7f, 0.7f, 0.7f, 1.0f);
    public static final Color pink = new Color(255, 175, 175, 255);
    public static final Color orange = new Color(255, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 0, 255);
    public static final Color magenta = new Color(255, 0, 255, 255);

    /* renamed from: r */
    public float f163r;

    /* renamed from: g */
    public float f164g;

    /* renamed from: b */
    public float f165b;

    /* renamed from: a */
    public float f166a;

    public Color(Color color) {
        this.f166a = 1.0f;
        this.f163r = color.f163r;
        this.f164g = color.f164g;
        this.f165b = color.f165b;
        this.f166a = color.f166a;
    }

    public Color(FloatBuffer floatBuffer) {
        this.f166a = 1.0f;
        this.f163r = floatBuffer.get();
        this.f164g = floatBuffer.get();
        this.f165b = floatBuffer.get();
        this.f166a = floatBuffer.get();
    }

    public Color(float f, float f2, float f3) {
        this.f166a = 1.0f;
        this.f163r = f;
        this.f164g = f2;
        this.f165b = f3;
        this.f166a = 1.0f;
    }

    public Color(float f, float f2, float f3, float f4) {
        this.f166a = 1.0f;
        this.f163r = Math.min(f, 1.0f);
        this.f164g = Math.min(f2, 1.0f);
        this.f165b = Math.min(f3, 1.0f);
        this.f166a = Math.min(f4, 1.0f);
    }

    public Color(int i, int i2, int i3) {
        this.f166a = 1.0f;
        this.f163r = i / 255.0f;
        this.f164g = i2 / 255.0f;
        this.f165b = i3 / 255.0f;
        this.f166a = 1.0f;
    }

    public Color(int i, int i2, int i3, int i4) {
        this.f166a = 1.0f;
        this.f163r = i / 255.0f;
        this.f164g = i2 / 255.0f;
        this.f165b = i3 / 255.0f;
        this.f166a = i4 / 255.0f;
    }

    public Color(int i) {
        this.f166a = 1.0f;
        int i2 = (i & 16711680) >> 16;
        int i3 = (i & 65280) >> 8;
        int i4 = i & 255;
        int i5 = (i & (-16777216)) >> 24;
        i5 = i5 < 0 ? i5 + 256 : i5;
        i5 = i5 == 0 ? 255 : i5;
        this.f163r = i2 / 255.0f;
        this.f164g = i3 / 255.0f;
        this.f165b = i4 / 255.0f;
        this.f166a = i5 / 255.0f;
    }

    public static Color decode(String str) {
        return new Color(Integer.decode(str).intValue());
    }

    public void bind() {
        this.f162GL.glColor4f(this.f163r, this.f164g, this.f165b, this.f166a);
    }

    public int hashCode() {
        return ((int) (this.f163r + this.f164g + this.f165b + this.f166a)) * 255;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Color) {
            Color color = (Color) obj;
            return color.f163r == this.f163r && color.f164g == this.f164g && color.f165b == this.f165b && color.f166a == this.f166a;
        }
        return false;
    }

    public String toString() {
        return "Color (" + this.f163r + "," + this.f164g + "," + this.f165b + "," + this.f166a + ")";
    }

    public Color darker() {
        return darker(0.5f);
    }

    public Color darker(float f) {
        float f2 = 1.0f - f;
        return new Color(this.f163r * f2, this.f164g * f2, this.f165b * f2, this.f166a);
    }

    public Color brighter() {
        return brighter(0.2f);
    }

    public int getRed() {
        return (int) (this.f163r * 255.0f);
    }

    public int getGreen() {
        return (int) (this.f164g * 255.0f);
    }

    public int getBlue() {
        return (int) (this.f165b * 255.0f);
    }

    public int getAlpha() {
        return (int) (this.f166a * 255.0f);
    }

    public int getRedByte() {
        return (int) (this.f163r * 255.0f);
    }

    public int getGreenByte() {
        return (int) (this.f164g * 255.0f);
    }

    public int getBlueByte() {
        return (int) (this.f165b * 255.0f);
    }

    public int getAlphaByte() {
        return (int) (this.f166a * 255.0f);
    }

    public Color brighter(float f) {
        float f2 = f + 1.0f;
        return new Color(this.f163r * f2, this.f164g * f2, this.f165b * f2, this.f166a);
    }

    public Color multiply(Color color) {
        return new Color(this.f163r * color.f163r, this.f164g * color.f164g, this.f165b * color.f165b, this.f166a * color.f166a);
    }

    public void add(Color color) {
        this.f163r += color.f163r;
        this.f164g += color.f164g;
        this.f165b += color.f165b;
        this.f166a += color.f166a;
    }

    public void scale(float f) {
        this.f163r *= f;
        this.f164g *= f;
        this.f165b *= f;
        this.f166a *= f;
    }

    public Color addToCopy(Color color) {
        Color color2 = new Color(this.f163r, this.f164g, this.f165b, this.f166a);
        color2.f163r += color.f163r;
        color2.f164g += color.f164g;
        color2.f165b += color.f165b;
        color2.f166a += color.f166a;
        return color2;
    }

    public Color scaleCopy(float f) {
        Color color = new Color(this.f163r, this.f164g, this.f165b, this.f166a);
        color.f163r *= f;
        color.f164g *= f;
        color.f165b *= f;
        color.f166a *= f;
        return color;
    }
}

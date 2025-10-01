package net.vitox;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.particle.util.RenderUtils;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/vitox/Particle.class */
class Particle {

    /* renamed from: x */
    public float f182x;

    /* renamed from: y */
    public float f183y;
    private int height;
    private int width;
    private final float ySpeed = new Random().nextInt(5);
    private final float xSpeed = new Random().nextInt(5);
    public final float size = genRandom();

    Particle(int x, int y) {
        this.f182x = x;
        this.f183y = y;
    }

    private float lint1(float f) {
        return (1.02f * (1.0f - f)) + (1.0f * f);
    }

    private float lint2(float f) {
        return 1.02f + (f * (-0.01999998f));
    }

    void connect(float x, float y) {
        RenderUtils.connectPoints(getX(), getY(), x, y);
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getX() {
        return this.f182x;
    }

    public void setX(int x) {
        this.f182x = x;
    }

    public float getY() {
        return this.f183y;
    }

    public void setY(int y) {
        this.f183y = y;
    }

    void interpolation() {
        for (int n = 0; n <= 64; n++) {
            float f = n / 64.0f;
            float p1 = lint1(f);
            float p2 = lint2(f);
            if (p1 != p2) {
                this.f183y -= f;
                this.f182x -= f;
            }
        }
    }

    void fall() {
        Minecraft mc = Minecraft.func_71410_x();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        this.f183y += this.ySpeed;
        this.f182x += this.xSpeed;
        if (this.f183y > mc.field_71440_d) {
            this.f183y = 1.0f;
        }
        if (this.f182x > mc.field_71443_c) {
            this.f182x = 1.0f;
        }
        if (this.f182x < 1.0f) {
            this.f182x = scaledResolution.func_78326_a();
        }
        if (this.f183y < 1.0f) {
            this.f183y = scaledResolution.func_78328_b();
        }
    }

    private float genRandom() {
        return (float) (0.30000001192092896d + (Math.random() * 1.2999999523162842d));
    }
}

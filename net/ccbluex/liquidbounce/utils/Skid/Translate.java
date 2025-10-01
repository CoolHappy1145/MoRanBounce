package net.ccbluex.liquidbounce.utils.Skid;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/Translate.class */
public final class Translate {

    /* renamed from: x */
    private float f171x;

    /* renamed from: y */
    private float f172y;
    private boolean first = false;

    public Translate(float f, float f2) {
        this.f171x = f;
        this.f172y = f2;
    }

    public final void interpolate(float f, float f2, double d) {
        if (this.first) {
            this.f171x = AnimationUtil.animate(f, this.f171x, d);
            this.f172y = AnimationUtil.animate(f2, this.f172y, d);
        } else {
            this.f171x = f;
            this.f172y = f2;
            this.first = true;
        }
    }

    public final void interpolate3(float f, float f2, double d) {
        this.f171x = AnimationUtil.animate(f, this.f171x, d);
        this.f172y = AnimationUtil.animate(f2, this.f172y, d);
    }

    public final void interpolate2(float f, float f2, double d) {
        this.f171x = f;
        this.f172y = AnimationUtil.animate(f2, this.f172y, d);
    }

    public float getX() {
        return this.f171x;
    }

    public void setX(float f) {
        this.f171x = f;
    }

    public float getY() {
        return this.f172y;
    }

    public void setY(float f) {
        this.f172y = f;
    }
}

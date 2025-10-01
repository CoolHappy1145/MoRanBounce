package net.ccbluex.liquidbounce.p005ui.client.hud.element;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0011\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0006\u0010\u0012\u001a\u00020\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\t\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "", "x", "", "y", "x2", "y2", "(FFFF)V", "getX", "()F", "getX2", "getY", "getY2", "component1", "component2", "component3", "component4", "copy", "draw", "", "equals", "", "other", "hashCode", "", "toString", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Border.class */
public final class Border {

    /* renamed from: x */
    private final float f141x;

    /* renamed from: y */
    private final float f142y;

    /* renamed from: x2 */
    private final float f143x2;

    /* renamed from: y2 */
    private final float f144y2;

    public final float component1() {
        return this.f141x;
    }

    public final float component2() {
        return this.f142y;
    }

    public final float component3() {
        return this.f143x2;
    }

    public final float component4() {
        return this.f144y2;
    }

    @NotNull
    public final Border copy(float f, float f2, float f3, float f4) {
        return new Border(f, f2, f3, f4);
    }

    public static Border copy$default(Border border, float f, float f2, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = border.f141x;
        }
        if ((i & 2) != 0) {
            f2 = border.f142y;
        }
        if ((i & 4) != 0) {
            f3 = border.f143x2;
        }
        if ((i & 8) != 0) {
            f4 = border.f144y2;
        }
        return border.copy(f, f2, f3, f4);
    }

    @NotNull
    public String toString() {
        return "Border(x=" + this.f141x + ", y=" + this.f142y + ", x2=" + this.f143x2 + ", y2=" + this.f144y2 + ")";
    }

    public int hashCode() {
        return (((((Float.hashCode(this.f141x) * 31) + Float.hashCode(this.f142y)) * 31) + Float.hashCode(this.f143x2)) * 31) + Float.hashCode(this.f144y2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Border)) {
            return false;
        }
        Border border = (Border) obj;
        return Float.compare(this.f141x, border.f141x) == 0 && Float.compare(this.f142y, border.f142y) == 0 && Float.compare(this.f143x2, border.f143x2) == 0 && Float.compare(this.f144y2, border.f144y2) == 0;
    }

    public final float getX() {
        return this.f141x;
    }

    public final float getY() {
        return this.f142y;
    }

    public final float getX2() {
        return this.f143x2;
    }

    public final float getY2() {
        return this.f144y2;
    }

    public Border(float f, float f2, float f3, float f4) {
        this.f141x = f;
        this.f142y = f2;
        this.f143x2 = f3;
        this.f144y2 = f4;
    }

    public final void draw() {
        RenderUtils.drawBorderedRect(this.f141x, this.f142y, this.f143x2, this.f144y2, 3.0f, Integer.MIN_VALUE, 0);
    }
}

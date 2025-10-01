package net.ccbluex.liquidbounce.api.minecraft.util;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0014\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J!\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0087\bJ!\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u000bH\u0087\bJ\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0007J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bH\u0007J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/WMathHelper;", "", "()V", "SIN_TABLE", "", "clamp_double", "", "num", "min", "max", "clamp_float", "", "cos", "p_cos_0_", "floor_double", "", PropertyDescriptor.VALUE, "sin", "p_sin_0_", "wrapAngleTo180_float", "angle", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/WMathHelper.class */
public final class WMathHelper {
    public static final WMathHelper INSTANCE = new WMathHelper();
    private static final float[] SIN_TABLE = new float[65536];

    private WMathHelper() {
    }

    @JvmStatic
    public static final float sin(float f) {
        return SIN_TABLE[((int) (f * 10430.378f)) & Integer.parseInt("\uffff")];
    }

    @JvmStatic
    public static final float cos(float f) {
        return SIN_TABLE[((int) ((f * 10430.378f) + 16384.0f)) & Integer.parseInt("\uffff")];
    }
}

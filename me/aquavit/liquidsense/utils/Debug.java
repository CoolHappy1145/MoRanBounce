package me.aquavit.liquidsense.utils;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

/* loaded from: L-out.jar:me/aquavit/liquidsense/utils/Debug.class */
public class Debug extends MinecraftInstance {
    public static Boolean thePlayerisBlocking = false;

    public static void transformSideFirstPersonBlock(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-20.0d)), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-20.0d)), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-80.0d)), 1.0f, 0.0f, 0.0f);
    }

    public static void WindMill(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-20.0d)), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-20.0d)), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-80.0d)), 1.0f, 0.0f, 0.0f);
    }

    public static void Push(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-10.0d)), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-10.0d)), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-10.0d)), 1.0f, 1.0f, 1.0f);
    }

    public static void LiquidSense(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(Math.sqrt(Math.sin(f2 * f2 * 3.141592653589793d)) * 3.1415927410125732d);
        GlStateManager.func_179114_b((float) ((-dSin) * 35.0d), -8.0f, -0.0f, 9.0f);
        GlStateManager.func_179114_b((float) ((-dSin) * 10.0d), 1.0f, -0.4f, -0.5f);
    }
}

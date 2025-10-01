package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.minecraft.client.Minecraft;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/MinecraftInstance.class */
public class MinecraftInstance {

    /* renamed from: mc */
    public static final IMinecraft f157mc = LiquidBounce.wrapper.getMinecraft();
    public static final Minecraft mc2 = Minecraft.func_71410_x();
    public static final IClassProvider classProvider = LiquidBounce.INSTANCE.getWrapper().getClassProvider();
    public static final IExtractedFunctions functions = LiquidBounce.INSTANCE.getWrapper().getFunctions();
}

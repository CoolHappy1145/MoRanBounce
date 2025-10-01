package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.Wrapper;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/WrapperImpl;", "Lnet/ccbluex/liquidbounce/api/Wrapper;", "()V", "classProvider", "Lnet/ccbluex/liquidbounce/api/IClassProvider;", "getClassProvider", "()Lnet/ccbluex/liquidbounce/api/IClassProvider;", "functions", "Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "getFunctions", "()Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "minecraft", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "getMinecraft", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WrapperImpl.class */
public final class WrapperImpl implements Wrapper {
    public static final WrapperImpl INSTANCE = new WrapperImpl();

    @NotNull
    private static final IClassProvider classProvider = ClassProviderImpl.INSTANCE;

    @NotNull
    private static final IExtractedFunctions functions = ExtractedFunctionsImpl.INSTANCE;

    private WrapperImpl() {
    }

    @Override // net.ccbluex.liquidbounce.api.Wrapper
    @NotNull
    public IClassProvider getClassProvider() {
        return classProvider;
    }

    @Override // net.ccbluex.liquidbounce.api.Wrapper
    @NotNull
    public IMinecraft getMinecraft() {
        Minecraft minecraftFunc_71410_x = Minecraft.func_71410_x();
        Intrinsics.checkExpressionValueIsNotNull(minecraftFunc_71410_x, "Minecraft.getMinecraft()");
        return new MinecraftImpl(minecraftFunc_71410_x);
    }

    @Override // net.ccbluex.liquidbounce.api.Wrapper
    @NotNull
    public IExtractedFunctions getFunctions() {
        return functions;
    }
}

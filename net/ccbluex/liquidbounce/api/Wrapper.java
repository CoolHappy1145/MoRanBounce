package net.ccbluex.liquidbounce.api;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\ufffd\ufffd2\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/Wrapper;", "", "classProvider", "Lnet/ccbluex/liquidbounce/api/IClassProvider;", "getClassProvider", "()Lnet/ccbluex/liquidbounce/api/IClassProvider;", "functions", "Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "getFunctions", "()Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "minecraft", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "getMinecraft", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/Wrapper.class */
public interface Wrapper {
    @NotNull
    IClassProvider getClassProvider();

    @NotNull
    IMinecraft getMinecraft();

    @NotNull
    IExtractedFunctions getFunctions();
}

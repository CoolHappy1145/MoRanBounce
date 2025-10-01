package net.ccbluex.liquidbounce.api.minecraft.client.renderer;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\ufffd\ufffd2\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH&J\u0018\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0007H&J\b\u0010\u0013\u001a\u00020\u0007H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "", "shaderGroup", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "getShaderGroup", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "disableLightmap", "", "isShaderActive", "", "loadShader", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "setupCameraTransform", "partialTicks", "", "i", "", "setupOverlayRendering", "stopUseShader", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer.class */
public interface IEntityRenderer {
    @Nullable
    IShaderGroup getShaderGroup();

    void disableLightmap();

    boolean isShaderActive();

    void loadShader(@NotNull IResourceLocation iResourceLocation);

    void stopUseShader();

    void setupCameraTransform(float f, int i);

    void setupOverlayRendering();
}

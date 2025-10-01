package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.ShaderGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\fH\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001c"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EntityRendererImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "wrapped", "Lnet/minecraft/client/renderer/EntityRenderer;", "(Lnet/minecraft/client/renderer/EntityRenderer;)V", "shaderGroup", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "getShaderGroup", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "getWrapped", "()Lnet/minecraft/client/renderer/EntityRenderer;", "disableLightmap", "", "equals", "", "other", "", "isShaderActive", "loadShader", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "setupCameraTransform", "partialTicks", "", "pass", "", "setupOverlayRendering", "stopUseShader", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EntityRendererImpl.class */
public final class EntityRendererImpl implements IEntityRenderer {

    @NotNull
    private final EntityRenderer wrapped;

    @NotNull
    public final EntityRenderer getWrapped() {
        return this.wrapped;
    }

    public EntityRendererImpl(@NotNull EntityRenderer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    @Nullable
    public IShaderGroup getShaderGroup() {
        ShaderGroup shaderGroupFunc_147706_e = this.wrapped.func_147706_e();
        if (shaderGroupFunc_147706_e != null) {
            return new ShaderGroupImpl(shaderGroupFunc_147706_e);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    public void disableLightmap() {
        this.wrapped.func_175072_h();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    public boolean isShaderActive() {
        return this.wrapped.func_147702_a();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    public void loadShader(@NotNull IResourceLocation resourceLocation) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        this.wrapped.func_175069_a(((ResourceLocationImpl) resourceLocation).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    public void stopUseShader() {
        this.wrapped.func_181022_b();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    public void setupCameraTransform(float f, int i) {
        this.wrapped.func_78479_a(f, i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer
    public void setupOverlayRendering() {
        this.wrapped.func_78478_c();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof EntityRendererImpl) && Intrinsics.areEqual(((EntityRendererImpl) obj).wrapped, this.wrapped);
    }
}

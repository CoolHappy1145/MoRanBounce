package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IAbstractTexture;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.client.renderer.texture.TextureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0018\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/TextureManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "wrapped", "Lnet/minecraft/client/renderer/texture/TextureManager;", "(Lnet/minecraft/client/renderer/texture/TextureManager;)V", "getWrapped", "()Lnet/minecraft/client/renderer/texture/TextureManager;", "bindTexture", "", "image", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "equals", "", "other", "", "loadTexture", "textureLocation", "textureObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IAbstractTexture;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/TextureManagerImpl.class */
public final class TextureManagerImpl implements ITextureManager {

    @NotNull
    private final TextureManager wrapped;

    @NotNull
    public final TextureManager getWrapped() {
        return this.wrapped;
    }

    public TextureManagerImpl(@NotNull TextureManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager
    public boolean loadTexture(@NotNull IResourceLocation textureLocation, @NotNull IAbstractTexture textureObj) {
        Intrinsics.checkParameterIsNotNull(textureLocation, "textureLocation");
        Intrinsics.checkParameterIsNotNull(textureObj, "textureObj");
        return this.wrapped.func_110579_a(((ResourceLocationImpl) textureLocation).getWrapped(), ((AbstractTextureImpl) textureObj).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager
    public void bindTexture(@NotNull IResourceLocation image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        this.wrapped.func_110577_a(((ResourceLocationImpl) image).getWrapped());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof TextureManagerImpl) && Intrinsics.areEqual(((TextureManagerImpl) obj).wrapped, this.wrapped);
    }
}

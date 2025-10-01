package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IAbstractTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002R\u0013\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/AbstractTextureImpl;", "T", "Lnet/minecraft/client/renderer/texture/AbstractTexture;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IAbstractTexture;", "wrapped", "(Lnet/minecraft/client/renderer/texture/AbstractTexture;)V", "getWrapped", "()Lnet/minecraft/client/renderer/texture/AbstractTexture;", "Lnet/minecraft/client/renderer/texture/AbstractTexture;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/AbstractTextureImpl.class */
public class AbstractTextureImpl implements IAbstractTexture {

    @NotNull
    private final AbstractTexture wrapped;

    @NotNull
    public final AbstractTexture getWrapped() {
        return this.wrapped;
    }

    public AbstractTextureImpl(@NotNull AbstractTexture wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof AbstractTextureImpl) && Intrinsics.areEqual(((AbstractTextureImpl) obj).wrapped, this.wrapped);
    }
}

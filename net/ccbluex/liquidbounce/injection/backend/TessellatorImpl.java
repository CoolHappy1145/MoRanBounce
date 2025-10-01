package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/TessellatorImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "wrapped", "Lnet/minecraft/client/renderer/Tessellator;", "(Lnet/minecraft/client/renderer/Tessellator;)V", "worldRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "getWorldRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "getWrapped", "()Lnet/minecraft/client/renderer/Tessellator;", "draw", "", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/TessellatorImpl.class */
public final class TessellatorImpl implements ITessellator {

    @NotNull
    private final Tessellator wrapped;

    @NotNull
    public final Tessellator getWrapped() {
        return this.wrapped;
    }

    public TessellatorImpl(@NotNull Tessellator wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator
    @NotNull
    public IWorldRenderer getWorldRenderer() {
        BufferBuilder bufferBuilderFunc_178180_c = this.wrapped.func_178180_c();
        Intrinsics.checkExpressionValueIsNotNull(bufferBuilderFunc_178180_c, "wrapped.buffer");
        return new WorldRendererImpl(bufferBuilderFunc_178180_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator
    public void draw() {
        this.wrapped.func_78381_a();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof TessellatorImpl) && Intrinsics.areEqual(((TessellatorImpl) obj).wrapped, this.wrapped);
    }
}

package net.ccbluex.liquidbounce.injection.backend;

import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/VertexBufferImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/vertex/IVertexBuffer;", "wrapped", "Lnet/minecraft/client/renderer/vertex/VertexBuffer;", "(Lnet/minecraft/client/renderer/vertex/VertexBuffer;)V", "getWrapped", "()Lnet/minecraft/client/renderer/vertex/VertexBuffer;", "bindBuffer", "", "bufferData", "buffer", "Ljava/nio/ByteBuffer;", "deleteGlBuffers", "drawArrays", "mode", "", "equals", "", "other", "", "unbindBuffer", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/VertexBufferImpl.class */
public final class VertexBufferImpl implements IVertexBuffer {

    @NotNull
    private final VertexBuffer wrapped;

    @NotNull
    public final VertexBuffer getWrapped() {
        return this.wrapped;
    }

    public VertexBufferImpl(@NotNull VertexBuffer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer
    public void deleteGlBuffers() {
        this.wrapped.func_177362_c();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer
    public void bindBuffer() {
        this.wrapped.func_177359_a();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer
    public void drawArrays(int i) {
        this.wrapped.func_177358_a(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer
    public void unbindBuffer() {
        this.wrapped.func_177361_b();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer
    public void bufferData(@NotNull ByteBuffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        this.wrapped.func_181722_a(buffer);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof VertexBufferImpl) && Intrinsics.areEqual(((VertexBufferImpl) obj).wrapped, this.wrapped);
    }
}

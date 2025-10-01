package net.ccbluex.liquidbounce.injection.backend;

import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.vertex.IVertexFormat;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0010H\u0016J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0016J \u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!H\u0016J\b\u0010$\u001a\u00020\u0010H\u0016J\u0018\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020!H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006("}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/WorldRendererImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "wrapped", "Lnet/minecraft/client/renderer/BufferBuilder;", "(Lnet/minecraft/client/renderer/BufferBuilder;)V", "byteBuffer", "Ljava/nio/ByteBuffer;", "getByteBuffer", "()Ljava/nio/ByteBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "getVertexFormat", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "getWrapped", "()Lnet/minecraft/client/renderer/BufferBuilder;", "begin", "", "mode", "", "color", "red", "", "green", "blue", "alpha", "endVertex", "equals", "", "other", "", "finishDrawing", "pos", "x", "", "y", "z", "reset", "tex", "u", "v", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldRendererImpl.class */
public final class WorldRendererImpl implements IWorldRenderer {

    @NotNull
    private final BufferBuilder wrapped;

    @NotNull
    public final BufferBuilder getWrapped() {
        return this.wrapped;
    }

    public WorldRendererImpl(@NotNull BufferBuilder wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    @NotNull
    public ByteBuffer getByteBuffer() {
        ByteBuffer byteBufferFunc_178966_f = this.wrapped.func_178966_f();
        Intrinsics.checkExpressionValueIsNotNull(byteBufferFunc_178966_f, "wrapped.byteBuffer");
        return byteBufferFunc_178966_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    @NotNull
    public IVertexFormat getVertexFormat() {
        VertexFormat vertexFormatFunc_178973_g = this.wrapped.func_178973_g();
        Intrinsics.checkExpressionValueIsNotNull(vertexFormatFunc_178973_g, "wrapped.vertexFormat");
        return new VertexFormatImpl(vertexFormatFunc_178973_g);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    public void begin(int i, @NotNull IVertexFormat vertexFormat) {
        Intrinsics.checkParameterIsNotNull(vertexFormat, "vertexFormat");
        this.wrapped.func_181668_a(i, ((VertexFormatImpl) vertexFormat).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    @NotNull
    public IWorldRenderer pos(double d, double d2, double d3) {
        this.wrapped.func_181662_b(d, d2, d3);
        return this;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    public void endVertex() {
        this.wrapped.func_181675_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    @NotNull
    public IWorldRenderer tex(double d, double d2) {
        this.wrapped.func_187315_a(d, d2);
        return this;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    @NotNull
    public IWorldRenderer color(float f, float f2, float f3, float f4) {
        this.wrapped.func_181666_a(f, f2, f3, f4);
        return this;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    public void finishDrawing() {
        this.wrapped.func_178977_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer
    public void reset() {
        this.wrapped.func_178965_a();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof WorldRendererImpl) && Intrinsics.areEqual(((WorldRendererImpl) obj).wrapped, this.wrapped);
    }
}

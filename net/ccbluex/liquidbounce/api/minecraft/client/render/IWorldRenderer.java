package net.ccbluex.liquidbounce.api.minecraft.client.render;

import java.nio.ByteBuffer;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.render.vertex.IVertexFormat;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0007\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u0007H&J(\u0010\u000e\u001a\u00020\ufffd\ufffd2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0010H&J\b\u0010\u0014\u001a\u00020\u000bH&J\b\u0010\u0015\u001a\u00020\u000bH&J \u0010\u0016\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018H&J\b\u0010\u001b\u001a\u00020\u000bH&J\u0018\u0010\u001c\u001a\u00020\ufffd\ufffd2\u0006\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0018H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "", "byteBuffer", "Ljava/nio/ByteBuffer;", "getByteBuffer", "()Ljava/nio/ByteBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "getVertexFormat", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "begin", "", "mode", "", "color", "red", "", "green", "blue", "alpha", "endVertex", "finishDrawing", "pos", "x", "", "y", "z", "reset", "tex", "u", "v", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer.class */
public interface IWorldRenderer {
    @NotNull
    ByteBuffer getByteBuffer();

    @NotNull
    IVertexFormat getVertexFormat();

    void begin(int i, @NotNull IVertexFormat iVertexFormat);

    @NotNull
    IWorldRenderer pos(double d, double d2, double d3);

    void endVertex();

    @NotNull
    IWorldRenderer tex(double d, double d2);

    @NotNull
    IWorldRenderer color(float f, float f2, float f3, float f4);

    void finishDrawing();

    void reset();
}

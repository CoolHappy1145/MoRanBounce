package net.ccbluex.liquidbounce.injection.backend.utils;

import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/SafeVertexBuffer.class */
public class SafeVertexBuffer extends VertexBuffer {
    public SafeVertexBuffer(VertexFormat vertexFormat) {
        super(vertexFormat);
    }

    protected void finalize() {
        func_177362_c();
    }
}

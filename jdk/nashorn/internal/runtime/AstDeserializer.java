package jdk.nashorn.internal.runtime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.InflaterInputStream;
import jdk.nashorn.internal.p001ir.FunctionNode;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/AstDeserializer.class */
final class AstDeserializer {
    AstDeserializer() {
    }

    static FunctionNode deserialize(byte[] bArr) {
        try {
            return (FunctionNode) new ObjectInputStream(new InflaterInputStream(new ByteArrayInputStream(bArr))).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError("Unexpected exception deserializing function", e);
        }
    }
}

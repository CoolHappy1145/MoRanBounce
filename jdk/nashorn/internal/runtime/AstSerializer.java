package jdk.nashorn.internal.runtime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/AstSerializer.class */
final class AstSerializer {
    private static final int COMPRESSION_LEVEL = Options.getIntProperty("nashorn.serialize.compression", 4);

    AstSerializer() {
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ObjectOutputStream, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.ObjectOutputStream, java.lang.Throwable] */
    static byte[] serialize(FunctionNode functionNode) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(COMPRESSION_LEVEL);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new DeflaterOutputStream(byteArrayOutputStream, deflater));
            try {
                objectOutputStream.writeObject(functionNode);
                if (objectOutputStream != null) {
                    close();
                }
                deflater.end();
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th) {
                throw th;
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception serializing function", e);
        } catch (Throwable th2) {
            deflater.end();
            throw th2;
        }
    }
}

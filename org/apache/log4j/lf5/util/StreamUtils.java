package org.apache.log4j.lf5.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: L-out.jar:org/apache/log4j/lf5/util/StreamUtils.class */
public abstract class StreamUtils {
    public static final int DEFAULT_BUFFER_SIZE = 2048;

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        copy(inputStream, outputStream, 2048);
    }

    public static void copy(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = inputStream.read(bArr);
        while (true) {
            int i3 = i2;
            if (i3 != -1) {
                outputStream.write(bArr, 0, i3);
                i2 = inputStream.read(bArr);
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    public static void copyThenClose(InputStream inputStream, OutputStream outputStream) throws IOException {
        copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}

package com.thealtening.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: L-out.jar:com/thealtening/utils/Utilities.class */
public class Utilities {
    private static final Utilities INSTANCE = new Utilities();
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = 2147483639;

    public byte[] readAllBytes(InputStream inputStream) throws IOException {
        int i;
        byte[] bArrCopyOf = new byte[8192];
        int length = bArrCopyOf.length;
        int i2 = 0;
        while (true) {
            int i3 = inputStream.read(bArrCopyOf, i2, length - i2);
            if (i3 > 0) {
                i2 += i3;
            } else {
                if (i3 < 0) {
                    return length == i2 ? bArrCopyOf : Arrays.copyOf(bArrCopyOf, i2);
                }
                if (length <= MAX_BUFFER_SIZE - length) {
                    i = length << 1;
                } else {
                    if (length == MAX_BUFFER_SIZE) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    i = MAX_BUFFER_SIZE;
                }
                length = i;
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, length);
            }
        }
    }

    public static Utilities getInstance() {
        return INSTANCE;
    }
}

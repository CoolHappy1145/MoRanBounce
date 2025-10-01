package org.spongepowered.asm.util.asm;

import java.lang.reflect.Field;
import org.objectweb.asm.Opcodes;

/* loaded from: L-out.jar:org/spongepowered/asm/util/asm/ASM.class */
public final class ASM {
    private static int majorVersion = 5;
    private static int minorVersion = 0;
    private static String maxVersion = "FALLBACK";
    public static final int API_VERSION = detectVersion();

    private ASM() {
    }

    public static int getApiVersionMajor() {
        return majorVersion;
    }

    public static int getApiVersionMinor() {
        return minorVersion;
    }

    public static String getApiVersionString() {
        return String.format("ASM %d.%d (%s)", Integer.valueOf(majorVersion), Integer.valueOf(minorVersion), maxVersion);
    }

    private static int detectVersion() throws IllegalAccessException, IllegalArgumentException {
        int i = 262144;
        for (Field field : Opcodes.class.getDeclaredFields()) {
            if (field.getType() == Integer.TYPE && field.getName().startsWith("ASM")) {
                try {
                    int i2 = field.getInt(null);
                    int i3 = (i2 >> 8) & 255;
                    int i4 = (i2 >> 16) & 255;
                    boolean z = ((i2 >> 24) & 255) != 0;
                    if (i4 >= majorVersion) {
                        maxVersion = field.getName();
                        if (!z) {
                            i = i2;
                            majorVersion = i4;
                            minorVersion = i3;
                        }
                    }
                } catch (ReflectiveOperationException e) {
                    throw new Error(e);
                }
            }
        }
        return i;
    }
}

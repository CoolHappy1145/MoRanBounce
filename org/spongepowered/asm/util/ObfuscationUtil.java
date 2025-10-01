package org.spongepowered.asm.util;

/* loaded from: L-out.jar:org/spongepowered/asm/util/ObfuscationUtil.class */
public abstract class ObfuscationUtil {

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ObfuscationUtil$IClassRemapper.class */
    public interface IClassRemapper {
        String map(String str);

        String unmap(String str);
    }

    private ObfuscationUtil() {
    }

    public static String mapDescriptor(String str, IClassRemapper iClassRemapper) {
        return remapDescriptor(str, iClassRemapper, false);
    }

    public static String unmapDescriptor(String str, IClassRemapper iClassRemapper) {
        return remapDescriptor(str, iClassRemapper, true);
    }

    private static String remapDescriptor(String str, IClassRemapper iClassRemapper, boolean z) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = null;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (sb2 != null) {
                if (cCharAt == ';') {
                    sb.append('L').append(remap(sb2.toString(), iClassRemapper, z)).append(';');
                    sb2 = null;
                } else {
                    sb2.append(cCharAt);
                }
            } else if (cCharAt == 'L') {
                sb2 = new StringBuilder();
            } else {
                sb.append(cCharAt);
            }
        }
        if (sb2 != null) {
            throw new IllegalArgumentException("Invalid descriptor '" + str + "', missing ';'");
        }
        return sb.toString();
    }

    private static Object remap(String str, IClassRemapper iClassRemapper, boolean z) {
        String strUnmap = z ? iClassRemapper.unmap(str) : iClassRemapper.map(str);
        return strUnmap != null ? strUnmap : str;
    }
}

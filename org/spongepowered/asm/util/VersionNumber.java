package org.spongepowered.asm.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: L-out.jar:org/spongepowered/asm/util/VersionNumber.class */
public final class VersionNumber implements Comparable, Serializable {
    private static final long serialVersionUID = 1;
    public static final VersionNumber NONE = new VersionNumber();
    private static final Pattern PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
    private final long value;
    private final String suffix;

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((VersionNumber) obj);
    }

    private VersionNumber() {
        this.value = 0L;
        this.suffix = "";
    }

    private VersionNumber(short[] sArr) {
        this(sArr, null);
    }

    private VersionNumber(short[] sArr, String str) {
        this.value = (sArr[0] << 48) | (sArr[1] << 32) | (sArr[2] << 16) | sArr[3];
        this.suffix = str != null ? str : "";
    }

    private VersionNumber(short s, short s2, short s3, short s4) {
        this(s, s2, s3, s4, null);
    }

    private VersionNumber(short s, short s2, short s3, short s4, String str) {
        short[] sArr = {s, s2, s3, s4};
        this.value = (sArr[0] << 48) | (sArr[1] << 32) | (sArr[2] << 16) | sArr[3];
        this.suffix = str != null ? str : "";
    }

    public String toString() {
        long j = this.value;
        short[] sArr = {(short) (j >> 48), (short) ((j >> 32) & 32767), (short) ((j >> 16) & 32767), (short) (j & 32767)};
        Object[] objArr = new Object[5];
        objArr[0] = Short.valueOf(sArr[0]);
        objArr[1] = Short.valueOf(sArr[1]);
        objArr[2] = (this.value & 2147483647L) > 0 ? String.format(".%d", Short.valueOf(sArr[2])) : "";
        objArr[3] = (this.value & 32767) > 0 ? String.format(".%d", Short.valueOf(sArr[3])) : "";
        objArr[4] = this.suffix;
        return String.format("%d.%d%3$s%4$s%5$s", objArr);
    }

    public int compareTo(VersionNumber versionNumber) {
        if (versionNumber == null) {
            return 1;
        }
        long j = this.value - versionNumber.value;
        if (j > 0) {
            return 1;
        }
        return j < 0 ? -1 : 0;
    }

    public boolean equals(Object obj) {
        return (obj instanceof VersionNumber) && ((VersionNumber) obj).value == this.value;
    }

    public int hashCode() {
        return ((int) (this.value >> 32)) ^ ((int) (this.value & JSType.MAX_UINT));
    }

    public static VersionNumber parse(String str) {
        return parse(str, NONE);
    }

    public static VersionNumber parse(String str, String str2) {
        return parse(str, parse(str2));
    }

    private static VersionNumber parse(String str, VersionNumber versionNumber) throws NumberFormatException {
        if (str == null) {
            return versionNumber;
        }
        Matcher matcher = PATTERN.matcher(str);
        if (!matcher.matches()) {
            return versionNumber;
        }
        short[] sArr = new short[4];
        for (int i = 0; i < 4; i++) {
            String strGroup = matcher.group(i + 1);
            if (strGroup != null) {
                int i2 = Integer.parseInt(strGroup);
                if (i2 > 32767) {
                    throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + i2);
                }
                sArr[i] = (short) i2;
            }
        }
        return new VersionNumber(sArr, matcher.group(5));
    }
}

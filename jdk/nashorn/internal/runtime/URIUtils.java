package jdk.nashorn.internal.runtime;

import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/URIUtils.class */
public final class URIUtils {
    private static final String URI_UNESCAPED_NONALPHANUMERIC = "-_.!~*'()";
    private static final String URI_RESERVED = ";/?:@&=+$,#";

    private URIUtils() {
    }

    static String encodeURI(Object obj, String str) {
        return encode(obj, str, false);
    }

    static String encodeURIComponent(Object obj, String str) {
        return encode(obj, str, true);
    }

    static String decodeURI(Object obj, String str) {
        return decode(obj, str, false);
    }

    static String decodeURIComponent(Object obj, String str) {
        return decode(obj, str, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v34, types: [int] */
    private static String encode(Object obj, String str, boolean z) {
        char c;
        if (str.isEmpty()) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (isUnescaped(cCharAt, z)) {
                sb.append(cCharAt);
            } else {
                if (cCharAt >= '\udc00' && cCharAt <= '\udfff') {
                    return error(str, i);
                }
                if (cCharAt < '\ud800' || cCharAt > '\udbff') {
                    c = cCharAt;
                } else {
                    i++;
                    if (i == length) {
                        return error(str, i);
                    }
                    char cCharAt2 = str.charAt(i);
                    if (cCharAt2 < '\udc00' || cCharAt2 > '\udfff') {
                        return error(str, i);
                    }
                    c = ((cCharAt - '\ud800') * 1024) + (cCharAt2 - CharCompanionObject.MIN_LOW_SURROGATE) + 65536;
                }
                try {
                    sb.append(toHexEscape(c));
                } catch (Exception e) {
                    throw ECMAErrors.uriError(e, "bad.uri", new String[]{str, Integer.toString(i)});
                }
            }
            i++;
        }
        return sb.toString();
    }

    private static String decode(Object obj, String str, boolean z) {
        int i;
        int i2;
        int i3;
        if (str.isEmpty()) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int i4 = 0;
        while (i4 < length) {
            char cCharAt = str.charAt(i4);
            if (cCharAt != '%') {
                sb.append(cCharAt);
            } else {
                int i5 = i4;
                if (i4 + 2 >= length) {
                    return error(str, i4);
                }
                int hexByte = toHexByte(str.charAt(i4 + 1), str.charAt(i4 + 2));
                if (hexByte < 0) {
                    return error(str, i4 + 1);
                }
                i4 += 2;
                if ((hexByte & 128) == 0) {
                    char c = (char) hexByte;
                    if (!z && URI_RESERVED.indexOf(c) >= 0) {
                        for (int i6 = i5; i6 <= i4; i6++) {
                            sb.append(str.charAt(i6));
                        }
                    } else {
                        sb.append(c);
                    }
                } else {
                    if ((hexByte & 192) == 128) {
                        return error(str, i4);
                    }
                    if ((hexByte & 32) == 0) {
                        i = 2;
                        i2 = hexByte & 31;
                        i3 = 128;
                    } else if ((hexByte & 16) == 0) {
                        i = 3;
                        i2 = hexByte & 15;
                        i3 = 2048;
                    } else if ((hexByte & 8) == 0) {
                        i = 4;
                        i2 = hexByte & 7;
                        i3 = 65536;
                    } else if ((hexByte & 4) == 0) {
                        i = 5;
                        i2 = hexByte & 3;
                        i3 = 2097152;
                    } else if ((hexByte & 2) == 0) {
                        i = 6;
                        i2 = hexByte & 1;
                        i3 = 67108864;
                    } else {
                        return error(str, i4);
                    }
                    if (i4 + (3 * (i - 1)) >= length) {
                        return error(str, i4);
                    }
                    for (int i7 = 1; i7 < i; i7++) {
                        int i8 = i4 + 1;
                        if (str.charAt(i8) != '%') {
                            return error(str, i8);
                        }
                        int hexByte2 = toHexByte(str.charAt(i8 + 1), str.charAt(i8 + 2));
                        if (hexByte2 < 0 || (hexByte2 & 192) != 128) {
                            return error(str, i8 + 1);
                        }
                        i2 = (i2 << 6) | (hexByte2 & 63);
                        i4 = i8 + 2;
                    }
                    if (i2 < i3 || (i2 >= 55296 && i2 <= 57343)) {
                        i2 = Integer.MAX_VALUE;
                    }
                    if (i2 < 65536) {
                        char c2 = (char) i2;
                        if (!z && URI_RESERVED.indexOf(c2) >= 0) {
                            for (int i9 = i5; i9 != i4; i9++) {
                                sb.append(str.charAt(i9));
                            }
                        } else {
                            sb.append(c2);
                        }
                    } else {
                        if (i2 > 1114111) {
                            return error(str, i4);
                        }
                        int i10 = ((i2 - 65536) & 1023) + CharCompanionObject.MIN_LOW_SURROGATE;
                        sb.append((char) ((((i2 - 65536) >> 10) & 1023) + 55296));
                        sb.append((char) i10);
                    }
                }
            }
            i4++;
        }
        return sb.toString();
    }

    private static int hexDigit(char c) {
        char upperCase = Character.toUpperCase(c);
        if (upperCase >= '0' && upperCase <= '9') {
            return upperCase - '0';
        }
        if (upperCase >= 'A' && upperCase <= 'F') {
            return (upperCase - 'A') + 10;
        }
        return -1;
    }

    private static int toHexByte(char c, char c2) {
        int iHexDigit = hexDigit(c);
        int iHexDigit2 = hexDigit(c2);
        if (iHexDigit >= 0 && iHexDigit2 >= 0) {
            return (iHexDigit << 4) | iHexDigit2;
        }
        return -1;
    }

    private static String toHexEscape(int i) {
        int i2;
        int i3 = i;
        byte[] bArr = new byte[6];
        if (i3 <= 127) {
            bArr[0] = (byte) i3;
            i2 = 1;
        } else {
            i2 = 2;
            int i4 = i3;
            int i5 = 11;
            while (true) {
                int i6 = i4 >>> i5;
                if (i6 == 0) {
                    break;
                }
                i2++;
                i4 = i6;
                i5 = 5;
            }
            for (int i7 = i2 - 1; i7 > 0; i7--) {
                bArr[i7] = (byte) (128 | (i3 & 63));
                i3 >>>= 6;
            }
            bArr[0] = (byte) ((((1 << (8 - i2)) - 1) ^ (-1)) | i3);
        }
        StringBuilder sb = new StringBuilder();
        for (int i8 = 0; i8 < i2; i8++) {
            sb.append('%');
            if ((bArr[i8] & 255) < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(bArr[i8] & 255).toUpperCase());
        }
        return sb.toString();
    }

    private static String error(String str, int i) {
        throw ECMAErrors.uriError("bad.uri", new String[]{str, Integer.toString(i)});
    }

    private static boolean isUnescaped(char c, boolean z) {
        if ('A' <= c && c <= 'Z') {
            return true;
        }
        if ('a' <= c && c <= 'z') {
            return true;
        }
        if (('0' > c || c > '9') && URI_UNESCAPED_NONALPHANUMERIC.indexOf(c) < 0) {
            return !z && URI_RESERVED.indexOf(c) >= 0;
        }
        return true;
    }
}

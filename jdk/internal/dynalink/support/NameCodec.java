package jdk.internal.dynalink.support;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/NameCodec.class */
public class NameCodec {
    private static final char ESCAPE_CHAR = '\\';
    private static final char EMPTY_ESCAPE = '=';
    private static final String EMPTY_NAME;
    private static final char EMPTY_CHAR = '\ufeff';
    private static final int MIN_ENCODING = 36;
    private static final int MAX_ENCODING = 93;
    private static final char[] ENCODING;
    private static final int MIN_DECODING = 33;
    private static final int MAX_DECODING = 125;
    private static final char[] DECODING;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NameCodec.class.desiredAssertionStatus();
        EMPTY_NAME = "\\=";
        ENCODING = new char[58];
        DECODING = new char[MAX_ENCODING];
        addEncoding('/', '|');
        addEncoding('.', ',');
        addEncoding(';', '?');
        addEncoding('$', '%');
        addEncoding('<', '^');
        addEncoding('>', '_');
        addEncoding('[', '{');
        addEncoding(']', '}');
        addEncoding(':', '!');
        addEncoding('\\', '-');
        DECODING[28] = '\ufeff';
    }

    private NameCodec() {
    }

    public static String encode(String str) {
        char c;
        int length = str.length();
        if (length == 0) {
            return EMPTY_NAME;
        }
        StringBuilder sb = null;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            int iCharAt = str.charAt(i2) - '$';
            if (iCharAt >= 0 && iCharAt < ENCODING.length && (c = ENCODING[iCharAt]) != 0) {
                if (sb == null) {
                    sb = new StringBuilder(str.length() + 3);
                    if (str.charAt(0) != ESCAPE_CHAR && i2 > 0) {
                        sb.append(EMPTY_NAME);
                    }
                    sb.append((CharSequence) str, 0, i2);
                } else {
                    sb.append((CharSequence) str, i + 1, i2);
                }
                sb.append('\\').append(c);
                i = i2;
            }
        }
        if (sb == null) {
            return str.toString();
        }
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        sb.append((CharSequence) str, i + 1, length);
        return sb.toString();
    }

    public static String decode(String str) {
        if (str.charAt(0) != ESCAPE_CHAR) {
            return str;
        }
        int length = str.length();
        if (length == 2 && str.charAt(1) == EMPTY_CHAR) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.length());
        int i = -2;
        int i2 = -1;
        while (true) {
            int iIndexOf = str.indexOf(ESCAPE_CHAR, i2 + 1);
            if (iIndexOf == -1 || iIndexOf == length - 1) {
                break;
            }
            int iCharAt = str.charAt(iIndexOf + 1) - '!';
            if (iCharAt >= 0 && iCharAt < DECODING.length) {
                char c = DECODING[iCharAt];
                if (c == EMPTY_CHAR) {
                    if (iIndexOf == 0) {
                        i = 0;
                    }
                } else if (c != 0) {
                    sb.append((CharSequence) str, i + 2, iIndexOf).append(c);
                    i = iIndexOf;
                }
            }
            i2 = iIndexOf;
        }
        sb.append((CharSequence) str, i + 2, length);
        return sb.toString();
    }

    private static void addEncoding(char c, char c2) {
        ENCODING[c - '$'] = c2;
        DECODING[c2 - '!'] = c;
    }
}

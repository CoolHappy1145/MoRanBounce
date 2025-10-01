package jdk.nashorn.internal.parser;

import jdk.nashorn.internal.runtime.Source;

/* loaded from: L-out.jar:jdk/nashorn/internal/parser/Token.class */
public class Token {
    private Token() {
    }

    public static long toDesc(TokenType tokenType, int i, int i2) {
        return (i << 32) | (i2 << 8) | tokenType.ordinal();
    }

    /* renamed from: jdk.nashorn.internal.parser.Token$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/parser/Token$1.class */
    static /* synthetic */ class C02041 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ESCSTRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EXECSTRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static long withDelimiter(long j) {
        TokenType tokenTypeDescType = descType(j);
        switch (C02041.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenTypeDescType.ordinal()]) {
            case 1:
            case 2:
            case 3:
                return toDesc(tokenTypeDescType, ((int) (j >>> 32)) - 1, (((int) j) >>> 8) + 2);
            default:
                return j;
        }
    }

    public static TokenType descType(long j) {
        return TokenType.getValues()[((int) j) & 255];
    }

    public static long recast(long j, TokenType tokenType) {
        return (j & (-256)) | tokenType.ordinal();
    }

    public static String toString(Source source, long j, boolean z) {
        String nameOrType;
        TokenType tokenTypeDescType = descType(j);
        if (source != null && tokenTypeDescType.getKind() == TokenKind.LITERAL) {
            nameOrType = source.getString(j);
        } else {
            nameOrType = tokenTypeDescType.getNameOrType();
        }
        if (z) {
            nameOrType = nameOrType + " (" + ((int) (j >>> 32)) + ", " + (((int) j) >>> 8) + ")";
        }
        return nameOrType;
    }

    public static String toString(Source source, long j) {
        return toString(source, j, false);
    }

    public static String toString(long j) {
        return toString(null, j, false);
    }
}

package jdk.nashorn.internal.parser;

/* loaded from: L-out.jar:jdk/nashorn/internal/parser/TokenLookup.class */
public final class TokenLookup {
    private static final TokenType[] table;
    private static final int tableBase = 32;
    private static final int tableLimit = 126;
    private static final int tableLength = 95;
    static final boolean $assertionsDisabled;

    static {
        TokenType tokenType;
        $assertionsDisabled = !TokenLookup.class.desiredAssertionStatus();
        table = new TokenType[tableLength];
        for (TokenType tokenType2 : TokenType.getValues()) {
            String name = tokenType2.getName();
            if (name != null && tokenType2.getKind() != TokenKind.SPECIAL) {
                int iCharAt = name.charAt(0) - ' ';
                if (!$assertionsDisabled && iCharAt >= tableLength) {
                    throw new AssertionError("Token name does not fit lookup table");
                }
                int length = tokenType2.getLength();
                TokenType tokenType3 = null;
                TokenType next = table[iCharAt];
                while (true) {
                    tokenType = next;
                    if (tokenType == null || tokenType.getLength() <= length) {
                        break;
                    }
                    tokenType3 = tokenType;
                    next = tokenType.getNext();
                }
                tokenType2.setNext(tokenType);
                if (tokenType3 == null) {
                    table[iCharAt] = tokenType2;
                } else {
                    tokenType3.setNext(tokenType2);
                }
            }
        }
    }

    private TokenLookup() {
    }

    public static TokenType lookupKeyword(char[] cArr, int i, int i2) {
        if (!$assertionsDisabled && table == null) {
            throw new AssertionError("Token lookup table is not initialized");
        }
        char c = cArr[i];
        if ('a' <= c && c <= 'z') {
            TokenType next = table[c - ' '];
            while (true) {
                TokenType tokenType = next;
                if (tokenType == null) {
                    break;
                }
                int length = tokenType.getLength();
                if (length == i2) {
                    String name = tokenType.getName();
                    int i3 = 0;
                    while (i3 < i2 && cArr[i + i3] == name.charAt(i3)) {
                        i3++;
                    }
                    if (i3 == i2) {
                        return tokenType;
                    }
                } else if (length < i2) {
                    break;
                }
                next = tokenType.getNext();
            }
        }
        return TokenType.IDENT;
    }

    public static TokenType lookupOperator(char c, char c2, char c3, char c4) {
        if (!$assertionsDisabled && table == null) {
            throw new AssertionError("Token lookup table is not initialized");
        }
        if (' ' >= c || c > tableLimit) {
            return null;
        }
        if ('a' > c || c > 'z') {
            TokenType next = table[c - ' '];
            while (true) {
                TokenType tokenType = next;
                if (tokenType != null) {
                    String name = tokenType.getName();
                    switch (name.length()) {
                        case 1:
                            return tokenType;
                        case 2:
                            if (name.charAt(1) != c2) {
                                break;
                            } else {
                                return tokenType;
                            }
                        case 3:
                            if (name.charAt(1) != c2 || name.charAt(2) != c3) {
                                break;
                            } else {
                                return tokenType;
                            }
                        case 4:
                            if (name.charAt(1) != c2 || name.charAt(2) != c3 || name.charAt(3) != c4) {
                                break;
                            } else {
                                return tokenType;
                            }
                            break;
                    }
                    next = tokenType.getNext();
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }
}

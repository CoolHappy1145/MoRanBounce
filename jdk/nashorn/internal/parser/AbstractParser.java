package jdk.nashorn.internal.parser;

import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;

/* loaded from: L-out.jar:jdk/nashorn/internal/parser/AbstractParser.class */
public abstract class AbstractParser {
    protected final Source source;
    protected final ErrorManager errors;
    protected TokenStream stream;
    protected long previousToken;
    protected int start;
    protected int finish;
    protected int line;
    protected int linePosition;
    protected Lexer lexer;
    protected boolean isStrictMode;
    protected final int lineOffset;
    private static final String SOURCE_URL_PREFIX = "sourceURL=";
    static final boolean $assertionsDisabled;
    private final Map canonicalNames = new HashMap();

    /* renamed from: k */
    protected int f35k = -1;
    protected long token = Token.toDesc(TokenType.EOL, 0, 1);
    protected TokenType type = TokenType.EOL;
    protected TokenType last = TokenType.EOL;

    static {
        $assertionsDisabled = !AbstractParser.class.desiredAssertionStatus();
    }

    protected AbstractParser(Source source, ErrorManager errorManager, boolean z, int i) {
        this.source = source;
        this.errors = errorManager;
        this.isStrictMode = z;
        this.lineOffset = i;
    }

    protected final long getToken(int i) {
        while (i > this.stream.last()) {
            if (this.stream.isFull()) {
                this.stream.grow();
            }
            this.lexer.lexify();
        }
        return this.stream.get(i);
    }

    /* renamed from: T */
    protected final TokenType m10T(int i) {
        return Token.descType(getToken(i));
    }

    protected final TokenType next() {
        while (true) {
            nextOrEOL();
            if (this.type != TokenType.EOL && this.type != TokenType.COMMENT) {
                return this.type;
            }
        }
    }

    protected final TokenType nextOrEOL() {
        while (true) {
            nextToken();
            if (this.type == TokenType.DIRECTIVE_COMMENT) {
                checkDirectiveComment();
            }
            if (this.type != TokenType.COMMENT && this.type != TokenType.DIRECTIVE_COMMENT) {
                return this.type;
            }
        }
    }

    private void checkDirectiveComment() {
        if (this.source.getExplicitURL() != null) {
            return;
        }
        String str = (String) this.lexer.getValueOf(this.token, this.isStrictMode);
        if (str.length() > 4 && str.substring(4).startsWith(SOURCE_URL_PREFIX)) {
            this.source.setExplicitURL(str.substring(14));
        }
    }

    private TokenType nextToken() {
        if (this.type != TokenType.COMMENT) {
            this.last = this.type;
        }
        if (this.type != TokenType.EOF) {
            this.f35k++;
            long j = this.token;
            this.previousToken = this.token;
            this.token = getToken(this.f35k);
            this.type = Token.descType(this.token);
            if (this.last != TokenType.EOL) {
                this.finish = this.start + (((int) j) >>> 8);
            }
            if (this.type == TokenType.EOL) {
                this.line = ((int) this.token) >>> 8;
                this.linePosition = (int) (this.token >>> 32);
            } else {
                this.start = (int) (this.token >>> 32);
            }
        }
        return this.type;
    }

    protected static String message(String str, String[] strArr) {
        return ECMAErrors.getMessage("parser.error." + str, strArr);
    }

    protected final ParserException error(String str, long j) {
        return error(JSErrorType.SYNTAX_ERROR, str, j);
    }

    protected final ParserException error(JSErrorType jSErrorType, String str, long j) {
        int i = (int) (j >>> 32);
        int line = this.source.getLine(i);
        int column = this.source.getColumn(i);
        return new ParserException(jSErrorType, ErrorManager.format(str, this.source, line, column, j), this.source, line, column, j);
    }

    protected final ParserException error(String str) {
        return error(JSErrorType.SYNTAX_ERROR, str);
    }

    protected final ParserException error(JSErrorType jSErrorType, String str) {
        int i = ((int) (this.token >>> 32)) - this.linePosition;
        return new ParserException(jSErrorType, ErrorManager.format(str, this.source, this.line, i, this.token), this.source, this.line, i, this.token);
    }

    protected final void warning(JSErrorType jSErrorType, String str, long j) {
        this.errors.warning(error(jSErrorType, str, j));
    }

    protected final String expectMessage(TokenType tokenType) {
        String strMessage;
        String string = Token.toString(this.source, this.token);
        if (tokenType == null) {
            strMessage = message("expected.stmt", new String[]{string});
        } else {
            strMessage = message("expected", new String[]{tokenType.getNameOrType(), string});
        }
        return strMessage;
    }

    protected final void expect(TokenType tokenType) {
        expectDontAdvance(tokenType);
        next();
    }

    protected final void expectDontAdvance(TokenType tokenType) {
        if (this.type != tokenType) {
            throw error(expectMessage(tokenType));
        }
    }

    protected final Object expectValue(TokenType tokenType) {
        if (this.type != tokenType) {
            throw error(expectMessage(tokenType));
        }
        Object value = getValue();
        next();
        return value;
    }

    protected final Object getValue() {
        return getValue(this.token);
    }

    protected final Object getValue(long j) {
        try {
            return this.lexer.getValueOf(j, this.isStrictMode);
        } catch (ParserException e) {
            this.errors.error(e);
            return null;
        }
    }

    protected final boolean isNonStrictModeIdent() {
        return !this.isStrictMode && this.type.getKind() == TokenKind.FUTURESTRICT;
    }

    protected final IdentNode getIdent() {
        long j = this.token;
        if (isNonStrictModeIdent()) {
            long jRecast = Token.recast(this.token, TokenType.IDENT);
            String str = (String) getValue(jRecast);
            next();
            return createIdentNode(jRecast, this.finish, str).setIsFutureStrictName();
        }
        String str2 = (String) expectValue(TokenType.IDENT);
        if (str2 == null) {
            return null;
        }
        return createIdentNode(j, this.finish, str2);
    }

    protected IdentNode createIdentNode(long j, int i, String str) {
        String str2 = (String) this.canonicalNames.putIfAbsent(str, str);
        return new IdentNode(j, i, str2 != null ? str2 : str);
    }

    protected final boolean isIdentifierName() {
        TokenKind kind = this.type.getKind();
        if (kind == TokenKind.KEYWORD || kind == TokenKind.FUTURE || kind == TokenKind.FUTURESTRICT) {
            return true;
        }
        if (kind == TokenKind.LITERAL) {
            switch (C01991.$SwitchMap$jdk$nashorn$internal$parser$TokenType[this.type.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return true;
                default:
                    return false;
            }
        }
        String str = (String) getValue(Token.recast(this.token, TokenType.IDENT));
        return !str.isEmpty() && Character.isJavaIdentifierStart(str.charAt(0));
    }

    /* renamed from: jdk.nashorn.internal.parser.AbstractParser$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/parser/AbstractParser$1.class */
    static /* synthetic */ class C01991 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.FALSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.TRUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected final IdentNode getIdentifierName() {
        if (this.type == TokenType.IDENT) {
            return getIdent();
        }
        if (isIdentifierName()) {
            long jRecast = Token.recast(this.token, TokenType.IDENT);
            String str = (String) getValue(jRecast);
            next();
            return createIdentNode(jRecast, this.finish, str);
        }
        expect(TokenType.IDENT);
        return null;
    }

    protected final LiteralNode getLiteral() throws ParserException {
        long j = this.token;
        Object value = getValue();
        next();
        LiteralNode literalNodeNewInstance = null;
        if (value == null) {
            literalNodeNewInstance = LiteralNode.newInstance(j, this.finish);
        } else if (value instanceof Number) {
            literalNodeNewInstance = LiteralNode.newInstance(j, this.finish, (Number) value);
        } else if (value instanceof String) {
            literalNodeNewInstance = LiteralNode.newInstance(j, this.finish, (String) value);
        } else if (value instanceof Lexer.LexerToken) {
            if (value instanceof Lexer.RegexToken) {
                Lexer.RegexToken regexToken = (Lexer.RegexToken) value;
                try {
                    RegExpFactory.validate(regexToken.getExpression(), regexToken.getOptions());
                } catch (ParserException e) {
                    throw error(e.getMessage());
                }
            }
            literalNodeNewInstance = LiteralNode.newInstance(j, this.finish, (Lexer.LexerToken) value);
        } else if (!$assertionsDisabled) {
            throw new AssertionError("unknown type for LiteralNode: " + value.getClass());
        }
        return literalNodeNewInstance;
    }
}

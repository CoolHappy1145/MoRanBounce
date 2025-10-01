package jdk.nashorn.internal.parser;

import java.util.Locale;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.tools.Shell;
import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LocationInfo;
import org.slf4j.Marker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Found several "values" enum fields: [] */
/* loaded from: L-out.jar:jdk/nashorn/internal/parser/TokenType.class */
public final class TokenType {
    public static final TokenType ERROR;
    public static final TokenType EOF;
    public static final TokenType EOL;
    public static final TokenType COMMENT;
    public static final TokenType DIRECTIVE_COMMENT;
    public static final TokenType NOT;

    /* renamed from: NE */
    public static final TokenType f39NE;
    public static final TokenType NE_STRICT;
    public static final TokenType MOD;
    public static final TokenType ASSIGN_MOD;
    public static final TokenType BIT_AND;
    public static final TokenType AND;
    public static final TokenType ASSIGN_BIT_AND;
    public static final TokenType LPAREN;
    public static final TokenType RPAREN;
    public static final TokenType MUL;
    public static final TokenType ASSIGN_MUL;
    public static final TokenType ADD;
    public static final TokenType INCPREFIX;
    public static final TokenType ASSIGN_ADD;
    public static final TokenType COMMARIGHT;
    public static final TokenType SUB;
    public static final TokenType DECPREFIX;
    public static final TokenType ASSIGN_SUB;
    public static final TokenType PERIOD;
    public static final TokenType DIV;
    public static final TokenType ASSIGN_DIV;
    public static final TokenType COLON;
    public static final TokenType SEMICOLON;

    /* renamed from: LT */
    public static final TokenType f40LT;
    public static final TokenType SHL;
    public static final TokenType ASSIGN_SHL;

    /* renamed from: LE */
    public static final TokenType f41LE;
    public static final TokenType ASSIGN;

    /* renamed from: EQ */
    public static final TokenType f42EQ;
    public static final TokenType EQ_STRICT;
    public static final TokenType BIND;

    /* renamed from: GT */
    public static final TokenType f43GT;

    /* renamed from: GE */
    public static final TokenType f44GE;
    public static final TokenType SAR;
    public static final TokenType ASSIGN_SAR;
    public static final TokenType SHR;
    public static final TokenType ASSIGN_SHR;
    public static final TokenType TERNARY;
    public static final TokenType LBRACKET;
    public static final TokenType RBRACKET;
    public static final TokenType BIT_XOR;
    public static final TokenType ASSIGN_BIT_XOR;
    public static final TokenType LBRACE;
    public static final TokenType BIT_OR;
    public static final TokenType ASSIGN_BIT_OR;

    /* renamed from: OR */
    public static final TokenType f45OR;
    public static final TokenType RBRACE;
    public static final TokenType BIT_NOT;
    public static final TokenType BREAK;
    public static final TokenType CASE;
    public static final TokenType CATCH;
    public static final TokenType CLASS;
    public static final TokenType CONST;
    public static final TokenType CONTINUE;
    public static final TokenType DEBUGGER;
    public static final TokenType DEFAULT;
    public static final TokenType DELETE;

    /* renamed from: DO */
    public static final TokenType f46DO;
    public static final TokenType ELSE;
    public static final TokenType ENUM;
    public static final TokenType EXPORT;
    public static final TokenType EXTENDS;
    public static final TokenType FALSE;
    public static final TokenType FINALLY;
    public static final TokenType FOR;
    public static final TokenType FUNCTION;

    /* renamed from: IF */
    public static final TokenType f47IF;
    public static final TokenType IMPLEMENTS;
    public static final TokenType IMPORT;

    /* renamed from: IN */
    public static final TokenType f48IN;
    public static final TokenType INSTANCEOF;
    public static final TokenType INTERFACE;
    public static final TokenType LET;
    public static final TokenType NEW;
    public static final TokenType NULL;
    public static final TokenType PACKAGE;
    public static final TokenType PRIVATE;
    public static final TokenType PROTECTED;
    public static final TokenType PUBLIC;
    public static final TokenType RETURN;
    public static final TokenType STATIC;
    public static final TokenType SUPER;
    public static final TokenType SWITCH;
    public static final TokenType THIS;
    public static final TokenType THROW;
    public static final TokenType TRUE;
    public static final TokenType TRY;
    public static final TokenType TYPEOF;
    public static final TokenType VAR;
    public static final TokenType VOID;
    public static final TokenType WHILE;
    public static final TokenType WITH;
    public static final TokenType YIELD;
    public static final TokenType DECIMAL;
    public static final TokenType OCTAL;
    public static final TokenType HEXADECIMAL;
    public static final TokenType FLOATING;
    public static final TokenType STRING;
    public static final TokenType ESCSTRING;
    public static final TokenType EXECSTRING;
    public static final TokenType IDENT;
    public static final TokenType REGEX;
    public static final TokenType XML;
    public static final TokenType OBJECT;
    public static final TokenType ARRAY;
    public static final TokenType COMMALEFT;
    public static final TokenType DECPOSTFIX;
    public static final TokenType INCPOSTFIX;
    private TokenType next;
    private final TokenKind kind;
    private final String name;
    private final int precedence;
    private final boolean isLeftAssociative;
    private static final TokenType[] values;
    private static final TokenType[] $VALUES;
    static final boolean $assertionsDisabled;

    public static TokenType[] values() {
        return (TokenType[]) $VALUES.clone();
    }

    public static TokenType valueOf(String str) {
        return (TokenType) Enum.valueOf(TokenType.class, str);
    }

    static {
        $assertionsDisabled = !TokenType.class.desiredAssertionStatus();
        ERROR = new TokenType("ERROR", 0, TokenKind.SPECIAL, null);
        EOF = new TokenType("EOF", 1, TokenKind.SPECIAL, null);
        EOL = new TokenType("EOL", 2, TokenKind.SPECIAL, null);
        COMMENT = new TokenType("COMMENT", 3, TokenKind.SPECIAL, null);
        DIRECTIVE_COMMENT = new TokenType("DIRECTIVE_COMMENT", 4, TokenKind.SPECIAL, null);
        NOT = new TokenType("NOT", 5, TokenKind.UNARY, "!", 14, false);
        f39NE = new TokenType("NE", 6, TokenKind.BINARY, "!=", 9, true);
        NE_STRICT = new TokenType("NE_STRICT", 7, TokenKind.BINARY, "!==", 9, true);
        MOD = new TokenType("MOD", 8, TokenKind.BINARY, "%", 13, true);
        ASSIGN_MOD = new TokenType("ASSIGN_MOD", 9, TokenKind.BINARY, "%=", 2, false);
        BIT_AND = new TokenType("BIT_AND", 10, TokenKind.BINARY, "&", 8, true);
        AND = new TokenType("AND", 11, TokenKind.BINARY, "&&", 5, true);
        ASSIGN_BIT_AND = new TokenType("ASSIGN_BIT_AND", 12, TokenKind.BINARY, "&=", 2, false);
        LPAREN = new TokenType("LPAREN", 13, TokenKind.BRACKET, "(", 16, true);
        RPAREN = new TokenType("RPAREN", 14, TokenKind.BRACKET, ")", 0, true);
        MUL = new TokenType("MUL", 15, TokenKind.BINARY, Marker.ANY_MARKER, 13, true);
        ASSIGN_MUL = new TokenType("ASSIGN_MUL", 16, TokenKind.BINARY, "*=", 2, false);
        ADD = new TokenType("ADD", 17, TokenKind.BINARY, Marker.ANY_NON_NULL_MARKER, 12, true);
        INCPREFIX = new TokenType("INCPREFIX", 18, TokenKind.UNARY, "++", 15, true);
        ASSIGN_ADD = new TokenType("ASSIGN_ADD", 19, TokenKind.BINARY, "+=", 2, false);
        COMMARIGHT = new TokenType("COMMARIGHT", 20, TokenKind.BINARY, ",", 1, true);
        SUB = new TokenType("SUB", 21, TokenKind.BINARY, "-", 12, true);
        DECPREFIX = new TokenType("DECPREFIX", 22, TokenKind.UNARY, "--", 15, true);
        ASSIGN_SUB = new TokenType("ASSIGN_SUB", 23, TokenKind.BINARY, "-=", 2, false);
        PERIOD = new TokenType("PERIOD", 24, TokenKind.BRACKET, ".", 17, true);
        DIV = new TokenType("DIV", 25, TokenKind.BINARY, "/", 13, true);
        ASSIGN_DIV = new TokenType("ASSIGN_DIV", 26, TokenKind.BINARY, "/=", 2, false);
        COLON = new TokenType("COLON", 27, TokenKind.BINARY, CallSiteDescriptor.TOKEN_DELIMITER);
        SEMICOLON = new TokenType("SEMICOLON", 28, TokenKind.BINARY, ";");
        f40LT = new TokenType("LT", 29, TokenKind.BINARY, "<", 10, true);
        SHL = new TokenType("SHL", 30, TokenKind.BINARY, "<<", 11, true);
        ASSIGN_SHL = new TokenType("ASSIGN_SHL", 31, TokenKind.BINARY, "<<=", 2, false);
        f41LE = new TokenType("LE", 32, TokenKind.BINARY, "<=", 10, true);
        ASSIGN = new TokenType("ASSIGN", 33, TokenKind.BINARY, "=", 2, false);
        f42EQ = new TokenType("EQ", 34, TokenKind.BINARY, "==", 9, true);
        EQ_STRICT = new TokenType("EQ_STRICT", 35, TokenKind.BINARY, "===", 9, true);
        BIND = new TokenType("BIND", 36, TokenKind.BINARY, "=>", 9, true);
        f43GT = new TokenType("GT", 37, TokenKind.BINARY, ">", 10, true);
        f44GE = new TokenType("GE", 38, TokenKind.BINARY, ">=", 10, true);
        SAR = new TokenType("SAR", 39, TokenKind.BINARY, ">>", 11, true);
        ASSIGN_SAR = new TokenType("ASSIGN_SAR", 40, TokenKind.BINARY, ">>=", 2, false);
        SHR = new TokenType("SHR", 41, TokenKind.BINARY, ">>>", 11, true);
        ASSIGN_SHR = new TokenType("ASSIGN_SHR", 42, TokenKind.BINARY, ">>>=", 2, false);
        TERNARY = new TokenType("TERNARY", 43, TokenKind.BINARY, LocationInfo.f204NA, 3, false);
        LBRACKET = new TokenType("LBRACKET", 44, TokenKind.BRACKET, "[", 17, true);
        RBRACKET = new TokenType("RBRACKET", 45, TokenKind.BRACKET, "]", 0, true);
        BIT_XOR = new TokenType("BIT_XOR", 46, TokenKind.BINARY, "^", 7, true);
        ASSIGN_BIT_XOR = new TokenType("ASSIGN_BIT_XOR", 47, TokenKind.BINARY, "^=", 2, false);
        LBRACE = new TokenType("LBRACE", 48, TokenKind.BRACKET, "{");
        BIT_OR = new TokenType("BIT_OR", 49, TokenKind.BINARY, CallSiteDescriptor.OPERATOR_DELIMITER, 6, true);
        ASSIGN_BIT_OR = new TokenType("ASSIGN_BIT_OR", 50, TokenKind.BINARY, "|=", 2, false);
        f45OR = new TokenType("OR", 51, TokenKind.BINARY, "||", 4, true);
        RBRACE = new TokenType("RBRACE", 52, TokenKind.BRACKET, "}");
        BIT_NOT = new TokenType("BIT_NOT", 53, TokenKind.UNARY, "~", 14, false);
        BREAK = new TokenType("BREAK", 54, TokenKind.KEYWORD, "break");
        CASE = new TokenType("CASE", 55, TokenKind.KEYWORD, "case");
        CATCH = new TokenType("CATCH", 56, TokenKind.KEYWORD, "catch");
        CLASS = new TokenType("CLASS", 57, TokenKind.FUTURE, "class");
        CONST = new TokenType("CONST", 58, TokenKind.KEYWORD, "const");
        CONTINUE = new TokenType("CONTINUE", 59, TokenKind.KEYWORD, "continue");
        DEBUGGER = new TokenType("DEBUGGER", 60, TokenKind.KEYWORD, "debugger");
        DEFAULT = new TokenType("DEFAULT", 61, TokenKind.KEYWORD, "default");
        DELETE = new TokenType("DELETE", 62, TokenKind.UNARY, "delete", 14, false);
        f46DO = new TokenType("DO", 63, TokenKind.KEYWORD, "do");
        ELSE = new TokenType("ELSE", 64, TokenKind.KEYWORD, "else");
        ENUM = new TokenType("ENUM", 65, TokenKind.FUTURE, "enum");
        EXPORT = new TokenType("EXPORT", 66, TokenKind.FUTURE, "export");
        EXTENDS = new TokenType("EXTENDS", 67, TokenKind.FUTURE, "extends");
        FALSE = new TokenType("FALSE", 68, TokenKind.LITERAL, "false");
        FINALLY = new TokenType("FINALLY", 69, TokenKind.KEYWORD, "finally");
        FOR = new TokenType("FOR", 70, TokenKind.KEYWORD, "for");
        FUNCTION = new TokenType("FUNCTION", 71, TokenKind.KEYWORD, "function");
        f47IF = new TokenType("IF", 72, TokenKind.KEYWORD, "if");
        IMPLEMENTS = new TokenType("IMPLEMENTS", 73, TokenKind.FUTURESTRICT, "implements");
        IMPORT = new TokenType("IMPORT", 74, TokenKind.FUTURE, "import");
        f48IN = new TokenType("IN", 75, TokenKind.BINARY, "in", 10, true);
        INSTANCEOF = new TokenType("INSTANCEOF", 76, TokenKind.BINARY, "instanceof", 10, true);
        INTERFACE = new TokenType("INTERFACE", 77, TokenKind.FUTURESTRICT, "interface");
        LET = new TokenType("LET", 78, TokenKind.FUTURESTRICT, "let");
        NEW = new TokenType("NEW", 79, TokenKind.UNARY, "new", 17, false);
        NULL = new TokenType(DateLayout.NULL_DATE_FORMAT, 80, TokenKind.LITERAL, Configurator.NULL);
        PACKAGE = new TokenType("PACKAGE", 81, TokenKind.FUTURESTRICT, "package");
        PRIVATE = new TokenType("PRIVATE", 82, TokenKind.FUTURESTRICT, "private");
        PROTECTED = new TokenType("PROTECTED", 83, TokenKind.FUTURESTRICT, "protected");
        PUBLIC = new TokenType("PUBLIC", 84, TokenKind.FUTURESTRICT, "public");
        RETURN = new TokenType("RETURN", 85, TokenKind.KEYWORD, "return");
        STATIC = new TokenType("STATIC", 86, TokenKind.FUTURESTRICT, "static");
        SUPER = new TokenType("SUPER", 87, TokenKind.FUTURE, "super");
        SWITCH = new TokenType("SWITCH", 88, TokenKind.KEYWORD, "switch");
        THIS = new TokenType("THIS", 89, TokenKind.KEYWORD, "this");
        THROW = new TokenType("THROW", 90, TokenKind.KEYWORD, "throw");
        TRUE = new TokenType("TRUE", 91, TokenKind.LITERAL, "true");
        TRY = new TokenType("TRY", 92, TokenKind.KEYWORD, "try");
        TYPEOF = new TokenType("TYPEOF", 93, TokenKind.UNARY, "typeof", 14, false);
        VAR = new TokenType("VAR", 94, TokenKind.KEYWORD, "var");
        VOID = new TokenType("VOID", 95, TokenKind.UNARY, "void", 14, false);
        WHILE = new TokenType("WHILE", 96, TokenKind.KEYWORD, "while");
        WITH = new TokenType("WITH", 97, TokenKind.KEYWORD, "with");
        YIELD = new TokenType("YIELD", 98, TokenKind.FUTURESTRICT, "yield");
        DECIMAL = new TokenType("DECIMAL", 99, TokenKind.LITERAL, null);
        OCTAL = new TokenType("OCTAL", 100, TokenKind.LITERAL, null);
        HEXADECIMAL = new TokenType("HEXADECIMAL", Shell.COMPILATION_ERROR, TokenKind.LITERAL, null);
        FLOATING = new TokenType("FLOATING", Shell.RUNTIME_ERROR, TokenKind.LITERAL, null);
        STRING = new TokenType("STRING", Shell.IO_ERROR, TokenKind.LITERAL, null);
        ESCSTRING = new TokenType("ESCSTRING", Shell.INTERNAL_ERROR, TokenKind.LITERAL, null);
        EXECSTRING = new TokenType("EXECSTRING", 105, TokenKind.LITERAL, null);
        IDENT = new TokenType("IDENT", 106, TokenKind.LITERAL, null);
        REGEX = new TokenType("REGEX", 107, TokenKind.LITERAL, null);
        XML = new TokenType("XML", 108, TokenKind.LITERAL, null);
        OBJECT = new TokenType("OBJECT", 109, TokenKind.LITERAL, null);
        ARRAY = new TokenType("ARRAY", 110, TokenKind.LITERAL, null);
        COMMALEFT = new TokenType("COMMALEFT", 111, TokenKind.IR, null);
        DECPOSTFIX = new TokenType("DECPOSTFIX", 112, TokenKind.IR, null);
        INCPOSTFIX = new TokenType("INCPOSTFIX", 113, TokenKind.IR, null);
        $VALUES = new TokenType[]{ERROR, EOF, EOL, COMMENT, DIRECTIVE_COMMENT, NOT, f39NE, NE_STRICT, MOD, ASSIGN_MOD, BIT_AND, AND, ASSIGN_BIT_AND, LPAREN, RPAREN, MUL, ASSIGN_MUL, ADD, INCPREFIX, ASSIGN_ADD, COMMARIGHT, SUB, DECPREFIX, ASSIGN_SUB, PERIOD, DIV, ASSIGN_DIV, COLON, SEMICOLON, f40LT, SHL, ASSIGN_SHL, f41LE, ASSIGN, f42EQ, EQ_STRICT, BIND, f43GT, f44GE, SAR, ASSIGN_SAR, SHR, ASSIGN_SHR, TERNARY, LBRACKET, RBRACKET, BIT_XOR, ASSIGN_BIT_XOR, LBRACE, BIT_OR, ASSIGN_BIT_OR, f45OR, RBRACE, BIT_NOT, BREAK, CASE, CATCH, CLASS, CONST, CONTINUE, DEBUGGER, DEFAULT, DELETE, f46DO, ELSE, ENUM, EXPORT, EXTENDS, FALSE, FINALLY, FOR, FUNCTION, f47IF, IMPLEMENTS, IMPORT, f48IN, INSTANCEOF, INTERFACE, LET, NEW, NULL, PACKAGE, PRIVATE, PROTECTED, PUBLIC, RETURN, STATIC, SUPER, SWITCH, THIS, THROW, TRUE, TRY, TYPEOF, VAR, VOID, WHILE, WITH, YIELD, DECIMAL, OCTAL, HEXADECIMAL, FLOATING, STRING, ESCSTRING, EXECSTRING, IDENT, REGEX, XML, OBJECT, ARRAY, COMMALEFT, DECPOSTFIX, INCPOSTFIX};
        values = values();
    }

    private TokenType(String str, int i, TokenKind tokenKind, String str2) {
        this.next = null;
        this.kind = tokenKind;
        this.name = str2;
        this.precedence = 0;
        this.isLeftAssociative = false;
    }

    private TokenType(String str, int i, TokenKind tokenKind, String str2, int i2, boolean z) {
        this.next = null;
        this.kind = tokenKind;
        this.name = str2;
        this.precedence = i2;
        this.isLeftAssociative = z;
    }

    public boolean needsParens(TokenType tokenType, boolean z) {
        return tokenType.precedence != 0 && (this.precedence > tokenType.precedence || (this.precedence == tokenType.precedence && this.isLeftAssociative && !z));
    }

    public boolean isOperator(boolean z) {
        return this.kind == TokenKind.BINARY && !((z && this == f48IN) || this.precedence == 0);
    }

    public int getLength() {
        if ($assertionsDisabled || this.name != null) {
            return this.name.length();
        }
        throw new AssertionError("Token name not set");
    }

    public String getName() {
        return this.name;
    }

    public String getNameOrType() {
        return this.name == null ? super.name().toLowerCase(Locale.ENGLISH) : this.name;
    }

    public TokenType getNext() {
        return this.next;
    }

    public void setNext(TokenType tokenType) {
        this.next = tokenType;
    }

    public TokenKind getKind() {
        return this.kind;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public boolean isLeftAssociative() {
        return this.isLeftAssociative;
    }

    boolean startsWith(char c) {
        return this.name != null && this.name.length() > 0 && this.name.charAt(0) == c;
    }

    static TokenType[] getValues() {
        return values;
    }

    @Override // java.lang.Enum
    public String toString() {
        return getNameOrType();
    }
}

package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import jdk.nashorn.tools.Shell;
import kotlin.jvm.internal.ByteCompanionObject;
import org.apache.log4j.net.SyslogAppender;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Lexer.class */
class Lexer extends ScannerSupport {
    protected final ScanEnvironment env;
    protected final Syntax syntax;
    protected final Token token;

    protected Lexer(ScanEnvironment scanEnvironment, char[] cArr, int i, int i2) {
        super(cArr, i, i2);
        this.token = new Token();
        this.env = scanEnvironment;
        this.syntax = scanEnvironment.syntax;
    }

    private int fetchRangeQuantifier() {
        int iScanUnsignedNumber;
        mark();
        boolean zAllowInvalidInterval = this.syntax.allowInvalidInterval();
        if (!left()) {
            if (zAllowInvalidInterval) {
                return 1;
            }
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_LEFT_BRACE);
        }
        if (!zAllowInvalidInterval) {
            this.f70c = peek();
            if (this.f70c == 41 || this.f70c == 40 || this.f70c == 124) {
                throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_LEFT_BRACE);
            }
        }
        int iScanUnsignedNumber2 = scanUnsignedNumber();
        if (iScanUnsignedNumber2 < 0) {
            throw new SyntaxException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
        }
        if (iScanUnsignedNumber2 > 100000) {
            throw new SyntaxException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
        }
        boolean z = false;
        if (this.f69p == this.f71_p) {
            if (this.syntax.allowIntervalLowAbbrev()) {
                iScanUnsignedNumber2 = 0;
                z = true;
            } else {
                return invalidRangeQuantifier(zAllowInvalidInterval);
            }
        }
        if (!left()) {
            return invalidRangeQuantifier(zAllowInvalidInterval);
        }
        fetch();
        int i = 0;
        if (this.f70c == 44) {
            int i2 = this.f69p;
            iScanUnsignedNumber = scanUnsignedNumber();
            if (iScanUnsignedNumber < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
            }
            if (iScanUnsignedNumber > 100000) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER_FOR_REPEAT_RANGE);
            }
            if (this.f69p == i2) {
                if (z) {
                    return invalidRangeQuantifier(zAllowInvalidInterval);
                }
                iScanUnsignedNumber = -1;
            }
        } else {
            if (z) {
                return invalidRangeQuantifier(zAllowInvalidInterval);
            }
            unfetch();
            iScanUnsignedNumber = iScanUnsignedNumber2;
            i = 2;
        }
        if (!left()) {
            return invalidRangeQuantifier(zAllowInvalidInterval);
        }
        fetch();
        if (this.syntax.opEscBraceInterval()) {
            if (this.f70c != this.syntax.metaCharTable.esc) {
                return invalidRangeQuantifier(zAllowInvalidInterval);
            }
            fetch();
        }
        if (this.f70c != 125) {
            return invalidRangeQuantifier(zAllowInvalidInterval);
        }
        if (!(iScanUnsignedNumber == -1) && iScanUnsignedNumber2 > iScanUnsignedNumber) {
            throw new ValueException(ErrorMessages.ERR_UPPER_SMALLER_THAN_LOWER_IN_REPEAT_RANGE);
        }
        this.token.type = TokenType.INTERVAL;
        this.token.setRepeatLower(iScanUnsignedNumber2);
        this.token.setRepeatUpper(iScanUnsignedNumber);
        return i;
    }

    private int invalidRangeQuantifier(boolean z) {
        if (z) {
            restore();
            return 1;
        }
        throw new SyntaxException(ErrorMessages.ERR_INVALID_REPEAT_RANGE_PATTERN);
    }

    private int fetchEscapedValue() {
        if (!left()) {
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_ESCAPE);
        }
        fetch();
        switch (this.f70c) {
            case OPCode.NULL_CHECK_END /* 67 */:
                if (this.syntax.op2EscCapitalCBarControl()) {
                    if (!left()) {
                        throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_CONTROL);
                    }
                    fetch();
                    if (this.f70c != 45) {
                        throw new SyntaxException(ErrorMessages.ERR_CONTROL_CODE_SYNTAX);
                    }
                    fetchEscapedValueControl();
                } else {
                    fetchEscapedValueBackSlash();
                }
                return this.f70c;
            case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                if (this.syntax.op2EscCapitalMBarMeta()) {
                    if (!left()) {
                        throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_META);
                    }
                    fetch();
                    if (this.f70c != 45) {
                        throw new SyntaxException(ErrorMessages.ERR_META_CODE_SYNTAX);
                    }
                    if (!left()) {
                        throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_META);
                    }
                    fetch();
                    if (this.f70c == this.syntax.metaCharTable.esc) {
                        this.f70c = fetchEscapedValue();
                    }
                    this.f70c = (this.f70c & 255) | 128;
                } else {
                    fetchEscapedValueBackSlash();
                }
                return this.f70c;
            case 99:
                if (this.syntax.opEscCControl()) {
                    fetchEscapedValueControl();
                }
            default:
                fetchEscapedValueBackSlash();
                return this.f70c;
        }
    }

    private void fetchEscapedValueBackSlash() {
        this.f70c = this.env.convertBackslashValue(this.f70c);
    }

    private void fetchEscapedValueControl() {
        if (!left()) {
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_CONTROL);
        }
        fetch();
        if (this.f70c == 63) {
            this.f70c = ByteCompanionObject.MAX_VALUE;
            return;
        }
        if (this.f70c == this.syntax.metaCharTable.esc) {
            this.f70c = fetchEscapedValue();
        }
        this.f70c &= 159;
    }

    private void fetchTokenInCCFor_charType(boolean z, int i) {
        this.token.type = TokenType.CHAR_TYPE;
        this.token.setPropCType(i);
        this.token.setPropNot(z);
    }

    private void fetchTokenInCCFor_x() {
        if (!left()) {
            return;
        }
        int i = this.f69p;
        if (!peekIs(123) || !this.syntax.opEscXBraceHex8()) {
            if (this.syntax.opEscXHex2()) {
                int iScanUnsignedHexadecimalNumber = scanUnsignedHexadecimalNumber(2);
                if (iScanUnsignedHexadecimalNumber < 0) {
                    throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
                }
                if (this.f69p == i) {
                    iScanUnsignedHexadecimalNumber = 0;
                }
                this.token.type = TokenType.RAW_BYTE;
                this.token.setC(iScanUnsignedHexadecimalNumber);
                return;
            }
            return;
        }
        inc();
        int iScanUnsignedHexadecimalNumber2 = scanUnsignedHexadecimalNumber(8);
        if (iScanUnsignedHexadecimalNumber2 < 0) {
            throw new ValueException(ErrorMessages.ERR_TOO_BIG_WIDE_CHAR_VALUE);
        }
        if (left() && EncodingHelper.isXDigit(peek())) {
            throw new ValueException(ErrorMessages.ERR_TOO_LONG_WIDE_CHAR_VALUE);
        }
        if (this.f69p > i + 1 && left() && peekIs(125)) {
            inc();
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(iScanUnsignedHexadecimalNumber2);
            return;
        }
        this.f69p = i;
    }

    private void fetchTokenInCCFor_u() {
        if (!left()) {
            return;
        }
        int i = this.f69p;
        if (this.syntax.op2EscUHex4()) {
            int iScanUnsignedHexadecimalNumber = scanUnsignedHexadecimalNumber(4);
            if (iScanUnsignedHexadecimalNumber < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
            }
            if (this.f69p == i) {
                iScanUnsignedHexadecimalNumber = 0;
            }
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(iScanUnsignedHexadecimalNumber);
        }
    }

    private void fetchTokenInCCFor_digit() {
        if (this.syntax.opEscOctal3()) {
            unfetch();
            int i = this.f69p;
            int iScanUnsignedOctalNumber = scanUnsignedOctalNumber(3);
            if (iScanUnsignedOctalNumber < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
            }
            if (this.f69p == i) {
                iScanUnsignedOctalNumber = 0;
            }
            this.token.type = TokenType.RAW_BYTE;
            this.token.setC(iScanUnsignedOctalNumber);
        }
    }

    private void fetchTokenInCCFor_and() {
        if (this.syntax.op2CClassSetOp() && left() && peekIs(38)) {
            inc();
            this.token.type = TokenType.CC_AND;
        }
    }

    protected final TokenType fetchTokenInCC() {
        if (!left()) {
            this.token.type = TokenType.EOT;
            return this.token.type;
        }
        fetch();
        this.token.type = TokenType.CHAR;
        this.token.setC(this.f70c);
        this.token.escaped = false;
        if (this.f70c == 93) {
            this.token.type = TokenType.CC_CLOSE;
        } else if (this.f70c == 45) {
            this.token.type = TokenType.CC_RANGE;
        } else if (this.f70c == this.syntax.metaCharTable.esc) {
            if (!this.syntax.backSlashEscapeInCC()) {
                return this.token.type;
            }
            if (!left()) {
                throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_ESCAPE);
            }
            fetch();
            this.token.escaped = true;
            this.token.setC(this.f70c);
            switch (this.f70c) {
                case 48:
                case OPCode.MEMORY_START_PUSH /* 49 */:
                case OPCode.MEMORY_END_PUSH /* 50 */:
                case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                case OPCode.MEMORY_END /* 52 */:
                case OPCode.MEMORY_END_REC /* 53 */:
                case OPCode.FAIL /* 54 */:
                case OPCode.JUMP /* 55 */:
                    fetchTokenInCCFor_digit();
                    break;
                case 56:
                case OPCode.POP /* 57 */:
                case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
                case 60:
                case OPCode.REPEAT_NG /* 61 */:
                case 62:
                case OPCode.REPEAT_INC_NG /* 63 */:
                case 64:
                case OPCode.REPEAT_INC_NG_SG /* 65 */:
                case OPCode.NULL_CHECK_START /* 66 */:
                case OPCode.NULL_CHECK_END /* 67 */:
                case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                case OPCode.PUSH_POS /* 70 */:
                case OPCode.POP_POS /* 71 */:
                case OPCode.FAIL_POS /* 73 */:
                case OPCode.PUSH_STOP_BT /* 74 */:
                case OPCode.POP_STOP_BT /* 75 */:
                case OPCode.LOOK_BEHIND /* 76 */:
                case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                case OPCode.FAIL_LOOK_BEHIND_NOT /* 78 */:
                case OPCode.CALL /* 79 */:
                case 80:
                case OPCode.STATE_CHECK_PUSH /* 81 */:
                case OPCode.STATE_CHECK_PUSH_OR_JUMP /* 82 */:
                case OPCode.STATE_CHECK_ANYCHAR_STAR /* 84 */:
                case OPCode.STATE_CHECK_ANYCHAR_ML_STAR /* 85 */:
                case OPCode.SET_OPTION_PUSH /* 86 */:
                case SyslogAppender.LOG_FTP /* 88 */:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case Shell.COMPILATION_ERROR /* 101 */:
                case Shell.RUNTIME_ERROR /* 102 */:
                case Shell.IO_ERROR /* 103 */:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                case 112:
                case 113:
                case 114:
                case 116:
                case 118:
                default:
                    unfetch();
                    int iFetchEscapedValue = fetchEscapedValue();
                    if (this.token.getC() != iFetchEscapedValue) {
                        this.token.setCode(iFetchEscapedValue);
                        this.token.type = TokenType.CODE_POINT;
                        break;
                    }
                    break;
                case OPCode.NULL_CHECK_END_MEMST /* 68 */:
                    fetchTokenInCCFor_charType(true, CharacterType.f90D);
                    break;
                case 72:
                    if (this.syntax.op2EscHXDigit()) {
                        fetchTokenInCCFor_charType(true, 11);
                        break;
                    }
                    break;
                case OPCode.STATE_CHECK /* 83 */:
                    fetchTokenInCCFor_charType(true, CharacterType.f89S);
                    break;
                case OPCode.SET_OPTION /* 87 */:
                    fetchTokenInCCFor_charType(true, CharacterType.f91W);
                    break;
                case Shell.COMMANDLINE_ERROR /* 100 */:
                    fetchTokenInCCFor_charType(false, CharacterType.f90D);
                    break;
                case Shell.INTERNAL_ERROR /* 104 */:
                    if (this.syntax.op2EscHXDigit()) {
                        fetchTokenInCCFor_charType(false, 11);
                        break;
                    }
                    break;
                case 115:
                    fetchTokenInCCFor_charType(false, CharacterType.f89S);
                    break;
                case 117:
                    fetchTokenInCCFor_u();
                    break;
                case 119:
                    fetchTokenInCCFor_charType(false, CharacterType.f91W);
                    break;
                case 120:
                    fetchTokenInCCFor_x();
                    break;
            }
        } else if (this.f70c == 38) {
            fetchTokenInCCFor_and();
        }
        return this.token.type;
    }

    private void fetchTokenFor_repeat(int i, int i2) {
        this.token.type = TokenType.OP_REPEAT;
        this.token.setRepeatLower(i);
        this.token.setRepeatUpper(i2);
        greedyCheck();
    }

    private void fetchTokenFor_openBrace() {
        switch (fetchRangeQuantifier()) {
            case 0:
                greedyCheck();
                break;
            case 2:
                if (this.syntax.fixedIntervalIsGreedyOnly()) {
                    possessiveCheck();
                    break;
                } else {
                    greedyCheck();
                    break;
                }
        }
    }

    private void fetchTokenFor_anchor(int i) {
        this.token.type = TokenType.ANCHOR;
        this.token.setAnchor(i);
    }

    private void fetchTokenFor_xBrace() {
        if (!left()) {
            return;
        }
        int i = this.f69p;
        if (!peekIs(123) || !this.syntax.opEscXBraceHex8()) {
            if (this.syntax.opEscXHex2()) {
                int iScanUnsignedHexadecimalNumber = scanUnsignedHexadecimalNumber(2);
                if (iScanUnsignedHexadecimalNumber < 0) {
                    throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
                }
                if (this.f69p == i) {
                    iScanUnsignedHexadecimalNumber = 0;
                }
                this.token.type = TokenType.RAW_BYTE;
                this.token.setC(iScanUnsignedHexadecimalNumber);
                return;
            }
            return;
        }
        inc();
        int iScanUnsignedHexadecimalNumber2 = scanUnsignedHexadecimalNumber(8);
        if (iScanUnsignedHexadecimalNumber2 < 0) {
            throw new ValueException(ErrorMessages.ERR_TOO_BIG_WIDE_CHAR_VALUE);
        }
        if (left() && EncodingHelper.isXDigit(peek())) {
            throw new ValueException(ErrorMessages.ERR_TOO_LONG_WIDE_CHAR_VALUE);
        }
        if (this.f69p > i + 1 && left() && peekIs(125)) {
            inc();
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(iScanUnsignedHexadecimalNumber2);
            return;
        }
        this.f69p = i;
    }

    private void fetchTokenFor_uHex() {
        if (!left()) {
            return;
        }
        int i = this.f69p;
        if (this.syntax.op2EscUHex4()) {
            int iScanUnsignedHexadecimalNumber = scanUnsignedHexadecimalNumber(4);
            if (iScanUnsignedHexadecimalNumber < 0) {
                throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
            }
            if (this.f69p == i) {
                iScanUnsignedHexadecimalNumber = 0;
            }
            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(iScanUnsignedHexadecimalNumber);
        }
    }

    private void fetchTokenFor_digit() {
        unfetch();
        int i = this.f69p;
        int iScanUnsignedNumber = scanUnsignedNumber();
        if (iScanUnsignedNumber >= 0 && iScanUnsignedNumber <= 1000 && this.syntax.opDecimalBackref() && (iScanUnsignedNumber <= this.env.numMem || iScanUnsignedNumber <= 9)) {
            if (this.syntax.strictCheckBackref() && (iScanUnsignedNumber > this.env.numMem || this.env.memNodes == null || this.env.memNodes[iScanUnsignedNumber] == null)) {
                throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
            }
            this.token.type = TokenType.BACKREF;
            this.token.setBackrefRef(iScanUnsignedNumber);
            return;
        }
        if (this.f70c == 56 || this.f70c == 57) {
            this.f69p = i;
            inc();
        } else {
            this.f69p = i;
            fetchTokenFor_zero();
        }
    }

    private void fetchTokenFor_zero() {
        if (!this.syntax.opEscOctal3()) {
            if (this.f70c != 48) {
                inc();
                return;
            }
            return;
        }
        int i = this.f69p;
        int iScanUnsignedOctalNumber = scanUnsignedOctalNumber(this.f70c == 48 ? 2 : 3);
        if (iScanUnsignedOctalNumber < 0) {
            throw new ValueException(ErrorMessages.ERR_TOO_BIG_NUMBER);
        }
        if (this.f69p == i) {
            iScanUnsignedOctalNumber = 0;
        }
        this.token.type = TokenType.RAW_BYTE;
        this.token.setC(iScanUnsignedOctalNumber);
    }

    private void fetchTokenFor_metaChars() {
        if (this.f70c == this.syntax.metaCharTable.anyChar) {
            this.token.type = TokenType.ANYCHAR;
            return;
        }
        if (this.f70c == this.syntax.metaCharTable.anyTime) {
            fetchTokenFor_repeat(0, -1);
            return;
        }
        if (this.f70c == this.syntax.metaCharTable.zeroOrOneTime) {
            fetchTokenFor_repeat(0, 1);
            return;
        }
        if (this.f70c == this.syntax.metaCharTable.oneOrMoreTime) {
            fetchTokenFor_repeat(1, -1);
        } else if (this.f70c == this.syntax.metaCharTable.anyCharAnyTime) {
            this.token.type = TokenType.ANYCHAR_ANYTIME;
        }
    }

    protected final TokenType fetchToken() {
        while (left()) {
            this.token.type = TokenType.STRING;
            this.token.backP = this.f69p;
            fetch();
            if (this.f70c == this.syntax.metaCharTable.esc && !this.syntax.op2IneffectiveEscape()) {
                if (!left()) {
                    throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_AT_ESCAPE);
                }
                this.token.backP = this.f69p;
                fetch();
                this.token.setC(this.f70c);
                this.token.escaped = true;
                switch (this.f70c) {
                    case OPCode.SEMI_END_BUF /* 39 */:
                        if (this.syntax.op2EscGnuBufAnchor()) {
                            fetchTokenFor_anchor(8);
                            break;
                        }
                        break;
                    case 40:
                        if (this.syntax.opEscLParenSubexp()) {
                            this.token.type = TokenType.SUBEXP_OPEN;
                            break;
                        }
                        break;
                    case OPCode.BACKREF1 /* 41 */:
                        if (this.syntax.opEscLParenSubexp()) {
                            this.token.type = TokenType.SUBEXP_CLOSE;
                            break;
                        }
                        break;
                    case OPCode.BACKREF2 /* 42 */:
                        if (this.syntax.opEscAsteriskZeroInf()) {
                            fetchTokenFor_repeat(0, -1);
                            break;
                        }
                        break;
                    case OPCode.BACKREFN /* 43 */:
                        if (this.syntax.opEscPlusOneInf()) {
                            fetchTokenFor_repeat(1, -1);
                            break;
                        }
                        break;
                    case OPCode.BACKREFN_IC /* 44 */:
                    case OPCode.BACKREF_MULTI /* 45 */:
                    case OPCode.BACKREF_MULTI_IC /* 46 */:
                    case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                    case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                    case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
                    case OPCode.REPEAT_NG /* 61 */:
                    case 64:
                    case OPCode.NULL_CHECK_END /* 67 */:
                    case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                    case OPCode.PUSH_POS /* 70 */:
                    case OPCode.FAIL_POS /* 73 */:
                    case OPCode.PUSH_STOP_BT /* 74 */:
                    case OPCode.POP_STOP_BT /* 75 */:
                    case OPCode.LOOK_BEHIND /* 76 */:
                    case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                    case OPCode.FAIL_LOOK_BEHIND_NOT /* 78 */:
                    case OPCode.CALL /* 79 */:
                    case 80:
                    case OPCode.STATE_CHECK_PUSH /* 81 */:
                    case OPCode.STATE_CHECK_PUSH_OR_JUMP /* 82 */:
                    case OPCode.STATE_CHECK_ANYCHAR_STAR /* 84 */:
                    case OPCode.STATE_CHECK_ANYCHAR_ML_STAR /* 85 */:
                    case OPCode.SET_OPTION_PUSH /* 86 */:
                    case SyslogAppender.LOG_FTP /* 88 */:
                    case 89:
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                    case 95:
                    case 97:
                    case 99:
                    case Shell.COMPILATION_ERROR /* 101 */:
                    case Shell.RUNTIME_ERROR /* 102 */:
                    case Shell.IO_ERROR /* 103 */:
                    case 105:
                    case 106:
                    case 107:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 112:
                    case 113:
                    case 114:
                    case 116:
                    case 118:
                    case 121:
                    default:
                        unfetch();
                        int iFetchEscapedValue = fetchEscapedValue();
                        if (this.token.getC() != iFetchEscapedValue) {
                            this.token.type = TokenType.CODE_POINT;
                            this.token.setCode(iFetchEscapedValue);
                            break;
                        } else {
                            this.f69p = this.token.backP + 1;
                            break;
                        }
                    case 48:
                        fetchTokenFor_zero();
                        break;
                    case OPCode.MEMORY_START_PUSH /* 49 */:
                    case OPCode.MEMORY_END_PUSH /* 50 */:
                    case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                    case OPCode.MEMORY_END /* 52 */:
                    case OPCode.MEMORY_END_REC /* 53 */:
                    case OPCode.FAIL /* 54 */:
                    case OPCode.JUMP /* 55 */:
                    case 56:
                    case OPCode.POP /* 57 */:
                        fetchTokenFor_digit();
                        break;
                    case 60:
                        if (this.syntax.opEscLtGtWordBeginEnd()) {
                            fetchTokenFor_anchor(256);
                            break;
                        }
                        break;
                    case 62:
                        if (this.syntax.opEscLtGtWordBeginEnd()) {
                            fetchTokenFor_anchor(512);
                            break;
                        }
                        break;
                    case OPCode.REPEAT_INC_NG /* 63 */:
                        if (this.syntax.opEscQMarkZeroOne()) {
                            fetchTokenFor_repeat(0, 1);
                            break;
                        }
                        break;
                    case OPCode.REPEAT_INC_NG_SG /* 65 */:
                        if (this.syntax.opEscAZBufAnchor()) {
                            fetchTokenFor_anchor(1);
                            break;
                        }
                        break;
                    case OPCode.NULL_CHECK_START /* 66 */:
                        if (this.syntax.opEscBWordBound()) {
                            fetchTokenFor_anchor(128);
                            break;
                        }
                        break;
                    case OPCode.NULL_CHECK_END_MEMST /* 68 */:
                        if (this.syntax.opEscDDigit()) {
                            fetchTokenInCCFor_charType(true, CharacterType.f90D);
                            break;
                        }
                        break;
                    case OPCode.POP_POS /* 71 */:
                        if (this.syntax.opEscCapitalGBeginAnchor()) {
                            fetchTokenFor_anchor(4);
                            break;
                        }
                        break;
                    case 72:
                        if (this.syntax.op2EscHXDigit()) {
                            fetchTokenInCCFor_charType(true, 11);
                            break;
                        }
                        break;
                    case OPCode.STATE_CHECK /* 83 */:
                        if (this.syntax.opEscSWhiteSpace()) {
                            fetchTokenInCCFor_charType(true, CharacterType.f89S);
                            break;
                        }
                        break;
                    case OPCode.SET_OPTION /* 87 */:
                        if (this.syntax.opEscWWord()) {
                            fetchTokenInCCFor_charType(true, CharacterType.f91W);
                            break;
                        }
                        break;
                    case 90:
                        if (this.syntax.opEscAZBufAnchor()) {
                            fetchTokenFor_anchor(16);
                            break;
                        }
                        break;
                    case 96:
                        if (this.syntax.op2EscGnuBufAnchor()) {
                            fetchTokenFor_anchor(1);
                            break;
                        }
                        break;
                    case 98:
                        if (this.syntax.opEscBWordBound()) {
                            fetchTokenFor_anchor(64);
                            break;
                        }
                        break;
                    case Shell.COMMANDLINE_ERROR /* 100 */:
                        if (this.syntax.opEscDDigit()) {
                            fetchTokenInCCFor_charType(false, CharacterType.f90D);
                            break;
                        }
                        break;
                    case Shell.INTERNAL_ERROR /* 104 */:
                        if (this.syntax.op2EscHXDigit()) {
                            fetchTokenInCCFor_charType(false, 11);
                            break;
                        }
                        break;
                    case 115:
                        if (this.syntax.opEscSWhiteSpace()) {
                            fetchTokenInCCFor_charType(false, CharacterType.f89S);
                            break;
                        }
                        break;
                    case 117:
                        fetchTokenFor_uHex();
                        break;
                    case 119:
                        if (this.syntax.opEscWWord()) {
                            fetchTokenInCCFor_charType(false, CharacterType.f91W);
                            break;
                        }
                        break;
                    case 120:
                        fetchTokenFor_xBrace();
                        break;
                    case 122:
                        if (this.syntax.opEscAZBufAnchor()) {
                            fetchTokenFor_anchor(8);
                            break;
                        }
                        break;
                    case 123:
                        if (this.syntax.opEscBraceInterval()) {
                            fetchTokenFor_openBrace();
                            break;
                        }
                        break;
                    case 124:
                        if (this.syntax.opEscVBarAlt()) {
                            this.token.type = TokenType.ALT;
                            break;
                        }
                        break;
                }
            } else {
                this.token.setC(this.f70c);
                this.token.escaped = false;
                if (this.f70c == 0 || !this.syntax.opVariableMetaCharacters()) {
                    switch (this.f70c) {
                        case 9:
                        case 10:
                        case 12:
                        case CharacterType.ALNUM /* 13 */:
                        case 32:
                            if (!((this.env.option & 2) != 0)) {
                                break;
                            }
                        case OPCode.BEGIN_BUF /* 35 */:
                            if (!((this.env.option & 2) != 0)) {
                                break;
                            } else {
                                while (left()) {
                                    fetch();
                                    int i = this.f70c;
                                    if (i == 10 || i == 13 || i == 8232 || i == 8233) {
                                        break;
                                    }
                                }
                            }
                            break;
                        case 36:
                            if (this.syntax.opLineAnchor()) {
                                fetchTokenFor_anchor((this.env.option & 8) != 0 ? 8 : 32);
                                break;
                            }
                            break;
                        case 40:
                            if (peekIs(63) && this.syntax.op2QMarkGroupEffect()) {
                                inc();
                                if (peekIs(35)) {
                                    fetch();
                                    while (left()) {
                                        fetch();
                                        if (this.f70c == this.syntax.metaCharTable.esc) {
                                            if (left()) {
                                                fetch();
                                            }
                                        } else if (this.f70c == 41) {
                                            break;
                                        }
                                    }
                                    throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_IN_GROUP);
                                }
                                unfetch();
                            }
                            if (this.syntax.opLParenSubexp()) {
                                this.token.type = TokenType.SUBEXP_OPEN;
                                break;
                            }
                            break;
                        case OPCode.BACKREF1 /* 41 */:
                            if (this.syntax.opLParenSubexp()) {
                                this.token.type = TokenType.SUBEXP_CLOSE;
                                break;
                            }
                            break;
                        case OPCode.BACKREF2 /* 42 */:
                            if (this.syntax.opAsteriskZeroInf()) {
                                fetchTokenFor_repeat(0, -1);
                                break;
                            }
                            break;
                        case OPCode.BACKREFN /* 43 */:
                            if (this.syntax.opPlusOneInf()) {
                                fetchTokenFor_repeat(1, -1);
                                break;
                            }
                            break;
                        case OPCode.BACKREF_MULTI_IC /* 46 */:
                            if (this.syntax.opDotAnyChar()) {
                                this.token.type = TokenType.ANYCHAR;
                                break;
                            }
                            break;
                        case OPCode.REPEAT_INC_NG /* 63 */:
                            if (this.syntax.opQMarkZeroOne()) {
                                fetchTokenFor_repeat(0, 1);
                                break;
                            }
                            break;
                        case 91:
                            if (this.syntax.opBracketCC()) {
                                this.token.type = TokenType.CC_CC_OPEN;
                                break;
                            }
                            break;
                        case 94:
                            if (this.syntax.opLineAnchor()) {
                                fetchTokenFor_anchor((this.env.option & 8) != 0 ? 1 : 2);
                                break;
                            }
                            break;
                        case 123:
                            if (this.syntax.opBraceInterval()) {
                                fetchTokenFor_openBrace();
                                break;
                            }
                            break;
                        case 124:
                            if (this.syntax.opVBarAlt()) {
                                this.token.type = TokenType.ALT;
                                break;
                            }
                            break;
                    }
                } else {
                    fetchTokenFor_metaChars();
                }
            }
            return this.token.type;
        }
        this.token.type = TokenType.EOT;
        return this.token.type;
    }

    private void greedyCheck() {
        if (left() && peekIs(63) && this.syntax.opQMarkNonGreedy()) {
            fetch();
            this.token.setRepeatGreedy(false);
            this.token.setRepeatPossessive(false);
            return;
        }
        possessiveCheck();
    }

    private void possessiveCheck() {
        if (left() && peekIs(43) && ((this.syntax.op2PlusPossessiveRepeat() && this.token.type != TokenType.INTERVAL) || (this.syntax.op2PlusPossessiveInterval() && this.token.type == TokenType.INTERVAL))) {
            fetch();
            this.token.setRepeatGreedy(true);
            this.token.setRepeatPossessive(true);
        } else {
            this.token.setRepeatGreedy(true);
            this.token.setRepeatPossessive(false);
        }
    }

    protected final void syntaxWarn(String str, char c) {
        syntaxWarn(str.replace("<%n>", Character.toString(c)));
    }

    protected final void syntaxWarn(String str) {
        this.env.reg.warnings.warn(str + ": /" + new String(this.chars, getBegin(), getEnd()) + "/");
    }
}

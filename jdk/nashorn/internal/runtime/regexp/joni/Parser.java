package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.AnyCharNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCVALTYPE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Parser.class */
class Parser extends Lexer {
    protected final Regex regex;
    protected Node root;
    protected int returnCode;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Parser.class.desiredAssertionStatus();
    }

    protected Parser(ScanEnvironment env, char[] chars, int p, int end) {
        super(env, chars, p, end);
        this.regex = env.reg;
    }

    protected final Node parse() {
        this.root = parseRegexp();
        this.regex.numMem = this.env.numMem;
        return this.root;
    }

    private boolean codeExistCheck(int code, boolean ignoreEscaped) {
        mark();
        boolean inEsc = false;
        while (left()) {
            if (ignoreEscaped && inEsc) {
                inEsc = false;
            } else {
                fetch();
                if (this.f70c == code) {
                    restore();
                    return true;
                }
                if (this.f70c == this.syntax.metaCharTable.esc) {
                    inEsc = true;
                }
            }
        }
        restore();
        return false;
    }

    private CClassNode parseCharClass() {
        boolean neg;
        fetchTokenInCC();
        if (this.token.type == TokenType.CHAR && this.token.getC() == 94 && !this.token.escaped) {
            neg = true;
            fetchTokenInCC();
        } else {
            neg = false;
        }
        if (this.token.type == TokenType.CC_CLOSE) {
            if (!codeExistCheck(93, true)) {
                throw new SyntaxException(ErrorMessages.ERR_EMPTY_CHAR_CLASS);
            }
            this.env.ccEscWarn("]");
            this.token.type = TokenType.CHAR;
        }
        CClassNode cc = new CClassNode();
        CClassNode prevCC = null;
        CClassNode workCC = null;
        CClassNode.CCStateArg arg = new CClassNode.CCStateArg();
        boolean andStart = false;
        arg.state = CCSTATE.START;
        while (this.token.type != TokenType.CC_CLOSE) {
            boolean fetched = false;
            switch (C02671.f68xe07ffbd6[this.token.type.ordinal()]) {
                case 1:
                    if (this.token.getC() > 255) {
                        arg.inType = CCVALTYPE.CODE_POINT;
                    } else {
                        arg.inType = CCVALTYPE.SB;
                    }
                    arg.f80v = this.token.getC();
                    arg.vIsRaw = false;
                    parseCharClassValEntry2(cc, arg);
                    break;
                case 2:
                    arg.f80v = this.token.getC();
                    arg.inType = CCVALTYPE.SB;
                    arg.vIsRaw = true;
                    parseCharClassValEntry2(cc, arg);
                    break;
                case 3:
                    arg.f80v = this.token.getCode();
                    arg.vIsRaw = true;
                    parseCharClassValEntry(cc, arg);
                    break;
                case 4:
                    cc.addCType(this.token.getPropCType(), this.token.getPropNot(), this.env, this);
                    cc.nextStateClass(arg, this.env);
                    break;
                case 5:
                    if (arg.state == CCSTATE.VALUE) {
                        fetchTokenInCC();
                        fetched = true;
                        if (this.token.type == TokenType.CC_CLOSE) {
                            parseCharClassRangeEndVal(cc, arg);
                            break;
                        } else if (this.token.type == TokenType.CC_AND) {
                            this.env.ccEscWarn("-");
                            parseCharClassRangeEndVal(cc, arg);
                            break;
                        } else {
                            arg.state = CCSTATE.RANGE;
                            break;
                        }
                    } else if (arg.state == CCSTATE.START) {
                        arg.f80v = this.token.getC();
                        arg.vIsRaw = false;
                        fetchTokenInCC();
                        fetched = true;
                        if (this.token.type == TokenType.CC_RANGE || andStart) {
                            this.env.ccEscWarn("-");
                        }
                        parseCharClassValEntry(cc, arg);
                        break;
                    } else if (arg.state == CCSTATE.RANGE) {
                        this.env.ccEscWarn("-");
                        parseCharClassSbChar(cc, arg);
                        break;
                    } else {
                        fetchTokenInCC();
                        fetched = true;
                        if (this.token.type == TokenType.CC_CLOSE) {
                            parseCharClassRangeEndVal(cc, arg);
                            break;
                        } else if (this.token.type == TokenType.CC_AND) {
                            this.env.ccEscWarn("-");
                            parseCharClassRangeEndVal(cc, arg);
                            break;
                        } else if (this.syntax.allowDoubleRangeOpInCC()) {
                            this.env.ccEscWarn("-");
                            arg.inType = CCVALTYPE.SB;
                            arg.f80v = 45;
                            arg.vIsRaw = false;
                            parseCharClassValEntry2(cc, arg);
                            break;
                        } else {
                            throw new SyntaxException(ErrorMessages.ERR_UNMATCHED_RANGE_SPECIFIER_IN_CHAR_CLASS);
                        }
                    }
                    break;
                case 6:
                    CClassNode acc = parseCharClass();
                    cc.m22or(acc);
                    break;
                case 7:
                    if (arg.state == CCSTATE.VALUE) {
                        arg.f80v = 0;
                        arg.vIsRaw = false;
                        cc.nextStateValue(arg, this.env);
                    }
                    andStart = true;
                    arg.state = CCSTATE.START;
                    if (prevCC != null) {
                        prevCC.and(cc);
                    } else {
                        prevCC = cc;
                        if (workCC == null) {
                            workCC = new CClassNode();
                        }
                        cc = workCC;
                    }
                    cc.clear();
                    break;
                case 8:
                    throw new SyntaxException(ErrorMessages.ERR_PREMATURE_END_OF_CHAR_CLASS);
                default:
                    throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
            }
            if (!fetched) {
                fetchTokenInCC();
            }
        }
        if (arg.state == CCSTATE.VALUE) {
            arg.f80v = 0;
            arg.vIsRaw = false;
            cc.nextStateValue(arg, this.env);
        }
        if (prevCC != null) {
            prevCC.and(cc);
            cc = prevCC;
        }
        if (neg) {
            cc.setNot();
        } else {
            cc.clearNot();
        }
        if (cc.isNot() && this.syntax.notNewlineInNegativeCC() && !cc.isEmpty() && EncodingHelper.isNewLine(10)) {
            cc.f78bs.set(10);
        }
        return cc;
    }

    /* renamed from: jdk.nashorn.internal.runtime.regexp.joni.Parser$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Parser$1.class */
    static /* synthetic */ class C02671 {

        /* renamed from: $SwitchMap$jdk$nashorn$internal$runtime$regexp$joni$constants$TokenType */
        static final int[] f68xe07ffbd6 = new int[TokenType.values().length];

        static {
            try {
                f68xe07ffbd6[TokenType.CHAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f68xe07ffbd6[TokenType.RAW_BYTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f68xe07ffbd6[TokenType.CODE_POINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f68xe07ffbd6[TokenType.CHAR_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f68xe07ffbd6[TokenType.CC_RANGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f68xe07ffbd6[TokenType.CC_CC_OPEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f68xe07ffbd6[TokenType.CC_AND.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f68xe07ffbd6[TokenType.EOT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f68xe07ffbd6[TokenType.ALT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f68xe07ffbd6[TokenType.SUBEXP_OPEN.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f68xe07ffbd6[TokenType.SUBEXP_CLOSE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f68xe07ffbd6[TokenType.STRING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f68xe07ffbd6[TokenType.ANYCHAR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f68xe07ffbd6[TokenType.ANYCHAR_ANYTIME.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f68xe07ffbd6[TokenType.BACKREF.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f68xe07ffbd6[TokenType.ANCHOR.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f68xe07ffbd6[TokenType.OP_REPEAT.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f68xe07ffbd6[TokenType.INTERVAL.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    private void parseCharClassSbChar(CClassNode cc, CClassNode.CCStateArg arg) {
        arg.inType = CCVALTYPE.SB;
        arg.f80v = this.token.getC();
        arg.vIsRaw = false;
        parseCharClassValEntry2(cc, arg);
    }

    private void parseCharClassRangeEndVal(CClassNode cc, CClassNode.CCStateArg arg) {
        arg.f80v = 45;
        arg.vIsRaw = false;
        parseCharClassValEntry(cc, arg);
    }

    private void parseCharClassValEntry(CClassNode cc, CClassNode.CCStateArg arg) {
        arg.inType = arg.f80v <= 255 ? CCVALTYPE.SB : CCVALTYPE.CODE_POINT;
        parseCharClassValEntry2(cc, arg);
    }

    private void parseCharClassValEntry2(CClassNode cc, CClassNode.CCStateArg arg) {
        cc.nextStateValue(arg, this.env);
    }

    private Node parseEnclose(TokenType term) {
        Node node = null;
        if (!left()) {
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_WITH_UNMATCHED_PARENTHESIS);
        }
        int option = this.env.option;
        if (peekIs(63) && this.syntax.op2QMarkGroupEffect()) {
            inc();
            if (!left()) {
                throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_IN_GROUP);
            }
            fetch();
            switch (this.f70c) {
                case OPCode.WORD_BEGIN /* 33 */:
                    node = new AnchorNode(2048);
                    break;
                case OPCode.SEMI_END_BUF /* 39 */:
                    break;
                case OPCode.BACKREF_MULTI /* 45 */:
                case 105:
                case 109:
                case 115:
                case 120:
                    boolean neg = false;
                    while (true) {
                        switch (this.f70c) {
                            case OPCode.BACKREF1 /* 41 */:
                            case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                                break;
                            case OPCode.BACKREF_MULTI /* 45 */:
                                neg = true;
                                break;
                            case 105:
                                option = BitStatus.bsOnOff(option, 1, neg);
                                break;
                            case 109:
                                if (this.syntax.op2OptionPerl()) {
                                    option = BitStatus.bsOnOff(option, 8, !neg);
                                    break;
                                } else if (this.syntax.op2OptionRuby()) {
                                    option = BitStatus.bsOnOff(option, 4, neg);
                                    break;
                                } else {
                                    throw new SyntaxException(ErrorMessages.ERR_UNDEFINED_GROUP_OPTION);
                                }
                            case 115:
                                if (this.syntax.op2OptionPerl()) {
                                    option = BitStatus.bsOnOff(option, 4, neg);
                                    break;
                                } else {
                                    throw new SyntaxException(ErrorMessages.ERR_UNDEFINED_GROUP_OPTION);
                                }
                            case 120:
                                option = BitStatus.bsOnOff(option, 2, neg);
                                break;
                            default:
                                throw new SyntaxException(ErrorMessages.ERR_UNDEFINED_GROUP_OPTION);
                        }
                        if (this.f70c == 41) {
                            EncloseNode en = new EncloseNode(option, 0);
                            this.returnCode = 2;
                            return en;
                        }
                        if (this.f70c == 58) {
                            int prev = this.env.option;
                            this.env.option = option;
                            fetchToken();
                            Node target = parseSubExp(term);
                            this.env.option = prev;
                            EncloseNode en2 = new EncloseNode(option, 0);
                            en2.setTarget(target);
                            this.returnCode = 0;
                            return en2;
                        }
                        if (!left()) {
                            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_IN_GROUP);
                        }
                        fetch();
                    }
                case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                    fetchToken();
                    Node node2 = parseSubExp(term);
                    this.returnCode = 1;
                    return node2;
                case 60:
                    fetch();
                    if (this.f70c == 61) {
                        node = new AnchorNode(4096);
                        break;
                    } else if (this.f70c == 33) {
                        node = new AnchorNode(8192);
                        break;
                    } else {
                        throw new SyntaxException(ErrorMessages.ERR_UNDEFINED_GROUP_OPTION);
                    }
                case OPCode.REPEAT_NG /* 61 */:
                    node = new AnchorNode(1024);
                    break;
                case 62:
                    node = new EncloseNode(4);
                    break;
                case 64:
                    if (this.syntax.op2AtMarkCaptureHistory()) {
                        EncloseNode en3 = new EncloseNode();
                        int num = this.env.addMemEntry();
                        if (num >= 32) {
                            throw new ValueException(ErrorMessages.ERR_GROUP_NUMBER_OVER_FOR_CAPTURE_HISTORY);
                        }
                        en3.regNum = num;
                        node = en3;
                        break;
                    } else {
                        throw new SyntaxException(ErrorMessages.ERR_UNDEFINED_GROUP_OPTION);
                    }
                default:
                    throw new SyntaxException(ErrorMessages.ERR_UNDEFINED_GROUP_OPTION);
            }
        } else {
            if (Option.isDontCaptureGroup(this.env.option)) {
                fetchToken();
                Node node3 = parseSubExp(term);
                this.returnCode = 1;
                return node3;
            }
            EncloseNode en4 = new EncloseNode();
            en4.regNum = this.env.addMemEntry();
            node = en4;
        }
        fetchToken();
        Node target2 = parseSubExp(term);
        if (node.getType() == 7) {
            AnchorNode an = (AnchorNode) node;
            an.setTarget(target2);
        } else {
            EncloseNode en5 = (EncloseNode) node;
            en5.setTarget(target2);
            if (en5.type == 1) {
                this.env.setMemNode(en5.regNum, node);
            }
        }
        this.returnCode = 0;
        return node;
    }

    private Node parseExp(TokenType term) {
        Node node;
        if (this.token.type == term) {
            return StringNode.EMPTY;
        }
        boolean group = false;
        switch (C02671.f68xe07ffbd6[this.token.type.ordinal()]) {
            case 2:
                return parseExpTkRawByte(false);
            case 3:
                char[] buf = {(char) this.token.getCode()};
                node = new StringNode(buf, 0, 1);
                break;
            case 4:
                switch (this.token.getPropCType()) {
                    case 4:
                    case 9:
                    case 11:
                        CClassNode ccn = new CClassNode();
                        ccn.addCType(this.token.getPropCType(), false, this.env, this);
                        if (this.token.getPropNot()) {
                            ccn.setNot();
                        }
                        node = ccn;
                        break;
                    case CharacterType.f90D /* 260 */:
                    case CharacterType.f89S /* 265 */:
                    case CharacterType.f91W /* 268 */:
                        CClassNode cc = new CClassNode();
                        cc.addCType(this.token.getPropCType(), false, this.env, this);
                        if (this.token.getPropNot()) {
                            cc.setNot();
                        }
                        node = cc;
                        break;
                    default:
                        throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
                }
            case 5:
            case 7:
            default:
                throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
            case 6:
                CClassNode cc2 = parseCharClass();
                node = cc2;
                if (Option.isIgnoreCase(this.env.option)) {
                    ApplyCaseFoldArg arg = new ApplyCaseFoldArg(this.env, cc2);
                    EncodingHelper.applyAllCaseFold(this.env.caseFoldFlag, ApplyCaseFold.INSTANCE, arg);
                    if (arg.altRoot != null) {
                        node = ConsAltNode.newAltNode(node, arg.altRoot);
                        break;
                    }
                }
                break;
            case 8:
            case 9:
                return StringNode.EMPTY;
            case 10:
                node = parseEnclose(TokenType.SUBEXP_CLOSE);
                if (this.returnCode == 1) {
                    group = true;
                    break;
                } else if (this.returnCode == 2) {
                    int prev = this.env.option;
                    EncloseNode en = (EncloseNode) node;
                    this.env.option = en.option;
                    fetchToken();
                    Node target = parseSubExp(term);
                    this.env.option = prev;
                    en.setTarget(target);
                    return node;
                }
                break;
            case 11:
                if (!this.syntax.allowUnmatchedCloseSubexp()) {
                    throw new SyntaxException(ErrorMessages.ERR_UNMATCHED_CLOSE_PARENTHESIS);
                }
                if (this.token.escaped) {
                    return parseExpTkRawByte(false);
                }
                return parseExpTkByte(false);
            case 12:
                return parseExpTkByte(false);
            case CharacterType.ALNUM /* 13 */:
                node = new AnyCharNode();
                break;
            case 14:
                Node node2 = new AnyCharNode();
                QuantifierNode qn = new QuantifierNode(0, -1, false);
                qn.setTarget(node2);
                node = qn;
                break;
            case OPCode.EXACTN_IC /* 15 */:
                int backRef = this.token.getBackrefRef();
                node = new BackRefNode(backRef, this.env);
                break;
            case 16:
                node = new AnchorNode(this.token.getAnchor());
                break;
            case OPCode.CCLASS_MB /* 17 */:
            case OPCode.CCLASS_MIX /* 18 */:
                if (this.syntax.contextIndepRepeatOps()) {
                    if (this.syntax.contextInvalidRepeatOps()) {
                        throw new SyntaxException(ErrorMessages.ERR_TARGET_OF_REPEAT_OPERATOR_NOT_SPECIFIED);
                    }
                    node = StringNode.EMPTY;
                    break;
                } else {
                    return parseExpTkByte(false);
                }
        }
        fetchToken();
        return parseExpRepeat(node, group);
    }

    private Node parseExpTkByte(boolean group) {
        StringNode node = new StringNode(this.chars, this.token.backP, this.f69p);
        while (true) {
            fetchToken();
            if (this.token.type == TokenType.STRING) {
                if (this.token.backP == node.end) {
                    node.end = this.f69p;
                } else {
                    node.cat(this.chars, this.token.backP, this.f69p);
                }
            } else {
                return parseExpRepeat(node, group);
            }
        }
    }

    private Node parseExpTkRawByte(boolean group) {
        StringNode node = new StringNode((char) this.token.getC());
        node.setRaw();
        fetchToken();
        node.clearRaw();
        return parseExpRepeat(node, group);
    }

    private Node parseExpRepeat(Node targetp, boolean group) {
        Node target = targetp;
        while (true) {
            if (this.token.type == TokenType.OP_REPEAT || this.token.type == TokenType.INTERVAL) {
                if (target.isInvalidQuantifier()) {
                    throw new SyntaxException(ErrorMessages.ERR_TARGET_OF_REPEAT_OPERATOR_INVALID);
                }
                QuantifierNode qtfr = new QuantifierNode(this.token.getRepeatLower(), this.token.getRepeatUpper(), this.token.type == TokenType.INTERVAL);
                qtfr.greedy = this.token.getRepeatGreedy();
                int ret = qtfr.setQuantifier(target, group, this.env, this.chars, getBegin(), getEnd());
                Node qn = qtfr;
                if (this.token.getRepeatPossessive()) {
                    EncloseNode en = new EncloseNode(4);
                    en.setTarget(qn);
                    qn = en;
                }
                if (ret == 0) {
                    target = qn;
                } else if (ret == 2) {
                    Node target2 = ConsAltNode.newListNode(target, null);
                    ConsAltNode tmp = ((ConsAltNode) target2).setCdr(ConsAltNode.newListNode(qn, null));
                    fetchToken();
                    return parseExpRepeatForCar(target2, tmp, group);
                }
                fetchToken();
            } else {
                return target;
            }
        }
    }

    private Node parseExpRepeatForCar(Node top, ConsAltNode target, boolean group) {
        while (true) {
            if (this.token.type == TokenType.OP_REPEAT || this.token.type == TokenType.INTERVAL) {
                if (target.car.isInvalidQuantifier()) {
                    throw new SyntaxException(ErrorMessages.ERR_TARGET_OF_REPEAT_OPERATOR_INVALID);
                }
                QuantifierNode qtfr = new QuantifierNode(this.token.getRepeatLower(), this.token.getRepeatUpper(), this.token.type == TokenType.INTERVAL);
                qtfr.greedy = this.token.getRepeatGreedy();
                int ret = qtfr.setQuantifier(target.car, group, this.env, this.chars, getBegin(), getEnd());
                Node qn = qtfr;
                if (this.token.getRepeatPossessive()) {
                    EncloseNode en = new EncloseNode(4);
                    en.setTarget(qn);
                    qn = en;
                }
                if (ret == 0) {
                    target.setCar(qn);
                } else if (ret == 2 && !$assertionsDisabled) {
                    throw new AssertionError();
                }
                fetchToken();
            } else {
                return top;
            }
        }
    }

    private Node parseBranch(TokenType term) {
        Node node = parseExp(term);
        if (this.token.type == TokenType.EOT || this.token.type == term || this.token.type == TokenType.ALT) {
            return node;
        }
        ConsAltNode top = ConsAltNode.newListNode(node, null);
        ConsAltNode consAltNode = top;
        while (true) {
            ConsAltNode t = consAltNode;
            if (this.token.type == TokenType.EOT || this.token.type == term || this.token.type == TokenType.ALT) {
                break;
            }
            Node node2 = parseExp(term);
            if (node2.getType() == 8) {
                t.setCdr((ConsAltNode) node2);
                while (((ConsAltNode) node2).cdr != null) {
                    node2 = ((ConsAltNode) node2).cdr;
                }
                consAltNode = (ConsAltNode) node2;
            } else {
                t.setCdr(ConsAltNode.newListNode(node2, null));
                consAltNode = t.cdr;
            }
        }
        return top;
    }

    private Node parseSubExp(TokenType term) {
        Node node = parseBranch(term);
        if (this.token.type == term) {
            return node;
        }
        if (this.token.type == TokenType.ALT) {
            ConsAltNode top = ConsAltNode.newAltNode(node, null);
            ConsAltNode consAltNode = top;
            while (true) {
                ConsAltNode t = consAltNode;
                if (this.token.type != TokenType.ALT) {
                    break;
                }
                fetchToken();
                t.setCdr(ConsAltNode.newAltNode(parseBranch(term), null));
                consAltNode = t.cdr;
            }
            if (this.token.type != term) {
                parseSubExpError(term);
            }
            return top;
        }
        parseSubExpError(term);
        return null;
    }

    private static void parseSubExpError(TokenType term) {
        if (term == TokenType.SUBEXP_CLOSE) {
            throw new SyntaxException(ErrorMessages.ERR_END_PATTERN_WITH_UNMATCHED_PARENTHESIS);
        }
        throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
    }

    private Node parseRegexp() {
        fetchToken();
        return parseSubExp(TokenType.EOT);
    }
}

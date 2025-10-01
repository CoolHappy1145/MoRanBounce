package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ArrayCompiler.class */
final class ArrayCompiler extends Compiler {
    private int[] code;
    private int codeLength;
    private char[][] templates;
    private int templateNum;
    private static final int REPEAT_RANGE_ALLOC = 8;
    private static final int QUANTIFIER_EXPAND_LIMIT_SIZE = 50;

    ArrayCompiler(Analyser analyser) {
        super(analyser);
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected final void prepare() {
        this.code = new int[8];
        this.codeLength = 0;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected final void finish() {
        addOpcode(1);
        addOpcode(0);
        this.regex.code = this.code;
        this.regex.codeLength = this.codeLength;
        this.regex.templates = this.templates;
        this.regex.templateNum = this.templateNum;
        this.regex.factory = MatcherFactory.DEFAULT;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileAltNode(ConsAltNode consAltNode) {
        ConsAltNode consAltNode2;
        ConsAltNode consAltNode3;
        ConsAltNode consAltNode4 = consAltNode;
        int iCompileLengthTree = 0;
        do {
            iCompileLengthTree += compileLengthTree(consAltNode4.car);
            if (consAltNode4.cdr != null) {
                iCompileLengthTree += 4;
            }
            consAltNode2 = consAltNode4.cdr;
            consAltNode4 = consAltNode2;
        } while (consAltNode2 != null);
        int i = this.codeLength + iCompileLengthTree;
        ConsAltNode consAltNode5 = consAltNode;
        do {
            int iCompileLengthTree2 = compileLengthTree(consAltNode5.car);
            if (consAltNode5.cdr != null) {
                addOpcodeRelAddr(56, iCompileLengthTree2 + 2);
            }
            compileTree(consAltNode5.car);
            if (consAltNode5.cdr != null) {
                addOpcodeRelAddr(55, i - (this.codeLength + 2));
            }
            consAltNode3 = consAltNode5.cdr;
            consAltNode5 = consAltNode3;
        } while (consAltNode3 != null);
    }

    private static boolean opTemplated(int i) {
        return i == 7 || i == 15;
    }

    private void compileTreeEmptyCheck(Node node, int i) {
        int i2 = this.regex.numNullCheck;
        if (i != 0) {
            addOpcode(66);
            addMemNum(this.regex.numNullCheck);
            this.regex.numNullCheck++;
        }
        compileTree(node);
        if (i != 0) {
            switch (i) {
                case 1:
                    addOpcode(67);
                    break;
                case 2:
                    addOpcode(68);
                    break;
            }
            addMemNum(i2);
        }
    }

    private static int addCompileStringlength(char[] cArr, int i, int i2, boolean z) {
        int i3;
        int i4;
        if (z) {
            switch (i2) {
                case 1:
                    i3 = 14;
                    break;
                default:
                    i3 = 15;
                    break;
            }
        } else {
            switch (i2) {
                case 1:
                    i3 = 2;
                    break;
                case 2:
                    i3 = 3;
                    break;
                case 3:
                    i3 = 4;
                    break;
                case 4:
                    i3 = 5;
                    break;
                case 5:
                    i3 = 6;
                    break;
                default:
                    i3 = 7;
                    break;
            }
        }
        int i5 = i3;
        int i6 = 1;
        if (!opTemplated(i5)) {
            if (i5 == 7 || i5 == 15) {
                i6 = 1 + 1;
            }
            i4 = i6 + i2;
        } else {
            i4 = 1 + 3;
        }
        return i4;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected final void addCompileString(char[] cArr, int i, int i2, boolean z) {
        int i3;
        if (z) {
            switch (i2) {
                case 1:
                    i3 = 14;
                    break;
                default:
                    i3 = 15;
                    break;
            }
        } else {
            switch (i2) {
                case 1:
                    i3 = 2;
                    break;
                case 2:
                    i3 = 3;
                    break;
                case 3:
                    i3 = 4;
                    break;
                case 4:
                    i3 = 5;
                    break;
                case 5:
                    i3 = 6;
                    break;
                default:
                    i3 = 7;
                    break;
            }
        }
        int i4 = i3;
        addOpcode(i4);
        if (i4 == 7 || i4 == 15) {
            addLength(i2);
        }
        if (opTemplated(i4)) {
            addInt(this.templateNum);
            addInt(i);
            addTemplate(cArr);
            return;
        }
        addChars(cArr, i, i2);
    }

    private static int compileLengthStringNode(Node node) {
        StringNode stringNode = (StringNode) node;
        if (stringNode.length() <= 0) {
            return 0;
        }
        boolean zIsAmbig = stringNode.isAmbig();
        int i = stringNode.f86p;
        int i2 = stringNode.end;
        char[] cArr = stringNode.chars;
        int i3 = 1;
        for (int i4 = i + 1; i4 < i2; i4++) {
            i3++;
        }
        return 0 + addCompileStringlength(cArr, i, i3, zIsAmbig);
    }

    private static int compileLengthStringRawNode(StringNode stringNode) {
        if (stringNode.length() <= 0) {
            return 0;
        }
        return addCompileStringlength(stringNode.chars, stringNode.f86p, stringNode.length(), false);
    }

    private void addMultiByteCClass(CodeRangeBuffer codeRangeBuffer) {
        addLength(codeRangeBuffer.used);
        addInts(codeRangeBuffer.f66p, codeRangeBuffer.used);
    }

    private static int compileLengthCClassNode(CClassNode cClassNode) {
        int i;
        int i2;
        if (cClassNode.isShare()) {
            return 2;
        }
        if (cClassNode.mbuf == null) {
            i2 = 9;
        } else {
            if (cClassNode.f78bs.isEmpty()) {
                i = 1;
            } else {
                i = 9;
            }
            i2 = i + 1 + cClassNode.mbuf.used;
        }
        return i2;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileCClassNode(CClassNode cClassNode) {
        if (cClassNode.isShare()) {
            addOpcode(22);
            addPointer(cClassNode);
            return;
        }
        if (cClassNode.mbuf == null) {
            if (cClassNode.isNot()) {
                addOpcode(19);
            } else {
                addOpcode(16);
            }
            addInts(cClassNode.f78bs.bits, 8);
            return;
        }
        if (cClassNode.f78bs.isEmpty()) {
            if (cClassNode.isNot()) {
                addOpcode(20);
            } else {
                addOpcode(17);
            }
            addMultiByteCClass(cClassNode.mbuf);
            return;
        }
        if (cClassNode.isNot()) {
            addOpcode(21);
        } else {
            addOpcode(18);
        }
        addInts(cClassNode.f78bs.bits, 8);
        addMultiByteCClass(cClassNode.mbuf);
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileAnyCharNode() {
        if ((this.regex.options & 4) != 0) {
            addOpcode(24);
        } else {
            addOpcode(23);
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileBackrefNode(BackRefNode backRefNode) {
        if ((this.regex.options & 1) != 0) {
            addOpcode(44);
            addMemNum(backRefNode.backRef);
            return;
        }
        switch (backRefNode.backRef) {
            case 1:
                addOpcode(41);
                break;
            case 2:
                addOpcode(42);
                break;
            default:
                addOpcode(43);
                addOpcode(backRefNode.backRef);
                break;
        }
    }

    private void entryRepeatRange(int i, int i2, int i3) {
        if (this.regex.repeatRangeLo == null) {
            this.regex.repeatRangeLo = new int[8];
            this.regex.repeatRangeHi = new int[8];
        } else if (i >= this.regex.repeatRangeLo.length) {
            int[] iArr = new int[this.regex.repeatRangeLo.length + 8];
            System.arraycopy(this.regex.repeatRangeLo, 0, iArr, 0, this.regex.repeatRangeLo.length);
            this.regex.repeatRangeLo = iArr;
            int[] iArr2 = new int[this.regex.repeatRangeHi.length + 8];
            System.arraycopy(this.regex.repeatRangeHi, 0, iArr2, 0, this.regex.repeatRangeHi.length);
            this.regex.repeatRangeHi = iArr2;
        }
        this.regex.repeatRangeLo[i] = i2;
        this.regex.repeatRangeHi[i] = i3 == -1 ? Integer.MAX_VALUE : i3;
    }

    private void compileRangeRepeatNode(QuantifierNode quantifierNode, int i, int i2) {
        int i3 = this.regex.numRepeat;
        addOpcode(quantifierNode.greedy ? 60 : 61);
        addMemNum(i3);
        this.regex.numRepeat++;
        addRelAddr(i + 2);
        entryRepeatRange(i3, quantifierNode.lower, quantifierNode.upper);
        compileTreeEmptyCheck(quantifierNode.target, i2);
        if (quantifierNode.isInRepeat()) {
            addOpcode(quantifierNode.greedy ? 64 : 65);
        } else {
            addOpcode(quantifierNode.greedy ? 62 : 63);
        }
        addMemNum(i3);
    }

    private int compileNonCECLengthQuantifierNode(QuantifierNode quantifierNode) {
        int i;
        int i2;
        int i3;
        boolean z = quantifierNode.upper == -1;
        int i4 = quantifierNode.targetEmptyInfo;
        int iCompileLengthTree = compileLengthTree(quantifierNode.target);
        if (quantifierNode.target.getType() == 3 && quantifierNode.greedy && z) {
            if (quantifierNode.nextHeadExact != null) {
                return 2 + (iCompileLengthTree * quantifierNode.lower);
            }
            return 1 + (iCompileLengthTree * quantifierNode.lower);
        }
        if (i4 != 0) {
            i = iCompileLengthTree + 4;
        } else {
            i = iCompileLengthTree;
        }
        if (z && (quantifierNode.lower <= 1 || iCompileLengthTree * quantifierNode.lower <= 50)) {
            if (quantifierNode.lower == 1 && iCompileLengthTree > 50) {
                i3 = 2;
            } else {
                i3 = iCompileLengthTree * quantifierNode.lower;
            }
            if (quantifierNode.greedy) {
                if (quantifierNode.headExact != null || quantifierNode.nextHeadExact != null) {
                    i2 = i3 + 3 + i + 2;
                } else {
                    i2 = i3 + 2 + i + 2;
                }
            } else {
                i2 = i3 + 2 + i + 2;
            }
        } else if (quantifierNode.upper == 0 && quantifierNode.isRefered) {
            i2 = 2 + iCompileLengthTree;
        } else if (!z && quantifierNode.greedy && (quantifierNode.upper == 1 || (iCompileLengthTree + 2) * quantifierNode.upper <= 50)) {
            i2 = (iCompileLengthTree * quantifierNode.lower) + ((2 + iCompileLengthTree) * (quantifierNode.upper - quantifierNode.lower));
        } else if (!quantifierNode.greedy && quantifierNode.upper == 1 && quantifierNode.lower == 0) {
            i2 = 4 + iCompileLengthTree;
        } else {
            i2 = 2 + i + 1 + 1 + 1;
        }
        return i2;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileNonCECQuantifierNode(QuantifierNode quantifierNode) {
        int i;
        boolean z = quantifierNode.upper == -1;
        int i2 = quantifierNode.targetEmptyInfo;
        int iCompileLengthTree = compileLengthTree(quantifierNode.target);
        if (quantifierNode.isAnyCharStar()) {
            compileTreeNTimes(quantifierNode.target, quantifierNode.lower);
            if (quantifierNode.nextHeadExact != null) {
                if ((this.regex.options & 4) != 0) {
                    addOpcode(28);
                } else {
                    addOpcode(27);
                }
                StringNode stringNode = (StringNode) quantifierNode.nextHeadExact;
                addChars(stringNode.chars, stringNode.f86p, 1);
                return;
            }
            if ((this.regex.options & 4) != 0) {
                addOpcode(26);
                return;
            } else {
                addOpcode(25);
                return;
            }
        }
        if (i2 != 0) {
            i = iCompileLengthTree + 4;
        } else {
            i = iCompileLengthTree;
        }
        if (z && (quantifierNode.lower <= 1 || iCompileLengthTree * quantifierNode.lower <= 50)) {
            if (quantifierNode.lower == 1 && iCompileLengthTree > 50) {
                if (quantifierNode.greedy) {
                    if (quantifierNode.headExact != null || quantifierNode.nextHeadExact != null) {
                        addOpcodeRelAddr(55, 3);
                    } else {
                        addOpcodeRelAddr(55, 2);
                    }
                } else {
                    addOpcodeRelAddr(55, 2);
                }
            } else {
                compileTreeNTimes(quantifierNode.target, quantifierNode.lower);
            }
            if (quantifierNode.greedy) {
                if (quantifierNode.headExact != null) {
                    addOpcodeRelAddr(58, i + 2);
                    StringNode stringNode2 = (StringNode) quantifierNode.headExact;
                    addChars(stringNode2.chars, stringNode2.f86p, 1);
                    compileTreeEmptyCheck(quantifierNode.target, i2);
                    addOpcodeRelAddr(55, -(i + 2 + 3));
                    return;
                }
                if (quantifierNode.nextHeadExact != null) {
                    addOpcodeRelAddr(59, i + 2);
                    StringNode stringNode3 = (StringNode) quantifierNode.nextHeadExact;
                    addChars(stringNode3.chars, stringNode3.f86p, 1);
                    compileTreeEmptyCheck(quantifierNode.target, i2);
                    addOpcodeRelAddr(55, -(i + 2 + 3));
                    return;
                }
                addOpcodeRelAddr(56, i + 2);
                compileTreeEmptyCheck(quantifierNode.target, i2);
                addOpcodeRelAddr(55, -(i + 2 + 2));
                return;
            }
            addOpcodeRelAddr(55, i);
            compileTreeEmptyCheck(quantifierNode.target, i2);
            addOpcodeRelAddr(56, -(i + 2));
            return;
        }
        if (quantifierNode.upper == 0 && quantifierNode.isRefered) {
            addOpcodeRelAddr(55, iCompileLengthTree);
            compileTree(quantifierNode.target);
            return;
        }
        if (!z && quantifierNode.greedy && (quantifierNode.upper == 1 || (iCompileLengthTree + 2) * quantifierNode.upper <= 50)) {
            int i3 = quantifierNode.upper - quantifierNode.lower;
            compileTreeNTimes(quantifierNode.target, quantifierNode.lower);
            for (int i4 = 0; i4 < i3; i4++) {
                addOpcodeRelAddr(56, ((i3 - i4) * iCompileLengthTree) + (((i3 - i4) - 1) * 2));
                compileTree(quantifierNode.target);
            }
            return;
        }
        if (!quantifierNode.greedy && quantifierNode.upper == 1 && quantifierNode.lower == 0) {
            addOpcodeRelAddr(56, 2);
            addOpcodeRelAddr(55, iCompileLengthTree);
            compileTree(quantifierNode.target);
            return;
        }
        compileRangeRepeatNode(quantifierNode, i, i2);
    }

    private int compileLengthOptionNode(EncloseNode encloseNode) {
        int i = this.regex.options;
        this.regex.options = encloseNode.option;
        int iCompileLengthTree = compileLengthTree(encloseNode.target);
        this.regex.options = i;
        int i2 = i ^ encloseNode.option;
        if (0 != 0) {
            return 5 + iCompileLengthTree + 2;
        }
        return iCompileLengthTree;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileOptionNode(EncloseNode encloseNode) {
        int i = this.regex.options;
        int i2 = i ^ encloseNode.option;
        if (0 != 0) {
            addOpcodeOption(86, encloseNode.option);
            addOpcodeOption(87, i);
            addOpcode(54);
        }
        this.regex.options = encloseNode.option;
        compileTree(encloseNode.target);
        this.regex.options = i;
        int i3 = i ^ encloseNode.option;
        if (0 != 0) {
            addOpcodeOption(87, i);
        }
    }

    private int compileLengthEncloseNode(EncloseNode encloseNode) {
        int iCompileLengthTree;
        int i;
        int i2;
        if (encloseNode.isOption()) {
            return compileLengthOptionNode(encloseNode);
        }
        if (encloseNode.target != null) {
            iCompileLengthTree = compileLengthTree(encloseNode.target);
        } else {
            iCompileLengthTree = 0;
        }
        switch (encloseNode.type) {
            case 1:
                int i3 = this.regex.btMemStart;
                int i4 = encloseNode.regNum;
                if ((i4 < 32 ? i3 & (1 << i4) : i3 & 1) != 0) {
                    i2 = 2;
                } else {
                    i2 = 2;
                }
                int i5 = i2;
                int i6 = iCompileLengthTree;
                int i7 = this.regex.btMemEnd;
                int i8 = encloseNode.regNum;
                i = i5 + i6 + ((i8 < 32 ? i7 & (1 << i8) : i7 & 1) != 0 ? 2 : 2);
                break;
            case 4:
                if (encloseNode.isStopBtSimpleRepeat()) {
                    QuantifierNode quantifierNode = (QuantifierNode) encloseNode.target;
                    int iCompileLengthTree2 = compileLengthTree(quantifierNode.target);
                    i = (iCompileLengthTree2 * quantifierNode.lower) + 2 + iCompileLengthTree2 + 1 + 2;
                    break;
                } else {
                    i = 1 + iCompileLengthTree + 1;
                    break;
                }
            default:
                newInternalException(ErrorMessages.ERR_PARSER_BUG);
                return 0;
        }
        return i;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileEncloseNode(EncloseNode encloseNode) {
        switch (encloseNode.type) {
            case 1:
                int i = this.regex.btMemStart;
                int i2 = encloseNode.regNum;
                if ((i2 < 32 ? i & (1 << i2) : i & 1) != 0) {
                    addOpcode(49);
                } else {
                    addOpcode(48);
                }
                addMemNum(encloseNode.regNum);
                compileTree(encloseNode.target);
                int i3 = this.regex.btMemEnd;
                int i4 = encloseNode.regNum;
                if ((i4 < 32 ? i3 & (1 << i4) : i3 & 1) != 0) {
                    addOpcode(50);
                } else {
                    addOpcode(52);
                }
                addMemNum(encloseNode.regNum);
                break;
            case 4:
                if (encloseNode.isStopBtSimpleRepeat()) {
                    QuantifierNode quantifierNode = (QuantifierNode) encloseNode.target;
                    compileTreeNTimes(quantifierNode.target, quantifierNode.lower);
                    int iCompileLengthTree = compileLengthTree(quantifierNode.target);
                    addOpcodeRelAddr(56, iCompileLengthTree + 1 + 2);
                    compileTree(quantifierNode.target);
                    addOpcode(57);
                    addOpcodeRelAddr(55, -(2 + iCompileLengthTree + 1 + 2));
                    break;
                } else {
                    addOpcode(74);
                    compileTree(encloseNode.target);
                    addOpcode(75);
                    break;
                }
            default:
                newInternalException(ErrorMessages.ERR_PARSER_BUG);
                break;
        }
    }

    private int compileLengthAnchorNode(AnchorNode anchorNode) {
        int iCompileLengthTree;
        int i;
        if (anchorNode.target != null) {
            iCompileLengthTree = compileLengthTree(anchorNode.target);
        } else {
            iCompileLengthTree = 0;
        }
        switch (anchorNode.type) {
            case 1024:
                i = 1 + iCompileLengthTree + 1;
                break;
            case 2048:
                i = 2 + iCompileLengthTree + 1;
                break;
            case 4096:
                i = 2 + iCompileLengthTree;
                break;
            case 8192:
                i = 3 + iCompileLengthTree + 1;
                break;
            default:
                i = 1;
                break;
        }
        return i;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Compiler
    protected void compileAnchorNode(AnchorNode anchorNode) {
        int charLengthTree;
        int charLengthTree2;
        switch (anchorNode.type) {
            case 1:
                addOpcode(35);
                break;
            case 2:
                addOpcode(37);
                break;
            case 4:
                addOpcode(40);
                break;
            case 8:
                addOpcode(36);
                break;
            case 16:
                addOpcode(39);
                break;
            case 32:
                addOpcode(38);
                break;
            case 64:
                addOpcode(31);
                break;
            case 128:
                addOpcode(32);
                break;
            case 256:
                addOpcode(33);
                break;
            case 512:
                addOpcode(34);
                break;
            case 1024:
                addOpcode(70);
                compileTree(anchorNode.target);
                addOpcode(71);
                break;
            case 2048:
                addOpcodeRelAddr(72, compileLengthTree(anchorNode.target) + 1);
                compileTree(anchorNode.target);
                addOpcode(73);
                break;
            case 4096:
                addOpcode(76);
                if (anchorNode.charLength < 0) {
                    charLengthTree2 = this.analyser.getCharLengthTree(anchorNode.target);
                    if (this.analyser.returnCode != 0) {
                        newSyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
                    }
                } else {
                    charLengthTree2 = anchorNode.charLength;
                }
                addLength(charLengthTree2);
                compileTree(anchorNode.target);
                break;
            case 8192:
                addOpcodeRelAddr(77, compileLengthTree(anchorNode.target) + 1);
                if (anchorNode.charLength < 0) {
                    charLengthTree = this.analyser.getCharLengthTree(anchorNode.target);
                    if (this.analyser.returnCode != 0) {
                        newSyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
                    }
                } else {
                    charLengthTree = anchorNode.charLength;
                }
                addLength(charLengthTree);
                compileTree(anchorNode.target);
                addOpcode(78);
                break;
            default:
                newInternalException(ErrorMessages.ERR_PARSER_BUG);
                break;
        }
    }

    private int compileLengthTree(Node node) {
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        int iCompileLengthAnchorNode = 0;
        switch (node.getType()) {
            case 0:
                StringNode stringNode = (StringNode) node;
                if (stringNode.isRaw()) {
                    iCompileLengthAnchorNode = compileLengthStringRawNode(stringNode);
                    break;
                } else {
                    iCompileLengthAnchorNode = compileLengthStringNode(stringNode);
                    break;
                }
            case 1:
                iCompileLengthAnchorNode = compileLengthCClassNode((CClassNode) node);
                break;
            case 2:
            case 3:
                iCompileLengthAnchorNode = 1;
                break;
            case 4:
                iCompileLengthAnchorNode = (((this.regex.options & 1) != 0) || ((BackRefNode) node).backRef > 2) ? 2 : 1;
                break;
            case 5:
                iCompileLengthAnchorNode = compileNonCECLengthQuantifierNode((QuantifierNode) node);
                break;
            case 6:
                iCompileLengthAnchorNode = compileLengthEncloseNode((EncloseNode) node);
                break;
            case 7:
                iCompileLengthAnchorNode = compileLengthAnchorNode((AnchorNode) node);
                break;
            case 8:
                ConsAltNode consAltNode3 = (ConsAltNode) node;
                do {
                    iCompileLengthAnchorNode += compileLengthTree(consAltNode3.car);
                    consAltNode2 = consAltNode3.cdr;
                    consAltNode3 = consAltNode2;
                } while (consAltNode2 != null);
            case 9:
                ConsAltNode consAltNode4 = (ConsAltNode) node;
                int i = 0;
                do {
                    iCompileLengthAnchorNode += compileLengthTree(consAltNode4.car);
                    i++;
                    consAltNode = consAltNode4.cdr;
                    consAltNode4 = consAltNode;
                } while (consAltNode != null);
                iCompileLengthAnchorNode += 4 * (i - 1);
                break;
            default:
                newInternalException(ErrorMessages.ERR_PARSER_BUG);
                break;
        }
        return iCompileLengthAnchorNode;
    }

    private void ensure(int i) {
        if (i >= this.code.length) {
            int length = this.code.length;
            while (true) {
                int i2 = length << 1;
                if (i2 <= i) {
                    length = i2;
                } else {
                    int[] iArr = new int[i2];
                    System.arraycopy(this.code, 0, iArr, 0, this.code.length);
                    this.code = iArr;
                    return;
                }
            }
        }
    }

    private void addInt(int i) {
        if (this.codeLength >= this.code.length) {
            int[] iArr = new int[this.code.length << 1];
            System.arraycopy(this.code, 0, iArr, 0, this.code.length);
            this.code = iArr;
        }
        int[] iArr2 = this.code;
        int i2 = this.codeLength;
        this.codeLength = i2 + 1;
        iArr2[i2] = i;
    }

    void setInt(int i, int i2) {
        ensure(i2);
        this.regex.code[i2] = i;
    }

    private void addObject(Object obj) {
        if (this.regex.operands == null) {
            this.regex.operands = new Object[4];
        } else if (this.regex.operandLength >= this.regex.operands.length) {
            Object[] objArr = new Object[this.regex.operands.length << 1];
            System.arraycopy(this.regex.operands, 0, objArr, 0, this.regex.operands.length);
            this.regex.operands = objArr;
        }
        addInt(this.regex.operandLength);
        Object[] objArr2 = this.regex.operands;
        Regex regex = this.regex;
        int i = regex.operandLength;
        regex.operandLength = i + 1;
        objArr2[i] = obj;
    }

    private void addChars(char[] cArr, int i, int i2) {
        ensure(this.codeLength + i2);
        int i3 = i;
        int i4 = i3 + i2;
        while (i3 < i4) {
            int[] iArr = this.code;
            int i5 = this.codeLength;
            this.codeLength = i5 + 1;
            int i6 = i3;
            i3++;
            iArr[i5] = cArr[i6];
        }
    }

    private void addInts(int[] iArr, int i) {
        ensure(this.codeLength + i);
        System.arraycopy(iArr, 0, this.code, this.codeLength, i);
        this.codeLength += i;
    }

    private void addOpcode(int i) {
        addInt(i);
        switch (i) {
            case OPCode.ANYCHAR_STAR /* 25 */:
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
            case OPCode.MEMORY_START_PUSH /* 49 */:
            case 50:
            case OPCode.MEMORY_END_PUSH_REC /* 51 */:
            case OPCode.MEMORY_END_REC /* 53 */:
            case 56:
            case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
            case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
            case 60:
            case OPCode.REPEAT_NG /* 61 */:
            case OPCode.REPEAT_INC_NG /* 63 */:
            case 64:
            case OPCode.REPEAT_INC_NG_SG /* 65 */:
            case OPCode.NULL_CHECK_START /* 66 */:
            case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
            case OPCode.PUSH_POS /* 70 */:
            case 72:
            case OPCode.PUSH_STOP_BT /* 74 */:
            case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
            case OPCode.CALL /* 79 */:
            case 80:
            case OPCode.STATE_CHECK_PUSH /* 81 */:
            case OPCode.STATE_CHECK_PUSH_OR_JUMP /* 82 */:
            case OPCode.STATE_CHECK /* 83 */:
            case OPCode.STATE_CHECK_ANYCHAR_STAR /* 84 */:
            case OPCode.STATE_CHECK_ANYCHAR_ML_STAR /* 85 */:
                this.regex.stackNeeded = true;
                break;
        }
    }

    private void addStateCheckNum(int i) {
        addInt(i);
    }

    private void addRelAddr(int i) {
        addInt(i);
    }

    private void addAbsAddr(int i) {
        addInt(i);
    }

    private void addLength(int i) {
        addInt(i);
    }

    private void addMemNum(int i) {
        addInt(i);
    }

    private void addPointer(Object obj) {
        addObject(obj);
    }

    private void addOption(int i) {
        addInt(i);
    }

    private void addOpcodeRelAddr(int i, int i2) {
        addOpcode(i);
        addRelAddr(i2);
    }

    private void addOpcodeOption(int i, int i2) {
        addOpcode(i);
        addOption(i2);
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [char[], char[][], java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v9, types: [char[], char[][]] */
    private void addTemplate(char[] cArr) {
        if (this.templateNum == 0) {
            this.templates = new char[2];
        } else if (this.templateNum == this.templates.length) {
            ?? r0 = new char[this.templateNum * 2];
            System.arraycopy(this.templates, 0, r0, 0, this.templateNum);
            this.templates = r0;
        }
        char[][] cArr2 = this.templates;
        int i = this.templateNum;
        this.templateNum = i + 1;
        cArr2[i] = cArr;
    }
}

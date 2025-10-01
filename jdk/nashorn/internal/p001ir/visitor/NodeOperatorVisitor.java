package jdk.nashorn.internal.p001ir.visitor;

import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/visitor/NodeOperatorVisitor.class */
public abstract class NodeOperatorVisitor extends NodeVisitor {
    public NodeOperatorVisitor(LexicalContext lexicalContext) {
        super(lexicalContext);
    }

    /* renamed from: jdk.nashorn.internal.ir.visitor.NodeOperatorVisitor$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/visitor/NodeOperatorVisitor$1.class */
    static /* synthetic */ class C01691 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_NOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NOT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SUB.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.TYPEOF.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.VOID.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPREFIX.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPOSTFIX.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPREFIX.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPOSTFIX.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.AND.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_ADD.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_BIT_AND.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_BIT_OR.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_BIT_XOR.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_DIV.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_MOD.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_MUL.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SAR.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SHL.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SHR.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SUB.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIND.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_AND.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_OR.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_XOR.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.COMMARIGHT.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.COMMALEFT.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DIV.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f42EQ.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f44GE.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f43GT.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f48IN.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INSTANCEOF.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f41LE.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f40LT.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.MOD.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.MUL.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f39NE.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f45OR.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SAR.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SHL.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SHR.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public final boolean enterUnaryNode(UnaryNode unaryNode) {
        switch (C01691.$SwitchMap$jdk$nashorn$internal$parser$TokenType[unaryNode.tokenType().ordinal()]) {
            case 1:
                return enterADD(unaryNode);
            case 2:
                return enterBIT_NOT(unaryNode);
            case 3:
                return enterDELETE(unaryNode);
            case 4:
                return enterNEW(unaryNode);
            case 5:
                return enterNOT(unaryNode);
            case 6:
                return enterSUB(unaryNode);
            case 7:
                return enterTYPEOF(unaryNode);
            case 8:
                return enterVOID(unaryNode);
            case 9:
            case 10:
            case 11:
            case 12:
                return enterDECINC(unaryNode);
            default:
                return super.enterUnaryNode(unaryNode);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public final Node leaveUnaryNode(UnaryNode unaryNode) {
        switch (C01691.$SwitchMap$jdk$nashorn$internal$parser$TokenType[unaryNode.tokenType().ordinal()]) {
            case 1:
                return leaveADD(unaryNode);
            case 2:
                return leaveBIT_NOT(unaryNode);
            case 3:
                return leaveDELETE(unaryNode);
            case 4:
                return leaveNEW(unaryNode);
            case 5:
                return leaveNOT(unaryNode);
            case 6:
                return leaveSUB(unaryNode);
            case 7:
                return leaveTYPEOF(unaryNode);
            case 8:
                return leaveVOID(unaryNode);
            case 9:
            case 10:
            case 11:
            case 12:
                return leaveDECINC(unaryNode);
            default:
                return super.leaveUnaryNode(unaryNode);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public final boolean enterBinaryNode(BinaryNode binaryNode) {
        switch (C01691.$SwitchMap$jdk$nashorn$internal$parser$TokenType[binaryNode.tokenType().ordinal()]) {
            case 1:
                return enterADD(binaryNode);
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            default:
                return super.enterBinaryNode(binaryNode);
            case 6:
                return enterSUB(binaryNode);
            case CharacterType.ALNUM /* 13 */:
                return enterAND(binaryNode);
            case 14:
                return enterASSIGN(binaryNode);
            case OPCode.EXACTN_IC /* 15 */:
                return enterASSIGN_ADD(binaryNode);
            case 16:
                return enterASSIGN_BIT_AND(binaryNode);
            case OPCode.CCLASS_MB /* 17 */:
                return enterASSIGN_BIT_OR(binaryNode);
            case OPCode.CCLASS_MIX /* 18 */:
                return enterASSIGN_BIT_XOR(binaryNode);
            case OPCode.CCLASS_NOT /* 19 */:
                return enterASSIGN_DIV(binaryNode);
            case 20:
                return enterASSIGN_MOD(binaryNode);
            case OPCode.CCLASS_MIX_NOT /* 21 */:
                return enterASSIGN_MUL(binaryNode);
            case OPCode.CCLASS_NODE /* 22 */:
                return enterASSIGN_SAR(binaryNode);
            case OPCode.ANYCHAR /* 23 */:
                return enterASSIGN_SHL(binaryNode);
            case 24:
                return enterASSIGN_SHR(binaryNode);
            case OPCode.ANYCHAR_STAR /* 25 */:
                return enterASSIGN_SUB(binaryNode);
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
                return enterBIND(binaryNode);
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
                return enterBIT_AND(binaryNode);
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                return enterBIT_OR(binaryNode);
            case OPCode.WORD /* 29 */:
                return enterBIT_XOR(binaryNode);
            case 30:
                return enterCOMMARIGHT(binaryNode);
            case 31:
                return enterCOMMALEFT(binaryNode);
            case 32:
                return enterDIV(binaryNode);
            case OPCode.WORD_BEGIN /* 33 */:
                return enterEQ(binaryNode);
            case 34:
                return enterEQ_STRICT(binaryNode);
            case OPCode.BEGIN_BUF /* 35 */:
                return enterGE(binaryNode);
            case 36:
                return enterGT(binaryNode);
            case OPCode.BEGIN_LINE /* 37 */:
                return enterIN(binaryNode);
            case 38:
                return enterINSTANCEOF(binaryNode);
            case OPCode.SEMI_END_BUF /* 39 */:
                return enterLE(binaryNode);
            case 40:
                return enterLT(binaryNode);
            case OPCode.BACKREF1 /* 41 */:
                return enterMOD(binaryNode);
            case OPCode.BACKREF2 /* 42 */:
                return enterMUL(binaryNode);
            case OPCode.BACKREFN /* 43 */:
                return enterNE(binaryNode);
            case OPCode.BACKREFN_IC /* 44 */:
                return enterNE_STRICT(binaryNode);
            case OPCode.BACKREF_MULTI /* 45 */:
                return enterOR(binaryNode);
            case OPCode.BACKREF_MULTI_IC /* 46 */:
                return enterSAR(binaryNode);
            case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                return enterSHL(binaryNode);
            case 48:
                return enterSHR(binaryNode);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public final Node leaveBinaryNode(BinaryNode binaryNode) {
        switch (C01691.$SwitchMap$jdk$nashorn$internal$parser$TokenType[binaryNode.tokenType().ordinal()]) {
            case 1:
                return leaveADD(binaryNode);
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            default:
                return super.leaveBinaryNode(binaryNode);
            case 6:
                return leaveSUB(binaryNode);
            case CharacterType.ALNUM /* 13 */:
                return leaveAND(binaryNode);
            case 14:
                return leaveASSIGN(binaryNode);
            case OPCode.EXACTN_IC /* 15 */:
                return leaveASSIGN_ADD(binaryNode);
            case 16:
                return leaveASSIGN_BIT_AND(binaryNode);
            case OPCode.CCLASS_MB /* 17 */:
                return leaveASSIGN_BIT_OR(binaryNode);
            case OPCode.CCLASS_MIX /* 18 */:
                return leaveASSIGN_BIT_XOR(binaryNode);
            case OPCode.CCLASS_NOT /* 19 */:
                return leaveASSIGN_DIV(binaryNode);
            case 20:
                return leaveASSIGN_MOD(binaryNode);
            case OPCode.CCLASS_MIX_NOT /* 21 */:
                return leaveASSIGN_MUL(binaryNode);
            case OPCode.CCLASS_NODE /* 22 */:
                return leaveASSIGN_SAR(binaryNode);
            case OPCode.ANYCHAR /* 23 */:
                return leaveASSIGN_SHL(binaryNode);
            case 24:
                return leaveASSIGN_SHR(binaryNode);
            case OPCode.ANYCHAR_STAR /* 25 */:
                return leaveASSIGN_SUB(binaryNode);
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
                return leaveBIND(binaryNode);
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
                return leaveBIT_AND(binaryNode);
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                return leaveBIT_OR(binaryNode);
            case OPCode.WORD /* 29 */:
                return leaveBIT_XOR(binaryNode);
            case 30:
                return leaveCOMMARIGHT(binaryNode);
            case 31:
                return leaveCOMMALEFT(binaryNode);
            case 32:
                return leaveDIV(binaryNode);
            case OPCode.WORD_BEGIN /* 33 */:
                return leaveEQ(binaryNode);
            case 34:
                return leaveEQ_STRICT(binaryNode);
            case OPCode.BEGIN_BUF /* 35 */:
                return leaveGE(binaryNode);
            case 36:
                return leaveGT(binaryNode);
            case OPCode.BEGIN_LINE /* 37 */:
                return leaveIN(binaryNode);
            case 38:
                return leaveINSTANCEOF(binaryNode);
            case OPCode.SEMI_END_BUF /* 39 */:
                return leaveLE(binaryNode);
            case 40:
                return leaveLT(binaryNode);
            case OPCode.BACKREF1 /* 41 */:
                return leaveMOD(binaryNode);
            case OPCode.BACKREF2 /* 42 */:
                return leaveMUL(binaryNode);
            case OPCode.BACKREFN /* 43 */:
                return leaveNE(binaryNode);
            case OPCode.BACKREFN_IC /* 44 */:
                return leaveNE_STRICT(binaryNode);
            case OPCode.BACKREF_MULTI /* 45 */:
                return leaveOR(binaryNode);
            case OPCode.BACKREF_MULTI_IC /* 46 */:
                return leaveSAR(binaryNode);
            case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                return leaveSHL(binaryNode);
            case 48:
                return leaveSHR(binaryNode);
        }
    }

    public boolean enterADD(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveADD(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterBIT_NOT(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveBIT_NOT(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterDECINC(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveDECINC(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterDELETE(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveDELETE(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterNEW(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveNEW(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterNOT(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveNOT(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterSUB(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveSUB(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterTYPEOF(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveTYPEOF(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterVOID(UnaryNode unaryNode) {
        return enterDefault(unaryNode);
    }

    public Node leaveVOID(UnaryNode unaryNode) {
        return leaveDefault(unaryNode);
    }

    public boolean enterADD(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveADD(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterAND(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveAND(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_ADD(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_ADD(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_BIT_AND(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_BIT_AND(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_BIT_OR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_BIT_OR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_BIT_XOR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_BIT_XOR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_DIV(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_DIV(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_MOD(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_MOD(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_MUL(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_MUL(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_SAR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_SAR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_SHL(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_SHL(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_SHR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_SHR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterASSIGN_SUB(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveASSIGN_SUB(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterBIND(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveBIND(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterBIT_AND(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveBIT_AND(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterBIT_OR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveBIT_OR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterBIT_XOR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveBIT_XOR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterCOMMALEFT(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveCOMMALEFT(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterCOMMARIGHT(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveCOMMARIGHT(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterDIV(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveDIV(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterEQ(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveEQ(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterEQ_STRICT(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveEQ_STRICT(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterGE(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveGE(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterGT(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveGT(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterIN(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveIN(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterINSTANCEOF(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveINSTANCEOF(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterLE(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveLE(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterLT(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveLT(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterMOD(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveMOD(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterMUL(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveMUL(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterNE(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveNE(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterNE_STRICT(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveNE_STRICT(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterOR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveOR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterSAR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveSAR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterSHL(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveSHL(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterSHR(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveSHR(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }

    public boolean enterSUB(BinaryNode binaryNode) {
        return enterDefault(binaryNode);
    }

    public Node leaveSUB(BinaryNode binaryNode) {
        return leaveDefault(binaryNode);
    }
}

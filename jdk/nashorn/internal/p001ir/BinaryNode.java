package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/BinaryNode.class */
public final class BinaryNode extends Expression implements Assignment<Expression>, Optimistic {
    private static final long serialVersionUID = 1;
    private static final Type OPTIMISTIC_UNDECIDED_TYPE;
    private final Expression lhs;
    private final Expression rhs;
    private final int programPoint;
    private final Type type;
    private transient Type cachedType;

    @Ignore
    private static final Set<TokenType> CAN_OVERFLOW;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !BinaryNode.class.desiredAssertionStatus();
        OPTIMISTIC_UNDECIDED_TYPE = Type.typeFor(new Object() { // from class: jdk.nashorn.internal.ir.BinaryNode.1
        }.getClass());
        CAN_OVERFLOW = Collections.unmodifiableSet(new HashSet(Arrays.asList(TokenType.ADD, TokenType.DIV, TokenType.MOD, TokenType.MUL, TokenType.SUB, TokenType.ASSIGN_ADD, TokenType.ASSIGN_DIV, TokenType.ASSIGN_MOD, TokenType.ASSIGN_MUL, TokenType.ASSIGN_SUB, TokenType.SHR, TokenType.ASSIGN_SHR)));
    }

    public BinaryNode(long token, Expression lhs, Expression rhs) {
        super(token, lhs.getStart(), rhs.getFinish());
        if (!$assertionsDisabled && ((isTokenType(TokenType.AND) || isTokenType(TokenType.f45OR)) && !(lhs instanceof JoinPredecessorExpression))) {
            throw new AssertionError();
        }
        this.lhs = lhs;
        this.rhs = rhs;
        this.programPoint = -1;
        this.type = null;
    }

    private BinaryNode(BinaryNode binaryNode, Expression lhs, Expression rhs, Type type, int programPoint) {
        super(binaryNode);
        this.lhs = lhs;
        this.rhs = rhs;
        this.programPoint = programPoint;
        this.type = type;
    }

    /* renamed from: jdk.nashorn.internal.ir.BinaryNode$2 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/BinaryNode$2.class */
    static /* synthetic */ class C01522 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f42EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f39NE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f41LE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f40LT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f44GE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f43GT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.AND.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f45OR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SHR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SHR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INSTANCEOF.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ADD.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_ADD.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SAR.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SHL.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_AND.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_OR.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_XOR.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_BIT_AND.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_BIT_OR.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_BIT_XOR.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SAR.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SHL.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DIV.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.MOD.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_DIV.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_MOD.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.MUL.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SUB.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_MUL.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN_SUB.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.VOID.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ASSIGN.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.COMMALEFT.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.COMMARIGHT.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPREFIX.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPREFIX.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
        }
    }

    public boolean isComparison() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public boolean isRelational() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public boolean isLogical() {
        return isLogical(tokenType());
    }

    public static boolean isLogical(TokenType tokenType) {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType.ordinal()]) {
            case 9:
            case 10:
                return true;
            default:
                return false;
        }
    }

    public Type getWidestOperandType() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 11:
            case 12:
                return Type.INT;
            case CharacterType.ALNUM /* 13 */:
                return Type.OBJECT;
            default:
                if (isComparison()) {
                    return Type.OBJECT;
                }
                return getWidestOperationType();
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getWidestOperationType() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 9:
            case 10:
                return Type.widestReturnType(this.lhs.getType(), this.rhs.getType());
            case 11:
            case 12:
                return Type.NUMBER;
            case CharacterType.ALNUM /* 13 */:
                return Type.BOOLEAN;
            case 14:
            case OPCode.EXACTN_IC /* 15 */:
                Type lhsType = this.lhs.getType();
                Type rhsType = this.rhs.getType();
                if (lhsType == Type.BOOLEAN && rhsType == Type.BOOLEAN) {
                    return Type.INT;
                }
                if (isString(lhsType) || isString(rhsType)) {
                    return Type.CHARSEQUENCE;
                }
                Type widestOperandType = Type.widest(undefinedToNumber(booleanToInt(lhsType)), undefinedToNumber(booleanToInt(rhsType)));
                if (widestOperandType.isNumeric()) {
                    return Type.NUMBER;
                }
                return Type.OBJECT;
            case 16:
            case OPCode.CCLASS_MB /* 17 */:
            case OPCode.CCLASS_MIX /* 18 */:
            case OPCode.CCLASS_NOT /* 19 */:
            case 20:
            case OPCode.CCLASS_MIX_NOT /* 21 */:
            case OPCode.CCLASS_NODE /* 22 */:
            case OPCode.ANYCHAR /* 23 */:
            case 24:
            case OPCode.ANYCHAR_STAR /* 25 */:
                return Type.INT;
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
            case OPCode.WORD /* 29 */:
                return Type.NUMBER;
            case 30:
            case 31:
            case 32:
            case OPCode.WORD_BEGIN /* 33 */:
                Type lhsType2 = this.lhs.getType();
                Type rhsType2 = this.rhs.getType();
                if (lhsType2 == Type.BOOLEAN && rhsType2 == Type.BOOLEAN) {
                    return Type.INT;
                }
                return Type.NUMBER;
            case 34:
                return Type.UNDEFINED;
            case OPCode.BEGIN_BUF /* 35 */:
                return this.rhs.getType();
            case 36:
                return this.lhs.getType();
            case OPCode.BEGIN_LINE /* 37 */:
                return this.rhs.getType();
            default:
                if (isComparison()) {
                    return Type.BOOLEAN;
                }
                return Type.OBJECT;
        }
    }

    private static boolean isString(Type type) {
        return type == Type.STRING || type == Type.CHARSEQUENCE;
    }

    private static Type booleanToInt(Type type) {
        return type == Type.BOOLEAN ? Type.INT : type;
    }

    private static Type undefinedToNumber(Type type) {
        return type == Type.UNDEFINED ? Type.NUMBER : type;
    }

    public boolean isAssignment() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 12:
            case OPCode.EXACTN_IC /* 15 */:
            case 16:
            case OPCode.CCLASS_MB /* 17 */:
            case OPCode.CCLASS_MIX_NOT /* 21 */:
            case OPCode.CCLASS_NODE /* 22 */:
            case OPCode.ANYCHAR /* 23 */:
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
            case OPCode.WORD /* 29 */:
            case 32:
            case OPCode.WORD_BEGIN /* 33 */:
            case OPCode.BEGIN_BUF /* 35 */:
                return true;
            case CharacterType.ALNUM /* 13 */:
            case 14:
            case OPCode.CCLASS_MIX /* 18 */:
            case OPCode.CCLASS_NOT /* 19 */:
            case 20:
            case 24:
            case OPCode.ANYCHAR_STAR /* 25 */:
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
            case 30:
            case 31:
            case 34:
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isSelfModifying() {
        return isAssignment() && !isTokenType(TokenType.ASSIGN);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentDest() {
        if (isAssignment()) {
            return lhs();
        }
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public BinaryNode setAssignmentDest(Expression n) {
        return setLHS(n);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentSource() {
        return rhs();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBinaryNode(this)) {
            return visitor.leaveBinaryNode(setLHS((Expression) this.lhs.accept(visitor)).setRHS((Expression) this.rhs.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 11:
            case 14:
            case OPCode.CCLASS_MIX /* 18 */:
            case OPCode.CCLASS_NOT /* 19 */:
            case 20:
            case 24:
            case OPCode.ANYCHAR_STAR /* 25 */:
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
            case 30:
            case 31:
                if (!this.lhs.isLocal() || !this.lhs.getType().isJSPrimitive() || !this.rhs.isLocal() || !this.rhs.getType().isJSPrimitive()) {
                }
                break;
            case 12:
            case OPCode.EXACTN_IC /* 15 */:
            case 16:
            case OPCode.CCLASS_MB /* 17 */:
            case OPCode.CCLASS_MIX_NOT /* 21 */:
            case OPCode.CCLASS_NODE /* 22 */:
            case OPCode.ANYCHAR /* 23 */:
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
            case OPCode.WORD /* 29 */:
            case 32:
            case OPCode.WORD_BEGIN /* 33 */:
                if (!(this.lhs instanceof IdentNode) || !this.lhs.isLocal() || !this.lhs.getType().isJSPrimitive() || !this.rhs.isLocal() || !this.rhs.getType().isJSPrimitive()) {
                }
                break;
            case OPCode.BEGIN_BUF /* 35 */:
                if (!(this.lhs instanceof IdentNode) || !this.lhs.isLocal() || !this.rhs.isLocal()) {
                }
                break;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isAlwaysFalse() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 36:
                return this.lhs.isAlwaysFalse();
            case OPCode.BEGIN_LINE /* 37 */:
                return this.rhs.isAlwaysFalse();
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isAlwaysTrue() {
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 36:
                return this.lhs.isAlwaysTrue();
            case OPCode.BEGIN_LINE /* 37 */:
                return this.rhs.isAlwaysTrue();
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        TokenType tokenType = tokenType();
        boolean lhsParen = tokenType.needsParens(lhs().tokenType(), true);
        boolean rhsParen = tokenType.needsParens(rhs().tokenType(), false);
        if (lhsParen) {
            sb.append('(');
        }
        lhs().toString(sb, printType);
        if (lhsParen) {
            sb.append(')');
        }
        sb.append(' ');
        switch (C01522.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType.ordinal()]) {
            case 36:
                sb.append(",<");
                break;
            case OPCode.BEGIN_LINE /* 37 */:
                sb.append(",>");
                break;
            case 38:
            case OPCode.SEMI_END_BUF /* 39 */:
                sb.append("++");
                break;
            default:
                sb.append(tokenType.getName());
                break;
        }
        if (isOptimistic()) {
            sb.append("%");
        }
        sb.append(' ');
        if (rhsParen) {
            sb.append('(');
        }
        rhs().toString(sb, printType);
        if (rhsParen) {
            sb.append(')');
        }
    }

    public Expression lhs() {
        return this.lhs;
    }

    public Expression rhs() {
        return this.rhs;
    }

    public BinaryNode setLHS(Expression lhs) {
        if (this.lhs == lhs) {
            return this;
        }
        return new BinaryNode(this, lhs, this.rhs, this.type, this.programPoint);
    }

    public BinaryNode setRHS(Expression rhs) {
        if (this.rhs == rhs) {
            return this;
        }
        return new BinaryNode(this, this.lhs, rhs, this.type, this.programPoint);
    }

    public BinaryNode setOperands(Expression lhs, Expression rhs) {
        if (this.lhs == lhs && this.rhs == rhs) {
            return this;
        }
        return new BinaryNode(this, lhs, rhs, this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public boolean canBeOptimistic() {
        return isTokenType(TokenType.ADD) || getMostOptimisticType() != getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public BinaryNode setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new BinaryNode(this, this.lhs, this.rhs, this.type, programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        TokenType tokenType = tokenType();
        if (tokenType == TokenType.ADD || tokenType == TokenType.ASSIGN_ADD) {
            return OPTIMISTIC_UNDECIDED_TYPE;
        }
        if (CAN_OVERFLOW.contains(tokenType)) {
            return Type.INT;
        }
        return getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return getWidestOperationType();
    }

    public boolean isOptimisticUndecidedType() {
        return this.type == OPTIMISTIC_UNDECIDED_TYPE;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        if (this.cachedType == null) {
            this.cachedType = getTypeUncached();
        }
        return this.cachedType;
    }

    private Type getTypeUncached() {
        if (this.type == OPTIMISTIC_UNDECIDED_TYPE) {
            return decideType(this.lhs.getType(), this.rhs.getType());
        }
        Type widest = getWidestOperationType();
        if (this.type == null) {
            return widest;
        }
        if (tokenType() == TokenType.ASSIGN_SHR || tokenType() == TokenType.SHR) {
            return this.type;
        }
        return Type.narrowest(widest, Type.widest(this.type, Type.widest(this.lhs.getType(), this.rhs.getType())));
    }

    private static Type decideType(Type lhsType, Type rhsType) {
        if (isString(lhsType) || isString(rhsType)) {
            return Type.CHARSEQUENCE;
        }
        Type widest = Type.widest(undefinedToNumber(booleanToInt(lhsType)), undefinedToNumber(booleanToInt(rhsType)));
        return widest.isObject() ? Type.OBJECT : widest;
    }

    public BinaryNode decideType() {
        if ($assertionsDisabled || this.type == OPTIMISTIC_UNDECIDED_TYPE) {
            return setType(decideType(this.lhs.getType(), this.rhs.getType()));
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public BinaryNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new BinaryNode(this, this.lhs, this.rhs, type, this.programPoint);
    }
}

package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/UnaryNode.class */
public final class UnaryNode extends Expression implements Assignment<Expression>, Optimistic {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final int programPoint;
    private final Type type;

    @Ignore
    private static final List<TokenType> CAN_OVERFLOW;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !UnaryNode.class.desiredAssertionStatus();
        CAN_OVERFLOW = Collections.unmodifiableList(Arrays.asList(TokenType.ADD, TokenType.SUB, TokenType.DECPREFIX, TokenType.DECPOSTFIX, TokenType.INCPREFIX, TokenType.INCPOSTFIX));
    }

    public UnaryNode(long token, Expression rhs) {
        this(token, Math.min(rhs.getStart(), Token.descPosition(token)), Math.max(Token.descPosition(token) + Token.descLength(token), rhs.getFinish()), rhs);
    }

    public UnaryNode(long token, int start, int finish, Expression expression) {
        super(token, start, finish);
        this.expression = expression;
        this.programPoint = -1;
        this.type = null;
    }

    private UnaryNode(UnaryNode unaryNode, Expression expression, Type type, int programPoint) {
        super(unaryNode);
        this.expression = expression;
        this.programPoint = programPoint;
        this.type = type;
    }

    /* renamed from: jdk.nashorn.internal.ir.UnaryNode$2 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/UnaryNode$2.class */
    static /* synthetic */ class C01582 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPOSTFIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPREFIX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPOSTFIX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPREFIX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ADD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SUB.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NOT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DELETE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_NOT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.VOID.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NEW.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public boolean isAssignment() {
        switch (C01582.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isSelfModifying() {
        return isAssignment();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getWidestOperationType() {
        switch (C01582.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 5:
                Type operandType = getExpression().getType();
                if (operandType == Type.BOOLEAN) {
                    return Type.INT;
                }
                if (operandType.isObject()) {
                    return Type.NUMBER;
                }
                if ($assertionsDisabled || operandType.isNumeric()) {
                    return operandType;
                }
                throw new AssertionError();
            case 6:
                return Type.NUMBER;
            case 7:
            case 8:
                return Type.BOOLEAN;
            case 9:
                return Type.INT;
            case 10:
                return Type.UNDEFINED;
            default:
                return isAssignment() ? Type.NUMBER : Type.OBJECT;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentDest() {
        if (isAssignment()) {
            return getExpression();
        }
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public UnaryNode setAssignmentDest(Expression n) {
        return setExpression(n);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentSource() {
        return getAssignmentDest();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterUnaryNode(this)) {
            return visitor.leaveUnaryNode(setExpression((Expression) this.expression.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        switch (C01582.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return (this.expression instanceof IdentNode) && this.expression.isLocal() && this.expression.getType().isJSPrimitive();
            case 5:
            case 6:
            case 7:
            case 9:
                return this.expression.isLocal() && this.expression.getType().isJSPrimitive();
            case 8:
            case 10:
            default:
                return this.expression.isLocal();
            case 11:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        toString(sb, new Runnable(this, sb, printType) { // from class: jdk.nashorn.internal.ir.UnaryNode.1
            final StringBuilder val$sb;
            final boolean val$printType;
            final UnaryNode this$0;

            {
                this.this$0 = this;
                this.val$sb = sb;
                this.val$printType = printType;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.this$0.getExpression().toString(this.val$sb, this.val$printType);
            }
        }, printType);
    }

    public void toString(StringBuilder sb, Runnable rhsStringBuilder, boolean printType) {
        TokenType tokenType = tokenType();
        String name = tokenType.getName();
        boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
        if (isOptimistic()) {
            sb.append("%");
        }
        boolean rhsParen = tokenType.needsParens(getExpression().tokenType(), false);
        if (!isPostfix) {
            if (name == null) {
                sb.append(tokenType.name());
                rhsParen = true;
            } else {
                sb.append(name);
                if (tokenType.ordinal() > TokenType.BIT_NOT.ordinal()) {
                    sb.append(' ');
                }
            }
        }
        if (rhsParen) {
            sb.append('(');
        }
        rhsStringBuilder.run();
        if (rhsParen) {
            sb.append(')');
        }
        if (isPostfix) {
            sb.append(tokenType == TokenType.DECPOSTFIX ? "--" : "++");
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public UnaryNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new UnaryNode(this, expression, this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public UnaryNode setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new UnaryNode(this, this.expression, this.type, programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public boolean canBeOptimistic() {
        return getMostOptimisticType() != getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        if (CAN_OVERFLOW.contains(tokenType())) {
            return Type.INT;
        }
        return getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return getWidestOperationType();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        Type widest = getWidestOperationType();
        if (this.type == null) {
            return widest;
        }
        return Type.narrowest(widest, Type.widest(this.type, this.expression.getType()));
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public UnaryNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new UnaryNode(this, this.expression, type, this.programPoint);
    }
}

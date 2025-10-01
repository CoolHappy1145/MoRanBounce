package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/TernaryNode.class */
public final class TernaryNode extends Expression {
    private static final long serialVersionUID = 1;
    private final Expression test;
    private final JoinPredecessorExpression trueExpr;
    private final JoinPredecessorExpression falseExpr;

    public TernaryNode(long j, Expression expression, JoinPredecessorExpression joinPredecessorExpression, JoinPredecessorExpression joinPredecessorExpression2) {
        super(j, joinPredecessorExpression2.getFinish());
        this.test = expression;
        this.trueExpr = joinPredecessorExpression;
        this.falseExpr = joinPredecessorExpression2;
    }

    private TernaryNode(TernaryNode ternaryNode, Expression expression, JoinPredecessorExpression joinPredecessorExpression, JoinPredecessorExpression joinPredecessorExpression2) {
        super(ternaryNode);
        this.test = expression;
        this.trueExpr = joinPredecessorExpression;
        this.falseExpr = joinPredecessorExpression2;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterTernaryNode(this)) {
            Expression expression = (Expression) getTest().accept(nodeVisitor);
            JoinPredecessorExpression joinPredecessorExpression = (JoinPredecessorExpression) this.trueExpr.accept(nodeVisitor);
            return nodeVisitor.leaveTernaryNode(setTest(expression).setTrueExpression(joinPredecessorExpression).setFalseExpression((JoinPredecessorExpression) this.falseExpr.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        TokenType tokenType = tokenType();
        boolean zNeedsParens = tokenType.needsParens(getTest().tokenType(), true);
        boolean zNeedsParens2 = tokenType.needsParens(getTrueExpression().tokenType(), false);
        boolean zNeedsParens3 = tokenType.needsParens(getFalseExpression().tokenType(), false);
        if (zNeedsParens) {
            sb.append('(');
        }
        getTest().toString(sb, z);
        if (zNeedsParens) {
            sb.append(')');
        }
        sb.append(" ? ");
        if (zNeedsParens2) {
            sb.append('(');
        }
        getTrueExpression().toString(sb, z);
        if (zNeedsParens2) {
            sb.append(')');
        }
        sb.append(" : ");
        if (zNeedsParens3) {
            sb.append('(');
        }
        getFalseExpression().toString(sb, z);
        if (zNeedsParens3) {
            sb.append(')');
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        getTest();
        return 0 != 0 && getTrueExpression().isLocal() && getFalseExpression().isLocal();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return Type.widestReturnType(getTrueExpression().getType(), getFalseExpression().getType());
    }

    public Expression getTest() {
        return this.test;
    }

    public JoinPredecessorExpression getTrueExpression() {
        return this.trueExpr;
    }

    public JoinPredecessorExpression getFalseExpression() {
        return this.falseExpr;
    }

    public TernaryNode setTest(Expression expression) {
        if (this.test == expression) {
            return this;
        }
        return new TernaryNode(this, expression, this.trueExpr, this.falseExpr);
    }

    public TernaryNode setTrueExpression(JoinPredecessorExpression joinPredecessorExpression) {
        if (this.trueExpr == joinPredecessorExpression) {
            return this;
        }
        return new TernaryNode(this, this.test, joinPredecessorExpression, this.falseExpr);
    }

    public TernaryNode setFalseExpression(JoinPredecessorExpression joinPredecessorExpression) {
        if (this.falseExpr == joinPredecessorExpression) {
            return this;
        }
        return new TernaryNode(this, this.test, this.trueExpr, joinPredecessorExpression);
    }
}

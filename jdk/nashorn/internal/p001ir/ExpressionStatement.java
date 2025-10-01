package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/ExpressionStatement.class */
public final class ExpressionStatement extends Statement {
    private static final long serialVersionUID = 1;
    private final Expression expression;

    public ExpressionStatement(int i, long j, int i2, Expression expression) {
        super(i, j, i2);
        this.expression = expression;
    }

    private ExpressionStatement(ExpressionStatement expressionStatement, Expression expression) {
        super(expressionStatement);
        this.expression = expression;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterExpressionStatement(this)) {
            return nodeVisitor.leaveExpressionStatement(setExpression((Expression) this.expression.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        this.expression.toString(sb, z);
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ExpressionStatement setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ExpressionStatement(this, expression);
    }
}

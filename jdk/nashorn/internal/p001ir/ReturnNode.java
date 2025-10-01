package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/ReturnNode.class */
public class ReturnNode extends Statement {
    private static final long serialVersionUID = 1;
    private final Expression expression;

    public ReturnNode(int i, long j, int i2, Expression expression) {
        super(i, j, i2);
        this.expression = expression;
    }

    private ReturnNode(ReturnNode returnNode, Expression expression) {
        super(returnNode);
        this.expression = expression;
    }

    public boolean isReturn() {
        return isTokenType(TokenType.RETURN);
    }

    public boolean hasExpression() {
        return this.expression != null;
    }

    public boolean isYield() {
        return isTokenType(TokenType.YIELD);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterReturnNode(this)) {
            if (this.expression != null) {
                return nodeVisitor.leaveReturnNode(setExpression((Expression) this.expression.accept(nodeVisitor)));
            }
            return nodeVisitor.leaveReturnNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(isYield() ? "yield" : "return");
        if (this.expression != null) {
            sb.append(' ');
            this.expression.toString(sb, z);
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ReturnNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ReturnNode(this, expression);
    }
}

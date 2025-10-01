package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/ThrowNode.class */
public final class ThrowNode extends Statement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final LocalVariableConversion conversion;
    private final boolean isSyntheticRethrow;

    public ThrowNode(int i, long j, int i2, Expression expression, boolean z) {
        super(i, j, i2);
        this.expression = expression;
        this.isSyntheticRethrow = z;
        this.conversion = null;
    }

    private ThrowNode(ThrowNode throwNode, Expression expression, boolean z, LocalVariableConversion localVariableConversion) {
        super(throwNode);
        this.expression = expression;
        this.isSyntheticRethrow = z;
        this.conversion = localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterThrowNode(this)) {
            return nodeVisitor.leaveThrowNode(setExpression((Expression) this.expression.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("throw ");
        if (this.expression != null) {
            this.expression.toString(sb, z);
        }
        if (this.conversion != null) {
            this.conversion.toString(sb);
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ThrowNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ThrowNode(this, expression, this.isSyntheticRethrow, this.conversion);
    }

    public boolean isSyntheticRethrow() {
        return this.isSyntheticRethrow;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return new ThrowNode(this, this.expression, this.isSyntheticRethrow, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}

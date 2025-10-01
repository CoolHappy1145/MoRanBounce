package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/WithNode.class */
public final class WithNode extends LexicalContextStatement {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final Block body;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public WithNode(int i, long j, int i2) {
        super(i, j, i2);
        this.expression = null;
        this.body = null;
    }

    private WithNode(WithNode withNode, Expression expression, Block block) {
        super(withNode);
        this.expression = expression;
        this.body = block;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterWithNode(this)) {
            return nodeVisitor.leaveWithNode(setExpression(lexicalContext, (Expression) this.expression.accept(nodeVisitor)).setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("with (");
        this.expression.toString(sb, z);
        sb.append(')');
    }

    public Block getBody() {
        return this.body;
    }

    public WithNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (WithNode) Node.replaceInLexicalContext(lexicalContext, this, new WithNode(this, this.expression, block));
    }

    public Expression getExpression() {
        return this.expression;
    }

    public WithNode setExpression(LexicalContext lexicalContext, Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return (WithNode) Node.replaceInLexicalContext(lexicalContext, this, new WithNode(this, expression, this.body));
    }
}

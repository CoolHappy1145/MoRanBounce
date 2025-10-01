package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/CatchNode.class */
public final class CatchNode extends Statement {
    private static final long serialVersionUID = 1;
    private final IdentNode exception;
    private final Expression exceptionCondition;
    private final Block body;
    private final boolean isSyntheticRethrow;

    public CatchNode(int i, long j, int i2, IdentNode identNode, Expression expression, Block block, boolean z) {
        super(i, j, i2);
        this.exception = identNode == null ? null : identNode.setIsInitializedHere();
        this.exceptionCondition = expression;
        this.body = block;
        this.isSyntheticRethrow = z;
    }

    private CatchNode(CatchNode catchNode, IdentNode identNode, Expression expression, Block block, boolean z) {
        super(catchNode);
        this.exception = identNode;
        this.exceptionCondition = expression;
        this.body = block;
        this.isSyntheticRethrow = z;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterCatchNode(this)) {
            return nodeVisitor.leaveCatchNode(setException((IdentNode) this.exception.accept(nodeVisitor)).setExceptionCondition(this.exceptionCondition == null ? null : (Expression) this.exceptionCondition.accept(nodeVisitor)).setBody((Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(" catch (");
        this.exception.toString(sb, z);
        if (this.exceptionCondition != null) {
            sb.append(" if ");
            this.exceptionCondition.toString(sb, z);
        }
        sb.append(')');
    }

    public IdentNode getException() {
        return this.exception;
    }

    public Expression getExceptionCondition() {
        return this.exceptionCondition;
    }

    public CatchNode setExceptionCondition(Expression expression) {
        if (this.exceptionCondition == expression) {
            return this;
        }
        return new CatchNode(this, this.exception, expression, this.body, this.isSyntheticRethrow);
    }

    public Block getBody() {
        return this.body;
    }

    public CatchNode setException(IdentNode identNode) {
        if (this.exception == identNode) {
            return this;
        }
        return new CatchNode(this, identNode, this.exceptionCondition, this.body, this.isSyntheticRethrow);
    }

    private CatchNode setBody(Block block) {
        if (this.body == block) {
            return this;
        }
        return new CatchNode(this, this.exception, this.exceptionCondition, block, this.isSyntheticRethrow);
    }

    public boolean isSyntheticRethrow() {
        return this.isSyntheticRethrow;
    }
}

package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/IfNode.class */
public final class IfNode extends Statement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final Expression test;
    private final Block pass;
    private final Block fail;
    private final LocalVariableConversion conversion;

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    public IfNode(int i, long j, int i2, Expression expression, Block block, Block block2) {
        super(i, j, i2);
        this.test = expression;
        this.pass = block;
        this.fail = block2;
        this.conversion = null;
    }

    private IfNode(IfNode ifNode, Expression expression, Block block, Block block2, LocalVariableConversion localVariableConversion) {
        super(ifNode);
        this.test = expression;
        this.pass = block;
        this.fail = block2;
        this.conversion = localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.pass.isTerminal() && this.fail != null && this.fail.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterIfNode(this)) {
            return nodeVisitor.leaveIfNode(setTest((Expression) this.test.accept(nodeVisitor)).setPass((Block) this.pass.accept(nodeVisitor)).setFail(this.fail == null ? null : (Block) this.fail.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("if (");
        this.test.toString(sb, z);
        sb.append(')');
    }

    public Block getFail() {
        return this.fail;
    }

    private IfNode setFail(Block block) {
        if (this.fail == block) {
            return this;
        }
        return new IfNode(this, this.test, this.pass, block, this.conversion);
    }

    public Block getPass() {
        return this.pass;
    }

    private IfNode setPass(Block block) {
        if (this.pass == block) {
            return this;
        }
        return new IfNode(this, this.test, block, this.fail, this.conversion);
    }

    public Expression getTest() {
        return this.test;
    }

    public IfNode setTest(Expression expression) {
        if (this.test == expression) {
            return this;
        }
        return new IfNode(this, expression, this.pass, this.fail, this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public IfNode setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return new IfNode(this, this.test, this.pass, this.fail, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}

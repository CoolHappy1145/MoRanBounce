package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/WhileNode.class */
public final class WhileNode extends LoopNode {
    private static final long serialVersionUID = 1;
    private final boolean isDoWhile;

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public LoopNode setControlFlowEscapes(LexicalContext lexicalContext, boolean z) {
        return setControlFlowEscapes(lexicalContext, z);
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public LoopNode setTest(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression) {
        return setTest(lexicalContext, joinPredecessorExpression);
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public LoopNode setBody(LexicalContext lexicalContext, Block block) {
        return setBody(lexicalContext, block);
    }

    public WhileNode(int i, long j, int i2, boolean z) {
        super(i, j, i2, (Block) null, false);
        this.isDoWhile = z;
    }

    private WhileNode(WhileNode whileNode, JoinPredecessorExpression joinPredecessorExpression, Block block, boolean z, LocalVariableConversion localVariableConversion) {
        super(whileNode, joinPredecessorExpression, block, z, localVariableConversion);
        this.isDoWhile = whileNode.isDoWhile;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lexicalContext) {
        return (Node) Node.replaceInLexicalContext(lexicalContext, this, new WhileNode(this, this.test, this.body, this.controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.Statement
    public boolean hasGoto() {
        return this.test == null;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterWhileNode(this)) {
            if (isDoWhile()) {
                return nodeVisitor.leaveWhileNode(setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)).setTest(lexicalContext, (JoinPredecessorExpression) this.test.accept(nodeVisitor)));
            }
            return nodeVisitor.leaveWhileNode(setTest(lexicalContext, (JoinPredecessorExpression) this.test.accept(nodeVisitor)).setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public WhileNode setTest(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression) {
        if (this.test == joinPredecessorExpression) {
            return this;
        }
        return (WhileNode) Node.replaceInLexicalContext(lexicalContext, this, new WhileNode(this, joinPredecessorExpression, this.body, this.controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public Block getBody() {
        return this.body;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public WhileNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (WhileNode) Node.replaceInLexicalContext(lexicalContext, this, new WhileNode(this, this.test, block, this.controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public WhileNode setControlFlowEscapes(LexicalContext lexicalContext, boolean z) {
        if (this.controlFlowEscapes == z) {
            return this;
        }
        return (WhileNode) Node.replaceInLexicalContext(lexicalContext, this, new WhileNode(this, this.test, this.body, z, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement
    JoinPredecessor setLocalVariableConversionChanged(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return (JoinPredecessor) Node.replaceInLexicalContext(lexicalContext, this, new WhileNode(this, this.test, this.body, this.controlFlowEscapes, localVariableConversion));
    }

    public boolean isDoWhile() {
        return this.isDoWhile;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("while (");
        this.test.toString(sb, z);
        sb.append(')');
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean mustEnter() {
        return isDoWhile() || this.test == null;
    }
}
